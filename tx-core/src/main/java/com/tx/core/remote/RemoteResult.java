/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2017年2月10日
 * <修改描述:>
 */
package com.tx.core.remote;

import java.io.Serializable;

import com.tx.core.exceptions.util.AssertUtils;

import io.swagger.annotations.ApiModelProperty;

/**
 * 远程调用结果<br/>
 * <功能详细描述>
 *
 * @author Administrator
 * @version [版本号, 2017年2月10日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class RemoteResult<T> implements Serializable {
    
    /** 注释内容 */
    private static final long serialVersionUID = -4513656123138973055L;
    
    /** 返回码，0-成功，1-通用错误， 其他-详见错误码定义 */
    //之所以只放错误码，如果多一个boolean字段，感觉和错误码重复了，明明可以只用一个字段表示，没有必要引入更多的字段表示
    @ApiModelProperty(value = "返回码，0-成功，非0-失败", required = true, example = "0")
    private int code = -1;
    
    /** 提示消息 */
    @ApiModelProperty(value = "返回消息", required = true, example = "成功")
    private String message;
    
    /** 返回的数据 */
    @ApiModelProperty(value = "返回数据", required = true)
    private T data;
    
    /**
     * <默认构造函数>
     */
    public RemoteResult() {
        super();
    }
    
    /** <默认构造函数> */
    public RemoteResult(T data) {
        super();
        
        this.code = RemoteConstants.CODE_SUCCESS;
        this.message = "成功";
        this.data = data;
    }
    
    /** <默认构造函数> */
    public RemoteResult(int errorCode, String errorMessage) {
        super();
        AssertUtils.isNotEq(RemoteConstants.CODE_SUCCESS,
                errorCode,
                "错误编码不能为成功编码.");
        
        this.code = errorCode;
        this.message = errorMessage;
        this.data = null;
    }
    
    /** <构造函数> */
    public RemoteResult(int code, String message, T data) {
        super();
        
        this.code = code;
        this.message = message;
        this.data = data;
    }
    
    /**
     * @return 返回 code
     */
    public int getCode() {
        return code;
    }
    
    /**
     * @param 对code进行赋值
     */
    public void setCode(int code) {
        this.code = code;
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
     * @return 返回 data
     */
    public T getData() {
        return data;
    }
    
    /**
     * @param 对data进行赋值
     */
    public void setData(T data) {
        this.data = data;
    }
    
    /**
     * 构建成功对象<br/>
     * <功能详细描述>
     * @param data
     * @return [参数说明]
     * 
     * @return Result<OBJECT> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static <OBJECT> RemoteResult<OBJECT> SUCCESS(OBJECT data) {
        AssertUtils.notNull(data, "data is null.");
        RemoteResult<OBJECT> remoteResult = new RemoteResult<OBJECT>(data);
        return remoteResult;
    }
    
    /**
     * 构建失败对象<br/>
     * <功能详细描述>
     * @param errorCode
     * @param errorMessage
     * @return [参数说明]
     * 
     * @return Result<Object> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static RemoteResult<Object> FAIL(int errorCode,
            String errorMessage) {
        RemoteResult<Object> remoteResult = new RemoteResult<Object>(errorCode,
                errorMessage, null);
        return remoteResult;
    }
    
    /**
     * 构建最终结果<br/>
     * <功能详细描述>
     * @param errorCode
     * @param errorMessage
     * @param data
     * @return [参数说明]
     * 
     * @return Result<OBJECT> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static <OBJECT> RemoteResult<OBJECT> RESULT(int errorCode,
            String errorMessage, OBJECT data) {
        RemoteResult<OBJECT> remoteResult = new RemoteResult<OBJECT>(errorCode,
                errorMessage, data);
        return remoteResult;
    }
}
