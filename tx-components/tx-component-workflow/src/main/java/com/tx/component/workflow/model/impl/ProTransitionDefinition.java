/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-2-1
 * <修改描述:>
 */
package com.tx.component.workflow.model.impl;


 /**
  * 流程流转定义
  * <功能详细描述>
  * 
  * @author  brady
  * @version  [版本号, 2013-2-1]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public class ProTransitionDefinition {
    
    /** 流程流转定义 */
    private String id;
    
    /** 流程流转定义名 */
    private String name;
    
    /** 流程流转源 */
    private Object source;
    
    /** 流程流转目标 */
    private Object target;
    
    /** 流程任务源目标定义 */
    private ProTaskDefinition sourceTaskDefinition;
    
    /** 流程流转定义 */
    private ProTaskDefinition targetTaskDefinition;
    
    /** 代理的引擎实例 */
    private Object delegate;

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

    /**
     * @return 返回 source
     */
    public Object getSource() {
        return source;
    }

    /**
     * @param 对source进行赋值
     */
    public void setSource(Object source) {
        this.source = source;
    }

    /**
     * @return 返回 target
     */
    public Object getTarget() {
        return target;
    }

    /**
     * @param 对target进行赋值
     */
    public void setTarget(Object target) {
        this.target = target;
    }

    /**
     * @return 返回 sourceTaskDefinition
     */
    public ProTaskDefinition getSourceTaskDefinition() {
        return sourceTaskDefinition;
    }

    /**
     * @param 对sourceTaskDefinition进行赋值
     */
    public void setSourceTaskDefinition(ProTaskDefinition sourceTaskDefinition) {
        this.sourceTaskDefinition = sourceTaskDefinition;
    }

    /**
     * @return 返回 targetTaskDefinition
     */
    public ProTaskDefinition getTargetTaskDefinition() {
        return targetTaskDefinition;
    }

    /**
     * @param 对targetTaskDefinition进行赋值
     */
    public void setTargetTaskDefinition(ProTaskDefinition targetTaskDefinition) {
        this.targetTaskDefinition = targetTaskDefinition;
    }

    /**
     * @return 返回 delegate
     */
    public Object getDelegate() {
        return delegate;
    }

    /**
     * @param 对delegate进行赋值
     */
    public void setDelegate(Object delegate) {
        this.delegate = delegate;
    }
}
