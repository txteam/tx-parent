/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2016年8月6日
 * <修改描述:>
 */
package com.tx.component.role.config;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 * 内容信息存放文件<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2016年8月6日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@XStreamAlias("role_config")
public class RoleContextConfig {
    
    /** 内容分类配置 */
    @XStreamImplicit(itemFieldName = "type")
    private List<RoleTypeConfig> types;

    /**
     * @return 返回 types
     */
    public List<RoleTypeConfig> getTypes() {
        return types;
    }

    /**
     * @param 对types进行赋值
     */
    public void setTypes(List<RoleTypeConfig> types) {
        this.types = types;
    }
}
