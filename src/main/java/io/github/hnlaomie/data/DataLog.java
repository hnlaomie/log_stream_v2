package io.github.hnlaomie.data;

import java.io.Serializable;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @author <a href="mailto:hnlaomie@163.com">李春辉</a>
 * @date 2018-09-13
 *
 * 最终输出日志对象
 */
public final class DataLog implements Serializable {
    private static final long serialVersionUID = 7526471155622776147L;

    // 日期(yyyy-MM-dd)
    private String logDate;
    // 时(hh)
    private String logHour;
    // 日志时间(yyyy-MM-dd hh:mm:ss)
    private String logTime;
    // 设备ID
    private String deviceId;
    // 地域ID
    private String areaId;
    // ip
    private String ip;
    // 事件ID
    private String eventId;

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

    public String getLogTime() {
        return logTime;
    }

    public void setLogTime(String logTime) {
        this.logTime = logTime;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}

