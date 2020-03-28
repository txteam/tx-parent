/*
s * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2014年5月11日
 * <修改描述:>
 */
package com.tx.component.file.context;

import java.io.InputStream;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.tx.component.file.FileContextConstants;
import com.tx.component.file.model.FileDefinition;
import com.tx.component.file.model.FileDefinitionDetail;
import com.tx.component.file.resource.FileResource;
import com.tx.component.file.service.FileDefinitionService;
import com.tx.core.exceptions.resource.ResourceIsExistException;
import com.tx.core.exceptions.util.AssertUtils;

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
     * 获取文件容器句柄<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return FileContext [返回类型说明]
     * @exception throws [异常类型] [异常说明]
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
     * @param catalog 文件存储目录
     * @param relativePath 存储路径,此存储路径为文件全路径(包括扩展名)
     * @param input        文件流
     * 
     * @return FileDefinitionDetail 文件定义的实体,以及文件对应访问地址,权限等
     * @throws throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Transactional
    public FileDefinitionDetail save(String catalog, String relativePath,
            InputStream input) {
        AssertUtils.notEmpty(relativePath, "relativePath is empty.");
        AssertUtils.notNull(input, "input is null.");
        
        if (StringUtils.isEmpty(catalog)) {
            catalog = FileContextConstants.DEFAULT_CATALOG;
        }
        FileDefinition fd = new FileDefinition(relativePath);
        fd.setCatalog(catalog);
        fd.setRelativePath(relativePath);
        FileDefinitionDetail fdd = save(fd, input);
        return fdd;
    }
    
    /**
     * 保存文件<br/>
     * 如果文件已经存在，则复写当前文件<br/>
     * 如果文件不存在，则创建文件后写入<br/>
     * 如果对应文件所在的文件夹不存在，对应文件夹会自动创建<br/>
     * 
     * <功能详细描述>
     * @param fd
     * @param input
     * @return [参数说明]
     * 
     * @return FileDefinitionDetail [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Transactional
    public FileDefinitionDetail save(FileDefinition fd, InputStream input) {
        AssertUtils.notNull(fd, "fd is null.");
        AssertUtils.notEmpty(fd.getRelativePath(),
                "relativePath.relativePath is empty.");
        AssertUtils.notNull(input, "input is null.");
        
        fd = this.fileDefinitionService.save(fd);
        FileDefinitionDetail fdd = this.fileCatalogComposite.setup(fd);
        fdd.getResource().save(input);
        return fdd;
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
    public FileDefinition add(String catalog, String relativePath,
            InputStream input) {
        AssertUtils.notEmpty(relativePath, "relativePath is empty.");
        AssertUtils.notNull(input, "input is null.");
        
        if (StringUtils.isEmpty(catalog)) {
            catalog = FileContextConstants.DEFAULT_CATALOG;
        }
        FileDefinition fd = new FileDefinition(relativePath);
        fd.setCatalog(catalog);
        fd.setRelativePath(relativePath);
        FileDefinitionDetail fdd = add(fd, input);
        return fdd;
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
    public FileDefinitionDetail add(FileDefinition fd, InputStream input)
            throws ResourceIsExistException {
        AssertUtils.notNull(fd, "fd is null.");
        AssertUtils.notEmpty(fd.getRelativePath(),
                "relativePath.relativePath is empty.");
        AssertUtils.notNull(input, "input is null.");
        
        this.fileDefinitionService.insert(fd);
        FileDefinitionDetail fdd = this.fileCatalogComposite.setup(fd);
        fdd.getResource().add(input);
        return fdd;
    }
    
    /**
     * 根据Id删除文件，并一并删除对应的资源<br/>
     * <功能详细描述>
     * @param id
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Transactional
    public boolean deleteById(String id) {
        boolean flag = deleteById(id, true);
        return flag;
    }
    
    /**
     * 根据文件定义id删除对应的文件定义及对应的资源<br/>
     * 删除对应数据库文件资源数据以及存储中对应的文件资源
     * <功能详细描述>
     * @param id
     * @param recycleAble 是否可回收
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Transactional
    public boolean deleteById(String id, boolean recycleAble) {
        AssertUtils.notEmpty(id, "fileDefinitionId is empty.");
        
        FileDefinition fd = this.fileDefinitionService.findById(id);
        if (fd == null) {
            return true;
        }
        boolean flag = false;
        if (recycleAble) {
            //可回收资源，仅移动数据至历史表，实际资源不进行删除
            flag = this.fileDefinitionService.moveToHis(fd);
        } else {
            //不可回收，则直接删除数据，以及资源
            FileDefinitionDetail fdd = this.fileCatalogComposite.setup(fd);
            FileResource fr = fdd.getResource();
            //删除数据
            flag = this.fileDefinitionService.deleteById(id) > 0;
            //删除资源
            fr.delete();
        }
        return flag;
    }
    
    /**
     * 根据相对路径删除文件定义<br/>
     * <功能详细描述>
     * @param catalog
     * @param relativePath
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public boolean deleteByByRelativePath(String catalog, String relativePath) {
        boolean flag = deleteByByRelativePath(catalog, relativePath, true);
        return flag;
    }
    
    /**
     * 根据文件定义id删除对应的文件定义及对应的资源<br/>
     * 删除对应数据库文件资源数据以及存储中对应的文件资源
     * <功能详细描述>
     * @param catalog
     * @param relativePath
     * @param recycleAble
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Transactional
    public boolean deleteByByRelativePath(String catalog, String relativePath,
            boolean recycleAble) {
        AssertUtils.notEmpty(relativePath, "relativePath is empty.");
        AssertUtils.notEmpty(catalog, "catalog is empty.");
        
        FileDefinition fd = this.fileDefinitionService
                .findByRelativePath(catalog, relativePath);
        if (fd == null) {
            return true;
        }
        boolean flag = false;
        if (recycleAble) {
            //可回收资源，仅移动数据至历史表，实际资源不进行删除
            flag = this.fileDefinitionService.moveToHis(fd);
        } else {
            //不可回收，则直接删除数据，以及资源
            FileDefinitionDetail fdd = this.fileCatalogComposite.setup(fd);
            FileResource fr = fdd.getResource();
            //删除数据
            flag = this.fileDefinitionService.deleteById(fd.getId()) > 0;
            //删除资源
            fr.delete();
        }
        return flag;
    }
    
    /**
     * 查询文件详情<br/>
     * <功能详细描述>
     * @param fileId
     * @return [参数说明]
     * 
     * @return FileDefinitionDetail [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public FileDefinitionDetail findById(String fileId) {
        AssertUtils.notEmpty(fileId, "fileId is empty.");
        
        FileDefinition fd = this.fileDefinitionService.findById(fileId);
        if (fd == null) {
            return null;
        }
        FileDefinitionDetail fdd = this.fileCatalogComposite.setup(fd);
        return fdd;
    }
    
    /**
     * 根据目录和相对路径查询文件详情<br/>
     * <功能详细描述>
     * @param catalog
     * @param relativePath
     * @return [参数说明]
     * 
     * @return FileDefinitionDetail [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public FileDefinitionDetail findByRelativePath(String catalog,
            String relativePath) {
        AssertUtils.notEmpty(catalog, "catalog is empty.");
        AssertUtils.notEmpty(relativePath, "relativePath is empty.");
        
        FileDefinition fd = this.fileDefinitionService
                .findByRelativePath(catalog, relativePath);
        if (fd == null) {
            return null;
        }
        FileDefinitionDetail fdd = this.fileCatalogComposite.setup(fd);
        return fdd;
    }
    
    /**
     * 获取文件定义业务层<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return FileDefinitionService [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public FileDefinitionService getFileDefinitionService() {
        return this.fileDefinitionService;
    }
}
