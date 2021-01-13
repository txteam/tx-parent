///*
// * 描          述:  <描述>
// * 修  改   人:  PengQingyang
// * 修改时间:  2020年12月27日
// * <修改描述:>
// */
//package com.tx.core.springmvc.path;
//
//import java.util.List;
//
//import org.springframework.boot.autoconfigure.security.SecurityProperties;
//import org.springframework.boot.web.servlet.DelegatingFilterProxyRegistrationBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.core.Ordered;
//
///**
// * contextPath可替换配置<br/>
// * <功能详细描述>
// * 
// * @author  PengQingyang
// * @version  [版本号, 2020年12月27日]
// * @see  [相关类/方法]
// * @since  [产品/模块版本]
// */
//public class ContextPathReplaceableAutoConfiguration {
//    
//    /** 请求contextPath可替换过滤器 */
//    private static final String REQUEST_CONTEXT_PATH_REPLACEABLE_FILTER_NAME = "REQUEST_CONTEXT_PATH_REPLACEABLE_FILTER";
//    
//    /**
//     * filter注册Bean实例<br/>
//     * <功能详细描述>
//     * @param securityProperties
//     * @return [参数说明]
//     * 
//     * @return DelegatingFilterProxyRegistrationBean [返回类型说明]
//     * @exception throws [异常类型] [异常说明]
//     * @see [类、类#方法、类#成员]
//     */
//    @Bean
//    public DelegatingFilterProxyRegistrationBean requestContextPathReplaceableFilterRegistration(
//            SecurityProperties securityProperties) {
//        DelegatingFilterProxyRegistrationBean registration = new DelegatingFilterProxyRegistrationBean(
//                REQUEST_CONTEXT_PATH_REPLACEABLE_FILTER_NAME);
//        registration.setOrder(Ordered.HIGHEST_PRECEDENCE);
//        return registration;
//    }
//    
//    /**
//     * 注入ContextPathReplaceHandler的所有实现<br/>
//     * <功能详细描述>
//     * @param handlers
//     * @return [参数说明]
//     * 
//     * @return RequestContextPathReplaceableFilter [返回类型说明]
//     * @exception throws [异常类型] [异常说明]
//     * @see [类、类#方法、类#成员]
//     */
//    @Bean(name = REQUEST_CONTEXT_PATH_REPLACEABLE_FILTER_NAME)
//    public RequestContextPathReplaceableFilter requestContextPathReplaceableFilter(
//            List<ContextPathReplaceHandler> handlers) {
//        RequestContextPathReplaceableFilter filter = new RequestContextPathReplaceableFilter(
//                handlers);
//        return filter;
//    }
//}
