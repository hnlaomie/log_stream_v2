package io.github.hnlaomie.parser.json;

import io.github.hnlaomie.common.constant.Constants;
import io.github.hnlaomie.data.BidKeys;

/**
 * @author <a href="mailto:lichunhui@adwo.com">李春辉</a>
 * @date 2018-07-22
 * 上海佳投(32)竞标数据处理
 *
 * 输入格式如下:
 *     时间|ip|json数据
 *
 *     输入用例:
 *     2018-06-04 00:00:54|115.153.148.124|{"id":"OJSOYmLjq0","seatbid":[{"seat":"1014884","bid":[{"impid":"1","price":5.36,
 *     "nurl":"","crid":"1014884","ext":{"curl":[],"adurl":[""],"durl":[],"pm":[],"title":"夏普618，千元4k","landingpage_url":"",
 *     "body":"夏普4K爆款，火热抢购中......","app_icon":""}}]}]}-
 */
public final class BidDsp32Parser extends JsonDataParser {
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
        // 佳投单位是元，需要乘以100得到分
        bidKeys.setBidFloorKey("price");
        bidKeys.setPriceMulti(100);

        return bidKeys;
    }

    /**
     * 获取exchange id
     * @return
     */
    @Override
    public String getExchangeId() {
        return Constants.EXCHANGE_SHJT;
    }
}



