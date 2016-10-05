/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2016年8月7日
 * <修改描述:>
 */
package com.tx.core.support.entrysupport.model;

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
public interface EntryAble<EE extends EntityEntry> {
    
    /**
      * 获取Entity的唯一键<br/>
      * <功能详细描述>
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public String getId();
    
    /**
     * @return 返回 entityEntry
     */
    public List<EE> getEntryList();
    
    /**
     * @param 对entityEntry进行赋值
     */
    public void setEntryList(List<EE> entityEntry);
}
