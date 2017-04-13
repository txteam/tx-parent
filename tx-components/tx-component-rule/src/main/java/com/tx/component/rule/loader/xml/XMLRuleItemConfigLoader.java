/*
 * 描          述:  <描述>
 * 修  改   人:  pengqingyang
 * 修改时间:  2013-1-27
 * <修改描述:>
 */
package com.tx.component.rule.loader.xml;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.drools.core.util.StringUtils;
import org.slf4j.helpers.MessageFormatter;
import org.springframework.core.Ordered;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import com.thoughtworks.xstream.XStream;
import com.tx.component.rule.loader.BaseRuleItemLoader;
import com.tx.component.rule.loader.RuleItem;
import com.tx.component.rule.loader.xml.model.ByteParam;
import com.tx.component.rule.loader.xml.model.FileParam;
import com.tx.component.rule.loader.xml.model.RuleItemConfig;
import com.tx.component.rule.loader.xml.model.RulesConfig;
import com.tx.component.rule.loader.xml.model.ValueParam;
import com.tx.core.exceptions.argument.ArgIllegalException;
import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.exceptions.util.ExceptionWrapperUtils;
import com.tx.core.util.XstreamUtils;

/**
 * 配置类型的规则加载器<br/>
 * 
 * @author  pengqingyang
 * @version  [版本号, 2013-1-27]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class XMLRuleItemConfigLoader extends BaseRuleItemLoader {
    
    /** 配置资源集 */
    private String configLocations;
    
    /** 加载器运行顺序 */
    private int order = Ordered.HIGHEST_PRECEDENCE + 1;
    
    /** 规则配置文件解析器 */
    private static final XStream ruleConfigParse = XstreamUtils.getXstream(RulesConfig.class);
    
    private ResourcePatternResolver resourceResolver = new PathMatchingResourcePatternResolver();
    
    /**
     * @return
     */
    @Override
    public int getOrder() {
        return this.order;
    }
    
    /**
     * @return
     */
    @Override
    public List<RuleItem> load() {
        if (StringUtils.isEmpty(this.configLocations)) {
            return null;
        }
        
        //读取配置文件
        List<RuleItemConfig> ruleItemConfigList = null;
        try {
            ruleItemConfigList = loadFromConfigXML();
        } catch (IOException e) {
            throw ExceptionWrapperUtils.wrapperIOException(e,
                    "RuleXMLConfigLoader load IOException:");
        }
        
        //如果配置文件未配置规则
        if (ruleItemConfigList == null || ruleItemConfigList.size() == 0) {
            return null;
        }
        
        List<RuleItem> resList = new ArrayList<RuleItem>();
        //循环根据配置加载支持类型的规则
        for (RuleItemConfig ruleItemConfigTemp : ruleItemConfigList) {
            RuleItem ruleItemTemp = buildRuleItemByRuleItemConfig(ruleItemConfigTemp);
            resList.add(ruleItemTemp);
        }
        return resList;
    }
    
    /**
      * 根据规则项配置构建返回容器的规则项
      *<功能详细描述>
      * @param ruleItemConfig
      * @return [参数说明]
      * 
      * @return RuleItem [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    private RuleItem buildRuleItemByRuleItemConfig(RuleItemConfig ruleItemConfig) {
        AssertUtils.notNull(ruleItemConfig, "ruleItemConfig is null");
        AssertUtils.notEmpty(ruleItemConfig.getKey(),
                "ruleItemConfig.key is empty");
        String ruleKey = ruleItemConfig.getKey();
        AssertUtils.notEmpty(ruleItemConfig.getServiceType(),
                "key:{} ruleItemConfig.serviceType is empty",
                ruleKey);
        AssertUtils.notEmpty(ruleItemConfig.getRuleType(),
                "key:{} ruleItemConfig.ruleType is empty",
                ruleKey);
        
        RuleItem ruleItem = new RuleItem();
        ruleItem.setModifyAble(false);
        ruleItem.setKey(ruleKey);
        ruleItem.setServiceType(ruleItemConfig.getServiceType());
        ruleItem.setRuleType(ruleItemConfig.getRuleType());
        ruleItem.setName(ruleItemConfig.getName());
        //解析其中byte相关配置
        if (!CollectionUtils.isEmpty(ruleItemConfig.getByteList())) {
            for (ByteParam byteParam : ruleItemConfig.getByteList()) {
                AssertUtils.notEmpty(byteParam.getKey(),
                        "key:{} paramKey is empty.");
                parseByteParamConfig(ruleKey, ruleItem, byteParam);
            }
        }
        //解析其中value相关配置
        if (!CollectionUtils.isEmpty(ruleItemConfig.getValueList())) {
            for (ValueParam valueParam : ruleItemConfig.getValueList()) {
                AssertUtils.notEmpty(valueParam.getKey(),
                        "key:{} paramKey is empty.");
                ruleItem.addValueParam(valueParam.getKey(),
                        valueParam.getValue());
            }
        }
        //解析其中file相关配置
        if (!CollectionUtils.isEmpty(ruleItemConfig.getFileList())) {
            for (FileParam fileParam : ruleItemConfig.getFileList()) {
                AssertUtils.notEmpty(fileParam.getKey(),
                        "key:{} paramKey is empty.");
                parseFileParamConfig(fileParam.getKey(), ruleItem, fileParam);
            }
        }
        return ruleItem;
    }
    
    /** 
     * 解析其中byte相关配置
     *<功能详细描述>
     * @param ruleKey
     * @param ruleItem
     * @param byteParam [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    private void parseByteParamConfig(String ruleKey, RuleItem ruleItem,
            ByteParam byteParam) {
        String paramKey = byteParam.getKey();
        AssertUtils.isExist(byteParam.getValue(),
                "key:{} paramKey:{} byteParam not exist.",
                ruleKey,
                paramKey);
        InputStream in = null;
        String context = "";
        try {
            in = byteParam.getValue().getInputStream();
            context = IOUtils.toString(in,"UTF-8");
        } catch (IOException e) {
            throw ExceptionWrapperUtils.wrapperIOException(e,
                    "key:{} paramKey:{}",
                    ruleKey,
                    paramKey);
        } finally {
            IOUtils.closeQuietly(in);
        }
        try {
            ruleItem.addByteParam(paramKey, context.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            throw new ArgIllegalException(
                    MessageFormatter.arrayFormat("key:{} paramKey:{} charsetName:{}",
                            new Object[] { ruleKey, paramKey, "UTF-8" })
                            .getMessage());
        }
    }
    
    /** 
     * 解析其中byte相关配置
     *<功能详细描述>
     * @param ruleKey
     * @param ruleItem
     * @param byteParam [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    private void parseFileParamConfig(String ruleKey, RuleItem ruleItem,
            FileParam fileParam) {
        String paramKey = fileParam.getKey();
        AssertUtils.isExist(fileParam.getValue(),
                "key:{} paramKey:{} byteParam not exist.",
                ruleKey,
                paramKey);
        ruleItem.addObjectParam(paramKey, fileParam.getValue());
    }
    
    /**
      * 从配置资源中加载规则配置<br/>
      * <功能详细描述>
      * @return [参数说明]
      * 
      * @return List<RuleItemConfig> [返回类型说明]
     * @throws IOException 
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    private List<RuleItemConfig> loadFromConfigXML() throws IOException {
        Resource[] locations = this.resourceResolver.getResources(this.configLocations);
        
        List<RuleItemConfig> ruleItemList = new ArrayList<RuleItemConfig>();
        for (Resource resourceTemp : locations) {
            InputStream in = null;
            try {
                in = resourceTemp.getInputStream();
                RulesConfig rulesConfigsTemp = (RulesConfig) ruleConfigParse.fromXML(in);
                
                if(rulesConfigsTemp.getRuleItemConfig() != null){
                    ruleItemList.addAll(rulesConfigsTemp.getRuleItemConfig());
                }
            } finally {
                IOUtils.closeQuietly(in);
            }
        }
        return ruleItemList;
    }
    
    /**
     * @return 返回 configLocations
     */
    public String getConfigLocations() {
        return configLocations;
    }
    
    /**
     * @param 对configLocations进行赋值
     */
    public void setConfigLocations(String configLocations) {
        this.configLocations = configLocations;
    }
    
    /**
     * @param arg0
     * @return
     */
    @Override
    public boolean equals(Object arg0) {
        if (arg0 == null) {
            return false;
        }
        if (XMLRuleItemConfigLoader.class.equals(arg0.getClass())) {
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * @return
     */
    @Override
    public int hashCode() {
        return XMLRuleItemConfigLoader.class.hashCode();
    }
}
