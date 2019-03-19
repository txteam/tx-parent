/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2014年5月24日
 * <修改描述:>
 */
package com.tx.component.task.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.tx.core.support.json.BaseEnum;
import com.tx.core.support.json.BaseEnumJsonSerializer;

/**
 * 事务定义枚举<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2014年5月24日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@JsonSerialize(using = BaseEnumJsonSerializer.class)
public enum TaskStatusEnum implements BaseEnum {
    
    /**
     * 事务容器在被激发后
     *     将会对所有非停止态的事务进行状态更新，将其状态更新为准备执行状态(独立事务处理)
     */
    EXECUTING("EXECUTING", "执行中"),
    
    /**
     * 对应任务已经在事务容器中不存在
     *     该任务在扫描中奖会被忽略
     */
    WAIT_EXECUTE("WAIT_EXECUTE", "待执行");
    
    /** 任务关键字 */
    private final String key;
    
    /** 任务名 */
    private final String name;
    
    /** <默认构造函数> */
    private TaskStatusEnum(String key, String name) {
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
