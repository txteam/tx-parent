/*
 * 描          述:  <描述>
 * 修  改   人:  PengQingyang
 * 修改时间:  2012-12-14
 * <修改描述:>
 */
package com.tx.component.auth.context.loader.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.Ordered;
import org.springframework.core.io.Resource;

import com.tx.component.auth.context.AuthTypeItemContext;
import com.tx.component.auth.context.loader.AuthLoader;
import com.tx.component.auth.exceptions.AuthContextInitException;
import com.tx.component.auth.model.AuthItem;
import com.tx.component.auth.model.AuthItemImpl;
import com.tx.core.exceptions.util.AssertUtils;

/**
 * 通过xml配置文件加载权限项<br/>
 * 1、通过指定权限配置的资源，加载权限资源后，生成系统权限集合
 * 
 * @author  PengQingyang
 * @version  [版本号, 2012-12-14]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class XmlAuthLoader implements AuthLoader, ApplicationContextAware {
    
    /** 日志记录器 */
    private static final Logger logger = LoggerFactory.getLogger(XmlAuthLoader.class);
    
    /** 权限类型节点名  */
    private final static String AUTHTYPE_ELEMENT_NAME = "authType";
    
    /** 权限项名 */
    private final static String AUTH_ELEMENT_NAME = "auth";
    
    /** 权限节点authType */
    private final static String AUTH_ELEMENT_ATTR_AUTHTYPE = "authType";
    
    /** 权限节点isValid */
    private final static String AUTH_ELEMENT_ATTR_ISVALID = "valid";
    
    /** 节点isEditAble */
    private final static String AUTH_ELEMENT_ATTR_ISEDITABLE = "editAble";
    
    /** 节点isEditAble */
    private final static String AUTH_ELEMENT_ATTR_ISVIRTUAL = "virtual";
    
    /** 节点id */
    private final static String ELEMENT_ATTR_ID = "key";
    
    /** 节点name */
    private final static String ELEMENT_ATTR_NAME = "name";
    
    /** 节点description */
    private final static String ELEMENT_ATTR_DESCRIPTION = "description";
    
    /** 节点isViewAble */
    private final static String ELEMENT_ATTR_ISVIEWABLE = "viewAble";
    
    /** 节点isConfigAble */
    private final static String ELEMENT_ATTR_ISCONFIGABLE = "configAble";
    
    /** Auth的节点属性集合 */
    private final static Set<String> AUTH_ELEMENT_ATTR_SET = new HashSet<>(
            Arrays.asList(AUTH_ELEMENT_ATTR_AUTHTYPE,
                    AUTH_ELEMENT_ATTR_ISVALID,
                    AUTH_ELEMENT_ATTR_ISEDITABLE,
                    AUTH_ELEMENT_ATTR_ISVIRTUAL,
                    ELEMENT_ATTR_ID,
                    ELEMENT_ATTR_NAME,
                    ELEMENT_ATTR_DESCRIPTION,
                    ELEMENT_ATTR_ISVIEWABLE,
                    ELEMENT_ATTR_ISCONFIGABLE));
    
    private ApplicationContext applicationContext;
    
    /** 权限配置地址 */
    private String[] authConfigLocaions = new String[] { "classpath*:authcontext/*_auth_config.xml" };
    
    /** 加载器运行顺序 */
    private int order = Ordered.HIGHEST_PRECEDENCE + 1;
    
    /** <默认构造函数> */
    public XmlAuthLoader() {
        super();
    }
    
    /** <默认构造函数> */
    public XmlAuthLoader(String[] authConfigLocaions) {
        super();
        if (!ArrayUtils.isEmpty(authConfigLocaions)) {
            this.authConfigLocaions = authConfigLocaions;
        }
    }
    
    /** <默认构造函数> */
    public XmlAuthLoader(ApplicationContext applicationContext,
            String[] authConfigLocaions) {
        super();
        if (!ArrayUtils.isEmpty(authConfigLocaions)) {
            this.authConfigLocaions = authConfigLocaions;
        }
        this.applicationContext = applicationContext;
    }
    
    /**
     * @param applicationContext
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        this.applicationContext = applicationContext;
    }
    
    /**
     * @return
     */
    @Override
    public Set<AuthItem> loadAuthItems(
            Map<String, AuthItem> sourceAuthItemMapping) {
        Set<AuthItem> authItemSet = new HashSet<AuthItem>(
                loadAuthItemConfig().values());
        return authItemSet;
    }
    
    /**
     * 加载权限项配置<br/>
     * <功能详细描述><br/> 
     * [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    private Map<String, AuthItemImpl> loadAuthItemConfig() {
        // 加载配置资源列表
        List<Resource> configResourceList = null;
        try {
            configResourceList = getConfigResourceList();
        } catch (Exception e) {
            logger.error("加载权限配置异常信息配置路径为:{}异常信息{}",
                    this.authConfigLocaions,
                    e.toString());
            logger.error("加载权限配置失败", e);
            throw new AuthContextInitException("权限配置加载异常.", e);
        }
        
        // 初始化局部权限映射以及，根权限树
        Map<String, AuthItemImpl> authItemMap = new HashMap<String, AuthItemImpl>();
        
        // 配置权限列表
        if (configResourceList == null || configResourceList.size() == 0) {
            return authItemMap;
        }
        
        // 加载配置资源集
        for (Resource resourceTemp : configResourceList) {
            SAXReader saxReader = new SAXReader();
            InputStream io = null;
            try {
                io = resourceTemp.getInputStream();
                Document doc = saxReader.read(io);
                Element rootElement = doc.getRootElement();
                
                //处理auth节点
                @SuppressWarnings("unchecked")
                List<Element> authElList = rootElement.elements(AUTH_ELEMENT_NAME);
                //根据配置资源加载权限
                loadAuthItemConfigFromAuthElement(null,
                        null,
                        authItemMap,
                        authElList);
                
                //处理authType节点
                @SuppressWarnings("unchecked")
                List<Element> authTypeElList = rootElement.elements(AUTHTYPE_ELEMENT_NAME);
                //加载权限项
                loadAuthItemConfigFromAuthTypeElement(authItemMap,
                        authTypeElList);
            } catch (Exception e) {
                throw new AuthContextInitException("权限配置加载异常.", e);
            } finally {
                IOUtils.closeQuietly(io);
            }
        }
        
        return authItemMap;
    }
    
    /**
      * 加载权限项配置
      * <功能详细描述>
      * @param authItemMap
      * @param authTypeElList [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    private void loadAuthItemConfigFromAuthTypeElement(
            Map<String, AuthItemImpl> authItemMap, List<Element> authTypeElList) {
        if (CollectionUtils.isEmpty(authTypeElList)) {
            return;
        }
        
        for (Element authTypeElTemp : authTypeElList) {
            // 读取权限配置的属性值
            String authType = authTypeElTemp.attributeValue(ELEMENT_ATTR_ID);
            String name = authTypeElTemp.attributeValue(ELEMENT_ATTR_NAME);
            String description = authTypeElTemp.attributeValue(ELEMENT_ATTR_DESCRIPTION);
            Boolean isViewAbleObj = BooleanUtils.toBooleanObject(authTypeElTemp.attributeValue(ELEMENT_ATTR_ISVIEWABLE));
            Boolean isConfigAbleObj = BooleanUtils.toBooleanObject(authTypeElTemp.attributeValue(ELEMENT_ATTR_ISCONFIGABLE));
            
            boolean isViewAble = isViewAbleObj != null ? isViewAbleObj.booleanValue()
                    : true;
            boolean isConfigAble = isConfigAbleObj != null ? isConfigAbleObj.booleanValue()
                    : true;
            
            //注册权限类型
            AuthTypeItemContext.getContext().registeAuthTypeItem(authType,
                    name,
                    description,
                    isViewAble,
                    isConfigAble);
            
            //权限ElTemp
            @SuppressWarnings("unchecked")
            List<Element> authTypeElListTemp = authTypeElTemp.elements(AUTH_ELEMENT_NAME);
            // 迭代生成子权限
            loadAuthItemConfigFromAuthElement(authType,
                    null,
                    authItemMap,
                    authTypeElListTemp);
        }
    }
    
    /**
     * 加载权限配置项<br/>
     * <功能详细描述><br/>
     * 
     * @param authItemMap
     * @param parentAuthItem
     * @param parentElement
     *            [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    private void loadAuthItemConfigFromAuthElement(String parentElAuthType,
            AuthItemImpl parentAuthItem, Map<String, AuthItemImpl> authItemMap,
            List<Element> authElList) {
        if (CollectionUtils.isEmpty(authElList)) {
            return;
        }
        
        // 循环子权限列表
        for (Element authElTemp : authElList) {
            // 读取权限配置的属性值
            String id = authElTemp.attributeValue(ELEMENT_ATTR_ID);
            String name = authElTemp.attributeValue(ELEMENT_ATTR_NAME);
            String description = authElTemp.attributeValue(ELEMENT_ATTR_DESCRIPTION);
            Boolean isValidObj = BooleanUtils.toBooleanObject(authElTemp.attributeValue(AUTH_ELEMENT_ATTR_ISVALID));
            Boolean isViewAbleObj = BooleanUtils.toBooleanObject(authElTemp.attributeValue(ELEMENT_ATTR_ISVIEWABLE));
            Boolean isEditAbleObj = BooleanUtils.toBooleanObject(authElTemp.attributeValue(AUTH_ELEMENT_ATTR_ISEDITABLE));
            Boolean isConfigAbleObj = BooleanUtils.toBooleanObject(authElTemp.attributeValue(ELEMENT_ATTR_ISCONFIGABLE));
            Boolean isVirtualObj = BooleanUtils.toBooleanObject(authElTemp.attributeValue(AUTH_ELEMENT_ATTR_ISVIRTUAL));
            
            boolean isValid = isValidObj != null ? isValidObj.booleanValue()
                    : true;
            boolean isViewAble = isViewAbleObj != null ? isViewAbleObj.booleanValue()
                    : true;
            boolean isEditAble = isEditAbleObj != null ? isEditAbleObj.booleanValue()
                    : false;
            boolean isConfigAble = isConfigAbleObj != null ? isConfigAbleObj.booleanValue()
                    : true;
            boolean isVirtual = isVirtualObj != null ? isVirtualObj.booleanValue()
                    : false;
            
            String authType = authElTemp.attributeValue(AUTH_ELEMENT_ATTR_AUTHTYPE);
            if (StringUtils.isEmpty(authType)) {
                authType = parentElAuthType;
            }
            AssertUtils.notEmpty("xml resource.authType",
                    "auth:{} authType is empty.",
                    id);
            
            //向权限类型容器中注册权限类型
            AuthTypeItemContext.getContext().registeAuthTypeItem(authType);
            
            AuthItemImpl newAuthItem = null;
            if (authItemMap.containsKey(id)) {
                // 如果对应权限已经存在则获取对应权限
                newAuthItem = authItemMap.get(id);
            } else {
                // 如果该权限原不存在则新生成
                newAuthItem = createChildAuthItem(parentAuthItem,
                        id,
                        authType,
                        name,
                        description,
                        isValid,
                        isViewAble,
                        isEditAble,
                        isConfigAble,
                        isVirtual);
            }
            
            //加载属性点属性
            Map<String, String> dataTemp = new HashMap<>();
            @SuppressWarnings("unchecked")
            List<Attribute> attributes = authElTemp.attributes();
            for(Attribute attrTemp : attributes){
                if(AUTH_ELEMENT_ATTR_SET.contains(attrTemp.getName())){
                    continue;
                }
                dataTemp.put(attrTemp.getName(), attrTemp.getValue());
            }
            newAuthItem.getData().putAll(dataTemp);
            authItemMap.put(id, newAuthItem);
            
            //权限ElTemp
            @SuppressWarnings("unchecked")
            List<Element> authTypeElListTemp = authElTemp.elements(AUTH_ELEMENT_NAME);
            // 迭代生成子权限
            loadAuthItemConfigFromAuthElement(newAuthItem.getAuthType(),
                    newAuthItem,
                    authItemMap,
                    authTypeElListTemp);
        }
    }
    
    /**
     * 创建权限列表<br/>
     * 1、根据父级权限创建子权限<br/>
     * 2、如果父权限为抽象权限，子权限如果没有指定权限类型，则可根据父权限设定权限类型<br/>
      * @param parentAuthItem
      * @param id
      * @param authType
      * @param name
      * @param description
      * @param isValid
      * @param isViewAble
      * @param isEditAble
      * @return [参数说明]
      * 
      * @return AuthItemImpl [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    private AuthItemImpl createChildAuthItem(AuthItem parentAuthItem,
            String id, String authType, String name, String description,
            boolean isValid, boolean isViewAble, boolean isEditAble,
            boolean isConfigAble, boolean isVirtual) {
        //创建权限实体
        AuthItemImpl authItem = new AuthItemImpl();
        authItem.setId(id);
        authItem.setName(name);
        authItem.setDescription(description);
        authItem.setAuthType(authType);
        
        //如果父权限不为空
        if (parentAuthItem != null) {
            authItem.setParentId(parentAuthItem.getId());
            
            if (StringUtils.isEmpty(authType)) {
                authItem.setAuthType(parentAuthItem.getAuthType());
            }
        }
        
        authItem.setValid(isValid);
        authItem.setViewAble(isViewAble);
        authItem.setEditAble(isEditAble);
        authItem.setConfigAble(isConfigAble);
        authItem.setVirtual(isVirtual);
        
        //断言新生成的权限项，权限类型不能为空
        AssertUtils.notEmpty(authItem.getAuthType(),
                "auth:{} authType is empty",
                id);
        
        return authItem;
    }
    
    /**
     * 获取权限配置资源列表<br/>
     * 1、根据配置路径 authConfigLocaions 加载资源 <br/>
     * <功能详细描述>
     * 
     * @return
     * @throws IOException
     *             [参数说明]
     * 
     * @return List<Resource> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    private List<Resource> getConfigResourceList() throws IOException {
        List<Resource> configResourceList = new ArrayList<Resource>();
        for (String location : this.authConfigLocaions) {
            Resource[] resources = this.applicationContext.getResources(location);
            if (resources == null || resources.length == 0) {
                continue;
            }
            for (Resource resourceTemp : resources) {
                if (!resourceTemp.exists()) {
                    continue;
                }
                configResourceList.add(resourceTemp);
            }
        }
        return configResourceList;
    }
    
    /**
     * @return 返回 authConfigLocaions
     */
    public String[] getAuthConfigLocaions() {
        return authConfigLocaions;
    }
    
    /**
     * @param 对authConfigLocaions进行赋值
     */
    public void setAuthConfigLocaions(String[] authConfigLocaions) {
        this.authConfigLocaions = authConfigLocaions;
    }
    
    /**
     * @return
     */
    @Override
    public int getOrder() {
        return this.order;
    }
}
