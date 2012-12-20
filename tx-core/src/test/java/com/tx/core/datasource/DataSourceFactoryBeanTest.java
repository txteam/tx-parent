package com.tx.core.datasource;

import javax.sql.DataSource;

import org.junit.Test;


import junit.framework.TestCase;

/**
 * <功能简述>
 * <功能详细描述>
 * 
 * @author  PengQingyang
 * @version  [版本号, 2012-10-7]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class DataSourceFactoryBeanTest extends TestCase {
    @Test
    public void testInitPostgresDataSource() {
        DataSourceFactoryBean dbfb = new DataSourceFactoryBean();
        
        dbfb.setJndiName("test_postgresql_db");
        DataSource ds = null;
        try {
            dbfb.afterPropertiesSet();
            ds = dbfb.getObject();
        }
        catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        assertNotNull(ds);
    }
}
