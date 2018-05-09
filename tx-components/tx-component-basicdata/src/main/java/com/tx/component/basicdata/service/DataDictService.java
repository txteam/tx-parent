/*
 * 描          述:  <描述>
 * 修  改   人:  
 * 修改时间:  
 * <修改描述:>
 */
package com.tx.component.basicdata.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.sql.DataSource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.ResourceLoader;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.tx.component.basicdata.dao.DataDictDao;
import com.tx.component.basicdata.model.DataDict;
import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.paged.model.PagedList;
import com.tx.core.support.entrysupport.model.EntityEntry;
import com.tx.core.support.entrysupport.support.AbstractEntityEntryAbleService;
import com.tx.core.support.entrysupport.support.EntityEntrySupport;
import com.tx.core.support.entrysupport.support.EntityEntrySupportFactory;
import com.tx.core.support.initable.helper.ConfigInitAbleHelper;
import com.tx.core.support.poi.excel.ExcelReadUtils;

/**
 * DataDict的业务层
 * <功能详细描述>
 * 
 * @author  
 * @version  [版本号, ]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class DataDictService extends AbstractEntityEntryAbleService<DataDict>
        implements InitializingBean, ResourceLoaderAware {
    
    @SuppressWarnings("unused")
    private Logger logger = LoggerFactory.getLogger(DataDictService.class);
    
    private ResourceLoader resourceLoader;
    
    private DataDictDao dataDictDao;
    
    private DataSource dataSource;
    
    private TransactionTemplate transactionTemplate;
    
    /** <默认构造函数> */
    public DataDictService() {
        super();
    }
    
    /** <默认构造函数> */
    public DataDictService(DataSource dataSource,
            TransactionTemplate transactionTemplate, DataDictDao dataDictDao) {
        super();
        this.dataDictDao = dataDictDao;
        this.dataSource = dataSource;
        this.transactionTemplate = transactionTemplate;
    }
    
    /**
     * @throws Exception
     */
    @Override
    protected EntityEntrySupport<EntityEntry> doBuildEntityEntrySupport()
            throws Exception {
        EntityEntrySupport<EntityEntry> entityEntrySupport = EntityEntrySupportFactory
                .getSupport("bd_data_dict_entry",
                        this.dataSource,
                        this.transactionTemplate);
        return entityEntrySupport;
    }
    
    /**
     * @param resourceLoader
     */
    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }
    
    /**
     * 从Excel中加载校验原因基础数据<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return List<ExamineReason> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    private List<DataDict> loadListFromExcelConfig() {
        List<DataDict> resList = new ArrayList<>();
        if (!resourceLoader
                .getResource("classpath*:init/basicdata/data_dict.xlsx")
                .exists()) {
            return resList;
        }
        
        Workbook wb = ExcelReadUtils
                .getWorkBook("classpath*:init/basicdata/data_dict.xlsx");
        int numberOfSheets = wb.getNumberOfSheets();
        for (int sheetIndex = 0; sheetIndex < numberOfSheets; sheetIndex++) {
            Sheet sheet = wb.getSheetAt(sheetIndex);
            if (sheet == null) {
                continue;
            }
            String sheetName = sheet.getSheetName();
            List<Map<String, String>> rowMapList = ExcelReadUtils
                    .readSheet(sheet);
            
            for (Map<String, String> rowMap : rowMapList) {
                DataDict ddTemp = new DataDict();
                BeanWrapper ddBW = PropertyAccessorFactory
                        .forBeanPropertyAccess(ddTemp);
                
                String code = rowMap.get("code");
                if (StringUtils.isEmpty(code)) {
                    //如果code不存在，则跳过该非法行
                    continue;
                }
                String basicDataTypeCode = rowMap.get("basicDataTypeCode");
                if (StringUtils.isEmpty(basicDataTypeCode)) {
                    basicDataTypeCode = sheetName;
                }
                ddTemp.setCode(code);
                ddTemp.setBasicDataTypeCode(basicDataTypeCode);
                
                //其他属性字段
                for (Entry<String, String> entryTemp : rowMap.entrySet()) {
                    String entryKey = entryTemp.getKey();
                    String entryValue = entryTemp.getValue();
                    if ("code".equals(entryKey)
                            || "basicDataTypeCode".equals(entryKey)
                            || "class".equals(entryKey)) {
                        continue;
                    }
                    if (ddBW.isWritableProperty(entryKey)) {
                        //如果是基础类可以写入的属性:比如parentId的值？
                        ddBW.setPropertyValue(entryKey, entryValue);
                    } else {
                        //如果不是
                        EntityEntry ee = new EntityEntry(entryKey, entryValue);
                        ddTemp.getEntryList().add(ee);
                    }
                }
                
                resList.add(ddTemp);
            }
        }
        return resList;
    }
    
    /**
      * 从数据库中加载数据字典数据<br/>
      * <功能详细描述>
      * @return [参数说明]
      * 
      * @return List<DataDict> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    private List<DataDict> loadListFromDB() {
        MultiValueMap<String, EntityEntry> eeMultiMap = new LinkedMultiValueMap<>();
        List<EntityEntry> entityEntryList = getEntityEntrySupport().queryList();
        for (EntityEntry eeTemp : entityEntryList) {
            eeMultiMap.add(eeTemp.getEntityId(), eeTemp);
        }
        
        List<DataDict> ddList = this.dataDictDao.queryList(null);
        for (DataDict ddTemp : ddList) {
            if (!eeMultiMap.containsKey(ddTemp.getId())) {
                continue;
            }
            ddTemp.setEntryList(eeMultiMap.get(ddTemp.getId()));
        }
        return ddList;
    }
    
    /**
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        if (!resourceLoader
                .getResource("classpath*:init/basicdata/data_dict.xlsx")
                .exists()) {
            return;
        }
        ConfigInitAbleHelper<DataDict> helper = new ConfigInitAbleHelper<DataDict>() {
            /**
             * @param cia
             * @return
             */
            @Override
            protected String getSingleCode(DataDict cia) {
                AssertUtils.notNull(cia, "cia is null.");
                AssertUtils.notEmpty(cia.getBasicDataTypeCode(),
                        "cia is null.");
                AssertUtils.notEmpty(cia.getCode(), "cia is null.");
                
                String code = cia.getBasicDataTypeCode() + "_" + cia.getCode();
                code = code.toUpperCase();
                return code;
            }
            
            @Override
            protected List<DataDict> queryListFromConfig() {
                List<DataDict> resListOfCfg = loadListFromExcelConfig();
                return resListOfCfg;
            }
            
            @Override
            protected List<DataDict> queryListFromDB() {
                List<DataDict> resListOfDB = loadListFromDB();
                return resListOfDB;
            }
            
            @Override
            protected void doBeforeUpdate(DataDict ciaOfDB, DataDict ciaOfCfg) {
                ciaOfDB.setName(ciaOfCfg.getName());
                ciaOfDB.setRemark(ciaOfCfg.getRemark());
                ciaOfDB.setBasicDataTypeCode(ciaOfCfg.getBasicDataTypeCode());
                ciaOfDB.setEntryList(ciaOfCfg.getEntryList());
            }
            
            @Override
            protected boolean isNeedUpdate(DataDict ciaOfDB,
                    DataDict ciaOfCfg) {
                if (!StringUtils.equals(ciaOfDB.getName(),
                        ciaOfCfg.getName())) {
                    return true;
                }
                if (!StringUtils.equals(ciaOfDB.getRemark(),
                        ciaOfCfg.getRemark())) {
                    return true;
                }
                if (ciaOfDB.getEntryList() != ciaOfCfg.getEntryList()) {
                    if (ciaOfDB.getEntryList() == null
                            || ciaOfCfg.getEntryList() == null) {
                        return false;
                    }
                    Map<String, EntityEntry> dbEntryMap = new HashMap<>();
                    Map<String, EntityEntry> cfgEntryMap = new HashMap<>();
                    for (EntityEntry ee : ciaOfDB.getEntryList()) {
                        dbEntryMap.put(ee.getEntryKey(), ee);
                    }
                    for (EntityEntry ee : ciaOfCfg.getEntryList()) {
                        cfgEntryMap.put(ee.getEntryKey(), ee);
                    }
                    if (dbEntryMap.size() != cfgEntryMap.size()) {
                        return false;
                    }
                    for (Entry<String, EntityEntry> dbeeEntry : dbEntryMap
                            .entrySet()) {
                        String key = dbeeEntry.getKey();
                        EntityEntry dbee = dbeeEntry.getValue();
                        if (!cfgEntryMap.containsKey(key)) {
                            return false;
                        } else if (!StringUtils.equals(dbee.getEntryValue(),
                                cfgEntryMap.get(key).getEntryValue())) {
                            return false;
                        }
                    }
                }
                return false;
            }
            
            @Override
            protected void batchUpdate(List<DataDict> needUpdateList) {
                if (CollectionUtils.isEmpty(needUpdateList)) {
                    return;
                }
                for (DataDict erTemp : needUpdateList) {
                    updateById(erTemp);
                }
            }
            
            @Override
            protected void batchInsert(List<DataDict> needInsertList) {
                if (CollectionUtils.isEmpty(needInsertList)) {
                    return;
                }
                for (DataDict erTemp : needInsertList) {
                    erTemp.setModifyAble(false);
                    insert(erTemp);
                }
            }
        };
        helper.init(this.transactionTemplate);
    }
    
    /**
     * @param entity
     */
    @Override
    protected void insertEntity(DataDict dataDict) {
        //验证参数是否合法
        AssertUtils.notNull(dataDict, "dataDict is null.");
        
        //为添加的数据需要填入默认值的字段填入默认值
        dataDict.setValid(true);
        //不能设置该值，该值在初始化自动插入逻辑中被用到，应当在初始化期间设置为true
        //dataDict.setModifyAble(true);
        
        Date now = new Date();
        dataDict.setCreateDate(now);
        dataDict.setLastUpdateDate(now);
        
        //调用数据持久层对实体进行持久化操作
        this.dataDictDao.insert(dataDict);
    }
    
    /**
     * @param entityId
     * @return
     */
    @Override
    protected boolean deleteEntityById(String id) {
        AssertUtils.notEmpty(id, "id is empty.");
        
        DataDict condition = new DataDict();
        condition.setId(id);
        int resInt = this.dataDictDao.delete(condition);
        
        boolean flag = resInt > 0;
        return flag;
    }
    
    /**
     * 根据Id查询DataDict实体
     * 1、当id为empty时抛出异常
     *
     * @param id
     * @return DataDict [返回类型说明]
     * @exception throws
     * @see [类、类#方法、类#成员]
     */
    public DataDict findEntityById(String id) {
        AssertUtils.notEmpty(id, "id is empty.");
        
        DataDict condition = new DataDict();
        condition.setId(id);
        
        DataDict res = this.dataDictDao.find(condition);
        return res;
    }
    
    /**
     * 根据Id查询DataDict实体
     * 1、当id为empty时抛出异常
     *
     * @param id
     * @return DataDict [返回类型说明]
     * @exception throws
     * @see [类、类#方法、类#成员]
     */
    public DataDict findByCode(String basicDataTypeCode, String code) {
        AssertUtils.notEmpty(basicDataTypeCode, "basicDataTypeCode is empty.");
        AssertUtils.notEmpty(code, "code is empty.");
        
        DataDict entity = findEntityByCode(basicDataTypeCode, code);
        
        //加载Entity的分项列表
        setupEntryList(entity);
        
        return entity;
    }
    
    /**
     * 根据Id查询DataDict实体
     * 1、当id为empty时抛出异常
     *
     * @param id
     * @return DataDict [返回类型说明]
     * @exception throws
     * @see [类、类#方法、类#成员]
     */
    public DataDict findEntityByCode(String basicDataTypeCode, String code) {
        AssertUtils.notEmpty(basicDataTypeCode, "basicDataTypeCode is empty.");
        AssertUtils.notEmpty(code, "code is empty.");
        
        DataDict condition = new DataDict();
        condition.setBasicDataTypeCode(basicDataTypeCode);
        condition.setCode(code);
        
        DataDict res = this.dataDictDao.find(condition);
        return res;
    }
    
    /**
     * 查询DataDict实体列表
     * <功能详细描述>
     * @param valid
     * @param params      
     * @return [参数说明]
     * 
     * @return List<DataDict> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public List<DataDict> queryList(String basicDataTypeCode, Boolean valid,
            Map<String, Object> params) {
        //判断条件合法性
        
        //生成查询条件
        params = params == null ? new HashMap<String, Object>() : params;
        params.put("basicDataTypeCode", basicDataTypeCode);
        params.put("valid", valid);
        
        //根据实际情况，填入排序字段等条件，根据是否需要排序，选择调用dao内方法
        List<DataDict> resList = this.dataDictDao.queryList(params);
        
        return resList;
    }
    
    /**
     * 查询DataDict实体列表
     * <功能详细描述>
     * @param valid
     * @param params      
     * @return [参数说明]
     * 
     * @return List<DataDict> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public List<DataDict> queryList(String basicDataTypeCode, String parentId,
            Boolean valid, Map<String, Object> params) {
        //判断条件合法性
        
        //生成查询条件
        params = params == null ? new HashMap<String, Object>() : params;
        params.put("basicDataTypeCode", basicDataTypeCode);
        params.put("parentId", parentId);
        params.put("valid", valid);
        
        //根据实际情况，填入排序字段等条件，根据是否需要排序，选择调用dao内方法
        List<DataDict> resList = this.dataDictDao.queryList(params);
        
        return resList;
    }
    
    /**
     * 分页查询DataDict实体列表
     * <功能详细描述>
     * @param valid
      * @param params    
     * @param pageIndex 当前页index从1开始计算
     * @param pageSize 每页显示行数
     * 
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return List<DataDict> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public PagedList<DataDict> queryPagedList(String basicDataTypeCode,
            Boolean valid, Map<String, Object> params, int pageIndex,
            int pageSize) {
        //T判断条件合法性
        
        //生成查询条件
        params = params == null ? new HashMap<String, Object>() : params;
        params.put("basicDataTypeCode", basicDataTypeCode);
        params.put("valid", valid);
        
        //根据实际情况，填入排序字段等条件，根据是否需要排序，选择调用dao内方法
        PagedList<DataDict> resPagedList = this.dataDictDao
                .queryPagedList(params, pageIndex, pageSize);
        
        return resPagedList;
    }
    
    /**
     * 分页查询DataDict实体列表
     * <功能详细描述>
     * @param valid
      * @param params    
     * @param pageIndex 当前页index从1开始计算
     * @param pageSize 每页显示行数
     * 
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return List<DataDict> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public PagedList<DataDict> queryPagedList(String basicDataTypeCode,
            String parentId, Boolean valid, Map<String, Object> params,
            int pageIndex, int pageSize) {
        //T判断条件合法性
        
        //生成查询条件
        params = params == null ? new HashMap<String, Object>() : params;
        params.put("basicDataTypeCode", basicDataTypeCode);
        params.put("parentId", parentId);
        params.put("valid", valid);
        
        //根据实际情况，填入排序字段等条件，根据是否需要排序，选择调用dao内方法
        PagedList<DataDict> resPagedList = this.dataDictDao
                .queryPagedList(params, pageIndex, pageSize);
        
        return resPagedList;
    }
    
    /**
     * 判断是否已经存在<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return int [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    public boolean isExist(String basicDataTypeCode,
            Map<String, String> key2valueMap, String excludeId) {
        AssertUtils.notEmpty(basicDataTypeCode, "basicDataTypeCode is empty");
        AssertUtils.notEmpty(key2valueMap, "key2valueMap is empty");
        
        //生成查询条件
        Map<String, Object> params = new HashMap<String, Object>();
        params.putAll(key2valueMap);
        params.put("basicDataTypeCode", basicDataTypeCode);
        params.put("excludeId", excludeId);
        
        //根据实际情况，填入排序字段等条件，根据是否需要排序，选择调用dao内方法
        int res = this.dataDictDao.count(params);
        
        return res > 0;
    }
    
    /**
     * @param entity
     * @return
     */
    @Override
    @Transactional
    public boolean updateEntityById(DataDict dataDict) {
        //验证参数是否合法，必填字段是否填写，
        AssertUtils.notNull(dataDict, "dataDict is null.");
        AssertUtils.notEmpty(dataDict.getId(), "dataDict.id is empty.");
        
        //生成需要更新字段的hashMap
        Map<String, Object> updateRowMap = new HashMap<String, Object>();
        updateRowMap.put("id", dataDict.getId());
        
        //需要更新的字段
        //updateRowMap.put("valid", false);
        updateRowMap.put("name", dataDict.getName());
        updateRowMap.put("remark", dataDict.getRemark());
        updateRowMap.put("modifyAble", dataDict.isModifyAble());
        updateRowMap.put("lastUpdateDate", new Date());
        
        int updateRowCount = this.dataDictDao.update(updateRowMap);
        
        //如果需要大于1时，抛出异常并回滚，需要在这里修改
        return updateRowCount >= 1;
    }
    
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
    @Transactional
    public boolean disableById(String id) {
        AssertUtils.notEmpty(id, "id is empty.");
        
        //生成查询条件
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", id);
        params.put("valid", false);
        params.put("lastUpdateDate", new Date());
        
        this.dataDictDao.update(params);
        
        return true;
    }
    
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
    @Transactional
    public boolean enableById(String id) {
        AssertUtils.notEmpty(id, "id is empty.");
        
        //生成查询条件
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", id);
        params.put("valid", true);
        params.put("lastUpdateDate", new Date());
        
        this.dataDictDao.update(params);
        
        return true;
    }
    
    /**
     * @param 对dataSource进行赋值
     */
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    
    /**
     * @param 对transactionTemplate进行赋值
     */
    public void setTransactionTemplate(
            TransactionTemplate transactionTemplate) {
        this.transactionTemplate = transactionTemplate;
    }
}
