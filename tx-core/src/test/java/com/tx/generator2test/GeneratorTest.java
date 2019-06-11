/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2019年6月6日
 * <修改描述:>
 */
package com.tx.generator2test;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.tx.core.mybatis.support.MyBatisDaoSupport;
import com.tx.core.mybatis.support.MyBatisDaoSupportHelper;
import com.tx.core.querier.model.Filter;
import com.tx.core.querier.model.OperatorEnum;
import com.tx.core.querier.model.OrderDirectionEnum;
import com.tx.core.querier.model.Querier;
import com.tx.core.querier.model.QuerierBuilder;
import com.tx.core.util.UUIDUtils;
import com.tx.core.util.dialect.DataSourceTypeEnum;

/**
 * <功能简述>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2019年6月6日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class GeneratorTest {
    
    @SuppressWarnings("deprecation")
    public static void main(String[] args) throws Exception {
        //jdbc:mysql://47.94.136.230:3306/webdemo?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC&zeroDateTimeBehavior=convertToNull&useSSL=false
        //jdbc:p6spy:mysql://47.94.136.230:3306/webdemo?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC&zeroDateTimeBehavior=convertToNull&useSSL=false
        DriverManagerDataSource ds = new DriverManagerDataSource(
                "jdbc:p6spy:mysql://120.24.75.25:3306/webdemo?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC&zeroDateTimeBehavior=convertToNull&useSSL=false",
                "pqy", "pqy");
        //com.mysql.cj.jdbc.Driver
        //com.mysql.jdbc.Driver
        ds.setDriverClassName("com.mysql.jdbc.Driver");
        JdbcTemplate jt = new JdbcTemplate(ds);
        MyBatisDaoSupport myBatisDaoSupport = MyBatisDaoSupportHelper
                .buildMyBatisDaoSupport("classpath:context/mybatis-config.xml",
                        null, //new String[] {"classpath*:com/tx/local/clientinfo/**/*SqlMap.xml" },
                        DataSourceTypeEnum.MYSQL,
                        ds);
        PlatformTransactionManager tm = new DataSourceTransactionManager(ds);
        TransactionTemplate transactionTemplate = new TransactionTemplate(tm);
        
        //        ClientSourceDaoImpl clientSourceDao = new ClientSourceDaoImpl();
        //        clientSourceDao.setMyBatisDaoSupport(myBatisDaoSupport);
        //        clientSourceDao.afterPropertiesSet();
        //        reCreateTable(jt);
        //        //测试插入
        //        testInsertClientSource(transactionTemplate, clientSourceDao,"0");
        //        testInsertClientSource(transactionTemplate, clientSourceDao,"1");
        //        testInsertClientSource(transactionTemplate, clientSourceDao,"2");
        //        testInsertClientSource(transactionTemplate, clientSourceDao,"3");
        //        testInsertClientSource(transactionTemplate, clientSourceDao,"4");
        //        testInsertClientSource(transactionTemplate, clientSourceDao,"5");
        //        System.out.println("插入数据成功");
        //        testBatchInsertClientSource(transactionTemplate, clientSourceDao);
        //        System.out.println("批量插入数据成功");
        //        
        //        testQueryListByParams(clientSourceDao);
        //        testQueryListByQuerier(clientSourceDao);
    }
    
    //    public static void reCreateTable(JdbcTemplate jt) {
    //        TableDDLExecutor ddlExecutor = new MysqlTableDDLExecutor(jt);
    //        String tableName = "cli_client_source";
    //        if (ddlExecutor.exists(tableName)) {
    //            ddlExecutor.drop(tableName);
    //        }
    //        CreateTableDDLBuilder createBuilder = ddlExecutor
    //                .generateCreateTableDDLBuilder(tableName);
    //        createBuilder.newColumnOfVarchar(true, "id", 64, true, null);
    //        createBuilder.newColumnOfVarchar("code", 64, true, null);
    //        createBuilder.newColumnOfVarchar("name", 64, false, null);
    //        createBuilder.newColumnOfVarchar("remark", 512, false, null);
    //        createBuilder.newColumnOfBoolean("modifyAble", true, true);
    //        createBuilder.newColumnOfBoolean("valid", true, true);
    //        createBuilder.newColumnOfDate("createDate", true, true);
    //        createBuilder.newColumnOfDate("lastUpdateDate", true, true);
    //        createBuilder.newIndex(true, "idx_client_source_00", "code");
    //        ddlExecutor.create(createBuilder);
    //    }
    //    
    //    public static void testInsertClientSource(
    //            TransactionTemplate transactionTemplate,
    //            ClientSourceDao clientSourceDao, String uuid) {
    //        ClientSource cs = new ClientSource();
    //        cs.setCode("code_" + uuid);
    //        cs.setName("name_" + uuid);
    //        cs.setCreateDate(new Date());
    //        cs.setLastUpdateDate(new Date());
    //        cs.setId(uuid);
    //        cs.setModifyAble(true);
    //        cs.setValid(true);
    //        cs.setRemark("remark_" + uuid);
    //        
    //        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
    //            @Override
    //            protected void doInTransactionWithoutResult(
    //                    TransactionStatus status) {
    //                clientSourceDao.insert(cs);
    //            }
    //        });
    //    }
    //    
    //    public static void testBatchInsertClientSource(
    //            TransactionTemplate transactionTemplate,
    //            ClientSourceDao clientSourceDao) {
    //        List<ClientSource> csList = new ArrayList<>();
    //        for (int i = 0; i < 600; i++) {
    //            ClientSource cs = new ClientSource();
    //            String uuid = UUIDUtils.generateUUID();
    //            cs.setCode("code_" + uuid);
    //            cs.setName("name_" + uuid);
    //            cs.setCreateDate(new Date());
    //            cs.setLastUpdateDate(new Date());
    //            cs.setId(uuid);
    //            cs.setModifyAble(true);
    //            cs.setValid(true);
    //            cs.setRemark("remark_" + uuid);
    //            
    //            csList.add(cs);
    //        }
    //        
    //        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
    //            @Override
    //            protected void doInTransactionWithoutResult(
    //                    TransactionStatus status) {
    //                clientSourceDao.batchInsert(csList);
    //            }
    //        });
    //    }
    //    
    //    public static void testQueryListByParams(ClientSourceDao clientSourceDao) {
    //        Map<String, Object> params = new HashMap<>();
    //        params.put("code", "code_0");
    //        List<ClientSource> csList = clientSourceDao.queryList(params);
    //        
    //        System.out.println(
    //                "testQueryListByParams，start-----------: code = code_0");
    //        csList.stream().forEach(cs -> {
    //            System.out.println(cs.getCode() + ":" + cs.getName());
    //        });
    //        
    //        Map<String, Object> params1 = new HashMap<>();
    //        params1.put("name", "name_0");
    //        List<ClientSource> csList1 = clientSourceDao.queryList(params1);
    //        System.out.println(
    //                "testQueryListByParams，start-----------: name = name_0");
    //        csList1.stream().forEach(cs -> {
    //            System.out.println(cs.getCode() + ":" + cs.getName());
    //        });
    //    }
    //    
    //    public static void testQueryListByQuerier(ClientSourceDao clientSourceDao) {
    //        System.out.println(
    //                "testQueryListByQuerier，start-----------: code = code_0");
    //        Querier q1 = QuerierBuilder.newInstance()
    //                .searchProperty("code", OperatorEnum.eq, "code_0")
    //                .querier();
    //        List<ClientSource> csList1 = clientSourceDao.queryList(q1);
    //        csList1.stream().forEach(cs -> {
    //            System.out.println(cs.getCode() + ":" + cs.getName());
    //        });
    //        
    //        System.out.println(
    //                "testQueryListByQuerier，start-----------: name like name_%");
    //        Querier q2 = QuerierBuilder.newInstance()
    //                .searchProperty("name", "name_%")
    //                .orderProperty("name")
    //                .querier();
    //        List<ClientSource> csList2 = clientSourceDao.queryList(q2);
    //        //csList2.stream().forEach(cs -> {
    //        //    System.out.println(cs.getCode() + ":" + cs.getName());
    //        //});
    //        System.out.println(csList2.size());
    //        
    //        Querier q3 = QuerierBuilder.newInstance()
    //                .searchProperty("name", "name_%")
    //                .addOrder("createDate")
    //                .addOrder("id")
    //                .addOrder("name", OrderDirectionEnum.ASC)
    //                .addFilter(Filter.isNotNull("remark"))
    //                .addFilter(Filter.eq("modifyAble", true))
    //                .addFilter(Filter.eq("valie", true))
    //                .querier();
    //        List<ClientSource> csList3 = clientSourceDao.queryList(q3);
    //        //csList2.stream().forEach(cs -> {
    //        //    System.out.println(cs.getCode() + ":" + cs.getName());
    //        //});
    //        System.out.println(csList3.size());
    //        
    //        Querier q4 = QuerierBuilder.newInstance()
    //                .searchProperty("name", "name_%")
    //                //.orderProperty("id")
    //                .addFilter(Filter.isNotNull("remark"))
    //                .addFilter(Filter.eq("modifyAble", true))
    //                .addFilter(Filter.eq("valie", true))
    //                .addFilter(Filter.in("code",
    //                        new String[] { "code_0", "code_1", "code_2", "code_3",
    //                                "code_4" }))
    //                .querier();
    //        List<ClientSource> csList4 = clientSourceDao.queryList(q4);
    //        //csList2.stream().forEach(cs -> {
    //        //    System.out.println(cs.getCode() + ":" + cs.getName());
    //        //});
    //        System.out.println(csList4.size());
    //    }
}
