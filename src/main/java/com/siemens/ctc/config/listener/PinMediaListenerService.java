package com.siemens.ctc.config.listener;

import com.siemens.ctc.dao.MediaMapper;
import com.siemens.ctc.dao.PinMediaMapper;
import com.siemens.ctc.pin.PinMediaListenerThread;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 置顶监听器服务，随系统启动
 *
 * @author Fan Yang
 * createTime: 2019-08-15
 */

@Component
@Order(value = 1)
public class PinMediaListenerService implements ApplicationRunner {

    private static final Logger LOGGER = LogManager.getLogger(PinMediaListenerService.class);

    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    @Resource
    private PinMediaMapper pinMediaMapper;

    @Resource
    private MediaMapper mediaMapper;

    @Override
    public void run(ApplicationArguments args) {
        LOGGER.info("启动置顶监听器");
        executorService.execute(new PinMediaListenerThread(pinMediaMapper, mediaMapper));
    }
}
