/*
 * 描          述:  <描述>
 * 修  改   人:  PengQingyang
 * 修改时间:  2012-12-14
 * <修改描述:>
 */
package com.tx.component.auth.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.Resource;

import com.tx.component.auth.exceptions.AuthContextInitException;
import com.tx.component.auth.model.AuthItem;
import com.tx.component.auth.model.DefaultAuthItem;
import com.tx.component.auth.service.AuthLoader;

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
    
    /** 权限类型：抽象权限根，抽象权限不配置权限映射  */
    private final static String ABSTRACT_AUTH_END = "_ABS";
    
    /** 权限项名 */
    private final static String AUTH_ELEMENT_NAME = "Auth";
    
    /** 权限节点id */
    private final static String AUTH_ELEMENT_ATTR_ID = "key";
    
    /** 权限节点name */
    private final static String AUTH_ELEMENT_ATTR_NAME = "name";
    
    /** 权限节点authType */
    private final static String AUTH_ELEMENT_ATTR_AUTHTYPE = "authType";
    
    /** 权限节点description */
    private final static String AUTH_ELEMENT_ATTR_DESCRIPTION = "description";
    
    private ApplicationContext applicationContext;
    
    /** 权限配置地址 */
    private String[] authConfigLocaions = new String[] { "classpath:authcontext/*_auth_config.xml" };
    
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
    public Set<AuthItem> loadAuthItems() {
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
    private Map<String, DefaultAuthItem> loadAuthItemConfig() {
        // 加载配置资源列表
        List<Resource> configResourceList = null;
        try {
            configResourceList = getConfigResourceList();
        }
        catch (Exception e) {
            logger.error("加载权限配置异常信息配置路径为:{}异常信息{}",
                    this.authConfigLocaions,
                    e.toString());
            logger.error("加载权限配置失败", e);
            throw new AuthContextInitException("权限配置加载异常.", e);
        }
        
        // 初始化局部权限映射以及，根权限树
        Map<String, DefaultAuthItem> authItemMap = new HashMap<String, DefaultAuthItem>();
        
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
                // 根据配置资源加载权限
                loadAuthItemConfig(null, authItemMap, rootElement);
            }
            catch (Exception e) {
                throw new AuthContextInitException("权限配置加载异常.", e);
            }
            finally {
                IOUtils.closeQuietly(io);
            }
        }
        
        return authItemMap;
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
    private void loadAuthItemConfig(DefaultAuthItem parentAuthItem,
            Map<String, DefaultAuthItem> authItemMap, Element parentElement) {
        @SuppressWarnings("unchecked")
        List<Element> authElList = parentElement.elements(AUTH_ELEMENT_NAME);
        if (CollectionUtils.isEmpty(authElList)) {
            return;
        }
        
        // 循环子权限列表
        for (Element authElTemp : authElList) {
            // 读取权限配置的属性值
            String id = authElTemp.attributeValue(AUTH_ELEMENT_ATTR_ID);
            String name = authElTemp.attributeValue(AUTH_ELEMENT_ATTR_NAME);
            String authType = authElTemp.attributeValue(AUTH_ELEMENT_ATTR_AUTHTYPE);
            String description = authElTemp.attributeValue(AUTH_ELEMENT_ATTR_DESCRIPTION);
            boolean isAbstract = false;
            // 如果为抽象权限，则设置权限id为抽象权限的权限type本身
            if (authType.endsWith(ABSTRACT_AUTH_END)) {
                id = authType;
                isAbstract = true;
            }
            
            DefaultAuthItem newAuthItem = null;
            if (authItemMap.containsKey(id)) {
                // 如果对应权限已经存在则获取对应权限
                newAuthItem = authItemMap.get(id);
            }
            else {
                // 如果该权限原不存在则新生成
                newAuthItem = createChildAuthItem(parentAuthItem,
                        id,
                        authType,
                        name,
                        isAbstract,
                        description);
            }
            authItemMap.put(id, newAuthItem);
            
            // 迭代生成子权限
            loadAuthItemConfig(newAuthItem, authItemMap, authElTemp);
        }
    }
    
    /**
     * 创建权限列表<br/>
     * 1、根据父级权限创建子权限<br/>
     * 2、如果父权限为抽象权限，子权限如果没有指定权限类型，则可根据父权限设定权限类型<br/>
     * @param key
     * @param authType
     * @param name
     * @param description
     * @return [参数说明]
     * 
     * @return List<AuthItem> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    private DefaultAuthItem createChildAuthItem(AuthItem parentAuthItem,
            String id, String authType, String name, boolean isAbstract,
            String description) {
        DefaultAuthItem authItem = new DefaultAuthItem();
        authItem.setId(id);
        authItem.setDescription(description);
        authItem.setAuthType(authType);
        
        if (StringUtils.isEmpty(authType) && parentAuthItem != null) {
            authItem.setParentId(parentAuthItem.getId());
            
            //如果当前节点没有指定权限类型，则根据父权限
            //如果没有指定的权限类型，判断当前权限是否为抽象权限
            if (parentAuthItem.isAbstract()
                    && parentAuthItem.getAuthType().endsWith(ABSTRACT_AUTH_END)) {
                //如果为抽象权限，则设定权限类型为对应抽象权限的子类型
                authItem.setAuthType(parentAuthItem.getAuthType().substring(0,
                        parentAuthItem.getAuthType().length()
                                - ABSTRACT_AUTH_END.length()));
            }
            else {
                //如果不为抽象权限，则子权限默认相同于父权限
                authItem.setAuthType(parentAuthItem.getAuthType());
            }
        }
        
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
    
}
