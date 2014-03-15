/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2014-2-13
 * <修改描述:>
 */
package com.tx.component.configuration.persister.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;

import com.tx.component.configuration.model.ConfigPropertyItem;
import com.tx.component.configuration.persister.dao.ConfigPropertyItemDao;
import com.tx.core.TxConstants;
import com.tx.core.exceptions.util.AssertUtils;

/**
 * 配置属性数据库持久层<br/>
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2014-2-13]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class ConfigPropertyItemDaoImpl implements ConfigPropertyItemDao {
    
    /** 系统id */
    private String systemId;
    
    /** 数据源实例 */
    private DataSource dataSource;
    
    private JdbcTemplate jdbcTemplate;
    
    /** <默认构造函数> */
    public ConfigPropertyItemDaoImpl(DataSource dataSource, String systemId) {
        super();
        AssertUtils.notNull(dataSource, "dataSource is null.");
        AssertUtils.notEmpty(systemId, "systemId is empty.");
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(this.dataSource);
        this.systemId = systemId;
    }
    
    /**
     * 插入配置属性项
     * @param configPropertyItem
     */
    @Override
    public void insert(final ConfigPropertyItem configPropertyItem) {
        StringBuilder sb = new StringBuilder(TxConstants.INITIAL_STR_LENGTH);
        sb.append("INSERT INTO CORE_CONFIG_CONTEXT(");
        sb.append("ID,");
        sb.append("SYSTEMID,");
        sb.append("KEYNAME,");
        sb.append("VALUE,");
        sb.append("NAME,");
        sb.append("DESCRIPTION,");
        sb.append("CREATEDATE,");
        sb.append("LASTUPDATEDATE,");
        sb.append("VALIDATEEXPRESSION");
        sb.append(") VALUES(?,?,?,?,?,?,?,?,?)");
        
        final String finalSystemId = this.systemId;
        final Date now = new Date();
        //执行插入数据逻辑
        this.jdbcTemplate.update(sb.toString(), new PreparedStatementSetter() {
            /**
             * @param ps
             * @throws SQLException
             */
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                int index = 0;
                
                ps.setString(++index, configPropertyItem.getId());
                ps.setString(++index, finalSystemId);
                ps.setString(++index, configPropertyItem.getKey());
                ps.setString(++index, configPropertyItem.getValue());
                ps.setString(++index, configPropertyItem.getName());
                ps.setString(++index, configPropertyItem.getDescription());
                ps.setTimestamp(++index, new Timestamp(now.getTime()));
                ps.setTimestamp(++index, new Timestamp(now.getTime()));
                ps.setString(++index,
                        configPropertyItem.getValidateExpression());
            }
        });
    }
    
    /**
     * 更新配置属性项
     * @param configPropertyItem
     */
    @Override
    public void update(final ConfigPropertyItem configPropertyItem) {
        StringBuilder sb = new StringBuilder(TxConstants.INITIAL_STR_LENGTH);
        sb.append("UPDATE CORE_CONFIG_CONTEXT SET ");
        sb.append("KEYNAME = ?,");
        sb.append("VALUE = ?,");
        sb.append("NAME = ?,");
        sb.append("DESCRIPTION = ?,");
        sb.append("LASTUPDATEDATE = ?,");
        sb.append("VALIDATEEXPRESSION = ?");
        sb.append(" WHERE ID = ? AND SYSTEMID= ?");
        
        final String finalSystemId = this.systemId;
        final Date now = new Date();
        //执行插入数据逻辑
        this.jdbcTemplate.update(sb.toString(), new PreparedStatementSetter() {
            /**
             * @param ps
             * @throws SQLException
             */
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                int index = 0;
                
                ps.setString(++index, configPropertyItem.getKey());
                ps.setString(++index, configPropertyItem.getValue());
                ps.setString(++index, configPropertyItem.getName());
                ps.setString(++index, configPropertyItem.getDescription());
                ps.setTimestamp(++index, new Timestamp(now.getTime()));
                ps.setString(++index,
                        configPropertyItem.getValidateExpression());
                ps.setString(++index, configPropertyItem.getId());
                ps.setString(++index, finalSystemId);
            }
        });
    }
    
    /**
     * 根据系统id查询配置属性项列表
     * @param systemId
     * @return
     */
    @Override
    public List<ConfigPropertyItem> queryConfigPropertyItemList() {
        StringBuilder sb = new StringBuilder(TxConstants.INITIAL_STR_LENGTH);
        sb.append("SELECT TC.ID,");
        sb.append("TC.SYSTEMID,");
        sb.append("TC.KEYNAME,");
        sb.append("TC.VALUE,");
        sb.append("TC.NAME,");
        sb.append("TC.DESCRIPTION,");
        sb.append("TC.CREATEDATE,");
        sb.append("TC.LASTUPDATEDATE,");
        sb.append("TC.VALIDATEEXPRESSION");
        sb.append(" FROM CORE_CONFIG_CONTEXT TC");
        sb.append(" WHERE TC.SYSTEMID = ?");
        List<ConfigPropertyItem> resList = this.jdbcTemplate.query(sb.toString(),
                new Object[] { systemId },
                new RowMapper<ConfigPropertyItem>() {
                    
                    /**
                     * @param rs
                     * @param rowNum
                     * @return
                     * @throws SQLException
                     */
                    @Override
                    public ConfigPropertyItem mapRow(ResultSet rs, int rowNum)
                            throws SQLException {
                        ConfigPropertyItem res = new ConfigPropertyItem();
                        res.setId(rs.getString("ID"));
                        res.setSystemId(rs.getString("SYSTEMID"));
                        res.setName(rs.getString("NAME"));
                        res.setKey(rs.getString("KEYNAME"));
                        res.setValue(rs.getString("VALUE"));
                        res.setDescription(rs.getString("DESCRIPTION"));
                        res.setCreateDate(rs.getDate("CREATEDATE"));
                        res.setLastUpdateDate(rs.getDate("LASTUPDATEDATE"));
                        res.setValidateExpression(rs.getString("VALIDATEEXPRESSION"));
                        return res;
                    }
                });
        return resList;
    }
    
    /**
     * @param 对systemId进行赋值
     */
    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }
    
    /**
     * @param 对dataSource进行赋值
     */
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    
}
