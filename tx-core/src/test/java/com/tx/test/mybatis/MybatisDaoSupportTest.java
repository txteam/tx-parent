/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2016年11月18日
 * <修改描述:>
 */
package com.tx.test.mybatis;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.ibatis.mapping.ResultMapping;
import org.apache.ibatis.mapping.SqlCommandType;
import org.springframework.jdbc.core.JdbcTemplate;

import com.tx.core.datasource.DataSourceFinder;
import com.tx.core.datasource.finder.SimpleDataSourceFinder;
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
        
        String tableName = "test_001";
        TableDDLExecutor ddlExecutor = new MysqlTableDDLExecutor(jt);
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
        
        String namespace = "test_namespace";
        MapperBuilderAssistantExtention ass = new MapperBuilderAssistantExtention(
                myBatisDaoSupport.getSqlSessionTemplate().getConfiguration(),
                namespace);
        ass.setCurrentNamespace(namespace);
        String insertStatementId = "test_mapper_insert_test_001";
        ass.saveMappedStatement(insertStatementId,
                SqlCommandType.INSERT,
                "insert into test_001(id,code)values(#{id},#{code}) ",
                TestDemo.class);
        
        TestDemo td = new TestDemo();
        td.setId(UUIDUtils.generateUUID());
        td.setCode("testCode");
        td.setName("testName");
        td.setRemark("testRemark");
        
        myBatisDaoSupport.insert(namespace + "." + insertStatementId, td);
        //.addMappedStatement(ms);
        
        ass.saveMappedStatement(insertStatementId,
                SqlCommandType.INSERT,
                "insert into test_001(id,code,name,remark)values(#{id},#{code},#{name},#{remark}) ",
                TestDemo.class);
        td.setId(UUIDUtils.generateUUID());
        myBatisDaoSupport.insert(namespace + "." + insertStatementId, td);
        
        ass.saveMappedStatement(insertStatementId,
                SqlCommandType.INSERT,
                "insert into test_001(id,code,name,remark,demoId)values(#{id},#{code},#{name},#{remark},#{demo.id}) ",
                TestDemo.class);
        td.setId(UUIDUtils.generateUUID());
        td.setDemo(null);
        myBatisDaoSupport.insert(namespace + "." + insertStatementId, td);
        
        ass.saveMappedStatement(insertStatementId,
                SqlCommandType.INSERT,
                "insert into test_001(id,code,name,remark,demoId)values(#{id},#{code},#{name},#{remark},#{demo.id}) ",
                TestDemo.class);
        td.setId(UUIDUtils.generateUUID());
        td.setDemo(new Demo());
        td.getDemo().setId(UUIDUtils.generateUUID());
        myBatisDaoSupport.insert(namespace + "." + insertStatementId, td);
        
        ass.saveMappedStatement(insertStatementId,
                SqlCommandType.INSERT,
                "insert into test_001(id,code,name,remark,demoId)values(#{id},#{code},#{name},#{remark},#{demo.id}) ",
                TestDemo.class);
        
        String queryStatementId = "test_mapper_query_test_001";
        String resultMapId = "test_resultmap_test_001Map";
        
        List<ResultMapping> resultMappings = new ArrayList<>();
        ass.saveResultMap(resultMapId, TestDemo.class, resultMappings);
        ass.saveMappedStatement(queryStatementId,
                SqlCommandType.SELECT,
                "select id,code from test_001",
                TestDemo.class,
                resultMapId);
        List<TestDemo> tdList = myBatisDaoSupport
                .queryList(namespace + "." + queryStatementId, null);
        for (TestDemo tdTemp : tdList) {
            System.out.println(tdTemp.getId() + " | " + tdTemp.getCode() + " | "
                    + tdTemp.getName() + " | " + tdTemp.getRemark() + " | "
                    + (tdTemp.getDemo() == null ? "null"
                            : tdTemp.getDemo().getId()));
        }
        
        resultMappings.clear();
        //        resultMappings.add(ass.buildResultMapping(TestDemo.class,
        //                "demo.id",
        //                String.class,//TestDemo.class
        //                "demoId",
        //                JdbcType.VARCHAR));
        resultMappings.add(ass.buildResultMapping(null,
                "demo.id",
                null, //TestDemo.class
                "demoId",
                null));
        
        ass.saveResultMap(resultMapId, TestDemo.class, resultMappings);
        ass.saveMappedStatement(queryStatementId,
                SqlCommandType.SELECT,
                "select id,code,name,remark,demoId from test_001",
                TestDemo.class,
                resultMapId);
        tdList = myBatisDaoSupport.queryList(namespace + "." + queryStatementId,
                null);
        System.out.println();
        for (TestDemo tdTemp : tdList) {
            System.out.println(tdTemp.getId() + " | " + tdTemp.getCode() + " | "
                    + tdTemp.getName() + " | " + tdTemp.getRemark() + " | "
                    + (tdTemp.getDemo() == null ? "null"
                            : tdTemp.getDemo().getId()));
        }
    }
}
