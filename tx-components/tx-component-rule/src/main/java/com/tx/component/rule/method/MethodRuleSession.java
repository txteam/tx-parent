/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-1-24
 * <修改描述:>
 */
package com.tx.component.rule.method;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Map.Entry;

import org.drools.core.util.StringUtils;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.tx.component.rule.annotation.RuleMethodParam;
import com.tx.component.rule.support.RuleSession;
import com.tx.core.support.method.MethodResolver;
import com.tx.core.support.method.ParameterResolver;


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
    public void execute(Map<String, Object> fact) {
        
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
    private Object[] resolveHandlerArguments(Method method,Map<String, Object> fact) throws Exception {
        //生成方法解析器
        MethodResolver methodResolver = MethodResolver.resolveMethodResolver(method);
        Object[] args = new Object[methodResolver.getParametersLength()];
        
        MultiValueMap<Class<?>, Object> typeMap = new LinkedMultiValueMap<Class<?>, Object>();
        for(Entry<String, Object> entryTemp : fact.entrySet()){
            typeMap.add(entryTemp.getValue().getClass(), entryTemp.getValue());
        }
        
        for(int i = 0 ; i < methodResolver.getParametersLength() ; i++ ){
            ParameterResolver paramterResolver = methodResolver.getParameterResolvers().get(i);
            if(paramterResolver.isHasAnnotation(RuleMethodParam.class)){
                //如果有RuleMethodParam注解
                RuleMethodParam ruleMethodParamInstance = paramterResolver.getAnnotation(RuleMethodParam.class);
                if(StringUtils.isEmpty(ruleMethodParamInstance.value())){
                    if(Map.class.isAssignableFrom(paramterResolver.getParamterType()) ||
                            Model.class.isAssignableFrom(paramterResolver.getParamterType()) ||
                            ModelMap.class.isAssignableFrom(paramterResolver.getParamterType())){
                        args[i] = fact;
                        continue;
                    }
                }else if(StringUtils.isEmpty(ruleMethodParamInstance.value())
                        && ruleMethodParamInstance.required()){
                    //throw new ParameterIsEmptyException("resolveHandlerArguments RuleMethodParam is ", parameters)
                }else{
                    //Object obj = fact.get(ruleMethodParamInstance.value());
                    
                }
                
            }else if(typeMap.containsKey(typeMap)){
                //如果没有RuleMethodParam注解，但事实对象中存在
            }else{
                
            }
        }

        return args;
    }
    

}
