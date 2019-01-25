package io.github.hnlaomie.common.util.exception;

import io.github.hnlaomie.common.constant.Constants;
import io.github.hnlaomie.common.util.ExceptionUtil;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.streams.errors.ProductionExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * @author <a href="mailto:lichunhui@adwo.com">李春辉</a>
 * @date 2018-07-26
 */
public class DspProductionExceptionHandler implements ProductionExceptionHandler {
    // 日志处理器
    private static Logger logger = LoggerFactory.getLogger(Constants.APP_LOGGER);

    @Override
    public ProductionExceptionHandlerResponse handle(final ProducerRecord<byte[], byte[]> record,
                                                     final Exception exception) {
        logger.info("========== DspProductionExceptionHandler handle =========");
        String errMsg = ExceptionUtil.getExceptionMessage(exception);
        logger.error(errMsg);
        return ProductionExceptionHandlerResponse.CONTINUE;
    }

    @Override
    public void configure(final Map<String, ?> configs) {
        // logger.info("========== DspProductionExceptionHandler configure =========");
    }

}

