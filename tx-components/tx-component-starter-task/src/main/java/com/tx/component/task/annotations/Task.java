/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2017年3月14日
 * <修改描述:>
 */
package com.tx.component.task.annotations;

import static java.lang.annotation.ElementType.METHOD;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 任务<br/>
 *     定义某方法为一个任务<br/>
 *     一旦发现某方法为一个任务。
 *     系统将会自动为该方法添加环绕Aop.
 *     被添加的方法，将会自动记录
 *     更新TaskDefinition(findAndlock),保证不会发生并发调度.
 *     写入执行日志.
 * <功能详细描述>
 * @author  Administrator
 * @version  [版本号, 2017年3月14日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ METHOD })
@Inherited
public @interface Task {
    
    /** 排序值 */
    int order() default 0;
    
    /** 任务关键字 */
    String code() default "";
    
    /** 父级任务编码 */
    String parentCode() default "";
    
    /** 任务名 */
    String name() default "";
    
    /** 任务备注 */
    String remark() default "";
}
