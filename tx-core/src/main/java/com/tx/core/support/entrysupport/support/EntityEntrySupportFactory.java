/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2016年8月8日
 * <修改描述:>
 */
package com.tx.core.support.entrysupport.support;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.support.entrysupport.model.EntityEntry;

/**
 * 实体分项属性支撑类工对应的工厂类<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2016年8月8日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class EntityEntrySupportFactory {
    
    /** 数据源与工厂类的映射关系 */
    private final static Map<DataSource, NamedParameterJdbcTemplate> dataSource2namedParameterJdbcTemplateMap = new HashMap<>();
    
    @SuppressWarnings("rawtypes")
    private final static Map<String, EntityEntrySupport> type2supportMap = new HashMap<>();
    
    /**
      * 获取类型对应的EntityEntrySupport<br/>
      * <功能详细描述>
      * @param type
      * @param tableName
      * @return [参数说明]
      * 
      * @return EntityEntrySupport<ENTRY> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    @SuppressWarnings("unchecked")
    public static <ENTRY extends EntityEntry> EntityEntrySupport<ENTRY> getSupport(
            Class<ENTRY> type, String tableName, DataSource dataSource) {
        AssertUtils.notEmpty(tableName, "tableName is empty.");
        AssertUtils.notNull(type, "type is null.");
        
        tableName = tableName.toLowerCase();//将表名toLowerCase
        
        EntityEntrySupport<ENTRY> support = null;
        if (type2supportMap.containsKey(tableName)) {
            support = type2supportMap.get(tableName);
            
            return support;
        }
        
        synchronized (type2supportMap) {
            NamedParameterJdbcTemplate jdbcTemplate = null;
            if (dataSource2namedParameterJdbcTemplateMap.containsKey(dataSource)) {
                jdbcTemplate = dataSource2namedParameterJdbcTemplateMap.get(dataSource);
            } else {
                AssertUtils.notNull(dataSource, "dataSource is null.");
                
                jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
                dataSource2namedParameterJdbcTemplateMap.put(dataSource,
                        jdbcTemplate);
            }
            
            support = new EntityEntrySupport<>(type, tableName, jdbcTemplate);
            type2supportMap.put(tableName, support);
        }
        return support;
    }
    
    /**
     * 获取类型对应的EntityEntrySupport<br/>
     * <功能详细描述>
     * @param type
     * @param tableName
     * @return [参数说明]
     * 
     * @return EntityEntrySupport<ENTRY> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    public static EntityEntrySupport<EntityEntry> getSupport(String tableName,
            DataSource dataSource) {
        EntityEntrySupport<EntityEntry> support = getSupport(EntityEntry.class,
                tableName,
                dataSource);
        return support;
    }
}
