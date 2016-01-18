/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2015年3月4日
 * <修改描述:>
 */
package com.tx.core.model;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import com.tx.core.exceptions.SILException;
import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.util.AopTargetUtils;

/**
 * 基础类能够获取该类的子类的注解类型
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2015年3月4日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class ParameterizedTypeReference<T> extends
        AbstractParameterizedTypeReference<T> {
    
    protected ParameterizedTypeReference() {
        
        if (!AopTargetUtils.isProxy(this)) {
            Type rawType = getClassRawType(this.getClass());
            AssertUtils.notNull(rawType, "rawType is null.");
            
            setRawType(rawType);
        }
    }
    
    private Type getClassRawType(@SuppressWarnings("rawtypes") Class objectClass) {
        Type rawTypeTemp = objectClass.getGenericSuperclass();
        if (rawTypeTemp instanceof ParameterizedType) {
            rawTypeTemp = ((ParameterizedType) rawTypeTemp).getActualTypeArguments()[0];
            if (rawTypeTemp instanceof ParameterizedType) {
                rawTypeTemp = ((ParameterizedType) rawTypeTemp).getRawType();
            }
        } else {
            if (Object.class.equals(objectClass)) {
                throw new SILException(
                        "TypeHandler '"
                                + getClass()
                                + "' extends TypeReference but misses the type parameter. "
                                + "Remove the extension or add a type parameter to it.");
            } else {
                Class<?> superClass = objectClass.getSuperclass();
                rawTypeTemp = getClassRawType(superClass);
            }
        }
        return rawTypeTemp;
    }
}
