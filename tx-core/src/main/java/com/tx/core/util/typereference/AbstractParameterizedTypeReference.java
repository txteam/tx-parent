///*
// * 描          述:  <描述>
// * 修  改   人:  Administrator
// * 修改时间:  2015年3月4日
// * <修改描述:>
// */
//package com.tx.core.util.typereference;
//
//import java.lang.reflect.Type;
//
///**
// * 基础类能够获取该类的子类的注解类型
// * <功能详细描述>
// * 
// * @author  Administrator
// * @version  [版本号, 2015年3月4日]
// * @see  [相关类/方法]
// * @since  [产品/模块版本]
// */
//public abstract class AbstractParameterizedTypeReference<T> {
//    
//    /** 泛型类型是否初始化 */
//    private boolean rawTypeInit = false;
//    
//    /** 泛型参数类型 */
//    private Type rawType;
//    
//    /**
//     * @param 对setRawType进行赋值
//     */
//    protected void setRawType(Type rawType) {
//        if (rawType != null) {
//            this.rawTypeInit = true;
//        }
//        this.rawType = rawType;
//    }
//    
//    /**
//     * @return 返回 rawTypeInit
//     */
//    protected boolean isRawTypeInit() {
//        return rawTypeInit;
//    }
//    
//    /**
//     * @return 返回 rawTypeInit
//     */
//    public Type getRawType() {
//        return this.rawType;
//    }
//    
//    /**
//     * @return 返回 rawTypeInit
//     */
//    public Class<?> getType() {
//        if (this.rawType instanceof Class<?>) {
//            return (Class<?>) this.rawType;
//        } else {
//            return null;
//        }
//    }
//}
