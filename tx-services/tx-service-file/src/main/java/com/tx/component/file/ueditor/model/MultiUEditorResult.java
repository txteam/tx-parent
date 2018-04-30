/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2017年3月11日
 * <修改描述:>
 */
package com.tx.component.file.ueditor.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.tx.component.file.ueditor.Encoder;

/**
 * 返回多文件结果<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2017年3月11日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class MultiUEditorResult implements UEditorResult {
    
    /** state: state */
    private boolean state = false;
    
    /** info: info消息 */
    private String info = null;
    
    /** infoMap: infoMap */
    private Map<String, String> infoMap = new HashMap<String, String>();
    
    /** intMap: intMap */
    private Map<String, Long> intMap = new HashMap<String, Long>();
    
    /** stateList: stateList */
    private List<String> resultList = new ArrayList<String>();
    
    /** 默认的UEditor结果 */
    public MultiUEditorResult(boolean state) {
        this.state = state;
    }
    
    /** 默认的UEditor结果 */
    public MultiUEditorResult(boolean state, String info) {
        this.state = state;
        this.info = info;
    }
    
    /** 默认的UEditor结果 */
    public MultiUEditorResult(boolean state, int infoKey) {
        this.state = state;
        this.info = UEditorResultCode.getStateInfo(infoKey);
    }
    
    /**
     * 是否成功<br/>
     * @return
     */
    @Override
    public boolean isSuccess() {
        return this.state;
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
     * 设置状态<br/>
     * <功能详细描述>
     * @param state [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public void addResult(UEditorResult result) {
        resultList.add(result.toJSONString());
    }
    
    @Override
    public String toJSONString() {
        String stateVal = this.isSuccess() ? UEditorResultCode.getStateInfo(UEditorResultCode.SUCCESS)
                : this.info;
        
        StringBuilder builder = new StringBuilder();
        builder.append("{\"state\": \"" + stateVal + "\"");
        
        // 数字转换
        Iterator<String> iterator = this.intMap.keySet().iterator();
        while (iterator.hasNext()) {
            stateVal = iterator.next();
            builder.append(",\"" + stateVal + "\": "
                    + this.intMap.get(stateVal));
        }
        
        iterator = this.infoMap.keySet().iterator();
        while (iterator.hasNext()) {
            stateVal = iterator.next();
            builder.append(",\"" + stateVal + "\": \""
                    + this.infoMap.get(stateVal) + "\"");
        }
        
        builder.append(", list: [");
        iterator = this.resultList.iterator();
        while (iterator.hasNext()) {
            builder.append(iterator.next() + ",");
        }
        if (this.resultList.size() > 0) {
            builder.deleteCharAt(builder.length() - 1);
        }
        builder.append(" ]}");
        
        return Encoder.toUnicode(builder.toString());
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
        this.intMap.put(name, val);
    }
}
