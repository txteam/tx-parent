/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2016年10月21日
 * <修改描述:>
 */
package com.tx.core.ddlutil.dialect;

import org.hibernate.dialect.MySQL5InnoDBDialect;

/**
 * MysqlDDL方言<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2016年10月21日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class MysqlDDLDialect extends DDLDialect {
    
    /** <默认构造函数> */
    public MysqlDDLDialect() {
        super();
        setDialect(new MySQL5InnoDBDialect());
    }
}
