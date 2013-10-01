/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-8-27
 * <修改描述:>
 */
package com.tx.core.reflection;

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
    
    /** 
     * 是否为简单类型
     * JdbcUtils.isSupportedSimpleType
     * 如果为简单类型，则可由typeHandle处理对应类型的增删查改
     */
    private boolean simpleType;
    
    /** 对应数据库字段名 */
    private String columnName;
    
    /** 字段名 */
    private String getterName;
    
    /** 字段对应java类型 */
    private Class<?> getterType;
    
    /** 外键名 */
    private String foreignKeyGetterName;
    
    /** 外键类型 */
    private Class<?> foreignKeyGetterType;
    
    /** 真正的getter名，可以为xxx.xxx的形式，通过column注解根据类型获取 */
    private String realGetterName;
    
    /** 真正的getter类型 */
    private Class<?> realGetterType;
    
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
     * <默认构造函数>
     */
    JpaColumnInfo() {
        super();
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
    public Class<?> getRealGetterType() {
        return realGetterType;
    }
    
    /**
     * @param 对realGetterType进行赋值
     */
    public void setRealGetterType(Class<?> realGetterType) {
        this.realGetterType = realGetterType;
    }
    
    /**
     * @return 返回 columnName
     */
    public String getColumnName() {
        return columnName.trim().toUpperCase();
    }
    
    /**
     * @param 对columnName进行赋值
     */
    public void setColumnName(String columnName) {
        this.columnName = columnName.trim().toUpperCase();
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

    /**
     * @return 返回 foreignKeyGetterName
     */
    public String getForeignKeyGetterName() {
        return foreignKeyGetterName;
    }

    /**
     * @param 对foreignKeyGetterName进行赋值
     */
    public void setForeignKeyGetterName(String foreignKeyGetterName) {
        this.foreignKeyGetterName = foreignKeyGetterName;
    }

    /**
     * @return 返回 foreignKeyGetterType
     */
    public Class<?> getForeignKeyGetterType() {
        return foreignKeyGetterType;
    }

    /**
     * @param 对foreignKeyGetterType进行赋值
     */
    public void setForeignKeyGetterType(Class<?> foreignKeyGetterType) {
        this.foreignKeyGetterType = foreignKeyGetterType;
    }
}
