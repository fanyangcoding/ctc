package com.siemens.ctc.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * UserInfo 实体类
 */

@Data
@ApiModel(value = "用户实体类", description = "用户实体对象")
public class UserInfo implements UserDetails {

    /**
     * id
     */
    @ApiModelProperty(value = "id", name = "id")
    private Integer id;

    /**
     * 姓名
     */
    @ApiModelProperty(value = "name", name = "姓名", required = true)
    private String name;

    /**
     * 用户名
     */

    @ApiModelProperty(value = "username", name = "用户名")
    private String username;

    /**
     * 邮箱
     */

    @ApiModelProperty(value = "email", name = "邮箱", required = true)
    private String email;

    /**
     * 部门
     */

    @ApiModelProperty(value = "department", name = "部门")
    private String department;

    /**
     * 角色
     */

    @ApiModelProperty(value = "role", name = "角色", required = true)
    private String role;

    /**
     * 密码
     */

    @ApiModelProperty(value = "password", name = "密码")
    private String password;

    /**
     * 最近更新密码日期
     */
    @ApiModelProperty(value = "lastPasswordResetDate", name = "最近更新密码日期", hidden = true)
    private Date lastPasswordResetDate;

    /**
     * 账号是否过去
     */
    @ApiModelProperty(value = "accountNonExpired", name = "账号是否过去", hidden = true)
    private boolean accountNonExpired;

    /**
     * 账号是否锁住
     */
    @ApiModelProperty(value = "accountNoneLocked", name = "账号是否锁住", hidden = true)
    private boolean accountNonLocked;

    /**
     * 令牌是否过去
     */
    @ApiModelProperty(value = "credentialsNonExpired", name = "令牌是否国期", hidden = true)
    private boolean credentialsNonExpired;

    /**
     * 验证
     */
    @ApiModelProperty(value = "authorities", name = "授权", hidden = true)
    private List<GrantedAuthority> authorities;

    /**
     * enabled
     */
    @ApiModelProperty(value = "enabled", name = "enabled", hidden = true)
    private boolean enabled;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> auths = new ArrayList<>();
        auths.add(new SimpleGrantedAuthority(getRole()));
        return auths;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    @Override
    public String toString() {
        return "{" +
                "name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", department='" + department + '\'' +
                ", role='" + role + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
