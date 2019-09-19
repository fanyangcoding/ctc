package com.siemens.ctc.model;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * JWT Token 实体类
 */
@ApiModel
@Data
public class JWTToken {

    /**
     * token
     */
    private String token;

    /**
     * 角色 role
     */
    private String role;

}
