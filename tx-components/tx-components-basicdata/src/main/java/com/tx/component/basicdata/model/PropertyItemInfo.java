/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-9-2
 * <修改描述:>
 */
package com.tx.component.basicdata.model;


 /**
  * 属性项信息<br/>
  * <功能详细描述>
  * 
  * @author  brady
  * @version  [版本号, 2013-9-2]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public class PropertyItemInfo {
    
    /** 对应数据库字段名 */
    private String column;
    
    /** 属性项label名 */
    private String name;
    
    /** 属性项对应字段 */
    private String filed;
    
    /** 属性项在列表中是否隐藏 */
    private boolean hidden;
    
    /** 对应属性是否被忽略 */
    private boolean omit;
    
    /** 是否是排序字段 */
    private boolean orderColumn;
    
    /** 属性项排序值 */
    private int order;

    
}
