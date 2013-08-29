/**
 * 文 件 名:  ConfigContext.java
 * 版    权:  TX Workgroup . Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  PengQingyang
 * 修改时间:  2012-10-5
 * <修改描述:>
 */
package com.tx.component.config.context;

import java.io.IOException;
import java.util.Properties;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.core.io.Resource;
import org.springframework.transaction.support.TransactionTemplate;

import com.tx.component.config.dao.ConfigPropertyItemDao;
import com.tx.component.config.dao.impl.ConfigPropertyItemDaoImpl;
import com.tx.component.config.exception.ConfigContextInitException;
import com.tx.component.config.service.ConfigPropertyItemService;
import com.tx.component.config.support.ConfigContextPropertiesPersister;

/**
 * 配置容器基础配置吃撑类<br/>
 * 用以加载系统配置，支持动态加载系统中各配置<br/>
 * 
 * @author PengQingyang
 * @version [版本号, 2012-10-5]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class ConfigContextConfigurator extends PropertyPlaceholderConfigurer implements
        InitializingBean{
    
    private static final String XML_SUFFIX = ".xml";
    
    /** 日志记录器 */
    private static Logger logger = LoggerFactory.getLogger(ConfigContextConfigurator.class);
    
    /** 默认的表后缀名 */
    private String tableSuffix;
    
    private boolean databaseSchemaUpdate = true;
    
    /** 配置容器基础配置文件资源 */
    private Resource configContextCfgLocation;
    
    /** 数据源 */
    private DataSource dataSource;
    
    /** 事务句柄 */
    private TransactionTemplate transactionTemplate;
    
    /**
     * 本类初始化时加载context/configContextCfg.xml 如果容器没有传入参数,则默认按上边的位置读取
     */
    public ConfigContextConfigurator() {
        logger.info("configContext Log:  配置容器加载器初始化开始.");
    }

    /**
     * @throws Exception
     */
    public void afterPropertiesSet() throws Exception {
        //核查配置数据源以及事务处理器
        if (dataSource == null) {
            throw new ConfigContextInitException("  请为配置容器配置数据源.");
        }
        
        
        if (this.transactionTemplate == null) {
            throw new ConfigContextInitException("  请为配置容器配置事务处理器.");
        }
        
        //加载配置容器配置文件
        if (configContextCfgLocation != null
                && configContextCfgLocation.exists()) {
            setLocation(configContextCfgLocation);
        }
        
        ConfigPropertyItemDao configPropertyItemDao = new ConfigPropertyItemDaoImpl(
                this.dataSource, "oracle", this.tableSuffix);
        ConfigPropertyItemService configPropertyItemService = new ConfigPropertyItemService(
                configPropertyItemDao, transactionTemplate);
        
        setPropertiesPersister(new ConfigContextPropertiesPersister(configPropertyItemService));
    }
    
    /**
     * @return
     * @throws IOException
     */
    //TODO:配置属性值为空的情况,log.warning
    //TODO:根据配置值,在启动时,利用log.info显示系统相关配置key及对应值
    @Override
    protected Properties mergeProperties() throws IOException {
        return super.mergeProperties();
    }
    
    /**
     * @param location
     */
    @Override
    public void setLocation(Resource location) {
        super.setLocation(location);
        //验证配置容器配置文件是否合法(xml文件)
        if (location.getFilename().endsWith(XML_SUFFIX)) {
            logger.error("配置容器初始化错误,配置容器基础配置文件不为xml文件.请修改文件名为.xml.现文件名为:{}",
                    configContextCfgLocation);
            throw new ConfigContextInitException(
                    "配置容器初始化错误,配置容器基础配置文件不为xml文件.请修改文件名为.xml.现文件名为:{}",
                    new Object[] { location });
        }
    }
    
    /**
     * @param locations
     */
    @Override
    public void setLocations(Resource[] locations) {
        //配置容器暂不支持多配置文件的形式
        throw new ConfigContextInitException("配置容器初始化错误,配置容器暂不支持多配置容器配置功能.");
    }
    
    /**
     * @return 返回 configContextCfgLocation
     */
    public Resource getConfigContextCfgLocation() {
        return configContextCfgLocation;
    }
    
    /**
     * @param 对configContextCfgLocation进行赋值
     */
    public void setConfigContextCfgLocation(Resource configContextCfgLocation) {
        this.configContextCfgLocation = configContextCfgLocation;
    }
    
    /**
     * @return 返回 dataSource
     */
    public DataSource getDataSource() {
        return dataSource;
    }
    
    /**
     * @param 对dataSource进行赋值
     */
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    
    /**
     * @return 返回 transactionTemplate
     */
    public TransactionTemplate getTransactionTemplate() {
        return transactionTemplate;
    }
    
    /**
     * @param 对transactionTemplate进行赋值
     */
    public void setTransactionTemplate(TransactionTemplate transactionTemplate) {
        this.transactionTemplate = transactionTemplate;
    }

    /**
     * @return 返回 tableSuffix
     */
    public String getTableSuffix() {
        return tableSuffix;
    }

    /**
     * @param 对tableSuffix进行赋值
     */
    public void setTableSuffix(String tableSuffix) {
        this.tableSuffix = tableSuffix;
    }

    /**
     * @return 返回 databaseSchemaUpdate
     */
    public boolean isDatabaseSchemaUpdate() {
        return databaseSchemaUpdate;
    }

    /**
     * @param 对databaseSchemaUpdate进行赋值
     */
    public void setDatabaseSchemaUpdate(boolean databaseSchemaUpdate) {
        this.databaseSchemaUpdate = databaseSchemaUpdate;
    }
}
