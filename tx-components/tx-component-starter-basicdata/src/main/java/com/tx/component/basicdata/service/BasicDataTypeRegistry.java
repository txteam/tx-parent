/*
 * 描          述:  <描述>
 * 修  改   人:  
 * 修改时间:  
 * <修改描述:>
 */
package com.tx.component.basicdata.service;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tx.component.basicdata.model.BasicData;
import com.tx.component.basicdata.model.BasicDataEntityInfo;

/**
 * BasicDataType的业务层
 * <功能详细描述>
 * 
 * @author  
 * @version  [版本号, ]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public final class BasicDataTypeRegistry {
    
    @SuppressWarnings("unused")
    private Logger logger = LoggerFactory
            .getLogger(BasicDataTypeRegistry.class);
    
    /** 类型到类型的映射 */
    private Map<String, BasicDataEntityInfo> map = new HashMap<>();
    
    /** 编码到类型的映射 */
    private Map<String, Class<?>> code2typeMap = new HashMap<>();
    
    /** <默认构造函数> */
    private BasicDataTypeRegistry() {
        super();
    }
    
    public void register(Class<? extends BasicData> type) {
        //register((Type) javaType, typeHandler);
    }
}