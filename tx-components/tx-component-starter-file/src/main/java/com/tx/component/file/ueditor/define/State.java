package com.tx.component.file.ueditor.define;

/**
 * 处理结果接口定义<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2020年3月13日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface State {
    
    /**
     * 是否成功<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public boolean isSuccess();
    
    /**
     * 写入String类型数据<br/>
     * <功能详细描述>
     * @param name
     * @param val [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public void putInfo(String name, String val);
    
    /**
     * 写入long类型参数<br/>
     * <功能详细描述>
     * @param name
     * @param val [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public void putInfo(String name, long val);
    
    /**
     * 转换为JSON字符串<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public String toJSONString();
    
}
