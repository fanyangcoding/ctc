package com.siemens.ctc.controller;

import com.siemens.ctc.tools.exception.BusinessException;
import com.siemens.ctc.tools.result.ResultModel;
import com.siemens.ctc.tools.result.ResultStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletResponse;

/**
 * Base Controller
 */

public class BaseController {

    private static final Logger LOGGER = LogManager.getLogger(BaseController.class);

    protected void validError(BindingResult result) {
        if (result.hasErrors()) {
            throw new BusinessException(result.getAllErrors().get(0).getCode(), result.getAllErrors().get(1).getDefaultMessage());
        }
    }

    protected ResultModel<String> basicResult(Boolean flag) {
        return flag ? ResultModel.defaultSuccess(null) : ResultModel.defaultError(null);
    }

    protected <T> T getData(ResultModel<T> resultModel) {
        if (ResultStatus.SUCCESS.getCode().equals(resultModel.getCode())) {
            return resultModel.getData();
        }
        throw new BusinessException(resultModel.getCode(), resultModel.getMsg());
    }
}
