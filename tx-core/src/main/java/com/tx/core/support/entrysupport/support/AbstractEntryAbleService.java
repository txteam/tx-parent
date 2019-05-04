/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2016年10月5日
 * <修改描述:>
 */
package com.tx.core.support.entrysupport.support;

import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.paged.model.PagedList;
import com.tx.core.support.entrysupport.model.EntityEntry;
import com.tx.core.support.entrysupport.model.EntryAble;

/**
 * 抽象分项实体的业务层逻辑<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2016年10月5日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public abstract class AbstractEntryAbleService<ENTRY extends EntityEntry, ENTITY extends EntryAble<ENTRY>> {
    
    private EntityEntrySupport<ENTRY> entityEntrySupport;
    
    @PostConstruct
    public void initEntityEntrySupport() throws Exception {
        this.entityEntrySupport = doBuildEntityEntrySupport();
    }
    
    protected abstract EntityEntrySupport<ENTRY> doBuildEntityEntrySupport()
            throws Exception;
    
    /**
      * 插入EntryAbleEntity实例<br/>
      * <功能详细描述>
      * @param entity [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    @Transactional
    public void insert(ENTITY entity) {
        AssertUtils.notNull(entity, "entity is null.");
        
        //插入entity实例
        insertEntity(entity);
        
        String entityId = entity.getId();
        AssertUtils.notEmpty(entityId, "entityId is empty.");
        List<ENTRY> entryList = entity.getEntryList();
        
        entityEntrySupport.batchInsert(entityId, entryList);
    }
    
    /**
      * 根据实例id删除对应的实例<br/>
      * <功能详细描述>
      * @param entityId [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    @Transactional
    public boolean deleteById(String entityId) {
        AssertUtils.notEmpty(entityId, "entityId is empty.");
        
        boolean flag = deleteEntityById(entityId);
        
        if (flag) {
            entityEntrySupport.deleteByEntityId(entityId);
        }
        return flag;
    }
    
    /**
      * 根据id查询实例<br/>
      * <功能详细描述>
      * @param id
      * @return [参数说明]
      * 
      * @return ENTITY [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public ENTITY findById(String entityId) {
        AssertUtils.notEmpty(entityId, "entityId is empty.");
        
        //根据id查询实例
        ENTITY entity = findEntityById(entityId);
        
        //加载Entity的分项列表
        setupEntryList(entity);
        
        return entity;
    }
    
    /**
      * 更新对象<br/>
      * <功能详细描述>
      * @param entityId
      * @return [参数说明]
      * 
      * @return ENTITY [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    @Transactional
    public boolean updateById(ENTITY entity) {
        AssertUtils.notNull(entity, "entityId is empty.");
        AssertUtils.notEmpty(entity.getId(), "entityId is empty.");
        
        //根据id查询实例
        boolean flag = updateEntityById(entity);
        
        //如果更新实体成功，同时更新其其余参数
        if (flag) {
            String entityId = entity.getId();
            entityEntrySupport.batchSaveEntry(entityId, entity.getEntryList());
        }
        
        return flag;
    }
    
    /**
     * 加载Entry列表信息
     * <功能详细描述>
     * @param entityList [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    public void setupEntryList(ENTITY entity) {
        //如果entity为空，或EntityId为空，或EntryList不为空则不再从数据库中加载
        if (entity == null || StringUtils.isEmpty(entity.getId())
                || !CollectionUtils.isEmpty(entity.getEntryList())) {
            return;
        }
        //加载数据
        entity.setEntryList(this.entityEntrySupport.queryListByEntityId(entity.getId()));
    }
    
    /**
      * 加载Entry列表信息
      * <功能详细描述>
      * @param entityList [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public void setupEntryList(List<ENTITY> entityList) {
        if (CollectionUtils.isEmpty(entityList)) {
            return;
        }
        for (ENTITY entity : entityList) {
            setupEntryList(entity);
        }
    }
    
    /**
      * 加载Entry列表信息
      * <功能详细描述>
      * @param entityPagedList [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public void setupEntryList(PagedList<ENTITY> entityPagedList) {
        if (entityPagedList == null
                || CollectionUtils.isEmpty(entityPagedList.getList())) {
            return;
        }
        setupEntryList(entityPagedList.getList());
    }
    
    /**
      * 插入实例<br/>
      * <功能详细描述>
      * @param entity [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    protected abstract void insertEntity(ENTITY entity);
    
    /**
     * 插入实例<br/>
     * <功能详细描述>
     * @param entity [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    protected abstract boolean deleteEntityById(String entityId);
    
    /**
     * 根据id查询实例<br/>
     * <功能详细描述>
     * @param id
     * @return [参数说明]
     * 
     * @return ENTITY [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    public abstract ENTITY findEntityById(String entityId);
    
    /**
     * 插入实例<br/>
     * <功能详细描述>
     * @param entity [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Transactional
    public abstract boolean updateEntityById(ENTITY entity);
    
    /**
     * @return 返回 entityEntrySupport
     */
    protected EntityEntrySupport<ENTRY> getEntityEntrySupport() {
        return entityEntrySupport;
    }
    
    /**
     * @param 对entityEntrySupport进行赋值
     */
    public void setEntityEntrySupport(
            EntityEntrySupport<ENTRY> entityEntrySupport) {
        this.entityEntrySupport = entityEntrySupport;
    }
}
