///*
// * 描          述:  <描述>
// * 修  改   人:  Administrator
// * 修改时间:  2015年11月8日
// * <修改描述:>
// */
//
//
//import java.util.ArrayList;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//
//import org.apache.commons.collections.CollectionUtils;
//import org.apache.commons.lang3.StringUtils;
//
//import com.tx.component.auth.AuthConstants;
//import com.tx.component.auth.context.AuthContext;
//import com.tx.component.auth.context.AuthSessionContext;
//import com.tx.component.auth.model.Auth;
//import com.tx.core.exceptions.util.AssertUtils;
//
///**
// * 服务于Mybatis的SqlMap中时减少类及方法的调用长度<br/>
// * 
// * @author  Administrator
// * @version  [版本号, 2015年11月8日]
// * @see  [相关类/方法]
// * @since  [产品/模块版本]
// */
//public class AuthUtils {
//    
//    /**
//      * 判断是否设置了根据指定属性的数据权限查询<br/>
//      * <功能详细描述>
//      * @param propertyName
//      * @return [参数说明]
//      * 
//      * @return boolean [返回类型说明]
//      * @exception throws [异常类型] [异常说明]
//      * @see [类、类#方法、类#成员]
//     */
//    public static boolean isQueryByAuth(String propertyName) {
//        boolean flag = AuthSessionContext.isContainsInQueryAuthMap(propertyName);
//        return flag;
//    }
//    
//    /**
//      * 校验对应的权限引用ID是否非空<br/>
//      * <功能详细描述>
//      * @param propertyName
//      * @return [参数说明]
//      * 
//      * @return boolean [返回类型说明]
//      * @exception throws [异常类型] [异常说明]
//      * @see [类、类#方法、类#成员]
//     */
//    public static boolean isNotEmptyOfQueryAuthRefIds(String propertyName) {
//        String authKey = AuthSessionContext.getAuthKeyFromQueryAuthMap(propertyName);
//        Set<String> refIds = getChildDataAuthRefIdsByAuthKey(authKey);
//        return CollectionUtils.isNotEmpty(refIds);
//    }
//    
//    /**
//     * 校验对应的权限引用ID是否非空<br/>
//     * <功能详细描述>
//     * @param propertyName
//     * @return [参数说明]
//     * 
//     * @return boolean [返回类型说明]
//     * @exception throws [异常类型] [异常说明]
//     * @see [类、类#方法、类#成员]
//    */
//    public static boolean isEmptyOfQueryAuthRefIds(String propertyName) {
//        String authKey = AuthSessionContext.getAuthKeyFromQueryAuthMap(propertyName);
//        Set<String> refIds = getChildDataAuthRefIdsByAuthKey(authKey);
//        return CollectionUtils.isEmpty(refIds);
//    }
//    
//    /**
//      * 获取引用的权限id集合<br/>
//      * <功能详细描述>
//      * @param propertyName
//      * @return [参数说明]
//      * 
//      * @return Set<String> [返回类型说明]
//      * @exception throws [异常类型] [异常说明]
//      * @see [类、类#方法、类#成员]
//     */
//    public static Set<String> getQueryAuthRefIds(String propertyName) {
//        String authKey = AuthSessionContext.getAuthKeyFromQueryAuthMap(propertyName);
//        Set<String> refIds = getChildDataAuthRefIdsByAuthKey(authKey);
//        return refIds;
//    }
//    
//    /**
//      * 设置当前逻辑之后的查询可依赖某数据权限进行查询<br/>
//      * <功能详细描述>
//      * @param authKey [参数说明]
//      * 
//      * @return void [返回类型说明]
//      * @exception throws [异常类型] [异常说明]
//      * @see [类、类#方法、类#成员]
//     */
//    public static void setQueryByAuth(String propertyName, String authKey) {
//        AuthSessionContext.putToQueryAuthMap(propertyName, authKey);
//    }
//    
//    /**
//      * 设置当前逻辑之后的查询可依赖某数据权限进行查询<br/>
//      * <功能详细描述>
//      * @param propertyName2authKeyMap [参数说明]
//      * 
//      * @return void [返回类型说明]
//      * @exception throws [异常类型] [异常说明]
//      * @see [类、类#方法、类#成员]
//     */
//    public static void setQueryByAuth(
//            Map<String, String> propertyName2authKeyMap) {
//        AuthSessionContext.getQueryAuthMap().putAll(propertyName2authKeyMap);
//    }
//    
//    /**
//      * 清除根据权限查询的设置<br/>
//      * <功能详细描述> [参数说明]
//      * 
//      * @return void [返回类型说明]
//      * @exception throws [异常类型] [异常说明]
//      * @see [类、类#方法、类#成员]
//     */
//    public static void clearQueryByAuth() {
//        AuthSessionContext.clearQueryAuthMap();
//    }
//    
//    //public static Set<String> 
//    
//    /**
//     * 辅助判断是否有某权限<br/>
//     * <功能详细描述>
//     * @param authKey
//     * @return [参数说明]
//     * 
//     * @return boolean [返回类型说明]
//     * @exception throws [异常类型] [异常说明]
//     * @see [类、类#方法、类#成员]
//    */
//    public static boolean isHasAuth(String authKey) {
//        AssertUtils.notEmpty(authKey, "authKey is empty.");
//        boolean isHasAuth = AuthContext.getContext().hasAuth(authKey);
//        return isHasAuth;
//    }
//    
//    /**
//     * 辅助判断是否有某权限<br/>
//     * <功能详细描述>
//     * @param authKey
//     * @return [参数说明]
//     * 
//     * @return boolean [返回类型说明]
//     * @exception throws [异常类型] [异常说明]
//     * @see [类、类#方法、类#成员]
//    */
//    public static boolean isHasAuth(String... authKeys) {
//        AssertUtils.notEmpty(authKeys, "authKeys is empty.");
//        for (String authKeyTemp : authKeys) {
//            if (!isHasAuth(authKeyTemp)) {
//                return false;
//            }
//        }
//        return true;
//    }
//    
//    /**
//      * 获取子集数据权限的引用Id集合<br/>
//      * <功能详细描述>
//      * @param authKey
//      * @return [参数说明]
//      * 
//      * @return Set<String> [返回类型说明]
//      * @exception throws [异常类型] [异常说明]
//      * @see [类、类#方法、类#成员]
//     */
//    public static Set<String> getChildDataAuthRefIdsByAuthKey(String authKey) {
//        AssertUtils.notEmpty(authKey, "authKey is empty.");
//        Set<String> resSet = new HashSet<>();
//        Set<Auth> authItemSet = getChildDataAuthItemListByAuthKey(authKey);
//        if (CollectionUtils.isEmpty(authItemSet)) {
//            return resSet;
//        }
//        for (Auth authTemp : authItemSet) {
//            if (StringUtils.isEmpty(authTemp.getRefId())) {
//                continue;
//            }
//            //如果不为空压入
//            resSet.add(authTemp.getRefId());
//        }
//        return resSet;
//    }
//    
//    /**
//      * 根据权限Key获取子集数据权限集合<br/>
//      * <功能详细描述>
//      * @param authKey
//      * @return [参数说明]
//      * 
//      * @return Set<AuthItem> [返回类型说明]
//      * @exception throws [异常类型] [异常说明]
//      * @see [类、类#方法、类#成员]
//     */
//    public static Set<Auth> getChildDataAuthItemListByAuthKey(String authKey) {
//        AssertUtils.notEmpty(authKey, "authKey is empty.");
//        Set<Auth> resSet = getChildAuthItemListByAuthTypeAndParentId(AuthConstants.AUTHTYPE_DATA,
//                authKey);
//        return resSet;
//    }
//    
//    /**
//      * 根据父级权限id以及权限类型
//      * <功能详细描述>
//      * @param authType
//      * @param parentId
//      * @return [参数说明]
//      * 
//      * @return List<AuthItem> [返回类型说明]
//      * @exception throws [异常类型] [异常说明]
//      * @see [类、类#方法、类#成员]
//     */
//    public static Set<Auth> getChildAuthItemListByAuthTypeAndParentId(
//            String authType, String parentId) {
//        AssertUtils.notEmpty(parentId, "parentId is empty.");
//        AssertUtils.notEmpty(authType, "authType is empty.");
//        
//        Set<Auth> resSet = new HashSet<Auth>();
//        List<Auth> authItems = AuthSessionContext.getAuthItemListByAuthTypeAndParentIdFromSession(authType,
//                parentId);
//        nestedLoadChildAuthItems(authType, authItems, resSet);
//        return resSet;
//    }
//    
//    /**
//      * 嵌套加载子集权限<br/>
//      * <功能详细描述>
//      * @param authItems
//      * @param resList [参数说明]
//      * 
//      * @return void [返回类型说明]
//      * @exception throws [异常类型] [异常说明]
//      * @see [类、类#方法、类#成员]
//     */
//    private static void nestedLoadChildAuthItems(String authType,
//            List<Auth> authItems, Set<Auth> resSet) {
//        List<Auth> newAuthItems = new ArrayList<Auth>();
//        for (Auth authItemTemp : authItems) {
//            List<Auth> authItems3 = AuthSessionContext.getAuthItemListByAuthTypeAndParentIdFromSession(authType,
//                    authItemTemp.getId());
//            if (CollectionUtils.isNotEmpty(authItems3)) {
//                newAuthItems.addAll(authItems3);
//            }
//            resSet.add(authItemTemp);
//        }
//        if (CollectionUtils.isNotEmpty(newAuthItems)) {
//            nestedLoadChildAuthItems(authType, newAuthItems, resSet);
//        }
//    }
//    
//    /**
//     * 根据父级权限id以及权限类型
//     *     该方便一般不建议使用，有时候存在数据权限以及非数据权限中重复的的情况<br/>
//     * @param authType
//     * @param parentId
//     * @return [参数说明]
//     * 
//     * @return List<AuthItem> [返回类型说明]
//     * @exception throws [异常类型] [异常说明]
//     * @see [类、类#方法、类#成员]
//    */
//    @Deprecated
//    public static Set<Auth> getChildAuthItemListByParentId(String parentId) {
//        AssertUtils.notEmpty(parentId, "parentId is empty.");
//        
//        Set<Auth> resSet = new HashSet<Auth>();
//        List<Auth> authItems = AuthSessionContext.getAuthItemListByParentIdFromSession(parentId);
//        nestedLoadChildAuthItems(authItems, resSet);
//        return resSet;
//    }
//    
//    /**
//     * 嵌套加载子集权限<br/>
//     * <功能详细描述>
//     * @param authItems
//     * @param resList [参数说明]
//     * 
//     * @return void [返回类型说明]
//     * @exception throws [异常类型] [异常说明]
//     * @see [类、类#方法、类#成员]
//    */
//    private static void nestedLoadChildAuthItems(List<Auth> authItems,
//            Set<Auth> resSet) {
//        List<Auth> newAuthItems = new ArrayList<Auth>();
//        for (Auth authItemTemp : authItems) {
//            List<Auth> authItems3 = AuthSessionContext.getAuthItemListByParentIdFromSession(authItemTemp.getId());
//            if (CollectionUtils.isNotEmpty(authItems3)) {
//                newAuthItems.addAll(authItems3);
//            } else {
//                resSet.add(authItemTemp);
//            }
//        }
//        if (CollectionUtils.isNotEmpty(newAuthItems)) {
//            nestedLoadChildAuthItems(newAuthItems, resSet);
//        }
//    }
//}
