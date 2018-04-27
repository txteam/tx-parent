///*
// * 描          述:  <描述>
// * 修  改   人:  brady
// * 修改时间:  2013-12-18
// * <修改描述:>
// */
//package com.tx.core.dbscript.model;
//
//import java.util.Date;
//
//import javax.persistence.Entity;
//import javax.persistence.Id;
//import javax.persistence.Table;
//
//import com.tx.core.jdbc.sqlsource.annotation.QueryConditionEqual;
//import com.tx.core.jdbc.sqlsource.annotation.UpdateAble;
//
//
// /**
//  * 数据脚本容器<br/>
//  * 
//  * @author  brady
//  * @version  [版本号, 2013-12-18]
//  * @see  [相关类/方法]
//  * @since  [产品/模块版本]
//  */
//@Entity
//@Table(name = "CORE_DBSCRIPT_CONTEXT")
//public class DBScriptContext {
//    
//    /** 唯一键 */
//    @Id
//    private String id;
//    
//    /** 表名 */
//    @QueryConditionEqual()
//    private String tableName;
//    
//    /** 表版本 */
//    @UpdateAble
//    @QueryConditionEqual()
//    private String tableVersion;
//    
//    /** 创建时间 */
//    private Date createDate;
//    
//    /** 最后更新时间 */
//    @UpdateAble
//    private Date lastUpdateDate;
//
//    /**
//     * @return 返回 id
//     */
//    public String getId() {
//        return id;
//    }
//
//    /**
//     * @param 对id进行赋值
//     */
//    public void setId(String id) {
//        this.id = id;
//    }
//
//    /**
//     * @return 返回 tableName
//     */
//    public String getTableName() {
//        return tableName;
//    }
//
//    /**
//     * @param 对tableName进行赋值
//     */
//    public void setTableName(String tableName) {
//        this.tableName = tableName;
//    }
//
//    /**
//     * @return 返回 tableVersion
//     */
//    public String getTableVersion() {
//        return tableVersion;
//    }
//
//    /**
//     * @param 对tableVersion进行赋值
//     */
//    public void setTableVersion(String tableVersion) {
//        this.tableVersion = tableVersion;
//    }
//
//    /**
//     * @return 返回 createDate
//     */
//    public Date getCreateDate() {
//        return createDate;
//    }
//
//    /**
//     * @param 对createDate进行赋值
//     */
//    public void setCreateDate(Date createDate) {
//        this.createDate = createDate;
//    }
//
//    /**
//     * @return 返回 lastUpdateDate
//     */
//    public Date getLastUpdateDate() {
//        return lastUpdateDate;
//    }
//
//    /**
//     * @param 对lastUpdateDate进行赋值
//     */
//    public void setLastUpdateDate(Date lastUpdateDate) {
//        this.lastUpdateDate = lastUpdateDate;
//    }
//}
