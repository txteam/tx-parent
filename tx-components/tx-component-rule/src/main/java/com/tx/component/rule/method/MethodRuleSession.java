/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-1-24
 * <修改描述:>
 */
package com.tx.component.rule.method;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.GenericTypeResolver;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpEntity;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.util.ClassUtils;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.support.HandlerMethodResolver;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.servlet.mvc.annotation.AnnotationMethodHandlerAdapter.ServletHandlerMethodResolver;

import com.tx.component.rule.model.Rule;
import com.tx.component.rule.support.RuleSession;


 /**
  * 方法规则会话
  * 
  * @author  brady
  * @version  [版本号, 2013-1-24]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public class MethodRuleSession implements RuleSession{

    private MethodRule rule;
    
    /**
     * @return
     */
    @Override
    public String rule() {
        return rule.rule();
    }

    /**
     * 
     */
    @Override
    public void start(Map<String, Object> globals) {
        MethodRuleSessionContext.open(globals);
    }

    /**
     * 
     */
    @Override
    public void execute(Map<String, Object> facts) {
        
    }

    /**
     * 
     */
    @Override
    public void close() {
        MethodRuleSessionContext.close();
    }
    
    /**
      * 解析生成规则会话方法调用参数数组<br/>
      * <功能详细描述>
      * @return
      * @throws Exception [参数说明]
      * 
      * @return Object[] [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    private Object[] resolveHandlerArguments() throws Exception {
        @SuppressWarnings("rawtypes")
        Class[] paramTypes = rule.getMethod().getParameterTypes();
        Object[] args = new Object[paramTypes.length];

        for (int i = 0; i < args.length; i++) {
            MethodParameter methodParam = new MethodParameter(handlerMethod, i);
            methodParam.initParameterNameDiscovery(this.parameterNameDiscoverer);
            GenericTypeResolver.resolveParameterType(methodParam, handler.getClass());
            String paramName = null;
            String headerName = null;
            boolean requestBodyFound = false;
            String cookieName = null;
            String pathVarName = null;
            String attrName = null;
            boolean required = false;
            String defaultValue = null;
            boolean validate = false;
            Object[] validationHints = null;
            int annotationsFound = 0;
            Annotation[] paramAnns = methodParam.getParameterAnnotations();

            for (Annotation paramAnn : paramAnns) {
                if (RequestParam.class.isInstance(paramAnn)) {
                    RequestParam requestParam = (RequestParam) paramAnn;
                    paramName = requestParam.value();
                    required = requestParam.required();
                    defaultValue = parseDefaultValueAttribute(requestParam.defaultValue());
                    annotationsFound++;
                }
                else if (paramAnn.annotationType().getSimpleName().startsWith("Valid")) {
                    validate = true;
                    Object value = AnnotationUtils.getValue(paramAnn);
                    validationHints = (value instanceof Object[] ? (Object[]) value : new Object[] {value});
                }
            }

            if (annotationsFound > 1) {
                throw new IllegalStateException("Handler parameter annotations are exclusive choices - " +
                        "do not specify more than one such annotation on the same parameter: " + handlerMethod);
            }

            if (annotationsFound == 0) {
                Object argValue = resolveCommonArgument(methodParam, webRequest);
                if (argValue != WebArgumentResolver.UNRESOLVED) {
                    args[i] = argValue;
                }
                else if (defaultValue != null) {
                    args[i] = resolveDefaultValue(defaultValue);
                }
                else {
                    Class<?> paramType = methodParam.getParameterType();
                    if (Model.class.isAssignableFrom(paramType) || Map.class.isAssignableFrom(paramType)) {
                        if (!paramType.isAssignableFrom(implicitModel.getClass())) {
                            throw new IllegalStateException("Argument [" + paramType.getSimpleName() + "] is of type " +
                                    "Model or Map but is not assignable from the actual model. You may need to switch " +
                                    "newer MVC infrastructure classes to use this argument.");
                        }
                        args[i] = implicitModel;
                    }
                    else if (SessionStatus.class.isAssignableFrom(paramType)) {
                        args[i] = this.sessionStatus;
                    }
                    else if (HttpEntity.class.isAssignableFrom(paramType)) {
                        args[i] = resolveHttpEntityRequest(methodParam, webRequest);
                    }
                    else if (Errors.class.isAssignableFrom(paramType)) {
                        throw new IllegalStateException("Errors/BindingResult argument declared " +
                                "without preceding model attribute. Check your handler method signature!");
                    }
                    else if (BeanUtils.isSimpleProperty(paramType)) {
                        paramName = "";
                    }
                    else {
                        attrName = "";
                    }
                }
            }

            if (paramName != null) {
                args[i] = resolveRequestParam(paramName, required, defaultValue, methodParam, webRequest, handler);
            }
            else if (headerName != null) {
                args[i] = resolveRequestHeader(headerName, required, defaultValue, methodParam, webRequest, handler);
            }
            else if (requestBodyFound) {
                args[i] = resolveRequestBody(methodParam, webRequest, handler);
            }
            else if (cookieName != null) {
                args[i] = resolveCookieValue(cookieName, required, defaultValue, methodParam, webRequest, handler);
            }
            else if (pathVarName != null) {
                args[i] = resolvePathVariable(pathVarName, methodParam, webRequest, handler);
            }
            else if (attrName != null) {
                WebDataBinder binder =
                        resolveModelAttribute(attrName, methodParam, implicitModel, webRequest, handler);
                boolean assignBindingResult = (args.length > i + 1 && Errors.class.isAssignableFrom(paramTypes[i + 1]));
                if (binder.getTarget() != null) {
                    doBind(binder, webRequest, validate, validationHints, !assignBindingResult);
                }
                args[i] = binder.getTarget();
                if (assignBindingResult) {
                    args[i + 1] = binder.getBindingResult();
                    i++;
                }
                implicitModel.putAll(binder.getBindingResult().getModel());
            }
        }

        return args;
    }
    

}
