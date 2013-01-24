/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-1-24
 * <修改描述:>
 */
package com.tx.component.rule.model;

import java.util.Map;


 /**
  * 规则回话容初始化容器
  * <功能详细描述>
  * 
  * @author  brady
  * @version  [版本号, 2013-1-24]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public class RuleSessionInitContext<T> {
    
    private T fact;
    
    private Map<String, ?> global;

    /**
     * @return 返回 fact
     */
    public T getFact() {
        return fact;
    }

    /**
     * @param 对fact进行赋值
     */
    public void setFact(T fact) {
        this.fact = fact;
    }

    /**
     * @return 返回 global
     */
    public Map<String, ?> getGlobal() {
        return global;
    }

    /**
     * @param 对global进行赋值
     */
    public void setGlobal(Map<String, ?> global) {
        this.global = global;
    }
}
