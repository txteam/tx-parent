/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2014年4月17日
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
 * 文件参数转换<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2014年4月17日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class FileParamConverter implements Converter {
    
    /** 默认的资源加载器 */
    private static final ResourceLoader resourceLoader = new DefaultResourceLoader();
    
    /**
     * @param arg0
     * @return
     */
    @Override
    public boolean canConvert(@SuppressWarnings("rawtypes") Class type) {
        return type.equals(FileParam.class);
    }
    
    /**
     * @param arg0
     * @param arg1
     * @param arg2
     */
    @Override
    public void marshal(Object source, HierarchicalStreamWriter writer,
            MarshallingContext context) {
        FileParam fileParam = (FileParam) source;
        if (fileParam == null) {
            return;
        }
        //attribute
        writer.addAttribute("key", fileParam.getKey());//rule不能为空
        
        //value
        if (fileParam.getValue() != null) {
            writer.setValue(fileParam.getValue().getPath());
        } else {
            writer.setValue("");
        }
    }
    
    /**
     * @param arg0
     * @param arg1
     * @return
     */
    @Override
    public Object unmarshal(HierarchicalStreamReader reader,
            UnmarshallingContext context) {
        FileParam fileParam = new FileParam();
        
        String key = reader.getAttribute("key");
        String value = reader.getValue();
        AssertUtils.notEmpty(key, "key is empty.");
        AssertUtils.notEmpty(value, "value:{} value is empty", key);
        Resource valueResource = resourceLoader.getResource(value);
        AssertUtils.isExist(valueResource, "key:{} resource is not exist.", key);
        
        fileParam.setKey(key);
        try {
            fileParam.setValue(valueResource.getFile());
        } catch (IOException e) {
            throw ExceptionWrapperUtils.wrapperIOException(e,
                    "marshal ByteParam exception.");
        }
        return fileParam;
    }
}
