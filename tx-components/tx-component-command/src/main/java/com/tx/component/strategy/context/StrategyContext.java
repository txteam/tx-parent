/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-1-23
 * <修改描述:>
 */
package com.tx.component.strategy.context;

import com.tx.core.exceptions.util.AssertUtils;

/**
 * 规则容器<br/>
 *     系统启动时通过该规则容器加载已有的规则<br/>
 *     可以通过该容器实现规则重加载，添加新的规则<br/>
 * 
 * @author  brady
 * @version  [版本号, 2013-1-23]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class StrategyContext extends StrategyContextBuilder {
    
    /** 基础数据容器实现 */
    protected static StrategyContext context;
    
    /**
      * 获取基础数据容器实例<br/>
      * <功能详细描述>
      * @return [参数说明]
      * 
      * @return BasicDataContext [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public static StrategyContext getContext() {
        if (StrategyContext.context != null) {
            return StrategyContext.context;
        }
        synchronized (StrategyContext.class) {
            StrategyContext.context = applicationContext.getBean(beanName, StrategyContext.class);
        }
        AssertUtils.notNull(StrategyContext.context, "context is null.");
        return StrategyContext.context;
    }
    
    /**
      * 获取对应的策略实现<br/>
      * <功能详细描述>
      * @param strategy
      * @param strategyType
      * @return [参数说明]
      * 
      * @return S [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public <S extends Strategy> S getStrategy(Class<S> strategyType, String strategy) {
        AssertUtils.notNull(strategyType, "strategyType is null.");
        AssertUtils.notEmpty(strategy, "strategy is empty.");
        AssertUtils.isTrue(this.strategyMap.containsKey(strategyType),
                "strategyType:{} is not exist.",
                new Object[] { strategyType });
        AssertUtils.isTrue(this.strategyMap.get(strategyType).containsKey(strategy),
                "strategyType:{} strategy:{} is not exist.",
                new Object[] { strategyType, strategy });
        AssertUtils.isInstanceOf(strategyType,
                this.strategyMap.get(strategyType).get(strategy),
                "strategyType: not match.actualType:{}",
                new Object[] { this.strategyMap.get(strategyType).get(strategy).getClass() });
        
        @SuppressWarnings("unchecked")
        S s = (S) this.strategyMap.get(strategyType).get(strategy);
        
        return s;
    }
}
