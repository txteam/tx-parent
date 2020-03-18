package com.tx.component.file.ueditor.define;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * <功能简述>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2020年3月13日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@SuppressWarnings("serial")
public class MIMEType {
    
    public static final Map<String, String> types = new HashMap<String, String>() {
        {
            put("image/gif", ".gif");
            put("image/jpeg", ".jpg");
            put("image/jpg", ".jpg");
            put("image/png", ".png");
            put("image/bmp", ".bmp");
        }
    };
    
    public static String getSuffix(String mime) {
        return MIMEType.types.get(mime);
    }
    
}
