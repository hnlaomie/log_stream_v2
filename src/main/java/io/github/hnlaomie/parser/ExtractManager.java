package io.github.hnlaomie.parser;

import io.github.hnlaomie.common.constant.Constants;
import io.github.hnlaomie.common.constant.MessageID;
import io.github.hnlaomie.common.util.exception.SystemException;
import io.github.hnlaomie.data.DataConfig;
import io.github.hnlaomie.data.DataLog;
import io.github.hnlaomie.data.ParserConfig;
import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:hnlaomie@163.com">李春辉</a>
 * @date 2018-09-17
 * 数据清洗解析器管理, 采用单例, 每类主题有一个解析器, 同主题数据用该解析器处理数据
 */
public final class ExtractManager {
    private static ExtractManager manager = null;
    // 各主题解析器
    private Map<String, IDataParser> parserMap = new HashMap<>();
    // avro schema
    private Schema schema = null;
    // 日志处理器
    private static Logger logger = LoggerFactory.getLogger(Constants.APP_LOGGER);

    public static ExtractManager getInstance() {
        if (manager == null) {
            synchronized (ExtractManager.class) {
                if (manager == null) {
                    manager = new ExtractManager();
                    manager.loadParser();
                    manager.loadAvroSchema();
                }
            }
        }
        return manager;
    }

    /**
     * 载入指定资源文件的内容
     * @param configFile
     * @return
     */
    private String loadConfigContent(String configFile) {
        String content = "";
        String lineSeparator = System.lineSeparator();
        try (InputStream in = getClass().getResourceAsStream(configFile);) {
            content = new BufferedReader(new InputStreamReader(in))
                    .lines()
                    .parallel()
                    .collect(Collectors.joining(lineSeparator));
        }  catch (Exception e) {
            SystemException exp = new SystemException(MessageID.MSG_010009, e);
            throw exp;
        }
        return content;
    }

    /**
     * 从配置文件(/config/parser/data.json)生成相关主题的解析器
     */
    private void loadParser() {
        String content = loadConfigContent(Constants.DATA_PARSER_CONFIG_FILE);
        DataConfig dataConfig = ConfigLoader.loadDspConfig(content);
        try {
            for (ParserConfig config : dataConfig.getParserList()) {
                String topic = config.getTopic();
                String buildClass = config.getBuildClass();
                IDataParser parser = (IDataParser) Class.forName(buildClass).newInstance();
                parserMap.put(topic, parser);
            }
        } catch (Exception e) {
            SystemException exp = new SystemException(MessageID.MSG_010009, e);
            throw exp;
        }
        logger.info("成功载入dsp解析配置文件。");
    }

    /**
     * 载入avro schema
     */
    private void loadAvroSchema() {
        /*
        Schema schema = new Schema.Parser().parse(
        getClass().getResourceAsStream("/avro/io/confluent/examples/streams/wikifeed.avsc"));
         */
        String content = loadConfigContent(Constants.AVRO_SCHEMA_FILE);
        Schema.Parser parser = new Schema.Parser();
        this.schema = parser.parse(content);
        logger.info("成功载入avro schema。");
    }

    /**
     * 清洗指定主题的数据
     * @param topic
     * @param content
     * @return null或dsp数据对象列表
     */
    public List<DataLog> build (String topic, String content) {
        List<DataLog> logList = null;
        IDataParser parser = parserMap.get(topic);
        if (parser != null) {
            logList = parser.build(topic, content);
        }
        return logList;
    }

    public GenericRecord getRecord(DataLog dspLog) {
        GenericRecord avroRecord = new GenericData.Record(schema);
        avroRecord.put("log_date", dspLog.getLogDate());
        avroRecord.put("log_hour", dspLog.getLogHour());
        avroRecord.put("log_time", dspLog.getLogTime());
        avroRecord.put("device_id", dspLog.getDeviceId());
        avroRecord.put("area_id", dspLog.getAreaId());
        avroRecord.put("ip", dspLog.getIp());
        avroRecord.put("event_id", dspLog.getEventId());
        return avroRecord;
    }

}
