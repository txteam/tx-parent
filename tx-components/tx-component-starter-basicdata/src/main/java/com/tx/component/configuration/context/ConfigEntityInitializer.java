/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2019年12月9日
 * <修改描述:>
 */
package com.tx.component.configuration.context;

import java.beans.PropertyDescriptor;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanInstantiationException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.core.convert.TypeDescriptor;

import com.tx.component.configuration.annotation.ConfigCatalog;
import com.tx.component.configuration.exception.ConfigAccessException;
import com.tx.component.configuration.model.ConfigPropertyItem;
import com.tx.component.configuration.service.impl.LocalConfigPropertyManager;
import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.util.MessageUtils;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 配置装载支撑类<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2019年12月9日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class ConfigEntityInitializer {
    
    /** 本地配置属性管理器 */
    private LocalConfigPropertyManager localConfigPropertyManager;
    
    /** <默认构造函数> */
    public ConfigEntityInitializer() {
        super();
    }
    
    /**
     * 根据配置实体类初始化配置实体<br/>
     * <功能详细描述>
     * @param prefix
     * @param configEntityType
     * @return [参数说明]
     * 
     * @return INS [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    private <INS> INS initialize(String prefix, Class<INS> configEntityType) {
        INS config = null;
        try {
            config = BeanUtils.instantiateClass(configEntityType);
        } catch (BeanInstantiationException e) {
            throw new ConfigAccessException(MessageUtils.format(
                    "类无法进行初始化.class:{configEntity}", new Object[] {}), e);
        }
        ConfigPropertyItem parent = doSetupCatalog(configEntityType);
        if (StringUtils.isEmpty(prefix)) {
            prefix = parent.getCode() + ".";
        }
        if (!StringUtils.endsWith(prefix, ".")) {
            prefix = prefix + ".";
        }
        BeanWrapper bw = PropertyAccessorFactory.forBeanPropertyAccess(config);
        doSetupProperties(prefix, parent, bw);
        return config;
    }
    
    private void doSetupProperties(String prefix, ConfigPropertyItem parent,
            BeanWrapper bw) {
        for (PropertyDescriptor pd : bw.getPropertyDescriptors()) {
            if (pd.getReadMethod() == null || pd.getWriteMethod() == null) {
                //忽略get,set不存在的属性
                continue;
            }
            ConfigPropertyItem item = doSetupProperty(prefix, parent, bw, pd);
            //如果存在
            this.localConfigPropertyManager.save(item);
        }
    }
    
    /**
     * 构建单个属性项<br/>
     * <功能详细描述>
     * @param parent
     * @param bw
     * @param pd
     * @return [参数说明]
     * 
     * @return ConfigPropertyItem [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public ConfigPropertyItem doSetupProperty(String prefix,
            ConfigPropertyItem parent, BeanWrapper bw, PropertyDescriptor pd) {
        AssertUtils.notNull(parent, "parent is null.");
        AssertUtils.notNull(bw, "bw is null.");
        AssertUtils.notNull(pd, "pd is null.");
        
        String code = prefix + pd.getName();
        String name = code;
        
        TypeDescriptor td = bw.getPropertyTypeDescriptor(code);
        if (td.hasAnnotation(ApiModelProperty.class)) {
            name = td.getAnnotation(ApiModelProperty.class).value();
        }
        if (StringUtils.isEmpty(name)) {
            name = code;
        }
        
        ConfigPropertyItem item = new ConfigPropertyItem();
        item.setCode(code);
        item.setName(name);
        
        item.setParentId(parent == null ? null : parent.getId());
        item.setModifyAble(true);
        item.setLeaf(true);
        item.setValidateExpression("");
        item.setRemark("");
        item.setValue("");
        return item;
    }
    
    /**
     * 根据类型装载配置分类<br/>
     * <功能详细描述>
     * @param configEntityType [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    private ConfigPropertyItem doSetupCatalog(Class<?> configEntityType) {
        AssertUtils.notNull(configEntityType, "configEntityType is null.");
        
        String code = "";
        //解析配置分类名称
        String name = "";
        if (!configEntityType.isAnnotationPresent(ConfigCatalog.class)) {
            code = configEntityType.getSimpleName();
            if (configEntityType.isAnnotationPresent(ApiModel.class)) {
                name = configEntityType.getAnnotation(ApiModel.class).value();
            }
        } else {
            
            ConfigCatalog catalogA = configEntityType
                    .getAnnotation(ConfigCatalog.class);
            code = catalogA.code();
            name = catalogA.name();
        }
        if (StringUtils.isEmpty(name)) {
            name = code;
        }
        
        ConfigPropertyItem parent = null;
        if (!configEntityType.getSuperclass().equals(Object.class)
                && configEntityType.getSuperclass()
                        .isAnnotationPresent(ConfigCatalog.class)) {
            parent = doSetupParentCatalog(configEntityType.getSuperclass());
        }
        
        //保存并返回便于下一级配置项目录进行存储
        ConfigPropertyItem item = this.localConfigPropertyManager
                .saveCatalog(parent, code, name);
        return item;
    }
    
    /**
     * 装载父级配置分类<br/>
     * <功能详细描述>
     * @param configEntityType
     * @return [参数说明]
     * 
     * @return ConfigPropertyItem [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    private ConfigPropertyItem doSetupParentCatalog(Class<?> parentType) {
        AssertUtils.notNull(parentType, "parentType is null.");
        AssertUtils.isTrue(!parentType.equals(Object.class),
                "parentType is Object.");
        AssertUtils.isTrue(parentType.isAnnotationPresent(ConfigCatalog.class),
                "parentType annotation is not exists.");
        
        ConfigCatalog catalogA = parentType.getAnnotation(ConfigCatalog.class);
        String code = catalogA.code();
        String name = catalogA.name();
        ConfigPropertyItem parent = null;
        if (!parentType.getSuperclass().equals(Object.class) && parentType
                .getSuperclass().isAnnotationPresent(ConfigCatalog.class)) {
            parent = doSetupParentCatalog(parentType.getSuperclass());
        }
        if (StringUtils.isEmpty(name)) {
            name = code;
        }
        
        //保存并返回便于下一级配置项目录进行存储
        ConfigPropertyItem item = this.localConfigPropertyManager
                .saveCatalog(parent, code, name);
        return item;
    }
    
}
