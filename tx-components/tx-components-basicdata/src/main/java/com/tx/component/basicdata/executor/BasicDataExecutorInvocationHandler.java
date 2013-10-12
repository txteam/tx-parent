/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-10-12
 * <修改描述:>
 */
package com.tx.component.basicdata.executor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import javax.sql.DataSource;

import com.tx.core.exceptions.util.AssertUtils;


 /**
  * <功能简述>
  * <功能详细描述>
  * 
  * @author  brady
  * @version  [版本号, 2013-10-12]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public class BasicDataExecutorInvocationHandler implements InvocationHandler {
    
    private BasicDataExecutor<?> basicDataExecutor;
    
    private DataSource dataSource;
    
    /** <默认构造函数> */
    public BasicDataExecutorInvocationHandler(
            BasicDataExecutor<?> basicDataExecutor) {
        super();
        AssertUtils.notNull(basicDataExecutor,"basicDataExecutor is null");
        AssertUtils.notNull(dataSource,"dataSource is null");
        
        this.basicDataExecutor = basicDataExecutor;
        this.dataSource = dataSource;
    }

    /**
     * @param arg0
     * @param arg1
     * @param arg2
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxyObj, Method method, Object[] args)
            throws Throwable {
        
        Object resObj = method.invoke(basicDataExecutor, args);
        
        return null;
    }
    
}
