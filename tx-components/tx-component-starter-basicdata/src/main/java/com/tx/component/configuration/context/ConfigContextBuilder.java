/**
  * 文 件 名:  ConfigContext.java
 * 版    权:  TX Workgroup . Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  PengQingyang
 * 修改时间:  2012-10-5
 * <修改描述:>
 */
package com.tx.component.configuration.context;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tx.component.configuration.model.ConfigProperty;

/**
 * 配置容器基础配置吃撑类<br/>
 * 用以加载系统配置，支持动态加载系统中各配置<br/>
 * 
 * @author PengQingyang
 * @version [版本号, 2012-10-5]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class ConfigContextBuilder extends ConfigContextConfigurator {
    
    /** 日志记录器 */
    private static Logger logger = LoggerFactory
            .getLogger(ConfigContextBuilder.class);
    
    /** 配置属性持久器集合 */
    protected List<ConfigPropertyFinder> configPropertyFinderList;
    
    /**
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        
    }
    
    /**
     * 根据code获取配置属性<br/>
     * <功能详细描述>
     * @param code
     * @return [参数说明]
     * 
     * @return ConfigProperty [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    protected ConfigProperty doFind(String code) {
        return null;
    }
    
    protected ConfigProperty doFind(String module, String code) {
        return null;
    }
    
    protected List<ConfigProperty> doQuery(String module, String code) {
        return null;
    }
}
