///*
// * 描          述:  <描述>
// * 修  改   人:  brady
// * 修改时间:  2013-12-17
// * <修改描述:>
// */
//package com.tx.core.dbscript;
//
//import java.util.Date;
//
//import javax.sql.DataSource;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.core.io.ByteArrayResource;
//import org.springframework.dao.DataAccessException;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
//import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
//
//import com.tx.core.dbscript.context.DBScriptExecutorContext;
//import com.tx.core.dbscript.model.DBScriptContext;
//import com.tx.core.dbscript.model.DataSourceTypeEnum;
//
///**
// * 表创建器<br/>
// * <功能详细描述>
// * 
// * @author  brady
// * @version  [版本号, 2013-12-17]
// * @see  [相关类/方法]
// * @since  [产品/模块版本]
// */
//public class TableCreator {
//    
//    private Logger logger = LoggerFactory.getLogger(TableCreator.class);
//    
//    /** 脚本执行器容器 */
//    private DBScriptExecutorContext context;
//    
//    /** 脚本创建器是否启用 */
//    private boolean enable = false;
//    
//    /** 在执行脚本过程中如果出现的错误是否继续执行 */
//    private boolean continueOnError = true;
//    
//    /** 是否忽略脚本执行过程中错误的drop语句 */
//    private boolean ignoreFailedDrops = true;
//    
//    /** 是否自动升级不存在容器表中的数据 */
//    private boolean updateNotExistTableInContext = false;
//    
//    /** sql脚本编码 */
//    private String sqlScriptEncoding;
//    
//    /** 数据源 */
//    private DataSource dataSource;
//    
//    private JdbcTemplate jdbcTemplate;
//    
//    private DataSourceTypeEnum dataSourceType;
//    
//    /** <默认构造函数> */
//    public TableCreator(DBScriptExecutorContext context) {
//        super();
//        this.context = context;
//        this.enable = this.context.isEnable();
//        this.continueOnError = this.context.isContinueOnError();
//        this.ignoreFailedDrops = this.context.isIgnoreFailedDrops();
//        this.sqlScriptEncoding = this.context.getSqlScriptEncoding();
//        this.dataSource = this.context.getDataSource();
//        this.jdbcTemplate = this.context.getJdbcTemplate();
//        this.dataSourceType = this.context.getDataSourceType();
//        this.updateNotExistTableInContext = this.context.isUpdateNotExistTableInContext();
//    }
//    
//    /**
//      * 如果表不存在，则根据表定义创建表
//      * 如果存在，并且版本为当前版本，则继续执行后续逻辑
//      * 如果版本不为最新版本，则对表结构进行升级
//      *<功能详细描述>
//      * @param jdbcTemplate
//      * @param tableCreator [参数说明]
//      * 
//      * @return void [返回类型说明]
//      * @exception throws [异常类型] [异常说明]
//      * @see [类、类#方法、类#成员]
//     */
//    public void createOrUpdateTable(TableDefinition tableDefinition) {
//        //如果自动创建为不启动，则不进行后续逻辑执行
//        if (!this.enable) {
//            return;
//        }
//        
//        logger.info("开始：创建或更新表:{}", new Object[] { tableDefinition.tableName() });
//        if (!isExist(tableDefinition)) {
//            logger.info("     检测到：表'{}'在数据库中不存在.", new Object[] { tableDefinition.tableName() });
//            Date now = new Date();
//            DBScriptContext dbScriptContext = new DBScriptContext();
//            dbScriptContext.setTableName(tableDefinition.tableName());
//            dbScriptContext.setTableVersion(tableDefinition.tableVersion());
//            dbScriptContext.setCreateDate(now);
//            dbScriptContext.setLastUpdateDate(now);
//            
//            //实际创建表
//            doCreateTable(tableDefinition);
//            if (context.isExistInContext(tableDefinition.tableName())) {
//                DBScriptContext dbScriptContextTemp = context
//                        .findDBScriptContextByTableName(tableDefinition.tableName());
//                context.deleteById(dbScriptContextTemp.getId());
//            }
//            
//            context.insert(dbScriptContext);
//            logger.info("结束：创建表:{}成功.", new Object[] { tableDefinition.tableName() });
//        } else {
//            logger.info("     检测到：表'{}'在数据库中已经存在.", new Object[] { tableDefinition.tableName() });
//            Date now = new Date();
//            DBScriptContext dbScriptContext = new DBScriptContext();
//            dbScriptContext.setCreateDate(now);
//            dbScriptContext.setLastUpdateDate(now);
//            dbScriptContext.setTableName(tableDefinition.tableName());
//            dbScriptContext.setTableVersion(tableDefinition.tableVersion());
//            
//            if (context.isExistInContext(tableDefinition.tableName())) {
//                DBScriptContext dbScriptContextTemp = context
//                        .findDBScriptContextByTableName(tableDefinition.tableName());
//                if (!dbScriptContextTemp.getTableVersion().equals(tableDefinition.tableVersion())) {
//                    logger.info("     检测到：表'{}'版需进行升级.{}->{}",
//                            new Object[] { tableDefinition.tableName(), dbScriptContextTemp.getTableVersion(),
//                                    tableDefinition.tableVersion() });
//                    //根据当前版本号对表进行升级
//                    doUpdateTable(dbScriptContext.getTableVersion(), tableDefinition);
//                    dbScriptContext.setCreateDate(dbScriptContext.getCreateDate());
//                    //删除原表中容器对其的管理
//                    context.deleteById(dbScriptContextTemp.getId());
//                    context.insert(dbScriptContext);
//                    logger.info("结束：升级表:{}成功.", new Object[] { tableDefinition.tableName() });
//                } else {
//                    logger.info("结束： 检测到：表'{}'版本'{}'一致无需进行升级.",
//                            new Object[] { tableDefinition.tableName(), tableDefinition.tableVersion() });
//                }
//            } else {
//                logger.warn("     在容器中并未检测到原表信息。");
//                if (this.updateNotExistTableInContext) {
//                    logger.warn("     在容器中并未检测到原表版本，容器现配置强制对原表进行一次默认升级.如无需升级，请关闭updateNotExistTableInContext值为false");
//                    doUpdateTable(null, tableDefinition);
//                    logger.info("结束：升级表:{}成功.", new Object[] { tableDefinition.tableName() });
//                }
//                context.insert(dbScriptContext);
//            }
//        }
//    }
//    
//    /**
//      * 判断表是否已经被创建<br/>
//      *<功能详细描述>
//      * @param tableCreator
//      * @return [参数说明]
//      * 
//      * @return boolean [返回类型说明]
//      * @exception throws [异常类型] [异常说明]
//      * @see [类、类#方法、类#成员]
//     */
//    protected boolean isExist(TableDefinition tableDefinition) {
//        boolean isExistFlag = false;
//        try {
//            this.jdbcTemplate.queryForObject("SELECT COUNT(1) FROM " + tableDefinition.tableName(), Integer.class);
//            isExistFlag = true;
//        } catch (DataAccessException e) {
//            isExistFlag = false;
//        }
//        return isExistFlag;
//    }
//    
//    /**
//      * 获取创建表的语句<br/>
//      *<功能详细描述>
//      * @param tableDefinition [参数说明]
//      * 
//      * @return void [返回类型说明]
//      * @exception throws [异常类型] [异常说明]
//      * @see [类、类#方法、类#成员]
//     */
//    protected void doCreateTable(TableDefinition tableDefinition) {
//        String createTableSql = tableDefinition.createTableScript(this.dataSourceType);
//        
//        ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator();
//        resourceDatabasePopulator.setContinueOnError(this.continueOnError);
//        resourceDatabasePopulator.setIgnoreFailedDrops(this.ignoreFailedDrops);
//        resourceDatabasePopulator.setSqlScriptEncoding(sqlScriptEncoding);
//        
//        resourceDatabasePopulator.addScript(new ByteArrayResource(createTableSql.getBytes()));
//        //执行脚本
//        DatabasePopulatorUtils.execute(resourceDatabasePopulator, this.dataSource);
//    }
//    
//    protected void doUpdateTable(String currentVersion, TableDefinition tableDefinition) {
//        String updateTableSql = tableDefinition.updateTableScript(currentVersion, this.dataSourceType);
//        
//        ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator();
//        resourceDatabasePopulator.setContinueOnError(this.continueOnError);
//        resourceDatabasePopulator.setIgnoreFailedDrops(this.ignoreFailedDrops);
//        resourceDatabasePopulator.setSqlScriptEncoding(sqlScriptEncoding);
//        
//        resourceDatabasePopulator.addScript(new ByteArrayResource(updateTableSql.getBytes()));
//        //执行脚本
//        DatabasePopulatorUtils.execute(resourceDatabasePopulator, this.dataSource);
//    }
//}
