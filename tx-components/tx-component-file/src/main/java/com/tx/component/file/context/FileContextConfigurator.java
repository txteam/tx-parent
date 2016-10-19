/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2014年5月11日
 * <修改描述:>
 */
package com.tx.component.file.context;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.tx.component.file.context.driver.DefaultFileDefinitionResourceDriver;
import com.tx.component.file.dao.FileDefinitionDao;
import com.tx.component.file.dao.impl.FileDefinitionDaoImpl;
import com.tx.component.file.service.FileDefinitionService;
import com.tx.core.dbscript.context.DBScriptExecutorContext;
import com.tx.core.dbscript.model.DataSourceTypeEnum;
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
@Configuration
public class FileContextConfigurator implements InitializingBean {
    
    /** 日志 */
    protected Logger logger = LoggerFactory.getLogger(FileContext.class);
    
    @Bean(name = "fileContext.myBatisDaoSupport")
    public MyBatisDaoSupport fileDefinitionMyBatisDaoSupport() throws Exception {
        MyBatisDaoSupport res = MyBatisDaoSupportHelper.buildMyBatisDaoSupport(this.mybatisConfigLocation,
                new String[] { "classpath*:com/tx/component/file/**/*SqlMap.xml" },
                this.dataSourceType,
                this.dataSource);
        return res;
    }
    
    @Bean(name = "fileContext.fileDefinitionDao")
    public FileDefinitionDao fileDefinitionDao() {
        return new FileDefinitionDaoImpl();
    }
    
    @Bean(name = "fileContext.fileDefinitionService")
    public FileDefinitionService fileDefinitionService() {
        return new FileDefinitionService();
    }
    
    /** 数据源 */
    @Resource(name = "dataSource")
    private DataSource dataSource;
    
    /** 数据源类型 */
    private DataSourceTypeEnum dataSourceType = DataSourceTypeEnum.MYSQL;
    
    /** 数据脚本自动执行器 */
    @Resource(name = "dbScriptExecutorContext")
    protected DBScriptExecutorContext dbScriptExecutorContext;
    
    /** mybatis的配置文件所在目录 */
    private String mybatisConfigLocation = "classpath:context/mybatis-config.xml";
    
    /** 是否自动执行数据脚本 */
    protected boolean databaseSchemaUpdate = true;
    
    /** 如果没有指定系统，则默认的系统id */
    protected String system = "default";
    
    /** 如果没有指定系统，则默认的系统id */
    protected String module = "default";
    
    /** 本地文件存储容器的基础路径: */
    protected String location = "/filecontext/";
    
    /** 如果存在指定的driver则使用指定的driver,如果不存在，则使用defaultFileDefitionResourceDriver */
    protected FileDefinitionResourceDriver driver;
    
    /**
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        AssertUtils.notNull(system, "system is null.");
        AssertUtils.notNull(module, "module is null.");
        
        if (driver == null) {
            this.driver = new DefaultFileDefinitionResourceDriver(location);
        }
    }
    
    /**
     * @param 对dataSource进行赋值
     */
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    
    /**
     * @param 对dataSourceType进行赋值
     */
    public void setDataSourceType(DataSourceTypeEnum dataSourceType) {
        this.dataSourceType = dataSourceType;
    }
    
    /**
     * @param 对mybatisConfigLocation进行赋值
     */
    public void setMybatisConfigLocation(String mybatisConfigLocation) {
        this.mybatisConfigLocation = mybatisConfigLocation;
    }
    
    /**
     * @param 对system进行赋值
     */
    public void setSystem(String system) {
        this.system = system;
    }
    
    /**
     * @param 对module进行赋值
     */
    public void setModule(String module) {
        this.module = module;
    }
    
    /**
     * @param 对location进行赋值
     */
    public void setLocation(String location) {
        this.location = location;
        if(!StringUtils.isEmpty(location)){
            if(!location.endsWith("/") && !location.endsWith("\\")){
                this.location = location + "/";
            }
        }
    }
    
    /**
     * @param 对driver进行赋值
     */
    public void setDriver(FileDefinitionResourceDriver driver) {
        this.driver = driver;
    }
}
