package io.github.hnlaomie.data;

import java.io.Serializable;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @author <a href="mailto:hnlaomie@163.com">李春辉</a>
 * @date 2018-09-13
 */
public class ParserConfig implements Serializable {
    private String topic;
    private String buildClass;

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getBuildClass() {
        return buildClass;
    }

    public void setBuildClass(String buildClass) {
        this.buildClass = buildClass;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
