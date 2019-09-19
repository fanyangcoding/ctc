package com.siemens.ctc.tools.result;


import com.siemens.ctc.tools.util.StringUtil;

import java.io.Serializable;

/**
 * Result Model 实体类
 *
 * @param <T>
 * @author Fan Yang
 * createTime: 2019-07-22
 */


public class ResultModel<T> implements Serializable {

    private static final long serialVersionUID = -1802122468331526708L;

    /**
     * 返回码
     */
    private String code;

    /**
     * 描述
     */
    private String msg;

    /**
     * 返回内容
     */
    private T data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public ResultModel() {

    }


    public ResultModel(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ResultModel(String code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public ResultModel(ResultStatus status) {
        this.code = String.valueOf(status.getCode());
        this.msg = status.getMsg();
    }

    public ResultModel(ResultStatus status, T data) {
        this.code = status.getCode();
        this.msg = status.getMsg();
        this.data = data;
    }

    public static ResultModel<String> defaultSuccess(String msg) {
        if (StringUtil.isNotBlank(msg)) {
            return new ResultModel<>(ResultStatus.SUCCESS.getCode(), msg);
        } else {
            return new ResultModel<>(ResultStatus.SUCCESS.getCode(), ResultStatus.SUCCESS.getMsg());
        }
    }

    public static ResultModel<String> defaultError(String msg) {
        if (StringUtil.isNotBlank(msg)) {
            return new ResultModel<>(ResultStatus.FAIL.getCode(), msg);
        } else {
            return new ResultModel<>(ResultStatus.FAIL.getCode(), ResultStatus.FAIL.getMsg());
        }
    }

    @Override
    public String toString() {
        return "Result{" +
                "code=" + code +
                ", message=" + msg +
                ", data=" + data +
                "}";
    }
}
