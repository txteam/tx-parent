/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-1-19
 * <修改描述:>
 */
package com.tx.component.rule.loader.xml.model;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.tx.core.exceptions.util.AssertUtils;

/**
 * 用以支持ruleElement又有属性又有值的情况
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2013-1-19]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class ValueParamConverter implements Converter {
    
    /**
     * @param type
     * @return
     */
    @Override
    public boolean canConvert(@SuppressWarnings("rawtypes") Class type) {
        return type.equals(ValueParam.class);
    }
    
    /**
     * @param source
     * @param writer
     * @param context
     */
    @Override
    public void marshal(Object source, HierarchicalStreamWriter writer,
            MarshallingContext context) {
        ValueParam valueParam = (ValueParam) source;
        if (valueParam == null) {
            return;
        }
        //attribute
        writer.addAttribute("key", valueParam.getKey());//rule不能为空
        
        //value
        if (valueParam.getValue() != null) {
            writer.setValue(valueParam.getValue());
        } else {
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
        ValueParam valueParam = new ValueParam();
        
        String key = reader.getAttribute("key");
        String value = reader.getValue();
        AssertUtils.notEmpty(key, "key is empty.");
        AssertUtils.notEmpty(value, "key:{} value is empty", key);

        valueParam.setKey(key);
        valueParam.setValue(value);
        return valueParam;
    }
    
}
