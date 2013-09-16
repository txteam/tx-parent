/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-9-16
 * <修改描述:>
 */
package com.tx.component.servicelog.logger;

import java.util.HashMap;
import java.util.Map;

/**
 * 业务日志器注册机<br/>
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2013-9-16]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class ServiceLoggerRegistry {
    
    /**
     * 业务日志记录器<br/>
     */
    private Map<Class<?>, ServiceLogger<?>> serviceLoggerMap = new HashMap<Class<?>, ServiceLogger<?>>();
    
    /**
      * 获取业务日志记录器<br/>
      *<功能详细描述>
      * @param type
      * @return [参数说明]
      * 
      * @return ServiceLogger<T> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public <T> ServiceLogger<T> getServiceLogger(Class<T> type) {
        
        return null;
    }
    
    public void register(Class<?> type) {
        
    }
    
    public void register(String packageName, Class<?> typeInterface) {
        
    }
}
