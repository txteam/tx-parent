/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2018年4月27日
 * <修改描述:>
 */
package com.tx.component.configuration.starter;

/**
 * 基础数据容器默认配置<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2018年4月27日]
 * @see  [相关类/方法]0
 * @since  [产品/模块版本]
 */
//tx.basicdata.config
public class ConfigContextProperties {
    
    /** 表是否自动初始化 */
    private boolean tableAutoInitialize = false;
    
    /** 命令容器是否启动 */
    private boolean enable = true;
    
    /** 持久层属性 */
    private ConfigContextPersisterProperties persister;

    /**
     * @return 返回 tableAutoInitialize
     */
    public boolean isTableAutoInitialize() {
        return tableAutoInitialize;
    }

    /**
     * @param 对tableAutoInitialize进行赋值
     */
    public void setTableAutoInitialize(boolean tableAutoInitialize) {
        this.tableAutoInitialize = tableAutoInitialize;
    }

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
     * @return 返回 persister
     */
    public ConfigContextPersisterProperties getPersister() {
        return persister;
    }

    /**
     * @param 对persister进行赋值
     */
    public void setPersister(ConfigContextPersisterProperties persister) {
        this.persister = persister;
    }
}
