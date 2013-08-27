/*
 * 描          述:  <描述>
 * 修  改   人:  PengQingyang
 * 修改时间:  2013-8-19
 * <修改描述:>
 */
package com.tx.core.dbscript.model;

import org.hibernate.dialect.Dialect;
import org.hibernate.dialect.H2Dialect;
import org.hibernate.dialect.MySQL5InnoDBDialect;
import org.hibernate.dialect.Oracle10gDialect;

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
    ORACLE("ORACLE", new Oracle10gDialect()),
    /**
     * oracle
     */
    ORACLE9I("ORACLE9I", new Oracle10gDialect()),
    /**
     * oracle
     */
    ORACLE10G("ORACLE10G", new Oracle10gDialect()),
    /**
     * H2
     */
    H2("H2", new H2Dialect()),
    /**
     * MYSQL
     */
    MYSQL("MYSQL", new MySQL5InnoDBDialect()),
    /**
     * MYSQL
     */
    MySQL5InnoDBDialect("MYSQL5INNODB", new MySQL5InnoDBDialect());
    
    /**
     * 数据源类型名
     */
    private String name;
    
    /**
     * 对应oracle方言
     */
    private Dialect hibernateDialect;
    
    /**
     * <默认构造函数>
     */
    private DataSourceTypeEnum(String name, Dialect hibernateDialect) {
        this.name = name;
        this.hibernateDialect = hibernateDialect;
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
     * @return 返回 hibernateDialect
     */
    public Dialect getHibernateDialect() {
        return hibernateDialect;
    }
    
    /**
     * @param 对hibernateDialect进行赋值
     */
    public void setHibernateDialect(Dialect hibernateDialect) {
        this.hibernateDialect = hibernateDialect;
    }
}
