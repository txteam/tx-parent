/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2014年3月5日
 * <修改描述:>
 */
package com.tx.component.basicdata.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;

import com.tx.component.basicdata.testmodel.BasicTypeEnum;
import com.tx.core.jdbc.sqlsource.annotation.QueryConditionEqual;
import com.tx.core.jdbc.sqlsource.annotation.QueryConditionLike;
import com.tx.core.jdbc.sqlsource.annotation.UpdateAble;


 /**
  * <功能简述>
  * <功能详细描述>
  * 
  * @author  Administrator
  * @version  [版本号, 2014年3月5日]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public class TestBasicData2 {
    
    @Id
    @QueryConditionEqual
    private String code;
    
    @UpdateAble
    @QueryConditionLike
    private String name;
    
    @UpdateAble
    private String remark;
    
    private Date createDate;
    
    @UpdateAble
    private Date lastUpdateDate;
    
    @Column(name = "type_")
    private BasicTypeEnum type;
    
    @UpdateAble
    private boolean valid = true;

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
     * @return 返回 type
     */
    public BasicTypeEnum getType() {
        return type;
    }

    /**
     * @param 对type进行赋值
     */
    public void setType(BasicTypeEnum type) {
        this.type = type;
    }

    /**
     * @return 返回 valid
     */
    public boolean isValid() {
        return valid;
    }

    /**
     * @param 对valid进行赋值
     */
    public void setValid(boolean valid) {
        this.valid = valid;
    }
    
    
}
