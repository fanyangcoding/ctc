package com.siemens.ctc.controller;

import com.siemens.ctc.config.MyPropsConstants;
import com.siemens.ctc.model.Folder;
import com.siemens.ctc.model.Media;
import com.siemens.ctc.service.MediaService;
import com.siemens.ctc.tools.exception.BusinessException;
import com.siemens.ctc.tools.exception.ErrorCodeEnum;
import com.siemens.ctc.tools.result.ResultModel;
import com.siemens.ctc.tools.result.ResultStatus;
import io.swagger.annotations.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Fan Yang
 * createTime：2019-07-22
 */

@RestController
@RequestMapping("/v1/ctc/dir")
@CrossOrigin(origins = "*", maxAge = 3600)
@Api(value = "文件夹管理", tags = "FolderMntController", description = "文件夹管理操作API接口")
public class FolderMntController extends BaseController {
    private static Logger LOGGER = LogManager.getLogger(FolderMntController.class);

    @Resource
    private MyPropsConstants myPropsConstants;

    @Resource
    private MediaService mediaService;

//  ####################################################################################################################

    /**
     * 获取所有文件夹，排序，搜索
     */
    @ApiOperation(value = "获取文件夹", notes = "获取文件夹，all获取所有文件夹, category指定某个顶层文件夹")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "category", value = "all获取所有文件夹，category指定顶层文件夹", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "order", value = "排序，传last_update_time按照最后一次更新时间排序", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "search", value = "文件夹名", dataType = "String", paramType = "query")
    })
    @ApiResponse(code = 200, response = List.class, message = "获取所有文件夹")
    @GetMapping(produces = "application/json")
//    @PreAuthorize("hasRole('admin')")
    public ResultModel<?> getAllFolders(@RequestParam(value = "category", defaultValue = "all") String category,
                                        @RequestParam(value = "order", required = false) String order,
                                        @RequestParam(value = "search", required = false) String search) {
        // 获取所有文件夹
        LOGGER.info("获取所有文件夹");
        if (category.equals("all")) {
            List<Folder> folderList = mediaService.getAllFolders(order);
            if (search != null) {
                List<Folder> folderListCopy = new ArrayList<>();
                for (Folder folder : folderList) {
                    if (folder.getFolderName().contains(search)) {
                        folderListCopy.add(folder);
                    }
                }
                return new ResultModel<>(ResultStatus.SUCCESS, folderListCopy);
            }
            return new ResultModel<>(ResultStatus.SUCCESS, folderList);
        } else { // 获取指定某个顶层文件夹下的所有文件夹
            LOGGER.info("获取某个顶层文件夹下的所有文件夹");
            List<Folder> certainFolderList = mediaService.getCertainFolders(category);
            if (search != null) {
                List<Folder> certainFolderListCopy = new ArrayList<>();
                for (Folder folder : certainFolderList) {
                    if (folder.getFolderName().contains(search)) {
                        certainFolderListCopy.add(folder);
                    }
                }
                return new ResultModel<>(ResultStatus.SUCCESS, certainFolderListCopy);
            }
            return new ResultModel<>(ResultStatus.SUCCESS, certainFolderList);
        }


//        List<String> dirList = new ArrayList<>();
//        String uploadBasePath = myPropsConstants.getUploadBasePath();

//        if (Strings.isNullOrEmpty(category) || category.equals("all")) {
//            File[] topCategories = new File(uploadBasePath).listFiles();
//            if (topCategories == null || topCategories.length <= 0) {
//                LOGGER.error("没有任何文件夹.");
//                return new ResultModel<>(ResultStatus.SUCCESS, "没有任何文件夹");
//            }
//            for (File topCategory : topCategories) {
//                if (topCategory.isDirectory()) {
//                    File[] eventCategories = new File(uploadBasePath + topCategory.getName()).listFiles();
////                    LOGGER.info("################" + eventCategory.length);
//                    if (eventCategories == null || eventCategories.length <= 0) {
//                        LOGGER.info("该二级目录下没有任何文件夹");
////                        dirList.add(topCategory.getName());
//                    } else {
//                        for (File eventCategory : eventCategories) {
//                            LOGGER.info("获取文件夹" + eventCategory.getName());
//                            dirList.add(topCategory.getName() + File.separator + eventCategory.getName());
//                        }
//                    }
//                }
//            }
//            LOGGER.info("获取所有文件夹成功");
//            return new ResultModel<>(ResultStatus.SUCCESS, dirList);
//        }

        // 获取指定目录下的文件夹
//        LOGGER.info("获取指定目录下的文件夹");
//        String topCategoryPath = uploadBasePath + category;
//        File[] files = new File(topCategoryPath).listFiles();
//        if (files == null || files.length <= 0) {
//            LOGGER.error("没有任何文件夹.");
////            throw new BusinessException(ErrorCodeEnum.FOLDER_NONE.getErrorCode(), ErrorCodeEnum.FOLDER_NONE.getMsg());
//            return new ResultModel<>(ResultStatus.SUCCESS, "没有任何文件夹");
//        }
//        for (File file : files) {
//            LOGGER.info("文件夹: " + file.getName());
//            dirList.add(file.getName());
//        }
//        LOGGER.info("获取所有文件夹成功");
//        return new ResultModel<>(ResultStatus.SUCCESS, dirList);
    }

//  ####################################################################################################################


    /**
     * 创建文件夹
     */
    @ApiIgnore
    @ApiOperation(value = "创建文件夹", notes = "创建文件夹")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "category", value = "顶层文件夹", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "folderName", value = "文件夹名", required = true, dataType = "String", paramType = "query")
    })
    @PreAuthorize("hasRole('admin')")
    @PostMapping(produces = "application/json")
    public ResultModel<String> createFolder(@RequestParam("category") String category, @RequestParam("folderName") String folderName) {
        LOGGER.info("创建文件夹");
        boolean created;
        File targetFile = new File(myPropsConstants.getUploadBasePath() + category + folderName);
        if (targetFile.exists()) {
            LOGGER.error("文件夹已存在");
            throw new BusinessException(ErrorCodeEnum.FOLDER_EXIST.getErrorCode(), ErrorCodeEnum.FOLDER_EXIST.getMsg());
        } else
            created = targetFile.mkdir();
        if (created) {
            LOGGER.info("文件夹" + category + folderName + "创建成功");
            return new ResultModel<>(ResultStatus.SUCCESS, "文件夹创建成功");
        } else
            return new ResultModel<>(ResultStatus.FAIL, "文件夹创建失败");
    }

    /**
     * 删除文件夹
     */
    @ApiIgnore
    @ApiOperation(value = "删除文件夹", notes = "删除文件夹")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "category", value = "顶层文件夹名", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "folderName", value = "文件夹名", required = true, dataType = "String", paramType = "query")

    })
    @PreAuthorize("hasRole('admin')")
    @DeleteMapping(produces = "application/json")
    public ResultModel<String> deleteFolder(@RequestParam("folderId") Integer folderId) {
        Folder folder = mediaService.getFolderById(folderId);
        String category = folder.getCategory() + File.separator + folder.getFolderName();
        LOGGER.info("删除文件夹" + category);
        List<Media> mediaList;
        File targetCategory = new File(myPropsConstants.getUploadBasePath() + category);
        File targetThumbnailCategory = new File(myPropsConstants.getThumbnailBasePath() + category);

        if (!targetCategory.exists()) {
            LOGGER.error("文件夹不存在");
            throw new BusinessException(ErrorCodeEnum.FOLDER_NOT_EXIST.getErrorCode(), ErrorCodeEnum.FOLDER_NOT_EXIST.getMsg());
        } else {
            // 先删除文件夹表中的信息
            mediaService.deleteFolder(folderId);
            // 再删除数据库中的文件信息，再删除磁盘上的文件和缩略图
            mediaList = mediaService.getByCategory(category);
            for (Media media : mediaList) {
                mediaService.delete(media.getId()); // 删除数据库中的文件信息
                // 然后删除磁盘上的文件和缩略图
                File deleteFile = new File(myPropsConstants.getUploadBasePath() + media.getPath());
                File thumbnailFile = new File(myPropsConstants.getThumbnailBasePath() + media.getThumbnailPath());
                boolean isDeleted = deleteFile.delete();
                boolean isThumbnailDeleted = thumbnailFile.delete();
                if (isDeleted && isThumbnailDeleted) LOGGER.info("文件和缩略图删除成功");
                else {
                    LOGGER.error("文件或缩略图删除失败");
                    return new ResultModel<>(ResultStatus.FAIL, "文件或缩略图删除失败");
                }
            }
        }
        // 删除文件夹和缩略图
        boolean deleted = targetCategory.delete();
        boolean thumbnailDeleted = targetThumbnailCategory.delete();
        if (deleted && thumbnailDeleted) {
            LOGGER.info("文件夹和缩略图文件夹删除成功");
            return new ResultModel<>(ResultStatus.SUCCESS, "文件夹缩略图删除成功");
        } else {
            LOGGER.error("文件夹或缩略图删除失败");
            return new ResultModel<>(ResultStatus.FAIL, "文件夹或缩略图删除失败");
        }
    }
}
