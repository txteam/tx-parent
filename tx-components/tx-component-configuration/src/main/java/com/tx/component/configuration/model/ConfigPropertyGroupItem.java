/*
* 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-12-20
 * <修改描述:>
 */
package com.tx.component.configuration.model;

import java.util.List;

import com.tx.core.tree.model.TreeAble;


 /**
  * 配置属性组<br/>
  * <功能详细描述>
  * 
  * @author  brady
  * @version  [版本号, 2013-12-20]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public class ConfigPropertyGroupItem implements TreeAble<List<ConfigPropertyGroupItem>, ConfigPropertyGroupItem>{

    /** 配置的id唯一键 */
    private String id;
    
    /** 是否可见 */
    private boolean viewAble;
    
    /** 是否有效 */
    private boolean valid;
    
    /** 配置属性名 */
    private String name;
    
    /** 配置属性组的父级id */
    private String parentId;
    
    /** 配置属性组的子组 */
    private List<ConfigPropertyGroupItem> childs;
    
    /** 所属配置组的配置属性集合 */
    private List<ConfigProperty> configs;

    /**
     * @return 返回 id
     */
    public String getId() {
        return id;
    }

    /**
     * @param 对id进行赋值
     */
    public void setId(String id) {
        this.id = id;
    }

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
     * @return 返回 parentId
     */
    public String getParentId() {
        return parentId;
    }

    /**
     * @param 对parentId进行赋值
     */
    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    /**
     * @return 返回 childs
     */
    public List<ConfigPropertyGroupItem> getChilds() {
        return childs;
    }

    /**
     * @param 对childs进行赋值
     */
    public void setChilds(List<ConfigPropertyGroupItem> childs) {
        this.childs = childs;
    }

    /**
     * @return 返回 viewAble
     */
    public boolean isViewAble() {
        return viewAble;
    }

    /**
     * @param 对viewAble进行赋值
     */
    public void setViewAble(boolean viewAble) {
        this.viewAble = viewAble;
    }

    /**
     * @return 返回 valid
     */
    public boolean isValid() {
        return valid;
    }

    /**
     * @param 对valid进行赋值
     */
    public void setValid(boolean valid) {
        this.valid = valid;
    }

    /**
     * @return 返回 configs
     */
    public List<ConfigProperty> getConfigs() {
        return configs;
    }

    /**
     * @param 对configs进行赋值
     */
    public void setConfigs(List<ConfigProperty> configs) {
        this.configs = configs;
    }
}
