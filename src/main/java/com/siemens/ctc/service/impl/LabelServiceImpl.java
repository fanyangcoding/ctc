package com.siemens.ctc.service.impl;

import com.siemens.ctc.dao.LabelMapper;
import com.siemens.ctc.model.Label;
import com.siemens.ctc.model.Media;
import com.siemens.ctc.service.LabelService;
import com.siemens.ctc.tools.exception.BusinessException;
import com.siemens.ctc.tools.exception.ErrorCodeEnum;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 *
 * @author Fan Yang
 * createTime：2019-08-09
 */

@Service
public class LabelServiceImpl implements LabelService {

    private static final Logger LOGGER = LogManager.getLogger(LabelServiceImpl.class);

    @Resource
    private LabelMapper labelMapper;

    /**
     * 标签表中添加新标签，若存在，则数量加1
     *
     * @param labelName 标签名
     * @return 新增的标签数
     */

    @Override
    public int addLabel(String labelName) {
        if (labelMapper.isLabelExist(labelName)) { // 如存在，标签数加1
            return labelMapper.plusLabelNum(labelName);
        } else {
            int count = labelMapper.addLabel(labelName); // 添加新标签，更新标签表
            if (count != 1) {
                LOGGER.error("添加新标签失败");
                throw new BusinessException(ErrorCodeEnum.LABEL_ADD_FAILURE.getErrorCode(), ErrorCodeEnum.LABEL_ADD_FAILURE.getMsg());
            }
            return count;
        }
    }

    /**
     * 获取文件的标签
     *
     * @param mediaId 文件id
     * @return 获取的标签list
     * @see com.siemens.ctc.model.Label
     */

    @Override
    public List<Label> getLabels(Integer mediaId) {
        return labelMapper.getLabels(mediaId);
    }

    /**
     * 判断标签是否存在
     *
     * @param labelName 标签名
     * @return 是否存在
     */

    @Override
    public boolean isLabelExist(String labelName) {
        return labelMapper.isLabelExist(labelName);
    }

    /**
     * 给文件添加标签
     *
     * @param mediaId   文件id
     * @param labelName 标签名
     * @return 新增量
     */

    @Override
    public int addLabels(Integer mediaId, String labelName) {
        this.addLabel(labelName);
        int count = labelMapper.addLabelMap(mediaId, labelName);
        if (count != 1) {
            throw new BusinessException(ErrorCodeEnum.LABEL_ADD_FAILURE.getMsg(), ErrorCodeEnum.LABEL_ADD_FAILURE.getMsg());
        }
        return count;
    }

    /**
     * 删除文件标签
     */

    @Override
    public int deleteLabel(Integer mediaId, String labelName) {
        labelMapper.minusLabelNum(labelName); // 更新标签表中label_num字段
        if (labelMapper.getLabelNum(labelName) == 0) {
            labelMapper.deleteLabelMap(mediaId, labelName);
            int count = labelMapper.removeLabel(labelName);
            if (count != 1) {
                LOGGER.error("清除标签失败");
                throw new BusinessException(ErrorCodeEnum.LABEL_REMOVE_FAILURE.getMsg(), ErrorCodeEnum.LABEL_REMOVE_FAILURE.getMsg());
            }
        }
        return labelMapper.deleteLabelMap(mediaId, labelName);
    }

    /**
     * 标签量加1
     *
     * @param labelName 标签名
     * @return 新增量
     */

    @Override
    public int plusLabelNum(String labelName) {
        return labelMapper.plusLabelNum(labelName);
    }

    /**
     * 标签量减1
     *
     * @param labelName 标签名
     * @return 减少量
     */

    @Override
    public int minusLabelNum(String labelName) {
        return labelMapper.minusLabelNum(labelName);
    }

    /**
     * 获取总的标签名和对应的标签量
     *
     * @return 标签list
     * @see com.siemens.ctc.model.Label
     */

    @Override
    public List<Label> getLabelNameAndNum() {
        return labelMapper.getLabelNameAndNum();
    }


    /**
     * 标签索引
     *
     * @param labelName 标签名
     * @return 文件的list
     * @see com.siemens.ctc.model.Media
     */
    @Override
    public List<Media> labelSearch(String labelName) {
        return labelMapper.labelSearch(labelName);


    }
}
