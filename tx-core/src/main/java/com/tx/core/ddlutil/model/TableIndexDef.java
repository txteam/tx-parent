package com.tx.core.ddlutil.model;

import com.tx.core.model.OrderedSupport;

/**
  * 表索引接口<br/>
  * <功能详细描述>
  * 
  * @author  Administrator
  * @version  [版本号, 2016年10月24日]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
 */
public interface TableIndexDef extends OrderedSupport {
    
    /**
      * 索引名称<br/>
      * <功能详细描述>
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public abstract String getIndexName();
    
    /**
      * 是否唯一键<br/>
      * <功能详细描述>
      * @return [参数说明]
      * 
      * @return boolean [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public abstract boolean isUnique();
    
    /**
      * 是否主键<br/>
      * <功能详细描述>
      * @return [参数说明]
      * 
      * @return boolean [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public abstract boolean isPrimaryKey();
    
    /**
      * 获取对应的字段名<br/>
      * <功能详细描述>
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public abstract String getColumnName();
    
}