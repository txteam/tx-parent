///*
// * 描          述:  <描述>
// * 修  改   人:  Administrator
// * 修改时间:  2014年4月20日
// * <修改描述:>
// */
//package com.tx.core.method.resolver.impl;
//
//import java.util.Map;
//
//import javax.servlet.ServletException;
//
//import org.springframework.core.MethodParameter;
//import org.springframework.core.Ordered;
//import org.springframework.util.StringUtils;
//import org.springframework.web.bind.annotation.RequestParam;
//
//import com.tx.core.method.annotation.MethodParam;
//import com.tx.core.util.MessageUtils;
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
//public class MethodParamMethodArgumentResolver
//        extends AbstractNamedValueMethodArgumentResolver {
//    
//    /**
//     * @return
//     */
//    @Override
//    public int getOrder() {
//        return Ordered.HIGHEST_PRECEDENCE;
//    }
//    
//    /**
//     * @param parameter
//     * @return
//     */
//    @Override
//    public boolean supportsParameter(MethodParameter parameter) {
//        MethodParam methodParam = parameter.getParameterAnnotation(MethodParam.class);
//        if (methodParam != null) {
//            if (Map.class.isAssignableFrom(parameter.getParameterType())) {
//                return !StringUtils.hasText(methodParam.value());
//            }
//        }
//        return false;
//    }
//    
//    /**
//     * @param name
//     * @param parameter
//     * @throws ServletException
//     */
//    @Override
//    protected void handleMissingValue(String name, MethodParameter parameter)
//            throws ServletException {
//        throw new EventListenerAccessException(MessageUtils.format(
//                "EventListener param:{} paramName:{} paramIndex:{} is required.",
//                new Object[] { parameter, parameter.getParameterName(),
//                        parameter.getParameterIndex() }));
//    }
//    
//    /**
//     * @param name
//     * @param parameter
//     * @param event
//     * @param params
//     * @return
//     * @throws Exception
//     */
//    @Override
//    protected Object resolveName(String name, MethodParameter parameter,
//            Event event, Map<String, Object> params) throws Exception {
//        Object arg = params.get(name);
//        Class<?> paramType = parameter.getParameterType();
//        if (paramType.isInstance(arg)) {
//            return arg;
//        } else {
//            throw new EventListenerAccessException(MessageUtils.format(
//                    "EventListener param:{} expectType:{} actualType:{}",
//                    new Object[] { parameter, paramType, arg.getClass() }));
//        }
//    }
//    
//    /**
//     * @param parameter
//     * @return
//     */
//    @Override
//    protected NamedValueInfo createNamedValueInfo(MethodParameter parameter) {
//        MethodParam methodParamAnno = parameter
//                .getParameterAnnotation(MethodParam.class);
//        NamedValueInfo nameValueInfo = new NamedValueInfo(
//                methodParamAnno.value(), methodParamAnno.required());
//        return nameValueInfo;
//    }
//}
