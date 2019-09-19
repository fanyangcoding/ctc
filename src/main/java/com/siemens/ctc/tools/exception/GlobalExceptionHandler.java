package com.siemens.ctc.tools.exception;

import com.siemens.ctc.tools.result.ResultModel;
import com.siemens.ctc.tools.result.ResultStatus;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Global Exception Handler
 *
 * @author Fan Yang
 * createTime: 2019-08-06
 */

@ControllerAdvice
public class GlobalExceptionHandler {
    private static Logger LOGGER = LogManager.getLogger(GlobalExceptionHandler.class);

    /**
     * Exception Handler
     */

    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public ResultModel<String> exceptionHandler(HttpServletRequest httpServletRequest, Exception e) {
        LOGGER.error("服务出错", e);
        return new ResultModel<>(ResultStatus.FAIL, "服务出错,原因是" + e);
    }

    /**
     * Business Exception Handler
     */
    @ResponseBody
    @ExceptionHandler(value = BusinessException.class)
    public ResultModel<?> businessExceptionHandler(HttpServletRequest httpServletRequest, BusinessException e) {
        LOGGER.info("业务异常", e.getErrorCode() + e.getMsg());
        return new ResultModel<>(ResultStatus.FAIL, e.getMsg());
    }

}
