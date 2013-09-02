/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-8-29
 * <修改描述:>
 */
package com.tx.component.basicdata.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


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
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface BasicDataProperty {
    
    /**
     * 数据库字段名<br/>
     *     如果不填写，则默认使用数据字段名<br/>
     *<功能详细描述>
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
   public String value() default "";
   
   /**
     * 基础数据名<br/>
     *     如果不制定，则默认使用数据字段名<br/>
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
   public String name() default "";
   
   /**
     * 指定字段在显示阶段是否隐藏<br/>
     *     默认不影藏<br/> 
     *<功能详细描述>
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
   public boolean isHidden() default false;
   
   /**
     * 指定属性项是否可编辑<br/>
     *<功能详细描述>
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
   public boolean isModifyAble() default false;
   
   /**
     * 是否不用进行持久<br/>
     *<功能详细描述>
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
   public boolean isOmit() default false;
   
   /**
     * 是否是排序字段
     *<功能详细描述>
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
   public boolean isOrderColumn() default false;
   
   /**
     * 在界面显示时默认的显示位置<br/>
     *<功能详细描述>
     * @return [参数说明]
     * 
     * @return int [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
   public int order() default 0;
}
