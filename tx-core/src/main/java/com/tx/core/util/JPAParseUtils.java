/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2018年6月4日
 * <修改描述:>
 */
package com.tx.core.util;

import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.core.convert.TypeDescriptor;

import com.tx.core.exceptions.util.AssertUtils;

/**
 * JPA解析工具类<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2018年6月4日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class JPAParseUtils {
    
    /**
     * 解析对象表名<br/>
     * <功能详细描述>
     * @param beanType
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static String parseTableName(Class<?> beanType) {
        AssertUtils.notNull(beanType, "beanType is null.");
        
        //没有注解的时候默认使用类名当作表名
        String tableName = beanType.getSimpleName();
        //如果存在javax注解中的Entity读取其Name
        //org.hibernate.annotations.Entity该类中不含有表名，不进行解析
        if (beanType.isAnnotationPresent(Entity.class)) {
            String entityName = beanType.getAnnotation(Entity.class).name();
            if (!StringUtils.isEmpty(entityName)) {
                tableName = entityName.toUpperCase();
            }
        }
        if (beanType.isAnnotationPresent(Table.class)) {
            //如果含有注解：javax.persistence.Table
            String annoTableName = beanType.getAnnotation(Table.class).name();
            if (!StringUtils.isEmpty(annoTableName)) {
                tableName = annoTableName.toUpperCase();
            }
        }
        return tableName;
    }
    
    /**
     * 解析表字段<br/>
     * <功能详细描述>
     * @param beanType
     * @param jpaPropertySimpleTypeFilter
     * @return [参数说明]
     * 
     * @return List<JPAProperty> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static List<JPAColumnInfo> parseTableColumns(Class<?> beanType) {
        AssertUtils.notNull(beanType, "beanType is null.");
        
        //解析jpa属性
        List<JPAColumnInfo> columns = new ArrayList<>();
        
        //objBW.getPropertyTypeDescriptor("parent").hasAnnotation(JsonIgnore.class)
        BeanWrapper bw = new BeanWrapperImpl(beanType);
        boolean hasPrimaryKey = false;//是否存在主键
        for (PropertyDescriptor pd : BeanUtils
                .getPropertyDescriptors(beanType)) {
            String propertyName = pd.getName();
            TypeDescriptor td = bw.getPropertyTypeDescriptor(propertyName);
            if (JPAParseUtils.isIgnoreProperty(pd, td)) {
                continue;
            }
            
            JPAColumnInfo jpaColumnInfo = new JPAColumnInfo(pd, td);
            
            boolean primaryKey = JPAParseUtils.isPrimaryKey(pd, td);//判断是否为主键
            if (primaryKey) {
                hasPrimaryKey = true;
            }
            jpaColumnInfo.setPrimaryKey(primaryKey);
            
            columns.add(jpaColumnInfo);
        }
        
        if (!hasPrimaryKey) {
            //如果没有主键：判断是否有id,code这样的字段
            //如果有id
            for (JPAColumnInfo col : columns) {
                if (StringUtils.equalsIgnoreCase("id",
                        col.getPropertyDescriptor().getName())) {
                    col.setPrimaryKey(true);
                    hasPrimaryKey = true;
                    break;
                }
            }
        }
        if (!hasPrimaryKey) {
            //如果没有主键：判断是否有id,code这样的字段
            //如果有code
            for (JPAColumnInfo col : columns) {
                if (StringUtils.equalsIgnoreCase("code",
                        col.getPropertyDescriptor().getName())) {
                    col.setPrimaryKey(true);
                    hasPrimaryKey = true;
                    break;
                }
            }
        }
        
        Collections.sort(columns, COLUMN_COMPARATOR);
        return columns;
    }
    
    /**
     * 解析表排序字段<br/>
     * <功能详细描述>
     * @param beanType
     * @param jpaPropertySimpleTypeFilter
     * @return [参数说明]
     * 
     * @return List<JPAProperty> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static List<String> parseOrderBys(Class<?> beanType,
            List<JPAColumnInfo> columnInfos,
            String... defaultOrderByColumnNames) {
        AssertUtils.notNull(beanType, "beanType is null.");
        AssertUtils.notEmpty(columnInfos, "columnInfos is null.");
        
        //解析jpa属性
        List<String> orderBys = new ArrayList<>();
        for (JPAColumnInfo columnInfo : columnInfos) {
            String orderBy = parseOrderBy(beanType, columnInfo);
            if (StringUtils.isEmpty(orderBy)) {
                continue;
            }
            orderBys.add(orderBy);
        }
        
        //如果有默认的排序字段
        if (!ArrayUtils.isEmpty(defaultOrderByColumnNames)) {
            for (String name : defaultOrderByColumnNames) {
                if (StringUtils.isEmpty(name)) {
                    continue;
                }
                for (JPAColumnInfo columnTemp : columnInfos) {
                    if (!StringUtils.equalsIgnoreCase(name,
                            columnTemp.getPropertyDescriptor().getName())) {
                        continue;
                    }
                    orderBys.add(columnTemp.getColumnName() + " ASC");
                }
            }
        }
        
        //如果默认的字段都不存在，则默认以主键为排序字段
        if (CollectionUtils.isEmpty(orderBys)) {
            for (JPAColumnInfo columnTemp : columnInfos) {
                if (!columnTemp.isPrimaryKey()) {
                    continue;
                }
                orderBys.add(columnTemp.getColumnName() + " ASC");
            }
        }
        return orderBys;
    }
    
    /**
     * 解析表排序字段<br/>
     * <功能详细描述>
     * @param beanType
     * @param jpaPropertySimpleTypeFilter
     * @return [参数说明]
     * 
     * @return List<JPAProperty> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static String parseOrderBy(Class<?> beanType,
            List<JPAColumnInfo> columnInfos,
            String... defaultOrderByColumnNames) {
        AssertUtils.notNull(beanType, "beanType is null.");
        AssertUtils.notEmpty(columnInfos, "columnInfos is null.");
        
        //解析jpa属性
        for (JPAColumnInfo columnInfo : columnInfos) {
            String orderBy = parseOrderBy(beanType, columnInfo);
            if (!StringUtils.isEmpty(orderBy)) {
                return orderBy;
            }
        }
        
        //如果有默认的排序字段
        if (!ArrayUtils.isEmpty(defaultOrderByColumnNames)) {
            for (String name : defaultOrderByColumnNames) {
                if (StringUtils.isEmpty(name)) {
                    continue;
                }
                for (JPAColumnInfo columnTemp : columnInfos) {
                    if (!StringUtils.equalsIgnoreCase(name,
                            columnTemp.getPropertyDescriptor().getName())) {
                        continue;
                    }
                    return columnTemp.getColumnName() + " ASC";
                }
            }
        }
        
        //如果默认的字段都不存在，则默认以主键为排序字段
        for (JPAColumnInfo columnTemp : columnInfos) {
            if (!columnTemp.isPrimaryKey()) {
                continue;
            }
            return columnTemp.getColumnName() + " ASC";
        }
        
        return null;
    }
    
    /**
     * 是否忽略属性<br/>
     * <功能详细描述>
     * @param propertyDescriptor
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static boolean isIgnoreProperty(PropertyDescriptor pd,
            TypeDescriptor td) {
        AssertUtils.notNull(pd, "pd is null.");
        AssertUtils.notNull(td, "td is null.");
        
        //如果属性没有同时具备Read,Write方法时则跳过该属性
        if (pd.getReadMethod() == null || pd.getWriteMethod() == null) {
            return true;
        }
        
        //collection,map，array,interface,annotation,abstract
        //|| Modifier.isAbstract(propertyType.getModifiers())  boolean等值，=true了，先修改以後再進行處理        
        Class<?> propertyType = pd.getPropertyType();
        if (td.isCollection() || td.isMap() || td.isArray()
                || propertyType.isInterface() || propertyType.isAnnotation()) {
            return true;
        }
        
        if (td.hasAnnotation(Transient.class)) {
            return true;
        } else if (td.hasAnnotation(java.beans.Transient.class)) {
            return true;
        } else if (td.hasAnnotation(
                org.springframework.data.annotation.Transient.class)) {
            return true;
        } else if (td.hasAnnotation(OneToMany.class)) {
            return true;
        }
        
        return false;
    }
    
    /**
     * 判断是否主键<br/>
     * <功能详细描述>
     * @param beanType
     * @param propertyDescriptor
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    private static boolean isPrimaryKey(PropertyDescriptor pd,
            TypeDescriptor td) {
        AssertUtils.notNull(pd, "pd is null.");
        AssertUtils.notNull(td, "td is null.");
        
        if (td.hasAnnotation(Id.class)) {
            return true;
        } else if (td
                .hasAnnotation(org.springframework.data.annotation.Id.class)) {
            return true;
        }
        
        return false;
    }
    
    /**
     * 获取字段名<br/>
     * <功能详细描述>
     * @param beanType
     * @param propertyDescriptor
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @SuppressWarnings("unused")
    private static <T extends Annotation> T parseColumnAnnotation(
            TypeDescriptor td, Class<T> annotation) {
        AssertUtils.notNull(td, "td is null.");
        if (!td.hasAnnotation(annotation)) {
            return null;
        }
        return td.getAnnotation(annotation);
    }
    
    /**
     * 获取字段名<br/>
     * <功能详细描述>
     * @param beanType
     * @param propertyDescriptor
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    private static String parseOrderBy(Class<?> beanType,
            JPAColumnInfo columnInfo) {
        AssertUtils.notNull(beanType, "beanType is null.");
        AssertUtils.notNull(columnInfo, "columnInfo is null.");
        AssertUtils.notNull(columnInfo.getTypeDescriptor(),
                "columnInfo.typeDescriptor is null.");
        
        TypeDescriptor td = columnInfo.getTypeDescriptor();
        String orderBy = null;
        
        //如果readMethod上有忽略字段的注解，则跳过该字段
        if (td.hasAnnotation(OrderBy.class)) {
            OrderBy orderByAnnotation = td.getAnnotation(OrderBy.class);
            if (StringUtils.isEmpty(orderByAnnotation.value())) {
                orderBy = columnInfo.getColumnName() + " ASC";
            } else {
                orderBy = orderByAnnotation.value();
            }
            return orderBy;
        }
        if (td.hasAnnotation(org.hibernate.annotations.OrderBy.class)) {
            org.hibernate.annotations.OrderBy orderByAnnotation = td
                    .getAnnotation(org.hibernate.annotations.OrderBy.class);
            if (StringUtils.isEmpty(orderByAnnotation.clause())) {
                orderBy = columnInfo.getColumnName() + " ASC";
            } else {
                orderBy = orderByAnnotation.clause();
            }
            return orderBy;
        }
        
        return orderBy;
    }
    
    /**
     * JPA字段信息<br/>
     * <功能详细描述>
     * 
     * @author  Administrator
     * @version  [版本号, 2018年6月6日]
     * @see  [相关类/方法]
     * @since  [产品/模块版本]
     */
    public static class JPAColumnInfo {
        
        /** 属性描述 */
        protected final PropertyDescriptor propertyDescriptor;
        
        /** 属性类型描述字段 */
        protected final TypeDescriptor typeDescriptor;
        
        /** 字段注解 */
        protected final Column columnAnnotation;
        
        /** 嵌套属性描述，如果不存在嵌套属性，则该值为空 */
        protected PropertyDescriptor nestedPropertyDescriptor;
        
        /** 是否是主键: 不根据属性判断，如果不存在@Id注解时，系统将考虑采用id,或是code字段当做唯一键字段 */
        protected boolean primaryKey = false;
        
        /** <默认构造函数> */
        public JPAColumnInfo(PropertyDescriptor propertyDescriptor,
                TypeDescriptor typeDescriptor) {
            super();
            this.propertyDescriptor = propertyDescriptor;
            this.typeDescriptor = typeDescriptor;
            this.columnAnnotation = typeDescriptor.getAnnotation(Column.class);
            
            if (!BeanUtils
                    .isSimpleValueType(propertyDescriptor.getPropertyType())) {
                //如果属性不为simpleType则使用JPA解析二级属性
                this.nestedPropertyDescriptor = parseCustomizeTypeNestedPropertyDescriptorByColumnName(
                        propertyDescriptor, getColumnName());
            }
        }
        
        /**
         * @return 返回 propertyDescriptor
         */
        public PropertyDescriptor getPropertyDescriptor() {
            return propertyDescriptor;
        }
        
        /**
         * @return 返回 typeDescriptor
         */
        public TypeDescriptor getTypeDescriptor() {
            return typeDescriptor;
        }
        
        /**
         * @return 返回 columnAnnotation
         */
        public Column getColumnAnnotation() {
            return columnAnnotation;
        }
        
        /**
         * @return 返回 primaryKey
         */
        public boolean isPrimaryKey() {
            return primaryKey;
        }
        
        /**
         * @param 对primaryKey进行赋值
         */
        public void setPrimaryKey(boolean primaryKey) {
            this.primaryKey = primaryKey;
        }
        
        /**
         * 属性是否是简单类型<br/>
         * <功能详细描述>
         * @return [参数说明]
         * 
         * @return boolean [返回类型说明]
         * @exception throws [异常类型] [异常说明]
         * @see [类、类#方法、类#成员]
         */
        public boolean isSimpleValueType() {
            return BeanUtils.isSimpleValueType(getPropertyType());
        }
        
        /**
         * 是否含有嵌套属性<br/>
         * <功能详细描述>
         * @return [参数说明]
         * 
         * @return boolean [返回类型说明]
         * @exception throws [异常类型] [异常说明]
         * @see [类、类#方法、类#成员]
         */
        public boolean hasNestedProperty() {
            return this.nestedPropertyDescriptor != null;
        }
        
        /**
         * @return 返回 nestedPropertyDescriptor
         */
        public PropertyDescriptor getNestedPropertyDescriptor() {
            return nestedPropertyDescriptor;
        }
        
        /**
         * @return 返回 columnName
         */
        public String getColumnName() {
            return (columnAnnotation == null
                    || StringUtils.isBlank(columnAnnotation.name()))
                            ? this.propertyDescriptor.getName()
                            : columnAnnotation.name();
        }
        
        /**
         * 获取属性名（如果含有嵌套属性，则显示为嵌套属性）<br/>
         * <功能详细描述>
         * @return [参数说明]
         * 
         * @return String [返回类型说明]
         * @exception throws [异常类型] [异常说明]
         * @see [类、类#方法、类#成员]
         */
        public String getColumnPropertyName() {
            if (this.nestedPropertyDescriptor == null) {
                return this.propertyDescriptor.getName();
            } else {
                return this.propertyDescriptor.getName() + "."
                        + this.nestedPropertyDescriptor.getName();
            }
        }
        
        /**
         * 获取嵌套属性类型<br/>
         * <功能详细描述>
         * @return [参数说明]
         * 
         * @return Class<?> [返回类型说明]
         * @exception throws [异常类型] [异常说明]
         * @see [类、类#方法、类#成员]
         */
        public Class<?> getColumnPropertyType() {
            if (this.nestedPropertyDescriptor == null) {
                return this.propertyDescriptor.getPropertyType();
            } else {
                return nestedPropertyDescriptor.getPropertyType();
            }
        }
        
        /**
         * @return 返回 nullable
         */
        public boolean isNullable() {
            if (primaryKey) {
                //如果为主键时必填，不能为空
                return false;
            } else {
                return (columnAnnotation == null) ? true
                        : columnAnnotation.nullable();
            }
        }
        
        /**
         * @return 返回 length
         */
        public int getLength() {
            return (columnAnnotation == null) ? 255 : columnAnnotation.length();
        }
        
        /**
         * @return 返回 precision
         */
        public int getPrecision() {
            return (columnAnnotation == null)
                    ? (Number.class.isAssignableFrom(getColumnPropertyType())
                            ? 32 : 64)
                    : columnAnnotation.precision();
        }
        
        /**
         * @return 返回 scale
         */
        public int getScale() {
            return (columnAnnotation == null)
                    ? (Number.class.isAssignableFrom(getColumnPropertyType())
                            ? 2 : 0)
                    : columnAnnotation.scale();
        }
        
        /**
         * @return 返回 unique
         */
        public boolean isUnique() {
            return (columnAnnotation == null) ? false
                    : columnAnnotation.unique();
        }
        
        /**
         * @return 返回 insertable
         */
        public boolean isInsertable() {
            return (columnAnnotation == null) ? true
                    : columnAnnotation.insertable();
        }
        
        /**
         * @return 返回 updatable
         */
        public boolean isUpdatable() {
            return (columnAnnotation == null) ? true
                    : columnAnnotation.updatable();
        }
        
        /**
         * 获取属性名<br/>
         * <功能详细描述>
         * @return [参数说明]
         * 
         * @return String [返回类型说明]
         * @exception throws [异常类型] [异常说明]
         * @see [类、类#方法、类#成员]
         */
        public String getPropertyName() {
            return this.propertyDescriptor.getName();
        }
        
        /**
         * 获取属性类型<br/>
         * <功能详细描述>
         * @return [参数说明]
         * 
         * @return Class<?> [返回类型说明]
         * @exception throws [异常类型] [异常说明]
         * @see [类、类#方法、类#成员]
         */
        public Class<?> getPropertyType() {
            return this.propertyDescriptor.getPropertyType();
        }
        
    }
    
    /**
     * 默认的字段比较器，用以排序
     */
    public static final Comparator<JPAColumnInfo> COLUMN_COMPARATOR = new Comparator<JPAColumnInfo>() {
        /**
         * @param o1
         * @param o2
         * @return
         */
        @Override
        public int compare(JPAColumnInfo o1, JPAColumnInfo o2) {
            //主键显示在最上面
            if (o1.isPrimaryKey()) {
                return -1;
            } else if (o2.isPrimaryKey()) {
                return 1;
            } else if (StringUtils.equalsIgnoreCase("code",
                    o1.getColumnName())) {
                return -1;
            } else if (StringUtils.equalsIgnoreCase("code",
                    o2.getColumnName())) {
                return 1;
            }
            
            //优先将分组名称类似的排序至一起？
            //根据字符串进行排序？
            //根据类型进行排序？
            //生成排序权重，将字符创进行拆分
            //剩下的字段根据长度以及相似度进行合理排序id,code,name,remark,description,createDate,createOperatorId,lastUpdateDate,lastUpdateOperator
            String[] o1ColumnNameArray = splitColumeName(o1.getColumnName());
            String[] o2ColumnNameArray = splitColumeName(o2.getColumnName());
            //优先根据首字母排序
            String o1ColumnI0 = o1ColumnNameArray[0];
            String o2ColumnI0 = o2ColumnNameArray[0];
            if (!StringUtils.equalsAnyIgnoreCase(o1ColumnI0, o2ColumnI0)) {
                //当首字母不相同时根据字长进行排序,根据特诊字段排序即可
                if (o1ColumnI0.length() != o2ColumnI0.length()) {
                    return Integer.compare(o1ColumnI0.length(),
                            o2ColumnI0.length());
                } else {
                    //长度相同时根据字母进行排序
                    return o1ColumnI0.compareTo(o2ColumnI0);
                }
            } else {
                if (o1ColumnNameArray.length == o2ColumnNameArray.length) {
                    //首字母相同，length长度也相同时，根据字长进行排序
                    return Integer.compare(o1.getColumnName().length(),
                            o2.getColumnName().length());
                } else {
                    return Integer.compare(o1ColumnNameArray.length,
                            o2ColumnNameArray.length);
                }
            }
        }
    };
    
    /**
     * 拆分字段名<br/>
     * <功能详细描述>
     * @param columnName
     * @return [参数说明]
     * 
     * @return String[] [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    private static String[] splitColumeName(String columnName) {
        //取字段名数组中最后一个字符串
        String columnNameArraySource = columnName;
        columnNameArraySource = columnNameArraySource.replaceAll("[A-Z]",
                ",$0");
        columnNameArraySource = columnNameArraySource.replaceAll("_", ",");
        String[] columnNameArray = StringUtils
                .splitByWholeSeparator(columnNameArraySource, ",");
        return columnNameArray;
    }
    
    /**
     * 根据字段名解析对象<br/>
     * <功能详细描述>
     * @param beanType
     * @param propertyDescriptor
     * @param columnName
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    private static PropertyDescriptor parseCustomizeTypeNestedPropertyDescriptorByColumnName(
            PropertyDescriptor propertyDescriptor, String columnName) {
        AssertUtils.notNull(propertyDescriptor, "propertyDescriptor is null.");
        AssertUtils.notEmpty(columnName, "columnName is empty.");
        
        Class<?> propertyType = propertyDescriptor.getPropertyType();
        String propertyName = propertyDescriptor.getName();
        
        //类型不应该为简单类型
        AssertUtils.isTrue(!BeanUtils.isSimpleValueType(propertyType),
                "propertyType:{} is not simpleValueType.",
                new Object[] { propertyDescriptor.getPropertyType() });
        //类型不应该为集合，Map,数组
        //&& !propertyType.isInterface()
        // or Interface
        AssertUtils.isTrue(
                !propertyType.isArray()
                        && !Collection.class.isAssignableFrom(propertyType)
                        && !Map.class.isAssignableFrom(propertyType),
                "propertyType:{} is Array or Abstract or Collection or Map.",
                new Object[] { propertyDescriptor.getPropertyType() });
        
        String nestedPropertyName = null;
        if (columnName.length() > propertyName.length()
                && columnName.indexOf("_") < 0 //字段中如果含有"_"则优先根据拆分办法进行取值
                && StringUtils.startsWithIgnoreCase(columnName, propertyName)) {
            //如果属性名是字段名的一部分采用该截取的方式获取字段名
            nestedPropertyName = StringUtils.substring(columnName,
                    propertyName.length());
        } else if (!StringUtils.equalsIgnoreCase(columnName, propertyName)) {
            //取字段名数组中最后一个字符串
            String columnNameArraySource = columnName;
            columnNameArraySource = columnNameArraySource.replaceAll("[A-Z]",
                    ",$0");
            columnNameArraySource = columnNameArraySource.replaceAll("_", ",");
            String[] columnNames = StringUtils
                    .splitByWholeSeparator(columnNameArraySource, ",");
            
            AssertUtils.isTrue(columnNames.length > 1,
                    "columnNames:{} length should > 1.",
                    new Object[] { columnNames });
            nestedPropertyName = columnNames[columnNames.length - 1];
        } else {
            return null;
        }
        
        //嵌套的属性名不能为空
        AssertUtils.notEmpty(nestedPropertyName,
                "parse nestedPropertyName error.nestedPropertyName is empty.");
        
        //嵌套属性名首写字母小写化
        nestedPropertyName = StringUtils.uncapitalize(nestedPropertyName);
        PropertyDescriptor nestedPropertyDescriptor = BeanUtils
                .getPropertyDescriptor(propertyType, nestedPropertyName);
        
        if (nestedPropertyDescriptor == null) {
            for (PropertyDescriptor pdTemp : BeanUtils
                    .getPropertyDescriptors(propertyType)) {
                if (StringUtils.equalsIgnoreCase(pdTemp.getName(),
                        nestedPropertyName)) {
                    nestedPropertyDescriptor = pdTemp;
                    break;
                }
            }
        }
        AssertUtils.notNull(nestedPropertyDescriptor,
                "nestedPropertyDescriptor is null.nestedPropertyName:{} ",
                nestedPropertyName);
        AssertUtils.isTrue(
                BeanUtils.isSimpleValueType(
                        nestedPropertyDescriptor.getPropertyType()),
                "nestedProperty should is simpleValueType.but actual is :{}",
                new Object[] { nestedPropertyDescriptor.getPropertyType() });
        
        return nestedPropertyDescriptor;
    }
    
    //    /**
    //     * 获取字段名<br/>
    //     * <功能详细描述>
    //     * @param beanType
    //     * @param propertyDescriptor
    //     * @return [参数说明]
    //     * 
    //     * @return String [返回类型说明]
    //     * @exception throws [异常类型] [异常说明]
    //     * @see [类、类#方法、类#成员]
    //     */
    //    private static String parseOrderBy(Class<?> beanType,
    //            JPAColumnInfo columnInfo) {
    //        AssertUtils.notNull(beanType, "beanType is null.");
    //        AssertUtils.notNull(columnInfo, "columnInfo is null.");
    //        AssertUtils.notNull(columnInfo.getPropertyDescriptor(),
    //                "columnInfo.propertyDescriptor is null.");
    //        
    //        PropertyDescriptor propertyDescriptor = columnInfo
    //                .getPropertyDescriptor();
    //        String orderBy = null;
    //        
    //        //如果readMethod上有忽略字段的注解，则跳过该字段
    //        Method readMethod = propertyDescriptor.getReadMethod();
    //        if (readMethod.isAnnotationPresent(OrderBy.class)) {
    //            OrderBy orderByAnnotation = readMethod.getAnnotation(OrderBy.class);
    //            if (StringUtils.isEmpty(orderByAnnotation.value())) {
    //                orderBy = columnInfo.getColumnName() + " ASC";
    //            } else {
    //                orderBy = orderByAnnotation.value();
    //            }
    //            return orderBy;
    //        }
    //        if (readMethod
    //                .isAnnotationPresent(org.hibernate.annotations.OrderBy.class)) {
    //            org.hibernate.annotations.OrderBy orderByAnnotation = readMethod
    //                    .getAnnotation(org.hibernate.annotations.OrderBy.class);
    //            if (StringUtils.isEmpty(orderByAnnotation.clause())) {
    //                orderBy = columnInfo.getColumnName() + " ASC";
    //            } else {
    //                orderBy = orderByAnnotation.clause();
    //            }
    //            return orderBy;
    //        }
    //        
    //        //如果存在字段，则字段上有注解需要跳过时，则跳过该字段
    //        Field field = ReflectionUtils.findField(beanType,
    //                propertyDescriptor.getName());
    //        if (field == null) {
    //            return orderBy;
    //        }
    //        
    //        //如果存在对应的字段
    //        if (field.isAnnotationPresent(OrderBy.class)) {
    //            OrderBy orderByAnnotation = field.getAnnotation(OrderBy.class);
    //            if (StringUtils.isEmpty(orderByAnnotation.value())) {
    //                orderBy = columnInfo.getColumnName() + " ASC";
    //            } else {
    //                orderBy = orderByAnnotation.value();
    //            }
    //            return orderBy;
    //        }
    //        if (field
    //                .isAnnotationPresent(org.hibernate.annotations.OrderBy.class)) {
    //            org.hibernate.annotations.OrderBy orderByAnnotation = field
    //                    .getAnnotation(org.hibernate.annotations.OrderBy.class);
    //            if (StringUtils.isEmpty(orderByAnnotation.clause())) {
    //                orderBy = columnInfo.getColumnName() + " ASC";
    //            } else {
    //                orderBy = orderByAnnotation.clause();
    //            }
    //            return orderBy;
    //        }
    //        
    //        return orderBy;
    //    }
    //    /**
    //     * 是否忽略属性<br/>
    //     * <功能详细描述>
    //     * @param propertyDescriptor
    //     * @return [参数说明]
    //     * 
    //     * @return boolean [返回类型说明]
    //     * @exception throws [异常类型] [异常说明]
    //     * @see [类、类#方法、类#成员]
    //     */
    //    @SuppressWarnings("unused")
    //    @Deprecated
    //    private static boolean isIgnoreProperty(Class<?> beanType,
    //            PropertyDescriptor propertyDescriptor) {
    //        AssertUtils.notNull(beanType, "beanType is null.");
    //        AssertUtils.notNull(propertyDescriptor, "propertyDescriptor is null.");
    //        
    //        //如果属性没有同时具备Read,Write方法时则跳过该属性
    //        if (propertyDescriptor.getReadMethod() == null
    //                || propertyDescriptor.getWriteMethod() == null) {
    //            return true;
    //        }
    //        
    //        //collection,map，array,interface,annotation,abstract
    //        //|| Modifier.isAbstract(propertyType.getModifiers())  boolean等值，=true了，先修改以後再進行處理
    //        Class<?> propertyType = propertyDescriptor.getPropertyType();
    //        if (Collection.class.isAssignableFrom(propertyType)
    //                || Map.class.isAssignableFrom(propertyType)
    //                || Set.class.isAssignableFrom(propertyType)
    //                || propertyType.isArray() || propertyType.isInterface()
    //                || propertyType.isAnnotation()) {
    //            return true;
    //        }
    //        
    //        //如果readMethod上有忽略字段的注解，则跳过该字段
    //        Method readMethod = propertyDescriptor.getReadMethod();
    //        if (readMethod.isAnnotationPresent(Transient.class)
    //                || readMethod.isAnnotationPresent(java.beans.Transient.class)
    //                || readMethod.isAnnotationPresent(
    //                        org.springframework.data.annotation.Transient.class)
    //                || readMethod.isAnnotationPresent(OneToMany.class)) {
    //            return true;
    //        }
    //        
    //        //如果存在字段，则字段上有注解需要跳过时，则跳过该字段
    //        Field field = ReflectionUtils.findField(beanType,
    //                propertyDescriptor.getName());
    //        if (field != null) {
    //            if (field.isAnnotationPresent(Transient.class)
    //                    || field.isAnnotationPresent(java.beans.Transient.class)
    //                    || field.isAnnotationPresent(
    //                            org.springframework.data.annotation.Transient.class)
    //                    || field.isAnnotationPresent(OneToMany.class)) {
    //                return true;
    //            }
    //        }
    //        
    //        return false;
    //    }
    //    
    //    /**
    //     * 获取字段名<br/>
    //     * <功能详细描述>
    //     * @param beanType
    //     * @param propertyDescriptor
    //     * @return [参数说明]
    //     * 
    //     * @return String [返回类型说明]
    //     * @exception throws [异常类型] [异常说明]
    //     * @see [类、类#方法、类#成员]
    //     */
    //    @SuppressWarnings("unused")
    //    @Deprecated
    //    private static Column parseColumnAnnotation(Class<?> beanType,
    //            PropertyDescriptor propertyDescriptor) {
    //        AssertUtils.notNull(beanType, "beanType is null.");
    //        AssertUtils.notNull(propertyDescriptor, "propertyDescriptor is null.");
    //        
    //        Column columnAnnotation = null;
    //        //如果readMethod上有忽略字段的注解，则跳过该字段
    //        Method readMethod = propertyDescriptor.getReadMethod();
    //        if (readMethod.isAnnotationPresent(Column.class)) {
    //            columnAnnotation = readMethod.getAnnotation(Column.class);
    //        }
    //        
    //        //如果存在字段，则字段上有注解需要跳过时，则跳过该字段
    //        Field field = ReflectionUtils.findField(beanType,
    //                propertyDescriptor.getName());
    //        if (field != null && field.isAnnotationPresent(Column.class)) {
    //            columnAnnotation = field.getAnnotation(Column.class);
    //        }
    //        
    //        return columnAnnotation;
    //    }
    //    
    //    /**
    //     * 判断是否主键<br/>
    //     * <功能详细描述>
    //     * @param beanType
    //     * @param propertyDescriptor
    //     * @return [参数说明]
    //     * 
    //     * @return boolean [返回类型说明]
    //     * @exception throws [异常类型] [异常说明]
    //     * @see [类、类#方法、类#成员]
    //     */
    //    @SuppressWarnings("unused")
    //    @Deprecated
    //    private static boolean isPrimaryKey(Class<?> beanType,
    //            PropertyDescriptor propertyDescriptor) {
    //        AssertUtils.notNull(beanType, "beanType is null.");
    //        AssertUtils.notNull(propertyDescriptor, "propertyDescriptor is null.");
    //        
    //        //如果readMethod上有忽略字段的注解，则跳过该字段
    //        Method readMethod = propertyDescriptor.getReadMethod();
    //        if (readMethod.isAnnotationPresent(Id.class)
    //                || readMethod.isAnnotationPresent(
    //                        org.springframework.data.annotation.Id.class)) {
    //            return true;
    //        }
    //        
    //        //如果存在字段，则字段上有注解需要跳过时，则跳过该字段
    //        Field field = ReflectionUtils.findField(beanType,
    //                propertyDescriptor.getName());
    //        if (field != null) {
    //            if (field.isAnnotationPresent(Id.class)
    //                    || field.isAnnotationPresent(
    //                            org.springframework.data.annotation.Id.class)) {
    //                return true;
    //            }
    //        }
    //        
    //        return false;
    //    }
}
