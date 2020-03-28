/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2014年11月24日
 * <修改描述:>
 */
package com.tx.component.file.resource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
public interface FolderResource {
    
    /**
     * 获取文件存储相对路径<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    String getRelativePath();
    
    /**
     * 创建文件夹<br/>
     * <功能详细描述>
     * @param force 如果为true则迭代删除其下的所有文件，再删除对应的文件夹；如果为false则如果下存在文件，则抛出异常
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public void mkdirs();
    
    /**
     * 删除文件夹<br/>
     * <功能详细描述>
     * @param force 如果为true则迭代删除其下的所有文件，再删除对应的文件夹；如果为false则如果下存在文件，则抛出异常
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public void delete();
    
    /**
     * 遍历文件资源<br/>
     * <功能详细描述>
     * @param filter
     * @return [参数说明]
     * 
     * @return List<FileResource> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public List<FileResource> list();
    
    /**
     * 遍历文件资源<br/>
     * <功能详细描述>
     * @param filter
     * @return [参数说明]
     * 
     * @return List<FileResource> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    default List<FileResource> list(FileResourceFilter filter) {
        List<FileResource> list = list();
        if (list == null) {
            return new ArrayList<FileResource>();
        }
        
        List<FileResource> resList = list;
        if (filter != null) {
            resList = list.stream().filter(fr -> filter.accept(fr)).collect(
                    Collectors.toList());
        }
        return resList;
    }
    
    /**
     * 根据相对路径创建关联文件<br/>
     * <功能详细描述>
     * @param relativePath
     * @return [参数说明]
     * 
     * @return FileResource [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public FileResource createRelative(String relativePath);
}
