/*
 * 描          述:  <描述>
 * 修  改   人:  PengQingyang
 * 修改时间:  2012-10-23
 * <修改描述:>
 */
package test.com.tx.component.config;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * <测试配置容器>
 * <功能详细描述>
 * 
 * @author  PengQingyang
 * @version  [版本号, 2012-10-23]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class TestConfigConext {
    
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext(
                "classpath:/com/tx/component/config/applicationContext-config.xml");
        
        
    }
    
}
