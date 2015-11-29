/*
 * 描          述:  <描述>
 * 修  改   人:  Rain.he
 * 修改时间:  2015年3月22日
 * <修改描述:>
 */
package com.tx.component.file.handler;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
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
public class ImageUtils {
    
    /** 缩略图宽 */
    public final static int THUMB_IMAGE_WIDTH = 100;
    
    /** 缩略图高 */
    public final static int THUMB_IMAGE_HEIGHT = 100;
    
    /** 图片格式 */
    public final static String IMAGE_EXTENSION = "jpg";
    
    /** 任务池中的任务数量 */
    public final static int POOLSIZE = 5;
    
    /** 任务池 */
    private static ThreadPoolTaskExecutor tpte = null;
    static {
        tpte = new ThreadPoolTaskExecutor();
        tpte.setCorePoolSize(POOLSIZE);
        tpte.setMaxPoolSize(POOLSIZE);
        tpte.afterPropertiesSet();
    }
    
    /** 锁 ,最多只能同时进行16张图片的压缩 */
    private final static Object[] LOCKS = new Object[16];
    
    static {
        for (int i = 0; i < 16; i++) {
            LOCKS[i] = new Object();
        }
    }
    
    /**
     * 
     * 旋转图片
     * 
     * @param file 文件
     * @param angel 旋转角度
     * 
     * @return void [返回类型说明]
     * @exception [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static void rotate(File file, Angel angel) {
        BufferedImage src = null;
        try {
            src = ImageIO.read(file);
        } catch (IOException ioe) {
            ExceptionWrapperUtils.wrapperIOException(ioe,
                    "读取图片失败,path : ",
                    file.getPath());
        }
        try {
            BufferedImage des = rotate(src, angel.getAngle());
            ImageIO.write(des, IMAGE_EXTENSION, file);
        } catch (IOException ioe) {
            ExceptionWrapperUtils.wrapperIOException(ioe,
                    "保存旋转后的图片失败,path : ",
                    file.getPath());
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
    public static File getThumbnailImage(File imageFile) {
        String thumbnailFilePath = getThumbnailImagePathFromImageFile(imageFile); // 获取缩略图全路径
        
        File thumbnailFile = new File(thumbnailFilePath); // 缩略图文件
        
        // 如果缩略图存在,则直接返回
        if (thumbnailFile.exists() && thumbnailFile.isFile()) {
            return thumbnailFile;
        }
        
        // 如果缩略图不存在,则创建
        try {
            thumbnailFile = doBulidThumbnailImage(imageFile,
                    thumbnailFilePath,
                    IMAGE_EXTENSION,
                    THUMB_IMAGE_WIDTH,
                    THUMB_IMAGE_HEIGHT);
        } catch (IOException ioe) {
            ExceptionWrapperUtils.wrapperIOException(ioe,
                    "缩略图生成失败,path : ",
                    thumbnailFilePath);
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
    public static void bulidThumbnailImage(final File imageFile)
            throws IOException {
        // 初始化任务池
        tpte.execute(new Runnable() {
            @Override
            public void run() {
                String thumbnailFilePath = getThumbnailImagePathFromImageFile(imageFile); // 获取缩略图全路径
                try {
                    doBulidThumbnailImage(imageFile,
                            thumbnailFilePath,
                            IMAGE_EXTENSION,
                            THUMB_IMAGE_WIDTH,
                            THUMB_IMAGE_HEIGHT);
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
    private static File doBulidThumbnailImage(File imageFile,
            String thumbnailFilePath, String imageExtension,
            int thumbImageWidth, int thumbImageHeight) throws IOException {
        
        // 根据缩略图路径进行锁定
        synchronized (getLock(thumbnailFilePath)) {
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
            FileInputStream fileInput = new FileInputStream(imageFile);
            BufferedImage image = ImageIO.read(new FileInputStream(imageFile));
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
            
            // 关闭图片流
            IOUtils.closeQuietly(fileInput);
            
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
    private static String getThumbnailImagePathFromImageFile(File imageFile) {
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
    
    /**
     * 
     * 旋转图片
     * 
     * @param src 原图
     * @param angel 旋转角度
     * 
     * @return BufferedImage 旋转后的图片
     * @exception [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static BufferedImage rotate(Image src, int angel) {
        int src_width = src.getWidth(null);
        int src_height = src.getHeight(null);
        // calculate the new image size  
        Rectangle rect_des = calcRotatedSize(new Rectangle(new Dimension(
                src_width, src_height)), angel);
        
        BufferedImage res = null;
        res = new BufferedImage(rect_des.width, rect_des.height,
                BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = res.createGraphics();
        // transform  
        g2.translate((rect_des.width - src_width) / 2,
                (rect_des.height - src_height) / 2);
        g2.rotate(Math.toRadians(angel), src_width / 2, src_height / 2);
        
        g2.drawImage(src, null, null);
        return res;
    }
    
    /**
     * 
     * 计算旋转后的图片尺寸
     * 
     * @param src 原图
     * @param angel 旋转角度
     * 
     * @return Rectangle 旋转后的尺寸
     * @exception [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    private static Rectangle calcRotatedSize(Rectangle src, int angel) {
        // if angel is greater than 90 degree, we need to do some conversion  
        if (angel >= 90) {
            if (angel / 90 % 2 == 1) {
                int temp = src.height;
                src.height = src.width;
                src.width = temp;
            }
            angel = angel % 90;
        }
        
        double r = Math.sqrt(src.height * src.height + src.width * src.width) / 2;
        double len = 2 * Math.sin(Math.toRadians(angel) / 2) * r;
        double angel_alpha = (Math.PI - Math.toRadians(angel)) / 2;
        double angel_dalta_width = Math.atan((double) src.height / src.width);
        double angel_dalta_height = Math.atan((double) src.width / src.height);
        
        int len_dalta_width = (int) (len * Math.cos(Math.PI - angel_alpha
                - angel_dalta_width));
        int len_dalta_height = (int) (len * Math.cos(Math.PI - angel_alpha
                - angel_dalta_height));
        int des_width = src.width + len_dalta_width * 2;
        int des_height = src.height + len_dalta_height * 2;
        return new Rectangle(new Dimension(des_width, des_height));
    }
    
    /**
     * 
     * 旋转角度
     * 
     * @author Rain.he
     * @version [版本号, 2015年5月11日]
     * @see [相关类/方法]
     * @since [产品/模块版本]
     */
    public static enum Angel {
        Left(270),
        
        Right(90), ;
        
        int angle;
        
        public int getAngle() {
            return angle;
        }
        
        Angel(int angle) {
            this.angle = angle;
        }
    }
}
