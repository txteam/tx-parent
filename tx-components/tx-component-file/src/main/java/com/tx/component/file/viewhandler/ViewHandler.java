/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2014年12月23日
 * <修改描述:>
 */
package com.tx.component.file.viewhandler;

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
public abstract class ViewHandler extends ResourceHttpRequestHandler {
    
    /** 文件容器 */
    protected FileContext fileContext;
    
    /**
      * 视图处理器名称<br/>
      * <功能详细描述>
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public abstract String viewHandlerName();
    
    /**
     * @return 返回 fileContext
     */
    public final FileContext getFileContext() {
        return fileContext;
    }
    
    /**
     * @param 对fileContext进行赋值
     */
    public final void setFileContext(FileContext fileContext) {
        this.fileContext = fileContext;
    }
}
