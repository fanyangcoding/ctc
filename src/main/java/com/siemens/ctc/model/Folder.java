package com.siemens.ctc.model;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 文件夹实体类
 */

@Data
@ApiModel(value = "文件夹实体类", description = "文件夹实体对象")
public class Folder {

    /**
     * id
     */
    @ApiModelProperty(value = "id", name = "id", required = true)
    private Integer id;

    /**
     * 文件夹名
     */
    @ApiModelProperty(value = "文件夹名", name = "folderName", required = true)
    private String folderName;

    /**
     * category
     */
    @ApiModelProperty(value = "顶层文件夹", name = "category", required = true)
    private String category;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间", name = "createTime", required = true)
    private Date createTime;

    /**
     * 最后一次修改时间
     */
    @ApiModelProperty(value = "最后一次修改时间", name = "lastUpdateTime", required = false)
    private Date lastUpdateTime;
}
