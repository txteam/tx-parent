/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2020年3月12日
 * <修改描述:>
 */
package com.tx.component.file.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.remoting.support.RemoteInvocationResult;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.netflix.client.http.HttpRequest;
import com.tx.component.file.context.FileContext;
import com.tx.component.file.model.FileDefinition;
import com.tx.component.file.model.FileDefinitionDetail;
import com.tx.component.file.resource.FileResource;
import com.tx.component.file.service.FileDefinitionDetailService;
import com.tx.component.file.util.FileContextUtils;
import com.tx.core.exceptions.util.ExceptionWrapperUtils;
import com.tx.core.remote.RemoteResult;
import com.tx.core.util.UUIDUtils;

/**
 * 文件容器<br/>
 *   // contentType: https://tool.oschina.net/commons/
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2020年3月12日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@RequestMapping("/file")
public class FileContextController implements InitializingBean{
    
    /** 日志记录句柄 */
    private Logger logger = LoggerFactory
            .getLogger(FileContextController.class);
    
    @Resource
    private FileContext fileContext;
    
    /** 文件定义详情业务层实现 */
    private FileDefinitionDetailService fileDefinitionDetailService;
    
    /**
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        //this.fileDefinitionDetailService = fileContext.get
    }

    /**
     * 可通过该方法，自行上传具备公共写的方法<br/>
     *    文件容器提供给具备公共写权限的目录的公共上传功能，如果不具备公共写，会抛出错误.
     * <功能详细描述>
     * @param file
     * @param model
     * @return [参数说明]
     * 
     * @return FileDefinitionDetail [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @ResponseBody
    @RequestMapping("/{catalog}/upload")
    public RemoteResult<FileDefinitionDetail> upload(
            @PathVariable(value = "catalog", required = true) String catalog,
            @RequestParam(value = "relativePath", required = false) String relativePath,
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "isUUIDFilename", required = false) Boolean isUUIDFilename,
            @RequestParam(value = "folderRelativePath", required = false) String folderRelativePath,
            @RequestParam(value = "filename", required = false) String filename,
            @RequestParam(value = "refId", required = false) String refId,
            @RequestParam(value = "refType", required = false) String refType,
            ModelMap model) {
        RemoteResult<FileDefinitionDetail> result = new RemoteResult<>();
        if (file == null) {
            result = new RemoteResult<>(-1, "上传文件不存在.");
            return result;
        }
        if (StringUtils.isEmpty(relativePath) && 
                StringUtils.isEmpty(folderRelativePath)) {
            result = new RemoteResult<>(-1, "上传文件需指定上传相对路径或上传目录相对路径.");
            return result;
        }
        
        //处理存储路径
        if (StringUtils.isEmpty(relativePath)) {
            //根据其他传入值生成存储路径
            if (isUUIDFilename) {
                //如果指定UUID文件名
                String filenameExtension = StringUtils
                        .getFilenameExtension(file.getOriginalFilename());
                filename = FileContextUtils.generateUUIDFilename();
                relativePath = FileContextUtils.handleRelativeFolderPath(
                        folderRelativePath, filename, filenameExtension);
            } else if (!StringUtils.isEmpty(filename)) {
                String filenameExtension = StringUtils
                        .getFilenameExtension(file.getOriginalFilename());
                relativePath = FileContextUtils.handleRelativeFolderPath(
                        folderRelativePath, filename, filenameExtension);
            } else {
                //默认使用原文件名进行存储
                String originalFilename = file.getOriginalFilename();
                String filenameExtension = StringUtils
                        .getFilenameExtension(originalFilename);
                relativePath = FileContextUtils.handleRelativeFolderPath(
                        folderRelativePath,
                        originalFilename,
                        filenameExtension);
            }
        }
        relativePath = FileContextUtils.handleRelativePath(relativePath);
        
        logger.info("   --- 上传文件: catalog:" + catalog + "|relativePath:"
                + relativePath);
        try (InputStream input = file.getInputStream()) {
            FileDefinitionDetail fdd = FileContext.getContext()
                    .save(catalog, relativePath, input);
            result.setCode(0);
            result.setData(fdd);
            return result;
        } catch (Exception e) {
            logger.error("   --- 上传文件异常: catalog:" + catalog + "|relativePath:"
                    + relativePath, e);
            result = new RemoteResult<>(-1, "上传文件异常.");
            return result;
        }
    }
    
    /**
     * 查看文件<br/>
     * <功能详细描述>
     * @param fileId
     * @param response
     * @param request [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping("/resource/fileid/{fileId}")
    public void file(@PathVariable(value = "fileId", required = true) String fileId, HttpServletResponse response,
            HttpRequest request) {
        
        //FileDefinition fd = 
        FileResource fileResource = FileContext.getContext()
                .getResourceById(fileId);
        String fullFileName = fileResource.getFilename();
        try (InputStream ins = fileResource.getInputStream();
                OutputStream os = response.getOutputStream()) {
            String contentType = Files.probeContentType(
                    Paths.get(fileResource.getRelativePath()));
            if (StringUtils.isEmpty(contentType)) {
                contentType = "application/octet-stream";
            }
            response.setContentType(contentType);
            
            int count = 0;
            byte[] buffer = new byte[1024 * 1024];
            while ((count = ins.read(buffer)) != -1)
                os.write(buffer, 0, count);
            os.flush();
        } catch (Exception e) {
            logger.warn("出现异常" + e);
        }
    }
    
    /**
     * 测试contentType
     * <功能详细描述>
     * @param args
     * @throws IOException [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static void main(String[] args) throws IOException {
        System.out.println(Files.probeContentType(Paths.get("d:/test/t.jpg")));
        System.out.println(Files.probeContentType(Paths.get("d:/test/t.png")));
        System.out.println(Files.probeContentType(Paths.get("d:/test/t.gif")));
        System.out.println(Files.probeContentType(Paths.get("d:/test/t.txt")));
        System.out.println(
                Files.probeContentType(Paths.get("d:/test/ttt")) == null);
    }
}
