/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2020年2月28日
 * <修改描述:>
 */
package com.tx.component.file.resource;

import com.tx.component.file.model.FileDefinition;

/**
 * 文件资源加载器<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2020年2月28日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface FileResourceLoader {
    
    /**
     * 获取文件定义<br/>
     * <功能详细描述>
     * @param fd
     * @return [参数说明]
     * 
     * @return FileResource [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public FileResource getResource(FileDefinition fd);
}
