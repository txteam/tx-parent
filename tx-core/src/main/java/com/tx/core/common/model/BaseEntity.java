/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2019年12月11日
 * <修改描述:>
 */
package com.tx.core.common.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.TableGenerator;

/**
 * 基础实体类型
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2019年12月11日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public abstract class BaseEntity<ID extends Serializable>
        implements Serializable {
    
    /** 注释内容 */
    private static final long serialVersionUID = -8593996002766246136L;
    
    /** ID */
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "tableGenerator")
    @TableGenerator(name = "tableGenerator", table = "IdGenerator")
    private ID id;
    
    /** 创建日期 */
    @Column(nullable = false, updatable = false)
    private Date createdDate;
    
    /** 最后修改日期 */
    @Column(nullable = false)
    private Date lastUpdateDate;
    
    /** 版本 */
    @Column(nullable = false)
    private Long version;
    
    /**
     * @return 返回 id
     */
    public ID getId() {
        return id;
    }
    
    /**
     * @param 对id进行赋值
     */
    public void setId(ID id) {
        this.id = id;
    }
    
    /**
     * @return 返回 createdDate
     */
    public Date getCreatedDate() {
        return createdDate;
    }
    
    /**
     * @param 对createdDate进行赋值
     */
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
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
     * @return 返回 version
     */
    public Long getVersion() {
        return version;
    }
    
    /**
     * @param 对version进行赋值
     */
    public void setVersion(Long version) {
        this.version = version;
    }
}
