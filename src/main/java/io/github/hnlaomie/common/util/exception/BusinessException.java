package io.github.hnlaomie.common.util.exception;

/**
 * @author <a href="mailto:lichunhui@adwo.com">李春辉</a>
 * @date 2018-07-26
 */
public final class BusinessException extends LogException {
    public BusinessException(String errCode) {
        super(errCode);
    }

    public BusinessException(String errCode, String[] msgParams) {
        super(errCode, msgParams);
    }

    public BusinessException(String errCode, Throwable cause) {
        super(errCode, cause);
    }

    public BusinessException(String errCode, String[] msgParams, Throwable cause) {
        super(errCode, msgParams, cause);
    }

}
