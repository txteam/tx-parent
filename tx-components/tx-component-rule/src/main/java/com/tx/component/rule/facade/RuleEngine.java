/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-1-19
 * <修改描述:>
 */
package com.tx.component.rule.facade;

import com.tx.component.rule.support.RuleSessionTemplate;


/**
 * 规则引擎<br/>
 * 集成Java规则引擎的目标是，
 * 使用标准的Java规则引擎API封装不同的实现，
 * 屏蔽不同的产品实现细节。这样做的好处是，
 * 当替换不同的规则引擎产品时，可以不必修改应用代码。
 * 
 * 1、负责规则容器的加载<br/>
 *      加载规则
 *      加载容器
 *      加载groovy等
 * 2、规则使用的门面接口封装
 * 
 * @author  brady
 * @version  [版本号, 2013-1-19]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class RuleEngine {
    
    private static RuleSessionTemplate ruleSessionTemplate;
    
    public static RuleSessionTemplate getRuleSessionTemplate(){
        return ruleSessionTemplate;
    }
}
