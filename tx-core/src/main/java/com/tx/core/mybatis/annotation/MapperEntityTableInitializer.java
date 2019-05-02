/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2018年6月10日
 * <修改描述:>
 */
package com.tx.core.mybatis.annotation;

import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.tx.core.dbscript.initializer.AbstractTableInitializer;
import com.tx.core.ddlutil.builder.alter.AlterTableDDLBuilder;
import com.tx.core.ddlutil.builder.create.CreateTableDDLBuilder;
import com.tx.core.ddlutil.executor.TableDDLExecutor;
import com.tx.core.ddlutil.helper.JPAEntityDDLHelper;
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
public class MapperEntityTableInitializer
        extends AbstractTableInitializer {
    
    private String basePackages = "com.tx";
    
    /** <默认构造函数> */
    public MapperEntityTableInitializer() {
        super();
    }
    
    /** <默认构造函数> */
    public MapperEntityTableInitializer(String basePackages) {
        super();
        this.basePackages = basePackages;
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
        Set<Class<?>> typeSet = ClassScanUtils.scanByAnnotation(
                MapperEntity.class, basePackageArray);
        
        StringBuilder sb = new StringBuilder();
        for (Class<?> type : typeSet) {
            TableDef td = JPAEntityDDLHelper.analyzeToTableDefDetail(type,
                    tableDDLExecutor.getDDLDialect());
            
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
}
