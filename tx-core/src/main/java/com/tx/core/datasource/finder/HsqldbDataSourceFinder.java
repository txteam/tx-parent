/*
 * 描          述:  <描述>
 * 修  改   人:  PengQingyang
 * 修改时间:  2012-12-21
 * <修改描述:>
 */
package com.tx.core.datasource.finder;

import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;


 /**
  * <功能简述>
  * <功能详细描述>
  * 
  * @author  PengQingyang
  * @version  [版本号, 2012-12-21]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
//TODO:未实现
public class HsqldbDataSourceFinder {
    
    /** <默认构造函数> */
    public HsqldbDataSourceFinder() {
        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
        EmbeddedDatabase db = builder.setType(EmbeddedDatabaseType.H2)
            .addScript("my-schema.sql")
            .addScript("my-test-data.sql").build();
        db.shutdown();
    }
    
}
