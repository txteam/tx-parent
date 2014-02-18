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
import org.springframework.beans.factory.InitializingBean;

import com.tx.component.configuration.persister.ConfigPropertiesPersister;

/**
 * 配置容器基础配置吃撑类<br/>
 * 用以加载系统配置，支持动态加载系统中各配置<br/>
 * 
 * @author PengQingyang
 * @version [版本号, 2012-10-5]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class ConfigContextConfigurator implements InitializingBean {
    
    /** 日志记录器 */
    private static Logger logger = LoggerFactory.getLogger(ConfigContextConfigurator.class);
    
    /** 是否处于开发模式  开发模式中 getValue 将优先获取 developValue */
    private boolean isDevelop = false;
    
    /** 配置是否可重复 */
    private boolean repeatAble = false;
    
    /** 配置属性持久器集合 */
    protected List<ConfigPropertiesPersister> configPropertiesPersisterList;
    
    /**
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        logger.info("configContext init.\n\t isDevelop:{} \n\t repeatAble:{}",
                new Object[] { isDevelop, repeatAble });
    }
    
    /**
     * @return 返回 isDevelop
     */
    public boolean isDevelop() {
        return isDevelop;
    }
    
    /**
     * @param 对isDevelop进行赋值
     */
    public void setDevelop(boolean isDevelop) {
        this.isDevelop = isDevelop;
    }
    
    /**
     * @return 返回 repeatAble
     */
    public boolean isRepeatAble() {
        return repeatAble;
    }
    
    /**
     * @param 对repeatAble进行赋值
     */
    public void setRepeatAble(boolean repeatAble) {
        this.repeatAble = repeatAble;
    }
    
    /**
     * @return 返回 configPropertiesPersisterList
     */
    public List<ConfigPropertiesPersister> getConfigPropertiesPersisterList() {
        return configPropertiesPersisterList;
    }
    
    /**
     * @param 对configPropertiesPersisterList进行赋值
     */
    public void setConfigPropertiesPersisterList(
            List<ConfigPropertiesPersister> configPropertiesPersisterList) {
        this.configPropertiesPersisterList = configPropertiesPersisterList;
    }
}
