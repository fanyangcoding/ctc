package com.siemens.ctc.controller;

import com.siemens.ctc.model.Label;
import com.siemens.ctc.model.Media;
import com.siemens.ctc.service.LabelService;
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

import javax.annotation.Resource;
import java.util.List;

/**
 * Label Controller层API接口
 *
 * @author Fan Yang
 * CreateTime: 2019-07-22
 */

@RestController
@RequestMapping(value = "/v1/ctc/label")
@CrossOrigin(origins = "*", maxAge = 3600)
@Api(value = "标签操作", tags = "LabelController", description = "标签操作API接口")
public class LabelController extends BaseController {
    private static Logger LOGGER = LogManager.getLogger(LabelController.class);
    @Resource
    MediaService mediaService;
    @Resource
    LabelService labelService;

//  ####################################################################################################################

    /**
     * 添加标签
     *
     * @param mediaId   文件id
     * @param labelName 标签名
     */
    @ApiOperation(value = "添加标签", notes = "添加标签")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "mediaId", value = "文件id", required = true, dataType = "Integer", paramType = "path"),
            @ApiImplicitParam(name = "labelName", value = "添加的标签名", required = true, dataType = "String", paramType = "query")})
    @ApiResponse(code = 200, response = ResultModel.class, message = "标签添加成功")
    @PutMapping(value = "/{mediaId}", produces = "application/json")
    @PreAuthorize("hasRole('admin')")
    public @ResponseBody
    ResultModel<String> addLabel(@PathVariable("mediaId") Integer mediaId, @RequestParam("labelName") String labelName) {
        LOGGER.info("跟文件添加标签");
        Media media = mediaService.getByMediaId(mediaId);
        if (media == null) {
            LOGGER.error("没有该文件");
            throw new BusinessException(ErrorCodeEnum.MEDIA_NULL.getErrorCode(), ErrorCodeEnum.MEDIA_NULL.getMsg());
        }
        String path = media.getPath();
        LOGGER.info("添加标签");
        if (!mediaService.isPathExist(path)) {
            LOGGER.error("该路径不存在");
            throw new BusinessException(ErrorCodeEnum.PATH_NOT_EXIST.getErrorCode(), ErrorCodeEnum.PATH_NOT_EXIST.getMsg());
        } else {
            labelService.addLabels(mediaId, labelName); // 添加标签，同时更新标签表和标签映射表
            LOGGER.info("标签：" + labelName + " 添加成功");
            return new ResultModel<>(ResultStatus.SUCCESS, labelName + "标签添加成功");
        }
    }
//  ####################################################################################################################

    /**
     * 删除标签
     *
     * @param mediaId   文件id
     * @param labelName 标签名
     */
    @ApiOperation(value = "删除标签", notes = "根据id获取文件，删除标签")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "mediaId", value = "文件id", required = true, dataType = "Integer", paramType = "path"),
            @ApiImplicitParam(name = "labelName", value = "删除的标签名", required = true, dataType = "String", paramType = "query")
    })
    @ApiResponse(code = 200, response = ResultModel.class, message = "标签删除成功")
    @DeleteMapping(value = "/{mediaId}", produces = "application/json")
    @PreAuthorize("hasRole('admin')")
    public @ResponseBody
    ResultModel<String> deleteLabel(@PathVariable("mediaId") Integer mediaId, @RequestParam("labelName") String labelName) {
        LOGGER.info("删除标签");
        Media media = mediaService.getByMediaId(mediaId);
        if (media == null) {
            LOGGER.error("没有该文件");
            throw new BusinessException(ErrorCodeEnum.MEDIA_NULL.getErrorCode(), ErrorCodeEnum.MEDIA_NULL.getMsg());
        }
        String path = media.getPath();
        if (!mediaService.isPathExist(path)) {
            LOGGER.error("该路径不存在");
            throw new BusinessException(ErrorCodeEnum.PATH_NOT_EXIST.getErrorCode(), ErrorCodeEnum.PATH_NOT_EXIST.getMsg());
        } else {
            // 判断该标签是否存在
            if (labelService.isLabelExist(labelName)) {
                labelService.deleteLabel(mediaId, labelName); // 删除标签，同时更新标签表和标签映射表
                LOGGER.info("删除标签: " + labelName + "成功");
                return new ResultModel<>(ResultStatus.SUCCESS, labelName + "标签删除成功");
            }
            throw new BusinessException(ErrorCodeEnum.LABEL_NOT_EXIST.getErrorCode(), ErrorCodeEnum.LABEL_NOT_EXIST.getMsg());
        }
    }
//  ####################################################################################################################

//    /**
//     * 获取标签
//     *
//     * @param mediaId 文件id
//     */
//    @ApiIgnore
//    @ApiOperation(value = "获取标签", notes = "通过id获取标签列表")
//    @ApiImplicitParam(name = "mediaId", value = "文件id", required = true, dataType = "Integer", paramType = "path")
//    @ApiResponse(code = 200, response = List.class, message = "获取标签成功")
//    @GetMapping(value = "/{mediaId}", produces = "application/json")
//    public
//    @ResponseBody
//    ResultModel<List<Label>> getLabels(@PathVariable(value = "mediaId") Integer mediaId) {
//        LOGGER.info("获取标签");
//        Media media = mediaService.getByMediaId(mediaId);
//        if (media == null) {
//            LOGGER.error("没有该文件");
//            throw new BusinessException(ErrorCodeEnum.MEDIA_NULL.getErrorCode(), ErrorCodeEnum.MEDIA_NULL.getMsg());
//        }
//        String path = media.getPath();
//        if (!mediaService.isPathExist(path)) {
//            LOGGER.error("该路径不存在: " + path);
//            throw new BusinessException(ErrorCodeEnum.PATH_NOT_EXIST.getErrorCode(), ErrorCodeEnum.PATH_NOT_EXIST.getMsg());
//        } else {
//            List<Label> labelList = labelService.getLabels(path);
//            LOGGER.info("获取标签: " + labelList + "成功");
//            return new ResultModel<>(ResultStatus.SUCCESS, labelList);
//        }
//    }

//  ####################################################################################################################

    /**
     * 获取所有标签
     */
    @ApiOperation(value = "获取所有标签", notes = "获取所有标签")
    @ApiResponse(code = 200, response = List.class, message = "获取标签实体对象列表")
    @GetMapping(value = "/all", produces = "application/json")
    public @ResponseBody
    ResultModel<List<Label>> getLabelNameAndNum() {
        LOGGER.info("获取标签名和数量");
        List<Label> labelList = labelService.getLabelNameAndNum();
        LOGGER.info("获取成功");
        return new ResultModel<>(ResultStatus.SUCCESS, labelList);
    }

//  ####################################################################################################################

//    /**
//     * 标签检索，标签用逗号分隔
//     */
//    @ApiOperation(value = "标签检索并分页", notes = "标签检索并分页,标签用逗号分隔")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "pageNum", value = "页数", required = false, dataType = "int", paramType = "query"),
//            @ApiImplicitParam(name = "pageSize", value = "单页数量", required = false, dataType = "int", paramType = "query"),
//            @ApiImplicitParam(name = "labels", value = "标签", required = false, dataType = "String", paramType = "query"),
//            @ApiImplicitParam(name = "order", value = "排序", required = false, dataType = "String", paramType = "query"),
//            @ApiImplicitParam(name = "period", value = "时间段", required = false, dataType = "String", paramType = "query"),
//            @ApiImplicitParam(name = "type", value = "后缀", required = false, dataType = "String", paramType = "query"),
//            @ApiImplicitParam(name = "search", value = "搜索", required = false, dataType = "String", paramType = "query")
////
//    })
//    @ApiResponse(code = 200, response = ResultModel.class, message = "标签检索，返回分页结果")
//    @GetMapping(value = "/label_search", produces = "application/json")
//    public @ResponseBody
//    ResultModel<PageInfo<Media>> labelSearch(@RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
//                                             @RequestParam(value = "pageSize", required = false, defaultValue = "0") int pageSize,
//                                             @RequestParam(value = "labels", required = false, defaultValue = "all") String labels,
//                                             @RequestParam(value = "order", required = false) String order,
//                                             @RequestParam(value = "period", required = false) String period,
//                                             @RequestParam(value = "type", required = false) String type,
//                                             @RequestParam(value = "search", required = false) String search) {
//
//        // 判断角色
//        String token = jwtToken.getToken();
//        String username = jwtTokenUtil.getUsernameFromToken(token);
//        LOGGER.info(username);
//        UserInfo userInfo = userInfoService.findByUsername(username);
//        String role;
//        if (userInfo == null) {
//            role = "";
//        } else
//            role = userInfo.getRole();
//
//        if (labels.equals("all")) {
//            // 普通访客显示unrestricted文件
//            if (Strings.isNullOrEmpty(role)) {
//                LOGGER.info("普通访客显示unrestricted文件（可选排序规则, 最近和最热），默认为最新最热");
//                if (Strings.isNullOrEmpty(order)) {
//                    LOGGER.info("普通访客显示unrestricted文件 (默认按照最新最热排序）");
//                    List<Media> mediaList = mediaService.findNoneRestrictMedia();
//                    PageHelper.startPage(pageNum, pageSize);
//                    PageInfo<Media> mediaPageInfo = new PageInfo<>(mediaList);
//                    // 默认最新最热 排序
//                    return new ResultModel<>(ResultStatus.SUCCESS, mediaPageInfo);
//
//                } else if (!order.equals("create_time") && !order.equals("view_num")) {
//                    LOGGER.error("order只能选择view_num或create_time中的一个");
//                    throw new BusinessException(ErrorCodeEnum.SORT_SELECTION_FAILURE.getErrorCode(), ErrorCodeEnum.SORT_SELECTION_FAILURE.getMsg());
//                }
////                PageInfo<Media> mediaPageInfo = mediaService.findNoneRestrictMediaByOrder(pageNum, pageSize, order);
//                List<Media> mediaList = mediaService.findNoneRestrictMediaByOrder(order);
//                PageHelper.startPage(pageNum, pageSize);
//                PageInfo<Media> mediaPageInfo = new PageInfo<>(mediaList);
//
//                return new ResultModel<>(ResultStatus.SUCCESS, mediaPageInfo);
//            }
//
//            // restricted访客显示所有文件
//            else if (role.equals("restricted") || role.equals("admin")) {
//                LOGGER.info("特权访客显示所有文件");
//
//                if (Strings.isNullOrEmpty(order)) {
//                    LOGGER.info("特权访客显示restricted文件(默认按照最新最热排序");
//                    List<Media> mediaList = mediaService.findAllMedia();
//                    PageHelper.startPage(pageNum, pageSize);
//                    PageInfo<Media> mediaPageInfo = new PageInfo<>(mediaList);
//                    return new ResultModel<>(ResultStatus.SUCCESS, mediaPageInfo);
//                } else if (!order.equals("create_time") && !order.equals("view_num")) {
//                    LOGGER.error("order只能选择view_num或create_time中的一个");
//                    throw new BusinessException(ErrorCodeEnum.SORT_SELECTION_FAILURE.getErrorCode(), ErrorCodeEnum.SORT_SELECTION_FAILURE.getMsg());
//                }
//                List<Media> mediaList = mediaService.findAllMediaByOrder(order);
//                PageHelper.startPage(pageNum, pageSize);
//                PageInfo<Media> mediaPageInfo = new PageInfo<>(mediaList);
//                return new ResultModel<>(ResultStatus.SUCCESS, mediaPageInfo);
//            }
//        } else {
//
//            LOGGER.info("标签检索");
//            if (Strings.isNullOrEmpty(labels)) {
//                LOGGER.error("标签不能为空");
//                throw new BusinessException(ErrorCodeEnum.LABEL_EMPTY.getErrorCode(), ErrorCodeEnum.LABEL_EMPTY.getMsg());
//            } else {
//                List<String> labelList = Arrays.asList(labels.split(","));
//                String firstLabel = labelList.get(0);
//                LOGGER.info("第1次用" + firstLabel + "检索");
//                List<Media> mediaList = labelService.labelSearch(pageNum, pageSize, firstLabel);
//
//                for (int i = 1; i < labelList.size(); i++) {
//                    String labelName = labelList.get(i);
//                    for (int j = 0; j < mediaList.size(); j++) {
//                        LOGGER.info("第" + (i + 1) + "次用" + labelName + "检索");
//                        if (!(StringUtil.labelList2String(labelService.getLabels(mediaList.get(j).getPath())).contains(labelName))) {
//                            mediaList.remove(mediaList.get(j));
//                            j--;
//                        }
//                    }
//                }
//
//                PageHelper.startPage(pageNum, pageSize);
//                return new ResultModel<>(ResultStatus.SUCCESS, new PageInfo<>(mediaList));
////            }
//        }
//    }
}
