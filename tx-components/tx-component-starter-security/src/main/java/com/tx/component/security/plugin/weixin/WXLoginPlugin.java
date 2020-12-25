///*
// * 描          述:  <描述>
// * 修  改   人:  Administrator
// * 修改时间:  2019年12月11日
// * <修改描述:>
// */
//package com.tx.component.security.plugin.weixin;
//
//import java.util.HashMap;
//import java.util.LinkedHashMap;
//import java.util.Map;
//
//import javax.servlet.http.HttpServletRequest;
//
//import org.apache.commons.lang3.RandomStringUtils;
//import org.apache.commons.lang3.StringUtils;
//import org.apache.oltu.oauth2.client.OAuthClient;
//import org.apache.oltu.oauth2.client.URLConnectionClient;
//import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
//import org.apache.oltu.oauth2.client.response.OAuthJSONAccessTokenResponse;
//import org.apache.oltu.oauth2.common.OAuth;
//import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
//import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
//import org.apache.oltu.oauth2.common.message.types.GrantType;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Component;
//import org.springframework.util.Base64Utils;
//import org.springframework.web.servlet.ModelAndView;
//
//import com.alibaba.fastjson.JSONObject;
//import com.fasterxml.jackson.databind.JsonNode;
//import com.tx.core.exceptions.SILException;
//import com.tx.core.exceptions.util.AssertUtils;
//import com.tx.core.util.HttpClientUtils;
//import com.tx.core.util.JsonUtils;
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
// *  1、网站服务器向微信API传入带有 回调url 的参数
// *  2、手机微信通过摄像头扫二维码，从 光学原理 上完成数据的传递
// *  3、PC浏览器上查询扫码状态的 长连接 收到返回的状态值，并更新提示
// *  4、PC浏览器上查询手机客户端 点击确认按钮的状态值，并更新提示，然后 重定向 到 过程1 中传递url地址上
// *  5、网站服务器在授权成功后，完成本系统的用户注册或者登录的业务逻辑
// *  6、网站服务器重定向到用户登录成功的界面中（如果对于新注册用户不需要额外的审核的话）
// *  注：
// *  微信平台的各种API接口请参考：微信开放平台提供的官方文档
// *  微信扫码登录的开发权限需要在微信开放平台中进行企业资质认证（个人用户无法获得）
// *  回调url 的域必需在微信开放平台中进行填写备案，本地开发时传递的 回调url 参数必须和备案一致
// * 
// * 参考： https://blog.csdn.net/qq_34190023/article/details/81133619
// * 参考： https://www.cnblogs.com/0201zcr/p/5131602.html
// * https://blog.csdn.net/qq_34664239/article/details/79107529
// * 
// * https://open.weixin.qq.com/
// * 账号：p7engqingyang@qq.com 密码:私有密码
// * 
// * @author  Administrator
// * @version  [版本号, 2019年12月11日]
// * @see  [相关类/方法]
// * @since  [产品/模块版本]
// */
//@Component("wxLoginPlugin")
//public class WXLoginPlugin extends LoginPlugin<WXLoginPluginConfig> {
//    
//    /** 日志记录器 */
//    private Logger logger = LoggerFactory.getLogger(WXLoginPlugin.class);
//    
//    /** 二维码登陆 */
//    public static final String QR_CODE_REQUEST_QR_URL = "https://open.weixin.qq.com/connect/qrconnect#wechat_redirect";
//    
//    /** wap端登陆 */
//    public static final String WAP_CODE_REQUEST_URL = "https://open.weixin.qq.com/connect/oauth2/authorize#wechat_redirect";
//    
//    /** openId请求URL */
//    public static final String OPEN_ID_REQUEST_URL = "https://api.weixin.qq.com/sns/oauth2/access_token";
//    
//    /** 用戶信息請求URL */
//    public static final String USER_INFO_REQUEST_URL = "https://api.weixin.qq.com/sns/userinfo";
//    
//    /** "状态"属性名称 */
//    private static final String STATE_ATTRIBUTE_NAME = WXLoginPlugin.class
//            .getName() + ".STATE";
//    
//    /** "状态"属性名称 */
//    private static final String REDIRECT_URL_ATTRIBUTE_NAME = WXLoginPlugin.class
//            .getName() + ".REDIRECT_URL";
//    
//    /**
//     * @return
//     */
//    @Override
//    public String getName() {
//        return "微信登陆插件";
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
//        return "plugin.login.wx";
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
//                + "loginplugin/callback/WX";
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
//        String scope = "snsapi_base";//"snsapi_userinfo";
//        //redirectUri
//        String redirectUri = WebUtils.getBaseUrl(request)
//                + "loginplugin/callback/WX";
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
//        WXLoginPluginConfig config = getConfig();
//        ModelAndView mview = new ModelAndView();
//        
//        Map<String, String> parameterMap = new LinkedHashMap<>();
//        //应用唯一标识，在微信开放平台提交应用审核通过后获得
//        parameterMap.put("appid", config.getAppId());
//        //redirect_uri  是   重定向地址，需要进行UrlEncode
//        parameterMap.put("redirect_uri", redirectUri);
//        //response_type 是   填code
//        parameterMap.put("response_type", "code");
//        //scope 是   应用授权作用域，拥有多个作用域用逗号（,）分隔，网页应用目前仅填写snsapi_login即可
//        //snsapi_base,snsapi_userinfo
//        if (StringUtils.isEmpty(scope)) {
//            parameterMap.put("scope", "snsapi_base");
//        } else {
//            parameterMap.put("scope", scope);
//        }
//        parameterMap.put("state", state);
//        
//        mview.addObject("requestUrl", WAP_CODE_REQUEST_URL);
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
//        
//        WXLoginPluginConfig config = getConfig();
//        try {
//            //从源代码分析，oauthClient是否重复使用，对性能无影响，URLConnectionClient的构造函数并无多余动作
//            //疑问： OauthClient以及URLConnectionClient中源代码显示，并无显式关闭连接的情况.系统是如何保证连接关闭的，需要查询资资料
//            OAuthClient oAuthClient = new OAuthClient(
//                    new URLConnectionClient());
//            OAuthClientRequest.TokenRequestBuilder builder = OAuthClientRequest
//                    .tokenLocation(OPEN_ID_REQUEST_URL);
//            String appid = config.getAppId();
//            String secret = config.getAppSecret();
//            builder.setParameter("appid", appid);
//            builder.setParameter("secret", secret);
//            builder.setGrantType(GrantType.AUTHORIZATION_CODE);
//            builder.setCode(code);
//            OAuthClientRequest accessTokenRequest = builder.buildQueryMessage();
//            OAuthJSONAccessTokenResponse response = oAuthClient
//                    .accessToken(accessTokenRequest, OAuth.HttpMethod.GET);
//            
//            LoginAccessToken token = new LoginAccessToken();
//            token.setUniqueId(response.getParam("openid"));
//            token.setAccessToken(response.getAccessToken());
//            token.setExpiresIn(response.getExpiresIn());
//            token.setRefreshToken(response.getRefreshToken());
//            token.setTokenType(response.getTokenType());
//            token.setScope(response.getScope());
//            logger.info(
//                    "------ getAccessToken:{appid:{},secret:{},uniqueId:{},accessToken:{},refreshToken:{},expiresIn:{}",
//                    appid,
//                    secret,
//                    token.getUniqueId(),
//                    token.getAccessToken(),
//                    token.getRefreshToken(),
//                    token.getExpiresIn());
//            if (StringUtils.isEmpty(token.getUniqueId())) {
//                throw new SocialAuthorizeException("获取微信用户openid失败.");
//            }
//            return token;
//        } catch (OAuthSystemException | OAuthProblemException
//                | SILException e) {
//            throw new SocialAuthorizeException(
//                    "获取微信用户openid失败.发生异常:" + e.getMessage(), e);
//        }
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
