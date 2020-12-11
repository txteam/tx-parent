/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2014年5月24日
 * <修改描述:>
 */
package com.tx.component.task.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 事务定义<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2014年5月24日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Entity
@Table(name = "TD_TASK_DEF")
public class TaskDef implements Serializable {
    
    /** 注释内容 */
    private static final long serialVersionUID = -2388525566988400621L;
    
    /** 唯一键 */
    @Id
    @Column(nullable = false, length = 64)
    private String id;
    
    /** 父级任务编码：存在父级的任务都不能自动触发 */
    @Column(nullable = true, length = 64)
    private String parentId;
    
    /** module: 所属模块 */
    @Column(nullable = false, length = 64)
    private String module;
    
    /** bean名 */
    @Column(nullable = true, length = 64)
    private String beanName;
    
    /** bean名 */
    @Column(nullable = true, length = 256)
    private String className;
    
    /** 方法名：启动期间将会自动判断类中是否有重写方法：原则上允许出现同名方法，避免出现code,beanName,methodName一致，但方法不一致的情形 */
    @Column(nullable = true, length = 64)
    private String methodName;
    
    /** 任务描述的参数Map */
    @Column(nullable = true, length = 4000)
    private String attributes;
    
    /** 事务名 */
    @Column(nullable = true, length = 64)
    private String name;
    
    /** 描述 */
    @Column(nullable = true, length = 1000)
    private String remark;
    
    /** 是否有效 */
    private boolean valid;
    
    /** 是否可执行 */
    private boolean executable;
    
    /** 优先级 */
    private int priority;
    
    /** 最后更新时间 */
    private Date lastUpdateDate;
    
    /** 创建时间 */
    private Date createDate;
    
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
     * @return 返回 parentId
     */
    public String getParentId() {
        return parentId;
    }
    
    /**
     * @param 对parentId进行赋值
     */
    public void setParentId(String parentId) {
        this.parentId = parentId;
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
     * @return 返回 beanName
     */
    public String getBeanName() {
        return beanName;
    }
    
    /**
     * @param 对beanName进行赋值
     */
    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }
    
    /**
     * @return 返回 className
     */
    public String getClassName() {
        return className;
    }
    
    /**
     * @param 对className进行赋值
     */
    public void setClassName(String className) {
        this.className = className;
    }
    
    /**
     * @return 返回 methodName
     */
    public String getMethodName() {
        return methodName;
    }
    
    /**
     * @param 对methodName进行赋值
     */
    public void setMethodName(String methodName) {
        this.methodName = methodName;
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
    
    /**
     * @return 返回 executable
     */
    public boolean isExecutable() {
        return executable;
    }
    
    /**
     * @param 对executable进行赋值
     */
    public void setExecutable(boolean executable) {
        this.executable = executable;
    }
    
    /**
     * @return 返回 priority
     */
    public int getPriority() {
        return priority;
    }
    
    /**
     * @param 对priority进行赋值
     */
    public void setPriority(int priority) {
        this.priority = priority;
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
}
