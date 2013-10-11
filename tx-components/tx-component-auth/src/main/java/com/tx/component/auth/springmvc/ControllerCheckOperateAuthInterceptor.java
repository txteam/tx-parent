/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-10-11
 * <修改描述:>
 */
package com.tx.component.auth.springmvc;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tx.component.auth.annotation.CheckOperateAuth;
import com.tx.component.auth.context.AuthContext;
import com.tx.core.exceptions.logic.NoAuthorityAccessException;

/**
 * 控制器中，操作权限校验切面
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2013-10-11]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Aspect
public class ControllerCheckOperateAuthInterceptor implements Ordered {
    
    /**
     * @return
     */
    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
    
    /**
     * 
     */
    @Before("execution(public * *(..)) &&"
            + "@target(controllerAnno) &&"
            + "@annotation(requestMappingAnno) &&"
            + "@annotation(checkOperateAuthAnno)")
    public void doBasicProfiling(
            Controller controllerAnno,
            RequestMapping requestMappingAnno,
            CheckOperateAuth checkOperateAuthAnno) throws Throwable {
        String authKey = checkOperateAuthAnno.key();
        if (!StringUtils.isEmpty(authKey)) {
            if (AuthContext.getContext().hasAuth(authKey)) {
                throw new NoAuthorityAccessException(
                        "无权限访问：controller:{} requestMapping:{} authKey:{}",
                        new Object[] { "",
                                requestMappingAnno.value(), authKey });
            }
        }
    }
    
}
