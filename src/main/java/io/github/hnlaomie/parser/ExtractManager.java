package io.github.hnlaomie.parser;

import io.github.hnlaomie.common.constant.Constants;
import io.github.hnlaomie.common.constant.MessageID;
import io.github.hnlaomie.common.util.ExceptionUtil;
import io.github.hnlaomie.common.util.exception.LogException;
import io.github.hnlaomie.data.DspConfig;
import io.github.hnlaomie.data.DspLog;
import io.github.hnlaomie.data.ParserConfig;
import io.github.hnlaomie.parser.csv.ClickParser;
import io.github.hnlaomie.parser.csv.CsvDataParser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:lichunhui@adwo.com">李春辉</a>
 * @date 2018-07-23
 * dsp数据清洗解析器管理, 采用单例, 每类主题有一个解析器, 同主题数据用该解析器处理数据
 */
public final class ExtractManager {
    private static ExtractManager manager = null;
    // 各主题解析器
    private Map<String, IDataParser> parserMap = new HashMap<>();

    public synchronized static ExtractManager getInstance() throws Exception {
        if (manager == null) {
            manager = new ExtractManager();
            manager.loadParser();
        }
        return manager;
    }

    /**
     * 从配置文件生成相关主题的解析器
     */
    private void loadParser() {
        DspConfig dspConfig = DspParserLoader.load();
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

}
