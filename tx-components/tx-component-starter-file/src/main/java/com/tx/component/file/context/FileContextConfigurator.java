/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2014年5月11日
 * <修改描述:>
 */
package com.tx.component.file.context;

import com.tx.component.file.FileContextConstants;
import com.tx.component.file.service.FileDefinitionPersistService;
import com.tx.component.file.service.FileDefinitionService;
import com.tx.component.file.viewhandler.FileContextHttpRequestHandler;
import com.tx.component.file.viewhandler.ViewHandlerRegistry;
import com.tx.component.file.viewhandler.impl.DefaultViewHandler;
import com.tx.component.file.viewhandler.impl.ThumbnailViewHandler;
import com.tx.core.ddlutil.executor.TableDDLExecutor;
import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.util.dialect.DataSourceTypeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.SingletonBeanRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * 文件容器配置器<br/>
 *
 * @author Administrator
 * @version [版本号, 2014年5月11日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Configuration
public class FileContextConfigurator implements ApplicationContextAware,
        InitializingBean, BeanFactoryAware {


    /**
     * spring容器句柄
     */
    protected static ApplicationContext applicationContext;

    /**
     * mybatisDaoSupport句柄<br/>
     * <功能详细描述>
     * @return
     * @throws Exception [参数说明]
     *
     * @return MyBatisDaoSupport [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
//    @Bean(name = "fileContext.myBatisDaoSupport")
//    public MyBatisDaoSupport fileDefinitionMyBatisDaoSupport() throws Exception {
//        MyBatisDaoSupport res = MyBatisDaoSupportHelper.buildMyBatisDaoSupport(this.mybatisConfigLocation,
//                new String[] { "classpath*:com/tx/component/file/**/*SqlMap_FILE_CONTEXT.xml" },
//                this.dataSourceType,
//                this.dataSource);
//        return res;
//    }
    /**
     * 日志
     */
    protected Logger logger = LoggerFactory.getLogger(FileContext.class);
    /**
     * 数据源类型
     */
    protected DataSourceTypeEnum dataSourceType = DataSourceTypeEnum.MYSQL;
    /**
     * 如果没有指定系统，则默认的系统id
     */
    protected String system = FileContextConstants.DEFAULT_SYSTEM;
    /**
     * 默认的存储路径
     */
    protected String configLocation = "classpath:context/file_context_config.xml";
    /**  */
    protected String location;
    /**
     * 缓存Manager
     */
    protected CacheManager cacheManager;
    /**
     * 数据源
     */
    protected DataSource dataSource;
    /**
     * 表DDL执行器
     */
    protected TableDDLExecutor tableDDLExecutor;
    /**
     * 单例对象注册方法
     */
    protected SingletonBeanRegistry singletonBeanRegistry;
    /**
     * Bean定义注册机
     */
    protected BeanDefinitionRegistry beanDefinitionRegistry;
    /**
     * mybatis的配置文件所在目录
     */
    private String mybatisConfigLocation = "classpath:context/mybatis-config.xml";

    /**
     * 文件定义持久层<br/>
     * <功能详细描述>
     *
     * @return FileDefinitionDao [返回类型说明]
     * @throws throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
//    @Bean(name = "fileContext.fileDefinitionDao")
//    public FileDefinitionDao fileDefinitionDao() {
//        FileDefinitionDao fdDao = new FileDefinitionDaoImpl();
//        return fdDao;
//    }

    /**
     * 文件定义业务层<br/>
     * <功能详细描述>
     *
     * @return FileDefinitionService [返回类型说明]
     * @throws throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Bean(name = "fileContext.fileDefinitionService")
    public FileDefinitionService fileDefinitionService() {
        FileDefinitionService fdService = new FileDefinitionService();
        return fdService;
    }

    /**
     * 文件持久业务层<br/>
     * <功能详细描述>
     *
     * @return FileDefinitionPersistService [返回类型说明]
     * @throws throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Bean(name = "fileContext.fileDefinitionPersistService")
    public FileDefinitionPersistService fileDefinitionPersistService() {
        FileDefinitionPersistService fdpService = new FileDefinitionPersistService();
        return fdpService;
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

    /**
     * @param beanFactory
     * @throws BeansException
     */
    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        AssertUtils.isInstanceOf(BeanDefinitionRegistry.class,
                beanFactory,
                "beanFactory is not BeanDefinitionRegistry instance.");
        this.beanDefinitionRegistry = (BeanDefinitionRegistry) beanFactory;

        AssertUtils.isInstanceOf(SingletonBeanRegistry.class,
                beanFactory,
                "beanFactory is not SingletonBeanRegistry instance.");
        this.singletonBeanRegistry = (SingletonBeanRegistry) beanFactory;
    }

    /**
     * @param applicationContext
     * @throws BeansException
     */
    @Override
    public final void setApplicationContext(
            ApplicationContext applicationContext) throws BeansException {
        FileContextConfigurator.applicationContext = applicationContext;
    }

    /**
     * @param beanName
     * @param beanDefinition
     * @desc 向spring容器注册bean
     */
    protected void registerSingletonBean(String beanName, Object bean) {
        if (!this.singletonBeanRegistry.containsSingleton(beanName)) {
            this.singletonBeanRegistry.registerSingleton(beanName, bean);
        }
    }



    /**
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        AssertUtils.notNull(system, "system is null.");
        AssertUtils.notTrue(dataSource == null, "dataSource all is null.");

        if (this.cacheManager == null) {
            this.cacheManager = new ConcurrentMapCacheManager();
        }

        registerSingletonBean("fileContext.cacheManager", this.cacheManager);

        //进行容器构建
        doBuild();

        //初始化容器
        doInitContext();
    }

    /**
     * 基础数据容器构建
     * <功能详细描述>
     *
     * @return void [返回类型说明]
     * @throws Exception [参数说明]
     * @throws throws    [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    protected void doBuild() throws Exception {
    }

    /**
     * 初始化容器<br/>
     * <功能详细描述>
     *
     * @return void [返回类型说明]
     * @throws Exception [参数说明]
     * @throws throws    [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    protected void doInitContext() throws Exception {
    }

    /**
     * @param 对mybatisConfigLocation进行赋值
     */
    public void setMybatisConfigLocation(String mybatisConfigLocation) {
        this.mybatisConfigLocation = mybatisConfigLocation;
    }

    /**
     * @param 对dataSourceType进行赋值
     */
    public void setDataSourceType(DataSourceTypeEnum dataSourceType) {
        this.dataSourceType = dataSourceType;
    }

    /**
     * @param 对dataSource进行赋值
     */
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * @param 对system进行赋值
     */
    public void setSystem(String system) {
        this.system = system;
    }

    /**
     * @param cacheManager进行赋值
     */
    public void setCacheManager(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    /**
     * @return 返回 location
     */
    public String getLocation() {
        return location;
    }

    /**
     * @param 对location进行赋值
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * @return 返回 configLocation
     */
    public String getConfigLocation() {
        return configLocation;
    }

    /**
     * @param 对configLocation进行赋值
     */
    public void setConfigLocation(String configLocation) {
        this.configLocation = configLocation;
    }
}
