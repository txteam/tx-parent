/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2018年7月3日
 * <修改描述:>
 */
package com.tx.component.security.starter;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Import;

/**
 * 启用JwtTokenSession<br/>
 *    一般配置于可能存在有状态会话的应用的启动器中<br/>
 *    配置了该类，将在启动器中加载该类下面的配置<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2018年7月3日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(JwtAuthenticationTokenAutoConfiuration.class)
public @interface EnableJwtAuthenticationToken {
    
}
