/*
 * 描          述:  <描述>
 * 修  改   人:  
 * 修改时间:  
 * <修改描述:>
 */
package com.tx.component.basicdata.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.ListUtils;
import org.apache.commons.collections4.Predicate;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.tx.component.basicdata.model.BasicData;
import com.tx.component.basicdata.model.BasicDataType;
import com.tx.core.exceptions.util.AssertUtils;

/**
 * BasicDataType的业务层
 * <功能详细描述>
 * 
 * @author  
 * @version  [版本号, ]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class BasicDataTypeService {
    
    @SuppressWarnings("unused")
    private Logger logger = LoggerFactory.getLogger(BasicDataTypeService.class);
    
    /** 类型到类型的映射 */
    private Map<Class<?>, BasicDataType> type2typeMap = new HashMap<>();
    
    /** 编码到类型的映射 */
    private Map<String, BasicDataType> code2typeMap = new HashMap<>();
    
    //private BasicDataTypeDao basicDataTypeDao;
    //
    //  /** <默认构造函数> */
    //  public BasicDataTypeService(BasicDataTypeDao basicDataTypeDao) {
    //      super();
    //      this.basicDataTypeDao = basicDataTypeDao;
    //  }
    
    /** <默认构造函数> */
    public BasicDataTypeService() {
        super();
    }
    
    /**
     * 将basicDataType实例插入数据库中保存
     * 1、如果basicDataType 为空时抛出参数为空异常
     * 2、如果basicDataType 中部分必要参数为非法值时抛出参数不合法异常
     * 
     * @param basicDataType [参数说明]
     * @return void [返回类型说明]
     * @exception throws
     * @see [类、类#方法、类#成员]
     */
    @Transactional
    public void insert(BasicDataType basicDataType) {
        //验证参数是否合法
        AssertUtils.notNull(basicDataType, "basicDataType is null.");
        
        AssertUtils.notNull(basicDataType.getType(),
                "basicDataType.type is null.");
        AssertUtils.notEmpty(basicDataType.getCode(),
                "basicDataType.code is Empty.");
        AssertUtils.notEmpty(basicDataType.getName(),
                "basicDataType.name is Empty.");
        AssertUtils.notEmpty(basicDataType.getTableName(),
                "basicDataType.tableName is Empty.");
        
        //为添加的数据需要填入默认值的字段填入默认值
        basicDataType.setValid(true);
        //basicDataType.setModifyAble(true);
        Date now = new Date();
        basicDataType.setCreateDate(now);
        basicDataType.setLastUpdateDate(now);
        
        this.type2typeMap.put(basicDataType.getType(), basicDataType);
        this.code2typeMap.put(basicDataType.getCode(), basicDataType);
        //调用数据持久层对实体进行持久化操作
        //this.basicDataTypeDao.insert(basicDataType);
    }
    
    /**
     * 查询BasicDataType实体列表
     * <功能详细描述>
     * @param valid
     * @param params      
     * @return [参数说明]
     * 
     * @return List<BasicDataType> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public List<BasicDataType> queryList(final String module,
            final Boolean common, final String code) {
        if (!StringUtils.isEmpty(code)) {
            return Arrays.asList(code2typeMap.get(code));
        }
        //根据实际情况，填入排序字段等条件，根据是否需要排序，选择调用dao内方法
        List<BasicDataType> resList = new ArrayList<>(code2typeMap.values());
        
        //根据module过滤
        resList = ListUtils.predicatedList(resList,
                new Predicate<BasicDataType>() {
                    @Override
                    public boolean evaluate(BasicDataType object) {
                        if (StringUtils.isEmpty(module)) {
                            return true;
                        } else if (module.equals(object.getModule())) {
                            return true;
                        } else {
                            return false;
                        }
                    }
                });
        
        //根据module过滤
        resList = ListUtils.predicatedList(resList,
                new Predicate<BasicDataType>() {
                    @Override
                    public boolean evaluate(BasicDataType object) {
                        if (common == null) {
                            return true;
                        } else if (common.booleanValue() == object.isCommon()) {
                            return true;
                        } else {
                            return false;
                        }
                    }
                });
        
        return resList;
    }
    
    /**
     * 根据Id查询BasicDataType实体
     * 1、当id为empty时抛出异常
     *
     * @param id
     * @return BasicDataType [返回类型说明]
     * @exception throws
     * @see [类、类#方法、类#成员]
     */
    public BasicDataType findByCode(String code) {
        AssertUtils.notEmpty(code, "code is empty.");
        
        BasicDataType condition = new BasicDataType();
        condition.setCode(code);
        
        BasicDataType res = this.code2typeMap.get(code);
        //BasicDataType res = this.basicDataTypeDao.find(condition);
        return res;
    }
    
    /**
     * 根据Id查询BasicDataType实体
     * 1、当id为empty时抛出异常
     *
     * @param id
     * @return BasicDataType [返回类型说明]
     * @exception throws
     * @see [类、类#方法、类#成员]
     */
    public BasicDataType findByType(Class<? extends BasicData> type) {
        AssertUtils.notNull(type, "type is null.");
        
        BasicDataType condition = new BasicDataType();
        condition.setCode(type.getName());
        
        BasicDataType res = this.type2typeMap.get(type);
        //BasicDataType res = this.basicDataTypeDao.find(condition);
        return res;
    }
    //  /**
    //     * 根据Id查询BasicDataType实体
    //     * 1、当id为empty时抛出异常
    //     *
    //     * @param id
    //     * @return BasicDataType [返回类型说明]
    //     * @exception throws
    //     * @see [类、类#方法、类#成员]
    //     */
    //    public BasicDataType findById(String id) {
    //        AssertUtils.notEmpty(id, "id is empty.");
    //        
    //        BasicDataType condition = new BasicDataType();
    //        condition.setId(id);
    //        
    //        BasicDataType res = this.basicDataTypeDao.find(condition);
    //        return res;
    //    }
    //    /**
    //     * 查询BasicDataType实体列表
    //     * <功能详细描述>
    //     * @param valid
    //     * @param params      
    //     * @return [参数说明]
    //     * 
    //     * @return List<BasicDataType> [返回类型说明]
    //     * @exception throws [异常类型] [异常说明]
    //     * @see [类、类#方法、类#成员]
    //     */
    //    public List<BasicDataType> queryList(Boolean valid,
    //            Map<String, Object> params) {
    //        //判断条件合法性
    //        
    //        //生成查询条件
    //        params = params == null ? new HashMap<String, Object>() : params;
    //        params.put("valid", valid);
    //        
    //        //根据实际情况，填入排序字段等条件，根据是否需要排序，选择调用dao内方法
    //        List<BasicDataType> resList = this.basicDataTypeDao.queryList(params);
    //        
    //        return resList;
    //    }
    //    /**
    //     * 分页查询BasicDataType实体列表
    //     * <功能详细描述>
    //     * @param valid
    //     * @param params    
    //     * @param pageIndex 当前页index从1开始计算
    //     * @param pageSize 每页显示行数
    //     * 
    //     * <功能详细描述>
    //     * @return [参数说明]
    //     * 
    //     * @return List<BasicDataType> [返回类型说明]
    //     * @exception throws [异常类型] [异常说明]
    //     * @see [类、类#方法、类#成员]
    //     */
    //    public PagedList<BasicDataType> queryPagedList(Boolean valid,
    //            Map<String, Object> params, int pageIndex, int pageSize) {
    //        //T判断条件合法性
    //        
    //        //生成查询条件
    //        params = params == null ? new HashMap<String, Object>() : params;
    //        params.put("valid", valid);
    //        
    //        //根据实际情况，填入排序字段等条件，根据是否需要排序，选择调用dao内方法
    //        PagedList<BasicDataType> resPagedList = this.basicDataTypeDao
    //                .queryPagedList(params, pageIndex, pageSize);
    //        
    //        return resPagedList;
    //    }
    //  /**
    //  * 根据id删除basicDataType实例
    //  * 1、如果入参数为空，则抛出异常
    //  * 2、执行删除后，将返回数据库中被影响的条数 > 0，则返回true
    //  *
    //  * @param id
    //  * @return boolean 删除的条数>0则为true [返回类型说明]
    //  * @exception throws 
    //  * @see [类、类#方法、类#成员]
    //  */
    // @Transactional
    // public boolean deleteById(String id) {
    //     AssertUtils.notEmpty(id, "id is empty.");
    //     
    //     BasicDataType condition = new BasicDataType();
    //     condition.setId(id);
    //     int resInt = this.basicDataTypeDao.delete(condition);
    //     
    //     boolean flag = resInt > 0;
    //     return flag;
    // }    
    //    /**
    //     * 判断是否已经存在<br/>
    //     * <功能详细描述>
    //     * @return [参数说明]
    //     * 
    //     * @return int [返回类型说明]
    //     * @exception throws [异常类型] [异常说明]
    //     * @see [类、类#方法、类#成员]
    //    */
    //    public boolean isExist(Map<String, String> key2valueMap, String excludeId) {
    //        AssertUtils.notEmpty(key2valueMap, "key2valueMap is empty");
    //        
    //        //生成查询条件
    //        Map<String, Object> params = new HashMap<String, Object>();
    //        params.putAll(key2valueMap);
    //        params.put("excludeId", excludeId);
    //        
    //        //根据实际情况，填入排序字段等条件，根据是否需要排序，选择调用dao内方法
    //        int res = this.basicDataTypeDao.count(params);
    //        
    //        return res > 0;
    //    }
    //    
    //    /**
    //      * 根据id更新对象
    //      * <功能详细描述>
    //      * @param basicDataType
    //      * @return [参数说明]
    //      * 
    //      * @return boolean [返回类型说明]
    //      * @exception throws [异常类型] [异常说明]
    //      * @see [类、类#方法、类#成员]
    //     */
    //    @Transactional
    //    public boolean updateById(BasicDataType basicDataType) {
    //        //验证参数是否合法，必填字段是否填写，
    //        AssertUtils.notNull(basicDataType, "basicDataType is null.");
    //        AssertUtils.notEmpty(basicDataType.getId(),
    //                "basicDataType.id is empty.");
    //        
    //        //生成需要更新字段的hashMap
    //        Map<String, Object> updateRowMap = new HashMap<String, Object>();
    //        updateRowMap.put("id", basicDataType.getId());
    //        
    //        //需要更新的字段
    //        updateRowMap.put("module", basicDataType.getModule());
    //        updateRowMap.put("code", basicDataType.getCode());
    //        updateRowMap.put("name", basicDataType.getName());
    //        updateRowMap.put("tableName", basicDataType.getTableName());
    //        updateRowMap.put("modifyAble", basicDataType.isModifyAble());
    //        updateRowMap.put("valid", basicDataType.isValid());
    //        updateRowMap.put("common", basicDataType.isCommon());
    //        updateRowMap.put("viewType", basicDataType.getViewType());
    //        
    //        updateRowMap.put("remark", basicDataType.getRemark());
    //        updateRowMap.put("lastUpdateDate", new Date());
    //        int updateRowCount = this.basicDataTypeDao.update(updateRowMap);
    //        
    //        //如果需要大于1时，抛出异常并回滚，需要在这里修改
    //        return updateRowCount >= 1;
    //    }
    //    
    //    /**
    //     * 根据id禁用BasicDataType<br/>
    //     * <功能详细描述>
    //     * @param id
    //     * @return [参数说明]
    //     * 
    //     * @return boolean [返回类型说明]
    //     * @exception throws [异常类型] [异常说明]
    //     * @see [类、类#方法、类#成员]
    //    */
    //    @Transactional
    //    public boolean disableById(String id) {
    //        AssertUtils.notEmpty(id, "id is empty.");
    //        
    //        //生成查询条件
    //        Map<String, Object> params = new HashMap<String, Object>();
    //        params.put("id", id);
    //        params.put("valid", false);
    //        params.put("lastUpdateDate", new Date());
    //        
    //        this.basicDataTypeDao.update(params);
    //        
    //        return true;
    //    }
    //    
    //    /**
    //      * 根据id启用BasicDataType<br/>
    //      * <功能详细描述>
    //      * @param postId
    //      * @return [参数说明]
    //      * 
    //      * @return boolean [返回类型说明]
    //      * @exception throws [异常类型] [异常说明]
    //      * @see [类、类#方法、类#成员]
    //     */
    //    @Transactional
    //    public boolean enableById(String id) {
    //        AssertUtils.notEmpty(id, "id is empty.");
    //        
    //        //生成查询条件
    //        Map<String, Object> params = new HashMap<String, Object>();
    //        params.put("id", id);
    //        params.put("valid", true);
    //        params.put("lastUpdateDate", new Date());
    //        
    //        this.basicDataTypeDao.update(params);
    //        
    //        return true;
    //    }
}