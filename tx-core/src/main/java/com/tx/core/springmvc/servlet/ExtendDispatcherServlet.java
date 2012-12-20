/*
 * 描          述:  <描述>
 * 修  改   人:  PengQingyang
 * 修改时间:  2012-10-21
 * <修改描述:>
 */
package com.tx.core.springmvc.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.HandlerAdapter;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ModelAndViewDefiningException;
import org.springframework.web.util.NestedServletException;
import org.springframework.web.util.UrlPathHelper;
import org.springframework.web.util.WebUtils;

import com.tx.core.springmvc.interceptor.SkipHandlerInterceptor;

/**
 * <扩展spring DispatcherServlet>
 * <使其支持根据接口判断是否跳过部分interceptor>
 * 
 * @author  PengQingyang
 * @version  [版本号, 2012-10-21]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class ExtendDispatcherServlet extends DispatcherServlet {
    
    /** 注释内容 */
    private static final long serialVersionUID = -2280451629686911410L;
    
    private static final UrlPathHelper urlPathHelper = new UrlPathHelper();
    
    /** <默认构造函数> */
    public ExtendDispatcherServlet() {
        super();
    }
    
    /** <默认构造函数> */
    public ExtendDispatcherServlet(WebApplicationContext webApplicationContext) {
        super(webApplicationContext);
    }
    
    /**
     * @param request
     * @param response
     * @throws Exception
     */
    @SuppressWarnings("deprecation")
    @Override
    protected void doDispatch(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        HttpServletRequest processedRequest = request;
        HandlerExecutionChain mappedHandler = null;
        int interceptorIndex = -1;
        
        try {
            ModelAndView mv;
            boolean errorView = false;
            
            try {
                processedRequest = checkMultipart(request);
                
                // Determine handler for the current request.
                mappedHandler = getHandler(processedRequest, false);
                if (mappedHandler == null || mappedHandler.getHandler() == null) {
                    noHandlerFound(processedRequest, response);
                    return;
                }
                
                // Determine handler adapter for the current request.
                HandlerAdapter ha = getHandlerAdapter(mappedHandler.getHandler());
                
                // Process last-modified header, if supported by the handler.
                String method = request.getMethod();
                boolean isGet = "GET".equals(method);
                if (isGet || "HEAD".equals(method)) {
                    long lastModified = ha.getLastModified(request,
                            mappedHandler.getHandler());
                    if (logger.isDebugEnabled()) {
                        String requestUri = urlPathHelper.getRequestUri(request);
                        logger.debug("Last-Modified value for [" + requestUri
                                + "] is: " + lastModified);
                    }
                    if (new ServletWebRequest(request, response).checkNotModified(lastModified)
                            && isGet) {
                        return;
                    }
                }
                
                // Apply preHandle methods of registered interceptors.
                HandlerInterceptor[] interceptors = mappedHandler.getInterceptors();
                if (interceptors != null) {
                    for (int i = 0; i < interceptors.length; i++) {
                        HandlerInterceptor interceptor = interceptors[i];
                        if (!interceptor.preHandle(processedRequest,
                                response,
                                mappedHandler.getHandler())) {
                            triggerAfterCompletion(mappedHandler,
                                    interceptorIndex,
                                    processedRequest,
                                    response,
                                    null);
                            return;
                        }
                        if (interceptor instanceof SkipHandlerInterceptor) {
                            SkipHandlerInterceptor skipInterceptor = (SkipHandlerInterceptor) interceptor;
                            if (skipInterceptor.isSkipAfterInterceptor(processedRequest,
                                    response,
                                    mappedHandler.getHandler())) {
                                break;
                            }
                        }
                        interceptorIndex = i;
                    }
                }
                
                // Actually invoke the handler.
                mv = ha.handle(processedRequest,
                        response,
                        mappedHandler.getHandler());
                
                // Do we need view name translation?
                if (mv != null && !mv.hasView()) {
                    mv.setViewName(getDefaultViewName(request));
                }
                
                // Apply postHandle methods of registered interceptors.
                if (interceptors != null) {
                    for (int i = interceptorIndex; i >= 0; i--) {
                        HandlerInterceptor interceptor = interceptors[i];
                        interceptor.postHandle(processedRequest,
                                response,
                                mappedHandler.getHandler(),
                                mv);
                    }
                }
            }
            catch (ModelAndViewDefiningException ex) {
                logger.debug("ModelAndViewDefiningException encountered", ex);
                mv = ex.getModelAndView();
            }
            catch (Exception ex) {
                Object handler = (mappedHandler != null ? mappedHandler.getHandler()
                        : null);
                mv = processHandlerException(processedRequest,
                        response,
                        handler,
                        ex);
                errorView = (mv != null);
            }
            
            // Did the handler return a view to render?
            if (mv != null && !mv.wasCleared()) {
                render(mv, processedRequest, response);
                if (errorView) {
                    WebUtils.clearErrorRequestAttributes(request);
                }
            }
            else {
                if (logger.isDebugEnabled()) {
                    logger.debug("Null ModelAndView returned to DispatcherServlet with name '"
                            + getServletName()
                            + "': assuming HandlerAdapter completed request handling");
                }
            }
            
            // Trigger after-completion for successful outcome.
            triggerAfterCompletion(mappedHandler,
                    interceptorIndex,
                    processedRequest,
                    response,
                    null);
        }
        
        catch (Exception ex) {
            // Trigger after-completion for thrown exception.
            triggerAfterCompletion(mappedHandler,
                    interceptorIndex,
                    processedRequest,
                    response,
                    ex);
            throw ex;
        }
        catch (Error err) {
            ServletException ex = new NestedServletException(
                    "Handler processing failed", err);
            // Trigger after-completion for thrown exception.
            triggerAfterCompletion(mappedHandler,
                    interceptorIndex,
                    processedRequest,
                    response,
                    ex);
            throw ex;
        }
        
        finally {
            // Clean up any resources used by a multipart request.
            if (processedRequest != request) {
                cleanupMultipart(processedRequest);
            }
        }
        ;
    }
    
    /**
      *<功能简述>
      * Trigger afterCompletion callbacks on the mapped HandlerInterceptors.
      * Will just invoke afterCompletion for all interceptors whose preHandle invocation
      * has successfully completed and returned true.
      * @param mappedHandler
      * @param interceptorIndex
      * @param request
      * @param response
      * @param ex
      * @throws Exception [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    private void triggerAfterCompletion(HandlerExecutionChain mappedHandler,
            int interceptorIndex, HttpServletRequest request,
            HttpServletResponse response, Exception ex) throws Exception {
        if (mappedHandler != null) {
            HandlerInterceptor[] interceptors = mappedHandler.getInterceptors();
            if (interceptors != null) {
                for (int i = interceptorIndex; i >= 0; i--) {
                    HandlerInterceptor interceptor = interceptors[i];
                    try {
                        interceptor.afterCompletion(request,
                                response,
                                mappedHandler.getHandler(),
                                ex);
                    }
                    catch (Throwable ex2) {
                        logger.error("HandlerInterceptor.afterCompletion threw exception",
                                ex2);
                    }
                }
            }
        }
    }
}
