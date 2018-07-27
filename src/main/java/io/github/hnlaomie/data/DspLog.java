package io.github.hnlaomie.data;

import java.io.Serializable;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @author <a href="mailto:lichunhui@adwo.com">李春辉</a>
 * @date 2018-07-19
 *
 * 最终输出日志对象
 */
public final class DspLog implements Serializable {
    private static final long serialVersionUID = 7526471155622776147L;

    // 日期(yyyy-MM-dd)
    private String logDate;
    // 时(hh)
    private String logHour;
    // bid(请求ID_曝光ID)
    private String bidId;
    // 日志类型(2竞标, 3中标, 4展示, 5点击）
    private String logType;
    // exchange id
    private String exchangeId;
    // 日志时间(yyyy-MM-dd hh:mm:ss)
    private String logTime;
    // 保存时间(yyyy-MM-dd hh:mm:ss)
    private String saveTime;
    // 日志是否有效
    private String logStatus;
    // 广告ID
    private String advId;
    // app id
    private String appId;
    // 城市ID
    private String cityId;
    // ip
    private String ip;
    // 广告主成本
    private String customerCost;
    // 安沃成本
    private String adwoCost;
    // 设备ID
    private String deviceId;
    // 设备机型
    private String deviceType;
    // 操作系统（2安卓, 3ios）
    private String platformId;
    // 成本类型（3中标, 4展示, 5点击）
    private String costType;

    public String getLogDate() {
        return logDate;
    }

    public void setLogDate(String logDate) {
        this.logDate = logDate;
    }

    public String getLogHour() {
        return logHour;
    }

    public void setLogHour(String logHour) {
        this.logHour = logHour;
    }

    public String getBidId() {
        return bidId;
    }

    public void setBidId(String bidId) {
        this.bidId = bidId;
    }

    public String getLogType() {
        return logType;
    }

    public void setLogType(String logType) {
        this.logType = logType;
    }

    public String getExchangeId() {
        return exchangeId;
    }

    public void setExchangeId(String exchangeId) {
        this.exchangeId = exchangeId;
    }

    public String getLogTime() {
        return logTime;
    }

    public void setLogTime(String logTime) {
        this.logTime = logTime;
    }

    public String getSaveTime() {
        return saveTime;
    }

    public void setSaveTime(String saveTime) {
        this.saveTime = saveTime;
    }

    public String getLogStatus() {
        return logStatus;
    }

    public void setLogStatus(String logStatus) {
        this.logStatus = logStatus;
    }

    public String getAdvId() {
        return advId;
    }

    public void setAdvId(String advId) {
        this.advId = advId;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getCustomerCost() {
        return customerCost;
    }

    public void setCustomerCost(String customerCost) {
        this.customerCost = customerCost;
    }

    public String getAdwoCost() {
        return adwoCost;
    }

    public void setAdwoCost(String adwoCost) {
        this.adwoCost = adwoCost;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getPlatformId() {
        return platformId;
    }

    public void setPlatformId(String platformId) {
        this.platformId = platformId;
    }

    public String getCostType() {
        return costType;
    }

    public void setCostType(String costType) {
        this.costType = costType;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
