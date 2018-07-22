package test;

import io.github.hnlaomie.common.constant.Constants;
import io.github.hnlaomie.data.DspLog;
import io.github.hnlaomie.parser.IDataParser;
import io.github.hnlaomie.parser.csv.ShowParser;
import me.doubledutch.lazyjson.LazyArray;
import me.doubledutch.lazyjson.LazyObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.hnlaomie.common.util.exception.LogException;
import io.github.hnlaomie.common.constant.MessageID;
import io.github.hnlaomie.common.util.ExceptionUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:laomie@163.com">laomie</a>
 * @date 2018/02/07
 */
public class Test {
    // 日志处理器
    private static Logger logger = LoggerFactory.getLogger(Test.class);

    public static void main(String[] args) {
        Test t = new Test();
        t.csvTest();
    }

    public void csvTest() {
        String lineSeparator = System.lineSeparator();
        String file = "/data/dspc.csv";
        try (InputStream in = getClass().getResourceAsStream(file);) {
            String source = new BufferedReader(new InputStreamReader(in))
                    .lines()
                    .parallel()
                    .collect(Collectors.joining(lineSeparator));

            IDataParser parser = new ShowParser();
            List<DspLog> logList = parser.build(Constants.TOPIC_DSPC, source);
            logger.info(logList.toString());
        } catch (IOException e) {
            logger.info(e.toString());
        } catch (LogException e) {
            logger.info(e.toString());
        }
    }

    public void logTest() {
        LogException e = ExceptionUtil.handle(MessageID.MSG_010002, new Exception("abc"));
        logger.info(e.toString());
    }

    public void numberTest() {
        String d = "1.2b-10";
        Boolean isNumber = false;
        try
        {
            Double.parseDouble(d);
            isNumber = true;
        }
        catch(NumberFormatException e)
        {
            isNumber = false;
        }
        logger.info(isNumber.toString());
    }

    public void jsonTest() {
        String lineSeparator = System.lineSeparator();
        String file = "/test/json/dsp03.json";

        try (InputStream in = getClass().getResourceAsStream(file);) {
            String source = new BufferedReader(new InputStreamReader(in))
                    .lines()
                    .parallel()
                    .collect(Collectors.joining(lineSeparator));

            LazyObject root = new LazyObject(source);
            String id = root.getString("id");
            LazyArray adArray = root.getJSONArray("ad");
            for (int i = 0; i < adArray.length(); i++) {
                LazyObject adNode = adArray.getJSONObject(i);
                Integer advId = adNode.getInt("creative_id");
                Double price = adNode.getDouble("max_cpm");
                logger.info(id);
                logger.info(advId.toString());
                logger.info(price.toString());
            }

        } catch (IOException e) {
            logger.info(e.toString());
        } catch (LogException e) {
            logger.info(e.toString());
        }
    }
}
