/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2016年10月20日
 * <修改描述:>
 */
package com.tx.core.ddlutil.builder.create.impl;

import com.tx.core.ddlutil.builder.create.AbstractCreateTableDDLBuilder;
import com.tx.core.ddlutil.builder.create.CreateTableDDLBuilder;
import com.tx.core.ddlutil.dialect.DDLDialect;
import com.tx.core.ddlutil.model.TableDef;
import com.tx.core.exceptions.util.AssertUtils;

/**
 * MySQL创建表DDL构建器<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2016年10月20日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class MysqlCreateTableDDLBuilder extends AbstractCreateTableDDLBuilder {
    
    /** <默认构造函数> */
    MysqlCreateTableDDLBuilder(DDLDialect ddlDialect) {
        super(ddlDialect);
    }
    
    /** <默认构造函数> */
    private MysqlCreateTableDDLBuilder(String tableName, DDLDialect ddlDialect) {
        super(tableName, ddlDialect);
    }
    
    /** <默认构造函数> */
    public MysqlCreateTableDDLBuilder(TableDef table, DDLDialect ddlDialect) {
        super(table, ddlDialect);
    }
    
    /**
     * @param tableName
     * @param ddlDialect
     * @return
     */
    @Override
    public CreateTableDDLBuilder newInstance(String tableName,
            DDLDialect ddlDialect) {
        AssertUtils.notEmpty(tableName, "tableName is empty.");
        AssertUtils.notNull(ddlDialect, "ddlDialect is null.");
        
        CreateTableDDLBuilder builder = new MysqlCreateTableDDLBuilder(
                tableName, ddlDialect);
        return builder;
    }
    
    /**
     * @param tableDef
     * @param ddlDialect
     * @return
     */
    @Override
    public CreateTableDDLBuilder newInstance(TableDef tableDef,
            DDLDialect ddlDialect) {
        AssertUtils.notNull(tableDef, "tableDef is empty.");
        AssertUtils.notNull(ddlDialect, "ddlDialect is null.");
        
        CreateTableDDLBuilder builder = new MysqlCreateTableDDLBuilder(
                tableDef, ddlDialect);
        return builder;
    }
    
    
}
