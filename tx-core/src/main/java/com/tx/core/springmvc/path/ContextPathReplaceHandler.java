///*
// * 描          述:  <描述>
// * 修  改   人:  PengQingyang
// * 修改时间:  2020年12月27日
// * <修改描述:>
// */
//package com.tx.core.springmvc.path;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
///**
// * 容器路径替换处理器<br/>
// * <功能详细描述>
// * 
// * @author  PengQingyang
// * @version  [版本号, 2020年12月27日]
// * @see  [相关类/方法]
// * @since  [产品/模块版本]
// */
//public interface ContextPathReplaceHandler {
//    
//    default boolean support(HttpServletRequest request,
//            HttpServletResponse response) {
//        return false;
//    }
//    
//    /**
//     * 根据请求返回自定义contextPath<br/>
//     * <功能详细描述>
//     * @param request
//     * @param response
//     * @return [参数说明]
//     * 
//     * @return String [返回类型说明]
//     * @exception throws [异常类型] [异常说明]
//     * @see [类、类#方法、类#成员]
//     */
//    default String contextPath(HttpServletRequest request,
//            HttpServletResponse response) {
//        return request.getContextPath();
//    }
//}
