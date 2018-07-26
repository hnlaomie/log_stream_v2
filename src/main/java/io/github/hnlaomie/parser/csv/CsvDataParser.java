package io.github.hnlaomie.parser.csv;

import io.github.hnlaomie.common.constant.Constants;
import io.github.hnlaomie.common.constant.MessageID;
import io.github.hnlaomie.common.util.DataUtil;
import io.github.hnlaomie.common.util.NumberUtil;
import io.github.hnlaomie.common.util.exception.BusinessException;
import io.github.hnlaomie.data.DspLog;
import io.github.hnlaomie.parser.IDataParser;
import io.github.hnlaomie.parser.IpMap;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author <a href="mailto:lichunhui@adwo.com">李春辉</a>
 * @date 2018-07-19
 *
 * CSV数据处理基类,输入CSV格式数据,清洗转换后输出最终数据对象列表
 */
public abstract class CsvDataParser implements IDataParser {
    private IpMap ipMap = IpMap.getInstance();
    /**
     * 获取数据类型, 1请求，2竞标，3中标，4展示，5点击
     *
     * @return
     */
    protected abstract String getLogType();

    /**
     * csv行的列数，中标，展示，点击前24列是一致的，展示，点击多出的列不处理
     * @return 列数
     */
    protected int getColumnSize() {
        return 24;
    }

    /**
     * ip列索引,-1不处理,给出索引才处理，此处为ip所在列的索引
     * @return ip索引
     */
    protected int getIpIndex() {
        return 9;
    }

    /**
     * 给出数字列的索引,以便后续做数字验证
     * @return 数字列索引
     */
    protected int[] getNumIndexes() {
        int[] indexArray = {2, 7, 8};
        return indexArray;
    }

    /**
     * 验证数据是否有效,1. 列数大于给出的值, 2. 数字列为数字
     * @param dataArray
     * @param content
     * @return
     */
    protected boolean dataValidate(String[] dataArray, String content) {
        boolean isValid = false;
        int dataSize = dataArray.length;
        int minSize = this.getColumnSize();

        if (minSize <= dataSize) {
            isValid = true;
            int[] numIndexes = this.getNumIndexes();
            for (int i : numIndexes) {
                if (i < minSize) {
                    boolean numValid = NumberUtil.isNumber(dataArray[i]);
                    // 数字列验证失败
                    if (!numValid) {
                        String[] msgParams = {Integer.toString(i), content};
                        BusinessException exception = new BusinessException(MessageID.MSG_010006, msgParams);
                        throw exception;
                    }
                    isValid &= numValid;
                }
            }
        } else {
            // 列数验证失败
            String[] msgParams = {Integer.toString(minSize), Integer.toString(dataSize), content};
            BusinessException exception = new BusinessException(MessageID.MSG_010004, msgParams);
            throw exception;
        }

        return isValid;
    }

    /**
     * 清洗相关主题的日志,返回日志数据对象列表
     * @param topic kafka主题
     * @param content "日志时间 - 数据原始内容"
     * @return 日志对象列表
     */
    public List<DspLog> build(String topic, String content) {
        List<DspLog> dataList = new ArrayList<>();
        List<String> colList = new ArrayList<>();

        // 少于30字符的窜直接忽略,不做任何处理
        if (content != null && content.length() > Constants.LINE_MIN_LENGTH) {
            // 时间分隔符索引
            int timeSplitIndex = content.indexOf(Constants.TIME_SPLIT);
            if (timeSplitIndex > 0 && timeSplitIndex < Constants.LINE_MIN_LENGTH) {
                // 日志时间
                String logTime = content.substring(0, timeSplitIndex).replace(',', '.');
                // 保存时间(当前时间)
                Date now = new Date();
                String saveTime = Constants.DATE_FORMAT.format(now);
                String[] colArray = content.substring(timeSplitIndex + 3).split(Constants.COL_SPLIT);

                boolean isValid = dataValidate(colArray, content);
                if (isValid) {
                    for (int i = 0; i < getColumnSize(); i ++) {
                        colList.add(colArray[i]);
                    }

                    // ip转城市,如需转化则放在ip前
                    int ipIndex = getIpIndex();
                    if (ipIndex > 0) {
                        String ip = colArray[ipIndex];
                        String cityId = ipMap.getCityId(ip);
                        colList.add(ipIndex, cityId);
                    }

                    // 日志类型, 保存时间, 日志时间
                    colList.add(0, logTime);
                    colList.add(0, saveTime);
                    colList.add(0, getLogType());
                }

                DspLog log = DataUtil.initWinLog(colList);
                if (log != null) {
                    dataList.add(log);
                }

            }

        }

        return dataList;
    }
}
