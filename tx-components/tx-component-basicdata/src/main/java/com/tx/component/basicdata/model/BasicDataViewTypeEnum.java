/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2016年10月11日
 * <修改描述:>
 */
package com.tx.component.basicdata.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * 基础数据视图类型<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2016年10月11日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@JsonSerialize(using = BasicDataEnumJsonSerializer.class)
public enum BasicDataViewTypeEnum implements BasicDataEnum {
    
    LIST("LIST", "列表"),
    
    PAGEDLIST("LIST", "分页列表"),
    
    TREE("TREE", "树型列表");
    
    private final String key;
    
    private final String name;
    
    /** <默认构造函数> */
    private BasicDataViewTypeEnum(String key, String name) {
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
