/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2016年9月17日
 * <修改描述:>
 */
package com.tx.core.support.initable.helper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;

import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.support.initable.model.ConfigInitAble;

/**
 * 可配置的辅助类<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2016年9月17日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public abstract class ConfigInitAbleHelper<CIA extends ConfigInitAble> {
    
    /**
      * 从配置中进行配置初始化<br/>
      * <功能详细描述> [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public final void init() {
        //预制处理列表
        List<CIA> needInsertList = new ArrayList<>();
        List<CIA> needUpdateModifyAbleList = new ArrayList<>();
        List<CIA> needUpdateToCannotModifyAbleList = new ArrayList<>();
        
        //从数据库中获取对象集合
        Map<String, CIA> code2CIAMapOfDB = new HashMap<String, CIA>();
        List<CIA> listFromDB = queryListFromDB();
        for (CIA ciaOfDBTemp : listFromDB) {
            code2CIAMapOfDB.put(ciaOfDBTemp.getCode(), ciaOfDBTemp);
        }
        
        //从配置中获取对象集合
        Map<String, CIA> code2CIAMapOfConfig = new HashMap<String, CIA>();
        List<CIA> listFromConfig = queryListFromConfig();
        for (CIA ciaOfCfgTemp : listFromConfig) {
            code2CIAMapOfConfig.put(ciaOfCfgTemp.getCode(), ciaOfCfgTemp);
            //如果数据库中不对应对应的Code的实例，则添加到需要添加的列表
            if (code2CIAMapOfDB.containsKey(ciaOfCfgTemp.getCode())) {
                needInsertList.add(ciaOfCfgTemp);
            } else if (code2CIAMapOfDB.get(ciaOfCfgTemp.getCode())
                    .isModifyAble()) {
                //如果可编辑：并且配置中存在，则添加到需要更新为不可编辑的列表
                CIA ciaOfDBTemp = code2CIAMapOfDB.get(ciaOfCfgTemp.getCode());
                ciaOfDBTemp.setValid(true);//设置为有效
                ciaOfDBTemp.setModifyAble(false);//设置为不可编辑
                needUpdateToCannotModifyAbleList.add(ciaOfDBTemp);
            }
        }
        
        for (CIA ciaOfDBTemp : listFromDB) {
            if (code2CIAMapOfConfig.containsKey(ciaOfDBTemp.getCode())) {
                //在配置中已经存在的则跳过
                continue;
            }
            //不存在的添加到可编辑的列表
            ciaOfDBTemp.setModifyAble(true);
            needUpdateModifyAbleList.add(ciaOfDBTemp);
        }
        //如果批量插入不为空
        if (!CollectionUtils.isEmpty(needInsertList)) {
            for (CIA ciaTemp : needInsertList) {
                //预制的前置插入方法
                beforeInsert(ciaTemp);
            }
            batchInsert(needInsertList);
        }
        //如果批量更新不为空
        if (!CollectionUtils.isEmpty(needUpdateToCannotModifyAbleList)) {
            for (CIA ciaTemp : needUpdateToCannotModifyAbleList) {
                //预制的前置插入方法
                beforeUpdate(ciaTemp);
            }
            batchUpdate(needUpdateToCannotModifyAbleList);
        }
        //如果批量更新不为空
        if (!CollectionUtils.isEmpty(needUpdateModifyAbleList)) {
            for (CIA ciaTemp : needUpdateModifyAbleList) {
                //预制的前置插入方法
                beforeUpdate(ciaTemp);
            }
            batchUpdate(needUpdateModifyAbleList);
        }
    }
    
    /**
     * 预制的插入前置处理<br/>
     * <功能详细描述>
     * @param needInsertCIA [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    protected void beforeUpdate(CIA needUpdateCIA) {
        AssertUtils.notEmpty(needUpdateCIA.getCode(), "CIA.code is empty.");
    }
    
    /**
      * 预制的插入前置处理<br/>
      * <功能详细描述>
      * @param needInsertCIA [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    protected void beforeInsert(CIA needInsertCIA) {
        AssertUtils.notEmpty(needInsertCIA.getCode(), "CIA.code is empty.");
        
        needInsertCIA.setValid(true);
        needInsertCIA.setModifyAble(false);
    }
    
    /**
      * 批量更新<br/>
      * <功能详细描述>
      * @param needUpdateList [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    protected abstract void batchUpdate(List<CIA> needUpdateList);
    
    /**
      * 批量插入对象列表
      * <功能详细描述>
      * @param needInsertList [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    protected abstract void batchInsert(List<CIA> needInsertList);
    
    /**
      * 从配置中获取对应的对象列表<br/>
      * <功能详细描述>
      * @return [参数说明]
      * 
      * @return List<CIA> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    protected abstract List<CIA> queryListFromConfig();
    
    /**
      * 从数据库中获取对应的实例列表<br/>
      * <功能详细描述>
      * @return [参数说明]
      * 
      * @return List<CIA> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    protected abstract List<CIA> queryListFromDB();
}
