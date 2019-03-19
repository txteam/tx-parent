package com.tx.component.auth.dao.impl;
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
//import java.sql.Timestamp;
//import java.util.Date;
//import java.util.List;
//import java.util.Map;
//import java.util.Map.Entry;
//
//import javax.sql.DataSource;
//
//import org.apache.commons.collections.CollectionUtils;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.jdbc.core.BatchPreparedStatementSetter;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.jdbc.core.PreparedStatementSetter;
//import org.springframework.jdbc.core.RowMapper;
//import org.springframework.util.MultiValueMap;
//
//import com.tx.component.auth.dao.AuthItemRefDao;
//import com.tx.component.auth.model.Auth;
//import com.tx.component.auth.model.AuthItem;
//import com.tx.component.auth.model.AuthItemRef;
//import com.tx.core.TxConstants;
//import com.tx.core.exceptions.SILException;
//import com.tx.core.exceptions.util.AssertUtils;
//import com.tx.core.util.ObjectUtils;
//import com.tx.core.util.UUIDUtils;
//
///**
// * AuthItemRefImpl持久层
// * <功能详细描述>
// * 
// * @author  
// * @version  [版本号, 2012-12-11]
// * @see  [相关类/方法]
// * @since  [产品/模块版本]
// */
//public class AuthItemRefImplDaoImpl implements AuthItemRefDao {
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
//    public AuthItemRefImplDaoImpl(JdbcTemplate jdbcTemplate,
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
//    /** <默认构造函数> */
//    public AuthItemRefImplDaoImpl() {
//        super();
//    }
//    
//    /**
//     * <默认构造函数>
//     */
//    public AuthItemRefImplDaoImpl(JdbcTemplate jdbcTemplate) {
//        super();
//        AssertUtils.notNull(jdbcTemplate, "jdbcTemplate is null.");
//        this.jdbcTemplate = jdbcTemplate;
//    }
//    
//    /**
//     * @param condition
//     */
//    @Override
//    public void insertAuthItemRefImpl(final AuthItemRef authItemRef,
//            String tableSuffix) {
//        authItemRef.setId(UUIDUtils.generateUUID());
//        
//        StringBuilder sb = new StringBuilder(TxConstants.INITIAL_STR_LENGTH);
//        sb.append("INSERT INTO AUTH_AUTHREF").append(tableSuffix).append("(");
//        sb.append("ID,");
//        sb.append("REFID,");
//        sb.append("AUTHREFTYPE,");
//        sb.append("AUTHID,");
//        sb.append("SYSTEMID,");
//        sb.append("AUTHTYPE,");
//        sb.append("TEMP,");
//        sb.append("CREATEOPERID,");
//        sb.append("EFFECTIVEDATE,");
//        sb.append("INVALIDDATE,");
//        sb.append("ENDDATE,");
//        sb.append("CREATEDATE");
//        sb.append(")");
//        sb.append("VALUES(");
//        sb.append("?,?,?,?,?,?,?,?,?,?,?,?");
//        sb.append(")");
//        
//        this.jdbcTemplate.update(sb.toString(), new PreparedStatementSetter() {
//            
//            @Override
//            public void setValues(PreparedStatement ps) throws SQLException {
//                int parameterIndex = 0;
//                ps.setString(++parameterIndex, authItemRef.getId());
//                ps.setString(++parameterIndex, authItemRef.getRefId());
//                ps.setString(++parameterIndex, authItemRef.getAuthRefType());
//                ps.setString(++parameterIndex, authItemRef.getAuthItem()
//                        .getId());
//                ps.setString(++parameterIndex, authItemRef.getAuthItem()
//                        .getSystemId());
//                ps.setString(++parameterIndex, authItemRef.getAuthItem()
//                        .getAuthType());
//                ps.setBoolean(++parameterIndex, authItemRef.isTemp());
//                ps.setString(++parameterIndex, authItemRef.getCreateOperId());
//                ps.setTimestamp(++parameterIndex,
//                        authItemRef.getEffectiveDate() == null ? null
//                                : new Timestamp(authItemRef.getEffectiveDate()
//                                        .getTime()));
//                ps.setTimestamp(++parameterIndex,
//                        authItemRef.getInvalidDate() == null ? null
//                                : new Timestamp(authItemRef.getInvalidDate()
//                                        .getTime()));
//                ps.setTimestamp(++parameterIndex,
//                        authItemRef.getEndDate() == null ? null
//                                : new Timestamp(authItemRef.getEndDate()
//                                        .getTime()));
//                ps.setTimestamp(++parameterIndex,
//                        authItemRef.getCreateDate() == null ? null
//                                : new Timestamp(authItemRef.getCreateDate()
//                                        .getTime()));
//            }
//        });
//    }
//    
//    /**
//     * @param condition
//     * @param tableSuffix
//     */
//    @Override
//    public void batchInsertAuthItemRefImplToHis(
//            final List<AuthItemRef> condition, String tableSuffix) {
//        if (CollectionUtils.isEmpty(condition)) {
//            return;
//        }
//        StringBuilder sb = new StringBuilder(TxConstants.INITIAL_STR_LENGTH);
//        sb.append("INSERT INTO AUTH_AUTHREF_HIS")
//                .append(tableSuffix)
//                .append("(");
//        sb.append("ID,");
//        sb.append("REFID,");
//        sb.append("AUTHREFTYPE,");
//        sb.append("AUTHID,");
//        sb.append("SYSTEMID,");
//        sb.append("AUTHTYPE,");
//        sb.append("TEMP,");
//        sb.append("CREATEOPERID,");
//        sb.append("EFFECTIVEDATE,");
//        sb.append("INVALIDDATE,");
//        sb.append("ENDDATE,");
//        sb.append("CREATEDATE");
//        sb.append(")");
//        sb.append("VALUES(");
//        sb.append("?,?,?,?,?,?,?,?,?,?,?,?");
//        sb.append(")");
//        
//        this.jdbcTemplate.batchUpdate(sb.toString(),
//                new BatchPreparedStatementSetter() {
//                    @Override
//                    public void setValues(PreparedStatement ps, int index)
//                            throws SQLException {
//                        AuthItemRef authItemRef = condition.get(index);
//                        
//                        int parameterIndex = 0;
//                        ps.setString(++parameterIndex, authItemRef.getId());
//                        ps.setString(++parameterIndex, authItemRef.getRefId());
//                        ps.setString(++parameterIndex,
//                                authItemRef.getAuthRefType());
//                        ps.setString(++parameterIndex,
//                                authItemRef.getAuthItem().getId());
//                        ps.setString(++parameterIndex,
//                                authItemRef.getAuthItem().getSystemId());
//                        ps.setString(++parameterIndex,
//                                authItemRef.getAuthItem().getAuthType());
//                        ps.setBoolean(++parameterIndex, authItemRef.isTemp());
//                        ps.setString(++parameterIndex,
//                                authItemRef.getCreateOperId());
//                        ps.setTimestamp(++parameterIndex,
//                                authItemRef.getEffectiveDate() == null ? null
//                                        : new Timestamp(
//                                                authItemRef.getEffectiveDate()
//                                                        .getTime()));
//                        ps.setTimestamp(++parameterIndex,
//                                authItemRef.getInvalidDate() == null ? null
//                                        : new Timestamp(
//                                                authItemRef.getInvalidDate()
//                                                        .getTime()));
//                        ps.setTimestamp(++parameterIndex, new Timestamp(
//                                (new Date()).getTime()));
//                        ps.setTimestamp(++parameterIndex,
//                                authItemRef.getCreateDate() == null ? null
//                                        : new Timestamp(
//                                                authItemRef.getCreateDate()
//                                                        .getTime()));
//                    }
//                    
//                    @Override
//                    public int getBatchSize() {
//                        return condition.size();
//                    }
//                });
//    }
//    
//    /**
//     * @param condition
//     */
//    @Override
//    public void batchInsertAuthItemRefImpl(
//            final List<AuthItemRef> condition, String tableSuffix) {
//        if (CollectionUtils.isEmpty(condition)) {
//            return;
//        }
//        StringBuilder sb = new StringBuilder(TxConstants.INITIAL_STR_LENGTH);
//        sb.append("INSERT INTO AUTH_AUTHREF").append(tableSuffix).append("(");
//        sb.append("ID,");
//        sb.append("REFID,");
//        sb.append("AUTHREFTYPE,");
//        sb.append("AUTHID,");
//        sb.append("SYSTEMID,");
//        sb.append("AUTHTYPE,");
//        sb.append("TEMP,");
//        sb.append("CREATEOPERID,");
//        sb.append("EFFECTIVEDATE,");
//        sb.append("INVALIDDATE,");
//        sb.append("ENDDATE,");
//        sb.append("CREATEDATE");
//        sb.append(")");
//        sb.append("VALUES(");
//        sb.append("?,?,?,?,?,?,?,?,?,?,?,?");
//        sb.append(")");
//        
//        this.jdbcTemplate.batchUpdate(sb.toString(),
//                new BatchPreparedStatementSetter() {
//                    @Override
//                    public void setValues(PreparedStatement ps, int index)
//                            throws SQLException {
//                        AuthItemRef authItemRef = condition.get(index);
//                        authItemRef.setId(UUIDUtils.generateUUID());
//                        
//                        int parameterIndex = 0;
//                        ps.setString(++parameterIndex, authItemRef.getId());
//                        ps.setString(++parameterIndex, authItemRef.getRefId());
//                        ps.setString(++parameterIndex,
//                                authItemRef.getAuthRefType());
//                        ps.setString(++parameterIndex,
//                                authItemRef.getAuthItem().getId());
//                        ps.setString(++parameterIndex,
//                                authItemRef.getAuthItem().getSystemId());
//                        ps.setString(++parameterIndex,
//                                authItemRef.getAuthItem().getAuthType());
//                        ps.setBoolean(++parameterIndex, authItemRef.isTemp());
//                        ps.setString(++parameterIndex,
//                                authItemRef.getCreateOperId());
//                        ps.setTimestamp(++parameterIndex,
//                                authItemRef.getEffectiveDate() == null ? null
//                                        : new Timestamp(
//                                                authItemRef.getEffectiveDate()
//                                                        .getTime()));
//                        ps.setTimestamp(++parameterIndex,
//                                authItemRef.getInvalidDate() == null ? null
//                                        : new Timestamp(
//                                                authItemRef.getInvalidDate()
//                                                        .getTime()));
//                        ps.setTimestamp(++parameterIndex,
//                                authItemRef.getEndDate() == null ? null
//                                        : new Timestamp(
//                                                authItemRef.getEndDate()
//                                                        .getTime()));
//                        ps.setTimestamp(++parameterIndex,
//                                authItemRef.getCreateDate() == null ? null
//                                        : new Timestamp(
//                                                authItemRef.getCreateDate()
//                                                        .getTime()));
//                    }
//                    
//                    @Override
//                    public int getBatchSize() {
//                        return condition.size();
//                    }
//                });
//    }
//    
//    /**
//     * @param condition
//     * @return
//     */
//    @Override
//    public int deleteAuthItemRefImpl(final AuthItemRef condition,
//            String tableSuffix) {
//        AssertUtils.notNull(condition, "deleteCondition is null.");
//        
//        StringBuilder sb = new StringBuilder(TxConstants.INITIAL_STR_LENGTH);
//        sb.append("DELETE FROM AUTH_AUTHREF")
//                .append(tableSuffix)
//                .append(" WHERE ");
//        
//        StringBuilder conditionSb = new StringBuilder(
//                TxConstants.INITIAL_STR_LENGTH);
//        if (condition.isTemp() != null) {
//            conditionSb.append(" TEMP = ?");
//        }
//        if (!StringUtils.isEmpty(condition.getRefId())) {
//            conditionSb.append(" AND REFID = ?");
//        }
//        if (!StringUtils.isEmpty(condition.getAuthRefType())) {
//            conditionSb.append(" AND AUTHREFTYPE = ? ");
//        }
//        if (condition.getAuthItem() != null) {
//            Auth authItem = condition.getAuthItem();
//            if (!StringUtils.isEmpty(authItem.getId())) {
//                conditionSb.append(" AND AUTHID = ?");
//            }
//            if (!StringUtils.isEmpty(authItem.getAuthType())) {
//                conditionSb.append(" AND AUTHTYPE = ?");
//            }
//            if (!StringUtils.isEmpty(authItem.getSystemId())) {
//                conditionSb.append(" AND SYSTEMID = ?");
//            }
//        }
//        sb.append(conditionSb.substring(4));
//        
//        int resInt = this.jdbcTemplate.update(sb.toString(),
//                new PreparedStatementSetter() {
//                    
//                    /**
//                     * @param ps
//                     * @throws SQLException
//                     */
//                    @Override
//                    public void setValues(PreparedStatement ps)
//                            throws SQLException {
//                        int parameterIndex = 0;
//                        if (condition.isTemp() != null) {
//                            ps.setBoolean(++parameterIndex, condition.isTemp());
//                        }
//                        if (!StringUtils.isEmpty(condition.getRefId())) {
//                            ps.setString(++parameterIndex, condition.getRefId());
//                        }
//                        if (!StringUtils.isEmpty(condition.getAuthRefType())) {
//                            ps.setString(++parameterIndex,
//                                    condition.getAuthRefType());
//                        }
//                        if (condition.getAuthItem() != null) {
//                            Auth authItem = condition.getAuthItem();
//                            if (!StringUtils.isEmpty(authItem.getId())) {
//                                ps.setString(++parameterIndex, authItem.getId());
//                            }
//                            if (!StringUtils.isEmpty(authItem.getAuthType())) {
//                                ps.setString(++parameterIndex,
//                                        authItem.getAuthType());
//                            }
//                            if (!StringUtils.isEmpty(authItem.getSystemId())) {
//                                ps.setString(++parameterIndex,
//                                        authItem.getSystemId());
//                            }
//                        }
//                    }
//                    
//                });
//        return resInt;
//    }
//    
//    /**
//     * @param authItemRefImplList
//     */
//    @Override
//    public void batchDeleteAuthItemRefImpl(
//            final List<AuthItemRef> authItemRefImplList, String tableSuffix) {
//        if (CollectionUtils.isEmpty(authItemRefImplList)) {
//            return;
//        }
//        
//        StringBuilder sb = new StringBuilder(TxConstants.INITIAL_STR_LENGTH);
//        sb.append("DELETE FROM AUTH_AUTHREF")
//                .append(tableSuffix)
//                .append(" WHERE ");
//        sb.append(" TEMP = ?");
//        sb.append(" AND REFID = ?");
//        sb.append(" AND AUTHREFTYPE = ?");
//        sb.append(" AND AUTHID = ?");
//        sb.append(" AND AUTHTYPE = ?");
//        sb.append(" AND SYSTEMID = ?");
//        
//        this.jdbcTemplate.batchUpdate(sb.toString(),
//                new BatchPreparedStatementSetter() {
//                    
//                    @Override
//                    public void setValues(PreparedStatement ps, int i)
//                            throws SQLException {
//                        AuthItemRef condition = authItemRefImplList.get(i);
//                        
//                        int parameterIndex = 0;
//                        ps.setBoolean(++parameterIndex, condition.isTemp());
//                        ps.setString(++parameterIndex, condition.getRefId());
//                        ps.setString(++parameterIndex,
//                                condition.getAuthRefType());
//                        Auth authItem = condition.getAuthItem();
//                        ps.setString(++parameterIndex, authItem.getId());
//                        ps.setString(++parameterIndex, authItem.getAuthType());
//                        ps.setString(++parameterIndex, authItem.getSystemId());
//                    }
//                    
//                    @Override
//                    public int getBatchSize() {
//                        return authItemRefImplList.size();
//                    }
//                });
//    }
//    
//    /**
//     * @param params
//     * @return
//     */
//    @Override
//    public List<AuthItemRef> queryAuthItemRefImplList(
//            final Map<String, Object> params, String tableSuffix) {
//        StringBuilder sb = new StringBuilder(TxConstants.INITIAL_STR_LENGTH);
//        sb.append("SELECT ");
//        sb.append("ID,");
//        sb.append("REFID,");
//        sb.append("AUTHREFTYPE,");
//        sb.append("AUTHID,");
//        sb.append("SYSTEMID,");
//        sb.append("AUTHTYPE,");
//        sb.append("TEMP,");
//        sb.append("CREATEOPERID,");
//        sb.append("EFFECTIVEDATE,");
//        sb.append("INVALIDDATE,");
//        sb.append("ENDDATE,");
//        sb.append("CREATEDATE");
//        sb.append(" FROM AUTH_AUTHREF").append(tableSuffix).append(" TAIRI");
//        
//        StringBuilder conditionSb = new StringBuilder(
//                TxConstants.INITIAL_STR_LENGTH);
//        if (!ObjectUtils.isEmpty(params.get("temp"))) {
//            conditionSb.append(" AND TAIRI.TEMP = ?");
//        }
//        if (!ObjectUtils.isEmpty(params.get("id"))) {
//            conditionSb.append(" AND TAIRI.ID = ?");
//        }
//        if (!ObjectUtils.isEmpty(params.get("authType"))) {
//            conditionSb.append(" AND TAIRI.AUTHTYPE = ?");
//        }
//        if (!ObjectUtils.isEmpty(params.get("systemId"))) {
//            conditionSb.append(" AND TAIRI.SYSTEMID = ?");
//        }
//        if (!ObjectUtils.isEmpty(params.get("authItemId"))) {
//            conditionSb.append(" AND TAIRI.AUTHID = ?");
//        }
//        if (!ObjectUtils.isEmpty(params.get("refType2RefIdMap"))) {
//            conditionSb.append(" AND (");
//            @SuppressWarnings("unchecked")
//            MultiValueMap<String, String> refType2RefIdMap = (MultiValueMap<String, String>) params.get("refType2RefIdMap");
//            int entryIndex = 0;
//            for (Entry<String, List<String>> entryTemp : refType2RefIdMap.entrySet()) {
//                if (entryIndex > 0) {
//                    conditionSb.append(" OR ");
//                }
//                if(entryTemp.getValue().size() > 1){
//                    conditionSb.append(" (TAIRI.AUTHREFTYPE = ? AND TAIRI.REFID IN ( ");
//                    for(@SuppressWarnings("unused") String refId : entryTemp.getValue()){
//                        conditionSb.append(" ? ,");
//                    }
//                    conditionSb.deleteCharAt(conditionSb.length() - 1);
//                    conditionSb.append(" ))");
//                }else{
//                    conditionSb.append(" (TAIRI.AUTHREFTYPE = ? AND TAIRI.REFID = ?) ");
//                }
//                entryIndex++;
//            }
//            conditionSb.append(" ) ");
//        }
//        if (!ObjectUtils.isEmpty(params.get("authRefType"))) {
//            conditionSb.append(" AND TAIRI.AUTHREFTYPE = ?");
//        }
//        if (!ObjectUtils.isEmpty(params.get("refId"))) {
//            conditionSb.append(" AND TAIRI.REFID = ?");
//        }
//        if (params.get("authItem") != null) {
//            Auth authItem = (Auth) params.get("authItem");
//            if (!ObjectUtils.isEmpty(authItem.getId())) {
//                conditionSb.append(" AND TAIRI.AUTHID = ?");
//            }
//            if (!ObjectUtils.isEmpty(authItem.getSystemId())) {
//                conditionSb.append(" AND TAIRI.SYSTEMID = ?");
//            }
//            if (!ObjectUtils.isEmpty(authItem.getAuthType())) {
//                conditionSb.append(" AND TAIRI.AUTHTYPE = ?");
//            }
//        }
//        
//        if (!StringUtils.isEmpty(conditionSb)) {
//            sb.append(" WHERE ").append(conditionSb.substring(4));
//        }
//        
//        List<AuthItemRef> authItemRefList = this.jdbcTemplate.query(sb.toString(),
//                new PreparedStatementSetter() {
//                    
//                    @Override
//                    public void setValues(PreparedStatement ps)
//                            throws SQLException {
//                        int parameterIndex = 0;
//                        if (!ObjectUtils.isEmpty(params.get("temp"))) {
//                            ps.setBoolean(++parameterIndex,
//                                    (Boolean) params.get("temp"));
//                        }
//                        if (!ObjectUtils.isEmpty(params.get("id"))) {
//                            ps.setString(++parameterIndex,
//                                    (String) params.get("id"));
//                        }
//                        if (!ObjectUtils.isEmpty(params.get("authType"))) {
//                            ps.setString(++parameterIndex,
//                                    (String) params.get("authType"));
//                        }
//                        if (!ObjectUtils.isEmpty(params.get("systemId"))) {
//                            ps.setString(++parameterIndex,
//                                    (String) params.get("systemId"));
//                        }
//                        if (!ObjectUtils.isEmpty(params.get("authItemId"))) {
//                            ps.setString(++parameterIndex,
//                                    (String) params.get("authItemId"));
//                        }
//                        if (!ObjectUtils.isEmpty(params.get("refType2RefIdMap"))) {
//                            @SuppressWarnings("unchecked")
//                            MultiValueMap<String, String> refType2RefIdMap = (MultiValueMap<String, String>) params.get("refType2RefIdMap");
//                            for (Entry<String, List<String>> entryTemp : refType2RefIdMap.entrySet()) {
//                                ps.setString(++parameterIndex,
//                                        entryTemp.getKey());
//                                for(String valueTemp  : entryTemp.getValue()){
//                                    ps.setString(++parameterIndex,
//                                            valueTemp);
//                                }
//                            }
//                        }
//                        if (!ObjectUtils.isEmpty(params.get("authRefType"))) {
//                            ps.setString(++parameterIndex,
//                                    (String) params.get("authRefType"));
//                        }
//                        if (!ObjectUtils.isEmpty(params.get("refId"))) {
//                            ps.setString(++parameterIndex,
//                                    (String) params.get("refId"));
//                        }
//                        if (params.get("authItem") != null) {
//                            Auth authItem = (Auth) params.get("authItem");
//                            if (!ObjectUtils.isEmpty(authItem.getId())) {
//                                ps.setString(++parameterIndex, authItem.getId());
//                            }
//                            if (!ObjectUtils.isEmpty(authItem.getSystemId())) {
//                                ps.setString(++parameterIndex,
//                                        authItem.getSystemId());
//                            }
//                            if (!ObjectUtils.isEmpty(authItem.getAuthType())) {
//                                ps.setString(++parameterIndex,
//                                        authItem.getAuthType());
//                            }
//                        }
//                    }
//                },
//                authItemRefImplRowMapper);
//        
//        return authItemRefList;
//    }
//    
//    /**
//     * @param updateRowMap
//     * @param tableSuffix
//     * @return
//     */
//    @Override
//    public int updateAuthItemRefImpl(Map<String, Object> updateRowMap,
//            String tableSuffix) {
//        /*
//        UPDATE AUTH_AUTHREF${tableSuffix} TAIRI
//        <trim prefix="SET" suffixOverrides=",">
//            <if test="_parameter.containsKey('validDependEndDate')">
//                VALIDDEPENDENDDATE = #{validDependEndDate,javaType=boolean},
//            </if>
//            <if test="_parameter.containsKey('endDate')">
//                ENDDATE = #{endDate,javaType=java.util.Date},
//            </if>
//        </trim>
//        WHERE
//        <trim prefixOverrides="AND | OR">
//            <if test="@com.tx.core.util.OgnlUtils@isNotEmpty(authItemRef.id)">  
//                AND TAIRI.id = #{authItemRef.refId}
//            </if>
//            <if test="@com.tx.core.util.OgnlUtils@isEmpty(authItemRef.id)">  
//                AND TAIRI.REFID = #{authItemRef.refId}
//                AND TAIRI.AUTHREFTYPE = #{authItemRef.authRefType} 
//                AND TAIRI.AUTHID = #{authItemRef.authItem.id}
//                AND TAIRI.SYSTEMID = #{authItemRef.authItem.systemId}
//                AND TAIRI.AUTHTYPE = #{authItemRef.authItem.authType}
//            </if>
//        </trim>
//        */
//        throw new SILException("暂不支持的功能，未来如果需要更新权限引用时，再进行添加：如：更新权限引用的过期时间。");
//    }
//    
//    /**
//     * 权限引用实例与查询结果映射关系
//     */
//    private static final RowMapper<AuthItemRef> authItemRefImplRowMapper = new RowMapper<AuthItemRef>() {
//        
//        /**
//         * @param rs
//         * @param rowNum
//         * @return
//         * @throws SQLException
//         */
//        @Override
//        public AuthItemRef mapRow(ResultSet rs, int rowNum)
//                throws SQLException {
//            AuthItemRef res = new AuthItemRef();
//            res.setId(rs.getString("ID"));
//            String authItemId = rs.getString("AUTHID");
//            String systemId = rs.getString("SYSTEMID");
//            String authType = rs.getString("AUTHTYPE");
//            res.setAuthItem(new AuthItem(authItemId, systemId, authType));
//            res.setAuthRefType(rs.getString("AUTHREFTYPE"));
//            res.setCreateOperId(rs.getString("CREATEOPERID"));
//            res.setRefId(rs.getString("REFID"));
//            res.setTemp(rs.getBoolean("TEMP"));
//            res.setEndDate(rs.getTimestamp("ENDDATE"));
//            res.setCreateDate(rs.getTimestamp("CREATEDATE"));
//            res.setEffectiveDate(rs.getTimestamp("EFFECTIVEDATE"));
//            res.setInvalidDate(rs.getTimestamp("INVALIDDATE"));
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
