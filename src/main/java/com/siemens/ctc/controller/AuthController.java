package com.siemens.ctc.controller;

import com.siemens.ctc.config.jwt.JwtAuthenticationRequest;
import com.siemens.ctc.config.jwt.JwtAuthenticationResponse;
import com.siemens.ctc.model.JWTToken;
import com.siemens.ctc.model.User;
import com.siemens.ctc.model.UserInfo;
import com.siemens.ctc.service.AuthService;
import com.siemens.ctc.service.UserInfoService;
import com.siemens.ctc.tools.exception.BusinessException;
import com.siemens.ctc.tools.exception.ErrorCodeEnum;
import com.siemens.ctc.tools.result.ResultModel;
import com.siemens.ctc.tools.result.ResultStatus;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Auth Controller层API接口
 *
 * @author Fan Yang
 * createTime: 2019-08-06
 */

@Api(value = "认证授权操作", tags = "AuthController", description = "认证授权操作API接口")
@RestController
@CrossOrigin(value = "*", maxAge = 3600)
@RequestMapping("/v1/ctc/auth")
public class AuthController {
    private static Logger LOGGER = LogManager.getLogger(UserController.class);

    @Value("${jwt.header}")
    private String tokenHeader;
    @Resource
    private AuthService authService;
    @Resource
    private UserInfoService userInfoService;

    @ApiOperation(value = "获取JWT token", notes = "获取JWT token，默认有效期7日")
    @ApiResponse(code = 200, response = JwtAuthenticationRequest.class, message = "获取token")
    @PostMapping(value = "/login", produces = "application/json")
    public ResultModel<Object> createAuthenticationToken(@ApiParam(name = "用户名和密码", value = "用户名和密码") @RequestBody User user) {
        String username = user.getUsername();
        String password = user.getPassword();
        UserInfo userInfo = userInfoService.findByUsername(username);
        if (userInfo == null) {
            LOGGER.error("该用户不存在");
            throw new BusinessException(ErrorCodeEnum.USER_NULL.getErrorCode(), ErrorCodeEnum.USER_NULL.getMsg());
        } else if (!password.equals(userInfo.getPassword()))
            return new ResultModel<>(ResultStatus.FAIL, "密码错误");
        else {
            final String token = authService.login(username, password);
            String role = userInfo.getRole();
            LOGGER.info("生成JWT token:" + token + "角色是" + role);
//            JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse(token);
            JWTToken jwtToken = new JWTToken();
            jwtToken.setToken(token);
            jwtToken.setRole(role);
            return new ResultModel<>(ResultStatus.SUCCESS, jwtToken);
        }
    }

    @ApiIgnore
    @ApiOperation(value = "刷新JWT token", notes = "刷新JWT token")
    @ApiResponse(code = 200, response = JwtAuthenticationRequest.class, message = "刷新token")
    @GetMapping(value = "/login")
    public ResultModel<?> refreshAndGetAuthenticationToken(HttpServletRequest request) {
        String token = request.getHeader(tokenHeader);
        String refreshToken = authService.refresh(token);
        if (refreshToken == null) {
            return new ResultModel<>(ResultStatus.FAIL, "token刷新失败");
        } else {
            return new ResultModel<>(ResultStatus.SUCCESS, new JwtAuthenticationResponse(token));
        }
    }

    @ApiIgnore
    @PostMapping(value = "${jwt.route.authentication.register}")
    UserInfo register(@RequestBody UserInfo addedUser) {
        return authService.register(addedUser);
    }
}
