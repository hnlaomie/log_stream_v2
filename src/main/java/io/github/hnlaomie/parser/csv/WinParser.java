package io.github.hnlaomie.parser.csv;

import io.github.hnlaomie.common.constant.Constants;

/**
 * @author <a href="mailto:lichunhui@adwo.com">李春辉</a>
 * @date 2018-07-20
 * dsp中标日志数据处理
 *
 *     输入格式如下:
 *     分:秒,毫秒 - exchangeid, bid(请求ID_曝光ID), 广告ID, 请求广告接口时间戳, 点击时间戳,
 *         uid, paymethod, payMoney, 展示价格, 请求接口的ip(需保存的ip),
 *         展示广告的客户ip, 展示ID, 设备机型, 媒体ID(有些为 app bundle), 是否重复,
 *         广告账户ID, 广告组ID, 广告平台, 中标价格单位, 中标是否有效(1有效，2中标价格有问题，4中标价格大于广告最高价，6为超时表示无效),
 *         设备ID, 设备类型(IMEI或ifa), 广告是否还有效(到达上限), ck校验信息
 *
 *     输入用例:
 *     3,2c9a94d7492f4c49_1,1014874,1527956257170,1528150869639,
 *     ,0,0.000000,0.002630,106.115.34.195,
 *     106.115.34.195,1,Coolpad T2-C01,ba9af525,0,
 *     333436,3723,2,CNY,6,
 *     d52861d9f95231bf5bc891bb7b58084a,IMEI,1,0
 */
public class WinParser extends CsvDataParser {
    /**
     * 获取数据类型, 1请求，2竞标，3中标，4展示，5点击
     *
     * @return
     */
    @Override
    public String getLogType() {
        return Constants.WIN_LOG;
    }
}
