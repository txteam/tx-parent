/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-9-6
 * <修改描述:>
 */
package com.tx.core.jdbc;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.hibernate.dialect.MySQL5InnoDBDialect;
import org.hibernate.dialect.Oracle9iDialect;
import org.junit.Test;

import com.tx.core.jdbc.sqlsource.SqlSource;
import com.tx.core.jdbc.sqlsource.SqlSourceBuilder;


 /**
  * 验证sqlBuilder
  * <功能详细描述>
  * 
  * @author  brady
  * @version  [版本号, 2013-9-6]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public class SimpleSqlSourceBuilderTest {
    
    private SqlSourceBuilder simpleSqlSourceBuilder = new SqlSourceBuilder();
    
    //@Test
    public void testbuild1(){
        
        SqlSource<TestA> r = simpleSqlSourceBuilder.build(TestA.class, new MySQL5InnoDBDialect());
        
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("maxCreateDate", new Date());
        params.put("maxCreateDate", new Date());
        params.put("bbb", "bValue");
        params.put("aParent", "aParentValue");
        params.put("aid", "idValue");
        
        System.out.println(r.insertSql());
        System.out.println(r.deleteSql());
        System.out.println(r.querySql(params));
        System.out.println(r.findSql());
        System.out.println(r.updateSql(params));
        System.out.println(r.queryPagedSql(params, 1, 10));
        System.out.println(r.queryPagedSql(params, 2, 10));
        
        MapUtils.verbosePrint(System.out, "map:", r.getQueryCondtionParamMaps(params));
    }
    
    //@Test
    public void testbuild2(){
        
        SqlSource<TestAChild> r = simpleSqlSourceBuilder.build(TestAChild.class, new Oracle9iDialect());
        
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("maxCreateDate", new Date());
        params.put("maxCreateDate", new Date());
        params.put("bbb", "bValue");
        params.put("aid", "idValue");
        
        System.out.println(r.insertSql());
        System.out.println(r.deleteSql());
        System.out.println(r.querySql(params));
        System.out.println(r.findSql());
        System.out.println(r.updateSql(params));
        System.out.println(r.queryPagedSql(params, 1, 10));
        System.out.println(r.queryPagedSql(params, 2, 10));
        
        MapUtils.verbosePrint(System.out, "map:", r.getQueryCondtionParamMaps(params));
    }
    
    @Test
    public void testbuild3(){
        

        
        SqlSource<TestAChild> r = simpleSqlSourceBuilder.build(TestAChild.class, new Oracle9iDialect());
        
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("maxCreateDate", new Date());
        params.put("maxCreateDate", new Date());
        params.put("bbb", "bValue");
        params.put("aid", "idValue");
        
        System.out.println(r.insertSql());
        System.out.println(r.deleteSql());
        
        //SimpleSqlSource rClone = null;
        
        try {
            SqlSource<TestAChild> srcSqlSource = simpleSqlSourceBuilder.build(TestAChild.class, new Oracle9iDialect());        
            System.out.println(srcSqlSource.findSql());
            
            @SuppressWarnings("unchecked")
            SqlSource<TestAChild> cloneSqlSource = (SqlSource<TestAChild>)srcSqlSource.clone();
            cloneSqlSource.setPkName("aaa");
            cloneSqlSource.addProperty2columnMapping("abcde", "abcdColone", String.class);
            
            System.out.println(cloneSqlSource.findSql());
            System.out.println(srcSqlSource.findSql());
            
        } catch (CloneNotSupportedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }
}
