/*
 * 描          述:  <描述>
 * 修  改   人:  PengQingyang
 * 修改时间:  2012-10-16
 * <修改描述:>
 */
package com.tx.component.config.setting;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * <配置资源设置>
 * <充血模型对象，将此类资源所有属性都可以映射到该类中，可匹配配置中各种类型资源>
 * <增加其他类型后，在该类中增加相应字段即可>
 * 
 * @author  PengQingyang
 * @version  [版本号, 2012-10-16]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@XStreamAlias("configResource")
public class ConfigResource {
    
    /** 配置资源唯一ID */
    private String id;
    
    /** 
      * 配置资源类型 ：支持database...待扩展 
      * 由于设计该类型,可由用户自定义扩展,所以该处使用String而非直接使用枚举型<br/>
      */
    private String type = ConfigResourceTypeEnum.DEFAULT.getName();
    
    /** 配置资源对应jndiName */
    private String jndiName;
    
    /** 查询sql */
    @XStreamAlias("query")
    private String querySql;
    
    /** 增加sql */
    @XStreamAlias("insert")
    private String insertSql;
    
    /** 更新sql */
    @XStreamAlias("update")
    private String updateSql;
    
    @XStreamAlias("delete")
    private String deleteSql;
    
    /** 是否需要重新加载 */
    private boolean isReload;
    
    /** 如果需要重新加载，需指定 重新加载表达式 */
    private String cronExpression;
    
    /** 配置文件路径  */
    @XStreamAlias("locations")
    private List<String> configLocations;
    
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
     * @return 返回 type
     */
    public String getType() {
        return type;
    }
    
    /**
     * @param 对type进行赋值
     */
    public void setType(String type) {
        this.type = type;
    }
    
    /**
     * @return 返回 jndiName
     */
    public String getJndiName() {
        return jndiName;
    }
    
    /**
     * @param 对jndiName进行赋值
     */
    public void setJndiName(String jndiName) {
        this.jndiName = jndiName;
    }
    
    /**
     * @return 返回 querySql
     */
    public String getQuerySql() {
        return querySql;
    }
    
    /**
     * @param 对querySql进行赋值
     */
    public void setQuerySql(String querySql) {
        this.querySql = querySql;
    }
    
    /**
     * @return 返回 insertSql
     */
    public String getInsertSql() {
        return insertSql;
    }
    
    /**
     * @param 对insertSql进行赋值
     */
    public void setInsertSql(String insertSql) {
        this.insertSql = insertSql;
    }
    
    /**
     * @return 返回 updateSql
     */
    public String getUpdateSql() {
        return updateSql;
    }
    
    /**
     * @param 对updateSql进行赋值
     */
    public void setUpdateSql(String updateSql) {
        this.updateSql = updateSql;
    }
    
    /**
     * @return 返回 isReload
     */
    public boolean isReload() {
        return isReload;
    }
    
    /**
     * @param 对isReload进行赋值
     */
    public void setReload(boolean isReload) {
        this.isReload = isReload;
    }
    
    /**
     * @return 返回 cronExpression
     */
    public String getCronExpression() {
        return cronExpression;
    }
    
    /**
     * @param 对cronExpression进行赋值
     */
    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }
    
    public List<String> getConfigLocations() {
        return configLocations;
    }
    
    /**
     * 将参数按逗号分隔转为路径集合
     * @param configLocations
     */
    public void setConfigLocations(String configLocations) {
        List<String> list = Arrays.asList(StringUtils.split(configLocations,
                ","));
        this.configLocations = list;
    }
    
    public String getDeleteSql() {
        return deleteSql;
    }
    
    public void setDeleteSql(String deleteSql) {
        this.deleteSql = deleteSql;
    }
    
    /**
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        } else if (this == obj) {
            return true;
        } else if (obj instanceof ConfigResource) {
            ConfigResource other = (ConfigResource) obj;
            if (ObjectUtils.equals(this.getId(), other.getId())) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
    
    /**
     * @return
     */
    @Override
    public int hashCode() {
        if (this.id != null) {
            return ConfigResource.class.hashCode() + this.id.hashCode();
        } else {
            return super.hashCode();
        }
    }
    
}
