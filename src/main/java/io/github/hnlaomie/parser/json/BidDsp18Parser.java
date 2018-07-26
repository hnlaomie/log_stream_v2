package io.github.hnlaomie.parser.json;

import io.github.hnlaomie.common.constant.Constants;
import io.github.hnlaomie.data.BidKeys;

/**
 * @author <a href="mailto:lichunhui@adwo.com">李春辉</a>
 * @date 2018-07-22
 * 网易有道(18)竞标数据处理
 *
 * 输入格式如下:
 *     时间|ip|json数据
 *
 *     输入用例:
 *     2018-06-16 00:12:26|42.7.1.75|{"id": "e0fddd54-8b3b-45e3-b6f5-e63665333106","seatbid": [{"bid": [{"id": "e0fddd54-8b3b-45e3-b6f5-e63665333106",
 *     "impid": "1","price": 300.0,"adid": "1014957","nurl": "","adomain": ["e.cn.miaozhen.com"],"iurl": "","crid": "1014957",
 *     "adm_native": {"assets": [{"id": 1,"img": {"url": "","w": 640,"h": 100}}],"link": {"url": "","clicktrackers": [""]},
 *     "imptrackers": [""]},"com.google.openrtb.youdao.attri": [101]}],"seat": "adwo"}],
 *     "bidid": "e0fddd54-8b3b-45e3-b6f5-e63665333106"}
 */
public final class BidDsp18Parser extends JsonDataParser {
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
        // 有道的底价单位为分
        bidKeys.setBidFloorKey("price");

        return bidKeys;
    }

    /**
     * 获取exchange id
     * @return
     */
    @Override
    public String getExchangeId() {
        return Constants.EXCHANGE_YOUDAO;
    }
}


