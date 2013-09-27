/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-9-4
 * <修改描述:>
 */
package com.tx.core.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;
import org.apache.ibatis.type.TypeHandlerRegistry;

import com.tx.core.exceptions.util.AssertUtils;

/**
 * 服务于jdbc的一些工具方法
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2013-9-4]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class JdbcUtils {
    
    /** 基本类型集合 */
    private static final Map<Class<?>, Integer> SIMPLE_TYPE_2_TYPES_MAP = new HashMap<Class<?>, Integer>();
    
    private static TypeHandlerRegistry typeHandlerRegistry = new TypeHandlerRegistry();
    
    static {
        typeHandlerRegistry.register("com.tx.core.mybatis.handler");
    }
    
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
    
    /**
      * 根据javaType获取对应的sqlType
      *<功能详细描述>
      * @param type
      * @return [参数说明]
      * 
      * @return int [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public static int getSqlTypeByJavaType(Class<?> type) {
        AssertUtils.isTrue(SIMPLE_TYPE_2_TYPES_MAP.containsKey(type),
                "unsupport type:{}. not simple type.");
        
        return SIMPLE_TYPE_2_TYPES_MAP.get(type);
    }
    
    /**
      * 根据javaType获取对应的ibatis中定义的jdbcType
      *<功能详细描述>
      * @param type
      * @return [参数说明]
      * 
      * @return JdbcType [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public static JdbcType getJdbcTypeByJavaType(Class<?> type) {
        AssertUtils.isTrue(SIMPLE_TYPE_2_TYPES_MAP.containsKey(type),
                "unsupport type:{}. not simple type.");
        
        JdbcType jdbcType = JdbcType.forCode(SIMPLE_TYPE_2_TYPES_MAP.get(type));
        return jdbcType;
    }
    
    /**
      * 为简单属性设值<br/>
      *<功能详细描述>
      * @param ps
      * @param parameterIndex
      * @param value
      * @param type
      * @throws SQLException [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static void setPreparedStatementValueForSimpleType(
            PreparedStatement ps, int parameterIndex, Object value,
            Class<?> type) throws SQLException {
        AssertUtils.isTrue(SIMPLE_TYPE_2_TYPES_MAP.containsKey(type),
                "type:{} is not simple type",
                new Object[] { type });
        
        if (value == null) {
            ps.setNull(parameterIndex, getSqlTypeByJavaType(type));
            return;
        }
        TypeHandler typeHandler = typeHandlerRegistry.getTypeHandler(type);
        
        AssertUtils.notNull(typeHandler,
                "type:{} is not typeHandler",
                new Object[] { type });
        typeHandler.setParameter(ps,
                parameterIndex,
                value,
                JdbcType.forCode(SIMPLE_TYPE_2_TYPES_MAP.get(type)));
    }
    
    /**
      * 是否是直接支持存储的类型
      *<功能详细描述>
      * @param type
      * @return [参数说明]
      * 
      * @return boolean [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public static boolean isSupportedSimpleType(Class<?> type) {
        @SuppressWarnings("rawtypes")
        TypeHandler typeHandler = typeHandlerRegistry.getTypeHandler(type);
        if (typeHandler != null) {
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * 为简单属性设值<br/>
     *<功能详细描述>
     * @param ps
     * @param parameterIndex
     * @param value
     * @param type
     * @throws SQLException [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static void setPreparedStatementValueForSimpleType(
            PreparedStatement ps, int parameterIndex, Object value,
            JdbcType jdbcType) throws SQLException {
        if (value == null) {
            ps.setNull(parameterIndex, jdbcType.TYPE_CODE);
            return;
        }
        
        TypeHandler typeHandler = typeHandlerRegistry.getTypeHandler(jdbcType);
        typeHandler.setParameter(ps, parameterIndex, value, jdbcType);
    }
    
    /**
      * 获取值<br/> 
      *<功能详细描述>
      * @param rs
      * @param columnName
      * @param type
      * @return
      * @throws SQLException [参数说明]
      * 
      * @return T [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    @SuppressWarnings("rawtypes")
    public static Object getResultSetValueForSimpleType(ResultSet rs,
            String columnName, Class<?> type) throws SQLException {
        TypeHandler typeHandler = typeHandlerRegistry.getTypeHandler(type);
        
        AssertUtils.notNull(typeHandler,
                "type:{} is not typeHandler",
                new Object[] { type });
        Object res = typeHandler.getResult(rs, columnName);
        return res;
    }
    
}
