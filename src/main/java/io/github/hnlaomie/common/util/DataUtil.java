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
            String ipRangeId = "0";
            String logTime = dataList.get(2);
            String logDate = logTime.substring(0, 10);
            String logHour = logTime.substring(11, 13);
            /*
                    日志时间(%Y-%m-%d %H:%M:%S), 保存时间(%Y-%m-%d %H:%M:%S), 是否有效, 广告ID, app id,
                    城市ID, ip范围ID, ip, 广告主成本(payMoney), 安沃成本(中标价格)
            用户ID, 设备ID, 设备机型, 操作系统（2安卓, 3ios）, 成本类型（3中标, 4展示, 5点击）
            log_time, data_list[1], data_list[18], data_list[5], data_list[17], \
            data_list[12], ip_range_id, data_list[13], customer_cost, adwo_cost, \
            data_list[8], data_list[24], data_list[16], data_list[21], cost_type \
            */
            log.setLogDate(logDate);
            log.setLogHour(logHour);
            log.setBidId(dataList.get(4));
            log.setLogType(dataList.get(0));
            log.setExchangeId(dataList.get(3));
            log.setLogTime(logTime);
            log.setSaveTime(dataList.get(1));
            log.setEffective(dataList.get(18));
            log.setAdvId(dataList.get(5));
            log.setAppId(dataList.get(17));
            log.setCityId(dataList.get(12));
            log.setIpRangeId(ipRangeId);
            log.setIp(dataList.get(13));
            log.setCustomerCost(dataList.get(10));
            log.setAdwoCost(dataList.get(11));
            log.setUserId(dataList.get(8));
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
        String ipRangeId = "0";
        // 中标是否有效(1有效)
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
        log.setEffective(effective);
        log.setAdvId(bid.getAdvId());
        log.setAppId("");
        log.setCityId(cityId);
        log.setIpRangeId(ipRangeId);
        log.setIp(ip);
        log.setCustomerCost(customerCost);
        log.setAdwoCost(adwoCost);
        log.setUserId("");
        log.setDeviceId("");
        log.setDeviceType("");
        log.setPlatformId("");
        log.setCostType(costType);

        return log;
    }
}
