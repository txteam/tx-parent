/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2019年3月5日
 * <修改描述:>
 */
package com.tx.component.configuration.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.Ordered;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import com.thoughtworks.xstream.XStream;
import com.tx.component.configuration.config.ConfigCatalogParser;
import com.tx.component.configuration.config.ConfigContentParser;
import com.tx.component.configuration.config.ConfigPropertyParser;
import com.tx.component.configuration.model.ConfigProperty;
import com.tx.component.configuration.model.ConfigPropertyItem;
import com.tx.component.configuration.service.ConfigPropertyItemService;
import com.tx.component.configuration.service.ConfigPropertyManager;
import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.querier.model.Querier;
import com.tx.core.util.PinyinUtils;
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
public class LocalConfigPropertyManager
        implements ConfigPropertyManager, InitializingBean {
    
    /** resourceResolver */
    private static final ResourcePatternResolver resourceResolver = new PathMatchingResourcePatternResolver();
    
    //日志记录句柄
    private Logger logger = LoggerFactory
            .getLogger(LocalConfigPropertyManager.class);
    
    //配置解析句柄
    private XStream configXstream = XstreamUtils
            .getXstream(ConfigContentParser.class);
    
    /** 所属模块 */
    private String module;
    
    /** 配置文件所在路径 */
    private String configLocation;
    
    /** 配置项业务层 */
    private ConfigPropertyItemService configPropertyItemService;
    
    /** code的有效值域 */
    private Set<String> codes = new HashSet<>();
    
    /** <默认构造函数> */
    public LocalConfigPropertyManager(String module, String configLocation,
            ConfigPropertyItemService configPropertyItemService) {
        super();
        this.module = module;
        this.configLocation = configLocation;
        
        this.configPropertyItemService = configPropertyItemService;
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
        
        org.springframework.core.io.Resource[] configResources = resourceResolver
                .getResources(configLocation);
        if (ArrayUtils.isEmpty(configResources)) {
            return;
        }
        
        for (org.springframework.core.io.Resource configResource : configResources) {
            if (!configResource.exists()) {
                logger.info("configLocation resource is not exist.");
                continue;
            }
            
            //解析配置文件
            ConfigContentParser parser = (ConfigContentParser) configXstream
                    .fromXML(configResource.getInputStream());
            
            //初始化配置属性
            initCatalogs(null, parser.getCatalogs());
            initConfigs(null, parser.getConfigs());
        }
    }
    
    /**
     * 初始化配置目录<br/>
     * <功能详细描述>
     * @param catalog
     * @param catalogs [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    private void initCatalogs(ConfigPropertyItem parent,
            List<ConfigCatalogParser> catalogs) {
        if (CollectionUtils.isEmpty(catalogs)) {
            return;
        }
        for (ConfigCatalogParser catalogTemp : catalogs) {
            preprocessing(catalogTemp);
            String codeTemp = catalogTemp.getCode();
            codes.add(codeTemp);
            
            ConfigPropertyItem item = this.configPropertyItemService
                    .findByCode(this.module, codeTemp);
            if (item == null) {
                item = doAdd(parent,
                        catalogTemp.getCode(),
                        catalogTemp.getName());
                logger.debug("...新增配置类目: code:{} | name:{}",
                        new Object[] { item.getCode(), item.getName() });
            } else if (!matches(item,
                    parent,
                    catalogTemp.getCode(),
                    catalogTemp.getName())) {
                doUpdate(item,
                        parent,
                        catalogTemp.getCode(),
                        catalogTemp.getName());
                logger.debug("...更新配置项: code:{} | name:{}",
                        new Object[] { item.getCode(), item.getName() });
            } else {
                logger.debug("...加载配置项: code:{} | name:{}",
                        new Object[] { item.getCode(), item.getName() });
            }
            
            //嵌套初始化配置
            initCatalogs(item, catalogTemp.getCatalogs());
            initConfigs(item, catalogTemp.getConfigs());
        }
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
    private void initConfigs(ConfigPropertyItem parent,
            List<ConfigPropertyParser> configs) {
        if (CollectionUtils.isEmpty(configs)) {
            return;
        }
        for (ConfigPropertyParser configTemp : configs) {
            preprocessing(configTemp);
            String codeTemp = configTemp.getCode();
            codes.add(codeTemp);
            
            ConfigPropertyItem item = this.configPropertyItemService
                    .findByCode(this.module, codeTemp);
            ConfigPropertyItem configItem = doBuild(parent, configTemp);
            if (item == null) {
                item = doAdd(configItem);
                logger.debug(
                        "...新增配置项: code:{} | name:{} | value:{} | remark:{} | parentId:{} | validateExpression:{}",
                        new Object[] { item.getCode(), item.getName(),
                                item.getValue(), item.getRemark(),
                                item.getParentId(),
                                item.getValidateExpression() });
            } else if (!matches(item, configItem)) {
                doUpdate(item, configItem);
                logger.debug(
                        "...更新配置项: code:{} | name:{} | value:{} | remark:{} | parentId:{} | validateExpression:{}",
                        new Object[] { item.getCode(), item.getName(),
                                item.getValue(), item.getRemark(),
                                item.getParentId(),
                                item.getValidateExpression() });
            } else {
                logger.debug(
                        "...加载配置项: code:{} | name:{} | value:{} | remark:{} | parentId:{} | validateExpression:{}",
                        new Object[] { item.getCode(), item.getName(),
                                item.getValue(), item.getRemark(),
                                item.getParentId(),
                                item.getValidateExpression() });
            }
        }
    }
    
    /**
     * 配置值需要进行预处理<br/>
     * <功能详细描述>
     * @param propertyConfig [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    private void preprocessing(ConfigCatalogParser catalog) {
        //配置项中name,code不能均为空
        AssertUtils.notTrue(
                StringUtils.isEmpty(catalog.getCode())
                        && StringUtils.isEmpty(catalog.getName()),
                "配置项中:code,name均为空.");
        
        //如果编码为空,则利用名称生成编码
        if (StringUtils.isEmpty(catalog.getCode())) {
            //当code为空时需要根据name生成对应的code
            String name = catalog.getName();
            String newcode = (new StringBuilder(PinyinUtils.parseToPY(name)))
                    .append("_").append(Math.abs(name.hashCode())).toString();
            if (newcode.length() > 32) {
                newcode = newcode.substring(0, 32);
            }
            catalog.setCode(newcode);
        }
        //如果名称为空，则用编码当做名称
        if (StringUtils.isEmpty(catalog.getName())) {
            catalog.setName(catalog.getCode());
        }
    }
    
    /**
     * 配置值需要进行预处理<br/>
     * <功能详细描述>
     * @param config [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    private void preprocessing(ConfigPropertyParser config) {
        //配置项中name,code不能均为空
        AssertUtils.notTrue(
                StringUtils.isEmpty(config.getCode())
                        && StringUtils.isEmpty(config.getName()),
                "配置项中:code,name均为空.");
        
        //如果编码为空,则利用名称生成编码
        if (StringUtils.isEmpty(config.getCode())) {
            //当code为空时需要根据name生成对应的code
            String name = config.getName();
            String newcode = (new StringBuilder(PinyinUtils.parseToPY(name)))
                    .append("_").append(Math.abs(name.hashCode())).toString();
            if (newcode.length() > 32) {
                newcode = newcode.substring(0, 32);
            }
            config.setCode(newcode);
        }
        //如果名称为空，则用编码当做名称
        if (StringUtils.isEmpty(config.getName())) {
            config.setName(config.getCode());
        }
        //值不能为空，如果未空则更新为空字符串
        if (config.getValue() == null) {
            config.setValue("");
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
    private ConfigPropertyItem doAdd(ConfigPropertyItem parent, String code,
            String name) {
        ConfigPropertyItem item = new ConfigPropertyItem();
        item.setCode(code);
        item.setModule(this.module);
        
        item.setName(name);
        item.setValue("");
        item.setRemark(null);
        item.setValidateExpression(null);
        item.setModifyAble(false);
        item.setParentId(parent == null ? null : parent.getId());
        item.setLeaf(false);
        
        this.configPropertyItemService.insert(item);
        return item;
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
    private ConfigPropertyItem doAdd(ConfigPropertyItem item) {
        this.configPropertyItemService.insert(item);
        return item;
    }
    
    /**
     * 构建配置属性<br/>
     * <功能详细描述>
     * @param parent
     * @param config
     * @return [参数说明]
     * 
     * @return ConfigPropertyItem [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    private ConfigPropertyItem doBuild(ConfigPropertyItem parent,
            ConfigPropertyParser config) {
        ConfigPropertyItem item = new ConfigPropertyItem();
        item.setCode(config.getCode());
        item.setModule(this.module);
        
        item.setName(config.getName());
        item.setValidateExpression(config.getValidateExpression());
        item.setParentId(parent == null ? null : parent.getId());
        item.setModifyAble(config.isModifyAble());
        item.setLeaf(true);
        item.setRemark(config.getRemark());
        item.setValue(config.getValue());
        return item;
    }
    
    /**
     * 根据配置 更新配置属性<br/>
     * <功能详细描述>
     * @param item
     * @param parent
     * @param config [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    private void doUpdate(ConfigPropertyItem item, ConfigPropertyItem parent,
            String code, String name) {
        item.setCode(code);
        item.setModule(this.module);
        
        item.setName(name);
        item.setValidateExpression(null);
        item.setParentId(parent == null ? null : parent.getId());
        item.setModifyAble(false);
        item.setLeaf(false);
        item.setRemark(null);
        item.setValue("");
        
        this.configPropertyItemService.updateById(item);
    }
    
    /**
     * 根据配置 更新配置属性<br/>
     * <功能详细描述>
     * @param item
     * @param parent
     * @param config [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    private void doUpdate(ConfigPropertyItem item,
            ConfigPropertyItem configItem) {
        item.setName(configItem.getName());
        item.setRemark(configItem.getRemark());
        item.setValidateExpression(configItem.getValidateExpression());
        item.setModifyAble(configItem.isModifyAble());
        item.setParentId(configItem.getId());
        item.setLeaf(true);
        if (!configItem.isModifyAble()) {
            item.setValue(configItem.getValue());
        }
        this.configPropertyItemService.updateById(item);
    }
    
    /**
     * 判断配置属性是否需要进行初始化<br/>
     * <功能详细描述>
     * @param item
     * @param parent
     * @param code
     * @param name
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    private boolean matches(ConfigPropertyItem item, ConfigPropertyItem parent,
            String code, String name) {
        if (!StringUtils.equalsAnyIgnoreCase(code, item.getCode())) {
            return false;
        }
        if (!StringUtils.equalsAnyIgnoreCase(name, item.getName())) {
            return false;
        }
        if (!StringUtils.equalsAnyIgnoreCase(
                parent == null ? null : parent.getId(), item.getParentId())) {
            return false;
        }
        if (item.isLeaf()) {
            return false;
        }
        return true;
    }
    
    /**
     * 判断配置属性是否需要进行初始化<br/>
     * <功能详细描述>
     * @param item
     * @param parent
     * @param config
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    private boolean matches(ConfigPropertyItem item,
            ConfigPropertyItem configItem) {
        if (!StringUtils.equalsAnyIgnoreCase(configItem.getCode(),
                item.getCode())) {
            return false;
        }
        if (!StringUtils.equalsAnyIgnoreCase(configItem.getName(),
                item.getName())) {
            return false;
        }
        if (!StringUtils.equalsAnyIgnoreCase(configItem.getRemark(),
                item.getRemark())) {
            return false;
        }
        if (!StringUtils.equalsAnyIgnoreCase(configItem.getValidateExpression(),
                item.getValidateExpression())) {
            return false;
        }
        if (!StringUtils.equalsAnyIgnoreCase(configItem.getParentId(),
                item.getParentId())) {
            return false;
        }
        if (!item.isLeaf()) {
            return false;
        }
        //如果配置可编辑则，不需要对比value值，如果不可编辑,value值不一致，则使用配置的值
        if (!configItem.isModifyAble()) {
            if (!StringUtils.equalsAnyIgnoreCase(configItem.getValue(),
                    item.getValue())) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * 保存配置分类<br/>
     * <功能详细描述>
     * @param parent
     * @param code
     * @param name [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public ConfigPropertyItem saveCatalog(ConfigPropertyItem parent,
            String code, String name) {
        AssertUtils.notEmpty(code, "code is empty.");
        codes.add(code);
        
        //查询对应的配置项是否存在
        ConfigPropertyItem item = this.configPropertyItemService
                .findByCode(this.module, code);
        if (item == null) {
            item = doAdd(parent, code, name);
            logger.debug("...新增配置类目: code:{} | name:{}",
                    new Object[] { item.getCode(), item.getName() });
        } else if (!matches(item, parent, code, name)) {
            doUpdate(item, parent, code, name);
            logger.debug("...更新配置项: code:{} | name:{}",
                    new Object[] { item.getCode(), item.getName() });
        } else {
            logger.debug("...加载配置项: code:{} | name:{}",
                    new Object[] { item.getCode(), item.getName() });
        }
        return item;
    }
    
    /**
     * 保存配置项<br/>
     * <功能详细描述>
     * @param catalog
     * @param code
     * @param name
     * @param defaultValue [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public void save(ConfigPropertyItem configItem) {
        AssertUtils.notNull(configItem, "configItem is null.");
        AssertUtils.notEmpty(configItem.getCode(), "configItem.code is empty.");
        String code = configItem.getCode();
        codes.add(configItem.getCode());
        
        configItem.setModule(this.module);
        ConfigPropertyItem item = this.configPropertyItemService
                .findByCode(this.module, code);
        if (item == null) {
            item = doAdd(configItem);
            logger.debug(
                    "...新增配置项: code:{} | name:{} | value:{} | remark:{} | parentId:{} | validateExpression:{}",
                    new Object[] { item.getCode(), item.getName(),
                            item.getValue(), item.getRemark(),
                            item.getParentId(), item.getValidateExpression() });
        } else if (!matches(item, configItem)) {
            doUpdate(item, configItem);
            logger.debug(
                    "...更新配置项: code:{} | name:{} | value:{} | remark:{} | parentId:{} | validateExpression:{}",
                    new Object[] { item.getCode(), item.getName(),
                            item.getValue(), item.getRemark(),
                            item.getParentId(), item.getValidateExpression() });
        } else {
            logger.debug(
                    "...加载配置项: code:{} | name:{} | value:{} | remark:{} | parentId:{} | validateExpression:{}",
                    new Object[] { item.getCode(), item.getName(),
                            item.getValue(), item.getRemark(),
                            item.getParentId(), item.getValidateExpression() });
        }
    }
    
    /**
     * @param module
     * @return
     */
    @Override
    public boolean supports(String module) {
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
    public ConfigProperty findByCode(String code) {
        AssertUtils.notEmpty(code, "code is empty.");
        
        if (!codes.contains(code)) {
            return null;
        }
        ConfigProperty res = this.configPropertyItemService
                .findByCode(this.module, code);
        return res;
    }
    
    /**
     * @param querier
     * @return
     */
    @Override
    public List<ConfigProperty> queryList(Querier querier) {
        List<ConfigPropertyItem> cpiList = this.configPropertyItemService
                .queryList(this.module, querier);
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
     * @param parentId
     * @param querier
     * @return
     */
    @Override
    public List<ConfigProperty> queryChildrenByParentId(String parentId,
            Querier querier) {
        List<ConfigPropertyItem> cpiList = this.configPropertyItemService
                .queryChildrenByParentId(this.module, parentId, querier);
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
     * @param parentId
     * @param querier
     * @return
     */
    @Override
    public List<ConfigProperty> queryDescendantsByParentId(String parentId,
            Querier querier) {
        List<ConfigPropertyItem> cpiList = this.configPropertyItemService
                .queryDescendantsByParentId(this.module, parentId, querier);
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
     * @param code
     * @param value
     * @return
     */
    @Override
    public boolean patch(String code, String value) {
        AssertUtils.notEmpty(module, "module is empty.");
        AssertUtils.notEmpty(code, "code is empty.");
        
        if (!codes.contains(code)) {
            return false;
        }
        value = value == null ? "" : value;
        boolean res = this.configPropertyItemService.patch(this.module,
                code,
                value);
        return res;
    }
    
    /**
     * @return
     */
    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
    
}
