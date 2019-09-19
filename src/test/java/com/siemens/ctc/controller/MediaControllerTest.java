package com.siemens.ctc.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class MediaControllerTest extends BaseController {

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
    public void upload() {
    }

    @Test
    public void getMediaByPath() {
    }

    @Test
    public void getMediaNum() {
    }

    @Test
    public void getNoneRestrictMedia() {
    }

    @Test
    public void getNoneRestrictMediaWithOrder() {
    }

    @Test
    public void getAllRestrictMedia() {
    }

    @Test
    public void download() {
    }

    @Test
    public void updateThumbnail() {
    }

    @Test
    public void mediaEdit() {
    }

    @Test
    public void deleteMedia() {
    }
}