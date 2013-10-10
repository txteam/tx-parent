/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-8-14
 * <修改描述:>
 */
package com.tx.component.basicdata.executor;

import java.util.List;
import java.util.Map;

import org.springframework.util.MultiValueMap;

import com.tx.core.paged.model.PagedList;

/**
 * 基础数据类型<br/>
 *     该类中由于默认仅提供了根据主键对对象的操作<br/>
 *     所以delete,update直接返回true or false<br/>
 * 
 * @author  brady
 * @version  [版本号, 2013-8-14]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface BasicDataExecutor<T> {
    
    /**
      * 执行操作<br/>
      *     用以透传入插件中<br/>
      *<功能详细描述>
      * @param processName [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public Object execute(String process, Object... args);
    
    /**
      * 根据list获取结果集后，更具getterName得到一个MultiValueMap
      *<功能详细描述>
      * @param getterName
      * @return [参数说明]
      * 
      * @return MultiValueMap [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public MultiValueMap<Object, T> getMultiValueMap(String getterName);
    
    /**
      * 根据条件查询指定对象
      *<功能详细描述>
      * @param findCondition
      * @return [参数说明]
      * 
      * @return T [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public T find(String pk);
    
    /**
      * 获取对应的所有基础数据<br/>
      *<功能详细描述>
      * @return [参数说明]
      * 
      * @return List<T> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public List<T> list();
    
    /**
      * 查询对应基础数据的列表<br/>
      *<功能详细描述>
      * @param params
      * @return [参数说明]
      * 
      * @return List<T> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public List<T> query(Map<String, Object> params);
    
    /**
      * 分页查询基础数据列表<br/>
      *<功能详细描述>
      * @param params
      * @param pageIndex
      * @param pageSize
      * @return [参数说明]
      * 
      * @return PagedList<T> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public PagedList<T> queryPagedList(Map<String, Object> params,
            int pageIndex, int pageSize);
    
    /**
      * 查询基础数据数目<br/>
      *<功能详细描述>
      * @param params
      * @return [参数说明]
      * 
      * @return int [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public int count(Map<String, Object> params);
    
    /**
      * 插入基础数据实例<br/>
      *<功能详细描述>
      * @param instance [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public void insert(T instance);
    
    /**
      * 删除对象<br/>
      *<功能详细描述>
      * @param params
      * @return [参数说明]
      * 
      * @return int [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public boolean delete(String pk);
    
    /**
      * 更新基础数据实例<br/>
      *<功能详细描述>
      * @param instance
      * @return [参数说明]
      * 
      * @return int [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public boolean update(Map<String, Object> params);
}
