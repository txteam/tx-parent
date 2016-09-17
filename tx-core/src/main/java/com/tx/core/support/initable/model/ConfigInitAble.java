/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2016年9月17日
 * <修改描述:>
 */
package com.tx.core.support.initable.model;

/**
 * Xml可初始化<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2016年9月17日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface ConfigInitAble {
    
    /**
      * 获取配置初始化的编码<br/>
      *     用以标定配置中编码的唯一性<br/>
      * <功能详细描述>
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public String getCode();
    
    /**
      * 设置编码<br/>
      * <功能详细描述>
      * @param code [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public void setCode(String code);
    
    /**
      * 是否有效<br/>
      * <功能详细描述>
      * @return [参数说明]
      * 
      * @return boolean [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public boolean isValid();
    
    /**
      * 设置是否有效<br/>
      * <功能详细描述>
      * @param valid [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public void setValid(boolean valid);
    
    /**
      * 是否可编辑<br/>
      * <功能详细描述>
      * @return [参数说明]
      * 
      * @return boolean [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public boolean isModifyAble();
    
    /**
      * 设置是否可编辑<br/>
      * <功能详细描述>
      * @param modifyAble [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public void setModifyAble(boolean modifyAble);
    
}
