/*
 * 描          述:  <描述>
 * 修  改   人:  
 * 修改时间:  
 * <修改描述:>
 */
package com.tx.component.role.dao;

import java.util.List;

import com.tx.component.role.model.RoleRefItem;
import com.tx.core.mybatis.dao.MybatisBaseDao;

/**
 * RoleRefItem持久层
 * <功能详细描述>
 * 
 * @author  
 * @version  [版本号, ]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface RoleRefItemDao extends MybatisBaseDao<RoleRefItem, String> {
    
    /**
     * 插入RoleRefItem对象实体
     * 1、auto generate
     * <功能详细描述>
     * @param condition [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public void insertToHis(RoleRefItem roleRef);
    
    /**
     * 批量插入权限项引用
     * <功能详细描述>
     * @param condition [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public void batchInsertToHis(List<RoleRefItem> roleRefs);
}
