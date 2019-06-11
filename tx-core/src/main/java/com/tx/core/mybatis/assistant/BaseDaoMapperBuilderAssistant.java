/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2016年11月17日
 * <修改描述:>
 */
package com.tx.core.mybatis.assistant;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.Configuration;
import org.springframework.beans.BeanUtils;
import org.springframework.core.annotation.AnnotationUtils;

import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.mybatis.annotation.MapperEntity;
import com.tx.core.mybatis.sqlbuilder.SqlMapSQLBuilder;
import com.tx.core.util.JPAParseUtils;
import com.tx.core.util.JPAParseUtils.JPAColumnInfo;

/**
 * Mapper构建助手扩展类<br/>
 *    该逻辑的调用，在设计上应该避免在修改statement期间出现业务调用的情况，不然可能出现不可预知的错误<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2016年11月17日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class BaseDaoMapperBuilderAssistant
        extends AbstractBaseDaoMapperBuilderAssistant {
    
    //表名
    protected String tableName;
    
    //表字段
    protected List<JPAColumnInfo> tableColumns;
    
    //MapperEntity的注解
    private MapperEntity annotation;
    
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
    public BaseDaoMapperBuilderAssistant(Configuration configuration,
            Class<?> entityType) {
        super(configuration, entityType);
        
        //被解析生成的类型应该不是一个simpleValueType
        AssertUtils.isTrue(!BeanUtils.isSimpleValueType(entityType),
                "type:{} is simpleValueType.",
                new Object[] { entityType });
        //解析表名
        this.annotation = AnnotationUtils.findAnnotation(entityType,
                MapperEntity.class);
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
            //判断是否具备typeHandler,也不存在嵌套属性描述时需要抛出异常
            if (!hasTypeHandler(column.getPropertyDescriptor())) {
                //如果不具备typeHandler，那么nestedPropertyDescriptor就不该为空
                AssertUtils.isTrue(column.getNestedPropertyDescriptor() != null,
                        "对应属性不存在类型处理器，且解析嵌套属性失败.beanType:{} property:{}",
                        new Object[] { entityType,
                                column.getPropertyDescriptor().getName() });
            }
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
    
    /**
     * @return
     */
    @Override
    protected String getInsertSQL() {
        if (annotation != null
                && StringUtils.isNotBlank(annotation.insertSQL())) {
            return annotation.insertSQL();
        }
        
        SqlMapSQLBuilder sql = new SqlMapSQLBuilder();
        sql.INSERT_INTO(this.tableName);
        
        for (JPAColumnInfo column : this.tableColumns) {
            if (!column.isInsertable()) {
                continue;
            }
            String columnName = column.getColumnName();
            String columnPropertyName = column.getColumnPropertyName();
            sql.VALUES(columnName, formatProperty(columnPropertyName));
        }
        
        String insertSQL = sql.toString();
        return insertSQL;
    }
    
    /**
     * @return
     */
    @Override
    protected String getDeleteSQL() {
        if (annotation != null
                && StringUtils.isNotBlank(annotation.deleteSQL())) {
            return annotation.deleteSQL();
        }
        
        SqlMapSQLBuilder sql = new SqlMapSQLBuilder();
        sql.DELETE_FROM(this.tableName);
        
        String whereItem = formatAndEqual(this.pkColumn);
        sql.WHERE(whereItem);
        if (this.codeColumn != null) {
            String whereItemTemp = formatAndEqual(this.codeColumn);
            sql.WHERE(whereItemTemp);
        }
        
        String deleteSQL = sql.toString();
        return deleteSQL;
    }
    
    /**
     * @return
     */
    @Override
    protected String getUpdateSQL() {
        if (annotation != null
                && StringUtils.isNotBlank(annotation.updateSQL())) {
            return annotation.updateSQL();
        }
        
        SqlMapSQLBuilder sql = new SqlMapSQLBuilder();
        sql.UPDATE(this.tableName);
        for (JPAColumnInfo column : this.tableColumns) {
            if (!column.isUpdatable() && !column.isPrimaryKey()
                    && !StringUtils.equals("code", column.getPropertyName())
                    && !StringUtils.equals("createDate",
                            column.getPropertyName())
                    && !StringUtils.equals("createOperatorId",
                            column.getPropertyName())) {
                continue;
            }
            String columnName = column.getColumnName();
            String propertyName = column.getPropertyName();
            String columnPropertyName = column.getColumnPropertyName();
            Class<?> columnPropertyType = column.getColumnPropertyType();
            
            String setItem = formatSetItem(columnName,
                    propertyName,
                    columnPropertyName,
                    columnPropertyType);
            sql.SET(setItem);
            
        }
        
        String whereItem = formatAndEqual(this.pkColumn);
        sql.WHERE(whereItem);
        if (this.codeColumn != null) {
            String whereItemTemp = formatAndEqual(this.codeColumn);
            sql.WHERE(whereItemTemp);
        }
        
        String updateSQL = sql.toString();
        return updateSQL;
    }
    
    /**
     * @return
     */
    @Override
    protected Map<String, String> getColumn2PropertyMap() {
        Map<String, String> column2propertyMap = new HashMap<>();
        for (JPAColumnInfo column : this.tableColumns) {
            String columnName = column.getColumnName();
            String columnPropertyName = column.getColumnPropertyName();
            //如果字段名和属性名不匹配时才回缴入customizeColumn2PropertyMap
            if (!StringUtils.equalsIgnoreCase(columnName, columnPropertyName)
                    || !hasTypeHandler(column.getPropertyDescriptor())) {
                column2propertyMap.put(columnName, columnPropertyName);
            }
        }
        
        return column2propertyMap;
    }
    
    /**
     * @return
     */
    @Override
    protected String getFindSQL() {
        if (annotation != null
                && StringUtils.isNotBlank(annotation.findSQL())) {
            return annotation.findSQL();
        }
        
        SqlMapSQLBuilder sql = new SqlMapSQLBuilder();
        for (JPAColumnInfo column : this.tableColumns) {
            String columnName = column.getColumnName();
            //查询字段
            sql.FIND(columnName);
        }
        sql.FROM(this.tableName);
        
        String whereItem = formatAndEqual(this.pkColumn);
        sql.WHERE(whereItem);
        if (this.codeColumn != null) {
            String whereItemTemp = formatAndEqual(this.codeColumn);
            sql.WHERE(whereItemTemp);
        }
        
        String findSQL = sql.toString();
        return findSQL;
    }
    
    /**
     * @return
     */
    @Override
    protected String getQuerySQL() {
        if (annotation != null
                && StringUtils.isNotBlank(annotation.querySQL())) {
            return annotation.querySQL();
        }
        
        SqlMapSQLBuilder sql = new SqlMapSQLBuilder();
        for (JPAColumnInfo column : this.tableColumns) {
            String columnName = column.getColumnName();
            //查询字段
            sql.QUERY(columnName);
        }
        sql.FROM(this.tableName);
        
        sql.WHERE(FORMATTER_OF_QUERIER);//查询的其他条件
        if(this.parentIdColumn != null){
            sql.WHERE(FORMATTER_OF_PARENTID);
        }
        buildQueryCondition(sql);//构建查询条件
        
        sql.ORDER_BY(defaultOrderBy);
        
        String querySQL = sql.toString();
        return querySQL;
    }
    
    /**
     * @return
     */
    @Override
    protected String getCountSQL() {
        if (annotation != null
                && StringUtils.isNotBlank(annotation.countSQL())) {
            return annotation.countSQL();
        }
        
        SqlMapSQLBuilder sql = new SqlMapSQLBuilder();
        sql.COUNT();
        sql.FROM(this.tableName);
        
        buildQueryCondition(sql);//构建查询条件
        
        String countSQL = sql.toString();
        return countSQL;
    }
    
    /** 
     * 构建查询条件<br/>
     * <功能详细描述>
     * @param sql [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    protected void buildQueryCondition(SqlMapSQLBuilder sql) {
        for (JPAColumnInfo column : this.tableColumns) {
            if (Date.class.isAssignableFrom(column.getPropertyType())
                    || java.sql.Date.class
                            .isAssignableFrom(column.getPropertyType())) {
                continue;
            }
            if (float.class.isAssignableFrom(column.getPropertyType())
                    || Float.class.isAssignableFrom(column.getPropertyType())) {
                continue;
            }
            if (double.class.isAssignableFrom(column.getPropertyType())
                    || Double.class
                            .isAssignableFrom(column.getPropertyType())) {
                continue;
            }
            
            String whereItem = formatAnd(column, " = ");
            sql.WHERE(whereItem);
        }
        if (createDateColumn != null) {
            String whereItem1 = formatAnd(this.createDateColumn.getColumnName(),
                    ">=",
                    "minCreateDate");
            sql.WHERE(whereItem1);
            String whereItem2 = formatAnd(this.createDateColumn.getColumnName(),
                    "<",
                    "maxCreateDate");
            sql.WHERE(whereItem2);
        }
        if (pkColumn != null) {
            String whereItem1 = formatAnd(this.pkColumn.getColumnName(),
                    "<>",
                    "exclude" + StringUtils
                            .capitalize(this.pkColumn.getPropertyName()));
            sql.WHERE(whereItem1);
        }
    }
    
    /**
     * @return 返回 pkColumn
     */
    public JPAColumnInfo getPkColumn() {
        return pkColumn;
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
     * @return 返回 tableName
     */
    public String getTableName() {
        return tableName;
    }

    /**
     * @return 返回 tableColumns
     */
    public List<JPAColumnInfo> getTableColumns() {
        return tableColumns;
    }

    /**
     * @return 返回 annotation
     */
    public MapperEntity getAnnotation() {
        return annotation;
    }

    /**
     * @return 返回 createDateColumn
     */
    public JPAColumnInfo getCreateDateColumn() {
        return createDateColumn;
    }

    /**
     * @return 返回 parentIdColumn
     */
    public JPAColumnInfo getParentIdColumn() {
        return parentIdColumn;
    }

    /**
     * @return 返回 defaultOrderBy
     */
    public String getDefaultOrderBy() {
        return defaultOrderBy;
    }
}