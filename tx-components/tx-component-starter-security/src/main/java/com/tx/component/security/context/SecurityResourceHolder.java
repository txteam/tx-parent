/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2019年11月24日
 * <修改描述:>
 */
package com.tx.component.security.context;

/**
 * 权限容器初始化后资源处理器<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2019年11月24日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface SecurityResourceHolder {
    
    /**
     * 重置资源<br/>
     * <功能详细描述> [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    void init();
    
    /**
     * 移除资源<br/>
     *<功能详细描述> [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    void clear();
}
