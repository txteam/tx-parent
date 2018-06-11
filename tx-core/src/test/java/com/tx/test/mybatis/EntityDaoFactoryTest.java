/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2018年6月7日
 * <修改描述:>
 */
package com.tx.test.mybatis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.tx.core.mybatis.support.EntityDao;
import com.tx.core.mybatis.support.EntityDaoFactory;
import com.tx.core.mybatis.support.MyBatisDaoSupport;
import com.tx.core.mybatis.support.MyBatisDaoSupportHelper;
import com.tx.core.util.UUIDUtils;
import com.tx.core.util.dialect.DataSourceTypeEnum;
import com.tx.test.mybatis.model.Demo;
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
public class EntityDaoFactoryTest {
    
    public static void main(String[] args) throws Exception {
        DataSourceFinder finder = new SimpleDataSourceFinder(
                "com.mysql.jdbc.Driver",
                "jdbc:mysql://120.24.75.25:3306/test_pqy", "pqy", "pqy");
        DataSource ds = finder.getDataSource();
        
        JdbcTemplate jt = new JdbcTemplate(ds);
        MyBatisDaoSupport myBatisDaoSupport = MyBatisDaoSupportHelper
                .buildMyBatisDaoSupport("classpath:context/mybatis-config.xml",
                        null,
                        DataSourceTypeEnum.MySQL5InnoDBDialect,
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
        
        EntityDaoFactory<TestDemo> entityDaoFactory = new EntityDaoFactory<>(
                TestDemo.class, myBatisDaoSupport);
        entityDaoFactory.afterPropertiesSet();
        EntityDao<TestDemo> dao = entityDaoFactory.getObject();
        
        String id1 = UUIDUtils.generateUUID();
        TestDemo td1 = new TestDemo();
        td1.setId(id1);
        td1.setCode("testCode");
        td1.setName("testName");
        td1.setRemark("testRemark");
        dao.insert(td1);
        
        String id2 = UUIDUtils.generateUUID();
        TestDemo td2 = new TestDemo();
        td2.setId(id2);
        td2.setCode("testCode");
        td2.setName("testName");
        td2.setRemark("testRemark");
        dao.insert(td2);
        
        String id3 = UUIDUtils.generateUUID();
        TestDemo td3 = new TestDemo();
        td3.setId(id3);
        td3.setCode("testCode");
        td3.setName("testName");
        td3.setSuperDemo(new Demo());
        td3.getSuperDemo().setId("3.superDemo.id");
        td3.setDemo(new Demo());
        td3.getDemo().setCode("3.demo.code");
        dao.insert(td3);
        
        Demo superDemo = new Demo();
        superDemo.setId("superDemoId1");
        superDemo.setCode("superDemoCode1");
        //up.setSuperDemo(superDemo);
        //up.setDemo(superDemo);
        Map<String, Object> updateRowMap1 = new HashMap<>();
        updateRowMap1.put("id", id1);
        updateRowMap1.put("superDemo", superDemo);
        updateRowMap1.put("demo", null);
        dao.update(updateRowMap1);
        
        superDemo.setId("superDemoId2");
        superDemo.setCode("superDemoCode2");
        //up.setSuperDemo(superDemo);
        //up.setDemo(superDemo);
        Map<String, Object> updateRowMap2 = new HashMap<>();
        updateRowMap2.put("id", id2);
        updateRowMap2.put("superDemo", superDemo);
        updateRowMap2.put("demo", superDemo);
        dao.update(updateRowMap2);
        
        List<TestDemo> tdList = dao.queryList(null);
        for (TestDemo tdTemp : tdList) {
            System.out.println(tdTemp.getId() + " | " + tdTemp.getCode() + " | "
                    + tdTemp.getName() + " | " + tdTemp.getRemark() + " | "
                    + (tdTemp.getDemo() == null ? "null"
                            : tdTemp.getDemo().getId())
                    + " | "
                    + (tdTemp.getSuperDemo() == null ? null
                            : tdTemp.getSuperDemo().getId())
                    + " | " + (tdTemp.getDemo() == null ? null
                            : tdTemp.getDemo().getCode()));
        }
        
        dao.delete(td3);
    }
}
