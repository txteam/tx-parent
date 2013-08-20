/*
 * 描          述:  <描述>
 * 修  改   人:  pengqingyang
 * 修改时间:  2013-1-27
 * <修改描述:>
 */
package com.tx.component.rule.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.core.util.StringUtils;
import org.drools.definition.KnowledgePackage;
import org.drools.io.ResourceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.Resource;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.thoughtworks.xstream.XStream;
import com.tx.component.rule.context.RuleLoader;
import com.tx.component.rule.drools.DroolsRule;
import com.tx.component.rule.model.Rule;
import com.tx.component.rule.model.RuleStateEnum;
import com.tx.component.rule.model.SimplePersistenceRule;
import com.tx.component.rule.model.SimpleRuleParamEnum;
import com.tx.component.rule.model.SimpleRulePropertyByte;
import com.tx.component.rule.service.SimplePersistenceRuleService;
import com.tx.core.exceptions.argument.NullArgumentException;
import com.tx.core.util.XstreamUtils;

/**
 * 配置类型的规则加载器<br/>
 * 
 * @author  pengqingyang
 * @version  [版本号, 2013-1-27]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class RuleConfigLoader implements RuleLoader,ApplicationContextAware{
    
    /** */
    private Logger logger = LoggerFactory.getLogger(RuleConfigLoader.class);
    
    /** 配置资源集 */
    @Value("classpath:/rulecontext/*.xml")
    private String configLocations;
    
    /** 加载器运行顺序 */
    private int order = Integer.MAX_VALUE - 256;
    
    /** 规则配置文件解析器 */
    private static final XStream ruleConfigParse = XstreamUtils.getXstream(RulesConfig.class);
    
    @javax.annotation.Resource(name = "simplePersistenceRuleService")
    private SimplePersistenceRuleService simplePersistenceRuleService;
    
    private ApplicationContext applicationContext;
    
    /**
     * @param arg0
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        this.applicationContext = applicationContext;
    }

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
    public List<Rule> load() {
        if (StringUtils.isEmpty(this.configLocations)) {
            return null;
        }
        
        //读取配置文件
        List<RuleItemConfig> ruleItemList = null;
        try {
            ruleItemList = loadFromConfigXML();
        } catch (IOException e) {
            logger.warn(e.toString(),e);
            logger.warn("load config fail. configLocations:{}",this.configLocations);
        }
        
        //如果配置文件未配置规则
        if(ruleItemList == null || ruleItemList.size() == 0){
            return null;
        }
        
        List<Rule> resList = new ArrayList<Rule>();
        
        
        //循环根据配置加载支持类型的规则
        for(RuleItemConfig ruleItemConfigTemp : ruleItemList){
            if(ruleItemConfigTemp == null || StringUtils.isEmpty(ruleItemConfigTemp.getRule())
                    || ruleItemConfigTemp.getRuleType() == null){
                throw new NullArgumentException("规则项{}不合法，rule or ruleType is empty.");
            }
            switch (ruleItemConfigTemp.getRuleType()) {
                case DROOLS_DRL_BYTE:
                    DroolsRule ruleTemp = createDrlByteDroolsRule(ruleItemConfigTemp);
                    if(ruleTemp != null){
                        resList.add(ruleTemp);
                    }
                    break;
                default:
                    //TODO:XXX
                    //暂不支持该类型规则
                    break;
            }
        }
        
        return resList;
    }
    
    /**
      * 持久化DROOLS_DRL_BYTE类型规则
      * <功能详细描述>
      * @param ruleItemConfigTemp [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    private DroolsRule createDrlByteDroolsRule(RuleItemConfig ruleItemConfigTemp){
        SimplePersistenceRule spRule = new SimplePersistenceRule();
        
        //设置规则通用属性
        spRule.setRule(ruleItemConfigTemp.getRule());
        spRule.setRuleType(ruleItemConfigTemp.getRuleType());
        spRule.setServiceType(ruleItemConfigTemp.getServiceType());
        
        //设置规则配置属性
        
        //设置规则表达式，并根据表达式解析规则
        String ruleExpression = ruleItemConfigTemp.getRuleExpression().trim();
        byte[] bytes = null;
        
        Resource drlFileResource = this.applicationContext.getResource(ruleExpression);
        if(!drlFileResource.exists() || !drlFileResource.isReadable()){
            spRule.setState(RuleStateEnum.ERROR);
            
            //如果对应资源不存在，则不再继续加载该资源，并不进行持久
            logger.warn("drl_drools_byte ruleExpression:{} is not exist.",ruleExpression);
            return null;
        }else{
            InputStream input = null;
            
            try {
                input = drlFileResource.getInputStream();
                bytes = IOUtils.toByteArray(input);
            } catch (IOException e) {
                logger.error(e.toString(),e);
                
            } finally{
                IOUtils.closeQuietly(input);
            }
            
        }
        
        //保存规则
        if(bytes == null){
            logger.warn("rule:{} byte is empty.load skip." + ruleItemConfigTemp.getRule());
            return null;
        }
        
        //开始构建drools规则
        KnowledgeBuilder kBuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
        kBuilder.add(ResourceFactory.newByteArrayResource(bytes),
                ResourceType.DRL);
        
        if (kBuilder.hasErrors()) {
            logger.warn("rule:{} build has error.load skip." + ruleItemConfigTemp.getRule());
            logger.warn("error:{}",kBuilder.getErrors());
            return null;
        }
        
        //持久化该规则
        MultiValueMap<SimpleRuleParamEnum, SimpleRulePropertyByte> bytePropertyValues = new LinkedMultiValueMap<SimpleRuleParamEnum, SimpleRulePropertyByte>();
        SimpleRulePropertyByte proByte = new SimpleRulePropertyByte();
        proByte.setParamValue(bytes);
        bytePropertyValues.add(SimpleRuleParamEnum.DROOLS_DRL_RESOURCE_BYTE, proByte);
        spRule.setBytePropertyValues(bytePropertyValues);
        spRule.setState(RuleStateEnum.OPERATION);
        //持久配置文件中规则
        this.simplePersistenceRuleService.saveSimplePersistenceRule(spRule);
        
        //构建返回的drools规则
        Collection<KnowledgePackage> kpCollection = kBuilder.getKnowledgePackages();
        KnowledgeBase knowledgeBase = KnowledgeBaseFactory.newKnowledgeBase();
        knowledgeBase.addKnowledgePackages(kpCollection);
        
        return new DroolsRule(spRule, knowledgeBase);
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
        Resource[] locations = this.applicationContext.getResources(this.configLocations);
        List<RuleItemConfig> ruleItemList = new ArrayList<RuleItemConfig>();
        for (Resource resourceTemp : locations) {
            InputStream in = null;
            try {
                in = resourceTemp.getInputStream();
                RulesConfig rulesConfigsTemp = (RulesConfig)ruleConfigParse.fromXML(in);
                
                ruleItemList.addAll(rulesConfigsTemp.getRuleItemConfig());
            } catch (Exception e) {
                logger.warn(e.toString(), e);
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
     * @param 对order进行赋值
     */
    public void setOrder(int order) {
        this.order = order;
    }
}
