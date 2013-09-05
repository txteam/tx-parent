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
    
    /** 属性界面展示名称 */
    private String name;
    
    /** 对应数据库字段名 */
    private String columnName;
    
    /** 属性项label名 */
    private String propertyName;
    
    /** 属性项在列表中是否隐藏 */
    private boolean hidden;
    
    /** 对应属性是否被忽略 */
    private boolean omit;
    
    /** 是否是排序字段 */
    private boolean visibal;
    
    /** 属性项排序值 */
    private int order;
    
    /** 字段对应的java类型 */
    private Class<?> type;

    /**
     * @return 返回 name
     */
    public String getName() {
        return name;
    }

    /**
     * @param 对name进行赋值
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return 返回 columnName
     */
    public String getColumnName() {
        return columnName;
    }

    /**
     * @param 对columnName进行赋值
     */
    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    /**
     * @return 返回 propertyName
     */
    public String getPropertyName() {
        return propertyName;
    }

    /**
     * @param 对propertyName进行赋值
     */
    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    /**
     * @return 返回 hidden
     */
    public boolean isHidden() {
        return hidden;
    }

    /**
     * @param 对hidden进行赋值
     */
    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    /**
     * @return 返回 omit
     */
    public boolean isOmit() {
        return omit;
    }

    /**
     * @param 对omit进行赋值
     */
    public void setOmit(boolean omit) {
        this.omit = omit;
    }

    /**
     * @return 返回 visibal
     */
    public boolean isVisibal() {
        return visibal;
    }

    /**
     * @param 对visibal进行赋值
     */
    public void setVisibal(boolean visibal) {
        this.visibal = visibal;
    }

    /**
     * @return 返回 order
     */
    public int getOrder() {
        return order;
    }

    /**
     * @param 对order进行赋值
     */
    public void setOrder(int order) {
        this.order = order;
    }

    /**
     * @return 返回 type
     */
    public Class<?> getType() {
        return type;
    }

    /**
     * @param 对type进行赋值
     */
    public void setType(Class<?> type) {
        this.type = type;
    }
}
