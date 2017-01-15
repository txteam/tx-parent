/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2017年1月14日
 * <修改描述:>
 */
package com.tx.component.file.config;

import com.thoughtworks.xstream.annotations.XStreamAlias;


 /**
  * 文件容器配置<br/>
  * <功能详细描述>
  * 
  * @author  Administrator
  * @version  [版本号, 2017年1月14日]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
@XStreamAlias("file_context_config")
public class FileContextConfig {
    
    /** 文件模块配置 */
    @XStreamAlias("file_module_config")
    private FileModuleConfig fileModuleConfig;

    /**
     * @return 返回 fileModuleConfig
     */
    public FileModuleConfig getFileModuleConfig() {
        return fileModuleConfig;
    }

    /**
     * @param 对fileModuleConfig进行赋值
     */
    public void setFileModuleConfig(FileModuleConfig fileModuleConfig) {
        this.fileModuleConfig = fileModuleConfig;
    }
}
