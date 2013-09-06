/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-9-6
 * <修改描述:>
 */
package com.tx.core.jdbc;


 /**
  * 测试:child
  * <功能详细描述>
  * 
  * @author  brady
  * @version  [版本号, 2013-9-6]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public class TestAChild extends TestA {

    /** 注释内容 */
    private static final long serialVersionUID = 5211953377335246934L;
    
    private String childPrivate;

    /**
     * @return 返回 childPrivate
     */
    public String getChildPrivate() {
        return childPrivate;
    }

    /**
     * @param 对childPrivate进行赋值
     */
    public void setChildPrivate(String childPrivate) {
        this.childPrivate = childPrivate;
    }
}
