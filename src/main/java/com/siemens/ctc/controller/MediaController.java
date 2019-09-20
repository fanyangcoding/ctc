package com.siemens.ctc.controller;

import com.github.pagehelper.PageInfo;
import com.siemens.ctc.config.MyPropsConstants;
import com.siemens.ctc.model.*;
import com.siemens.ctc.service.LabelService;
import com.siemens.ctc.service.MediaService;
import com.siemens.ctc.service.UserInfoService;
import com.siemens.ctc.tools.exception.BusinessException;
import com.siemens.ctc.tools.exception.ErrorCodeEnum;
import com.siemens.ctc.tools.result.ResultModel;
import com.siemens.ctc.tools.result.ResultStatus;
import com.siemens.ctc.tools.util.DateUtil;
import com.siemens.ctc.tools.util.JwtTokenUtil;
import com.siemens.ctc.tools.util.MediaUtil;
import com.siemens.ctc.tools.util.ThumbnailUtil;
import io.swagger.annotations.*;
import joptsimple.internal.Strings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Fan Yang
 * createdTime: 2019-07-22
 */

@Api(value = "文件操作", tags = "MediaController", description = "文件操作API接口")
@RestController
@RequestMapping(value = "/v1/ctc/media")
@CrossOrigin(origins = "*", maxAge = 3600)
@Transactional
public class MediaController extends BaseController {

    private static final Logger LOGGER = LogManager.getLogger(MediaController.class);

    @Resource
    private MediaService mediaService;
    @Resource
    private LabelService labelService;
    @Resource
    private ThumbnailUtil thumbnailUtil;
    @Resource
    private MyPropsConstants myPropsConstants;
    @Resource
    private MediaUtil mediaUtil;
    @Resource
    JwtTokenUtil jwtTokenUtil;
    @Resource
    UserInfoService userInfoService;


//  ############################################ 上传文件 ###############################################################

    /**
     * 上传文件，创建文件夹，生成缩略图
     * <p>
     * //     * @param owner     owner
     * //     * @param authority 安全等级
     * //     * @param category  文件夹名
     * //     * @param labels    标签
     */

    @ApiOperation(value = "文件批量上传", notes = "文件批量上传, 创建文件夹，标签之间用逗号分隔")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "file", value = "批量上传的文件", required = true, dataType = "file", paramType = "form"),
            @ApiImplicitParam(name = "owner", value = "owner", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "authority", value = "安全等级, 只能是unrestricted或restricted", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "labels", value = "标签", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "category", value = "顶层文件夹", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "folderName", value = "文件夹名", required = true, dataType = "String", paramType = "query")
    })
    @ApiResponse(code = 200, response = String.class, message = "上传结束")
    @PostMapping(value = "/upload", consumes = "multipart/form-data", produces = "application/json")
    @PreAuthorize("hasRole('admin')")
    public @ResponseBody
    ResultModel<String> upload(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("等待文件上传");

        // Multipart Resolver 上传文件
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());

        if (multipartResolver.isMultipart(request)) {
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            List<MultipartFile> files = multipartRequest.getFiles("file");

            String owner = multipartRequest.getParameter("owner");
            String authority = multipartRequest.getParameter("authority");
            String category = multipartRequest.getParameter("category");
            String folderName = multipartRequest.getParameter("folderName");
            String labels = multipartRequest.getParameter("labels");

            if (files.size() == 0) {
                throw new BusinessException(ErrorCodeEnum.FILES_UNSELECTED.getErrorCode(), ErrorCodeEnum.FILES_UNSELECTED.getMsg());
            } else if (Strings.isNullOrEmpty(category)) {
                throw new BusinessException(ErrorCodeEnum.FOLDER_UNNAME.getErrorCode(), ErrorCodeEnum.FOLDER_UNNAME.getMsg());
            } else if (Strings.isNullOrEmpty(owner)) {
                throw new BusinessException(ErrorCodeEnum.OWNER_UNFILLED.getErrorCode(), ErrorCodeEnum.OWNER_UNFILLED.getMsg());
            } else if (Strings.isNullOrEmpty(authority)) {
                throw new BusinessException(ErrorCodeEnum.AUTHORITY_UNFILLED.getErrorCode(), ErrorCodeEnum.AUTHORITY_UNFILLED.getMsg());
            } else if (Strings.isNullOrEmpty(labels)) {
                throw new BusinessException(ErrorCodeEnum.LABELS_UNFILLED.getErrorCode(), ErrorCodeEnum.LABELS_UNFILLED.getMsg());
            }

            LOGGER.info("共有" + files.size() + "个文件上传" + DateUtil.getTime());

            Folder folder = new Folder();
            folder.setCategory(category);
            folder.setFolderName(folderName);

            /**
             * 创建或更新数据库中的文件夹信息
             */

            // 第一次上传，更新create_time，last_update_time
            if (!mediaService.isCategoryExist(category + File.separator + folderName)) {
                folder.setCreateTime(new Date());
                folder.setLastUpdateTime(new Date());
                mediaService.uploadFolder(folder); // 更新数据库中的文件夹表
            } else { // 后续上传，更新last_update_time
                Integer folderId = mediaService.getFolderId(category, folderName);
                mediaService.updateFolder(folderId, new Date()); // 更新数据库中文件夹表中的last_update_time字段
            }

            category = category + File.separator + folderName; // 拼装媒体表中的category字段值

            for (MultipartFile file : files) {
                Media media = new Media();
                String mediaName = file.getOriginalFilename();
                long fileSize = file.getSize();
                String ext = mediaName.substring(mediaName.lastIndexOf(".") + 1);
                String tmpName = mediaName.substring(0, mediaName.lastIndexOf(".")).replace(" ", "-");
                String path = category + File.separator + tmpName + "." + ext;
                if (mediaService.isPathExist(path)) {// 路径重复
                    LOGGER.error(path + "路径重复");
                    throw new BusinessException(ErrorCodeEnum.PATH_EXIST.getErrorCode(), ErrorCodeEnum.PATH_EXIST.getMsg());
                }
                File targetFile = new File(myPropsConstants.getUploadBasePath() + path);
                if (!targetFile.getParentFile().exists()) {
                    targetFile.getParentFile().mkdirs();
                }
                try {
                    file.transferTo(targetFile);
                } catch (IOException e) {
                    LOGGER.error("上传失败");
                    throw new BusinessException(ErrorCodeEnum.UPLOAD_FAILURE.getErrorCode(), ErrorCodeEnum.UPLOAD_FAILURE.getMsg());
                }
                LOGGER.info("文件保存到: " + myPropsConstants.getUploadBasePath() + path);

                Date createTime = new Date();
                media.setCreateTime(createTime); // 设置创建时间
                media.setFileSize(fileSize); // 设置大小
                media.setMediaName(mediaName); // 设置文件名
                media.setPath(path); // 设置文件路径
                media.setType(ext); // 设置扩展名
                media.setDownloadNum(0); // 设置下载量
                media.setViewNum(0); // 设置浏览量
                media.setPin(0); // 设置未置顶
                if (!(authority.equals("restricted") || authority.equals("unrestricted")))
                    throw new BusinessException(ErrorCodeEnum.AUTHORITY_ERROR.getErrorCode(), ErrorCodeEnum.AUTHORITY_ERROR.getMsg());
                media.setAuthority(authority); // 设置安全等级
                media.setOwner(owner); // 设置owner
                media.setCategory(category); // 设置文件夹名,包含顶层文件夹

                // 设置缩略图路径
                String uuid = UUID.randomUUID().toString();
                String thumbnailName = tmpName + "-" + uuid.replaceAll("-", "") + "-thumbnail.jpg";
                String thumbnailPath = category + File.separator + thumbnailName; //缩略图的相对路径
                media.setThumbnailPath(thumbnailPath);
                LOGGER.info("设置缩略图路径" + thumbnailPath);


                // 更新数据库中的文件信息
                Integer mediaId = mediaService.upload(media);
                LOGGER.info(media.getPath() + "已上传");

                // 生成标签
                List<String> labelList = Arrays.asList(labels.split(","));
                for (String labelName : labelList) {
                    labelService.addLabels(mediaId, labelName);
                    LOGGER.info("生成标签：" + labelName);
                }
                LOGGER.info("生成标签完成");

                // 生成缩略图
                thumbnailUtil.generateThumbnail(media);
            }
        }
        LOGGER.info("上传结束");
        return new ResultModel<>(ResultStatus.SUCCESS, "全部上传");
    }

//  ########################################## 获取上传进度 #############################################################

    /**
     * 获取上传进度，表示第几个文件正在上传
     *
     * @param request 请求
     * @return 第几个文件正在上传
     */
    @ApiOperation(value = "获取上传进度", notes = "轮询获取session中保存的进度")
    @ApiResponse(code = 200, response = Integer.class, message = "获取上传进度，正在上传第几个文件")
    @GetMapping(value = "/upload_progress", produces = "application/json")
    @ResponseBody
    public ResultModel<Integer> getProgress(HttpServletRequest request) {
        ProgressEntity status = (ProgressEntity) request.getSession().getAttribute("status");
        if (status == null) {
            return new ResultModel<>(ResultStatus.SUCCESS, 0);
        }
        return new ResultModel<>(ResultStatus.SUCCESS, status.getPItems());
    }

//  #################################### 获取 media 对象#################################################################

    /**
     * 文件详情页
     *
     * @param mediaId 文件id
     * @return media对象
     */

    @ApiOperation(value = "获取文件详情页", notes = "通过文件id获取文件对象")
    @ApiImplicitParam(name = "mediaId", value = "mediaId", required = true, dataType = "Integer", paramType = "query")
    @ApiResponse(code = 200, response = Media.class, message = "返回文件对象")
    @GetMapping(produces = "application/json")
//    @PreAuthorize("hasRole('admin')")
    @ResponseBody
    public ResultModel<Media>
    getMediaByMediaId(@RequestParam("mediaId") Integer mediaId) {
        Media media = mediaService.getByMediaId(mediaId);
        if (media == null) {
            throw new BusinessException(ErrorCodeEnum.MEDIA_NULL.getErrorCode(), ErrorCodeEnum.MEDIA_NULL.getMsg());
        }
        String path = media.getPath();
        LOGGER.info("获取文件详情页： " + path);
        if (!mediaService.isPathExist(path)) {
            LOGGER.error("该路径不存在");
            throw new BusinessException(ErrorCodeEnum.PATH_NOT_EXIST.getErrorCode(), ErrorCodeEnum.PATH_NOT_EXIST.getMsg());
        } else {
            mediaService.plusViewNum(mediaId); // 浏览量加1
            return new ResultModel<>(ResultStatus.SUCCESS, media);
        }
    }

//  #################################### 加载文件缩略图 #################################################################

    /**
     * 加载文件缩略图（封面）
     */

    @ApiOperation(value = "加载文件缩略图", notes = "加载文件缩略图")
//    @ApiImplicitParam(name = "mediaId", value = "文件id", required = true, dataType = "Integer", paramType = "path")
//    @ApiResponse(code = 200, response = String.class, message = "获取缩略图url")
    @GetMapping(value = "/thumbnail/{thumbnailPath}")
    public void showThumbnail(@PathVariable("thumbnailPath") String thumbnailPath) {
    }

//  ####################################################################################################################

    /**
     * 获取文件总数
     */
    @ApiIgnore
    @ApiOperation(value = "获取文件总数", notes = "获取文件总数")
    @GetMapping("/media_number")
    @ApiResponse(code = 200, response = ResultModel.class, message = "获取文件总数")
    public @ResponseBody
    ResultModel<?> getMediaNum() {
        int mediaNum = mediaService.getMediaNum();
        return new ResultModel<>(ResultStatus.SUCCESS, mediaNum);
    }


//  ######################################## 获取 MediaList ############################################################

    /**
     * 瀑布流，文件列表分页
     */
    @ApiOperation(value = "文件列表", notes = "返回文件列表，包括标签搜索，排序")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页数", required = false, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "单页数量", required = false, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "labels", value = "标签, 默认all", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "order", value = "排序，order为空，按默认（最新最热）排序，order为view_num按最热排序，为create_time按最新排序", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "period", value = "字符串，oneweek, twoweeks, onemonth, halfyear 四个选项", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "type", value = "文件类型", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "search", value = "搜索，根据文件名模糊查找", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "category", value = "六个顶层文件夹名，默认all", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "folderId", value = "folderId", required = false, dataType = "Integer", paramType = "query")
    })
    @GetMapping(value = "/waterfall", produces = "application/json")
    public ResultModel<PageInfo<Media>> getMediaList(@RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                                     @RequestParam(value = "pageSize", required = false, defaultValue = "0") Integer pageSize,
                                                     @RequestParam(value = "labels", required = false, defaultValue = "all") String labels,
                                                     @RequestParam(value = "order", required = false, defaultValue = "default") String order,
                                                     @RequestParam(value = "period", required = false) String period,
                                                     @RequestParam(value = "type", required = false) String type,
                                                     @RequestParam(value = "search", required = false) String search,
                                                     @RequestParam(value = "category", required = false) String category,
                                                     @RequestParam(value = "folderId", required = false) Integer folderId,
                                                     HttpServletRequest request) {
        List<Media> mediaList;
        List<Media> filterMediaList;
        String role;
        String token = "";

        // 判断角色
        try {
            token = request.getHeader("Authorization").substring(7);
        } catch (NullPointerException e) {
            LOGGER.info("token 为 null");
        }

        if (Strings.isNullOrEmpty(token)) {
            LOGGER.info("角色是普通访客");
            role = "";
        } else {
            String username = jwtTokenUtil.getUsernameFromToken(token);
            UserInfo userInfo = userInfoService.findByUsername(username);
            if (userInfo != null) {
                role = userInfo.getRole();
                LOGGER.info("角色是" + role);
            } else
                throw new BusinessException(ErrorCodeEnum.USER_NULL.getErrorCode(), ErrorCodeEnum.USER_NULL.getMsg());
        }

        if (Strings.isNullOrEmpty(role)) { // 普通访客
            if (Strings.isNullOrEmpty(category)) {
                Folder folder = mediaService.getFolderById(folderId);
                category = folder.getCategory() + File.separator + folder.getFolderName(); // 拼装媒体表中的category字段值
                LOGGER.info("###########category = " + category);
            }
            if (category.equals("all"))
                mediaList = mediaService.getMediaByAuthority("unrestricted"); // 获取所有unrestricted等级的文件
            else mediaList = mediaService.getMedia(category, "unrestricted"); // 获取category下unrestricted等级的文件

            mediaUtil.setMediaList(mediaList);
            filterMediaList = mediaUtil.filterChain(labels, period, type, search);
            PageInfo<Media> mediaPage = mediaUtil.getMediaListWithPagination(pageNum, pageSize, filterMediaList, order);
            return new ResultModel<>(ResultStatus.SUCCESS, mediaPage);

        } else { // 管理员或特权访客
            if (Strings.isNullOrEmpty(category)) {
                Folder folder = mediaService.getFolderById(folderId);
                category = folder.getCategory() + File.separator + folder.getFolderName(); // 拼装媒体表中的category字段值
                LOGGER.info("###########category = " + category);
            }
            if (category.equals("all"))
                mediaList = mediaService.getAllMedia(); // 获取所有文件
            else mediaList = mediaService.getMediaByCategory(category); // 获取category下的所有文件

            mediaUtil.setMediaList(mediaList);
            filterMediaList = mediaUtil.filterChain(labels, period, type, search);
            PageInfo<Media> mediaPage = mediaUtil.getMediaListWithPagination(pageNum, pageSize, filterMediaList, order);
            return new ResultModel<>(ResultStatus.SUCCESS, mediaPage);
        }
    }

//  ######################################### 下载文件 ##################################################################

    /**
     * 下载文件
     *
     * @param mediaId 文件id
     * @param req     servlet request
     * @param res     servlet response
     */
    @ApiOperation(value = "断点下载")
    @ApiImplicitParam(name = "mediaId", value = "mediaId", required = true, dataType = "Integer", paramType = "query")
    @GetMapping(value = "/download", produces = "application/json")
    public void download(@RequestParam("mediaId") Integer mediaId, HttpServletRequest
            req, HttpServletResponse res) {
        Media media = mediaService.getByMediaId(mediaId);

        if (media == null) {
            LOGGER.error("没有该文件");
            throw new BusinessException(ErrorCodeEnum.MEDIA_NULL.getErrorCode(), ErrorCodeEnum.MEDIA_NULL.getMsg());
        }

        String path = media.getPath();

        LOGGER.info("文件路径： " + path);

        if (!mediaService.isPathExist(path)) {
            LOGGER.info("该路径不存在: " + path);
            throw new BusinessException(ErrorCodeEnum.PATH_NOT_EXIST.getErrorCode(), ErrorCodeEnum.PATH_NOT_EXIST.getMsg());
        } else {
            String fullPath = myPropsConstants.getUploadBasePath() + path;
            LOGGER.info("下载路径: " + fullPath);

            File downloadFile = new File(fullPath);
            ServletContext context = req.getServletContext();
            String mimeType = context.getMimeType(fullPath);
            if (mimeType == null) {
                mimeType = "application/octet-stream";
            }
            res.setContentType(mimeType);

            String headerKey = "Content-Disposition";
            String headerValue = String.format("attachment;filename=\"%s\"", downloadFile.getName());
            res.setHeader(headerKey, headerValue);

            // 解析断点续传相关信息
            res.setHeader("Accept-Ranges", "bytes");
            long downloadSize = downloadFile.length();
            long fromPos = 0, toPos = 0;
            if (req.getHeader("Range") == null) {
                res.setHeader("Content-Length", downloadSize + "");
            } else {
                res.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);
                String range = req.getHeader("Range");
                String bytes = range.replaceAll("bytes=", "");
                String[] ary = bytes.split("-");
                fromPos = Long.parseLong(ary[0]);
                if (ary.length == 2) {
                    toPos = Long.parseLong(ary[1]);
                }
                int size;
                if (toPos > fromPos) {
                    size = (int) (toPos - fromPos);
                } else {
                    size = (int) (downloadSize - fromPos);
                }
                res.setHeader("Content-Length", size + "");
                downloadSize = size;
            }

            RandomAccessFile in = null;
            OutputStream out = null;

            try {
                in = new RandomAccessFile(downloadFile, "rw");
                if (fromPos > 0) {
                    in.seek(fromPos);
                }
                int bufLen = (int) (downloadSize < 2048 ? downloadSize : 2048);
                byte[] buffer = new byte[bufLen];
                int num;
                int count = 0;
                out = res.getOutputStream();
                while ((num = in.read(buffer)) != -1) {
                    out.write(buffer, 0, num);
                    count += num;
                    if (downloadSize - count < bufLen) {
                        bufLen = (int) (downloadSize - count);
                        if (bufLen == 0) {
                            break;
                        }
                        buffer = new byte[bufLen];
                    }
                }
                res.flushBuffer();
            } catch (IOException e) {
                LOGGER.error("数据被暂停或中断.");
                throw new BusinessException(ErrorCodeEnum.DOWNLOAD_SUSPEND.getErrorCode(), ErrorCodeEnum.DOWNLOAD_SUSPEND.getMsg());
            } finally {
                if (null != out) {
                    try {
                        out.close();
                    } catch (IOException e) {
                        LOGGER.error("数据被暂停或中断.");
                    }
                }
                if (null != in) {
                    try {
                        in.close();
                    } catch (IOException e) {
                        LOGGER.error("数据被暂停或中断.");
                    }
                }
            }
            mediaService.plusDownloadNum(mediaId); // 下载后下载量加1
        }
    }

//  ################################ 更换缩略图 #########################################################################

    /**
     * 更换缩略图（封面）
     */

    @ApiOperation(value = "更换缩略图", notes = "上传新缩略图")
    @ApiImplicitParam(name = "mediaId", value = "mediaId", required = true, dataType = "Integer", paramType = "query")
    @ApiResponse(code = 200, response = String.class, message = "缩略图更换成功")
    @PreAuthorize("hasRole('admin')")
    @PutMapping(value = "/thumbnail", produces = "application/json")
    @ResponseBody
    public ResultModel<String> updateThumbnail(@RequestParam("mediaId") Integer mediaId, @RequestParam("file") MultipartFile file) {
        LOGGER.info("更换缩略图");
        Media media = mediaService.getByMediaId(mediaId);
        if (media == null) {
            LOGGER.error("没有该文件");
            throw new BusinessException(ErrorCodeEnum.MEDIA_NULL.getErrorCode(), ErrorCodeEnum.MEDIA_NULL.getMsg());
        }
        String path = media.getPath();
        if (!mediaService.isPathExist(path)) {
            LOGGER.error("该路径不存在");
            throw new BusinessException(ErrorCodeEnum.PATH_NOT_EXIST.getErrorCode(), ErrorCodeEnum.PATH_NOT_EXIST.getMsg());
        }
        String thumbnailPath = media.getThumbnailPath();
        try {
            File targetFile = new File(myPropsConstants.getThumbnailBasePath() + thumbnailPath);
            if (!targetFile.getParentFile().exists()) {
                targetFile.getParentFile().mkdirs();
            }
            file.transferTo(targetFile);
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
            throw new BusinessException(ErrorCodeEnum.UPLOAD_FAILURE.getErrorCode(), ErrorCodeEnum.UPLOAD_FAILURE.getMsg());
        }
        LOGGER.info("缩略图更换成功");
        return new ResultModel<>(ResultStatus.SUCCESS, "缩略图更换成功");
    }

//  ################################ 编辑文件 ###########################################################################

    /**
     * 编辑文件
     * 置顶，标签修改，owner标注，安全等级标注，更改封面，文件重命名
     */
    @ApiOperation(value = "编辑文件", notes = "文件重命名，标注安全等级、owner，是否置顶，修改创建时间")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "mediaId", value = "mediaId", required = true, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "pin", value = "数字，必须为：1,2,4,0 中的任意一个。1代表1周，2代表2周，4代表一个月，0代表不置顶", required = true, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "owner", value = "owner", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "authority", value = "安全等级", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "mediaName", value = "文件名", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "createTime", value = "创建时间 yyyy-MM-dd", required = false, dataType = "String", paramType = "query")
    })
    @ApiResponse(code = 200, response = String.class, message = "更新完成")
    @PutMapping(produces = "application/json")
    @PreAuthorize("hasRole('admin')")
    public @ResponseBody
    ResultModel<String> edit(
            @RequestParam(value = "mediaId", required = true) Integer mediaId,
            @RequestParam(value = "pin", required = true) Integer pin,
            @RequestParam(value = "owner", required = false) String owner,
            @RequestParam(value = "authority", required = false) String authority,
            @RequestParam(value = "mediaName", required = false) String mediaName,
            @RequestParam(value = "createTime", required = false) String createTime) {

        LOGGER.info("更新文件信息");

        Media media = mediaService.getByMediaId(mediaId);
        if (media == null) {
            LOGGER.error("没有该文件");
            throw new BusinessException(ErrorCodeEnum.MEDIA_NULL.getErrorCode(), ErrorCodeEnum.MEDIA_NULL.getMsg());
        }
        if (Strings.isNullOrEmpty(owner)) {
            owner = media.getOwner();
        }
        if (Strings.isNullOrEmpty(authority)) {
            authority = media.getAuthority();
        }
        if (Strings.isNullOrEmpty(mediaName)) {
            mediaName = media.getMediaName();
        }
        if (Strings.isNullOrEmpty(createTime)) {
            createTime = new SimpleDateFormat("yyyy-MM-dd").format(media.getCreateTime());
        }

        String path = media.getPath();
        if (!mediaService.isPathExist(path)) {
            LOGGER.error("该路径不存在：" + path);
            throw new BusinessException(ErrorCodeEnum.PATH_NOT_EXIST.getErrorCode(), ErrorCodeEnum.PATH_NOT_EXIST.getMsg());
        }

        // 设置置顶
        LOGGER.info("设置置顶");
        if (pin != 0) { // 设置置顶，分两种情况，一种已置顶，需要更新置顶周期，另一种未置顶，需要先判断置顶数，再置顶
            if (media.getPin() != 0) { // 之前置顶，修改置顶时间
                LOGGER.info("该文件已置顶" + media.getPath());
                mediaService.updatePinDuration(mediaId, pin); // 修改置顶时间
            } else { // 之前没置顶
                int count = mediaService.getPinNum(); // 得到目前的置顶数
                LOGGER.info("现有置顶数: " + count + ", 最多置顶数: " + myPropsConstants.getMaxPinNum());
                if (count <= myPropsConstants.getMaxPinNum() - 1) {
                    media.setPin(pin); // 设置置顶
                    mediaService.setPin(mediaId, pin); // 设置置顶周期
                    LOGGER.info("置顶成功, 现有置顶数: " + mediaService.getPinNum());
                } else {
                    LOGGER.error("置顶数已满，无法置顶");
                    throw new BusinessException(ErrorCodeEnum.PIN_OVER_MAX_SIZE.getErrorCode(), ErrorCodeEnum.PIN_OVER_MAX_SIZE.getMsg());
                }
            }
        } else { // 不置顶
            if (media.getPin() != 0) {
                LOGGER.info(path + "取消置顶");
                mediaService.cancelPin(mediaId); // 从置顶表中删除
            }
        }
        mediaService.update(mediaId, mediaName, authority, owner, pin, createTime);
        LOGGER.info("文件更新完成");
        return new ResultModel<>(ResultStatus.SUCCESS, "更新完成");
    }

//  ################################### 删除文件 ########################################################################

    /**
     * 删除文件
     *
     * @param mediaId 文件id
     */

    @ApiOperation(value = "删除文件", notes = "根据id删除文件")
    @ApiImplicitParam(name = "mediaId", value = "mediaId", required = true, dataType = "Integer", paramType = "query")
    @ApiResponse(code = 200, response = String.class, message = "删除成功")
    @DeleteMapping(produces = "application/json")
    @PreAuthorize("hasRole('admin')")
    public ResultModel<String> deleteMedia(@RequestParam("mediaId") Integer mediaId) {
        Media media = mediaService.getByMediaId(mediaId);
        if (media == null) {
            LOGGER.error("没有该文件");
            throw new BusinessException(ErrorCodeEnum.MEDIA_NULL.getErrorCode(), ErrorCodeEnum.MEDIA_NULL.getMsg());
        }
        LOGGER.info("删除文件");
        String path = media.getPath();
        if (!mediaService.isPathExist(path)) {
            LOGGER.error("该路径不存在: " + path);
            throw new BusinessException(ErrorCodeEnum.PATH_NOT_EXIST.getErrorCode(), ErrorCodeEnum.PATH_NOT_EXIST.getMsg());
        } else {
            mediaService.delete(mediaId); // 删除数据库中的文件信息

            // 最后删除文件夹中的文件和缩略图
            File deleteFile = new File(myPropsConstants.getUploadBasePath() + File.separator + path);
            File thumbnailFile = new File(myPropsConstants.getThumbnailBasePath() + File.separator + media.getThumbnailPath());
            if (!deleteFile.exists()) {
                LOGGER.error("文件不存在");
                throw new BusinessException(ErrorCodeEnum.FILE_NOT_EXIST.getMsg(), ErrorCodeEnum.FILE_NOT_EXIST.getMsg());
            } else if (deleteFile.isFile()) {
                boolean isDeleted = deleteFile.delete();
                boolean isThumbnailDeleted = thumbnailFile.delete();
                if (isDeleted && isThumbnailDeleted) {
                    LOGGER.info("文件和缩略图删除成功");
                    return new ResultModel<>(ResultStatus.SUCCESS, "删除成功");
                } else
                    throw new BusinessException(ErrorCodeEnum.FILE_DELETE_FAILURE.getErrorCode(), ErrorCodeEnum.FILE_DELETE_FAILURE.getMsg());
            }
        }
        return new ResultModel<>(ResultStatus.SUCCESS, "删除成功");
    }

    /**
     * 获取文件的所有类型
     */

    @ApiOperation(value = "获取全部的文件类型", notes = "获取全部的文件类型")
    @GetMapping(value = "/type", produces = "application/json")
    @ApiResponse(code = 200, response = List.class, message = "获取文件类型成功")
    public ResultModel<List> getMediaTypes() {
        List<String> typeList = mediaService.getMediaTypes();
        return new ResultModel<>(ResultStatus.SUCCESS, typeList);
    }

    /**
     * 获取相关资源
     * 按照标签，相同文件夹，相同category，相同文件名进行匹配
     */
    @ApiOperation(value = "获取相关资源", notes = "获取文件的相关资源")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "mediaId", value = "mediaId", required = true, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "pageNum", value = "页数", required = false, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "单页数量", required = false, dataType = "Integer", paramType = "query")})
    @CrossOrigin(value = "*", maxAge = 3600)
    @GetMapping(value = "/resources", produces = "application/json")
    public ResultModel<PageInfo<Media>> getRelativeResources(@RequestParam("mediaId") Integer mediaId,
                                                             @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                                             @RequestParam(value = "pageSize", defaultValue = "0") Integer pageSize,
                                                             HttpServletRequest request) {
        LOGGER.info("获取相关资源");

        List<Media> unrestrictedMediaList;  // 非特权文件
        List<Media> mediaList;  // 特权文件
        List<Media> eliminatedMediaList = new LinkedList<>();
        List<Media> resourcesMediaList; // 资源文件

        String role;
        String token = "";
        // 判断角色
        try {
            token = request.getHeader("Authorization").substring(7);
        } catch (NullPointerException e) {
            LOGGER.info("token 为 null");
        }

        if (Strings.isNullOrEmpty(token)) {
            LOGGER.info("角色是普通访客");
            role = "";
        } else {
            String username = jwtTokenUtil.getUsernameFromToken(token);
            UserInfo userInfo = userInfoService.findByUsername(username);
            if (userInfo != null) {
                role = userInfo.getRole();
                LOGGER.info("角色是" + role);
            } else
                throw new BusinessException(ErrorCodeEnum.USER_NULL.getErrorCode(), ErrorCodeEnum.USER_NULL.getMsg());
        }

        Media media = mediaService.getByMediaId(mediaId);
        String category = media.getCategory().substring(0, media.getCategory().indexOf(File.separator));
        List<Label> labelList = labelService.getLabels(mediaId);

        if (Strings.isNullOrEmpty(role)) {

            unrestrictedMediaList = mediaService.getMediaByAuthority("unrestricted"); // 普通访客只能获取非特权文件

            // 获取相关资源文件
            resourcesMediaList = mediaUtil.getResourcesMediaList(labelList, category, "unrestricted");
            resourcesMediaList = mediaUtil.removeMedia(resourcesMediaList, media);


            // 分页
            PageInfo<Media> mediaPage = mediaUtil.getResourcesMediaListWithPagination(pageNum, pageSize, resourcesMediaList);
            return new ResultModel<>(ResultStatus.SUCCESS, mediaPage);

        } else {
            mediaList = mediaService.getAllMedia(); // 管理员或特权访客可以获取所有文件

            // 获取相关资源文件
            resourcesMediaList = mediaUtil.getResourcesMediaList(labelList, category);
            resourcesMediaList = mediaUtil.removeMedia(resourcesMediaList, media);
            
            // 分页
            PageInfo<Media> mediaPage = mediaUtil.getResourcesMediaListWithPagination(pageNum, pageSize, resourcesMediaList);
            return new ResultModel<>(ResultStatus.SUCCESS, mediaPage);
        }

    }
}
