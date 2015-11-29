/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2014年11月24日
 * <修改描述:>
 */
package com.tx.component.file.context;

import org.springframework.core.io.WritableResource;

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
public interface FileDefinitionResource extends WritableResource {
    
}
