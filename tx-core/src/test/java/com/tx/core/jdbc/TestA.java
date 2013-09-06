/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-9-6
 * <修改描述:>
 */
package com.tx.core.jdbc;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.tx.core.jdbc.sqlsource.annotation.QueryConditionGreaterOrEqual;
import com.tx.core.jdbc.sqlsource.annotation.QueryConditionLess;
import com.tx.core.jdbc.sqlsource.annotation.QueryConditionLikeAfter;
import com.tx.core.jdbc.sqlsource.annotation.UpdateAble;


 /**
  * <功能简述>
  * <功能详细描述>
  * 
  * @author  brady
  * @version  [版本号, 2013-9-6]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
@Entity
@Table(name="test_testa")
public class TestA extends TestAParent implements Serializable{
    
    /** 注释内容 */
    private static final long serialVersionUID = 8039578409711587281L;

    @Id
    private String aid;

    @UpdateAble
    @Column(name="aaaClo")
    private String aaa;
    
    @QueryConditionLikeAfter
    @UpdateAble
    private String bbb;
    
    @UpdateAble
    private String ccc;
    
    @QueryConditionGreaterOrEqual(key="minCreateDate")
    @QueryConditionLess(key="maxCreateDate")
    private Date createDate;
    
    private Date endDate;
    
    @Column(name="aintClo")
    private int aint;
    
    private boolean aBoolean;
    
    @UpdateAble
    private Boolean aBooleanObj;
    
    @Transient
    private String aTri;

    /**
     * @return 返回 aid
     */
    public String getAid() {
        return aid;
    }

    /**
     * @param 对aid进行赋值
     */
    public void setAid(String aid) {
        this.aid = aid;
    }

    /**
     * @return 返回 aaa
     */
    public String getAaa() {
        return aaa;
    }

    /**
     * @param 对aaa进行赋值
     */
    public void setAaa(String aaa) {
        this.aaa = aaa;
    }

    /**
     * @return 返回 bbb
     */
    public String getBbb() {
        return bbb;
    }

    /**
     * @param 对bbb进行赋值
     */
    public void setBbb(String bbb) {
        this.bbb = bbb;
    }

    /**
     * @return 返回 ccc
     */
    public String getCcc() {
        return ccc;
    }

    /**
     * @param 对ccc进行赋值
     */
    public void setCcc(String ccc) {
        this.ccc = ccc;
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
     * @return 返回 endDate
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * @param 对endDate进行赋值
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * @return 返回 aint
     */
    public int getAint() {
        return aint;
    }

    /**
     * @param 对aint进行赋值
     */
    public void setAint(int aint) {
        this.aint = aint;
    }

    /**
     * @return 返回 aBoolean
     */
    public boolean isaBoolean() {
        return aBoolean;
    }

    /**
     * @param 对aBoolean进行赋值
     */
    public void setaBoolean(boolean aBoolean) {
        this.aBoolean = aBoolean;
    }

    /**
     * @return 返回 aBooleanObj
     */
    public Boolean getaBooleanObj() {
        return aBooleanObj;
    }

    /**
     * @param 对aBooleanObj进行赋值
     */
    public void setaBooleanObj(Boolean aBooleanObj) {
        this.aBooleanObj = aBooleanObj;
    }

    /**
     * @return 返回 aTri
     */
    public String getaTri() {
        return aTri;
    }

    /**
     * @param 对aTri进行赋值
     */
    public void setaTri(String aTri) {
        this.aTri = aTri;
    }
}
