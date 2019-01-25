package io.github.hnlaomie.parser;

import io.github.hnlaomie.data.DataLog;
import java.util.List;

/**
 * @author <a href="mailto:hnlaomie@163.com">李春辉</a>
 * @date 2018-07-19
 */
public interface IDataParser {
    public List<DataLog> build(String topic, String content);
}
