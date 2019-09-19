package com.siemens.ctc.service;

import com.github.pagehelper.PageInfo;
import com.siemens.ctc.model.Folder;
import com.siemens.ctc.model.Label;
import com.siemens.ctc.model.Media;

import java.util.Date;
import java.util.List;

/**
 * @author Fan Yang
 * <p>
 * createTime：2019-08-05
 */

public interface MediaService {

    /**
     * 添加文件信息
     *
     * @param media 文件
     */

    Integer upload(Media media);

    /**
     * 添加文件夹信息
     */

    Integer uploadFolder(Folder folder);

    /**
     * 更新数据库中sys_folder中的last_update_time字段
     *
     * @param folderId   folder id
     * @param updateTime 更新时间
     * @return
     */

    Integer updateFolder(Integer folderId, Date updateTime);

    /**
     * 通过id获取文件对象
     */

    Media getByMediaId(Integer mediaId);

    /**
     * 获取folderId
     */
    Integer getFolderId(String category, String folderName);

    /**
     * 获取folder对象
     */
    Folder getFolderById(Integer folderId);

    /**
     * 获取所有文件夹
     */
    List<Folder> getAllFolders(String order);

    /**
     * 获取指定category下指定安全等级的文件
     *
     * @param category  目录
     * @param authority 安全等级
     */
    List<Media> getMedia(String category, String authority);

    /**
     * 获取指定安全等级的文件
     */
    List<Media> getMediaByAuthority(String authority);

    /**
     * 获取指定category下的所有文件
     */
    List<Media> getMediaByCategory(String category);

    /**
     * 获取所有文件
     */
    List<Media> getAllMedia();

//    /**
//     * 获取全部的非特权文件，默认最新最热排序
//     *
//     * @return Media List
//     */
//
//    List<Media> findAllNoneRestrictMediaByDefault();
//
//    /**
//     * 获取指定目录下的非特权文件，默认最新最热排序
//     *
//     * @return Media List
//     */
//
//    List<Media> findMediaByDefault(String category);
//
//    /**
//     * 获取非特权文件，根据order排序
//     *
//     * @param order 排序，最新或最热
//     * @return Media List
//     */
//
//    List<Media> findNoneRestrictMediaByOrder(String category, String order);
//
//    /**
//     * 获取特权文件，根据order排序
//     *
//     * @param order 排序，最新或最热
//     * @return Media List
//     */
//
//    List<Media> findAllMediaByOrder(String category, String order);
//
//    /**
//     * 获取所有文件
//     *
//     * @return Media List
//     */
//
//    List<Media> findAllMedia(String category);


    /**
     * @param category 获取指定文件夹下的所有文件
     * @return Media List
     */

    List<Media> getByCategory(String category);
//
//    /**
//     * @param topCategory 获取指定顶层文件夹下的所有文件
//     * @return Media List
//     */
//
//    List<Media> getByTopCategory(String topCategory);
////    PageInfo<Media> getByTopCategory(int pageNum, int pageSize, String topCategory);

    /**
     * 对默认排序的文件分页
     */
    PageInfo<Media> getMediaWithPaginationByDefault(int pageNum, int pageSize, Integer[] mediaIds);

    /**
     * 对order排序的文件分页
     */

    PageInfo<Media> getMediaWithPaginationByOrder(int pageNum, int pageSize, Integer[] mediaIds, String order);

    /**
     * 对相关资源分页
     *
     * @param pageNum
     * @param pageSize
     * @param mediaIds
     * @return
     */
    PageInfo<Media> getResourcesMediaListWithPagination(Integer pageNum, Integer pageSize, Integer[] mediaIds);

    /**
     * 浏览量加1
     *
     * @param mediaId 文件id
     */
    void plusViewNum(Integer mediaId);

    /**
     * 下载量加1
     *
     * @param mediaId 文件id
     */

    void plusDownloadNum(Integer mediaId);

    /**
     * 文件重命名
     */

    int rename(Integer mediaId, String afterName);

    /**
     * 删除文件
     */

    int delete(Integer mediaId);

    /**
     * 更新文件
     *
     * @param mediaId    文件id
     * @param mediaName  文件名
     * @param authority  安全等级
     * @param owner      owner
     * @param pin        是否置顶
     * @param createTime 上传时间
     * @return 更新数
     */

    int update(Integer mediaId, String mediaName,
               String authority, String owner, Integer pin, String createTime);


    /**
     * 获取当前置顶数
     *
     * @return 当前置顶数
     */
    int getPinNum();

    /**
     * 获取文件总数
     *
     * @return 文件数量
     */

    int getMediaNum();

    /**
     * 判断文件是否存在
     *
     * @param path 文件路径
     * @return 是否存在
     */
    boolean isPathExist(String path);

    /**
     * 判断文件夹是否存在
     */

    boolean isCategoryExist(String category);

    /**
     * 判断顶层文件夹是否存在
     */

    boolean isTopCategoryExist(String topCategory);

    /**
     * 设置置顶周期(one week, two weeks, one month, unpin)
     * 只能是上面四个中的一个
     */
    void setPin(Integer mediaId, Integer duration);

    /**
     * 删除置顶表中的置顶记录
     */
    int cancelPin(Integer mediaId);

    /**
     * 更新置顶周期
     */
    void updatePinDuration(Integer mediaId, Integer duration);

    /**
     * 获取文件的所有类型
     */
    List<String> getMediaTypes();

    /**
     * 删除数据库中文件夹信息
     *
     * @param folderId
     * @return
     */
    Integer deleteFolder(Integer folderId);

    /**
     * 获取某个顶层文件夹下的所有文件夹
     *
     * @param category
     * @return
     */
    List<Folder> getCertainFolders(String category);

    /**
     * 获取指定安全等级的label相同的media
     */
    List<Media> getMediaWithSameLabels(List<Label> labelList, String authority);

    /**
     * 获取所有label相同的media
     */
    List<Media> getAllMediaWithSameLabels(List<Label> labelList);

}
