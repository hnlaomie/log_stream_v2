package io.github.hnlaomie.common.util.message;

import io.github.hnlaomie.common.constant.Constants;
import io.github.hnlaomie.common.util.sysparam.SystemParameters;
import io.github.hnlaomie.common.util.sysparam.SystemParametersImpl;

/**
 * @author <a href="mailto:laomie@163.com">laomie</a>
 * @date 2018/02/07
 */
public class MessageFactoryImpl implements MessageFactory {
    // 系统参数
    private static SystemParameters sysParam;

    private void loadSysParam() {
        if (sysParam == null) {
            sysParam = new SystemParametersImpl(Constants.MSG_CONFIG_FILE);
        }
    }

    /**
     * 用参数值初始化消息
     * @param value　带参数消息定义
     * @param messageParameters　参数值
     * @return
     */
    private String initValue(String value, Object... messageParameters) {
        String result = value;
        if (messageParameters != null && messageParameters.length > 0) {
            int length = messageParameters.length;
            for (int i = 0; i < length; i++) {
                // 在带参数的消息中，参数用{i}的格式定义
                String key = "\\{" + Integer.toString(i) + "\\}";
                result = result.replaceAll(key, (String) messageParameters[i]);
            }
        }
        return result;
    }

    @Override
    public Message createMessage(String key) {
        Message result = new Message();
        loadSysParam();
        result.setKey(key);
        String value = sysParam.getStringParameter(key);
        result.setValue(value);
        return result;
    }

    @Override
    public Message createMessage(String key, Object... messageParameters) {
        Message result = new Message();
        loadSysParam();
        result.setKey(key);
        String value = sysParam.getStringParameter(key);
        value = initValue(value, messageParameters);
        result.setValue(value);
        result.setParams(messageParameters);
        return result;
    }
}
