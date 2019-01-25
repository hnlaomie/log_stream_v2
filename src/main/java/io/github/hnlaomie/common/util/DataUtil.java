package io.github.hnlaomie.common.util;

import io.github.hnlaomie.common.constant.Constants;
import io.github.hnlaomie.data.DataLog;

import java.util.Date;
import java.util.List;

/**
 * @author <a href="mailto:lichunhui@adwo.com">李春辉</a>
 * @date 2018-07-20
 */
public class DataUtil {
    /**
     * 构建数据对象
     * @param dataList 数据列表
     * [
     *     日期, 时, 日志时间, 设备ID
     *     地域ID, ip, 事件ID
     * ]
     * @return null或数据对象
     */
    public static DataLog initDataLog(List<String> dataList) {
        DataLog log = null;
        if (dataList.size() > 0) {
            log = new DataLog();
            // 默认用展示计费
            String costType = Constants.SHOW_LOG;
            String logTime = dataList.get(0);
            String logDate = logTime.substring(0, 10);
            String logHour = logTime.substring(11, 13);

            log.setLogDate(logDate);
            log.setLogHour(logHour);
            log.setLogTime(logTime);
            log.setDeviceId(dataList.get(1));
            log.setAreaId(dataList.get(2));
            log.setIp(dataList.get(3));
            log.setEventId(dataList.get(4));
        }

        return log;
    }

}
