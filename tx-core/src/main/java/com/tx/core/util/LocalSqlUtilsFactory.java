/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2014年2月24日
 * <修改描述:>
 */
package com.tx.core.util;

import org.springframework.beans.factory.FactoryBean;

/**
 * 本地sql工具工厂<br/>
 * 
 * @author  Administrator
 * @version  [版本号, 2014年2月24日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class LocalSqlUtilsFactory extends LocalSqlUtils implements
        FactoryBean<LocalSqlUtils> {
    
    /**
     * @return
     * @throws Exception
     */
    @Override
    public LocalSqlUtils getObject() throws Exception {
        return LocalSqlUtils.instance;
    }
    
    /**
     * @return
     */
    @Override
    public Class<?> getObjectType() {
        return LocalSqlUtils.class;
    }
    
    /**
     * @return
     */
    @Override
    public boolean isSingleton() {
        return true;
    }
    
}
