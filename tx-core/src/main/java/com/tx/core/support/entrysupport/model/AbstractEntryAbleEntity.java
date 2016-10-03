/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2016年8月7日
 * <修改描述:>
 */
package com.tx.core.support.entrysupport.model;

import java.io.Serializable;
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
public abstract class AbstractEntryAbleEntity<EE extends EntityEntry>
        implements EntryAbleEntity<EE>, Serializable {
    
    /** 注释内容 */
    private static final long serialVersionUID = -2430827302865996612L;
    
    /** 实体分项属性 */
    private List<EE> entryList;
    
    /**
     * @return 返回 entryList
     */
    public List<EE> getEntryList() {
        return entryList;
    }
    
    /**
     * @param 对entryList进行赋值
     */
    public void setEntryList(List<EE> entryList) {
        this.entryList = entryList;
    }
}
