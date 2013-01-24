/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-1-21
 * <修改描述:>
 */
package com.tx.component.rule.drools;

import org.drools.FactException;
import org.drools.WorkingMemory;


/**
 * 规则工作（运行时）环境回调函数<br/>
 *     在droolsEngine中主要提供该接口，进行环境初始化，用以写入事实<br/>
 *     初始化工作环境的内容<br/>
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2013-1-21]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface WorkingEnvironmentCallback {
    
    /**
      * 初始化工作环境回调
      * <功能详细描述>
      * @param workingMemory
      * @throws FactException [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    void initEnvironment(WorkingMemory workingMemory) throws FactException;
}
