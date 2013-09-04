/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-8-27
 * <修改描述:>
 */
package com.tx.core.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * JdbcTypes数据类型工具集
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2013-8-27]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class DialectUtils {
    
    /** 基本类型集合 */
    private static final Map<Class<?>, Integer> SIMPLE_TYPE_2_TYPES_MAP = new HashMap<Class<?>, Integer>();
    
    static {
        SIMPLE_TYPE_2_TYPES_MAP.put(char[].class, Types.CLOB);
        SIMPLE_TYPE_2_TYPES_MAP.put(byte[].class, Types.BLOB);
        
        SIMPLE_TYPE_2_TYPES_MAP.put(short.class, Types.SMALLINT);
        SIMPLE_TYPE_2_TYPES_MAP.put(Short.class, Types.SMALLINT);
        SIMPLE_TYPE_2_TYPES_MAP.put(int.class, Types.INTEGER);
        SIMPLE_TYPE_2_TYPES_MAP.put(Integer.class, Types.INTEGER);
        SIMPLE_TYPE_2_TYPES_MAP.put(long.class, Types.BIGINT);
        SIMPLE_TYPE_2_TYPES_MAP.put(Long.class, Types.BIGINT);
        SIMPLE_TYPE_2_TYPES_MAP.put(boolean.class, Types.BIT);
        SIMPLE_TYPE_2_TYPES_MAP.put(Boolean.class, Types.BIT);
        SIMPLE_TYPE_2_TYPES_MAP.put(byte.class, Types.TINYINT);
        SIMPLE_TYPE_2_TYPES_MAP.put(Byte.class, Types.TINYINT);
        SIMPLE_TYPE_2_TYPES_MAP.put(Date.class, Types.TIMESTAMP);
        SIMPLE_TYPE_2_TYPES_MAP.put(java.sql.Date.class, Types.TIMESTAMP);
        SIMPLE_TYPE_2_TYPES_MAP.put(Timestamp.class, Types.TIMESTAMP);
        
        SIMPLE_TYPE_2_TYPES_MAP.put(char.class, Types.CHAR);
        SIMPLE_TYPE_2_TYPES_MAP.put(Character.class, Types.CHAR);
        SIMPLE_TYPE_2_TYPES_MAP.put(double.class, Types.NUMERIC);
        SIMPLE_TYPE_2_TYPES_MAP.put(Double.class, Types.NUMERIC);
        SIMPLE_TYPE_2_TYPES_MAP.put(float.class, Types.NUMERIC);
        SIMPLE_TYPE_2_TYPES_MAP.put(Float.class, Types.NUMERIC);
        SIMPLE_TYPE_2_TYPES_MAP.put(BigDecimal.class, Types.NUMERIC);
        SIMPLE_TYPE_2_TYPES_MAP.put(BigInteger.class, Types.NUMERIC);
        SIMPLE_TYPE_2_TYPES_MAP.put(String.class, Types.VARCHAR);
    }
    
    public static int changeSimpleJavaTypeToSqlType(Class<?> type) {
        return SIMPLE_TYPE_2_TYPES_MAP.get(type);
    }
    
}
