/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2015年3月4日
 * <修改描述:>
 */
package com.tx.core.model;

import java.lang.reflect.Type;

/**
 * 基础类能够获取该类的子类的注解类型
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2015年3月4日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public abstract class AbstractParameterizedTypeReference<T> {
    
    private Type rawType;
    
    protected void setRawType(Type rawType){
        this.rawType = rawType;
    }
    
    public Type getType() {
        return this.rawType;
    }
    
    public Type getRawType() {
        return this.rawType;
    }
}
