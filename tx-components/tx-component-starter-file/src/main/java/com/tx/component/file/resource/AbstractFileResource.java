/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2020年3月20日
 * <修改描述:>
 */
package com.tx.component.file.resource;

/**
 * 对文件资源的描述<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2020年3月20日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public abstract class AbstractFileResource implements FileResource {
    
    /** 文件目录 */
    private String catalog;
    
    /**
     * @return 返回 catalog
     */
    public String getCatalog() {
        return catalog;
    }
    
    /**
     * @param 对catalog进行赋值
     */
    public void setCatalog(String catalog) {
        this.catalog = catalog;
    }
}
