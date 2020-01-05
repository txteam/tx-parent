/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2018年6月9日
 * <修改描述:>
 */
package com.tx.component.servicelogger.support.mybatis;

import java.util.List;

import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.tx.component.servicelogger.support.ServiceLogger;
import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.mybatis.support.MyBatisDaoSupport;
import com.tx.core.paged.model.PagedList;
import com.tx.core.querier.model.Querier;

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
    public Class<T> getEntityType() {
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
        String primaryPropertyName = this.assistant.getPrimaryProperyName();
        this.myBatisDaoSupport.batchInsert(primaryPropertyName,
                objectList,
                false);
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
        String primaryPropertyName = this.assistant.getPrimaryProperyName();
        this.myBatisDaoSupport.insertUseUUID(primaryPropertyName,
                condition,
                primaryPropertyName);
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
    public List<T> queryList(Querier querier) {
        return this.myBatisDaoSupport
                .queryList(this.assistant.getQueryStatementName(), querier);
    }
    
    /**
     * @param params
     * @return
     */
    @Override
    public int count(Querier querier) {
        return this.myBatisDaoSupport
                .count(this.assistant.getCountStatmentName(), querier);
    }
    
    /**
     * @param params
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @Override
    public PagedList<T> queryPagedList(Querier querier, int pageIndex,
            int pageSize) {
        return this.myBatisDaoSupport.queryPagedList(
                this.assistant.getQueryStatementName(),
                querier,
                pageIndex,
                pageSize);
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
