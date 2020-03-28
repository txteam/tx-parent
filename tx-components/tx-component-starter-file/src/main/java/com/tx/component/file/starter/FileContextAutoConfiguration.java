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
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.Ordered;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import com.tx.component.file.FileContextConstants;
import com.tx.component.file.catalog.FileCatalog;
import com.tx.component.file.catalog.FileCatalogPermissionEnum;
import com.tx.component.file.catalog.impl.LocalFileCatalog;
import com.tx.component.file.catalog.impl.OSSLocalAwareFileCatalog;
import com.tx.component.file.context.FileContextFactory;
import com.tx.component.file.controller.FileContextResourceHttpRequestHandler;
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
    }
    
    /**
     * 默认的文件目录<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return FileCatalog [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    //如果有配置默认中的存放路径，则自动创建默认存储目录catalog,其优先级为最低
    @ConditionalOnProperty(prefix = FileContextConstants.PROPERTIES_PREFIX
            + ".default", value = "path", havingValue = "true")
    @Bean(name = "defaultFileCatalog")
    public FileCatalog defaultFileCatalog() {
        FileCatalog catalog = null;
        if (StringUtils
                .isEmpty(this.properties.getDefaults().getOssAccessKeyId())) {
            LocalFileCatalog lfc = new LocalFileCatalog() {
                
                @Override
                public boolean match(String relativePath) {
                    return true;
                }
                
                @Override
                public int getOrder() {
                    return Ordered.LOWEST_PRECEDENCE;
                }
                
                @Override
                public FileCatalogPermissionEnum getPermission() {
                    return FileCatalogPermissionEnum.PUBLIC_READ_WRITE;
                }
            };
            lfc.setCatalog(FileContextConstants.DEFAULT_CATALOG);
            lfc.setPath(this.properties.getDefaults().getPath());
            catalog = lfc;
        } else {
            OSSLocalAwareFileCatalog ossLfc = new OSSLocalAwareFileCatalog() {
                
                @Override
                public boolean match(String relativePath) {
                    return true;
                }
                
                @Override
                public int getOrder() {
                    return Ordered.LOWEST_PRECEDENCE;
                }
                
                @Override
                public FileCatalogPermissionEnum getPermission() {
                    return FileCatalogPermissionEnum.PUBLIC_READ_WRITE;
                }
            };
            ossLfc.setCatalog(FileContextConstants.DEFAULT_CATALOG);
            ossLfc.setPath(this.properties.getDefaults().getPath());
            ossLfc.setAccessKeyId(
                    this.properties.getDefaults().getOssAccessKeyId());
            
            AssertUtils.notEmpty(
                    this.properties.getDefaults().getOssBucketName(),
                    "default.ossBucketName is empty.");
            AssertUtils.notEmpty(
                    this.properties.getDefaults().getOssSecretAccessKey(),
                    "default.ossSecretAccessKey is empty.");
            AssertUtils.notEmpty(this.properties.getDefaults().getOssEndpoint(),
                    "default.ossEndpoint is empty.");
            
            ossLfc.setSecretAccessKey(
                    this.properties.getDefaults().getOssSecretAccessKey());
            ossLfc.setEndpoint(this.properties.getDefaults().getOssEndpoint());
            ossLfc.setBucketName(
                    this.properties.getDefaults().getOssBucketName());
            catalog = ossLfc;
        }
        return catalog;
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
    public FileContextFactory fileContextFactroy() {
        FileContextFactory fileContext = new FileContextFactory();
        
        return fileContext;
    }
    
    /**
     * 控制层自动配置层<br/>
     * <功能详细描述>
     * 
     * @author  Administrator
     * @version  [版本号, 2020年3月19日]
     * @see  [相关类/方法]
     * @since  [产品/模块版本]
     */
    @Configuration
    @AutoConfigureAfter({ DispatcherServletAutoConfiguration.class })
    public class ControllerAutoConfiguration {
        
        /**
         * 返回文件容器Http请求处理器<br/>
         * <功能详细描述>
         *
         * @return FileContextHttpRequestHandler [返回类型说明]
         * @throws throws [异常类型] [异常说明]
         * @see [类、类#方法、类#成员]
         */
        @Bean(name = "file.resourceHttpRequestHandler")
        public FileContextResourceHttpRequestHandler resourceHttpRequestHandler() {
            FileContextResourceHttpRequestHandler requestHandler = new FileContextResourceHttpRequestHandler();
            return requestHandler;
        }
        
    }
}
