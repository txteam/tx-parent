/*
 * Copyright 2005-2017 cqtianxin.com. All rights reserved.
 * Support: http://www.cqtianxin.com
 * License: http://www.cqtianxin.com/license
 */
package com.tx.core.querier.model;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.tx.core.paged.PagedConstant;

/**
 * 查询条件筛选<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2019年6月4日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class Filter implements Serializable {

	private static final long serialVersionUID = -8712382358441065075L;

	/** 属性 */
	private String property;

	/** 运算符 */
	private OperatorEnum operator;

	/** 值 */
	private Object value;

	/** 是否忽略大小写 */
	private boolean ignoreCase = PagedConstant.DEFAULT_IGNORE_CASE;

	/**
	 * 构造方法
	 */
	public Filter() {
	}

	/**
	 * 构造方法<br/>
	 * @param property 属性
	 * @param operator 运算符
	 * @param value 值
	 */
	public Filter(String property, OperatorEnum operator, Object value) {
		this.property = property;
		this.operator = operator;
		this.value = value;
	}

	/**
	 * 构造方法<br/>
	 * @param property 属性
	 * @param operator 运算符
	 * @param value 值
	 * @param ignoreCase 忽略大小写
	 */
	public Filter(String property, OperatorEnum operator, Object value, boolean ignoreCase) {
		this.property = property;
		this.operator = operator;
		this.value = value;
		this.ignoreCase = ignoreCase;
	}

	/**
	 * 返回等于筛选<br/>
	 * @param property 属性
	 * @param value 值
	 * @return 等于筛选
	 */
	public static Filter eq(String property, Object value) {
		return new Filter(property, OperatorEnum.eq, value);
	}

	/**
	 * 返回等于筛选
	 * @param property 属性
	 * @param value 值
	 * @param ignoreCase 忽略大小写
	 * @return 等于筛选
	 */
	public static Filter eq(String property, Object value, boolean ignoreCase) {
		return new Filter(property, OperatorEnum.eq, value, ignoreCase);
	}

	/**
	 * 返回不等于筛选
	 * 
	 * @param property
	 *            属性
	 * @param value
	 *            值
	 * @return 不等于筛选
	 */
	public static Filter ne(String property, Object value) {
		return new Filter(property, OperatorEnum.ne, value);
	}

	/**
	 * 返回不等于筛选
	 * 
	 * @param property
	 *            属性
	 * @param value
	 *            值
	 * @param ignoreCase
	 *            忽略大小写
	 * @return 不等于筛选
	 */
	public static Filter ne(String property, Object value, boolean ignoreCase) {
		return new Filter(property, OperatorEnum.ne, value, ignoreCase);
	}

	/**
	 * 返回大于筛选
	 * 
	 * @param property
	 *            属性
	 * @param value
	 *            值
	 * @return 大于筛选
	 */
	public static Filter gt(String property, Object value) {
		return new Filter(property, OperatorEnum.gt, value);
	}

	/**
	 * 返回小于筛选
	 * 
	 * @param property
	 *            属性
	 * @param value
	 *            值
	 * @return 小于筛选
	 */
	public static Filter lt(String property, Object value) {
		return new Filter(property, OperatorEnum.lt, value);
	}

	/**
	 * 返回大于等于筛选
	 * 
	 * @param property
	 *            属性
	 * @param value
	 *            值
	 * @return 大于等于筛选
	 */
	public static Filter ge(String property, Object value) {
		return new Filter(property, OperatorEnum.ge, value);
	}

	/**
	 * 返回小于等于筛选
	 * 
	 * @param property
	 *            属性
	 * @param value
	 *            值
	 * @return 小于等于筛选
	 */
	public static Filter le(String property, Object value) {
		return new Filter(property, OperatorEnum.le, value);
	}

	/**
	 * 返回相似筛选
	 * 
	 * @param property
	 *            属性
	 * @param value
	 *            值
	 * @return 相似筛选
	 */
	public static Filter like(String property, Object value) {
		return new Filter(property, OperatorEnum.like, value);
	}

	/**
	 * 返回包含筛选
	 * 
	 * @param property
	 *            属性
	 * @param value
	 *            值
	 * @return 包含筛选
	 */
	public static Filter in(String property, Object value) {
		return new Filter(property, OperatorEnum.in, value);
	}

	/**
	 * 返回为Null筛选
	 * 
	 * @param property
	 *            属性
	 * @return 为Null筛选
	 */
	public static Filter isNull(String property) {
		return new Filter(property, OperatorEnum.isNull, null);
	}

	/**
	 * 返回不为Null筛选
	 * 
	 * @param property
	 *            属性
	 * @return 不为Null筛选
	 */
	public static Filter isNotNull(String property) {
		return new Filter(property, OperatorEnum.isNotNull, null);
	}

	/**
	 * 返回忽略大小写筛选
	 * 
	 * @return 忽略大小写筛选
	 */
	public Filter ignoreCase() {
		this.ignoreCase = true;
		return this;
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
	 * 获取运算符
	 * 
	 * @return 运算符
	 */
	public OperatorEnum getOperator() {
		return operator;
	}

	/**
	 * 设置运算符
	 * 
	 * @param operator
	 *            运算符
	 */
	public void setOperator(OperatorEnum operator) {
		this.operator = operator;
	}

	/**
	 * 获取值
	 * 
	 * @return 值
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * 设置值
	 * 
	 * @param value
	 *            值
	 */
	public void setValue(Object value) {
		this.value = value;
	}

	/**
     * @return 返回 ignoreCase
     */
    public boolean isIgnoreCase() {
        return ignoreCase;
    }

    /**
     * @param 对ignoreCase进行赋值
     */
    public void setIgnoreCase(boolean ignoreCase) {
        this.ignoreCase = ignoreCase;
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