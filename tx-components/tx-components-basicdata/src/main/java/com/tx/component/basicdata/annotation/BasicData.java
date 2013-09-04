/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-8-14
 * <修改描述:>
 */
package com.tx.component.basicdata.annotation;

import com.tx.component.basicdata.executor.BasicDataExecutor;
import com.tx.component.basicdata.executor.DefaultBasicDataExecutor;

/**
 * 基础数据模型<br/>
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2013-8-14]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public @interface BasicData {
    
    /**
      * 基础数据对应表名
      *<功能详细描述>
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public String tableName() default "";
    
    /**
      * 基础数据类型别名<br/>
      *<功能详细描述>
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public String name();
    
    /**
      * 是否可编辑<br/>
      *<功能详细描述>
      * @return [参数说明]
      * 
      * @return boolean [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public boolean isModifyAble();
    
    /**
      * 默认的查询条件
      * 例如可以设置   type = x这样的条件
      *<功能详细描述>
      * @return [参数说明]
      * 
      * @return String[] [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public String[] queryCondition() default {};
    
    /**
      * 基础数据的默认执行器类型<br/>
      *<功能详细描述>
      * @return [参数说明]
      * 
      * @return Class<BasicDataExecutor> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    @SuppressWarnings("rawtypes")
    public Class<? extends BasicDataExecutor> executor() default DefaultBasicDataExecutor.class;
}
