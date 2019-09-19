package com.siemens.ctc.dao;

import com.siemens.ctc.model.Label;
import com.siemens.ctc.model.Media;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Fan Yang
 * createTime: 2019-07-22
 */

@Repository
@Mapper
public interface LabelMapper {

    /**
     * 添加新标签
     *
     * @param labelName 新标签名
     * @return 增加量
     */

    int addLabel(@Param("labelName") String labelName);

    /**
     * 通过文件id获取标签
     */
    List<Label> getLabels(@Param("mediaId") Integer mediaId);

    /**
     * 通过标签名判断标签是否存在
     *
     * @param labelName 标签名
     * @return 是否存在
     */
    boolean isLabelExist(@Param("labelName") String labelName);

    /**
     * 标签量加1
     *
     * @param labelName 标签名
     * @return 增加量
     */
    int plusLabelNum(@Param("labelName") String labelName);

    /**
     * 标签量减1
     *
     * @param labelName 标签名
     * @return 减少量
     */
    int minusLabelNum(@Param("labelName") String labelName);

    /**
     * 获取标签名和对应的数量
     *
     * @return 标签对象列表
     */
    List<Label> getLabelNameAndNum();

    /**
     * 删除标签映射表中的记录
     *
     * @param mediaId 文件id
     * @param labelName 标签名
     * @return 删除量
     */

    int deleteLabelMap(@Param("mediaId") Integer mediaId, @Param("labelName") String labelName);


    /**
     * 添加标签映射表中的记录
     */
    int addLabelMap(@Param("mediaId") Integer mediaId, @Param("labelName") String labelName);

    /**
     * 标签检索
     *
     * @param labelName 标签名
     * @return 文件列表
     */
    List<Media> labelSearch(@Param("labelName") String labelName);

    /**
     * 通过标签名获取标签数
     *
     * @param labelName 标签名
     * @return 获取量
     */
    int getLabelNum(@Param("labelName") String labelName);

    /**
     * 从标签表中删除标签
     *
     * @param labelName 标签名
     */
    int removeLabel(@Param("labelName") String labelName);

}
