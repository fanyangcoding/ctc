package com.siemens.ctc.controller;

import com.github.pagehelper.PageInfo;
import com.siemens.ctc.model.UserInfo;
import com.siemens.ctc.service.UserInfoService;
import com.siemens.ctc.tools.exception.BusinessException;
import com.siemens.ctc.tools.exception.ErrorCodeEnum;
import com.siemens.ctc.tools.result.ResultModel;
import com.siemens.ctc.tools.result.ResultStatus;
import io.swagger.annotations.*;
import joptsimple.internal.Strings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import java.util.Date;

/**
 * UserInfo Controller层API接口
 *
 * @author Fan Yang
 * createTime: 2019-07-29
 */
//@ApiVersion(1)
@RestController
@RequestMapping(value = "/v1/ctc/user")
@CrossOrigin(value = "*", maxAge = 3600)
@Api(value = "用户操作", tags = "UserController", description = "用户操作API接口")
public class UserController extends BaseController {
    private static Logger LOGGER = LogManager.getLogger(UserController.class);
    @Resource
    UserInfoService userInfoService;

// #####################################################################################################################

    /**
     * 查询用户
     *
     * @param userId 用户id
     */

    @ApiIgnore
    @ApiOperation(value = "查询用户", notes = "通过id查询用户")
    @ApiImplicitParam(name = "userId", value = "用户id", required = true, dataType = "Integer", paramType = "path")
    @ApiResponse(code = 200, response = UserInfo.class, message = "获取用户对象")
    @PreAuthorize("hasRole('admin')")
    @GetMapping(value = "/{userId}", produces = "application/json")
    public @ResponseBody
    ResultModel<UserInfo> getUser(@PathVariable("userId") Integer userId) {
        LOGGER.info("查询用户: " + userId);
        UserInfo user = userInfoService.findByUserId(userId);
        LOGGER.info("查询用户：" + user.getId() + "成功");
        return new ResultModel<>(ResultStatus.SUCCESS, user);
    }

//  ####################################################################################################################

    /**
     * 获取用户（restricted和admin）
     */
    @ApiOperation(value = "根据角色获取用户，并分页", notes = "根据角色获取用户，并分页")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "role", value = "角色, admin或者restricted", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "pageNum", value = "页数", required = false, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "单页数量", required = false, dataType = "int", paramType = "query")
    })
    @ApiResponse(code = 200, response = PageInfo.class, message = "获取用户白名单成功")
    @PreAuthorize("hasRole('admin')")
    @GetMapping(produces = "application/json")
    public @ResponseBody
    ResultModel<PageInfo<UserInfo>> getUsersByRole(@RequestParam("role") String role, @RequestParam(value = "pageNum", defaultValue = "1") int pageNum, @RequestParam(value = "pageSize", defaultValue = "0") int pageSize) {
        if (!(role.equals("admin") || role.equals("restricted"))) {
            LOGGER.error("角色只能是admin或restricted");
            throw new BusinessException(ErrorCodeEnum.NOT_A_ROLE.getErrorCode(), ErrorCodeEnum.NOT_A_ROLE.getMsg());
        }
        PageInfo<UserInfo> userInfoPageInfo = userInfoService.findUsersByRole(role, pageNum, pageSize);
        return new ResultModel<>(ResultStatus.SUCCESS, userInfoPageInfo);
    }

//  ####################################################################################################################

    /**
     * 添加用户
     */
    @ApiOperation(value = "添加用户", notes = "添加用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "email", value = "邮箱", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "name", value = "姓名", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "department", value = "部门", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "role", value = "角色", required = false, dataType = "String", paramType = "query")
    })
    @ApiResponse(code = 200, response = String.class, message = "添加成功")
    @PreAuthorize("hasRole('admin')")
    @PostMapping(produces = "application/json")
    public @ResponseBody
    ResultModel<String> addUser(@RequestParam(value = "email", required = true) String email,
                                @RequestParam(value = "name", required = false) String name,
                                @RequestParam(value = "department", required = false) String department,
                                @RequestParam(value = "role", required = true) String role
    ) {
        LOGGER.info("添加用户");
        if (Strings.isNullOrEmpty(email)) {
            LOGGER.error("邮箱不能为空");
            throw new BusinessException(ErrorCodeEnum.USER_EMAIL_EMPTY.getErrorCode(), ErrorCodeEnum.USER_EMAIL_EMPTY.getMsg());
        }
        // 判断邮箱是否注册
        if (!userInfoService.isEmailExist(email)) {
            LOGGER.info("该邮箱未注册");

            UserInfo addUserInfo = new UserInfo();
            addUserInfo.setUsername(email); // username 默认为 email
            addUserInfo.setPassword(email); // password 默认为 email

            if (!(role.equals("admin") || role.equals("restricted"))) { // role只能是admin或restricted
                LOGGER.error("Role只能是admin或restricted");
                throw new BusinessException(ErrorCodeEnum.NOT_A_ROLE.getErrorCode(), ErrorCodeEnum.NOT_A_ROLE.getMsg());
            }

            addUserInfo.setRole(role);
            addUserInfo.setLastPasswordResetDate(new Date());
            addUserInfo.setName(name);
            addUserInfo.setEmail(email);
            addUserInfo.setDepartment(department);
            userInfoService.addUser(addUserInfo);

            LOGGER.info("邮箱是" + email + "的用户添加成功");
            return new ResultModel<>(ResultStatus.SUCCESS, "用户添加成功");
        } else {
            LOGGER.error("该邮箱已注册");
            throw new BusinessException(ErrorCodeEnum.EMAIL_EXIST.getErrorCode(), ErrorCodeEnum.EMAIL_EXIST.getMsg());
        }
    }

//  ####################################################################################################################

    /**
     * 删除用户
     */
    @ApiOperation(value = "删除用户", notes = "删除用户")
    @ApiImplicitParam(name = "userId", value = "用户id", required = true, dataType = "Integer", paramType = "path")
    @ApiResponse(code = 200, response = String.class, message = "删除用户成功")
    @PreAuthorize("hasRole('admin')")
    @DeleteMapping(value = "/{userId}", produces = "application/json")
    public @ResponseBody
    ResultModel<String> deleteUser(@PathVariable("userId") Integer userId) {
        UserInfo userInfo = userInfoService.findByUserId(userId);
        if (userInfo == null) {
            throw new BusinessException(ErrorCodeEnum.USER_NULL.getErrorCode(), ErrorCodeEnum.USER_NULL.getMsg());
        }
        String email = userInfo.getEmail();
        LOGGER.info("删除用户: " + email);
        if (Strings.isNullOrEmpty(email)) {
            LOGGER.error("邮箱不能为空");
            throw new BusinessException(ErrorCodeEnum.USER_EMAIL_EMPTY.getErrorCode(), ErrorCodeEnum.USER_EMAIL_EMPTY.getMsg());

        } else if (!userInfoService.isEmailExist(email)) {
            LOGGER.error("该邮箱不存在");
            throw new BusinessException(ErrorCodeEnum.EMAIL_NOT_EXIST.getErrorCode(), ErrorCodeEnum.EMAIL_NOT_EXIST.getMsg());
        }
        userInfoService.deleteUser(email);
        LOGGER.info("用户：" + email + "删除成功");
        return new ResultModel<>(ResultStatus.SUCCESS, "删除成功");
    }

//  ####################################################################################################################

    /**
     * 更新用户信息
     */
    @ApiOperation(value = "更新用户信息", notes = "更新用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "newRole", value = "新角色", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "name", value = "姓名", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "department", value = "部门", required = false, dataType = "String", paramType = "query")
    })
    @ApiResponse(code = 200, response = String.class, message = "用户信息更新成功")
    @PreAuthorize("hasRole('admin')")
    @PutMapping(value = "/{userId}", produces = "application/json")
    public @ResponseBody
    ResultModel<String> updateUser(@PathVariable(value = "userId", required = true) Integer userId,
                                   @RequestParam(value = "name", required = false) String name,
                                   @RequestParam(value = "department", required = false) String department,
                                   @RequestParam(value = "newRole", required = true) String newRole) {
        LOGGER.info("更新用户信息");
        UserInfo userInfo = userInfoService.findByUserId(userId);
        if (!(newRole.equals("admin") || newRole.equals("restricted"))) {
            LOGGER.error("Role只能是admin或restricted");
            throw new BusinessException(ErrorCodeEnum.NOT_A_ROLE.getErrorCode(), ErrorCodeEnum.NOT_A_ROLE.getMsg());
        }
        userInfo.setRole(newRole);

        if (Strings.isNullOrEmpty(name)) {
            userInfo.setName(userInfo.getName());
        } else
            userInfo.setName(name);

        if (Strings.isNullOrEmpty(department)) {
            userInfo.setDepartment(userInfo.getDepartment());
        } else
            userInfo.setDepartment(department);

        userInfoService.updateUser(userInfo);
        return new ResultModel<>(ResultStatus.SUCCESS, "更新用户信息成功");

    }

//  ####################################################################################################################

    /**
     * 修改用户登陆密码
     */
    @ApiIgnore
    @ApiOperation(value = "修改用户登陆密码", notes = "修改用户登陆密码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "email", value = "邮箱", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "newPassword", value = "新密码", required = true, dataType = "String", paramType = "query")
    })
    @ApiResponse(code = 200, response = String.class, message = "密码修改成功")
    @PreAuthorize("hasRole('admin')")
    @PutMapping("/password")
    public @ResponseBody
    ResultModel<String> updatePassword(@RequestParam("email") String email, @RequestParam("newPassword") String newPassword) {
        LOGGER.info(email + "修改密码");
        if (!userInfoService.isEmailExist(email)) {
            LOGGER.error("该邮箱不存在");
            throw new BusinessException(ErrorCodeEnum.EMAIL_NOT_EXIST.getErrorCode(), ErrorCodeEnum.EMAIL_NOT_EXIST.getMsg());
        }
        userInfoService.updatePassword(email, newPassword);
        return new ResultModel<>(ResultStatus.SUCCESS, "密码修改成功");
    }
}
