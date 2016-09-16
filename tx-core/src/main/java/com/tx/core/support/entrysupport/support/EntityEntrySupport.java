/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2016年8月7日
 * <修改描述:>
 */
package com.tx.core.support.entrysupport.support;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.support.entrysupport.model.EntityEntry;

/**
 * 实体分项属性支撑类<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2016年8月7日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class EntityEntrySupport<EE extends EntityEntry> implements
        InitializingBean {
    
    /** 类型:type */
    private Class<EE> type;
    
    /** 设定的类型表名 */
    private String tableName;
   
    /** metaEntityEntry:反射实例 */
    private MetaEntityEntry metaEntityEntry;
    
    /** 数据源 */
    private DataSource dataSource;
    
    /** namedParameterJdbcTemplate实例 */
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    
    /**
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        init();
    }
    
    /**
      * 初始化EntityEntrySupport<br/>
      * <功能详细描述> [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public void init() {
        AssertUtils.notNull(type, "type is null.");//类型不能为空
        
        //初始化jdbcTemplate
        if (this.namedParameterJdbcTemplate == null) {
            AssertUtils.notNull(this.dataSource,
                    "jdbcTemplate And dataSource is all null.");
            this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(
                    this.dataSource);
        }
        
        //获取对应的MetaEntityEntry
        this.metaEntityEntry = MetaEntityEntry.forClass(this.type, this.tableName);
    }
    
    /**
      * 保存Entry实例<br/>
      * <功能详细描述>
      * @param entry [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    @Transactional
    private void save(EE entry) {
        AssertUtils.notNull(entry, "entry is null.");
        AssertUtils.notEmpty(entry.getEntityId(), "entry.entityId is empty.");
        AssertUtils.notEmpty(entry.getEntryKey(), "entry.entryKey is empty.");
        
        EE dbEntry = findEntryByEntryKey(entry.getEntityId(),
                entry.getEntryKey());
        if (dbEntry == null) {
            insert(dbEntry);
        } else {
            updateByEntryKey(entry);
        }
    }
    
    /**
      * 批量保存Entry实例<br/>
      * <功能详细描述>
      * @param entityId
      * @param entryList [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    @Transactional
    public void batchSave(String entityId, List<EE> entryList) {
        if (CollectionUtils.isEmpty(entryList)) {
            return;
        }
        AssertUtils.notEmpty(entityId, "entityId is empty.");
        
        Map<String, EE> entryMap = loadEntryMapByEntityId(entityId);
    }
    
    /**
     * 批量插入实体<br/>
     * <功能详细描述>
     * @param entityId
     * @param entryList [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    @Transactional
    private void insert(EE entry) {
        AssertUtils.notNull(entry, "entry is null.");
        AssertUtils.notEmpty(entry.getEntityId(), "entry.entityId is empty.");
        AssertUtils.notEmpty(entry.getEntryKey(), "entry.entryKey is empty.");
        
        //转换为Map数组
        Map<String, ?> entryMap = this.metaEntityEntry.transferBean2Map(entry);
        
        //批量处理
        this.namedParameterJdbcTemplate.update(this.metaEntityEntry.getSqlOfInsert(),
                entryMap);
    }
    
    /**
      * 批量插入实体<br/>
      * <功能详细描述>
      * @param entityId
      * @param entryList [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    @Transactional
    private void batchInsert(String entityId, List<EE> entryList) {
        if (CollectionUtils.isEmpty(entryList)) {
            return;
        }
        AssertUtils.notEmpty(entityId, "entityId is empty.");
        
        //转换为Map数组
        @SuppressWarnings("unchecked")
        Map<String, ?>[] entryMapArray = new Map[entryList.size()];
        for (int i = 0; i < entryList.size(); i++) {
            EE entry = entryList.get(i);
            entry.setEntityId(entityId);
            AssertUtils.notEmpty(entry.getEntryKey(),
                    "entry.entryKey is empty.");
            
            entryMapArray[i] = this.metaEntityEntry.transferBean2Map(entry);
        }
        
        //批量处理
        this.namedParameterJdbcTemplate.batchUpdate(this.metaEntityEntry.getSqlOfInsert(),
                entryMapArray);
    }
    
    /**
      * 根据Entry.id获取Entry实例<br/>
      * <功能详细描述>
      * @param id
      * @return [参数说明]
      * 
      * @return EE [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public EE findEntryById(String id) {
        AssertUtils.notEmpty(id, "id is empty.");
        
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("id", id);
        
        @SuppressWarnings("unchecked")
        EE res = this.namedParameterJdbcTemplate.queryForObject(this.metaEntityEntry.getSqlOfFindById(),
                paramMap,
                (RowMapper<EE>) this.metaEntityEntry.getRowMap());
        
        return res;
    }
    
    /**
      * 根据Entry.entityId && Entry.entryKey 获取Entry实例<br/>
      * <功能详细描述>
      * @param entityId
      * @param entryKey
      * @return [参数说明]
      * 
      * @return EE [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public EE findEntryByEntryKey(String entityId, String entryKey) {
        AssertUtils.notEmpty(entityId, "entityId is empty.");
        AssertUtils.notEmpty(entryKey, "entryKey is empty.");
        
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("entityId", entityId);
        paramMap.put("entryKey", entryKey);
        
        @SuppressWarnings("unchecked")
        EE res = this.namedParameterJdbcTemplate.queryForObject(this.metaEntityEntry.getSqlOfFindByEntryKey(),
                paramMap,
                (RowMapper<EE>) this.metaEntityEntry.getRowMap());
        
        return res;
    }
    
    /**
      * 根据实体Id查询分项列表<br/>
      * <功能详细描述>
      * @param entityId
      * @return [参数说明]
      * 
      * @return List<EE> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public List<EE> queryEntryListByEntityId(String entityId) {
        AssertUtils.notEmpty(entityId, "entityId is empty.");
        
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("entityId", entityId);
        //this.namedParameterJdbcTemplate.batchUpdate(sql, batchValues);
        
        @SuppressWarnings("unchecked")
        List<EE> resList = this.namedParameterJdbcTemplate.query(this.metaEntityEntry.getSqlOfQueryListByEntityId(),
                paramMap,
                (RowMapper<EE>) this.metaEntityEntry.getRowMap());
        
        return resList;
    }
    
    /**
     * 批量插入实体<br/>
     * <功能详细描述>
     * @param entityId
     * @param entryList [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    @Transactional
    private void updateById(EE entry) {
        AssertUtils.notNull(entry, "entry is null.");
        AssertUtils.notEmpty(entry.getId(), "entry.id is empty.");
        
        //转换为Map数组
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("id", entry.getId());
        
        //批量处理
        this.namedParameterJdbcTemplate.update(this.metaEntityEntry.getSqlOfUpdateById(),
                paramMap);
    }
    
    /**
     * 批量插入实体<br/>
     * <功能详细描述>
     * @param entityId
     * @param entryList [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    @Transactional
    private void updateByEntryKey(EE entry) {
        AssertUtils.notNull(entry, "entry is null.");
        AssertUtils.notEmpty(entry.getEntityId(), "entry.entityId is empty.");
        AssertUtils.notEmpty(entry.getEntryKey(), "entry.entryKey is empty.");
        
        //转换为Map数组
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("entityId", entry.getEntityId());
        paramMap.put("entryKey", entry.getEntryKey());
        
        //批量处理
        this.namedParameterJdbcTemplate.update(this.metaEntityEntry.getSqlOfUpdateByEntryKey(),
                paramMap);
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
    private Map<String, EE> loadEntryMapByEntityId(String entityId) {
        List<EE> entryList = queryEntryListByEntityId(entityId);//查询实体对象列表
        
        //将对象加载Map中
        Map<String, EE> sourceMap = new HashMap<>();
        if (CollectionUtils.isEmpty(entryList)) {
            return sourceMap;
        }
        for (EE entryTemp : entryList) {
            sourceMap.put(entryTemp.getEntryKey(), entryTemp);
        }
        return sourceMap;
    }
    
    /**
     * @param 对type进行赋值
     */
    public void setType(Class<EE> type) {
        this.type = type;
    }
    
    /**
     * @param 对dataSource进行赋值
     */
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    
    /**
     * @param 对jdbcTemplate进行赋值
     */
    public void setNamedParameterJdbcTemplate(
            NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }
    
    /**
     * @param 对tableName进行赋值
     */
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
}
