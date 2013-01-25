/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-1-24
 * <修改描述:>
 */
package com.tx.component.rule.model;

import java.util.Map;


 /**
  * 规则回话容初始化容器<br>
  * 主要含有，事实与全局变量<br/>
  * 
  * 
  * @author  brady
  * @version  [版本号, 2013-1-24]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public class RuleSessionInitContext<T> {
    
    /** 规则执行时的，事实 */
    private T fact;
    
    /** 规则执行时的全局参量 */
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
