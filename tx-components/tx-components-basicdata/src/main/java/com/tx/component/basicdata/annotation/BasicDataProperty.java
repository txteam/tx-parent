/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-8-29
 * <修改描述:>
 */
package com.tx.component.basicdata.annotation;


/**
 * 基础数据属性定义<br/>
 *     可指定对应属性在界面显示时是否被显示<br/>
 *     可指定是否为查询条件<br/>
 *     对应数据库字段名<br/>
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2013-8-29]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public @interface BasicDataProperty {
    
    /**
      * 数据字段别名<br/>
      *<功能详细描述>
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public String alias();
    
    /**
      * 数据字段是否可见<br/>
      *<功能详细描述>
      * @return [参数说明]
      * 
      * @return boolean [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public boolean isVisible() default false;
    
    /**
      * 是否为查询条件<br/>
      *<功能详细描述>
      * @return [参数说明]
      * 
      * @return boolean [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public boolean isQueryCondition() default false;
    
    /**
      * 对应数据库字段名<br/>
      *<功能详细描述>
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public String columnName() default "";
}
