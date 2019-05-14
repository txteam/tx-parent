/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2019年5月9日
 * <修改描述:>
 */
package com.tx.component.security.role.model;

/**
 * 角色<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2019年5月9日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface Role {
    
    /**
     * 获取角色唯一键<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    String getId();
    
    /**
     * 获取角色类型<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    String getRoleType();
    
    /**
     * 获取角色备注<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    default String getRemark() {
        return "";
    }
}
