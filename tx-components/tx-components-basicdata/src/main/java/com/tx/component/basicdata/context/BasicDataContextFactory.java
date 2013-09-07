/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-8-14
 * <修改描述:>
 */
package com.tx.component.basicdata.context;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;



 /**
  * 基础数据容器工厂<br/>
  * <功能详细描述>
  * 
  * @author  brady
  * @version  [版本号, 2013-8-14]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
@Component("basicDataContext")
public class BasicDataContextFactory extends BasicDataContext implements FactoryBean<BasicDataContext>{
    
    /**
     * @return
     * @throws Exception
     */
    @Override
    public BasicDataContext getObject() throws Exception {
        return null;
    }

    /**
     * @return
     */
    @Override
    public Class<?> getObjectType() {
        return BasicDataContext.class;
    }

    /**
     * @return
     */
    @Override
    public boolean isSingleton() {
        return true;
    }
}
