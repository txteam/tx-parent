/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-3-27
 * <修改描述:>
 */
package com.tx.component.rule.transation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import com.tx.core.exceptions.argument.NullArgumentException;

/**
 * 规则会话工具类，用以支持当前事务的开启关闭<br/>
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2013-3-27]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class RuleSessionTransactionUtils {
    
    /**
     * 规则会话事务，日志记录器
     */
    private static Logger logger = LoggerFactory.getLogger(RuleSessionTransactionUtils.class);
    
    /**
      * 湖区偶规则会话事务
      * <功能详细描述>
      * @param rstFactory
      * @return [参数说明]
      * 
      * @return RuleSessionTransaction [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public static RuleSessionTransaction openRuleSessionTransation(
            RuleSessionTransactionFactory rstFactory) {
        return doOpenRuleSessionTransation(rstFactory);
    }
    
    /**
      * 开启规则会话
      * <功能详细描述>
      * @param rstFactory
      * @return [参数说明]
      * 
      * @return RuleSessionTransaction [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    private static RuleSessionTransaction doOpenRuleSessionTransation(
            RuleSessionTransactionFactory rstFactory){
        if(rstFactory == null){
            throw new NullArgumentException("ruleSessionTransactionFactory is empty.");
        }
        
        //从当前线程中根据factory获取可能存在的transHolder
        RuleSessionTransactionHolder ruleSessionTransHolder = (RuleSessionTransactionHolder) TransactionSynchronizationManager.getResource(rstFactory);
        
        //如果transHolder存在，并且（并未结束，或已经托管给事务容器）
        if (ruleSessionTransHolder != null
                && (!ruleSessionTransHolder.isReleased() || ruleSessionTransHolder.isSynchronizedWithTransaction())) {
            ruleSessionTransHolder.requested();
            if (ruleSessionTransHolder.isReleased()) {
                logger.warn("-----------------------------------------------------------------------");
                logger.warn("ruleSessionTransHolder.isReleased. so re new open ruleSessionTransation");
                logger.warn("-----------------------------------------------------------------------");
                ruleSessionTransHolder.setRuleSessionTransaction(rstFactory.openRuleSessionTransaction());
            }
            return ruleSessionTransHolder.getRuleSessionTransaction();
        }
        
        //开启新的规则会话
        RuleSessionTransaction ruleSessionTrans = rstFactory.openRuleSessionTransaction();
        
        RuleSessionTransactionHolder holderToUse = ruleSessionTransHolder;
        if (holderToUse == null) {
            holderToUse = new RuleSessionTransactionHolder(ruleSessionTrans);
        } else {
            holderToUse.setRuleSessionTransaction(ruleSessionTrans);
        }
        holderToUse.requested();
        
        //如果ruleSessionTransation与原绑定到流程的的holder不是同一个对象的引用，则绑定入当前线程中
        if (holderToUse != ruleSessionTransHolder) {
            TransactionSynchronizationManager.bindResource(rstFactory,
                    holderToUse);
        }
        
        return ruleSessionTrans;
    }
    
    /**
      * 关闭规则会话事务
      * <功能详细描述>
      * @param con
      * @param dataSource [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public static void closeRuleSessionTransation(RuleSessionTransaction trans, RuleSessionTransactionFactory fac) {
        doCloseRuleSessionTransation(trans, fac);
    }

    /**
      * 关闭规则会话事务 
      * <功能详细描述>
      * @param con
      * @param dataSource [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    private static void doCloseRuleSessionTransation(RuleSessionTransaction trans, RuleSessionTransactionFactory fac){
        if (trans == null) {
            return;
        }
        if (fac != null) {
            RuleSessionTransactionHolder transHolder = (RuleSessionTransactionHolder) TransactionSynchronizationManager.getResource(fac);
            if (transHolder != null && transationEquals(transHolder, trans)) {
                //利用holder进行资源关闭
                transHolder.released();
                return;
            }
        }
        logger.warn("ruleSessionTransHolder.isNotEqual. so close imeditily.");
        //如果传入事务与线程中绑定事务保持器持有的不同，则直接在此关闭
        trans.close();
    }

    /**
     * Determine whether the given two Connections are equal, asking the target
     * Connection in case of a proxy. Used to detect equality even if the
     * user passed in a raw target Connection while the held one is a proxy.
     * @param conHolder the ConnectionHolder for the held Connection (potentially a proxy)
     * @param passedInCon the Connection passed-in by the user
     * (potentially a target Connection without proxy)
     * @return whether the given Connections are equal
     * @see #getTargetConnection
     */
    private static boolean transationEquals(RuleSessionTransactionHolder transHolder, RuleSessionTransaction trans) {
        if (transHolder.isReleased()) {
            return false;
        }
        
        RuleSessionTransaction heldCon = transHolder.getRuleSessionTransaction();
        //判断ruleSessionTranstion是否是同一个对象
        return (heldCon == trans || heldCon.equals(trans));
    }
}
