///*
// * 描          述:  <描述>
// * 修  改   人:  Administrator
// * 修改时间:  2015年11月7日
// * <修改描述:>
// */
//package com.tx.component.auth.context.loaderprocessor;
//
//import java.lang.reflect.Method;
//import java.util.HashMap;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//
//import org.apache.commons.collections.CollectionUtils;
//import org.apache.commons.collections.MapUtils;
//import org.apache.commons.lang3.BooleanUtils;
//import org.apache.commons.lang3.ClassUtils;
//import org.apache.commons.lang3.StringUtils;
//import org.slf4j.helpers.MessageFormatter;
//import org.springframework.beans.factory.InitializingBean;
//import org.springframework.stereotype.Controller;
//import org.springframework.util.LinkedMultiValueMap;
//import org.springframework.util.MultiValueMap;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import com.tx.component.auth.annotation.CheckOperateAuth;
//import com.tx.component.auth.context.AuthItemLoaderProcessor;
//import com.tx.component.auth.context.AuthTypeItemContext;
//import com.tx.component.auth.exception.AuthContextInitException;
//import com.tx.component.auth.model.Auth;
//import com.tx.component.auth.model.AuthItem;
//import com.tx.core.exceptions.util.AssertUtils;
//import com.tx.core.util.ClassScanUtils;
//import com.tx.core.util.MessageUtils;
//
///**
// * 子数据权限权限加载器处理器<br/>
// * 
// * @author  Administrator
// * @version  [版本号, 2015年11月7日]
// * @see  [相关类/方法]
// * @since  [产品/模块版本]
// */
//public class ControllerAuthRegisterSupportLoaderProcessor implements
//        AuthItemLoaderProcessor, InitializingBean {
//    
//    /** 是否加载controllerAuth的标志 */
//    private static final String CONTROLLER_AUTH_LOAD = "controller_auth_load";
//    
//    /** 存放controller中权限项映射 */
//    private static final MultiValueMap<String, Auth> parentId2authItemMap = new LinkedMultiValueMap<String, Auth>();
//    
//    /** 存放controller中权限项映射 */
//    private static final Map<String, Auth> id2authItemMap = new HashMap<String, Auth>();
//    
//    /** 如果某权限设置了controller的权限加载时，不存在时是否抛出异常 */
//    private boolean throwExceptionWhenNotExist = false;
//    
//    /** 类基础包 */
//    private String basePackages = "com.tx";
//    
//    /**
//     * @throws Exception
//     */
//    @Override
//    public void afterPropertiesSet() throws Exception {
//        //扫描得到Class的类集合
//        Set<Class<? extends Object>> controllerClasses = ClassScanUtils.scanByAnnotation(Controller.class,
//                StringUtils.splitByWholeSeparator(basePackages, ","));
//        if (CollectionUtils.isEmpty(controllerClasses)) {
//            return;
//        }
//        
//        for (Class<?> controllerClassTemp : controllerClasses) {
//            //根据controller class加载权限
//            AuthItem beanAuthItem = null;
//            if (controllerClassTemp.isAnnotationPresent(CheckOperateAuth.class)) {
//                beanAuthItem = buildAuthItem(ClassUtils.getShortClassName(controllerClassTemp),
//                        "",
//                        null,
//                        controllerClassTemp.getAnnotation(CheckOperateAuth.class));
//                putAuthItem(beanAuthItem);
//            }
//            
//            String parentAuthKey = null;
//            if (beanAuthItem != null) {
//                parentAuthKey = beanAuthItem.getId();
//            }
//            //根据方法加载权限
//            Method[] methods = controllerClassTemp.getMethods();
//            for (Method methodTemp : methods) {
//                if (!methodTemp.isAnnotationPresent(RequestMapping.class)
//                        || !methodTemp.isAnnotationPresent(CheckOperateAuth.class)) {
//                    //如果不是同事具备RequestMapping注解以及CheckOperateAuth两个注解，则认为是无效的操作权限不需要进行加载
//                    continue;
//                }
//                
//                AuthItem methodAuthItem = buildAuthItem(ClassUtils.getShortClassName(controllerClassTemp.getClass()),
//                        methodTemp.getName(),
//                        parentAuthKey,
//                        methodTemp.getAnnotation(CheckOperateAuth.class));
//                putAuthItem(methodAuthItem);
//            }
//        }
//    }
//    
//    /**
//      * 将权限压入容器记录<br/>
//      *<功能详细描述>
//      * @param authItem [参数说明]
//      * 
//      * @return void [返回类型说明]
//      * @exception throws [异常类型] [异常说明]
//      * @see [类、类#方法、类#成员]
//     */
//    private void putAuthItem(AuthItem authItem) {
//        if (StringUtils.isEmpty(authItem.getId())) {
//            return;
//        }
//        if (authItem.getId().equals(authItem.getParentId())) {
//            authItem.setParentId(null);
//        }
//        ControllerAuthRegisterSupportLoaderProcessor.id2authItemMap.put(authItem.getId(),
//                authItem);
//        if (!StringUtils.isEmpty(authItem.getParentId())) {
//            ControllerAuthRegisterSupportLoaderProcessor.parentId2authItemMap.add(authItem.getParentId(),
//                    authItem);
//        }
//    }
//    
//    /**
//     * 根据菜单创建权限项<br/>
//     * <功能详细描述>
//     * @param parentMenuAuthItem
//     * @param menuItem
//     * @return [参数说明]
//     * 
//     * @return AuthItem [返回类型说明]
//     * @exception throws [异常类型] [异常说明]
//     * @see [类、类#方法、类#成员]
//    */
//    private AuthItem buildAuthItem(String beanSimpleName,
//            String methodName, String parentAuthKey,
//            CheckOperateAuth checkOperateAuthAnno) {
//        AssertUtils.notNull(checkOperateAuthAnno,
//                "checkOperateAuthAnno is null");
//        
//        //根据菜单生成对应权限项
//        AuthItem authItem = new AuthItem();
//        authItem.setAuthType(AuthTypeItemContext.getContext()
//                .registeAuthTypeItem("AUTHTYPE_OPERATE", "操作权限", "", true, true)
//                .getAuthType());
//        //对应权限是否可赋予超级管理员以外的人员
//        authItem.setConfigAble(checkOperateAuthAnno.configAble());
//        authItem.setDescription(MessageFormatter.arrayFormat("控制器[{}.{}]的操作权限",
//                new Object[] { beanSimpleName, methodName }).getMessage());
//        
//        authItem.setEditAble(false);
//        authItem.setId(checkOperateAuthAnno.key());
//        authItem.setName(!StringUtils.isBlank(checkOperateAuthAnno.name()) ? checkOperateAuthAnno.name()
//                : checkOperateAuthAnno.key());
//        authItem.setParentId(!StringUtils.isBlank(checkOperateAuthAnno.parentKey()) ? checkOperateAuthAnno.parentKey()
//                : parentAuthKey);
//        authItem.setValid(true);
//        authItem.setViewAble(true);
//        if (!StringUtils.isEmpty(authItem.getId())
//                && authItem.getId().equals(authItem.getParentId())) {
//            authItem.setParentId(null);
//        }
//        
//        return authItem;
//    }
//    
//    /**
//     * @return
//     */
//    @Override
//    public int getOrder() {
//        return 1;
//    }
//    
//    /**
//     * @param beforeLoadAuthItemMapping
//     * @param authItemSet
//     * @return
//     * @throws AuthContextInitException
//     */
//    @Override
//    public Set<Auth> postProcessAfterLoad(
//            Map<String, Auth> beforeLoadAuthItemMapping,
//            Set<Auth> authItemSet) throws AuthContextInitException {
//        Set<Auth> newAuthItemSet = new HashSet<>();
//        for (Auth authTemp : authItemSet) {
//            if (MapUtils.isEmpty(authTemp.getData())
//                    || !authTemp.getData().containsKey(CONTROLLER_AUTH_LOAD)
//                    || BooleanUtils.isTrue(StringUtils.isEmpty(authTemp.getData()
//                            .get(CONTROLLER_AUTH_LOAD)))) {
//                continue;
//            }
//            //校验parentId2authItemMap中是否含有
//            if (!ControllerAuthRegisterSupportLoaderProcessor.parentId2authItemMap.containsKey(authTemp.getId())) {
//                if (throwExceptionWhenNotExist) {
//                    throw new AuthContextInitException(
//                            MessageUtils.format("authItem:{} set CONTROLLER_AUTH_LOAD.but child not exist.",
//                                    new Object[] { authTemp.getId() }));
//                } else {
//                    continue;
//                }
//            }
//            //如果含有迭代获取子权限Set
//            Set<String> parentAuthItemIdSet = new HashSet<>();
//            parentAuthItemIdSet.add(authTemp.getId());
//            //迭代获取子集权限
//            nestedLoadChildAuthItem(parentAuthItemIdSet, newAuthItemSet);
//        }
//        return newAuthItemSet;
//    }
//    
//    /**
//      * 迭代获取子集权限<br/>
//      *<功能详细描述>
//      * @param parentAuthItemIdSet
//      * @param newAuthItemSet [参数说明]
//      * 
//      * @return void [返回类型说明]
//      * @exception throws [异常类型] [异常说明]
//      * @see [类、类#方法、类#成员]
//     */
//    private void nestedLoadChildAuthItem(Set<String> parentAuthItemIdSet,
//            Set<Auth> newAuthItemSet) {
//        Set<String> parentAuthItemIdSetTemp = new HashSet<>();
//        for (String parentAuthItemIdTemp : parentAuthItemIdSet) {
//            List<Auth> authItemList = parentId2authItemMap.get(parentAuthItemIdTemp);
//            if (CollectionUtils.isEmpty(authItemList)) {
//                continue;
//            }
//            for (Auth authItemTemp : authItemList) {
//                newAuthItemSet.add(authItemTemp);
//                parentAuthItemIdSetTemp.add(authItemTemp.getId());
//            }
//        }
//        if (!CollectionUtils.isEmpty(parentAuthItemIdSetTemp)) {
//            nestedLoadChildAuthItem(parentAuthItemIdSetTemp, newAuthItemSet);
//        }
//    }
//    
//    /**
//     * @param 对basePackages进行赋值
//     */
//    public void setBasePackages(String basePackages) {
//        this.basePackages = basePackages;
//    }
//}
