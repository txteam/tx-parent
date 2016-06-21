/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2016年6月19日
 * <修改描述:>
 */
package com.tx.core.jdbc.tableview.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 表视图注解<br/>
 *     有该注解的对象，将在系统启动期间启动<br/>
 *     最好在系统中对该对象驱动配置独立的用户名，具备另一数据库的读取权限<br/>
 *     那么启动期间就可以自动创建视图<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2016年6月19日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE })
@Inherited
public @interface TableView {
    
    public String viewSql() default "";
    
    public String sourceDatasource() default "";
    
    public String sourceTableName() default "";
    
    public String tableViewName();
}
