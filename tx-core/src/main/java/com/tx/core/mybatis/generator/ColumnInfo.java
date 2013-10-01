/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-8-27
 * <修改描述:>
 */
package com.tx.core.mybatis.generator;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;

import org.apache.commons.lang3.StringUtils;

import com.tx.core.exceptions.util.AssertUtils;

/**
 * 字段信息<br/>
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2013-8-27]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Deprecated
public class ColumnInfo {
    
    /** 基本类型集合 */
    private static final Map<Class<?>, Integer> SIMPLE_TYPE_2_TYPES_MAP = new HashMap<Class<?>, Integer>();
    
    /*
        Java数据类型    Hibernate数据类型   标准SQL数据类型(PS:对于不同的DB可能有所差异)
        byte、java.lang.Byte byte    TINYINT
        short、java.lang.Short   short   SMALLINT
        int、java.lang.Integer   integer INGEGER
        long、java.lang.Long long    BIGINT
        float、java.lang.Float   float   FLOAT
        double、java.lang.Double double  DOUBLE
        java.math.BigDecimal    big_decimal NUMERIC
        char、java.lang.Character    character   CHAR(1)
        boolean、java.lang.Boolean   boolean BIT
        java.lang.String    string  VARCHAR
        boolean、java.lang.Boolean   yes_no  CHAR(1)('Y'或'N')
        boolean、java.lang.Boolean   true_false  CHAR(1)('Y'或'N')
        java.util.Date、java.sql.Date    date    DATE
        java.util.Date、java.sql.Time    time    TIME
        java.util.Date、java.sql.Timestamp   timestamp   TIMESTAMP
        java.util.Calendar  calendar    TIMESTAMP
        java.util.Calendar  calendar_date   DATE
        byte[]  binary  VARBINARY、BLOB
        java.lang.String    text    CLOB
        java.io.Serializable    serializable    VARBINARY、BLOB
        java.sql.Clob   clob    CLOB
        java.sql.Blob   blob    BLOB
        java.lang.Class class   VARCHAR
        java.util.Locale    locale  VARCHAR
        java.util.TimeZone  timezone    VARCHAR
        java.util.Currency  currency    VARCHAR
     */
    static {
        SIMPLE_TYPE_2_TYPES_MAP.put(char[].class,Types.CLOB);
        SIMPLE_TYPE_2_TYPES_MAP.put(byte[].class,Types.BLOB);
        
        SIMPLE_TYPE_2_TYPES_MAP.put(short.class,Types.SMALLINT);
        SIMPLE_TYPE_2_TYPES_MAP.put(Short.class,Types.SMALLINT);
        SIMPLE_TYPE_2_TYPES_MAP.put(int.class,Types.INTEGER);
        SIMPLE_TYPE_2_TYPES_MAP.put(Integer.class,Types.INTEGER);
        SIMPLE_TYPE_2_TYPES_MAP.put(long.class,Types.BIGINT);
        SIMPLE_TYPE_2_TYPES_MAP.put(Long.class,Types.BIGINT);
        SIMPLE_TYPE_2_TYPES_MAP.put(boolean.class,Types.BIT);
        SIMPLE_TYPE_2_TYPES_MAP.put(Boolean.class,Types.BIT);
        SIMPLE_TYPE_2_TYPES_MAP.put(byte.class,Types.TINYINT);
        SIMPLE_TYPE_2_TYPES_MAP.put(Byte.class,Types.TINYINT);
        SIMPLE_TYPE_2_TYPES_MAP.put(Date.class,Types.TIMESTAMP);
        SIMPLE_TYPE_2_TYPES_MAP.put(java.sql.Date.class,Types.TIMESTAMP);
        SIMPLE_TYPE_2_TYPES_MAP.put(Timestamp.class,Types.TIMESTAMP);
        
        SIMPLE_TYPE_2_TYPES_MAP.put(char.class,Types.CHAR);
        SIMPLE_TYPE_2_TYPES_MAP.put(Character.class,Types.CHAR);
        SIMPLE_TYPE_2_TYPES_MAP.put(double.class,Types.NUMERIC);
        SIMPLE_TYPE_2_TYPES_MAP.put(Double.class,Types.NUMERIC);
        SIMPLE_TYPE_2_TYPES_MAP.put(float.class,Types.NUMERIC);
        SIMPLE_TYPE_2_TYPES_MAP.put(Float.class,Types.NUMERIC);
        SIMPLE_TYPE_2_TYPES_MAP.put(BigDecimal.class,Types.NUMERIC);
        SIMPLE_TYPE_2_TYPES_MAP.put(BigInteger.class,Types.NUMERIC);
        SIMPLE_TYPE_2_TYPES_MAP.put(String.class,Types.VARCHAR);
    }
    
    public ColumnInfo(Column columnAnno, String columnName, Class<?> javaType,
            String propertyName, String comment) {
        this.javaType = javaType;
        this.propertyName = propertyName;
        this.comment = comment;
        this.name = columnName;
        this.length = 0;//createColumnLength(propertyName);
        this.precision = 0;
        this.scale = 0;
        if (javaType.isEnum()) {
            this.jdbcType = Types.VARCHAR;
            this.length = 64;
        }else if(SIMPLE_TYPE_2_TYPES_MAP.keySet().contains(javaType)){
            this.jdbcType = SIMPLE_TYPE_2_TYPES_MAP.get(javaType);
            if(char.class.equals(javaType) || Character.class.equals(javaType)){
                this.length = 1;
            }else if(String.class.equals(javaType)){
                this.length = createColumnLength(propertyName);
            }else if(double.class.equals(javaType) || Double.class.equals(javaType)){
                this.length = 64;
                this.precision = 64;
                this.scale = 10;
            }else if(float.class.equals(javaType) || Float.class.equals(javaType)){
                this.length = 32;
                this.precision = 32;
                this.scale = 5;
            }else if(BigDecimal.class.equals(javaType)){
                this.length = 64;
                this.precision = 64;
                this.scale = 10;
            }else if(BigInteger.class.equals(javaType)){
                this.length = 64;
                this.precision = 64;
                this.scale = 0;
            }
        }else{
            this.jdbcType = Types.VARCHAR;
            this.length = 64;
        }
        
        if (columnAnno != null) {
            if (!StringUtils.isEmpty(columnAnno.columnDefinition())) {
                this.comment = columnAnno.columnDefinition();
            }
            if (!StringUtils.isEmpty(columnAnno.name())) {
                AssertUtils.isTrue(columnAnno.name().equals(this.name),
                        "columnAnno.name is not equals columnName");
            }
            if (columnAnno.length() != 255) {
                this.length = columnAnno.length();
            }
            this.precision = columnAnno.precision();
            this.scale = columnAnno.scale();
        }
    }
    
    private int createColumnLength(String propertyName) {
        if (StringUtils.endsWithIgnoreCase(propertyName, "id")) {
            return 64;
        } else if (StringUtils.endsWithIgnoreCase(propertyName, "desc")
                || StringUtils.endsWithIgnoreCase(propertyName, "description")
                || StringUtils.endsWithIgnoreCase(propertyName, "remark")) {
            return 2000;
        } else if (StringUtils.endsWithIgnoreCase(propertyName, "name")
                || StringUtils.endsWithIgnoreCase(propertyName, "code")) {
            return 64;
        } else {
            return 255;
        }
    }
    
    /**
     * 字段对应java类型
     */
    private Class<?> javaType;
    
    /**
     * 属性名
     */
    private String propertyName;
    
    /**
     * 字段名
     */
    private String name;
    
    /**
     * 是否是唯一键
     */
    private boolean unique;
    
    /**
      * 是否可为空
     */
    private boolean nullable;
    
    /**
     * 字段注释部分
     */
    private String comment;
    
    /**
     * 数据库类型
     */
    private int jdbcType;
    
    /**
     * (Optional) The column length. (Applies only if a
     * string-valued column is used.)
     */
    private int length = 255;
    
    /**
     * (Optional) The precision for a decimal (exact numeric)
     * column. (Applies only if a decimal column is used.)
     * Value must be set by developer if used when generating
     * the DDL for the column.
     */
    private int precision = 0;
    
    /**
     * (Optional) The scale for a decimal (exact numeric) column.
     * (Applies only if a decimal column is used.)
     */
    private int scale = 0;
    
    /**
     * @return 返回 unique
     */
    public boolean isUnique() {
        return unique;
    }
    
    /**
     * @param 对unique进行赋值
     */
    public void setUnique(boolean unique) {
        this.unique = unique;
    }
    
    /**
     * @return 返回 nullable
     */
    public boolean isNullable() {
        return nullable;
    }
    
    /**
     * @param 对nullable进行赋值
     */
    public void setNullable(boolean nullable) {
        this.nullable = nullable;
    }
    
    /**
     * @return 返回 comment
     */
    public String getComment() {
        return comment;
    }
    
    /**
     * @param 对comment进行赋值
     */
    public void setComment(String comment) {
        this.comment = comment;
    }
    
    /**
     * @return 返回 javaType
     */
    public Class<?> getJavaType() {
        return javaType;
    }
    
    /**
     * @param 对javaType进行赋值
     */
    public void setJavaType(Class<?> javaType) {
        this.javaType = javaType;
    }
    
    /**
     * @return 返回 jdbcType
     */
    public int getJdbcType() {
        return jdbcType;
    }
    
    /**
     * @param 对jdbcType进行赋值
     */
    public void setJdbcType(int jdbcType) {
        this.jdbcType = jdbcType;
    }
    
    /**
     * @return 返回 length
     */
    public int getLength() {
        return length;
    }
    
    /**
     * @param 对length进行赋值
     */
    public void setLength(int length) {
        this.length = length;
    }
    
    /**
     * @return 返回 precision
     */
    public int getPrecision() {
        return precision;
    }
    
    /**
     * @param 对precision进行赋值
     */
    public void setPrecision(int precision) {
        this.precision = precision;
    }
    
    /**
     * @return 返回 scale
     */
    public int getScale() {
        return scale;
    }
    
    /**
     * @param 对scale进行赋值
     */
    public void setScale(int scale) {
        this.scale = scale;
    }
    
    /**
     * @return 返回 propertyName
     */
    public String getPropertyName() {
        return propertyName;
    }
    
    /**
     * @param 对propertyName进行赋值
     */
    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }
    
    /**
     * @return 返回 name
     */
    public String getName() {
        return name;
    }
    
    /**
     * @param 对name进行赋值
     */
    public void setName(String name) {
        this.name = name;
    }
}
