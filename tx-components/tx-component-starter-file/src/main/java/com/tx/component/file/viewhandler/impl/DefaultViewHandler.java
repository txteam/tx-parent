/*
+ * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2014年12月23日
 * <修改描述:>
 */
package com.tx.component.file.viewhandler.impl;

import javax.servlet.http.HttpServletRequest;

import com.tx.component.file.FileContextConstants;
import com.tx.component.file.model.FileDefinition;
import com.tx.component.file.resource.FileResource;
import com.tx.component.file.viewhandler.AbstractViewHandler;

/**
 * 图片资源HttpRequest处理器<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2014年12月23日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class DefaultViewHandler extends AbstractViewHandler {
    
    /**
     * @return
     */
    @Override
    public final String name() {
        return FileContextConstants.VIEWHANDLER_DEFAULT;
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
        FileResource fileResource = this.fileContext.getResourceById(fileId);
        return fileResource;
    }
}
