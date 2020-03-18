/*
 * 描述： <描述>
 * 修改人： rain
 * 修改时间： 2015年12月2日
 * 项目： tx-component-file
 */
package com.tx.component.file.context;

import org.springframework.beans.factory.FactoryBean;

/**
 * 文件容器工厂类
 * 
 * @author rain
 * @version [版本号, 2015年12月2日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class FileContextFactory extends FileContextImpl
        implements FactoryBean<FileContextImpl> {
    
    /**
     * 文件容器工厂<br/>
     * @return
     * @throws Exception
     */
    @Override
    public FileContextImpl getObject() throws Exception {
        if (FileContextFactory.context == null) {
            return this;
        } else {
            return FileContextFactory.context;
        }
    }
    
    /**
     * 对象类型
     * @return
     */
    @Override
    public Class<?> getObjectType() {
        return FileContextImpl.class;
    }
    
    /**
     * 是否单例
     * @return
     */
    @Override
    public boolean isSingleton() {
        return true;
    }
    
}
