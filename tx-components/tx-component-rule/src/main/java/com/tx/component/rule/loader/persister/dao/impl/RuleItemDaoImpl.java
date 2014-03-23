/*
0 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2014-3-18
 * <修改描述:>
 */
package com.tx.component.rule.loader.persister.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.EnumUtils;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;

import com.tx.component.rule.loader.RuleItem;
import com.tx.component.rule.loader.RuleStateEnum;
import com.tx.component.rule.loader.RuleTypeEnum;
import com.tx.component.rule.loader.persister.dao.RuleItemDao;
import com.tx.core.TxConstants;
import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.util.ObjectUtils;

/**
 * 规则项目持久层<br/>
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2014-3-18]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class RuleItemDaoImpl implements RuleItemDao {
    
    private JdbcTemplate jdbcTemplate;
    
    /** <默认构造函数> */
    public RuleItemDaoImpl(JdbcTemplate jdbcTemplate) {
        super();
        AssertUtils.notNull(jdbcTemplate == null, "jdbcTemplate is null.");
        this.jdbcTemplate = jdbcTemplate;
    }
    
    /**
     * @param condition
     */
    @Override
    public void insertRuleItem(final RuleItem condition) {
        AssertUtils.notNull(condition, "condition is null");
        
        StringBuilder sb = new StringBuilder(TxConstants.INITIAL_STR_LENGTH);
        sb.append("INSERT INTO RULE_RULEITEM( ");
        sb.append("ruleKey,");
        sb.append("name,");
        sb.append("serviceType,");
        sb.append("ruleType,");
        sb.append("state,");
        sb.append("createDate,");
        sb.append("lastUpdateDate,");
        sb.append("remark");
        sb.append(") VALUES(?,?,?,?,?,?,?,?)");
        
        this.jdbcTemplate.update(sb.toString(), new PreparedStatementSetter() {
            
            /**
             * @param ps
             * @throws SQLException
             */
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                int parameterIndex = 0;
                ps.setString(++parameterIndex, condition.getKey());
                ps.setString(++parameterIndex, condition.getName());
                ps.setString(++parameterIndex, condition.getServiceType());
                ps.setString(++parameterIndex, condition.getRuleType()
                        .toString());
                ps.setString(++parameterIndex, condition.getState().toString());
                ps.setTimestamp(++parameterIndex,
                        condition.getCreateDate() == null ? null
                                : new Timestamp(condition.getCreateDate()
                                        .getTime()));
                ps.setTimestamp(++parameterIndex,
                        condition.getLastUpdateDate() == null ? null
                                : new Timestamp(condition.getLastUpdateDate()
                                        .getTime()));
                ps.setString(++parameterIndex, condition.getKey());
            }
        });
    }
    
    /**
     * @param condition
     * @return
     */
    @Override
    public int deleteRuleItem(final RuleItem condition) {
        AssertUtils.notNull(condition, "condition is null");
        AssertUtils.notEmpty(condition.getKey(), "condition.key is null");
        
        StringBuilder sb = new StringBuilder(TxConstants.INITIAL_STR_LENGTH);
        sb.append("DELETE FROM RULE_RULEITEM WHERE ruleKey = ?");
        
        int resInt = this.jdbcTemplate.update(sb.toString(),
                new PreparedStatementSetter() {
                    
                    @Override
                    public void setValues(PreparedStatement ps)
                            throws SQLException {
                        int parameterIndex = 0;
                        ps.setString(++parameterIndex, condition.getKey());
                    }
                });
        return resInt;
    }
    
    /**
     * @param condition
     * @return
     */
    @Override
    public RuleItem findRuleItem(RuleItem condition) {
        AssertUtils.notNull(condition, "condition is null");
        AssertUtils.notEmpty(condition.getKey(), "condition.key is null");
        
        StringBuilder sb = new StringBuilder(TxConstants.INITIAL_STR_LENGTH);
        sb.append("SELECT ");
        sb.append("ruleKey,");
        sb.append("name,");
        sb.append("serviceType,");
        sb.append("ruleType,");
        sb.append("state,");
        sb.append("createDate,");
        sb.append("lastUpdateDate,");
        sb.append("remark");
        sb.append(" FROM RULE_RULEITEM");
        sb.append(" WHERE ruleKey = ?");
        List<RuleItem> resList = this.jdbcTemplate.query(sb.toString(),
                new Object[] { condition.getKey() },
                ruleItemRowMapper);
        RuleItem res = DataAccessUtils.singleResult(resList);
        return res;
    }
    
    /**
     * @param params
     * @return
     */
    @Override
    public List<RuleItem> queryRuleItemList(final Map<String, Object> params) {
        StringBuilder sb = new StringBuilder(TxConstants.INITIAL_STR_LENGTH);
        sb.append("SELECT ");
        sb.append("ruleKey,");
        sb.append("name,");
        sb.append("serviceType,");
        sb.append("ruleType,");
        sb.append("state,");
        sb.append("createDate,");
        sb.append("lastUpdateDate,");
        sb.append("remark");
        sb.append(" FROM RULE_RULEITEM ");
        
        StringBuilder conditionSb = new StringBuilder(
                TxConstants.INITIAL_STR_LENGTH);
        if (params != null && !ObjectUtils.isEmpty(params.get("key"))) {
            conditionSb.append("AND ruleKey = ? ");
        }
        if (params != null && !ObjectUtils.isEmpty(params.get("serviceType"))) {
            conditionSb.append("AND serviceType = ? ");
        }
        if (params != null && !ObjectUtils.isEmpty(params.get("state"))) {
            conditionSb.append("AND state = ? ");
        }
        if (conditionSb.length() > 0) {
            sb.append(" WHERE ").append(conditionSb.substring(3));
        }
        List<RuleItem> resList = this.jdbcTemplate.query(sb.toString(),
                new PreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps)
                            throws SQLException {
                        int parameterIndex = 0;
                        if (params != null && !ObjectUtils.isEmpty(params.get("ruleKey"))) {
                            ps.setString(++parameterIndex,
                                    (String) params.get("ruleKey"));
                        }
                        if (params != null && !ObjectUtils.isEmpty(params.get("serviceType"))) {
                            ps.setString(++parameterIndex,
                                    (String) params.get("serviceType"));
                        }
                        if (params != null && !ObjectUtils.isEmpty(params.get("state"))) {
                            ps.setString(++parameterIndex,
                                    ((RuleStateEnum) params.get("state")).toString());
                        }
                        
                    }
                },
                ruleItemRowMapper);
        return resList;
    }
    
    /**
     * @param updateRowMap
     * @return
     */
    @Override
    public int updateRuleItem(final Map<String, Object> updateRowMap) {
        AssertUtils.notNull(updateRowMap, "updateRowMap is null.");
        AssertUtils.notEmpty(updateRowMap.get("key"),
                "updateRowMap.key is null.");
        
        StringBuilder sb = new StringBuilder(TxConstants.INITIAL_STR_LENGTH);
        sb.append("UPDATE RULE_RULEITEM SET ");
        sb.append(" lastUpdateDate = ?");
        if (!ObjectUtils.isEmpty(updateRowMap.get("name"))) {
            sb.append(" ,name = ? ");
        }
        if (!ObjectUtils.isEmpty(updateRowMap.get("serviceType"))) {
            sb.append(" ,serviceType = ? ");
        }
        if (!ObjectUtils.isEmpty(updateRowMap.get("state"))) {
            sb.append(" ,state = ? ");
        }
        if (!ObjectUtils.isEmpty(updateRowMap.get("remark"))) {
            sb.append(" ,remark = ? ");
        }
        sb.append(" WHERE ruleKey = ?");
        
        int resInt = this.jdbcTemplate.update(sb.toString(),
                new PreparedStatementSetter() {
                    
                    @Override
                    public void setValues(PreparedStatement ps)
                            throws SQLException {
                        int parameterIndex = 0;
                        ps.setTimestamp(++parameterIndex, new Timestamp(
                                (new Date()).getTime()));
                        if (!ObjectUtils.isEmpty(updateRowMap.get("name"))) {
                            ps.setString(++parameterIndex,
                                    (String) updateRowMap.get("name"));
                        }
                        if (!ObjectUtils.isEmpty(updateRowMap.get("serviceType"))) {
                            ps.setString(++parameterIndex,
                                    (String) updateRowMap.get("serviceType"));
                        }
                        if (!ObjectUtils.isEmpty(updateRowMap.get("state"))) {
                            ps.setString(++parameterIndex,
                                    ((RuleStateEnum) updateRowMap.get("state")).toString());
                        }
                        if (!ObjectUtils.isEmpty(updateRowMap.get("remark"))) {
                            ps.setString(++parameterIndex,
                                    (String) updateRowMap.get("remark"));
                        }
                        ps.setString(++parameterIndex,
                                (String) updateRowMap.get("key"));
                    }
                });
        return resInt;
    }
    
    /** ruleMapper:RowMapper实现 */
    private static RowMapper<RuleItem> ruleItemRowMapper = new RowMapper<RuleItem>() {
        @Override
        public RuleItem mapRow(ResultSet rs, int rowNum) throws SQLException {
            RuleItem ruleItem = new RuleItem();
            ruleItem.setCreateDate(rs.getTimestamp("createDate"));
            ruleItem.setLastUpdateDate(rs.getTimestamp("lastUpdateDate"));
            ruleItem.setKey(rs.getString("ruleKey"));
            ruleItem.setModifyAble(true);
            ruleItem.setName(rs.getString("name"));
            ruleItem.setRemark(rs.getString("remark"));
            ruleItem.setServiceType(rs.getString("serviceType"));
            ruleItem.setRuleType(EnumUtils.getEnum(RuleTypeEnum.class,
                    rs.getString("state")));
            return ruleItem;
        }
    };
}
