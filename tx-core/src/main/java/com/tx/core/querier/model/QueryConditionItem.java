/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2019年6月3日
 * <修改描述:>
 */
package com.tx.core.querier.model;

import java.util.Collection;

import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.util.JPAParseUtils.JPAColumnInfo;

/**
 * 查询条件<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2019年6月3日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class QueryConditionItem {
    
    /** 搜索属性 */
    private final String column;
    
    /** 运算符 */
    private final String operator;
    
    /** 值 */
    private final Object value;
    
    /** 是否foreach的逻辑 */
    private final boolean foreach;
    
    /** 是否无需拼接value值 */
    private final boolean withoutValue;
    
    /** <默认构造函数> */
    public QueryConditionItem(JPAColumnInfo column, OperatorEnum operator,
            Object value) {
        super();
        AssertUtils.notNull(column, "column is null.");
        AssertUtils.notEmpty(column.getColumnName(),
                "column.columnName is empty.");
        AssertUtils.notNull(operator, "operator is empty.");
        
        this.column = column.getColumnName();
        this.operator = operator == null ? OperatorEnum.eq.getOperator()
                : operator.getOperator();
        this.value = value;
        switch (operator) {
            case isNull:
            case isNotNull:
                this.withoutValue = true;
                this.foreach = false;
                break;
            case in:
            case notIn:
                AssertUtils.notNull(value, "value is null.");
                AssertUtils.isTrue(
                        Collection.class.isInstance(value)
                                || value.getClass().isArray(),
                        "value should is collection or array.");
                this.withoutValue = false;
                this.foreach = true;
                break;
            default:
                this.withoutValue = false;
                this.foreach = false;
                break;
        }
    }
    
    /**
     * @return 返回 foreach
     */
    public boolean isForeach() {
        return foreach;
    }
    
    /**
     * @return 返回 withoutValue
     */
    public boolean isWithoutValue() {
        return withoutValue;
    }
    
    /**
     * @return 返回 column
     */
    public String getColumn() {
        return column;
    }
    
    /**
     * @return 返回 operator
     */
    public String getOperator() {
        return operator;
    }
    
    /**
     * @return 返回 value
     */
    public Object getValue() {
        return value;
    }
    
    //    public static void main(String[] args) {
    //        List<String> testList = new ArrayList<>();
    //        Set<String> testSet = new HashSet<>();
    //        String[] testArray = new String[] { "test1", "test2" };
    //        System.out.println(Iterable.class.isInstance(testList));
    //        System.out.println(Iterable.class.isInstance(testSet));
    //        System.out.println(Iterable.class.isInstance(testArray));
    //        
    //        System.out.println(Collection.class.isInstance(testSet));
    //        System.out.println(testArray.getClass().isArray());
    //    }
}
