/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2015年3月4日
 * <修改描述:>
 */
package com.tx.core.util.typereference;

import org.springframework.core.ResolvableType;

/**
 * 基础类能够获取该类的子类的注解类型<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2015年3月4日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public abstract class ParameterizedTypeReference<T> {
    
    /** 实体类类型 */
    private final Class<T> entityClass;
    
    /** 构造方法 */
    @SuppressWarnings("unchecked")
    public ParameterizedTypeReference() {
        ResolvableType resolvableType = ResolvableType.forClass(getClass());
        entityClass = (Class<T>) resolvableType
                .as(ParameterizedTypeReference.class).getGeneric().resolve();
    }
    
    /**
     * 获取参数类型<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return Class<T> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public Class<T> getType() {
        return entityClass;
    }
    
    /**
     * 获取泛型参数类型<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return Class<T> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public Class<T> getEntityClass() {
        return entityClass;
    }
    
    /**
     * 获取泛型参数类型<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return Class<T> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public Class<T> getRawType(){
        return entityClass;
    }
    
    //
    //private boolean rawTypeNullAble = false;
    //    
    //    protected ParameterizedTypeReference() {
    //        this(false);
    //    }
    //    
    //    protected ParameterizedTypeReference(boolean rawTypeNullAble) {
    //        //this.rawTypeNullAble = rawTypeNullAble;
    //        if (!AopTargetUtils.isProxy(this)) {
    //            Type rawType = getClassRawType(this.getClass());
    //            if (!rawTypeNullAble) {
    //                //如果不能为空，则进行检查
    //                AssertUtils.notNull(rawType, "rawType is null.");
    //            }
    //            setRawType(rawType);
    //        }
    //    }
    //    
    //    private Type getClassRawType(@SuppressWarnings("rawtypes") Class objectClass) {
    //        Type rawTypeTemp = objectClass.getGenericSuperclass();
    //        if (rawTypeTemp instanceof ParameterizedType) {
    //            rawTypeTemp = ((ParameterizedType) rawTypeTemp).getActualTypeArguments()[0];
    //            if (rawTypeTemp instanceof ParameterizedType) {
    //                rawTypeTemp = ((ParameterizedType) rawTypeTemp).getRawType();
    //            }
    //        } else {
    //            Class<?> superClass = objectClass.getSuperclass();
    //            rawTypeTemp = getClassRawType(superClass);
    //            //            if (Object.class.equals(objectClass)) {
    //            //                throw new SILException(
    //            //                        "TypeHandler '"
    //            //                                + getClass()
    //            //                                + "' extends TypeReference but misses the type parameter. "
    //            //                                + "Remove the extension or add a type parameter to it.");
    //            //            } else {
    //            //                Class<?> superClass = objectClass.getSuperclass();
    //            //                rawTypeTemp = getClassRawType(superClass);
    //            //            }
    //        }
    //        return rawTypeTemp;
    //    }
}
