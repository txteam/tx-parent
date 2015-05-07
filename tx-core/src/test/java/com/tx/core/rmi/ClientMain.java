/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-10-16
 * <修改描述:>
 */
package com.tx.core.rmi;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


 /**
  * <功能简述>
  * <功能详细描述>
  * 
  * @author  brady
  * @version  [版本号, 2013-10-16]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public class ClientMain {
    
    @SuppressWarnings("resource")
	public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext(
                new String[] {"com/tx/core/rmi/beans-rmiclient.xml"});
        
        HelloWorld helloWorld = context.getBean("helloWorldClient",HelloWorld.class);
    
        helloWorld.helloWorld();
    }
}
