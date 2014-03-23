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
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;

import com.tx.component.rule.loader.RuleItemValueParam;
import com.tx.component.rule.loader.persister.dao.RuleItemValueParamDao;
import com.tx.core.TxConstants;
import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.util.ObjectUtils;

/**
 * 规则项值类型参数持久器<br/>
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2014-3-20]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class RuleItemValueParamDaoImpl implements RuleItemValueParamDao {
    
    private JdbcTemplate jdbcTemplate;
    
    /** <默认构造函数> */
    public RuleItemValueParamDaoImpl(JdbcTemplate jdbcTemplate) {
        super();
        AssertUtils.notNull(jdbcTemplate == null, "jdbcTemplate is null.");
        this.jdbcTemplate = jdbcTemplate;
    }
    
    /**
     * @param condition
     */
    @Override
    public void batchInsertRuleItemValueParam(final List<RuleItemValueParam> condition) {
        if(CollectionUtils.isEmpty(condition)){
            return ;
        }
        StringBuilder sb = new StringBuilder(TxConstants.INITIAL_STR_LENGTH);
        sb.append("INSERT INTO RULE_VALUE_PARAM (");
        sb.append("ruleKey,");
        sb.append("paramKey,");
        sb.append("paramOrder,");
        sb.append("paramValue,");
        sb.append(") VALUES(?,?,?,?)");
        this.jdbcTemplate.batchUpdate(sb.toString(), new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int index) throws SQLException {
                RuleItemValueParam ruleItemValueParamTemp = condition.get(index);
                
                int parameterIndex = 0;
                ps.setString(++parameterIndex, ruleItemValueParamTemp.getRuleKey());
                ps.setString(++parameterIndex, ruleItemValueParamTemp.getParamKey());
                ps.setInt(++parameterIndex, ruleItemValueParamTemp.getParamOrder());
                ps.setString(++parameterIndex, ruleItemValueParamTemp.getParamValue());
            }
            @Override
            public int getBatchSize() {
                return condition.size();
            }
        });
    }
    
    /**
     * @param condition
     * @return
     */
    @Override
    public int deleteRuleItemValueParam(final RuleItemValueParam ruleItemValueParam) {
        AssertUtils.notNull(ruleItemValueParam,"ruleItemValueParam is null.");
        AssertUtils.notEmpty(ruleItemValueParam.getRuleKey(),"ruleItemValueParam.ruleKey is null.");
        
        StringBuilder sb = new StringBuilder(TxConstants.INITIAL_STR_LENGTH);
        sb.append("DELETE FROM RULE_VALUE_PARAM ");
        sb.append(" WHERE ruleKey = ? ");
        if(!ObjectUtils.isEmpty(ruleItemValueParam.getParamKey())){
            sb.append(" AND paramKey = ? ");
        }
        int resInt = this.jdbcTemplate.update(sb.toString(),new PreparedStatementSetter(){
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                int parameterIndex = 0;
                ps.setString(++parameterIndex, ruleItemValueParam.getRuleKey());
                if(!ObjectUtils.isEmpty(ruleItemValueParam.getParamKey())){
                    ps.setString(++parameterIndex, ruleItemValueParam.getParamKey());
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
    public List<RuleItemValueParam> queryRuleItemValueParamList(
            final Map<String, Object> params) {
        AssertUtils.notNull(params,"params is null.");
        AssertUtils.notEmpty(params.get("ruleKey"),"params.ruleKey is null.");
        
        StringBuilder sb = new StringBuilder(TxConstants.INITIAL_STR_LENGTH);
        sb.append("SELECT ");
        sb.append("ruleKey,");
        sb.append("paramKey,");
        sb.append("paramOrder,");
        sb.append("paramValue,");
        sb.append(" FROM RULE_VALUE_PARAM ");
        sb.append(" WHERE ruleKey = ?");
        List<RuleItemValueParam> resList = this.jdbcTemplate.query(sb.toString(), new PreparedStatementSetter(){
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                int parameterIndex = 0;
                ps.setString(++parameterIndex, (String)params.get("ruleKey"));
            }
        }, ruleItemValueParamRowMapper);
        return resList;
    }
    
    /**
     * 对象映射
     */
    private static final RowMapper<RuleItemValueParam> ruleItemValueParamRowMapper = new RowMapper<RuleItemValueParam>() {
        @Override
        public RuleItemValueParam mapRow(ResultSet rs, int rowNum)
                throws SQLException {
            RuleItemValueParam ruleItemValueParam = new RuleItemValueParam();
            ruleItemValueParam.setParamKey(rs.getString("paramKey"));
            ruleItemValueParam.setParamOrder(rs.getInt("paramOrder"));
            ruleItemValueParam.setParamValue(rs.getString("paramValue"));
            ruleItemValueParam.setRuleKey(rs.getString("ruleKey"));
            return ruleItemValueParam;
        }
        
    }; 
}
