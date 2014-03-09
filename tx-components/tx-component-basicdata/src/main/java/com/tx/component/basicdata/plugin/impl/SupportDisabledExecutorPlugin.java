/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-10-10
 * <修改描述:>
 */
package com.tx.component.basicdata.plugin.impl;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        Object resObj = null;
        if (METHOD_NAME_EXECUTE.equals(methodName)) {
            String process = (String) args[0];
            if ("disable".equals(process)) {
                resObj = disable(args);
            } else if ("enable".equals(process)) {
                resObj = enable(args);
            } 
        }else if(METHOD_NAME_LIST.equals(methodName)){
            resObj = listValid();
        }else{
            resObj = method.invoke(getBasicDataExecutor(), args);
        }
        
        System.out.println(resObj);
        System.out.println(resObj != null ? resObj.getClass() : "null");
        return resObj;
    }
    
    /**
      * 查询所有有效的数据
      *<功能详细描述>
      * @return [参数说明]
      * 
      * @return List [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    @SuppressWarnings("rawtypes")
    public List listValid(){
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("valid", true);
        List res = getBasicDataExecutor().query(params);
        return res;
    }
    
    /**
      * 禁用
      *<功能详细描述> [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    private boolean disable(Object[] args) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("valid", false);
        params.put(getJpaMetaClass().getPkGetterName(), args[0]);
        return getBasicDataExecutor().update(params);
        
    }
    
    /**
      * 启用
      *<功能详细描述>
      * @param args [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    private boolean enable(Object[] args) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("valid", true);
        params.put(getJpaMetaClass().getPkGetterName(), args[0]);
        return getBasicDataExecutor().update(params);
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
