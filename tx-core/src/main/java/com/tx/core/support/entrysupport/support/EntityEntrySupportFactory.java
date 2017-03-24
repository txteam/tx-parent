/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2016年8月8日
 * <修改描述:>
 */
package com.tx.core.support.entrysupport.support;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.collections.map.HashedMap;
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
            if (dataSource2namedParameterJdbcTemplateMap
                    .containsKey(dataSource)) {
                jdbcTemplate = dataSource2namedParameterJdbcTemplateMap
                        .get(dataSource);
            } else {
                AssertUtils.notNull(dataSource, "dataSource is null.");
                
                jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
                dataSource2namedParameterJdbcTemplateMap.put(dataSource,
                        jdbcTemplate);
            }
            
            support = new EntityEntrySupport<>(type, tableName, jdbcTemplate);
            //验证表是否存在，若不存在则创建表
            Connection connection = null;
            try {
                connection = dataSource.getConnection();
                DatabaseMetaData databaseMetaData = connection.getMetaData();
                String dataBaseName = connection.getCatalog();
                ResultSet rsTables = databaseMetaData.getTables(dataBaseName,
                        null,
                        tableName,
                        new String[] { "TABLE" });
                if (!rsTables.next()) {
                    //创建表
                    String CREATE_SQL_TEMPLATE = (new StringBuilder(
                            "CREATE TABLE  ")).append(tableName )
                                    .append(" ( id varchar(64) not null, ")
                                    .append(" entityId varchar(64) not null,")
                                    .append(" entryKey varchar(64) not null,")
                                    .append(" entryValue varchar(256),")
                                    .append("  primary key(id))")
                                    .toString();
                    
                    Map<String, Object> params = new HashedMap();
                    jdbcTemplate.update(CREATE_SQL_TEMPLATE, params);
                }
                
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    try {
                        connection.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
            
            type2supportMap.put(tableName, support);
        }
        return support;
    }
    
    /**
     * 获取类型对应的EntityEntrySupport<br/>
     * <功能详细描述>
     * @param tableName
     * @param dataSource
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
