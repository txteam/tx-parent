/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2012-12-12
 * <修改描述:>
 */
package com.tx.component.servicelog.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;


 /**
  * 业务日志模型类
  * <功能详细描述>
  * 
  * @author  brady
  * @version  [版本号, 2012-12-12]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
@Entity
@Table(name="LOS_SERVICELOG")
public class ServiceLogModel {
    
    /** 业务日志主键id */
    private String id;
    
    /** 业务日志创建时间 */
    private Date createDate;
    
    /** 业务日志信息 */
    private String message;
    
    /** 业务模块信息 */
    private String module;
    
    /** 类名 */
    private String className;
    
    /** 类信息详细描述 */
    private String classDescription;
    
    /** 方法名 */
    private String methodName;
    
    /** 方法描述  */
    private String methodDescription;
    
    /** 是否是controller中记录的日志 */
    private boolean isControllerLog;
    
    //********************当前回话相关信息****************
    //如果回话不存在则记录为空
    /** 
     * 当前操作人id 可为空，
     * 如果当前不存在操作人员，将记录为空
     * 将记录当前操作人员id 
     */
    private String currentOperatorId;
    
    /** 当前请求路径 */
    private String currentRequestPath;
    
    /** 当前请求用户ip地址 */
    private String requestUserIp;

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
     * @return 返回 message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param 对message进行赋值
     */
    public void setMessage(String message) {
        this.message = message;
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
     * @return 返回 classDescription
     */
    public String getClassDescription() {
        return classDescription;
    }

    /**
     * @param 对classDescription进行赋值
     */
    public void setClassDescription(String classDescription) {
        this.classDescription = classDescription;
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
     * @return 返回 methodDescription
     */
    public String getMethodDescription() {
        return methodDescription;
    }

    /**
     * @param 对methodDescription进行赋值
     */
    public void setMethodDescription(String methodDescription) {
        this.methodDescription = methodDescription;
    }

    /**
     * @return 返回 isControllerLog
     */
    public boolean isControllerLog() {
        return isControllerLog;
    }

    /**
     * @param 对isControllerLog进行赋值
     */
    public void setControllerLog(boolean isControllerLog) {
        this.isControllerLog = isControllerLog;
    }

    /**
     * @return 返回 currentOperatorId
     */
    public String getCurrentOperatorId() {
        return currentOperatorId;
    }

    /**
     * @param 对currentOperatorId进行赋值
     */
    public void setCurrentOperatorId(String currentOperatorId) {
        this.currentOperatorId = currentOperatorId;
    }

    /**
     * @return 返回 currentRequestPath
     */
    public String getCurrentRequestPath() {
        return currentRequestPath;
    }

    /**
     * @param 对currentRequestPath进行赋值
     */
    public void setCurrentRequestPath(String currentRequestPath) {
        this.currentRequestPath = currentRequestPath;
    }

    /**
     * @return 返回 requestUserIp
     */
    public String getRequestUserIp() {
        return requestUserIp;
    }

    /**
     * @param 对requestUserIp进行赋值
     */
    public void setRequestUserIp(String requestUserIp) {
        this.requestUserIp = requestUserIp;
    }
}
