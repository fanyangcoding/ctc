package com.siemens.ctc;

import com.siemens.ctc.config.listener.FileUploadProgressListenerService;
import com.siemens.ctc.tools.exception.BusinessException;
import com.siemens.ctc.tools.exception.ErrorCodeEnum;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class CustomMultipartResolver extends CommonsMultipartResolver {

    private static final Logger LOGGER = LogManager.getLogger(CustomMultipartResolver.class);

    @Autowired
    private FileUploadProgressListenerService progressListenerService;

    public void setFileUploadProgressListenerService(FileUploadProgressListenerService progressListenerService) {
        this.progressListenerService = progressListenerService;
    }

    @Override
    protected MultipartParsingResult parseRequest(HttpServletRequest request) {
        LOGGER.info("############################# Parse Request");

        String encoding = determineEncoding(request);
        FileUpload fileUpload = prepareFileUpload(encoding);
        fileUpload.setFileSizeMax(1024 * 1024 * 2000);
        fileUpload.setSizeMax(1024 * 1024 * 2000);

        progressListenerService.setSession(request.getSession());

        // 注册进度监听器
        fileUpload.setProgressListener(progressListenerService);

        try {
            List<FileItem> fileItems = ((ServletFileUpload) fileUpload).parseRequest(request);
            return parseFileItems(fileItems, encoding);
        } catch (FileUploadBase.SizeLimitExceededException ex) {
            throw new BusinessException(ErrorCodeEnum.UPLOAD_OVER_MAX_SIZE.getErrorCode(), ErrorCodeEnum.UPLOAD_OVER_MAX_SIZE.getMsg());
        } catch (FileUploadException ex) {
            throw new BusinessException(ErrorCodeEnum.UPLOAD_FAILURE.getErrorCode(), ErrorCodeEnum.UPLOAD_FAILURE.getMsg());
        }
    }
}
