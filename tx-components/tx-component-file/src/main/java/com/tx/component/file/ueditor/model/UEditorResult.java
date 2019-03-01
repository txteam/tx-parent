package com.tx.component.file.ueditor.model;

/**
  * 处理状态接口<br/>
  * <功能详细描述>
  * 
  * @author  Administrator
  * @version  [版本号, 2017年3月8日]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
 */
public interface UEditorResult {
	
    /**
      * 是否成功<br/>
      * <功能详细描述>
      * @return [参数说明]
      * 
      * @return boolean [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
	public boolean isSuccess ();
	
	/**
	  * 写入信息<br/>
	  * <功能详细描述>
	  * @param name
	  * @param val [参数说明]
	  * 
	  * @return void [返回类型说明]
	  * @exception throws [异常类型] [异常说明]
	  * @see [类、类#方法、类#成员]
	 */
	public void putInfo( String name, String val );
	
	/**
     * 写入信息<br/>
     * <功能详细描述>
     * @param name
     * @param val [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
	public void putInfo ( String name, long val );
	
	/**
	  * 生成Json字符串<br/>
	  * <功能详细描述>
	  * @return [参数说明]
	  * 
	  * @return String [返回类型说明]
	  * @exception throws [异常类型] [异常说明]
	  * @see [类、类#方法、类#成员]
	 */
	public String toJSONString ();
}
