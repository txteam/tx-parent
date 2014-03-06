/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2014年3月6日
 * <修改描述:>
 */
package com.tx.core.jdbc.model;

import java.lang.reflect.Method;

import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.reflection.JpaMetaClass;
import com.tx.core.util.JdbcUtils;

/**
 *  getter与column之间的映射消息
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2014年3月6日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class Getter2ColumnInfo {
    
    /** 是否是pk,主键字段 */
    private boolean id;
    
    /** 是否是直接能被mapHandler识别的类型 */
    private boolean simpleType;
    
    /** 对应字段名 */
    private String columnName;
    
    /** field名 */
    private String getterName;
    
    /** 类型 */
    private Class<?> getterType;
    
    /** 获取的方法 */
    private Method getterMethod;
    
    /** 非简单类型下外键的getter的getter对column关系信息 */
    private Getter2ColumnInfo foreignKeyGetter2ColumnInfo;
    
    /** <默认构造函数> */
    public Getter2ColumnInfo() {
        super();
    }
    
    /** <默认构造函数> */
    public Getter2ColumnInfo(JpaMetaClass<?> jpaMetaClass, String getterName) {
        super();
        AssertUtils.notNull(jpaMetaClass, "jpaMetaClass is null");
        AssertUtils.notEmpty(getterName, "getterName is empty");
        AssertUtils.isTrue(jpaMetaClass.getGetterNames().contains(getterName),
                "jpaMetaClass:{} not contains getterName:{}",
                new Object[] { jpaMetaClass.getEntityTypeName(), getterName });
        
        this.getterName = getterName;
        this.id = getterName.equals(jpaMetaClass.getPkGetterName());
        this.simpleType = JdbcUtils.isSupportedSimpleType(jpaMetaClass.getGetterType(getterName));
        this.getterType = jpaMetaClass.getGetterType(getterName);
        this.getterMethod = jpaMetaClass.getClassReflector()
                .getGetterMethod(getterName);
        this.columnName = jpaMetaClass.getGetter2columnInfoMapping()
                .get(getterName)
                .getColumnName()
                .toUpperCase();
        
        if (!this.simpleType) {
            JpaMetaClass<?> foreignKeyMetaClass = JpaMetaClass.forClass(this.getterType);
            String foreignKeyPkName = foreignKeyMetaClass.getPkGetterName();
            Class<?> foreignKeyPkType = foreignKeyMetaClass.getGetterType(foreignKeyPkName);
            
            JdbcUtils.isSupportedSimpleType(foreignKeyPkType);
            AssertUtils.isTrue(JdbcUtils.isSupportedSimpleType(foreignKeyPkType),
                    "pk type is not simpleType.sourceType:{} sourceGetterName:{} foreignPkName:{} foreignPkType:{}",
                    new Object[] { jpaMetaClass.getEntityTypeName(),
                            getterName, foreignKeyPkName, foreignKeyPkType });
        }
    }
    
    /**
     * @return 返回 id
     */
    public boolean isId() {
        return id;
    }
    
    /**
     * @param 对id进行赋值
     */
    public void setId(boolean id) {
        this.id = id;
    }
    
    /**
     * @return 返回 simpleType
     */
    public boolean isSimpleType() {
        return simpleType;
    }
    
    /**
     * @param 对simpleType进行赋值
     */
    public void setSimpleType(boolean simpleType) {
        this.simpleType = simpleType;
    }
    
    /**
     * @return 返回 getterName
     */
    public String getGetterName() {
        return getterName;
    }
    
    /**
     * @param 对getterName进行赋值
     */
    public void setGetterName(String getterName) {
        this.getterName = getterName;
    }
    
    /**
     * @return 返回 getterType
     */
    public Class<?> getGetterType() {
        return getterType;
    }
    
    /**
     * @param 对getterType进行赋值
     */
    public void setGetterType(Class<?> getterType) {
        this.getterType = getterType;
    }
    
    /**
     * @return 返回 getterMethod
     */
    public Method getGetterMethod() {
        return getterMethod;
    }
    
    /**
     * @param 对getterMethod进行赋值
     */
    public void setGetterMethod(Method getterMethod) {
        this.getterMethod = getterMethod;
    }
    
    /**
     * @return 返回 foreignKeyGetter2ColumnInfo
     */
    public Getter2ColumnInfo getForeignKeyGetter2ColumnInfo() {
        return foreignKeyGetter2ColumnInfo;
    }
    
    /**
     * @param 对foreignKeyGetter2ColumnInfo进行赋值
     */
    public void setForeignKeyGetter2ColumnInfo(
            Getter2ColumnInfo foreignKeyGetter2ColumnInfo) {
        this.foreignKeyGetter2ColumnInfo = foreignKeyGetter2ColumnInfo;
    }
    
    /**
     * @return 返回 columnName
     */
    public String getColumnName() {
        return columnName;
    }
    
    /**
     * @param 对columnName进行赋值
     */
    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }
}
