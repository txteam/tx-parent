/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-1-16
 * <修改描述:>
 */
package com.tx.component.workflow.model.impl;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.core.Ordered;

import com.tx.core.tree.model.TreeAble;

/**
 * 流程环节定义模型<br/>
 * 1、具体的一个流程环节定义<br/>
 * 2、对应到流程图中具体的一个环节点task<br/>
 * 3、利用该二次封装使节点之间具有父子关系<br/>
 * 4、利用该流程任务定义扩展工作流的插件机制<br/>
 * 
 * @author  brady
 * @version  [版本号, 2013-1-16]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Entity
@Table(name="WF_TASK_DEF")
public class ProTaskDefinition implements Serializable,
        TreeAble<List<ProTaskDefinition>, ProTaskDefinition>,Ordered{
    
    /** 注释内容 */
    private static final long serialVersionUID = 7157946783670094278L;
    
    /** 流程任务定义 */
    @Id
    private String id;
    
    /** 流程父节点id */
    private String parentId;
    
    /** 流程任务定义:映射实际流程中的key */
    @Column(name="wftkdkey")
    private String key;
    
    /** 流程任务节点id */
    @Column(name="wftkdId")
    private String wftkdId;
    
    /** 流程定义id */
    @Column(name="wfdId")
    private String wfdId;
    
    /** 流程环节名 */
    private String name;
    
    /** 流程环节别名 */
    private String alise;
    
    /** 流程环节顺序 */
    @Column(name="taskorder")
    private int order;
    
    /** 节点任务类型： */
    private String taskType;
    
    /** 业务类型 */
    private String serviceType;
    
    /** 流程是否可见 */
    private boolean isViewAble;
    
    /** 流程子环节 */
    private List<ProTaskDefinition> childs;
    
    /** 代理的引擎实例  org.activiti.engine.task.Task*/
    @Transient
    private Object delegate;
    
    /**
     * @return 返回 taskType
     */
    public String getTaskType() {
        return taskType;
    }

    /**
     * @param 对taskType进行赋值
     */
    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    /**
     * @param 对order进行赋值
     */
    public void setOrder(int order) {
        this.order = order;
    }

    /**
     * @return
     */
    @Override
    public int getOrder() {
        return this.order;
    }

    /**
     * @return 返回 alise
     */
    public String getAlise() {
        return alise;
    }

    /**
     * @param 对alise进行赋值
     */
    public void setAlise(String alise) {
        this.alise = alise;
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
     * @return 返回 isViewAble
     */
    public boolean isViewAble() {
        return isViewAble;
    }

    /**
     * @param 对isViewAble进行赋值
     */
    public void setViewAble(boolean isViewAble) {
        this.isViewAble = isViewAble;
    }

    /**
     * @return 返回 childs
     */
    public List<ProTaskDefinition> getChilds() {
        return childs;
    }
    
    /**
     * @param 对childs进行赋值
     */
    public void setChilds(List<ProTaskDefinition> childs) {
        this.childs = childs;
    }

    /**
     * @return 返回 key
     */
    public String getKey() {
        return key;
    }

    /**
     * @param 对key进行赋值
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * @return 返回 serviceType
     */
    public String getServiceType() {
        return serviceType;
    }

    /**
     * @param 对serviceType进行赋值
     */
    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    /**
     * @return 返回 wftkdId
     */
    public String getWftkdId() {
        return wftkdId;
    }

    /**
     * @param 对wftkdId进行赋值
     */
    public void setWftkdId(String wftkdId) {
        this.wftkdId = wftkdId;
    }

    /**
     * @return 返回 wfdId
     */
    public String getWfdId() {
        return wfdId;
    }

    /**
     * @param 对wfdId进行赋值
     */
    public void setWfdId(String wfdId) {
        this.wfdId = wfdId;
    }

    /**
     * @return 返回 delegate
     */
    public Object getDelegate() {
        return delegate;
    }

    /**
     * @param 对delegate进行赋值
     */
    public void setDelegate(Object delegate) {
        this.delegate = delegate;
    }
}
