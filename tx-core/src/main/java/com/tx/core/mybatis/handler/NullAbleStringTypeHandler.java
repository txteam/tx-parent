/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2012-12-6
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
 * 修改默认的String映射处理器
 * 替换系统中的TypeHandler
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2012-12-6]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@MappedTypes({String.class,Character.class})
public class NullAbleStringTypeHandler extends BaseTypeHandler<String>{
    
    /**
     * <默认构造函数>
     */
    public NullAbleStringTypeHandler() {
        super();
    }
    
    /**
     * @param ps
     * @param i
     * @param parameter
     * @param jdbcType
     * @throws SQLException
     */
    @Override
    public void setParameter(PreparedStatement ps, int i, String parameter,
            JdbcType jdbcType) throws SQLException {
        if (parameter == null && (jdbcType == null || JdbcType.OTHER == jdbcType)) {
            ps.setString(i, null);
        } else {
            super.setParameter(ps, i, parameter, jdbcType);
        }
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, String parameter, JdbcType jdbcType)
        throws SQLException {
      ps.setString(i, parameter);
    }

    @Override
    public String getNullableResult(ResultSet rs, String columnName)
        throws SQLException {
      return rs.getString(columnName);
    }

    @Override
    public String getNullableResult(ResultSet rs, int columnIndex)
        throws SQLException {
      return rs.getString(columnIndex);
    }

    @Override
    public String getNullableResult(CallableStatement cs, int columnIndex)
        throws SQLException {
      return cs.getString(columnIndex);
    }
    
}
