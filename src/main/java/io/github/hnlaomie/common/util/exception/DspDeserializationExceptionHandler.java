package io.github.hnlaomie.common.util.exception;

import io.github.hnlaomie.common.constant.Constants;
import io.github.hnlaomie.common.util.ExceptionUtil;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.streams.errors.DeserializationExceptionHandler;
import org.apache.kafka.streams.processor.ProcessorContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * @author <a href="mailto:lichunhui@adwo.com">李春辉</a>
 * @date 2018-07-26
 */
public class DspDeserializationExceptionHandler implements DeserializationExceptionHandler {
    // 日志处理器
    private static Logger logger = LoggerFactory.getLogger(Constants.APP_LOGGER);

    @Override
    public DeserializationHandlerResponse handle(final ProcessorContext context,
                                                 final ConsumerRecord<byte[], byte[]> record,
                                                 final Exception exception) {
        logger.info("========== DspDeserializationExceptionHandler handle =========");
        if (exception instanceof SystemException) {
            logger.error(exception.toString());
            return DeserializationHandlerResponse.FAIL;
        } else if (exception instanceof BusinessException){
            logger.error(((BusinessException) exception).getErrMessage());
            return DeserializationHandlerResponse.CONTINUE;
        } else {
            String errMsg = ExceptionUtil.getExceptionMessage(exception);
            logger.error(errMsg);
            return DeserializationHandlerResponse.CONTINUE;
        }
    }

    @Override
    public void configure(final Map<String, ?> configs) {
        // logger.info("========== DspDeserializationExceptionHandler configure =========");
    }

}


