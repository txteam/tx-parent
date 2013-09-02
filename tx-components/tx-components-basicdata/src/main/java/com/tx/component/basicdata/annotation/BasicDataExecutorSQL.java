/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-9-2
 * <修改描述:>
 */
package com.tx.component.basicdata.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


 /**
  * 基础数据执行器sql
  * <功能详细描述>
  * 
  * @author  brady
  * @version  [版本号, 2013-9-2]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface BasicDataExecutorSQL {
    
    /**
      * 插入基础数据的sql
      *<功能详细描述>
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public String insertSql();
    
    /**
      * 更新基础数据的sql
      *<功能详细描述>
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public String updateSql();
    
    /**
      * 查询基础数据的sql
      *<功能详细描述>
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public String querySql();
    
    /**
      * 删除基础数据的sql
      *<功能详细描述>
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public String deleteSql();
    
    /**
      * 查询单个基础数据的sql
      *<功能详细描述>
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public String findSql();
    
    /**
      * 查询总条数的sql
      *<功能详细描述>
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public String countSql();
}
