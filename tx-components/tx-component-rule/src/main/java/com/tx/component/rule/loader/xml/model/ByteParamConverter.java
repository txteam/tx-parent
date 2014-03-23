/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-1-19
 * <修改描述:>
 */
package com.tx.component.rule.loader.xml.model;

import java.io.IOException;

import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.exceptions.util.ExceptionWrapperUtils;

/**
 * 用以支持ruleElement又有属性又有值的情况
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2013-1-19]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class ByteParamConverter implements Converter {
    
    /** 默认的资源加载器 */
    private static final ResourceLoader resourceLoader = new DefaultResourceLoader();
    
    /**
     * @param type
     * @return
     */
    @Override
    public boolean canConvert(@SuppressWarnings("rawtypes") Class type) {
        return type.equals(ByteParam.class);
    }
    
    /**
     * @param source
     * @param writer
     * @param context
     */
    @Override
    public void marshal(Object source, HierarchicalStreamWriter writer,
            MarshallingContext context) {
        ByteParam byteParam = (ByteParam) source;
        if (byteParam == null) {
            return;
        }
        //attribute
        writer.addAttribute("key", byteParam.getKey());//rule不能为空
        
        //value
        if (byteParam.getValue() != null) {
            try {
                writer.setValue(byteParam.getValue().getURL().getPath());
            } catch (IOException e) {
                throw ExceptionWrapperUtils.wrapperIOException(e,
                        "marshal ByteParam exception.");
            }
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
        ByteParam byteParam = new ByteParam();
        
        String key = reader.getAttribute("key");
        String value = reader.getValue();
        AssertUtils.notEmpty(key, "key is empty.");
        AssertUtils.notEmpty(value, "key:{} value is empty", key);
        Resource valueResource = resourceLoader.getResource(value);
        AssertUtils.isExist(valueResource, "key:{} resource is not exist.", key);
        
        byteParam.setKey(key);
        byteParam.setValue(valueResource);
        return byteParam;
    }
    
}
