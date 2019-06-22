/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2019年6月23日
 * <修改描述:>
 */
package com.tx.core.jpa.assistant;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;

import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.mybatis.annotation.MapperEntity;
import com.tx.core.util.JPAParseUtils;
import com.tx.core.util.JPAParseUtils.JPAColumnInfo;

/**
 * <功能简述>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2019年6月23日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class JPABaseDaoAssistant {
    
    //表名
    protected String tableName;
    
    /** 对应的类类型 */
    protected Class<?> entityType;
    
    //表字段
    protected List<JPAColumnInfo> tableColumns;
    
    /** 主键字段列表 */
    private final JPAColumnInfo pkColumn;
    
    /** 是否有编码属性 */
    private JPAColumnInfo createDateColumn;
    
    /** 是否有编码属性 */
    private JPAColumnInfo codeColumn;
    
    /** 是否有是否有效的属性 */
    private JPAColumnInfo validColumn;
    
    /** 是否有是否有效的属性 */
    private JPAColumnInfo parentIdColumn;
    
    /** 排序字段 */
    private String defaultOrderBy;
    
    /** <默认构造函数> */
    public JPABaseDaoAssistant(Class<?> entityType) {
        super();
        this.entityType = entityType;
        
        //被解析生成的类型应该不是一个simpleValueType
        AssertUtils.isTrue(!BeanUtils.isSimpleValueType(entityType),
                "type:{} is simpleValueType.",
                new Object[] { entityType });
        
        this.tableName = JPAParseUtils.parseTableName(entityType);
        this.tableColumns = JPAParseUtils.parseTableColumns(entityType);
        
        //主键字段以及非主键字段集合记录
        List<JPAColumnInfo> pkColumns = this.tableColumns.stream()
                .filter(column -> {
                    return column.isPrimaryKey();
                })
                .collect(Collectors.toList());
        //必须存在主键字段
        AssertUtils.notEmpty(pkColumns,
                "实体无法判断主键字段，不支持自动生成SqlMap.type:{}",
                new Object[] { entityType });
        this.pkColumn = pkColumns.get(0);
        
        for (JPAColumnInfo column : this.tableColumns) {
            if (column.isPrimaryKey()) {
                //虽然可以支持，但是变化情况太复杂，而且暂时想不到哪里有常用的应用场景，所以暂时如果出现这种情况就抛出异常
                AssertUtils.isTrue(!column.hasNestedProperty(),
                        "主键不支持具有嵌套属性的情况.");
            }
            if ("createDate".equals(column.getPropertyName())) {
                this.createDateColumn = column;
                AssertUtils.isTrue(
                        Date.class.isAssignableFrom(column.getPropertyType()),
                        "createDateColumn.propertyType:{} is not assign from Date.class.",
                        new Object[] { column.getPropertyType() });
            } else if ("code".equals(column.getPropertyName())) {
                this.codeColumn = column;
                AssertUtils.isTrue(
                        String.class.isAssignableFrom(column.getPropertyType()),
                        "createDateColumn.propertyType:{} is not assign from Date.class.",
                        new Object[] { column.getPropertyType() });
            } else if ("valid".equals(column.getPropertyName())) {
                this.validColumn = column;
                AssertUtils.isTrue(
                        Boolean.class.equals(column.getPropertyType())
                                || boolean.class
                                        .equals(column.getPropertyType()),
                        "createDateColumn.propertyType:{} is not assign from boolean.class or Boolean.class.",
                        new Object[] { column.getPropertyType() });
            } else if ("parentId".equals(column.getPropertyName())) {
                this.parentIdColumn = column;
                AssertUtils.isTrue(
                        this.pkColumn.getPropertyType()
                                .equals(column.getPropertyType()),
                        "pkColumn.propertyType:{} should equals parentIdColumn.propertyType:{}",
                        new Object[] { this.pkColumn.getPropertyType(),
                                column.getPropertyType() });
            }
        }
        
        //解析排序字段
        this.defaultOrderBy = JPAParseUtils.parseOrderBy(entityType,
                this.tableColumns,
                "createDate",
                "id",
                "code",
                "name");
    }
    
}
