/*
 * Copyright 2005-2017 cqtianxin.com. All rights reserved.
 * Support: http://www.cqtianxin.com
 * License: http://www.cqtianxin.com/license
 */
package com.tx.component.plugin.loginplugin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tx.component.plugin.context.Plugin;

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
    
    /**
     * @return
     */
    @Override
    public String getCatalog() {
        return "login";
    }
    
    /**
     * 获取第三方的用户名<br/>
     * <功能详细描述>
     * @param request
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public String getSocialUsername(HttpServletRequest request) {
        return "";
    }
    
    /**
     * 获取第三方的头像ImageUrl<br/>
     * <功能详细描述>
     * @param request
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public String getHeadImgUrl(HttpServletRequest request) {
        return "";
    }
    
    /**
     * 是否支持当前请求<br/>
     * <功能详细描述>
     * @param request
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public boolean supports(HttpServletRequest request) {
        return true;
    }
    
    /**
     * 获取唯一ID<br/>
     * <功能详细描述>
     * @param request
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public abstract String getUniqueId(HttpServletRequest request);
    
    /**
     * 获取客户信息<br/>
     * <功能详细描述>
     * @param uniqueId
     * @param accessToken
     * @param request
     * @param response [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public abstract void getUserInfo(String uniqueId, String accessToken,
            HttpServletRequest request, HttpServletResponse response);
    
    /**
     * 判断是否登录成功<br/>
     * <功能详细描述>
     * @param request
     * @param response
     * @return
     * @throws Exception [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public abstract boolean isSignInSuccess(HttpServletRequest request,
            HttpServletResponse response) throws Exception;
    
    //    /**
    //     * 登录前处理
    //     *
    //     * @param loginPlugin  登录插件
    //     * @param extra        附加内容
    //     * @param request      HttpServletRequest
    //     * @param response     HttpServletResponse
    //     * @param modelAndView ModelAndView
    //     * @throws Exception
    //     */
    //    public void preSignInHandle(LoginPlugin loginPlugin, String extra,
    //            HttpServletRequest request, HttpServletResponse response,
    //            ModelAndView modelAndView) throws Exception {
    //        modelAndView.setViewName(
    //                "redirect:" + loginPlugin.getSignInUrl(loginPlugin));
    //    }
    //    
    //    /**
    //     * 登录处理
    //     *
    //     * @param loginPlugin  登录插件
    //     * @param extra        附加内容
    //     * @param request      HttpServletRequest
    //     * @param response     HttpServletResponse
    //     * @param modelAndView ModelAndView
    //     * @throws Exception
    //     */
    //    public abstract void signInHandle(LoginPlugin loginPlugin, String extra,
    //            HttpServletRequest request, HttpServletResponse response,
    //            ModelAndView modelAndView) throws Exception;
    //    
    //    /**
    //     * 登录后处理
    //     *
    //     * @param loginPlugin    登录插件
    //     * @param socialUser     社会化用户
    //     * @param extra          附加内容
    //     * @param isLoginSuccess 是否登录成功
    //     * @param request        HttpServletRequest
    //     * @param response       HttpServletResponse
    //     * @param modelAndView   ModelAndView
    //     * @throws Exception
    //     */
    //    public void postSignInHandle(LoginPlugin loginPlugin, SocialUser socialUser,
    //            String extra, boolean isLoginSuccess, HttpServletRequest request,
    //            HttpServletResponse response, ModelAndView modelAndView)
    //            throws Exception {
    //        if (socialUser != null && socialUser == null) {
    //            //TODO 创建会员用户
    //        }
    //        
    //        if (socialUser != null && socialUser.getUser() != null) {
    //            String redirectUrl;
    //            SavedRequest savedRequest = org.apache.shiro.web.util.WebUtils
    //                    .getAndClearSavedRequest(request);
    //            String contextPath = request.getContextPath();
    //            if (savedRequest != null) {
    //                redirectUrl = StringUtils.substringAfter(
    //                        savedRequest.getRequestUrl(), contextPath);
    //            } else {
    //                String sessionRedirectUrl = (String) request.getSession()
    //                        .getAttribute("redirectUrl");
    //                if (sessionRedirectUrl != null) {
    //                    redirectUrl = sessionRedirectUrl;
    //                    request.getSession().removeAttribute("redirectUrl");
    //                } else {
    //                    redirectUrl = memberIndex;
    //                }
    //            }
    //            modelAndView.setViewName("redirect:" + redirectUrl);
    //        } else {
    //            //TODO 自动给用户注册用户
    //            modelAndView.setViewName("redirect:" + memberLogin);
    //        }
    //    }
    //    
    
    //    
    //    /**
    //     * 获取登录前处理URL
    //     *
    //     * @param loginPlugin 登录插件
    //     * @return 登录前处理URL
    //     */
    //    public String getPreSignInUrl(LoginPlugin loginPlugin) {
    //        return getPreSignInUrl(loginPlugin, null);
    //    }
    //    
    //    /**
    //     * 获取登录前处理URL
    //     *
    //     * @param loginPlugin 登录插件
    //     * @param extra       附加内容
    //     * @return 登录前处理URL
    //     */
    //    public String getPreSignInUrl(LoginPlugin loginPlugin, String extra) {
    //        Assert.notNull(loginPlugin);
    //        Assert.hasText(loginPlugin.getId());
    //        
    //        Setting setting = SystemUtils.getSetting();
    //        return setting.getSiteUrl() + "/social_user_login/pre_sign_in_"
    //                + loginPlugin.getId()
    //                + (StringUtils.isNotEmpty(extra) ? "_" + extra : "");
    //    }
    //    
    //    /**
    //     * 获取登录处理URL
    //     *
    //     * @param loginPlugin 登录插件
    //     * @return 登录处理URL
    //     */
    //    public String getSignInUrl(LoginPlugin loginPlugin) {
    //        return getSignInUrl(loginPlugin, null);
    //    }
    //    
    //    /**
    //     * 获取登录处理URL
    //     *
    //     * @param loginPlugin 登录插件
    //     * @param extra       附加内容
    //     * @return 登录处理URL
    //     */
    //    public String getSignInUrl(LoginPlugin loginPlugin, String extra) {
    //        Assert.notNull(loginPlugin);
    //        Assert.hasText(loginPlugin.getId());
    //        
    //        Setting setting = SystemUtils.getSetting();
    //        return setting.getSiteUrl() + "/social_user_login/sign_in_"
    //                + loginPlugin.getId()
    //                + (StringUtils.isNotEmpty(extra) ? "_" + extra : "");
    //    }
    //    
    //    /**
    //     * 获取登录后处理URL
    //     *
    //     * @param loginPlugin 登录插件
    //     * @return 登录后处理URL
    //     */
    //    public String getPostSignInUrl(LoginPlugin loginPlugin) {
    //        return getPostSignInUrl(loginPlugin, null);
    //    }
    //    
    //    /**
    //     * 获取登录后处理URL
    //     *
    //     * @param loginPlugin 登录插件
    //     * @param extra       附加内容
    //     * @return 登录后处理URL
    //     */
    //    public String getPostSignInUrl(LoginPlugin loginPlugin, String extra) {
    //        Assert.notNull(loginPlugin);
    //        Assert.hasText(loginPlugin.getId());
    //        
    //        Setting setting = SystemUtils.getSetting();
    //        return setting.getSiteUrl() + "/social_user_login/post_sign_in_"
    //                + loginPlugin.getId()
    //                + (StringUtils.isNotEmpty(extra) ? "_" + extra : "");
    //    }
    //    
    
    //    
    
    //    
    
    //    
    //    
    //    public String getAccessToken(HttpServletRequest request) {
    //        String access_token = (String) request.getAttribute("access_token");
    //        return StringUtils.isNotEmpty(access_token) ? access_token : null;
    //    }
    //    
    
    //    /**
    //     * 连接Map值
    //     *
    //     * @param map              Map
    //     * @param prefix           前缀
    //     * @param suffix           后缀
    //     * @param separator        连接符
    //     * @param ignoreEmptyValue 忽略空值
    //     * @param ignoreKeys       忽略Key
    //     * @return 字符串
    //     */
    //    protected String joinValue(Map<String, Object> map, String prefix,
    //            String suffix, String separator, boolean ignoreEmptyValue,
    //            String... ignoreKeys) {
    //        List<String> list = new ArrayList<>();
    //        if (map != null) {
    //            for (Map.Entry<String, Object> entry : map.entrySet()) {
    //                String key = entry.getKey();
    //                String value = ConvertUtils.convert(entry.getValue());
    //                if (StringUtils.isNotEmpty(key)
    //                        && !ArrayUtils.contains(ignoreKeys, key)
    //                        && (!ignoreEmptyValue
    //                                || StringUtils.isNotEmpty(value))) {
    //                    list.add(value != null ? value : "");
    //                }
    //            }
    //        }
    //        return (prefix != null ? prefix : "")
    //                + StringUtils.join(list, separator)
    //                + (suffix != null ? suffix : "");
    //    }
    //  /**
    //     * 连接Map键值对
    //     *
    //     * @param map              Map
    //     * @param prefix           前缀
    //     * @param suffix           后缀
    //     * @param separator        连接符
    //     * @param ignoreEmptyValue 忽略空值
    //     * @param ignoreKeys       忽略Key
    //     * @return 字符串
    //     */
    //    protected String joinKeyValue(Map<String, Object> map, String prefix,
    //            String suffix, String separator, boolean ignoreEmptyValue,
    //            String... ignoreKeys) {
    //        List<String> list = new ArrayList<>();
    //        if (map != null) {
    //            for (Map.Entry<String, Object> entry : map.entrySet()) {
    //                String key = entry.getKey();
    //                String value = ConvertUtils.convert(entry.getValue());
    //                if (StringUtils.isNotEmpty(key)
    //                        && !ArrayUtils.contains(ignoreKeys, key)
    //                        && (!ignoreEmptyValue
    //                                || StringUtils.isNotEmpty(value))) {
    //                    list.add(key + "=" + (value != null ? value : ""));
    //                }
    //            }
    //        }
    //        return (prefix != null ? prefix : "")
    //                + StringUtils.join(list, separator)
    //                + (suffix != null ? suffix : "");
    //    }
}