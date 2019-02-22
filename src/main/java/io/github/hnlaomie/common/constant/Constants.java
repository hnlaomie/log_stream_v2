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

    public static final String MSG_CONFIG_FILE = "config.log_messages";

    // 日期显示格式
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    // 日志类型
    public static final String REQUEST_LOG = "1";
    public static final String BID_LOG = "2";
    public static final String WIN_LOG = "3";
    public static final String SHOW_LOG = "4";
    public static final String CLICK_LOG = "5";

    // kafka主题
    public static final String TOPIC_TESTDATA = "testdata";

    // 数据分隔符号
    public static final String TIME_SPLIT = " - ";
    public static final String COL_SPLIT = ",";
    public static final String BID_DATA_SPLIT = "\\|";

    // ip转城市映射文件
    public static final String IP_CITY_FILE = "/config/ip/ip_area.csv";
    // avro schema
    public static final String AVRO_SCHEMA_FILE = "/config/avro/datalogs_schema.avsc";
    // data parser configuration file
    public static final String DATA_PARSER_CONFIG_FILE = "/config/parser/data.json";

    // kafka streams相关
    public static final String APPLICATION_ID_CONFIG = "data-streams-v2";
    public static final String BOOTSTRAP_SERVERS_CONFIG = "localhost:9092";
    //public static final String BOOTSTRAP_SERVERS_CONFIG = "192.168.11.61:9092,192.168.11.62:9092,192.168.11.87:9092";
    public static final String SCHEMA_REGISTRY_URL_CONFIG = "http://localhost:8081";
    //public static final String SCHEMA_REGISTRY_URL_CONFIG = "http://192.168.11.61:8091,http://192.168.11.62:8091,http://192.168.11.87:8091";
    public static final String AUTO_OFFSET_RESET_CONFIG = "earliest";

    public static final String[] SOURCE_TOPICS = {
            "testdata"
    };

    public static final String SINK_TOPIC = "datalogs";

    // 行最少字符
    public static final int LINE_MIN_LENGTH = 20;
    // 默认城市ID
    public static final String DEFAULT_CITY_ID = "-999999";
    // app logger
    public static final String APP_LOGGER = "log_stream";

}