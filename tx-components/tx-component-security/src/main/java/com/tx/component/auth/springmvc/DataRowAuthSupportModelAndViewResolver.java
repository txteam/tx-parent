/*
 * 描          述:  <描述>
 * 修  改   人:  PengQingyang
 * 修改时间:  2012-12-15
 * <修改描述:>
 */
package com.tx.component.auth.springmvc;

import java.lang.reflect.Method;
import java.util.Map;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.annotation.ModelAndViewResolver;


 /**
  * 用以支持数据列权限
  * <功能详细描述>
  * 
  * @author  PengQingyang
  * @version  [版本号, 2012-12-15]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public class DataRowAuthSupportModelAndViewResolver implements ModelAndViewResolver,InitializingBean{
    
    
    
    /**
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        // TODO Auto-generated method stub
        
    }

    /**
     * @param handlerMethod
     * @param handlerType
     * @param returnValue
     * @param implicitModel
     * @param webRequest
     * @return
     */
    @Override
    public ModelAndView resolveModelAndView(Method handlerMethod,
            @SuppressWarnings("rawtypes") Class handlerType, Object returnValue,
            ExtendedModelMap implicitModel, NativeWebRequest webRequest) {
        
        
        //returnValue type HttpEntity undeal
        //returnValue
        if (AnnotationUtils.findAnnotation(handlerMethod, ResponseBody.class) != null) {
            //如果是ajax请求，则对返回对象进行行过滤处理
            handleByDataRowAuth(returnValue);
        }else if(returnValue instanceof Model ||
                returnValue instanceof Map<?, ?> ||
                returnValue instanceof ModelMap){
            //对implicitModel以及map进行一并处理
            
        }else{
            //returnValue instanceof ModelAndView
            //returnValue instanceof View
            //returnValue instanceof String
            //...
            //对implicitModel map进行处理
        }
        
        //仅处理model中的数据不进行
        return ModelAndViewResolver.UNRESOLVED;
    }
    
    public void handleByDataRowAuth(Object obj){
        //如果obj为空，则无需进行处理
        if(obj == null){
            return ;
        }
        
        //Class<?> type = obj.getClass();
        
        //AnnotationUtils.s
    }
    
}
