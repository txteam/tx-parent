/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2014年12月21日
 * <修改描述:>
 */
package com.tx.component.file.driver;

import org.springframework.beans.factory.InitializingBean;

import com.tx.component.file.model.FileDefinition;
import com.tx.component.file.resource.FileResource;

/**
 * 文件定义资源驱动<br/>
 * 
 * @author Administrator
 * @version [版本号, 2014年12月21日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public interface FileDefinitionResourceDriver extends InitializingBean {
    
    /**
     * 获取资源对象<br/>
     * 
     * @param fileDefinition 文件定义
     * @return FileDefinitionResource 资源对象
     * @exception [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public FileResource getResource(FileDefinition fileDefinition);
}
