///*
// * 描          述:  <描述>
// * 修  改   人:  Administrator
// * 修改时间:  2019年12月11日
// * <修改描述:>
// */
//package com.tx.component.security.plugin.github;
//
//import java.util.HashMap;
//import java.util.LinkedHashMap;
//import java.util.Map;
//
//import javax.servlet.http.HttpServletRequest;
//
//import org.apache.commons.lang3.RandomStringUtils;
//import org.apache.commons.lang3.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Component;
//import org.springframework.util.Base64Utils;
//import org.springframework.web.servlet.ModelAndView;
//
//import com.alibaba.fastjson.JSONObject;
//import com.fasterxml.jackson.databind.JsonNode;
//import com.tx.component.security.exception.SocialAuthorizeException;
//import com.tx.component.security.plugin.LoginPlugin;
//import com.tx.component.security.plugin.model.LoginAccessToken;
//import com.tx.component.security.plugin.model.LoginUserInfo;
//import com.tx.core.exceptions.util.AssertUtils;
//import com.tx.core.util.HttpClientUtils;
//import com.tx.core.util.JsonUtils;
//import com.tx.core.util.MessageUtils;
//import com.tx.core.util.WebUtils;
//import com.tx.plugin.login.LoginPluginUtils;
//
///**
// * GITHUB登陆插件<br/>
// * 
// * @author  Administrator
// * @version  [版本号, 2019年12月11日]
// * @see  [相关类/方法]
// * @since  [产品/模块版本]
// */
//@Component("ghLoginPlugin")
//public class GHLoginPlugin extends LoginPlugin<GHLoginPluginConfig> {
//    
//    /** 日志记录器 */
//    private Logger logger = LoggerFactory.getLogger(GHLoginPlugin.class);
//    
//    /** code请求URL */
//    private static final String CODE_REQUEST_URL = "https://github.com/login/oauth/authorize";
//    
//    /** uid请求URL */
//    private static final String UID_REQUEST_URL = "https://github.com/login/oauth/access_token";
//    
//    /** userInfo请求URL */
//    private static final String USER_INFO_REQUEST_URL = "https://api.github.com/user";
//    
//    /** "状态"属性名称 */
//    private static final String STATE_ATTRIBUTE_NAME = GHLoginPlugin.class
//            .getName() + ".STATE";
//    
//    /** "状态"属性名称 */
//    private static final String REDIRECT_URL_ATTRIBUTE_NAME = GHLoginPlugin.class
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
//        return "plugin.login.gh";
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
//                + "loginplugin/callback/GH";
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
//                + "loginplugin/callback/GH";
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
//        GHLoginPluginConfig config = getConfig();
//        ModelAndView mview = new ModelAndView();
//        
//        Map<String, String> parameterMap = new LinkedHashMap<>();
//        parameterMap.put("response_type", "code");
//        parameterMap.put("client_id", config.getClientId());
//        parameterMap.put("redirect_uri", redirectUri);
//        parameterMap.put("state", state);
//        
//        mview.addObject("requestUrl", CODE_REQUEST_URL);
//        mview.addObject("parameterMap", parameterMap);
//        mview.setViewName(viewName);
//        //List<NameValuePair> nameValuePairs = new ArrayList<>();
//        //for (Entry<String, String> entryTemp : parameterMap.entrySet()) {
//        //    if (StringUtils.isEmpty(entryTemp.getKey())) {
//        //        continue;
//        //    }
//        //    nameValuePairs.add(new BasicNameValuePair(entryTemp.getKey(),
//        //            entryTemp.getValue()));
//        //}
//        //
//        ////mview.addObject("parameterMap", parameterMap);
//        //String url = CODE_REQUEST_URL;
//        //try {
//        //    url = url + (StringUtils.contains(url, "?") ? "&" : "?")
//        //            + EntityUtils.toString(
//        //                    new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
//        //} catch (ParseException | IOException e) {
//        //    throw new SILException("拼接redirectUrl异常.", e);
//        //}
//        //mview.setViewName("redirect:" + CODE_REQUEST_URL);
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
//        GHLoginPluginConfig config = getConfig();
//        Map<String, String> parameterMap = new HashMap<>();
//        parameterMap.put("grant_type", "authorization_code");
//        String appKey = config.getClientId();
//        String appSecret = config.getClientSecret();
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
//        //getAccessToken:{client_id:e89c345917ce6375e634,client_secret:c9757c70a8af2c01e2a85b5f6e06fc240c122867,code:48896d56a152d946da65,result:access_token=dc400027043a1980222dcdf3fb90858b1b7ac75b&scope=&token_type=bearer}
//        //access_token=dc400027043a1980222dcdf3fb90858b1b7ac75b&scope=&token_type=bearer
//        Map<String, String> tokenMap = WebUtils.parse(result);
//        String accessToken = tokenMap.get("access_token");
//        LoginAccessToken token = new LoginAccessToken();
//        token.setAccessToken(accessToken);
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
//        AssertUtils.notEmpty(accessToken.getAccessToken(),
//                "accessToken.accessToken is empty.");
//        
//        Map<String, String> params = new HashMap<String, String>();
//        params.put("access_token", accessToken.getAccessToken());
//        String result = HttpClientUtils.get(USER_INFO_REQUEST_URL, params);
//        logger.info("--- getUserInfo:{access_token:{},openid:{},result:{}}",
//                accessToken.getAccessToken(),
//                accessToken.getUniqueId(),
//                result);
//        //getUserInfo:{access_token:b7ae403f31c13fb524c9777b7aed8aa3824eb475,openid:null,result:{"login":"240638006","id":2492318,"node_id":"MDQ6VXNlcjI0OTIzMTg=","avatar_url":"https://avatars3.githubusercontent.com/u/2492318?v=4","gravatar_id":"","url":"https://api.github.com/users/240638006","html_url":"https://github.com/240638006","followers_url":"https://api.github.com/users/240638006/followers","following_url":"https://api.github.com/users/240638006/following{/other_user}","gists_url":"https://api.github.com/users/240638006/gists{/gist_id}","starred_url":"https://api.github.com/users/240638006/starred{/owner}{/repo}","subscriptions_url":"https://api.github.com/users/240638006/subscriptions","organizations_url":"https://api.github.com/users/240638006/orgs","repos_url":"https://api.github.com/users/240638006/repos","events_url":"https://api.github.com/users/240638006/events{/privacy}","received_events_url":"https://api.github.com/users/240638006/received_events","type":"User","site_admin":false,"name":"Tim.PQY","company":"ChongQingTianXinWangLuoKeJiYouXianGongSi","blog":"","location":null,"email":"240638006@qq.com","hireable":true,"bio":null,"public_repos":14,"public_gists":0,"followers":1,"following":2,"created_at":"2012-10-05T07:58:47Z","updated_at":"2020-01-02T03:53:42Z"}}
//        //{"login":"240638006","id":2492318,"node_id":"MDQ6VXNlcjI0OTIzMTg=","avatar_url":"https://avatars3.githubusercontent.com/u/2492318?v=4","gravatar_id":"","type":"User","site_admin":false,"name":"Tim.PQY","company":"ChongQingTianXinWangLuoKeJiYouXianGongSi","blog":"","location":null,"email":"240638006@qq.com","hireable":true,"bio":null,"public_repos":14,"public_gists":0,"followers":1,"following":2,"created_at":"2012-10-05T07:58:47Z","updated_at":"2020-01-02T03:53:42Z"}
//        //Map<String, String> userMap = WebUtils.parse(result);
//        JsonNode node = JsonUtils.toTree(result);
//        String uniqueId = node.get("id").asText();
//        String username = node.get("login").asText();
//        String headImgUrl = node.get("avatar_url") != null
//                ? node.get("avatar_url").asText()
//                : null;
//        
//        String name = node.get("name").asText();
//        String location = node.get("location") != null
//                ? node.get("location").asText()
//                : null;
//        String company = node.get("company") != null
//                ? node.get("company").asText()
//                : null;
//        String blog = node.get("blog") != null ? node.get("blog").asText()
//                : null;
//        String email = node.get("email") != null ? node.get("email").asText()
//                : null;
//        LoginUserInfo user = new LoginUserInfo();
//        user.setId(uniqueId);
//        user.setUsername(username);
//        user.setHeadImgUrl(headImgUrl);
//        user.setSex(null);
//        user.setUniqueId(uniqueId);
//        JSONObject json = user.getAttributeJSONObject();
//        json.put("name", name);
//        json.put("location", location);
//        json.put("company", company);
//        json.put("blog", blog);
//        json.put("email", email);
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
//        AssertUtils.notEmpty(accessToken.getAccessToken(),
//                "accessToken.accessToken is empty.");
//        
//        LoginUserInfo userInfo = getUserInfo(accessToken, request);
//        String uniqueId = userInfo.getUniqueId();
//        return uniqueId;
//    }
//}
