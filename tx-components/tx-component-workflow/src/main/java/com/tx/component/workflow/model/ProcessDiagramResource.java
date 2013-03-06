/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-3-6
 * <修改描述:>
 */
package com.tx.component.workflow.model;



 /**
  * 流程图资源<br/>
  * <功能详细描述>
  * 
  * @author  brady
  * @version  [版本号, 2013-3-6]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public class ProcessDiagramResource {
    
    /** 流程资源名 */
    private String resourceName;
    
    /** 流程定义id */
    private String processDefinitionId;
    
    /** 流程定义key */
    private String processDefinitionKey;
    
    /** 流程定义key对应版本 */
    private String version;
    
    /** 流程图资源 */
    private byte[] inputStreamBytes;

    /**
     * @return 返回 resourceName
     */
    public String getResourceName() {
        return resourceName;
    }

    /**
     * @param 对resourceName进行赋值
     */
    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    /**
     * @return 返回 processDefinitionId
     */
    public String getProcessDefinitionId() {
        return processDefinitionId;
    }

    /**
     * @param 对processDefinitionId进行赋值
     */
    public void setProcessDefinitionId(String processDefinitionId) {
        this.processDefinitionId = processDefinitionId;
    }

    /**
     * @return 返回 processDefinitionKey
     */
    public String getProcessDefinitionKey() {
        return processDefinitionKey;
    }

    /**
     * @param 对processDefinitionKey进行赋值
     */
    public void setProcessDefinitionKey(String processDefinitionKey) {
        this.processDefinitionKey = processDefinitionKey;
    }

    /**
     * @return 返回 version
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
     * @return 返回 inputStreamBytes
     */
    public byte[] getInputStreamBytes() {
        return inputStreamBytes;
    }

    /**
     * @param 对inputStreamBytes进行赋值
     */
    public void setInputStreamBytes(byte[] inputStreamBytes) {
        this.inputStreamBytes = inputStreamBytes;
    }
}
