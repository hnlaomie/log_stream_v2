package io.github.hnlaomie.parser;

import io.github.hnlaomie.common.constant.Constants;
import io.github.hnlaomie.common.constant.MessageID;
import io.github.hnlaomie.common.util.ExceptionUtil;
import io.github.hnlaomie.common.util.exception.LogException;
import io.github.hnlaomie.data.DspConfig;
import io.github.hnlaomie.data.DspLog;
import io.github.hnlaomie.data.ParserConfig;
import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:lichunhui@adwo.com">李春辉</a>
 * @date 2018-07-23
 * dsp数据清洗解析器管理, 采用单例, 每类主题有一个解析器, 同主题数据用该解析器处理数据
 */
public final class ExtractManager {
    private static ExtractManager manager = null;
    // 各主题解析器
    private Map<String, IDataParser> parserMap = new HashMap<>();
    // avro schema
    private Schema schema = null;
    // 日志处理器
    private static Logger logger = LoggerFactory.getLogger(Constants.APP_LOGGER);

    public synchronized static ExtractManager getInstance() {
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
        }  catch (IOException e) {
            LogException exp = ExceptionUtil.handle(MessageID.MSG_010009, e);
            throw exp;
        }
        return content;
    }

    /**
     * 从配置文件(/config/parser/dsp.json)生成相关主题的解析器
     */
    private void loadParser() {
        String content = loadConfigContent(Constants.DSP_PARSER_CONFIG_FILE);
        DspConfig dspConfig = ConfigLoader.loadDspConfig(content);
        try {
            for (ParserConfig config : dspConfig.getParserList()) {
                String topic = config.getTopic();
                String buildClass = config.getBuildClass();
                IDataParser parser = (IDataParser) Class.forName(buildClass).newInstance();
                parserMap.put(topic, parser);
            }
        } catch (Exception e) {
            LogException exp = ExceptionUtil.handle(MessageID.MSG_010009, e);
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
    public List<DspLog> build (String topic, String content) {
        List<DspLog> logList = null;
        IDataParser parser = parserMap.get(topic);
        if (parser != null) {
            logList = parser.build(topic, content);
        }
        return logList;
    }

    public GenericRecord getRecord(DspLog dspLog) {
        GenericRecord avroRecord = new GenericData.Record(schema);
        avroRecord.put("log_date", dspLog.getLogDate());
        avroRecord.put("log_hour", dspLog.getLogHour());
        avroRecord.put("bid_id", dspLog.getBidId());
        avroRecord.put("log_type", dspLog.getLogType());
        avroRecord.put("exchange_id", dspLog.getExchangeId());
        avroRecord.put("log_time", dspLog.getLogTime());
        avroRecord.put("save_time", dspLog.getSaveTime());
        avroRecord.put("log_status", dspLog.getEffective());
        avroRecord.put("adv_id", dspLog.getAdvId());
        avroRecord.put("app_id", dspLog.getAppId());
        avroRecord.put("city_id", dspLog.getCityId());
        avroRecord.put("ip_range_id", dspLog.getIpRangeId());
        avroRecord.put("ip", dspLog.getIp());
        avroRecord.put("customer_cost", dspLog.getCustomerCost());
        avroRecord.put("adwo_cost", dspLog.getAdwoCost());
        avroRecord.put("user_id", dspLog.getUserId());
        avroRecord.put("device_id", dspLog.getDeviceId());
        avroRecord.put("device_type", dspLog.getLogDate());
        avroRecord.put("platform_id", dspLog.getPlatformId());
        avroRecord.put("cost_type", dspLog.getCostType());
        return avroRecord;
    }

}
