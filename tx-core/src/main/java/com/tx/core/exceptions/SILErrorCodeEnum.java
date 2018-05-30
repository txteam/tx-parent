/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2017年2月11日
 * <修改描述:>
 */
package com.tx.core.exceptions;

/**
 * <功能简述>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2017年2月11日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public enum SILErrorCodeEnum implements ErrorCode {
    
    /* -------　参数异常 start ------- */
    ARG_ILLEGAL_ERROR(101000, "参数非法"),
    
    ARG_EMPTY_ERROR(101011, "参数为空"),
    
    ARG_NOT_EMPTY_ERROR(101012, "参数非空"),
    
    ARG_NULL_ERROR(101002, "参数为空"),
    
    ARG_NOT_NULL_ERROR(101002, "参数非空"),
    
    ARG_TYPE_ILLEGAL_ERROR(102000, "参数类型非法"),
    
    ARG_TYPE_NOT_MATCHED_ERROR(102001, "参数类型不匹配"),
    
    ARG_TYPE_IS_INTERFACE_ERROR(102101, "参数为接口"),
    
    ARG_TYPE_NOT_INTERFACE_ERROR(102201, "参数不为接口"),
    
    ARG_TYPE_IS_ABSTRACT_CLASS_ERROR(102102, "参数为抽象类"),
    
    ARG_TYPE_NOT_ABSTRACT_CLASS_ERROR(102202, "参数不为抽象类"),
    
    ARG_TYPE_IS_ENUM_ERROR(102302, "参数为枚举"),
    
    ARG_TYPE_NOT_ENUM_ERROR(102302, "参数不为枚举"),
    /* -------　参数异常 end   ------- */
    
    /* -------　反射异常 start ------- */
    REFLECTION_ERROR(111000, "反射调用异常"),
    
    JPA_META_CLASS_NEW_INSTANCE_ERROR(111001, "JPA实例化异常"),
    
    INVALID_GETTER_METHOD_ERROR(111002, "无效的GETTER方法"),
    
    INVALID_SETTER_METHOD_ERROR(111003, "无效的SETTER方法"),
    /* -------　反射异常 end   ------- */
    
    /* -------　Resource异常 start ------- */
    RESOURCE_ACCESS_ERROR(120000, "资源访问异常"),
    
    RESOURCE_IS_EXSIT_ERROR(120001, "资源已存在"),
    
    RESOURCE_NOT_EXSIT_ERROR(120002, "资源不存在"),
    
    RESOURCE_PARSE_ERROR(121003, "资源解析异常"),
    
    RESOURCE_READ_ERROR(123001, "资源读取异常"),
    
    RESOURCE_WRITE_ERROR(123002, "资源写入异常"),
    /* -------　Resource异常 end   ------- */
    
    /* -------　method invoke异常 start ------- */
    METHOD_INVOKE_ACCESS_ERROR(151001,"方法注入访问异常"),
    
    METHOD_INVOKE_ARG_ILLEGAL_ERROR(151002,"方法参数异常"),
    
    METHOD_INVOKE_TARGET_ERROR(151003,"方法参数异常"),
    
    METHOD_INVOKE_ARG_RESOLVE_ERROR(151101,"方法参数解析异常"),
    
    METHOD_INVOKE_ARG_RESOLVE_BIND_ERROR(151101,"方法参数解析异常"),
    
    
    /* -------　method invoke异常 end   ------- */
    
    /* -------　Remote异常 start ------- */
    REMOTE_ACCESS_ERROR(130000, "资源访问异常"),
    
    HTTP_EXECUTE_ERROR(131000, "发送HTTP请求异常"),
    
    HTTP_BEFORE_EXECUTE_ERROR(131001, "发送HTTP请求异常"),
    
    HTTP_EXECUTEING_ERROR(131002, "发送HTTP请求异常"),
    
    HTTP_AFTER_EXECUTE_ERROR(131003, "发送HTTP请求异常"),
    
    HTTP_SOCKET_ERROR(131004, "发送HTTP请求异常")
    /* -------　Remote异常 end   ------- */
    
    
    
    /* -------　context异常 start ------- */
    
    /* -------　context异常 end   ------- */
    ;
    
    /** 编码 */
    private final int code;
    
    /** 内容 */
    private final String message;
    
    /** <默认构造函数> */
    private SILErrorCodeEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }
    
    /**
     * @return 返回 code
     */
    public int getCode() {
        return code;
    }
    
    /**
     * @return 返回 message
     */
    public String getMessage() {
        return message;
    }
}
