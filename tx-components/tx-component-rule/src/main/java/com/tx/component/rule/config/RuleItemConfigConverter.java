/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-1-19
 * <修改描述:>
 */
package com.tx.component.rule.config;

import org.apache.cxf.common.util.StringUtils;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

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
        return type.equals(RuleElementConfig.class);
    }
    
    /**
     * @param source
     * @param writer
     * @param context
     */
    @Override
    public void marshal(Object source, HierarchicalStreamWriter writer,
            MarshallingContext context) {
        RuleElementConfig ruleElement = (RuleElementConfig) source;
        
        if (ruleElement == null){
            return ;
        }
        
        if(!StringUtils.isEmpty(ruleElement.getRuleType())){
            writer.addAttribute("ruleType", ruleElement.getRuleType());
        }
        if(!StringUtils.isEmpty(ruleElement.getRuleExpression())){
            writer.setValue(ruleElement.getRuleExpression());
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
        RuleElementConfig ruleElement = new RuleElementConfig();
        String type = reader.getAttribute("ruleType");
        ruleElement.setRuleType(type);
        String value = reader.getValue();
        ruleElement.setRuleExpression(value);
        return ruleElement;
    }
    
}
