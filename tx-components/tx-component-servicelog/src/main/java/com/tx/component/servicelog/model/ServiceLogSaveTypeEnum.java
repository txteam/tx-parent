/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2018年5月11日
 * <修改描述:>
 */
package com.tx.component.servicelog.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.tx.core.support.json.BaseEnum;
import com.tx.core.support.json.BaseEnumJsonSerializer;

/**
 * 业务日志存储类型<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2018年5月11日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@JsonSerialize(using = BaseEnumJsonSerializer.class)
public enum ServiceLogSaveTypeEnum implements BaseEnum{
    
    DATASOURCE("DATASOURCE","数据库"),
    
    FILE("","");
    
    private final String key;
    
    private final String name;

    /** <默认构造函数> */
    private ServiceLogSaveTypeEnum(String key, String name) {
        this.key = key;
        this.name = name;
    }

    /**
     * @return 返回 key
     */
    public String getKey() {
        return key;
    }

    /**
     * @return 返回 name
     */
    public String getName() {
        return name;
    }
}
