/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2018年5月4日
 * <修改描述:>
 */
package com.tx.component.test.starter;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.tx.component.test.bean.TestBean;

/**
  * <功能简述>
  * <功能详细描述>
  * 
  * @author  Administrator
  * @version  [版本号, 2018年5月4日]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
//@Configuration
public class TestContextImportConfiguration implements InitializingBean{

    /** <默认构造函数> */
    public TestContextImportConfiguration() {
        super();
        System.out.println("TestContextImportConfiguration constrution. called");
    }
    
    /**
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("TestContextImportConfiguration afterPropertiesSet. called");
    }

    @PostConstruct
    public void postConstruct(){
        System.out.println("TestContextImportConfiguration @PostConstruct. called");
    }
    
    @Bean
    public TestBean testBeanImport1(){
        TestBean bean = new TestBean("testBeanImport1");
        return bean;
    }
    
    @Configuration
    public static class TestContextImportInnerConfiguration{

        /** <默认构造函数> */
        public TestContextImportInnerConfiguration() {
            super();
            System.out.println("TestContextImportInnerConfiguration constrution. called");
        }
        
        @Bean
        public TestBean testBeanImport3(){
            TestBean bean = new TestBean("testBeanImport3");
            return bean;
        }
    }
    
    @Bean
    public TestBean testBeanImport2(){
        TestBean bean = new TestBean("testBeanImport2");
        return bean;
    }
}
