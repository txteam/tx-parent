/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2016年10月4日
 * <修改描述:>
 */
package com.tx.component.basicdata.context;

import org.springframework.beans.factory.FactoryBean;

/**
 * 基础数据容器工厂类<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2016年10月4日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class BasicDataContextFactory extends BasicDataContext implements
        FactoryBean<BasicDataContext> {
    
    /**
     * @return
     * @throws Exception
     */
    @Override
    public BasicDataContext getObject() throws Exception {
        if (BasicDataContextFactory.context == null) {
            return this;
        } else {
            return BasicDataContextFactory.context;
        }
    }
    
    /**
     * @return
     */
    @Override
    public Class<?> getObjectType() {
        return BasicDataContext.class;
    }
    
    /**
     * @return
     */
    @Override
    public boolean isSingleton() {
        return true;
    }
    
}
