package com.siemens.ctc.tools.result;


import lombok.AllArgsConstructor;

/**
 * Result Status 实体类
 *
 * @author Fan Yang
 * createTime: 2019-07-22
 */
@AllArgsConstructor
public enum ResultStatus {
    SUCCESS("0", "成功"),
    FAIL("1", "失败");

    /**
     * 返回码
     */
    private String code;

    /**
     * 返回结果描述
     */
    private String msg;


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
}
