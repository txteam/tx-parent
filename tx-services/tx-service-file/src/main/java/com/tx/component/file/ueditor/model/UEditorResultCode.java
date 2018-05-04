package com.tx.component.file.ueditor.model;

import java.util.HashMap;
import java.util.Map;

/**
  * UEditorResult错误编码<br/>
  * <功能详细描述>
  * 
  * @author  PengQY
  * @version  [版本号, 2017年3月8日]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
 */
@SuppressWarnings("serial")
public final class UEditorResultCode {
    
    /** 成功 0:SUCCESS */
    public static final int SUCCESS = 0;
    
    /** 文件大小超出限制 */
    public static final int MAX_SIZE = 1;
    
    /** 权限不足 */
    public static final int PERMISSION_DENIED = 2;
    
    /** 创建文件失败 */
    public static final int FAILED_CREATE_FILE = 3;
    
    /** IO错误 */
    public static final int IO_ERROR = 4;
    
    /** 上传表单不是multipart/form-data类型 */
    public static final int NOT_MULTIPART_CONTENT = 5;
    
    /** 解析请求错误 */
    public static final int PARSE_REQUEST_ERROR = 6;
    
    /** 未找到上传数据 */
    public static final int NOTFOUND_UPLOAD_DATA = 7;
    
    /** 不允许的文件类型 */
    public static final int NOT_ALLOW_FILE_TYPE = 8;
    
    /** 无效的Action */
    public static final int INVALID_ACTION = 101;
    
    /** 配置错误 */
    public static final int CONFIG_ERROR = 102;
    
    /** 被阻止的远程主机 */
    public static final int PREVENT_HOST = 201;
    
    /** 远程连接出错 */
    public static final int CONNECTION_ERROR = 202;
    
    /** 抓取远程图片失败 */
    public static final int REMOTE_FAIL = 203;
    
    /** 指定路径不是目录 */
    public static final int NOT_DIRECTORY = 301;
    
    /** 指定路径并不存在 */
    public static final int NOT_EXIST = 302;
    
    /** Callback参数名不合法 */
    public static final int ILLEGAL = 401;
    
    public static Map<Integer, String> info = new HashMap<Integer, String>() {
        {
            // 成功
            put(UEditorResultCode.SUCCESS, "SUCCESS");
            
            // 无效的Action
            //put(UEditorResultErrorCode.INVALID_ACTION,"\u65E0\u6548\u7684Action");
            put(UEditorResultCode.INVALID_ACTION, "无效的Action");
            
            // 配置文件初始化失败
            //put(UEditorResultCode.CONFIG_ERROR,"\u914D\u7F6E\u6587\u4EF6\u521D\u59CB\u5316\u5931\u8D25");
            put(UEditorResultCode.INVALID_ACTION, "配置文件初始化失败");
            
            // 抓取远程图片失败
            //put(UEditorResultCode.REMOTE_FAIL,"\u6293\u53D6\u8FDC\u7A0B\u56FE\u7247\u5931\u8D25");
            put(UEditorResultCode.REMOTE_FAIL, "抓取远程图片失败");
            
            // 被阻止的远程主机
            //put(UEditorResultCode.PREVENT_HOST,"\u88AB\u963B\u6B62\u7684\u8FDC\u7A0B\u4E3B\u673A");
            put(UEditorResultCode.PREVENT_HOST, "被阻止的远程主机");
            
            // 远程连接出错
            //put(UEditorResultCode.CONNECTION_ERROR,"\u8FDC\u7A0B\u8FDE\u63A5\u51FA\u9519");
            put(UEditorResultCode.CONNECTION_ERROR, "远程连接出错");
            
            // "文件大小超出限制"
            //put(UEditorResultCode.MAX_SIZE,"\u6587\u4ef6\u5927\u5c0f\u8d85\u51fa\u9650\u5236");
            put(UEditorResultCode.MAX_SIZE, "文件大小超出限制");
            
            // 权限不足， 多指写权限
            //put(UEditorResultCode.PERMISSION_DENIED, "\u6743\u9650\u4E0D\u8DB3");
            put(UEditorResultCode.PERMISSION_DENIED, "权限不足");
            
            // 创建文件失败
            //put(UEditorResultCode.FAILED_CREATE_FILE,"\u521B\u5EFA\u6587\u4EF6\u5931\u8D25");
            put(UEditorResultCode.FAILED_CREATE_FILE, "创建文件失败");
            
            // IO错误
            //put(UEditorResultCode.IO_ERROR, "IO\u9519\u8BEF");
            put(UEditorResultCode.IO_ERROR, "IO错误");
            
            // 上传表单不是multipart/form-data类型
            //put(UEditorResultCode.NOT_MULTIPART_CONTENT,"\u4E0A\u4F20\u8868\u5355\u4E0D\u662Fmultipart/form-data\u7C7B\u578B");
            put(UEditorResultCode.NOT_MULTIPART_CONTENT,
                    "上传表单不是multipart/form-data类型");
            
            // 解析上传表单错误
            //put(UEditorResultCode.PARSE_REQUEST_ERROR,"\u89E3\u6790\u4E0A\u4F20\u8868\u5355\u9519\u8BEF");
            put(UEditorResultCode.PARSE_REQUEST_ERROR, "解析上传表单错误");
            
            // 未找到上传数据
            //put(UEditorResultCode.NOTFOUND_UPLOAD_DATA,"\u672A\u627E\u5230\u4E0A\u4F20\u6570\u636E");
            put(UEditorResultCode.NOTFOUND_UPLOAD_DATA, "未找到上传数据");
            
            // 不允许的文件类型
            //put(UEditorResultCode.NOT_ALLOW_FILE_TYPE,"\u4E0D\u5141\u8BB8\u7684\u6587\u4EF6\u7C7B\u578B");
            put(UEditorResultCode.NOT_ALLOW_FILE_TYPE, "不允许的文件类型");
            
            // 指定路径不是目录
            //put(UEditorResultCode.NOT_DIRECTORY,"\u6307\u5B9A\u8DEF\u5F84\u4E0D\u662F\u76EE\u5F55");
            put(UEditorResultCode.NOT_DIRECTORY, "指定路径不是目录");
            
            // 指定路径并不存在
            //put(UEditorResultCode.NOT_EXIST, "\u6307\u5B9A\u8DEF\u5F84\u5E76\u4E0D\u5B58\u5728");
            put(UEditorResultCode.NOT_EXIST, "指定路径并不存在");
            
            // callback参数名不合法
            //put(UEditorResultCode.ILLEGAL,"Callback\u53C2\u6570\u540D\u4E0D\u5408\u6CD5");
            put(UEditorResultCode.ILLEGAL, "Callback参数名不合法");
        }
    };
    
    /**
      * 获取编码对应的结果消息<br/>
      * <功能详细描述>
      * @param code
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public static String getStateInfo(int infoCode) {
        String message = UEditorResultCode.info.get(infoCode);
        return message;
    }
}
