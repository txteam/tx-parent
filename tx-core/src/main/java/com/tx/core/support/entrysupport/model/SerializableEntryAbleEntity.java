/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2016年9月16日
 * <修改描述:>
 */
package com.tx.core.support.entrysupport.model;

import java.io.Serializable;

/**
 * 序列支撑分项属性的属性<br/>
 *     基本属性为Supper类的基本属性<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2016年9月16日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface SerializableEntryAbleEntity<SUPER> extends
        EntryAbleEntity<SerializableEntryAbleFieldEntity>, Serializable {
    
}
