/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2018年5月1日
 * <修改描述:>
 */
package com.tx.core.ddlutil.model;


 /**
  * <功能简述>
  * <功能详细描述>
  * 
  * @author  Administrator
  * @version  [版本号, 2018年5月1日]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public class AbstractIndexDef implements TableIndexDef{
    
    /** 索引名称. */
    private String indexName;
    
    /** 索引所在字段名 */
    private String columnNames;
    
    /** 是否唯一键 */
    private boolean uniqueKey = false;

    /**
     * @return 返回 indexName
     */
    public String getIndexName() {
        return indexName;
    }

    /**
     * @param 对indexName进行赋值
     */
    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }

    /**
     * @return 返回 columnNames
     */
    public String getColumnNames() {
        return columnNames;
    }

    /**
     * @param 对columnNames进行赋值
     */
    public void setColumnNames(String columnNames) {
        this.columnNames = columnNames;
    }

    /**
     * @return 返回 uniqueKey
     */
    public boolean isUniqueKey() {
        return uniqueKey;
    }

    /**
     * @param 对uniqueKey进行赋值
     */
    public void setUniqueKey(boolean uniqueKey) {
        this.uniqueKey = uniqueKey;
    }
}
