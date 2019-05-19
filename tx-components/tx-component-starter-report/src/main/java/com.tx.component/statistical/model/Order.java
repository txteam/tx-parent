package com.tx.component.statistical.model;

import com.tx.core.exceptions.argument.ArgIllegalException;
import com.tx.core.exceptions.argument.ArgNullException;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * <br/>
 *
 * @author XRX
 * @version [版本号, 2018/05/09]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class Order implements Serializable {
    private static final long serialVersionUID = 5352365437992477012L;
    private static final Set<String> unSafeKeyword = new HashSet();

    static {
        unSafeKeyword.add("DESC");
        unSafeKeyword.add("DROP");
        unSafeKeyword.add("SELECT");
        unSafeKeyword.add("UPDATE");
        unSafeKeyword.add("DELETE");
        unSafeKeyword.add("DISTINCT");
        unSafeKeyword.add("WHERE");
        unSafeKeyword.add("FROM");
        unSafeKeyword.add("JOIN");
        unSafeKeyword.add("ON");
        unSafeKeyword.add("AND");
        unSafeKeyword.add("OR");
        unSafeKeyword.add("GROUP");
        unSafeKeyword.add("BY");
    }

    private boolean ascending;
    private String columnNames;

    private Order(String columnNames, boolean ascending) {
        if (StringUtils.isBlank(columnNames)) {
            throw new ArgNullException("Order(columnNames,ascending) columnNames is empty.");
        } else if (columnNames.indexOf(";") < 0 && columnNames.indexOf("\'") < 0) {
            String upcaseColumnNamesString = columnNames.toUpperCase();
            String[] columns = upcaseColumnNamesString.split(",");
            StringBuilder sb = new StringBuilder(128);
            String[] var6 = columns;
            int var7 = columns.length;

            for (int var8 = 0; var8 < var7; ++var8) {
                String columnTemp = var6[var8];
                columnTemp = columnTemp.trim();
                if (unSafeKeyword.contains(columnTemp)) {
                    throw new ArgIllegalException("Order(columnNames,ascending) columnNames hase invalid keyword " + columnTemp);
                }

                sb.append(columnTemp).append(",");
            }

            this.columnNames = sb.substring(0, sb.length() - 1);
            this.ascending = ascending;
        } else {
            throw new ArgIllegalException("Order(columnNames,ascending) columnNames hase invalid char ;或\'");
        }
    }

    public static Order asc(String propertyName) {
        return new Order(propertyName, true);
    }

    public static Order desc(String propertyName) {
        return new Order(propertyName, false);
    }

    public String toSqlString() {
        return this.columnNames + (this.ascending ? " ASC" : " DESC");
    }

    public String toString() {
        return this.toSqlString();
    }
}