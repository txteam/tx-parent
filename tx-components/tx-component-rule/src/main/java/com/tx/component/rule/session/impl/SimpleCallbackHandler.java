/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-1-24
 * <修改描述:>
 */
package com.tx.component.rule.session.impl;

import com.tx.component.rule.session.CallbackHandler;
import com.tx.core.exceptions.util.AssertUtils;

/**
 * 简单的规则回话结果句柄实现
 * 
 * @author  brady
 * @version  [版本号, 2013-1-24]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class SimpleCallbackHandler<R> implements CallbackHandler<R> {
    
    /** 值 */
    private R value;
    
    /** 值类型 */
    private Class<R> rowType;
    
    private SimpleCallbackHandler(Class<R> type) {
        super();
        AssertUtils.notNull(type, "type is null.");
        this.rowType = type;
    }
    
    private SimpleCallbackHandler(Class<R> type, R value) {
        super();
        AssertUtils.notNull(type, "type is null.");
        this.rowType = type;
        this.value = value;
    }
    
    public static <R> SimpleCallbackHandler<?> newInstance(Class<R> valueType) {
        SimpleCallbackHandler<R> re = new SimpleCallbackHandler<R>(valueType);
        return re;
    }
    
    public static <R> SimpleCallbackHandler<?> newInstance(Class<R> valueType,
            R initValue) {
        SimpleCallbackHandler<R> re = new SimpleCallbackHandler<R>(valueType,
                initValue);
        return re;
    }
    
    /**
     * @return
     */
    @Override
    public R getValue() {
        return this.value;
    }
    
    /**
     * @param value
     */
    @Override
    public void setValue(R value) {
        AssertUtils.isAssignable(rowType, value.getClass());
        this.value = value;
    }
    
    /**
     * @return
     */
    @Override
    public Class<?> getRowType() {
        return this.rowType;
    }
}
