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
import com.tx.core.reflection.ClassReflector;
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
    
    public <TYPE> JpaColumnInfo(String getterName, Class<?> getterType,
            Class<TYPE> type) {
        ClassReflector<TYPE> classReflector = ClassReflector.forClass(type);
        this.getterName = getterName;
        this.getterType = getterType;
        this.columnName = getterName.toUpperCase();

        //defaultValue
        this.length = 255;
        this.precision = 0;
        this.scale = 0;
        
        if(JdbcUtils.isSupportedSimpleType(this.getterType)){
            
        }
        
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
     * 是否为简单类型
     * JdbcUtils.isSupportedSimpleType
     * 如果为简单类型，则可由typeHandle处理对应类型的增删查改
     */
    private boolean simpleType;
    
    /** 字段名 */
    private String getterName;
    
    /** 字段对应java类型 */
    private Class<?> getterType;
    
    /** 真正的getter名，可以为xxx.xxx的形式，通过column注解根据类型获取 */
    private String realGetterName;
    
    /** 真正的getter类型 */
    private String realGetterType;
    
    /** 对应数据库字段名 */
    private String columnName;
    
    /** 字段对应注解 */
    private String columnComment;
    
    /** 是否是唯一键 */
    private boolean unique;
    
    /** 是否可为空 */
    private boolean nullable;
    
    /** column length */
    private int length = 255;
    
    /** The precision for a decimal */
    private int precision = 0;
    
    /** The scale for a decimal (exact numeric) column */
    private int scale = 0;

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
     * @return 返回 realGetterName
     */
    public String getRealGetterName() {
        return realGetterName;
    }

    /**
     * @param 对realGetterName进行赋值
     */
    public void setRealGetterName(String realGetterName) {
        this.realGetterName = realGetterName;
    }

    /**
     * @return 返回 realGetterType
     */
    public String getRealGetterType() {
        return realGetterType;
    }

    /**
     * @param 对realGetterType进行赋值
     */
    public void setRealGetterType(String realGetterType) {
        this.realGetterType = realGetterType;
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

    /**
     * @return 返回 columnComment
     */
    public String getColumnComment() {
        return columnComment;
    }

    /**
     * @param 对columnComment进行赋值
     */
    public void setColumnComment(String columnComment) {
        this.columnComment = columnComment;
    }

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
}
