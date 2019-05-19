/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2014年4月17日
 * <修改描述:>
 */
package com.tx.component.statistical.context;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

//import com.tx.component.event.context.EventContext;

/**
 * 统计报表工厂<br/>
 * <功能详细描述>
 *
 * @author Administrator
 * @version [版本号, 2014年4月17日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Component("statistcalContext")
public class StatisticalContextFactory extends StatisticalContext implements
        FactoryBean<StatisticalContext> {

    /**
     * @return
     * @throws Exception
     */
    @Override
    public StatisticalContext getObject() throws Exception {
        return getContext();
    }

    /**
     * @return
     */
    @Override
    public Class<?> getObjectType() {
        return StatisticalContextFactory.class;
    }

    /**
     * @return
     */
    @Override
    public boolean isSingleton() {
        return true;
    }
}
