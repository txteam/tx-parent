/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2017年3月13日
 * <修改描述:>
 */
package com.tx.component.communication.senddialect.sms.juhe;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.collections.MapUtils;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONObject;

/**
 * <功能简述>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2017年3月13日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class JuheDemo {
    
    public static final String DEF_CHATSET = "UTF-8";
    
    public static final int DEF_CONN_TIMEOUT = 30000;
    
    public static final int DEF_READ_TIMEOUT = 30000;
    
    public static String userAgent = "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.66 Safari/537.36";
    
    //配置您申请的KEY
    public static final String APPKEY = "036a43cf37e9ffd5aff0e22e6e775862";
    
    //聚合发送短信接口
    public static final String SERVER_URL = "http://v.juhe.cn/sms/send";
    
    //1.屏蔽词检查测
    public static void getRequest1() {
        String result = null;
        String url = "http://v.juhe.cn/sms/black";//请求接口地址
        Map<String, String> params = new HashMap<>();//请求参数
        params.put("word", "");//需要检测的短信内容，需要UTF8 URLENCODE
        params.put("key", APPKEY);//应用APPKEY(应用详细页查询)
        try {
            result = net(url, params, "GET");
            JSONObject object = JSONObject.parseObject(result);
            if (object.getInteger("error_code") == 0) {
                System.out.println(object.get("result"));
            } else {
                System.out.println(object.get("error_code") + ":"
                        + object.get("reason"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    //2.发送短信
    public static void getRequest2(String mobilePhoneNumber,
            String contentTemplateId, Map<String, String> params) {
        String result = null;
        StringBuilder paramSb = new StringBuilder("");
        if (!MapUtils.isEmpty(params)) {
            for (Entry<String, String> entryTemp : params.entrySet()) {
                String key = entryTemp.getKey();
                if (StringUtils.isEmpty(key)) {
                    continue;
                }
                if (!key.startsWith("#")) {
                    paramSb.append("#");
                }
                paramSb.append(key);
                if (!key.endsWith("#")) {
                    paramSb.append("#");
                }
                paramSb.append("=").append(entryTemp.getValue());
                paramSb.append("&");
            }
            paramSb.deleteCharAt(paramSb.length() - 1);
        }
        System.out.println(paramSb.toString());
        
        Map<String, String> requestParams = new HashMap<String, String>();//请求参数
        requestParams.put("mobile", mobilePhoneNumber);//接收短信的手机号码
        requestParams.put("tpl_id", contentTemplateId);//短信模板ID，请参考个人中心短信模板设置
        requestParams.put("tpl_value", paramSb.toString());//变量名和变量值对。如果你的变量名或者变量值中带有#&=中的任意一个特殊符号，请先分别进行urlencode编码后再传递，<a href="http://www.juhe.cn/news/index/id/50" target="_blank">详细说明></a>
        requestParams.put("key", APPKEY);//应用APPKEY(应用详细页查询)
        requestParams.put("dtype", "");//返回数据的格式,xml或json，默认json
        try {
            result = net(SERVER_URL, requestParams, "GET");
            JSONObject object = JSONObject.parseObject(result);
            if (object.getInteger("error_code") == 0) {
                System.out.println(object.get("result"));
            } else {
                System.out.println(object.get("error_code") + ":"
                        + object.get("reason"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("code", String.valueOf((int) Math.random() * 10000));
        params.put("app", "重庆添馨网络科技有限公司");
        getRequest2("18983379637", "29669", params);
    }
    
    /**
     *
     * @param strUrl 请求地址
     * @param params 请求参数
     * @param method 请求方法
     * @return  网络请求字符串
     * @throws Exception
     */
    public static String net(String strUrl, Map<String, String> params,
            String method) throws Exception {
        HttpURLConnection conn = null;
        BufferedReader reader = null;
        String rs = null;
        try {
            StringBuffer sb = new StringBuffer();
            if (method == null || method.equals("GET")) {
                strUrl = strUrl + "?" + urlencode(params);
            }
            System.out.println(strUrl);
            URL url = new URL(strUrl);
            conn = (HttpURLConnection) url.openConnection();
            if (method == null || method.equals("GET")) {
                conn.setRequestMethod("GET");
            } else {
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
            }
            conn.setRequestProperty("User-agent", userAgent);
            conn.setUseCaches(false);
            conn.setConnectTimeout(DEF_CONN_TIMEOUT);
            conn.setReadTimeout(DEF_READ_TIMEOUT);
            conn.setInstanceFollowRedirects(false);
            conn.connect();
            if (params != null && method.equals("POST")) {
                try {
                    DataOutputStream out = new DataOutputStream(
                            conn.getOutputStream());
                    out.writeBytes(urlencode(params));
                } catch (Exception e) {
                    // TODO: handle exception
                }
            }
            InputStream is = conn.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is, DEF_CHATSET));
            String strRead = null;
            while ((strRead = reader.readLine()) != null) {
                sb.append(strRead);
            }
            rs = sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                reader.close();
            }
            if (conn != null) {
                conn.disconnect();
            }
        }
        return rs;
    }
    
    //将map型转为请求参数型
    public static String urlencode(Map<String, String> data) {
        StringBuilder sb = new StringBuilder();
        for (Entry<String, String> i : data.entrySet()) {
            try {
                sb.append(i.getKey())
                        .append("=")
                        .append(URLEncoder.encode(i.getValue() + "", "UTF-8"))
                        .append("&");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}
