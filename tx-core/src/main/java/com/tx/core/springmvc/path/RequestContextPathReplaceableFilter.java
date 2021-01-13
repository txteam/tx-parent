///*
// * 描          述:  <描述>
// * 修  改   人:  PengQingyang
// * 修改时间:  2020年12月27日
// * <修改描述:>
// */
//package com.tx.core.springmvc.path;
//
//import java.io.IOException;
//import java.util.Collections;
//import java.util.List;
//
//import javax.annotation.Resource;
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.springframework.core.OrderComparator;
//import org.springframework.util.CollectionUtils;
//import org.springframework.web.filter.OncePerRequestFilter;
//
///**
// * 替换ContextPath的实现<br/>
// * <功能详细描述>
// * 
// * @author  PengQingyang
// * @version  [版本号, 2020年12月27日]
// * @see  [相关类/方法]
// * @since  [产品/模块版本]
// */
//public class RequestContextPathReplaceableFilter extends OncePerRequestFilter {
//    
//    /** 处理句柄集 */
//    private List<ContextPathReplaceHandler> handlers;
//    
//    @Resource
//    private LocalPathReplaceUtils localPathReplaceUtils;
//    
//    /** <默认构造函数> */
//    public RequestContextPathReplaceableFilter(
//            List<ContextPathReplaceHandler> handlers) {
//        super();
//        this.handlers = handlers;
//    }
//    
//    /**
//     * @throws ServletException
//     */
//    @Override
//    protected void initFilterBean() throws ServletException {
//        if (!CollectionUtils.isEmpty(handlers)) {
//            Collections.sort(this.handlers, OrderComparator.INSTANCE);
//        }
//    }
//    
//    /**
//     * @param request
//     * @param response
//     * @param filterChain
//     * @throws ServletException
//     * @throws IOException
//     */
//    @Override
//    protected void doFilterInternal(HttpServletRequest request,
//            HttpServletResponse response, FilterChain filterChain)
//            throws ServletException, IOException {
//        if (!CollectionUtils.isEmpty(handlers)) {
//            for (ContextPathReplaceHandler h : handlers) {
//                if (request instanceof ContextPathReplaceableRequestWrapper) {
//                    break;
//                }
//                if (h.support(request, response)) {
//                    //如果匹配则调用生成Wrapper实现
//                    request = new ContextPathReplaceableRequestWrapper(request,
//                            this.localPathReplaceUtils.getPrefix());
//                    break;
//                }
//            }
//        }
//        filterChain.doFilter(request, response);
//    }
//    
//}
