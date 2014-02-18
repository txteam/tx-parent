///*
// * 描          述:  <描述>
// * 修  改   人:  brady
// * 修改时间:  2014-2-18
// * <修改描述:>
// */
//package com.tx.core.spring.support;
//
//import javax.persistence.criteria.Order;
//
//import org.springframework.beans.BeansException;
//import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
//import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.support.ClassPathXmlApplicationContext;
//import org.springframework.core.Ordered;
//
///**
// * 父容器支持<br/>
// * <功能详细描述>
// * 
// * @author  brady
// * @version  [版本号, 2014-2-18]
// * @see  [相关类/方法]
// * @since  [产品/模块版本]
// */
//public class ParentApplicationContextSupport implements
//        BeanFactoryPostProcessor,Ordered {
//    
//    /** 父容器的spring配置文件 */
//    private String location;
//    
//    /**
//     * @return
//     */
//    @Override
//    public int getOrder() {
//        return Ordered.HIGHEST_PRECEDENCE;
//    }
//
//    /**
//     * @param beanFactory
//     * @throws BeansException
//     */
//    @Override
//    public void postProcessBeanFactory(
//            ConfigurableListableBeanFactory beanFactory) throws BeansException {
//        ApplicationContext context = new ClassPathXmlApplicationContext(
//                this.location);
//        beanFactory.setParentBeanFactory(context);
//    }
//    
//    /**
//     * @param 对location进行赋值
//     */
//    public void setLocation(String location) {
//        this.location = location;
//    }
//}
