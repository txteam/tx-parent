/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2017年3月8日
 * <修改描述:>
 */
package com.tx.component.file.ueditor.controller;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.tx.component.file.context.FileContext;
import com.tx.component.file.model.FileDefinition;
import com.tx.component.file.ueditor.model.DefaultUEditorResult;
import com.tx.component.file.ueditor.model.MultiUEditorResult;
import com.tx.component.file.ueditor.model.UEditorActionMap;
import com.tx.component.file.ueditor.model.UEditorConfigManager;
import com.tx.component.file.ueditor.model.UEditorResult;
import com.tx.component.file.ueditor.model.UEditorResultCode;
import com.tx.core.exceptions.SILException;
import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.util.UUIDUtils;

/**
 * AbstractFileContextUEditorController相关接口实现<br/>
 *    一般富文本编辑器中的图片，文件，视频，等附件上传存储的逻辑是独立于业务逻辑存在
 *    所以相关数据被删除（如：内容，产品）其对应的文件定义并不能马上被联动删除。
 *    可考虑在业务复杂度上去后，扩展FileDefinition的Entry实现，以及使用定时调度来删除对应多余的文件即可
 *    当前版本暂可不作处理<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2017年3月8日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public abstract class AbstractUEditorController
        implements InitializingBean, ApplicationContextAware {
    
    /** 日志记录器 */
    protected Logger logger = LoggerFactory
            .getLogger(AbstractUEditorController.class);
    
    /** callbackName正则表达式 */
    private static final Pattern FILENAME_PATTERN = Pattern
            .compile("^\\w+?\\.[a-zA-Z]+$");
    
    /** callbackName正则表达式 */
    private static final Pattern CALLBACK_PATTERN = Pattern
            .compile("^[a-zA-Z_]+[\\w0-9_]*$");
    
    /** spring applicationContext 句柄 */
    protected ApplicationContext applicationContext;
    
    /** spring configManager 句柄 */
    protected UEditorConfigManager configManager = null;
    
    /**
     * @param applicationContext
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        this.applicationContext = applicationContext;
    }
    
    /**
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        //启动期间加载Controller同级的目录中的配置文件
        try (InputStream input = getClass().getResourceAsStream("config.js");) {
            String configContext = IOUtils.toString(input, "UTF-8");
            //过滤输入字符串, 剔除多行注释以及替换掉反斜杠
            configContext = configContext.replaceAll("/\\*[\\s\\S]*?\\*/", "");
            //获取配置实例
            this.configManager = UEditorConfigManager
                    .newInstance(configContext);
        } catch (Exception e) {
            AssertUtils.wrap(e, "解析配置文件异常.");
        }
    }
    
    /**
      * 验证Callback名<br/>
      * <功能详细描述>
      * @param callback
      * @return [参数说明]
      * 
      * @return boolean [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    protected boolean validateCallback(String callback) {
        if (!CALLBACK_PATTERN.matcher(callback).matches()) {
            return false;
        }
        return true;
    }
    
    /**
      * 获取配置<br/>
      * 服务于getConfig的请求，jsonp调用的返回.
      * <功能详细描述>
      * @param callback
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    @ResponseBody
    @RequestMapping("/execute")
    public String execute(
            @RequestParam(value = "callback", required = false) String callback,
            @RequestParam(value = "action", required = false) String actionType,
            HttpServletRequest request) {
        //        @SuppressWarnings("unchecked")
        //        Enumeration<String> names = request.getParameterNames();
        //        while (names.hasMoreElements()) {
        //            String nameTemp = names.nextElement();
        //            String[] values = request.getParameterValues(nameTemp);
        //            System.out.print(nameTemp + " : ");
        //            for (String valueTemp : values) {
        //                System.out.print(valueTemp + " , ");
        //            }
        //            System.out.println("");
        //        }
        
        String json = null;
        if (!StringUtils.isEmpty(callback) && !validateCallback(callback)) {
            //callback不为空，并且验证不通过时
            return new DefaultUEditorResult(false, UEditorResultCode.ILLEGAL)
                    .toJSONString();
        }
        
        try {
            json = this.invoke(actionType, request);
        } catch (SILException e) {
            logger.error(e.getErrorMessage(), e);
            
            return new DefaultUEditorResult(false, e.getErrorMessage())
                    .toJSONString();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            
            return new DefaultUEditorResult(false, e.getMessage())
                    .toJSONString();
        }
        
        //callback不为空，并且验证不通过时
        if (!StringUtils.isEmpty(callback)) {
            json = callback + "(" + json + ");";
        }
        return json;
    }
    
    /**
      * 注入调用<br/>
      * <功能详细描述>
      * @param callback
      * @param actionType
      * @param uploadFile
      * @param entityId
      * @param fileId
      * @param request
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    protected String invoke(String actionType, HttpServletRequest request) {
        if (StringUtils.isEmpty(actionType)
                || !UEditorActionMap.mapping.containsKey(actionType)) {
            return new DefaultUEditorResult(false,
                    UEditorResultCode.INVALID_ACTION).toJSONString();
        }
        
        UEditorResult result = null;
        int actionCode = UEditorActionMap.getActionCode(actionType);
        Map<String, Object> config = null;
        switch (actionCode) {
            case UEditorActionMap.CONFIG:
                String json = this.configManager.getAllConfig().toString();
                return json;
            case UEditorActionMap.UPLOAD_SCRAWL:
                //上传涂鸦：upfile为base64编码的字符串: 存储于图片存放目录
                config = this.configManager.getConfig(actionCode);
                result = uploadBase64File(request,
                        config,
                        "image",
                        UUIDUtils.generateUUID() + "scrawl.jpg");
                break;
            case UEditorActionMap.UPLOAD_IMAGE:
                //上传图片
                config = this.configManager.getConfig(actionCode);
                result = uploadFile(request, config, "image");
                break;
            case UEditorActionMap.UPLOAD_VIDEO:
                //上传视频
                config = this.configManager.getConfig(actionCode);
                result = uploadFile(request, config, "video");
                break;
            case UEditorActionMap.UPLOAD_FILE:
                //上传文件
                config = this.configManager.getConfig(actionCode);
                result = uploadFile(request, config, "file");
                break;
            //图片抓取功能完全可以人工存储以后再上传进去进行代替，如果增加该功能需要额外考虑系统的安全性，所以此处不提供抓取功能
            //case UEditorActionMap.CATCH_IMAGE:
            //    config = configManager.getConfig(actionCode);
            //    String[] list = this.request.getParameterValues((String) conf.get("fieldName"));
            //    result = new ImageHunter(conf).capture(list);
            //    break;
            case UEditorActionMap.LIST_IMAGE:
                config = configManager.getConfig(actionCode);
                result = listFile(request, config, "image");
                break;
            case UEditorActionMap.LIST_FILE:
                config = configManager.getConfig(actionCode);
                result = listFile(request, config, "file");
                break;
            case UEditorActionMap.DELETE_IMAGE:
                result = deleteFile(request, "image");
                break;
            case UEditorActionMap.DELETE_FILE:
                result = deleteFile(request, "file");
                break;
        }
        
        //返回结果的json字符串
        String resultJson = result.toJSONString();
        return resultJson;
    }
    
    /**
      * 文件存储模块<br/>
      * <功能详细描述>
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    protected abstract String module();
    
    /**
     * 存储文件名<br/>
     *    如果文件名相同则将进行覆盖<br/>
     * <功能详细描述>
     * @param request
     * @param fileType
     * @param originalFilename
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    protected String generateFilename(HttpServletRequest request,
            String fileType, String originalFilename) {
        String filenameExtention = org.springframework.util.StringUtils
                .getFilenameExtension(originalFilename);
        String filename = originalFilename;
        if (!FILENAME_PATTERN.matcher(originalFilename).matches()) {
            filename = UUIDUtils.generateUUID() + "." + filenameExtention;
        }
        return filename;
    }
    
    /**
     * 生成存储的相对路径<br/>
     *      fileName != null时生成的存储路径
     *      fileName == null是就可以得到存储的目录
     * @param request
     * @param config
     * @param fileType
     * @param fileName
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    protected abstract String generateRelativePath(HttpServletRequest request,
            Map<String, Object> config, String fileType, String fileName);
    
    /**
      * 存放文件后调用<br/>
      * <功能详细描述>
      * @param request
      * @param config
      * @param fileType
      * @param fileDefinition [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    protected void doAfterSaveFile(HttpServletRequest request,
            Map<String, Object> config, String fileType,
            FileDefinition fileDefinition) {
        return;
    }
    
    /**
     * 存放文件后调用<br/>
     * <功能详细描述>
     * @param request
     * @param config
     * @param fileType
     * @param fileDefinition [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    protected void doAfterDeleteFile(HttpServletRequest request,
            String fileType, FileDefinition fileDefinition) {
        return;
    }
    
    /**
      * 删除上传文件<br/>
      * <功能详细描述>
      * @param request
      * @param config
      * @param fileType
      * @return [参数说明]
      * 
      * @return UEditorResult [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    protected UEditorResult deleteFile(HttpServletRequest request,
            String fileType) {
        String fileId = request.getParameter("fileId");
        if (StringUtils.isEmpty(fileId)) {
            return new DefaultUEditorResult(false, "文件id为空.");
        }
        FileDefinition fd = FileContext.getContext().findById(fileId);
        if (fd == null) {
            return new DefaultUEditorResult(false, "文件不存在.");
        }
        
        FileContext.getContext().deleteById(fileId);
        
        UEditorResult result = new DefaultUEditorResult(true);
        
        doAfterDeleteFile(request, fileType, fd);
        return result;
    }
    
    /**
      * 查询历史已经上传的文件<br/>
      * <功能详细描述>
      * @param request
      * @param config
      * @param fileType
      * @return [参数说明]
      * 
      * @return UEditorResult [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    protected UEditorResult listFile(HttpServletRequest request,
            Map<String, Object> config, String fileType) {
        String start = request.getParameter("start");
        int startIndex = NumberUtils.toInt(start, 0);
        
        String[] filenameExtensions = (String[]) config.get("allowFiles");
        int count = NumberUtils.toInt(String.valueOf(config.get("count")), 20);
        String relativeFolder = generateRelativePath(request,
                config,
                fileType,
                null);
        List<FileDefinition> fdList = FileContext.getContext()
                .queryList(module(), relativeFolder, filenameExtensions, null);
        
        MultiUEditorResult result = null;
        if (startIndex < 0 || startIndex > fdList.size()) {
            result = new MultiUEditorResult(true);
        } else {
            List<FileDefinition> subFdList = new ArrayList<>();
            for (int i = startIndex; i < fdList.size()
                    && i < (startIndex + count); i++) {
                subFdList.add(fdList.get(i));
            }
            
            result = new MultiUEditorResult(true);
            for (FileDefinition fd : subFdList) {
                if (fd == null) {
                    break;
                }
                UEditorResult fdResult = new DefaultUEditorResult(true);
                fdResult.putInfo("id", fd.getId());
                fdResult.putInfo("relativePath", fd.getRelativePath());
                fdResult.putInfo("url", fd.getViewUrl());
                fdResult.putInfo("type", fd.getFilenameExtension());
                fdResult.putInfo("fileName", fd.getFilename());
                fdResult.putInfo("original", fd.getFilename());
                
                result.addResult(fdResult);
            }
        }
        
        result.putInfo("start", startIndex);
        result.putInfo("total", fdList.size());
        return result;
    }
    
    /**
     * 更新银行信息中文件信息<br/>
     * // uploadscrawl
     * array(
     *      "state" => "",          //上传状态，上传成功时必须返回"SUCCESS"
     *      "title" => "",          //新文件名
     *      "original" => "",       //原始文件名
     *      "type" => ""            //文件类型
     *      "size" => "",           //文件大小
     *  )
     * <功能详细描述>
     * @param request
     * @param config
     * @param fileType //文件类型
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    private UEditorResult uploadBase64File(HttpServletRequest request,
            Map<String, Object> config, String fileType,
            String originalFilename) {
        AssertUtils.notEmpty(fileType, "fileType is empty.");
        AssertUtils.notEmpty(originalFilename, "originalFilename is empty.");
        
        DefaultUEditorResult result = null;
        String filedName = (String) config.get("fieldName");
        String content = request.getParameter(filedName);
        byte[] data = Base64.decodeBase64(content);
        Resource inputResource = new ByteArrayResource(data);
        
        //生成存储相对路径
        long maxSize = ((Long) config.get("maxSize")).longValue();
        if (data.length > maxSize) {
            return new DefaultUEditorResult(false, UEditorResultCode.MAX_SIZE);
        }
        String filename = generateFilename(request, fileType, originalFilename);
        String relativePath = generateRelativePath(request,
                config,
                fileType,
                filename);
        FileDefinition fd = null;
        try {
            fd = FileContext.getContext().save(module(),
                    relativePath,
                    inputResource);
            result = new DefaultUEditorResult(true);
            
            //调用后置逻辑
            doAfterSaveFile(request, config, fileType, fd);
            
            result.putInfo("id", fd.getId());
            result.putInfo("relativePath", fd.getRelativePath());
            result.putInfo("url", fd.getViewUrl());
            result.putInfo("type", fd.getFilenameExtension());
            result.putInfo("fileName", fd.getFilename());
            result.putInfo("original", fd.getFilename());
        } catch (SILException e) {
            logger.info(e.getMessage(), e);
            
            result = new DefaultUEditorResult(false);
            result.putInfo("errorCode", e.getErrorCode());
            result.putInfo("errorMessage", e.getErrorMessage());
            result.setInfo(e.getErrorMessage());
        } catch (Exception e) {
            logger.info(e.getMessage(), e);
            
            result = new DefaultUEditorResult(false);
            result.putInfo("errorMessage", e.getMessage());
            result.setInfo(e.getMessage());
        }
        return result;
    }
    
    /**
     * 更新银行信息中文件信息<br/>
     * // uploadimage
     * // uploadvideo
     * // uploadfile
     * array(
     *      "state" => "",          //上传状态，上传成功时必须返回"SUCCESS"
     *      "title" => "",          //新文件名
     *      "original" => "",       //原始文件名
     *      "type" => ""            //文件类型
     *      "size" => "",           //文件大小
     *  )
     * <功能详细描述>
     * @param request
     * @param config
     * @param fileType //文件类型
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    private UEditorResult uploadFile(HttpServletRequest request,
            Map<String, Object> config, String fileType) {
        //将当前上下文初始化给  CommonsMutipartResolver （多部分解析器）
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
                request.getSession().getServletContext());
        if (!multipartResolver.isMultipart(request)) {
            //检查form中是否有enctype="multipart/form-data"
            return new DefaultUEditorResult(false,
                    UEditorResultCode.NOT_MULTIPART_CONTENT);
        }
        DefaultUEditorResult result = null;
        //将request变成多部分request
        MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
        MultipartFile upfile = getUploadFile(multiRequest, config);
        if (upfile == null) {
            return new DefaultUEditorResult(false,
                    UEditorResultCode.NOTFOUND_UPLOAD_DATA);
        }
        //生成存储相对路径
        String originalFilename = upfile.getOriginalFilename();
        String filenameExtention = org.springframework.util.StringUtils
                .getFilenameExtension(originalFilename);
        //验证文件扩展名是否合法
        if (!validateFilenameExtention(filenameExtention,
                (String[]) config.get("allowFiles"))) {
            return new DefaultUEditorResult(false,
                    UEditorResultCode.NOT_ALLOW_FILE_TYPE);
        }
        long maxSize = ((Long) config.get("maxSize")).longValue();
        if (upfile.getSize() > maxSize) {
            return new DefaultUEditorResult(false, UEditorResultCode.MAX_SIZE);
        }
        
        String filename = generateFilename(request, fileType, originalFilename);
        String relativePath = generateRelativePath(multiRequest,
                config,
                fileType,
                filename);
        InputStream input = null;
        FileDefinition fd = null;
        try {
            input = upfile.getInputStream();
            fd = FileContext.getContext().save(module(), relativePath, input);
            result = new DefaultUEditorResult(true);
            
            //调用后置逻辑
            doAfterSaveFile(multiRequest, config, fileType, fd);
            
            result.putInfo("id", fd.getId());
            result.putInfo("relativePath", fd.getRelativePath());
            result.putInfo("url", fd.getViewUrl());
            result.putInfo("type", fd.getFilenameExtension());
            result.putInfo("fileName", fd.getFilename());
            result.putInfo("original", fd.getFilename());
        } catch (SILException e) {
            logger.info(e.getMessage(), e);
            
            result = new DefaultUEditorResult(false);
            result.putInfo("errorCode", e.getErrorCode());
            result.putInfo("errorMessage", e.getErrorMessage());
            result.setInfo(e.getErrorMessage());
        } catch (Exception e) {
            logger.info(e.getMessage(), e);
            
            result = new DefaultUEditorResult(false);
            result.putInfo("errorMessage", e.getMessage());
            result.setInfo(e.getMessage());
        } finally {
            IOUtils.closeQuietly(input);
        }
        return result;
    }
    
    /**
      * 验证文件扩展名是否合法<br/>
      * <功能详细描述>
      * @param type
      * @param allowTypes
      * @return [参数说明]
      * 
      * @return boolean [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    protected boolean validateFilenameExtention(String type,
            String[] allowTypes) {
        if (StringUtils.isEmpty(type) || ArrayUtils.isEmpty(allowTypes)) {
            return false;
        }
        for (String allowTemp : allowTypes) {
            if (allowTemp.toUpperCase().indexOf(type.toUpperCase()) >= 0) {
                return true;
            }
        }
        return false;
    }
    
    /**
      * 获取上传的文件<br/>
      * <功能详细描述>
      * @param multiRequest
      * @return [参数说明]
      * 
      * @return MultipartFile [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    protected MultipartFile getUploadFile(
            MultipartHttpServletRequest multiRequest,
            Map<String, Object> config) {
        String filedName = (String) config.get("fieldName");
        MultipartFile resFile = multiRequest.getFile(filedName);
        if (resFile != null) {
            return resFile;
        }
        //获取multiRequest 中所有的文件名
        Iterator<String> fileNameIterator = multiRequest.getFileNames();
        while (fileNameIterator.hasNext()) {
            String fileName = fileNameIterator.next();
            resFile = multiRequest.getFile(fileName);
            if (resFile != null) {
                return resFile;
            }
        }
        return null;
    }
}
