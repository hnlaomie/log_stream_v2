package io.github.hnlaomie.common.util;

import io.github.hnlaomie.common.constant.Constants;
import io.github.hnlaomie.data.BidData;
import io.github.hnlaomie.data.DspLog;

import java.util.Date;
import java.util.List;

/**
 * @author <a href="mailto:lichunhui@adwo.com">李春辉</a>
 * @date 2018-07-20
 */
public class DataUtil {
    /**
     * 构建中标,展示,点击数据对象
     * @param dataList 数据列表
     * [
     *     日志类型(2竞标, 3中标, 4展示, 5点击）, 保存时间, 日志时间, exchangeid, bid(请求ID_曝光ID),
     *     广告ID, 请求广告接口时间戳, 点击时间戳, uid, paymethod,
     *     payMoney, 中标价格, 城市ID（对应请求ip）， 请求接口的ip(需保存的ip), 展示广告的客户ip,
     *     展示ID, 设备机型, 程序ID, 是否重复, 广告账户ID,
     *     广告组ID, 广告平台（2安卓, 3ios）, 中标价格单位, 中标是否有效(1有效，2中标价格有问题，4中标价格大于广告最高价，6为超时表示无效),
     *     设备ID, 设备类型(IMEI或ifa)，...
     * ]
     * @return null或数据对象
     */
    public static DspLog initWinLog(List<String> dataList) {
        DspLog log = null;
        if (dataList.size() > 0) {
            log = new DspLog();
            // 默认用展示计费
            String costType = Constants.SHOW_LOG;
            String logTime = dataList.get(2);
            String logDate = logTime.substring(0, 10);
            String logHour = logTime.substring(11, 13);

            log.setLogDate(logDate);
            log.setLogHour(logHour);
            log.setBidId(dataList.get(4));
            log.setLogType(dataList.get(0));
            log.setExchangeId(dataList.get(3));
            log.setLogTime(logTime);
            log.setSaveTime(dataList.get(1));
            log.setLogStatus(dataList.get(23));
            log.setAdvId(dataList.get(5));
            log.setAppId(dataList.get(17));
            log.setCityId(dataList.get(12));
            log.setIp(dataList.get(13));
            log.setCustomerCost(dataList.get(10));
            log.setAdwoCost(dataList.get(11));
            log.setDeviceId(dataList.get(24));
            log.setDeviceType(dataList.get(16));
            log.setPlatformId(dataList.get(21));
            log.setCostType(costType);
        }
        return log;
    }

    /**
     * 构建竞标数据对象
     * @param bid 竞标对象
     * @param exchangeId
     * @param logTime 日志时间
     * @param ip
     * @param cityId
     * @return
     */
    public static DspLog initBidLog(BidData bid, String exchangeId, String logTime, String ip, String cityId) {
        DspLog log = new DspLog();
        // 默认用展示计费
        String costType = Constants.SHOW_LOG;
        // 竞标是否有效(1有效)
        String effective = "1";
        // 广告主成本
        String customerCost = "0";
        // 安沃成本，千次单位分转为单个单位元
        String adwoCost = Double.toString(Double.parseDouble(bid.getBidFloor()) / 100000);
        String logDate = logTime.substring(0, 10);
        String logHour = logTime.substring(11, 13);

        Date now = new Date();
        String saveTime = Constants.DATE_FORMAT.format(now);

        log.setLogDate(logDate);
        log.setLogHour(logHour);
        log.setBidId(bid.getBidId());
        log.setLogType(Constants.BID_LOG);
        log.setExchangeId(exchangeId);
        log.setLogTime(logTime);
        log.setSaveTime(saveTime);
        log.setLogStatus(effective);
        log.setAdvId(bid.getAdvId());
        log.setAppId("");
        log.setCityId(cityId);
        log.setIp(ip);
        log.setCustomerCost(customerCost);
        log.setAdwoCost(adwoCost);
        log.setDeviceId("");
        log.setDeviceType("");
        log.setPlatformId("");
        log.setCostType(costType);

        return log;
    }
}
