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

/**
 * <功能简述>
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2012-12-11]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@MappedTypes(value = { Byte.class, Character.class })
public class NullAbleByteTypeHandler extends BaseTypeHandler<Byte> {
    
    /**
     * @param ps
     * @param i
     * @param parameter
     * @param jdbcType
     * @throws SQLException
     */
    @Override
    public void setParameter(PreparedStatement ps, int i, Byte parameter,
            JdbcType jdbcType) throws SQLException {
        if (parameter == null
                && (jdbcType == null || JdbcType.OTHER == jdbcType)) {
            ps.setNull(i, JdbcType.CHAR.TYPE_CODE);
        } else {
            super.setParameter(ps, i, parameter, jdbcType);
        }
    }
    
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i,
            Byte parameter, JdbcType jdbcType) throws SQLException {
        ps.setByte(i, parameter);
    }
    
    @Override
    public Byte getNullableResult(ResultSet rs, String columnName)
            throws SQLException {
        return rs.getByte(columnName);
    }
    
    @Override
    public Byte getNullableResult(ResultSet rs, int columnIndex)
            throws SQLException {
        return rs.getByte(columnIndex);
    }
    
    @Override
    public Byte getNullableResult(CallableStatement cs, int columnIndex)
            throws SQLException {
        return cs.getByte(columnIndex);
    }
}
