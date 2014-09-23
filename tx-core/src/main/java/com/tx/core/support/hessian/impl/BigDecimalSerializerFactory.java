/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2014年6月4日
 * <修改描述:>
 */
package com.tx.core.support.hessian.impl;

import java.math.BigDecimal;

import com.caucho.hessian.io.BigDecimalDeserializer;
import com.caucho.hessian.io.Deserializer;
import com.caucho.hessian.io.HessianProtocolException;
import com.caucho.hessian.io.Serializer;
import com.caucho.hessian.io.StringValueSerializer;
import com.tx.core.support.hessian.TypeHandleSerializerFactory;


 /**
  * BigDecimal类型的序列化工厂
  * <功能详细描述>
  * 
  * @author  Administrator
  * @version  [版本号, 2014年6月4日]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public class BigDecimalSerializerFactory extends TypeHandleSerializerFactory<BigDecimal> {
    
    /** <默认构造函数> */
    public BigDecimalSerializerFactory() {
        super(BigDecimal.class);
    }
    
    /**
     * @param type
     * @return
     * @throws HessianProtocolException
     */
    @SuppressWarnings("rawtypes")
    @Override
    public Serializer doGetSerializer(Class type)
            throws HessianProtocolException {
        return new StringValueSerializer();
    }



    /**
     * @param type
     * @return
     * @throws HessianProtocolException
     */
    @SuppressWarnings("rawtypes")
    @Override
    public Deserializer doGetDeserializer(Class type)
            throws HessianProtocolException {
        return new BigDecimalDeserializer();
    }    
}
