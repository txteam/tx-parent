/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-1-19
 * <修改描述:>
 */
package com.tx.component.rule.loader.xml.model;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import com.tx.component.rule.loader.RuleTypeEnum;

/**
 * 规则项配置
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2013-1-19]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@XStreamAlias("rule")
public class RuleItemConfig {
    
    /** 规则key，不能为空 */
    @XStreamAsAttribute
    @XStreamAlias("key")
    private String key;
    
    /** 规则名 */
    @XStreamAsAttribute
    @XStreamAlias("name")
    private String name = "";
    
    /**
     * 规则业务类型
     */
    @XStreamAsAttribute
    @XStreamAlias("serviceType")
    private String serviceType = "";
    
    /** 
     * 规则类型 可以为drools规则，可以为其他规则的实现<br/>
     * 可以为ognl<br/>
     * 可以为
     */
    @XStreamAsAttribute
    @XStreamAlias("ruleType")
    private RuleTypeEnum ruleType;
    
    /**
     * byte类型参数
     */
    @XStreamImplicit(itemFieldName="byte")
    private List<ByteParam> byteList;
    
    /**
     * value类型参数
     */
    @XStreamImplicit(itemFieldName="value")
    private List<ValueParam> valueList;

    
    /**
     * @return 返回 ruleType
     */
    public RuleTypeEnum getRuleType() {
        return ruleType;
    }
    
    /**
     * @param 对ruleType进行赋值
     */
    public void setRuleType(RuleTypeEnum ruleType) {
        this.ruleType = ruleType;
    }
    
    /**
     * @return 返回 serviceType
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
     * @return 返回 byteList
     */
    public List<ByteParam> getByteList() {
        return byteList;
    }

    /**
     * @param 对byteList进行赋值
     */
    public void setByteList(List<ByteParam> byteList) {
        this.byteList = byteList;
    }

    /**
     * @return 返回 valueList
     */
    public List<ValueParam> getValueList() {
        return valueList;
    }

    /**
     * @param 对valueList进行赋值
     */
    public void setValueList(List<ValueParam> valueList) {
        this.valueList = valueList;
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
}
