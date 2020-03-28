/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2020年3月18日
 * <修改描述:>
 */
package com.tx.component.file.resource;

/**
 * 文件资源过滤器<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2020年3月18日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@FunctionalInterface
public interface FileResourceFilter {
    
    /**
     * 判断文件资源是否进行过滤<br/>
     * <功能详细描述>
     * @param fileResource
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    boolean accept(FileResource fileResource);
}
