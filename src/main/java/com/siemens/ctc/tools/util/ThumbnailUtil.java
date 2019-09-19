package com.siemens.ctc.tools.util;

import com.siemens.ctc.config.MyPropsConstants;
import com.siemens.ctc.model.Media;
import com.siemens.ctc.tools.exception.BusinessException;
import com.siemens.ctc.tools.exception.ErrorCodeEnum;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * 生成缩略图
 *
 * @author Fan Yang
 * createTime: 2019-07-23
 */

@Component
public class ThumbnailUtil {

    private static final Logger LOGGER = LogManager.getLogger(ThumbnailUtil.class);

    @Resource
    MyPropsConstants myPropsConstants;

    /**
     * @param media 文件对象
     */
    public void generateThumbnail(Media media) {

        String type = media.getType().toLowerCase();

        switch (type) {
            case "mp4": // video 缩略图
                generateVideoThumbnail(media);
                break;
            case "jpg": // 图片缩略图
                generatePictureThumbnail(media);
                break;
            case "png": // 图片缩略图
                generatePictureThumbnail(media);
                break;
            case "ppt": // ppt 文档缩略图
                generatePPTThumbnail(media);
                break;
            case "pptx": // ppt 文档缩略图
                generatePPTThumbnail(media);
                break;
            case "doc": // word 文档缩略图
                generateDocThumbnail(media);
            case "docx": // word 文档缩略图
                generateDocThumbnail(media);
                break;
            case "pdf": // pdf缩略图
                generatePdfThumbnail(media);
                break;
            default:  // 默认缩略图
                generateDefaultThumbnail(media);
                break;
        }
    }

    /**
     * 生成视频缩略图
     *
     * @param media 文件对象
     */
    private void generateVideoThumbnail(Media media) {
        videoImage(media);
        LOGGER.info("生成缩略图，保存至" + myPropsConstants.getThumbnailBasePath() + media.getThumbnailPath());
    }

    /**
     * 生成PPT缩略图
     *
     * @param media 文件对象
     */
    private void generatePPTThumbnail(Media media) {
        pptImage(media);
        LOGGER.info("生成缩略图，保存至" + myPropsConstants.getThumbnailBasePath() + media.getThumbnailPath());
    }

    /**
     * 生成图片缩略图
     */
    private void generatePictureThumbnail(Media media) {
        pictureImage(media);
        LOGGER.info("生成缩略图，保存至" + myPropsConstants.getThumbnailBasePath() + media.getThumbnailPath());
    }

    /**
     * 生成word文档缩略图
     */
    private void generateDocThumbnail(Media media) {
        wordImage(media);
        LOGGER.info("生成缩略图，保存至" + myPropsConstants.getThumbnailBasePath() + media.getThumbnailPath());

    }

    /**
     * 生成默认的缩略图
     * 带西门子logo的图片
     */
    private void generateDefaultThumbnail(Media media) {
        defaultImage(media);
        LOGGER.info("生成缩略图，保存至" + myPropsConstants.getThumbnailBasePath() + media.getThumbnailPath());
    }

    /**
     * 生成pdf缩略图
     */
    private void generatePdfThumbnail(Media media) {
        pdfImage(media);
        LOGGER.info("生成缩略图，保存至" + myPropsConstants.getThumbnailBasePath() + media.getThumbnailPath());
    }

//  ####################################################################################################################

    /**
     * 生成pdf缩略图
     */
    private void pdfImage(Media media) {
        try {
            File targetFile = new File(myPropsConstants.getThumbnailBasePath() + media.getThumbnailPath());
            if (!targetFile.getParentFile().exists()) {
                targetFile.getParentFile().mkdirs();
            }
            InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("image/pdf_logo.jpg");

            Thumbnails.of(inputStream)
                    .scale(0.5f)
                    .toFile(targetFile);
        } catch (IOException e) {
            LOGGER.error("生成默认缩略图失败： " + e.getMessage());
            throw new BusinessException(ErrorCodeEnum.DEFAULT_PICTURE_ERROR.getErrorCode(), ErrorCodeEnum.DEFAULT_PICTURE_ERROR.getMsg());
        }
    }

    /**
     * 生成图片缩略图
     */

    private void pictureImage(Media media) {
        File targetFile = new File(myPropsConstants.getThumbnailBasePath() + media.getThumbnailPath());
        if (!targetFile.getParentFile().exists()) {
            targetFile.getParentFile().mkdirs();
        }
        try {
            Thumbnails.of(myPropsConstants.getUploadBasePath() + media.getPath()) // 原图
//                    .size(200, 300)
                    .scale(0.5f)
                    .toFile(targetFile); // 缩略图
        } catch (IOException e) {
            LOGGER.error("生成图片缩略图失败：" + e.getMessage());
            throw new BusinessException(ErrorCodeEnum.THUMBNAIL_GENERATION_FAILURE.getErrorCode(), ErrorCodeEnum.THUMBNAIL_GENERATION_FAILURE.getMsg());
        }
    }

    /**
     * 生成word缩略图
     */
    private void wordImage(Media media) {
        try {
            File targetFile = new File(myPropsConstants.getThumbnailBasePath() + media.getThumbnailPath());
            if (!targetFile.getParentFile().exists()) {
                targetFile.getParentFile().mkdirs();
            }
            InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("image/word_logo.jpg");

            Thumbnails.of(inputStream)
                    .scale(0.5f)
                    .toFile(targetFile);
        } catch (IOException e) {
            LOGGER.error("生成默认缩略图失败： " + e.getMessage());
            throw new BusinessException(ErrorCodeEnum.DEFAULT_PICTURE_ERROR.getErrorCode(), ErrorCodeEnum.DEFAULT_PICTURE_ERROR.getMsg());
        }
    }

    /**
     * 生成ppt缩略图
     */

    private void pptImage(Media media) {
        try {
            File targetFile = new File(myPropsConstants.getThumbnailBasePath() + media.getThumbnailPath());
            if (!targetFile.getParentFile().exists()) {
                targetFile.getParentFile().mkdirs();
            }
            InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("image/ppt_logo.jpg");

            Thumbnails.of(inputStream)
                    .scale(0.5f)
                    .toFile(targetFile);
        } catch (IOException e) {
            LOGGER.error("生成默认缩略图失败： " + e.getMessage());
            throw new BusinessException(ErrorCodeEnum.DEFAULT_PICTURE_ERROR.getErrorCode(), ErrorCodeEnum.DEFAULT_PICTURE_ERROR.getMsg());
        }
    }


    /**
     * 生成默认缩略图
     */

    private void defaultImage(Media media) {
        try {
            File targetFile = new File(myPropsConstants.getThumbnailBasePath() + media.getThumbnailPath());
            if (!targetFile.getParentFile().exists()) {
                targetFile.getParentFile().mkdirs();
            }
            InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("image/siemens_logo.jpg");

            Thumbnails.of(inputStream)
                    .scale(0.5f)
                    .toFile(targetFile);
        } catch (IOException e) {
            LOGGER.error("生成默认缩略图失败： " + e.getMessage());
            throw new BusinessException(ErrorCodeEnum.DEFAULT_PICTURE_ERROR.getErrorCode(), ErrorCodeEnum.DEFAULT_PICTURE_ERROR.getMsg());
        }
    }

    /**
     * 生成视频缩略图
     *
     * @param media 文件对象
     */

    private void videoImage(Media media) {

        try {
            FFmpegFrameGrabber ff = FFmpegFrameGrabber.createDefault(myPropsConstants.getUploadBasePath() + media.getPath()); // 存放视频的路径

            ff.start();
            int ffLength = ff.getLengthInFrames();
            Frame f;
            int i = 0;
            while (i < ffLength) {
                f = ff.grabImage();
                // 截取第1帧
                if ((i > 0) && (f.image != null)) {
                    doExecuteFrame(f, myPropsConstants.getThumbnailBasePath() + media.getThumbnailPath()); // 存放缩略图的路径
                    break;
                }
                i++;
            }
            ff.stop();
        } catch (FrameGrabber.Exception e) {
            LOGGER.error("生成视频缩略图失败: " + e.getMessage());
            throw new BusinessException(ErrorCodeEnum.THUMBNAIL_GENERATION_FAILURE.getErrorCode(), ErrorCodeEnum.THUMBNAIL_GENERATION_FAILURE.getMsg());
        }
    }

    private static void doExecuteFrame(Frame f, String targetFilePath) {
        String imageExt = "png";
        if (null == f || null == f.image) {
            return;
        }
        Java2DFrameConverter converter = new Java2DFrameConverter();
        BufferedImage bi = converter.getBufferedImage(f);
        File output = new File(targetFilePath);
        if (!output.getParentFile().exists()) {
            output.getParentFile().mkdirs();
        }
        try {
            ImageIO.write(bi, imageExt, output);
        } catch (IOException e) {
            LOGGER.error("缩略图生成失败: " + e.getMessage());
            throw new BusinessException(ErrorCodeEnum.THUMBNAIL_GENERATION_FAILURE.getErrorCode(), ErrorCodeEnum.THUMBNAIL_GENERATION_FAILURE.getMsg());
        }
    }

}
