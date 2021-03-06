/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2016年11月18日
 * <修改描述:>
 */
package com.tx.test.mybatis;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.mapping.ResultMapping;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.tx.core.ddlutil.builder.create.CreateTableDDLBuilder;
import com.tx.core.ddlutil.executor.TableDDLExecutor;
import com.tx.core.ddlutil.executor.impl.MysqlTableDDLExecutor;
import com.tx.core.mybatis.builder.MapperBuilderAssistantExtention;
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
 * @version  [版本号, 2016年11月18日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class MybatisDaoSupportTest {
    
    public static void main(String[] args) throws Exception {
        //120.24.75.25
        DriverManagerDataSource ds = new DriverManagerDataSource(
                "jdbc:mysql://127.0.0.1:3306/webdemo?characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull&useSSL=false",
                "root", "root");
        ds.setDriverClassName("com.mysql.jdbc.Driver");
        JdbcTemplate jt = new JdbcTemplate(ds);
        MyBatisDaoSupport myBatisDaoSupport = MyBatisDaoSupportHelper
                .buildMyBatisDaoSupport("classpath:context/mybatis-config.xml",
                        null,
                        DataSourceTypeEnum.MYSQL,
                        ds);
        PlatformTransactionManager tm = new DataSourceTransactionManager(ds);
        TransactionTemplate transactionTemplate = new TransactionTemplate(tm);
        TableDDLExecutor ddlExecutor = new MysqlTableDDLExecutor(jt);
        
        testBatchInsert1(myBatisDaoSupport, ddlExecutor, transactionTemplate);
        testBatchInsert2(myBatisDaoSupport, ddlExecutor, transactionTemplate);
        testBatchInsert3(myBatisDaoSupport, ddlExecutor, transactionTemplate);
        
        //        String tableName = "test_001";
        //        if (ddlExecutor.exists(tableName)) {
        //            ddlExecutor.drop(tableName);
        //        }
        //        CreateTableDDLBuilder createBuilder = ddlExecutor
        //                .generateCreateTableDDLBuilder(tableName);
        //        createBuilder.newColumnOfVarchar(true, "id", 64, true, null);
        //        createBuilder.newColumnOfVarchar("code", 64, true, null);
        //        createBuilder.newColumnOfVarchar("name", 64, false, null);
        //        createBuilder.newColumnOfVarchar("demoId", 64, false, null);
        //        createBuilder.newColumnOfVarchar("remark", 512, false, null);
        //        createBuilder.newColumnOfDate("createDate", true, true);
        //        createBuilder.newColumnOfDate("lastUpdateDate", true, true);
        //        ddlExecutor.create(createBuilder);
        //
        //        String namespace = "test_namespace";
        //        MapperBuilderAssistantExtention ass = new MapperBuilderAssistantExtention(
        //                myBatisDaoSupport.getSqlSessionTemplate().getConfiguration(),
        //                namespace);
        //        ass.setCurrentNamespace(namespace);
        //        String insertStatementId = "insert";
        //        ass.saveMappedStatement(insertStatementId,
        //                SqlCommandType.INSERT,
        //                "insert into test_001(id,code)values(#{id},#{code}) ",
        //                TestDemo.class);
        //        TestDemo td = new TestDemo();
        //        td.setId(UUIDUtils.generateUUID());
        //        td.setCode("testCode");
        //        td.setName("testName");
        //        td.setRemark("testRemark");
        //       
        //        myBatisDaoSupport.insert(namespace + "." + insertStatementId, td);
        //        //.addMappedStatement(ms);
        //        ass.saveMappedStatement(insertStatementId,
        //                SqlCommandType.INSERT,
        //                "insert into test_001(id,code,name,remark)values(#{id},#{code},#{name},#{remark}) ",
        //                TestDemo.class);
        //        td.setId(UUIDUtils.generateUUID());
        //        myBatisDaoSupport.insert(namespace + "." + insertStatementId, td);
        //        
        //        ass.saveMappedStatement(insertStatementId,
        //                SqlCommandType.INSERT,
        //                "insert into test_001(id,code,name,remark,demoId)values(#{id},#{code},#{name},#{remark},#{demo.id}) ",
        //                TestDemo.class);
        //        td.setId(UUIDUtils.generateUUID());
        //        td.setDemo(null);
        //        myBatisDaoSupport.insert(namespace + "." + insertStatementId, td);
        //        
        //        ass.saveMappedStatement(insertStatementId,
        //                SqlCommandType.INSERT,
        //                "insert into test_001(id,code,name,remark,demoId)values(#{id},#{code},#{name},#{remark},#{demo.id}) ",
        //                TestDemo.class);
        //        td.setId(UUIDUtils.generateUUID());
        //        td.setDemo(new Demo());
        //        td.getDemo().setId(UUIDUtils.generateUUID());
        //        myBatisDaoSupport.insert(namespace + "." + insertStatementId, td);
        //        
        //        ass.saveMappedStatement(insertStatementId,
        //                SqlCommandType.INSERT,
        //                "insert into test_001(id,code,name,remark,demoId)values(#{id},#{code},#{name},#{remark},#{demo.id}) ",
        //                TestDemo.class);
        //        
        //        String queryStatementId = "query";
        //        String resultMapId = "testDemoMap";
        //        
        //        List<ResultMapping> resultMappings = new ArrayList<>();
        //        ass.saveResultMap(resultMapId, TestDemo.class, resultMappings);
        //        ass.saveMappedStatement(queryStatementId,
        //                SqlCommandType.SELECT,
        //                "select id,code from test_001",
        //                TestDemo.class,
        //                resultMapId);
        //        List<TestDemo> tdList = myBatisDaoSupport
        //                .queryList(namespace + "." + queryStatementId, null);
        //        
        //        for (TestDemo tdTemp : tdList) {
        //            System.out.println(tdTemp.getId() + " | " + tdTemp.getCode() + " | "
        //                    + tdTemp.getName() + " | " + tdTemp.getRemark() + " | "
        //                    + (tdTemp.getDemo() == null ? "null"
        //                            : tdTemp.getDemo().getId()));
        //        }
        //        
        //        resultMappings.clear();
        //        resultMappings.add(ass.buildResultMapping(null,
        //                "demo.id",
        //                null, //TestDemo.class
        //                "demoId",
        //                null));
        //        ass.saveResultMap(resultMapId, TestDemo.class, resultMappings);
        //        ass.saveMappedStatement(queryStatementId,
        //                SqlCommandType.SELECT,
        //                "select id,code,name,remark,demoId from test_001",
        //                TestDemo.class,
        //                resultMapId);
        //        tdList = myBatisDaoSupport.queryList(namespace + "." + queryStatementId,
        //                null);
        //        System.out.println();
        //        for (TestDemo tdTemp : tdList) {
        //            System.out.println(tdTemp.getId() + " | " + tdTemp.getCode() + " | "
        //                    + tdTemp.getName() + " | " + tdTemp.getRemark() + " | "
        //                    + (tdTemp.getDemo() == null ? "null"
        //                            : tdTemp.getDemo().getId()));
        //        }
    }
    
    public static void testBatchInsert1(MyBatisDaoSupport myBatisDaoSupport,
            TableDDLExecutor ddlExecutor,
            TransactionTemplate transactionTemplate) throws Exception {
        String tableName = "test1";
        
        if (ddlExecutor.exists(tableName)) {
            ddlExecutor.drop(tableName);
        }
        CreateTableDDLBuilder createBuilder = ddlExecutor
                .generateCreateTableDDLBuilder(tableName);
        createBuilder.newColumnOfVarchar(true, "id", 64, true, null);
        createBuilder.newColumnOfVarchar("code", 64, true, null);
        createBuilder.newColumnOfVarchar("name", 64, false, null);
        createBuilder.newColumnOfVarchar("demoId", 64, false, null);
        createBuilder.newColumnOfVarchar("remark", 512, false, null);
        createBuilder.newColumnOfDate("createDate", true, true);
        createBuilder.newColumnOfDate("lastUpdateDate", true, true);
        ddlExecutor.create(createBuilder);
        
        String namespace = "test1";
        MapperBuilderAssistantExtention ass = new MapperBuilderAssistantExtention(
                myBatisDaoSupport.getSqlSessionTemplate().getConfiguration(),
                namespace);
        ass.setCurrentNamespace(namespace);
        String insertStatementId = "insert";
        ass.saveMappedStatement(insertStatementId,
                SqlCommandType.INSERT,
                "insert into test1(id,code,name,remark,demoId)values(#{id},#{code},#{name},#{remark},#{demo.id}) ",
                TestDemo.class);
        
        List<TestDemo> tdList = new ArrayList<>();
        for (int i = 0; i < 50000; i++) {
            TestDemo tdTemp = new TestDemo();
            String random = UUIDUtils.generateUUID();
            tdTemp.setId(random);
            tdTemp.setCode("code_" + random);
            tdTemp.setName("name_" + random);
            tdTemp.setRemark("remark_" + random);
            
            tdList.add(tdTemp);
        }
        
        System.out.println("批量插入开始");
        Date startDate = new Date();
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            
            @Override
            protected void doInTransactionWithoutResult(
                    TransactionStatus status) {
                myBatisDaoSupport.batchInsert(
                        namespace + "." + insertStatementId, tdList, true);
            }
        });
        Date endDate = new Date();
        
        System.out
                .println("批量插入耗时：" + (endDate.getTime() - startDate.getTime()));
        //批量插入耗时：26467
        //批量插入耗时：15736
    }
    
    public static void testBatchInsert2(MyBatisDaoSupport myBatisDaoSupport,
            TableDDLExecutor ddlExecutor,
            TransactionTemplate transactionTemplate) throws Exception {
        String tableName = "test2";
        
        if (ddlExecutor.exists(tableName)) {
            ddlExecutor.drop(tableName);
        }
        CreateTableDDLBuilder createBuilder = ddlExecutor
                .generateCreateTableDDLBuilder(tableName);
        createBuilder.newColumnOfVarchar(true, "id", 64, true, null);
        createBuilder.newColumnOfVarchar("code", 64, true, null);
        createBuilder.newColumnOfVarchar("name", 64, false, null);
        createBuilder.newColumnOfVarchar("demoId", 64, false, null);
        createBuilder.newColumnOfVarchar("remark", 512, false, null);
        createBuilder.newColumnOfDate("createDate", true, true);
        createBuilder.newColumnOfDate("lastUpdateDate", true, true);
        ddlExecutor.create(createBuilder);
        
        String namespace = "test2";
        MapperBuilderAssistantExtention ass = new MapperBuilderAssistantExtention(
                myBatisDaoSupport.getSqlSessionTemplate().getConfiguration(),
                namespace);
        ass.setCurrentNamespace(namespace);
        String insertStatementId = "insert";
        ass.saveMappedStatement(insertStatementId,
                SqlCommandType.INSERT,
                "insert into test2(id,code,name,remark,demoId)values(#{id},#{code},#{name},#{remark},#{demo.id}) ",
                TestDemo.class);
        
        List<TestDemo> tdList = new ArrayList<>();
        for (int i = 0; i < 50000; i++) {
            TestDemo tdTemp = new TestDemo();
            String random = UUIDUtils.generateUUID();
            tdTemp.setId(random);
            tdTemp.setCode("code_" + random);
            tdTemp.setName("name_" + random);
            tdTemp.setRemark("remark_" + random);
            
            tdList.add(tdTemp);
        }
        
        System.out.println("批量插入开始");
        Date startDate = new Date();
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            
            @Override
            protected void doInTransactionWithoutResult(
                    TransactionStatus status) {
                for (TestDemo td : tdList) {
                    myBatisDaoSupport
                            .insert(namespace + "." + insertStatementId, td);
                }
            }
        });
        Date endDate = new Date();
        
        System.out
                .println("批量插入耗时：" + (endDate.getTime() - startDate.getTime()));
        //批量插入耗时：23464
    }
    
    public static void testBatchInsert3(MyBatisDaoSupport myBatisDaoSupport,
            TableDDLExecutor ddlExecutor,
            TransactionTemplate transactionTemplate) throws Exception {
        String tableName = "test3";
        
        if (ddlExecutor.exists(tableName)) {
            ddlExecutor.drop(tableName);
        }
        CreateTableDDLBuilder createBuilder = ddlExecutor
                .generateCreateTableDDLBuilder(tableName);
        createBuilder.newColumnOfVarchar(true, "id", 64, true, null);
        createBuilder.newColumnOfVarchar("code", 64, true, null);
        createBuilder.newColumnOfVarchar("name", 64, false, null);
        createBuilder.newColumnOfVarchar("demoId", 64, false, null);
        createBuilder.newColumnOfVarchar("remark", 512, false, null);
        createBuilder.newColumnOfDate("createDate", true, true);
        createBuilder.newColumnOfDate("lastUpdateDate", true, true);
        ddlExecutor.create(createBuilder);
        
        String namespace = "test3";
        MapperBuilderAssistantExtention ass = new MapperBuilderAssistantExtention(
                myBatisDaoSupport.getSqlSessionTemplate().getConfiguration(),
                namespace);
        ass.setCurrentNamespace(namespace);
        String insertStatementId = "insert";
        ass.saveMappedStatement(insertStatementId,
                SqlCommandType.INSERT,
                "insert into test3(id,code,name,remark,demoId)values(#{id},#{code},#{name},#{remark},#{demo.id}) ",
                TestDemo.class);
        
        List<TestDemo> tdList = new ArrayList<>();
        for (int i = 0; i < 50000; i++) {
            TestDemo tdTemp = new TestDemo();
            String random = UUIDUtils.generateUUID();
            tdTemp.setId(random);
            tdTemp.setCode("code_" + random);
            tdTemp.setName("name_" + random);
            tdTemp.setRemark("remark_" + random);
            
            tdList.add(tdTemp);
        }
        
        System.out.println("批量插入开始");
        Date startDate = new Date();
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            
            @Override
            protected void doInTransactionWithoutResult(
                    TransactionStatus status) {
                SqlSessionFactory sqlSessionFactory = myBatisDaoSupport
                        .getSqlSessionFactory();
                SqlSession sqlSession = sqlSessionFactory
                        .openSession(ExecutorType.BATCH);
                
                try {
                    for (TestDemo td : tdList) {
                        sqlSession.insert(namespace + "." + insertStatementId,
                                td);
                    }
                    sqlSession.commit();
                } finally {
                    sqlSession.close();
                }
            }
        });
        Date endDate = new Date();
        
        System.out
                .println("批量插入耗时：" + (endDate.getTime() - startDate.getTime()));
        //批量插入耗时：45067
    }
    
    //    ------删除表:test1----------------------------start    
    //    DROP TABLE test1    
    //    ------删除表:test1------------------------------end    
    //
    //    [2019-06-04 11:54:14,029] [INFO ] com.tx.core.ddlutil.executor.impl.MysqlTableDDLExecutor.drop(MysqlTableDDLExecutor.java:321) -删除数据库表:test1成功.
    //    [2019-06-04 11:54:14,047] [INFO ] com.tx.core.ddlutil.executor.impl.MysqlTableDDLExecutor.create(MysqlTableDDLExecutor.java:410) -创建数据库表:test1  
    //    ------创建表:test1----------------------------start    
    //    -- ----------------------------------------------------------------------- 
    //    -- test1 
    //    -- ----------------------------------------------------------------------- 
    //    CREATE TABLE test1(
    //       id varchar(64) not null,
    //       code varchar(64) not null,
    //       name varchar(64),
    //       demoId varchar(64),
    //       remark varchar(512),
    //       createDate datetime(6) default now(6) not null,
    //       lastUpdateDate datetime(6) default now(6) not null,
    //       PRIMARY KEY (id)
    //    );
    //        
    //    ------创建表:test1------------------------------end    
    //
    //    [2019-06-04 11:54:14,771] [INFO ] com.tx.core.ddlutil.executor.impl.MysqlTableDDLExecutor.create(MysqlTableDDLExecutor.java:420) -创建数据库表: test1 成功.
    //    批量插入开始
    //    批量插入耗时：15736
    //    [2019-06-04 11:54:30,641] [INFO ] com.tx.core.ddlutil.executor.impl.MysqlTableDDLExecutor.drop(MysqlTableDDLExecutor.java:317) -删除数据库表:test2    
    //    ------删除表:test2----------------------------start    
    //    DROP TABLE test2    
    //    ------删除表:test2------------------------------end    
    //
    //    [2019-06-04 11:54:31,025] [INFO ] com.tx.core.ddlutil.executor.impl.MysqlTableDDLExecutor.drop(MysqlTableDDLExecutor.java:321) -删除数据库表:test2成功.
    //    [2019-06-04 11:54:31,031] [INFO ] com.tx.core.ddlutil.executor.impl.MysqlTableDDLExecutor.create(MysqlTableDDLExecutor.java:410) -创建数据库表:test2  
    //    ------创建表:test2----------------------------start    
    //    -- ----------------------------------------------------------------------- 
    //    -- test2 
    //    -- ----------------------------------------------------------------------- 
    //    CREATE TABLE test2(
    //       id varchar(64) not null,
    //       code varchar(64) not null,
    //       name varchar(64),
    //       demoId varchar(64),
    //       remark varchar(512),
    //       createDate datetime(6) default now(6) not null,
    //       lastUpdateDate datetime(6) default now(6) not null,
    //       PRIMARY KEY (id)
    //    );
    //        
    //    ------创建表:test2------------------------------end    
    //
    //    [2019-06-04 11:54:31,237] [INFO ] com.tx.core.ddlutil.executor.impl.MysqlTableDDLExecutor.create(MysqlTableDDLExecutor.java:420) -创建数据库表: test2 成功.
    //    批量插入开始
    //    批量插入耗时：23976
    //    [2019-06-04 11:54:55,300] [INFO ] com.tx.core.ddlutil.executor.impl.MysqlTableDDLExecutor.create(MysqlTableDDLExecutor.java:410) -创建数据库表:test3  
    //    ------创建表:test3----------------------------start    
    //    -- ----------------------------------------------------------------------- 
    //    -- test3 
    //    -- ----------------------------------------------------------------------- 
    //    CREATE TABLE test3(
    //       id varchar(64) not null,
    //       code varchar(64) not null,
    //       name varchar(64),
    //       demoId varchar(64),
    //       remark varchar(512),
    //       createDate datetime(6) default now(6) not null,
    //       lastUpdateDate datetime(6) default now(6) not null,
    //       PRIMARY KEY (id)
    //    );
    //        
    //    ------创建表:test3------------------------------end    
    //
    //    [2019-06-04 11:54:55,676] [INFO ] com.tx.core.ddlutil.executor.impl.MysqlTableDDLExecutor.create(MysqlTableDDLExecutor.java:420) -创建数据库表: test3 成功.
    //    批量插入开始
    //    批量插入耗时：13551
}
