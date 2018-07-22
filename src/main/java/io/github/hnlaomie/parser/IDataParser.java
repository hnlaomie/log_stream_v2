package io.github.hnlaomie.parser;

import io.github.hnlaomie.common.util.exception.LogException;
import io.github.hnlaomie.data.DspLog;

import java.util.List;

/**
 * @author <a href="mailto:lichunhui@adwo.com">李春辉</a>
 * @date 2018-07-19
 */
public interface IDataParser {
    public List<DspLog> build(String topic, String content);
}
