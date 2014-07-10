/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2014年6月4日
 * <修改描述:>
 */
package com.tx.core.support.hessian;

import com.caucho.hessian.io.AbstractSerializerFactory;
import com.caucho.hessian.io.Deserializer;
import com.caucho.hessian.io.HessianProtocolException;
import com.caucho.hessian.io.Serializer;

/**
 * 类型序列化工厂
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2014年6月4日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public abstract class TypeHandleSerializerFactory<T> extends
        AbstractSerializerFactory {
    
    private Class<T> type;
    
    /** <默认构造函数> */
    public TypeHandleSerializerFactory(Class<T> type) {
        super();
        this.type = type;
    }
    
    /**
     * @param cl
     * @return
     * @throws HessianProtocolException
     */
    @SuppressWarnings({ "rawtypes" })
    @Override
    public Serializer getSerializer(Class type) throws HessianProtocolException {
        if (this.type.isAssignableFrom(type)) {
            return doGetSerializer(type);
        }
        return null;
    }
    
    public abstract Serializer doGetSerializer(
            @SuppressWarnings("rawtypes") Class type)
            throws HessianProtocolException;
    
    /**
     * @param cl
     * @return
     * @throws HessianProtocolException
     */
    @SuppressWarnings({ "rawtypes" })
    @Override
    public Deserializer getDeserializer(Class type)
            throws HessianProtocolException {
        if (this.type.isAssignableFrom(type)) {
            return doGetDeserializer(type);
        }
        return null;
    }
    
    public abstract Deserializer doGetDeserializer(
            @SuppressWarnings("rawtypes") Class type)
            throws HessianProtocolException;
    
}
