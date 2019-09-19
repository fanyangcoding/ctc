package com.siemens.ctc.pin;

import com.siemens.ctc.dao.MediaMapper;
import com.siemens.ctc.dao.PinMediaMapper;
import com.siemens.ctc.model.PinMedia;
import com.siemens.ctc.tools.exception.BusinessException;
import com.siemens.ctc.tools.exception.ErrorCodeEnum;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 置顶监听器
 *
 * @author Fan Yang
 * createTime: 2019-08-15
 */

@Component
@Transactional
public class PinMediaListenerThread implements Runnable {

    private static final Logger LOGGER = LogManager.getLogger(PinMediaListenerThread.class);

    private PinMediaMapper pinMediaMapper;

    private MediaMapper mediaMapper;

    public PinMediaListenerThread(PinMediaMapper pinMediaMapper, MediaMapper mediaMapper) {
        this.pinMediaMapper = pinMediaMapper;
        this.mediaMapper = mediaMapper;
    }

    @Override
    public void run() {

        while (true) {
            LOGGER.info("置顶监听器启动");

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date today = new Date();

            List<PinMedia> pinMediaList = pinMediaMapper.getPinMedia();
            if (pinMediaList.size() == 0) {
                LOGGER.info("没有置顶文件");
                try {
                    LOGGER.info("监听器将进入休眠状态, 7天后将被唤醒");
                    TimeUnit.DAYS.sleep(7);
                } catch (InterruptedException e) {
                    LOGGER.error("置顶监听器异常: " + e.getMessage());
                    Thread.currentThread().interrupt();
                    throw new BusinessException(ErrorCodeEnum.PIN_LISTENER_EXCEPTION.getErrorCode(), ErrorCodeEnum.PIN_LISTENER_EXCEPTION.getMsg());

                }
            }
            LOGGER.info("按日期排序, 由近及远");
            Collections.sort(pinMediaList); // 置顶文件按日期排序，由近及远

            for (PinMedia pinMedia : pinMediaList) {
                Date endDate = pinMedia.getEndDate(); // 首先取最近的一天
//                String path = mediaMapper.getMediaById(pinMedia.getMediaId()).getPath();
                String path = mediaMapper.getMediaById(pinMedia.getMediaId()).getPath();
                LOGGER.info(path + " 结束置顶日期: " + dateFormat.format(endDate) + "，距离今天最近");
                long daysBetween = (endDate.getTime() - today.getTime() + 1000000) / (1000 * 60 * 60 * 24); // 换算成天
                LOGGER.info(path + " 距离结束置顶日期还剩：" + daysBetween + "天");
                if (daysBetween == 0) {
                    LOGGER.info(path + " 的置顶周期结束，取消置顶");
                    mediaMapper.cancelPin(pinMedia.getMediaId());
                    LOGGER.info(path + " 的pin设为'unpin'");
                    pinMediaMapper.unsetPin(path);
                } else
                    try {
                        LOGGER.info("监听器将进入休眠状态, " + dateFormat.format(endDate) + " 将被唤醒");
                        TimeUnit.DAYS.sleep(daysBetween);
                    } catch (InterruptedException e) {
                        LOGGER.error("置顶监听器异常: " + e.getMessage());
                        Thread.currentThread().interrupt();
                        throw new BusinessException(ErrorCodeEnum.PIN_LISTENER_EXCEPTION.getErrorCode(), ErrorCodeEnum.PIN_LISTENER_EXCEPTION.getMsg());
                    }
            }
        }

    }
}
