/*
 * 描          述:  <描述>
 * 修  改   人:  PengQingyang
 * 修改时间:  2013-8-19
 * <修改描述:>
 */
package com.tx.core.util.dialect;

import org.hibernate.dialect.Dialect;

import com.tx.core.ddlutil.dialect.Dialect4DDL;
import com.tx.core.ddlutil.dialect.MysqlDDLDialect;

/**
 * 数据库类型<br/>
 * <功能详细描述>
 * 
 * @author  PengQingyang
 * @version  [版本号, 2013-8-19]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public enum DataSourceTypeEnum {
    /**
     * oracle
     */
    ORACLE("ORACLE", DialectUtils.oracle10gDialect),
    /**
     * H2
     */
    H2("H2", DialectUtils.h2Dialect),
    /**
     * MYSQL
     */
    MYSQL("MYSQL", DialectUtils.mySQL57Dialect, MysqlDDLDialect.INSTANCE);
    
    /**
     * 数据源类型名
     */
    private final String name;
    
    /**
     * 对应oracle方言
     */
    private final Dialect dialect;
    
    /** ddl生成方言 */
    private final Dialect4DDL dialect4DDL;
    
    /**
     * <默认构造函数>
     */
    private DataSourceTypeEnum(String name, Dialect dialect) {
        this.name = name;
        this.dialect = dialect;
        this.dialect4DDL = null;
    }
    
    /**
     * <默认构造函数>
     */
    private DataSourceTypeEnum(String name, Dialect dialect,
            Dialect4DDL dialect4DDL) {
        this.name = name;
        this.dialect = dialect;
        this.dialect4DDL = dialect4DDL;
    }
    
    /**
     * @return 返回 name
     */
    public String getName() {
        return name;
    }
    
    /**
     * @return 返回 dialect
     */
    public Dialect getDialect() {
        return dialect;
    }

    /**
     * @return 返回 dialect4DDL
     */
    public Dialect4DDL getDialect4DDL() {
        return dialect4DDL;
    }
}
