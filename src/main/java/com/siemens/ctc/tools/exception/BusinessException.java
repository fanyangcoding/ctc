package com.siemens.ctc.tools.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Business Exception 实体类
 */

@AllArgsConstructor
@Data
public class BusinessException extends RuntimeException {
    private String errorCode;
    private String msg;

}
