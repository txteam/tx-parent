/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2014年12月24日
 * <修改描述:>
 */
package com.tx.component.file.viewhandler.impl;

import java.io.File;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.core.io.FileSystemResource;
import org.springframework.util.StringUtils;

import com.tx.component.file.FileContextConstants;
import com.tx.component.file.model.FileDefinition;
import com.tx.component.file.resource.FileResource;
import com.tx.component.file.resource.impl.SystemFileResource;
import com.tx.component.file.util.ThumbnailImageUtils;
import com.tx.component.file.viewhandler.AbstractViewHandler;
import com.tx.core.exceptions.SILException;
import com.tx.core.exceptions.util.AssertUtils;

/**
 * 缩略图资源ViewHandler处理器<br/>
 * <功能详细描述>
 * 
 * @author Administrator
 * @version [版本号, 2014年12月24日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class ThumbnailViewHandler extends AbstractViewHandler {
    
    /** 系统文件资源驱动 */
    private String thumbnailLocation;
    
    /**
     * 初始化<br/>
     */
    @Override
    protected void init() {
        String location = this.fileContext.getLocation();
        AssertUtils.notEmpty(location, "location is empty.");
        location = StringUtils.cleanPath(location);//整理location中"\\"为"/"
        while (location.endsWith("/")) {
            //去除location中尾部存在的"/"
            location = location.substring(0, location.length() - 1);
        }
        AssertUtils.notEmpty(location, "path is empty.");
        if (!location.endsWith("/")) {
            //追加"/"
            location = location + "/";
        }
        this.thumbnailLocation = location + "thumbnail/";
    }
    
    /**
     * @return
     */
    @Override
    public final String name() {
        return FileContextConstants.VIEWHANDLER_THUMBNAIL;
    }
    
    /**
      * 缩略图文件路径<br/>
      * <功能详细描述>
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    private String thumbnailFilePath(FileDefinition fileDefinition) {
        String thumbnailFilename = (new StringBuilder(this.thumbnailLocation)).append(fileDefinition.getModule())
                .append("/")
                .append(fileDefinition.getRelativePath())
                .toString();
        return thumbnailFilename;
    }
    
    /**
     * @param fileDefinition
     * @param request
     * @param path
     * @param fileId
     * @param viewHandlerName
     * @return
     */
    @Override
    public FileResource getResource(FileDefinition fileDefinition,
            HttpServletRequest request, String path, String fileId,
            String viewHandlerName) {
        String cacheParameter = request.getParameter("cache");
        String widthParameter = request.getParameter("width");
        String heightParameter = request.getParameter("height");
        boolean isCache = StringUtils.isEmpty(cacheParameter) ? true
                : BooleanUtils.toBoolean(cacheParameter);
        int width = NumberUtils.toInt(widthParameter, 0);
        int height = NumberUtils.toInt(heightParameter, 0);
        
        //查看
        FileResource fdResource = null;
        String thumbnailFilePath = thumbnailFilePath(fileDefinition);
        if (isCache) {
            FileSystemResource fileSystemResource = new FileSystemResource(
                    thumbnailFilePath);
            if (fileSystemResource.exists()) {
                fdResource = new SystemFileResource(fileDefinition,
                        fileSystemResource);
                return fdResource;
            }
        }
        
        File thumbnailFile = null;
        InputStream sourceImageInputStream = null;
        try {
            FileResource fileResource = this.fileContext.getResourceById(fileId);
            sourceImageInputStream = fileResource.getInputStream();
            
            thumbnailFile = ThumbnailImageUtils.getThumbnailOrBlowImage(thumbnailFilePath,
                    StringUtils.getFilenameExtension(fileDefinition.getFilename()),
                    width,
                    height,
                    sourceImageInputStream,
                    isCache);
        } catch (Exception ioe) {
            throw new SILException(ioe.getMessage(), ioe);
        } finally {
            IOUtils.closeQuietly(sourceImageInputStream);
        }
        
        if (thumbnailFile == null) {
            return null;
        }
        FileSystemResource fileSystemResource = new FileSystemResource(
                thumbnailFile);
        fdResource = new SystemFileResource(fileDefinition, fileSystemResource);
        
        return fdResource;
    }
}
