///*
// * 描          述:  <描述>
// * 修  改   人:  PengQingyang
// * 修改时间:  2012-12-9
// * <修改描述:>
// */
//package com.tx.core.mybatis.data;
//
//import java.math.BigDecimal;
//import java.sql.Timestamp;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.HashSet;
//
//import javax.annotation.Generated;
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.Id;
//import javax.persistence.Table;
//
//
//
// /**
//  * <功能简述>
//  * <功能详细描述>
//  * 
//  * @author  PengQingyang
//  * @version  [版本号, 2012-12-9]
//  * @see  [相关类/方法]
//  * @since  [产品/模块版本]
//  */
//@Entity(name="wd_demo")
//@Table(name="wd_demo")
//public class Demo {
//    
//    private String id;
//    
//    private String name;
//    
//    private String passowrd;
//    
//    private String email;
//    
//    private Demo subDemo;
//    
//    private Date createDate;
//    
//    private java.sql.Date endDate;
//    
//    private Timestamp lastUpdateDate;
//    
//    private Integer testInteger;
//    
//    private BigDecimal testBigDeceimal;
//    
//    private HashMap<String, String> testHashMap;
//    
//    private HashSet<String> testHashSet;
//    
//    private ArrayList<String> testArrayList;
//    
//    private boolean booleanTest;
//    
//    private Boolean isBooleanObjTest;
//    
//    private int intTest;
//    
//    private Integer integerTest;
//    
//    
//
//    /**
//     * @return 返回 testHashMap
//     */
//    public HashMap<String, String> getTestHashMap() {
//        return testHashMap;
//    }
//
//    /**
//     * @param 对testHashMap进行赋值
//     */
//    public void setTestHashMap(HashMap<String, String> testHashMap) {
//        this.testHashMap = testHashMap;
//    }
//
//    /**
//     * @return 返回 testHashSet
//     */
//    public HashSet<String> getTestHashSet() {
//        return testHashSet;
//    }
//
//    /**
//     * @param 对testHashSet进行赋值
//     */
//    public void setTestHashSet(HashSet<String> testHashSet) {
//        this.testHashSet = testHashSet;
//    }
//
//    /**
//     * @return 返回 testArrayList
//     */
//    public ArrayList<String> getTestArrayList() {
//        return testArrayList;
//    }
//
//    /**
//     * @param 对testArrayList进行赋值
//     */
//    public void setTestArrayList(ArrayList<String> testArrayList) {
//        this.testArrayList = testArrayList;
//    }
//
//    /**
//     * @return 返回 id
//     */
//    @Id
//    @Generated(value="UUID")
//    public String getId() {
//        return id;
//    }
//    
//    
//
//    /**
//     * @return 返回 subDemo
//     */
//    public Demo getSubDemo() {
//        return subDemo;
//    }
//
//    /**
//     * @param 对subDemo进行赋值
//     */
//    public void setSubDemo(Demo subDemo) {
//        this.subDemo = subDemo;
//    }
//
//    /**
//     * @param 对id进行赋值
//     */
//    public void setId(String id) {
//        this.id = id;
//    }
//
//    /**
//     * @return 返回 name
//     */
//    @Column(name="newName")
//    public String getName() {
//        return name;
//    }
//
//    /**
//     * @param 对name进行赋值
//     */
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    /**
//     * @return 返回 passowrd
//     */
//    public String getPassowrd() {
//        return passowrd;
//    }
//
//    /**
//     * @param 对passowrd进行赋值
//     */
//    public void setPassowrd(String passowrd) {
//        this.passowrd = passowrd;
//    }
//
//    /**
//     * @return 返回 email
//     */
//    public String getEmail() {
//        return email;
//    }
//
//    /**
//     * @param 对email进行赋值
//     */
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    /**
//     * @return 返回 createDate
//     */
//    public Date getCreateDate() {
//        return createDate;
//    }
//
//    /**
//     * @param 对createDate进行赋值
//     */
//    public void setCreateDate(Date createDate) {
//        this.createDate = createDate;
//    }
//
//    /**
//     * @return 返回 endDate
//     */
//    public java.sql.Date getEndDate() {
//        return endDate;
//    }
//
//    /**
//     * @param 对endDate进行赋值
//     */
//    public void setEndDate(java.sql.Date endDate) {
//        this.endDate = endDate;
//    }
//
//    /**
//     * @return 返回 lastUpdateDate
//     */
//    public Timestamp getLastUpdateDate() {
//        return lastUpdateDate;
//    }
//
//    /**
//     * @param 对lastUpdateDate进行赋值
//     */
//    public void setLastUpdateDate(Timestamp lastUpdateDate) {
//        this.lastUpdateDate = lastUpdateDate;
//    }
//
//    /**
//     * @return 返回 testInteger
//     */
//    public Integer getTestInteger() {
//        return testInteger;
//    }
//
//    /**
//     * @param 对testInteger进行赋值
//     */
//    public void setTestInteger(Integer testInteger) {
//        this.testInteger = testInteger;
//    }
//
//    /**
//     * @return 返回 testBigDeceimal
//     */
//    public BigDecimal getTestBigDeceimal() {
//        return testBigDeceimal;
//    }
//
//    /**
//     * @param 对testBigDeceimal进行赋值
//     */
//    public void setTestBigDeceimal(BigDecimal testBigDeceimal) {
//        this.testBigDeceimal = testBigDeceimal;
//    }
//
//    /**
//     * @return 返回 booleanTest
//     */
//    public boolean isBooleanTest() {
//        return booleanTest;
//    }
//
//    /**
//     * @param 对booleanTest进行赋值
//     */
//    public void setBooleanTest(boolean booleanTest) {
//        this.booleanTest = booleanTest;
//    }
//
//    
//
//    /**
//     * @return 返回 isBooleanObjTest
//     */
//    public Boolean getIsBooleanObjTest() {
//        return isBooleanObjTest;
//    }
//
//    /**
//     * @param 对isBooleanObjTest进行赋值
//     */
//    public void setIsBooleanObjTest(Boolean isBooleanObjTest) {
//        this.isBooleanObjTest = isBooleanObjTest;
//    }
//
//    /**
//     * @return 返回 intTest
//     */
//    public int getIntTest() {
//        return intTest;
//    }
//
//    /**
//     * @param 对intTest进行赋值
//     */
//    public void setIntTest(int intTest) {
//        this.intTest = intTest;
//    }
//
//    /**
//     * @return 返回 integerTest
//     */
//    public Integer getIntegerTest() {
//        return integerTest;
//    }
//
//    /**
//     * @param 对integerTest进行赋值
//     */
//    public void setIntegerTest(Integer integerTest) {
//        this.integerTest = integerTest;
//    }
//}
