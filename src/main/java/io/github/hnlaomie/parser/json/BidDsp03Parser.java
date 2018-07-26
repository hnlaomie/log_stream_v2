package io.github.hnlaomie.parser.json;

import io.github.hnlaomie.common.constant.Constants;
import io.github.hnlaomie.data.BidKeys;

/**
 * @author <a href="mailto:lichunhui@adwo.com">李春辉</a>
 * @date 2018-07-22
 * 百度(3)竞标数据处理
 *
 *     输入格式如下:
 *     时间|ip|json数据
 *
 *     输入用例:
 *     2018-06-02 16:38:11|1.59.77.220|{"id":"b5af3804472f620d","ad":[{"sequence_id":1,"creative_id":1014883,"max_cpm":500,
 *     "extdata":"74F5DBA68337DE7F1261C9E99519501D1A5D7AC1C722FAB7231E34B30F44605F449D60E5F255CB4AEC09092BDCBBC849E87B4CD29C2DF64E8983E89346ADAAF95F1FF92DCB3E6D83E46F2AFF1D9C09D49F47929BA651B4F76EE528A3AA2958EA90C54C2205CB3F0348A08B99923EA150B90D92EE6DF34A76",
 *     "is_cookie_matching":false,"advertiser_id":100,"width":1280,"height":720,"category":4,"landing_page":"http://e.cn.miaozhen.com/r/k=2083242&p=7FRjh&dx=__IPDX__&rt=2&ns=__IP__&ni=__IESID__&v=__LOC__&xa=__ADPLATFORM__&tr=__REQUESTID__&mo=__OS__&m0=__OPENUDID__&m0a=__DUID__&m1=__ANDROIDID1__&m1a=__ANDROIDID__&m2=__IMEI__&m4=__AAID__&m5=__IDFA__&m6=__MAC1__&m6a=__MAC__&o=http://monitor.180china.com:8080/monitor/mobile/u100001/p100001/02/?a0=72&a1=163&a2=115&a3=56&a4=TS&a6=1&a7=2&a8=2&d0=i&d1=d&d2=r&d8=1&m0=31&m1=3161&m2=2&m3=2&m4=62&m5=b&m6=u&m7=0&m8=0.0&mc=IP&md=udid&me=34&r5=1&ab=1640",
 *     "native_ad":{"image":[{"url":"http://static.adwo.com/uploaddsp/2018/0531/3723/1014883/1280x720.jpg","width":1280,"height":720}]
 *     }}]}
 */
public final class BidDsp03Parser extends JsonDataParser {
    /**
     * 获取竞标json需要处理数据的键值对象
     * @return
     */
    @Override
    public BidKeys getBidKeys() {
        BidKeys bidKeys = new BidKeys();

        bidKeys.setBidIdKey("id");
        bidKeys.setBidListKey("ad");
        bidKeys.setImpIdKey("sequence_id");
        bidKeys.setAdvIdKey("creative_id");
        // 百度的底价单位为分
        bidKeys.setBidFloorKey("max_cpm");

        return bidKeys;
    }

    /**
     * 获取exchange id
     * @return
     */
    @Override
    public String getExchangeId() {
        return Constants.EXCHANGE_BAIDU;
    }
}
