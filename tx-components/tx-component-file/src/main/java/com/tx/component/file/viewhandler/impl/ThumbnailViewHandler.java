/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2014年12月24日
 * <修改描述:>
 */
package com.tx.component.file.viewhandler.impl;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import com.tx.component.file.util.ThumbnailImageUtils;
import com.tx.component.file.viewhandler.ViewHandler;
import com.tx.core.exceptions.util.ExceptionWrapperUtils;

/**
 * 缩略图资源ViewHandler处理器<br/>
 * <功能详细描述>
 * 
 * @author Administrator
 * @version [版本号, 2014年12月24日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class ThumbnailViewHandler extends ViewHandler {
    
    private float width = 100;
    
    private float height = 100;
    
    /**
     * @return
     */
    @Override
    public String viewHandlerName() {
        return "thumbnail";
    }
    
    /**
     * @param request
     * @return
     */
    @Override
    protected Resource getResource(HttpServletRequest request) {
        String fileDefinitionId = request.getParameter("fileDefinitionId");
        if (StringUtils.isEmpty(fileDefinitionId)) {
            fileDefinitionId = request.getParameter("fileId");
        }
        if (StringUtils.isEmpty(fileDefinitionId)) {
            return null;
        }
        
        //根据fileDefinitionId获取对应的资源
        Resource resource = fileContext.getResourceById(fileDefinitionId);
        if (!resource.exists()) {
            return null;
        }
        File thumbnaileImageFile = null;
        try {
            thumbnaileImageFile = ThumbnailImageUtils.getThumbnailOrBlowImage(resource.getFile(),
                    this.width,
                    this.height);
        } catch (IOException ioe) {
            throw ExceptionWrapperUtils.wrapperIOException(ioe,
                    ioe.getMessage());
        }
        if (thumbnaileImageFile == null) {
            return null;
        }
        
        return new FileSystemResource(thumbnaileImageFile);
    }
    
    /**
     * @param 对width进行赋值
     */
    public void setWidth(float width) {
        this.width = width;
    }
    
    /**
     * @param 对height进行赋值
     */
    public void setHeight(float height) {
        this.height = height;
    }
}
