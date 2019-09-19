//package com.siemens.ctc.controller;
//
//import com.alibaba.fastjson.JSONObject;
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.MvcResult;
//import org.springframework.test.web.servlet.RequestBuilder;
//import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.web.context.WebApplicationContext;
//
//import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//
///**
// * UserController Test
// *
// * @author Fan Yang
// * createTime: 2019-08-19
// */
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//@AutoConfigureMockMvc
//public class UserControllerTest extends BaseController {
//
//    @Autowired
//    private MockMvc mvc;
//
//    @Autowired
//    private WebApplicationContext context;
//
//    private RequestBuilder request;
//
//    @Before
//    public void setup() {
//        this.mvc = MockMvcBuilders.webAppContextSetup(this.context).apply(springSecurity()).build();
//    }
//
//    @WithMockUser(roles = {"admin"})
//    @Test
//    public void getUser() throws Exception {
//        //通过Email查询用户
//        request = get("/ctc/user/get")
//                .param("email", "admin@siemens.com")
//                .accept(MediaType.APPLICATION_JSON);
//        MvcResult mvcResult = mvc.perform(request)
//                .andDo(MockMvcResultHandlers.print())
//                .andReturn();
//        String content = mvcResult.getResponse().getContentAsString();
//        JSONObject resultModel = JSONObject.parseObject(content);
//        Assert.assertEquals("admin", resultModel.getString("username"));
//    }
//
//    @WithMockUser(roles = {"admin"})
//    @Test
//    public void getAllUsers() throws Exception {
//        request = get("/ctc/user/getAll")
//                .param("pageNum", "1")
//                .param("pageSize", "0")
//                .accept(MediaType.APPLICATION_JSON);
//        MvcResult mvcResult = mvc.perform(request)
//                .andDo(MockMvcResultHandlers.print())
//                .andReturn();
//        String content = mvcResult.getResponse().getContentAsString();
//        JSONObject resultModel = JSONObject.parseObject(content);
//        Assert.assertEquals("0", resultModel.getString("code"));
//    }
//
//    @WithMockUser(roles = {"admin"})
//    @Test
//    public void addUser() throws Exception {
//
//        // 添加未注册用户
//        request = post("/ctc/user/add")
//                .param("name", "test")
//                .param("email", "test@siemens.com")
//                .param("department", "ct")
//                .param("role", "admin")
//                .accept(MediaType.APPLICATION_JSON);
//        MvcResult mvcResult = mvc.perform(request)
//                .andDo(MockMvcResultHandlers.print())
//                .andReturn();
//        String content = mvcResult.getResponse().getContentAsString();
//        JSONObject resultModel = JSONObject.parseObject(content);
//        Assert.assertEquals("0", resultModel.getString("code"));
//
//        // 添加已注册用户
//        request = post("/ctc/user/add")
//                .param("email", "admin@siemens.com")
//                .param("name", "")
//                .param("role", "admin")
//                .param("department", "")
//                .accept(MediaType.APPLICATION_JSON_UTF8);
//        MvcResult mvcResult1 = mvc.perform(request)
//                .andDo(MockMvcResultHandlers.print())
//                .andReturn();
//        String content1 = mvcResult1.getResponse().getContentAsString();
//        JSONObject resultModel1 = JSONObject.parseObject(content1);
//        Assert.assertEquals("1", resultModel1.getString("code"));
//
//        // 添加空邮箱用户
//        request = post("/ctc/user/add")
//                .param("email", "")
//                .param("name", "")
//                .param("role", "admin")
//                .param("department", "")
//                .accept(MediaType.APPLICATION_JSON_UTF8);
//        MvcResult mvcResult2 = mvc.perform(request)
//                .andDo(MockMvcResultHandlers.print())
//                .andReturn();
//        String content2 = mvcResult2.getResponse().getContentAsString();
//        JSONObject resultModel2 = JSONObject.parseObject(content2);
//        Assert.assertEquals("1", resultModel2.getString("code"));
//
//        // 添加角色不是admin和restrict的用户
//        request = post("/ctc/user/add")
//                .param("email", "admin2@siemens.com")
//                .param("name", "")
//                .param("role", "normal")
//                .param("department", "")
//                .accept(MediaType.APPLICATION_JSON_UTF8);
//        MvcResult mvcResult3 = mvc.perform(request)
//                .andDo(MockMvcResultHandlers.print())
//                .andReturn();
//        String content3 = mvcResult3.getResponse().getContentAsString();
//        JSONObject resultModel3 = JSONObject.parseObject(content3);
//        Assert.assertEquals("1", resultModel3.getString("code"));
//    }
//
//    @WithMockUser(roles = {"admin"})
//    @Test
//    public void deleteUser() throws Exception {
//
//        // 删除已注册用户
//        request = delete("/ctc/user/delete")
//                .param("email", "test@siemens.com")
//                .accept(MediaType.APPLICATION_JSON);
//        MvcResult mvcResult = mvc.perform(request)
//                .andDo(MockMvcResultHandlers.print())
//                .andReturn();
//        String content = mvcResult.getResponse().getContentAsString();
//        JSONObject resultModel = JSONObject.parseObject(content);
//        Assert.assertEquals("0", resultModel.getString("code"));
//
//        // 删除邮箱地址错误用户
//        request = delete("/ctc/user/delete")
//                .param("email", "xxxx@siemens.com")
//                .accept(MediaType.APPLICATION_JSON);
//        MvcResult mvcResult1 = mvc.perform(request)
//                .andDo(MockMvcResultHandlers.print())
//                .andReturn();
//        String content1 = mvcResult1.getResponse().getContentAsString();
//        JSONObject resultModel1 = JSONObject.parseObject(content1);
//        Assert.assertEquals("1", resultModel1.getString("code"));
//
//        // 删除空邮箱用户
//        request = delete("/ctc/user/delete")
//                .param("email", "xxxx@siemens.com")
//                .accept(MediaType.APPLICATION_JSON);
//        MvcResult mvcResult2 = mvc.perform(request)
//                .andDo(MockMvcResultHandlers.print())
//                .andReturn();
//        String content2 = mvcResult2.getResponse().getContentAsString();
//        JSONObject resultModel2 = JSONObject.parseObject(content2);
//        Assert.assertEquals("1", resultModel2.getString("code"));
//    }
//
//    @WithMockUser(roles = {"admin"})
//    @Test
//    public void updateUser() throws Exception {
//
//        //更新已注册用户信息（角色admin）
//        request = put("/ctc/user/update")
//                .param("email", "admin1@siemens.com")
//                .param("newRole", "restrict")
//                .param("name", "admin1")
//                .param("department", "iot")
//                .accept(MediaType.APPLICATION_JSON_UTF8);
//        MvcResult mvcResult = mvc.perform(request)
//                .andDo(MockMvcResultHandlers.print())
//                .andReturn();
//        String content = mvcResult.getResponse().getContentAsString();
//        JSONObject resultModel = JSONObject.parseObject(content);
//        Assert.assertEquals("0", resultModel.getString("code"));
//
//        // 更新已注册用户信息（角色不是admin或restrict）
//        request = put("/ctc/user/update")
//                .param("email", "admin1@siemens.com")
//                .param("newRole", "normal")
//                .param("name", "admin1")
//                .param("department", "iot")
//                .accept(MediaType.APPLICATION_JSON_UTF8);
//        MvcResult mvcResult3 = mvc.perform(request)
//                .andDo(MockMvcResultHandlers.print())
//                .andReturn();
//        String content3 = mvcResult3.getResponse().getContentAsString();
//        JSONObject resultModel3 = JSONObject.parseObject(content3);
//        Assert.assertEquals("1", resultModel3.getString("code"));
//
////
//        // 更新未注册用户信息
//        request = put("/ctc/user/update")
//                .param("email", "xxxxx@siemens.com")
//                .param("newRole", "admin")
//                .param("name", "xxxx")
//                .param("department", "xxxx")
//                .accept(MediaType.APPLICATION_JSON_UTF8);
//        MvcResult mvcResult1 = mvc.perform(request)
//                .andDo(MockMvcResultHandlers.print())
//                .andReturn();
//        String content1 = mvcResult1.getResponse().getContentAsString();
//        JSONObject resultModel1 = JSONObject.parseObject(content1);
//        Assert.assertEquals("1", resultModel1.getString("code"));
//
//        // 更新空邮箱地址用户信息
//        request = put("/ctc/user/update")
//                .param("email", "xxxxx@siemens.com")
//                .param("newRole", "admin")
//                .param("name", "xxxx")
//                .param("department", "xxxx")
//                .accept(MediaType.APPLICATION_JSON_UTF8);
//        MvcResult mvcResult2 = mvc.perform(request)
//                .andDo(MockMvcResultHandlers.print())
//                .andReturn();
//        String content2 = mvcResult2.getResponse().getContentAsString();
//        JSONObject resultModel2 = JSONObject.parseObject(content2);
//        Assert.assertEquals("1", resultModel2.getString("code"));
//    }
//
//    @WithMockUser(roles = {"admin"})
//    @Test
//    public void updatePassword() throws Exception {
//
//        // 更新密码成功
//        request = put("/ctc/user/updatePwd")
//                .param("email", "normal@siemens.com")
//                .param("newPassword", "normal")
//                .accept(MediaType.APPLICATION_JSON_UTF8);
//        MvcResult mvcResult = mvc.perform(request).andDo(MockMvcResultHandlers.print())
//                .andReturn();
//        String content = mvcResult.getResponse().getContentAsString();
//        JSONObject resultModel = JSONObject.parseObject(content);
//        Assert.assertEquals("0", resultModel.getString("code"));
//
//        // 更新密码失败
//        request = put("/ctc/user/updatePwd")
//                .param("email", "xxxx@siemens.com")
//                .param("newPassword", "normal")
//                .accept(MediaType.APPLICATION_JSON_UTF8);
//        MvcResult mvcResult1 = mvc.perform(request).andDo(MockMvcResultHandlers.print())
//                .andReturn();
//        String content1 = mvcResult1.getResponse().getContentAsString();
//        JSONObject resultModel1 = JSONObject.parseObject(content1);
//        Assert.assertEquals("1", resultModel1.getString("code"));
//    }
//}