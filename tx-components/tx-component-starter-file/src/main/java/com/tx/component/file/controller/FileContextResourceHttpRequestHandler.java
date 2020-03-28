/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2017年1月18日
 * <修改描述:>
 */
package com.tx.component.file.controller;

import java.io.IOException;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.AbstractResource;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import com.tx.component.file.context.FileContext;
import com.tx.component.file.model.FileResourceDetail;
import com.tx.core.TxConstants;

/**
 * 视图处理器<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2017年1月18日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class FileContextResourceHttpRequestHandler
        extends ResourceHttpRequestHandler {
    
    /** 日志记录器 */
    protected static final Logger logger = LoggerFactory
            .getLogger(ResourceHttpRequestHandler.class);
    
    /** 文件容器 */
    @javax.annotation.Resource(name = "fileContext")
    private FileContext fileContext;
    
    /**
     * SpringFileResource
     * <功能详细描述>
     * 
     * @author  Administrator
     * @version  [版本号, 2020年3月20日]
     * @see  [相关类/方法]
     * @since  [产品/模块版本]
     */
    public static class SpringFileResource extends AbstractResource {
        
        /** 文件资源 */
        private FileResourceDetail fileResourceDetail;
        
        /** <默认构造函数> */
        public SpringFileResource() {
            super();
        }
        
        /** <默认构造函数> */
        public SpringFileResource(FileResourceDetail fileResourceDetail) {
            super();
            this.fileResourceDetail = fileResourceDetail;
        }
        
        /**
         * @return
         * @throws IOException
         */
        @Override
        public InputStream getInputStream() throws IOException {
            return this.fileResourceDetail.getResource().getInputStream();
        }
        
        /**
         * @return
         */
        @Override
        public boolean exists() {
            return this.fileResourceDetail.getResource().exists();
        }
        
        /**
         * 
         * @return
         */
        @Override
        public String getDescription() {
            StringBuilder sb = new StringBuilder(
                    TxConstants.INITIAL_STR_LENGTH);
            sb.append("FileContext.resource:")
                    .append("{")
                    .append("catalog:")
                    .append(this.fileResourceDetail.getCatalog())
                    .append(",")
                    .append("relativePath:")
                    .append(this.fileResourceDetail.getResource()
                            .getRelativePath())
                    .append("}.");
            return sb.toString();
        }
    }
}
