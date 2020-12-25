/*
 * Copyright 2005-2017 cqtianxin.com. All rights reserved.
 * Support: http://www.cqtianxin.com
 * License: http://www.cqtianxin.com/license
 */
package com.tx.component.social;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;

import com.tx.component.plugin.context.Plugin;
import com.tx.component.security.exception.SocialAuthorizeException;
import com.tx.component.social.model.SocialAccessToken;
import com.tx.component.social.model.SocialUserInfo;

/**
 * 登陆插件<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2019年12月4日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public abstract class LoginPlugin<CONFIG extends LoginPluginConfig>
        extends Plugin<CONFIG> {
    
    /** 日志记录器 */
    protected Logger logger = LoggerFactory.getLogger(LoginPlugin.class);
    
    /**
     * 插件类型<br/>
     * @return
     */
    @Override
    public String getCatalog() {
        return "login";
    }
    
    /**
     * 登陆前置句柄<br/>
     * <功能详细描述>
     * @param request
     * @return [参数说明]
     * 
     * @return ModelAndView [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public ModelAndView loginHandle(HttpServletRequest request) {
        return null;
    }
    
    public ModelAndView login() {
        return null;
    }
    
    public ModelAndView bindHandle(String operatorId,
            HttpServletRequest request) {
        return null;
    }
    
    public ModelAndView bind() {
        return null;
    }
    
    /**
     * 跳转到请求code的页<br/>
     * <功能详细描述>
     * @param viewName
     * @param redirectUrl
     * @param state
     * @param scope
     * @param request
     * @return [参数说明]
     * 
     * @return ModelAndView [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public abstract ModelAndView getCodeHandle(String viewName,
            String redirectUrl, String state, String scope,
            HttpServletRequest request);
    
    /**
     * 获取AccessToken值<br/>
     * <功能详细描述>
     * @param code
     * @param state
     * @param request
     * @return [参数说明]
     * 
     * @return LoginAccessToken [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public abstract SocialAccessToken getAccessToken(String code, String state,
            HttpServletRequest request) throws SocialAuthorizeException;
    
    /**
     * 获取用户在第三方的唯一键<br/>
     * <功能详细描述>
     * @param code
     * @param state
     * @param request
     * @return
     * @throws SocialAuthorizeException [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public abstract String getUniqueId(SocialAccessToken token,
            HttpServletRequest request) throws SocialAuthorizeException;
    
    /**
     * 获取用户第三方用户信息<br/>
     * <功能详细描述>
     * @param code
     * @param state
     * @param request
     * @return
     * @throws SocialAuthorizeException [参数说明]
     * 
     * @return LoginUserInfo [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public abstract SocialUserInfo getUserInfo(SocialAccessToken token,
            HttpServletRequest request) throws SocialAuthorizeException;
    
}