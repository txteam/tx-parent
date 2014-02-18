/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-12-18
 * <修改描述:>
 */
package com.tx.core.dbscript.config;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;


 /**
  * 更新表脚本配置
  * <功能详细描述>
  * 
  * @author  brady
  * @version  [版本号, 2013-12-18]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
@XStreamAlias("updateTableScript")
@XStreamConverter(UpdateTableScriptConfigConverter.class)
public class UpdateTableScriptConfig {
    
    /** 原版本号 */
    private String sourceVersion;
    
    /** 目标版本号 */
    private String targetVersion;
    
    /** 脚本 */
    private String script;

    /**
     * @return 返回 sourceVersion
     */
    public String getSourceVersion() {
        return sourceVersion;
    }

    /**
     * @param 对sourceVersion进行赋值
     */
    public void setSourceVersion(String sourceVersion) {
        this.sourceVersion = sourceVersion;
    }

    /**
     * @return 返回 targetVersion
     */
    public String getTargetVersion() {
        return targetVersion;
    }

    /**
     * @param 对targetVersion进行赋值
     */
    public void setTargetVersion(String targetVersion) {
        this.targetVersion = targetVersion;
    }

    /**
     * @return 返回 script
     */
    public String getScript() {
        return script;
    }

    /**
     * @param 对script进行赋值
     */
    public void setScript(String script) {
        this.script = script;
    }
}
