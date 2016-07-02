/*
 * 描          述:  <描述>
 * 修  改   人:  PengQingyang
 * 修改时间:  2012-12-9
 * <修改描述:>
 */
package com.tx.core.reflection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;

import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.reflection.exception.JpaMetaClassNewInstanceException;
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
    
    /** */
    private Class<T> type;
    
    private ClassReflector<T> classReflector;
    
    /** 实体类型:包括包名的类全名 */
    private String entityTypeName;
    
    /** 去掉包名的类名:并转换首字母为小写 */
    private String entitySimpleName;
    
    /** 所在模块包名 */
    private String modulePackageName;
    
    /** 所在模块包简写名 */
    private String modulePackageSimpleName;
    
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
    private Map<String, JpaColumnInfo> getter2columnInfoMapping = new LinkedHashMap<String, JpaColumnInfo>();
    
    /** 数据库字段和getter之间的映射 */
    private Map<String, String> column2realGetterMapping = new LinkedHashMap<String, String>();
    
    /**
     * <默认构造函数>
     */
    private JpaMetaClass(Class<T> type, boolean isIncludeInaccessible) {
        AssertUtils.notNull(type, "type is null");
        
        //解析类型
        this.type = type;
        this.classReflector = ClassReflector.forClass(type);
        
        //解析实体对象，获取类名，对应数据库表名等信息
        parseEntity();
        //解析所有的getter
        parseGetters();
        //解析pk主键
        parsePK();
    }
    
    /**
     * 私有
     */
    private JpaMetaClass(Class<T> type) {
        AssertUtils.notNull(type, "type is null");
        
        //解析类型
        this.type = type;
        this.classReflector = ClassReflector.forClass(type);
        
        //解析实体对象，获取类名，对应数据库表名等信息
        parseEntity();
        //解析pk主键
        parsePK();
    }
    
    /**
      * 解析jap对象的getter形成getter到column的映射关系(realGetter与getter区别需要明明确)
      *<功能详细描述> [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    private void parseGetters() {
        //解析所有的getterNames
        Map<String, JpaColumnInfo> getter2columnInfoMapping = new LinkedHashMap<String, JpaColumnInfo>();
        Map<String, String> column2realGetterMapping = new LinkedHashMap<String, String>();
        
        for (String getterNameTemp : this.classReflector.getGetterNames()) {
            //是否需要忽略对应字段
            if (isNeedSkip(type,
                    getterNameTemp,
                    this.classReflector.getGetterType(getterNameTemp))) {
                continue;
            }
            
            Class<?> getterType = this.classReflector.getGetterType(getterNameTemp);
            JpaColumnInfo jpaColumnInfo = parseGetter(getterNameTemp,
                    getterType,
                    type,
                    this.classReflector);
            
            //将解析结果压入
            column2realGetterMapping.put(jpaColumnInfo.getColumnName()
                    .toUpperCase(), jpaColumnInfo.getRealGetterName());
            getter2columnInfoMapping.put(getterNameTemp, jpaColumnInfo);
        }
        
        List<String> getterNameList = new ArrayList<>(
                getter2columnInfoMapping.keySet());
        Collections.sort(getterNameList, nameComparator);
        for (String keyTemp : getterNameList) {
            JpaColumnInfo columnInfoTemp = getter2columnInfoMapping.get(keyTemp);
            this.getter2columnInfoMapping.put(keyTemp, columnInfoTemp);
        }
        List<String> columnNameList = new ArrayList<>(
                column2realGetterMapping.keySet());
        Collections.sort(columnNameList, nameComparator);
        for (String keyTemp : columnNameList) {
            String getterNameTemp = column2realGetterMapping.get(keyTemp);
            this.column2realGetterMapping.put(keyTemp, getterNameTemp);
        }
    }
    
    /**
      * 解析getter
      *<功能详细描述>
      * @param getterName
      * @param getterType
      * @param type
      * @param classReflector
      * @return [参数说明]
      * 
      * @return JpaColumnInfo [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    private JpaColumnInfo parseGetter(String getterName, Class<?> getterType,
            Class<T> type, ClassReflector<T> classReflector) {
        //解析所有的getterNames
        JpaColumnInfo jpaColumnInfo = new JpaColumnInfo();
        
        //jpa对象默认属性设置
        jpaColumnInfo.setGetterName(getterName);
        jpaColumnInfo.setGetterType(getterType);
        jpaColumnInfo.setColumnName(getterName.toUpperCase());
        jpaColumnInfo.setColumnComment("");
        jpaColumnInfo.setNullable(true);
        jpaColumnInfo.setUnique(false);
        jpaColumnInfo.setRealGetterName(getterName);
        jpaColumnInfo.setRealGetterType(getterType);
        //设置对应数据库字段类型长度等几个
        jpaColumnInfo.setLength(255);
        jpaColumnInfo.setPrecision(0);
        jpaColumnInfo.setScale(0);
        
        //根据是否为简单类型
        if (JdbcUtils.isSupportedSimpleType(getterType)) {
            jpaColumnInfo.setSimpleType(true);
        } else {
            jpaColumnInfo.setSimpleType(false);
        }
        
        //是否存在Column注解
        if (ReflectionUtils.isHasAnnotationForGetter(type,
                getterName,
                ManyToOne.class)
                || ReflectionUtils.isHasAnnotationForGetter(type,
                        getterName,
                        OneToOne.class)
                || !JdbcUtils.isSupportedSimpleType(getterType)) {
            //关联字段默认长度为64，如果注解中写了则进行覆盖
            jpaColumnInfo.setLength(64);
            
            //断言为非简单类型
            if (!JdbcUtils.isSupportedSimpleType(getterType) && !getterType.isEnum()) {
                //关联字段的类型解析结果
                //为了避免无限循环调用，这里使用了一个内置的特殊方法，不会用到缓存
                @SuppressWarnings({ "unchecked", "rawtypes" })
                JpaMetaClass<?> getterTypeJpaMetaClass = new JpaMetaClass(
                        getterType);
                //由于在解析过程中已经限定了主键字段仅支持简单类型的字段，所以这里可以简单的就这样进行限制和设定
                jpaColumnInfo.setRealGetterName(getterName + "."
                        + getterTypeJpaMetaClass.getPkGetterName());
                jpaColumnInfo.setRealGetterType(getterTypeJpaMetaClass.getPkGetterType());
                
                jpaColumnInfo.setForeignKeyGetterName(getterTypeJpaMetaClass.getPkGetterName());
                jpaColumnInfo.setForeignKeyGetterType(getterTypeJpaMetaClass.getPkGetterType());
            }
            
            //这里为了兼容处理不作注解的严格兼容行判断，允许存在Column的情况
            //AssertUtils.isTrue(!JdbcUtils.isSupportedSimpleType(getterType),
            //new JpaMetaClassNewInstanceException(
            //"存在ManayToOne,OneToOne注解的字段不应该存在注解@Column应该为JoinColumn.type:{},getterName:{},getterType:{}",
            //new Object[] { type, getterName, getterType }));
            //存在Column注解并且为简单类型时
            if (ReflectionUtils.isHasAnnotationForGetter(type,
                    getterName,
                    Column.class)) {
                processWhenColumnAnnotationExist(getterName,
                        type,
                        jpaColumnInfo);
            }
            
            //当JoinColumn存在时
            if (ReflectionUtils.isHasAnnotationForGetter(type,
                    getterName,
                    JoinColumn.class)) {
                processWhenJoinColumnExist(getterName, type, jpaColumnInfo);
            }
        } else {
            //非关联字段简单类型默认长度为64，如果注解中写了则进行覆盖
            jpaColumnInfo.setLength(createColumnLength(getterName));
            
            //JdbcUtils.isSupportedSimpleType(type)
            //为简单类型且不存在OneToOne ManyToOne时的解析方案
            //当JoinColumn存在时
            if (ReflectionUtils.isHasAnnotationForGetter(type,
                    getterName,
                    Column.class)) {
                processWhenColumnAnnotationExist(getterName,
                        type,
                        jpaColumnInfo);
            }
        }
        
        return jpaColumnInfo;
    }
    
    /** 
     * 当存在JoinColumn注解时
     *<功能详细描述>
     * @param getterName
     * @param type
     * @param jpaColumnInfo [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    private void processWhenJoinColumnExist(String getterName, Class<T> type,
            JpaColumnInfo jpaColumnInfo) {
        JoinColumn joinColumnAnno = ReflectionUtils.getGetterAnnotation(type,
                getterName,
                JoinColumn.class);
        
        if (!StringUtils.isEmpty(joinColumnAnno.columnDefinition())) {
            jpaColumnInfo.setColumnComment(joinColumnAnno.columnDefinition());
        }
        if (!StringUtils.isEmpty(joinColumnAnno.name())) {
            jpaColumnInfo.setColumnName(joinColumnAnno.name().toUpperCase());
        }
    }
    
    /** 
     * 当存在Colume注解时的解析办法<br/>
     *<功能详细描述>
     * @param getterName
     * @param type
     * @param jpaColumnInfo [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    private void processWhenColumnAnnotationExist(String getterName,
            Class<T> type, JpaColumnInfo jpaColumnInfo) {
        Column columnAnno = ReflectionUtils.getGetterAnnotation(type,
                getterName,
                Column.class);
        
        if (!StringUtils.isEmpty(columnAnno.columnDefinition())) {
            jpaColumnInfo.setColumnComment(columnAnno.columnDefinition());
        }
        if (!StringUtils.isEmpty(columnAnno.name())) {
            jpaColumnInfo.setColumnName(columnAnno.name().toUpperCase());
        }
        if (columnAnno.length() != 255) {
            jpaColumnInfo.setLength(columnAnno.length());
        }
        jpaColumnInfo.setPrecision(columnAnno.precision());
        jpaColumnInfo.setScale(columnAnno.scale());
    }
    
    /**
      * 字段长度默认生成器<br/>
      *<功能详细描述>
      * @param propertyName
      * @return [参数说明]
      * 
      * @return int [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    private int createColumnLength(String getterName) {
        if (StringUtils.endsWithIgnoreCase(getterName, "id")) {
            return 64;
        } else if (StringUtils.endsWithIgnoreCase(getterName, "desc")
                || StringUtils.endsWithIgnoreCase(getterName, "description")
                || StringUtils.endsWithIgnoreCase(getterName, "remark")) {
            return 2000;
        } else if (StringUtils.endsWithIgnoreCase(getterName, "name")
                || StringUtils.endsWithIgnoreCase(getterName, "code")) {
            return 64;
        } else {
            return 255;
        }
    }
    
    /**
     * 是否需要跳过对应类型<br/>
     *<功能详细描述>
     * @param type
     * @param getterName
     * @param getterType
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    private boolean isNeedSkip(Class<T> type, String getterName,
            Class<?> getterType) {
        if (ReflectionUtils.isHasAnnotationForGetter(type,
                getterName,
                Transient.class)) {
            return true;
        }
        //由于simpleSqlSource不处理过于复杂的对象关联，所以存在oneToManay,ManayToManay也一并忽略
        if (ReflectionUtils.isHasAnnotationForGetter(type,
                getterName,
                OneToMany.class)
                || ReflectionUtils.isHasAnnotationForGetter(type,
                        getterName,
                        ManyToMany.class)) {
            return true;
        }
        
        //由于simpleSqlSource不处理过于复杂的对象关联，所以存在oneToManay,ManayToManay也一并忽略
        if ((ReflectionUtils.isHasAnnotationForGetter(type,
                getterName,
                OneToMany.class) || ReflectionUtils.isHasAnnotationForGetter(type,
                getterName,
                ManyToMany.class))
                && ReflectionUtils.isHasAnnotationForGetter(getterType,
                        getterName,
                        PrimaryKeyJoinColumn.class)) {
            return true;
        }
        
        //在存在ManyToOne或OneToOne同时又存在PrimaryKeyJoinColumn
        //认为是比较特殊的情况不进行解析
        
        return false;
    }
    
    /**
     * 解析主键
     *     主键对应getter应当存在setter:在该类中设定。
     *     如果不存在则抛出异常
     * <功能详细描述>
     * @param propertyName
     * @param getterMethod [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    private void parsePK() {
        //解析所有的getterNames
        for (String getterNameTemp : this.classReflector.getGetterNames()) {
            if (!ReflectionUtils.isHasAnnotationForGetter(type,
                    getterNameTemp,
                    Id.class)) {
                continue;
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
        
        //将类型截取掉
        this.modulePackageName = StringUtils.substringBeforeLast(this.type.getName(),
                ".");
        //将所在包
        this.modulePackageName = StringUtils.substringBeforeLast(this.modulePackageName,
                ".");
        //截取最后一个包名作为模块名
        String[] packages = StringUtils.splitByWholeSeparator(this.modulePackageName,
                ".");
        this.modulePackageSimpleName = packages[packages.length - 1];
        
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
     * @return 返回 pkGetterType
     */
    public Class<?> getPkGetterType() {
        return pkGetterType;
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
    
    /**
     * @return 返回 getter2columnInfoMapping
     */
    @SuppressWarnings("unchecked")
    public Map<String, JpaColumnInfo> getGetter2columnInfoMapping() {
        return MapUtils.unmodifiableMap(getter2columnInfoMapping);
    }
    
    /**
     * @return 返回 column2getterMapping
     */
    @SuppressWarnings("unchecked")
    public Map<String, String> getColumn2getterMapping() {
        return MapUtils.unmodifiableMap(column2realGetterMapping);
    }
    
    /**
     * @return 返回 modulePackageName
     */
    public String getModulePackageName() {
        return modulePackageName;
    }
    
    /**
     * @return 返回 modulePackageSimpleName
     */
    public String getModulePackageSimpleName() {
        return modulePackageSimpleName;
    }
    
    private static Comparator<String> nameComparator = new Comparator<String>() {
        @Override
        public int compare(String o1, String o2) {
            if ("id".equals(o1.toLowerCase())) {
                return -1;
            } else if ("id".equals(o2.toLowerCase())) {
                return 1;
            } else if (o1.toLowerCase().endsWith("id")
                    && o2.toLowerCase().endsWith("id")) {
                return o1.compareTo(o2);
            } else if (o1.toLowerCase().endsWith("id")) {
                return -1;
            } else if (o2.toLowerCase().endsWith("id")) {
                return 1;
            } else {
                return o1.compareTo(o2);
            }
        }
    };
}
