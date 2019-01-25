package io.github.hnlaomie.common.util.exception;

/**
 * @author <a href="mailto:lichunhui@adwo.com">李春辉</a>
 * @date 2018-07-26
 */
public final class SystemException extends LogException {
    public SystemException(String errCode) {
        super(errCode);
    }

    public SystemException(String errCode, String[] msgParams) {
        super(errCode, msgParams);

    }

    public SystemException(String errCode, Throwable cause) {
        super(errCode, cause);
    }

    public SystemException(String errCode, String[] msgParams, Throwable cause) {
        super(errCode, msgParams, cause);
    }
}
