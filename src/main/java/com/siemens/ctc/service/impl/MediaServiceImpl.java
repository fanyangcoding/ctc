package com.siemens.ctc.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.siemens.ctc.dao.MediaMapper;
import com.siemens.ctc.model.Folder;
import com.siemens.ctc.model.Label;
import com.siemens.ctc.model.Media;
import com.siemens.ctc.service.LabelService;
import com.siemens.ctc.service.MediaService;
import com.siemens.ctc.tools.exception.BusinessException;
import com.siemens.ctc.tools.exception.ErrorCodeEnum;
import com.siemens.ctc.tools.util.MediaUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author Fan Yang
 * <p>
 * createTime: 2019-08-07
 */

@Service
public class MediaServiceImpl implements MediaService {
    private static final Logger LOGGER = LogManager.getLogger(MediaServiceImpl.class);
    @Resource
    private MediaMapper mediaMapper;
    @Resource
    private LabelService labelService;
    @Resource
    private MediaUtil mediaUtil;

    /**
     * 上传文件
     *
     * @param media 文件对象
     * @return media id
     */

    @Override
    public Integer upload(Media media) {
        if (media == null) {
            throw new BusinessException(ErrorCodeEnum.MEDIA_NULL.getErrorCode(), ErrorCodeEnum.MEDIA_NULL.getMsg());
        }
        int count = mediaMapper.upload(media);
        Integer mediaId = media.getId();
        if (count == 0) {
            LOGGER.error(media.getMediaName() + "数据库上传失败");
            throw new BusinessException(ErrorCodeEnum.UPLOAD_FAILURE.getErrorCode(), ErrorCodeEnum.UPLOAD_FAILURE.getMsg());
        } else
            LOGGER.info(media.getMediaName() + "数据库上传成功");
        return mediaId;
    }


    /**
     * 上传文件夹信息
     */

    @Override
    public Integer uploadFolder(Folder folder) {
        if (folder == null) {
            throw new BusinessException(ErrorCodeEnum.FOLDER_NONE.getErrorCode(), ErrorCodeEnum.FOLDER_NONE.getMsg());
        }
        int count = mediaMapper.uploadFolder(folder);
        if (count == 0) {
            LOGGER.error(folder.getFolderName() + "数据库上传失败");
            throw new BusinessException(ErrorCodeEnum.UPLOAD_FAILURE.getErrorCode(), ErrorCodeEnum.UPLOAD_FAILURE.getMsg());
        } else
            LOGGER.info(folder.getFolderName() + "数据库上传成功");
        return count;
    }

    /**
     * 更新文件夹信息
     */

    @Override
    public Integer updateFolder(Integer folderId, Date updateTime) {
        int count = mediaMapper.updateFolder(folderId, updateTime);
        if (count == 0) {
            LOGGER.error("更新时间失败");
            throw new BusinessException(ErrorCodeEnum.UPDATE_TIME_FAILURE.getErrorCode(), ErrorCodeEnum.UPDATE_TIME_FAILURE.getMsg());
        }
        return count;
    }

    /**
     * 获取指定category下指定安全等级的文件
     */

    @Override
    public List<Media> getMedia(String category, String authority) {
        return mediaMapper.getMedia(category, authority);
    }

    /**
     * 获取全部文件
     */
    @Override
    public List<Media> getAllMedia() {
        return mediaMapper.getAllMedia();
    }

    /**
     * 获取category下所有的文件
     */
    @Override
    public List<Media> getMediaByCategory(String category) {
        return mediaMapper.getMediaByCategory(category);
    }

    /**
     * 获取指定安全等级的所有文件
     */
    @Override
    public List<Media> getMediaByAuthority(String authority) {
        return mediaMapper.getMediaByAuthority(authority);
    }


    //    /**
//     * 获取非特权文件
//     *
//     * @return Media List
//     * @see com.siemens.ctc.model.Media
//     */
//
//    @Override
//    public List<Media> findNoneRestrictMediaByDefault(String category) {
//        if (category.equals("all")) {
//            List<Media> mediaList = mediaMapper.findNoneRestrictMediaByDefault();
//        } else {
//            List<Media> mediaList = mediaMapper.findNoneRestrictMediaByDefault(category);
//        }
//
//        List<Media> mediaListCopy = new ArrayList<>();
//        for (int i = 0; i < mediaList.size(); i++) {
//            Media media = mediaList.get(i);
//            List<Label> labelList = labelService.getLabels(media.getId());
//            media.setLabels(labelList);
//            mediaListCopy.add(media);
//        }
//        return mediaListCopy;
//    }
//
//    /**
//     * 获取非特权文件，并排序
//     *
//     * @param order 排序，最新或最热
//     * @return Media List
//     * @see com.siemens.ctc.model.Media
//     */
//
//    @Override
//    public List<Media> findNoneRestrictMediaByOrder(String order) {
//        List<Media> mediaList = mediaMapper.findNoneRestrictMediaByOrder(order);
//        List<Media> mediaListCopy = new ArrayList<>();
//        for (int i = 0; i < mediaList.size(); i++) {
//            Media media = mediaList.get(i);
//            List<Label> labelList = labelService.getLabels(media.getId());
//            media.setLabels(labelList);
//            mediaListCopy.add(media);
//        }
//        return mediaListCopy;
//    }
//
//    /**
//     * 获取特权文件，并排序
//     *
//     * @param order 排序，最新或最热
//     * @return Media List
//     * @see com.siemens.ctc.model.Media
//     */
//
//    @Override
//    public List<Media> findAllMediaByOrder(String order) {
//        List<Media> mediaList = mediaMapper.findAllMediaByOrder(order);
//        List<Media> mediaListCopy = new ArrayList<>();
//        for (int i = 0; i < mediaList.size(); i++) {
//            Media media = mediaList.get(i);
//            List<Label> labelList = labelService.getLabels(media.getId());
//            media.setLabels(labelList);
//            mediaListCopy.add(media);
//        }
//        return mediaListCopy;
//    }
//
//    /**
//     * 获取所有文件
//     *
//     * @return Media List
//     * @see com.siemens.ctc.model.Media
//     */
//
//    @Override
//    public List<Media> findAllMedia() {
//        List<Media> mediaList = mediaMapper.findAllMedia();
//        List<Media> mediaListCopy = new ArrayList<>();
//        for (int i = 0; i < mediaList.size(); i++) {
//            Media media = mediaList.get(i);
//            List<Label> labelList = labelService.getLabels(media.getId());
//            media.setLabels(labelList);
//            mediaListCopy.add(media);
//        }
//        return mediaListCopy;
//    }
//
//    /**
//     * 获取指定文件夹下的所有文件
//     *
//     * @param category 指定文件夹
//     * @return Media List
//     * @see com.siemens.ctc.model.Media
//     */
//

    /**
     * 获取指定category下的所有文件
     *
     * @param category
     * @return
     */
    @Override
    public List<Media> getByCategory(String category) {
        List<Media> mediaList = mediaMapper.getByCategory(category);
        List<Media> mediaListCopy = new ArrayList<>();
        for (int i = 0; i < mediaList.size(); i++) {
            Media media = mediaList.get(i);
            List<Label> labelList = labelService.getLabels(media.getId());
            media.setLabels(labelList);
            mediaListCopy.add(media);
        }
        return mediaListCopy;
    }

    /**
     * @param pageNum
     * @param pageSize
     * @param mediaIds
     * @return
     */

    @Override
    public PageInfo<Media> getMediaWithPaginationByDefault(int pageNum, int pageSize, Integer[] mediaIds) {
        PageHelper.startPage(pageNum, pageSize);
        List<Media> mediaList = mediaMapper.getMediaWithPaginationByDefault(mediaIds);
        PageInfo<Media> mediaPageInfo = new PageInfo<>(mediaList);
        return mediaPageInfo;
    }

    /**
     * @param pageNum
     * @param pageSize
     * @param mediaIds
     * @param order
     * @return
     */
    @Override
    public PageInfo<Media> getMediaWithPaginationByOrder(int pageNum, int pageSize, Integer[] mediaIds, String order) {
        PageHelper.startPage(pageNum, pageSize);
        List<Media> mediaList = mediaMapper.getMediaWithPaginationByOrder(mediaIds, order);
        PageInfo<Media> mediaPageInfo = new PageInfo<>(mediaList);
        return mediaPageInfo;
    }

    /**
     * 对相关资源分页
     *
     * @param pageNum
     * @param pageSize
     * @param mediaIds
     * @return
     */

    @Override
    public PageInfo<Media> getResourcesMediaListWithPagination(Integer pageNum, Integer pageSize, Integer[] mediaIds) {
        PageHelper.startPage(pageNum, pageSize);
        List<Media> mediaList = mediaMapper.getResourcesMediaListWithPagination(mediaIds);
        PageInfo<Media> mediaPageInfo = new PageInfo<>(mediaList);
        return mediaPageInfo;
    }
//
//    /**
//     * 获取指定顶层文件夹下的所有文件
//     *
//     * @param topCategory 指定的顶层文件夹
//     * @return Media List
//     * @see com.siemens.ctc.model.Media
//     */
////
//    @Override
//    public List<Media> getByTopCategory(String topCategory) {
//        List<Media> mediaList = mediaMapper.getByTopCategory(topCategory);
//        List<Media> mediaListCopy = new ArrayList<>();
//        for (int i = 0; i < mediaList.size(); i++) {
//            Media media = mediaList.get(i);
//            List<Label> labelList = labelService.getLabels(media.getId());
//            media.setLabels(labelList);
//            mediaListCopy.add(media);
//        }
//        return mediaListCopy;
//    }


    /**
     * 通过mediaId获取文件对象
     */

    @Override
    public Media getByMediaId(Integer mediaId) {
        Media media = mediaMapper.getMediaById(mediaId);
        List<Label> labelList = labelService.getLabels(media.getId());
        media.setLabels(labelList);
        return media;
    }

    /**
     * 获取folderId
     *
     * @param category
     * @param folderName
     * @return
     */

    @Override
    public Integer getFolderId(String category, String folderName) {
        int count = mediaMapper.getFolderId(category, folderName);
        return count;
    }

    /**
     * 获取folder对象
     *
     * @param folderId
     * @return
     */

    @Override
    public Folder getFolderById(Integer folderId) {
        return mediaMapper.getFolderById(folderId);
    }

    @Override
    public List<Folder> getAllFolders(String order) {
        return mediaMapper.getAllFolders(order);
    }

    /**
     * 浏览量增加1
     *
     * @param mediaId 文件id
     */
    @Override
    public void plusViewNum(Integer mediaId) {
        mediaMapper.plusViewNum(mediaId);
    }

    /**
     * 下载量增加1
     *
     * @param mediaId 文件id
     */
    @Override
    public void plusDownloadNum(Integer mediaId) {
        mediaMapper.plusDownloadNum(mediaId);
    }

    /**
     * 文件重命名
     *
     * @param mediaId   文件id
     * @param afterName 文件新命名
     */

    @Override
    public int rename(Integer mediaId, String afterName) {
        Media media = mediaMapper.getMediaById(mediaId);
        media.setMediaName(afterName);
        return mediaMapper.rename(mediaId, afterName);
    }

    /**
     * 删除文件
     *
     * @param mediaId 文件id
     */
    @Override
    public int delete(Integer mediaId) {
        //先删除标签
        LOGGER.info("先删除标签");
        List<Label> labelList = labelService.getLabels(mediaId);
        if (labelList != null) {
            for (Label label : labelList) {
                LOGGER.info("删除标签: " + label.getLabelName());
                labelService.deleteLabel(mediaId, label.getLabelName());
            }
        }
        // 判断是否置顶
        Media media = mediaMapper.getMediaById(mediaId);
        if (!media.getPin().equals("unpin")) { // 若文件置顶，需删除置顶表中的记录
            LOGGER.info(mediaId + " 该文件置顶，需先从置顶表中删除");
            mediaMapper.cancelPin(mediaId);
            LOGGER.info("置顶表中删除成功");
        }
        // 删除媒体表的记录
        LOGGER.info(mediaId + " 需从媒体表中删除");
        int count = mediaMapper.delete(mediaId);
        LOGGER.info(mediaId + " 媒体表中删除成功");

        return count;
    }


    /**
     * 编辑文件
     *
     * @param mediaId    文件id
     * @param mediaName  文件名
     * @param authority  安全等级
     * @param owner      owner
     * @param pin        是否置顶
     * @param createTime 上传时间
     * @return 更新量
     */

    @Override
    public int update(Integer mediaId, String mediaName, String authority, String owner, Integer pin, String createTime) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = dateFormat.parse(createTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int count = mediaMapper.update(mediaId, mediaName, authority, owner, pin, date);
        LOGGER.info(mediaName + " 保存成功");
        return count;
    }

    /**
     * 获取当前置顶数
     *
     * @return 获取量
     */

    @Override
    public int getPinNum() {
        int count = mediaMapper.getPinNum();
        return count;
    }

    /**
     * 获取文件总数
     *
     * @return 获取量
     */
    @Override
    public int getMediaNum() {
        int count = mediaMapper.getMediaNum();
        return count;
    }

    /**
     * 判断路径是否存在
     *
     * @param path 文件路径
     * @return 是否存在
     */
    @Override
    public boolean isPathExist(String path) {
        boolean exist = mediaMapper.isPathExist(path);
        return exist;
    }

    /**
     * 判断文件夹是否存在
     *
     * @return 返回存在量
     */
    @Override
    public boolean isCategoryExist(String category) {
        int count = mediaMapper.isCategoryExist(category);
        if (count == 0) {
            LOGGER.error("没有该文件夹");
            return false;
        }
        return true;
    }

    /**
     * 判断顶层文件夹是否存在
     *
     * @param topCategory 顶层文件夹
     * @return
     */
    @Override
    public boolean isTopCategoryExist(String topCategory) {
        int count = mediaMapper.isTopCategoryExist(topCategory);
        if (count == 0) {
            LOGGER.error("没有该顶层文件夹");
            return false;
        }
        return true;
    }

    /**
     * 设置置顶周期
     *
     * @param mediaId 文件id
     * @param pin     字符串，必须为：one week，two weeks，one month，中的一个
     */
    @Override
    public void setPin(Integer mediaId, Integer pin) {
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        switch (pin) {
            case 1: //
                LOGGER.info("置顶周期设置为1周");
                Calendar oneWeekCal = Calendar.getInstance();
                oneWeekCal.set(Calendar.WEEK_OF_MONTH, oneWeekCal.get(Calendar.WEEK_OF_MONTH) + 1);
                Date oneWeekEndDate = oneWeekCal.getTime();
//                String oneWeekEndDateStr = dateFormat.format(oneWeekCal.getTime());
                mediaMapper.setPin(mediaId, oneWeekEndDate);
                break;
            case 2:
                LOGGER.info("置顶周期设置为2周");
                Calendar twoWeeksCal = Calendar.getInstance();
                twoWeeksCal.set(Calendar.WEEK_OF_MONTH, twoWeeksCal.get(Calendar.WEEK_OF_MONTH) + 2);
                Date twoWeekEndDate = twoWeeksCal.getTime();
//                String twoWeekEndDateStr = dateFormat.format(twoWeeksCal.getTime());
                mediaMapper.setPin(mediaId, twoWeekEndDate);
                break;
            case 4:
                LOGGER.info("置顶周期设置为1个月");
                Calendar oneMonthCal = Calendar.getInstance();
                oneMonthCal.set(Calendar.MONTH, oneMonthCal.get(Calendar.MONTH) + 1);
                Date oneMonthEndDate = oneMonthCal.getTime();
//                String oneMonthEndDateStr = dateFormat.format(oneMonthCal.getTime());
                mediaMapper.setPin(mediaId, oneMonthEndDate);
                break;
        }
    }

    /**
     * 取消置顶，从置顶表中删除记录
     *
     * @param mediaId 文件id
     */
    @Override
    public int cancelPin(Integer mediaId) {
        int count = mediaMapper.cancelPin(mediaId);
        return count;
    }

    /**
     * 更新置顶周期
     *
     * @param mediaId 文件id
     * @param pin     新周期，必须为：one week，two weeks，one month 中的一个
     */
    @Override
    public void updatePinDuration(Integer mediaId, Integer pin) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        switch (pin) {
            case 1:
                LOGGER.info("置顶周期更新为1周");
                Calendar oneWeekCal = Calendar.getInstance();
                oneWeekCal.set(Calendar.WEEK_OF_MONTH, oneWeekCal.get(Calendar.WEEK_OF_MONTH) + 1);
                Date oneWeekEndDate = oneWeekCal.getTime();
//                String oneWeekEndDateStr = dateFormat.format(oneWeekCal.getTime());
                mediaMapper.updatePinDuration(mediaId, oneWeekEndDate);
                break;
            case 2:
                LOGGER.info("置顶周期更新为2周");
                Calendar twoWeeksCal = Calendar.getInstance();
                twoWeeksCal.set(Calendar.WEEK_OF_MONTH, twoWeeksCal.get(Calendar.WEEK_OF_MONTH) + 2);
                Date twoWeekEndDate = twoWeeksCal.getTime();
//                String twoWeekEndDateStr = dateFormat.format(twoWeeksCal.getTime());
                mediaMapper.updatePinDuration(mediaId, twoWeekEndDate);
                break;
            case 4:
                LOGGER.info("置顶周期更新为1个月");
                Calendar oneMonthCal = Calendar.getInstance();
                oneMonthCal.set(Calendar.MONTH, oneMonthCal.get(Calendar.MONTH) + 1);
                Date oneMonthEndDate = oneMonthCal.getTime();
//                String oneMonthEndDateStr = dateFormat.format(oneMonthCal.getTime());
                mediaMapper.updatePinDuration(mediaId, oneMonthEndDate);
                break;
        }
    }

    /**
     * 获取文件类型
     */
    @Override
    public List<String> getMediaTypes() {
        return mediaMapper.getMediaTypes();
    }

    /**
     * 删除数据库中的文件夹信息
     *
     * @param folderId
     * @return
     */
    @Override
    public Integer deleteFolder(Integer folderId) {
        int count = mediaMapper.deleteFolder(folderId);
        return count;
    }

    /**
     * 获取某个顶层文件夹下所有的文件夹
     *
     * @param category
     * @return
     */

    @Override
    public List<Folder> getCertainFolders(String category) {
        return mediaMapper.getCertainFolders(category);
    }

    /**
     * 获取指定安全等级的label相同的media
     *
     * @param labelList
     * @return
     */
    @Override
    public List<Media> getMediaWithSameLabels(List<Label> labelList, String authority) {
        List<Integer> labelIdList = new ArrayList<>();
        for (Label label : labelList) {
            Integer labelId = label.getId();
            labelIdList.add(labelId);
        }
        Integer[] labelIds = new Integer[labelList.size()];
        labelIdList.toArray(labelIds);

        return mediaMapper.getMediaWithSameLabels(labelIds, authority);
    }

    /**
     * 获取所有lebel相同的media
     * @param labelList
     * @return
     */

    @Override
    public List<Media> getAllMediaWithSameLabels(List<Label> labelList) {
        List<Integer> labelIdList = new ArrayList<>();
        for (Label label : labelList) {
            Integer labelId = label.getId();
            labelIdList.add(labelId);
        }
        Integer[] labelIds = new Integer[labelList.size()];
        labelIdList.toArray(labelIds);

        return mediaMapper.getAllMediaWithSameLabels(labelIds);
    }
}
