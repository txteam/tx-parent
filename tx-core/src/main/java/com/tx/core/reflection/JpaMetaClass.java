/*
 * 描          述:  <描述>
 * 修  改   人:  PengQingyang
 * 修改时间:  2012-12-9
 * <修改描述:>
 */
package com.tx.core.reflection;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.reflect.FieldUtils;
import org.apache.ibatis.reflection.MetaClass;
import org.apache.poi.hssf.record.formula.functions.T;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import com.tx.core.generator.model.ColumnInfo;
import com.tx.core.jdbc.sqlsource.SqlSource;

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
    @SuppressWarnings("rawtypes")
    private static WeakHashMap<Class<?>, JpaMetaClass<?>> mapping = new WeakHashMap<Class<?>, JpaMetaClass<?>>();
    
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
    public static <TYPE> JpaMetaClass<TYPE> forClass(Class<TYPE> type) {
        synchronized (type) {
            JpaMetaClass<TYPE> jpaMetaClass = null;
            if (mapping.containsKey(type)) {
                jpaMetaClass = (JpaMetaClass<TYPE>)mapping.get(type);
                return jpaMetaClass;
            }
            
            //简答的sqlSource源
            jpaMetaClass = new JpaMetaClass<TYPE>(type);
            //缓存起来
            mapping.put(type, jpaMetaClass);
            return jpaMetaClass;
        }
    }
    
    private JpaMetaClass(Class<T> type) {
        //解析实体对象，获取类名，对应数据库表名等信息
        parseForEntity(type);
        
        //获取对应实体的的所有getter方法
        MetaClass metaClass = MetaClass.forClass(type);
        this.getterNames = Arrays.asList(metaClass.getGetterNames());
        
        for (String getterNameTemp : this.getterNames) {
            PropertyDescriptor propertyDescriptor = BeanUtils.getPropertyDescriptor(type,
                    getterNameTemp);
            if (propertyDescriptor == null) {
                this.ignoreGetterMapping.put(getterNameTemp, true);
                continue;
            }
            Method methodTemp = PropertyUtils.getReadMethod(propertyDescriptor);
            Class<?> propertyType = metaClass.getGetterType(getterNameTemp);
            Field getterField = FieldUtils.getField(type, getterNameTemp, true);
            
            this.getterMethodMapping.put(getterNameTemp, methodTemp);
            this.getterTypeMapping.put(getterNameTemp, propertyType);
            
            //设置为不需要忽略，在方法解析中如果发现为被忽略字段，则重设置
            this.ignoreGetterMapping.put(getterNameTemp, false);
            
            parseMethod(getterNameTemp, propertyType, methodTemp, getterField);
        }
    }
    
    /**
      * 解析方法
      * <功能详细描述>
      * @param getterMethod [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    private void parseMethod(String propertyName, Class<?> propertyType,
            Method getterMethod, Field getterField) {
        //判断对应字段是否为id,如果为id，一并解析id相关jpa配置
        parseForId(propertyName, propertyType, getterMethod, getterField);
        
        //是否为忽略字段
        if (getterMethod.isAnnotationPresent(Transient.class)
                || (getterField != null && getterField.isAnnotationPresent(Transient.class))
                || Map.class.isAssignableFrom(propertyType)
                || Collection.class.isAssignableFrom(propertyType)) {
            this.ignoreGetterMapping.put(propertyName, true);
            return;
        }
        //忽略一对多关系字段
        if (getterMethod.isAnnotationPresent(OneToMany.class)
                || (getterField != null && getterField.isAnnotationPresent(OneToMany.class))) {
            this.ignoreGetterMapping.put(propertyName, true);
            return;
        }
        //忽略一对一，但不存在column的字段,
        if ((getterMethod.isAnnotationPresent(OneToOne.class) || (getterField != null && getterField.isAnnotationPresent(OneToOne.class)))
                && !(getterMethod.isAnnotationPresent(JoinColumn.class)
                        || getterMethod.isAnnotationPresent(Column.class)
                        || (getterField != null && getterField.isAnnotationPresent(JoinColumn.class)) || (getterField != null && getterField.isAnnotationPresent(Column.class)))) {
            this.ignoreGetterMapping.put(propertyName, true);
            return;
        }
        //忽略一对一，但不存在column的字段,
        if ((getterMethod.isAnnotationPresent(ManyToMany.class) || (getterField != null && getterField.isAnnotationPresent(ManyToMany.class)))
                && !(getterMethod.isAnnotationPresent(JoinColumn.class)
                        || getterMethod.isAnnotationPresent(Column.class)
                        || (getterField != null && getterField.isAnnotationPresent(JoinColumn.class)) || (getterField != null && getterField.isAnnotationPresent(Column.class)))) {
            this.ignoreGetterMapping.put(propertyName, true);
            return;
        }
        
        //识别属性对应字段
        if (getterMethod.isAnnotationPresent(Column.class)
                || (getterField != null && getterField.isAnnotationPresent(Column.class))) {
            Column columnAnn = getterMethod.getAnnotation(Column.class);
            if (columnAnn == null && getterField != null) {
                columnAnn = getterField.getAnnotation(Column.class);
            }
            this.columnNameMapping.put(propertyName, columnAnn.name());
            
            this.columnInfoMapping.put(propertyName, new ColumnInfo(columnAnn,
                    columnAnn.name(), getterField.getType(), propertyName, ""));
        } else if (getterMethod.isAnnotationPresent(JoinColumn.class)
                || (getterField != null && getterField.isAnnotationPresent(JoinColumn.class))) {
            JoinColumn columnAnn = getterMethod.getAnnotation(JoinColumn.class);
            if (columnAnn == null && getterField != null) {
                columnAnn = getterField.getAnnotation(JoinColumn.class);
            }
            this.columnNameMapping.put(propertyName, columnAnn.name());
            
            this.columnInfoMapping.put(propertyName, new ColumnInfo(null,
                    columnAnn.name(), getterField.getType(), propertyName, ""));
        } else {
            this.columnNameMapping.put(propertyName, propertyName);
            
            this.columnInfoMapping.put(propertyName, new ColumnInfo(null,
                    propertyName, getterField.getType(), propertyName, ""));
        }
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
    private void parseForId(String propertyName, Class<?> propertyType,
            Method getterMethod, Field getterField) {
        if (!getterMethod.isAnnotationPresent(Id.class)
                && (getterField == null || !getterField.isAnnotationPresent(Id.class))) {
            return;
        }
        
        this.pkPropertyName = propertyName;
        if (getterMethod.isAnnotationPresent(Generated.class)
                || (getterField != null && getterField.isAnnotationPresent(Generated.class))) {
            //
        }
        if (getterMethod.isAnnotationPresent(org.hibernate.annotations.Generated.class)
                || (getterField != null && getterField.isAnnotationPresent(org.hibernate.annotations.Generated.class))) {
            
        }
        if (getterMethod.isAnnotationPresent(GeneratedValue.class)
                || (getterField != null && getterField.isAnnotationPresent(GeneratedValue.class))) {
            GeneratedValue geNnno = getterMethod.getAnnotation(GeneratedValue.class);
            if (geNnno == null && getterField != null) {
                geNnno = getterField.getAnnotation(GeneratedValue.class);
            }
            this.generator = geNnno.generator();
            this.generatorType = geNnno.strategy();
        }
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
    private void parseForEntity(Class<?> type) {
        //获取实体类相关信息
        this.entityTypeName = type.getName();
        this.entitySimpleName = type.getSimpleName();
        this.lowerCaseFirstCharEntitySimpleName = StringUtils.uncapitalize(this.entitySimpleName);
        this.tableName = this.entitySimpleName;
        
        //获取jpa注解
        org.hibernate.annotations.Entity hiberEntityAnn = type.getAnnotation(org.hibernate.annotations.Entity.class);
        if (hiberEntityAnn != null) {
            //do nothing  如果要检查合法性可以将table标签放到这里来检查
        }
        org.hibernate.annotations.Table hibernateTableAnn = type.getAnnotation(org.hibernate.annotations.Table.class);
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
        this.simpleTableName = sb.toString();
    }
    
    private Class<T> type;
    
    private ClassReflector<T> classReflector;
    
    /** 实体类型:包括包名的类全名 */
    private String entityTypeName;
    
    /** 去掉包名的类名:并转换首字母为小写 */
    private String entitySimpleName;
    
    /** 对应表名 */
    public String tableName;
    
    /** 生成表名的简写，根据对象名生成  */
    public String simpleTableName;
    
    /** Id注解对应的属性名 */
    private String pkFieldName = "";
    
    /** getter名列表 */
    private List<String> getterNames;
    
    
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
        if ("TO".equals(simpleTableName.toUpperCase())) {
            simpleTableName = "TO_";
        }
        return simpleTableName;
    }
    
    /**
     * @return 返回 getterNames
     */
    public List<String> getGetterNames() {
        return getterNames;
    }
    
    /**
     * @param 对getterNames进行赋值
     */
    public void setGetterNames(List<String> getterNames) {
        this.getterNames = getterNames;
    }
}
