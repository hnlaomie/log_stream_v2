package io.github.hnlaomie;

import io.github.hnlaomie.common.constant.Constants;
import io.github.hnlaomie.common.constant.MessageID;
import io.github.hnlaomie.common.util.ExceptionUtil;
import io.github.hnlaomie.common.util.exception.LogException;
import io.github.hnlaomie.data.DspLog;
import io.github.hnlaomie.parser.ExtractManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:lichunhui@adwo.com">李春辉</a>
 * @date 2018-07-23
 */
public class DspStreams {
    // 日志处理器
    private static Logger logger = LoggerFactory.getLogger("test");

    public String loadTestData() throws Exception {
        String content = "";

        String file = "/data/dspc.csv";
        String lineSeparator = System.lineSeparator();
        try (InputStream in = getClass().getResourceAsStream(file);) {
            content = new BufferedReader(new InputStreamReader(in))
                    .lines()
                    .parallel()
                    .collect(Collectors.joining(lineSeparator));
        } catch (IOException e) {
            throw e;
        }

        return content;
    }


    public void test() {
        try {
            ExtractManager manager = ExtractManager.getInstance();
            String content = loadTestData();
            List<DspLog> logList = manager.build(Constants.TOPIC_DSPC, content);
            logger.info(logList.toString());
        } catch (Exception e) {
            LogException exp = ExceptionUtil.handle(MessageID.MSG_010009, e);
            logger.error(exp.toString());
        }
    }

    public static void main(String[] args) {
        DspStreams test = new DspStreams();
        test.test();
    }
}
