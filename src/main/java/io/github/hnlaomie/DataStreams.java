package io.github.hnlaomie;


import io.github.hnlaomie.common.constant.Constants;
import io.github.hnlaomie.common.constant.MessageID;
import io.github.hnlaomie.common.util.ExceptionUtil;
import io.github.hnlaomie.common.util.exception.DspDeserializationExceptionHandler;
import io.github.hnlaomie.common.util.exception.DspProductionExceptionHandler;
import io.github.hnlaomie.common.util.exception.LogException;
import io.github.hnlaomie.common.util.exception.SystemException;
import io.github.hnlaomie.data.DataLog;
import io.github.hnlaomie.parser.ExtractManager;
import io.confluent.kafka.streams.serdes.avro.GenericAvroSerde;
import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.ValueMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @author <a href="mailto:hnlaomie@163.com">李春辉</a>
 * @date 2018-09-17
 * kafka streams 从各主题读取csv格式数据,清洗转换为avro后,写入到新的主题
 */
public class DataStreams {
    // 日志处理器
    private static Logger logger = LoggerFactory.getLogger(Constants.APP_LOGGER);

    private void topicStream(String topic, StreamsBuilder builder) {
        logger.info("topicStream: " + topic);
        final Serde<String> stringSerde = Serdes.String();
        final Serde<byte[]> byteArraySerde = Serdes.ByteArray();
        final KStream<byte[], String> textLines = builder.stream(topic, Consumed.with(byteArraySerde, stringSerde));
        final KStream<byte[], GenericRecord> result = textLines.flatMapValues(new ValueMapper<String, Iterable<GenericRecord>>() {
            @Override
            public Iterable<GenericRecord> apply(String value) {
                List<GenericRecord> dataList = new ArrayList<>();
                try {
                    ExtractManager manager = ExtractManager.getInstance();
                    List<DataLog> logList = manager.build(topic, value);
                    for (DataLog log : logList) {
                        GenericRecord record = manager.getRecord(log);
                        dataList.add(record);
                        logger.info("成功处理: " + record.toString());
                    }
                } catch (LogException e) {
                    logger.error(e.getErrMessage());
                    if (e instanceof SystemException) {
                        throw e;
                    }
                } catch (Exception e) {
                    String[] params = {value};
                    LogException exp = ExceptionUtil.handle(MessageID.MSG_010010, params, e);
                    logger.error(exp.toString());
                }
                return dataList;
            }
        });
        // final KStream<byte[], String> uppercasedWithMapValues = textLines.mapValues(v -> v.toLowerCase());
        result.to(Constants.SINK_TOPIC);
    }

    public void csvToAvro() {
        final Properties streamsConfiguration = new Properties();
        // Give the Streams application a unique name.  The name must be unique in the Kafka cluster
        // against which the application is run.
        streamsConfiguration.put(StreamsConfig.APPLICATION_ID_CONFIG, Constants.APPLICATION_ID_CONFIG);
        // Where to find Kafka broker(s).
        streamsConfiguration.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, Constants.BOOTSTRAP_SERVERS_CONFIG);
        // Specify default (de)serializers for record keys and for record values.
        streamsConfiguration.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.ByteArray().getClass().getName());
        //streamsConfiguration.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        streamsConfiguration.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, GenericAvroSerde.class);
        //streamsConfiguration.put(AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, "http://192.168.1.20:8083");
        streamsConfiguration.put("schema.registry.url", Constants.SCHEMA_REGISTRY_URL_CONFIG);
        streamsConfiguration.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, Constants.AUTO_OFFSET_RESET_CONFIG);
        // for exception handle
        streamsConfiguration.put(StreamsConfig.DEFAULT_DESERIALIZATION_EXCEPTION_HANDLER_CLASS_CONFIG, DspDeserializationExceptionHandler.class);
        streamsConfiguration.put(StreamsConfig.DEFAULT_PRODUCTION_EXCEPTION_HANDLER_CLASS_CONFIG, DspProductionExceptionHandler.class);

        final StreamsBuilder builder = new StreamsBuilder();
        // 启动stream做数据转换
        for (String topic : Constants.SOURCE_TOPICS) {
            topicStream(topic, builder);
        }

        final KafkaStreams streams = new KafkaStreams(builder.build(), streamsConfiguration);
        streams.setUncaughtExceptionHandler((Thread thread, Throwable throwable) -> {
            // 异常写日志
            String errMsg = ExceptionUtil.getThrowableMessage(throwable);
            logger.error(errMsg);
        });

        streams.cleanUp();
        streams.start();

        // Add shutdown hook to respond to SIGTERM and gracefully close Kafka Streams
        Runtime.getRuntime().addShutdownHook(new Thread(streams::close));
    }

    public static void main(String[] args) {
        DataStreams stream = new DataStreams();
        stream.csvToAvro();
    }
}
