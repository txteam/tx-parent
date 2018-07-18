/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2016年10月3日
 * <修改描述:>
 */
package com.tx.component.basicdata.context;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Table;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.transaction.annotation.Transactional;

import com.tx.component.basicdata.annotation.BasicDataType;
import com.tx.component.basicdata.model.BasicData;
import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.support.initable.helper.ConfigInitAbleHelper;
import com.tx.core.util.typereference.ParameterizedTypeReference;

/**
 * 基础数据业务层<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2016年10月3日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public abstract class AbstractBasicDataService<T extends BasicData>
        extends ParameterizedTypeReference<T>
        implements BasicDataService<T>, InitializingBean {
    
    protected ConfigInitAbleHelper<T> configInitAbleHelper;
    
    /**
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        final AbstractBasicDataService<T> service = this;
        this.configInitAbleHelper = new ConfigInitAbleHelper<T>() {
            
            /**
             * @param needUpdateList
             */
            @Override
            protected void batchUpdate(List<T> needUpdateList) {
                service.doBatchUpdateWhenInit(needUpdateList);
            }
            
            /**
             * @param needInsertList
             */
            @Override
            protected void batchInsert(List<T> needInsertList) {
                service.doBatchInsertWhenInit(needInsertList);
            }
            
            /**
             * @return
             */
            @Override
            protected List<T> queryListFromConfig() {
                List<T> resCfgList = service.loadDataFromConfig();
                return resCfgList;
            }
            
            /**
             * @return
             */
            @Override
            protected List<T> queryListFromDB() {
                List<T> resDbList = service.queryList(null, null);
                return resDbList;
            }
            
            /**
             * @param ciaOfDBTemp
             * @param ciaOfConfig
             * @return
             */
            @Override
            protected boolean isNeedUpdate(T ciaOfDBTemp, T ciaOfConfig) {
                boolean flag = service.isNeedUpdate(ciaOfDBTemp, ciaOfConfig);
                return flag;
            }
            
            /**
             * @param ciaOfDB
             * @param ciaOfCfg
             */
            @Override
            protected void doBeforeUpdate(T ciaOfDB, T ciaOfCfg) {
                service.doBeforeUpdate(ciaOfDB, ciaOfCfg);
            }
        };
        
        //初始化基础数据
        init();
    }
    
    /**
      * 对初始化数据进行加载<br/>
      * <功能详细描述>
      * @throws Exception [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    protected void init() throws Exception {
    }
    
    /**
     * 执行初始化期间批量插入逻辑<br/>
     * <功能详细描述>
     * @param dataList [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    protected void doBatchInsertWhenInit(List<T> dataList) {
        batchInsert(dataList);
    }
    
    /**
     * 执行初始化期间批量插入逻辑<br/>
     * <功能详细描述>
     * @param dataList [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    protected void doBatchUpdateWhenInit(List<T> dataList) {
        batchUpdate(dataList);
    }
    
    /**
      * 根据配置加载基础数据
      * <功能详细描述>
      * @return [参数说明]
      * 
      * @return List<T> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    protected List<T> loadDataFromConfig() {
        return new ArrayList<>();
    }
    
    /**
      * 相同code的数据库中数据与配置中数据比较后判断是否需要对数据进行更新<br/>
      * <功能详细描述>
      * @param ciaOfDBTemp
      * @param ciaOfConfig
      * @return [参数说明]
      * 
      * @return boolean [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    protected boolean isNeedUpdate(T ciaOfDBTemp, T ciaOfConfig) {
        if (!StringUtils.equals(ciaOfDBTemp.getName(), ciaOfConfig.getName())) {
            return true;
        }
        if (!StringUtils.equals(ciaOfDBTemp.getRemark(),
                ciaOfConfig.getRemark())) {
            return true;
        }
        return false;
    }
    
    /**
      * 需要更新的数据进行设置值更新<br/>
      * <功能详细描述>
      * @param ciaOfDB
      * @param ciaOfCfg [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    protected void doBeforeUpdate(T ciaOfDB, T ciaOfCfg) {
        ciaOfDB.setName(ciaOfCfg.getName());
        ciaOfDB.setRemark(ciaOfCfg.getRemark());
    }
    
    /**
     * @return
     */
    @SuppressWarnings("unchecked")
    @Override
    public Class<T> type() {
        Class<T> type = (Class<T>) getType();
        return type;
    }
    
    /**
     * @return
     */
    @Override
    public String code() {
        Class<T> type = type();
        AssertUtils.notNull(type, "type is null.");
        
        String code = type.getSimpleName();
        return code;
    }
    
    /**
     * @return
     */
    @Override
    public String module() {
        Class<T> type = type();
        AssertUtils.notNull(type, "type is null.");
        
        String module = "";
        if (type.isAnnotationPresent(BasicDataType.class) && StringUtils
                .isNotEmpty(type.getAnnotation(BasicDataType.class).module())) {
            module = type.getAnnotation(BasicDataType.class).module();
        }
        return module;
    }
    
    /**
     * @return
     */
    @Override
    public String tableName() {
        Class<T> type = type();
        AssertUtils.notNull(type, "type is null.");
        
        String tableName = type.getSimpleName();
        if (type.isAnnotationPresent(Table.class)) {
            tableName = type.getAnnotation(Table.class).name();
        }
        if (type.isAnnotationPresent(org.hibernate.annotations.Table.class)) {
            tableName = type.getAnnotation(Table.class).name();
        }
        if (StringUtils.isEmpty(tableName)) {
            tableName = "bd_data_dict";
        }
        return tableName;
    }
    
    /**
     * @param dataList
     */
    @Override
    @Transactional
    public void batchInsert(List<T> dataList) {
        if (CollectionUtils.isEmpty(dataList)) {
            return;
        }
        for (T dataTemp : dataList) {
            insert(dataTemp);
        }
    }
    
    /**
     * @param dataList
     */
    @Override
    @Transactional
    public void batchUpdate(List<T> dataList) {
        if (CollectionUtils.isEmpty(dataList)) {
            return;
        }
        for (T dataTemp : dataList) {
            updateById(dataTemp);
        }
    }
}
