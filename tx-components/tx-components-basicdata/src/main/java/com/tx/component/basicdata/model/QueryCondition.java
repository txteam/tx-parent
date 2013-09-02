/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-9-2
 * <修改描述:>
 */
package com.tx.component.basicdata.model;

/**
 * 查询条件
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2013-9-2]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class QueryCondition {
    
    /**
     * 前置信息(and,or,and exist ...)
     */
    private String prepend;
    
    /**
     * 条件  = , like ,in ...
     */
    private String condition;
    
}
