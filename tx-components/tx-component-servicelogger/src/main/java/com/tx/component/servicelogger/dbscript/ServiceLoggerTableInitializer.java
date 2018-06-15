/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2018年6月10日
 * <修改描述:>
 */
package com.tx.component.servicelogger.dbscript;

import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.annotation.AnnotationUtils;

import com.tx.component.servicelogger.annotation.ServiceLog;
import com.tx.core.dbscript.initializer.AbstractTableInitializer;
import com.tx.core.ddlutil.builder.alter.AlterTableDDLBuilder;
import com.tx.core.ddlutil.builder.create.CreateTableDDLBuilder;
import com.tx.core.ddlutil.executor.TableDDLExecutor;
import com.tx.core.ddlutil.helper.JPAEntityDDLHelper;
import com.tx.core.ddlutil.model.JPAEntityTableDef;
import com.tx.core.ddlutil.model.TableDef;
import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.util.ClassScanUtils;

/**
 * 自动持久化实体表初始化实现<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2018年6月10日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class ServiceLoggerTableInitializer extends AbstractTableInitializer implements InitializingBean{
    
    private String basePackages = "com.tx";
    
    /** 表DDL执行器 */
    private TableDDLExecutor tableDDLExecutor;
    
    /** 是否自动执行 */
    private boolean tableAutoInitialize = false;
    
    /** <默认构造函数> */
    public ServiceLoggerTableInitializer() {
        super();
    }
    
    /** <默认构造函数> */
    public ServiceLoggerTableInitializer(TableDDLExecutor tableDDLExecutor) {
        super();
        this.tableDDLExecutor = tableDDLExecutor;
    }
    
    /** <默认构造函数> */
    public ServiceLoggerTableInitializer(TableDDLExecutor tableDDLExecutor,
            boolean tableAutoInitialize) {
        super();
        this.tableDDLExecutor = tableDDLExecutor;
        this.tableAutoInitialize = tableAutoInitialize;
    }
    
    
    
    /**
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        if(this.tableDDLExecutor != null && this.tableAutoInitialize){
            initialize(this.tableDDLExecutor, this.tableAutoInitialize);
        }
    }

    /**
     * @param tableAutoInitialize
     * @return
     */
    @Override
    public String tables(TableDDLExecutor tableDDLExecutor,
            boolean tableAutoInitialize) {
        AssertUtils.notNull(tableDDLExecutor, "tableDDLExecutor is null.");
        
        String[] basePackageArray = StringUtils
                .splitByWholeSeparator(basePackages.trim(), ",");
        Set<Class<?>> typeSet = ClassScanUtils
                .scanByAnnotation(ServiceLog.class, basePackageArray);
        
        StringBuilder sb = new StringBuilder();
        for (Class<?> type : typeSet) {
            JPAEntityTableDef td = (JPAEntityTableDef) JPAEntityDDLHelper
                    .analyzeToTableDefDetail(type,
                            tableDDLExecutor.getDDLDialect());
            ServiceLog annotation = AnnotationUtils.findAnnotation(type,
                    ServiceLog.class);
            if (!StringUtils.isBlank(annotation.tablename())) {
                td.setTableName(annotation.tablename());
            }
            
            sb.append(COMMENT_PREFIX)
                    .append("----------table:")
                    .append(td.getTableName())
                    .append("----------")
                    .append(COMMENT_SUFFIX)
                    .append(LINE_SEPARATOR);
            
            sb.append(generateTableScriptByTableDef(td,
                    tableDDLExecutor,
                    tableAutoInitialize));
            
            sb.append(LINE_SEPARATOR);
        }
        
        return sb.toString();
    }
    
    /**
     * 根据表定义生成表的ddl语句<br/>
     * <功能详细描述>
     * @param td
     * @param tableDDLExecutor
     * @param tableAutoInitialize
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    private String generateTableScriptByTableDef(TableDef td,
            TableDDLExecutor tableDDLExecutor, boolean tableAutoInitialize) {
        String tableName = td.getTableName();
        
        CreateTableDDLBuilder createDDLBuilder = null;
        AlterTableDDLBuilder alterDDLBuilder = null;
        
        if (tableDDLExecutor.exists(tableName)) {
            alterDDLBuilder = tableDDLExecutor.generateAlterTableDDLBuilder(td);
        } else {
            createDDLBuilder = tableDDLExecutor
                    .generateCreateTableDDLBuilder(td);
        }
        
        if (alterDDLBuilder != null
                && alterDDLBuilder.compare().isNeedAlter()) {
            if (tableAutoInitialize) {
                tableDDLExecutor.alter(alterDDLBuilder);
            }
            return alterDDLBuilder.alterSql();
        } else if (createDDLBuilder != null) {
            if (tableAutoInitialize) {
                tableDDLExecutor.create(createDDLBuilder);
            }
            return createDDLBuilder.createSql();
        }
        return "";
    }
    
    /**
     * @return 返回 basePackages
     */
    public String getBasePackages() {
        return basePackages;
    }
    
    /**
     * @param 对basePackages进行赋值
     */
    public void setBasePackages(String basePackages) {
        this.basePackages = basePackages;
    }

    /**
     * @return 返回 tableDDLExecutor
     */
    public TableDDLExecutor getTableDDLExecutor() {
        return tableDDLExecutor;
    }

    /**
     * @param 对tableDDLExecutor进行赋值
     */
    public void setTableDDLExecutor(TableDDLExecutor tableDDLExecutor) {
        this.tableDDLExecutor = tableDDLExecutor;
    }

    /**
     * @return 返回 tableAutoInitialize
     */
    public boolean isTableAutoInitialize() {
        return tableAutoInitialize;
    }

    /**
     * @param 对tableAutoInitialize进行赋值
     */
    public void setTableAutoInitialize(boolean tableAutoInitialize) {
        this.tableAutoInitialize = tableAutoInitialize;
    }
}
