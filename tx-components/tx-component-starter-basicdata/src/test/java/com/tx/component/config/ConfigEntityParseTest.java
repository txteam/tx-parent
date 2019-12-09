/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2019年12月5日
 * <修改描述:>
 */
package com.tx.component.config;

import java.beans.PropertyDescriptor;
import java.beans.PropertyEditor;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.convert.support.DefaultConversionService;

import com.tx.component.configuration.annotation.ConfigCatalog;

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
    
    private static DefaultConversionService service = new DefaultConversionService();
    
    public static void main(String[] args) {
        ConfigCatalog ceAnno = AnnotationUtils
                .getAnnotation(QQLoginPluginConfig.class, ConfigCatalog.class);
        System.out.println("----------------------");
        System.out.println(ceAnno.code());
        System.out.println(ceAnno.name());
        
        ConfigCatalog parentAnno = AnnotationUtils.getAnnotation(
                QQLoginPluginConfig.class.getSuperclass(), ConfigCatalog.class);
        System.out.println("----------------------");
        System.out.println(parentAnno.code());
        System.out.println(parentAnno.name());
        
        ConfigCatalog parentAnno1 = AnnotationUtils.getAnnotation(
                QQLoginPluginConfig.class.getSuperclass().getSuperclass(),
                ConfigCatalog.class);
        System.out.println("----------------------");
        System.out.println(parentAnno1.code());
        System.out.println(parentAnno1.name());
        
        System.out.println(QQLoginPluginConfig.class.getSuperclass()
                .getSuperclass()
                .getSuperclass());
        
        QQLoginPluginConfig c = new QQLoginPluginConfig();
        BeanWrapper bw = new BeanWrapperImpl(c);//PropertyAccessorFactory.forBeanPropertyAccess(c);
        for(PropertyDescriptor pd : bw.getPropertyDescriptors()){
            if(pd.getReadMethod() == null || pd.getWriteMethod() == null){
                continue;
            }
            System.out.println(pd.getName());
            if(service.canConvert(pd.getPropertyType(), String.class)){
                System.out.println("pt:" + pd.getPropertyType() + " can be convert.");
            }else{
                System.out.println("pt:" + pd.getPropertyType() + " can not be convert.");
            }
        }
        
        bw.setPropertyValue("enable", "true");
        System.out.println("---------------------------");
        System.out.println(bw.getPropertyValue("enable"));
    }
}
