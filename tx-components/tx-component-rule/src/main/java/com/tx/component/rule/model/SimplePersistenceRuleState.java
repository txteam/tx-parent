/*
 * 描          述:  <描述>
 * 修  改   人:  PengQingyang
 * 修改时间:  2013-2-27
 * <修改描述:>
 */
package com.tx.component.rule.model;


 /**
  * 规则状态<br/>
  * 
  * @author  PengQingyang
  * @version  [版本号, 2013-2-27]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public enum SimplePersistenceRuleState {
    
    /**
     * 测试态
     */
    TEST("测试态"),
    /**
     * 运营态
     */
    OPERATION("运营态"),
    /**
     * 错误态
     */
    ERROR("错误态"),
    /**
     * 停止态
     */
    STOP("停止态");
    
    /** 规则状态名 */
    private String name;
    
    /**
     * <默认构造函数>
     */
    private SimplePersistenceRuleState(String name) {
        this.name = name;
    }

    /**
     * @return 返回 name
     */
    public String getName() {
        return name;
    }

    /**
     * @param 对name进行赋值
     */
    public void setName(String name) {
        this.name = name;
    }
}
