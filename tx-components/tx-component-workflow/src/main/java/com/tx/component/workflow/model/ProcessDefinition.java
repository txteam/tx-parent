/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-1-16
 * <修改描述:>
 */
package com.tx.component.workflow.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

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
@Table(name="WF_PROCESS_DEF")
public class ProcessDefinition implements Serializable{
    
    /** 注释内容 */
    private static final long serialVersionUID = 6540929500393797624L;
    
    /** 流程定义id */
    private String id;
    
    /** 流程名 */
    private String name;
    
    /** 流程定义对应的业务类型 */
    private String serviceType;
    
    /** 流程版本号 */
    private String version;
    
    /** 流程状态:用以支持，测试态，运营态等流程状态 */
    private String state = WorkFlowConstants.PROCESS_DEFINITION_STATE_TEST;
    
    /** 
     * 定义文件夹所在路径，
     * 该文件夹下可能为一个zip文件或多个文件，
     * 其中包含流程定义文件 
     */
    private String definitionFileFolderPath;
    
    /**
     * 流程定义文件名
     */
    private String fileName;
    
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
     * @return
     */
    
    public String getFileName() {
        return fileName;
    }

    /**
     * @param 对fileName进行赋值
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * @return
     */
    public String getDefinitionFileFolderPath() {
        return definitionFileFolderPath;
    }
    
    /**
     * @param 对definitionFileFolderPath进行赋值
     */
    public void setDefinitionFileFolderPath(String definitionFileFolderPath) {
        this.definitionFileFolderPath = definitionFileFolderPath;
    }
}
