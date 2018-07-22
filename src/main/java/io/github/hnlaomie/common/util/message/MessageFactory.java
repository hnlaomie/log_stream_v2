package io.github.hnlaomie.common.util.message;

/**
 * @author <a href="mailto:laomie@163.com">laomie</a>
 * @date 2018/02/07
 */
public interface MessageFactory {
    /**
     * 根据键值生成消息
     * @param key
     * @return
     */
    public Message createMessage(String key);

    /**
     * 根据键值生成带参数的消息
     * @param key
     * @param messageParameters
     * @return
     */
    public Message createMessage(String key, Object... messageParameters);
}
