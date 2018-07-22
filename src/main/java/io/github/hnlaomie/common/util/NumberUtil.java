package io.github.hnlaomie.common.util;

/**
 * @author <a href="mailto:lichunhui@adwo.com">李春辉</a>
 * @date 2018-07-19
 */
public class NumberUtil {

    /**
     * 判读给出的字符窜是否是整数,小数,科学计数法数字
     * @param value
     * @return
     */
    public static boolean isNumber(String value) {
        boolean result = false;
        try {
            Double.parseDouble(value);
            result = true;
        } catch(NumberFormatException e) {
            result = false;
        }
        return result;
    }
}
