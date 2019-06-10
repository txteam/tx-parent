/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2019年6月4日
 * <修改描述:>
 */
package com.tx.core.querier.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.querier.model.Filter;
import com.tx.core.querier.model.Order;
import com.tx.core.querier.model.Querier;
import com.tx.core.querier.model.QueryConditionItem;
import com.tx.core.querier.model.QueryOrderItem;
import com.tx.core.util.JPAParseUtils;
import com.tx.core.util.JPAParseUtils.JPAColumnInfo;

/**
 * Mybatis查询器相关工具类<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2019年6月4日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class MybatisQuerierUtils {
    
    /** 属性映射 */
    private static final Map<Class<?>, Map<String, JPAColumnInfo>> PROPERTY_MAP = new HashMap<>();
    
    /**
     * 返回查询条件集合<br/>
     * <功能详细描述>
     * @param entityType
     * @param querier
     * @return [参数说明]
     * 
     * @return List<QueryConditionItem> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static List<QueryConditionItem> parseQueryConditions(
            Class<?> entityType, Querier querier) {
        AssertUtils.notNull(entityType, "entityType is null.");
        
        List<QueryConditionItem> resList = new ArrayList<>();
        if (querier == null) {
            return resList;
        }
        
        Map<String, JPAColumnInfo> propertyMap = getPropertyMap(entityType);
        List<Filter> filters = querier.getFilters() == null ? new ArrayList<>()
                : querier.getFilters();
        if (!StringUtils.isEmpty(querier.getSearchProperty())) {
            Filter filter = new Filter(querier.getSearchProperty(),
                    querier.getSearchOperator(), querier.getSearchValue());
            if (!filters.contains(filter)) {
                filters.add(filter);
            }
        }
        
        filters.stream().forEach(filter -> {
            if (propertyMap.containsKey(filter.getProperty())) {
                resList.add(new QueryConditionItem(
                        propertyMap.get(filter.getProperty()),
                        filter.getOperator(), filter.getValue()));
            }
        });
        return resList;
    }
    
    /**
     * 返回查询条件集合<br/>
     * <功能详细描述>
     * @param entityType
     * @param querier
     * @return [参数说明]
     * 
     * @return List<QueryConditionItem> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static List<QueryOrderItem> parseQueryOrders(Class<?> entityType,
            Querier querier) {
        AssertUtils.notNull(entityType, "entityType is null.");
        
        List<QueryOrderItem> resList = new ArrayList<>();
        if (querier == null) {
            return resList;
        }
        
        Map<String, JPAColumnInfo> propertyMap = getPropertyMap(entityType);
        List<Order> orders = querier.getOrders() == null ? new ArrayList<>()
                : querier.getOrders();
        if (!StringUtils.isEmpty(querier.getOrderProperty())) {
            if (propertyMap.containsKey(querier.getOrderProperty())) {
                resList.add(new QueryOrderItem(
                        propertyMap.get(querier.getOrderProperty()),
                        querier.getOrderDirection()));
            }
        }
        
        orders.stream().forEach(order -> {
            if (propertyMap.containsKey(order.getProperty()) && !StringUtils
                    .equals(order.getProperty(), querier.getOrderProperty())) {
                resList.add(
                        new QueryOrderItem(propertyMap.get(order.getProperty()),
                                order.getDirection()));
            }
        });
        return resList;
    }
    
    /**
     * 获取属性映射<br/>
     * <功能详细描述>
     * @param entityType
     * @return [参数说明]
     * 
     * @return Map<String,JPAColumnInfo> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    private static Map<String, JPAColumnInfo> getPropertyMap(
            Class<?> entityType) {
        AssertUtils.notNull(entityType, "entityType is null.");
        
        if (PROPERTY_MAP.containsKey(entityType)) {
            return PROPERTY_MAP.get(entityType);
        }
        Map<String, JPAColumnInfo> propertyMap = new HashMap<>();
        List<JPAColumnInfo> columns = JPAParseUtils
                .parseTableColumns(entityType);
        columns.stream().forEach(column -> {
            propertyMap.put(column.getPropertyName(), column);
        });
        PROPERTY_MAP.put(entityType, propertyMap);
        return propertyMap;
    }
    
    /**
     * 解析排序项<br/>
     * <功能详细描述>
     * @param entityType
     * @param order
     * @return [参数说明]
     * 
     * @return QueryOrderItem [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static QueryOrderItem parseOrder(Class<?> entityType, Order order) {
        AssertUtils.notNull(entityType, "entityType is null.");
        AssertUtils.notNull(order, "order is null.");
        AssertUtils.notEmpty(order.getProperty(), "order is null.");
        
        Map<String, JPAColumnInfo> propertyMap = getPropertyMap(entityType);
        AssertUtils.isTrue(propertyMap.containsKey(order.getProperty()),
                "property:{} not exist.entityType:{}",
                new Object[] { order.getProperty(), entityType });
        JPAColumnInfo column = propertyMap.get(order.getProperty());
        
        QueryOrderItem orderItem = new QueryOrderItem(column,
                order.getDirection());
        
        return orderItem;
    }
    
    /**
     * 解析排序项<br/>
     * <功能详细描述>
     * @param entityType
     * @param order
     * @return [参数说明]
     * 
     * @return QueryOrderItem [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static List<QueryOrderItem> parseOrders(Class<?> entityType,
            List<Order> orders) {
        AssertUtils.notNull(entityType, "entityType is null.");
        List<QueryOrderItem> resList = new ArrayList<>();
        if (CollectionUtils.isEmpty(orders)) {
            return resList;
        }
        
        for (Order orderTemp : orders) {
            resList.add(parseOrder(entityType, orderTemp));
        }
        return resList;
    }
}
