/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-10-10
 * <修改描述:>
 */
package com.tx.component.basicdata.plugin.impl;

import java.lang.reflect.Method;

import com.tx.component.basicdata.executor.BasicDataExecutor;
import com.tx.component.basicdata.plugin.BaseBasicDataExecutorPlugin;
import com.tx.core.exceptions.util.AssertUtils;

/**
 * 支持警用执行器插件
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2013-10-10]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class SupportDisabledExecutorPlugin extends BaseBasicDataExecutorPlugin {

    /**
     * @return
     */
    @Override
    public int getOrder() {
        return 0;
    }
    
    /**
     * @param proxy
     * @param method
     * @param args
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args)
            throws Throwable {
        String methodName = method.getName();
        if(METHOD_NAME_EXECUTE.equals(methodName)){
            String process = (String)args[0];
            if("disable".equals(process)){
                
                //getBasicDataExecutor().update(params);
            }
        }
        
        return null;
    }
    
    /**
     * @param type
     * @return
     */
    @Override
    public <T> boolean isSupportType(Class<T> type) {
        AssertUtils.notNull(type, "type is null");
        
        if (SupportDisabled.class.isAssignableFrom(type)) {
            return true;
        }
        return false;
    }
    
}
