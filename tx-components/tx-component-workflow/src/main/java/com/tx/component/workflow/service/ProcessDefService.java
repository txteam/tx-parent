/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-1-17
 * <修改描述:>
 */
package com.tx.component.workflow.service;


 /**
  * 流程定义业务层<br/>
  * 1、用以封闭底层引擎实现接口，为未来可能的流程变更以及扩展提供可能<br/>
  * 2、主要包含流程文件的解析，以及流程定义自定义扩展的服务<br/>
  *     a、提供通过导入的流程定义文件，解析生成流程<br/>
  *     b、提供启动时检查是否需要加载新的流程定义，通过版本<br/>
  *     c、通过该定义决定启动时加载哪些流程<br/>
  * 
  * @author  brady
  * @version  [版本号, 2013-1-17]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public interface ProcessDefService {
    
    
}
