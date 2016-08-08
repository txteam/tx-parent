/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-8-27
 * <修改描述:>
 */
package com.tx.core.generator.model;

import java.sql.Types;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.dialect.Dialect;
import org.hibernate.dialect.MySQL5InnoDBDialect;

import com.tx.core.reflection.JpaColumnInfo;
import com.tx.core.reflection.JpaMetaClass;
import com.tx.core.util.JdbcUtils;

/**
 * 数据脚本映射<br/>
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2013-8-27]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class DBScriptMapper {
    
    /** 表名 */
    private String tableName;
    
    /** 主键字段名 */
    private String pkColumnName;
    
    /** 字段对应数据库类型 */
    private Map<String, String> columnName2TypeNameMapping = new LinkedHashMap<String, String>();
    
    /** 字段对应注释 */
    private Map<String, String> columnName2CommentMapping = new LinkedHashMap<String, String>();
    
    public DBScriptMapper() {
        super();
    }
    
    public DBScriptMapper(JpaMetaClass<?> jpaMetaClass, Dialect dialect){
        this(jpaMetaClass,dialect,true);
    }
    
    public DBScriptMapper(JpaMetaClass<?> jpaMetaClass, Dialect dialect,
            boolean nameToUpperCase) {
        super();
        if (nameToUpperCase) {
            this.tableName = jpaMetaClass.getTableName().toUpperCase();
            this.pkColumnName = jpaMetaClass.getGetter2columnInfoMapping()
                    .get(jpaMetaClass.getPkGetterName())
                    .getColumnName()
                    .toUpperCase();
        } else {
            this.tableName = jpaMetaClass.getTableName();
            this.pkColumnName = jpaMetaClass.getGetter2columnInfoMapping()
                    .get(jpaMetaClass.getPkGetterName())
                    .getColumnName();
        }
        
        Map<String, JpaColumnInfo> property2columnMap = jpaMetaClass.getGetter2columnInfoMapping();
        List<String> propertyList = new ArrayList<>(property2columnMap.keySet());
        
        for (String keyTemp : propertyList) {
            JpaColumnInfo columnInfo = property2columnMap.get(keyTemp);
            
            this.columnName2TypeNameMapping.put(columnInfo.getColumnName(),
                    dialect.getTypeName(JdbcUtils.getSqlTypeByJavaType(columnInfo.getRealGetterType()),
                            columnInfo.getLength(),
                            columnInfo.getPrecision(),
                            columnInfo.getScale()));
        }
    }
    
    /**
     * @return 返回 tableName
     */
    public String getTableName() {
        return tableName;
    }
    
    /**
     * @param 对tableName进行赋值
     */
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
    
    /**
     * @return 返回 pkColumnName
     */
    public String getPkColumnName() {
        return pkColumnName;
    }
    
    /**
     * @param 对pkColumnName进行赋值
     */
    public void setPkColumnName(String pkColumnName) {
        this.pkColumnName = pkColumnName;
    }
    
    /**
     * @return 返回 columnName2TypeNameMapping
     */
    public Map<String, String> getColumnName2TypeNameMapping() {
        return columnName2TypeNameMapping;
    }
    
    /**
     * @param 对columnName2TypeNameMapping进行赋值
     */
    public void setColumnName2TypeNameMapping(
            Map<String, String> columnName2TypeNameMapping) {
        this.columnName2TypeNameMapping = columnName2TypeNameMapping;
    }
    
    /**
     * @return 返回 columnName2CommentMapping
     */
    public Map<String, String> getColumnName2CommentMapping() {
        return columnName2CommentMapping;
    }
    
    /**
     * @param 对columnName2CommentMapping进行赋值
     */
    public void setColumnName2CommentMapping(
            Map<String, String> columnName2CommentMapping) {
        this.columnName2CommentMapping = columnName2CommentMapping;
    }
    
    public static void main(String[] args) {
        MySQL5InnoDBDialect d = new MySQL5InnoDBDialect();
        
        System.out.println(d.getTypeName(Types.BIT));
        System.out.println(d.getTypeName(Types.CHAR));
        System.out.println(d.getTypeName(Types.VARCHAR, 40, 0, 0));
        System.out.println(d.getTypeName(Types.INTEGER, 100, 100, 32));
    }
}
