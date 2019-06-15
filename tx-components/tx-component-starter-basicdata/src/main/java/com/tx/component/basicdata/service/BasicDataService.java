/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2016年10月3日
 * <修改描述:>
 */
package com.tx.component.basicdata.service;

import java.util.List;

import com.tx.component.basicdata.model.BasicData;
import com.tx.component.basicdata.util.BasicDataUtils;
import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.paged.model.PagedList;
import com.tx.core.querier.model.Querier;

/**
 * 基础数据业务层<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2016年10月3日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface BasicDataService<T extends BasicData> {
    
    /**
     * 基础数据类型<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return Class<T> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public Class<T> getRawType();
    
    /**
     * 对应基础数据编码<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public default String type() {
        Class<T> rawType = getRawType();
        AssertUtils.notNull(rawType, "rawType is null.");
        
        String code = BasicDataUtils.getType(rawType);
        return code;
    }
    
    /**
     * 获取对应的表名<br/>
     *     用户写入基础数据类型中
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public default String tableName() {
        Class<T> rawType = getRawType();
        AssertUtils.notNull(rawType, "rawType is null.");
        
        String tableName = BasicDataUtils.getTableName(rawType);
        return tableName;
    }
    
    /**
     * 插入基础数据对象
     * <功能详细描述>
     * @param data [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public void insert(T data);
    
    /**
     * 批量插入基础数据
     * <功能详细描述>
     * @param dataList [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public void batchInsert(List<T> dataList);
    
    /**
     * 根据id进行删除
     * <功能详细描述>
     * @param id
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public boolean deleteById(String id);
    
    /**
     * 根据code进行删除
     * <功能详细描述>
     * @param basicDataTypeCode
     * @param code
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public boolean deleteByCode(String code);
    
    /**
     * 根据id更新基础数据对象<br/>
     * <功能详细描述>
     * @param data
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public boolean updateById(T data);
    
    /**
     * 根据id更新基础数据对象<br/>
     * <功能详细描述>
     * @param data
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public boolean updateByCode(T data);
    
    /**
     * 批量更新基础数据<br/>
     * <功能详细描述>
     * @param dataList [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public void batchUpdate(List<T> dataList);
    
    /**
     * 根据id禁用DataDict<br/>
     * <功能详细描述>
     * @param id
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public boolean disableById(String id);
    
    /**
     * 根据id启用DataDict<br/>
     * <功能详细描述>
     * @param postId
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public boolean enableById(String id);
    
    /**
     * 判断基础数据是否存在<br/>
     * <功能详细描述>
     * @param querier
     * @param excludeId
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public boolean exists(Querier querier, String excludeId);
    
    /**
     * 根据id查询基础数据实例<br/>
     * <功能详细描述>
     * @param id
     * @return [参数说明]
     * 
     * @return T [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public T findById(String id);
    
    /**
     * 根据code查询基础数据实例<br/>
     * <功能详细描述>
     * @param code
     * @return [参数说明]
     * 
     * @return T [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public T findByCode(String code);
    
    /**
     * 根据条件查询基础数据列表<br/>
     * <功能详细描述>
     * @param valid
     * @param querier
     * @return [参数说明]
     * 
     * @return List<T> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public List<T> queryList(Boolean valid, Querier querier);
    
    /**
     * 根据条件查询基础数据分页列表<br/>
     * <功能详细描述>
     * @param valid
     * @param querier
     * @param pageIndex
     * @param pageSize
     * @return [参数说明]
     * 
     * @return PagedList<T> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public PagedList<T> queryPagedList(Boolean valid, Querier querier,
            int pageIndex, int pageSize);
    
}
