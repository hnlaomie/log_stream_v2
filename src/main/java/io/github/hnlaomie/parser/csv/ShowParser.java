package io.github.hnlaomie.parser.csv;

import io.github.hnlaomie.common.constant.Constants;

/**
 * @author <a href="mailto:lichunhui@adwo.com">李春辉</a>
 * @date 2018-07-20
 * dsp展示日志数据处理
 *
 *     输入格式如下:
 *     分:秒,毫秒 - exchangeid, bid(请求ID_曝光ID), 广告ID, 请求广告接口时间戳, 展示时间戳,
 *         uid, paymethod, payMoney, 展示价格, 请求接口的ip(需保存的ip),
 *         展示广告的客户ip, 展示ID, 设备机型, 程序ID, 是否重复,
 *         广告账户ID, 广告组ID, 广告平台, 中标价格单位, 展示是否有效(1有效，2展示价格有问题，4展示价格大于广告最高价，6为超时表示无效),
 *         设备ID, 设备类型(IMEI或ifa), 广告是否还有效(到达上限), ck校验信息，机器信息
 *         user_agen
 *
 *     输入用例:
 *     18,6f5d66cc-8e82-4821-9f33-1cfd8083c5f3_1,1014957,1528041666812,1528041731165,
 *     ,0,0.003000,0.003000,124.90.133.29,
 *     124.90.133.29,1,iPhone,778,1,
 *     333436,3742,3,CNY,1,
 *     3B1D1E80-0000-0000-0000-640652EC0000,ifa,1,0,,
 *     Mozilla/5.0 (iPhone; CPU iPhone OS 11_1_2 like Mac OS X) AppleWebKit/604.3.5 (KHTML; like Gecko) Mobile/15B202,Mozilla/5.0 (iPhone; CPU iPhone OS 11_1_2 like Mac OS X) AppleWebKit/604.3.5 (KHTML; like Gecko) Mobile/15B202
 */
public class ShowParser extends CsvDataParser {
    /**
     * 获取数据类型, 1请求，2竞标，3中标，4展示，5点击
     *
     * @return
     */
    @Override
    public String getLogType() {
        return Constants.SHOW_LOG;
    }
}
