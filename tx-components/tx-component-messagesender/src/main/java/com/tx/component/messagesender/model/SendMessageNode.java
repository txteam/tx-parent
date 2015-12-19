/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2015年12月19日
 * <修改描述:>
 */
package com.tx.component.messagesender.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 消息节点<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2015年12月19日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class SendMessageNode {
    
    /** key值 */
    private String key = "value";
    
    /** value值 */
    private String value;
    
    /** 根节点 */
    private final List<SendMessageNode> childs = new ArrayList<>();
    
    /** 根节点映射 */
    private final Map<String, SendMessageNode> childMap = new HashMap<String, SendMessageNode>();

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
     * @return 返回 value
     */
    public String getValue() {
        return value;
    }

    /**
     * @param 对value进行赋值
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * @return 返回 childs
     */
    public List<SendMessageNode> getChilds() {
        return childs;
    }

    /**
     * @return 返回 childMap
     */
    public Map<String, SendMessageNode> getChildMap() {
        return childMap;
    }
}
