package io.github.hnlaomie.parser.json;

import io.github.hnlaomie.common.constant.Constants;
import io.github.hnlaomie.data.BidKeys;

/**
 * @author <a href="mailto:lichunhui@adwo.com">李春辉</a>
 * @date 2018-07-22
 * 迈观网络(19)竞标数据处理
 *
 * 输入格式如下:
 *     时间|ip|json数据
 *
 *     输入用例:
 *     TODO:
 */
public final class BidDsp19Parser extends JsonDataParser {
    /**
     * 获取竞标json需要处理数据的键值对象
     * @return
     */
    public BidKeys getBidKeys() {
        BidKeys bidKeys = new BidKeys();

        bidKeys.setBidIdKey("id");
        bidKeys.setSeatListKey("seatbid");
        bidKeys.setBidListKey("bid");
        bidKeys.setImpIdKey("impid");
        bidKeys.setAdvIdKey("adid");
        // 迈观单位是放大千倍的分，需要除以1000得到分
        bidKeys.setBidFloorKey("price");
        bidKeys.setPriceMulti(0.001);

        return bidKeys;
    }

    /**
     * 获取exchange id
     * @return
     */
    public String getExchangeId() {
        return Constants.EXCHANGE_BESTV;
    }
}

