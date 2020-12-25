///*
// * 描          述:  <描述>
// * 修  改   人:  Administrator
// * 修改时间:  2019年12月11日
// * <修改描述:>
// */
//package com.tx.component.security.plugin.weibo;
//
//import java.util.HashMap;
//import java.util.LinkedHashMap;
//import java.util.Map;
//
//import javax.servlet.http.HttpServletRequest;
//
//import org.apache.commons.lang3.RandomStringUtils;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.stereotype.Component;
//import org.springframework.util.Base64Utils;
//import org.springframework.web.servlet.ModelAndView;
//
//import com.alibaba.fastjson.JSONObject;
//import com.fasterxml.jackson.databind.JsonNode;
//import com.tx.core.exceptions.util.AssertUtils;
//import com.tx.core.util.HttpClientUtils;
//import com.tx.core.util.JsonUtils;
//import com.tx.core.util.MessageUtils;
//import com.tx.core.util.WebUtils;
//import com.tx.local.basicdata.model.SexEnum;
//import com.tx.plugin.login.LoginPlugin;
//import com.tx.plugin.login.LoginPluginUtils;
//import com.tx.plugin.login.exception.SocialAuthorizeException;
//import com.tx.plugin.login.model.LoginAccessToken;
//import com.tx.plugin.login.model.LoginUserInfo;
//
///**
// * 微信登陆插件<br/>
// * OAuth2.0错误响应中的错误码定义如下表所示：
// * https://open.weibo.com/wiki/%E6%8E%88%E6%9D%83%E6%9C%BA%E5%88%B6%E8%AF%B4%E6%98%8E#.E5.8F.96.E6.B6.88.E6.8E.88.E6.9D.83.E5.9B.9E.E8.B0.83.E9.A1.B5
// * https://open.weibo.com/apps/571926088/info/advanced 修改Oauth2授权设置，在该页设置回调页地址
// * 
// * 错误码(error)  错误编号(error_code)    错误描述(error_description)
// * redirect_uri_mismatch   21322   重定向地址不匹配
// * invalid_request 21323   请求不合法
// * invalid_client  21324   client_id或client_secret参数无效
// * invalid_grant   21325   提供的Access Grant是无效的、过期的或已撤销的
// * unauthorized_client 21326   客户端没有权限
// * expired_token   21327   token过期
// * unsupported_grant_type  21328   不支持的 GrantType
// * unsupported_response_type   21329   不支持的 ResponseType
// * access_denied   21330   用户或授权服务器拒绝授予数据访问权限
// * temporarily_unavailable 21331   服务暂时无法访问
// * appkey permission denied    21337   应用权限不足
// * 
// * @author  Administrator
// * @version  [版本号, 2019年12月11日]
// * @see  [相关类/方法]
// * @since  [产品/模块版本]
// */
//@Component("wbLoginPlugin")
//public class WBLoginPlugin extends LoginPlugin<WBLoginPluginConfig> {
//    
//    /** code请求URL */
//    private static final String CODE_REQUEST_URL = "https://api.weibo.com/oauth2/authorize";
//    
//    /** uid请求URL */
//    private static final String UID_REQUEST_URL = "https://api.weibo.com/oauth2/access_token";
//    
//    /** userInfo请求URL */
//    private static final String USER_INFO_REQUEST_URL = "https://api.weibo.com/2/users/show.json";
//    
//    /** "状态"属性名称 */
//    private static final String STATE_ATTRIBUTE_NAME = WBLoginPlugin.class
//            .getName() + ".STATE";
//    
//    /** "状态"属性名称 */
//    private static final String REDIRECT_URL_ATTRIBUTE_NAME = WBLoginPlugin.class
//            .getName() + ".REDIRECT_URL";
//    
//    /**
//     * @return
//     */
//    @Override
//    public String getName() {
//        return "微博登陆插件";
//    }
//    
//    /**
//     * @return
//     */
//    @Override
//    public String getVersion() {
//        return "1.0";
//    }
//    
//    /**
//     * @return
//     */
//    @Override
//    public String getPrefix() {
//        return "plugin.login.wb";
//    }
//    
//    /**
//     * @param operatorId
//     * @param request
//     * @return
//     */
//    @Override
//    public ModelAndView bindHandle(String operatorId,
//            HttpServletRequest request) {
//        //将当前操作人员写入会话中
//        LoginPluginUtils.pushUserId(operatorId);
//        
//        //state
//        String state = Base64Utils.encodeToString(
//                RandomStringUtils.randomAlphabetic(30).getBytes());
//        //scope信息
//        String scope = "snsapi_userinfo";
//        //redirectUri
//        String redirectUri = WebUtils.getBaseUrl(request)
//                + "loginplugin/callback/WB";
//        ModelAndView mv = getCodeHandle("loginplugin/signIn",
//                redirectUri,
//                state,
//                scope,
//                request);
//        return mv;
//    }
//    
//    /**
//     * @param request
//     * @return
//     */
//    @Override
//    public ModelAndView loginHandle(HttpServletRequest request) {
//        //state
//        String state = Base64Utils.encodeToString(
//                RandomStringUtils.randomAlphabetic(30).getBytes());
//        //scope信息
//        String scope = "snsapi_userinfo";
//        //redirectUri
//        String redirectUri = WebUtils.getBaseUrl(request)
//                + "loginplugin/callback/WB";
//        ModelAndView mv = getCodeHandle("loginplugin/signIn",
//                redirectUri,
//                state,
//                scope,
//                request);
//        return mv;
//    }
//    
//    /**
//     * 获取code的handle<br/>
//     * @param viewName
//     * @param redirectUri
//     * @param scope
//     * @param request
//     * @return
//     */
//    @Override
//    public ModelAndView getCodeHandle(String viewName, String redirectUri,
//            String state, String scope, HttpServletRequest request) {
//        AssertUtils.notEmpty(viewName, "viewName is empty.");
//        AssertUtils.notEmpty(redirectUri, "redirectUri is empty.");
//        
//        //写入state值
//        LoginPluginUtils.pushAttribute(STATE_ATTRIBUTE_NAME, state);
//        LoginPluginUtils.pushAttribute(REDIRECT_URL_ATTRIBUTE_NAME,
//                redirectUri);
//        
//        WBLoginPluginConfig config = getConfig();
//        ModelAndView mview = new ModelAndView();
//        
//        Map<String, Object> parameterMap = new LinkedHashMap<>();
//        parameterMap.put("response_type", "code");
//        parameterMap.put("client_id", config.getAppKey());
//        parameterMap.put("redirect_uri", redirectUri);
//        parameterMap.put("state", state);
//        
//        mview.addObject("requestUrl", CODE_REQUEST_URL);
//        mview.addObject("parameterMap", parameterMap);
//        mview.setViewName(viewName);
//        return mview;
//    }
//    
//    /**
//     * 获取AccessToken<br/>
//     *      https://open.weibo.com/wiki/Oauth/access_token
//     * <功能详细描述>
//     * @param request
//     * @param response
//     * @return
//     * @throws Exception [参数说明]
//     * 
//     * @return boolean [返回类型说明]
//     * @exception throws [异常类型] [异常说明]
//     * @see [类、类#方法、类#成员]
//     */
//    @Override
//    public LoginAccessToken getAccessToken(String code, String state,
//            HttpServletRequest request) throws SocialAuthorizeException {
//        if (StringUtils.isEmpty(code)) {
//            throw new SocialAuthorizeException("code is empty.");
//        }
//        if (StringUtils.isEmpty(state)) {
//            throw new SocialAuthorizeException("state is empty.");
//        }
//        String sessionState = LoginPluginUtils
//                .popAttribute(STATE_ATTRIBUTE_NAME);
//        if (!StringUtils.equals(state, sessionState)) {
//            throw new SocialAuthorizeException(MessageUtils.format(
//                    "state value is invalid.request_state:{}  session_state:{}",
//                    state,
//                    sessionState));
//        }
//        //从会话中获取redirectUri
//        String redirectUri = LoginPluginUtils
//                .popAttribute(REDIRECT_URL_ATTRIBUTE_NAME);
//        
//        WBLoginPluginConfig config = getConfig();
//        Map<String, String> parameterMap = new HashMap<>();
//        parameterMap.put("grant_type", "authorization_code");
//        String appKey = config.getAppKey();
//        String appSecret = config.getAppSecret();
//        parameterMap.put("client_id", appKey);
//        parameterMap.put("client_secret", appSecret);
//        parameterMap.put("code", code);
//        parameterMap.put("redirect_uri", redirectUri);
//        String result = HttpClientUtils.post(UID_REQUEST_URL, parameterMap);
//        logger.info(
//                "--- getAccessToken:{client_id:{},client_secret:{},code:{},result:{}}",
//                appKey,
//                appSecret,
//                code,
//                result);
//        //{"access_token":"2.00zleWPB01Gkhc5cc927db85Da_qIB","remind_in":"157679999","expires_in":157679999,"uid":"1145561103","isRealName":"true"}
//        
//        JsonNode node = JsonUtils.toTree(result);
//        String accessToken = node.get("access_token").textValue();
//        String uid = node.get("uid").textValue();
//        LoginAccessToken token = new LoginAccessToken();
//        token.setUniqueId(uid);
//        token.setAccessToken(accessToken);
//        token.setExpiresIn(node.get("expires_in").longValue());
//        token.getAttributeJSONObject()
//                .put("remind_in", node.get("remind_in").longValue());
//        token.getAttributeJSONObject()
//                .put("isRealName", node.get("isRealName").booleanValue());
//        if (StringUtils.isEmpty(token.getUniqueId())) {
//            throw new SocialAuthorizeException("获取UniqueId失败.");
//        }
//        if (StringUtils.isEmpty(token.getAccessToken())) {
//            throw new SocialAuthorizeException("获取AccessToken失败.");
//        }
//        return token;
//    }
//    
//    /**
//     * 获取用户信息<br/>
//     * <功能详细描述>
//     * @param accessToken
//     * @param request
//     * @return [参数说明]
//     * 
//     * @return LoginUserInfo [返回类型说明]
//     * @exception throws [异常类型] [异常说明]
//     * @see [类、类#方法、类#成员]
//     */
//    @Override
//    public LoginUserInfo getUserInfo(LoginAccessToken accessToken,
//            HttpServletRequest request) {
//        AssertUtils.notNull(accessToken, "accessToken is null.");
//        AssertUtils.notEmpty(accessToken.getUniqueId(),
//                "accessToken.uniqueId is empty.");
//        AssertUtils.notEmpty(accessToken.getAccessToken(),
//                "accessToken.accessToken is empty.");
//        
//        Map<String, String> params = new HashMap<String, String>();
//        params.put("access_token", accessToken.getAccessToken());
//        params.put("uid", accessToken.getUniqueId());
//        String result = HttpClientUtils.get(USER_INFO_REQUEST_URL, params);
//        logger.info("--- getUserInfo:{access_token:{},openid:{},result:{}}",
//                accessToken.getAccessToken(),
//                accessToken.getUniqueId(),
//                result);
//        
//        JsonNode node = JsonUtils.toTree(result);
//        LoginUserInfo user = new LoginUserInfo();
//        user.setId(node.get("id").asText());
//        user.setUsername(node.get("name").asText());
//        user.setSex(StringUtils.equalsAnyIgnoreCase("m",
//                node.get("gender").asText()) ? SexEnum.MALE : SexEnum.FEMALE);
//        user.setUniqueId(accessToken.getUniqueId());
//        user.setHeadImgUrl(node.get("profile_image_url").asText());
//        JSONObject json = user.getAttributeJSONObject();
//        json.put("screen_name", node.get("screen_name").asText());
//        json.put("domain", node.get("domain").asText());
//        json.put("province", node.get("province").asText());
//        json.put("city", node.get("city").asText());
//        json.put("location", node.get("location").asText());
//        json.put("description", node.get("description").asText());
//        json.put("url", node.get("url").asText());
//        return user;
//    }
//    
//    /**
//     * 获取登陆用户的第三方唯一键<br/>
//     * @param code
//     * @param state
//     * @param request
//     * @return
//     * @throws SocialAuthorizeException
//     */
//    @Override
//    public String getUniqueId(LoginAccessToken accessToken,
//            HttpServletRequest request) throws SocialAuthorizeException {
//        AssertUtils.notNull(accessToken, "accessToken is null.");
//        AssertUtils.notEmpty(accessToken.getUniqueId(),
//                "accessToken.uniqueId is empty.");
//        AssertUtils.notEmpty(accessToken.getAccessToken(),
//                "accessToken.accessToken is empty.");
//        
//        String uniqueId = accessToken.getUniqueId();
//        return uniqueId;
//    }
//    
//}
