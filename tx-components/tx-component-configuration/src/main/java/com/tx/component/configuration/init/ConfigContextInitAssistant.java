/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2017年12月6日
 * <修改描述:>
 */
package com.tx.component.configuration.init;

import java.io.InputStream;

import javax.sql.DataSource;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.tx.core.dbscript.model.DataSourceTypeEnum;
import com.tx.core.ddlutil.builder.DDLBuilder;
import com.tx.core.ddlutil.builder.alter.AlterTableDDLBuilder;
import com.tx.core.ddlutil.builder.create.CreateTableDDLBuilder;
import com.tx.core.ddlutil.executor.TableDDLExecutor;
import com.tx.core.ddlutil.executor.TableDDLExecutorFactory;
import com.tx.core.exceptions.SILException;

/**
 * <功能简述>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2017年12月6日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class ConfigContextInitAssistant implements InitializingBean,
        ResourceLoaderAware, ApplicationContextAware {
    
    /** spring容器句柄 */
    private ApplicationContext applicationContext;
    
    /** spring中 resouceLoader句柄 */
    private ResourceLoader resourceLoader;
    
    private final TableDDLExecutor tableDDLExecutor;
    
    /** 数据源类型枚举 */
    private final DataSourceTypeEnum dataSourceType;
    
    /** 数据源 */
    private final DataSource dataSource;
    
    /** transactionTemplate: 如果存在事务则在当前事务中执行 */
    private final TransactionTemplate transactionTemplate;
    
    /** 动态表配置路径 */
    private final String configLocation;
    
    /** <默认构造函数> */
    public ConfigContextInitAssistant(DataSourceTypeEnum dataSourceType,
            DataSource dataSource, TransactionTemplate transactionTemplate,
            String configLocation) {
        super();
        this.dataSourceType = dataSourceType;
        this.dataSource = dataSource;
        this.transactionTemplate = transactionTemplate;
        this.configLocation = configLocation;
        
        this.tableDDLExecutor = TableDDLExecutorFactory
                .buildTableDDLExecutor(dataSourceType, dataSource);
    }
    
    /**
     * @param arg0
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        this.applicationContext = applicationContext;
    }
    
    /**
     * @param arg0
     */
    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }
    
    /**
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        //为容器创建或升级表<br/>
        initTables();
        
        //从配置中初始化配置容器
        this.transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                //加载动态表引擎配置
                initConfigContext();
            }
        });
    }
    
    /**
     * 为容器创建或升级表
     * <功能详细描述> [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    protected void initTables() {
        table_core_config_context();
    }
    
    /**
     * 加载动态表引擎配置<br/>
     * <功能详细描述> [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
   protected void initConfigContext() {
       Resource configResource = this.resourceLoader.getResource(configLocation);
       if(!configResource.exists()){
           return ;
       }
       
       InputStream ins = null;
       String xml = null;
       try {
           ins = configResource.getInputStream();
           xml = IOUtils.toString(ins, "UTF-8");
       } catch (Exception e) {
           throw new SILException("loadDynamicTableEngineConfig exception.", e);
       }
   }
    
    
    
    /**
    * dt_table_service_type:DynamicTableServiceType实体表<br/>
    * <功能详细描述> [参数说明]
    * 
    * @return void [返回类型说明]
    * @exception throws [异常类型] [异常说明]
    * @see [类、类#方法、类#成员]
    */
    private void table_core_config_context() {
        //  create table core_config_context(
        //      id varchar2(64) not null,
        //      systemId varchar2(64) not null,
        //      keyName varchar2(64) not null,
        //      value varchar2(64) not null,
        //      name varchar2(64) not null,
        //      description varchar2(512),
        //      createDate timestamp,
        //      lastUpdateDate timestamp,
        //      validateExpression varchar2(128),
        //      primary key(id)
        //  );
        //  create unique index idx_core_config_context_00 on core_config_context(keyName,systemId);
        
        String tableName = "core_config_context";
        CreateTableDDLBuilder createDDLBuilder = null;
        AlterTableDDLBuilder alterDDLBuilder = null;
        DDLBuilder<?> ddlBuilder = null;
        if (this.tableDDLExecutor.exists(tableName)) {
            alterDDLBuilder = this.tableDDLExecutor
                    .generateAlterTableDDLBuilder(tableName);
            ddlBuilder = alterDDLBuilder;
        } else {
            createDDLBuilder = this.tableDDLExecutor
                    .generateCreateTableDDLBuilder(tableName);
            ddlBuilder = createDDLBuilder;
        }
        ddlBuilder.newColumnOfVarchar(true, "id", 64, true, null)//id varchar(64) not null,
                .newColumnOfVarchar("systemId", 64, true, null)
                .newColumnOfVarchar("keyName", 64, true, null)
                .newColumnOfVarchar("value", 64, true, null)
                .newColumnOfVarchar("name", 64, true, null)
                .newColumnOfVarchar("description", 512, false, null)
                .newColumnOfVarchar("validateExpression", 128, false, null)
                .newColumnOfDate("lastUpdateDate", true, true)
                .newColumnOfDate("createDate", true, true);
        ddlBuilder.newIndex(true,
                "idx_core_config_context_00",
                "keyName",
                "systemId");
        
        if (alterDDLBuilder != null
                && alterDDLBuilder.isNeedAlter(false, false)) {
            this.tableDDLExecutor.alter(alterDDLBuilder, false, false);
        } else if (createDDLBuilder != null) {
            this.tableDDLExecutor.create(createDDLBuilder);
        }
    }
}
