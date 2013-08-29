/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-8-14
 * <修改描述:>
 */
package com.tx.component.basicdata.context;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.core.Ordered;
import org.springframework.core.PriorityOrdered;

import com.tx.component.basicdata.annotation.BasicDataModel;
import com.tx.core.util.ClassScanUtils;

/**
 * 基础数据容器配置加载<br/>
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2013-8-14]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class BasicDataContextConfigurator implements BeanFactoryPostProcessor,
        PriorityOrdered {
    
    private int order = Ordered.LOWEST_PRECEDENCE - 1;
    
    private String[] packageNames;
    
    /**
     * @return
     */
    @Override
    public int getOrder() {
        return this.order;
    }
    
    /**
     * @param beanFactory
     * @throws BeansException
     */
    @Override
    public void postProcessBeanFactory(
            ConfigurableListableBeanFactory beanFactory) throws BeansException {
        ClassScanUtils.scanByAnnotation(BasicDataModel.class, packageNames);
    }
}
