/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2018年4月27日
 * <修改描述:>
 */
package com.tx.core.starter.component;

import com.tx.core.starter.persister.PersisterTypeEnum;

/**
 * 基础数据容器默认配置<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2018年4月27日]
 * @see  [相关类/方法]0
 * @since  [产品/模块版本]
 */
public class ComponentPersisterProperties {
    
    /** 持久化类型 */
    private PersisterTypeEnum type = PersisterTypeEnum.mybatis;
    
    /** 表是否自动初始化 */
    private boolean tableAutoInitialize = false;
    
    /**
     * @return 返回 type
     */
    public PersisterTypeEnum getType() {
        return type;
    }
    
    /**
     * @param 对type进行赋值
     */
    public void setType(PersisterTypeEnum type) {
        this.type = type;
    }

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
}
