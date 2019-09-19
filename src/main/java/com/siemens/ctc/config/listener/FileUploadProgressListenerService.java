package com.siemens.ctc.config.listener;

import com.siemens.ctc.model.ProgressEntity;
import com.siemens.ctc.tools.util.DateUtil;
import org.apache.commons.fileupload.ProgressListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;

@Component
public class FileUploadProgressListenerService implements ProgressListener {

    private static final Logger LOGGER = LogManager.getLogger(FileUploadProgressListenerService.class);

    private HttpSession session;

    public void setSession(HttpSession session) {
        this.session = session;
        LOGGER.info("上传进度  -------------------" + DateUtil.getTime());
        LOGGER.info("sessionId: " + session.getId());
        ProgressEntity status = new ProgressEntity();
        session.setAttribute("status", status);
    }


    @Override
    public void update(long pBytesRead, long pContentLength, int pItems) {
        ProgressEntity status = (ProgressEntity) session.getAttribute("status");
        status.setPBytesRead(pBytesRead);
        status.setPContentLength(pContentLength);
        status.setPItems(pItems);
        LOGGER.info("#################现在上传到第 " + ((ProgressEntity) session.getAttribute("status")).getPItems() + "个");
    }
}
