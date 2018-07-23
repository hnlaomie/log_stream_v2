package io.github.hnlaomie.parser;

import io.github.hnlaomie.common.constant.MessageID;
import io.github.hnlaomie.common.util.ExceptionUtil;
import io.github.hnlaomie.common.util.exception.LogException;
import io.github.hnlaomie.data.DspConfig;
import io.github.hnlaomie.data.ParserConfig;
import me.doubledutch.lazyjson.LazyArray;
import me.doubledutch.lazyjson.LazyObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:lichunhui@adwo.com">李春辉</a>
 * @date 2018-07-23
 */
public class DspParserLoader {
    public static DspConfig load() {
        DspConfig dspConfig = new DspConfig();

        String configFile = "/config/parser/dsp.json";
        String lineSeparator = System.lineSeparator();
        try (InputStream in = DspParserLoader.class.getClass().getResourceAsStream(configFile);) {
            String content = new BufferedReader(new InputStreamReader(in))
                    .lines()
                    .parallel()
                    .collect(Collectors.joining(lineSeparator));

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

        }  catch (IOException e) {
            LogException exp = ExceptionUtil.handle(MessageID.MSG_010009, e);
            throw exp;
        }

        return dspConfig;
    }
}
