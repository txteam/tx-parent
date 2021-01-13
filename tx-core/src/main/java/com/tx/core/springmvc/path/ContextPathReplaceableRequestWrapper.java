///*
// * 描          述:  <描述>
// * 修  改   人:  PengQingyang
// * 修改时间:  2020年12月27日
// * <修改描述:>
// */
//package com.tx.core.springmvc.path;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletRequestWrapper;
//
//import org.apache.commons.lang3.StringUtils;
//
///**
// * contextPath可替换的请求环绕器<br/>
// * <功能详细描述>
// * 
// * @author  PengQingyang
// * @version  [版本号, 2020年12月27日]
// * @see  [相关类/方法]
// * @since  [产品/模块版本]
// */
//public class ContextPathReplaceableRequestWrapper
//        extends HttpServletRequestWrapper {
//    
//    /** contextPath */
//    private String contextPathExtend;
//    
//    /** contextPath可替换的请求环绕器 */
//    public ContextPathReplaceableRequestWrapper(HttpServletRequest request) {
//        super(request);
//    }
//    
//    /** contextPath可替换的请求环绕器 */
//    public ContextPathReplaceableRequestWrapper(HttpServletRequest request,
//            String contextPathExtend) {
//        super(request);
//        if (StringUtils.isEmpty(contextPathExtend)) {
//            this.contextPathExtend = "";
//        } else {
//            if (!contextPathExtend.startsWith("/")) {
//                contextPathExtend = "/" + contextPathExtend;
//            }
//            this.contextPathExtend = contextPathExtend;
//        }
//    }
//    
//    /**
//     * @return
//     */
//    @Override
//    public String getContextPath() {
//        if (StringUtils.isEmpty(this.contextPathExtend)) {
//            return super.getContextPath();
//        }
//        return super.getContextPath() + contextPathExtend;
//    }
//    
//}
