/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2019年12月9日
 * <修改描述:>
 */
package com.tx.component.configuration.context;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanInstantiationException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.support.ConfigurableConversionService;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.tx.component.configuration.annotation.ConfigCatalog;
import com.tx.component.configuration.exception.ConfigAccessException;
import com.tx.component.configuration.model.ConfigProperty;
import com.tx.component.configuration.model.ConfigPropertyItem;
import com.tx.component.configuration.service.impl.LocalConfigPropertyManager;
import com.tx.component.configuration.util.ConfigContextUtils;
import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.querier.model.Filter;
import com.tx.core.querier.model.QuerierBuilder;
import com.tx.core.util.MessageUtils;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModelProperty.AccessMode;

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
    
    /** 转换业务层 */
    private ConfigurableConversionService conversionService;
    
    /** 事务控制 */
    private TransactionTemplate transactionTemplate;
    
    /** 本地配置属性管理器 */
    private LocalConfigPropertyManager localConfigPropertyManager;
    
    /** <默认构造函数> */
    public ConfigEntityInitializer() {
        super();
    }
    
    /** <默认构造函数> */
    public ConfigEntityInitializer(
            ConfigurableConversionService conversionService,
            TransactionTemplate transactionTemplate,
            LocalConfigPropertyManager localConfigPropertyManager) {
        super();
        this.conversionService = conversionService;
        this.transactionTemplate = transactionTemplate;
        this.localConfigPropertyManager = localConfigPropertyManager;
    }
    
    /**
     * 将配置类解析为具体的配置项<br/>
     * <功能详细描述>
     * @param prefix
     * @param configEntityType
     * @return [参数说明]
     * 
     * @return List<ConfigProperty> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public List<ConfigProperty> parse(String prefix,
            Class<?> configEntityType) {
        AssertUtils.notNull(this.localConfigPropertyManager,
                "localConfigPropertyManager is null.");
        AssertUtils.notNull(configEntityType, "configEntityType is null.");
        
        List<ConfigProperty> resList = new ArrayList<ConfigProperty>();
        
        BeanWrapper bw = new BeanWrapperImpl(configEntityType);
        for (PropertyDescriptor pd : bw.getPropertyDescriptors()) {
            if (pd.getReadMethod() == null || pd.getWriteMethod() == null) {
                //忽略get,set不存在的属性
                continue;
            }
            String code = doBuildProperty(prefix, null, bw, pd).getCode();
            resList.add(ConfigContext.getContext().find(code));
        }
        return resList;
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
    public void uninstall(String prefix, Class<?> configEntityType) {
        AssertUtils.notNull(this.transactionTemplate,
                "transactionTemplate is null.");
        AssertUtils.notNull(this.localConfigPropertyManager,
                "localConfigPropertyManager is null.");
        
        final String newprefix = ConfigContextUtils.preprocessPrefix(prefix,
                configEntityType);
        
        if (configEntityType != null) {
            //如果存在类型则直接进行精确删除
            BeanWrapper bw = new BeanWrapperImpl(configEntityType);
            this.transactionTemplate
                    .execute(new TransactionCallbackWithoutResult() {
                        @Override
                        protected void doInTransactionWithoutResult(
                                TransactionStatus status) {
                            doUninstallProperties(newprefix, bw);
                        }
                    });
        } else {
            //兼容配置类型为空的形式，考虑到插件重构，原来的类型根本就不存在的情形，这个时候直接根据prefix匹配上进行删除
            //当然也可能存在配置值误删的可能性，但通过命名约定是能够规避这种情形的
            this.transactionTemplate
                    .execute(new TransactionCallbackWithoutResult() {
                        @Override
                        protected void doInTransactionWithoutResult(
                                TransactionStatus status) {
                            doUninstallPropertiesByPrefix(newprefix);
                        }
                    });
        }
    }
    
    /**
     * 卸载属性<br/>
     * <功能详细描述>
     * @param prefix
     * @param parent
     * @param bw [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    private void doUninstallPropertiesByPrefix(String prefix) {
        List<ConfigProperty> cpList = this.localConfigPropertyManager
                .queryList(QuerierBuilder.newInstance()
                        .addFilter(Filter.like("code", prefix + "%"))
                        .querier());
        for (ConfigProperty cp : cpList) {
            this.localConfigPropertyManager.uninstall(cp);
        }
    }
    
    /**
     * 卸载属性<br/>
     * <功能详细描述>
     * @param prefix
     * @param parent
     * @param bw [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    private void doUninstallProperties(String prefix, BeanWrapper bw) {
        for (PropertyDescriptor pd : bw.getPropertyDescriptors()) {
            if (pd.getReadMethod() == null || pd.getWriteMethod() == null) {
                //忽略get,set不存在的属性
                continue;
            }
            ConfigPropertyItem item = doBuildProperty(prefix, null, bw, pd);
            //如果存在
            this.localConfigPropertyManager.uninstall(item);
        }
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
    public <INS> INS install(String prefix, Class<INS> configEntityType) {
        AssertUtils.notNull(this.transactionTemplate,
                "transactionTemplate is null.");
        AssertUtils.notNull(this.localConfigPropertyManager,
                "localConfigPropertyManager is null.");
        AssertUtils.notNull(configEntityType, "configEntityType is null.");
        
        final String newprefix = ConfigContextUtils.preprocessPrefix(prefix,
                configEntityType);
        INS config = null;
        try {
            config = BeanUtils.instantiateClass(configEntityType);
        } catch (BeanInstantiationException e) {
            throw new ConfigAccessException(MessageUtils.format(
                    "类无法进行初始化.class:{configEntity}", new Object[] {}), e);
        }
        BeanWrapper bw = PropertyAccessorFactory.forBeanPropertyAccess(config);
        
        this.transactionTemplate
                .execute(new TransactionCallbackWithoutResult() {
                    @Override
                    protected void doInTransactionWithoutResult(
                            TransactionStatus status) {
                        ConfigPropertyItem parent = doSetupCatalog(
                                configEntityType);
                        doInstallProperties(newprefix, parent, bw);
                    }
                });
        return config;
    }
    
    /**
     * 安装属性<br/>
     * <功能详细描述>
     * @param prefix
     * @param parent
     * @param bw [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    private void doInstallProperties(String prefix, ConfigPropertyItem parent,
            BeanWrapper bw) {
        for (PropertyDescriptor pd : bw.getPropertyDescriptors()) {
            if (pd.getReadMethod() == null || pd.getWriteMethod() == null) {
                //忽略get,set不存在的属性
                continue;
            }
            ConfigPropertyItem item = doBuildProperty(prefix, parent, bw, pd);
            //如果存在
            if(item != null){
                this.localConfigPropertyManager.install(item);
            }
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
    private ConfigPropertyItem doBuildProperty(String prefix,
            ConfigPropertyItem parent, BeanWrapper bw, PropertyDescriptor pd) {
        //AssertUtils.notNull(parent, "parent is null.");卸载期间为空
        AssertUtils.notNull(bw, "bw is null.");
        AssertUtils.notNull(pd, "pd is null.");
        
        String code = prefix + pd.getName();
        String name = pd.getName();
        boolean modifyAble = true;//默认为可编辑
        
        String defaultValue = this.conversionService
                .convert(bw.getPropertyValue(pd.getName()), String.class);
        TypeDescriptor td = bw.getPropertyTypeDescriptor(pd.getName());
        if (td.hasAnnotation(ApiModelProperty.class)) {
            ApiModelProperty proinfo = td.getAnnotation(ApiModelProperty.class);
            name = proinfo.value();
            //复用属性注解，使用其中accessMode表示属性不可编辑
            modifyAble = proinfo.accessMode() == AccessMode.READ_ONLY ? false
                    : true;
        }
        
        if (StringUtils.isEmpty(name)) {
            name = pd.getName();
        }
        
        ConfigPropertyItem item = new ConfigPropertyItem();
        item.setCode(code);
        item.setName(name);
        item.setModifyAble(modifyAble);
        item.setParentId(parent == null ? null : parent.getId());
        item.setLeaf(true);
        item.setValidateExpression("");
        item.setRemark("");
        item.setValue(defaultValue);
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
                .setupCatalog(parent, code, name);
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
                .setupCatalog(parent, code, name);
        return item;
    }
    
    /**
     * @param 对transactionTemplate进行赋值
     */
    public void setTransactionTemplate(
            TransactionTemplate transactionTemplate) {
        this.transactionTemplate = transactionTemplate;
    }
    
    /**
     * @param 对localConfigPropertyManager进行赋值
     */
    public void setLocalConfigPropertyManager(
            LocalConfigPropertyManager localConfigPropertyManager) {
        this.localConfigPropertyManager = localConfigPropertyManager;
    }
}
