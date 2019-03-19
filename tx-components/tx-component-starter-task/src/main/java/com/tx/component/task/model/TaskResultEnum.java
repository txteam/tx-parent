/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2014年6月19日
 * <修改描述:>
 */
package com.tx.component.task.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.tx.core.support.json.BaseEnum;
import com.tx.core.support.json.BaseEnumJsonSerializer;

/**
 * 任务执行结果枚举<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2014年6月19日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@JsonSerialize(using = BaseEnumJsonSerializer.class)
public enum TaskResultEnum implements BaseEnum{
    
    /** 未执行完成 */
    UNCOMPLETED("UNCOMPLETED","未执行完成"),
    
    /** 未执行 */
    UNNEED_EXECUTED("UNNEED_EXECUTED","无需执行"),
    
    /** 成功 */
    SUCCESS("SUCCESS","成功"),
    
    /** 失败 */
    FAIL("FAIL","失败");
    
    /** 任务关键字 */
    private final String key;
    
    /** 任务名 */
    private final String name;
    
    /** <默认构造函数> */
    private TaskResultEnum(String key, String name) {
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
