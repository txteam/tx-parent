/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2016年8月7日
 * <修改描述:>
 */
package com.tx.core.entityentry.model;

import java.util.List;


 /**
  * 实体分项属性支撑类<br/>
  * <功能详细描述>
  * 
  * @author  Administrator
  * @version  [版本号, 2016年8月7日]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public abstract class EntryEntityAble {
    
    /** 实体分项属性 */
    private List<EntityEntry> entityEntry;

    /**
     * @return 返回 entityEntry
     */
    public List<EntityEntry> getEntityEntry() {
        return entityEntry;
    }

    /**
     * @param 对entityEntry进行赋值
     */
    public void setEntityEntry(List<EntityEntry> entityEntry) {
        this.entityEntry = entityEntry;
    }
}
