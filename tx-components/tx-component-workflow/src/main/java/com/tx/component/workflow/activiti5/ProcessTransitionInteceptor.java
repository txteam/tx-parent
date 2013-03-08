/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-3-8
 * <修改描述:>
 */
package com.tx.component.workflow.activiti5;

import java.util.Stack;




 /**
  * 处理流转拦截器，用于向当前执行任务线程中写入操作流程变量以及移除流程变量<br/>
  *     使流程处理变量闭环<br/>
  * <功能详细描述>
  * 
  * @author  brady
  * @version  [版本号, 2013-3-8]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public class ProcessTransitionInteceptor {
    
    /**
     * 当前操作
     */
    private static final ThreadLocal<Stack<String>> currentOperation = new ThreadLocal<Stack<String>>(){
        /**
         * @return
         */
        @Override
        protected Stack<String> initialValue() {
            return new Stack<String>();
        }
    };
}
