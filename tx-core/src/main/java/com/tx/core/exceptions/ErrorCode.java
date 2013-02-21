/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-2-6
 * <修改描述:>
 */
package com.tx.core.exceptions;

/**
 * 错误编码枚举类型<br/>
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2013-2-6]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public enum ErrorCode {
    
    /* 参数类异常  */
    /**
     *  参数类异常 
     */
    PARAMETER_EXCEPTION_ERROR_CODE("000-000-001-000", "参数异常"), 
    /**
     * 参数不能为空
     */
    PARAMETER_IS_EMPTY_ERROR_CODE("000-000-001-001", "参数不能为空",PARAMETER_EXCEPTION_ERROR_CODE),
    /**
     * 参数无效
     */
    PARAMETER_IS_INVALID("000-000-001-002", "参数无效",PARAMETER_EXCEPTION_ERROR_CODE),
    
    /* 资源加载类异常 */
    RESOURCE_LOAD_EXCEPTION("000-000-002-000", "资源加载异常"), 
    /* 日志异常 */
    LOG_EXCEPTION("000-000-003-000", "日志异常 "),
    LOG_SERVICELOG_EXCEPTION("000-000-001-002", "业务日志记录异常",LOG_EXCEPTION),
    /* 权限异常 */
    AUTH_CONTEXT_EXCEPTION("000-000-001-002", "权限容器异常"),
    AUTH_CONTEXT_INIT_EXCEPTION("000-000-001-002", "权限容器初始化异常",AUTH_CONTEXT_EXCEPTION),
    /* 自定义标签异常异常 */
    TAGLIB_EXCEPTION("000-100-000-000", "自定义标签异常异常"),
    /* 规则异常 **/
    RULE_EXCEPTION("RULE-004-000-000-000","规则容器异常"),
    /* 工作流引擎访问异常 */
    WORKFLOW_EXCEPTION_ERROR_CODE("WORKFLOW-005-000-000-000","流程异常");
    
    private String code;
    
    private String name;
    
    private ErrorCode parent;
    
    private String message;
    
    private Object[] messageParameter;
    
    private ErrorCode(String code, String name) {
        this.code = code;
        this.name = name;
    }
    
    private ErrorCode(String code, String name,ErrorCode parent) {
        this.code = code;
        this.name = name;
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
     * @return 返回 messageParameter
     */
    public Object[] getMessageParameter() {
        return messageParameter;
    }

    /**
     * @param 对messageParameter进行赋值
     */
    public void setMessageParameter(Object[] messageParameter) {
        this.messageParameter = messageParameter;
    }

    /**
     * @return 返回 parent
     */
    public ErrorCode getParent() {
        return parent;
    }

    /**
     * @param 对parent进行赋值
     */
    public void setParent(ErrorCode parent) {
        this.parent = parent;
    }
}
