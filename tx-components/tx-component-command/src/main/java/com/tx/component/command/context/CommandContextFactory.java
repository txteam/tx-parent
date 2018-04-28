package com.tx.component.command.context;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

/**
  * 
  * <功能简述>
  * <功能详细描述>
  * 
  * @author  bobby
  * @version  [版本号, 2015年11月30日]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
 */
@Component("commandContext")
public class CommandContextFactory extends CommandContext implements
        FactoryBean<CommandContext> {
    
    /**
     * @return
     * @throws Exception
     */
    @Override
    public CommandContext getObject() throws Exception {
        if (CommandContextFactory.context == null) {
            return this;
        } else {
            return CommandContextFactory.context;
        }
    }
    
    @Override
    public Class<?> getObjectType() {
        return CommandContext.class;
    }
    
    @Override
    public boolean isSingleton() {
        return true;
    }
}
