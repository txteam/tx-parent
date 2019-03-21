/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2017年1月14日
 * <修改描述:>
 */
package com.tx.component.file.config;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;


 /**
  * 文件模块配置<br/>
  * <功能详细描述>
  * 
  * @author  Administrator
  * @version  [版本号, 2017年1月14日]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
@XStreamAlias("file_module_config")
public class FileModuleConfig {
    
    /** 文件模块配置 */
    @XStreamImplicit(itemFieldName = "file_module")
    private List<FileModuleCfg> fileModuleCfg;

    /**
     * @return 返回 fileModuleCfg
     */
    public List<FileModuleCfg> getFileModuleCfg() {
        return fileModuleCfg;
    }

    /**
     * @param 对fileModuleCfg进行赋值
     */
    public void setFileModuleCfg(List<FileModuleCfg> fileModuleCfg) {
        this.fileModuleCfg = fileModuleCfg;
    }
}
