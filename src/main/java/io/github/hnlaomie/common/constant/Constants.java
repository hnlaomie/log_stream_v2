package io.github.hnlaomie.common.constant;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;

/**
 * 常量定义
 *
 * @author <a href="mailto:laomie@163.com">laomie</a>
 * @date 2018.02.07
 */
public interface Constants {
    // 文件编码
    public static final Charset CHARSET = StandardCharsets.UTF_8;
    public static final String ENCODING = "UTF-8";

    // 版本号
    public static final long SERIAL_VERSION_ID = 1;

    // 资源文件路径
    public static final String RESOURCE_KEY = "configPath";

    public static final String MSG_CONFIG_FILE = "config.log_messages";

    // 日志里异常标题
    public static final String EXCEPTION_HEADER = "BI异常：";

    // 日期显示格式
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    // csv的json名称
    public static final String CSV_TOPIC = "topic";
    public static final String CSV_INPUT_SIZE = "input_size";
    public static final String CSV_SEPARATOR = "separator";
    public static final String CSV_INPUT = "input";
    public static final String CSV_OUTPUT = "output";
    public static final String CSV_COLUMNS = "columns";
    public static final String CSV_INDEX = "index";
    public static final String CSV_NAME = "name";
    public static final String CSV_TYPE = "type";
    public static final String CSV_EXPRESSION = "expression";

    // csv column value type
    public static final String TYPE_STRING = "String";

    // 日志类型
    public static final String REQUEST_LOG = "1";
    public static final String BID_LOG = "2";
    public static final String WIN_LOG = "3";
    public static final String SHOW_LOG = "4";
    public static final String CLICK_LOG = "5";

    // kafka主题
    public static final String TOPIC_DSPN = "dspn";
    public static final String TOPIC_DSPS = "dsps";
    public static final String TOPIC_DSPC = "dspc";
    public static final String TOPIC_DSP03 = "dsp03";
    public static final String TOPIC_DSP07 = "dsp07";
    public static final String TOPIC_DSP13 = "dsp13";
    public static final String TOPIC_DSP18 = "dsp18";
    public static final String TOPIC_DSP19 = "dsp19";
    public static final String TOPIC_DSP31 = "dsp31";
    public static final String TOPIC_DSP32 = "dsp32";
    public static final String TOPIC_DSP39 = "dsp39";

    // exchange id
    public static final String EXCHANGE_BAIDU = "3";
    public static final String EXCHANGE_XUNFEI = "7";
    public static final String EXCHANGE_ZPLAY = "13";
    public static final String EXCHANGE_YOUDAO = "18";
    public static final String EXCHANGE_BESTV = "19";
    public static final String EXCHANGE_BDSP = "31";
    public static final String EXCHANGE_SHJT = "32";
    public static final String EXCHANGE_DINGYUE = "39";


    // 数据分隔符号
    public static final String TIME_SPLIT = " - ";
    public static final String COL_SPLIT = ",";
    public static final String BID_DATA_SPLIT = "\\|";

    // ip转城市映射文件
    public static final String IP_CITY_FILE = "/config/ip/city_ip_new.csv";
    // avro schema
    public static final String AVRO_SCHEMA_FILE = "/config/avro/dsplogs_schema.avsc";
    // dsp parser configuration file
    public static final String DSP_PARSER_CONFIG_FILE = "/config/parser/dsp.json";

    // kafka streams相关
    public static final String APPLICATION_ID_CONFIG = "dsp-streams-v1";
    public static final String BOOTSTRAP_SERVERS_CONFIG = "192.168.1.20:9092";
    public static final String SCHEMA_REGISTRY_URL_CONFIG = "http://192.168.1.20:8081";
    public static final String AUTO_OFFSET_RESET_CONFIG = "earliest";
    public static final String[] SOURCE_TOPICS = {
            "dspc", "dsps", "dspn", "dsp03", "dsp07",
            "dsp13", "dsp18", "dsp19", "dsp31", "dsp32",
            "dsp39"
    };
    public static final String SINK_TOPIC = "dsplogs";

    // 行最少字符
    public static final int LINE_MIN_LENGTH = 30;
    // 默认城市ID
    public static final String DEFAULT_CITY_ID = "-999999";
    // app logger
    public static final String APP_LOGGER = "log_stream";

}