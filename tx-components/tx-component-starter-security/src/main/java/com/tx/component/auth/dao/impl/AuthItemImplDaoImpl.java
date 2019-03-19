///*
// * 描          述:  <描述>
// * 修  改   人:  
// * 修改时间:  
// * <修改描述:>
// */
//package com.tx.component.auth.dao.impl;
//
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import javax.sql.DataSource;
//
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.dao.support.DataAccessUtils;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.jdbc.core.PreparedStatementSetter;
//import org.springframework.jdbc.core.RowMapper;
//
//import com.tx.component.auth.dao.AuthItemDao;
//import com.tx.component.auth.model.AuthItem;
//import com.tx.core.TxConstants;
//import com.tx.core.exceptions.util.AssertUtils;
//import com.tx.core.util.ObjectUtils;
//import com.tx.core.util.UUIDUtils;
//
///**
// * AuthItemImpl持久层
// * 
// * @author  
// * @version  [版本号, 2012-12-11]
// * @see  [相关类/方法]
// * @since  [产品/模块版本]
// */
//public class AuthItemImplDaoImpl implements AuthItemDao {
//    
//    RedisTemplate<K, V> redisTemplate;
//    
//    /** jdbcTemplate 句柄 */
//    private JdbcTemplate jdbcTemplate;
//    
//    /** dataSource句柄 */
//    private DataSource dataSource;
//    
//    /**
//     * <默认构造函数>
//     */
//    public AuthItemImplDaoImpl(JdbcTemplate jdbcTemplate,
//            DataSource dataSource) {
//        super();
//        AssertUtils.notTrue(jdbcTemplate == null && dataSource == null,
//                "jdbcTemplate and dataSource is null.");
//        this.jdbcTemplate = jdbcTemplate;
//        this.dataSource = dataSource;
//        if(this.jdbcTemplate == null){
//            this.jdbcTemplate = new JdbcTemplate(this.dataSource);
//        }
//    }
//    
//    /**
//     * <默认构造函数>
//     */
//    public AuthItemImplDaoImpl(JdbcTemplate jdbcTemplate) {
//        super();
//        AssertUtils.notNull(jdbcTemplate, "jdbcTemplate is null.");
//        this.jdbcTemplate = jdbcTemplate;
//    }
//    
//    /**
//     * @param condition
//     */
//    @Override
//    public void insert(final AuthItem condition) {
//        StringBuilder sb = new StringBuilder(TxConstants.INITIAL_STR_LENGTH);
//        sb.append("INSERT INTO AUTH_AUTHITEM").append(tableSuffix).append("(");
//        sb.append("ID,");
//        sb.append("PARENTID,");
//        sb.append("SYSTEMID,");
//        sb.append("VALID,");
//        sb.append("EDITABLE,");
//        sb.append("VIEWABLE,");
//        sb.append("NAME,");
//        sb.append("DESCRIPTION,");
//        sb.append("AUTHTYPE");
//        sb.append(")");
//        sb.append("VALUES(");
//        sb.append("?,?,?,?,?,?,?,?,?");
//        sb.append(")");
//        
//        Map<String, Object> params = new HashMap<String, Object>();
//        params.put("authItem", condition);
//        params.put("tableSuffix", tableSuffix);
//        
//        if (StringUtils.isEmpty(condition.getId())) {
//            condition.setId(UUIDUtils.generateUUID());
//        }
//        
//        this.jdbcTemplate.update(sb.toString(), new PreparedStatementSetter() {
//            @Override
//            public void setValues(PreparedStatement ps) throws SQLException {
//                int parameterIndex = 0;
//                ps.setString(++parameterIndex, condition.getId());
//                ps.setString(++parameterIndex, condition.getParentId());
//                ps.setString(++parameterIndex, condition.getSystemId());
//                ps.setBoolean(++parameterIndex, condition.isValid());
//                ps.setBoolean(++parameterIndex, condition.isEditAble());
//                ps.setBoolean(++parameterIndex, condition.isViewAble());
//                ps.setString(++parameterIndex, condition.getName());
//                ps.setString(++parameterIndex, condition.getDescription());
//                ps.setString(++parameterIndex, condition.getAuthType());
//            }
//        });
//    }
//    
//    /**
//     * @param condition
//     * @return
//     */
//    @Override
//    public int delete(final AuthItem condition) {
//        StringBuilder sb = new StringBuilder(TxConstants.INITIAL_STR_LENGTH);
//        sb.append("DELETE FROM AUTH_AUTHITEM").append(tableSuffix);
//        sb.append(" WHERE ID = ?");
//        sb.append(" AND SYSTEMID = ?");
//        
//        int resInt = this.jdbcTemplate.update(sb.toString(),
//                new PreparedStatementSetter() {
//                    
//                    @Override
//                    public void setValues(PreparedStatement ps)
//                            throws SQLException {
//                        int parameterIndex = 0;
//                        ps.setString(++parameterIndex, condition.getId());
//                        ps.setString(++parameterIndex, condition.getSystemId());
//                    }
//                });
//        
//        return resInt;
//    }
//    
//    /**
//     * @param condition
//     * @return
//     */
//    @Override
//    public AuthItem find(AuthItem condition) {
//        StringBuilder sb = new StringBuilder(TxConstants.INITIAL_STR_LENGTH);
//        sb.append("SELECT ");
//        sb.append("ID,");
//        sb.append("PARENTID,");
//        sb.append("REFID,");
//        sb.append("REFTYPE,");
//        sb.append("SYSTEMID,");
//        sb.append("VALID,");
//        sb.append("EDITABLE,");
//        sb.append("VIEWABLE,");
//        sb.append("NAME,");
//        sb.append("DESCRIPTION,");
//        sb.append("AUTHTYPE");
//        sb.append(" FROM AUTH_AUTHITEM").append(tableSuffix).append(" TAII");
//        sb.append(" WHERE TAII.ID = ? AND TAII.SYSTEMID = ?");
//        
//        List<AuthItem> authItemList = this.jdbcTemplate.query(sb.toString(), new Object[] { condition.getId(), condition.getSystemId() },authItemRowMapper);
//        AuthItem res = DataAccessUtils.singleResult(authItemList);
//        return res;
//    }
//    
//    /**
//     * @param params
//     * @return
//     */
//    @Override
//    public List<AuthItem> queryList(
//            final Map<String, Object> params, String tableSuffix) {
//        StringBuilder sb = new StringBuilder(TxConstants.INITIAL_STR_LENGTH);
//        sb.append("SELECT ");
//        sb.append("ID,");
//        sb.append("PARENTID,");
//        sb.append("REFID,");
//        sb.append("REFTYPE,");
//        sb.append("SYSTEMID,");
//        sb.append("VALID,");
//        sb.append("EDITABLE,");
//        sb.append("VIEWABLE,");
//        sb.append("NAME,");
//        sb.append("DESCRIPTION,");
//        sb.append("AUTHTYPE");
//        sb.append(" FROM AUTH_AUTHITEM").append(tableSuffix).append(" TAII ");
//        
//        StringBuilder conditionSb = new StringBuilder(
//                TxConstants.INITIAL_STR_LENGTH);
//        if (!ObjectUtils.isEmpty(params.get("id"))) {
//            conditionSb.append(" AND TAII.ID = ?");
//        }
//        if (!ObjectUtils.isEmpty(params.get("refId"))) {
//            conditionSb.append(" AND TAII.REFID = ?");
//        }
//        if (!ObjectUtils.isEmpty(params.get("refType"))) {
//            conditionSb.append(" AND TAII.REFTYPE = ?");
//        }
//        if (!ObjectUtils.isEmpty(params.get("systemId"))) {
//            conditionSb.append(" AND TAII.SYSTEMID = ?");
//        }
//        if (!ObjectUtils.isEmpty(params.get("valid"))) {
//            conditionSb.append(" AND TAII.VALID = ?");
//        }
//        if (!ObjectUtils.isEmpty(params.get("parentId"))) {
//            conditionSb.append(" AND TAII.PARENTID = ?");
//        }
//        if (!ObjectUtils.isEmpty(params.get("editAble"))) {
//            conditionSb.append(" AND TAII.EDITABLE = ?");
//        }
//        if (!ObjectUtils.isEmpty(params.get("viewAble"))) {
//            conditionSb.append(" AND TAII.VIEWABLE = ?");
//        }
//        if (!ObjectUtils.isEmpty(params.get("authType"))) {
//            conditionSb.append(" AND TAII.AUTHTYPE = ?");
//        }
//        
//        if (!StringUtils.isEmpty(conditionSb)) {
//            sb.append(" WHERE ").append(conditionSb.substring(4));
//        }
//        
//        List<AuthItem> resList = this.jdbcTemplate.query(sb.toString(),
//                new PreparedStatementSetter() {
//                    
//                    @Override
//                    public void setValues(PreparedStatement ps)
//                            throws SQLException {
//                        int parameterIndex = 0;
//                        if (!ObjectUtils.isEmpty(params.get("id"))) {
//                            ps.setString(++parameterIndex,
//                                    (String) params.get("id"));
//                        }
//                        if (!ObjectUtils.isEmpty(params.get("refId"))) {
//                            ps.setString(++parameterIndex,
//                                    (String) params.get("refId"));
//                        }
//                        if (!ObjectUtils.isEmpty(params.get("refType"))) {
//                            ps.setString(++parameterIndex,
//                                    (String) params.get("refType"));
//                        }
//                        if (!ObjectUtils.isEmpty(params.get("systemId"))) {
//                            ps.setString(++parameterIndex,
//                                    (String) params.get("systemId"));
//                        }
//                        if (!ObjectUtils.isEmpty(params.get("valid"))) {
//                            ps.setBoolean(++parameterIndex,
//                                    (Boolean) params.get("valid"));
//                        }
//                        if (!ObjectUtils.isEmpty(params.get("parentId"))) {
//                            ps.setString(++parameterIndex,
//                                    (String) params.get("parentId"));
//                        }
//                        if (!ObjectUtils.isEmpty(params.get("editAble"))) {
//                            ps.setBoolean(++parameterIndex,
//                                    (Boolean) params.get("editAble"));
//                        }
//                        if (!ObjectUtils.isEmpty(params.get("viewAble"))) {
//                            ps.setBoolean(++parameterIndex,
//                                    (Boolean) params.get("viewAble"));
//                        }
//                        if (!ObjectUtils.isEmpty(params.get("authType"))) {
//                            ps.setString(++parameterIndex,
//                                    (String) params.get("authType"));
//                        }
//                    }
//                }, authItemRowMapper);
//        
//        return resList;
//    }
//    
//    /**
//     * @param updateRowMap
//     * @return
//     */
//    @Override
//    public int updateAuthItemImpl(final Map<String, Object> updateRowMap,
//            String tableSuffix) {
//        StringBuilder sb = new StringBuilder(TxConstants.INITIAL_STR_LENGTH);
//        sb.append(" UPDATE AUTH_AUTHITEM").append(tableSuffix).append(" SET ");
//        if (!ObjectUtils.isEmpty(updateRowMap.get("refId"))) {
//            sb.append(" REFID = ?,");
//        }
//        if (!ObjectUtils.isEmpty(updateRowMap.get("refType"))) {
//            sb.append(" REFTYPE = ?,");
//        }
//        if (!ObjectUtils.isEmpty(updateRowMap.get("valid"))) {
//            sb.append(" VALID = ?,");
//        }
//        if (!ObjectUtils.isEmpty(updateRowMap.get("parentId"))) {
//            sb.append(" PARENTID = ?,");
//        }
//        if (!ObjectUtils.isEmpty(updateRowMap.get("description"))) {
//            sb.append(" DESCRIPTION = ?,");
//        }
//        if (!ObjectUtils.isEmpty(updateRowMap.get("editAble"))) {
//            sb.append(" EDITABLE = ?,");
//        }
//        if (!ObjectUtils.isEmpty(updateRowMap.get("viewAble"))) {
//            sb.append(" VIEWABLE = ?,");
//        }
//        if (!ObjectUtils.isEmpty(updateRowMap.get("name"))) {
//            sb.append(" NAME = ?,");
//        }
//        if (!ObjectUtils.isEmpty(updateRowMap.get("authType"))) {
//            sb.append(" AUTHTYPE = ?,");
//        }
//        sb.deleteCharAt(sb.length() - 1);
//        sb.append(" WHERE ID = ? AND SYSTEMID = ? ");
//        
//        int resInt = this.jdbcTemplate.update(sb.toString(), new PreparedStatementSetter() {
//            
//            @Override
//            public void setValues(PreparedStatement ps) throws SQLException {
//                int parameterIndex = 0;
//                if (!ObjectUtils.isEmpty(updateRowMap.get("refId"))) {
//                    ps.setString(++parameterIndex,
//                            (String) updateRowMap.get("refId"));
//                }
//                if (!ObjectUtils.isEmpty(updateRowMap.get("refType"))) {
//                    ps.setString(++parameterIndex,
//                            (String) updateRowMap.get("refType"));
//                }
//                if (!ObjectUtils.isEmpty(updateRowMap.get("valid"))) {
//                    ps.setBoolean(++parameterIndex,
//                            (Boolean) updateRowMap.get("valid"));
//                }
//                if (!ObjectUtils.isEmpty(updateRowMap.get("parentId"))) {
//                    ps.setString(++parameterIndex,
//                            (String) updateRowMap.get("parentId"));
//                }
//                if (!ObjectUtils.isEmpty(updateRowMap.get("description"))) {
//                    ps.setString(++parameterIndex,
//                            (String) updateRowMap.get("description"));
//                }
//                if (!ObjectUtils.isEmpty(updateRowMap.get("editAble"))) {
//                    ps.setBoolean(++parameterIndex,
//                            (Boolean) updateRowMap.get("editAble"));
//                }
//                if (!ObjectUtils.isEmpty(updateRowMap.get("viewAble"))) {
//                    ps.setBoolean(++parameterIndex,
//                            (Boolean) updateRowMap.get("viewAble"));
//                }
//                if (!ObjectUtils.isEmpty(updateRowMap.get("name"))) {
//                    ps.setString(++parameterIndex,
//                            (String) updateRowMap.get("name"));
//                }
//                if (!ObjectUtils.isEmpty(updateRowMap.get("authType"))) {
//                    ps.setString(++parameterIndex,
//                            (String) updateRowMap.get("authType"));
//                }
//                ps.setString(++parameterIndex,
//                        (String) updateRowMap.get("id"));
//                ps.setString(++parameterIndex,
//                        (String) updateRowMap.get("systemId"));
//            }
//        });
//        
//        return resInt;
//    }
//    
//    private static RowMapper<AuthItem> authItemRowMapper = new RowMapper<AuthItem>() {
//        @Override
//        public AuthItem mapRow(ResultSet rs, int rowNum)
//                throws SQLException {
//            AuthItem res = new AuthItem();
//            res.setId(rs.getString("ID"));
//            res.setRefId(rs.getString("REFID"));
//            res.setRefType(rs.getString("REFTYPE"));
//            res.setParentId(rs.getString("PARENTID"));
//            res.setSystemId(rs.getString("SYSTEMID"));
//            res.setDescription(rs.getString("DESCRIPTION"));
//            res.setName(rs.getString("NAME"));
//            res.setValid(rs.getBoolean("VALID"));
//            res.setEditAble(rs.getBoolean("EDITABLE"));
//            res.setViewAble(rs.getBoolean("VIEWABLE"));
//            res.setAuthType(rs.getString("AUTHTYPE"));
//            return res;
//        }
//    };
//
//    /**
//     * @param 对jdbcTemplate进行赋值
//     */
//    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
//        this.jdbcTemplate = jdbcTemplate;
//    }
//
//    /**
//     * @param 对dataSource进行赋值
//     */
//    public void setDataSource(DataSource dataSource) {
//        this.dataSource = dataSource;
//    }
//}
