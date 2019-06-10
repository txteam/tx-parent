/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2019年6月7日
 * <修改描述:>
 */
package com.tx.core.querier.model;

import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.querier.QuerierConstants;

/**
 * 查询构建器<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2019年6月7日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class QuerierBuilder {
    
    /** 查询器实例 */
    private Querier querier;
    
    /** <默认构造函数> */
    private QuerierBuilder() {
        this.querier = new Querier();
    }
    
    /**
     * 生成查询构建起实例<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return QuerierBuilder [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static QuerierBuilder newInstance() {
        QuerierBuilder builder = new QuerierBuilder();
        return builder;
    }
    
    /**
     * 生成查询构建起实例<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return QuerierBuilder [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public QuerierBuilder searchProperty(String searchProperty,
            String searchValue) {
        AssertUtils.notEmpty(searchProperty, "searchProperty is empty.");
        AssertUtils.notEmpty(searchValue, "searchValue is empty.");
        
        this.querier.setSearchProperty(searchProperty);
        this.querier.setSearchValue(searchValue);
        
        return this;
    }
    
    /**
     * 生成查询构建起实例<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return QuerierBuilder [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public QuerierBuilder searchProperty(String searchProperty,
            OperatorEnum searchOperator, String searchValue) {
        AssertUtils.notEmpty(searchProperty, "searchProperty is empty.");
        AssertUtils.notEmpty(searchValue, "searchValue is empty.");
        
        this.querier.setSearchProperty(searchProperty);
        this.querier.setSearchOperator(
                searchOperator == null ? OperatorEnum.like : searchOperator);
        this.querier.setSearchValue(searchValue);
        
        return this;
    }
    
    /**
     * 生成查询构建起实例<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return QuerierBuilder [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public QuerierBuilder orderProperty(String orderProperty) {
        AssertUtils.notEmpty(orderProperty, "orderProperty is empty.");
        
        this.querier.setOrderProperty(orderProperty);
        this.querier.setOrderDirection(QuerierConstants.DEFAULT_DIRECTION);
        
        return this;
    }
    
    /**
     * 生成查询构建起实例<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return QuerierBuilder [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public QuerierBuilder orderProperty(String orderProperty,
            OrderDirectionEnum orderDirection) {
        AssertUtils.notEmpty(orderProperty, "orderProperty is empty.");
        
        this.querier.setOrderProperty(orderProperty);
        this.querier.setOrderDirection(orderDirection == null
                ? QuerierConstants.DEFAULT_DIRECTION : orderDirection);
        
        return this;
    }
    
    /**
     * 生成查询构建起实例<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return QuerierBuilder [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public QuerierBuilder addOrder(String property) {
        AssertUtils.notEmpty(property, "property is empty.");
        
        OrderDirectionEnum direction = QuerierConstants.DEFAULT_DIRECTION;
        this.querier.getOrders().add(new Order(property, direction));
        
        return this;
    }
    
    /**
     * 生成查询构建起实例<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return QuerierBuilder [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public QuerierBuilder addOrder(String property,
            OrderDirectionEnum direction) {
        AssertUtils.notEmpty(property, "property is empty.");
        
        direction = direction == null ? QuerierConstants.DEFAULT_DIRECTION
                : direction;
        this.querier.getOrders().add(new Order(property, direction));
        
        return this;
    }
    
    /**
     * 生成查询构建起实例<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return QuerierBuilder [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public QuerierBuilder addFilter(String property, OperatorEnum operator,
            Object value) {
        AssertUtils.notEmpty(property, "property is empty.");
        
        operator = operator == null ? OperatorEnum.eq : operator;
        this.querier.getFilters().add(new Filter(property, operator, value));
        
        return this;
    }
    
    /**
     * 生成查询构建起实例<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return QuerierBuilder [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public QuerierBuilder addFilter(Filter filter) {
        AssertUtils.notNull(filter, "filter is null.");
        AssertUtils.notEmpty(filter.getProperty(), "filter.property is empty.");
        AssertUtils.notNull(filter.getOperator(), "filter.operator is null.");
        
        this.querier.getFilters().add(filter);
        
        return this;
    }
    
    /**
     * 返回查询器实例<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return Querier [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public Querier querier() {
        return this.querier;
    }
}
