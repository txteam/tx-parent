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

import org.apache.commons.lang.time.DateUtils;
import org.apache.ibatis.type.JdbcType;
import org.hibernate.dialect.MySQL5InnoDBDialect;

import com.tx.core.jdbc.model.QueryConditionTypeEnum;
import com.tx.core.jdbc.sqlsource.SqlSource;

/**
 * <功能简述> <功能详细描述>
 * 
 * @author brady
 * @version [版本号, 2013-9-6]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class SimpleSqlSourceTest {
    
    @SuppressWarnings("unchecked")
    public static void main(String[] args) {
        @SuppressWarnings("rawtypes")
        SqlSource simpleSqlMapMapper = new SqlSource("t_test", "id", new MySQL5InnoDBDialect());
        simpleSqlMapMapper.addProperty2columnMapping("id", "idcol", String.class);
        simpleSqlMapMapper.addProperty2columnMapping("aaa", "aCol", String.class);
        simpleSqlMapMapper.addProperty2columnMapping("bbb", "bCol", String.class);
        simpleSqlMapMapper.addProperty2columnMapping("ccc", "cCol", Date.class);
        //simpleSqlMapMapper.addQueryConditionProperty2SqlMapping("", conditionExpression)
        //simpleSqlMapMapper.addq
        
        System.out.println(simpleSqlMapMapper.insertSql());
        System.out.println(" --- --- --- --- --- --- --- ");
        System.out.println(simpleSqlMapMapper.deleteSql());
        System.out.println(" --- --- --- --- --- --- --- ");
        System.out.println(simpleSqlMapMapper.findSql());
        System.out.println(" --- --- --- --- --- --- --- ");
        
        simpleSqlMapMapper.addQueryConditionKey2SqlMapping(QueryConditionTypeEnum.EQUAL, "aaa", "AAA = ?", JdbcType.VARCHAR, String.class);
        simpleSqlMapMapper.addQueryConditionKey2SqlMapping(QueryConditionTypeEnum.GREATER, "minCCC", "CCC > ?", JdbcType.TIMESTAMP, Date.class);
        simpleSqlMapMapper.addQueryConditionKey2SqlMapping(QueryConditionTypeEnum.LESS, "maxCCC", "CCC < ?", JdbcType.TIMESTAMP, Date.class);
        
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("aaa", "111");
        params.put("maxCCC", DateUtils.addDays(new Date(), 1));
        
        System.out.println(simpleSqlMapMapper.querySql(params));
        System.out.println(" --- --- --- --- --- --- --- ");
        System.out.println(simpleSqlMapMapper.countSql(params));
        System.out.println(" --- --- --- --- --- --- --- ");
        
        simpleSqlMapMapper.addUpdateAblePropertyNames("aaa");
        simpleSqlMapMapper.addUpdateAblePropertyNames("bbb");
        
        Map<String, Object> params2 = new HashMap<String, Object>();
        params2.put("aaa", "111");
        params2.put("id", "111");
        System.out.println(simpleSqlMapMapper.updateSql(params2));
        System.out.println(" --- --- --- --- --- --- --- ");
        
        //        System.out.println(simpleSqlMapMapper.queryPagedSql(new Oracle9iDialect(),
        //                params,
        //                1,
        //                10));
        //        System.out.println(simpleSqlMapMapper.queryPagedSql(new Oracle9iDialect(),
        //                params,
        //                2,
        //                10));
        //        System.out.println(simpleSqlMapMapper.queryPagedSql(new MySQL5InnoDBDialect(),
        //                params,
        //                1,
        //                10));
        //        System.out.println(simpleSqlMapMapper.queryPagedSql(new MySQL5InnoDBDialect(),
        //                params,
        //                2,
        //                10));
        //        System.out.println(simpleSqlMapMapper.queryPagedSql(new H2Dialect(),
        //                params,
        //                1,
        //                10));
        //        System.out.println(simpleSqlMapMapper.queryPagedSql(new H2Dialect(),
        //                params,
        //                2,
        //                10));
    }
}
