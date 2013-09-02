/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-9-2
 * <修改描述:>
 */
package com.tx.component.basicdata.context;

import java.util.List;

import com.tx.component.basicdata.executor.BasicDataExecutor;
import com.tx.component.basicdata.model.PropertyItemInfo;
import com.tx.component.basicdata.model.QueryCondition;


 /**
  *  基础数据定义<br/>
  * <功能详细描述>
  * 
  * @author  brady
  * @version  [版本号, 2013-9-2]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public class BasicData<T>{
    
    /** 基础数据属性项列表(根据order排序) */
    private List<PropertyItemInfo> propertyItemInfoList;
    
    /** 基础数据界面展示时，查询条件集合 */
    private List<QueryCondition> queryConditionList;
    
    /** 基础数据执行器 */
    private BasicDataExecutor<T> executor;

    /**
     * @return 返回 propertyItemInfoList
     */
    public List<PropertyItemInfo> getPropertyItemInfoList() {
        return propertyItemInfoList;
    }

    /**
     * @param 对propertyItemInfoList进行赋值
     */
    public void setPropertyItemInfoList(List<PropertyItemInfo> propertyItemInfoList) {
        this.propertyItemInfoList = propertyItemInfoList;
    }

    /**
     * @return 返回 queryConditionList
     */
    public List<QueryCondition> getQueryConditionList() {
        return queryConditionList;
    }

    /**
     * @param 对queryConditionList进行赋值
     */
    public void setQueryConditionList(List<QueryCondition> queryConditionList) {
        this.queryConditionList = queryConditionList;
    }

    /**
     * @return 返回 executor
     */
    public BasicDataExecutor<T> getExecutor() {
        return executor;
    }

    /**
     * @param 对executor进行赋值
     */
    public void setExecutor(BasicDataExecutor<T> executor) {
        this.executor = executor;
    }
}
