/*
 * 修  改   人:  Administrator
 * 修改时间:  2014年3月16日
 * <修改描述:>
 */
package com.tx.component.rule.loader.persister;

import java.util.List;

import javax.annotation.Resource;

import com.tx.component.rule.loader.RuleItem;
import com.tx.component.rule.loader.RuleItemPersister;
import com.tx.component.rule.loader.persister.service.RuleItemService;
import com.tx.core.exceptions.util.AssertUtils;

/**
 * 数据库规则项配置加载器<br/>
 *     动态加载，支持数据库部分由于在集群环境下
 *     一态机器添加规则后，其他机器应当进行添加，这里机制需要想清楚<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2014年3月16日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class RuleItemPersisterImpl implements RuleItemPersister {
    
    @Resource(name = "ruleItemService")
    private RuleItemService ruleItemService;
    
    /**
     * @param key
     * @return
     */
    @Override
    public RuleItem find(String key) {
        AssertUtils.notEmpty(key, "key is empty.");
        RuleItem res = this.ruleItemService.findRuleItemByKey(key);
        return res;
    }
    
    /**
     * @return
     */
    @Override
    public List<RuleItem> list() {
        List<RuleItem> resList = this.ruleItemService.listRuleItem();
        return resList;
    }
    
    /**
     * @param ruleKey
     * @return
     */
    @Override
    public boolean deleteRuleByRuleKey(String ruleKey) {
        boolean resFlag = this.ruleItemService.deleteByKey(ruleKey);
        return resFlag;
    }
    
}
