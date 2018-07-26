package io.github.hnlaomie.parser.json;

import io.github.hnlaomie.common.constant.Constants;
import io.github.hnlaomie.common.constant.MessageID;
import io.github.hnlaomie.common.util.DataUtil;
import io.github.hnlaomie.common.util.NumberUtil;
import io.github.hnlaomie.common.util.exception.BusinessException;
import io.github.hnlaomie.data.BidKeys;
import io.github.hnlaomie.data.BidData;
import io.github.hnlaomie.data.DspLog;
import io.github.hnlaomie.parser.IDataParser;
import io.github.hnlaomie.parser.IpMap;
import me.doubledutch.lazyjson.LazyArray;
import me.doubledutch.lazyjson.LazyObject;
import me.doubledutch.lazyjson.LazyType;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:lichunhui@adwo.com">李春辉</a>
 * @date 2018-07-20
 * json数据处理基类,输入json格式数据,清洗转换后输出最终数据对象列表
 */
public abstract class JsonDataParser implements IDataParser {
    private IpMap ipMap = IpMap.getInstance();

    /**
     * 获取竞标json需要处理数据的键值对象
     * @return
     */
    protected abstract BidKeys getBidKeys();

    /**
     * 获取exchange id
     * @return
     */
    protected abstract String getExchangeId();

    /**
     * 获取数据类型, 1请求，2竞标，3中标，4展示，5点击
     *
     * @return
     */
    protected String getLogType() {
        return Constants.BID_LOG;
    }

    /**
     * 一般为如下格式： 时间|ip|json数据
     * @return 列数
     */
    protected int getColumnSize() {
        return 3;
    }

    /**
     * 从json对象中生成bid数据对象
     * @param bid
     * @param keys
     * @param bidId
     * @param advId
     * @return
     */
    protected BidData loadBid(LazyObject bid, BidKeys keys, String bidId, String advId) {
        BidData bidData = new BidData();

        String impId = (bid.getType(keys.getImpIdKey()) == LazyType.STRING) ?
                bid.getString(keys.getImpIdKey()) : Integer.toString(bid.getInt(keys.getImpIdKey()));

        String newAdvId = advId;
        if (newAdvId == null) {
            newAdvId = (bid.getType(keys.getAdvIdKey()) == LazyType.STRING) ?
                    bid.getString(keys.getAdvIdKey()) : Integer.toString(bid.getInt(keys.getAdvIdKey()));
        }

        Double bidFloor = (bid.getType(keys.getBidFloorKey()) == LazyType.STRING) ?
                Double.parseDouble(bid.getString(keys.getBidFloorKey())) : bid.getDouble(keys.getBidFloorKey());
        bidFloor = bidFloor == null ? 0 : bidFloor * keys.getPriceMulti();

        bidData.setAdvId(newAdvId);
        bidData.setBidId(bidId + "_" + impId);
        bidData.setBidFloor(bidFloor.toString());
        return bidData;
    }

    /**
     * 载入竞标数据,数据为json格式，主要取竞标ID，广告ID和底价
     * @param jsonContent
     * @param advId
     * @return 空列或数据对象列表
     */
    protected List<BidData> loadJson(String jsonContent, String advId) {
        BidKeys keys = getBidKeys();
        List<BidData> dataList = new ArrayList<>();

        LazyObject root = new LazyObject(jsonContent);
        String bidId = root.getString(keys.getBidIdKey());

        if (keys.getSeatListKey() != null) {
            LazyArray seatArray = root.getJSONArray(keys.getSeatListKey());
            for (int i = 0; i < seatArray.length(); i++) {
                LazyObject seat = seatArray.getJSONObject(i);
                if (seat.has(keys.getBidListKey())) {
                    LazyArray bidArray = seat.getJSONArray(keys.getBidListKey());
                    for (int j = 0; j < bidArray.length(); j++) {
                        LazyObject bid = bidArray.getJSONObject(j);
                        BidData data = loadBid(bid, keys, bidId, advId);
                        dataList.add(data);
                    }
                }
            }
        } else {
            if (root.has(keys.getBidListKey())) {
                LazyArray bidArray = root.getJSONArray(keys.getBidListKey());
                for (int j = 0; j < bidArray.length(); j++) {
                    LazyObject bid = bidArray.getJSONObject(j);
                    BidData data = loadBid(bid, keys, bidId, advId);
                    dataList.add(data);
                }
            }
        }

        return dataList;
    }

    /**
     * 验证数据是否有效,数字列为数字,json数据统一为第0列
     * @param bidList
     * @param content
     * @return
     */
    protected boolean dataValidate(List<BidData> bidList, String content) {
        boolean result = false;

        for (BidData bid : bidList) {
            boolean numValid = NumberUtil.isNumber(bid.getAdvId());
            numValid &= NumberUtil.isNumber(bid.getBidFloor());

            if (!numValid) {
                String[] msgParams = {"0", content};
                BusinessException exception = new BusinessException(MessageID.MSG_010006, msgParams);
                throw exception;
            }
            result = true;
        }

        return result;
    }

    /**
     * 清洗相关主题的日志,返回日志数据对象列表
     * @param topic 竞标相关主题
     * @param content 竞标数据 "日志时间 - 数据原始内容"
     * @return
     */
    public List<DspLog> build(String topic, String content) {
        List<DspLog> dataList = new ArrayList<>();

        // 少于30字符的窜直接忽略,不做任何处理
        if (content != null && content.length() > Constants.LINE_MIN_LENGTH) {
            String[] dataArray = content.trim().split(Constants.BID_DATA_SPLIT);

            // 列数一致才处理，否则抛出列数不一致异常
            int dataSize = dataArray.length;
            int colSize = getColumnSize();
            if (dataSize == colSize) {
                String logTime = dataArray[0];
                String ip = dataArray[1];
                // 部分exchange的第3列为广告ID，例如讯飞(7)
                String advId = colSize > 3 ? dataArray[2] : null;
                String jsonContent = dataArray[colSize - 1];

                List<BidData> bidList = null;
                try {
                     bidList = loadJson(jsonContent, advId);
                } catch (Exception e) {
                    String[] params = {content};
                    BusinessException exception = new BusinessException(MessageID.MSG_010008, params, e);
                    throw exception;
                }

                if (bidList != null) {
                    boolean isValid = dataValidate(bidList, content);
                    if (isValid) {
                        String exchangeId = getExchangeId();
                        String cityId = ipMap.getCityId(ip);
                        for (BidData bid : bidList) {
                            DspLog dspLog = DataUtil.initBidLog(bid, exchangeId, logTime, ip, cityId);
                            dataList.add(dspLog);
                        }
                    }
                }
            } else {
                // 列数验证失败
                String[] msgParams = {Integer.toString(colSize), Integer.toString(dataSize), content};
                BusinessException exception = new BusinessException(MessageID.MSG_010004, msgParams);
                throw exception;
            }
        }

        return dataList;
    }

}
