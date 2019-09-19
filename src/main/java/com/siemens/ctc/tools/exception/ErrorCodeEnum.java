package com.siemens.ctc.tools.exception;

import lombok.AllArgsConstructor;

/**
 * Error Code 枚举类
 */

@AllArgsConstructor
public enum ErrorCodeEnum {

    // 文件夹未取名
    FOLDER_UNNAME("E100", "文件夹未取名"),

    // 未选择上传文件
    FILES_UNSELECTED("E101", "未选择上传文件"),

    // 未填写owner
    OWNER_UNFILLED("E102", "未填写owner"),

    // 未填写安全等级
    AUTHORITY_UNFILLED("E103", "未填写安全等级"),

    // 未选择标签
    LABELS_UNFILLED("E104", "未选择标签"),

    // 存在空标签
    LABEL_EMPTY("E105", "空标签"),

    // 空邮箱
    USER_EMAIL_EMPTY("E106", "空邮箱"),

    // 无此用户
    USER_NULL("E107", "无此用户"),

    // 下载暂停或中断
    DOWNLOAD_SUSPEND("E108", "下载暂停或中断"),

    // role只能是admin或restrict
    NOT_A_ROLE("E109", "role只能是admin或restricted"),

    // 添加用户失败
    ADD_USER_FAILURE("E110", "添加用户失败"),

    // 删除用户失败
    DELETE_USER_FAILURE("E111", "删除用户失败"),

    // 更新用户失败
    UPDATE_USER_FAILURE("E112", "更新用户失败"),

    // 无此邮箱
    EMAIL_NOT_EXIST("E113", "无此邮箱"),

    // 密码更新失败
    PASSWORD_UPDATE_FAILURE("E114", "密码更新失败"),

    // 文件重名
    FILENAME_EXIST("E115", "文件重名"),

    // 没有该文件
    MEDIA_NULL("E116", "没有该文件"),

    //上传失败
    UPLOAD_FAILURE("E117", "上传失败"),

    // 路径不存在
    PATH_NOT_EXIST("E118", "路径不存在"),

    // 无法再继续检索
    LABEL_SEARCH_LAST("E119", "无法再继续检索"),

    // 标签添加失败
    LABEL_ADD_FAILURE("E120", "标签添加失败"),

    // 路径重复，重复上传
    PATH_EXIST("E121", "不能重复上传"),

    // 上传文件大小超过限制
    UPLOAD_OVER_MAX_SIZE("E122", "上传文件大小超过限制"),

    // 无此文件夹
    FOLDER_NOT_EXIST("E123", "无此文件夹"),

    // 邮箱已注册
    EMAIL_EXIST("E124", "该邮箱已注册"),

    // 置顶数已满
    PIN_OVER_MAX_SIZE("E125", "置顶数已满3个"),

    // 无文件夹
    FOLDER_NONE("E126", "没有任何文件夹"),

    // 置顶监听器异常
    PIN_LISTENER_EXCEPTION("E127", "置顶监听器异常"),

    // 缩略图生成失败
    THUMBNAIL_GENERATION_FAILURE("E128", "缩略图生成失败"),

    // jwt token 生成失败
    JWT_TOKEN_FAILURE("E129", "JWT TOKEN生成失败"),

    // SORT 只能选view_num 或者 create_time
    SORT_SELECTION_FAILURE("E130", "SORT只能选view_num或create_time"),

    // 文件不存在
    FILE_NOT_EXIST("E131", "文件不存在"),

    // 文件删除失败
    FILE_DELETE_FAILURE("E132", "文件删除失败"),

    // 没有该标签
    LABEL_NOT_EXIST("E133", "没有该标签"),

    // 文件安全等级只能是”restricted“或”unrestricted“
    AUTHORITY_ERROR("E134", "文件安全等级只能是restricted或unrestricted"),

    // 找不到默认图片
    DEFAULT_PICTURE_ERROR("E135", "找不到默认图片"),

    // 更新文件信息失败
    UPDATE_MEDIA_FAILURE("E136", "更新文件失败"),

    // 置顶失败
    PIN_FAILURE("E137", "置顶失败,必须是四种状态中的任意一种(unpin、oneweek、twoweeks、onemonth)"),

    // 消除标签失败
    LABEL_REMOVE_FAILURE("E138", "标签消除失败"),

    // 文件夹已存在
    FOLDER_EXIST("E139", "文件夹已存在"),

    // 拒绝访问
    ACCESS_DENY("E140", "只有管理员才能访问"),

    // 没有文件
    MEDIA_NONE("E141", "没有文件"),

    // 更新时间失败
    UPDATE_TIME_FAILURE("E142", "更新时间失败"),

    // 没有相关资源
    NONE_RELATIVE_RESOURCES("E143", "没有相关资源"),
    // 未知错误
    OTHER_ERROR("E400", "未知错误");


    private String errorCode;

    private String msg;

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static String getResponseMsg(String code) {
        for (ErrorCodeEnum errorCode : ErrorCodeEnum.values()) {
            if (code.equals(errorCode.getErrorCode())) {
                return errorCode.getMsg();
            }
        }
        return OTHER_ERROR.getMsg();
    }
}
