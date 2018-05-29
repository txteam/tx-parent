///*
// * 描          述:  <描述>
// * 修  改   人:  Administrator
// * 修改时间:  2018年5月29日
// * <修改描述:>
// */
//package com.tx.core.springmvc.annotation;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Map.Entry;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.Part;
//
//import org.apache.commons.collections4.MapUtils;
//import org.springframework.beans.MutablePropertyValues;
//import org.springframework.util.Assert;
//import org.springframework.util.ClassUtils;
//import org.springframework.util.LinkedMultiValueMap;
//import org.springframework.util.MultiValueMap;
//import org.springframework.util.StringUtils;
//import org.springframework.validation.BindException;
//import org.springframework.web.bind.support.WebRequestDataBinder;
//import org.springframework.web.context.request.NativeWebRequest;
//import org.springframework.web.context.request.WebRequest;
//import org.springframework.web.multipart.MultipartException;
//import org.springframework.web.multipart.MultipartRequest;
//
///**
// * WebRequest DataBinder
// * <功能详细描述>
// * 
// * @author  Administrator
// * @version  [版本号, 2018年5月29日]
// * @see  [相关类/方法]
// * @since  [产品/模块版本]
// */
//public class RequestModelDataBinder extends WebRequestDataBinder {
//    
//    /** servlet3Parts */
//    private static final boolean servlet3Parts = ClassUtils
//            .hasMethod(HttpServletRequest.class, "getParts");
//    
//    /** 前置 */
//    private String prefix = "";
//    
//    /** 前置分隔 */
//    private String prefixSeparator = ".";
//    
//    /** <默认构造函数> */
//    public RequestModelDataBinder(String prefix, Object target,
//            String objectName) {
//        super(target, objectName);
//        this.prefix = prefix;
//    }
//    
//    /** <默认构造函数> */
//    public RequestModelDataBinder(String prefix, Object target) {
//        super(target);
//        this.prefix = prefix;
//    }
//    
//    /** bind */
//    public void bind(WebRequest request) {
//        MutablePropertyValues mpvs = new MutablePropertyValues(
//                getParametersStartingWith(request,
//                        (prefix != null ? prefix + prefixSeparator : null)));
//        
//        if (isMultipartRequest(request)
//                && request instanceof NativeWebRequest) {
//            MultipartRequest multipartRequest = ((NativeWebRequest) request)
//                    .getNativeRequest(MultipartRequest.class);
//            if (multipartRequest != null) {
//                bindMultipart(multipartRequest.getMultiFileMap(), mpvs);
//            } else if (servlet3Parts) {
//                HttpServletRequest serlvetRequest = ((NativeWebRequest) request)
//                        .getNativeRequest(HttpServletRequest.class);
//                new Servlet3MultipartHelper(isBindEmptyMultipartFiles())
//                        .bindParts(serlvetRequest, mpvs);
//            }
//        }
//        
//        doBind(mpvs);
//    }
//    
//    /**
//     * 获取参数映射<br/>
//     * <功能详细描述>
//     * @param request
//     * @param prefix
//     * @return [参数说明]
//     * 
//     * @return Map<String,String[]> [返回类型说明]
//     * @exception throws [异常类型] [异常说明]
//     * @see [类、类#方法、类#成员]
//     */
//    private static Map<String, String[]> getParametersStartingWith(
//            WebRequest request, String prefix) {
//        Assert.notNull(request, "Request must not be null");
//        Assert.notNull(request.getParameterMap(),
//                "request.parameterMap must not be null");
//        
//        Map<String, String[]> resMap = new HashMap<>();
//        if (MapUtils.isEmpty(request.getParameterMap())) {
//            return resMap;
//        }
//        for (Entry<String, String[]> entryTemp : request.getParameterMap()
//                .entrySet()) {
//            String paramName = entryTemp.getKey();
//            if ("".equals(prefix) || paramName.startsWith(prefix)) {
//                String unprefixed = paramName.substring(prefix.length());
//                
//                resMap.put(unprefixed, entryTemp.getValue());
//            }
//        }
//        
//        return resMap;
//    }
//    
//    /**
//     * Check if the request is a multipart request (by checking its Content-Type header).
//     * @param request request with parameters to bind
//     */
//    private boolean isMultipartRequest(WebRequest request) {
//        String contentType = request.getHeader("Content-Type");
//        return (contentType != null
//                && StringUtils.startsWithIgnoreCase(contentType, "multipart"));
//    }
//    
//    /**
//     * Treats errors as fatal.
//     * <p>Use this method only if it's an error if the input isn't valid.
//     * This might be appropriate if all input is from dropdowns, for example.
//     * @throws BindException if binding errors have been encountered
//     */
//    public void closeNoCatch() throws BindException {
//        if (getBindingResult().hasErrors()) {
//            throw new BindException(getBindingResult());
//        }
//    }
//    
//    /**
//     * Encapsulate Part binding code for Servlet 3.0+ only containers.
//     * @see javax.servlet.http.Part
//     */
//    private static class Servlet3MultipartHelper {
//        
//        private final boolean bindEmptyMultipartFiles;
//        
//        public Servlet3MultipartHelper(boolean bindEmptyMultipartFiles) {
//            this.bindEmptyMultipartFiles = bindEmptyMultipartFiles;
//        }
//        
//        public void bindParts(HttpServletRequest request,
//                MutablePropertyValues mpvs) {
//            try {
//                MultiValueMap<String, Part> map = new LinkedMultiValueMap<String, Part>();
//                for (Part part : request.getParts()) {
//                    map.add(part.getName(), part);
//                }
//                for (Map.Entry<String, List<Part>> entry : map.entrySet()) {
//                    if (entry.getValue().size() == 1) {
//                        Part part = entry.getValue().get(0);
//                        if (this.bindEmptyMultipartFiles
//                                || part.getSize() > 0) {
//                            mpvs.add(entry.getKey(), part);
//                        }
//                    } else {
//                        mpvs.add(entry.getKey(), entry.getValue());
//                    }
//                }
//            } catch (Exception ex) {
//                throw new MultipartException("Failed to get request parts", ex);
//            }
//        }
//    }
//}
