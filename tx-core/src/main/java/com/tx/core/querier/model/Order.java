/*
 * Copyright 2005-2017 cqtianxin.com. All rights reserved.
 * Support: http://www.cqtianxin.com
 * License: http://www.cqtianxin.com/license
 */
package com.tx.core.querier.model;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.tx.core.querier.QuerierConstants;

/**
 * 排序项<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2019年6月4日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class Order implements Serializable {
    
    private static final long serialVersionUID = -3078342809727773232L;
    
    /** 属性 */
    private String property;
    
    /** 方向 */
    private OrderDirectionEnum direction = QuerierConstants.DEFAULT_DIRECTION;
    
    /**
     * 构造方法
     */
    public Order() {
    }
    
    /**
     * 构造方法
     * 
     * @param property
     *            属性
     * @param direction
     *            方向
     */
    public Order(String property, OrderDirectionEnum direction) {
        this.property = property;
        this.direction = direction;
    }
    
    /**
     * 返回递增排序
     * 
     * @param property
     *            属性
     * @return 递增排序
     */
    public static Order asc(String property) {
        return new Order(property, OrderDirectionEnum.ASC);
    }
    
    /**
     * 返回递减排序
     * 
     * @param property
     *            属性
     * @return 递减排序
     */
    public static Order desc(String property) {
        return new Order(property, OrderDirectionEnum.DESC);
    }
    
    /**
     * 获取属性
     * 
     * @return 属性
     */
    public String getProperty() {
        return property;
    }
    
    /**
     * 设置属性
     * 
     * @param property
     *            属性
     */
    public void setProperty(String property) {
        this.property = property;
    }
    
    /**
     * 获取方向
     * 
     * @return 方向
     */
    public OrderDirectionEnum getDirection() {
        return direction;
    }
    
    /**
     * 设置方向
     * 
     * @param direction
     *            方向
     */
    public void setDirection(OrderDirectionEnum direction) {
        this.direction = direction;
    }
    
    /**
     * 重写equals方法
     * 
     * @param obj
     *            对象
     * @return 是否相等
     */
    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }
    
    /**
     * 重写hashCode方法
     * 
     * @return HashCode
     */
    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }
    
}