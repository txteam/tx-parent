/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2016年11月10日
 * <修改描述:>
 */
package com.tx.core.ddlutil.model;

/**
 * 约束类型<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2016年11月10日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public enum ConstraintTypeEnum {
    
    FOREIGN_KEY("FOREIGN_KEY","外键约束"), 
    
    PRIMARY_KEY("PRIMARY_KEY","主键约束"), 
    
    UNIQUE("UNIQUE","唯一键约束");
    
    private final String key;
    
    private final String name;

    /** <默认构造函数> */
    private ConstraintTypeEnum(String key, String name) {
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
