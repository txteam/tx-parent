/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2017年1月18日
 * <修改描述:>
 */
package com.tx.component.file.viewhandler;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import com.tx.component.file.context.FileContext;
import com.tx.component.file.model.FileDefinition;
import com.tx.component.file.resource.FileResource;

/**
 * 视图处理器<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2017年1月18日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class FileContextHttpRequestHandler extends ResourceHttpRequestHandler {
    
    /** 日志记录器 */
    protected static final Logger logger = LoggerFactory.getLogger(ResourceHttpRequestHandler.class);
    
    /** 视图处理器注册表 */
    @javax.annotation.Resource(name = "fileContext.viewHandlerRegistry")
    private ViewHandlerRegistry viewHandlerRegistry;
    
    /** 文件容器 */
    @javax.annotation.Resource(name = "fileContext")
    private FileContext fileContext;
    
    /**
     * 获取视图资源<br/>
     * @param request
     * @return
     */
    protected final Resource getResource(HttpServletRequest request) {
        String path = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
        if (path == null) {
            throw new IllegalStateException("Required request attribute '"
                    + HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE
                    + "' is not set");
        }
        path = processPath(path);
        if ((!(StringUtils.hasText(path))) || (isInvalidPath(path))) {
            if (logger.isDebugEnabled()) {
                logger.debug("Ignoring invalid resource path [" + path + "]");
            }
            return null;
        }
        if (path.contains("%")) {
            try {
                if (isInvalidPath(URLDecoder.decode(path, "UTF-8"))) {
                    if (logger.isTraceEnabled()) {
                        logger.trace("Ignoring invalid resource path with escape sequences ["
                                + path + "].");
                    }
                    return null;
                }
            } catch (UnsupportedEncodingException localUnsupportedEncodingException) {
            } catch (IllegalArgumentException localIllegalArgumentException) {
            }
        }
        boolean isRestRequest = false;
        String[] paths = org.apache.commons.lang3.StringUtils.splitByWholeSeparator(path,
                "/");
        if (!StringUtils.isEmpty(request.getParameter("fileDefinitionId"))
                || !StringUtils.isEmpty(request.getParameter("fileId"))) {
            isRestRequest = false;
        } else {
            isRestRequest = true;
        }
        
        //解析请求路径，以及模块名
        String fileId = doGetFileId(isRestRequest, paths, request);//从请求中获取文件id
        if (StringUtils.isEmpty(fileId)) {
            //如果文件id为空，则返回空
            return null;
        }
        
        //获取视图处理器名
        String viewHandlerName = doGetViewHandlerName(isRestRequest,
                paths,
                request);
        FileDefinition fileDefinition = this.fileContext.findById(fileId);
        if (fileDefinition == null) {
            return null;
        }
        //获取视图处理器，传入的视图处理器名允许为空
        ViewHandler viewHandler = this.viewHandlerRegistry.getViewHandler(viewHandlerName);
        FileResource fileResource = viewHandler.getResource(fileDefinition,
                request,
                path,
                fileId,
                viewHandlerName);
        Resource res = fileResource == null ? null
                : new FileDefinitionResource(fileDefinition, fileResource);
        return res;
    }
    
    /**
      * 获取文件id<br/>
      * <功能详细描述>
      * @param isRestRequest
      * @param paths
      * @param parameterMap
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    private String doGetFileId(final boolean isRestRequest,
            final String[] paths, final HttpServletRequest request) {
        String fileId = null;
        if (isRestRequest) {
            fileId = paths[paths.length - 1];
        } else {
            fileId = request.getParameter("fileDefinitionId");
            if (StringUtils.isEmpty(fileId)) {
                fileId = request.getParameter("fileId");
            }
        }
        //如果请求路径中含有.则提取.以前的数据
        if (!StringUtils.isEmpty(fileId) && fileId.indexOf(".") > 0) {
            fileId = fileId.substring(0, fileId.indexOf("."));
        }
        return fileId;
    }
    
    /**
      * 获取视图处理器名<br/>
      * <功能详细描述>
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    private String doGetViewHandlerName(final boolean isRestRequest,
            final String[] paths, final HttpServletRequest request) {
        String viewHandlerName = null;
        if (isRestRequest) {
            viewHandlerName = paths.length <= 1 ? null
                    : paths[paths.length - 2];
        } else {
            viewHandlerName = paths.length <= 0 ? null
                    : paths[paths.length - 1];
        }
        //如果请求路径中含有.则提取.以前的数据
        if (!StringUtils.isEmpty(viewHandlerName)
                && viewHandlerName.indexOf(".") > 0) {
            viewHandlerName = viewHandlerName.substring(0,
                    viewHandlerName.indexOf("."));
        }
        return viewHandlerName;
    }
    
    /**
     * @param 对viewHandlerRegistry进行赋值
     */
    public void setViewHandlerRegistry(ViewHandlerRegistry viewHandlerRegistry) {
        this.viewHandlerRegistry = viewHandlerRegistry;
    }
}
