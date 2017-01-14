/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2014年5月11日
 * <修改描述:>
 */
package com.tx.component.file.context;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.SingletonBeanRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;

import com.tx.component.file.FileContextConstants;
import com.tx.component.file.dao.FileDefinitionDao;
import com.tx.component.file.dao.impl.FileDefinitionDaoImpl;
import com.tx.component.file.service.FileDefinitionService;
import com.tx.core.dbscript.model.DataSourceTypeEnum;
import com.tx.core.ddlutil.executor.TableDDLExecutor;
import com.tx.core.ddlutil.executor.TableDDLExecutorFactory;
import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.mybatis.support.MyBatisDaoSupport;
import com.tx.core.mybatis.support.MyBatisDaoSupportHelper;

/**
 * 文件容器配置器<br/>
 * 
 * @author Administrator
 * @version [版本号, 2014年5月11日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class FileContextConfigurator implements ApplicationContextAware,
        InitializingBean, BeanFactoryAware {
    
    /** 日志 */
    protected Logger logger = LoggerFactory.getLogger(FileContext.class);
    
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
    @Bean(name = "fileContext.myBatisDaoSupport")
    public MyBatisDaoSupport fileDefinitionMyBatisDaoSupport() throws Exception {
        MyBatisDaoSupport res = MyBatisDaoSupportHelper.buildMyBatisDaoSupport(this.mybatisConfigLocation,
                new String[] { "classpath*:com/tx/component/file/**/*SqlMap_FILE_CONTEXT.xml" },
                this.dataSourceType,
                this.dataSource);
        return res;
    }
    
    /**
      * 文件定义持久层<br/>
      * <功能详细描述>
      * @return [参数说明]
      * 
      * @return FileDefinitionDao [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    @Bean(name = "fileContext.fileDefinitionDao")
    public FileDefinitionDao fileDefinitionDao() {
        FileDefinitionDao fdDao = new FileDefinitionDaoImpl();
        return fdDao;
    }
    
    /**
      * 文件定义业务层<br/>
      * <功能详细描述>
      * @return [参数说明]
      * 
      * @return FileDefinitionService [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    @Bean(name = "fileContext.fileDefinitionService")
    public FileDefinitionService fileDefinitionService() {
        FileDefinitionService fdService = new FileDefinitionService();
        return fdService;
    }
    
    /** spring容器句柄 */
    protected static ApplicationContext applicationContext;
    
    /** mybatis的配置文件所在目录 */
    private String mybatisConfigLocation = "classpath:context/mybatis-config.xml";
    
    /** 数据源类型 */
    private DataSourceTypeEnum dataSourceType = DataSourceTypeEnum.MYSQL;
    
    /** 如果没有指定系统，则默认的系统id */
    protected String system = FileContextConstants.DEFAULT_SYSTEM;
    
    /** 默认的存储路径 */
    protected String location = "classpath:context/file-context-config.xml";;
    
    /** 数据源 */
    private DataSource dataSource;
    
    /** 表DDL执行器 */
    protected TableDDLExecutor tableDDLExecutor;
    
    /** 单例对象注册方法 */
    protected SingletonBeanRegistry singletonBeanRegistry;
    
    /** Bean定义注册机 */
    protected BeanDefinitionRegistry beanDefinitionRegistry;
    
    /** 文件定义业务层 */
    private FileDefinitionService fileDefinitionService;
    
    /**
      * 获取文件定义业务层<br/>
      * <功能详细描述>
      * @return [参数说明]
      * 
      * @return FileDefinitionService [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    protected FileDefinitionService getFileDefinitionService() {
        if (this.fileDefinitionService != null) {
            return this.fileDefinitionService;
        }
        this.fileDefinitionService = applicationContext.getBean("fileContext.fileDefinitionService",
                FileDefinitionService.class);
        return this.fileDefinitionService;
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
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        AssertUtils.notNull(system, "system is null.");
        AssertUtils.notTrue(dataSource == null, "dataSource all is null.");
        
        this.tableDDLExecutor = TableDDLExecutorFactory.buildTableDDLExecutor(dataSourceType,
                dataSource);
        
        //进行容器构建
        doBuild();
        
        //初始化容器
        doInitContext();
    }
    
    /**
     * 基础数据容器构建
     * <功能详细描述>
     * @throws Exception [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    protected void doBuild() throws Exception {
    }
    
    /**
      * 初始化容器<br/>
      * <功能详细描述>
      * @throws Exception [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
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
     * @param 对location进行赋值
     */
    public void setLocation(String location) {
        this.location = location;
    }
}
