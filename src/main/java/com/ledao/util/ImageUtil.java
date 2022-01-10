package com.ledao.util;

import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

/**
 * 图片工具类
 *
 * @author LeDao
 * @company
 * @create 2022-01-10 2:50
 */
public class ImageUtil {

    /**
     * 压缩图片
     *
     * @param image1 要压缩的图片
     * @param image2 生成的图片
     */
    public static void compressImage(File image1, File image2) throws IOException {
        //压缩图片
        Thumbnails.of(image1).scale(1f).outputQuality(0.5f).toFile(image2);
        //删除原图
        FileUtils.deleteQuietly(image1);
    }
}
