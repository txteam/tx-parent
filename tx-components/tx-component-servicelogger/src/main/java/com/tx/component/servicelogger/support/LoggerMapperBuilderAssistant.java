/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2016年11月17日
 * <修改描述:>
 */
package com.tx.component.servicelogger.support;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.Configuration;
import org.springframework.beans.BeanUtils;
import org.springframework.core.annotation.AnnotationUtils;

import com.tx.component.servicelogger.annotation.ServiceLog;
import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.mybatis.builder.AbstractEntityMapperBuilderAssistant;
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
public class LoggerMapperBuilderAssistant
        extends AbstractEntityMapperBuilderAssistant {
    
    /** 注解信息 */
    protected ServiceLog annotation;
    
    //表名
    protected String tableName;
    
    //表字段
    protected List<JPAColumnInfo> tableColumns;
    
    //排序字段
    protected List<String> orderBys;
    
    //queryOrderBy
    protected String queryOrderBy;
    
    //主键字段
    protected List<JPAColumnInfo> primaryKeyColumns;
    
    //非主键字段
    protected List<JPAColumnInfo> notPrimaryKeyColumns;
    
    /** <默认构造函数> */
    public LoggerMapperBuilderAssistant(Configuration configuration,
            Class<?> beanType) {
        super(configuration, beanType);
        
        //被解析生成的类型应该不是一个simpleValueType
        AssertUtils.isTrue(!BeanUtils.isSimpleValueType(beanType),
                "type:{} is simpleValueType.",
                new Object[] { beanType });
        AssertUtils.isTrue(beanType.isAnnotationPresent(ServiceLog.class),
                "type:{} annotation is not exist.",
                new Object[] { beanType });
        
        //解析表名
        this.annotation = AnnotationUtils.findAnnotation(beanType,
                ServiceLog.class);
        this.tableName = StringUtils.isBlank(this.annotation.tablename())
                ? JPAParseUtils.parseTableName(this.beanType)
                : this.annotation.tablename();
        this.tableColumns = JPAParseUtils.parseTableColumns(this.beanType);
        
        //主键字段以及非主键字段集合记录
        this.primaryKeyColumns = new ArrayList<>();
        this.notPrimaryKeyColumns = new ArrayList<>();
        for (JPAColumnInfo column : this.tableColumns) {
            if (column.isPrimaryKey()) {
                this.primaryKeyColumns.add(column);
            } else {
                this.notPrimaryKeyColumns.add(column);
            }
            
            //判断是否具备typeHandler,也不存在嵌套属性描述时需要抛出异常
            if (!hasTypeHandler(column.getPropertyDescriptor())) {
                //如果不具备typeHandler，那么nestedPropertyDescriptor就不该为空
                AssertUtils.isTrue(column.getNestedPropertyDescriptor() != null,
                        "对应属性不存在类型处理器，且解析嵌套属性失败.beanType:{} property:{}",
                        new Object[] { this.beanType,
                                column.getPropertyDescriptor().getName() });
            }
            
            if (column.isPrimaryKey()) {
                //虽然可以支持，但是变化情况太复杂，而且暂时想不到哪里有常用的应用场景，所以暂时如果出现这种情况就抛出异常
                AssertUtils.isTrue(!column.hasNestedProperty(),
                        "主键不支持具有嵌套属性的情况.");
            }
        }
        
        //必须存在主键字段
        AssertUtils.notEmpty(this.primaryKeyColumns,
                "实体无法判断主键字段，不支持自动生成SqlMap.type:{}",
                new Object[] { this.beanType });
        
        //解析排序字段
        this.orderBys = JPAParseUtils.parseOrderBys(beanType,
                this.tableColumns,
                "createDate",
                "id");
        this.queryOrderBy = JPAParseUtils.parseOrderBy(beanType,
                this.tableColumns,
                "createDate",
                "id");
    }
    
    /**
     * 获取主键属性名列表<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return List<String> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public List<String> getPrimaryProperyNameList() {
        List<String> resList = new ArrayList<>();
        for (JPAColumnInfo column : this.primaryKeyColumns) {
            resList.add(column.getNestedPropertyName());
        }
        return resList;
    }
    
    /**
     * @return
     */
    @Override
    protected String getInsertSQL() {
        if (StringUtils.isNotBlank(annotation.insertSQL())) {
            return annotation.insertSQL();
        }
        
        SqlMapSQLBuilder sql = new SqlMapSQLBuilder();
        sql.INSERT_INTO(this.tableName);
        
        for (JPAColumnInfo column : this.tableColumns) {
            if (!column.isInsertable()) {
                continue;
            }
            String columnName = column.getColumnName();
            String propertyName = column.getNestedPropertyName();
            sql.VALUES(columnName, formatProperty(propertyName));
        }
        
        String insertSQL = sql.toString();
        return insertSQL;
    }
    
    /**
     * @return
     */
    @Override
    protected String getDeleteSQL() {
        return null;
    }
    
    /**
     * @return
     */
    @Override
    protected String getUpdateSQL() {
        return null;
    }
    
    /**
     * @return
     */
    @Override
    protected Map<String, String> getCustomizeColumn2PropertyMap() {
        Map<String, String> column2propertyMap = new HashMap<>();
        for (JPAColumnInfo column : this.tableColumns) {
            String columnName = column.getColumnName();
            String propertyName = column.getNestedPropertyName();
            
            //如果字段名和属性名不匹配时才回缴入customizeColumn2PropertyMap
            if (!StringUtils.equalsIgnoreCase(columnName, propertyName)) {
                column2propertyMap.put(columnName, propertyName);
            }
        }
        
        return column2propertyMap;
    }
    
    /**
     * @return
     */
    @Override
    protected String getFindSQL() {
        if (StringUtils.isNotBlank(annotation.findSQL())) {
            return annotation.findSQL();
        }
        
        SqlMapSQLBuilder sql = new SqlMapSQLBuilder();
        for (JPAColumnInfo column : this.tableColumns) {
            String columnName = column.getColumnName();
            //查询字段
            sql.FIND(columnName);
        }
        sql.FROM(this.tableName);
        for (JPAColumnInfo column : this.primaryKeyColumns) {
            String columnName = column.getColumnName();
            String propertyName = column.getNestedPropertyName();
            
            String whereItem = formatWhereAndItem(columnName,
                    " = ",
                    propertyName);
            sql.WHERE(whereItem);
        }
        
        String findSQL = sql.toString();
        return findSQL;
    }
    
    /**
     * @return
     */
    @Override
    protected String getQuerySQL() {
        if (StringUtils.isNotBlank(annotation.querySQL())) {
            return annotation.querySQL();
        }
        
        SqlMapSQLBuilder sql = new SqlMapSQLBuilder();
        for (JPAColumnInfo column : this.tableColumns) {
            String columnName = column.getColumnName();
            //查询字段
            sql.QUERY(columnName);
        }
        sql.FROM(this.tableName);
        
        buildQueryCondition(sql);//构建查询条件
        
        sql.ORDER_BY(this.queryOrderBy);
        
        String querySQL = sql.toString();
        return querySQL;
    }
    
    /**
     * @return
     */
    @Override
    protected String getCountSQL() {
        if (StringUtils.isNotBlank(annotation.countSQL())) {
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
            String columnName = column.getColumnName();
            String propertyName = column.getPropertyName();
            String nestedPropertyName = column.getNestedPropertyName();
            
            if (column.hasNestedProperty()) {
                String whereItem = formatNestedWhereAndItem(columnName,
                        " = ",
                        propertyName,
                        nestedPropertyName);
                sql.WHERE(whereItem);
            } else {
                String whereItem = formatWhereAndItem(columnName,
                        " = ",
                        propertyName);
                sql.WHERE(whereItem);
            }
            
            //这里以后可以做成子类可扩展的
            if ("createDate".equals(propertyName)) {
                String minWhereItem = formatWhereAndItem(columnName,
                        " >= ",
                        "minCreateDate");
                sql.WHERE(minWhereItem);
                
                String maxWhereItem = formatWhereAndItem(columnName,
                        " < ",
                        "maxCreateDate");
                sql.WHERE(maxWhereItem);
            }
        }
    }
    
}