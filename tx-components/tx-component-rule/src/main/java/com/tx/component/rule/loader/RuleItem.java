/*
 * 描          述:  <描述>
 * 修  改   人:  PengQingyang
 * 修改时间:  2013-2-26
 * <修改描述:>
 */
package com.tx.component.rule.loader;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.tx.component.rule.context.Rule;
import com.tx.core.exceptions.util.AssertUtils;

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
@Table(name = "RULE_RULEITEM")
public class RuleItem implements Serializable, Rule {
    
    /** 注释内容 */
    private static final long serialVersionUID = 3816894065600661189L;
    
    /** 规则唯一键key */
    private String key;
    
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
    
    /** 创建日期 */
    private Date createDate;
    
    /** 最后更新日期 */
    private Date lastUpdateDate;
    
    /** 是否可编辑 */
    @Transient
    private boolean modifyAble;
    
    /** 规则包含的参数集 */
    @Transient
    private Map<String, RuleItemParam> paramMap = new HashMap<String, RuleItemParam>();
    
    /** byte类型规则参数 */
    @Transient
    private MultiValueMap<String, RuleItemByteParam> byteParamMultiValueMap = new LinkedMultiValueMap<String, RuleItemByteParam>();
    
    /** string类型规则参数 */
    @Transient
    private MultiValueMap<String, RuleItemValueParam> valueParamMultiValueMap = new LinkedMultiValueMap<String, RuleItemValueParam>();
    
    /** object类型规则参数：在methodRule中会用到 */
    @Transient
    private MultiValueMap<String, Object> objectParamMultiValueMap = new LinkedMultiValueMap<String, Object>();
    
    /**
     * <默认构造函数>
     */
    public RuleItem() {
        super();
    }
    
    /**
     * <默认构造函数>
     */
    public RuleItem(Rule rule) {
        super();
        this.key = rule.getKey();
        this.name = rule.getName();
        this.ruleType = rule.getRuleType();
        this.serviceType = rule.getServiceType();
        this.modifyAble = rule.isModifyAble();
        this.state = rule.getState();
    }
    
    /**
      * 载入规则项参 
      *<功能详细描述>
      * @param ruleItemParam [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public void loadParam(Set<RuleItemParam> ruleItemParam) {
        
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
     * @return 返回 modifyAble
     */
    public boolean isModifyAble() {
        return modifyAble;
    }
    
    /**
     * @param 对modifyAble进行赋值
     */
    public void setModifyAble(boolean modifyAble) {
        this.modifyAble = modifyAble;
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
     * @return 返回 lastUpdateDate
     */
    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }
    
    /**
     * @param 对lastUpdateDate进行赋值
     */
    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }
    
    /**
      * 添加byte类型值<br/>
      *<功能详细描述>
      * @param key
      * @param bytes [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public void addByteParam(String key, byte[] bytes) {
        AssertUtils.notEmpty(key, "key is empty.");
        AssertUtils.notNull(bytes, "bytes is null");
        if (this.paramMap != null && this.paramMap.get(key) != null) {
            RuleItemParam param = this.paramMap.get(key);
            AssertUtils.isTrue(RuleItemParamTypeEnum.BYTE.equals(param.getType()),
                    "key:[{}] is not byte param.",
                    new Object[] { key });
        }
        if (this.byteParamMultiValueMap == null) {
            this.byteParamMultiValueMap = new LinkedMultiValueMap<String, RuleItemByteParam>();
        }
        this.byteParamMultiValueMap.remove(key);
        
        RuleItemByteParam byteParam = new RuleItemByteParam();
        byteParam.setParamKey(key);
        byteParam.setParamValue(bytes);
        byteParam.setRuleKey(this.key);
        byteParam.setParamOrder(0);
        
        this.byteParamMultiValueMap.add(key, byteParam);
    }
    
    /**
      * 添加byte类型列表值<br/>
      *<功能详细描述>
      * @param key
      * @param bytesList [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public void addByteParamList(String key, List<byte[]> bytesList) {
        AssertUtils.notEmpty(key, "key is empty.");
        AssertUtils.notEmpty(bytesList, "bytesList is null");
        if (this.paramMap != null && this.paramMap.get(key) != null) {
            RuleItemParam param = this.paramMap.get(key);
            AssertUtils.isTrue(RuleItemParamTypeEnum.BYTE.equals(param.getType()),
                    "key:[{}] is not byte param.",
                    new Object[] { key });
            AssertUtils.isTrue(param.isMultiple(),
                    "key:[{}] is not multiple param.",
                    new Object[] { key });
        }
        if (this.byteParamMultiValueMap == null) {
            this.byteParamMultiValueMap = new LinkedMultiValueMap<String, RuleItemByteParam>();
        }
        this.byteParamMultiValueMap.remove(key);
        
        int index = 0;
        for (byte[] byteTemp : bytesList) {
            RuleItemByteParam byteParam = new RuleItemByteParam();
            byteParam.setRuleKey(this.key);
            byteParam.setParamKey(key);
            byteParam.setParamValue(byteTemp);
            byteParam.setParamOrder(index++);
            
            this.byteParamMultiValueMap.add(key, byteParam);
        }
    }
    
    /**
     * 添加byte类型列表值<br/>
     *<功能详细描述>
     * @param key
     * @param bytesList [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    public void addByteParamList(List<RuleItemByteParam> ruleItemByteParamsList) {
        if (CollectionUtils.isEmpty(ruleItemByteParamsList)) {
            return;
        }
        this.byteParamMultiValueMap.clear();
        for (RuleItemByteParam ruleItemByteParamTemp : ruleItemByteParamsList) {
            this.byteParamMultiValueMap.add(ruleItemByteParamTemp.getParamKey(),
                    ruleItemByteParamTemp);
        }
    }
    
    /** 
      * 获取byte类型参数列表<br/>
      *<功能详细描述>
      * @param key
      * @return [参数说明]
      * 
      * @return List<RuleItemByteParam> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public List<RuleItemByteParam> getByteParamList(String key) {
        AssertUtils.notEmpty(key, "key is empty.");
        
        List<RuleItemByteParam> resList = this.byteParamMultiValueMap.get(key);
        return resList;
    }
    
    /**
      * 获取byte类型参数<br/>
      *<功能详细描述>
      * @param key
      * @return [参数说明]
      * 
      * @return RuleItemByteParam [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public RuleItemByteParam getByteParam(String key) {
        AssertUtils.notEmpty(key, "key is empty.");
        
        RuleItemByteParam res = this.byteParamMultiValueMap.getFirst(key);
        return res;
    }
    
    /**
     * 添加byte类型值<br/>
     *<功能详细描述>
     * @param key
     * @param bytes [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    public void addValueParam(String key, String value) {
        AssertUtils.notEmpty(key, "key is empty.");
        AssertUtils.notNull(value, "value is empty.");
        
        if (this.paramMap != null && this.paramMap.get(key) != null) {
            RuleItemParam param = this.paramMap.get(key);
            AssertUtils.isTrue(RuleItemParamTypeEnum.VALUE.equals(param.getType()),
                    "key:[{}] is not value param.",
                    new Object[] { key });
        }
        if (this.valueParamMultiValueMap == null) {
            this.valueParamMultiValueMap = new LinkedMultiValueMap<String, RuleItemValueParam>();
        }
        this.valueParamMultiValueMap.remove(key);
        
        RuleItemValueParam valueParam = new RuleItemValueParam();
        valueParam.setParamKey(key);
        valueParam.setParamValue(value);
        valueParam.setRuleKey(this.key);
        valueParam.setParamOrder(0);
        
        this.valueParamMultiValueMap.add(key, valueParam);
    }
    
    /**
     * 添加byte类型列表值<br/>
     *<功能详细描述>
     * @param key
     * @param bytesList [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    public void addValueParamList(List<RuleItemValueParam> ruleItemValueParamsList) {
        if (CollectionUtils.isEmpty(ruleItemValueParamsList)) {
            return;
        }
        this.byteParamMultiValueMap.clear();
        for (RuleItemValueParam ruleItemValueParamTemp : ruleItemValueParamsList) {
            this.valueParamMultiValueMap.add(ruleItemValueParamTemp.getParamKey(),
                    ruleItemValueParamTemp);
        }
    }
    
    /**
      * 添加byte类型列表值<br/>
      *<功能详细描述>
      * @param key
      * @param bytesList [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public void addValueParamList(String key, List<String> valueList) {
        AssertUtils.notEmpty(key, "key is empty.");
        AssertUtils.notEmpty(valueList, "valueList is null");
        if (this.paramMap != null && this.paramMap.get(key) != null) {
            RuleItemParam param = this.paramMap.get(key);
            AssertUtils.isTrue(RuleItemParamTypeEnum.VALUE.equals(param.getType()),
                    "key:[{}] is not value param.",
                    new Object[] { key });
            AssertUtils.isTrue(param.isMultiple(),
                    "key:[{}] is not multiple param.",
                    new Object[] { key });
        }
        if (this.valueParamMultiValueMap == null) {
            this.valueParamMultiValueMap = new LinkedMultiValueMap<String, RuleItemValueParam>();
        }
        this.valueParamMultiValueMap.remove(key);
        
        this.valueParamMultiValueMap.remove(key);
        int index = 0;
        for (String valueTemp : valueList) {
            RuleItemValueParam valueParam = new RuleItemValueParam();
            valueParam.setRuleKey(this.key);
            valueParam.setParamKey(key);
            valueParam.setParamValue(valueTemp);
            valueParam.setParamOrder(index++);
            
            this.valueParamMultiValueMap.add(key, valueParam);
        }
    }
    
    /**
      * 获取规则项目值参数
      *<功能详细描述>
      * @param key
      * @return [参数说明]
      * 
      * @return RuleItemValueParam [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public RuleItemValueParam getValueParam(String key) {
        AssertUtils.notEmpty(key, "key is empty.");
        
        RuleItemValueParam res = this.valueParamMultiValueMap.getFirst(key);
        return res;
    }
    
    /**
      * 获取规则项目值参数
      *<功能详细描述>
      * @param key
      * @return [参数说明]
      * 
      * @return List<RuleItemValueParam> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public List<RuleItemValueParam> getValueParamList(String key) {
        AssertUtils.notEmpty(key, "key is empty.");
        
        List<RuleItemValueParam> resList = this.valueParamMultiValueMap.get(key);
        return resList;
    }
    
    /**
     * 添加byte类型值<br/>
     *<功能详细描述>
     * @param key
     * @param bytes [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    public void addObjectParam(String key, Object object) {
        AssertUtils.notEmpty(key, "key is empty.");
        AssertUtils.notNull(object, "object is empty.");
        
        if (this.paramMap != null && this.paramMap.get(key) != null) {
            RuleItemParam param = this.paramMap.get(key);
            AssertUtils.isTrue(RuleItemParamTypeEnum.OBJECT.equals(param.getType()),
                    "key:[{}] is not object param.",
                    new Object[] { key });
        }
        if (this.objectParamMultiValueMap == null) {
            this.objectParamMultiValueMap = new LinkedMultiValueMap<String, Object>();
        }
        this.objectParamMultiValueMap.remove(key);
        
        this.objectParamMultiValueMap.add(key, object);
    }
    
    /**
      * 添加byte类型列表值<br/>
      *<功能详细描述>
      * @param key
      * @param bytesList [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public void addObjectParamList(String key, List<Object> valueList) {
        AssertUtils.notEmpty(key, "key is empty.");
        AssertUtils.notEmpty(valueList, "valueList is null");
        
        if (this.paramMap != null && this.paramMap.get(key) != null) {
            RuleItemParam param = this.paramMap.get(key);
            AssertUtils.isTrue(RuleItemParamTypeEnum.OBJECT.equals(param.getType()),
                    "key:[{}] is not object param.",
                    new Object[] { key });
            AssertUtils.isTrue(param.isMultiple(),
                    "key:[{}] is not multiple param.",
                    new Object[] { key });
        }
        if (this.objectParamMultiValueMap == null) {
            this.objectParamMultiValueMap = new LinkedMultiValueMap<String, Object>();
        }
        this.objectParamMultiValueMap.remove(key);
        
        this.objectParamMultiValueMap.remove(key);
        for (Object objectTemp : valueList) {
            this.objectParamMultiValueMap.add(key, objectTemp);
        }
    }
    
    /**
     * 获取规则项目值参数
     *<功能详细描述>
     * @param key
     * @return [参数说明]
     * 
     * @return RuleItemValueParam [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    public Object getObjectParam(String key) {
        AssertUtils.notEmpty(key, "key is empty.");
        
        Object res = this.objectParamMultiValueMap.getFirst(key);
        return res;
    }
    
    /**
      * 获取规则项目值参数
      *<功能详细描述>
      * @param key
      * @return [参数说明]
      * 
      * @return List<RuleItemValueParam> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public List<Object> getObjectParamList(String key) {
        AssertUtils.notEmpty(key, "key is empty.");
        
        List<Object> resList = this.objectParamMultiValueMap.get(key);
        return resList;
    }

    /**
     * @return 返回 byteParamMultiValueMap
     */
    public MultiValueMap<String, RuleItemByteParam> getByteParamMultiValueMap() {
        return byteParamMultiValueMap;
    }

    /**
     * @return 返回 valueParamMultiValueMap
     */
    public MultiValueMap<String, RuleItemValueParam> getValueParamMultiValueMap() {
        return valueParamMultiValueMap;
    }

    /**
     * @return 返回 objectParamMultiValueMap
     */
    public MultiValueMap<String, Object> getObjectParamMultiValueMap() {
        return objectParamMultiValueMap;
    }
}
