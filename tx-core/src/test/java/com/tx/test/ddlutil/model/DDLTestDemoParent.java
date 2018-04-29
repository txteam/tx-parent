/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2018年4月27日
 * <修改描述:>
 */
package com.tx.test.ddlutil.model;

import java.math.BigDecimal;

/**
  * <功能简述>
  * <功能详细描述>
  * 
  * @author  Administrator
  * @version  [版本号, 2018年4月27日]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public class DDLTestDemoParent {
    
    private String id;
    
    private int parentInt;
    
    private BigDecimal parentBigDecimal;
    
    private String parentString;
    
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
    
    /**
     * @return 返回 parentInt
     */
    public int getParentInt() {
        return parentInt;
    }
    
    /**
     * @param 对parentInt进行赋值
     */
    public void setParentInt(int parentInt) {
        this.parentInt = parentInt;
    }
    
    /**
     * @return 返回 parentBigDecimal
     */
    public BigDecimal getParentBigDecimal() {
        return parentBigDecimal;
    }
    
    /**
     * @param 对parentBigDecimal进行赋值
     */
    public void setParentBigDecimal(BigDecimal parentBigDecimal) {
        this.parentBigDecimal = parentBigDecimal;
    }
    
    /**
     * @return 返回 parentString
     */
    public String getParentString() {
        return parentString;
    }
    
    /**
     * @param 对parentString进行赋值
     */
    public void setParentString(String parentString) {
        this.parentString = parentString;
    }
}
