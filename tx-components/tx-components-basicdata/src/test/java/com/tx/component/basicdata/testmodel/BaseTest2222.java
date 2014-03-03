/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-10-10
 * <修改描述:>
 */
package com.tx.component.basicdata.testmodel;

import javax.persistence.Table;

import com.tx.component.basicdata.model.BaseTestBasicModel;
import com.tx.core.jdbc.sqlsource.annotation.QueryCondition;


 /**
  * 基础数据类型
  * <功能详细描述>
  * 
  * @author  brady
  * @version  [版本号, 2013-10-10]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
@QueryCondition(condition="TYPE_ = '类型二'")
@Table(name = "test_basicdata")
public class BaseTest2222 extends BaseTestBasicModel{

    /** 注释内容 */
    private static final long serialVersionUID = 1664437148843230184L;

    /** <默认构造函数> */
    public BaseTest2222() {
        setType(BasicTypeEnum.类型二);
    }
    
}
