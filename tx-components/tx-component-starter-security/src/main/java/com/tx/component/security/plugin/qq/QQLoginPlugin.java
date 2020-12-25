///*
// * 描          述:  <描述>
// * 修  改   人:  Administrator
// * 修改时间:  2019年12月11日
// * <修改描述:>
// */
//package com.tx.component.security.plugin.qq;
//
//import java.util.HashMap;
//import java.util.LinkedHashMap;
//import java.util.Map;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
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
//import com.tx.plugin.login.qq.model.QQUserInfo;
//
///**
// * QQ登陆插件<br/>
// * https://wiki.open.qq.com/wiki/%E3%80%90QQ%E7%99%BB%E5%BD%95%E3%80%91OAuth2.0%E5%BC%80%E5%8F%91%E6%96%87%E6%A1%A3
// * 申请appid和appkey；申请地址：https://connect.qq.com/
// * 腾讯开放平台  http://op.open.qq.com/index.php
// * https://www.php.cn/js/js-OAuth2.html
// * 
// * <功能详细描述>
// * 
// * @author  Administrator
// * @version  [版本号, 2019年12月11日]
// * @see  [相关类/方法]
// * @since  [产品/模块版本]
// */
//@Component("qqLoginPlugin")
//public class QQLoginPlugin extends LoginPlugin<QQLoginPluginConfig> {
//    
//    /** code请求URL */
//    private static final String CODE_REQUEST_URL = "https://graph.qq.com/oauth2.0/authorize";
//    
//    /** accessToken请求URL */
//    private static final String ACCESS_TOKEN_REQUEST_URL = "https://graph.qq.com/oauth2.0/token";
//    
//    /** openId请求URL */
//    //https://graph.qq.com/oauth2.0/me?access_token=YOUR_ACCESS_TOKEN
//    //callback( {"client_id":"YOUR_APPID","openid":"YOUR_OPENID"} ); 
//    //PC网站：https://graph.qq.com/oauth2.0/me
//    private static final String PC_OPEN_ID_REQUEST_URL = "https://graph.qq.com/oauth2.0/me";
//    
//    /** "openId"配比 */
//    private static final Pattern PC_OPEN_ID_PATTERN = Pattern
//            .compile("\"openid\"\\s*:\\s*\"(\\S*?)\"");
//    
//    /** openId请求URL */
//    //client_id=100222222&openid=1704************************878C
//    //WAP网站：https://graph.z.qq.com/moc2/me
//    private static final String WAP_OPEN_ID_REQUEST_URL = "https://graph.z.qq.com/moc2/me";
//    
//    /** userInfo请求URL */
//    //https://graph.qq.com/user/get_user_info?access_token=YOUR_ACCESS_TOKEN&oauth_consumer_key=YOUR_APP_ID&openid=YOUR_OPENID
//    //"ret":0,"msg":"","nickname":"YOUR_NICK_NAME",
//    private static final String USER_INFO_REQUEST_URL = "https://graph.qq.com/user/get_user_info";
//    
//    /** "状态"属性名称 */
//    private static final String STATE_ATTRIBUTE_NAME = QQLoginPlugin.class
//            .getName() + ".STATE";
//    
//    /** "状态"属性名称 */
//    private static final String REDIRECT_URL_ATTRIBUTE_NAME = QQLoginPlugin.class
//            .getName() + ".REDIRECT_URL";
//    
//    /**
//     * @return
//     */
//    @Override
//    public String getName() {
//        return "QQ登陆插件";
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
//        return "plugin.login.qq";
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
//        //state
//        String state = Base64Utils.encodeToString(
//                RandomStringUtils.randomAlphabetic(30).getBytes());
//        //scope信息
//        String scope = "snsapi_userinfo";
//        //redirectUri
//        String redirectUri = WebUtils.getBaseUrl(request)
//                + "loginplugin/callback/QQ";
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
//                + "loginplugin/callback/QQ";
//        ModelAndView mv = getCodeHandle("loginplugin/signIn",
//                redirectUri,
//                state,
//                scope,
//                request);
//        return mv;
//    }
//    
//    /**
//     * @param viewName
//     * @param redirectUrl
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
//        QQLoginPluginConfig config = getConfig();
//        ModelAndView mview = new ModelAndView();
//        
//        Map<String, String> parameterMap = new LinkedHashMap<>();
//        //应用唯一标识，在微信开放平台提交应用审核通过后获得
//        parameterMap.put("client_id", config.getAppId());
//        //redirect_uri  是   重定向地址，需要进行UrlEncode
//        parameterMap.put("redirect_uri", redirectUri);
//        //state值
//        parameterMap.put("state", state);
//        //response_type 是   填code
//        parameterMap.put("response_type", "code");
//        //scope 是   应用授权作用域，拥有多个作用域用逗号（,）分隔，网页应用目前仅填写snsapi_login即可
//        parameterMap.put("scope", scope);
//        //mview.addObject("requestUrl", CODE_REQUEST_URL);
//        //mview.addObject("parameterMap", parameterMap);
//        //mview.setViewName(viewName);
//        mview.setViewName(
//                "redirect:" + WebUtils.apply(CODE_REQUEST_URL, parameterMap));
//        return mview;
//    }
//    
//    /**
//     * 获取AccessToken《》
//     * @param code
//     * @param state
//     * @param request
//     * @return
//     * @throws SocialAuthorizeException
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
//        //获取配置
//        QQLoginPluginConfig config = getConfig();
//        String appId = config.getAppId();
//        String appKey = config.getAppKey();
//        Map<String, String> parameterMap = new HashMap<>();
//        parameterMap.put("grant_type", "authorization_code");
//        parameterMap.put("client_id", appId);
//        parameterMap.put("client_secret", appKey);
//        parameterMap.put("redirect_uri", redirectUri);
//        parameterMap.put("code", code);
//        String result = HttpClientUtils.get(ACCESS_TOKEN_REQUEST_URL,
//                parameterMap);
//        logger.info(
//                "--- getAccessToken:{client_id:{},client_secret:{},code:{},result:{}}",
//                appId,
//                appKey,
//                code,
//                result);
//        Map<String, String> resultMap = WebUtils.parse(result);
//        
//        LoginAccessToken token = new LoginAccessToken();
//        String accessToken = resultMap.get("access_token");
//        if (StringUtils.isNotEmpty(accessToken)) {
//            token.setAccessToken(accessToken);
//        } else {
//            throw new SocialAuthorizeException("access token is empty.");
//        }
//        return token;
//    }
//    
//    /**
//     * @param token
//     * @param request
//     * @return
//     * @throws SocialAuthorizeException
//     */
//    @Override
//    public LoginUserInfo getUserInfo(LoginAccessToken token,
//            HttpServletRequest request) throws SocialAuthorizeException {
//        AssertUtils.notNull(token, "token is null.");
//        AssertUtils.notEmpty(token.getAccessToken(),
//                "token.accessToken is empty.");
//        //需要先调用openid
//        AssertUtils.notEmpty(token.getUniqueId(), "token.uniqueId is empty.");
//        
//        QQLoginPluginConfig config = getConfig();
//        String appId = config.getAppId();
//        Map<String, String> params = new HashMap<String, String>();
//        params.put("access_token", token.getAccessToken());
//        params.put("oauth_consumer_key", appId);
//        params.put("openid", token.getUniqueId());
//        
//        String result = HttpClientUtils.get(USER_INFO_REQUEST_URL, params);
//        logger.info(
//                "--- getUserInfo:{access_token:{},oauth_consumer_key:{},openid:{},result:{}}",
//                token.getAccessToken(),
//                appId,
//                token.getUniqueId(),
//                result);
//        
//        QQUserInfo qqUserInfo = JsonUtils.toObject(result, QQUserInfo.class);
//        LoginUserInfo user = new LoginUserInfo();
//        user.setId(String.valueOf(qqUserInfo.getRet()));
//        user.setUsername(qqUserInfo.getNickname());
//        user.setHeadImgUrl(qqUserInfo.getFigureurl());
//        user.setSex(
//                StringUtils.equals(qqUserInfo.getGender(), "m") ? SexEnum.MALE
//                        : SexEnum.FEMALE);
//        user.setUniqueId(token.getUniqueId());
//        JSONObject json = user.getAttributeJSONObject();
//        json.put("msg", qqUserInfo.getMsg());
//        json.put("figureurl1", qqUserInfo.getFigureurl1());
//        json.put("figureurlQQ1", qqUserInfo.getFigureurlQQ1());
//        return user;
//    }
//    
//    /**
//     * @param token
//     * @param request
//     * @return
//     * @throws SocialAuthorizeException
//     */
//    @Override
//    public String getUniqueId(LoginAccessToken token,
//            HttpServletRequest request) throws SocialAuthorizeException {
//        AssertUtils.notNull(token, "accessToken is null.");
//        AssertUtils.notEmpty(token.getAccessToken(),
//                "token.accessToken is empty.");
//        
//        Map<String, String> parameterMap = new HashMap<>();
//        parameterMap.put("access_token", token.getAccessToken());
//        if (WebUtils.isMobileRequest(request)) {
//            String result = HttpClientUtils.get(WAP_OPEN_ID_REQUEST_URL,
//                    parameterMap);
//            logger.info("--- getUniqueId[WAP]:{access_token:{},result:{}}",
//                    token.getAccessToken(),
//                    result);
//            Map<String, String> map = WebUtils.parse(result);
//            if (map.containsKey("openid")) {
//                String openId = map.get("openid");
//                token.setUniqueId(openId);
//                return openId;
//            }
//            throw new SocialAuthorizeException("获取客户唯一键失败(WAP端).");
//        } else {
//            String result = HttpClientUtils.get(PC_OPEN_ID_REQUEST_URL,
//                    parameterMap);
//            logger.info("--- getUniqueId[PC]:{access_token:{},result:{}}",
//                    token.getAccessToken(),
//                    result);
//            Matcher matcher = PC_OPEN_ID_PATTERN.matcher(result);
//            if (matcher.find()) {
//                String openId = matcher.group(1);
//                request.setAttribute("openId", openId);
//                token.setUniqueId(openId);
//                return openId;
//            }
//            throw new SocialAuthorizeException("获取客户唯一键失败(PC端).");
//        }
//    }
//    
//    //    /**
//    //     * 获取用户信息<br/>
//    //     * <功能详细描述>
//    //     * @param uniqueId
//    //     * @param accessToken
//    //     * @return [参数说明]
//    //     * 
//    //     * @return WeixinUserInfo [返回类型说明]
//    //     * @exception throws [异常类型] [异常说明]
//    //     * @see [类、类#方法、类#成员]
//    //     */
//    //    public WeixinUserInfo getUserInfo(String uniqueId, String accessToken) {
//    //        AssertUtils.notEmpty(uniqueId, "uniqueId is empty.");
//    //        AssertUtils.notEmpty(accessToken, "accessToken is empty.");
//    //        
//    //        Map<String, String> params = new HashMap<>();
//    //        params.put("access_token", accessToken);
//    //        params.put("openid", uniqueId);
//    //        params.put("lang", "zh_CN");
//    //        String result = HttpClientUtils.get(USER_INFO_REQUEST_URL, params);
//    //        //        try {
//    //        //            responseStr = new String(responseStr.getBytes("ISO-8859-1"),
//    //        //                    "utf8");
//    //        //        } catch (Exception e) {
//    //        //            throw new SILException("返回值字符集转码异常.ISO-8859-1 > UTF8.", e);
//    //        //        }
//    //        logger.info("------ getUserInfo:{access_token:{},openid:{},result:{}}",
//    //                accessToken,
//    //                uniqueId,
//    //                result);
//    //        @SuppressWarnings("unchecked")
//    //        Map<String, String> res = JsonUtils.toObject(result, Map.class);
//    //        
//    //        WeixinUserInfo user = new WeixinUserInfo();
//    //        user.setNickname(res.get("nickname"));
//    //        user.setSex(res.get("sex"));
//    //        user.setProvince(res.get("province"));
//    //        user.setCountry(res.get("country"));
//    //        user.setHeadImgUrl(res.get("headimgurl"));
//    //        user.setPrivilege(res.get("privilege"));
//    //        user.setUnionId(res.get("unionid"));
//    //        return user;
//    //    }
//    
//}
