package com.siemens.ctc.service;

import com.siemens.ctc.model.Label;
import com.siemens.ctc.model.Media;

import java.util.List;

/**
 * @author Fan Yang
 * createTime：2019-08-09
 */

public interface LabelService {

    /**
     * 标签表中添加新标签
     *
     * @param labelName 标签名
     */

    int addLabel(String labelName);


    /**
     * 获取文件所有标签
     * @param mediaId 文件id
     */

    List<Label> getLabels(Integer mediaId);

    /**
     * 判断标签是否存在
     *
     * @param labelName 标签名
     * @return true 存在， false 不存在
     */

    boolean isLabelExist(String labelName);

    /**
     * 标签数量加1
     *
     * @param labelName 标签名
     */

    int plusLabelNum(String labelName);

    /**
     * 标签数量减1
     *
     * @param labelName 标签名
     */

    int minusLabelNum(String labelName);

    /**
     * 文件添加标签
     */

    int addLabels(Integer mediaId, String labelName);


    /**
     * 删除标签
     */

    int deleteLabel(Integer mediaId, String labelName);

    /**
     * 获取所有的标签名和数量
     *
     * @return 标签list
     * @see com.siemens.ctc.model.Label
     */

    List<Label> getLabelNameAndNum();

    /**
     * 标签检索，并分页
     */
    List<Media> labelSearch(String labelName);
}
