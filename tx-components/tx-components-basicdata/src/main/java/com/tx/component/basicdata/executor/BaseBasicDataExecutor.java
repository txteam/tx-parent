/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-8-14
 * <修改描述:>
 */
package com.tx.component.basicdata.executor;

import java.util.List;
import java.util.Map;

import com.tx.core.paged.model.PagedList;


 /**
  * 基础基础数据执行器<br/>
  * <功能详细描述>
  * 
  * @author  brady
  * @version  [版本号, 2013-8-14]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public class BaseBasicDataExecutor<T> implements BasicDataExecutor<T>{

    /**
     * @param o
     * @return
     */
    @Override
    public int compareTo(T o) {
        return 0;
    }

    /**
     * @return
     */
    @Override
    public List<T> list() {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @param params
     * @return
     */
    @Override
    public List<T> query(Map<String, Object> params) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @param params
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @Override
    public PagedList<T> queryPagedList(Map<String, Object> params,
            int pageIndex, int pageSize) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @param params
     * @return
     */
    @Override
    public int count(Map<String, Object> params) {
        // TODO Auto-generated method stub
        return 0;
    }

    /**
     * @param instance
     */
    @Override
    public void insert(T instance) {
        // TODO Auto-generated method stub
        
    }

    /**
     * @param params
     * @return
     */
    @Override
    public int delete(Map<String, Object> params) {
        // TODO Auto-generated method stub
        return 0;
    }

    /**
     * @param instance
     * @return
     */
    @Override
    public int update(T instance) {
        // TODO Auto-generated method stub
        return 0;
    }
    
    
}
