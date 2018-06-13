/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2018年5月1日
 * <修改描述:>
 */
package com.tx.component.event.starter;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
  * 命令容器配置<br/>
  * <功能详细描述>
  * 
  * @author  Administrator
  * @version  [版本号, 2018年5月1日]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
@ConfigurationProperties(prefix = "tx.event")
public class EventContextProperties {
    
    /** 命令容器是否启动 */
    private boolean enable;
    
    /** 事务管理器bean名称 */
    private String transactionManager;
    
    /** 事件监听工厂bean名称 */
    private String eventListenerSupportFactory;
    
    /**
     * @return 返回 enable
     */
    public boolean isEnable() {
        return enable;
    }
    
    /**
     * @param 对enable进行赋值
     */
    public void setEnable(boolean enable) {
        this.enable = enable;
    }
    
    /**
     * @return 返回 transactionManager
     */
    public String getTransactionManager() {
        return transactionManager;
    }
    
    /**
     * @param 对transactionManager进行赋值
     */
    public void setTransactionManager(String transactionManager) {
        this.transactionManager = transactionManager;
    }
    
    /**
     * @return 返回 eventListenerSupportFactory
     */
    public String getEventListenerSupportFactory() {
        return eventListenerSupportFactory;
    }
    
    /**
     * @param 对eventListenerSupportFactory进行赋值
     */
    public void setEventListenerSupportFactory(
            String eventListenerSupportFactory) {
        this.eventListenerSupportFactory = eventListenerSupportFactory;
    }
}
