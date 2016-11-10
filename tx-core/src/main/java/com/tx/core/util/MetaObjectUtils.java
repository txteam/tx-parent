/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2016年11月10日
 * <修改描述:>
 */
package com.tx.core.util;

import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.ReflectorFactory;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.apache.ibatis.reflection.factory.ObjectFactory;
import org.apache.ibatis.reflection.wrapper.DefaultObjectWrapperFactory;
import org.apache.ibatis.reflection.wrapper.ObjectWrapperFactory;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;

import com.tx.core.exceptions.util.AssertUtils;

/**
 * MetaObject工具类<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2016年11月10日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public abstract class MetaObjectUtils {
    
    public static final ObjectFactory DEFAULT_OBJECT_FACTORY = new DefaultObjectFactory();
    
    private static final ObjectWrapperFactory DEFAULT_OBJECT_WRAPPER_FACTORY = new DefaultObjectWrapperFactory();
    
    private static final ReflectorFactory DEFAULT_REFLECTOR_FACTORY = new DefaultReflectorFactory();
    
    /**
      * 创建MetaObject对象<br/>
      *    一般适用于创建对象可能为Map的场景获取值<br/>
      *    参数允许为空.
      * <功能详细描述>
      * @param obj
      * @return [参数说明]
      * 
      * @return MetaObject [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public static MetaObject forObject(Object obj) {
        MetaObject metaObject = MetaObject.forObject(obj,
                DEFAULT_OBJECT_FACTORY,
                DEFAULT_OBJECT_WRAPPER_FACTORY,
                DEFAULT_REFLECTOR_FACTORY);
        return metaObject;
    }
    
    /**
      * 获取对象对应的BeanWrapper
      * <功能详细描述>
      * @param obj
      * @return [参数说明]
      * 
      * @return BeanWrapper [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public static BeanWrapper forBeanPropertyAccess(Object obj){
        AssertUtils.notNull(obj,"obj is null.");
        
        BeanWrapper bw = PropertyAccessorFactory.forBeanPropertyAccess(obj);
        return bw;
    }
}
