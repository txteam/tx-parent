/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2016年2月3日
 * <修改描述:>
 */
package com.tx.component.command.context;

import java.util.Collection;
import java.util.Iterator;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.tx.core.exceptions.util.AssertUtils;

/**
 * 辅助类工厂类<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2016年2月3日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class HelperFactory implements ApplicationContextAware {
    
    private static ApplicationContext applicationContext;
    
    /**
     * @param applicationContext
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        HelperFactory.applicationContext = applicationContext;
    }
    
    /**
      * 获取辅助类实例<br/>
      * <功能详细描述>
      * @param type
      * @param args
      * @return [参数说明]
      * 
      * @return H [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public static <H extends Helper<H>> H getHelper(Class<H> type,
            Object... args) {
        AssertUtils.notNull(type, "type is null.");
        
        Collection<H> helperCollections = HelperFactory.applicationContext.getBeansOfType(type)
                .values();
        AssertUtils.notEmpty(helperCollections,
                "helper not exist.type:{}",
                new Object[] { type });
        
        Iterator<H> ite = helperCollections.iterator();
        H helper = null;
        if (helperCollections.size() == 1) {
            helper = ite.next();
        } else {
            while (ite.hasNext() && helper == null) {
                H helperTemp = ite.next();
                if (helperTemp.supports(args)) {
                    helper = helperTemp;
                }
            }
        }
        AssertUtils.notNull(helper,
                "matched helper not exist.type:{}",
                new Object[] { type });
        
        return helper;
    }
}
