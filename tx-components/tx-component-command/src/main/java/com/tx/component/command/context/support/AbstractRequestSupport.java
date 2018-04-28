package com.tx.component.command.context.support;

import org.slf4j.helpers.MessageFormatter;
import org.springframework.transaction.annotation.Transactional;

import com.tx.component.command.context.CommandReceiver;
import com.tx.component.command.context.CommandRequest;
import com.tx.component.command.context.CommandResponse;
import com.tx.component.command.context.CommandSessionContext;
import com.tx.component.command.context.RequestSupport;
import com.tx.component.command.context.response.DefaultResponse;
import com.tx.component.command.exception.ReceiverAccessException;
import com.tx.core.exceptions.SILException;
import com.tx.core.exceptions.util.AssertUtils;

/**
 * 抽象请求支撑类<br/>
 * <功能详细描述>
 *
 * @author bobby
 * @version [版本号, 2015年11月30日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public abstract class AbstractRequestSupport<PR extends CommandRequest, RE extends CommandReceiver<PR>>
        extends RequestSupport<PR, RE> {

    /**
     * <默认构造函数>
     */
    protected AbstractRequestSupport() {
        super();
    }

    /**
     * <默认构造函数>
     */
    public AbstractRequestSupport(RE receiver) {
        super(receiver);
        AssertUtils.notNull(receiver, "receiver is null.");
    }

    /**
     * 请求处理支撑器<br/>
     *
     * @param request
     * @param response
     */
    @Override
    @Transactional
    public final void handle(PR request, CommandResponse response) {
        //判断入参的合法性
        AssertUtils.notNull(request, "request is null.");
        if (response == null) {
            response = new DefaultResponse();
        }

        boolean flag = preHandle(request, response);
        if (!flag) {
            return;
        }
        Throwable tae = null;

        //开启会话实例<br/>
        CommandSessionContext.open(request, response);
        //异常接收器
        try {
            //实际贷款账户操作处理逻辑
            try {
                if (isLockWhenHandle(request, response)) {
                    //锁定（处理期间是否需要锁定）
                    CommandSessionContext.lockSuspend();
                }

                doHandle(request, response);
            } finally {
                if (isLockWhenHandle(request, response)) {
                    //无论如何都需要进行解锁
                    CommandSessionContext.unLockSuspend();
                }
            }

            afterHandle(request, response);
        } catch (SILException ex) {
            tae = ex;
            throw ex;
        } catch (Exception ex) {
            logger.error("ReceiverAccessException:命令容器调用异常.request:{} , receiver: {} ",
                    new Object[]{request, this.receiver},
                    ex);
            tae = ex;
            throw new ReceiverAccessException(
                    MessageFormatter.format("命令容器调用异常.request:{} ,receiver: {} ,异常信息:{}",
                            new Object[]{request, this.receiver, ex.getMessage()})
                            .getMessage(), ex);
        } finally {
            CommandSessionContext.close();

            //在线程变量关闭后再去调用afterCompletion的逻辑
            afterCompletion(request, response, tae);
        }
    }

    /**
     * 前置处理逻辑<br/>
     * <功能详细描述>
     *
     * @param request
     * @param response
     * @return boolean [返回类型说明]
     * @throws throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    protected boolean preHandle(PR request, CommandResponse response) {
        logger.info("===> 【receiver】:" + this.receiver.getClass() + "   【request】: " + request);
        //会话关闭后执行逻辑
        boolean flag = this.receiver.preHandle(request, response);
        return flag;
    }

    /**
     * <功能详细描述>
     *
     * @param request
     * @param response [参数说明]
     * @return void [返回类型说明]
     * @throws throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    protected abstract void doHandle(PR request, CommandResponse response);

    /**
     * 后置处理逻辑<br/>
     * <功能详细描述>
     *
     * @param request
     * @param response [参数说明]
     * @return void [返回类型说明]
     * @throws throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    protected void afterHandle(PR request, CommandResponse response) {
    }

    /**
     * 处理期间是否进行锁定
     * <功能详细描述>
     *
     * @param request
     * @param response
     * @return void [返回类型说明]
     * @throws throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    protected boolean isLockWhenHandle(PR request, CommandResponse response) {
        return false;
    }

    /**
     * 后置完成处理逻辑<br/>
     * <功能详细描述>
     *
     * @param request
     * @param response
     * @param tae      [参数说明]
     * @return void [返回类型说明]
     * @throws throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    protected void afterCompletion(PR request, CommandResponse response,
                                   Throwable tae) {
        //会话关闭后执行逻辑
        this.receiver.afterCompletion((PR) request, response, tae);
    }
}
