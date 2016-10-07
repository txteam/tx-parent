/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2016年10月4日
 * <修改描述:>
 */
package com.tx.component.basicdata.context;

import com.tx.core.exceptions.util.AssertUtils;

/**
 * 基础数据容器<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2016年10月4日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class BasicDataContext extends BasicDataContextBuilder {
    
    protected static BasicDataContext context;
    
    /**
      * 获取基础数据容器实例<br/>
      * <功能详细描述>
      * @return [参数说明]
      * 
      * @return BasicDataContext [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public static BasicDataContext getContext() {
        AssertUtils.notNull(BasicDataContext.context, "context not inited.");
        
        return BasicDataContext.context;
    }
    
    /**
     * @throws Exception
     */
    @Override
    protected final void doInitContext() throws Exception {
        //限定容器有且只能被初始化一次
        AssertUtils.isNull(BasicDataContext.context, "context already inited.");
        
        BasicDataContext.context = this;
    }
    
}
