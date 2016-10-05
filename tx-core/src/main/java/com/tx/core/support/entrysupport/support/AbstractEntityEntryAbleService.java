/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2016年10月5日
 * <修改描述:>
 */
package com.tx.core.support.entrysupport.support;

import com.tx.core.support.entrysupport.model.EntityEntry;
import com.tx.core.support.entrysupport.model.EntryAble;

/**
 * 抽象分项实体的业务层逻辑<br/>
 *     适用于EntityEntry可以利用EntityEntry对象简单描述的情形<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2016年10月5日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public abstract class AbstractEntityEntryAbleService<ENTITY extends EntryAble<EntityEntry>>
        extends AbstractEntryAbleService<EntityEntry, ENTITY> {
    
}
