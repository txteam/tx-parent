/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-9-4
 * <修改描述:>
 */
package com.tx.component.auth.dbscript;


 /**
  * 数据源类型<br/>
  * <功能详细描述>
  * 
  * @author  brady
  * @version  [版本号, 2013-9-4]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public enum DataSourceType {
    
    /**
     * h2数据库
     */
    H2("com/tx/component/auth/dbscript/h2/"),
    /**
     * mysql数据库
     */
    MYSQL("com/tx/component/auth/dbscript/mysql/"),
    /**
     * oracle数据库
     */
    ORACLE("com/tx/component/auth/dbscript/oracle/");
    
    private String basePath;
    
    /** <默认构造函数> */
    private DataSourceType(String basePath) {
        this.basePath = basePath;
    }

    /**
     * @return 返回 basePath
     */
    public String getBasePath() {
        return basePath;
    }

    /**
     * @param 对basePath进行赋值
     */
    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }
}
