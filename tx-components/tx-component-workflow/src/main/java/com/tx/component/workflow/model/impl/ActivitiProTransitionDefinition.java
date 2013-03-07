/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-3-5
 * <修改描述:>
 */
package com.tx.component.workflow.model.impl;

import org.activiti.engine.impl.pvm.PvmTransition;

import com.tx.component.workflow.model.ProTaskDefinition;
import com.tx.component.workflow.model.ProTransitionDefinition;


 /**
  * 流程流转定义activiti实现
  * <功能详细描述>
  * 
  * @author  brady
  * @version  [版本号, 2013-3-5]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public class ActivitiProTransitionDefinition implements ProTransitionDefinition{
    
    private PvmTransition pvmTransition;
    
    public ActivitiProTransitionDefinition(PvmTransition pvmTransition) {
        super();
        this.pvmTransition = pvmTransition;
    }

    /**
     * @return
     */
    @Override
    public String getId() {
        return pvmTransition.getId();
    }

    /**
     * @return
     */
    @Override
    public String getName() {
        return (String)pvmTransition.getProperty("name");
    }

    /**
     * @return
     */
    @Override
    public ProTaskDefinition getSource() {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @return
     */
    @Override
    public ProTaskDefinition getDestination() {
        // TODO Auto-generated method stub
        return null;
    }
}
