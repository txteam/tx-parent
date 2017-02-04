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
public class FileContextFactroy extends FileContext implements
        FactoryBean<FileContext> {
    
    @Override
    public FileContext getObject() throws Exception {
        if (FileContextFactroy.context == null) {
            return this;
        } else {
            return FileContextFactroy.context;
        }
    }
    
    @Override
    public Class<?> getObjectType() {
        return FileContext.class;
    }
    
    @Override
    public boolean isSingleton() {
        return true;
    }
    
}
