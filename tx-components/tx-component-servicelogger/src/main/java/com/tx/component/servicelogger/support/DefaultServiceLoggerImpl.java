/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2018年6月9日
 * <修改描述:>
 */
package com.tx.component.servicelogger.support;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.mybatis.model.Order;
import com.tx.core.mybatis.support.MyBatisDaoSupport;
import com.tx.core.paged.model.PagedList;

/**
 * 默认的实体自动持久层<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2018年6月9日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class DefaultServiceLoggerImpl<T> implements ServiceLogger<T> {
    
    /** bean类型 */
    private Class<T> beanType;
    
    /** mybatis句柄 */
    private MyBatisDaoSupport myBatisDaoSupport;
    
    /** 事务模板句柄 */
    private TransactionTemplate transactionTemplate;
    
    /** 实体Mpper构建辅助类 */
    private LoggerMapperBuilderAssistant assistant;
    
    /** <默认构造函数> */
    public DefaultServiceLoggerImpl(Class<T> beanType,
            MyBatisDaoSupport myBatisDaoSupport,
            TransactionTemplate transactionTemplate,
            LoggerMapperBuilderAssistant assistant) {
        super();
        AssertUtils.notNull(beanType, "beanType is null.");
        AssertUtils.notNull(myBatisDaoSupport, "myBatisDaoSupport is null.");
        AssertUtils.notNull(transactionTemplate,
                "transactionTemplate is null.");
        AssertUtils.notNull(assistant, "assistant is null.");
        
        this.beanType = beanType;
        this.myBatisDaoSupport = myBatisDaoSupport;
        this.transactionTemplate = transactionTemplate;
        this.assistant = assistant;
    }
    
    /**
     * @return
     */
    @Override
    public Type getLoggerType() {
        return this.beanType;
    }
    
    /**
     * @param condition
     */
    @Override
    public void batchInsert(final List<T> objectList) {
        this.transactionTemplate
                .execute(new TransactionCallbackWithoutResult() {
                    
                    @Override
                    protected void doInTransactionWithoutResult(
                            TransactionStatus status) {
                        doBatchInsert(objectList);
                    }
                });
    }
    
    /**
     * 批量插入<br/>
     * <功能详细描述>
     * @param objectList [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    private void doBatchInsert(List<T> objectList) {
        List<String> primaryPropertyList = this.assistant
                .getPrimaryProperyNameList();
        if (CollectionUtils.isEmpty(primaryPropertyList)
                || primaryPropertyList.size() > 1) {
            this.myBatisDaoSupport.batchInsert(
                    this.assistant.getInsertStatementName(), objectList, true);
        } else {
            this.myBatisDaoSupport.batchInsertUseUUID(
                    this.assistant.getInsertStatementName(),
                    objectList,
                    primaryPropertyList.get(0),
                    true);
        }
    }
    
    /**
     * @param condition
     */
    @Override
    public void insert(T condition) {
        this.transactionTemplate
                .execute(new TransactionCallbackWithoutResult() {
                    
                    @Override
                    protected void doInTransactionWithoutResult(
                            TransactionStatus status) {
                        doInsert(condition);
                    }
                });
    }
    
    /**
     * 插入数据<br/>
     * <功能详细描述>
     * @param condition [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    private void doInsert(T condition) {
        List<String> primaryPropertyList = this.assistant
                .getPrimaryProperyNameList();
        if (CollectionUtils.isEmpty(primaryPropertyList)
                || primaryPropertyList.size() > 1) {
            this.myBatisDaoSupport
                    .insert(this.assistant.getInsertStatementName(), condition);
        } else {
            this.myBatisDaoSupport.insertUseUUID(
                    this.assistant.getInsertStatementName(),
                    condition,
                    primaryPropertyList.get(0));
        }
    }
    
    /**
     * @param condition
     * @return
     */
    @Override
    public T find(T condition) {
        return this.myBatisDaoSupport
                .find(this.assistant.getFindStatementName(), condition);
    }
    
    /**
     * @param params
     * @return
     */
    @Override
    public List<T> queryList(Map<String, Object> params) {
        return this.myBatisDaoSupport
                .queryList(this.assistant.getQueryStatementName(), params);
    }
    
    /**
     * @param params
     * @param orderList
     * @return
     */
    @Override
    public List<T> queryList(Map<String, Object> params,
            List<Order> orderList) {
        return this.myBatisDaoSupport.queryList(
                this.assistant.getQueryStatementName(), params, orderList);
    }
    
    /**
     * @param params
     * @return
     */
    @Override
    public int count(Map<String, Object> params) {
        return this.myBatisDaoSupport
                .count(this.assistant.getCountStatmentName(), params);
    }
    
    /**
     * @param params
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @Override
    public PagedList<T> queryPagedList(Map<String, Object> params,
            int pageIndex, int pageSize) {
        return this.myBatisDaoSupport.queryPagedList(
                this.assistant.getQueryStatementName(),
                params,
                pageIndex,
                pageSize);
    }
    
    /**
     * @param params
     * @param pageIndex
     * @param pageSize
     * @param orderList
     * @return
     */
    @Override
    public PagedList<T> queryPagedList(Map<String, Object> params,
            int pageIndex, int pageSize, List<Order> orderList) {
        return this.myBatisDaoSupport.queryPagedList(
                this.assistant.getQueryStatementName(),
                params,
                pageIndex,
                pageSize,
                orderList);
    }
    
    /**
     * @param 对beanType进行赋值
     */
    public void setBeanType(Class<T> beanType) {
        this.beanType = beanType;
    }
    
    /**
     * @param 对myBatisDaoSupport进行赋值
     */
    public void setMyBatisDaoSupport(MyBatisDaoSupport myBatisDaoSupport) {
        this.myBatisDaoSupport = myBatisDaoSupport;
    }
    
    /**
     * @param 对transactionTemplate进行赋值
     */
    public void setTransactionTemplate(
            TransactionTemplate transactionTemplate) {
        this.transactionTemplate = transactionTemplate;
    }
    
    /**
     * @param 对assistant进行赋值
     */
    public void setAssistant(LoggerMapperBuilderAssistant assistant) {
        this.assistant = assistant;
    }
}
