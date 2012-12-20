/*
 * 描          述:  <描述>
 * 修  改   人:  PengQingyang
 * 修改时间:  2012-12-9
 * <修改描述:>
 */
package com.tx.core.mybatis.generator.model;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

/**
 * jpa实体解析结果类
 * <功能详细描述>
 * 
 * @author  PengQingyang
 * @version  [版本号, 2012-12-9]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class JpaMetaClass {
    
    /**
      * 获取该类解析器的构造方法
      * @param type
      * @return [参数说明]
      * 
      * @return JpaMetaClass [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public static JpaMetaClass forClass(Class<?> type) {
        return new JpaMetaClass(type);
    }
    
    private JpaMetaClass(Class<?> type) {
        //解析实体对象，获取类名，对应数据库表名等信息
        parseForEntity(type);
        
        //获取对应实体的的所有getter方法
        MetaClass metaClass = MetaClass.forClass(type);
        this.getterNames = Arrays.asList(metaClass.getGetterNames());
        
        for (String getterNameTemp : this.getterNames) {
            
            PropertyDescriptor propertyDescriptor = BeanUtils.getPropertyDescriptor(type,
                    getterNameTemp);
            if(propertyDescriptor == null){
                this.ignoreGetterMapping.put(getterNameTemp, true);
                continue;
            }
            Method methodTemp = PropertyUtils.getReadMethod(propertyDescriptor);
            Class<?> propertyType = metaClass.getGetterType(getterNameTemp);
            Field getterField = FieldUtils.getField(type, getterNameTemp, true);
            
            this.getterMethodMapping.put(getterNameTemp, methodTemp);
            this.getterFieldMapping.put(getterNameTemp, getterField);
            
            this.propertyDescriptorMapping.put(getterNameTemp,
                    propertyDescriptor);
            this.getterReturnTypeMapping.put(getterNameTemp, propertyType);
            
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
        if (getterMethod.isAnnotationPresent(JoinColumn.class)
                || (getterField != null && getterField.isAnnotationPresent(JoinColumn.class))) {
            JoinColumn columnAnn = getterMethod.getAnnotation(JoinColumn.class);
            if (columnAnn == null && getterField != null) {
                columnAnn = getterField.getAnnotation(JoinColumn.class);
            }
            this.columnNameMapping.put(propertyName, columnAnn.name());
            this.joinColumnAnnoMapping.put(propertyName, columnAnn);
        } else if (getterMethod.isAnnotationPresent(Column.class)
                || (getterField != null && getterField.isAnnotationPresent(Column.class))) {
            Column columnAnn = getterMethod.getAnnotation(Column.class);
            if (columnAnn == null && getterField != null) {
                columnAnn = getterField.getAnnotation(Column.class);
            }
            this.columnNameMapping.put(propertyName, columnAnn.name());
            this.columnAnnoMapping.put(propertyName, columnAnn);
        } else {
            this.columnNameMapping.put(propertyName, propertyName);
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
        
        //如果主键类型不为String类型，这里输入一条解析警告信息：提醒核查生成的信息是否合法
        if (!String.class.equals(propertyType)) {
            addParseMessage("warnInfo: @Id type is not String");
        }
        
        this.idPropertyName = propertyName;
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
        sb.append("t").append(this.tableName.charAt(0));
        for (int i = 1; i < this.tableName.length(); i++) {
            if (Character.isUpperCase(this.tableName.charAt(i))) {
                sb.append(Character.toLowerCase(this.tableName.charAt(i)));
            }
        }
        this.simpleTableName = sb.toString();
    }
    
    /** 实体解析信息 */
    private StringBuffer parseMessage = new StringBuffer();
    
    /** 添加实体解析信息 */
    public void addParseMessage(String message) {
        parseMessage.append(message).append("\n");
    }
    
    /** 实体类型:包括报名的类全名 */
    private String entityTypeName;
    
    /** 去掉包名的类名:并转换首字母为小写 */
    private String lowerCaseFirstCharEntitySimpleName;
    
    /** 去掉包名的类名:并转换首字母为小写 */
    private String entitySimpleName;
    
    /** 对应表名 */
    public String tableName;
    
    /** 生成表名的简写，根据对象名生成  */
    public String simpleTableName;
    
    /** Id注解对应的属性名 */
    private String idPropertyName = "";
    
    private String generator;
    
    private GenerationType generatorType;
    
    /** getter名列表 */
    private List<String> getterNames;
    
    /** 对应的getter方法 */
    private Map<String, Method> getterMethodMapping = new HashMap<String, Method>();
    
    /** 对应getter的属性字段  */
    private Map<String, Field> getterFieldMapping = new HashMap<String, Field>();
    
    /** getter对应的属性类型  */
    private Map<String, Class<?>> getterReturnTypeMapping = new HashMap<String, Class<?>>();
    
    /**
     * 字段名映射
     */
    private Map<String, String> columnNameMapping = new HashMap<String, String>();
    
    private Map<String, Column> columnAnnoMapping = new HashMap<String, Column>();
    
    private Map<String, JoinColumn> joinColumnAnnoMapping = new HashMap<String, JoinColumn>();
    
    /** 属性描述映射 */
    private Map<String, PropertyDescriptor> propertyDescriptorMapping = new HashMap<String, PropertyDescriptor>();
    
    /** 是否忽略属性的映射 */
    private Map<String, Boolean> ignoreGetterMapping = new HashMap<String, Boolean>();
    
    /**
     * @return 返回 parseMessage
     */
    public StringBuffer getParseMessage() {
        return parseMessage;
    }
    
    /**
     * @return 返回 entityTypeName
     */
    public String getEntityTypeName() {
        return entityTypeName;
    }
    
    /**
     * @param 对entityTypeName进行赋值
     */
    public void setEntityTypeName(String entityTypeName) {
        this.entityTypeName = entityTypeName;
    }
    
    /**
     * @return 返回 lowerCaseFirstCharEntitySimpleName
     */
    public String getLowerCaseFirstCharEntitySimpleName() {
        return lowerCaseFirstCharEntitySimpleName;
    }
    
    /**
     * @param 对lowerCaseFirstCharEntitySimpleName进行赋值
     */
    public void setLowerCaseFirstCharEntitySimpleName(
            String lowerCaseFirstCharEntitySimpleName) {
        this.lowerCaseFirstCharEntitySimpleName = lowerCaseFirstCharEntitySimpleName;
    }
    
    /**
     * @return 返回 entitySimpleName
     */
    public String getEntitySimpleName() {
        return entitySimpleName;
    }
    
    /**
     * @param 对entitySimpleName进行赋值
     */
    public void setEntitySimpleName(String entitySimpleName) {
        this.entitySimpleName = entitySimpleName;
    }
    
    /**
     * @return 返回 tableName
     */
    public String getTableName() {
        return tableName;
    }
    
    /**
     * @param 对tableName进行赋值
     */
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
    
    /**
     * @return 返回 simpleTableName
     */
    public String getSimpleTableName() {
        return simpleTableName;
    }
    
    /**
     * @param 对simpleTableName进行赋值
     */
    public void setSimpleTableName(String simpleTableName) {
        this.simpleTableName = simpleTableName;
    }
    
    /**
     * @return 返回 idPropertyName
     */
    public String getIdPropertyName() {
        return idPropertyName;
    }
    
    /**
     * @param 对idPropertyName进行赋值
     */
    public void setIdPropertyName(String idPropertyName) {
        this.idPropertyName = idPropertyName;
    }
    
    /**
     * @return 返回 generator
     */
    public String getGenerator() {
        return generator;
    }
    
    /**
     * @param 对generator进行赋值
     */
    public void setGenerator(String generator) {
        this.generator = generator;
    }
    
    /**
     * @return 返回 generatorType
     */
    public GenerationType getGeneratorType() {
        return generatorType;
    }
    
    /**
     * @param 对generatorType进行赋值
     */
    public void setGeneratorType(GenerationType generatorType) {
        this.generatorType = generatorType;
    }
    
    /**
     * @return 返回 ignoreGetterMapping
     */
    public Map<String, Boolean> getIgnoreGetterMapping() {
        return ignoreGetterMapping;
    }
    
    /**
     * @param 对ignoreGetterMapping进行赋值
     */
    public void setIgnoreGetterMapping(Map<String, Boolean> ignoreGetterMapping) {
        this.ignoreGetterMapping = ignoreGetterMapping;
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
    
    /**
     * @return 返回 getterMethodMapping
     */
    public Map<String, Method> getGetterMethodMapping() {
        return getterMethodMapping;
    }
    
    /**
     * @param 对getterMethodMapping进行赋值
     */
    public void setGetterMethodMapping(Map<String, Method> getterMethodMapping) {
        this.getterMethodMapping = getterMethodMapping;
    }
    
    /**
     * @return 返回 getterReturnTypeMapping
     */
    public Map<String, Class<?>> getGetterReturnTypeMapping() {
        return getterReturnTypeMapping;
    }
    
    /**
     * @param 对getterReturnTypeMapping进行赋值
     */
    public void setGetterReturnTypeMapping(
            Map<String, Class<?>> getterReturnTypeMapping) {
        this.getterReturnTypeMapping = getterReturnTypeMapping;
    }
    
    /**
     * @return 返回 propertyDescriptorMapping
     */
    public Map<String, PropertyDescriptor> getPropertyDescriptorMapping() {
        return propertyDescriptorMapping;
    }
    
    /**
     * @param 对propertyDescriptorMapping进行赋值
     */
    public void setPropertyDescriptorMapping(
            Map<String, PropertyDescriptor> propertyDescriptorMapping) {
        this.propertyDescriptorMapping = propertyDescriptorMapping;
    }
    
    /**
     * @return 返回 columnNameMapping
     */
    public Map<String, String> getColumnNameMapping() {
        return columnNameMapping;
    }
    
    /**
     * @param 对columnNameMapping进行赋值
     */
    public void setColumnNameMapping(Map<String, String> columnNameMapping) {
        this.columnNameMapping = columnNameMapping;
    }
    
    /**
     * @return 返回 getterFieldMapping
     */
    public Map<String, Field> getGetterFieldMapping() {
        return getterFieldMapping;
    }
    
    /**
     * @param 对getterFieldMapping进行赋值
     */
    public void setGetterFieldMapping(Map<String, Field> getterFieldMapping) {
        this.getterFieldMapping = getterFieldMapping;
    }
    
    /**
     * @return 返回 columnAnnoMapping
     */
    public Map<String, Column> getColumnAnnoMapping() {
        return columnAnnoMapping;
    }
    
    /**
     * @param 对columnAnnoMapping进行赋值
     */
    public void setColumnAnnoMapping(Map<String, Column> columnAnnoMapping) {
        this.columnAnnoMapping = columnAnnoMapping;
    }
    
    /**
     * @return 返回 joinColumnAnnoMapping
     */
    public Map<String, JoinColumn> getJoinColumnAnnoMapping() {
        return joinColumnAnnoMapping;
    }
    
    /**
     * @param 对joinColumnAnnoMapping进行赋值
     */
    public void setJoinColumnAnnoMapping(
            Map<String, JoinColumn> joinColumnAnnoMapping) {
        this.joinColumnAnnoMapping = joinColumnAnnoMapping;
    }
}
