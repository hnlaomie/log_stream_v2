package io.github.hnlaomie.data;

/**
 * @author <a href="mailto:lichunhui@adwo.com">李春辉</a>
 * @date 2018-07-20
 * 竞标数据对象
 */
public class BidData {
    // 竞标ID
    private String bidId;
    // 广告ID
    private String advId;
    // 底价
    private String bidFloor;

    public String getBidId() {
        return bidId;
    }

    public void setBidId(String bidId) {
        this.bidId = bidId;
    }

    public String getAdvId() {
        return advId;
    }

    public void setAdvId(String advId) {
        this.advId = advId;
    }

    public String getBidFloor() {
        return bidFloor;
    }

    public void setBidFloor(String bidFloor) {
        this.bidFloor = bidFloor;
    }
}
