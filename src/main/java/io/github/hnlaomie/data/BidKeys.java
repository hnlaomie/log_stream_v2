package io.github.hnlaomie.data;

/**
 * @author <a href="mailto:lichunhui@adwo.com">李春辉</a>
 * @date 2018-07-22
 * 竞标json数据的键值名
 */
public class BidKeys {
    // 竞标ID键值
    private String bidIdKey;
    // 数据列表键值
    private String seatListKey;
    // 竞标列表键值
    private String bidListKey;
    // 低价键值, 底价（千次），单位一般为分或元
    private String bidFloorKey;
    // 曝光ID键值
    private String impIdKey;
    // 广告ID键值
    private String advIdKey;
    // 低价倍乘, 单位默认为分，不需要倍乘（有的底价是元，有的分，最后需要统一为分，是元的乘100）
    private double priceMulti = 1;

    public String getBidIdKey() {
        return bidIdKey;
    }

    public void setBidIdKey(String bidIdKey) {
        this.bidIdKey = bidIdKey;
    }

    public String getSeatListKey() {
        return seatListKey;
    }

    public void setSeatListKey(String seatListKey) {
        this.seatListKey = seatListKey;
    }

    public String getBidListKey() {
        return bidListKey;
    }

    public void setBidListKey(String bidListKey) {
        this.bidListKey = bidListKey;
    }

    public String getBidFloorKey() {
        return bidFloorKey;
    }

    public void setBidFloorKey(String bidFloorKey) {
        this.bidFloorKey = bidFloorKey;
    }

    public String getImpIdKey() {
        return impIdKey;
    }

    public void setImpIdKey(String impIdKey) {
        this.impIdKey = impIdKey;
    }

    public String getAdvIdKey() {
        return advIdKey;
    }

    public void setAdvIdKey(String advIdKey) {
        this.advIdKey = advIdKey;
    }

    public double getPriceMulti() {
        return priceMulti;
    }

    public void setPriceMulti(double priceMulti) {
        this.priceMulti = priceMulti;
    }
}
