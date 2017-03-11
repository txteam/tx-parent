package com.tx.component.file.ueditor.model;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
  * 默认的UEditorResult<br/>
  * <功能详细描述>
  * 
  * @author  Administrator
  * @version  [版本号, 2017年3月9日]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
 */
public class DefaultUEditorResult implements UEditorResult {
    
    /** state: state */
    private boolean state = false;
    
    /** info: info消息 */
    private String info = null;
    
    /** infoMap: infoMap */
    private Map<String, String> infoMap = new HashMap<String, String>();
    
    /** 默认的UEditor结果 */
    public DefaultUEditorResult() {
        this.state = true;
    }
    
    /** 默认的UEditor结果 */
    public DefaultUEditorResult(boolean state) {
        this.setState(state);
    }
    
    /** 默认的UEditor结果 */
    public DefaultUEditorResult(boolean state, String info) {
        this.setState(state);
        this.info = info;
    }
    
    /** 默认的UEditor结果 */
    public DefaultUEditorResult(boolean state, int infoCode) {
        this.setState(state);
        this.info = UEditorResultCode.getStateInfo(infoCode);
    }
    
    /**
     * 是否成功<br/>
     * @return
     */
    public boolean isSuccess() {
        return this.state;
    }
    
    /**
      * 设置状态<br/>
      * <功能详细描述>
      * @param state [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public void setState(boolean state) {
        this.state = state;
    }
    
    /**
      * 设置信息<br/>
      * <功能详细描述>
      * @param info [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public void setInfo(String info) {
        this.info = info;
    }
    
    /**
      * 根据信息编码设置信息<br/>
      * <功能详细描述>
      * @param infoCode [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public void setInfo(int infoCode) {
        this.info = UEditorResultCode.getStateInfo(infoCode);
    }
    
    /**
     * 转换为JSON字符串<br/>
     * @return
     */
    @Override
    public String toJSONString() {
        return this.toString();
    }
    
    /**
     * 转换为字符串<br/>
     * @return
     */
    public String toString() {
        String key = null;
        String stateVal = this.isSuccess() ? UEditorResultCode.getStateInfo(UEditorResultCode.SUCCESS)
                : this.info;
        
        StringBuilder builder = new StringBuilder();
        builder.append("{\"state\": \"" + stateVal + "\"");
        Iterator<String> iterator = this.infoMap.keySet().iterator();
        while (iterator.hasNext()) {
            key = iterator.next();
            builder.append(",\"" + key + "\": \"" + this.infoMap.get(key)
                    + "\"");
        }
        builder.append("}");
        
        return UEditorEncoder.toUnicode(builder.toString());
    }
    
    /**
     * 写入信息Map内容
     * @param name
     * @param val
     */
    @Override
    public void putInfo(String name, String val) {
        this.infoMap.put(name, val);
    }
    
    /**
     * 写入信息Map内容
     * @param name
     * @param val
     */
    @Override
    public void putInfo(String name, long val) {
        this.putInfo(name, val + "");
    }
    
}
