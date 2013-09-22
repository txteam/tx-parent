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

import com.tx.component.basicdata.valuegenerator.DefaultValueGenerator;
import com.tx.component.basicdata.valuegenerator.ValueGenerator;

/**
 * 基础数据属性定义<br/>
 *     添加了该注解的字段，才会出现在界面列表，以及增加界面中
 *     利用该注解，指定字段意义，以及验证规则
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2013-8-29]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD })
public @interface BasicDataProperty {
    
    /**
      * 基础数据名<br/>
      *     如果不制定，则默认使用数据字段名<br/>
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public String name();
    
    /**
     * 在界面显示时默认的显示位置<br/>
     *<功能详细描述>
     * @return [参数说明]
     * 
     * @return int [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    public int order();
    
    /**
     * 基础数据对应字段名
     *     如果不为empty则优先使用该字段
     *<功能详细描述>
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
   public String column() default "";
   
   /**
     * 是否是可插入字段<br/>
     *<功能详细描述>
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
   public boolean isInsertAble() default true;
   
   /**
     * 是否是可更新字段<br/>
     *<功能详细描述>
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
   public boolean isUpdateAble() default true;
    
    /**
      * 验证表达式
      *<功能详细描述>
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public String validateExpression() default "";
    
    /**
      * 提示信息<br/>
      *<功能详细描述>
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public String tipMessage() default "";
    
    /**
      * 验证不通过时错误提示信息
      *<功能详细描述>
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public String errorMessage() default "";
    
    /**
      * 是否能够编辑<br/>
      *     比如主键字段<br/>
      *     这些字段不能由用户填写，就需要指定对应的属性自动生成器<br/>
      *<功能详细描述>
      * @return [参数说明]
      * 
      * @return boolean [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public boolean isModifyAble() default true;
    
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
      * 在界面中是否可见<br/>
      *<功能详细描述>
      * @return [参数说明]
      * 
      * @return boolean [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public boolean isVisible() default true;
    
    /**
     * 默认的值生成器
     *<功能详细描述>
     * @return [参数说明]
     * 
     * @return Class<ValueGenerator> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
   @SuppressWarnings("rawtypes")
   public Class<? extends ValueGenerator> valueGenerator() default DefaultValueGenerator.class;
}
