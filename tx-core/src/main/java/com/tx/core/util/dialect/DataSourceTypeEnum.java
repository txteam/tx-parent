/*
 * 描          述:  <描述>
 * 修  改   人:  PengQingyang
 * 修改时间:  2013-8-19
 * <修改描述:>
 */
package com.tx.core.util.dialect;

import org.hibernate.dialect.Dialect;

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
    //    /**
    //     * sqlServer
    //     */
    //    SQLSERVER2008("sqlserver2008", DialectUtils.sqlServer2008Dialect),
    //    /**
    //     * MYSQL
    //     */
    //    MySQL5InnoDBDialect("MYSQL5INNODB", DialectUtils.mySQL5InnoDBDialect),
    //    /**
    //     * oracle
    //     */
    //    ORACLE9I("ORACLE9I", DialectUtils.oracle9iDialect),
    //    /**
    //     * oracle
    //     */
    //    ORACLE10G("ORACLE10G", DialectUtils.oracle10gDialect),
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
    MYSQL("MYSQL", DialectUtils.mySQL5Dialect);
    
    /**
     * 数据源类型名
     */
    private String name;
    
    /**
     * 对应oracle方言
     */
    private Dialect dialect;
    
    /**
     * <默认构造函数>
     */
    private DataSourceTypeEnum(String name, Dialect dialect) {
        this.name = name;
        this.dialect = dialect;
    }
    
    /**
     * @return 返回 name
     */
    public String getName() {
        return name;
    }
    
    /**
     * @param 对name进行赋值
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * @return 返回 dialect
     */
    public Dialect getDialect() {
        return dialect;
    }
    
    /**
     * @param 对dialect进行赋值
     */
    public void setDialect(Dialect dialect) {
        this.dialect = dialect;
    }
}
