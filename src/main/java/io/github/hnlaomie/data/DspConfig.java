package io.github.hnlaomie.data;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:lichunhui@adwo.com">李春辉</a>
 * @date 2018-07-23
 */
public class DspConfig implements Serializable {
    private List<ParserConfig> parserList;

    public DspConfig() {
        this.parserList = new ArrayList<>();
    }

    public List<ParserConfig> getParserList() {
        return parserList;
    }

    public void setParserList(List<ParserConfig> parserList) {
        this.parserList = parserList;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
