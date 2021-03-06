/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2019年6月3日
 * <修改描述:>
 */
package com.tx.core.querier.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.tx.core.querier.QuerierConstants;

/**
 * 查询条件<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2019年6月3日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class Querier implements Serializable, Cloneable {
    
    /** 注释内容 */
    private static final long serialVersionUID = 7977479044915973631L;
    
    /** 搜索属性 */
    private String searchProperty;
    
    /** 运算符 */
    private OperatorEnum searchOperator = OperatorEnum.like;
    
    /** 搜索值 */
    private String searchValue;
    
    /** 排序属性 */
    private String orderProperty;
    
    /** 排序方向 */
    private OrderDirectionEnum orderDirection = QuerierConstants.DEFAULT_DIRECTION;
    
    /** 筛选 */
    private List<Filter> filters = new ArrayList<>();
    
    /** 排序 */
    private List<Order> orders = new ArrayList<>();
    
    /** 额外参数 */
    private Map<String, Object> params = new HashMap<>();
    
    /**
     * 构造方法
     */
    public Querier() {
    }
    
    /**
     * 获取搜索属性
     * @return 搜索属性
     */
    public String getSearchProperty() {
        return searchProperty;
    }
    
    /**
     * 设置搜索属性
     * @param searchProperty搜索属性
     */
    public void setSearchProperty(String searchProperty) {
        this.searchProperty = searchProperty;
    }
    
    /**
     * 获取搜索值
     * @return 搜索值
     */
    public String getSearchValue() {
        return searchValue;
    }
    
    /**
     * 设置搜索值
     * @param searchValue搜索值
     */
    public void setSearchValue(String searchValue) {
        this.searchValue = searchValue;
    }
    
    /**
     * @return 返回 searchOperator
     */
    public OperatorEnum getSearchOperator() {
        return searchOperator;
    }
    
    /**
     * @param 对searchOperator进行赋值
     */
    public void setSearchOperator(OperatorEnum searchOperator) {
        this.searchOperator = searchOperator;
    }
    
    /**
     * 获取排序属性
     * @return 排序属性
     */
    public String getOrderProperty() {
        return orderProperty;
    }
    
    /**
     * 设置排序属性
     * @param orderProperty排序属性
     */
    public void setOrderProperty(String orderProperty) {
        this.orderProperty = orderProperty;
    }
    
    /**
     * @return 返回 orderDirection
     */
    public OrderDirectionEnum getOrderDirection() {
        return orderDirection;
    }
    
    /**
     * @param 对orderDirection进行赋值
     */
    public void setOrderDirection(OrderDirectionEnum orderDirection) {
        this.orderDirection = orderDirection;
    }
    
    /**
     * @return 返回 filters
     */
    public List<Filter> getFilters() {
        if (this.filters == null) {
            this.filters = new ArrayList<>();
        }
        return filters;
    }
    
    /**
     * @param 对filters进行赋值
     */
    public void setFilters(List<Filter> filters) {
        this.filters = filters;
    }
    
    /**
     * 获取排序
     * @return 排序
     */
    public List<Order> getOrders() {
        if (this.orders == null) {
            this.orders = new ArrayList<>();
        }
        return orders;
    }
    
    /**
     * 设置排序
     * @param orders排序
     */
    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
    
    /**
     * @return 返回 params
     */
    public Map<String, Object> getParams() {
        if (this.params == null) {
            this.params = new HashMap<>();
        }
        return params;
    }
    
    /**
     * @param 对params进行赋值
     */
    public void setParams(Map<String, Object> params) {
        this.params = params;
    }
    
    /**
     * 重写equals方法
     * @param obj 对象
     * @return 是否相等
     */
    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }
    
    /**
     * 重写hashCode方法
     * @return HashCode
     */
    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }
    
    /**
     * @return
     */
    @Override
    public Object clone() {
        Querier querier = new Querier();
        querier.setOrderProperty(orderProperty);
        querier.setOrderDirection(orderDirection);
        querier.setSearchProperty(searchProperty);
        querier.setSearchOperator(searchOperator);
        querier.setSearchValue(searchValue);
        
        querier.setFilters(new ArrayList<>(getFilters()));
        querier.setOrders(new ArrayList<>(getOrders()));
        querier.setParams(new HashMap<>(getParams()));
        return querier;
    }
    
    //    public static void main(String[] args) {
    //        Querier t1 = QuerierBuilder.newInstance()
    //                .searchProperty("parentId", "testParentId")
    //                .orderProperty("createDate")
    //                .addOrder("name")
    //                .addOrder("lastUpdateDate", OrderDirectionEnum.DESC)
    //                .addOrder("createDate", OrderDirectionEnum.ASC)
    //                .addFilter(Filter.like("name", "namelike%"))
    //                .addFilter(Filter.isNotNull("code"))
    //                .querier();
    //        
    //        Querier t2 = QuerierBuilder.newInstance()
    //                .searchProperty("parentId", "testParentId")
    //                .orderProperty("createDate")
    //                .addOrder("name")
    //                .addOrder("lastUpdateDate", OrderDirectionEnum.DESC)
    //                .addOrder("createDate", OrderDirectionEnum.ASC)
    //                .addFilter(Filter.like("name", "namelike%"))
    //                .addFilter(Filter.isNotNull("code"))
    //                .querier();
    //        
    //        System.out.println(t1.hashCode());
    //        System.out.println(t2.hashCode());
    //        System.out.println(t1.equals(t2));
    //        
    //        Querier t3 = (Querier)t1.clone();
    //        Querier t4 = (Querier)t2.clone();
    //        System.out.println(t3.hashCode());
    //        System.out.println(t4.hashCode());
    //        System.out.println(t3.equals(t4));
    //    }
}
