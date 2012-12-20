/*
 * 描          述:  <描述>
 * 修  改   人:  PengQingyang
 * 修改时间:  2012-10-16
 * <修改描述:>
 */
package com.tx.component.config.setting;

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
public class ConfigResourceSetting {
    
    public static final String TYPE_DATABASE = "database";
    
    /** 配置资源唯一ID */
    private String id;
    
    /** 配置资源类型 ：支持database...待扩展 */
    private String type = TYPE_DATABASE;
    
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
    
    /** 是否需要重新加载 */
    private boolean isReload;
    
    /** 如果需要重新加载，需指定 重新加载表达式 */
    private String cronExpression;
    
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
}
