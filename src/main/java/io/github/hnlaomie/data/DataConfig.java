package io.github.hnlaomie.data;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:hnlaomie@163.com">李春辉</a>
 * @date 2018-09-13
 */
public class DataConfig implements Serializable {
    private List<ParserConfig> parserList;

    public DataConfig() {
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
