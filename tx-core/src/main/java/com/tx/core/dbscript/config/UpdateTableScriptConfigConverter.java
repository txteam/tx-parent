/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-12-18
 * <修改描述:>
 */
package com.tx.core.dbscript.config;

import org.apache.cxf.common.util.StringUtils;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

/**
 * 更新表脚本配置xml转换器
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2013-12-18]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class UpdateTableScriptConfigConverter implements Converter {
    
    /**
     * @param type
     * @return
     */
    @Override
    public boolean canConvert(@SuppressWarnings("rawtypes") Class type) {
        return type.equals(UpdateTableScriptConfig.class);
    }
    
    /**
     * @param source
     * @param writer
     * @param context
     */
    @Override
    public void marshal(Object source, HierarchicalStreamWriter writer,
            MarshallingContext context) {
        UpdateTableScriptConfig updateTableScriptConfig = (UpdateTableScriptConfig) source;
        if (updateTableScriptConfig == null) {
            return;
        }
        
        writer.addAttribute("sourceVersion",
                updateTableScriptConfig.getSourceVersion());
        writer.addAttribute("targetVersion",
                updateTableScriptConfig.getTargetVersion());
        
        //写入值
        if (!StringUtils.isEmpty(updateTableScriptConfig.getScript())) {
            writer.setValue(updateTableScriptConfig.getScript());
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
        UpdateTableScriptConfig updateTableScriptConfig = new UpdateTableScriptConfig();
        
        String sourceVersion = reader.getAttribute("sourceVersion");
        String targetVersion = reader.getAttribute("targetVersion");
        String value = reader.getValue();
        
        updateTableScriptConfig.setScript(value);
        updateTableScriptConfig.setSourceVersion(sourceVersion);
        updateTableScriptConfig.setTargetVersion(targetVersion);
        
        return updateTableScriptConfig;
    }
    
}
