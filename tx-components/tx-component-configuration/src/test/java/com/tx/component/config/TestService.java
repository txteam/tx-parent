/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2014-2-18
 * <修改描述:>
 */
package com.tx.component.config;

import org.springframework.beans.factory.annotation.Value;

import com.tx.component.configuration.context.ConfigContext;


 /**
  * <功能简述>
  * <功能详细描述>
  * 
  * @author  brady
  * @version  [版本号, 2014-2-18]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public class TestService {
    
    @Value("${helloworldservice.url}")
    private String url;
    
    public void test(){
        ConfigContext.getContext().getValue("111");
        
        ConfigContext.getContext().getAllConfigProperty();
    }
}
