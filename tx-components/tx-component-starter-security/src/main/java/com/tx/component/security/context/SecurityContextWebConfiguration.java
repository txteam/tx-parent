//package com.tx.component.security.context;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.boot.autoconfigure.security.SecurityProperties;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.Ordered;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.builders.WebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.web.authentication.switchuser.SwitchUserFilter;
//
//import com.tx.component.security.filter.SecurityThreadLocalResourceSupportFilter;
//
///**
// * SpringSecurity本地权限定制<br/>
// * <功能详细描述>
// * 
// * @author  Administrator
// * @version  [版本号, 2018年7月7日]
// * @see  [相关类/方法]
// * @since  [产品/模块版本]
// */
//@Configuration
//public class SecurityContextWebConfiguration
//        extends WebSecurityConfigurerAdapter implements Ordered {
//    
//    /** 日志记录句柄 */
//    protected static Logger logger = LoggerFactory
//            .getLogger(SecurityContextWebConfiguration.class);
//    
//    /** security容器 */
//    private SecurityThreadLocalResourceSupportFilter stlrsFilter = new SecurityThreadLocalResourceSupportFilter();
//    
//    /**
//     * webSecurity配置
//     * @param web
//     * @throws Exception
//     */
//    @Override
//    public void configure(WebSecurity web) throws Exception {
//    }
//    
//    /**
//     * httpSecurity配置
//     * @param httpSecurity
//     * @throws Exception
//     */
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.antMatcher("/**").authorizeRequests().anyRequest().authenticated();
//        http.addFilterAfter(stlrsFilter, SwitchUserFilter.class);
//    }
//    
//    /**
//     * 用户验证
//     * @param auth
//     * @throws Exception
//     */
//    @Override
//    protected void configure(AuthenticationManagerBuilder builder)
//            throws Exception {
//    }
//    
//    @Override
//    public int getOrder() {
//        return SecurityProperties.BASIC_AUTH_ORDER - 1;
//    }
//}
