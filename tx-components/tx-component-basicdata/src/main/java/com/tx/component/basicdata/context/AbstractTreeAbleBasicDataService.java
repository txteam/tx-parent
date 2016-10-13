/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2016年10月3日
 * <修改描述:>
 */
package com.tx.component.basicdata.context;

import com.tx.component.basicdata.model.TreeAbleBasicData;

/**
 * 基础数据业务层<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2016年10月3日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public abstract class AbstractTreeAbleBasicDataService<T extends TreeAbleBasicData<T>>
        extends AbstractBasicDataService<T> implements
        TreeAbleBasicDataService<T> {
    
}
