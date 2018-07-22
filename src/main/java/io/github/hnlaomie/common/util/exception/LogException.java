package io.github.hnlaomie.common.util.exception;

import io.github.hnlaomie.common.util.message.Message;
import io.github.hnlaomie.common.util.message.MessageFactory;
import io.github.hnlaomie.common.util.message.MessageFactoryImpl;
import org.apache.commons.lang3.StringUtils;

/**
 * 异常处理
 * @author <a href="mailto:laomie@163.com">laomie</a>
 * @date 2018/02/07
 */
public class LogException extends LogRuntimeException {
    // 消息
    private final Message message;

    /**
     * 生成错误消息
     * @param errCode
     * @return
     */
    private Message initMessage(String errCode) {
        MessageFactory messageFactory = new MessageFactoryImpl();
        Message message = messageFactory.createMessage(errCode);
        return message;
    }

    /**
     * 生成带参数的错误消息
     * @param errCode
     * @return
     */
    private Message initMessage(String errCode, String[] msgParams) {
        MessageFactory messageFactory = new MessageFactoryImpl();
        Message message = messageFactory.createMessage(errCode, msgParams);
        return message;
    }

    public LogException(String errCode) {
        super();
        this.errCode = errCode;
        this.message = initMessage(errCode);
    }

    public LogException(String errCode, String[] msgParams) {
        super();
        this.errCode = errCode;
        this.msgParams = msgParams;
        this.message = initMessage(errCode, msgParams);

    }

    public LogException(String errCode, Throwable cause) {
        super(cause);
        this.errCode = errCode;
        this.message = initMessage(errCode);
        this.message.setException(cause);
    }

    public LogException(String errCode, String[] msgParams, Throwable cause) {
        super(cause);
        this.errCode = errCode;
        this.msgParams = msgParams;
        this.message = initMessage(errCode, msgParams);
        this.message.setException(cause);
    }

    public String getErrMessage() {
        String msg = null;
        if (message != null) {
            msg = message.getValue();
        }
        return msg;
    }

    @Override
    public String toString() {
        String lineSeparator = System.lineSeparator();
        StringBuilder sb = new StringBuilder();

        sb.append("err_code: " + message.getKey());
        sb.append(" err_message: ");
        sb.append(message.getValue());
        if (message.getException() != null) {
            sb.append(message.getException().getMessage() + lineSeparator);
            String causeMsg = StringUtils.join(message.getException().getStackTrace(), lineSeparator);
            sb.append(causeMsg);
        }

        return sb.toString();
    }
}