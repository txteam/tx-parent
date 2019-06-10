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

import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
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
public class BatchMybatisDaoSupportTest {
    
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
        
        //testBatchInsert1(myBatisDaoSupport, ddlExecutor, transactionTemplate);
        //testBatchInsert2(myBatisDaoSupport, ddlExecutor, transactionTemplate);
        //testBatchInsert3(myBatisDaoSupport, ddlExecutor, transactionTemplate);
        
        testBatchInsertException(myBatisDaoSupport, ddlExecutor, transactionTemplate);
    }
    
    public static void testBatchInsertException(MyBatisDaoSupport myBatisDaoSupport,
            TableDDLExecutor ddlExecutor,
            TransactionTemplate transactionTemplate) throws Exception {
        String tableName = "test5";
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
        createBuilder.newIndex(true, "idx_test5_00", "code");
        ddlExecutor.create(createBuilder);
        
        String namespace = "test5";
        MapperBuilderAssistantExtention ass = new MapperBuilderAssistantExtention(
                myBatisDaoSupport.getSqlSessionTemplate().getConfiguration(),
                namespace);
        ass.setCurrentNamespace(namespace);
        String insertStatementId = "insert";
        ass.saveMappedStatement(insertStatementId,
                SqlCommandType.INSERT,
                "insert into test5(id,code,name,remark,demoId)values(#{id},#{code},#{name},#{remark},#{demo.id}) ",
                TestDemo.class);
        
        List<TestDemo> tdList = new ArrayList<>();
        {
            TestDemo tdTemp = new TestDemo();
            String random = UUIDUtils.generateUUID();
            tdTemp.setId(random);
            tdTemp.setCode("code_001");
            tdTemp.setName("name_1");
            tdTemp.setRemark("remark_1");
            tdList.add(tdTemp);
        }
        for (int i = 0; i < 750; i++) {
            TestDemo tdTemp = new TestDemo();
            String random = UUIDUtils.generateUUID();
            tdTemp.setId(random);
            tdTemp.setCode("code_" + random);
            tdTemp.setName("name_" + random);
            tdTemp.setRemark("remark_" + random);
            
            tdList.add(tdTemp);
        }
        {
            TestDemo tdTemp = new TestDemo();
            String random = UUIDUtils.generateUUID();
            tdTemp.setId(random);
            tdTemp.setCode("code_001");
            tdTemp.setName("name_2");
            tdTemp.setRemark("remark_2");
            tdList.add(tdTemp);
        }
        {
            TestDemo tdTemp = new TestDemo();
            String random = UUIDUtils.generateUUID();
            tdTemp.setId(random);
            tdTemp.setCode("code_001");
            tdTemp.setName("name_3");
            tdTemp.setRemark("remark_3");
            tdList.add(tdTemp);
        }
        {
            TestDemo tdTemp = new TestDemo();
            String random = UUIDUtils.generateUUID();
            tdTemp.setId(random);
            tdTemp.setCode("code_001");
            tdTemp.setName("name_001");
            tdTemp.setRemark("remark_");
            tdList.add(tdTemp);
        }
        {
            TestDemo tdTemp = new TestDemo();
            String random = UUIDUtils.generateUUID();
            tdTemp.setId(random);
            tdTemp.setCode(null);
            tdTemp.setName("name_001");
            tdTemp.setRemark("remark_");
            tdList.add(tdTemp);
        }
        for (int i = 0; i < 750; i++) {
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
        try {
            transactionTemplate.execute(new TransactionCallbackWithoutResult() {
                
                @Override
                protected void doInTransactionWithoutResult(
                        TransactionStatus status) {
                    myBatisDaoSupport.batchInsert(
                            namespace + "." + insertStatementId, tdList, false);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        Date endDate = new Date();
        
        System.out
                .println("批量插入耗时：" + (endDate.getTime() - startDate.getTime()));
        //批量插入耗时：13106
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
        //批量插入耗时：13106
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
        //批量插入耗时：23037
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
        //批量插入耗时：14256
    }
}
