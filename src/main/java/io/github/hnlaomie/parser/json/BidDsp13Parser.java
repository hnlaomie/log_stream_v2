package io.github.hnlaomie.parser.json;

import io.github.hnlaomie.common.constant.Constants;
import io.github.hnlaomie.data.BidKeys;

/**
 * @author <a href="mailto:lichunhui@adwo.com">李春辉</a>
 * @date 2018-07-22
 * 掌游天下zplay(13)竞标数据处理
 *
 * 输入格式如下:
 *     时间|ip|json数据
 *
 *     输入用例:
 *     2018-06-18 23:10:01|119.79.228.94|{"id": "0btcbj1FuVN826G4fc1dXbmO29oK1P","seatbid": [{"bid": [{
 *     "id": "0btcbj1FuVN826G4fc1dXbmO29oK1P","impid": "1","price": 1200.0,"adid": "1014818","nurl": "",
 *     "adomain": ["e.cn.miaozhen.com"],"iurl": "","crid": "1014818","zadx.imptrackers": [],"zadx.clktrackers": [],
 *     "zadx.clkurl": "","zadx.action": 2}],"seat": "0b5e28b49b654f4a9e658405fa48a74e"}],
 *     "bidid": "0btcbj1FuVN826G4fc1dXbmO29oK1P"}
 */
public final class BidDsp13Parser extends JsonDataParser {
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
        bidKeys.setAdvIdKey("adid");
        // zplay的底价单位为分
        bidKeys.setBidFloorKey("price");

        return bidKeys;
    }

    /**
     * 获取exchange id
     * @return
     */
    @Override
    public String getExchangeId() {
        return Constants.EXCHANGE_ZPLAY;
    }
}

