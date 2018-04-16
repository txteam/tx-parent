/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-12-19
 * <修改描述:>
 */
package com.tx.core.dbscript.context;

import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.core.Ordered;

import com.tx.core.dbscript.TableDefinition;
import com.tx.core.dbscript.XMLTableDefinition;

/**
 * 数据脚本执行容器处理器<br/>
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2013-12-19]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
//已经对执行容器进行了修改，该类不再建议使用
@Deprecated
public class DBScriptExecutorContextProcessor implements
        BeanFactoryPostProcessor,Ordered {
    
    private TableDefinition dbScriptContextTableDefinition = new XMLTableDefinition(
            "classpath:com/tx/core/dbscript/script/dbscript_context_table.xml");
    
    private int order = Ordered.LOWEST_PRECEDENCE - 1;
    
    /**
     * @param beanFactory
     * @throws BeansException
     */
    @Override
    public void postProcessBeanFactory(
            ConfigurableListableBeanFactory beanFactory) throws BeansException {
        Map<String, DBScriptExecutorContext> contextMap = beanFactory.getBeansOfType(DBScriptExecutorContext.class);
        
        if (MapUtils.isEmpty(contextMap)) {
            return;
        }
        //在启动期间将dbScriptContext表进行创建
        for (Entry<String, DBScriptExecutorContext> entryTemp : contextMap.entrySet()) {
            entryTemp.getValue().createOrUpdateTable(dbScriptContextTableDefinition);
        }
    }

    /**
     * @return 返回 order
     */
    public int getOrder() {
        return order;
    }

    /**
     * @param 对order进行赋值
     */
    public void setOrder(int order) {
        this.order = order;
    }
}
