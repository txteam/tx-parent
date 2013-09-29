/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-8-27
 * <修改描述:>
 */
package com.tx.core.reflection.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Types;

import javax.persistence.Column;

import org.apache.commons.lang3.StringUtils;

import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.util.JdbcUtils;

/**
 * 字段信息<br/>
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2013-8-27]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class JpaColumnInfo {
    
    public JpaColumnInfo(Column columnAnno, String columnName,
            Class<?> javaType, String propertyName, String comment) {
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
        } else if (JdbcUtils.isSupportedSimpleType(javaType)) {
            this.jdbcType = JdbcUtils.getSqlTypeByJavaType(javaType);
            if (char.class.equals(javaType) || Character.class.equals(javaType)) {
                this.length = 1;
            } else if (String.class.equals(javaType)) {
                this.length = createColumnLength(propertyName);
            } else if (double.class.equals(javaType)
                    || Double.class.equals(javaType)) {
                this.length = 64;
                this.precision = 64;
                this.scale = 10;
            } else if (float.class.equals(javaType)
                    || Float.class.equals(javaType)) {
                this.length = 32;
                this.precision = 32;
                this.scale = 5;
            } else if (BigDecimal.class.equals(javaType)) {
                this.length = 64;
                this.precision = 64;
                this.scale = 10;
            } else if (BigInteger.class.equals(javaType)) {
                this.length = 64;
                this.precision = 64;
                this.scale = 0;
            }
        } else {
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
