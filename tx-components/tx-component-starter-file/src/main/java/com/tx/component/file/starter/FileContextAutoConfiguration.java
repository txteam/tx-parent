package com.tx.component.file.starter;

import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import com.tx.component.file.FileContextConstants;
import com.tx.component.file.context.FileContextFactory;
import com.tx.component.file.controller.FileContextHttpRequestHandler;
import com.tx.component.file.dao.FileDefinitionDao;
import com.tx.component.file.service.FileDefinitionPersistService;
import com.tx.component.file.service.FileDefinitionService;
import com.tx.component.file.service.impl.FileDefinitionServiceImpl;
import com.tx.component.file.viewhandler.ViewHandlerRegistry;
import com.tx.component.file.viewhandler.impl.DefaultViewHandler;
import com.tx.component.file.viewhandler.impl.ThumbnailViewHandler;
import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.starter.component.ComponentSupportAutoConfiguration;

/**
 * 
 * <功能简述>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2020年2月18日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Configuration
@AutoConfigureAfter({ ComponentSupportAutoConfiguration.class })
@EnableConfigurationProperties(FileContextProperties.class)
@ConditionalOnClass({ FileContextFactory.class })
@ConditionalOnSingleCandidate(DataSource.class)
@ConditionalOnBean(PlatformTransactionManager.class)
@ConditionalOnProperty(prefix = FileContextConstants.PROPERTIES_PREFIX, value = "enable", havingValue = "true")
@Import({ FileContextPersisterConfiguration.class })
public class FileContextAutoConfiguration
        implements InitializingBean, ApplicationContextAware {
    
    /** spring容器句柄　*/
    private ApplicationContext applicationContext;
    
    /** 属性文件 */
    private FileContextProperties properties;
    
    /** 基础包 */
    private String basePackages;
    
    /** 容器所属模块：当该值为空时，使用spring.application.name的内容 */
    private String module;
    
    /** application.name */
    @Value(value = "${spring.application.name}")
    private String applicationName;
    
    private TransactionTemplate transactionTemplate;
    
    /** <默认构造函数> */
    public FileContextAutoConfiguration(FileContextProperties properties,
            PlatformTransactionManager transactionManager) {
        super();
        this.properties = properties;
        this.transactionTemplate = new TransactionTemplate(transactionManager);
    }
    
    /**
     * @param applicationContext
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        this.applicationContext = applicationContext;
    }
    
    /**
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        if (!StringUtils.isEmpty(this.properties.getBasePackages())) {
            this.basePackages = this.properties.getBasePackages();
        }
        //初始化包名
        if (!StringUtils.isBlank(this.applicationName)) {
            this.module = this.applicationName;
        }
        if (!StringUtils.isEmpty(this.properties.getModule())) {
            this.module = this.properties.getModule();
        }
        AssertUtils.notEmpty(this.module, "module is empty.");
    }
    
    /**
     * 文件定义业务层<br/>
     * <功能详细描述>
     *
     * @return FileDefinitionService [返回类型说明]
     * @throws throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Bean(name = "file.fileDefinitionService")
    public FileDefinitionService fileDefinitionService(
            FileDefinitionDao fileDefinitionDao) {
        FileDefinitionService fdService = new FileDefinitionServiceImpl(
                fileDefinitionDao, this.module);
        return fdService;
    }
    
    /**
     * 视图处理器注册表<br/>
     * <功能详细描述>
     *
     * @return ViewHandlerRegistry [返回类型说明]
     * @throws throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Bean(name = "fileContext.viewHandlerRegistry")
    public ViewHandlerRegistry viewHandlerRegistry() {
        ViewHandlerRegistry vhRegistry = new ViewHandlerRegistry();
        return vhRegistry;
    }
    
    /**
     * 注册文件容器<br/>
     * <功能详细描述>
     *
     * @return FileContextFactroy [返回类型说明]
     * @throws throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Bean(name = "fileContext")
    public FileContextFactroy fileContextFactroy() {
        FileContextFactroy fileContext = new FileContextFactroy();
        
        fileContext.setMybatisConfigLocation(this.mybatisConfigLocation);
        fileContext.setDataSource(this.dataSource);
        fileContext.setDataSourceType(this.dataSourceType);
        fileContext.setLocation(this.location);
        fileContext.setConfigLocation(this.configLocation);
        fileContext.setSystem(this.system);
        fileContext.setCacheManager(this.cacheManager);
        
        return fileContext;
    }
    
    /**
     * 返回文件容器Http请求处理器<br/>
     * <功能详细描述>
     *
     * @return FileContextHttpRequestHandler [返回类型说明]
     * @throws throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Bean(name = "fileContextHttpRequestHandler")
    public FileContextHttpRequestHandler fileContextHttpRequestHandler() {
        FileContextHttpRequestHandler requestHandler = new FileContextHttpRequestHandler();
        return requestHandler;
    }
    
    /**
     * 缩略图视图处理器<br/>
     * <功能详细描述>
     *
     * @return ThumbnailViewHandler [返回类型说明]
     * @throws throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Bean(name = "thumbnailViewHandler")
    public ThumbnailViewHandler thumbnailViewHandler() {
        ThumbnailViewHandler viewHandler = new ThumbnailViewHandler();
        return viewHandler;
    }
    
    /**
     * 默认的视图处理器<br/>
     * <功能详细描述>
     *
     * @return DefaultViewHandler [返回类型说明]
     * @throws throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Bean(name = "defaultViewHandler")
    public DefaultViewHandler defaultViewHandler() {
        DefaultViewHandler viewHandler = new DefaultViewHandler();
        return viewHandler;
    }
}
