/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2017年9月18日
 * <修改描述:>
 */
package com.tx.component.task.model;

import java.util.Date;

import com.tx.core.jdbc.sqlsource.annotation.QueryConditionEqual;
import com.tx.core.jdbc.sqlsource.annotation.UpdateAble;

/**
  * 任务详情<br/>
  * <功能详细描述>
  * 
  * @author  Administrator
  * @version  [版本号, 2017年9月18日]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public class TaskDetail extends TaskDef {
    
    /** 注释内容 */
    private static final long serialVersionUID = -6178042742842940900L;
    
    /** attributes */
    private String taskAttributes;
    
    /** 任务id */
    @QueryConditionEqual
    private String taskId;
    
    /** 任务状态 */
    @QueryConditionEqual
    private TaskStatusEnum status = TaskStatusEnum.WAIT_EXECUTE;
    
    /** 最后一次任务执行结果 */
    @QueryConditionEqual
    private TaskResultEnum result;
    
    /** 最后执行时间：记录最后一次执行的时间 */
    private Date startDate;
    
    /** 最后执行时间：记录最后一次执行的时间 */
    private Date endDate;
    
    /** 最后一次陈功执行耗时 */
    @UpdateAble
    private Long consuming;
    
    /** 执行所在机器的签名：根据singnature一致的情况下，才会有权对任务的状态进行重置（暂不提供自动重置）   */
    @QueryConditionEqual
    private String signature;
    
    /** 执行总次数 */
    private int executeCount = 0;
    
    /** 最后一次成功执行时间 */
    private Date successStartDate;
    
    /** 最后一次成功执行时间 */
    private Date successEndDate;
    
    /** 最后一次执行耗时 */
    private Long successConsuming;
    
    /** 成功次数 */
    private int successCount = 0;
    
    /** 最后一次失败执行时间 */
    private Date failStartDate;
    
    /** 最后一次失败执行时间 */
    private Date failEndDate;
    
    /** 最后一次失败执行耗时 */
    private Long failConsuming;
    
    /** 执行失败总次数 */
    private int failCount = 0;
    
    /** attributes */
    private String attributes;
    
    /** 下次执行时间：如果当前时间大于该事件，则事务可以被启动,冗余字段，在jobDataMap中也会记录 */
    private Date nextFireDate;
    
    /** 最后更新时间 */
    private Date lastUpdateDate;
    
    /** 创建时间 */
    private Date createDate;
    
    /**
     * @return 返回 taskAttributes
     */
    public String getTaskAttributes() {
        return taskAttributes;
    }
    
    /**
     * @param 对taskAttributes进行赋值
     */
    public void setTaskAttributes(String taskAttributes) {
        this.taskAttributes = taskAttributes;
    }
    
    /**
     * @return 返回 status
     */
    public TaskStatusEnum getStatus() {
        return status;
    }
    
    /**
     * @param 对status进行赋值
     */
    public void setStatus(TaskStatusEnum status) {
        this.status = status;
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
     * @return 返回 successStartDate
     */
    public Date getSuccessStartDate() {
        return successStartDate;
    }
    
    /**
     * @param 对successStartDate进行赋值
     */
    public void setSuccessStartDate(Date successStartDate) {
        this.successStartDate = successStartDate;
    }
    
    /**
     * @return 返回 successEndDate
     */
    public Date getSuccessEndDate() {
        return successEndDate;
    }
    
    /**
     * @param 对successEndDate进行赋值
     */
    public void setSuccessEndDate(Date successEndDate) {
        this.successEndDate = successEndDate;
    }
    
    /**
     * @return 返回 successConsuming
     */
    public Long getSuccessConsuming() {
        return successConsuming;
    }
    
    /**
     * @param 对successConsuming进行赋值
     */
    public void setSuccessConsuming(Long successConsuming) {
        this.successConsuming = successConsuming;
    }
    
    /**
     * @return 返回 failStartDate
     */
    public Date getFailStartDate() {
        return failStartDate;
    }
    
    /**
     * @param 对failStartDate进行赋值
     */
    public void setFailStartDate(Date failStartDate) {
        this.failStartDate = failStartDate;
    }
    
    /**
     * @return 返回 failEndDate
     */
    public Date getFailEndDate() {
        return failEndDate;
    }
    
    /**
     * @param 对failEndDate进行赋值
     */
    public void setFailEndDate(Date failEndDate) {
        this.failEndDate = failEndDate;
    }
    
    /**
     * @return 返回 failConsuming
     */
    public Long getFailConsuming() {
        return failConsuming;
    }
    
    /**
     * @param 对failConsuming进行赋值
     */
    public void setFailConsuming(Long failConsuming) {
        this.failConsuming = failConsuming;
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
    public Long getConsuming() {
        return consuming;
    }
    
    /**
     * @param 对consuming进行赋值
     */
    public void setConsuming(Long consuming) {
        this.consuming = consuming;
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
     * @return 返回 nextFireDate
     */
    public Date getNextFireDate() {
        return nextFireDate;
    }
    
    /**
     * @param 对nextFireDate进行赋值
     */
    public void setNextFireDate(Date nextFireDate) {
        this.nextFireDate = nextFireDate;
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
     * @return 返回 executeCount
     */
    public int getExecuteCount() {
        return executeCount;
    }
    
    /**
     * @param 对executeCount进行赋值
     */
    public void setExecuteCount(int executeCount) {
        this.executeCount = executeCount;
    }
    
    /**
     * @return 返回 failCount
     */
    public int getFailCount() {
        return failCount;
    }
    
    /**
     * @param 对failCount进行赋值
     */
    public void setFailCount(int failCount) {
        this.failCount = failCount;
    }
    
    /**
     * @return 返回 successCount
     */
    public int getSuccessCount() {
        return successCount;
    }
    
    /**
     * @param 对successCount进行赋值
     */
    public void setSuccessCount(int successCount) {
        this.successCount = successCount;
    }
}
