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
    
    private Class<EE> type;
    
    private MetaEntityEntry metaEntityEntry;
    
    private DataSource dataSource;
    
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
        
        //jpaMetaClass.getGetterNames();
    }
    
    public void batchSave(String entityId, List<EE> entryList) {
        Map<String, EE> entryMap = loadEntryMapByEntityId(entityId);
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
    private void batchInsert(String entityId, List<EE> entryList) {
        if (CollectionUtils.isEmpty(entryList)) {
            return;
        }
        //转换为Map数组
        @SuppressWarnings("unchecked")
        Map<String, ?>[] entryMapArray = new Map[entryList.size()];
        for (int i = 0; i < entryList.size(); i++) {
            entryMapArray[i] = new HashMap<>();
        }
        //批量处理
        this.namedParameterJdbcTemplate.batchUpdate(this.metaEntityEntry.getSqlOfInsert(),
                entryMapArray);
    }
    
    public int count(Map<String, Object> params) {
        
        return 1;
    }
    
    public List<EE> queryEntryListByEntityId(String entityId) {
        //this.namedParameterJdbcTemplate.batchUpdate(sql, batchValues);
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
}
