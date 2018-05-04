/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2016年10月20日
 * <修改描述:>
 */
package com.tx.core.ddlutil.model;

import java.io.Serializable;

import com.tx.core.util.ObjectUtils;

/**
 * 表字段<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2016年10月20日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class JPAEntityIndexDef extends AbstractIndexDef
        implements Serializable {
    
    /** 注释内容 */
    private static final long serialVersionUID = -6431639195048067292L;
    
    /** <默认构造函数> */
    public JPAEntityIndexDef() {
        super();
    }
    
    /** <默认构造函数> */
    public JPAEntityIndexDef(String indexName, String columnNames,
            boolean uniqueKey) {
        super();
        setIndexName(indexName);
        setColumnNames(columnNames);
        setUniqueKey(uniqueKey);
    }
    
    /**
     * @return
     */
    @Override
    public int hashCode() {
        int hashCode = ObjectUtils.generateHashCode(super.hashCode(),
                this,
                "indexName",
                "tableName");
        return hashCode;
    }
    
    /**
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        boolean flag = ObjectUtils.equals(this, obj, "indexName", "tableName");
        return flag;
    }
    
}
