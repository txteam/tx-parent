/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2016年10月3日
 * <修改描述:>
 */
package com.tx.component.basicdata.model;

import java.io.Serializable;
import java.util.List;

import com.tx.core.support.initable.model.ConfigInitAble;
import com.tx.core.tree.model.TreeAble;

/**
 * 基础数据接口实现<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2016年10月3日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface TreeAbleBasicData<T extends TreeAbleBasicData<T>> extends
        BasicData, TreeAble<List<T>, T>, ConfigInitAble, Serializable {
    
    /**
      * 获取父节点对象<br/>
      * <功能详细描述>
      * @return [参数说明]
      * 
      * @return T [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public T getParent();
    
    /**
      * 父级节点<br/>
      * <功能详细描述>
      * @param parent [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
      */
    public void setParent(T parent);
}
