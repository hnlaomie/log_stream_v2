package io.github.hnlaomie.common.util;

import io.github.hnlaomie.common.constant.MessageID;
import io.github.hnlaomie.common.util.exception.LogException;
import io.github.hnlaomie.parser.IpMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import test.Test;

/**
 * @author <a href="mailto:lichunhui@adwo.com">李春辉</a>
 * @date 2018-07-20
 */
public class IpUtil {

    public static IpMap getIpMap() {
        IpMap ipMap = null;
        try {
            ipMap = IpMap.getInstance();
        } catch (Exception e) {
            LogException exp = ExceptionUtil.handle(MessageID.MSG_010007, e);
            throw exp;
        }
        return ipMap;
    }
}
