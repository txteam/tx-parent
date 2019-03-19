/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-1-19
 * <修改描述:>
 */
package com.tx.component.strategy.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.tx.component.strategy.StrategyConstants;

/**
 * 策略类；方法注解<br/>
 * 具有该注解的方法将被自动环绕代理，并根据策略对其结果进行加工<br/>
 * 该处规则环绕，用以处理方法级规则环绕<br/>
 * 
 * @author  tim
 * @version  [版本号, 2017-4-29]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Target({ ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface StrategyMapping {
    
    /**
      * 请求策略的访问路径<br/>
      * <功能详细描述>
      * @return [参数说明]
      * 
      * @return String[] [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    String value();
    
    /**
      * 策略的备注<br/>
      * <功能详细描述>
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    String remark() default "";
    
    /**
     * 策略名称<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    String name() default StrategyConstants.DEFAULT_STRATEGY_NAME;
    
    /**
     * 规则业务类型
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    public String serviceType() default StrategyConstants.DEFAULT_STRATEGY_SERVICE_TYPE;
}
