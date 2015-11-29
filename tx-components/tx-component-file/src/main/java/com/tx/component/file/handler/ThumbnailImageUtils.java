/*
 * 描          述:  <描述>
 * 修  改   人:  Rain.he
 * 修改时间:  2015年3月22日
 * <修改描述:>
 */
package com.tx.component.file.handler;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.tx.core.exceptions.util.ExceptionWrapperUtils;

/**
 * 图片工具<br/>
 * 
 * @author Rain.he
 * @version [版本号, 2015年3月22日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class ThumbnailImageUtils {
    
    private static Logger logger = LoggerFactory.getLogger(ThumbnailImageUtils.class);
    
    /** 图片格式 */
    public final static String IMAGE_EXTENSION = "jpg";
    
    /** 任务池中的任务数量 */
    public final static int POOLSIZE = 8;
    
    /** 任务池 */
    private static ThreadPoolTaskExecutor tpte = null;
    
    static {
        tpte = new ThreadPoolTaskExecutor();
        tpte.setCorePoolSize(POOLSIZE);
        tpte.setMaxPoolSize(POOLSIZE);
        tpte.afterPropertiesSet();
    }
    
    private final static int LOCK_COUNT = 32;
    
    /** 锁 ,最多只能同时进行16张图片的压缩 */
    private final static Object[] LOCKS = new Object[LOCK_COUNT];
    
    static {
        for (int i = 0; i < LOCK_COUNT; i++) {
            LOCKS[i] = new Object();
        }
    }
    
    /**
     * 
     * 获取缩略图<br/>
     * 如果文件不存在,则根据缩略图文件名加锁后创建缩略图<br/>
     * 
     * @param imageFile 图片文件
     * 
     * @return File 缩略图文件
     * @exception IOException 图片读取失败或者缩略图生成失败
     * @see [类、类#方法、类#成员]
     */
    public static File getThumbnailImage(File imageFile, float width,
            float height) {
        //如果原文件不存在，直接返回null
        if (!imageFile.exists()) {
            return null;
        }
        String thumbnailFilePath = getThumbnailImagePathBySourceImageFile(imageFile); // 获取缩略图全路径
        
        File thumbnailFile = new File(thumbnailFilePath); // 缩略图文件
        
        // 如果缩略图存在,则直接返回
        if (thumbnailFile.exists() && thumbnailFile.isFile()) {
            return thumbnailFile;
        }
        
        // 如果缩略图不存在,则创建
        BufferedImage sourceImage = null;
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(imageFile);
            sourceImage = ImageIO.read(inputStream);
            int sourceWidth = sourceImage.getWidth();
            int sourceHeight = sourceImage.getHeight();
            
            int resultWidth = sourceWidth;
            int resultHeight = sourceHeight;
            if (width < 0 && height < 0) {
                //宽高均小于0不进行压缩
                return imageFile;
            } else if (height < 0) {
                //根据指定宽度进行压缩
                if (width < 1) {
                    //根据比例进行压缩
                    resultWidth = (int) (sourceWidth * width);
                    resultHeight = (int) (sourceHeight * width);
                } else {
                    resultWidth = (int) width;
                    resultHeight = (int) (sourceHeight * (width / sourceWidth));
                }
            } else if (width < 0) {
                //根据指定高度进行压缩
                if (height < 1) {
                    resultWidth = (int) (sourceWidth * height);
                    resultHeight = (int) (sourceHeight * height);
                } else {
                    resultHeight = (int) height;
                    resultWidth = (int) (sourceWidth * (height / sourceHeight));
                }
            } else {
                if (width < 1) {
                    //根据比例进行压缩
                    resultWidth = (int) (sourceWidth * width);
                } else {
                    resultWidth = (int) width;
                }
                if (height < 1) {
                    //根据比例进行压缩
                    resultHeight = (int) (sourceHeight * height);
                } else {
                    resultHeight = (int) height;
                }
            }
            
            thumbnailFile = doBulidThumbnailImage(sourceImage,
                    thumbnailFilePath,
                    IMAGE_EXTENSION,
                    resultWidth,
                    resultHeight);
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
     * @param imageFile 图片文件
     * 
     * @exception IOException 图片读取失败或者缩略图生成失败
     * @see [类、类#方法、类#成员]
     */
    public static void bulidThumbnailImage(final String thumbnailFilePath,
            final BufferedImage image, final int width, final int height)
            throws IOException {
        // 初始化任务池
        tpte.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    doBulidThumbnailImage(image,
                            thumbnailFilePath,
                            IMAGE_EXTENSION,
                            width,
                            height);
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
     * 生成缩略图<br/>
     * 
     * @param imageFile 图片资源
     * @param thumbnailFile 缩略图资源
     * @param imageExtension 缩略图格式"例如:  jpg    png"
     * @param thumbImageWidth 缩略图宽
     * @param thumbImageHeight 缩略图高
     * 
     * @return void
     * @exception IOException 图片读取失败或者缩略图生成失败
     * @see [类、类#方法、类#成员]
     */
    private static File doBulidThumbnailImage(BufferedImage image,
            String thumbnailFilePath, String imageExtension,
            int thumbImageWidth, int thumbImageHeight) throws IOException {
        
        // 根据缩略图路径进行锁定
        synchronized (getLock(thumbnailFilePath)) {
            //如果等待期间，缩略图已经存在，所以需要在此重新判断缩略图文件是否存在
            File thumbnailFile = new File(thumbnailFilePath); // 缩略图文件
            // 如果缩略图存在,则直接返回
            if (thumbnailFile.exists() && thumbnailFile.isFile()) {
                return thumbnailFile;
            }
            
            // 创建缩略图文件夹路径
            try {
                FileUtils.forceMkdir(thumbnailFile.getParentFile());
            } catch (IOException e) {
            }
            
            // 读取原始图片
            Image thumbImage = image.getScaledInstance(thumbImageWidth,
                    thumbImageHeight,
                    Image.SCALE_SMOOTH);
            
            // 生成缩略图画布
            BufferedImage bufImg = new BufferedImage(thumbImage.getWidth(null),
                    thumbImage.getHeight(null), BufferedImage.TYPE_INT_RGB);
            Graphics g = bufImg.createGraphics();
            g.drawImage(thumbImage, 0, 0, null);
            g.dispose();
            
            // 保存缩略图
            ImageIO.write(bufImg, imageExtension, thumbnailFile);
            
            return thumbnailFile;
        }
    }
    
    /**
     * 
     * 从图片文件获取缩略图全路径<br/>
     * 
     * @param imageFile 图片文件
     * 
     * @return String 缩略图路径
     * @exception [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    private static String getThumbnailImagePathBySourceImageFile(File imageFile) {
        String directoryName = imageFile.getParent(); // 文件所在文件夹
        String fileName = imageFile.getName(); // 文件名
        String fileNameSuffix = StringUtils.substringBeforeLast(fileName, "."); // 文件名(没有后缀)
        
        String thumbnailFilePath = directoryName + "/" + fileNameSuffix
                + "_thumbnail." + IMAGE_EXTENSION; // 缩略图文件名
        
        return thumbnailFilePath;
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
