package io.github.hnlaomie.parser.json;

import io.github.hnlaomie.common.constant.Constants;
import io.github.hnlaomie.data.BidKeys;

/**
 * @author <a href="mailto:lichunhui@adwo.com">李春辉</a>
 * @date 2018-07-22
 * 科大讯飞(7)竞标数据处理
 *
 * 输入格式如下:
 *     时间|ip|adv_id|json数据
 *
 *     输入用例:
 *     2018-06-17 09:08:42|125.120.200.116|1014937|{"id":"64e9ae0f-ea0b-4407-9c41-934f80507884-1529197722275","seatbid":[{"bid":[{"impid":
 *     "b7fc4ef4-c655-49c1-af07-2abce12a8224","price":4.0,"nurl":"https://dspifly.adwo.com","native_ad":{"title":"夏普618，千元4k","desc":
 *     "","img":"https://static./480x320.jpg","landing":"http://e.cn.miaozhen.com","imptrackers":[],"clicktrackers":[],"is_marked":0},
 *     "lattr":1}]}],"bidid":"64e9ae0f-ea0b-4407-9c41-934f80507884-1529197722275"}
 */
public final class BidDsp07Parser extends JsonDataParser {
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
        bidKeys.setBidFloorKey("price");
        // 讯飞单位是元，需要乘以100得到分
        bidKeys.setPriceMulti(100);

        return bidKeys;
    }

    /**
     * 获取exchange id
     * @return
     */
    @Override
    public String getExchangeId() {
        return Constants.EXCHANGE_XUNFEI;
    }

    /**
     * 数据格式： 时间|ip|adv_id|json数据
     * @return 列数
     */
    @Override
    public int getColumnSize() {
        return 4;
    }
}
