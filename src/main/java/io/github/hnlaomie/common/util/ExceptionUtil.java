package io.github.hnlaomie.common.util;

import io.github.hnlaomie.common.util.exception.LogException;

/**
 * 异常处理类
 * @author <a href="mailto:laomie@163.com">laomie</a>
 * @date 2018/02/07
 */
public class ExceptionUtil {
    /**
     * 输出业务异常
     * @param errCode
     * @return
     */
    public static LogException handle(String errCode) {
        LogException exp = new LogException(errCode);
        return exp;
    }

    /**
     * 输出业务异常
     * @param errCode
     * @param msgParams 参数值
     * @return
     */
    public static LogException handle(String errCode, String[] msgParams) {
        LogException exp = new LogException(errCode, msgParams);
        return exp;
    }


    /**
     * 输出异常，并根据错误码生成新的BI异常
     * @param errCode 错误代码
     * @param e 异常
     * @return BI异常
     */
    public static LogException handle(String errCode, Exception e) {
        LogException exp = new LogException(errCode, e);
        return exp;
    }

    /**
     * 输出异常，并根据错误代码和参数值生成新的BI异常
     * @param errCode 错误代码
     * @param msgParams 参数值
     * @param e　异常
     * @return
     */
    public static LogException handle(String errCode, String[] msgParams, Exception e) {
        LogException exp = new LogException(errCode, msgParams, e);
        return exp;
    }

}
