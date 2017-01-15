/*
+ * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2014年12月23日
 * <修改描述:>
 */
package com.tx.component.file.viewhandler.impl;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;

import com.tx.component.file.resource.FileDefinitionResource;
import com.tx.component.file.viewhandler.ViewHandler;
import com.tx.core.exceptions.SILException;

/**
 * 图片资源HttpRequest处理器<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2014年12月23日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class DefaultViewHandler extends ViewHandler {
    
    /**
     * @return
     */
    @Override
    public String viewHandlerName() {
        return "default";
    }
    
    /**
     * @param request
     * @return
     */
    @Override
    protected Resource getResource(HttpServletRequest request) {
        String fileId = request.getParameter("fileDefinitionId");
        if (StringUtils.isEmpty(fileId)) {
            fileId = request.getParameter("fileId");
        }
        if (StringUtils.isEmpty(fileId)) {
            return null;
        }
        
        //根据fileDefinitionId获取对应的资源
        FileDefinitionResource resource = fileContext.getResourceById(fileId);
        if (resource == null || !resource.exists()) {
            return null;
        }
        
        InputStream inputStream = null;
        try {
            inputStream = resource.getInputStream();
        } catch (IOException e) {
            IOUtils.closeQuietly(inputStream);
            throw new SILException(e.getMessage(), e);
        }
        Resource inputStreamResource = new InputStreamResource(inputStream);
        //返回图片资源
        return inputStreamResource;
    }
}
