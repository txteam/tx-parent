/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2014-3-20
 * <修改描述:>
 */
package com.tx.component.rule.loader.persister.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.AbstractLobCreatingPreparedStatementCallback;
import org.springframework.jdbc.support.lob.LobCreator;
import org.springframework.jdbc.support.lob.LobHandler;

import com.tx.component.rule.loader.RuleItemByteParam;
import com.tx.component.rule.loader.persister.dao.RuleItemByteParamDao;
import com.tx.core.TxConstants;
import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.util.ObjectUtils;

/**
 * 规则项的byte类型参数持久层逻辑<br/>
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2014-3-20]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class RuleItemByteParamDaoImpl implements RuleItemByteParamDao {
    
    private JdbcTemplate jdbcTemplate;
    
    private LobHandler lobHandler;
    
    private RowMapper<RuleItemByteParam> ruleItemByteParamRowMapper = null;
    
    /** <默认构造函数> */
    public RuleItemByteParamDaoImpl(JdbcTemplate jdbcTemplate,
            LobHandler lobHandler) {
        super();
        AssertUtils.notNull(jdbcTemplate == null, "jdbcTemplate is null.");
        AssertUtils.notNull(lobHandler == null, "lobHandler is null.");
        this.jdbcTemplate = jdbcTemplate;
        this.lobHandler = lobHandler;
        
        final LobHandler finalLobHandler = this.lobHandler;
        this.ruleItemByteParamRowMapper = new RowMapper<RuleItemByteParam>() {
            @Override
            public RuleItemByteParam mapRow(ResultSet rs, int rowNum)
                    throws SQLException {
                RuleItemByteParam ruleItemByteParam = new RuleItemByteParam();
                ruleItemByteParam.setParamKey(rs.getString("paramKey"));
                ruleItemByteParam.setParamOrder(rs.getInt("paramOrder"));
                byte[] attach = finalLobHandler.getBlobAsBytes(rs, "paramValue");
                ruleItemByteParam.setParamValue(attach);
                ruleItemByteParam.setRuleKey(rs.getString("ruleKey"));
                
                return ruleItemByteParam;
            }
        };
    }
    
    /**
     * @param condition
     */
    @Override
    public void batchInsertRuleItemByteParam(
            final List<RuleItemByteParam> condition) {
        if (CollectionUtils.isEmpty(condition)) {
            return;
        }
        StringBuilder sb = new StringBuilder(TxConstants.INITIAL_STR_LENGTH);
        sb.append("INSERT INTO RULE_BYTE_PARAM (");
        sb.append("ruleKey,");
        sb.append("paramKey,");
        sb.append("paramOrder,");
        sb.append("paramValue,");
        sb.append(") VALUES(?,?,?,?)");
        for (RuleItemByteParam ruleItemByteParamTemp : condition) {
            final RuleItemByteParam finalRuleItemByteParam = ruleItemByteParamTemp;
            this.jdbcTemplate.execute(sb.toString(),
                    new AbstractLobCreatingPreparedStatementCallback(
                            this.lobHandler) {
                        protected void setValues(PreparedStatement ps,
                                LobCreator lobCreator) throws SQLException {
                            int parameterIndex = 0;
                            ps.setString(++parameterIndex,
                                    finalRuleItemByteParam.getRuleKey());
                            ps.setString(++parameterIndex,
                                    finalRuleItemByteParam.getParamKey());
                            ps.setInt(++parameterIndex,
                                    finalRuleItemByteParam.getParamOrder());
                            lobCreator.setBlobAsBytes(ps,
                                    ++parameterIndex,
                                    finalRuleItemByteParam.getParamValue());
                        }
                    });
        }
        
    }
    
    /**
     * @param condition
     * @return
     */
    @Override
    public int deleteRuleItemByteParam(final RuleItemByteParam condition) {
        AssertUtils.notNull(condition, "ruleItemByteParam is null.");
        AssertUtils.notEmpty(condition.getRuleKey(),
                "ruleItemValueParam.ruleKey is null.");
        
        StringBuilder sb = new StringBuilder(TxConstants.INITIAL_STR_LENGTH);
        sb.append("DELETE FROM RULE_BYTE_PARAM ");
        sb.append(" WHERE ruleKey = ? ");
        if (!ObjectUtils.isEmpty(condition.getParamKey())) {
            sb.append(" AND paramKey = ? ");
        }
        int resInt = this.jdbcTemplate.update(sb.toString(),
                new PreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps)
                            throws SQLException {
                        int parameterIndex = 0;
                        ps.setString(++parameterIndex, condition.getRuleKey());
                        if (!ObjectUtils.isEmpty(condition.getParamKey())) {
                            ps.setString(++parameterIndex,
                                    condition.getParamKey());
                        }
                    }
                });
        return resInt;
    }
    
    /**
     * @param params
     * @return
     */
    @Override
    public List<RuleItemByteParam> queryRuleItemByteParamList(
            final Map<String, Object> params) {
        AssertUtils.notNull(params, "params is null.");
        AssertUtils.notEmpty(params.get("ruleKey"), "params.ruleKey is null.");
        
        StringBuilder sb = new StringBuilder(TxConstants.INITIAL_STR_LENGTH);
        sb.append("SELECT ");
        sb.append("ruleKey,");
        sb.append("paramKey,");
        sb.append("paramOrder,");
        sb.append("paramValue,");
        sb.append(" FROM RULE_BYTE_PARAM ");
        sb.append(" WHERE ruleKey = ?");
        List<RuleItemByteParam> resList = this.jdbcTemplate.query(sb.toString(),
                new PreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps)
                            throws SQLException {
                        int parameterIndex = 0;
                        ps.setString(++parameterIndex,
                                (String) params.get("ruleKey"));
                    }
                }, ruleItemByteParamRowMapper);
        return resList;
    }
    
}
