package com.siemens.ctc.controller;

import com.alibaba.fastjson.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private WebApplicationContext context;

    private RequestBuilder request;

    @Before
    public void setup() {
        this.mvc = MockMvcBuilders.webAppContextSetup(this.context).apply(springSecurity()).build();
    }


    @Test
    public void createAuthenticationToken() throws Exception {
        request = post("/ctc/auth/login")
                .param("username", "admin")
                .param("password", "admin")
                .accept(MediaType.APPLICATION_JSON_UTF8);
        MvcResult mvcResult = mvc
                .perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        JSONObject resultModel = JSONObject.parseObject(content);
        Assert.assertEquals("0", resultModel.getString("code"));

        request = post("/ctc/auth/login")
                .param("username", "xxxx") // 无此用户
                .param("password", "admin")
                .accept(MediaType.APPLICATION_JSON_UTF8);
        MvcResult mvcResult1 = mvc
                .perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        String content1 = mvcResult1.getResponse().getContentAsString();
        JSONObject resultModel1 = JSONObject.parseObject(content1);
        Assert.assertEquals("1", resultModel1.getString("code"));

        request = post("/ctc/auth/login")
                .param("username", "admin")
                .param("password", "xxxxx") // 密码错误
                .accept(MediaType.APPLICATION_JSON_UTF8);
        MvcResult mvcResult2 = mvc
                .perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        String content2 = mvcResult2.getResponse().getContentAsString();
        JSONObject resultModel2 = JSONObject.parseObject(content2);
        Assert.assertEquals("1", resultModel2.getString("code"));
    }
}