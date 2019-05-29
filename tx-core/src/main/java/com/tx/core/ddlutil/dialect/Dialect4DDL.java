/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2016年10月21日
 * <修改描述:>
 */
package com.tx.core.ddlutil.dialect;

import org.hibernate.dialect.Dialect;

import com.tx.core.ddlutil.model.JdbcTypeEnum;

/**
 * DDL方言类
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2016年10月21日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public abstract class Dialect4DDL {
    
    private Dialect dialect;
    
    /** <默认构造函数> */
    public Dialect4DDL() {
        super();
    }
    
    /**
     * 获取类型对应的JdbcType<br/>
     * <功能详细描述>
     * @param type
     * @return [参数说明]
     * 
     * @return JdbcTypeEnum [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    public abstract JdbcTypeEnum getJdbcType(Class<?> type);
    
    /**
    * 根据类型
    * <功能详细描述>
    * @param type
    * @return [参数说明]
    * 
    * @return int [返回类型说明]
    * @exception throws [异常类型] [异常说明]
    * @see [类、类#方法、类#成员]
    */
    public abstract int getDefaultLengthByType(Class<?> type);
    
    /**
    * 获取默认的Scale值<br/>
    * <功能详细描述>
    * @param type
    * @return [参数说明]
    * 
    * @return int [返回类型说明]
    * @exception throws [异常类型] [异常说明]
    * @see [类、类#方法、类#成员]
    */
    public abstract int getDefaultScaleByType(Class<?> type);
    
    /**
    * 根据类型
    * <功能详细描述>
    * @param type
    * @return [参数说明]
    * 
    * @return int [返回类型说明]
    * @exception throws [异常类型] [异常说明]
    * @see [类、类#方法、类#成员]
    */
    public abstract int getDefaultLengthByName(Class<?> type, String name);
    
    /**
    * 获取默认的Scale值<br/>
    * <功能详细描述>
    * @param type
    * @return [参数说明]
    * 
    * @return int [返回类型说明]
    * @exception throws [异常类型] [异常说明]
    * @see [类、类#方法、类#成员]
    */
    public abstract int getDefaultScaleByName(Class<?> type, String name);
    
    /**
      * 是否生成sql注释<br/>
      * <功能详细描述>
      * @return [参数说明]
      * 
      * @return boolean [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public boolean isSqlCommentsOn() {
        return true;
    }
    
    /**
      * sql注释前置字符串<br/>
      * <功能详细描述>
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public String getCommentPrefix() {
        return "--";
    }
    
    /**
      * sql注释后置字符串<br/>
      * <功能详细描述>
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public String getCommentSuffix() {
        return "";
    }
    
    /**
     * Determines whether delimited identifiers are used or normal SQL92 identifiers
     * (which may only contain alphanumerical characters and the underscore, must start
     * with a letter and cannot be a reserved keyword).
     * Per default, delimited identifiers are not used
     *
     * @return <code>true</code> if delimited identifiers are used
     */
    public boolean isDelimitedIdentifierModeOn() {
        return true;
    }
    
    /**
     * @return 返回 dialect
     */
    public Dialect getDialect() {
        return dialect;
    }
    
    /**
     * @param 对dialect进行赋值
     */
    protected void setDialect(Dialect dialect) {
        this.dialect = dialect;
    }
}
