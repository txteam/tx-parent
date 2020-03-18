/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2014年11月24日
 * <修改描述:>
 */
package com.tx.component.file.resource;

import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

import com.tx.component.file.model.FileDefinition;

/**
 * 文件定义资源实体<br/>
 * 接口的实现可以是基于SystemFile的实现<br/>
 * 也可以是基于VFSFile的实现<br/>
 * 或者是基于其他的实现<br/>
 * 
 * @author Administrator
 * @version [版本号, 2014年11月24日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public interface FileResource {
    
    /**
     * 获取文件定义<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return FileDefinition [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    FileDefinition getFileDefinition();
    
    /**
     * 获取文件存储相对路径<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    default String getRelativePath() {
        String relativePath = getFileDefinition().getRelativePath();
        return relativePath;
    }
    
    /**
     * 获取文件名<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    default String getFilename() {
        String filename = getFileDefinition().getFilename();
        return filename;
    }
    
    /**
     * 获取文件名后缀<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    default String getFilenameExtension() {
        String filenameExtension = getFileDefinition().getFilenameExtension();
        return filenameExtension;
    }
    
    /**
     * 判断文件定义是否存在<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    boolean exists();
    
    /**
     * 删除文件<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public void delete();
    
    /**
     * 存储输入流,如果资源已经存在，则覆盖已存在的资源<br/>
     * <功能详细描述>
     * @param inputStream [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    void save(InputStream inputStream);
    
    /**
     * 新增保存资源，如果资源已经存在，则抛出资源已经存在的异常<br/>
     * <功能详细描述>
     * @param inputStream [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public void add(InputStream inputStream);
    
    /**
     * 获取输入流<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return InputStream [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public InputStream getInputStream() throws IOException;
    
    /**
     * 获取读取通道<br/>
     * <功能详细描述>
     * @return
     * @throws IOException [参数说明]
     * 
     * @return ReadableByteChannel [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    default ReadableByteChannel readableChannel() throws IOException {
        return Channels.newChannel(getInputStream());
    }
}
