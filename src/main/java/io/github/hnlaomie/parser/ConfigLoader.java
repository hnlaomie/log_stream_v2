package io.github.hnlaomie.parser;

import io.github.hnlaomie.common.constant.MessageID;
import io.github.hnlaomie.common.util.exception.SystemException;
import io.github.hnlaomie.data.DspConfig;
import io.github.hnlaomie.data.ParserConfig;
import me.doubledutch.lazyjson.LazyArray;
import me.doubledutch.lazyjson.LazyObject;

/**
 * @author <a href="mailto:lichunhui@adwo.com">李春辉</a>
 * @date 2018-07-23
 */
public class ConfigLoader {

    public static DspConfig loadDspConfig(String content) {
        DspConfig dspConfig = new DspConfig();

        try {
            LazyObject root = new LazyObject(content);
            LazyArray parserArray = root.getJSONArray("parsers");
            for (int i = 0; i < parserArray.length(); i++) {
                LazyObject parser = parserArray.getJSONObject(i);
                String topic = parser.getString("topic");
                String buildClass = parser.getString("buildClass");
                ParserConfig config = new ParserConfig();
                config.setTopic(topic);
                config.setBuildClass(buildClass);
                dspConfig.getParserList().add(config);
            }
        }  catch (SystemException e) {
            throw e;
        } catch (Exception e) {
            SystemException exp = new SystemException(MessageID.MSG_010009, e);
            throw exp;
        }

        return dspConfig;
    }


}

