package com.tx.component.file.controller;

import com.netflix.client.http.HttpRequest;
import com.tx.component.file.context.FileContext;
import com.tx.component.file.model.FileDefinition;
import com.tx.component.file.resource.FileResource;
import com.tx.core.exceptions.util.ExceptionWrapperUtils;
import com.tx.core.util.UUIDUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * <br/>
 *
 * @author XRX
 * @version [版本号, 2018/05/08]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Controller
@RequestMapping("resource")
public class ResourceController {
    private Log logger = LogFactory.getLog(ResourceController.class);

    //文件上传

    @ResponseBody
    @RequestMapping("/uploadFile")
    public FileDefinition uploadFile(
            @RequestParam("uploadFile") MultipartFile uploadFile,
            ModelMap model) {
        String fileDefinitionId = "";
        FileDefinition fileDefinition = null;
        logger.info("uploadFile=" + uploadFile.getContentType());

        if (uploadFile != null) {
            String fileName = uploadFile.getName();
            InputStream input = null;
            try {
                input = uploadFile.getInputStream();
                String relativePath = UUIDUtils.generateUUID();
                logger.info("relativePath=" + relativePath);
                fileDefinition = FileContext.getContext().save(relativePath, input,
                        fileName
                );

                fileDefinitionId = fileDefinition.getId();

            } catch (IOException e) {
                logger.error(e.getMessage(), e);
                throw ExceptionWrapperUtils.wrapperIOException(e, "文件存放失败.");
            } finally {
                IOUtils.closeQuietly(input);
            }
            System.out.println("fileDefinitionId>>>>>" + fileDefinitionId);
        }
        model.put("result", true);
        model.put("data", fileDefinition);
        return fileDefinition;
    }

    //查看文件
    @RequestMapping("/view/{fileId}")
    public void view(@PathVariable String fileId, HttpServletResponse response, HttpRequest request) {
        response.setContentType("image/jpeg");
        FileResource fileResource = FileContext.getContext().getResourceById(fileId);
        String fullFileName = fileResource.getFilename();
        InputStream fis = null;
        OutputStream os = null;
        try {
            fis = fileResource.getInputStream();
            os = response.getOutputStream();
            int count = 0;
            byte[] buffer = new byte[1024 * 1024];
            while ((count = fis.read(buffer)) != -1)
                os.write(buffer, 0, count);
            os.flush();
        } catch (Exception e) {
            logger.warn("出现异常" + e);
        } finally {
            if (os != null)
                try {
                    os.close();
                } catch (IOException e) {
                    logger.warn("出现异常" + e);
                }
            if (fis != null)
                try {
                    fis.close();
                } catch (IOException e) {
                    logger.warn("出现异常" + e);
                }
        }

    }
}
