/*
 * 描          述:  <描述>
 * 修  改   人:  pengqingyang
 * 修改时间:  2013-1-27
 * <修改描述:>
 */
package com.tx.component.rule.loader.java;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.drools.core.util.StringUtils;
import org.slf4j.helpers.MessageFormatter;
import org.springframework.context.ApplicationContext;
import org.springframework.core.Ordered;

import com.tx.component.rule.impl.java.JavaMethodRuleParamEnum;
import com.tx.component.rule.loader.BaseRuleItemLoader;
import com.tx.component.rule.loader.RuleItem;
import com.tx.component.rule.loader.RuleTypeEnum;
import com.tx.component.rule.loader.java.annotation.RuleClassMapping;
import com.tx.component.rule.loader.java.annotation.RuleMethodMapping;
import com.tx.core.exceptions.util.AssertUtils;

/**
 * 方法类型的规则加载器<br/>
 * 
 * @author  pengqingyang
 * @version  [版本号, 2013-1-27]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class JavaMethodRuleItemLoader extends BaseRuleItemLoader {
    
    /**
     * 规则顺序：值大的规则加载器将会覆盖同名规则加载器
     */
    private int order = Ordered.HIGHEST_PRECEDENCE;
    
    /**
     * @return
     */
    public List<RuleItem> load() {
        //获取当前存在的方法规则
        List<RuleItem> resList = scanJavaMethodRule(this.applicationContext);
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
     * 扫描获取当前系统中存在的方法类型规则
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return List<MethodRule> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    private List<RuleItem> scanJavaMethodRule(
            ApplicationContext applicationContext) {
        //在spring容器中扫描含有RuleMethodClass注解的类
        Map<String, Object> beanMap = applicationContext.getBeansWithAnnotation(RuleClassMapping.class);
        List<RuleItem> resList = new ArrayList<RuleItem>();
        //遍历类中所有方法，如果含有RuleMethod注解
        for (Entry<String, Object> entryTemp : beanMap.entrySet()) {
            Object beanTemp = entryTemp.getValue();
            RuleClassMapping beanAnno = beanTemp.getClass()
                    .getAnnotation(RuleClassMapping.class);
            String beanAnnoKey = beanAnno.value() == null ? ""
                    : beanAnno.value().trim();
            for (Method methodTemp : beanTemp.getClass().getMethods()) {
                //如果方法没有ruleMethod注解
                if (!methodTemp.isAnnotationPresent(RuleMethodMapping.class)) {
                    continue;
                }
                
                //如果含有：提取，并生成MethodRule
                RuleMethodMapping methodAnno = methodTemp.getAnnotation(RuleMethodMapping.class);
                String methodAnnoKey = methodAnno.value();
                String serviceType = methodAnno.serviceType();
                //key不能为空
                AssertUtils.notEmpty(methodAnnoKey,
                        "methodAnnoKey is null.beanClass:{},method:{}",
                        new Object[] { beanTemp.getClass(), methodTemp });
                //serviceType不能为空
                AssertUtils.notEmpty(serviceType,
                        "serviceType is null.beanClass:{},method:{}",
                        new Object[] { beanTemp.getClass(), methodTemp });
                String key = StringUtils.isEmpty(beanAnnoKey) ? methodAnnoKey
                        : beanAnnoKey + "." + methodAnnoKey;
                
                RuleItem newRuleItem = null;
                newRuleItem = buildRuleItem(beanTemp,
                        methodTemp,
                        methodAnno,
                        serviceType,
                        key);
                resList.add(newRuleItem);
            }
        }
        return resList;
    }
    
    /** 
     * 构建规则项<br/>
     *<功能详细描述>
     * @param beanTemp
     * @param methodTemp
     * @param methodAnno
     * @param serviceType
     * @param key
     * @return [参数说明]
     * 
     * @return RuleItem [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    private RuleItem buildRuleItem(Object beanTemp, Method methodTemp,
            RuleMethodMapping methodAnno, String serviceType, String key) {
        RuleItem newRuleItem;
        newRuleItem = new RuleItem();
        newRuleItem.setRuleType(RuleTypeEnum.JAVA_METHOD);
        newRuleItem.addObjectParam(JavaMethodRuleParamEnum.BEAN.toString(),
                beanTemp);
        newRuleItem.addObjectParam(JavaMethodRuleParamEnum.METHOD.toString(),
                methodTemp);
        newRuleItem.setKey(key);
        newRuleItem.setName(methodAnno.name());
        newRuleItem.setServiceType(serviceType);
        newRuleItem.setModifyAble(false);
        
        newRuleItem.setRemark(MessageFormatter.arrayFormat("JavaMethodRule class:{} method:{}",
                new Object[] { beanTemp.getClass().getName(),
                        methodTemp.getName() })
                .getMessage());
        return newRuleItem;
    }
    
    /**
     * @param arg0
     * @return
     */
    @Override
    public boolean equals(Object arg0) {
        if(arg0 == null){
            return false;
        }
        if(JavaMethodRuleItemLoader.class.equals(arg0.getClass())){
            return true;
        }else{
            return false;
        }
    }

    /**
     * @return
     */
    @Override
    public int hashCode() {
        return JavaMethodRuleItemLoader.class.hashCode();
    }
}
