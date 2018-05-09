package com.tx.component.command.context;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.springframework.core.OrderComparator;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.tx.component.command.exception.ReceiverAccessException;
import com.tx.core.exceptions.SILException;
import com.tx.core.exceptions.util.AssertUtils;

/**
  * 交易容器构建器<br/> 
  * <功能详细描述>
  * 
  * @author  bobby
  * @version  [版本号, 2015年1月6日]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
 */
public class CommandContextBuilder extends CommandContextConfigurator {
    
    /** 操作支持类映射 */
    protected final MultiValueMap<Class<? extends CommandRequest>, CommandReceiver<? extends CommandRequest>> rawType2receiverMap = new LinkedMultiValueMap<>();
    
    /** 操作处理实现支撑对象 */
    protected final Map<Class<? extends CommandRequest>, String> rawType2supportMap = new HashMap<>();
    
    /** 注入感知支撑器实例集合 */
    protected final Set<InjectHandler> injectHandlerSet = new HashSet<>();
    
    /** 注入支撑类 */
    protected final Map<Class<? extends CommandRequest>, Set<InjectHandler>> type2injecthandlerMap = new HashMap<>();
    
    /** 处理器的选择器 */
    protected final List<ReceiverSelector> receiverSelectorList = new ArrayList<>();
    
    /**
     * @throws Exception
     */
    @Override
    protected void doBuild() {
        //加载容器中存在的Support
        logger.info("   开始加载操作接收器支撑处理器......");
        this.rawType2supportMap.putAll(loadRawType2SupportMap());
        if (MapUtils.isEmpty(this.rawType2supportMap)) {
            logger.warn("   加载操作接收器.不存在支撑处理器...");
            return;
        }
        //加载容器中存在的Receiver
        logger.info("   开始加载操作接收器......");
        this.rawType2receiverMap.putAll(loadRawType2ReceiverMap(this.rawType2supportMap));
        if (MapUtils.isEmpty(this.rawType2receiverMap)) {
            logger.warn("   加载操作接收器.不存在接收器...");
            return;
        }
        
        //加载容器中存在的InjectAwareSupport
        logger.info("   开始加注入句柄......");
        this.injectHandlerSet.addAll(loadInjectHandlers());
        
        //加载容器中存在的InjectAwareSupport
        logger.info("   开始加选择器......");
        this.receiverSelectorList.addAll(loadReceiverSelectors());
        Collections.sort(this.receiverSelectorList, OrderComparator.INSTANCE);
    }
    
    /**
     * 加载注入感知支撑器<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return Collection<InjectAwareSupport> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    private Collection<ReceiverSelector> loadReceiverSelectors() {
        Collection<ReceiverSelector> selectors = applicationContext.getBeansOfType(ReceiverSelector.class).values();
        return selectors;
    }
    
    /**
      * 加载注入感知支撑器<br/>
      * <功能详细描述>
      * @return [参数说明]
      * 
      * @return Collection<InjectAwareSupport> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    private Collection<InjectHandler> loadInjectHandlers() {
        Collection<InjectHandler> awareInjectors = applicationContext.getBeansOfType(InjectHandler.class).values();
        return awareInjectors;
    }
    
    /**
      * 获取注入支撑类<br/>
      * <功能详细描述>
      * @param requestType
      * @return [参数说明]
      * 
      * @return Set<InjectAwareSupport> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    private Set<InjectHandler> getInjectorHandlers(Class<? extends CommandRequest> requestType) {
        if (this.type2injecthandlerMap.containsKey(requestType)) {
            return type2injecthandlerMap.get(requestType);
        }
        synchronized (requestType) {
            Set<InjectHandler> supportSet = new HashSet<>();
            for (InjectHandler supportTemp : this.injectHandlerSet) {
                if (!supportTemp.supports(requestType)) {
                    continue;
                }
                supportSet.add(supportTemp);
            }
            type2injecthandlerMap.put(requestType, supportSet);
        }
        return type2injecthandlerMap.get(requestType);
    }
    
    /** 
     * 加载类型与Receiver的映射<br/>
     * <功能详细描述> [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @SuppressWarnings("unchecked")
    private MultiValueMap<Class<? extends CommandRequest>, CommandReceiver<? extends CommandRequest>> loadRawType2ReceiverMap(
            Map<Class<? extends CommandRequest>, String> rawType2supportMap) {
        MultiValueMap<Class<? extends CommandRequest>, CommandReceiver<? extends CommandRequest>> type2receiverMultiMap = null;
        type2receiverMultiMap = new LinkedMultiValueMap<>();
        //加载分类
        @SuppressWarnings("rawtypes")
        Collection<CommandReceiver> receivers = applicationContext.getBeansOfType(CommandReceiver.class).values();
        if (!CollectionUtils.isEmpty(receivers)) {
            //按贷款单类型分类
            for (CommandReceiver<? extends CommandRequest> receiverTemp : receivers) {
                Class<? extends CommandRequest> rawType = receiverTemp.getRequestType();
                AssertUtils.notNull(rawType, "rawType is null.receiver:{}", new Object[] { receiverTemp.getClass() });
                
                //判断其对应的support是否存在
                boolean existSupport = false;
                for (Class<? extends CommandRequest> trType : rawType2supportMap.keySet()) {
                    if (trType.isAssignableFrom(rawType)) {
                        existSupport = true;
                        break;
                    }
                }
                AssertUtils.isTrue(existSupport,
                        "receiver:{}.rawType:{} not matched support.",
                        new Object[] { receiverTemp.getClass(), rawType });
                //类型压入
                type2receiverMultiMap.add(rawType, receiverTemp);
            }
        }
        return type2receiverMultiMap;
    }
    
    /**
      * 加载类型和支撑起映射
      * <功能详细描述>
      * @return [参数说明]
      * 
      * @return Map<Class<? extends TradingRequest>,String> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    @SuppressWarnings("rawtypes")
    private Map<Class<? extends CommandRequest>, String> loadRawType2SupportMap() {
        Map<Class<? extends CommandRequest>, String> type2supportMap = null;
        type2supportMap = new HashMap<>();
        //加载分类
        
        Map<String, RequestSupport> supportsMap = applicationContext.getBeansOfType(RequestSupport.class);
        if (!MapUtils.isEmpty(supportsMap)) {
            //按贷款单类型分类
            for (Entry<String, RequestSupport> entryTemp : supportsMap.entrySet()) {
                String supportBeanName = entryTemp.getKey();
                RequestSupport support = entryTemp.getValue();
                @SuppressWarnings("unchecked")
                Class<? extends CommandRequest> rawType = support.getRequestType();
                AssertUtils.notNull(rawType, "rawType is null.receiver:{}", new Object[] { support.getClass() });
                AssertUtils.isTrue(!type2supportMap.containsKey(rawType),
                        "重复的类型映射:rawType:{} supportBeanName:{}",
                        new Object[] { rawType, supportBeanName });
                //类型压入
                type2supportMap.put(rawType, supportBeanName);
            }
        }
        return type2supportMap;
    }
    
    /**
     * 根据请求获取其对应的Receiver
     * <功能详细描述>
     * @param request
     * @return [参数说明]
     * 
     * @return TradingReceiver<? extends TradingRequest> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    private ReceiverSelector getReceiverSelector(CommandRequest request) {
        AssertUtils.notNull(request, "request is null.");
        if (CollectionUtils.isEmpty(this.receiverSelectorList)) {
            return null;
        }
        
        ReceiverSelector resRS = null;
        for (ReceiverSelector rsTemp : this.receiverSelectorList) {
            if (rsTemp.getRequestType().isInstance(request)) {
                resRS = rsTemp;
                break;
            }
        }
        return resRS;
    }
    
    /**
      * 根据请求获取其对应的Receiver
      * <功能详细描述>
      * @param request
      * @return [参数说明]
      * 
      * @return TradingReceiver<? extends TradingRequest> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    private CommandReceiver<? extends CommandRequest> getReceiver(CommandRequest request,
            ReceiverSelector receiverSelector) {
        AssertUtils.notNull(request, "request is null.");
        Class<? extends CommandRequest> requestType = request.getClass();
        AssertUtils.isTrue(this.rawType2receiverMap.containsKey(requestType),
                "rawType2receiverMap should containsKey:{}",
                new Object[] { requestType });
        List<CommandReceiver<? extends CommandRequest>> receivers = this.rawType2receiverMap.get(requestType);
        AssertUtils.notEmpty(receivers, "请求类型为:{}适配的请求接受处理器不存在.", new Object[] { requestType });
        
        Collections.sort(receivers, OrderComparator.INSTANCE);//默认根据order值进行排序
        CommandReceiver<? extends CommandRequest> receiver = null;
        if (receiverSelector == null) {
            for (CommandReceiver<? extends CommandRequest> rcTemp : receivers) {
                if (rcTemp.supports(request)) {
                    receiver = rcTemp;
                    break;
                }
            }
        } else {
            List<CommandReceiver<? extends CommandRequest>> matchedReceivers = new ArrayList<>();
            for (CommandReceiver<? extends CommandRequest> rcTemp : receivers) {
                if (rcTemp.supports(request)) {
                    matchedReceivers.add(rcTemp);
                }
            }
            receiver = receiverSelector.select(request, matchedReceivers);
        }
        
        AssertUtils.notNull(receiver, "请求类型为:{}适配的请求接受处理器存在，但不存在匹配的处理器.", new Object[] { requestType });
        return receiver;
    }
    
    /**
     * 构建BuildReceiver对应的support 
     *<功能详细描述>
     * @param investProjectRefTypeEnum
     * @param receiver
     * @return [参数说明]
     * 
     * @return RequestSupport [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    @SuppressWarnings({ "rawtypes" })
    private RequestSupport getSupport(CommandRequest request, CommandReceiver<? extends CommandRequest> receiver) {
        AssertUtils.notNull(request, "request is null.");
        Class<? extends CommandRequest> requestType = request.getClass();
        
        String supportName = null;
        for (Entry<Class<? extends CommandRequest>, String> entryTemp : this.rawType2supportMap.entrySet()) {
            Class<? extends CommandRequest> supportRequestType = entryTemp.getKey();
            String beanName = entryTemp.getValue();
            
            if (supportRequestType.isAssignableFrom(requestType)) {
                supportName = beanName;
                break;
            }
        }
        
        AssertUtils.notEmpty(supportName, "support not exist.requestType:{}", new Object[] { requestType });
        
        RequestSupport support = (RequestSupport) applicationContext.getBean(supportName, receiver);
        return support;
    }
    
    //    /**
    //     * 根据操作请求类型获取对应的操作支持实例<br/>
    //     *<功能详细描述>
    //     * @param processRequestType
    //     * @return [参数说明]
    //     * 
    //     * @return ProcessSupport<ProcessRequest> [返回类型说明]
    //     * @exception throws [异常类型] [异常说明]
    //     * @see [类、类#方法、类#成员]
    //    */
    //    @SuppressWarnings("rawtypes")
    //    protected RequestSupport getRequestSupport(CommandRequest request) {
    //        AssertUtils.notNull(request, "request is null.");
    //        
    //        CommandReceiver<? extends CommandRequest> receiver = getReceiver(request);
    //        RequestSupport support = getSupport(request, receiver);
    //        
    //        return support;
    //    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    protected void doPost(final CommandRequest request, final CommandResponse response) {
        AssertUtils.notNull(request, "request is null.");
        AssertUtils.notNull(response, "response is null.");
        
        try {
            this.transactionTemplate.execute(new TransactionCallbackWithoutResult() {
                @Override
                protected void doInTransactionWithoutResult(TransactionStatus status) {
                    //注入并锁定对象
                    inject(request);
                    
                    //获取处理器的选择器
                    ReceiverSelector receiverSelector = getReceiverSelector(request);
                    //获取匹配的请求处理器
                    CommandReceiver<? extends CommandRequest> receiver = getReceiver(request, receiverSelector);
                    //组装请求处理支撑器实例
                    RequestSupport rs = getSupport(request, receiver);
                    
                    rs.handle(request, response);
                }
            });
        } catch (ReceiverAccessException rae) {
            logger.error(rae.getMessage(), rae);
            throw rae;
        } catch (SILException sie) {
            ReceiverAccessException rae = new ReceiverAccessException(sie.getMessage(), sie);
            rae.setErrorCode(sie.getErrorCode());
            throw rae;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new ReceiverAccessException(e.getMessage(), e);
        }
    }
    
    /**
      * 注入交易请求中的信息，并锁定对应的对象<br/>
      * <功能详细描述>
      * @param request [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    protected void inject(CommandRequest request) {
        Set<InjectHandler> injectors = getInjectorHandlers(request.getClass());
        if (CollectionUtils.isEmpty(injectors)) {
            return;
        }
        for (InjectHandler supportTemp : injectors) {
            supportTemp.inject(request);
        }
    }
}
