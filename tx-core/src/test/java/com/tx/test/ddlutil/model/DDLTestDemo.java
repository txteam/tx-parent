/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2018年4月27日
 * <修改描述:>
 */
package com.tx.test.ddlutil.model;

import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Table;

/**
  * <功能简述>
  * <功能详细描述>
  * 
  * @author  Administrator
  * @version  [版本号, 2018年4月27日]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
@Table(name = "test_ddl_test_demo")
public class DDLTestDemo extends DDLTestDemoParent {
    
    private Object test_Object;
    
    private Class<?> test_Class;
    
    private short test_short;
    
    private int test_int;
    
    private long test_long;
    
    private boolean test_boolean;
    
    private char test_char;
    
    private Short Short_object_Short;
    
    private Integer test_object_Integer;
    
    private Long test_object_Long;
    
    private Float test_object_Float;
    
    private Double test_object_Double;
    
    private Boolean test_object_Boolean;
    
    private Character test_object_Character;
    
    private java.sql.Date test_sql_Date;
    
    private java.util.Date test_util_Date;
    
    private Timestamp test_sql_Timestamp;
    
    private Time test_util_Time;
    
    private BigDecimal test_bigdecimal;
    
    private String test_String;
    
    @Column(length = 8, scale = 6)
    private BigDecimal test_bigdecimal1;
    
    @Column(length = 8, scale = 6)
    private BigDecimal test_bigdecimal2;
    
    @Column(length = 8, scale = 2)
    private BigDecimal test_bigdecima3;
    
    /**
     * @return 返回 test_Object
     */
    public Object getTest_Object() {
        return test_Object;
    }
    
    /**
     * @param 对test_Object进行赋值
     */
    public void setTest_Object(Object test_Object) {
        this.test_Object = test_Object;
    }
    
    /**
     * @return 返回 test_Class
     */
    public Class<?> getTest_Class() {
        return test_Class;
    }
    
    /**
     * @param 对test_Class进行赋值
     */
    public void setTest_Class(Class<?> test_Class) {
        this.test_Class = test_Class;
    }
    
    /**
     * @return 返回 test_short
     */
    public short getTest_short() {
        return test_short;
    }
    
    /**
     * @param 对test_short进行赋值
     */
    public void setTest_short(short test_short) {
        this.test_short = test_short;
    }
    
    /**
     * @return 返回 test_int
     */
    public int getTest_int() {
        return test_int;
    }
    
    /**
     * @param 对test_int进行赋值
     */
    public void setTest_int(int test_int) {
        this.test_int = test_int;
    }
    
    /**
     * @return 返回 test_long
     */
    public long getTest_long() {
        return test_long;
    }
    
    /**
     * @param 对test_long进行赋值
     */
    public void setTest_long(long test_long) {
        this.test_long = test_long;
    }
    
    /**
     * @return 返回 test_boolean
     */
    public boolean isTest_boolean() {
        return test_boolean;
    }
    
    /**
     * @param 对test_boolean进行赋值
     */
    public void setTest_boolean(boolean test_boolean) {
        this.test_boolean = test_boolean;
    }
    
    /**
     * @return 返回 test_char
     */
    public char getTest_char() {
        return test_char;
    }
    
    /**
     * @param 对test_char进行赋值
     */
    public void setTest_char(char test_char) {
        this.test_char = test_char;
    }
    
    /**
     * @return 返回 short_object_Short
     */
    public Short getShort_object_Short() {
        return Short_object_Short;
    }
    
    /**
     * @param 对short_object_Short进行赋值
     */
    public void setShort_object_Short(Short short_object_Short) {
        Short_object_Short = short_object_Short;
    }
    
    /**
     * @return 返回 test_object_Integer
     */
    public Integer getTest_object_Integer() {
        return test_object_Integer;
    }
    
    /**
     * @param 对test_object_Integer进行赋值
     */
    public void setTest_object_Integer(Integer test_object_Integer) {
        this.test_object_Integer = test_object_Integer;
    }
    
    /**
     * @return 返回 test_object_Long
     */
    public Long getTest_object_Long() {
        return test_object_Long;
    }
    
    /**
     * @param 对test_object_Long进行赋值
     */
    public void setTest_object_Long(Long test_object_Long) {
        this.test_object_Long = test_object_Long;
    }
    
    /**
     * @return 返回 test_object_Float
     */
    public Float getTest_object_Float() {
        return test_object_Float;
    }
    
    /**
     * @param 对test_object_Float进行赋值
     */
    public void setTest_object_Float(Float test_object_Float) {
        this.test_object_Float = test_object_Float;
    }
    
    /**
     * @return 返回 test_object_Double
     */
    public Double getTest_object_Double() {
        return test_object_Double;
    }
    
    /**
     * @param 对test_object_Double进行赋值
     */
    public void setTest_object_Double(Double test_object_Double) {
        this.test_object_Double = test_object_Double;
    }
    
    /**
     * @return 返回 test_object_Boolean
     */
    public Boolean getTest_object_Boolean() {
        return test_object_Boolean;
    }
    
    /**
     * @param 对test_object_Boolean进行赋值
     */
    public void setTest_object_Boolean(Boolean test_object_Boolean) {
        this.test_object_Boolean = test_object_Boolean;
    }
    
    /**
     * @return 返回 test_object_Character
     */
    public Character getTest_object_Character() {
        return test_object_Character;
    }
    
    /**
     * @param 对test_object_Character进行赋值
     */
    public void setTest_object_Character(Character test_object_Character) {
        this.test_object_Character = test_object_Character;
    }
    
    /**
     * @return 返回 test_sql_Date
     */
    public java.sql.Date getTest_sql_Date() {
        return test_sql_Date;
    }
    
    /**
     * @param 对test_sql_Date进行赋值
     */
    public void setTest_sql_Date(java.sql.Date test_sql_Date) {
        this.test_sql_Date = test_sql_Date;
    }
    
    /**
     * @return 返回 test_util_Date
     */
    public java.util.Date getTest_util_Date() {
        return test_util_Date;
    }
    
    /**
     * @param 对test_util_Date进行赋值
     */
    public void setTest_util_Date(java.util.Date test_util_Date) {
        this.test_util_Date = test_util_Date;
    }
    
    /**
     * @return 返回 test_sql_Timestamp
     */
    public Timestamp getTest_sql_Timestamp() {
        return test_sql_Timestamp;
    }
    
    /**
     * @param 对test_sql_Timestamp进行赋值
     */
    public void setTest_sql_Timestamp(Timestamp test_sql_Timestamp) {
        this.test_sql_Timestamp = test_sql_Timestamp;
    }
    
    /**
     * @return 返回 test_util_Time
     */
    public Time getTest_util_Time() {
        return test_util_Time;
    }
    
    /**
     * @param 对test_util_Time进行赋值
     */
    public void setTest_util_Time(Time test_util_Time) {
        this.test_util_Time = test_util_Time;
    }
    
    /**
     * @return 返回 test_bigdecimal
     */
    public BigDecimal getTest_bigdecimal() {
        return test_bigdecimal;
    }
    
    /**
     * @param 对test_bigdecimal进行赋值
     */
    public void setTest_bigdecimal(BigDecimal test_bigdecimal) {
        this.test_bigdecimal = test_bigdecimal;
    }
    
    /**
     * @return 返回 test_String
     */
    public String getTest_String() {
        return test_String;
    }
    
    /**
     * @param 对test_String进行赋值
     */
    public void setTest_String(String test_String) {
        this.test_String = test_String;
    }
    
    /**
     * @return 返回 test_bigdecimal1
     */
    public BigDecimal getTest_bigdecimal1() {
        return test_bigdecimal1;
    }
    
    /**
     * @param 对test_bigdecimal1进行赋值
     */
    public void setTest_bigdecimal1(BigDecimal test_bigdecimal1) {
        this.test_bigdecimal1 = test_bigdecimal1;
    }
    
    /**
     * @return 返回 test_bigdecimal2
     */
    public BigDecimal getTest_bigdecimal2() {
        return test_bigdecimal2;
    }
    
    /**
     * @param 对test_bigdecimal2进行赋值
     */
    public void setTest_bigdecimal2(BigDecimal test_bigdecimal2) {
        this.test_bigdecimal2 = test_bigdecimal2;
    }
    
    /**
     * @return 返回 test_bigdecima3
     */
    public BigDecimal getTest_bigdecima3() {
        return test_bigdecima3;
    }
    
    /**
     * @param 对test_bigdecima3进行赋值
     */
    public void setTest_bigdecima3(BigDecimal test_bigdecima3) {
        this.test_bigdecima3 = test_bigdecima3;
    }
}
