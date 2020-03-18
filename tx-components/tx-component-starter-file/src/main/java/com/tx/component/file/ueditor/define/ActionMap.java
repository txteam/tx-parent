package com.tx.component.file.ueditor.define;

import java.util.Map;
import java.util.HashMap;

/**
 * 定义请求action类型<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2020年3月13日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@SuppressWarnings("serial")
public final class ActionMap {
    
    public static final Map<String, Integer> mapping;
    
    // 获取配置请求
    public static final int CONFIG = 0;
    
    /** 上传图片 */
    public static final int UPLOAD_IMAGE = 1;
    
    /** 上传涂鸦 */
    public static final int UPLOAD_SCRAWL = 2;
    
    /** 上传video */
    public static final int UPLOAD_VIDEO = 3;
    
    /** 上传文件 */
    public static final int UPLOAD_FILE = 4;
    
    /** 访问图片 */
    public static final int CATCH_IMAGE = 5;
    
    /** 文件清单 */
    public static final int LIST_FILE = 6;
    
    /** 图片清单 */
    public static final int LIST_IMAGE = 7;
    
    static {
        mapping = new HashMap<String, Integer>() {
            {
                put("config", ActionMap.CONFIG);
                put("uploadimage", ActionMap.UPLOAD_IMAGE);
                put("uploadscrawl", ActionMap.UPLOAD_SCRAWL);
                put("uploadvideo", ActionMap.UPLOAD_VIDEO);
                put("uploadfile", ActionMap.UPLOAD_FILE);
                put("catchimage", ActionMap.CATCH_IMAGE);
                put("listfile", ActionMap.LIST_FILE);
                put("listimage", ActionMap.LIST_IMAGE);
            }
        };
    }
    
    /**
     * 获取对应的action类型<br/>
     * <功能详细描述>
     * @param key
     * @return [参数说明]
     * 
     * @return int [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static int getType(String key) {
        return ActionMap.mapping.get(key);
    }
    
}
