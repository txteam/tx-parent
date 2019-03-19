/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2016年2月3日
 * <修改描述:>
 */
package com.tx.component.command.context;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 抽象的辅助类<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2016年2月3日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class AbstractHelper<H extends Helper<H>> implements Helper<H>,
        ApplicationContextAware, BeanNameAware {
    
    protected String beanName;
    
    protected ApplicationContext applicationContext;
    
    /**
     * @param name
     */
    @Override
    public void setBeanName(String name) {
        this.beanName = name;
    }
    
    /**
     * @param applicationContext
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        this.applicationContext = applicationContext;
    }
    
    /**
     * @return
     */
    @Override
    public final H getHelper() {
        @SuppressWarnings("unchecked")
        H helper = (H) this.applicationContext.getBean(this.beanName);
        return helper;
    }
    
    /**
     * @param params
     * @return
     */
    @Override
    public boolean supports(Object... params) {
        return true;
    }
    
    @SuppressWarnings("unchecked")
    protected <T> T getParam(Object[] params, int index, Class<T> type) {
        if (ArrayUtils.isEmpty(params)) {
            return null;
        }
        if (index > params.length - 1) {
            return null;
        }
        Object resObject = params[index];
        if (type.isInstance(resObject)) {
            return (T) resObject;
        } else {
            return null;
        }
    }
}
