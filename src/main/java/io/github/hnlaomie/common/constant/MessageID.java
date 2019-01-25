package io.github.hnlaomie.common.constant;

/**
 * @author <a href="mailto:laomie@163.com">laomie</a>
 * @date 2018/02/07
 */
public interface MessageID {
    // 未知异常。
    public static final String MSG_010001 = "0x010001";
    // 数据编码有问题。
    public static final String MSG_010002= "0x010002";
    // 规则定义格式不正确。
    public static final String MSG_010003= "0x010003";
    // 输入格式不正确。需要{0}列，只有{1}列。"{2}"
    public static final String MSG_010004 = "0x010004";
    // 第{0}列为非ip格式。"{1}"
    public static final String MSG_010005 = "0x010005";
    // 第{0}列为非数字格式。"{1}"
    public static final String MSG_010006 = "0x010006";
    // 载入ip城市转换文件出错。
    public static final String MSG_010007 = "0x010007";
    // json数据格式不正确。"{0}"
    public static final String MSG_010008 = "0x010008";
    // 载入dsp数据解析器出错。
    public static final String MSG_010009 = "0x010009";
    // 数据异常:{0}。
    public static final String MSG_010010 = "0x010010";
}
