/*
 * 描          述:  <描述>
 * 修  改   人:  pengqingyang
 * 修改时间:  2013-1-27
 * <修改描述:>
 */
package com.tx.component.rule.method;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.transaction.annotation.Transactional;

import com.tx.component.rule.context.RuleLoader;
import com.tx.component.rule.model.Rule;
import com.tx.component.rule.model.RuleStateEnum;
import com.tx.component.rule.model.RuleTypeEnum;
import com.tx.component.rule.model.SimplePersistenceRule;
import com.tx.component.rule.service.SimplePersistenceRuleService;
import com.tx.core.TxConstants;

/**
 * 方法类型的规则加载器<br/>
 * 
 * @author  pengqingyang
 * @version  [版本号, 2013-1-27]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class MethodRuleLoader implements RuleLoader, ApplicationContextAware {
    
    private ApplicationContext applicationContext;
    
    /** 规则存在的包 */
    private String rulePackage = "com.tx";
    
    /** 负责对规则进行持久  */
    @Resource(name = "simplePersistenceRuleService")
    private SimplePersistenceRuleService simplePersistenceRuleService;
    
    /** 
     * 是否同步移除不存在方法类型规则
     *     开发阶段如果打开该设置可能造成，当前新添加的规则，被其他机器意外移除
     */
    private boolean isSynchronizeRemoveNotExsitMethodRule = true;
    
    /**
     * 规则顺序：值大的规则加载器将会覆盖同名规则加载器
     */
    private int order = 0;
    
    /**
     * @param applicationContext
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
    @Transactional
    public List<Rule> load() {
        List<Rule> resList = new ArrayList<Rule>();
        
        //获取当前存在的方法规则
        List<MethodRule> currentRuleMethodList = MethodRuleHelper.scanCurrentSystemRuleMethod(this.applicationContext);
        //获取历史数据库中存储的规则
        Map<String, MethodRule> currentRuleMap = new HashMap<String, MethodRule>();
        
        //获取历史数据库中存储的规则
        Map<String, SimplePersistenceRule> dbRuleMap = queryDBMethodRuleMap();
        
        //压入加载的规则方法
        for (MethodRule mrTemp : currentRuleMethodList) {
            //设置该部分需要写入容器的规则状态为运营态
            mrTemp.setState(RuleStateEnum.OPERATION);
            currentRuleMap.put(getRuleKeyFromRule(mrTemp), mrTemp);
            
            SimplePersistenceRule dbRuleTemp = dbRuleMap.get(getRuleKeyFromRule(mrTemp));
            
            //如果对应规则，在数据库中原不存在
            if (dbRuleTemp == null) {
                //持久化新添加的规则
                SimplePersistenceRule spr = new SimplePersistenceRule(mrTemp);
                this.simplePersistenceRuleService.saveSimplePersistenceRule(spr);
                
                //设置对应的存储规则
                mrTemp.setSimplePersistenceRule(spr);
            }
            else if (dbRuleTemp != null
                    && !RuleStateEnum.OPERATION.equals(dbRuleTemp.getState())) {
                //修改存储中规则状态为持久态
                this.simplePersistenceRuleService.changeRuleStateById(dbRuleTemp.getId(),
                        RuleStateEnum.OPERATION);
                
                mrTemp.setSimplePersistenceRule(dbRuleTemp);
            }
            else {
                //设置规则状态
                mrTemp.setSimplePersistenceRule(dbRuleTemp);
            }
            
            //添加到容器中
            resList.add(mrTemp);
        }
        
        //根据当前系统中存在的规则，如果方法级规则现在不存在了，则将该规则设置为错误态
        if (isSynchronizeRemoveNotExsitMethodRule) {
            for (Entry<String, SimplePersistenceRule> entryTemp : dbRuleMap.entrySet()) {
                if (!currentRuleMap.containsKey(getRuleKeyFromRule(entryTemp.getValue()))) {
                    SimplePersistenceRule spRule = entryTemp.getValue();
                    //如果不为运营态则将该规则实体社会自为错误态
                    if (!RuleStateEnum.OPERATION.equals(spRule.getState())) {
                        //设置对应规则状态为错误态
                        spRule.setState(RuleStateEnum.ERROR);
                        
                        //持久化规则状态
                        this.simplePersistenceRuleService.changeRuleStateById(spRule.getId(),
                                RuleStateEnum.ERROR);
                        
                        //错误态的规则，依然加入规则容器中
                        MethodRule methodRule = new MethodRule(spRule);
                        
                        resList.add(methodRule);
                    }
                    
                }
            }
        }
        
        return resList;
    }
    
    /**
     * 方法级规则最先调用校验方法
     * @return
     */
    @Override
    public int getOrder() {
        return order;
    }
    
    /**
      *<功能简述>
      *<功能详细描述>
      * @param rule
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    private String getRuleKeyFromRule(Rule rule) {
        StringBuilder sb = new StringBuilder(TxConstants.INITIAL_STR_LENGTH);
        sb.append(rule.getRuleType())
                .append(".")
                .append(rule.getServiceType())
                .append(".")
                .append(rule.rule());
        return sb.toString();
    }
    
    /**
      * 查询数据库中方法类型的规则映射
      * <功能详细描述>
      * @return [参数说明]
      * 
      * @return List<SimplePersistenceRule> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    private Map<String, SimplePersistenceRule> queryDBMethodRuleMap() {
        List<SimplePersistenceRule> resList = this.simplePersistenceRuleService.querySimplePersistenceRuleListByRuleType(RuleTypeEnum.METHOD);
        Map<String, SimplePersistenceRule> res = new HashMap<String, SimplePersistenceRule>();
        if (resList == null) {
            return res;
        }
        for (SimplePersistenceRule simplePRuleTemp : resList) {
            res.put(getRuleKeyFromRule(simplePRuleTemp), simplePRuleTemp);
        }
        return res;
    }
    
    /**
     * @return 返回 rulePackage
     */
    public String getRulePackage() {
        return rulePackage;
    }
    
    /**
     * @param 对rulePackage进行赋值
     */
    public void setRulePackage(String rulePackage) {
        this.rulePackage = rulePackage;
    }
    
    /**
     * @return 返回 isSynchronizeRemoveNotExsitMethodRule
     */
    public boolean isSynchronizeRemoveNotExsitMethodRule() {
        return isSynchronizeRemoveNotExsitMethodRule;
    }
    
    /**
     * @param 对isSynchronizeRemoveNotExsitMethodRule进行赋值
     */
    public void setSynchronizeRemoveNotExsitMethodRule(
            boolean isSynchronizeRemoveNotExsitMethodRule) {
        this.isSynchronizeRemoveNotExsitMethodRule = isSynchronizeRemoveNotExsitMethodRule;
    }
    
    /**
     * @param 对order进行赋值
     */
    public void setOrder(int order) {
        this.order = order;
    }
    
    /**
     * @return 返回 simplePersistenceRuleService
     */
    public SimplePersistenceRuleService getSimplePersistenceRuleService() {
        return simplePersistenceRuleService;
    }
    
    /**
     * @param 对simplePersistenceRuleService进行赋值
     */
    public void setSimplePersistenceRuleService(
            SimplePersistenceRuleService simplePersistenceRuleService) {
        this.simplePersistenceRuleService = simplePersistenceRuleService;
    }
    
}
