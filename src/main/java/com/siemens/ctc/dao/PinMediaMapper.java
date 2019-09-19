package com.siemens.ctc.dao;

import com.siemens.ctc.model.PinMedia;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * PinMedia DAO
 *
 * @author Fan Yang
 * crateTime: 2019-08-15
 */

@Mapper
@Repository
public interface PinMediaMapper {

    /**
     * 获取置顶文件
     *
     * @return 置顶文件列表
     */
    List<PinMedia> getPinMedia();

    /**
     * 设置置顶文件为unpin
     *
     * @param path 文件路径
     */

    void unsetPin(@Param("path") String path);

}
