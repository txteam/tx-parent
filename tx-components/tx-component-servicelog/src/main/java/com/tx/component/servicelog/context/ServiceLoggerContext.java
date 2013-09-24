/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-9-24
 * <修改描述:>
 */
package com.tx.component.servicelog.context;

import org.springframework.beans.factory.InitializingBean;

import com.tx.core.exceptions.util.AssertUtils;

/**
 * 业务日志容器<br/>
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2013-9-24]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class ServiceLoggerContext extends ServiceLoggerFactory implements
        InitializingBean {
    
    private static ServiceLoggerContext context;
    
    /** <默认构造函数> */
    protected ServiceLoggerContext() {
        super();
    }
    
    /**
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        super.afterPropertiesSet();
        context = this;
    }
    
    protected static ServiceLoggerContext getContext() {
        AssertUtils.notNull(ServiceLoggerContext.context,
                "serviceLogContext not init.");
        
        return ServiceLoggerContext.context;
    }
}
