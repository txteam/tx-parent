/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-8-14
 * <修改描述:>
 */
package com.tx.component.basicdata.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.tx.component.basicdata.model.BasicDataViewTypeEnum;

/**
 * 基础数据模型<br/>
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2013-8-14]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE })
@Inherited
public @interface BasicDataEntity {
    
    /**
     * 基础数据归属模块<br/>
     *    如果为空，则默认为当前项目的基础数据<br/>
     *    如果在公共引用包中，应该指定其为哪一个模块<br/>
     *    在远程调用时，将会识别该对象对应的模块，从而获取数据<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    String module() default "";
    
    /**
     * 基础数据名称<br/>
     *    如果为空，默认使用类名（全名）
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    String type() default "";
    
    /**
     * 基础数据名称<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    String name();
    
    /**
     * 基础数据类型备注<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    String remark() default "";
    
    /**
     * 显示类型(默认为非分页，可选择分页显示)<br/>
     * 
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    BasicDataViewTypeEnum viewType() default BasicDataViewTypeEnum.COMMON_LIST;
}
