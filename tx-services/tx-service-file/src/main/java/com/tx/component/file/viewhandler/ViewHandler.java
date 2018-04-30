/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2014年12月23日
 * <修改描述:>
 */
package com.tx.component.file.viewhandler;

import javax.servlet.http.HttpServletRequest;

import com.tx.component.file.model.FileDefinition;
import com.tx.component.file.resource.FileResource;

/**
 * 图片资源HttpRequest处理器<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2014年12月23日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface ViewHandler {
    
    /**
      * 视图处理器名称<br/>
      * <功能详细描述>
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    String name();
    
    /**
      * 获取文件定义资源<br/>
      * <功能详细描述>
      * @param fileId
      * @param request
      * @param path
      * @return [参数说明]
      * 
      * @return FileDefinitionResource [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    FileResource getResource(FileDefinition fileDefinition,
            HttpServletRequest request, String path, String fileId,
            String viewHandlerName);
}
