/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-1-28
 * <修改描述:>
 */
package com.tx.component.rule.controller;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tx.component.rule.model.SimplePersistenceRule;
import com.tx.component.rule.service.SimplePersistenceRuleService;
import com.tx.core.paged.model.PagedList;


 /**
  * 规则控制器<br/>
  * <功能详细描述>
  * 
  * @author  brady
  * @version  [版本号, 2013-1-28]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
@Controller("ruleController")
@RequestMapping("/rule")
public class RuleController {
    
    /** 日志记录器 */
    @SuppressWarnings("unused")
    private Logger logger = LoggerFactory.getLogger(RuleController.class);
    
    @Resource(name = "simplePersistenceRuleService")
    private SimplePersistenceRuleService simplePersistenceRuleService;
    
    /**
      * 分页查看规则集
      * <功能详细描述>
      * @return [参数说明]
      * 
      * @return PagedList<SimplePersistenceRule> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public PagedList<SimplePersistenceRule> querySimpleRulePagedList(){
        
        return null;
    }
    
    public List<SimplePersistenceRule> querySimpleRuleList(){
        
        return null;
    }
    
    public boolean registeRule(){
        
        return false;
    }
}
