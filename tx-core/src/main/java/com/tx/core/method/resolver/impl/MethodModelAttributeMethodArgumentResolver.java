///*
// * 描          述:  <描述>
// * 修  改   人:  Administrator
// * 修改时间:  2014年4月20日
// * <修改描述:>
// */
//package com.tx.core.method.resolver.impl;
//
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
//import org.springframework.beans.BeanUtils;
//import org.springframework.core.MethodParameter;
//import org.springframework.core.Ordered;
//
//import com.tx.core.method.annotation.MethodModelAttribute;
//import com.tx.core.method.request.InvokeRequest;
//import com.tx.core.method.resolver.MethodArgumentResolver;
//
///**
// * 参数具@EventListenerParam注解的方法参数解析器<br/>
// * <功能详细描述>
// * 
// * @author  Administrator
// * @version  [版本号, 2014年4月20日]
// * @see  [相关类/方法]
// * @since  [产品/模块版本]
// */
//public class MethodModelAttributeMethodArgumentResolver
//        implements MethodArgumentResolver {
//    
//    /** 日志记录器 */
//    protected final Log logger = LogFactory.getLog(getClass());
//    
//    /** 是否必须存在对应的注解 */
//    private final boolean annotationNotRequired;
//    
//    /**
//     * Class constructor.
//     * @param annotationNotRequired if "true", non-simple method arguments and
//     * return values are considered model attributes with or without a
//     * {@code @ModelAttribute} annotation
//     */
//    public MethodModelAttributeMethodArgumentResolver(
//            boolean annotationNotRequired) {
//        this.annotationNotRequired = annotationNotRequired;
//    }
//    
//    /**
//     * @return
//     */
//    @Override
//    public int getOrder() {
//        return Ordered.HIGHEST_PRECEDENCE + 1;
//    }
//    
//    /**
//     * @param parameter
//     * @return
//     */
//    @Override
//    public boolean supportsParameter(MethodParameter parameter) {
//        return (parameter
//                .hasParameterAnnotation(MethodModelAttribute.class)
//                || (this.annotationNotRequired && !BeanUtils
//                        .isSimpleProperty(parameter.getParameterType())));
//    }
//    
//    /**
//     * @param parameter
//     * @param event
//     * @param params
//     * @return
//     * @throws Exception
//     */
//    @Override
//    public Object resolveArgument(MethodParameter parameter,
//            InvokeRequest request) throws Exception {
//        
//    }
//    
//}
