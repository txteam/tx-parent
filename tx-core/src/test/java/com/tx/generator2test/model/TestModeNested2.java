/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2019年5月28日
 * <修改描述:>
 */
package com.tx.generator2test.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * <功能简述>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2019年5月28日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Entity
@Table(name = "test_testnestedmode")
public class TestModeNested2 {
    
    @Id
    private String id;
    
    private String code;
    
    private String name;
    
    private String remark;
    
    private String description;
    
    private String attributes;
    
    private Date createDate;
    
    private Date lastUpdateDate;
    
    private int testInt;
    
    private Long testLong;

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
     * @return 返回 code
     */
    public String getCode() {
        return code;
    }

    /**
     * @param 对code进行赋值
     */
    public void setCode(String code) {
        this.code = code;
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

    /**
     * @return 返回 remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * @param 对remark进行赋值
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * @return 返回 description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param 对description进行赋值
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return 返回 attributes
     */
    public String getAttributes() {
        return attributes;
    }

    /**
     * @param 对attributes进行赋值
     */
    public void setAttributes(String attributes) {
        this.attributes = attributes;
    }

    /**
     * @return 返回 createDate
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * @param 对createDate进行赋值
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * @return 返回 lastUpdateDate
     */
    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    /**
     * @param 对lastUpdateDate进行赋值
     */
    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    /**
     * @return 返回 testInt
     */
    public int getTestInt() {
        return testInt;
    }

    /**
     * @param 对testInt进行赋值
     */
    public void setTestInt(int testInt) {
        this.testInt = testInt;
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
