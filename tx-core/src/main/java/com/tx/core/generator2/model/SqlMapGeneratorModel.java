/*
 * 描          述:  <描述>
 * 修  改   人:  PengQingyang
 * 修改时间:  2012-12-9
 * <修改描述:>
 */
package com.tx.core.generator2.model;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.util.JPAParseUtils;
import com.tx.core.util.JPAParseUtils.JPAColumnInfo;

/**
 * 对应sqlMap顶层相关配置
 * <功能详细描述>
 * 
 * @author  PengQingyang
 * @version  [版本号, 2012-12-9]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class SqlMapGeneratorModel {
    
    /** 实体类型 */
    private final Class<?> entityType;
    
    /** 实体类型name */
    private final String entityTypeName;
    
    /** 实体类型simpleName */
    private final String entityTypeSimpleName;
    
    /** 表名 */
    private final String tableName;
    
    /** 表名缩写 */
    private final String simpleTableName;
    
    /** jpa字段列表 */
    private final List<JPAColumnInfo> columnList;
    
    /** 主键字段列表 */
    private final List<JPAColumnInfo> pkColumnList;
    
    /** 是否有编码属性 */
    private JPAColumnInfo codeColumn;
    
    /** <默认构造函数> */
    public SqlMapGeneratorModel(Class<?> entityType) {
        super();
        this.entityType = entityType;
        this.entityTypeName = entityType.getName();
        this.entityTypeSimpleName = entityType.getSimpleName();
        
        this.tableName = JPAParseUtils.parseTableName(entityType);
        this.simpleTableName = generateSimpleTableName(
                entityType.getSimpleName());
        
        this.columnList = JPAParseUtils.parseTableColumns(entityType);
        this.pkColumnList = this.columnList.stream().filter(column -> {
            return column.isPrimaryKey();
        }).collect(Collectors.toList());
        
        this.columnList.stream().forEach(column -> {
            if (StringUtils.equals("code", column.getPropertyName())
                    && !column.isPrimaryKey()) {
                //如果主键就是code，则无需标定hasCodeProperty
                this.codeColumn = column;
                AssertUtils.isTrue(
                        String.class.isAssignableFrom(
                                this.codeColumn.getPropertyType())
                                || boolean.class.equals(
                                        this.codeColumn.getPropertyType()),
                        "code type should is String.");
            }
        });
    }
    
    /**
     * @return 返回 entityType
     */
    public Class<?> getEntityType() {
        return entityType;
    }
    
    /**
     * @return 返回 entityTypeName
     */
    public String getEntityTypeName() {
        return entityTypeName;
    }
    
    /**
     * @return 返回 entityTypeSimpleName
     */
    public String getEntityTypeSimpleName() {
        return entityTypeSimpleName;
    }
    
    /**
     * @return 返回 tableName
     */
    public String getTableName() {
        return tableName;
    }
    
    /**
     * @return 返回 simpleTableName
     */
    public String getSimpleTableName() {
        return simpleTableName;
    }
    
    /**
     * @return 返回 columnList
     */
    public List<JPAColumnInfo> getColumnList() {
        return columnList;
    }
    
    /**
     * @return 返回 pkColumnList
     */
    public List<JPAColumnInfo> getPkColumnList() {
        return pkColumnList;
    }
    
    /**
     * @return 返回 codeColumn
     */
    public JPAColumnInfo getCodeColumn() {
        return codeColumn;
    }
    
    /**
     * 生成表名缩写<br/>
     * <功能详细描述>
     * @param entitySimpleName
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    private static String generateSimpleTableName(String entityTypeSimpleName) {
        //取字段名数组中最后一个字符串
        String nameArraySource = entityTypeSimpleName;
        nameArraySource = nameArraySource.replaceAll("[A-Z]", ",$0");
        nameArraySource = nameArraySource.replaceAll("_", ",");
        String[] nameArray = StringUtils.splitByWholeSeparator(nameArraySource,
                ",");
        StringBuilder sb = new StringBuilder();
        for (String columnTemp : nameArray) {
            if (StringUtils.isEmpty(columnTemp)) {
                continue;
            }
            sb.append(columnTemp.charAt(0));
        }
        return sb.toString().toUpperCase();
    }
}
