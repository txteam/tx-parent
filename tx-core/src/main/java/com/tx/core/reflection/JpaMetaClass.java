/*
 * 描          述:  <描述>
 * 修  改   人:  PengQingyang
 * 修改时间:  2012-12-9
 * <修改描述:>
 */
package com.tx.core.reflection;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.reflection.exception.JpaMetaClassNewInstanceException;
import com.tx.core.reflection.model.ColumnInfo;
import com.tx.core.util.JdbcUtils;

/**
 * jpa实体解析结果类
 * <功能详细描述>
 * 
 * @author  PengQingyang
 * @version  [版本号, 2012-12-9]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class JpaMetaClass<T> {
    
    /**
     * 本地资源缓存映射,采用弱引用的形式，以便及时回收一些使用不高的sqlSource
     */
    private static WeakHashMap<Class<?>, JpaMetaClass<?>> mapping = new WeakHashMap<Class<?>, JpaMetaClass<?>>();
    
    /**
      * 默认getterName是不包括仅有field没有get方法的字段
      *<功能详细描述>
      * @param type
      * @return [参数说明]
      * 
      * @return JpaMetaClass<TYPE> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public static <TYPE> JpaMetaClass<TYPE> forClass(Class<TYPE> type) {
        return forClass(type, false);
    }
    
    /**
      * 获取该类解析器的构造方法
      * @param type
      * @return [参数说明]
      * 
      * @return JpaMetaClass [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    @SuppressWarnings("unchecked")
    public static <TYPE> JpaMetaClass<TYPE> forClass(Class<TYPE> type,
            boolean isIncludeInaccessible) {
        AssertUtils.notNull(type, "type is null");
        
        synchronized (type) {
            JpaMetaClass<TYPE> jpaMetaClass = null;
            if (mapping.containsKey(type)) {
                jpaMetaClass = (JpaMetaClass<TYPE>) mapping.get(type);
                return jpaMetaClass;
            }
            
            //简答的sqlSource源
            jpaMetaClass = new JpaMetaClass<TYPE>(type, isIncludeInaccessible);
            //缓存起来
            mapping.put(type, jpaMetaClass);
            return jpaMetaClass;
        }
    }
    
    private JpaMetaClass(Class<T> type, boolean isIncludeInaccessible) {
        AssertUtils.notNull(type, "type is null");
        
        //解析类型
        this.type = type;
        this.classReflector = ClassReflector.forClass(type);
        
        //解析实体对象，获取类名，对应数据库表名等信息
        parseEntity();
        //解析pk主键
        parsePKGetter();
    }
    
    /**
     * 解析生成是否为id
     * <功能详细描述>
     * @param propertyName
     * @param getterMethod [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    private void parsePKGetter() {
        //解析所有的getterNames
        for (String getterNameTemp : this.classReflector.getGetterNames()) {
            if (!ReflectionUtils.isHasAnnotationForGetter(type,
                    getterNameTemp,
                    Id.class)) {
                return;
            }
            
            this.pkGetterName = getterNameTemp;
            this.pkGetterType = this.classReflector.getGetterType(getterNameTemp);
            
            //限定主键字段field必须具有get,set方法
            AssertUtils.isTrue(this.classReflector.getGetterMethod(getterNameTemp) != null,
                    new JpaMetaClassNewInstanceException(
                            "type:{} pkPropertyName:{} getterMethod is not exist.",
                            new Object[] { this.type, getterNameTemp }));
            AssertUtils.isTrue(this.classReflector.getSetterMethod(getterNameTemp) != null,
                    new JpaMetaClassNewInstanceException(
                            "type:{} pkPropertyName:{} setterMethod is not exist.",
                            new Object[] { this.type, getterNameTemp }));
            //主键类型应该为直接可以进行存取的类型
            AssertUtils.isTrue(JdbcUtils.isSupportedSimpleType(pkGetterType),
                    new JpaMetaClassNewInstanceException(
                            "type:{} pkPropertyName:{} getterType:{} is not supported.",
                            new Object[] { this.type, getterNameTemp,
                                    this.pkGetterType }));
            
            //主键生成策略
            //org.hibernate.annotations.Generated.class
            //Generated.class
            if (ReflectionUtils.isHasAnnotationForGetter(type,
                    getterNameTemp,
                    GeneratedValue.class)) {
                GeneratedValue geNnno = ReflectionUtils.getGetterAnnotation(type,
                        getterNameTemp,
                        GeneratedValue.class);
                
                this.generator = geNnno.generator();
                this.generationType = geNnno.strategy();
            }
            
            //一旦成功解析到第一个符合的主键即跳出
            return;
        }
        
        //是否存在名为id的getter
        for (String getterNameTemp : this.classReflector.getGetterNames()) {
            if (!"id".equals(getterNameTemp)) {
                continue;
            }
            
            this.pkGetterName = getterNameTemp;
            this.pkGetterType = this.classReflector.getGetterType(getterNameTemp);
            
            //如果注解不存在，但存在一个getter名为id，就把对应字段作为主键处理
            //限定主键字段field必须具有get,set方法
            AssertUtils.isTrue(this.classReflector.getGetterMethod(getterNameTemp) != null,
                    "type:{} pkPropertyName:{} getterMethod is not exist.",
                    new Object[] { this.type, getterNameTemp });
            AssertUtils.isTrue(this.classReflector.getSetterMethod(getterNameTemp) != null,
                    "type:{} pkPropertyName:{} setterMethod is not exist.",
                    new Object[] { this.type, getterNameTemp });
            //主键类型应该为直接可以进行存取的类型
            AssertUtils.isTrue(JdbcUtils.isSupportedSimpleType(pkGetterType),
                    new JpaMetaClassNewInstanceException(
                            "type:{} pkPropertyName:{} getterType:{} is not supported.",
                            new Object[] { this.type, getterNameTemp,
                                    this.pkGetterType }));
            
            //主键生成策略
            //org.hibernate.annotations.Generated.class
            //Generated.class
            if (ReflectionUtils.isHasAnnotationForGetter(type,
                    getterNameTemp,
                    GeneratedValue.class)) {
                GeneratedValue geNnno = ReflectionUtils.getGetterAnnotation(type,
                        getterNameTemp,
                        GeneratedValue.class);
                
                this.generator = geNnno.generator();
                this.generationType = geNnno.strategy();
            }
            
            //一旦成功解析到第一个符合的主键即跳出
            return;
        }
        
        throw new JpaMetaClassNewInstanceException(
                "type:{} pkGetter is not exist.", new Object[] { this.type });
    }
    
    /**
      * 解析实体
      * 解析实体对象，获取类名，对应数据库表名等信息
      * <功能详细描述>
      * @param type [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    private void parseEntity() {
        //获取实体类相关信息
        this.entityTypeName = this.type.getName();
        this.entitySimpleName = this.type.getSimpleName();
        this.tableName = this.entitySimpleName;
        
        //获取jpa注解
        org.hibernate.annotations.Entity hiberEntityAnn = this.type.getAnnotation(org.hibernate.annotations.Entity.class);
        if (hiberEntityAnn != null) {
            //do nothing  如果要检查合法性可以将table标签放到这里来检查
        }
        org.hibernate.annotations.Table hibernateTableAnn = this.type.getAnnotation(org.hibernate.annotations.Table.class);
        if (hibernateTableAnn != null) {
            //do nothing
        }
        Entity entityAnn = type.getAnnotation(Entity.class);
        if (entityAnn != null
                && !org.apache.commons.lang.StringUtils.isEmpty(entityAnn.name())) {
            this.tableName = entityAnn.name();
        }
        
        Table tableAnn = type.getAnnotation(Table.class);
        if (tableAnn != null
                && !org.apache.commons.lang.StringUtils.isEmpty(tableAnn.name())) {
            this.tableName = tableAnn.name();
        }
        
        //生成表名的简写
        StringBuilder sb = new StringBuilder();
        sb.append("t").append(this.entitySimpleName.charAt(0));
        for (int i = 1; i < this.entitySimpleName.length(); i++) {
            if (Character.isUpperCase(this.entitySimpleName.charAt(i))) {
                sb.append(Character.toLowerCase(this.entitySimpleName.charAt(i)));
            }
        }
        this.simpleTableName = sb.toString().toUpperCase();
        if ("TO".equals(this.simpleTableName)) {
            this.simpleTableName = "TO_";
        }
    }
    
    /** */
    private Class<T> type;
    
    private ClassReflector<T> classReflector;
    
    /** 实体类型:包括包名的类全名 */
    private String entityTypeName;
    
    /** 去掉包名的类名:并转换首字母为小写 */
    private String entitySimpleName;
    
    /** 对应表名 */
    private String tableName;
    
    /** 生成表名的简写，根据对象名生成  */
    private String simpleTableName;
    
    /** Id注解对应的属性名 */
    private String pkGetterName;
    
    /** 主键类型 */
    private Class<?> pkGetterType;
    
    /** 自动生成器类型 */
    private GenerationType generationType;
    
    /** 自动生成器 */
    private String generator;
    
    /** getter名及字段信息的映射关系 */
    private Map<String, ColumnInfo> getter2columnInfoMapping = new HashMap<String, ColumnInfo>();
    
    /** getter名及对应的字段类型关系 */
    private Map<String, Class<?>> getter2typeMapping = new HashMap<String, Class<?>>();
    
    /**
     * @return 返回 type
     */
    public Class<T> getType() {
        return type;
    }
    
    /**
     * @return 返回 classReflector
     */
    public ClassReflector<T> getClassReflector() {
        return classReflector;
    }
    
    /**
     * @return 返回 entityTypeName
     */
    public String getEntityTypeName() {
        return entityTypeName;
    }
    
    /**
     * @return 返回 entitySimpleName
     */
    public String getEntitySimpleName() {
        return entitySimpleName;
    }
    
    /**
     * @return 返回 tableName
     */
    public String getTableName() {
        return tableName;
    }
    
    /**
     * @return 返回 simpleTableName
     */
    public String getSimpleTableName() {
        return simpleTableName;
    }
    
    /**
     * @return 返回 pkFieldName
     */
    public String getPkGetterName() {
        return pkGetterName;
    }
    
    /**
     * @return 返回 generationType
     */
    public GenerationType getGenerationType() {
        return generationType;
    }
    
    /**
     * @return 返回 generator
     */
    public String getGenerator() {
        return generator;
    }
    
    /**
     * @return 返回 getterNames
     */
    public Set<String> getGetterNames() {
        return this.classReflector.getGetterNames();
    }
    
    /**
     * @return 返回 setterNames
     */
    public Set<String> getSetterNames() {
        return this.classReflector.getSetterNames();
    }
    
    /**
      * 获取getter对应类型
      *<功能详细描述>
      * @param getterName
      * @return [参数说明]
      * 
      * @return Class<?> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public Class<?> getGetterType(String getterName) {
        return this.classReflector.getGetterType(getterName);
    }
}
