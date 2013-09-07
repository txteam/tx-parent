/*
 * 描          述:  <描述>
 * 修  改   人:  PengQingyang
 * 修改时间:  2013-9-8
 * <修改描述:>
 */
package com.tx.core.springmvc.exceptionresolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import com.tx.core.exceptions.SILException;

/**
 * 异常信息解析器<br/>
 * <功能详细描述>
 * 
 * @author  PengQingyang
 * @version  [版本号, 2013-9-8]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class SimpleHandlerExceptionResolver extends
        SimpleMappingExceptionResolver {
    
    private static Logger logger = LoggerFactory.getLogger(SimpleHandlerExceptionResolver.class);
    
    private String ajaxErrorView = "error/ajaxError";
    
    @Override
    public ModelAndView resolveException(HttpServletRequest request,
            HttpServletResponse response, Object handler, Exception ex) {
        if (ex == null) {
            super.resolveException(request, response, handler, ex);
        }
        //如果异常不为空：则日志显示异常
        //日志显示Exception信息
        logger.error(ex.toString(), ex);
        if (isAjax(request)) {
            String errorMessage = getExceptionMessage(ex);
            
            ModelAndView ajaxErrorMV = new ModelAndView(ajaxErrorView);
            ajaxErrorMV.addObject("errorMessage", errorMessage);
            
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            
            return ajaxErrorMV;
        } else {
            return super.resolveException(request, response, handler, ex);
        }
    }
    
    /**
      * 获取异常显示信息<br/>
      *<功能详细描述>
      * @param e
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    protected String getExceptionMessage(Throwable e) {
        String message = "";
        if (e instanceof SILException) {
            SILException sile = (SILException) e;
            message = sile.getErrorMessage();
        } else if(e instanceof DataAccessException){
            message = "系统内部错误：数据处理异常";
        } else if(e instanceof IllegalArgumentException){
            message = "系统内部错误：参数异常";
        } else if(e instanceof IllegalArgumentException){
            message = "系统内部错误：参数异常";
        }else {
            message =  "系统内部错误：" + e.toString();
        }
        return message;
    }
    
    private boolean isAjax(HttpServletRequest request) {
        return "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
    }
    
    /**
     * @param 对ajaxErrorView进行赋值
     */
    public void setAjaxErrorView(String ajaxErrorView) {
        this.ajaxErrorView = ajaxErrorView;
    }
}
