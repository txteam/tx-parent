/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2018年6月7日
 * <修改描述:>
 */
package com.tx.test.mybatis;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import com.tx.core.datasource.DataSourceFinder;
import com.tx.core.datasource.finder.SimpleDataSourceFinder;
import com.tx.core.ddlutil.builder.alter.AlterTableDDLBuilder;
import com.tx.core.ddlutil.builder.create.CreateTableDDLBuilder;
import com.tx.core.ddlutil.dialect.DDLDialect;
import com.tx.core.ddlutil.dialect.MysqlDDLDialect;
import com.tx.core.ddlutil.executor.TableDDLExecutor;
import com.tx.core.ddlutil.executor.impl.MysqlTableDDLExecutor;
import com.tx.core.ddlutil.helper.JPAEntityDDLHelper;
import com.tx.core.ddlutil.model.TableDef;
import com.tx.core.mybatis.support.EntityMapperBuilderAssistant;
import com.tx.core.mybatis.support.MyBatisDaoSupport;
import com.tx.core.mybatis.support.MyBatisDaoSupportHelper;
import com.tx.core.util.dialect.DataSourceTypeEnum;
import com.tx.test.mybatis.model.TestDemo;

/**
 * <功能简述>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2018年6月7日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class EntityMapperBuilderTest {
    
    public static void main(String[] args) throws Exception {
        DataSourceFinder finder = new SimpleDataSourceFinder(
                "com.mysql.jdbc.Driver",
                "jdbc:mysql://120.24.75.25:3306/test_pqy", "pqy", "pqy");
        DataSource ds = finder.getDataSource();
        
        JdbcTemplate jt = new JdbcTemplate(ds);
        MyBatisDaoSupport myBatisDaoSupport = MyBatisDaoSupportHelper
                .buildMyBatisDaoSupport("classpath:context/mybatis-config.xml",
                        null,
                        DataSourceTypeEnum.MYSQL,
                        ds);
        
        TableDDLExecutor ddlExecutor = new MysqlTableDDLExecutor(jt);
        
        DDLDialect ddlDialect = MysqlDDLDialect.INSTANCE;
        TableDef testDemoTableDef = JPAEntityDDLHelper
                .analyzeToTableDefDetail(TestDemo.class, ddlDialect);
        String tableName = testDemoTableDef.getTableName();
        if (ddlExecutor.exists(tableName)) {
            AlterTableDDLBuilder alterBuilder = ddlExecutor
                    .generateAlterTableDDLBuilder(testDemoTableDef);
            if (alterBuilder.compare().isNeedAlter()) {
                ddlExecutor.alter(alterBuilder);
            }
        } else {
            CreateTableDDLBuilder createBuilder = ddlExecutor
                    .generateCreateTableDDLBuilder(testDemoTableDef);
            ddlExecutor.create(createBuilder);
        }
        
        EntityMapperBuilderAssistant assistant = new EntityMapperBuilderAssistant(
                myBatisDaoSupport.getSqlSessionFactory().getConfiguration(),
                TestDemo.class);
        assistant.registe();
    }
}
