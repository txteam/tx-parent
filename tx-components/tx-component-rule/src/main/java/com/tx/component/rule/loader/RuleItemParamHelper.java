/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2014年3月17日
 * <修改描述:>
 */
package com.tx.component.rule.loader;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.commons.lang.enums.EnumUtils;

import com.tx.component.rule.context.Rule;
import com.tx.core.exceptions.util.AssertUtils;

/**
 * 规则项目参数帮助类<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2014年3月17日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public abstract class RuleItemParamHelper {
    
    /**
      * 根据传入的类名获取规则项目参数类型集合<br/> 
      *<功能详细描述>
      * @param ruleItemParamEnumClass
      * @return [参数说明]
      * 
      * @return Set<RuleItemParam> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public static <R extends Rule, P extends RuleItemParamEnumInterface<R>> Set<RuleItemParam> getRuleItemParamSet(
            Class<P> ruleItemParamEnumClass) {
        AssertUtils.notNull(ruleItemParamEnumClass,
                "ruleItemParamEnumClass is null.");
        AssertUtils.isTrue(ruleItemParamEnumClass.isEnum(),
                "ruleItemParamEnumClass is not Enum.");
        
        @SuppressWarnings("unchecked")
        Iterator<P> ite = EnumUtils.iterator(ruleItemParamEnumClass);
        Set<RuleItemParam> resSet = new HashSet<RuleItemParam>();
        while (ite.hasNext()) {
            P pTemp = ite.next();
            RuleItemParam ruleItemParamTemp = new RuleItemParam(pTemp);
            
            resSet.add(ruleItemParamTemp);
        }
        
        return resSet;
    }
    
}
