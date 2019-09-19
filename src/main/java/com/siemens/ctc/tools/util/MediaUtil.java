package com.siemens.ctc.tools.util;

import com.github.pagehelper.PageInfo;
import com.siemens.ctc.model.Label;
import com.siemens.ctc.model.Media;
import com.siemens.ctc.service.LabelService;
import com.siemens.ctc.service.MediaService;
import com.siemens.ctc.tools.exception.BusinessException;
import com.siemens.ctc.tools.exception.ErrorCodeEnum;
import joptsimple.internal.Strings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.File;
import java.util.*;

@Component
public class MediaUtil {

    private static final Logger LOGGER = LogManager.getLogger(MediaUtil.class);

    @Resource
    private LabelService labelService;

    @Resource
    private MediaService mediaService;

    private List<Media> mediaList;

    public void setMediaList(List<Media> mediaList) {
        this.mediaList = mediaList;
    }

    /**
     * 排序
     * order为空，则按照默认（最新最热）排序
     * order为view_num按照最热排序，为create_time按照最新排序
     *
     * @param category  文件夹 包括all，顶层目录和二级目录
     * @param authority 安全等级
     * @param order     排序规则
     */
//    public List<Media> getMediaList(String category, String authority, String order) {
//        mediaList = mediaService.getMedia(category, authority, order);
//        if (mediaList == null) {
//            LOGGER.info("文件列表为空");
//            throw new BusinessException(ErrorCodeEnum.MEDIA_NULL.getErrorCode(), ErrorCodeEnum.MEDIA_NULL.getMsg());
//        }
//        return mediaList;
//    }

//    /**
//     * 检索
//     */
//    public List<Media> search(List<Media> mediaList, String labels) {
//        LOGGER.info("标签检索");
//
//        if (labels.equals("all")) {
//            return mediaList;
//        }
//
//        List<String> labelList = Arrays.asList(labels.split(","));
//        String firstLabel = labelList.get(0);
//        LOGGER.info("第1次用" + firstLabel + "检索");
//
//        for (int i = 1; i < labelList.size(); i++) {
//            String labelName = labelList.get(i);
//            for (int j = 0; j < mediaList.size(); j++) {
//                LOGGER.info("第" + (i + 1) + "次用" + labelName + "检索");
//                if (!(StringUtil.labelList2String(labelService.getLabels(mediaList.get(j).getId())).contains(labelName))) {
//                    mediaList.remove(mediaList.get(j));
//                    j--;
//                }
//            }
//        }
//        return mediaList;
//    }

    /**
     * 标签过滤
     */

    private List<Media> labelFilter(List<Media> mediaList, String labels) {

        LOGGER.info(mediaList);
        if (Strings.isNullOrEmpty(labels) || labels.equals("all")) return mediaList;

        List<String> labelList = Arrays.asList(labels.split(","));
        String firstLabel = labelList.get(0);
        LOGGER.info("第1次用" + firstLabel + "检索");

        for (int i = 0; i < labelList.size(); i++) {
            String labelName = labelList.get(i);
            for (int j = 0; j < mediaList.size(); j++) {
                LOGGER.info("第" + (i + 1) + "次用" + labelName + "检索");
                if (!(StringUtil.labelList2String(labelService.getLabels(mediaList.get(j).getId())).contains(labelName))) {
                    mediaList.remove(mediaList.get(j));
                    j--;
                }
            }
        }
        return mediaList;
    }

    /**
     * 时间段过滤
     * period: yyyy-MM-dd HH:mm:ss, yyyy-MM-dd HH:mm:ss
     */

    private List<Media> periodFilter(List<Media> mediaList, String period) {

        if (Strings.isNullOrEmpty(period)) return mediaList;

        List<Media> mediaListCopy = new ArrayList<>();

//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Calendar calendar = Calendar.getInstance();

        Date today = new Date();

        switch (period) {
            case "oneweek": // 过去一周
                calendar.setTime(today);
                calendar.add(Calendar.WEEK_OF_MONTH, -1);
                Date oneweekago = calendar.getTime();
                for (Media media : mediaList) {
                    Date createTime = media.getCreateTime();
                    if (createTime.after(oneweekago))
                        mediaListCopy.add(media);
                }
                break;
            case "twoweeks": // 过去两周
                calendar.setTime(today);
                calendar.add(Calendar.WEEK_OF_MONTH, -2);
                Date twoweeksago = calendar.getTime();
                for (Media media : mediaList) {
                    Date createTime = media.getCreateTime();
                    if (createTime.after(twoweeksago)) {
                        mediaListCopy.add(media);
                    }
                }
                break;
            case "onemonth": // 过去一个月
                calendar.setTime(today);
                calendar.add(Calendar.MONTH, -1);
                Date onemonthago = calendar.getTime();
                for (Media media : mediaList) {
                    Date createTime = media.getCreateTime();
                    if (createTime.after(onemonthago)) {
                        mediaListCopy.add(media);
                    }
                }
                break;
            case "halfyear": // 过去半年
                calendar.setTime(today);
                calendar.add(Calendar.MONTH, -6);
                Date halfyearago = calendar.getTime();
                for (Media media : mediaList) {
                    Date createTime = media.getCreateTime();
                    if (createTime.after(halfyearago)) {
                        mediaListCopy.add(media);
                    }
                }
                break;
        }

//
//        Date startDate = new Date(); // today
//        Date endDate = new Date();
//
//        String[] periodArr = period.split(",");
//        try {
//            String start = periodArr[0];
//            startDate = dateFormat.parse(start);
//            LOGGER.info("start: " + startDate);
//
//            String end = periodArr[1];
//            endDate = dateFormat.parse(end);
//            LOGGER.info("end: " + endDate);
//        } catch (ParseException e) {
//            LOGGER.error(e.getMessage());
//        }
//
//        for (Media media : mediaList) {
//            Date createdTime = media.getCreateTime();
//            if (createdTime.after(startDate) && createdTime.before(endDate)) {
//                LOGGER.info(media.getMediaName() + " 的创建时间: " + createdTime);
//                mediaListCopy.add(media);
//            }
//        }
        return mediaListCopy;
    }

    /**
     * 文件类型过滤
     */

    private List<Media> typeFilter(List<Media> mediaList, String type) {

        if (Strings.isNullOrEmpty(type)) return mediaList;

        List<Media> mediaListCopy = new ArrayList<>();

        for (Media media : mediaList) {
            if (media.getType().equals(type)) {
                mediaListCopy.add(media);
            }
        }
        return mediaListCopy;
    }

    /**
     * 搜索过滤 文件名
     */

    private List<Media> searchFilter(List<Media> mediaList, String search) {
        if (Strings.isNullOrEmpty(search)) return mediaList;

        List<Media> mediaListCopy = new ArrayList<>();
        // mediaName 搜索
        for (Media media : mediaList) {
            if (media.getMediaName().contains(search)) {
                mediaListCopy.add(media);
            }
        }
        return mediaListCopy;
    }

    /**
     * 过滤 同一目录
     */

    private List<Media> folderFilter(List<Media> mediaList, String folder) {
        if (Strings.isNullOrEmpty(folder)) return mediaList;

        List<Media> mediaListCopy = new ArrayList<>();
        for (Media media : mediaList) {
            if (media.getCategory().equals(folder)) {
                mediaListCopy.add(media);
            }
        }
        return mediaListCopy;
    }

    /**
     * 过滤 同一category
     */

    private List<Media> categoryFilter(List<Media> mediaList, String topCategory) {
        if (Strings.isNullOrEmpty(topCategory)) return mediaList;

        List<Media> mediaListCopy = new ArrayList<>();

        for (Media media : mediaList) {
            String category = media.getCategory().substring(0, media.getCategory().indexOf(File.separator));
            LOGGER.info(category);
            if (category.equals(topCategory)) {
                mediaListCopy.add(media);
            }
        }
        return mediaListCopy;
    }

    /**
     * 过滤 同一名字
     */

    private List<Media> mediaNameFilter(List<Media> mediaList, String mediaName) {
        if (Strings.isNullOrEmpty(mediaName)) return mediaList;
        List<Media> mediaListCopy = new ArrayList<>();
        for (Media media : mediaList) {
            if (media.getMediaName().equals(mediaName)) {
                mediaListCopy.add(media);
            }
        }
        return mediaListCopy;
    }

    /**
     * 瀑布流中的过滤链
     */

    public List<Media> filterChain(String labels, String period, String type, String search) {
        List<Media> filterMediaList;
        filterMediaList = labelFilter(mediaList, labels);
        filterMediaList = periodFilter(filterMediaList, period);
        filterMediaList = typeFilter(filterMediaList, type);
        filterMediaList = searchFilter(filterMediaList, search);
        return filterMediaList;
    }

    /**
     * 相关资源推荐中的过滤链
     */
    public List<Media> filterChainInResources(String labels, String folder, String category, String mediaName) {
        List<Media> filterMediaList;
        filterMediaList = labelFilter(mediaList, labels);
        filterMediaList = folderFilter(filterMediaList, folder);
        filterMediaList = categoryFilter(filterMediaList, category);
        filterMediaList = mediaNameFilter(filterMediaList, mediaName);

        return filterMediaList;

    }

    /**
     * 给文件添加标签对象
     */
    public List<Media> addLabels(List<Media> mediaList) {
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
     * 对过滤后的文件进行分页
     */

    public PageInfo<Media> getMediaListWithPagination(int pageNum, int pageSize, List<Media> mediaList, String order) {
        List<Integer> mediaIdList = new ArrayList<>();
        PageInfo<Media> mediaPage;

        if (mediaList.size() == 0) {
            LOGGER.info("没有文件");
            throw new BusinessException(ErrorCodeEnum.MEDIA_NONE.getErrorCode(), ErrorCodeEnum.MEDIA_NONE.getMsg());
        }
        for (Media media : mediaList) {
            Integer mediaId = media.getId();
            mediaIdList.add(mediaId);
        }
        Integer[] mediaIds = new Integer[mediaIdList.size()];
        mediaIdList.toArray(mediaIds);
        if (order.equals("default")) {
            mediaPage = mediaService.getMediaWithPaginationByDefault(pageNum, pageSize, mediaIds);
        } else {
            mediaPage = mediaService.getMediaWithPaginationByOrder(pageNum, pageSize, mediaIds, order);
        }
        List<Media> pageMediaList = mediaPage.getList();
        List<Media> mediaListWithLabel = this.addLabels(pageMediaList);
        mediaPage.setList(mediaListWithLabel);
        return mediaPage;
    }

    /**
     * 对获取的相关资源进行分页
     *
     * @param pageNum
     * @param pageSize
     * @param mediaList
     * @return
     */
    public PageInfo<Media> getResourcesMediaListWithPagination(Integer pageNum, Integer pageSize, List<Media> mediaList) {
        List<Integer> mediaIdList = new ArrayList<>();
        PageInfo<Media> mediaPage;

        if (mediaList.size() == 0) {
            LOGGER.info("没有相关资源");
            throw new BusinessException(ErrorCodeEnum.NONE_RELATIVE_RESOURCES.getErrorCode(), ErrorCodeEnum.NONE_RELATIVE_RESOURCES.getMsg());
        }
        for (Media media : mediaList) {
            Integer mediaId = media.getId();
            mediaIdList.add(mediaId);
        }
        Integer[] mediaIds = new Integer[mediaIdList.size()];
        mediaIdList.toArray(mediaIds);
        mediaPage = mediaService.getResourcesMediaListWithPagination(pageNum, pageSize, mediaIds);
        List<Media> pageMediaList = mediaPage.getList();
        List<Media> mediaListWithLabel = this.addLabels(pageMediaList);
        mediaPage.setList(mediaListWithLabel);
        return mediaPage;
    }

    public List<Media> getResourcesMediaList(List<Label> labels, String category, String authority) {

        List<Media> mediaListWithSameLabel = mediaService.getMediaWithSameLabels(labels, authority);
        List<Media> mediaListWithSameCategory = mediaService.getMedia(category, authority);
        mediaListWithSameLabel.addAll(mediaListWithSameCategory);
        return mediaListWithSameLabel;
    }

    public List<Media> getResourcesMediaList(List<Label> labels, String category) {

        List<Media> mediaListWithSameLabel = mediaService.getAllMediaWithSameLabels(labels);
        List<Media> mediaListWithSameCategory = mediaService.getMediaByCategory(category);
        mediaListWithSameLabel.addAll(mediaListWithSameCategory);
        return mediaListWithSameLabel;
    }


//        List<Media> filterMediaList;
//        List<Media> resourcesMediaList = new LinkedList<>();
//        List<Media> leftMediaList;
//
//        // 获取标签相同的文件，标签匹配从多到少
////        this.setMediaList(mediaList);
//        LOGGER.info("####################标签查找前，mediaList = " + mediaList);
//
//        filterMediaList = this.filterChainInResources(labels, null, null, null);
//        LOGGER.info("####################标签查找后，filterMediaList = " + filterMediaList);
//
//        resourcesMediaList.addAll(filterMediaList);
//
//        leftMediaList = this.removeMediaList(mediaList, filterMediaList);
////        mediaList.removeAll(filterMediaList);
//        LOGGER.info("#############标签查找后，leftMediaList = " + leftMediaList);
//
//
//        this.setMediaList(mediaList);
//        LOGGER.info("####################标签查找后，mediaList = " + mediaList);
//
//        if (mediaList.size() == 0) {
//            LOGGER.info("################已获取全部相关资源");
//            return resourcesMediaList;
//        }

//        // 获取同一文件夹下的文件
////        this.setMediaList(mediaList);
//        LOGGER.info("#################文件夹查找前, mediaList = " + mediaList);
//        filterMediaList = this.filterChainInResources(null, folder, null, null);
//        LOGGER.info("#################文件夹查找后，filterMediaList = " + filterMediaList);
//        resourcesMediaList.addAll(filterMediaList);
////        mediaList.removeAll(filterMediaList);
//        leftMediaList = this.removeMediaList(mediaList, filterMediaList);
//        this.setMediaList(leftMediaList);
//        LOGGER.info("###################文件夹查找后，mediaList = " + mediaList);
//        if (leftMediaList.size() == 0) {
//            LOGGER.info("##############已获取全部相关资源");
//            return resourcesMediaList;
//        }
//
//        // 获取同一category下的文件
////        this.setMediaList(mediaList);
//        filterMediaList = this.filterChainInResources(null, null, category, null);
//        resourcesMediaList.addAll(filterMediaList);
////        mediaList.removeAll(filterMediaList);
//        leftMediaList = this.removeMediaList(mediaList, filterMediaList);
//        this.setMediaList(leftMediaList);
//        if (leftMediaList.size() == 0) {
//            return resourcesMediaList;
//        }
//
//        // 获取同名文件
////        this.setMediaList(mediaList);
//        filterMediaList = this.filterChainInResources(null, null, null, mediaName);
//        resourcesMediaList.addAll(filterMediaList);
//        leftMediaList = this.removeMediaList(mediaList, filterMediaList);
//        this.setMediaList(leftMediaList);
////        mediaList.removeAll(filterMediaList);
//        if (leftMediaList.size() == 0) {
//            return resourcesMediaList;
//        }
//
//        // 剩下标签不同，不在同一文件夹，不在同一category，不同名的文件
//        resourcesMediaList.addAll(mediaList);
//        LOGGER.info("################resources mediaList = " + resourcesMediaList);
//        return resourcesMediaList;

    private List<Media> removeMediaList(List<Media> mediaList, List<Media> filterMediaList) {
        for (Media filterMedia : filterMediaList) {
            mediaList = this.removeMedia(mediaList, filterMedia);
        }
        return mediaList;
    }

    // 不能重写equal和hashcode，因为标签检索的时候，已经制定了把指定media从media list中remove的规则，所以remove不是根据mediaId是否相等
    // 必须自定义方法，把mediaId相等的从media list中删除
    public List<Media> removeMedia(List<Media> mediaList, Media media) {
        mediaList.removeIf(itselfMedia -> itselfMedia.getId().equals(media.getId()));
        return mediaList;
    }
}