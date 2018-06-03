/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2012-12-7
 * <修改描述:>
 */
package com.tx.core.mybatis.handler;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tx.core.util.JdbcUtils;

/**
 * 修改默认的BigDecimal映射处理器
 * 替换系统中的TypeHandler
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2012-12-7]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@MappedTypes(value = { Class.class })
public class NullAbleClassTypeHandler extends BaseTypeHandler<Class<?>> {
    
    private Logger logger = LoggerFactory.getLogger(NullAbleClassTypeHandler.class);
    
    private static Map<String, Class<?>> SIMPLE_TYPE_CLASS_MAP = new HashMap<String, Class<?>>();
    
    static {
        SIMPLE_TYPE_CLASS_MAP.put("byte", byte.class);
        SIMPLE_TYPE_CLASS_MAP.put("short", short.class);
        SIMPLE_TYPE_CLASS_MAP.put("int", int.class);
        SIMPLE_TYPE_CLASS_MAP.put("long", long.class);
        SIMPLE_TYPE_CLASS_MAP.put("float", float.class);
        SIMPLE_TYPE_CLASS_MAP.put("double", double.class);
        SIMPLE_TYPE_CLASS_MAP.put("boolean", boolean.class);
        SIMPLE_TYPE_CLASS_MAP.put("char", char.class);
        
        SIMPLE_TYPE_CLASS_MAP.put("String", String.class);
        SIMPLE_TYPE_CLASS_MAP.put("Byte", Byte.class);
        SIMPLE_TYPE_CLASS_MAP.put("Short", Short.class);
        SIMPLE_TYPE_CLASS_MAP.put("Integer", Integer.class);
        SIMPLE_TYPE_CLASS_MAP.put("Long", Long.class);
        SIMPLE_TYPE_CLASS_MAP.put("Float", Float.class);
        SIMPLE_TYPE_CLASS_MAP.put("Double", Double.class);
        SIMPLE_TYPE_CLASS_MAP.put("Boolean", Boolean.class);
        SIMPLE_TYPE_CLASS_MAP.put("Character", Character.class);
        
        SIMPLE_TYPE_CLASS_MAP.put("BigDecimal", BigDecimal.class);
        SIMPLE_TYPE_CLASS_MAP.put("Date", Date.class);
        SIMPLE_TYPE_CLASS_MAP.put("Class", Class.class);
    }
    
    /**
     * @param ps
     * @param i
     * @param parameter
     * @param jdbcType
     * @throws SQLException
     */
    @Override
    public void setParameter(PreparedStatement ps, int i, Class<?> parameter,
            JdbcType jdbcType) throws SQLException {
        if (parameter == null
                && (jdbcType == null || JdbcType.OTHER == jdbcType)) {
            //如果为空值，则按照String类型进行设值
            ps.setNull(i, JdbcUtils.getSqlTypeByJavaType(String.class));
        } else {
            super.setParameter(ps, i, parameter, jdbcType);
        }
    }
    
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i,
            Class<?> parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, parameter.getName());
        //ps.setBigDecimal(i, parameter);
    }
    
    @Override
    @SuppressWarnings("rawtypes")
    public Class getNullableResult(ResultSet rs, String columnName)
            throws SQLException {
        String className = rs.getString(columnName);
        if (StringUtils.isEmpty(className)) {
            return null;
        } else if (SIMPLE_TYPE_CLASS_MAP.containsKey(className)) {
            Class type = SIMPLE_TYPE_CLASS_MAP.get(className);
            return type;
        } else {
            Class type = null;
            try {
                type = Class.forName(className);
            } catch (ClassNotFoundException e) {
                type = null;
                //logger.info("Class.forName exception.className:{}", className);
            }
            return type;
        }
    }
    
    @Override
    @SuppressWarnings("rawtypes")
    public Class getNullableResult(ResultSet rs, int columnIndex)
            throws SQLException {
        String className = rs.getString(columnIndex);
        if (StringUtils.isEmpty(className)) {
            return null;
        } else if (SIMPLE_TYPE_CLASS_MAP.containsKey(className)) {
            Class type = SIMPLE_TYPE_CLASS_MAP.get(className);
            return type;
        } else {
            Class type = null;
            try {
                type = Class.forName(className);
            } catch (ClassNotFoundException e) {
                logger.warn("Class.forName exception.className:{}", className);
            }
            return type;
        }
    }
    
    @Override
    @SuppressWarnings("rawtypes")
    public Class getNullableResult(CallableStatement cs, int columnIndex)
            throws SQLException {
        String className = cs.getString(columnIndex);
        if (StringUtils.isEmpty(className)) {
            return null;
        } else if (SIMPLE_TYPE_CLASS_MAP.containsKey(className)) {
            Class type = SIMPLE_TYPE_CLASS_MAP.get(className);
            return type;
        } else {
            Class type = null;
            try {
                type = Class.forName(className);
            } catch (ClassNotFoundException e) {
                logger.warn("Class.forName exception.className:{}", className);
            }
            return type;
        }
    }
    
}
