/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-1-16
 * <修改描述:>
 */
package com.tx.component.workflow.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.tx.component.workflow.WorkFlowConstants;

/**
 * 流程定义模型<br/>
 * 1、用以支持流程多版本时，根据具体的流程定义名<br/>
 * 2、流程定义模型，对应具体的一个流程定义文件<br/>
 * 3、原始的processDefinition不会与业务相关，当应用到系统中<br/>
 * 流程定义可能存在部分业务流程适用于该业务类型，部分流程又适用于其他业务类型
 * 4、业务类型的实现，由于现有系统暂不需要，所以现在仅做二次封装并不实现<br/>
 * 5、由于processDefinition现在仅作一次二次存储，并且有缓存存在，所以在性能上讲也不会造成影响<br/>
 * @author  brady
 * @version  [版本号, 2013-1-16]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Entity
@Table(name = "WF_PROCESS_DEF")
public class ProcessDefinition implements Serializable {
    
    /** 注释内容 */
    private static final long serialVersionUID = 6540929500393797624L;
    
    /** 流程定义id:由系统自生成 */
    @Id
    private String id;
    
    /** 流程定义id */
    @Column(name = "wfdid")
    private String wfdId;
    
    /** 流程定义key:对应activiti中的key */
    @Column(name = "wfdkey")
    private String key;
    
    /** 流程名 */
    private String name;
    
    /** 流程类别 */
    private String category;
    
    /** 流程版本号 */
    private String version;
    
    /** 流程定义对应的业务类型 */
    private String serviceType;
    
    /** 流程状态:用以支持，测试态，运营态等流程状态 */
    private String state = WorkFlowConstants.PROCESS_DEFINITION_STATE_TEST;
    
    /** 代理的引擎实例 可对应org.activiti.engine.repository.ProcessDefinition */
    @Transient
    private Object delegate;
    
    private Date createDate;
    
    private Date lastUpdateDate;
    
    /**
     * @return
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
     * @return
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
     * @return
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
     * @return
     */
    public String getVersion() {
        return version;
    }
    
    /**
     * @param 对version进行赋值
     */
    public void setVersion(String version) {
        this.version = version;
    }
    
    /**
     * @return
     */
    public String getState() {
        return state;
    }
    
    /**
     * @param 对state进行赋值
     */
    public void setState(String state) {
        this.state = state;
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
     * @return 返回 category
     */
    public String getCategory() {
        return category;
    }
    
    /**
     * @param 对category进行赋值
     */
    public void setCategory(String category) {
        this.category = category;
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
}
