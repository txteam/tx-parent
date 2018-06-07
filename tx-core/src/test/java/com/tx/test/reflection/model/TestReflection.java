/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2018年6月1日
 * <修改描述:>
 */
package com.tx.test.reflection.model;

import java.util.Date;

import javax.persistence.Column;

/**
 * <功能简述>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2018年6月1日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class TestReflection extends TestReflectionSuper {
    
    /** 注释内容 */
    private static final long serialVersionUID = -273345294728251305L;

    private Date createDate;
    
    @Column(name = "last_update_date")
    private Date lastUpdateDate;

    /**
     * @return 返回 createDate
     */
    @Column(name = "create_date")
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
    
    public String getUnExistField(){
        return "";
    }
    
    public void setUnExistField(String unExistField){
        return ;
    }
}
