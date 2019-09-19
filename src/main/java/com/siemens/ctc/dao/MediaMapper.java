package com.siemens.ctc.dao;

import com.siemens.ctc.model.Folder;
import com.siemens.ctc.model.Media;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @author Fan Yang
 * createTime: 2019-07-30
 */

@Repository
@Mapper
public interface MediaMapper {

    /**
     * 更新文件信息
     *
     * @param media 文件
     * @return 更新量
     */

    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    Integer upload(Media media);


    /**
     * 上传文件夹信息
     */
    Integer uploadFolder(Folder folder);

    /**
     * 更新文件夹lastUpdateTime字段时间
     */
    Integer updateFolder(@Param("folderId") Integer folderId, @Param("updateTime") Date updateTime);

    /**
     * 通过id获取文件对象
     *
     * @param mediaId 文件id
     * @return 文件对象
     */

    Media getMediaById(@Param("mediaId") Integer mediaId);

    /**
     * 获取指定category下指定authority的文件
     */
    List<Media> getMedia(@Param("category") String category, @Param("authority") String authority);

    /**
     * 获取所有文件
     */
    List<Media> getAllMedia();

    /**
     * 获取所有指定安全等级的文件
     */
    List<Media> getMediaByAuthority(@Param("authority") String authority);

    /**
     * 获取指定category下的所有文件
     */
    List<Media> getMediaByCategory(@Param("category") String category);

    /**
     * 获取全部的非特权文件,默认排序
     *
     * @return 文件列表
     */
    List<Media> findAllNoneRestrictMediaByDefault();

    /**
     * 按排序规则获取非特权文件列表
     *
     * @param order 排序
     * @return 文件列表
     */

    List<Media> findAllNoneRestrictMediaByOrder(@Param("order") String order);

    /**
     * 获取指定文件夹下的非特权文件
     */

    List<Media> findNoneRestrictMediaByDefaultWithCategory(@Param("category") String category);

    /**
     * 获取指定文件夹下的非特权文件,并排序
     */

    List<Media> findNoneRestrictMediaByOrderWithCategory(@Param("category") String category, @Param("order") String order);

    /**
     * 获取所有文件
     *
     * @return 文件列表
     */
    List<Media> findAllMediaByDefault();

    /**
     * 获取指定目录下的所有文件
     *
     * @return 文件列表
     */
    List<Media> findAllMediaByDefaultWithCategory(@Param("category") String category);

    /**
     * 按排序规则获取特权文件列表
     *
     * @param order 排序
     * @return 文件列表
     */

    List<Media> findAllMediaByOrder(@Param("order") String order);

    /**
     * 获取指定文件夹下的所有文件
     *
     * @param category 文件夹名
     * @return 文件列表
     */

    List<Media> findAllMediaByOrderWithCategory(@Param("category") String category, @Param("order") String order);

    /**
     * 获取指定文件夹下的所有文件
     *
     * @param category 文件夹名
     * @return 文件列表
     */

    List<Media> getByCategory(@Param("category") String category);
//
//    /**
//     * 通过顶层文件夹名获取该文件夹下的所有文件
//     *
//     * @param topCategory 顶层文件夹名
//     * @return 文件列表
//     */
//    List<Media> getByTopCategory(@Param("topCategory") String topCategory);

    /**
     * 获取文件列表，默认最新最热排序
     */

    List<Media> getMediaWithPaginationByDefault(@Param("mediaIds") Integer[] mediaIds);

    /**
     * 获取文件列表，并排序
     *
     * @param mediaIds
     * @param order
     * @return
     */
    List<Media> getMediaWithPaginationByOrder(@Param("mediaIds") Integer[] mediaIds, @Param("order") String order);

    /**
     * 对相关资源分页
     * @param mediaIds
     * @return
     */

    List<Media> getResourcesMediaListWithPagination(@Param("mediaIds") Integer[] mediaIds);


    /**
     * 浏览量加1
     *
     * @param mediaId 文件id
     */
    void plusViewNum(@Param("mediaId") Integer mediaId);

    /**
     * 下载量加1
     *
     * @param mediaId 文件id
     */
    void plusDownloadNum(@Param("mediaId") Integer mediaId);


    /**
     * 文件重命名
     */

    int rename(@Param("mediaId") Integer mediaId, @Param("afterName") String afterName);

    /**
     * 通过路径删除文件
     *
     * @param mediaId 文件id
     * @return 删除量
     */
    int delete(@Param("mediaId") Integer mediaId);

    /**
     * 获取置顶数
     *
     * @return 置顶数
     */
    int getPinNum();

    /**
     * 获取文件总数
     *
     * @return 文件总数
     */

    int getMediaNum();

    /**
     * 更新文件信息
     *
     * @param mediaId    文件id
     * @param mediaName  文件名
     * @param authority  安全等级
     * @param owner      owner
     * @param pin        是否置顶
     * @param createTime 创建时间
     * @return 更新量
     */
    int update(@Param("mediaId") Integer mediaId, @Param("mediaName") String mediaName,
               @Param("authority") String authority, @Param("owner") String owner,
               @Param("pin") Integer pin, @Param("createTime") Date createTime);


    /**
     * 通过路径判断文件是否存在
     *
     * @param path 文件路径
     * @return 是否存在
     */
    boolean isPathExist(@Param("path") String path);

    /**
     * 通过文件夹名判断文件夹是否存在
     *
     * @param category 文件夹名
     * @return 是否存在
     */
    int isCategoryExist(@Param("category") String category);

    /**
     * 通过文件夹名判断文件夹是否存在
     *
     * @param topCategory 顶层文件夹名
     * @return 是否存在
     */

    int isTopCategoryExist(@Param("topCategory") String topCategory);

    /**
     * 设置置顶周期，在置顶表中添加记录
     *
     * @param mediaId 文件id
     * @param endDate 置顶结束日期
     */
    void setPin(@Param("mediaId") Integer mediaId, @Param("endDate") Date endDate);

    /**
     * 删除置顶表中的记录
     *
     * @param mediaId 文件id
     * @return 删除量
     */
    int cancelPin(@Param("mediaId") Integer mediaId);

    /**
     * 更新置顶周期
     *
     * @param mediaId 文件id
     * @param endDate 置顶结束日期
     */
    void updatePinDuration(@Param("mediaId") Integer mediaId, Date endDate);

    /**
     * 获取文件的所有类型
     */
    List<String> getMediaTypes();

    /**
     * 获取folderId
     *
     * @param category
     * @param folderName
     * @return
     */

    Integer getFolderId(@Param("category") String category, @Param("folderName") String folderName);

    /**
     * 获取folderId
     *
     * @param folderId
     * @return
     */
    Folder getFolderById(@Param("folderId") Integer folderId);

    /**
     * 删除数据库中文件夹表中的信息
     *
     * @param folderId
     * @return
     */
    int deleteFolder(@Param("folderId") Integer folderId);

    /**
     * 获取所有foler对象
     */
    List<Folder> getAllFolders(@Param("order") String order);

    /**
     * 获取某个顶层文件夹下的所有文件夹
     *
     * @param category
     */
    List<Folder> getCertainFolders(@Param("category") String category);

    /**
     * 获取指定安全等级的label相同的media
     * @param labelIds
     * @return
     */

    List<Media> getMediaWithSameLabels(@Param("labelIds") Integer[] labelIds, @Param("authority") String authority);

    /**
     * 获取所有label相同的media
     * @param labelIds
     * @return
     */
    List<Media> getAllMediaWithSameLabels(@Param("labelIds") Integer[] labelIds);
}
