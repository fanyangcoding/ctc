package com.siemens.ctc.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Label 实体类
 */

@Data
@ApiModel(value = "标签实体类", description = "标签实体对象")
public class Label {
    /**
     * id
     */
    @ApiModelProperty(value = "标签id", name = "id", required = true)
    private Integer id;

    /**
     * 名字
     */
    @ApiModelProperty(value = "标签名", name = "labelName", required = true)
    private String labelName;

    /**
     * 数量
     */
    @ApiModelProperty(value = "标签数量", name = "labelNum")
    private int labelNum;
}
