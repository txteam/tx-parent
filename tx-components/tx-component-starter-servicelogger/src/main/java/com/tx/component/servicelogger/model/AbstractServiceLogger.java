/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-9-22
 * <修改描述:>
 */
package com.tx.component.servicelogger.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;

import com.tx.component.servicelogger.annotation.ServiceLog;

import io.swagger.annotations.ApiModelProperty;

/**
 * 基础业务日志类
 * 
 * @author brady
 * @version [版本号, 2013-9-22]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@ServiceLog
public abstract class AbstractServiceLogger {
    
    /** 业务日志id */
    @Id
    private String id;
    
    /** 业务日志记录时间 */
    @Column(nullable = false, updatable = false)
    private Date createDate = new Date();
    
    /**
     * @return 返回 id
     */
    @ApiModelProperty(value = "主键", position = Integer.MIN_VALUE)
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
     * @return 返回 createDate
     */
    @ApiModelProperty(value = "操作记录", position = Integer.MAX_VALUE)
    public Date getCreateDate() {
        return createDate;
    }
    
    /**
     * @param 对createDate进行赋值
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
