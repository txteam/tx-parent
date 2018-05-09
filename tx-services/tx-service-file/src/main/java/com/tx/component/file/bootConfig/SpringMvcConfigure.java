//package com.tx.component.file.bootConfig;
//
//import com.tx.component.file.context.FileContextConfigurator;
//import com.tx.component.file.viewhandler.FileContextHttpRequestHandler;
//import org.springframework.beans.BeansException;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.ApplicationContextAware;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.handler.SimpleUrlHandlerMapping;
//
//import javax.sql.DataSource;
//import java.util.Properties;
//
///**
// * <br/>
// *
// * @author XRX
// * @version [版本号, 2018/05/09]
// * @see [相关类/方法]
// * @since [产品/模块版本]
// */
//@Configuration
//public class SpringMvcConfigure implements ApplicationContextAware {
//    private ApplicationContext applicationContext;
//
//    @Bean
//    public FileContextHttpRequestHandler fileContextHttpRequestHandler() {
//        return new FileContextHttpRequestHandler();
//    }
//
//    @Bean("fileContextConfigurator")
//    public FileContextConfigurator fileContextConfigurator() {
//        FileContextConfigurator contextConfigurator = new FileContextConfigurator();
//        DataSource dataSource = applicationContext.getBean(DataSource.class);
//
//        contextConfigurator.setDataSource(dataSource);
//        contextConfigurator.setLocation("/home/share/wtp/upload/indexconfig");
//
//        return contextConfigurator;
//    }
//
//    @Bean
//    public SimpleUrlHandlerMapping filterRegistrationBean() {
//        SimpleUrlHandlerMapping handlerMapping = new SimpleUrlHandlerMapping();
//        handlerMapping.setOrder(0);
//
//        FileContextHttpRequestHandler handler = applicationContext.getBean(FileContextHttpRequestHandler.class);
//        Properties properties = new Properties();
//        properties.put("/filecontext/resource/**", handler);
//
//        handlerMapping.setMappings(properties);
//        return handlerMapping;
//    }
//
//    @Override
//    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
//        this.applicationContext = applicationContext;
//    }
//}
