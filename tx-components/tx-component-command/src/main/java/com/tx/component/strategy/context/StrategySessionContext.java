/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2014年4月25日
 * <修改描述:>
 */
package com.tx.component.strategy.context;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import com.tx.core.TxConstants;
import com.tx.core.exceptions.util.AssertUtils;

/**
 * 操作会话容器<br/>
 * 
 * @author  Administrator
 * @version  [版本号, 2014年4月25日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class StrategySessionContext {
    
    /** 日志记录句柄 */
    private static Logger logger = LoggerFactory.getLogger(StrategySessionContext.class);
    
    /** 当前线程中的操作容器实例 */
    private static ThreadLocal<Stack<StrategySessionContext>> context = new ThreadLocal<Stack<StrategySessionContext>>() {
        /**
         * @return
         */
        @Override
        protected Stack<StrategySessionContext> initialValue() {
            logger.info("ProcessSessionContext: current thread: {} processSessionContext init.");
            
            Stack<StrategySessionContext> stack = new Stack<StrategySessionContext>();
            return stack;
        }
        
        /**
         * 
         */
        @Override
        public void remove() {
            logger.info("ProcessSessionContext: current thread: {} processSessionContext remove.");
            super.remove();
        }
    };
    
    /**
     * 打开一个操作请求会话 
     * <功能详细描述>
     * @param request
     * @param response [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    public static void open(Object[]... args) {
        //该会话必须在事务中进行执行
        AssertUtils.isTrue(TransactionSynchronizationManager.isSynchronizationActive(), "必须在事务中进行执行");
        
        //获取堆栈
        Stack<StrategySessionContext> stack = StrategySessionContext.context.get();
        //如果堆栈中存在原来交易，将堆栈中交易，进行持久后，再将新值进行插入
        if (stack.isEmpty()) {
            TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
                /**
                 * @param status
                 */
                @Override
                public void afterCompletion(int status) {
                    //在事务结束期间保证业务逻辑依然被调用一次
                    StrategySessionContext.context.remove();
                }
            });
        }
        
        //构建新的堆栈
        StrategySessionContext newSessionContext = new StrategySessionContext(args);
        stack.push(newSessionContext);
    }
    
    /**
      * 关闭一个操作请求会话<br/> 
      *<功能详细描述> [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public static void close() {
        Stack<StrategySessionContext> stack = StrategySessionContext.context.get();
        //将出栈的会话进行清理
        StrategySessionContext closeProcessSessionContext = stack.pop();
        closeProcessSessionContext.clear();
        //如果堆栈顶部仍然存在交易，则对该交易重新进行持久
        if (stack.isEmpty()) {
            StrategySessionContext.context.remove();
        }
    }
    
    /**
      * 获取当前的操作会话<br/>
      *<功能详细描述>
      * @return [参数说明]
      * 
      * @return ProcessSessionContext [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public static StrategySessionContext getSession() {
        Stack<StrategySessionContext> stack = StrategySessionContext.context.get();
        if (stack.isEmpty()) {
            return null;
        }
        StrategySessionContext currentProcessSessionContext = stack.peek();
        return currentProcessSessionContext;
    }
    
    /** 请求实例 */
    private Object[] args;
    
    /** 会话中的参数实例 */
    private Map<String, Object> attributes = new HashMap<>();
    
    /** <默认构造函数> */
    private StrategySessionContext(Object[] args) {
        super();
        
        this.args = args;
        attributes = new HashMap<String, Object>(TxConstants.INITIAL_MAP_SIZE);
    }
    
    /**
      * 将当前操作回话容器清空<br/>
      * <功能详细描述> [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    private void clear() {
        this.args = null;
        this.attributes = null;
    }
    
    /**
     * @return 返回 args
     */
    public Object[] getArgs() {
        return args;
    }

    /**
     * @param 对args进行赋值
     */
    public void setArgs(Object[] args) {
        this.args = args;
    }

    /**
      * 设置值<br/>
      * <功能详细描述>
      * @param key
      * @param value [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public <T> void setValue(String key, T value) {
        AssertUtils.notEmpty(key, "key is empty.");
        
        if (value == null) {
            return;
        }
        if (this.attributes == null) {
            return;
        }
        this.attributes.put(key, value);
    }
    
    /**
     * 从线程中获取对应的Key值
     * <功能详细描述>
     * @param key
     * @param type
     * @return [参数说明]
     * 
     * @return T [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    @SuppressWarnings("unchecked")
    public <T> T getValue(String key, Class<T> type) {
        AssertUtils.notEmpty(key, "key is empty.");
        AssertUtils.notNull(type, "type is null.");
        
        if (this.attributes == null) {
            return null;
        }
        Object value = this.attributes.get(key);
        if (value != null) {
            AssertUtils.isTrue(type.isInstance(value),
                    "value:{} should be instance of type:{}.",
                    new Object[] { value, type });
        }
        return (T) value;
    }
}
