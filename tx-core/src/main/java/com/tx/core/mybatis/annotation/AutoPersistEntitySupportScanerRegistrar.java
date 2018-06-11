///*
// * 描          述:  <描述>
// * 修  改   人:  Administrator
// * 修改时间:  2018年6月9日
// * <修改描述:>
// */
//package com.tx.core.mybatis.annotation;
//
//import org.springframework.beans.factory.support.BeanDefinitionBuilder;
//import org.springframework.beans.factory.support.BeanDefinitionRegistry;
//import org.springframework.context.EnvironmentAware;
//import org.springframework.context.ResourceLoaderAware;
//import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
//import org.springframework.core.annotation.AnnotationAttributes;
//import org.springframework.core.env.Environment;
//import org.springframework.core.io.ResourceLoader;
//import org.springframework.core.type.AnnotationMetadata;
//
//import com.tx.core.exceptions.util.AssertUtils;
//import com.tx.core.mybatis.support.EntityDaoFactory;
//import com.tx.core.mybatis.support.MyBatisDaoSupport;
//
///**
// * 实体自动持久注册机<br/>
// * <功能详细描述>
// * 
// * @author  Administrator
// * @version  [版本号, 2018年6月9日]
// * @see  [相关类/方法]
// * @since  [产品/模块版本]
// */
//public class AutoPersistEntitySupportScanerRegistrar implements
//        ImportBeanDefinitionRegistrar, ResourceLoaderAware, EnvironmentAware {
//    
//    private ResourceLoader resourceLoader;
//    
//    private Environment environment;
//    
//    /**
//     * @param resourceLoader
//     */
//    @Override
//    public void setResourceLoader(ResourceLoader resourceLoader) {
//        this.resourceLoader = resourceLoader;
//    }
//    
//    /**
//     * @param environment
//     */
//    @Override
//    public void setEnvironment(Environment environment) {
//        this.environment = environment;
//    }
//    
//    /**
//     * @param importingClassMetadata
//     * @param registry
//     */
//    @Override
//    public void registerBeanDefinitions(
//            AnnotationMetadata importingClassMetadata,
//            BeanDefinitionRegistry registry) {
//        //注解属性集合
//        AnnotationAttributes annoAttrs = AnnotationAttributes
//                .fromMap(importingClassMetadata.getAnnotationAttributes(
//                        AutoPersistEntitySupport.class.getName()));
//        
//        //扫描注解类
//        
//        //扫描遍历，如果已经存在持久层的实体类，则不再添加
//        //Set<Class<?>> types = scanner.findTypes("...");
//        
//        for (Class<?> type : types) {
//            
//        }
//    }
//    
//    /**
//     * 注册实体的持久层实现<br/>
//     * <功能详细描述>
//     * @param registry
//     * @param beanType
//     * @param myBatisDaoSupport [参数说明]
//     * 
//     * @return void [返回类型说明]
//     * @exception throws [异常类型] [异常说明]
//     * @see [类、类#方法、类#成员]
//     */
//    private void registerEntityDao(BeanDefinitionRegistry registry,
//            Class<?> beanType, MyBatisDaoSupport myBatisDaoSupport) {
//        AssertUtils.notNull(registry, "registry is null.");
//        AssertUtils.notNull(beanType, "beanType is null.");
//        AssertUtils.notNull(myBatisDaoSupport, "myBatisDaoSupport is null.");
//        
//        BeanDefinitionBuilder builder = BeanDefinitionBuilder
//                .genericBeanDefinition(EntityDaoFactory.class);
//        builder.addConstructorArgValue(beanType);
//        builder.addConstructorArgValue(myBatisDaoSupport);
//        
//        registry.registerBeanDefinition(
//                name + "." + FeignClientSpecification.class.getSimpleName(),
//                builder.getBeanDefinition());
//    }
//    
//}
