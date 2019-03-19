/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2014年5月24日
 * <修改描述:>
 */
package com.tx.component.task.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.tx.core.jdbc.sqlsource.annotation.QueryConditionEqual;
import com.tx.core.jdbc.sqlsource.annotation.QueryConditionGreaterOrEqual;
import com.tx.core.jdbc.sqlsource.annotation.QueryConditionLess;
import com.tx.core.jdbc.sqlsource.annotation.UpdateAble;

/**
 * 事务执行记录<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2014年5月24日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Entity
@Table(name = "task_execute_log")
public class TaskExecuteLog {
    
    /** 日志id */
    @Id
    private String id;
    
    /** 归属任务id */
    @QueryConditionEqual
    private String taskId;
    
    /** 事务定义key */
    @QueryConditionEqual
    private String code;
    
    /** 所属模块 */
    @QueryConditionEqual
    private String module;
    
    /** 事务名 */
    @UpdateAble
    @QueryConditionEqual
    private String name;
    
    /** 描述 */
    @UpdateAble
    private String remark;
    
    /** 最后一次任务执行结果 */
    @UpdateAble
    @QueryConditionEqual
    private TaskResultEnum result;
    
    /** 执行开始时间 */
    @QueryConditionGreaterOrEqual(key = "minStartDate")
    @QueryConditionLess(key = "maxStartDate")
    private Date startDate;
    
    /** 执行结束时间 */
    @QueryConditionGreaterOrEqual(key = "minEndDate")
    @QueryConditionLess(key = "maxEndDate")
    private Date endDate;
    
    /** 耗时 */
    @UpdateAble
    private long consuming;
    
    /** 属性集：用于存放执行数据，例：最后一次执行期间的数据 */
    private String attributes;
    
    /** 执行所在机器的签名 */
    @QueryConditionEqual
    private String signature;
    
    /**  事务执行人 */
    private String operatorId;
    
    /** 事务执行虚中心 */
    private String vcid;
    
    /** <默认构造函数> */
    public TaskExecuteLog() {
        super();
    }
    
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
     * @return 返回 taskId
     */
    public String getTaskId() {
        return taskId;
    }
    
    /**
     * @param 对taskId进行赋值
     */
    public void setTaskId(String taskId) {
        this.taskId = taskId;
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
     * @return 返回 result
     */
    public TaskResultEnum getResult() {
        return result;
    }
    
    /**
     * @param 对result进行赋值
     */
    public void setResult(TaskResultEnum result) {
        this.result = result;
    }
    
    /**
     * @return 返回 startDate
     */
    public Date getStartDate() {
        return startDate;
    }
    
    /**
     * @param 对startDate进行赋值
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
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
     * @return 返回 consuming
     */
    public long getConsuming() {
        return consuming;
    }
    
    /**
     * @param 对consuming进行赋值
     */
    public void setConsuming(long consuming) {
        this.consuming = consuming;
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
     * @return 返回 signature
     */
    public String getSignature() {
        return signature;
    }
    
    /**
     * @param 对signature进行赋值
     */
    public void setSignature(String signature) {
        this.signature = signature;
    }
    
    /**
     * @return 返回 module
     */
    public String getModule() {
        return module;
    }
    
    /**
     * @param 对module进行赋值
     */
    public void setModule(String module) {
        this.module = module;
    }
    
    /**
     * @return 返回 operatorId
     */
    public String getOperatorId() {
        return operatorId;
    }
    
    /**
     * @param 对operatorId进行赋值
     */
    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }
    
    /**
     * @return 返回 vcid
     */
    public String getVcid() {
        return vcid;
    }
    
    /**
     * @param 对vcid进行赋值
     */
    public void setVcid(String vcid) {
        this.vcid = vcid;
    }
}
