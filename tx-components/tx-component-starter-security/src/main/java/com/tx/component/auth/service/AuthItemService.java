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
//
//import com.tx.component.auth.dao.AuthItemDao;
//import com.tx.component.auth.dao.impl.AuthItemImplDaoImpl;
//import com.tx.component.auth.model.AuthItem;
//import com.tx.core.exceptions.util.AssertUtils;
//
///**
// * AuthItemImpl的业务层
// * <功能详细描述>
// * 
// * @author  
// * @version  [版本号, ]
// * @see  [相关类/方法]
// * @since  [产品/模块版本]
// */
//public class AuthItemService {
//    
//    /** 事务管理器 */
//    private PlatformTransactionManager txManager;
//    
//    /** 权限引用业务层 */
//    @Resource(name = "authItemRefImplService")
//    private AuthItemRefImplService authItemRefService;
//    
//    /** 权限项持久层 */
//    @Resource(name = "authItemImplDao")
//    private AuthItemDao authItemDao;
//    
//    /** <默认构造函数> */
//    public AuthItemService() {
//        super();
//    }
//
//    /**
//     * <默认构造函数>
//     */
//    public AuthItemService(PlatformTransactionManager txManager) {
//        super();
//        AssertUtils.notNull(txManager, "txManager is null.");
//        this.txManager = txManager;
//    }
//    
//    /**
//     * <默认构造函数>
//     */
//    public AuthItemService(PlatformTransactionManager txManager,
//            JdbcTemplate jdbcTemplate, AuthItemRefImplService authItemRefService) {
//        super();
//        AssertUtils.notNull(txManager, "txManager is null.");
//        AssertUtils.notNull(jdbcTemplate, "jdbcTemplate is null.");
//        this.txManager = txManager;
//        
//        this.authItemDao = new AuthItemImplDaoImpl(jdbcTemplate);
//    }
//    
//    /**
//      * 查找权限项目实例
//      * <功能详细描述>
//      * @param authItemId
//      * @return [参数说明]
//      * 
//      * @return AuthItemImpl [返回类型说明]
//      * @exception throws [异常类型] [异常说明]
//      * @see [类、类#方法、类#成员]
//     */
//    public AuthItem findAuthItemImplById(String authItemId,
//            String systemId, String tableSuffix) {
//        AssertUtils.notEmpty(authItemId, "authItemId is empty.");
//        AssertUtils.notEmpty(systemId, "systemId is empty.");
//        
//        AuthItem condition = new AuthItem();
//        condition.setId(authItemId);
//        condition.setSystemId(systemId);
//        
//        AuthItem res = this.authItemDao.findAuthItemImpl(condition,
//                tableSuffix);
//        return res;
//    }
//    
//    /**
//     * 查询数据库中存储的所有权限项<br/>
//     * <功能详细描述>
//     * 
//     * @return [参数说明]
//     * 
//     * @return List<AuthItemImpl> [返回类型说明]
//     * @exception throws [异常类型] [异常说明]
//     * @see [类、类#方法、类#成员]
//    */
//    public List<AuthItem> queryAllAuthItemListBySystemId(String systemId,
//            String tableSuffix) {
//        AssertUtils.notEmpty(systemId, "systemId is empty.");
//        
//        Map<String, Object> params = new HashMap<String, Object>();
//        params.put("systemId", systemId);
//        //查询权限项集合
//        List<AuthItem> resList = this.authItemDao.queryAuthItemImplList(params,
//                tableSuffix);
//        
//        return resList;
//    }
//    
//    /**
//      * 查询AuthItem实体列表<br/>
//      * <功能详细描述>
//      * 
//      * @return [参数说明]
//      * 
//      * @return List<AuthItemImpl> [返回类型说明]
//      * @exception throws [异常类型] [异常说明]
//      * @see [类、类#方法、类#成员]
//     */
//    public List<AuthItem> queryAuthItemListByAuthType(String authType,
//            String systemId, String tableSuffix) {
//        AssertUtils.notEmpty(systemId, "systemId is empty.");
//        AssertUtils.notEmpty(authType, "authType is empty.");
//        
//        //生成查询条件
//        Map<String, Object> params = new HashMap<String, Object>();
//        params.put("authType", authType);
//        params.put("systemId", systemId);
//        
//        //根据实际情况，填入排序字段等条件，根据是否需要排序，选择调用dao内方法
//        List<AuthItem> resList = this.authItemDao.queryAuthItemImplList(params,
//                tableSuffix);
//        
//        return resList;
//    }
//    
//    /**
//      * 将authItemImpl实例插入数据库中保存
//      * 1、如果authItemImpl为空时抛出参数为空异常
//      * 2、如果authItemImpl中部分必要参数为非法值时抛出参数不合法异常
//      * <功能详细描述>
//      * @param authItemImpl [参数说明]
//      * 
//      * @return void [返回类型说明]
//      * @exception throws 可能存在数据库访问异常DataAccessException
//      * @see [类、类#方法、类#成员]
//     */
//    public void insertAuthItemImpl(AuthItem authItemImpl, String systemId,
//            String tableSuffix) {
//        AssertUtils.notNull(authItemImpl, "authItem is null.");
//        AssertUtils.notEmpty(authItemImpl.getAuthType(),
//                "authItem.authType is empty.");
//        AssertUtils.notEmpty(systemId, "systemId is empty.");
//        
//        //setSystemId
//        authItemImpl.setSystemId(systemId);
//        
//        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
//        def.setName("authItemImplServiceTxName");
//        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
//        TransactionStatus status = this.txManager.getTransaction(def);
//        
//        try {
//            this.authItemDao.insertAuthItemImpl(authItemImpl, tableSuffix);
//        } catch (DataAccessException e) {
//            this.txManager.rollback(status);
//            throw e;
//        }
//        this.txManager.commit(status);
//    }
//    
//    /**
//      * 根据id删除authItemImpl实例
//      * 1、如果入参数为空，则抛出异常
//      * 2、执行删除后，将返回数据库中被影响的条数
//      * @param id
//      * @return 返回删除的数据条数，<br/>
//      * 有些业务场景，如果已经被别人删除同样也可以认为是成功的
//      * 这里讲通用生成的业务层代码定义为返回影响的条数
//      * @return int [返回类型说明]
//      * @exception throws 可能存在数据库访问异常DataAccessException
//      * @see [类、类#方法、类#成员]
//     */
//    public void deleteById(String authItemId, String systemId,
//            String tableSuffix) {
//        AssertUtils.notEmpty(systemId, "systemId is empty.");
//        AssertUtils.notEmpty(authItemId, "authItemId is empty.");
//        
//        AuthItem condition = new AuthItem();
//        condition.setId(authItemId);
//        condition.setSystemId(systemId);
//        
//        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
//        def.setName("authItemImplServiceTxName");
//        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
//        TransactionStatus status = this.txManager.getTransaction(def);
//        
//        try {
//            this.authItemRefService.deleteByAuthItemId(authItemId,
//                    systemId,
//                    tableSuffix);
//            this.authItemDao.deleteAuthItemImpl(condition, tableSuffix);
//        } catch (DataAccessException e) {
//            this.txManager.rollback(status);
//            throw e;
//        }
//        this.txManager.commit(status);
//    }
//    
//    /**
//      * 根据id更新对象
//      * <功能详细描述>
//      * @param authItemImpl
//      * @return [参数说明]
//      * 
//      * @return boolean [返回类型说明]
//      * @exception throws [异常类型] [异常说明]
//      * @see [类、类#方法、类#成员]
//     */
//    public boolean updateById(AuthItem authItemImpl, String systemId,
//            String tableSuffix) {
//        AssertUtils.notNull(authItemImpl, "authItem is null");
//        AssertUtils.notEmpty(authItemImpl.getId(), "authItem.id is empty.");
//        AssertUtils.notEmpty(systemId, "systemId is empty.");
//        
//        //生成需要更新字段的hashMap
//        Map<String, Object> updateRowMap = new HashMap<String, Object>();
//        updateRowMap.put("id", authItemImpl.getId());
//        updateRowMap.put("systemId", systemId);
//        
//        //需要更新的字段
//        updateRowMap.put("valid", authItemImpl.isValid());
//        updateRowMap.put("parentId", authItemImpl.getParentId());
//        updateRowMap.put("description", authItemImpl.getDescription());
//        updateRowMap.put("editAble", authItemImpl.isEditAble());
//        updateRowMap.put("viewAble", authItemImpl.isViewAble());
//        updateRowMap.put("name", authItemImpl.getName());
//        updateRowMap.put("authType", authItemImpl.getAuthType());
//        
//        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
//        def.setName("authItemImplServiceTxName");
//        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
//        TransactionStatus status = this.txManager.getTransaction(def);
//        
//        int updateRowCount = 0;
//        try {
//            updateRowCount = this.authItemDao.updateAuthItemImpl(updateRowMap,
//                    tableSuffix);
//        } catch (DataAccessException e) {
//            this.txManager.rollback(status);
//            throw e;
//        }
//        this.txManager.commit(status);
//        
//        //如果需要大于1时，抛出异常并回滚，需要在这里修改
//        return updateRowCount >= 1;
//    }
//    
//    /**
//      * 更新权限项
//      *<功能详细描述>
//      * @param authItemRowMap
//      * @return [参数说明]
//      * 
//      * @return boolean [返回类型说明]
//      * @exception throws [异常类型] [异常说明]
//      * @see [类、类#方法、类#成员]
//     */
//    public AuthItem saveAuthItemImplByAuthItemRowMap(
//            Map<String, Object> authItemRowMap, String systemId,
//            String tableSuffix) {
//        AssertUtils.notNull(authItemRowMap, "authItemRowMap is null");
//        AssertUtils.notEmpty((String) authItemRowMap.get("id"),
//                "authItemRowMap.id is empty.");
//        AssertUtils.notEmpty(systemId, "systemId is empty.");
//        //写入systemId
//        authItemRowMap.put("systemId", systemId);
//        
//        String authItemId = (String) authItemRowMap.get("id");
//        AuthItem authItem = findAuthItemImplById(authItemId,
//                systemId,
//                tableSuffix);
//        
//        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
//        def.setName("authItemImplServiceTxName");
//        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
//        TransactionStatus status = this.txManager.getTransaction(def);
//        
//        try {
//            if (authItem != null) {
//                this.authItemDao.updateAuthItemImpl(authItemRowMap, tableSuffix);
//            } else {
//                authItem = new AuthItem(authItemRowMap);
//                insertAuthItemImpl(authItem, systemId, tableSuffix);
//            }
//        } catch (DataAccessException e) {
//            this.txManager.rollback(status);
//            throw e;
//        }
//        this.txManager.commit(status);
//        
//        return authItem;
//    }
//}
