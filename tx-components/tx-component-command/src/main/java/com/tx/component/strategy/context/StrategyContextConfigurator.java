/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2014年3月16日
 * <修改描述:>
 */
package com.tx.component.strategy.context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

/**
 * 规则容器配置器<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2014年3月16日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class StrategyContextConfigurator implements InitializingBean {
    
    /** 日志记录器 */
    protected final static Logger logger = LoggerFactory.getLogger(StrategyContext.class);
    
    /**
     * @throws Exception
     */
    @Override
    public final void afterPropertiesSet() throws Exception {
        //进行容器构建
        doBuild();
        
        //初始化容器
        doInitContext();
    }
    
    /**
     * 基础数据容器构建
     * <功能详细描述>
     * @throws Exception [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    protected void doBuild() throws Exception {
        
    }
    
    /**
      * 初始化容器<br/>
      * <功能详细描述>
      * @throws Exception [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    protected void doInitContext() throws Exception {
        
    }
    
}
