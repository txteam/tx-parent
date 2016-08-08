/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2016年8月7日
 * <修改描述:>
 */
package com.tx.core.entityentry.support;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import com.tx.core.entityentry.model.EntityEntry;

/**
 * 实体分项属性支撑类<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2016年8月7日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class EntityEntrySupport<E extends EntityEntry> {
    
    //实体分项基本字段
    private static final Set<String> entityEntryUCFieldNames = new HashSet<>(
            Arrays.asList("id".toUpperCase(),
                    "entityId".toUpperCase(),
                    "entryKey".toUpperCase(),
                    "entryValue".toUpperCase()));
    
    //插入语句模板
    //"INSERT INTO {} (id,entityId,entryKey,entryValue{}) values(?,?,?,?{})";
    private static final String INSERT_SQL_TEMPLATE = (new StringBuilder(
            "INSERT INTO ")).append(" {}")
            .append("(id,entityId,entryKey,entryValue{})")
            .append("values(?,?,?,?{})")
            .toString();
    
    private static final String update_sql_template = null;
    
    private JdbcTemplate jdbcTemplate;
    
    private Class<E> type;
    
    private String tableName;
    
    private String hisTableName;
    
    private String INSERT_SQL = "";
    
    private String UPDATE_SQL = "";
    
    private String DELETE_BY_ENTITYID_SQL = "";
    
    public void saveEntryList(List<E> entryList) {
        
    }
    
    public List<E> queryEntryListByEntityId(String entityId) {
        
        return null;
    }
    
    /**
     * 获取保存以前实体对象的映射<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return Map<PaymentOrderAttributeKeyEnum,PaymentOrderAttribute> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    private Map<String, E> loadBeforeEntryMap(String entityId) {
        List<E> entryList = queryEntryListByEntityId(entityId);//查询实体对象列表
        
        //将对象加载Map中
        Map<String, E> sourceMap = new HashMap<>();
        if (CollectionUtils.isEmpty(entryList)) {
            return sourceMap;
        }
        for (E entryTemp : entryList) {
            sourceMap.put(entryTemp.getEntryKey(), entryTemp);
        }
        return sourceMap;
    }
    
    /**
     * 将paymentOrderAttribute实例插入数据库中保存<br />
     * 1、如果paymentOrderAttribute为空时抛出参数为空异常<br />
     * 2、如果paymentOrderAttribute中部分必要参数为非法值时抛出参数不合法异常<br />
     *
     * <功能详细描述>
     * 
     * @param district [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws
     * @see [类、类#方法、类#成员]
     */
    @Transactional
    private void batchInsert(String entityId, List<E> entryList) {
        if (CollectionUtils.isEmpty(entryList)) {
            return;
        }
        //批量执行以前校验从参数合法性
        //validateBeforeBatchExecute(entityId, entryList);
        
        //this.paymentOrderAttributeDao.batchInsertPaymentOrderAttribute(poas);
    }
}
