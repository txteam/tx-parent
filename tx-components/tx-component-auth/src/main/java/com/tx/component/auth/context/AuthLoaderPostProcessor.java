package com.tx.component.auth.context;

import org.springframework.stereotype.Component;

import com.tx.component.auth.context.loader.AuthLoader;
import com.tx.core.spring.processor.BeansInitializedEventSupportPostProcessor;

/**
  * 权限加载器，加载完成事件
  * <功能详细描述>
  * 
  * @author  brady
  * @version  [版本号, 2013-4-8]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
 */
@Component("authLoaderPostProcessor")
public class AuthLoaderPostProcessor extends
        BeansInitializedEventSupportPostProcessor<AuthLoader> {
    
    @Override
    public Class<AuthLoader> beanType() {
        return AuthLoader.class;
    }
}
