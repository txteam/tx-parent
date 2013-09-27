/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-9-27
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
 * @version  [版本号, 2013-9-27]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@MappedTypes(value = { byte[].class })
public class NullAbleByteArrayTypeHandler extends BaseTypeHandler<byte[]> {
    
    /**
     * @param ps
     * @param i
     * @param parameter
     * @param jdbcType
     * @throws SQLException
     */
    @Override
    public void setParameter(PreparedStatement ps, int i, byte[] parameter,
            JdbcType jdbcType) throws SQLException {
        if (parameter == null
                && (jdbcType == null || JdbcType.OTHER == jdbcType)) {
            ps.setNull(i, JdbcUtils.getSqlTypeByJavaType(byte[].class));
        } else {
            super.setParameter(ps, i, parameter, jdbcType);
        }
    }
    
    /**
     * @param ps
     * @param i
     * @param parameter
     * @param jdbcType
     * @throws SQLException
     */
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i,
            byte[] parameter, JdbcType jdbcType) throws SQLException {
        ps.setBytes(i, parameter);
    }
    
    /**
     * @param rs
     * @param columnName
     * @return
     * @throws SQLException
     */
    @Override
    public byte[] getNullableResult(ResultSet rs, String columnName)
            throws SQLException {
        return rs.getBytes(columnName);
    }
    
    /**
     * @param rs
     * @param columnIndex
     * @return
     * @throws SQLException
     */
    @Override
    public byte[] getNullableResult(ResultSet rs, int columnIndex)
            throws SQLException {
        return rs.getBytes(columnIndex);
    }
    
    /**
     * @param cs
     * @param columnIndex
     * @return
     * @throws SQLException
     */
    @Override
    public byte[] getNullableResult(CallableStatement cs, int columnIndex)
            throws SQLException {
        return cs.getBytes(columnIndex);
    }
}
