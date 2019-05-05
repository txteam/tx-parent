/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2019年5月5日
 * <修改描述:>
 */
package com.tx.component.configuration.context;

import java.lang.reflect.Method;

import org.springframework.cache.Cache;

import com.tx.core.spring.interceptor.ServiceSupportCacheInterceptor;

/**
 * 基础数据缓存拦截器<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2019年5月5日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class ConfigCacheInterceptor extends ServiceSupportCacheInterceptor {
    
    /** <默认构造函数> */
    public ConfigCacheInterceptor() {
        super();
    }
    
    /** <默认构造函数> */
    public ConfigCacheInterceptor(Cache cache) {
        super(cache);
    }
    
    /**
     * 判断是否是查询方法<br/>
     *    如果是查询方法，将进入缓存处理逻辑<br/>
     * <功能详细描述>
     * @param methodName
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    protected boolean isUseCache(Method method, Object[] args) {
        String methodName = method.getName();
        boolean isQueryMethod = methodName.startsWith("find")
                || methodName.startsWith("query");
        return isQueryMethod;
    }
}
