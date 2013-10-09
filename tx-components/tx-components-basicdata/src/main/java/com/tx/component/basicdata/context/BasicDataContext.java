/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-8-14
 * <修改描述:>
 */
package com.tx.component.basicdata.context;

import org.springframework.beans.factory.InitializingBean;

import com.tx.core.exceptions.util.AssertUtils;

/**
 * 基础数据容器<br/>
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2013-8-14]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class BasicDataContext extends BasicDataExecutorFactory implements
        InitializingBean {
    
    private static BasicDataContext context = null;
    
    protected BasicDataContext() {
        super();
    }
    
    /**
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        BasicDataContext.context = this;
    }
    
    /**
      * 基础数据容器单例<br/>
      *<功能详细描述>
      * @return [参数说明]
      * 
      * @return BasicDataContext [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public static BasicDataContext getContext() {
        AssertUtils.notNull(BasicDataContext.context,
                "BasicDataContext.context is null.");
        
        return BasicDataContext.context;
    }
    
}
