package io.github.hnlaomie.common.util.exception;

import io.github.hnlaomie.common.constant.Constants;

/**
 * 系统异常基类，采用RuntimeException
 * @author <a href="mailto:laomie@163.com">laomie</a>
 * @date 2018/02/07
 */
public class LogRuntimeException extends RuntimeException {
    // 版本号
    protected final static long serialVersionID = Constants.SERIAL_VERSION_ID;
    // 错误号码
    protected String errCode;
    // 消息参数
    protected String[] msgParams;

    public LogRuntimeException() {
        super();
    }

    public LogRuntimeException(String msg) {
        super(msg);
    }

    public LogRuntimeException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public LogRuntimeException(Throwable cause) {
        super(cause);
    }

    public String getErrCode() {
        return errCode;
    }

    public String[] getMsgParams() {
        return msgParams;
    }
}
