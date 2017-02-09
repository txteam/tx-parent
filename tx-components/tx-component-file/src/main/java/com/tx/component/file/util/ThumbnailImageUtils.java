/*
 * 描          述:  <描述>
 * 修  改   人:  Rain.he
 * 修改时间:  2015年3月22日
 * <修改描述:>
 */
package com.tx.component.file.util;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.util.StringUtils;

import com.tx.core.exceptions.util.ExceptionWrapperUtils;

/**
 * 缩略图工具<br/>
 * 
 * @author Rain.he
 * @version [版本号, 2015年3月22日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class ThumbnailImageUtils {
    
    /** 日志记录器 */
    private static Logger logger = LoggerFactory.getLogger(ThumbnailImageUtils.class);
    
    /** 图片格式 */
    public final static String DEFAULT_IMAGE_EXTENSION = "jpg";
    
    /** 任务池中的任务数量 */
    public final static int POOLSIZE = 10;
    
    /** 任务池 */
    private static ThreadPoolTaskExecutor tpte = null;
    
    static {
        tpte = new ThreadPoolTaskExecutor();
        tpte.setCorePoolSize(POOLSIZE);
        tpte.setMaxPoolSize(POOLSIZE);
        tpte.afterPropertiesSet();
    }
    
    private final static int LOCK_COUNT = 32;
    
    /** 锁 ,最多只能同时进行32张图片的压缩 */
    private final static Object[] LOCKS = new Object[LOCK_COUNT];
    
    static {
        for (int i = 0; i < LOCK_COUNT; i++) {
            LOCKS[i] = new Object();
        }
    }
    
    /**
     * 
     * 获取缩放图<br/>
     * 如果缩放图文件不存在,则根据缩放图文件名加锁后创建缩放图<br/>
     * <ul>
     * 缩放规则
     * <li>width, height同时小于0则不进行缩放</li>
     * <li>width 小于等于0, height 小于等于1则根据 height 等比缩放高宽</li>
     * <li>width 小于等于0, height 大于1则根据 height 缩放高,根据新旧 height 比例压缩高</li>
     * <li>height 小于等于0, width 小于等于1则根据 width 等比缩放高宽</li>
     * <li>height 小于等于0, width 大于1则根据 width 缩放宽,根据新旧 width 比例压缩宽</li>
     * <li>width 小于等于1, height 小于等于1则根据 width, height 比例缩放高宽</li>
     * <li>width 小于等于1, height 大于1则根据 width 比例缩放宽, 直接根据 height 指定高</li>
     * <li>height 小于等于1, width 小于等于1则根据 width, height 比例缩放高宽</li>
     * <li>height 小于等于1, width 大于1则根据 height 比例缩放高, 直接根据 width 指定宽</li>
     * </ul>
     * 
     * 
     * @param thumbnailFilePath　缩略图输入流
     * @param fileExtention 文件扩展名
     * @param width 压缩宽
     * @param height 压缩高
     * @param sourceImageInputStream 图片文件输入流
     * @param isCache 是否支撑缓存
     *            
     * @return File 缩略图文件
     * @exception IOException 图片读取失败或者缩略图生成失败
     * @see [类、类#方法、类#成员]
     */
    public static File getThumbnailOrBlowImage(String thumbnailFilePath,
            String filenameExtension, float width, float height,
            InputStream sourceImageInputStream, boolean isCache) {
        //如果原文件不存在，直接返回null
        if (sourceImageInputStream == null) {
            return null;
        }
        
        File thumbnailFile = new File(thumbnailFilePath); // 缩略图文件
        // 如果缩略图存在,则直接返回
        if (isCache && thumbnailFile.exists() && thumbnailFile.isFile()) {
            return thumbnailFile;
        }
        
        // 如果缩略图不存在,则创建
        BufferedImage sourceImage = null;
        InputStream inputStream = null;
        try {
            sourceImage = ImageIO.read(sourceImageInputStream);
            int sourceWidth = sourceImage.getWidth();
            int sourceHeight = sourceImage.getHeight();
            
            int resultWidth = sourceWidth;
            int resultHeight = sourceHeight;
            if (width <= 0 && height <= 0) {
                //宽高均小于等于0不进行压缩
                resultWidth = (int) (sourceWidth);
                resultHeight = (int) (sourceHeight);
            } else if (height <= 0) {
                //根据指定宽度进行压缩
                if (width <= 1) {
                    //根据比例进行压缩
                    resultWidth = (int) (sourceWidth * width);
                    resultHeight = (int) (sourceHeight * width);
                } else {
                    resultWidth = (int) width;
                    resultHeight = (int) (sourceHeight * (width / sourceWidth));
                }
            } else if (width <= 0) {
                //根据指定高度进行压缩
                if (height <= 1) {
                    resultWidth = (int) (sourceWidth * height);
                    resultHeight = (int) (sourceHeight * height);
                } else {
                    resultHeight = (int) height;
                    resultWidth = (int) (sourceWidth * (height / sourceHeight));
                }
            } else {
                if (width <= 1) {
                    //根据比例进行压缩
                    resultWidth = (int) (sourceWidth * width);
                } else {
                    resultWidth = (int) width;
                }
                if (height <= 1) {
                    //根据比例进行压缩
                    resultHeight = (int) (sourceHeight * height);
                } else {
                    resultHeight = (int) height;
                }
            }
            
            thumbnailFile = doBulidThumbnailImage(sourceImage,
                    thumbnailFilePath,
                    filenameExtension,
                    resultWidth,
                    resultHeight,
                    isCache);
        } catch (Exception ioe) {
            logger.error("生成缩略图失败.", ioe);
            return null;
        } finally {
            IOUtils.closeQuietly(inputStream);
        }
        return thumbnailFile;
    }
    
    /**
     * 生成缩略图<br/>
     *
     * @param thumbnailFilePath 缩略图路径
     * @param image
     * @param width
     * @param height
     * @throws IOException
     *             
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     * @version [版本号, 2015年11月30日]
     * @author rain
     */
    public static void bulidThumbnailImage(final String thumbnailFilePath,
            final String imageExtension, final BufferedImage image,
            final int width, final int height, final boolean isCache)
            throws IOException {
        // 初始化任务池
        tpte.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    doBulidThumbnailImage(image,
                            thumbnailFilePath,
                            imageExtension,
                            width,
                            height,
                            isCache);
                } catch (IOException ioe) {
                    ExceptionWrapperUtils.wrapperIOException(ioe,
                            "缩略图生成失败,path : ",
                            thumbnailFilePath);
                }
                
            }
        });
    }
    
    /**
     * 
     * 构建缩略图
     *
     * @param image 原始图
     * @param thumbnailFilePath 缩略图路径
     * @param imageExtension 缩略图格式"例如:jpg,png"
     * @param thumbImageWidth 缩略图宽
     * @param thumbImageHeight 缩略图高
     *            
     * @return File 缩略图
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     * @version [版本号, 2015年11月30日]
     * @author rain
     */
    private static File doBulidThumbnailImage(BufferedImage image,
            String thumbnailFilePath, String imageExtension,
            int thumbImageWidth, int thumbImageHeight, boolean isCache)
            throws IOException {
        imageExtension = StringUtils.isEmpty(imageExtension) ? DEFAULT_IMAGE_EXTENSION
                : imageExtension;
        // 根据缩略图路径哈希值进行锁定
        synchronized (getLock(thumbnailFilePath)) {
            //如果等待期间，缩略图已经存在，所以需要在此重新判断缩略图文件是否存在
            File thumbnailFile = new File(thumbnailFilePath); // 缩略图文件
            // 如果缩略图存在,则直接返回
            if (isCache && thumbnailFile.exists() && thumbnailFile.isFile()) {
                return thumbnailFile;
            }
            
            // 创建缩略图文件夹路径
            try {
                FileUtils.forceMkdir(thumbnailFile.getParentFile());
            } catch (IOException e) {
            }
            
            // 创建缩略图
            Image thumbImage = image.getScaledInstance(thumbImageWidth,
                    thumbImageHeight,
                    Image.SCALE_SMOOTH);
            
            // 生成缩略图画布
            BufferedImage bufImg = new BufferedImage(thumbImage.getWidth(null),
                    thumbImage.getHeight(null), BufferedImage.TYPE_INT_RGB);
            Graphics g = bufImg.createGraphics(); // 创建原始图画板
            g.drawImage(thumbImage, 0, 0, null); // 把原始图画到缩略图
            g.dispose();
            
            // 保存缩略图
            ImageIO.write(bufImg, imageExtension, thumbnailFile);
            
            return thumbnailFile;
        }
    }
    
    /**
     * 
     * 根据缩略图全路径获取锁<br/>
     * 
     * @param thumbnailFilePath 缩略图全路径
     *            
     * @return Object 锁
     * @exception [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    private static Object getLock(String thumbnailFilePath) {
        int hashCode = thumbnailFilePath.hashCode();
        return LOCKS[Math.abs(hashCode) % LOCKS.length];
    }
}
