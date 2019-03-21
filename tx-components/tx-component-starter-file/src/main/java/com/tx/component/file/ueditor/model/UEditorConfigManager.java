package com.tx.component.file.ueditor.model;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tx.component.file.ueditor.define.ActionMap;
import com.tx.core.exceptions.util.AssertUtils;

/**
  * UEditor配置管理器<br/>
  * <功能详细描述>
  * 
  * @author  PengQY
  * @version  [版本号, 2017年3月9日]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
 */
public final class UEditorConfigManager {
    
    /** json配置 */
    protected JSONObject jsonConfig = null;
    
    /*
     * 通过一个给定的路径构建一个配置管理器， 该管理器要求地址路径所在目录下必须存在config.properties文件
     */
    private UEditorConfigManager(String configContext) {
        JSONObject jsonConfig = JSONObject.parseObject(configContext);
        this.jsonConfig = jsonConfig;
        
        AssertUtils.notNull(this.jsonConfig, "jsonConfig is null.");
    }
    
    /**
     * 配置管理器构造工厂
     * @param rootPath 服务器根路径
     * @param contextPath 服务器所在项目路径
     * @param uri 当前访问的uri
     * @return 配置管理器实例或者null
     */
    public static UEditorConfigManager newInstance(String configContext) {
        UEditorConfigManager configManager = new UEditorConfigManager(
                configContext);
        
        return configManager;
    }
    
    /**
     * 获取所有的配置
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return JSONObject [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public JSONObject getAllConfig() {
        return this.jsonConfig;
    }
    
    /**
      * 获取到配置<br/>
      * <功能详细描述>
      * @param type
      * @return [参数说明]
      * 
      * @return Map<String,Object> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public Map<String, Object> getConfig(int type) {
        Map<String, Object> conf = new HashMap<String, Object>();
        String savePath = null;
        switch (type) {
            case ActionMap.UPLOAD_FILE:
                conf.put("fieldName",
                        this.jsonConfig.getString("fileFieldName"));
                conf.put("maxSize", this.jsonConfig.getLong("fileMaxSize"));
                conf.put("isBase64", "false");
                
                conf.put("allowFiles", this.getArray("fileAllowFiles"));
                savePath = this.jsonConfig.getString("filePathFormat");
                break;
            case ActionMap.UPLOAD_IMAGE:
                conf.put("fieldName",
                        this.jsonConfig.getString("imageFieldName"));
                conf.put("maxSize", this.jsonConfig.getLong("imageMaxSize"));
                conf.put("isBase64", "false");
                
                conf.put("allowFiles", this.getArray("imageAllowFiles"));
                savePath = this.jsonConfig.getString("imagePathFormat");
                break;
            case ActionMap.UPLOAD_VIDEO:
                conf.put("fieldName",
                        this.jsonConfig.getString("videoFieldName"));
                conf.put("maxSize", this.jsonConfig.getLong("videoMaxSize"));
                
                conf.put("allowFiles", this.getArray("videoAllowFiles"));
                savePath = this.jsonConfig.getString("videoPathFormat");
                break;
            case ActionMap.UPLOAD_SCRAWL:
                conf.put("fieldName",
                        this.jsonConfig.getString("scrawlFieldName"));
                conf.put("maxSize", this.jsonConfig.getLong("scrawlMaxSize"));
                
                conf.put("isBase64", "true");
                savePath = this.jsonConfig.getString("scrawlPathFormat");
                break;
            case ActionMap.CATCH_IMAGE:
                conf.put("fieldName",
                        this.jsonConfig.getString("catcherFieldName") + "[]");
                conf.put("maxSize", this.jsonConfig.getLong("catcherMaxSize"));
                
                conf.put("filter", this.getArray("catcherLocalDomain"));
                conf.put("allowFiles", this.getArray("catcherAllowFiles"));
                savePath = this.jsonConfig.getString("catcherPathFormat");
                break;
            case ActionMap.LIST_IMAGE:
                conf.put("allowFiles", this.getArray("imageManagerAllowFiles"));
                conf.put("dir",
                        this.jsonConfig.getString("imageManagerListPath"));
                conf.put("count",
                        this.jsonConfig.getInteger("imageManagerListSize"));
                break;
            case ActionMap.LIST_FILE:
                conf.put("allowFiles", this.getArray("fileManagerAllowFiles"));
                conf.put("dir",
                        this.jsonConfig.getString("fileManagerListPath"));
                conf.put("count",
                        this.jsonConfig.getInteger("fileManagerListSize"));
                break;
        }
        conf.put("savePath", savePath);
        return conf;
    }
    
    /**
      * getArray
      * <功能详细描述>
      * @param key
      * @return [参数说明]
      * 
      * @return String[] [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    private String[] getArray(String key) {
        JSONArray jsonArray = this.jsonConfig.getJSONArray(key);
        String[] result = new String[jsonArray.size()];
        
        for (int i = 0, len = jsonArray.size(); i < len; i++) {
            result[i] = jsonArray.getString(i);
        }
        return result;
    }
}
