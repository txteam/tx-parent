/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2018年6月7日
 * <修改描述:>
 */
package com.tx.test.mybatis.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import javax.persistence.Column;
import javax.persistence.Transient;

/**
 * <功能简述>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2018年6月7日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class TestDemoSuper {
    
    @Column(name = "superDemoId")
    private Demo superDemo;
    
    private BigDecimal superBigDeceimal;
    
    private boolean superBoolean;
    
    private Boolean superIsBooleanObject;
    
    private int superInt;
    
    private Integer superIntegerObject;
    
    @Transient
    private HashMap<String, String> testHashMap;
    
    @Transient
    private ArrayList<String> testArrayList;
    
    private HashSet<String> testHashSet;

    /**
     * @return 返回 superDemo
     */
    public Demo getSuperDemo() {
        return superDemo;
    }

    /**
     * @param 对superDemo进行赋值
     */
    public void setSuperDemo(Demo superDemo) {
        this.superDemo = superDemo;
    }

    /**
     * @return 返回 superBigDeceimal
     */
    public BigDecimal getSuperBigDeceimal() {
        return superBigDeceimal;
    }

    /**
     * @param 对superBigDeceimal进行赋值
     */
    public void setSuperBigDeceimal(BigDecimal superBigDeceimal) {
        this.superBigDeceimal = superBigDeceimal;
    }

    /**
     * @return 返回 superBoolean
     */
    public boolean isSuperBoolean() {
        return superBoolean;
    }

    /**
     * @param 对superBoolean进行赋值
     */
    public void setSuperBoolean(boolean superBoolean) {
        this.superBoolean = superBoolean;
    }

    /**
     * @return 返回 superIsBooleanObject
     */
    public Boolean getSuperIsBooleanObject() {
        return superIsBooleanObject;
    }

    /**
     * @param 对superIsBooleanObject进行赋值
     */
    public void setSuperIsBooleanObject(Boolean superIsBooleanObject) {
        this.superIsBooleanObject = superIsBooleanObject;
    }

    /**
     * @return 返回 superInt
     */
    public int getSuperInt() {
        return superInt;
    }

    /**
     * @param 对superInt进行赋值
     */
    public void setSuperInt(int superInt) {
        this.superInt = superInt;
    }

    /**
     * @return 返回 superIntegerObject
     */
    public Integer getSuperIntegerObject() {
        return superIntegerObject;
    }

    /**
     * @param 对superIntegerObject进行赋值
     */
    public void setSuperIntegerObject(Integer superIntegerObject) {
        this.superIntegerObject = superIntegerObject;
    }

    /**
     * @return 返回 testHashMap
     */
    public HashMap<String, String> getTestHashMap() {
        return testHashMap;
    }

    /**
     * @param 对testHashMap进行赋值
     */
    public void setTestHashMap(HashMap<String, String> testHashMap) {
        this.testHashMap = testHashMap;
    }

    /**
     * @return 返回 testArrayList
     */
    public ArrayList<String> getTestArrayList() {
        return testArrayList;
    }

    /**
     * @param 对testArrayList进行赋值
     */
    public void setTestArrayList(ArrayList<String> testArrayList) {
        this.testArrayList = testArrayList;
    }

    /**
     * @return 返回 testHashSet
     */
    public HashSet<String> getTestHashSet() {
        return testHashSet;
    }

    /**
     * @param 对testHashSet进行赋值
     */
    public void setTestHashSet(HashSet<String> testHashSet) {
        this.testHashSet = testHashSet;
    }
}
