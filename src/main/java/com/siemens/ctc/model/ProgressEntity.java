package com.siemens.ctc.model;

import lombok.Data;

import java.io.Serializable;

/**
 * 进度实体类
 */
@Data
public class ProgressEntity implements Serializable {
    private long pBytesRead = 0L; // 到目前为止读取文件的大小，单位Byte
    private long pContentLength = 0L; // 文件总大小
    private int pItems; // 目前正在读取第几个文件

    @Override
    public String toString() {
        float tmp = (float) pBytesRead;
        float result = tmp * 100 / pContentLength;
        return "ProgressEntity{" +
                "pBytesRead=" + pBytesRead +
                ", pContentLength=" + pContentLength +
                ", percent=" + result + "%" +
                ", pItems=" + pItems +
                '}';
    }
}
