/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2018年5月31日
 * <修改描述:>
 */
package com.tx.test.method.model;

import java.math.BigDecimal;

/**
 * <功能简述>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2018年5月31日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class TestMethodMedel1 {
    
    private String testString;
    
    private BigDecimal testBigDecimal;
    
    private Long testLong;

    /**
     * @return 返回 testString
     */
    public String getTestString() {
        return testString;
    }

    /**
     * @param 对testString进行赋值
     */
    public void setTestString(String testString) {
        this.testString = testString;
    }

    /**
     * @return 返回 testBigDecimal
     */
    public BigDecimal getTestBigDecimal() {
        return testBigDecimal;
    }

    /**
     * @param 对testBigDecimal进行赋值
     */
    public void setTestBigDecimal(BigDecimal testBigDecimal) {
        this.testBigDecimal = testBigDecimal;
    }

    /**
     * @return 返回 testLong
     */
    public Long getTestLong() {
        return testLong;
    }

    /**
     * @param 对testLong进行赋值
     */
    public void setTestLong(Long testLong) {
        this.testLong = testLong;
    }
}
