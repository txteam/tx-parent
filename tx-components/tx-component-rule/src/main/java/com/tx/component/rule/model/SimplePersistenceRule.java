/*
 * 描          述:  <描述>
 * 修  改   人:  PengQingyang
 * 修改时间:  2013-2-26
 * <修改描述:>
 */
package com.tx.component.rule.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.util.MultiValueMap;

import com.tx.core.exceptions.parameter.ParameterIsEmptyException;
import com.tx.core.exceptions.parameter.ParameterIsInvalidException;

/**
 * 规则基础实现<br/>
 *     用以支持规则写入数据库中<br/>
 * 
 * <功能详细描述>
 * 
 * @author  PengQingyang
 * @version  [版本号, 2013-2-26]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Entity
@Table(name = "ru_rule_def")
public class SimplePersistenceRule implements Serializable, Rule {
    
    /** 注释内容 */
    private static final long serialVersionUID = 3816894065600661189L;
    
    /** 规则在数据库中的id */
    @Id
    private String id;
    
    /** 规则唯一键 */
    private String rule;
    
    /** 规则名 */
    private String name;
    
    /** 业务类型 */
    private String serviceType;
    
    /** 备注 */
    private String remark;
    
    /** 规则类型 */
    private RuleTypeEnum ruleType;
    
    /** 规则状态 */
    private RuleStateEnum state;
    
    /** 规则包含的参数集 */
    @Transient
    private List<SimpleRuleParamEnum> params;
    
    /** 是否拥有byte类型的参数 */
    private boolean isHasByteParam = false;
    
    /** 是否拥有value类型的参数 */
    private boolean isHasValueParam = false;
    
    /** byte类型规则参数 */
    @Transient
    private MultiValueMap<SimpleRuleParamEnum, SimpleRulePropertyByte> bytePropertyValues;
    
    /** string类型规则参数 */
    @Transient
    private MultiValueMap<SimpleRuleParamEnum, SimpleRulePropertyValue> stringPropertyValues;
    
    /**
      * 获取byte类型属性值，单值和多值的情况都可以从该方法获取
      * <功能详细描述>
      * @param propertyParam
      * @return [参数说明]
      * 
      * @return List<SimpleRulePropertyByte> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public List<SimpleRulePropertyByte> getBytePropertyList(
            SimpleRuleParamEnum propertyParam) {
        if (propertyParam == null) {
            throw new ParameterIsEmptyException("propertyParam is empty.");
        }
        if (!propertyParam.isBlob()) {
            throw new ParameterIsInvalidException(
                    "propertyParam is not byte type.{}", propertyParam.getKey());
        }
        
        return this.bytePropertyValues.get(propertyParam);
    }
    
    /**
      * 获取byte类型属性值，如果属性为多值的情况，将会抛出异常
      * <功能详细描述>
      * @param propertyParam
      * @return [参数说明]
      * 
      * @return SimpleRulePropertyByte [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public SimpleRulePropertyByte getByteProperty(
            SimpleRuleParamEnum propertyParam) {
        if (propertyParam == null) {
            throw new ParameterIsEmptyException("propertyParam is empty.");
        }
        if (!propertyParam.isBlob()) {
            throw new ParameterIsInvalidException(
                    "propertyParam is not byte type.{}", propertyParam.getKey());
        }
        if (propertyParam.isMultiProerty()) {
            throw new ParameterIsInvalidException("propertyParam is Multi.{}",
                    propertyParam.getKey());
        }
        
        return this.bytePropertyValues.getFirst(propertyParam);
    }
    
    /**
     * 获取value类型属性值，单值和多值的情况都可以从该方法获取
     * <功能详细描述>
     * @param propertyParam
     * @return [参数说明]
     * 
     * @return List<SimpleRulePropertyByte> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    public List<SimpleRulePropertyValue> getStringPropertyList(
            SimpleRuleParamEnum propertyParam) {
        if (propertyParam == null) {
            throw new ParameterIsEmptyException("propertyParam is empty.");
        }
        if (propertyParam.isBlob()) {
            throw new ParameterIsInvalidException(
                    "propertyParam is byte type.{}: rule {}",
                    propertyParam.getKey(), this.rule);
        }
        
        return this.stringPropertyValues.get(propertyParam);
    }
    
    /**
     * 获取value类型属性值，单值和多值的情况都可以从该方法获取
     * <功能详细描述>
     * @param propertyParam
     * @return [参数说明]
     * 
     * @return List<SimpleRulePropertyByte> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    public SimpleRulePropertyValue getStringProperty(
            SimpleRuleParamEnum propertyParam) {
        if (propertyParam == null) {
            throw new ParameterIsEmptyException("propertyParam is empty.");
        }
        if (propertyParam.isBlob()) {
            throw new ParameterIsInvalidException(
                    "propertyParam is byte type.{}: rule {}",
                    propertyParam.getKey(), this.rule);
        }
        
        return this.stringPropertyValues.getFirst(propertyParam);
    }
    
    /**
     * <默认构造函数>
     */
    public SimplePersistenceRule() {
        super();
    }
    
    /**
     * <默认构造函数>
     */
    public SimplePersistenceRule(Rule rule) {
        super();
        this.rule = rule.rule();
        this.ruleType = rule.getRuleType();
        this.serviceType = rule.getServiceType();
        this.state = RuleStateEnum.OPERATION;
    }
    
    /**
     * @return
     */
    public RuleTypeEnum getRuleType() {
        return this.ruleType;
    }
    
    /**
     * @return
     */
    public String getServiceType() {
        return this.serviceType;
    }
    
    /**
     * @return 返回 rule
     */
    public String getRule() {
        return rule;
    }
    
    /**
     * @param 对rule进行赋值
     */
    public void setRule(String rule) {
        this.rule = rule;
    }
    
    /**
     * @param 对ruleType进行赋值
     */
    public void setRuleType(RuleTypeEnum ruleType) {
        this.ruleType = ruleType;
    }
    
    /**
     * @param 对serviceType进行赋值
     */
    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }
    
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
     * @return 返回 state
     */
    public RuleStateEnum getState() {
        return state;
    }
    
    /**
     * @param 对state进行赋值
     */
    public void setState(RuleStateEnum state) {
        this.state = state;
    }
    
    /**
     * @return
     */
    @Override
    public String rule() {
        return this.rule;
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
     * @return 返回 remark
     */
    public String getRemark() {
        return remark;
    }
    
    /**
     * @param 对remark进行赋值
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }
    
    /**
     * @return 返回 bytePropertyValues
     */
    public MultiValueMap<SimpleRuleParamEnum, SimpleRulePropertyByte> getBytePropertyValues() {
        return bytePropertyValues;
    }
    
    /**
     * @param 对bytePropertyValues进行赋值
     */
    public void setBytePropertyValues(
            MultiValueMap<SimpleRuleParamEnum, SimpleRulePropertyByte> bytePropertyValues) {
        this.bytePropertyValues = bytePropertyValues;
    }
    
    /**
     * @return 返回 stringPropertyValues
     */
    public MultiValueMap<SimpleRuleParamEnum, SimpleRulePropertyValue> getStringPropertyValues() {
        return stringPropertyValues;
    }
    
    /**
     * @param 对stringPropertyValues进行赋值
     */
    public void setStringPropertyValues(
            MultiValueMap<SimpleRuleParamEnum, SimpleRulePropertyValue> stringPropertyValues) {
        this.stringPropertyValues = stringPropertyValues;
    }
    
    /**
     * @return 返回 params
     */
    public List<SimpleRuleParamEnum> getParams() {
        return params;
    }
    
    /**
     * @param 对params进行赋值
     */
    public void setParams(List<SimpleRuleParamEnum> params) {
        this.params = params;
        
        if(this.params != null){
            for(SimpleRuleParamEnum paramEnumTemp : this.params){
                if(paramEnumTemp.isBlob()){
                    this.isHasByteParam = true;
                }else{
                    this.isHasValueParam = true;
                }
            }
        }
    }

    /**
     * @return 返回 isHasByteParam
     */
    public boolean isHasByteParam() {
        return isHasByteParam;
    }

    /**
     * @param 对isHasByteParam进行赋值
     */
    public void setHasByteParam(boolean isHasByteParam) {
        this.isHasByteParam = isHasByteParam;
    }

    /**
     * @return 返回 isHasValueParam
     */
    public boolean isHasValueParam() {
        return isHasValueParam;
    }

    /**
     * @param 对isHasValueParam进行赋值
     */
    public void setHasValueParam(boolean isHasValueParam) {
        this.isHasValueParam = isHasValueParam;
    }
    
}
