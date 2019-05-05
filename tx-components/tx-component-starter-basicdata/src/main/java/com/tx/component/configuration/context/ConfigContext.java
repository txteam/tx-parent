/*
 * 描          述:  <描述>
 * 修  改   人:  wanxin
 * 修改时间:  2013-8-8
 * <修改描述:>
 */
package com.tx.component.configuration.context;

import com.tx.component.configuration.model.ConfigProperty;
import com.tx.core.exceptions.util.AssertUtils;

/**
 * 配置容器<br/>
 * 
 * @author wanxin
 * @version [版本号, 2013-8-8]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class ConfigContext extends ConfigContextBuilder {
    
    /** 配置容器的唯一实例 */
    protected static ConfigContext context;
    
    /** 配置容器构造函数 */
    protected ConfigContext() {
        super();
    }
    
    /**
     * @throws Exception
     */
    @Override
    protected void doInitContext() throws Exception {
        
    }
    
    /**
     * 获取配置容器的唯一实例
     * 
     * @return ConfigContext [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static ConfigContext getContext() {
        if (ConfigContext.context != null) {
            return ConfigContext.context;
        }
        synchronized (ConfigContext.class) {
            ConfigContext.context = applicationContext.getBean(beanName,
                    ConfigContext.class);
        }
        AssertUtils.notNull(ConfigContext.context, "context is null.");
        
        return ConfigContext.context;
    }
    
    /**
     * 根据code获取对应的配置属性实例
     * @param code
     * 
     * @return ConfigProperty [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public ConfigProperty find(String code) {
        AssertUtils.notEmpty(code, "code is empty.");
        
        ConfigProperty p = doFind(this.module, code);
        return p;
    }
    
    /**
     * 根据code获取对应的配置属性实例
     * @param module
     * @param code
     * 
     * @return ConfigProperty [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public ConfigProperty find(String module, String code) {
        AssertUtils.notEmpty(module, "module is empty.");
        AssertUtils.notEmpty(code, "code is empty.");
        
        ConfigProperty p = doFind(module, code);
        return p;
    }
}
