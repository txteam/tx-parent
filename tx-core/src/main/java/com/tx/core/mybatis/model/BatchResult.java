/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2012-12-5
 * <修改描述:>
 */
package com.tx.core.mybatis.model;

import java.util.HashMap;
import java.util.Map;

/**
 * 批量操作结果<br/>
 * 
 * @author brady
 * @version [版本号, 2012-12-5]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Deprecated
public class BatchResult {
    
    /** 是否成功 */
    private boolean success = true;
    
    /** 总数 */
    private int totalNum = 0;
    
    /** 错误数目 */
    private int errorNum = 0;
    
    private Map<Integer, Object> errorRownum2ObjectMap = new HashMap<Integer, Object>();
    
    private Map<Integer, Throwable> errorRownum2ExceptionMap = new HashMap<Integer, Throwable>();
    
    private Map<Integer, String> errorRownum2MessageMapping = new HashMap<Integer, String>();
    
    /**
     * @return 返回 success
     */
    public boolean isSuccess() {
        return success;
    }
    
    /**
     * @param 对success进行赋值
     */
    public void setSuccess(boolean success) {
        this.success = success;
    }
    
    /**
     * @return 返回 totalNum
     */
    public int getTotalNum() {
        return totalNum;
    }
    
    /**
     * @param 对totalNum进行赋值
     */
    public void setTotalNum(int totalNum) {
        this.totalNum = totalNum;
    }
    
    /**
     * @return 返回 errorNum
     */
    public int getErrorNum() {
        return errorNum;
    }
    
    /**
     * @param 对errorNum进行赋值
     */
    public void setErrorNum(int errorNum) {
        this.errorNum = errorNum;
    }
    
    /**
     * @return 返回 successNum
     */
    public int getSuccessNum() {
        return this.totalNum - this.errorNum;
    }
    
    /**
     * 当发生异常时设置 <功能详细描述> [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public void addErrorInfo(Object data, int rownum, Throwable ex) {
        success = false;
        
        if (!this.errorRownum2ObjectMap.containsKey(rownum)) {
            this.errorNum++;
        }
        this.errorRownum2ObjectMap.put(rownum, data);
        this.errorRownum2ExceptionMap.put(rownum, ex);
        this.errorRownum2MessageMapping.put(rownum, ex.toString());
    }
    
    /**
     * @return 返回 errorRownum2ObjectMap
     */
    public Map<Integer, Object> getErrorRownum2ObjectMap() {
        return errorRownum2ObjectMap;
    }
    
    /**
     * @param 对errorRownum2ObjectMap进行赋值
     */
    public void setErrorRownum2ObjectMap(
            Map<Integer, Object> errorRownum2ObjectMap) {
        this.errorRownum2ObjectMap = errorRownum2ObjectMap;
    }
    
    /**
     * @return 返回 errorRownum2ExceptionMap
     */
    public Map<Integer, Throwable> getErrorRownum2ExceptionMap() {
        return errorRownum2ExceptionMap;
    }
    
    /**
     * @param 对errorRownum2ExceptionMap进行赋值
     */
    public void setErrorRownum2ExceptionMap(
            Map<Integer, Throwable> errorRownum2ExceptionMap) {
        this.errorRownum2ExceptionMap = errorRownum2ExceptionMap;
    }
    
    /**
     * @return 返回 errorRownum2MessageMapping
     */
    public Map<Integer, String> getErrorRownum2MessageMapping() {
        return errorRownum2MessageMapping;
    }
    
    /**
     * @param 对errorRownum2MessageMapping进行赋值
     */
    public void setErrorRownum2MessageMapping(
            Map<Integer, String> errorRownum2MessageMapping) {
        this.errorRownum2MessageMapping = errorRownum2MessageMapping;
    }
}
