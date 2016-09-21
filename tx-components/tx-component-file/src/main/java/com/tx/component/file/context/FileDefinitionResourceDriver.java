/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2014年12月21日
 * <修改描述:>
 */
package com.tx.component.file.context;

import java.io.InputStream;

import com.tx.component.file.model.FileDefinition;

/**
 * 文件定义资源驱动<br/>
 * 
 * @author Administrator
 * @version [版本号, 2014年12月21日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public interface FileDefinitionResourceDriver {
    
    /** 文件资源驱动名称 */
    public String driverName();
    
    /**
     * 获取资源对象<br/>
     * 
     * @param fileDefinition 文件定义
     * 
     * @return FileDefinitionResource 资源对象
     * @exception [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public FileDefinitionResource getResource(FileDefinition fileDefinition);
    
    /**
     * 存储文件<br/>
     * 如果文件已经存在，则进行替换<br/>
     * 如果不存在，则新建后进行写入<br/>
     * 如果目录不存在,则自动创建目录<br/>
     * 
     * @param fileDefinition 文件定义
     * @param input 文件流
     * 
     * @return FileDefinitionResource 资源对象
     * @exception [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public FileDefinitionResource save(FileDefinition fileDefinition,
            InputStream input);
    
    /**
     * 新增文件<br/>
     * 如果对应文件已经存在，则抛出异常<br/>
     * 
     * @param fileDefinition 文件定义
     * @param input 文件流
     * 
     * @return FileDefinitionResource 资源对象
     * @exception [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public FileDefinitionResource add(FileDefinition fileDefinition,
            InputStream input);
    
    /**
     * 删除对应的文件资源<br/>
     * 
     * @param fileDefinition [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public void delete(FileDefinition fileDefinition);
}
