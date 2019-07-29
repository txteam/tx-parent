/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2012-11-30
 * <修改描述:>
 */
package com.tx.component.security.context;

import com.tx.core.exceptions.util.AssertUtils;

/**
 * 权限容器<br/>
 * 功能详细描述<br/>
 *     下一版本，将把权限注册的相关逻辑抽取接口AuthRegister从AuthContext中移出<br/>
 * 
 * @author PengQingyang
 * @version [版本号, 2012-12-1]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class SecurityContext extends SecurityContextBuilder{
    /* 不需要进行注入部分属性 */
    
    /** 单子模式权限容器唯一实例 */
    protected static SecurityContext context;
    
    /**
     * <默认构造函数>
     * 构造函数级别为子类可见<br/>
     */
    protected SecurityContext() {
    }
    
    /**
      * 获取权限容器实例
      *<功能详细描述>
      * @return [参数说明]
      * 
      * @return AuthContext [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public static SecurityContext getContext() {
        if (SecurityContext.context != null) {
            return SecurityContext.context;
        }
        synchronized (SecurityContext.class) {
            SecurityContext.context = applicationContext.getBean(beanName,
                    SecurityContext.class);
        }
        AssertUtils.notNull(SecurityContext.context, "context is null.");
        return SecurityContext.context;
    }
    
    /**
     * @throws Exception
     */
    @Override
    protected void doInitContext() throws Exception {
        // TODO Auto-generated method stub
        
    }
    
    /**
     * @return
     */
    public boolean hasAuth() {
        // TODO Auto-generated method stub
        return false;
    }
    
    /**
     * @return
     */
    public boolean hasRole() {
        // TODO Auto-generated method stub
        return false;
    }
    
    /**
     * @return
     */
    public boolean hasAuthority() {
        // TODO Auto-generated method stub
        return false;
    }
    
    /**
     * @return
     */
    public boolean access() {
        // TODO Auto-generated method stub
        return false;
    }
    
}
