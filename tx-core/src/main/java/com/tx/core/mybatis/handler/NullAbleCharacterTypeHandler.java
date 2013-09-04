/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2012-12-11
 * <修改描述:>
 */
package com.tx.core.mybatis.handler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;

import com.tx.core.util.JdbcUtils;

/**
 * <功能简述>
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2012-12-11]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@MappedTypes(value = { Character.class })
public class NullAbleCharacterTypeHandler extends BaseTypeHandler<Character> {
    
    /**
     * @param ps
     * @param i
     * @param parameter
     * @param jdbcType
     * @throws SQLException
     */
    @Override
    public void setParameter(PreparedStatement ps, int i, Character parameter,
            JdbcType jdbcType) throws SQLException {
        if (parameter == null
                && (jdbcType == null || JdbcType.OTHER == jdbcType)) {
            ps.setNull(i, JdbcUtils.getSqlTypeByJavaType(Character.class));
        } else {
            super.setParameter(ps, i, parameter, jdbcType);
        }
    }
    
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i,
            Character parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, parameter.toString());
    }
    
    @Override
    public Character getNullableResult(ResultSet rs, String columnName)
            throws SQLException {
        String columnValue = rs.getString(columnName);
        if (columnValue != null) {
            return columnValue.charAt(0);
        } else {
            return null;
        }
    }
    
    @Override
    public Character getNullableResult(ResultSet rs, int columnIndex)
            throws SQLException {
        String columnValue = rs.getString(columnIndex);
        if (columnValue != null) {
            return columnValue.charAt(0);
        } else {
            return null;
        }
    }
    
    @Override
    public Character getNullableResult(CallableStatement cs, int columnIndex)
            throws SQLException {
        String columnValue = cs.getString(columnIndex);
        if (columnValue != null) {
            return columnValue.charAt(0);
        } else {
            return null;
        }
    }
}
