/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2016年10月6日
 * <修改描述:>
 */
package com.tx.core.cache.context;

import org.springframework.beans.factory.FactoryBean;

/**
 * <功能简述>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2016年10月6日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class CacheContextFactory extends CacheContext implements
        FactoryBean<CacheContext> {
    
    /**
     * @return
     * @throws Exception
     */
    @Override
    public CacheContext getObject() throws Exception {
        return getContext();
    }
    
    /**
     * @return
     */
    @Override
    public Class<?> getObjectType() {
        return CacheContext.class;
    }
    
    /**
     * @return
     */
    @Override
    public boolean isSingleton() {
        return true;
    }
}
