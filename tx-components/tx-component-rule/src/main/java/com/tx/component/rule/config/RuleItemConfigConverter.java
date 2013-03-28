/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-1-19
 * <修改描述:>
 */
package com.tx.component.rule.config;

import java.util.Map.Entry;

import org.apache.commons.collections.MapUtils;
import org.apache.cxf.common.util.StringUtils;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.tx.component.rule.model.RuleTypeEnum;

/**
 * 用以支持ruleElement又有属性又有值的情况
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2013-1-19]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class RuleItemConfigConverter implements Converter {
    
    /**
     * @param type
     * @return
     */
    @Override
    public boolean canConvert(@SuppressWarnings("rawtypes") Class type) {
        return type.equals(RuleItemConfig.class);
    }
    
    /**
     * @param source
     * @param writer
     * @param context
     */
    @Override
    public void marshal(Object source, HierarchicalStreamWriter writer,
            MarshallingContext context) {
        RuleItemConfig ruleItem = (RuleItemConfig) source;
        
        if (ruleItem == null){
            return ;
        }
        
        //attribute
        writer.addAttribute("rule", ruleItem.getRule());//rule不能为空
        writer.addAttribute("ruleType", ruleItem.getRuleType().toString());
        writer.addAttribute("serviceType", ruleItem.getServiceType());
        
        if(MapUtils.isEmpty(ruleItem.getProperties())){
            for(Entry<String, String> entryTemp : ruleItem.getProperties().entrySet()){
                writer.addAttribute(entryTemp.getKey(), entryTemp.getValue());
            }
        }
        //
        if(!StringUtils.isEmpty(ruleItem.getRuleExpression())){
            writer.setValue(ruleItem.getRuleExpression());
        }else{
            writer.setValue("");
        }
    }
    
    /**
     * @param reader
     * @param context
     * @return
     */
    @Override
    public Object unmarshal(HierarchicalStreamReader reader,
            UnmarshallingContext context) {
        RuleItemConfig ruleItem = new RuleItemConfig();
        
        String rule = reader.getAttribute("rule");
        String serviceType = reader.getAttribute("serviceType");
        String ruleType = reader.getAttribute("ruleType");
        String value = reader.getValue();
        
        ruleItem.setRule(rule);
        ruleItem.setServiceType(serviceType);
        ruleItem.setRuleType(RuleTypeEnum.valueOf(ruleType));
        ruleItem.setRuleExpression(value);
        
        return ruleItem;
    }
    
}
