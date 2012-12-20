/*
 * 描          述:  <描述>
 * 修  改   人:  PengQingyang
 * 修改时间:  2012-12-1
 * <修改描述:>
 */
package com.tx.core.mybatis.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import org.apache.cxf.common.util.StringUtils;

import com.tx.core.TxConstants;
import com.tx.core.exceptions.parameter.ParameterIsEmptyException;
import com.tx.core.exceptions.parameter.ParameterIsInvalidException;

/**
 * <排序支持>
 * <功能详细描述>
 * 
 * @author  PengQingyang
 * @version  [版本号, 2012-12-1]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class Order implements Serializable {
    
    /** 注释内容 */
    private static final long serialVersionUID = 5352365437992477012L;
    
    private final static Set<String> unSafeKeyword = new HashSet<String>();
    
    static {
        unSafeKeyword.add("DESC");
        unSafeKeyword.add("DROP");
        unSafeKeyword.add("SELECT");
        unSafeKeyword.add("UPDATE");
        unSafeKeyword.add("DELETE");
        
        //这里去掉truncate经实验oracle建表时可以用该字段
        //unSafeKeyword.add("TRUNCATE");
        unSafeKeyword.add("DISTINCT");
        unSafeKeyword.add("WHERE");
        unSafeKeyword.add("FROM");
        
        //这里去掉INNER OUTER经实验oracle建表时可以用该字段
        //unSafeKeyword.add("INNER");
        //unSafeKeyword.add("OUTER");
        unSafeKeyword.add("JOIN");
        unSafeKeyword.add("ON");
        //unSafeKeyword.add("LEFT");
        //unSafeKeyword.add("RIGHT");
        
        unSafeKeyword.add("AND");
        unSafeKeyword.add("OR");
        unSafeKeyword.add("GROUP");
        unSafeKeyword.add("BY");
    }
    
    private boolean ascending;
    
    private String columnNames;
    
    /**
     * Constructor for Order.
     */
    private Order(String columnNames, boolean ascending) {
        if (StringUtils.isEmpty(columnNames)) {
            throw new ParameterIsEmptyException(
                    "Order(columnNames,ascending) columnNames is empty.");
        }
        if (columnNames.indexOf(";") >= 0 || columnNames.indexOf("'") >= 0) {
            throw new ParameterIsInvalidException(
                    "Order(columnNames,ascending) columnNames hase invalid char {}",
                    ";或'");
        }
        String upcaseColumnNamesString = columnNames.toUpperCase();
        String columns[] = upcaseColumnNamesString.split(",");
        StringBuilder sb = new StringBuilder(TxConstants.INITIAL_STR_LENGTH);
        for (String columnTemp : columns) {
            columnTemp = columnTemp.trim();
            if (unSafeKeyword.contains(columnTemp)) {
                throw new ParameterIsInvalidException(
                        "Order(columnNames,ascending) columnNames hase invalid keyword {}.",
                        columnTemp);
            }
            sb.append(columnTemp).append(",");
        }
        
        this.columnNames = sb.substring(0, sb.length() - 1);
        this.ascending = ascending;
    }
    
    /**
     * Render the SQL fragment
     *
     */
    public String toSqlString() {
        return this.columnNames + (ascending ? " ASC" : " DESC");
    }
    
    /**
     * @return
     */
    @Override
    public String toString() {
        return toSqlString();
    }

    /**
     * Ascending order
     *
     * @param propertyName
     * @return Order
     */
    public static Order asc(String propertyName) {
        return new Order(propertyName, true);
    }
    
    /**
     * Descending order
     *
     * @param propertyName
     * @return Order
     */
    public static Order desc(String propertyName) {
        return new Order(propertyName, false);
    }
    
}
