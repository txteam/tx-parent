/**
 * 文 件 名:  ConfigProperty.java
 * 版    权:  TX Workgroup . Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  PengQingyang
 * 修改时间:  2012-10-5
 * <修改描述:>
 */
package com.tx.component.config.model;

/**
 * <配置属性>
 * <功能详细描述>
 * 
 * @author  PengQingyang
 * @version  [版本号, 2012-10-5]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface ConfigProperty1 {

    public static String STATUS_VALID = "0";
    
    public static String STATUS_INVALID = "1";
    
    /**
     * @return 返回 name 配置名
     */
    public String getName();
    
    public void setName(String name);

    /**
     * @return 返回 key 关键字
     */
    public String getKey();
    public void setKey(String key);
    /**
     * @return 返回 value 配置属值
     */
    public String getValue();
    public void setValue(String value);
    
    /**
     * @return 返回 description 配置描述信息
     */
    public String getDescription();
    public void setDescription(String description);
    
    /**
      *<获取配置属性状态>
      *<功能详细描述>
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public String getStatus();
    public void setStatus(String status);
    
	public boolean isDevValue();
	public void setDevValue(boolean isDevValue);
}
