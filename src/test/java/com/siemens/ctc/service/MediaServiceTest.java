package com.siemens.ctc.service;

import com.github.pagehelper.PageInfo;
import com.siemens.ctc.model.Media;
import com.siemens.ctc.tools.exception.BusinessException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MediaServiceTest {

    @Resource
    private MediaService mediaService;

    /**
     * 上传文件信息
     */

    @Test
    public void upload() {

        // 上传成功
        Media media = new Media();
        media.setPath("/siemens/cengjingdeni.mp4");
        media.setCategory("event");
        media.setOwner("ct");
        media.setAuthority("restrict");
        int count = mediaService.upload(media);
        Assert.assertEquals(1, count);

        // 上传失败
        try {
            mediaService.upload(media);
        } catch (BusinessException e) {
            Assert.assertEquals("E116", e.getErrorCode());
        }

    }

    /**
     * 根据路径获取文件
     */

    @Test
    public void getByPath() {

        //文件获取成功
        Media media = mediaService.getByMediaId(1);
        Assert.assertNotNull(media);

        //文件获取失败
        try {
            mediaService.getByMediaId(33);
        } catch (BusinessException e) {
            Assert.assertEquals("E118", e.getErrorCode());
        }
    }

    /**
     * 获取非特权文件，默认按照最新最热排序
     */

    @Test
    public void findNoneRestrictMedia() {
//        PageInfo<Media> mediaPageInfo = mediaService.findNoneRestrictMedia(1, 0);
//        Assert.assertNotNull(mediaPageInfo);

    }

    /**
     * 获取非特权文件，默认按照order排序（最新或最热）
     */

    @Test
    public void findNoneRestrictMediaByOrder() {
//        PageInfo<Media> mediaPageInfo = mediaService.findNoneRestrictMediaByOrder(1, 0, "view_num");
//        Assert.assertNotNull(mediaPageInfo);
    }

    /**
     * 获取所有文件
     */

    @Test
    public void findAllMedia() {
//        PageInfo<Media> mediaPageInfo = mediaService.findAllMedia(1, 0);
//        Assert.assertNotNull(mediaPageInfo);
    }

//    /**
//     * 获取指定文件夹下的所有文件
//     */
//
//    @Test
//    public void getByCategory() {
//        PageInfo<Media> mediaPageInfo = mediaService.getByCategory("event", 1, 0);
//        Assert.assertNotNull(mediaPageInfo);
//    }

    /**
     * 浏览量加1
     */

    @Test
    public void plusViewNum() {
        mediaService.plusViewNum(1);
        Media media = mediaService.getByMediaId(1);
        Assert.assertEquals(3, media.getViewNum());
    }

    /**
     * 下载量加1
     */

    @Test
    public void plusDownloadNum() {
        mediaService.plusDownloadNum(1);
        Media media = mediaService.getByMediaId(1);
        Assert.assertEquals(1, media.getDownloadNum());
    }

    /**
     * 文件重命名
     */

    @Test
    public void rename() {
        mediaService.rename(1, "cengjing.mp4");
        Media media = mediaService.getByMediaId(1);
        Assert.assertEquals("cengjing.mp4", media.getMediaName());
    }

    /**
     * 删除文件
     */

    @Test
    public void delete() {
        int count = mediaService.delete(1);

    }

    /**
     * 更新文件信息
     */

    @Test
    public void update() {
    }

    /**
     * 获取当前置顶数
     */

    @Test
    public void pinNum() {
    }

    /**
     * 获取文件数量
     */

    @Test
    public void getMediaNum() {
    }

    /**
     * 路径是否存在
     */

    @Test
    public void isPathExist() {
    }
}