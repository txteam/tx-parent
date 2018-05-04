package com.tx.component.file.ueditor.model;

import java.util.Map;
import java.util.HashMap;

/**
  * 定义请求action类型<br/>
  * <功能详细描述>
  * 
  * @author  Administrator
  * @version  [版本号, 2017年3月9日]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
 */
@SuppressWarnings("serial")
public final class UEditorActionMap {
    
    public static final Map<String, Integer> mapping;
    
    /** 获取配置请求 */
    public static final int CONFIG = 0;
    
    /** 上传图片 */
    public static final int UPLOAD_IMAGE = 1;
    
    /** 上传涂鸦 */
    public static final int UPLOAD_SCRAWL = 2;
    
    /** 上传视频 */
    public static final int UPLOAD_VIDEO = 3;
    
    /** 上传文件 */
    public static final int UPLOAD_FILE = 4;
    
    /** 上传截图 */
    public static final int CATCH_IMAGE = 5;
    
    /** 文件列表 */
    public static final int LIST_FILE = 6;
    
    /** 图片列表 */
    public static final int LIST_IMAGE = 7;
    
    /** 图片列表 */
    public static final int DELETE_IMAGE = 11;
    
    /** 图片列表 */
    public static final int DELETE_FILE = 16;
    
    static {
        mapping = new HashMap<String, Integer>() {
            {
                put("config", UEditorActionMap.CONFIG);
                put("uploadimage", UEditorActionMap.UPLOAD_IMAGE);
                put("uploadscrawl", UEditorActionMap.UPLOAD_SCRAWL);
                put("uploadvideo", UEditorActionMap.UPLOAD_VIDEO);
                put("uploadfile", UEditorActionMap.UPLOAD_FILE);
                put("catchimage", UEditorActionMap.CATCH_IMAGE);
                put("listfile", UEditorActionMap.LIST_FILE);
                put("listimage", UEditorActionMap.LIST_IMAGE);
                
                put("deleteimage", UEditorActionMap.DELETE_IMAGE);//删除
                put("deletefile", UEditorActionMap.DELETE_FILE);//删除
            }
        };
    }
    
    /**
      * 根据actionType获取对应的actionCode<br/>
      * <功能详细描述>
      * @param actionType
      * @return [参数说明]
      * 
      * @return int [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public static int getActionCode(String actionType) {
        int actionCode = UEditorActionMap.mapping.get(actionType);
        return actionCode;
    }
}
