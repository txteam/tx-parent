///*
// * 描          述:  <描述>
// * 修  改   人:  Administrator
// * 修改时间:  2014年4月25日
// * <修改描述:>
// */
//package com.tx.component.command.context;
//
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Stack;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.transaction.support.TransactionSynchronizationAdapter;
//import org.springframework.transaction.support.TransactionSynchronizationManager;
//
//import com.tx.core.TxConstants;
//import com.tx.core.exceptions.util.AssertUtils;
//
///**
// * 操作会话容器<br/>
// * 
// * @author  Administrator
// * @version  [版本号, 2014年4月25日]
// * @see  [相关类/方法]
// * @since  [产品/模块版本]
// */
//@Deprecated
//public class RequestSessionContext {
//    
//    /** 日志记录句柄 */
//    private static Logger logger = LoggerFactory.getLogger(RequestSessionContext.class);
//    
//    /** 当前线程中的操作容器实例 */
//    private static ThreadLocal<Stack<RequestSessionContext>> context = new ThreadLocal<Stack<RequestSessionContext>>() {
//        /**
//         * @return
//         */
//        @Override
//        protected Stack<RequestSessionContext> initialValue() {
//            logger.info("ProcessSessionContext: current thread: {} processSessionContext init.");
//            
//            Stack<RequestSessionContext> stack = new Stack<RequestSessionContext>();
//            return stack;
//        }
//        
//        /**
//         * 
//         */
//        @Override
//        public void remove() {
//            logger.info("ProcessSessionContext: current thread: {} processSessionContext remove.");
//            
//            super.remove();
//        }
//    };
//    
//    /**
//     * 打开一个操作请求会话 
//     * <功能详细描述>
//     * @param request
//     * @param response [参数说明]
//     * 
//     * @return void [返回类型说明]
//     * @exception throws [异常类型] [异常说明]
//     * @see [类、类#方法、类#成员]
//    */
//    public static void open(CommandRequest request, CommandResponse response) {
//        //该会话必须在事务中进行执行
//        AssertUtils.isTrue(TransactionSynchronizationManager.isSynchronizationActive(), "必须在事务中进行执行");
//        
//        //获取堆栈
//        Stack<RequestSessionContext> stack = RequestSessionContext.context.get();
//        //如果堆栈中存在原来交易，将堆栈中交易，进行持久后，再将新值进行插入
//        if (!stack.isEmpty()) {
//            RequestSessionContext peek = stack.peek();
//            //如果栈顶存在,判断堆栈顶的session是否为可挂起的
//            AssertUtils.isTrue(!peek.isLockSuspend(), "当前会话不能进行挂起，或已经被挂起。");
//            peek.setLockSuspend(true);
//        } else {
//            TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
//                /**
//                 * @param status
//                 */
//                @Override
//                public void afterCompletion(int status) {
//                    //在事务结束期间保证业务逻辑依然被调用一次
//                    RequestSessionContext.context.remove();
//                }
//            });
//        }
//        
//        //构建新的堆栈
//        RequestSessionContext newSessionContext = new RequestSessionContext(request, response);
//        stack.push(newSessionContext);
//    }
//    
//    /**
//      * 关闭一个操作请求会话<br/> 
//      *<功能详细描述> [参数说明]
//      * 
//      * @return void [返回类型说明]
//      * @exception throws [异常类型] [异常说明]
//      * @see [类、类#方法、类#成员]
//     */
//    public static void close() {
//        Stack<RequestSessionContext> stack = RequestSessionContext.context.get();
//        
//        //将出栈的会话进行清理
//        RequestSessionContext closeProcessSessionContext = stack.pop();
//        closeProcessSessionContext.clear();
//        //如果堆栈顶部仍然存在交易，则对该交易重新进行持久
//        if (stack.isEmpty()) {
//            RequestSessionContext.context.remove();
//        } else {
//            RequestSessionContext peek = stack.peek();
//            peek.setLockSuspend(false);
//        }
//    }
//    
//    /**
//     * 打开一个操作请求会话 
//     * <功能详细描述>
//     * @param request
//     * @param response [参数说明]
//     * 
//     * @return void [返回类型说明]
//     * @exception throws [异常类型] [异常说明]
//     * @see [类、类#方法、类#成员]
//    */
//    public static void lockSuspend() {
//        //该会话必须在事务中进行执行
//        AssertUtils.isTrue(TransactionSynchronizationManager.isSynchronizationActive(), "必须在事务中进行执行");
//        //获取堆栈
//        Stack<RequestSessionContext> stack = RequestSessionContext.context.get();
//        AssertUtils.notNull(stack, "stack is null");
//        AssertUtils.notEmpty(stack, "stack is empty");
//        
//        //取栈顶值
//        RequestSessionContext peek = stack.peek();
//        peek.setLockSuspend(true);
//    }
//    
//    /**
//      * 关闭一个操作请求会话<br/> 
//      *<功能详细描述> [参数说明]
//      * 
//      * @return void [返回类型说明]
//      * @exception throws [异常类型] [异常说明]
//      * @see [类、类#方法、类#成员]
//     */
//    public static void unLockSuspend() {
//        //该会话必须在事务中进行执行
//        AssertUtils.isTrue(TransactionSynchronizationManager.isSynchronizationActive(), "必须在事务中进行执行");
//        //获取堆栈
//        Stack<RequestSessionContext> stack = RequestSessionContext.context.get();
//        AssertUtils.notNull(stack, "stack is null");
//        AssertUtils.notEmpty(stack, "stack is empty");
//        
//        //取栈顶值
//        RequestSessionContext peek = stack.peek();
//        peek.setLockSuspend(false);
//    }
//    
//    /**
//      * 获取当前的操作会话<br/>
//      *<功能详细描述>
//      * @return [参数说明]
//      * 
//      * @return ProcessSessionContext [返回类型说明]
//      * @exception throws [异常类型] [异常说明]
//      * @see [类、类#方法、类#成员]
//     */
//    public static RequestSessionContext getSession() {
//        Stack<RequestSessionContext> stack = RequestSessionContext.context.get();
//        if (stack.isEmpty()) {
//            return null;
//        }
//        RequestSessionContext currentProcessSessionContext = stack.peek();
//        return currentProcessSessionContext;
//    }
//    
//    /** 请求实例 */
//    private CommandRequest request;
//    
//    /** 请求应答实例 */
//    private CommandResponse response;
//    
//    /** 
//     * 锁定挂起功能
//     *    会话如果被挂起（压栈），必须先检查对应的逻辑是被锁定挂起，如果已经被锁定挂起，则抛出异常
//     */
//    private boolean lockSuspend = false;
//    
//    /** 是否已关闭 */
//    private boolean closed;
//    
//    /** 会话中的参数实例 */
//    private Map<String, Object> attributes = new HashMap<>();
//    
//    /** <默认构造函数> */
//    private RequestSessionContext(CommandRequest request, CommandResponse response) {
//        super();
//        //AssertUtils.notNull(response, "response is null.");
//        AssertUtils.notNull(request, "request is null.");
//        
//        this.request = request;
//        this.response = response;
//        attributes = new HashMap<String, Object>(TxConstants.INITIAL_MAP_SIZE);
//    }
//    
//    /**
//      * 将当前操作回话容器清空<br/>
//      * <功能详细描述> [参数说明]
//      * 
//      * @return void [返回类型说明]
//      * @exception throws [异常类型] [异常说明]
//      * @see [类、类#方法、类#成员]
//     */
//    private void clear() {
//        this.request = null;
//        this.response = null;
//        this.attributes = null;
//    }
//    
//    /**
//     * @return 返回 lockSuspend
//     */
//    public boolean isLockSuspend() {
//        return lockSuspend;
//    }
//    
//    /**
//     * @param 对lockSuspend进行赋值
//     */
//    public void setLockSuspend(boolean lockSuspend) {
//        this.lockSuspend = lockSuspend;
//    }
//    
//    /**
//     * @param 对request进行赋值
//     */
//    public void setRequest(CommandRequest request) {
//        this.request = request;
//    }
//    
//    /**
//     * @param 对response进行赋值
//     */
//    public void setResponse(CommandResponse response) {
//        this.response = response;
//    }
//    
//    /**
//     * @return 返回 request
//     */
//    public CommandRequest getRequest() {
//        return request;
//    }
//    
//    /**
//     * @return 返回 response
//     */
//    public CommandResponse getResponse() {
//        return response;
//    }
//    
//    /**
//      * 向线程变量中设置值<br/>
//      * <功能详细描述>
//      * @param key
//      * @param value [参数说明]
//      * 
//      * @return void [返回类型说明]
//      * @exception throws [异常类型] [异常说明]
//      * @see [类、类#方法、类#成员]
//     */
//    public void setAttribute(String key, Object value) {
//        AssertUtils.notEmpty(key, "key is empty.");
//        
//        if (value == null) {
//            return;
//        }
//        if (this.attributes == null) {
//            return;
//        }
//        this.attributes.put(key, value);
//    }
//    
//    /**
//     * 从容器中获取值
//     * <功能详细描述>
//     * @param key
//     * @return [参数说明]
//     * 
//     * @return Object [返回类型说明]
//     * @exception throws [异常类型] [异常说明]
//     * @see [类、类#方法、类#成员]
//    */
//    public Object getAttribute(String key) {
//        AssertUtils.notEmpty(key, "key is empty.");
//        
//        if (this.attributes == null) {
//            return null;
//        }
//        Object value = this.attributes.get(key);
//        return value;
//    }
//    
//    /**
//      * 设置值<br/>
//      * <功能详细描述>
//      * @param key
//      * @param value [参数说明]
//      * 
//      * @return void [返回类型说明]
//      * @exception throws [异常类型] [异常说明]
//      * @see [类、类#方法、类#成员]
//     */
//    public <T> void setValue(String key, T value) {
//        AssertUtils.notEmpty(key, "key is empty.");
//        
//        if (value == null) {
//            return;
//        }
//        if (this.attributes == null) {
//            return;
//        }
//        this.attributes.put(key, value);
//    }
//    
//    /**
//     * 从线程中获取对应的Key值
//     * <功能详细描述>
//     * @param key
//     * @param type
//     * @return [参数说明]
//     * 
//     * @return T [返回类型说明]
//     * @exception throws [异常类型] [异常说明]
//     * @see [类、类#方法、类#成员]
//    */
//    @SuppressWarnings("unchecked")
//    public <T> T getValue(String key, Class<T> type) {
//        AssertUtils.notEmpty(key, "key is empty.");
//        AssertUtils.notNull(type, "type is null.");
//        
//        if (this.attributes == null) {
//            return null;
//        }
//        Object value = this.attributes.get(key);
//        if (value != null) {
//            AssertUtils.isTrue(type.isInstance(value),
//                    "value:{} should be instance of type:{}.",
//                    new Object[] { value, type });
//        }
//        return (T) value;
//    }
//    
//    /**
//      * 获取属性值<br/>
//      * <功能详细描述>
//      * @param key
//      * @param type
//      * @param valueHandler
//      * @return [参数说明]
//      * 
//      * @return T [返回类型说明]
//      * @exception throws [异常类型] [异常说明]
//      * @see [类、类#方法、类#成员]
//     */
//    public <T> T getValueByHandler(String key, Class<T> type, ValueHandler<T> valueHandler) {
//        AssertUtils.notEmpty(key, "key is empty.");
//        AssertUtils.notNull(type, "type is null.");
//        AssertUtils.notNull(valueHandler, "attributeHandle is null.");
//        
//        T value = getValue(key, type);//从线程变量中获取值
//        if (value != null) {
//            return value;
//        }
//        value = valueHandler.getValue();
//        
//        //写入值
//        setValue(key, value);
//        valueHandler.setValue(key, value);
//        
//        return value;
//    }
//    
//    /**
//     * 向线程变量中写入值到对应Map中
//     *     如果该Map没有创建则自动创建并进行写入<br/>
//     * <功能详细描述>
//     * @param key
//     * @param value [参数说明]
//     * 
//     * @return void [返回类型说明]
//     * @exception throws [异常类型] [异常说明]
//     * @see [类、类#方法、类#成员]
//    */
//    public <T> void setValueToMapAttribute(String mapKey, String key, T value) {
//        AssertUtils.notEmpty(mapKey, "mapKey is empty.");
//        AssertUtils.notEmpty(key, "key is empty.");
//        
//        if (value == null) {
//            return;
//        }
//        if (this.attributes == null) {
//            return;
//        }
//        
//        @SuppressWarnings("unchecked")
//        Map<String, T> mapAttribute = (Map<String, T>) this.attributes.get(mapKey);
//        if (mapAttribute == null) {
//            mapAttribute = new HashMap<>();
//            this.attributes.put(mapKey, mapAttribute);
//        }
//        mapAttribute.put(key, value);
//    }
//    
//    /**
//      * 从线程变量中获取值Map中entryKey对应的值<br/>
//      * <功能详细描述>
//      * @param key
//      * @param entryKey
//      * @param type
//      * @return [参数说明]
//      * 
//      * @return T [返回类型说明]
//      * @exception throws [异常类型] [异常说明]
//      * @see [类、类#方法、类#成员]
//     */
//    public <T> T getValueFromMapAttribute(String mapKey, String key, Class<T> type) {
//        AssertUtils.notEmpty(mapKey, "mapKey is empty.");
//        AssertUtils.notEmpty(key, "key is empty.");
//        AssertUtils.notNull(type, "type is null.");
//        
//        if (this.attributes == null) {
//            return null;
//        }
//        @SuppressWarnings("unchecked")
//        Map<String, T> mapAttribute = (Map<String, T>) this.attributes.get(mapKey);
//        if (mapAttribute == null) {
//            return null;
//        }
//        T value = mapAttribute.get(key);
//        if (value != null) {
//            AssertUtils.isTrue(type.isInstance(value),
//                    "value:{} should be instance of type:{}.",
//                    new Object[] { value, type });
//        }
//        return value;
//    }
//    
//    /**
//      * 从类型为Map的Attribute的值中获取对应的值<br/>
//      * <功能详细描述>
//      * @param key
//      * @param type
//      * @param attributeHandle
//      * @param entryKey
//      * @return [参数说明]
//      * 
//      * @return T [返回类型说明]
//      * @exception throws [异常类型] [异常说明]
//      * @see [类、类#方法、类#成员]
//     */
//    public <T> T getValueFromMapAttributeByHandler(String mapKey, String key, Class<T> type,
//            ValueHandler<T> valueHandler) {
//        AssertUtils.notEmpty(mapKey, "mapKey is empty.");
//        AssertUtils.notEmpty(key, "key is empty.");
//        AssertUtils.notNull(type, "type is null.");
//        AssertUtils.notNull(valueHandler, "valueHandler is null.");
//        
//        T value = getValueFromMapAttribute(mapKey, key, type);
//        if (value != null) {
//            return value;
//        }
//        
//        value = valueHandler.getValue();//生成值
//        //写入值
//        setValueToMapAttribute(mapKey, key, value);
//        valueHandler.setValue(key, value);
//        
//        return value;
//    }
//    
//    /**
//     * @return 返回 closed
//     */
//    public boolean isClosed() {
//        return closed;
//    }
//    
//    /**
//     * @param 对closed进行赋值
//     */
//    public void setClosed(boolean closed) {
//        this.closed = closed;
//    }
//    
//    /**
//     * @return 返回 attributes
//     */
//    public Map<String, Object> getAttributes() {
//        return attributes;
//    }
//    
//    /**
//     * @param 对attributes进行赋值
//     */
//    public void setAttributes(Map<String, Object> attributes) {
//        this.attributes = attributes;
//    }
//}
