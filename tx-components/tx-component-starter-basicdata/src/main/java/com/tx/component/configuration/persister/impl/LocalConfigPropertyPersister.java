/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2019年3月5日
 * <修改描述:>
 */
package com.tx.component.configuration.persister.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.ResourceLoader;

import com.thoughtworks.xstream.XStream;
import com.tx.component.configuration.config.ConfigPropertyParser;
import com.tx.component.configuration.model.ConfigProperty;
import com.tx.component.configuration.model.ConfigPropertyItem;
import com.tx.component.configuration.persister.ConfigPropertyPersister;
import com.tx.component.configuration.service.ConfigPropertyItemService;
import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.util.XstreamUtils;

/**
 * 本地配置属性查询器<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2019年3月5日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class LocalConfigPropertyPersister implements ConfigPropertyPersister,
        InitializingBean, ResourceLoaderAware {
    
    //日志记录句柄
    private Logger logger = LoggerFactory
            .getLogger(LocalConfigPropertyPersister.class);
    
    //配置解析句柄
    private XStream configXstream = XstreamUtils
            .getXstream(ConfigPropertyParser.class);
    
    /** 所属模块 */
    private String module;
    
    /** 资源加载器 */
    private ResourceLoader resourceLoader;
    
    /** 配置文件所在路径 */
    private String configLocation;
    
    /** 配置项业务层 */
    private ConfigPropertyItemService configPropertyItemService;
    
    /** code的有效值域 */
    private Set<String> codes = new HashSet<>();
    
    /** <默认构造函数> */
    public LocalConfigPropertyPersister(String module, String configLocation,
            ConfigPropertyItemService configPropertyItemService) {
        super();
        this.module = module;
        this.configLocation = configLocation;
    }
    
    /**
     * @param resourceLoader
     */
    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }
    
    /**
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        if (StringUtils.isEmpty(this.configLocation)
                || this.configPropertyItemService == null) {
            logger.info(
                    "configLocation is empty or configPropertyItemService is null.");
            return;
        }
        
        AssertUtils.notEmpty(this.module, "module is empty.");
        
        org.springframework.core.io.Resource configResource = this.resourceLoader
                .getResource(this.configLocation);
        if (!configResource.exists()) {
            logger.info("configLocation resource is not exist.");
            return;
        }
        
        //解析配置文件
        ConfigPropertyParser parser = (ConfigPropertyParser) configXstream
                .fromXML(configResource.getInputStream());
        //初始化配置属性
        initConfigProperties(null, parser.getConfigs());
    }
    
    /**
     * 初始化配置属性<br/>
     * <功能详细描述>
     * @param parserList [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    private void initConfigProperties(ConfigPropertyItem parent,
            List<ConfigPropertyParser> parserList) {
        if (CollectionUtils.isEmpty(parserList)) {
            return;
        }
        for (ConfigPropertyParser configParserTemp : parserList) {
            String codeTemp = configParserTemp.getCode();
            
            codes.add(codeTemp);
            ConfigPropertyItem configPropertyItem = this.configPropertyItemService
                    .findByCode(this.module, codeTemp);
            
            if (configPropertyItem == null) {
                configPropertyItem = addConfigPropertyByParser(parent,
                        configParserTemp);
                logger.debug(
                        "...新增配置项: code:{} | name:{} | value:{} | remark:{} | parentId:{} | validateExpression:{}",
                        new Object[] { configPropertyItem.getCode(),
                                configPropertyItem.getName(),
                                configPropertyItem.getValue(),
                                configPropertyItem.getRemark(),
                                configPropertyItem.getParentId(),
                                configPropertyItem.getValidateExpression() });
            } else if (!matches(parent, configParserTemp, configPropertyItem)) {
                updateConfigPropertyByParser(configPropertyItem,
                        parent,
                        configParserTemp);
                logger.debug(
                        "...更新配置项: code:{} | name:{} | value:{} | remark:{} | parentId:{} | validateExpression:{}",
                        new Object[] { configPropertyItem.getCode(),
                                configPropertyItem.getName(),
                                configPropertyItem.getValue(),
                                configPropertyItem.getRemark(),
                                configPropertyItem.getParentId(),
                                configPropertyItem.getValidateExpression() });
            } else {
                logger.debug(
                        "...加载配置项: code:{} | name:{} | value:{} | remark:{} | parentId:{} | validateExpression:{}",
                        new Object[] { configPropertyItem.getCode(),
                                configPropertyItem.getName(),
                                configPropertyItem.getValue(),
                                configPropertyItem.getRemark(),
                                configPropertyItem.getParentId(),
                                configPropertyItem.getValidateExpression() });
            }
            //嵌套初始化配置属性
            initConfigProperties(configPropertyItem,
                    configParserTemp.getConfigs());
        }
    }
    
    /** 
     * 根据配置 新增配置属性<br/>
     * <功能详细描述>
     * @param parent
     * @param configParserTemp
     * @return [参数说明]
     * 
     * @return ConfigPropertyItem [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    private ConfigPropertyItem addConfigPropertyByParser(
            ConfigPropertyItem parent, ConfigPropertyParser configParserTemp) {
        ConfigPropertyItem configPropertyItem = new ConfigPropertyItem();
        configPropertyItem.setCode(configParserTemp.getCode());
        configPropertyItem.setModule(this.module);
        
        configPropertyItem.setName(configParserTemp.getName());
        configPropertyItem.setValue(configParserTemp.getValue());
        configPropertyItem.setRemark(configParserTemp.getRemark());
        configPropertyItem.setValidateExpression(
                configParserTemp.getValidateExpression());
        
        configPropertyItem.setModifyAble(configParserTemp.isModifyAble());
        configPropertyItem.setParentId(parent == null ? null : parent.getId());
        configPropertyItem
                .setLeaf(CollectionUtils.isEmpty(configParserTemp.getConfigs())
                        ? true : false);
        
        this.configPropertyItemService.insert(configPropertyItem);
        return configPropertyItem;
    }
    
    /** 
     * 根据配置 新增配置属性<br/>
     * <功能详细描述>
     * @param parent
     * @param configParserTemp
     * @return [参数说明]
     * 
     * @return ConfigPropertyItem [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    private void updateConfigPropertyByParser(
            ConfigPropertyItem configPropertyItem, ConfigPropertyItem parent,
            ConfigPropertyParser configParserTemp) {
        //configPropertyItem.setCode(configParserTemp.getCode());
        //configPropertyItem.setModule(this.module);
        
        configPropertyItem.setName(configParserTemp.getName());
        configPropertyItem.setValue(configParserTemp.getValue());
        configPropertyItem.setRemark(configParserTemp.getRemark());
        configPropertyItem.setValidateExpression(
                configParserTemp.getValidateExpression());
        configPropertyItem.setModifyAble(configParserTemp.isModifyAble());
        configPropertyItem.setParentId(parent == null ? null : parent.getId());
        configPropertyItem
                .setLeaf(CollectionUtils.isEmpty(configParserTemp.getConfigs())
                        ? true : false);
        if (!configParserTemp.isModifyAble()) {
            configPropertyItem.setValue(configParserTemp.getValue());
        }
        
        this.configPropertyItemService.updateById(configPropertyItem);
    }
    
    /**
     * 判断配置属性是否需要进行初始化<br/>
     * <功能详细描述>
     * @param parent
     * @param configParser
     * @param configPropertyItem
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    private boolean matches(ConfigPropertyItem parent,
            ConfigPropertyParser configParser,
            ConfigPropertyItem configPropertyItem) {
        if (!StringUtils.equalsAnyIgnoreCase(configParser.getCode(),
                configPropertyItem.getCode())) {
            return false;
        }
        if (!StringUtils.equalsAnyIgnoreCase(configParser.getName(),
                configPropertyItem.getName())) {
            return false;
        }
        if (!StringUtils.equalsAnyIgnoreCase(configParser.getRemark(),
                configPropertyItem.getRemark())) {
            return false;
        }
        if (!StringUtils.equalsAnyIgnoreCase(
                configParser.getValidateExpression(),
                configPropertyItem.getValidateExpression())) {
            return false;
        }
        if (Objects.hashCode(configParser.getAttributes()) == Objects
                .hashCode(configPropertyItem.getAttributes())) {
            return false;
        }
        if (!StringUtils.equalsAnyIgnoreCase(
                parent == null ? null : parent.getId(),
                configPropertyItem.getParentId())) {
            return false;
        }
        //如果配置可编辑则，不需要对比value值，如果不可编辑,value值不一致，则使用配置的值
        if (!configParser.isModifyAble()) {
            if (!StringUtils.equalsAnyIgnoreCase(configParser.getValue(),
                    configPropertyItem.getValue())) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * @param module
     * @return
     */
    @Override
    public boolean supportsModule(String module) {
        if (StringUtils.isEmpty(module)
                || StringUtils.equalsAnyIgnoreCase(this.module, module)) {
            return true;
        }
        return false;
    }
    
    /**
     * @param key
     * @return
     */
    @Override
    public ConfigProperty findByCode(String module, String code) {
        AssertUtils.notEmpty(code, "code is empty.");
        
        if (!codes.contains(code)) {
            return null;
        }
        
        ConfigProperty res = this.configPropertyItemService
                .findByCode(this.module, code);
        return res;
    }
    
    /**
     * @param params
     * @return
     */
    @Override
    public List<ConfigProperty> queryList(String module,
            Map<String, Object> params) {
        List<ConfigPropertyItem> cpiList = this.configPropertyItemService
                .queryList(this.module, params);
        List<ConfigProperty> resList = new ArrayList<ConfigProperty>();
        for (ConfigPropertyItem itemTemp : cpiList) {
            if (!codes.contains(itemTemp.getCode())) {
                continue;
            }
            resList.add(itemTemp);
        }
        return resList;
    }
    
    /**
     * @param module
     * @param parentId
     * @param params
     * @return
     */
    @Override
    public List<ConfigProperty> queryChildsByParentId(String module,
            String parentId, Map<String, Object> params) {
        List<ConfigPropertyItem> cpiList = this.configPropertyItemService
                .queryChildrenByParentId(this.module, parentId, params);
        List<ConfigProperty> resList = new ArrayList<ConfigProperty>();
        for (ConfigPropertyItem itemTemp : cpiList) {
            if (!codes.contains(itemTemp.getCode())) {
                continue;
            }
            resList.add(itemTemp);
        }
        return resList;
    }
    
    /**
     * @param module
     * @param parentId
     * @param params
     * @return
     */
    @Override
    public List<ConfigProperty> queryNestedChildsByParentId(String module,
            String parentId, Map<String, Object> params) {
        List<ConfigPropertyItem> cpiList = this.configPropertyItemService
                .queryDescendantsByParentId(this.module, parentId, params);
        List<ConfigProperty> resList = new ArrayList<ConfigProperty>();
        for (ConfigPropertyItem itemTemp : cpiList) {
            if (!codes.contains(itemTemp.getCode())) {
                continue;
            }
            resList.add(itemTemp);
        }
        return resList;
    }
    
    /**
     * @param module
     * @param code
     * @param value
     * @return
     */
    @Override
    public boolean patch(String module, String code, String value) {
        AssertUtils.notEmpty(module, "module is empty.");
        AssertUtils.notEmpty(code, "code is empty.");
        value = value == null ? "" : value;
        
        boolean res = this.configPropertyItemService.patch(this.module,
                code,
                value);
        return res;
    }
    
}
