package com.siemens.ctc.model;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Media 实体类
 */
@Data
@ApiModel(value = "文件实体类", description = "文件实体对象")
public class Media implements Comparable<Media> {

    /**
     * id
     */
    @ApiModelProperty(value = "mediaId", name = "mediaId")

    private Integer id;

    /**
     * 文件名
     */
    @ApiModelProperty(value = "文件名", name = "mediaName", required = true)
    private String mediaName;

    /**
     * 文件大小（单位：字节）
     */
    @ApiModelProperty(value = "文件大小", name = "fileSize", required = true)
    private Long fileSize;

    /**
     * 存储路径
     */
    @ApiModelProperty(value = "文件路径", name = "path", required = true)
    private String path;

    /**
     * 是否置顶
     */

    @ApiModelProperty(value = "是否置顶", name = "pin", required = true, notes = "四种选择，unpin, one week, two weeks, three weeks")
    private Integer pin;
    /**
     * 文件夹名字
     */
    @ApiModelProperty(value = "文件夹名字", name = "category", required = true)
    @NotBlank
    private String category;

    /**
     * 保密级别
     */
    @ApiModelProperty(value = "安全等级", name = "authority", required = true)
    @NotBlank
    private String authority;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间", name = "createTime", required = true)
    private Date createTime;

    /**
     * 拥有者
     */
    @ApiModelProperty(value = "owner", name = "owner", required = true)
    @NotBlank
    private String owner;

    /**
     * 扩展名
     */
    @ApiModelProperty(value = "扩展名", name = "type", required = true)
    private String type;

    /**
     * 下载量
     */
    @ApiModelProperty(value = "下载量", name = "downloadNum")
    private int downloadNum;

    /**
     * 点击量
     */
    @ApiModelProperty(value = "浏览量", name = "viewNum")
    private int viewNum;

    /**
     * 标签
     */
    @ApiModelProperty(value = "标签", name = "labels")
    @NotBlank
    private List<Label> labels;

    /**
     * 缩略图路径
     */
    @ApiModelProperty(value = "封面", name = "thumbnailPath")
    private String thumbnailPath;

//    @Override
//    protected Serializable pkVal() {
//        return this.id;
//    }

    @Override
    public String toString() {
        return "Media{" +
                ", id=" + id +
                ", media_name=" + mediaName +
                ", fileSize=" + fileSize +
                ", pin=" + pin +
                ", path=" + path +
                ", category=" + category +
                ", createTime=" + createTime +
                ", owner=" + owner +
                ", type=" + type +
                ", downloadCount" + downloadNum +
                ", viewCount" + viewNum +
//                ", labels" + labels +
                "}";
    }

    @Override
    public int compareTo(Media media) {
        return this.viewNum - media.getViewNum();
    }

//    @Override
//    public boolean equals(Object object) {
//        if (this == object) return true;
//        if (object == null || getClass() != object.getClass()) return false;
//        if (object instanceof Media) {
//            Media media = (Media) object;
//            if (media.getId().equals(((Media) object).getId())) return true;
//        }
//        return false;
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(id);
//    }
}
