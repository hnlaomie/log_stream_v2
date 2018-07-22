package io.github.hnlaomie.common.util.message;

/**
 * @author <a href="mailto:laomie@163.com">laomie</a>
 * @date 2018/02/07
 */
public class Message {
    // 键
    private String key;
    // 值
    private String value;
    // 参数
    private Object[] params;
    // 异常
    private Throwable exception;

    public Message() {
    }

    public Message(String key) {
        this.key = key;
    }

    public Message(String key, Object... params) {
        this.key = key;
        this.params = params;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Object[] getParams() {
        return params;
    }

    public void setParams(Object[] params) {
        this.params = params;
    }

    public Throwable getException() {
        return exception;
    }

    public void setException(Throwable exception) {
        this.exception = exception;
    }
}
