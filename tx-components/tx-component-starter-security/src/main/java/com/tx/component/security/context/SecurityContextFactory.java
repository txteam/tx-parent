/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-9-4
 * <修改描述:>
 */
package com.tx.component.security.context;

import org.springframework.beans.factory.FactoryBean;

/**
 * 权限容器工厂类<br/>
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2013-9-4]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class SecurityContextFactory extends SecurityContext
        implements FactoryBean<SecurityContext> {
    
    /**
     * @return
     * @throws Exception
     */
    @Override
    public SecurityContext getObject() throws Exception {
        if (SecurityContextFactory.context == null) {
            return this;
        } else {
            return SecurityContextFactory.context;
        }
    }
    
    /**
     * @return
     */
    @Override
    public Class<?> getObjectType() {
        return SecurityContext.class;
    }
    
    /**
     * @return
     */
    @Override
    public boolean isSingleton() {
        return true;
    }
}
