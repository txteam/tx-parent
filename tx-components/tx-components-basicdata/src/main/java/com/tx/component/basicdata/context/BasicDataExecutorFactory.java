/*
 * 描          述:  <描述>
 * 修  改   人:  PengQingyang
 * 修改时间:  2013-10-2
 * <修改描述:>
 */
package com.tx.component.basicdata.context;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.InitializingBean;

import com.tx.component.basicdata.executor.BasicDataExecutor;

/**
 * 基础数据执行器工厂类
 * <功能详细描述>
 * 
 * @author  PengQingyang
 * @version  [版本号, 2013-10-2]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class BasicDataExecutorFactory extends BasicDataContextConfigurator
        implements InitializingBean {
    
    private static Map<Class<?>, BasicDataExecutor<?>> basicDataExecutorMapping = new HashMap<Class<?>, BasicDataExecutor<?>>();
    
    public static <TYPE> BasicDataExecutor<TYPE> getExecutor(Class<TYPE> type) {
        return null;
    }
    
    /**
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        
    }
}
