/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-9-6
 * <修改描述:>
 */
package com.tx.core.jdbc;

import com.tx.core.jdbc.sqlsource.annotation.UpdateAble;


 /**
  * <功能简述>
  * <功能详细描述>
  * 
  * @author  brady
  * @version  [版本号, 2013-9-6]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public class TestAParent {
    
    @UpdateAble
    private String aParent;

    /**
     * @return 返回 aParent
     */
    public String getaParent() {
        return aParent;
    }

    /**
     * @param 对aParent进行赋值
     */
    public void setaParent(String aParent) {
        this.aParent = aParent;
    }
    
    
}
