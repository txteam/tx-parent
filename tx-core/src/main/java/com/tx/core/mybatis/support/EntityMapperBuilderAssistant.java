/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2016年11月17日
 * <修改描述:>
 */
package com.tx.core.mybatis.support;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.Configuration;
import org.springframework.beans.BeanUtils;

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
public class EntityMapperBuilderAssistant
        extends AbstractEntityMapperBuilderAssistant {
    
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
    public EntityMapperBuilderAssistant(Configuration configuration,
            Class<?> beanType) {
        super(configuration, beanType);
        
        //被解析生成的类型应该不是一个simpleValueType
        AssertUtils.isTrue(!BeanUtils.isSimpleValueType(beanType),
                "type is simpleValueType.");
        
        //解析表名
        this.tableName = JPAParseUtils.parseTableName(this.beanType);
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
     * @return
     */
    @Override
    protected String getInsertSQL() {
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
        SqlMapSQLBuilder sql = new SqlMapSQLBuilder();
        sql.DELETE_FROM(this.tableName);
        
        for (JPAColumnInfo column : this.primaryKeyColumns) {
            String columnName = column.getColumnName();
            String propertyName = column.getNestedPropertyName();
            
            String whereItem = formatWhereAndItem(columnName,
                    " = ",
                    propertyName);
            sql.WHERE(whereItem);
        }
        
        String deleteSQL = sql.toString();
        return deleteSQL;
    }
    
    /**
     * @return
     */
    @Override
    protected String getUpdateSQL() {
        SqlMapSQLBuilder sql = new SqlMapSQLBuilder();
        sql.UPDATE(this.tableName);
        for (JPAColumnInfo column : this.notPrimaryKeyColumns) {
            if (!column.isUpdatable()) {
                continue;
            }
            String columnName = column.getColumnName();
            String propertyName = column.getNestedPropertyName();
            Class<?> javaType = column.getNestedPropertyType();
            
            String setItem = formatSetItem(propertyName, columnName, javaType);
            sql.SET(setItem);
        }
        for (JPAColumnInfo column : this.primaryKeyColumns) {
            String columnName = column.getColumnName();
            String propertyName = column.getNestedPropertyName();
            
            String whereItem = formatWhereAndItem(columnName,
                    " = ",
                    propertyName);
            sql.WHERE(whereItem);
        }
        
        String updateSQL = sql.toString();
        return updateSQL;
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
        SqlMapSQLBuilder sql = new SqlMapSQLBuilder();
        for (JPAColumnInfo column : this.tableColumns) {
            String columnName = column.getColumnName();
            //查询字段
            sql.QUERY(columnName);
        }
        sql.FROM(this.tableName);
        for (JPAColumnInfo column : this.tableColumns) {
            String columnName = column.getColumnName();
            String propertyName = column.getNestedPropertyName();
            
            String whereItem = formatWhereAndItem(columnName,
                    " = ",
                    propertyName);
            sql.WHERE(whereItem);
        }
        //        if(!CollectionUtils.isEmpty(this.orderBys)){
        //            for(String orderByTemp : this.orderBys){
        //                sql.ORDER_BY(orderByTemp);
        //            }
        //        }
        sql.ORDER_BY(this.queryOrderBy);
        
        String querySQL = sql.toString();
        return querySQL;
    }
    
    /**
     * @return
     */
    @Override
    protected String getCountSQL() {
        SqlMapSQLBuilder sql = new SqlMapSQLBuilder();
        sql.COUNT();
        sql.FROM(this.tableName);
        for (JPAColumnInfo column : this.tableColumns) {
            String columnName = column.getColumnName();
            String propertyName = column.getNestedPropertyName();
            
            String whereItem = formatWhereAndItem(columnName,
                    " = ",
                    propertyName);
            sql.WHERE(whereItem);
        }
        
        String countSQL = sql.toString();
        return countSQL;
    }
    
}