package io.github.hnlaomie.common.util.sysparam;

/**
 * @author <a href="mailto:laomie@163.com">laomie</a>
 * @date 2018/02/07
 */
public interface SystemParameters {
    /**
     * 获取字符串参数
     * @param key
     * @return
     */
    public String getStringParameter(String key);

    /**
     * 获取整数参数
     * @param key
     * @return
     */
    public int getIntParameter(String key);
}
