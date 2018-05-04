package com.tx.component.command.context;

import java.io.Serializable;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import com.tx.component.command.context.response.DefaultResponse;
import com.tx.core.exceptions.util.AssertUtils;

/**
  * 交易容器<br/>
  * <功能详细描述>
  * 
  * @author  bobby
  * @version  [版本号, 2015年11月30日]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
 */
public class CommandContext extends CommandContextBuilder {
    
    /** 交易容器 */
    protected static CommandContext context;
    
    /**
     * @return 返回 applynoteContext
     */
    public static CommandContext getContext() {
        if (CommandContext.context != null) {
            return context;
        }
        synchronized (CommandContext.class) {
            CommandContext.context = (CommandContext) applicationContext.getBean(beanName);
        }
        AssertUtils.notNull(context, "context is null.maybe not inited.");
        return context;
    }
    
    /**
     * 根据操作请求触发操作<br/>
     * @param processRequest [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Transactional
    public CommandResponse post(CommandRequest request) {
        AssertUtils.notNull(request, "request is null.");
        CommandResponse response = new DefaultResponse();
        
        doPost(request, response);
        
        return response;
    }
    
    /**
     * 根据操作请求触发操作<br/>
     * @param processRequest [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    @Transactional
    public void post(CommandRequest request, CommandResponse response) {
        AssertUtils.notNull(request, "request is null.");
        AssertUtils.notNull(response, "response is null.");
        
        doPost(request, response);
    }
    
    /**
     * 根据操作请求触发操作<br/>
     * @param processRequest [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Transactional
    public void notify(final CommandRequest request) {
        AssertUtils.notNull(request, "request is null.");
        AssertUtils.isTrue(request instanceof Serializable,
                "request should is Serializable instance.");
        
        //FIXME:插入数据
        
        //在事务执行成功提交以后，执行notify的实际逻辑
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
            @Override
            public void afterCommit() {
                CommandResponse response = new DefaultResponse();
                doPost(request, response);
                
                //FIXME: 如果执行成功，在此处执行更新为成功，并迁移至历史表的逻辑
                
                //FIXME: 否则，则记录为错误
            }
        });
    }
}
