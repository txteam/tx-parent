/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2015年10月21日
 * <修改描述:>
 */
package com.tx.component.statistical.context;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;

/**
 * 统计容器配置器<br/>
 * <功能详细描述>
 *
 * @author Administrator
 * @version [版本号, 2015年10月21日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Configuration
public class StatisticalContextConfigurator implements ApplicationContextAware, InitializingBean {

    /**
     * spring容器句柄
     */
    protected ApplicationContext applicationContext;


//    /** 任务配置路径 */
//    protected String configPath = "classpath:statistical/*/*Report.xml";

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }

}
