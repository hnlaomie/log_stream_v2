package io.github.hnlaomie.parser.csv;

import io.github.hnlaomie.common.constant.Constants;

/**
 * @author <a href="mailto:lichunhui@adwo.com">李春辉</a>
 * @date 2018-07-20
 * dsp点击日志数据处理
 *
 *     输入格式如下:
 *     分:秒,毫秒 - exchangeid, bid(请求ID_曝光ID), 广告ID, 请求广告接口时间戳, 点击时间戳,
 *         uid, paymethod, payMoney, 展示价格, 请求接口的ip(需保存的ip),
 *         展示广告的客户ip, 展示ID, 设备机型, 媒体ID(有些为 app bundle), 是否重复,
 *         广告账户ID, 广告组ID, 广告平台, 中标价格单位, 点击是否有效(1有效，2展示价格有问题，4展示价格大于广告最高价，6为超时表示无效),
 *         设备ID, 设备类型(IMEI或ifa), 广告是否还有效(到达上限), ck校验信息，机器信息
 *         user_agen
 *
 *     输入用例:
 *     13,0bt1kI1FplJq2bUAGv2z7HN64GyGCs_1,1014901,1528004348279,1528004366985,
 *     ,0,0.002,0.001000,123.10.197.31,
 *     123.10.197.31,1,m1 metal,com.outfit7.mytalkingtomfree,1,
 *     333436,3725,2,CNY,1,
 *     869402020000000,imei,1,0,,
 *     Mozilla/5.0 (Linux; Android 5.1; m1 metal Build/LMY47I; wv) AppleWebKit/537.36 (KHTML; like Gecko) Version/4.0 Chrome/44.0.2403.147 Mobile Safari/537.36,Mozilla/5.0 (Linux; Android 5.1; m1 metal Build/LMY47I; wv) AppleWebKit/537.36 (KHTML; like Gecko) Version/4.0 Chrome/44.0.2403.147 Mobile Safari/537.36
 */
public class ClickParser extends CsvDataParser {
    /**
     * 获取数据类型, 1请求，2竞标，3中标，4展示，5点击
     *
     * @return
     */
    @Override
    public String getLogType() {
        return Constants.CLICK_LOG;
    }
}
