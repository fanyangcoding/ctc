//package com.siemens.ctc.service;
//
//import com.github.pagehelper.PageInfo;
//import com.siemens.ctc.model.UserInfo;
//import com.siemens.ctc.tools.exception.BusinessException;
//import org.junit.Assert;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import javax.annotation.Resource;
//
//import static org.junit.Assert.*;
//
///**
// * UserInfoService Test
// *
// * @author Fan Yangn
// * createTime: 2019-08-19
// */
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class UserInfoServiceTest {
//
//    @Resource
//    private UserInfoService userInfoService;
//
//    /**
//     * 根据用户名查找用户
//     */
//    @Test
//    public void findByUsername() {
//        // 查找用户，用户存在
//        UserInfo userInfo = userInfoService.findByUsername("admin");
//        assertNotNull(userInfo);
//
//        // 查找失败
//        try {
//            userInfoService.findByUsername("xxxxx");
//        } catch (BusinessException e) {
//            assertEquals("E107", e.getErrorCode());
//        }
//    }
//
//    /**
//     * 根据邮箱查找用户
//     */
//    @Test
//    public void findByEmail() {
//        // 查找用户，用户存在
//        UserInfo userInfo = userInfoService.findByEmail("admin@siemens.com");
//        assertNotNull(userInfo);
//
//        // 查找失败
//        try {
//            userInfoService.findByEmail("xxxx@siemens.com");
//        } catch (BusinessException e) {
//            assertEquals("E113", e.getErrorCode());
//        }
//    }
//
//    /**
//     * 添加用户
//     */
//    @Test
//    public void addUser() {
//        // 添加用户成功
//        UserInfo userInfo = new UserInfo();
//        userInfo.setEmail("admin2@siemens.com");
//        userInfo.setName("admin2");
//        userInfo.setRole("admin");
//        userInfo.setDepartment("ctc");
//
//        int count = userInfoService.addUser(userInfo);
//        assertEquals(1, count);
//
//        // 添加用户失败
//        try {
//            userInfoService.addUser(null);
//        } catch (BusinessException e) {
//            assertEquals("E107", e.getErrorCode());
//        }
//    }
//
//    /**
//     * 删除用户
//     */
//
//    @Test
//    public void deleteUser() {
//        // 删除成功
//        int count = userInfoService.deleteUser("admin1@siemens.com");
//        assertEquals(1, count);
//
//        // 删除失败
//        try {
//            userInfoService.deleteUser("xxxx@siemens.com");
//        } catch (BusinessException e) {
//            assertEquals("E113", e.getErrorCode());
//        }
//    }
//
//    /**
//     * 更新用户信息
//     */
//
//    @Test
//    public void updateUser() {
//        // 更新成功
//        int count = userInfoService.updateUser("test@siemens.com", "restrict", "admin1", "siemens");
//        assertEquals(1, count);
//
//        // 更新失败
//        try {
//            userInfoService.updateUser("xxxxx@siemens.com", "admin", "xxxxx", "xxxxx");
//        } catch (BusinessException e) {
//            assertEquals("E113", e.getErrorCode());
//        }
//    }
//
//    /**
//     * 查找所有用户
//     */
//
//    @Test
//    public void findAllUsers() {
//        PageInfo<UserInfo> userInfoPageInfo = userInfoService.findAllUsers(1, 0);
//        Assert.assertNotNull(userInfoPageInfo);
//    }
//
//    /**
//     * 更新密码
//     */
//
//    @Test
//    public void updatePassword() {
//
//        // 更新成功
//        boolean updateSuccess = userInfoService.updatePassword("test@siemens.com", "123456");
//        assertTrue(updateSuccess);
//
//        // 更新失败
//        try {
//            userInfoService.updatePassword("xxxxx@siemens.com", "123456");
//        } catch (BusinessException e) {
//            assertEquals("E113", e.getErrorCode());
//        }
//    }
//
//    /**
//     * 判断邮箱是否存在（用户是否存在）
//     */
//
//    @Test
//    public void isEmailExist() {
//        // 邮箱存在
//        boolean isExist = userInfoService.isEmailExist("admin@siemens.com");
//        assertTrue(isExist);
//
//        // 邮箱不存在
//        try {
//            userInfoService.isEmailExist("xxxxx@siemens.com");
//        } catch (BusinessException e) {
//            assertEquals("E113", e.getErrorCode());
//        }
//    }
//}