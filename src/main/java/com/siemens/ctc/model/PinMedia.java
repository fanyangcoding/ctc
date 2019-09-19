package com.siemens.ctc.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * PinMedia 实体类
 *
 * @author Fan Yang
 * createTime: 2019-08-15
 */

@Data
@ApiModel(value = "置顶文件实体类", description = "置顶文件实体对象")
public class PinMedia implements Comparable<PinMedia> {

    /**
     * id
     */
    @ApiModelProperty(value = "id", name = "id", hidden = true)
    private Integer id;

    /**
     * media_id
     */
    @ApiModelProperty(value = "media_id", name = "文件id")
    private Integer mediaId;

    /**
     * 置顶结束日期
     */

    @ApiModelProperty(value = "endDate", name = "置顶结束日期")
    private Date endDate;

    /**
     * 比较结束置顶日期
     *
     * @param pinMedia 置顶文件对象
     * @return
     */
    @Override
    public int compareTo(PinMedia pinMedia) {
        if (this.endDate.before(pinMedia.getEndDate()))
            return -1;
        else if (this.endDate.equals(pinMedia.getEndDate()))
            return 0;
        else return 1;
    }
}
