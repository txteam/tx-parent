/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2016年8月7日
 * <修改描述:>
 */
package com.tx.core.support.entrysupport.model;

import java.io.Serializable;

import javax.persistence.Id;

import com.tx.core.jdbc.sqlsource.annotation.QueryConditionEqual;
import com.tx.core.jdbc.sqlsource.annotation.UpdateAble;

/**
 * 实体属性分项<br/>
 *     该对象设计为：为基础数据类：即不会经常变动的实体
 *     提供动态属性功能支撑
 *     key:value 的映射，用于支撑一对多的属性写入
 *     一般来说，如果非基础数据，与业务耦合较高的类，建议自定义自己的实现
 *     此封装一般用于扩展：比如区域，银行等基础数据实体，需要动态增加属性的基础数据上<br/>
 *     由于该类的的Value主实现一般直接用于映射String类型的值，
 *     现阶段一般适用于扩展基础数据，数据字典类的实现<br/>
 * 
 * @author  Administrator
 * @version  [版本号, 2016年8月7日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class EntityEntry implements Serializable {
    
    /** 注释内容 */
    private static final long serialVersionUID = 7375322166119051390L;
    
    /** 分项属性id */
    @Id
    private String id;
    
    /** 实体id */
    @QueryConditionEqual
    private String entityId;
    
    /** 分项key值 */
    @QueryConditionEqual
    private String entryKey;
    
    /** 分项value值 */
    @UpdateAble
    private String entryValue;
    
    /** <默认构造函数> */
    public EntityEntry() {
        super();
    }
    
    /** <默认构造函数> */
    public EntityEntry(String entryKey, String entryValue) {
        super();
        this.entryKey = entryKey;
        this.entryValue = entryValue;
    }
    
    /**
     * @return 返回 id
     */
    public String getId() {
        return id;
    }
    
    /**
     * @param 对id进行赋值
     */
    public void setId(String id) {
        this.id = id;
    }
    
    /**
     * @return 返回 entityId
     */
    public String getEntityId() {
        return entityId;
    }
    
    /**
     * @param 对entityId进行赋值
     */
    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }
    
    /**
     * @return 返回 entryKey
     */
    public String getEntryKey() {
        return entryKey;
    }
    
    /**
     * @param 对entryKey进行赋值
     */
    public void setEntryKey(String entryKey) {
        this.entryKey = entryKey;
    }
    
    /**
     * @return 返回 entryValue
     */
    public String getEntryValue() {
        return entryValue;
    }
    
    /**
     * @param 对entryValue进行赋值
     */
    public void setEntryValue(String entryValue) {
        this.entryValue = entryValue;
    }
}
