/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2014年3月22日
 * <修改描述:>
 */
package com.tx.core.util.dialect;

import org.hibernate.dialect.H2Dialect;
import org.hibernate.dialect.MySQL5Dialect;
import org.hibernate.dialect.MySQL5InnoDBDialect;
import org.hibernate.dialect.Oracle10gDialect;
import org.hibernate.dialect.Oracle9iDialect;
import org.hibernate.dialect.SQLServer2008Dialect;


 /**
  * <功能简述>
  * <功能详细描述>
  * 
  * @author  Administrator
  * @version  [版本号, 2014年3月22日]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public abstract class DialectUtils {
    
    public static final SQLServer2008Dialect sqlServer2008Dialect = new SQLServer2008Dialect();

    public static final Oracle10gDialect oracle10gDialect = new Oracle10gDialect();
    
    public static final Oracle9iDialect oracle9iDialect = new Oracle9iDialect();
    
    public static final H2Dialect h2Dialect = new H2Dialect();
    
    public static final MySQL5Dialect  mySQL5Dialect = new MySQL5Dialect();
    
    public static final MySQL5InnoDBDialect mySQL5InnoDBDialect = new MySQL5InnoDBDialect();
}
