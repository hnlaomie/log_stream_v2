package io.github.hnlaomie.parser;

import java.io.*;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.github.hnlaomie.common.constant.Constants;
import io.github.hnlaomie.common.constant.MessageID;
import io.github.hnlaomie.common.util.exception.SystemException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ip转城市ID处理类
 * @author <a href="mailto:laomie@163.com">laomie</a>
 * @date 2018/07/19
 */

class IpData implements Comparable {
    // 开始ip
    private Long beginIp;
    // 结束ip
    private Long endIp;
    // 城市ID
    private String cityId;

    IpData(Long beginIp, Long endIp, String cityId) {
        this.beginIp = beginIp;
        this.endIp = endIp;
        this.cityId = cityId;
    }

    public Long getBeginIp() {
        return beginIp;
    }

    public Long getEndIp() {
        return endIp;
    }

    public String getCityId() {
        return cityId;
    }

    @Override
    public int compareTo(Object o) {
        IpData e = (IpData) o;
        return getBeginIp().compareTo(e.getBeginIp());
    }
}

public class IpMap implements Serializable {
    /**
     * 将ip转化为城市ID,通过在映射数据中定位记录,给出该记录的城市ID
     */
    private static final long serialVersionUID = 6270426736192867242L;
    //
    private List<IpData> ipCityList = new ArrayList<IpData>();
    private int ipCityLength = 0;

    private static IpMap ipMap = null;

    // 日志处理器
    private static Logger logger = LoggerFactory.getLogger(Constants.APP_LOGGER);

    public static IpMap getInstance() {
        if (ipMap == null) {
            synchronized(IpMap.class) {
                if (ipMap == null) {
                    ipMap = new IpMap();
                }
            }
        }
        return ipMap;
    }

    public int getIpCityLength() {
        return ipCityLength;
    }

    /**
     * 根据ip获取城市ID
     * @param ip
     * @return
     */
    public String getCityId(String ip) {
        IpData data = getIpData(ip);
        // 默认为"-999999"
        String result = (data == null) ? Constants.DEFAULT_CITY_ID : data.getCityId();
        return result;
    }

    /**
     * 获取ip所在段的开始ip,无段则返回ip的整数形式
     * @param ip
     * @return
     */
    public Long getBeginIp(String ip) {
        // 默认给ip的整数形式
        IpData data = getIpData(ip);
        Long result = (data == null) ? ipToLong(ip) : data.getBeginIp();
        return result;
    }

    /**
     * 如果字符串包含ip则返回ip
     *
     * @param str
     * @return
     */
    public String getIp(String str) {
        if (str == null) {
            return null;
        }
        Pattern pattern = Pattern
                .compile("((\\d|[1-9]\\d|1\\d\\d|2[0-4]\\d|25[0-5]|[*])\\.){3}(\\d|[1-9]\\d|1\\d\\d|2[0-4]\\d|25[0-5]|[*])");
        Matcher matcher = pattern.matcher(str);

        if (matcher.find()) {
            return matcher.group();
        }
        return null;
    }

    public String numToIp(long num) {
        long[] rtn = new long[4];
        long y = 0, x = num;

        if (num > 0) {
            for (int i = 0; i < rtn.length - 1; i++) {
                y = x;
                x = y % (2 << (8 * (3 - i) - 1));
                rtn[i] = (y - x) / (2 << (8 * (3 - i) - 1));
            }

            return rtn[0] + "," + rtn[1] + "," + rtn[2] + "," + x;
        } else {
            return "" + num;
        }

    }

    private IpMap() {
        loadData();
    }

    private void loadFromReader(BufferedReader br) throws IOException {
        String s = "";
        while ((s = br.readLine()) != null) {
            String line = s;
            IpData ipData = lineToIpData(line);
            // 规范的ip数据,则将数据放入ip列表
            if (ipData != null) {
                this.ipCityList.add(ipData);
            }
        }
        this.ipCityLength = this.ipCityList.size();
        // ip数据根据开始ip排序
        if (this.ipCityLength > 0) {
            Collections.sort(this.ipCityList);
        }
        logger.info("成功载入ip城市数据,共" + this.ipCityLength + "行。");
    }

    private void loadData() {
        try (
                InputStream in = getClass().getResourceAsStream(Constants.IP_CITY_FILE);
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));) {
            loadFromReader(reader);
        }  catch (Exception e) {
            SystemException exp = new SystemException(MessageID.MSG_010007, e);
            throw exp;
        }
    }

    /**
     * 行数据转化为ip数据对象
     * @param line 开始ip,结束ip,城市ID
     * @return
     */
    private IpData lineToIpData(String line) {
        IpData ipData = null;

        if (line != null && line.length() > 0) {
            String[] cols = line.split(",");
            // 有3列,则将数据放入ip列表
            if (cols.length == 3) {
                Long beginIp = Long.valueOf(cols[0]);
                Long endIp = Long.valueOf(cols[1]);
                String cityId = cols[2];
                ipData = new IpData(beginIp, endIp, cityId);
            }
        }

        return ipData;
    }

    /**
     * 将ip转为长整数
     * @param ip
     * @return
     */
    private Long ipToLong(String ip) {
        if (ip == null) return 0L;
        String[] arr = ip.trim().split("\\.");
        Long num;
        if (arr.length == 4) {
            try {
                num = Long.parseLong(arr[0]) * 256 * 256 * 256
                        + Long.parseLong(arr[1]) * 256 * 256
                        + Long.parseLong(arr[2]) * 256
                        + Long.parseLong(arr[3]);
            } catch (Exception ex) {
                num = 0L;
            }
        } else
            num = 0L;

        return num;
    }

    /**
     * 判断整数ip是否在给定范围的数据里
     * @param intIp 整数ip
     * @param indexes 数据范围([开始数,结束数])
     * @return true(在范围里), false(不在范围里)
     */
    private boolean numberInRange(Long intIp, int[] indexes) {
        int indexBegin = indexes[0];
        int indexEnd = indexes[1] - 1;
        Long beginIp = this.ipCityList.get(indexBegin).getBeginIp();
        Long endIp = this.ipCityList.get(indexEnd).getEndIp();
        boolean isInRange = (beginIp <= intIp && intIp <= endIp) ? true : false;

        return isInRange;
    }

    private IpData getIpData(String ip) {
        IpData result = null;
        Long intIp = ipToLong(ip);

        if (intIp > 0) {
            // 保存数据的范围
            Stack<int[]> indexStack = new Stack<>();
            // 开始范围是所有数据
            int[] indexes = {0, ipCityLength};
            boolean hasIp = numberInRange(intIp, indexes);
            // ip在指定范围数据里则进栈
            if (hasIp) {
                indexStack.push(indexes);
            }

            while (!indexStack.empty()) {
                indexes = indexStack.pop();
                int indexBegin = indexes[0];
                int indexEnd = indexes[1];
                int indexStep = indexEnd - indexBegin;
                // 当范围只有一行数据，则ip为该行数据的城市ＩＤ
                if (indexStep == 1) {
                    result = ipCityList.get(indexBegin);
                } else {
                    // 平分范围
                    int indexMedian = (indexEnd - indexBegin) / 2 + indexBegin;
                    int[] leftIndexes = {indexBegin, indexMedian};
                    boolean leftHasIp = numberInRange(intIp, leftIndexes);
                    if (leftHasIp) {
                        indexStack.push(leftIndexes);
                    } else {
                        int[] rightIndexes = {indexMedian, indexEnd};
                        boolean rightHasIp = numberInRange(intIp, rightIndexes);
                        if (rightHasIp) {
                            indexStack.push(rightIndexes);
                        }
                    }
                }

            }
        }

        return result;
    }

}