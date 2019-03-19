///*
// * 描          述:  <描述>
// * 修  改   人:  brady
// * 修改时间:  2013-10-11
// * <修改描述:>
// */
//package com.tx.component.auth.context.loader.impl;
//
//import java.lang.reflect.Method;
//import java.util.HashMap;
//import java.util.HashSet;
//import java.util.Map;
//import java.util.Set;
//
//import org.apache.commons.collections.CollectionUtils;
//import org.apache.commons.lang3.ClassUtils;
//import org.apache.commons.lang3.StringUtils;
//import org.slf4j.helpers.MessageFormatter;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import com.tx.component.auth.annotation.CheckOperateAuth;
//import com.tx.component.auth.context.AuthTypeItemContext;
//import com.tx.component.auth.context.loader.AuthLoader;
//import com.tx.component.auth.model.AuthItem;
//import com.tx.component.auth.model.AuthItemImpl;
//import com.tx.core.exceptions.util.AssertUtils;
//import com.tx.core.util.ClassScanUtils;
//
///**
// * 注解操作权限加载器<br/>
// *     controller中注解的权限部分<br/>
// *     根据controller加载权限可能加载出来较多的权限
// *     而有些基础数据维护项是完全不需要太多的操作权限去控制的
// * @author  brady
// * @version  [版本号, 2013-10-11]
// * @see  [相关类/方法]
// * @since  [产品/模块版本]
// */
//@Deprecated
//public class ControllerCheckOperateAuthLoader implements AuthLoader {
//    
//    /** 权限加载项的优先级 */
//    private int order = 0;
//    
//    /** 类基础包 */
//    private String basePackages = "com.tx";
//    
//    /**
//     * @return
//     */
//    @Override
//    public int getOrder() {
//        return order;
//    }
//    
//    /**
//     * @return
//     */
//    @Override
//    public Set<AuthItem> loadAuthItems(
//            Map<String, AuthItem> sourceAuthItemMapping) {
//        Set<Class<? extends Object>> controllerClasses = ClassScanUtils.scanByAnnotation(Controller.class,
//                StringUtils.splitByWholeSeparator(basePackages, ","));
//        Map<String, AuthItem> authItemMapping = new HashMap<String, AuthItem>();
//        if (CollectionUtils.isEmpty(controllerClasses)) {
//            return new HashSet<AuthItem>(authItemMapping.values());
//        }
//        
//        for (Class<?> controllerClassTemp : controllerClasses) {
//            
//            //根据controller class加载权限
//            AuthItem beanAuthItem = null;
//            if (controllerClassTemp.isAnnotationPresent(CheckOperateAuth.class)) {
//                beanAuthItem = createAuthItem(ClassUtils.getShortClassName(controllerClassTemp),
//                        "",
//                        null,
//                        controllerClassTemp.getAnnotation(CheckOperateAuth.class));
//                putAuthItem(authItemMapping, beanAuthItem);
//            }
//            
//            String parentAuthKey = null;
//            if (beanAuthItem != null) {
//                parentAuthKey = beanAuthItem.getId();
//            }
//            
//            //根据方法加载权限
//            Method[] methods = controllerClassTemp.getMethods();
//            for (Method methodTemp : methods) {
//                if (!methodTemp.isAnnotationPresent(RequestMapping.class)
//                        || !methodTemp.isAnnotationPresent(CheckOperateAuth.class)) {
//                    //如果不是同事具备RequestMapping注解以及CheckOperateAuth两个注解，则认为是无效的操作权限不需要进行加载
//                    continue;
//                }
//                
//                AuthItem methodAuthItem = createAuthItem(ClassUtils.getShortClassName(controllerClassTemp.getClass()),
//                        methodTemp.getName(),
//                        parentAuthKey,
//                        methodTemp.getAnnotation(CheckOperateAuth.class));
//                putAuthItem(authItemMapping, methodAuthItem);
//            }
//        }
//        
//        return new HashSet<AuthItem>(authItemMapping.values());
//    }
//    
//    /**
//      * 将权限压入authItemMapping
//      * <功能详细描述>
//      * @param authItemMapping
//      * @param methodAuthItem [参数说明]
//      * 
//      * @return void [返回类型说明]
//      * @exception throws [异常类型] [异常说明]
//      * @see [类、类#方法、类#成员]
//     */
//    private void putAuthItem(Map<String, AuthItem> authItemMapping,
//            AuthItem methodAuthItem) {
//        if (authItemMapping.containsKey(methodAuthItem.getId())) {
//            if (!(authItemMapping.get(methodAuthItem.getId()) instanceof AuthItemImpl)) {
//                return;
//            }
//            AuthItemImpl realAuthItem = (AuthItemImpl) authItemMapping.get(methodAuthItem.getId());
//            if (StringUtils.isEmpty(realAuthItem.getName())
//                    || realAuthItem.getId().equals(realAuthItem.getName())) {
//                realAuthItem.setName(methodAuthItem.getName());
//            } else if (StringUtils.isEmpty(realAuthItem.getParentId())) {
//                realAuthItem.setParentId(methodAuthItem.getParentId());
//            } else if (StringUtils.isEmpty(realAuthItem.getDescription())) {
//                realAuthItem.setDescription(methodAuthItem.getDescription());
//            }
//        } else {
//            authItemMapping.put(methodAuthItem.getId(), methodAuthItem);
//        }
//    }
//    
//    /**
//     * 根据菜单创建权限项<br/>
//     *<功能详细描述>
//     * @param parentMenuAuthItem
//     * @param menuItem
//     * @return [参数说明]
//     * 
//     * @return AuthItem [返回类型说明]
//     * @exception throws [异常类型] [异常说明]
//     * @see [类、类#方法、类#成员]
//    */
//    private AuthItem createAuthItem(String beanSimpleName, String methodName,
//            String parentAuthKey, CheckOperateAuth checkOperateAuthAnno) {
//        AssertUtils.notNull(checkOperateAuthAnno,
//                "checkOperateAuthAnno is null");
//        
//        //根据菜单生成对应权限项
//        AuthItemImpl authItem = new AuthItemImpl();
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
//        
//        return authItem;
//    }
//    
//    /**
//     * @return 返回 basePackages
//     */
//    public String getBasePackages() {
//        return basePackages;
//    }
//    
//    /**
//     * @param 对basePackages进行赋值
//     */
//    public void setBasePackages(String basePackages) {
//        this.basePackages = basePackages;
//    }
//    
//    /**
//     * @param 对order进行赋值
//     */
//    public void setOrder(int order) {
//        this.order = order;
//    }
//}
