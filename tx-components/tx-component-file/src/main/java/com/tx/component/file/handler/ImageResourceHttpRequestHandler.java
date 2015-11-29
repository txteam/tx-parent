/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2014年12月23日
 * <修改描述:>
 */
package com.tx.component.file.handler;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import com.tx.component.file.context.FileContext;

/**
 * 图片资源HttpRequest处理器<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2014年12月23日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class ImageResourceHttpRequestHandler extends ResourceHttpRequestHandler {
    
    /** 文件容器 */
    private FileContext fileContext;
    
    /**
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        //覆写该方法去除locations必填的设定
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
        Resource resource = fileContext.getResourceByFileDefinitionId(fileDefinitionId);
        if (resource == null || !resource.exists()) {
            return null;
        }
        //返回图片资源
        return resource;
    }
    
    /**
     * @return 返回 fileContext
     */
    public FileContext getFileContext() {
        return fileContext;
    }
    
    /**
     * @param 对fileContext进行赋值
     */
    public void setFileContext(FileContext fileContext) {
        this.fileContext = fileContext;
    }
    
}
