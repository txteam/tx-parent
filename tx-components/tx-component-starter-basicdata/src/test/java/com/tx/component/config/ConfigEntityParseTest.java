/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2019年12月5日
 * <修改描述:>
 */
package com.tx.component.config;

import org.springframework.core.annotation.AnnotationUtils;

import com.tx.component.configuration.annotation.ConfigEntity;

/**
 * <功能简述>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2019年12月5日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class ConfigEntityParseTest {
    
    public static void main(String[] args) {
        ConfigEntity ceAnno = AnnotationUtils
                .getAnnotation(QQLoginPluginConfig.class, ConfigEntity.class);
        System.out.println(ceAnno.code());
        
    }
}
