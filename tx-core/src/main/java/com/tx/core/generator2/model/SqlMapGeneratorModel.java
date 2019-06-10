/*
 * 描          述:  <描述>
 * 修  改   人:  PengQingyang
 * 修改时间:  2012-12-9
 * <修改描述:>
 */
package com.tx.core.generator2.model;

import java.util.Date;
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
    private final JPAColumnInfo pkColumn;
    
    /** 是否有编码属性 */
    private JPAColumnInfo createDateColumn;
    
    /** 是否有编码属性 */
    private JPAColumnInfo codeColumn;
    
    /** 是否有是否有效的属性 */
    private JPAColumnInfo validColumn;
    
    /** 父节点id对应的属性 */
    private JPAColumnInfo parentIdColumn;
    
    /** 排序字段 */
    private String defaultOrderBy;
    
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
        this.pkColumn = this.columnList.stream().filter(column -> {
            return column.isPrimaryKey();
        }).collect(Collectors.toList()).get(0);
        AssertUtils.isTrue(this.pkColumn != null, "没有找到主键字段");
        
        this.columnList.stream().forEach(column -> {
            if (StringUtils.equals("code", column.getPropertyName())
                    && !column.isPrimaryKey()) {
                //如果主键就是code，则无需标定hasCodeProperty
                this.codeColumn = column;
                AssertUtils.isTrue(
                        String.class.isAssignableFrom(
                                this.codeColumn.getPropertyType()),
                        "code type should is String.");
            } else if (StringUtils.equals("valid", column.getPropertyName())) {
                this.validColumn = column;
                AssertUtils.isTrue(
                        Boolean.class.isAssignableFrom(column.getPropertyType())
                                || boolean.class
                                        .equals(column.getPropertyType()),
                        "valid type should is boolean or Boolean.");
            } else if (StringUtils.equals("createDate",
                    column.getPropertyName())) {
                this.createDateColumn = column;
                AssertUtils.isTrue(
                        Date.class.isAssignableFrom(column.getPropertyType())
                                || boolean.class
                                        .equals(column.getPropertyType()),
                        "createDate type should is Date.");
            } else if (StringUtils.equals("parentId",
                    column.getPropertyName())) {
                this.parentIdColumn = column;
                AssertUtils.isTrue(
                        this.pkColumn.getPropertyType()
                                .equals(column.getPropertyType()),
                        "parentId type:{} should equals pk type:{}.",
                        new Object[] { column.getPropertyType(),
                                this.pkColumn.getPropertyType() });
            }
        });
        
        this.defaultOrderBy = JPAParseUtils.parseOrderBy(entityType,
                this.columnList,
                "createDate",
                "id",
                "code",
                "name");
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
     * @return 返回 codeColumn
     */
    public JPAColumnInfo getCodeColumn() {
        return codeColumn;
    }
    
    /**
     * @return 返回 validColumn
     */
    public JPAColumnInfo getValidColumn() {
        return validColumn;
    }
    
    /**
     * @return 返回 pkColumn
     */
    public JPAColumnInfo getPkColumn() {
        return pkColumn;
    }
    
    /**
     * @return 返回 createDateColumn
     */
    public JPAColumnInfo getCreateDateColumn() {
        return createDateColumn;
    }
    
    /**
     * @return 返回 defaultOrderBy
     */
    public String getDefaultOrderBy() {
        return defaultOrderBy;
    }
    
    /**
     * @return 返回 parentIdColumn
     */
    public JPAColumnInfo getParentIdColumn() {
        return parentIdColumn;
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
