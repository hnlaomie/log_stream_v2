package io.github.hnlaomie.parser.json;

import io.github.hnlaomie.common.constant.Constants;
import io.github.hnlaomie.data.BidKeys;

/**
 * @author <a href="mailto:lichunhui@adwo.com">李春辉</a>
 * @date 2018-07-22
 * 百度视频(31)竞标数据处理
 *
 * 输入格式如下:
 *     时间|ip|json数据
 *
 *     输入用例:
 *     2018-06-16 01:53:35|223.74.49.53|{"id":"83a0ad2d3692c16be6ad645adf4935e9","seatbid":[{"bid":[{"id":"83a0ad2d3692c16be6ad645adf4935e9",
 *     "impid":"1008","price":700,"bidtype":0,"adm":{"","w":640,"h":360,"imptrackers":[],"landingpage":"","clicktrackers":[]},
 *     "crid":"1015043","extdata":""}],"seat":"xiaodu"}],"bidid":"83a0ad2d3692c16be6ad645adf4935e9"}
 */
public final class BidDsp31Parser extends JsonDataParser {
    /**
     * 获取竞标json需要处理数据的键值对象
     * @return
     */
    @Override
    public BidKeys getBidKeys() {
        BidKeys bidKeys = new BidKeys();

        bidKeys.setBidIdKey("id");
        bidKeys.setSeatListKey("seatbid");
        bidKeys.setBidListKey("bid");
        bidKeys.setImpIdKey("impid");
        bidKeys.setAdvIdKey("crid");
        // 百度视频的底价单位为分
        bidKeys.setBidFloorKey("price");

        return bidKeys;
    }

    /**
     * 获取exchange id
     * @return
     */
    @Override
    public String getExchangeId() {
        return Constants.EXCHANGE_BDSP;
    }
}



