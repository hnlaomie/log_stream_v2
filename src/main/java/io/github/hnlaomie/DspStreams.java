package io.github.hnlaomie;

import io.confluent.kafka.streams.serdes.avro.GenericAvroSerde;
import io.github.hnlaomie.common.constant.Constants;
import io.github.hnlaomie.common.constant.MessageID;
import io.github.hnlaomie.common.util.ExceptionUtil;
import io.github.hnlaomie.common.util.exception.LogException;
import io.github.hnlaomie.data.DspLog;
import io.github.hnlaomie.parser.ExtractManager;
import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.Consumed;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.ValueMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:lichunhui@adwo.com">李春辉</a>
 * @date 2018-07-23
 */
public class DspStreams {
    // 日志处理器
    private static Logger logger = LoggerFactory.getLogger("test");

    public String loadTestData() throws Exception {
        String content = "";

        String file = "/data/dspc.csv";
        String lineSeparator = System.lineSeparator();
        try (InputStream in = getClass().getResourceAsStream(file);) {
            content = new BufferedReader(new InputStreamReader(in))
                    .lines()
                    .parallel()
                    .collect(Collectors.joining(lineSeparator));
        } catch (IOException e) {
            throw e;
        }

        return content;
    }


    public void test() {
        try {
            ExtractManager manager = ExtractManager.getInstance();
            String content = loadTestData();
            List<DspLog> logList = manager.build(Constants.TOPIC_DSPC, content);
            logger.info(logList.toString());
        } catch (Exception e) {
            LogException exp = ExceptionUtil.handle(MessageID.MSG_010009, e);
            logger.error(exp.toString());
        }
    }

    public void topicStream(String topic, StreamsBuilder builder) {
        logger.info("topicStream: " + topic);
        final Serde<String> stringSerde = Serdes.String();
        final Serde<byte[]> byteArraySerde = Serdes.ByteArray();
        final KStream<byte[], String> textLines = builder.stream(topic, Consumed.with(byteArraySerde, stringSerde));
        final KStream<byte[], GenericRecord> result = textLines.flatMapValues(new ValueMapper<String, Iterable<GenericRecord>>() {
            @Override
            public Iterable<GenericRecord> apply(String value) {
                List<GenericRecord> dataList = new ArrayList<>();
                ExtractManager manager = ExtractManager.getInstance();
                List<DspLog> logList = manager.build(topic, value);
                for (DspLog log: logList) {
                    GenericRecord record = manager.getRecord(log);
                    logger.info(record.toString());
                    dataList.add(record);
                }
                return dataList;
            }
        });
        // final KStream<byte[], String> uppercasedWithMapValues = textLines.mapValues(v -> v.toLowerCase());
        result.to("dsplogs");
    }

    public void streamTest() {
        final String bootstrapServers = "192.168.1.20:9092";
        final Properties streamsConfiguration = new Properties();
        // Give the Streams application a unique name.  The name must be unique in the Kafka cluster
        // against which the application is run.
        streamsConfiguration.put(StreamsConfig.APPLICATION_ID_CONFIG, "dsp-streams");
        // Where to find Kafka broker(s).
        streamsConfiguration.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        // Specify default (de)serializers for record keys and for record values.
        streamsConfiguration.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.ByteArray().getClass().getName());
        //streamsConfiguration.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        streamsConfiguration.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, GenericAvroSerde.class);
        //streamsConfiguration.put(AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, "http://192.168.1.20:8083");
        streamsConfiguration.put("schema.registry.url", "http://192.168.1.20:8081");
        streamsConfiguration.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        final StreamsBuilder builder = new StreamsBuilder();
        topicStream("dsps", builder);
        topicStream("dspc", builder);

        final KafkaStreams streams = new KafkaStreams(builder.build(), streamsConfiguration);

        streams.cleanUp();
        streams.start();

        // Add shutdown hook to respond to SIGTERM and gracefully close Kafka Streams
        Runtime.getRuntime().addShutdownHook(new Thread(streams::close));
    }

    public static void main(String[] args) {
        DspStreams test = new DspStreams();
        test.streamTest();
    }
}
