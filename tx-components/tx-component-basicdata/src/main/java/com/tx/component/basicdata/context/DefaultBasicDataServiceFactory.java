/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2016年10月7日
 * <修改描述:>
 */
package com.tx.component.basicdata.context;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

import com.tx.component.basicdata.model.BasicData;
import com.tx.core.exceptions.util.AssertUtils;

/**
 * 默认的基础数据业务类工厂类<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2016年10月7日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class DefaultBasicDataServiceFactory<T extends BasicData> implements
        FactoryBean<BasicDataService<T>>, InitializingBean {
    
    private Class<T> type;
    
    private BasicDataService<T> service;
    
    /**
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("DefaultBasicDataServiceFactory afterPropertiesSet.");
        
        AssertUtils.notNull(type, "type is null.");
        
        this.service = new DefaultBasicDataService<T>(type);
    }
    
    /** <默认构造函数> */
    public DefaultBasicDataServiceFactory() {
        super();
        System.out.println("DefaultBasicDataServiceFactory construct.");
    }
    
    /**
     * @return
     * @throws Exception
     */
    @Override
    public BasicDataService<T> getObject() throws Exception {
        System.out.println("DefaultBasicDataServiceFactory getObject.");
        return this.service;
    }
    
    /**
     * @return
     */
    @Override
    public Class<?> getObjectType() {
        return BasicDataService.class;
    }
    
    /**
     * @return
     */
    @Override
    public boolean isSingleton() {
        return false;
    }
    
    /**
     * @param 对type进行赋值
     */
    public void setType(Class<T> type) {
        this.type = type;
    }
}
