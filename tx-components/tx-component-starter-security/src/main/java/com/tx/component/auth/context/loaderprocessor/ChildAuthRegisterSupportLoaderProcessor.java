///*
// * 描          述:  <描述>
// * 修  改   人:  Administrator
// * 修改时间:  2015年11月7日
// * <修改描述:>
// */
//package com.tx.component.auth.context.loaderprocessor;
//
//import java.util.HashSet;
//import java.util.Map;
//import java.util.Set;
//
//import org.apache.commons.collections.CollectionUtils;
//import org.apache.commons.collections.MapUtils;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.beans.BeansException;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.ApplicationContextAware;
//import org.springframework.util.ClassUtils;
//
//import com.tx.component.auth.context.AuthItemLoaderProcessor;
//import com.tx.component.auth.context.loaderprocessor.childauth.ChildAuthRegister;
//import com.tx.component.auth.exception.AuthContextInitException;
//import com.tx.component.auth.model.Auth;
//import com.tx.core.util.MessageUtils;
//
///**
// * 子数据权限权限加载器处理器<br/>
// * <功能详细描述>
// * 
// * @author  Administrator
// * @version  [版本号, 2015年11月7日]
// * @see  [相关类/方法]
// * @since  [产品/模块版本]
// */
//public class ChildAuthRegisterSupportLoaderProcessor implements
//        AuthItemLoaderProcessor, ApplicationContextAware {
//    
//    private static final String CHILD_AUTH_REGISTER = "child_auth_register";
//    
//    private ApplicationContext applicationContext;
//    
//    /**
//     * @return
//     */
//    @Override
//    public int getOrder() {
//        return 0;
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
//                    || !authTemp.getData().containsKey(CHILD_AUTH_REGISTER)
//                    || StringUtils.isEmpty(authTemp.getData()
//                            .get(CHILD_AUTH_REGISTER))) {
//                continue;
//            }
//            String registerName = authTemp.getData().get(CHILD_AUTH_REGISTER);
//            ChildAuthRegister registerTemp = null;
//            registerTemp = getChildAuthRegisterByRegisterName(registerName,
//                    registerTemp);
//            
//            Set<Auth> resSet = registerTemp.loadAuthItems(authTemp);
//            if (!CollectionUtils.isEmpty(resSet)) {
//                newAuthItemSet.addAll(resSet);
//            }
//        }
//        return newAuthItemSet;
//    }
//    
//    /**
//      * 根据register的name获取其实例<br/>
//      *     先利用registerName在applicationContext中获取
//      *     如果获取不到，直接获取Class然后利用无参构造函数进行构建<br/>
//      * <功能详细描述>
//      * @param registerName
//      * @param registerTemp
//      * @return
//      * @throws LinkageError [参数说明]
//      * 
//      * @return ChildAuthRegister [返回类型说明]
//      * @exception throws [异常类型] [异常说明]
//      * @see [类、类#方法、类#成员]
//     */
//    private ChildAuthRegister getChildAuthRegisterByRegisterName(
//            String registerName, ChildAuthRegister registerTemp)
//            throws LinkageError {
//        if (this.applicationContext.containsBean(registerName)) {
//            Object registerObj = this.applicationContext.getBean(registerName);
//            if (registerObj instanceof ChildAuthRegister) {
//                registerTemp = (ChildAuthRegister) registerObj;
//            }
//        } else {
//            try {
//                Class<?> registerType = ClassUtils.forName(registerName,
//                        this.getClass().getClassLoader());
//                Object registerObj = com.tx.core.util.ObjectUtils.newInstance(registerType);
//                if (registerObj instanceof ChildAuthRegister) {
//                    registerTemp = (ChildAuthRegister) registerObj;
//                }
//            } catch (Exception e) {
//                //doNothing
//            }
//        }
//        if (registerTemp == null) {
//            throw new AuthContextInitException(
//                    MessageUtils.format("register:[{}] not exist.register not regist to springApplicationContext "
//                            + "or not instance of ChildAuthRegister.",
//                            new Object[] { registerName }));
//        }
//        return registerTemp;
//    }
//    
//    /**
//     * @param arg0
//     * @throws BeansException
//     */
//    @Override
//    public void setApplicationContext(ApplicationContext applicationContext)
//            throws BeansException {
//        this.applicationContext = applicationContext;
//    }
//}
