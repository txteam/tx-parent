/*
s * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2014年5月11日
 * <修改描述:>
 */
package com.tx.component.file.context;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.Resource;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.tx.component.file.FileContextConstants;
import com.tx.component.file.helper.FileContextHelper;
import com.tx.component.file.model.FileDefinition;
import com.tx.component.file.resource.FileResource;
import com.tx.core.exceptions.resource.ResourceIsExistException;
import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.paged.model.PagedList;

/**
 * 文件处理容器<br/>
 * 利用该容器能够实现存放文件<br/>
 * 获取文件<br/>
 * 存放临时文件<br/>
 * 自动清理临时文件<br/>
 * 获取临时文件流<br/>
 * ... ...<br/>
 * 不建议在该封装中对业务逻辑相关查询进行支撑<br/>
 * 文件容器对文件的获取最好都是find的逻辑关系<br/>
 * 文件容器，作为控件支撑系统中的文件存放逻辑<br/>
 *
 * @author Rain.he
 * @version [版本号, 2015年3月12日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class FileContext extends FileContextBuilder
        implements InitializingBean {
    
    /** 文件容器自我引用 */
    public static FileContext context;
    
    /**
     * 返回自身唯一引用
     *
     * @return MRSContext 消息路由服务容器
     * @throws throws [异常类型] [异常说明]
     * @version [版本号, 2015年11月12日]
     * @author rain
     * @see [类、类#方法、类#成员]
     */
    public static FileContext getContext() {
        if (FileContext.context != null) {
            return FileContext.context;
        }
        synchronized (FileContext.class) {
            FileContext.context = applicationContext.getBean(beanName,
                    FileContext.class);
        }
        AssertUtils.notNull(FileContext.context, "context is null.");
        
        return FileContext.context;
    }
    
    /**
     * 保存文件<br/>
     * 如果文件已经存在，则复写当前文件<br/>
     * 如果文件不存在，则创建文件后写入<br/>
     * 如果对应文件所在的文件夹不存在，对应文件夹会自动创建<br/>
     *
     * @param relativePath 存储路径,此存储路径为文件全路径(包括扩展名)
     * @param resource     文件流
     * @return FileDefinition 文件定义的实体
     * @throws throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Transactional
    public FileDefinition save(String relativePath, Resource resource) {
        AssertUtils.notEmpty(relativePath, "relativePath is null.");
        AssertUtils.notNull(resource, "input is null.");
        
        FileDefinition fileDefinition = save(
                FileContextConstants.DEFAULT_MODULE,
                relativePath,
                resource,
                null);
        
        return fileDefinition;
    }
    
    /**
     * 保存文件<br/>
     * 如果文件已经存在，则复写当前文件<br/>
     * 如果文件不存在，则创建文件后写入<br/>
     * 如果对应文件所在的文件夹不存在，对应文件夹会自动创建<br/>
     *
     * @param relativePath 存储路径,此存储路径为文件全路径(包括扩展名)
     * @param resource     文件流
     * @param filename     文件名,此文件的实际文件名称
     * @return FileDefinition 文件定义的实体
     * @throws throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Transactional
    public FileDefinition save(String module, String relativePath,
            Resource resource) {
        AssertUtils.notEmpty(relativePath, "relativePath is null.");
        AssertUtils.notNull(resource, "input is null.");
        
        FileDefinition fileDefinition = save(module,
                relativePath,
                resource,
                null);
        
        return fileDefinition;
    }
    
    /**
     * 保存文件<br/>
     * 如果文件已经存在，则复写当前文件<br/>
     * 如果文件不存在，则创建文件后写入<br/>
     * 如果对应文件所在的文件夹不存在，对应文件夹会自动创建<br/>
     *
     * @param relativePath 存储路径,此存储路径为文件全路径(包括扩展名)
     * @param resource     文件流
     * @param filename     文件名,此文件的实际文件名称
     * @return FileDefinition 文件定义的实体
     * @throws throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Transactional
    public FileDefinition save(String relativePath, Resource resource,
            String filename) {
        AssertUtils.notEmpty(relativePath, "relativePath is null.");
        AssertUtils.notNull(resource, "input is null.");
        
        FileDefinition fileDefinition = save(
                FileContextConstants.DEFAULT_MODULE,
                relativePath,
                resource,
                filename);
        
        return fileDefinition;
    }
    
    /**
     * 保存文件<br/>
     * 如果文件已经存在，则复写当前文件<br/>
     * 如果文件不存在，则创建文件后写入<br/>
     * 如果对应文件所在的文件夹不存在，对应文件夹会自动创建<br/>
     *
     * @param module       归属模块
     * @param relativePath 存储路径,此存储路径为文件全路径(包括扩展名)
     * @param resource     文件流
     * @param filename     文件名,此文件的实际文件名称
     * @param used         文件是否被使用
     * @return FileDefinition 文件定义的实体
     * @throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Transactional
    public FileDefinition save(String module, String relativePath,
            Resource resource, String filename) {
        relativePath = FileContextHelper.handleRelativePath(relativePath);
        AssertUtils.notEmpty(relativePath, "relativePath is null.");
        AssertUtils.notNull(resource, "resource is null.");
        AssertUtils.isExist(resource, "resource is not exsit.");
        
        module = StringUtils.isEmpty(module)
                ? FileContextConstants.DEFAULT_MODULE : module;
        if (!StringUtils.isEmpty(filename)) {
            relativePath = StringUtils.applyRelativePath(relativePath,
                    filename);
        }
        
        FileDefinition fileDefinition = null;
        try (InputStream input = resource.getInputStream();) {
            fileDefinition = doSaveFile(module, relativePath, input);
        } catch (IOException e) {
            AssertUtils.wrap(e, "文件流处理异常.");
        }
        return fileDefinition;
    }
    
    /**
     * 添加文件资源<br/>
     * <功能详细描述>
     *
     * @param relativePath
     * @param resource
     * @return FileDefinition [返回类型说明]
     * @throws ResourceIsExistException [参数说明]
     * @throws throws                   [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Transactional
    public FileDefinition add(String relativePath, Resource resource)
            throws ResourceIsExistException {
        AssertUtils.notEmpty(relativePath, "relativePath is null.");
        AssertUtils.notNull(resource, "input is null.");
        
        FileDefinition fileDefinition = add(FileContextConstants.DEFAULT_MODULE,
                relativePath,
                resource,
                null);
        return fileDefinition;
    }
    
    /**
     * 保存文件<br/>
     * 如果文件已经存在，则复写当前文件<br/>
     * 如果文件不存在，则创建文件后写入<br/>
     * 如果对应文件所在的文件夹不存在，对应文件夹会自动创建<br/>
     *
     * @param relativePath 存储路径,此存储路径为文件全路径(包括扩展名)
     * @param resource     文件流
     * @param filename     文件名,此文件的实际文件名称
     * @return FileDefinition 文件定义的实体
     * @throws throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Transactional
    public FileDefinition add(String module, String relativePath,
            Resource resource) {
        AssertUtils.notEmpty(relativePath, "relativePath is null.");
        AssertUtils.notNull(resource, "input is null.");
        
        FileDefinition fileDefinition = add(module,
                relativePath,
                resource,
                null);
        
        return fileDefinition;
    }
    
    /**
     * 保存文件<br/>
     * 如果文件已经存在，则会抛出ResourceIsExistException<br/>
     * 如果文件不存在，则创建文件后写入<br/>
     * 如果对应文件所在的文件夹不存在，对应文件夹会自动创建<br/>
     * <功能详细描述>
     *
     * @param relativePath
     * @param resource
     * @param filename
     * @return FileDefinition [返回类型说明]
     * @throws ResourceIsExistException [参数说明]
     * @throws throws                   [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Transactional
    public FileDefinition add(String relativePath, Resource resource,
            String filename) throws ResourceIsExistException {
        AssertUtils.notEmpty(relativePath, "relativePath is null.");
        AssertUtils.notNull(resource, "input is null.");
        
        FileDefinition fileDefinition = add(FileContextConstants.DEFAULT_MODULE,
                relativePath,
                resource,
                filename);
        return fileDefinition;
    }
    
    /**
     * 保存文件<br/>
     * 如果文件已经存在，则会抛出ResourceIsExistException<br/>
     * 如果文件不存在，则创建文件后写入<br/>
     * 如果对应文件所在的文件夹不存在，对应文件夹会自动创建<br/>
     *
     * @param module       归属模块
     * @param relativePath 存储路径,此存储路径为文件全路径(包括扩展名)
     * @param resource     文件流
     * @param filename     文件名,此文件的实际文件名称
     * @return FileDefinition 文件定义的实体
     * @throws ResourceIsExistException 如果存在同全路径文件,则抛出此异常
     * @see [类、类#方法、类#成员]
     */
    @Transactional
    public FileDefinition add(String module, String relativePath,
            Resource resource, String filename)
            throws ResourceIsExistException {
        relativePath = FileContextHelper.handleRelativePath(relativePath);
        AssertUtils.notEmpty(relativePath, "relativePath is null.");
        AssertUtils.notNull(resource, "resource is null.");
        AssertUtils.isExist(resource, "resource is not exsit.");
        
        module = StringUtils.isEmpty(module)
                ? FileContextConstants.DEFAULT_MODULE : module;
        if (!StringUtils.isEmpty(filename)) {
            relativePath = StringUtils.applyRelativePath(relativePath,
                    filename);
        }
        
        
        FileDefinition fileDefinition = null;
        try (InputStream input = resource.getInputStream()){
            fileDefinition = doAddFile(module, relativePath, input);
        } catch (IOException e) {
            AssertUtils.wrap(e, "文件流处理异常.");
        } 
        return fileDefinition;
    }
    
    /**
     * 保存文件<br/>
     * 如果文件已经存在，则复写当前文件<br/>
     * 如果文件不存在，则创建文件后写入<br/>
     * 如果对应文件所在的文件夹不存在，对应文件夹会自动创建<br/>
     *
     * @param relativePath 存储路径,此存储路径为文件全路径(包括扩展名)
     * @param input        文件流
     * @return FileDefinition 文件定义的实体
     * @throws throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Transactional
    public FileDefinition save(String relativePath, InputStream input) {
        AssertUtils.notEmpty(relativePath, "relativePath is null.");
        AssertUtils.notNull(input, "input is null.");
        
        FileDefinition fileDefinition = save(
                FileContextConstants.DEFAULT_MODULE, relativePath, input, null);
        
        return fileDefinition;
    }
    
    /**
     * 保存文件<br/>
     * 如果文件已经存在，则复写当前文件<br/>
     * 如果文件不存在，则创建文件后写入<br/>
     * 如果对应文件所在的文件夹不存在，对应文件夹会自动创建<br/>
     *
     * @param relativePath 存储路径,此存储路径为文件全路径(包括扩展名)
     * @param input        文件流
     * @param filename     文件名,此文件的实际文件名称
     * @return FileDefinition 文件定义的实体
     * @throws throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Transactional
    public FileDefinition save(String module, String relativePath,
            InputStream input) {
        AssertUtils.notEmpty(relativePath, "relativePath is null.");
        AssertUtils.notNull(input, "input is null.");
        
        FileDefinition fileDefinition = save(module, relativePath, input, null);
        
        return fileDefinition;
    }
    
    /**
     * 保存文件<br/>
     * 如果文件已经存在，则复写当前文件<br/>
     * 如果文件不存在，则创建文件后写入<br/>
     * 如果对应文件所在的文件夹不存在，对应文件夹会自动创建<br/>
     *
     * @param relativePath 存储路径,此存储路径为文件全路径(包括扩展名)
     * @param input        文件流
     * @param filename     文件名,此文件的实际文件名称
     * @return FileDefinition 文件定义的实体
     * @throws throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Transactional
    public FileDefinition save(String relativePath, InputStream input,
            String filename) {
        AssertUtils.notEmpty(relativePath, "relativePath is null.");
        AssertUtils.notNull(input, "input is null.");
        
        FileDefinition fileDefinition = save(
                FileContextConstants.DEFAULT_MODULE,
                relativePath,
                input,
                filename);
        
        return fileDefinition;
    }
    
    /**
     * 保存文件<br/>
     * 如果文件已经存在，则复写当前文件<br/>
     * 如果文件不存在，则创建文件后写入<br/>
     * 如果对应文件所在的文件夹不存在，对应文件夹会自动创建<br/>
     *
     * @param module       归属模块
     * @param relativePath 存储路径,此存储路径为文件全路径(包括扩展名)
     * @param input        文件流
     * @param filename     文件名,此文件的实际文件名称
     * @param used         文件是否被使用
     * @return FileDefinition 文件定义的实体
     * @throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Transactional
    public FileDefinition save(String module, String relativePath,
            InputStream input, String filename) {
        AssertUtils.notEmpty(relativePath, "relativePath is null.");
        AssertUtils.notNull(input, "input is null.");
        
        module = StringUtils.isEmpty(module)
                ? FileContextConstants.DEFAULT_MODULE : module;
        if (!StringUtils.isEmpty(filename)) {
            relativePath = StringUtils.applyRelativePath(relativePath,
                    filename);
        }
        
        FileDefinition fileDefinition = doSaveFile(module, relativePath, input);
        
        return fileDefinition;
    }
    
    /**
     * 添加文件资源<br/>
     * <功能详细描述>
     *
     * @param relativePath
     * @param input
     * @return FileDefinition [返回类型说明]
     * @throws ResourceIsExistException [参数说明]
     * @throws throws                   [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Transactional
    public FileDefinition add(String relativePath, InputStream input)
            throws ResourceIsExistException {
        AssertUtils.notEmpty(relativePath, "relativePath is null.");
        AssertUtils.notNull(input, "input is null.");
        
        FileDefinition fileDefinition = add(FileContextConstants.DEFAULT_MODULE,
                relativePath,
                input,
                null);
        return fileDefinition;
    }
    
    /**
     * 保存文件<br/>
     * 如果文件已经存在，则复写当前文件<br/>
     * 如果文件不存在，则创建文件后写入<br/>
     * 如果对应文件所在的文件夹不存在，对应文件夹会自动创建<br/>
     *
     * @param relativePath 存储路径,此存储路径为文件全路径(包括扩展名)
     * @param input        文件流
     * @param filename     文件名,此文件的实际文件名称
     * @return FileDefinition 文件定义的实体
     * @throws throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Transactional
    public FileDefinition add(String module, String relativePath,
            InputStream input) {
        AssertUtils.notEmpty(relativePath, "relativePath is null.");
        AssertUtils.notNull(input, "input is null.");
        
        FileDefinition fileDefinition = add(module, relativePath, input, null);
        
        return fileDefinition;
    }
    
    /**
     * 保存文件<br/>
     * 如果文件已经存在，则会抛出ResourceIsExistException<br/>
     * 如果文件不存在，则创建文件后写入<br/>
     * 如果对应文件所在的文件夹不存在，对应文件夹会自动创建<br/>
     * <功能详细描述>
     *
     * @param relativePath
     * @param input
     * @param filename
     * @return FileDefinition [返回类型说明]
     * @throws ResourceIsExistException [参数说明]
     * @throws throws                   [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Transactional
    public FileDefinition add(String relativePath, InputStream input,
            String filename) throws ResourceIsExistException {
        AssertUtils.notEmpty(relativePath, "relativePath is null.");
        AssertUtils.notNull(input, "input is null.");
        
        FileDefinition fileDefinition = add(FileContextConstants.DEFAULT_MODULE,
                relativePath,
                input,
                filename);
        return fileDefinition;
    }
    
    /**
     * 保存文件<br/>
     * 如果文件已经存在，则会抛出ResourceIsExistException<br/>
     * 如果文件不存在，则创建文件后写入<br/>
     * 如果对应文件所在的文件夹不存在，对应文件夹会自动创建<br/>
     *
     * @param module       归属模块
     * @param relativePath 存储路径,此存储路径为文件全路径(包括扩展名)
     * @param input        文件流
     * @param filename     文件名,此文件的实际文件名称
     * @return FileDefinition 文件定义的实体
     * @throws ResourceIsExistException 如果存在同全路径文件,则抛出此异常
     * @see [类、类#方法、类#成员]
     */
    @Transactional
    public FileDefinition add(String module, String relativePath,
            InputStream input, String filename)
            throws ResourceIsExistException {
        AssertUtils.notEmpty(relativePath, "relativePath is null.");
        AssertUtils.notNull(input, "input is null.");
        
        module = StringUtils.isEmpty(module)
                ? FileContextConstants.DEFAULT_MODULE : module;
        if (!StringUtils.isEmpty(filename)) {
            relativePath = StringUtils.applyRelativePath(relativePath,
                    filename);
        }
        
        FileDefinition fileDefinition = doAddFile(module, relativePath, input);
        return fileDefinition;
    }
    
    /**
     * 根据文件定义id删除对应的文件定义及对应的资源<br/>
     * 删除对应数据库文件资源数据以及存储中对应的文件资源
     *
     * @param fileDefinitionId 文件定义id
     * @return void
     * @throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Transactional
    public boolean deleteById(String fileDefinitionId) {
        AssertUtils.notEmpty(fileDefinitionId, "fileDefinitionId is empty.");
        
        boolean flag = doDeleteById(fileDefinitionId);
        return flag;
    }
    
    /**
     * 根据文件定义id删除对应的文件定义及对应的资源<br/>
     * 删除对应数据库文件资源数据以及存储中对应的文件资源
     *
     * @param fileDefinitionId 文件定义id
     * @return void
     * @throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Transactional
    public boolean deleteByByRelativePath(String module, String relativePath) {
        AssertUtils.notEmpty(module, "module is empty.");
        AssertUtils.notEmpty(relativePath, "relativePath is empty.");
        
        FileDefinition fileDefinition = doFindByRelativePath(module,
                relativePath);
        if (fileDefinition == null) {
            return false;
        }
        boolean flag = doDeleteById(fileDefinition.getId());
        return flag;
    }
    
    /**
     * 根据文件定义id获取对应的文件定义实例对象<br/>
     * <功能详细描述>
     *
     * @param fileDefinitionId 文件定义id
     * @return FileDefinition 文件定义实体
     * @throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public FileDefinition findById(String fileDefinitionId) {
        AssertUtils.notEmpty(fileDefinitionId, "fileDefinitionId is empty.");
        
        FileDefinition res = doFindById(fileDefinitionId);
        
        return res;
    }
    
    /**
     * 根据文件id获取对应的文件定义(含fileResource)<br/>
     * <功能详细描述>
     *
     * @param fileDefinitionId
     * @return FileDefinition [返回类型说明]
     * @throws throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public FileDefinition findWithResourceById(String fileDefinitionId) {
        AssertUtils.notEmpty(fileDefinitionId, "fileDefinitionId is empty.");
        
        FileDefinition fileDefinition = doFindById(fileDefinitionId);
        if (fileDefinition != null) {
            FileResource fileResource = doGetFileResource(fileDefinition);
            fileDefinition.setResource(fileResource);
        }
        
        return fileDefinition;
    }
    
    /**
     * 根据文件定义id获取文件定义对应的资源<br/>
     * <功能详细描述>
     *
     * @param fileDefinitionId 文件定义id
     * @return Resource 文件资源
     * @throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public FileResource getResourceById(String fileDefinitionId) {
        AssertUtils.notEmpty(fileDefinitionId, "fileDefinitionId is empty.");
        
        FileDefinition fileDefinition = doFindById(fileDefinitionId);
        if (fileDefinition == null) {
            return null;
        }
        
        FileResource fileResource = doGetFileResource(fileDefinition);
        return fileResource;
    }
    
    /**
     * 根据所属模块以及存储相对路径目录查询对应的文件定义列表<br/>
     * <功能详细描述>
     *
     * @param module
     * @param relativePathFolder
     * @param params
     * @return List<FileDefinition> [返回类型说明]
     * @throws throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public List<FileDefinition> queryList(String module, String relativeFolder,
            String[] filenameExtensions, Map<String, Object> params) {
        AssertUtils.notEmpty(module, "module is empty.");
        
        List<FileDefinition> resList = doQueryList(module,
                relativeFolder,
                filenameExtensions,
                params);
        return resList;
    }
    
    /**
     * 根据所属模块以及存储相对路径目录查询对应的文件定义列表<br/>
     * <功能详细描述>
     *
     * @param relativePathFolder
     * @param module
     * @param params
     * @return List<FileDefinition> [返回类型说明]
     * @throws throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public PagedList<FileDefinition> queryPagedList(String module,
            String relativeFolder, String[] filenameExtensions,
            Map<String, Object> params, int pageIndex, int pageSize) {
        AssertUtils.notEmpty(module, "module is empty.");
        
        PagedList<FileDefinition> resPagedList = doQueryPagedList(module,
                relativeFolder,
                filenameExtensions,
                params,
                pageIndex,
                pageSize);
        return resPagedList;
    }
}
