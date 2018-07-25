package io.github.hnlaomie.parser.json;

import io.github.hnlaomie.common.constant.Constants;
import io.github.hnlaomie.data.BidKeys;

/**
 * @author <a href="mailto:lichunhui@adwo.com">李春辉</a>
 * @date 2018-07-25
 * 鼎阅传媒(39)竞标数据处理
 *
 * 输入格式如下:
 *     时间|ip|json数据
 *
 *     输入用例:
 *     2018-07-24 18:13:33|114.83.237.156|{"id":"cc83ecfe-8f7f-4e41-af4b-b3cbdb6a7e69","seatbid":[{"bid":[{"id":"cc83ecfe-8f7f-4e41-af4b-b3cbdb6a7e69",
 *     "impid":"1","price":20.0,"adm":"{\"native\":{\"assets\":[{\"id\":1,\"required\":1,\"title\":{\"text\":\"2048复活版\"}},
 *     {\"id\":2,\"required\":1,\"img\":{\"url\":\"http://static.adwo.com/300x300.jpg\",\"w\":300,\"h\":300}},
 *     {\"id\":3,\"required\":1,\"data\":{\"value\":\"玩不死的2048，快来挑战吧！\"}}],\"link\":{\"url\":\"http://android.myapp.com/myapp/detail.htm?
 *     apkName=com.huazhitech.game2048\",\"clicktrackers\":[\"http://dspifly.adwo.com/dspCharge/click?p0=7643AC&auction=${AUCTION_BID_ID}
 *     &price=${AUCTION_PRICE}&ua=\"]},\"imptrackers\":[\"http://dspifly.adwo.com/dspCharge/imp?p0=6E362210&auction=${AUCTION_BID_ID}
 *     &price=${AUCTION_PRICE}&ua=\"]}}","adid":"1015185","adomain":["android.myapp.com"],"cid":"3830","crid":"1015185",
 *     "w":300,"h":300}],"seat":"dingyue"}],"bidid":"cc83ecfe-8f7f-4e41-af4b-b3cbdb6a7e69","cur":"CNY"}
 */
public final class BidDsp39Parser extends JsonDataParser {
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
        bidKeys.setAdvIdKey("crid");
        // 鼎阅传媒(39)，价格是千次的价格，单位是分
        bidKeys.setBidFloorKey("price");

        return bidKeys;
    }

    /**
     * 获取exchange id
     * @return
     */
    public String getExchangeId() {
        return Constants.EXCHANGE_DINGYUE;
    }
}



