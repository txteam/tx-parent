/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-9-5
 * <修改描述:>
 */
package com.tx.core.jdbc.util;

import java.util.WeakHashMap;

import com.tx.core.dbutils.SimpleSqlMapMapper;
import com.tx.core.mybatis.generator.model.SqlMapMapper;

/**
 * 简单的sqlMapMapper工具类<br/>
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2013-9-5]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class SimpleSqlMapMapperUtils {
    
    private String test;
    
    /**
     * sqlMapMapper的弱引用工具类<br/>
     */
    private static WeakHashMap<Class<?>, SqlMapMapper> sqlMapMapperMapping = new WeakHashMap<Class<?>, SqlMapMapper>();

    public static SimpleSqlMapMapper buildSqlMapMapper(Class<?> type){
        return null;
    }
    
    private static SimpleSqlMapMapper buildSimpleSqlMapMapperByJpaAnnotation(Class<?> type){
        //type.getAnnotation(annotationClass)
        return null;
    }
}
