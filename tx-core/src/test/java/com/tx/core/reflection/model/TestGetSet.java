/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-9-27
 * <修改描述:>
 */
package com.tx.core.reflection.model;

import javax.persistence.Id;

import org.apache.ibatis.reflection.MetaClass;


 /**
  * <功能简述>
  * <功能详细描述>
  * 
  * @author  brady
  * @version  [版本号, 2013-9-27]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public class TestGetSet {
    
    @Id
    private String id;
    
    /**
     * @return 返回 id
     */
    public String getId() {
        return id;
    }

    /**
     * @param 对id进行赋值
     */
    public void setId(String id) {
        this.id = id;
    }

    private boolean isabc;
    
    private boolean isIsisAbc;
    
    private boolean is;
    
    private boolean isTest;
    
    private boolean test2;
    
    private boolean abc1Able;
    
    private boolean isAbc2Able;
    
    private Boolean abcObjAble;
    
    private Boolean isAbcObjAble;
    
    private String abcString;
    
    private int abcInt;
    
    private Integer abcInteger;

    /**
     * @return 返回 isabc
     */
    public boolean isIsabc() {
        return isabc;
    }

    /**
     * @param 对isabc进行赋值
     */
    public void setIsabc(boolean isabc) {
        this.isabc = isabc;
    }

    /**
     * @return 返回 isIsisAbc
     */
    public boolean isIsisAbc() {
        return isIsisAbc;
    }

    /**
     * @param 对isIsisAbc进行赋值
     */
    public void setIsisAbc(boolean isIsisAbc) {
        this.isIsisAbc = isIsisAbc;
    }

    /**
     * @return 返回 test2
     */
    public boolean isTest2() {
        return test2;
    }

    /**
     * @param 对test2进行赋值
     */
    public void setTest2(boolean test2) {
        this.test2 = test2;
    }

    /**
     * @return 返回 is
     */
    public boolean isIs() {
        return is;
    }

    /**
     * @param 对is进行赋值
     */
    public void setIs(boolean is) {
        this.is = is;
    }

    /**
     * @return 返回 isTest
     */
    public boolean isTest() {
        return isTest;
    }

    /**
     * @param 对isTest进行赋值
     */
    public void setTest(boolean isTest) {
        this.isTest = isTest;
    }

    /**
     * @return 返回 abc1Able
     */
    public boolean isAbc1Able() {
        return abc1Able;
    }

    /**
     * @param 对abc1Able进行赋值
     */
    public void setAbc1Able(boolean abc1Able) {
        this.abc1Able = abc1Able;
    }

    /**
     * @return 返回 isAbc2Able
     */
    public boolean isAbc2Able() {
        return isAbc2Able;
    }

    /**
     * @param 对isAbc2Able进行赋值
     */
    public void setAbc2Able(boolean isAbc2Able) {
        this.isAbc2Able = isAbc2Able;
    }

    /**
     * @return 返回 abcObjAble
     */
    public Boolean getAbcObjAble() {
        return abcObjAble;
    }

    /**
     * @param 对abcObjAble进行赋值
     */
    public void setAbcObjAble(Boolean abcObjAble) {
        this.abcObjAble = abcObjAble;
    }

    /**
     * @return 返回 isAbcObjAble
     */
    public Boolean getIsAbcObjAble() {
        return isAbcObjAble;
    }

    /**
     * @param 对isAbcObjAble进行赋值
     */
    public void setIsAbcObjAble(Boolean isAbcObjAble) {
        this.isAbcObjAble = isAbcObjAble;
    }

    /**
     * @return 返回 abcString
     */
    public String getAbcString() {
        return abcString;
    }

    /**
     * @param 对abcString进行赋值
     */
    public void setAbcString(String abcString) {
        this.abcString = abcString;
    }

    /**
     * @return 返回 abcInt
     */
    public int getAbcInt() {
        return abcInt;
    }

    /**
     * @param 对abcInt进行赋值
     */
    public void setAbcInt(int abcInt) {
        this.abcInt = abcInt;
    }

    /**
     * @return 返回 abcInteger
     */
    public Integer getAbcInteger() {
        return abcInteger;
    }

    /**
     * @param 对abcInteger进行赋值
     */
    public void setAbcInteger(Integer abcInteger) {
        this.abcInteger = abcInteger;
    }
    
    public static void main(String[] args) {
        //ClassReflector<TestGetSet> classReflector = ClassReflector.forClass(TestGetSet.class);
        
        System.out.println("\n.......getterNames:........");
        for(String getterName : MetaClass.forClass(TestGetSet.class).getGetterNames()){
            System.out.println(getterName);
        }
        
        /*
        System.out.println("\n.......setterNames:........");
        for(String setterName : classReflector.getSetterNames()){
            System.out.println(setterName);
        }
        */
    }
}
