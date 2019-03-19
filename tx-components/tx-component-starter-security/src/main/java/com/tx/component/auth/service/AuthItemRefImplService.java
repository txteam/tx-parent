///*
// * 描          述:  <描述>
// * 修  改   人:  
// * 修改时间:  
// * <修改描述:>
// */
//package com.tx.component.auth.service;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import javax.annotation.Resource;
//
//import org.springframework.dao.DataAccessException;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.transaction.PlatformTransactionManager;
//import org.springframework.transaction.TransactionDefinition;
//import org.springframework.transaction.TransactionStatus;
//import org.springframework.transaction.support.DefaultTransactionDefinition;
//import org.springframework.util.MultiValueMap;
//
//import com.tx.component.auth.context.AuthContext;
//import com.tx.component.auth.dao.AuthItemRefDao;
//import com.tx.component.auth.dao.impl.AuthItemRefImplDaoImpl;
//import com.tx.component.auth.model.Auth;
//import com.tx.component.auth.model.AuthRefItem;
//import com.tx.core.exceptions.util.AssertUtils;
//
///**
// * AuthItemRefImpl的业务层<br/>
// *     主要针对包含temp以及非temp的权限引用的处理逻辑
// * <功能详细描述>
// * 
// * @author  
// * @version  [版本号, ]
// * @see  [相关类/方法]
// * @since  [产品/模块版本]
// */
//public class AuthItemRefImplService {
//    
//    /** 事务管理器 */
//    private PlatformTransactionManager txManager;
//    
//    /** 权限引用项持久层 */
//    @Resource(name = "authItemRefImplDao")
//    private AuthItemRefDao authItemRefImplDao;
//    
//    /** <默认构造函数> */
//    public AuthItemRefImplService() {
//        super();
//    }
//
//    /**
//     * <默认构造函数>
//     */
//    public AuthItemRefImplService(PlatformTransactionManager txManager) {
//        super();
//        AssertUtils.notNull(txManager, "txManager is null.");
//        this.txManager = txManager;
//    }
//    
//    /**
//     * <默认构造函数>
//     */
//    public AuthItemRefImplService(PlatformTransactionManager txManager,
//            JdbcTemplate jdbcTemplate) {
//        super();
//        AssertUtils.notNull(txManager, "txManager is null.");
//        AssertUtils.notNull(jdbcTemplate, "jdbcTemplate is null.");
//        this.txManager = txManager;
//        
//        this.authItemRefImplDao = new AuthItemRefImplDaoImpl(jdbcTemplate);
//    }
//    
//    /**
//      * 根据具体的权限引用类型以及引用id查询权限引用集合<br/>
//      *     系统登录需要调用该方法，以获知当前人员拥有的权限引用集合<br/>
//      * 
//      * <功能详细描述>
//      * @return [参数说明]
//      * 
//      * @return List<AuthItemRefImpl> [返回类型说明]
//      * @exception throws [异常类型] [异常说明]
//      * @see [类、类#方法、类#成员]
//     */
//    public List<AuthRefItem> queryAuthItemRefListByRefType2RefIdMapping(
//            MultiValueMap<String, String> refType2RefIdMapping, String systemId,
//            String tableSuffix) {
//        AssertUtils.notEmpty(refType2RefIdMapping,
//                "refType2RefIdMapping is empty.");
//        AssertUtils.notEmpty(systemId, "systemId is empty.");
//        
//        //生成查询条件
//        Map<String, Object> params = new HashMap<String, Object>();
//        params.put("refType2RefIdMap", refType2RefIdMapping);
//        params.put("systemId", systemId);
//        
//        //根据实际情况，填入排序字段等条件，根据是否需要排序，选择调用dao内方法
//        List<AuthRefItem> authItemRefImplList = this.authItemRefImplDao.queryAuthItemRefImplList(params,
//                tableSuffix);
//        
//        return authItemRefImplList;
//    }
//    
//    /**
//     * 根据refId删除authItemRefImpl实例
//     * 1、如果入参数为空，则抛出异常
//     * 2、执行删除后，将返回数据库中被影响的条数
//     * @param refId
//     * @return 返回删除的数据条数，<br/>
//     * 有些业务场景，如果已经被别人删除同样也可以认为是成功的
//     * 这里讲通用生成的业务层代码定义为返回影响的条数
//     * @return int [返回类型说明]
//     * @exception throws 可能存在数据库访问异常DataAccessException
//     * @see [类、类#方法、类#成员]
//    */
//    void deleteByAuthItemId(String authItemId, String systemId,
//            String tableSuffix) {
//        AssertUtils.notEmpty(authItemId, "authItemId is empty.");
//        AssertUtils.notEmpty(systemId, "systemId is empty");
//        
//        AuthRefItem condition = new AuthRefItem();
//        Auth authItemTemp = AuthContext.getContext()
//                .getAuthItemFromContextById(authItemId);
//        condition.setAuthItem(authItemTemp);
//        condition.setTemp(null);
//        
//        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
//        def.setName("authItemRefImplServiceTxName");
//        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
//        TransactionStatus status = this.txManager.getTransaction(def);
//        
//        try {
//            this.authItemRefImplDao.deleteAuthItemRefImpl(condition,
//                    tableSuffix);
//        } catch (DataAccessException e) {
//            this.txManager.rollback(status);
//            throw e;
//        }
//        this.txManager.commit(status);
//    }
//}
