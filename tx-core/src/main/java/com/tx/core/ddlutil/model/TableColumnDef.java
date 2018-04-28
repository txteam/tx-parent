package com.tx.core.ddlutil.model;

import com.tx.core.util.order.OrderedSupport;

/**
  * 字段接口<br/>
  * <功能详细描述>
  * 
  * @author  Administrator
  * @version  [版本号, 2016年10月24日]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
 */
public interface TableColumnDef extends OrderedSupport {
    
    /**
      * 返回字段名<br/> 
      * <功能详细描述>
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public abstract String getColumnName();
    
    /**
      * 返回字段定义字符串<br/>
      * <功能详细描述>
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public abstract String getColumnType();
    
    /**
      * 设置字段类型<br/>
      * <功能详细描述>
      * @param columnType [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public abstract void setColumnType(String columnType);
    
    /**
      * 返回对应的jdbcType类型
      * <功能详细描述>
      * @return [参数说明]
      * 
      * @return JdbcTypeEnum [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public abstract JdbcTypeEnum getJdbcType();
    
    /**
      * 获取字段长度
      * <功能详细描述>
      * @return [参数说明]
      * 
      * @return int [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public abstract int getSize();
    
    /**
      * 获取字段精度<br/>
      * <功能详细描述>
      * @return [参数说明]
      * 
      * @return int [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public abstract int getScale();
    
    /**
      * 获取默认值<br/>
      * <功能详细描述>
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public abstract String getDefaultValue();
    
    /**
      * 是否主键<br/>
      * <功能详细描述>
      * @return [参数说明]
      * 
      * @return boolean [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public boolean isPrimaryKey();
    
    /**
      * 是否必填<br/>
      * <功能详细描述>
      * @return [参数说明]
      * 
      * @return boolean [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public boolean isRequired();
    
    /**
      * 设置是否为必填<br/>
      * <功能详细描述>
      * @param required [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public void setRequired(boolean required);
    
    /**
      * 获取表字段注释<br/>
      * <功能详细描述>
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public String getComment();
}