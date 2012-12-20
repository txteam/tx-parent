/*
 * 描          述:  <描述>
 * 修  改   人:  PengQingyang
 * 修改时间:  2012-12-16
 * <修改描述:>
 */
package com.tx.core.springmvc.beans;

import java.beans.PropertyEditor;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.PropertyEditorRegistrar;
import org.springframework.beans.PropertyEditorRegistry;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * 属性编辑器工厂
 * <功能详细描述>
 * 
 * @author  PengQingyang
 * @version  [版本号, 2012-12-16]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public abstract class PropertyEditorRegistrarsFactory implements
        FactoryBean<PropertyEditorRegistrar[]>, InitializingBean {
    
    private PropertyEditorRegistrar[] propertyEditorRegistrars = new PropertyEditorRegistrar[0];
    
    /**
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        Map<Class<?>, PropertyEditor> type2EditorMap = getType2PropertyEditorMapping();
        if (type2EditorMap == null) {
            return;
        }
        
        List<PropertyEditorRegistrar> propertyEditorRegistrarList = new ArrayList<PropertyEditorRegistrar>();
        //如果存在客户自定义的属性编辑器，则根据该属性编辑器生成相应的PropertyEditorRegistrar
        for (Entry<Class<?>, PropertyEditor> entryTemp : type2EditorMap.entrySet()) {
            propertyEditorRegistrarList.add(new CustomPropertyEditorRegistrar(
                    entryTemp.getKey(), entryTemp.getValue()));
        }
        
        this.propertyEditorRegistrars = (PropertyEditorRegistrar[]) propertyEditorRegistrarList.toArray();
    }
    
    public abstract Map<Class<?>, PropertyEditor> getType2PropertyEditorMapping();
    
    /**
     * @return
     * @throws Exception
     */
    @Override
    public PropertyEditorRegistrar[] getObject() throws Exception {
        return propertyEditorRegistrars;
    }
    
    /**
     * @return
     */
    @Override
    public Class<?> getObjectType() {
        return PropertyEditorRegistrar[].class;
    }
    
    /**
     * @return
     */
    @Override
    public boolean isSingleton() {
        return true;
    }
    
    /**
      * 客户自定义属性编辑器
      * <功能详细描述>
      * 
      * @author  PengQingyang
      * @version  [版本号, 2012-12-16]
      * @see  [相关类/方法]
      * @since  [产品/模块版本]
     */
    private static class CustomPropertyEditorRegistrar implements
            PropertyEditorRegistrar {
        
        private final Class<?> requiredType;
        
        private final PropertyEditor sharedEditor;
        
        public CustomPropertyEditorRegistrar(Class<?> requiredType,
                PropertyEditor sharedEditor) {
            this.requiredType = requiredType;
            this.sharedEditor = sharedEditor;
        }
        
        public void registerCustomEditors(PropertyEditorRegistry registry) {
            registry.registerCustomEditor(this.requiredType, this.sharedEditor);
        }
    }
}
