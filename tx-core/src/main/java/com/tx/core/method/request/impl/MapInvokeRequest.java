/*
  * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2018年5月25日
 * <修改描述:>
 */
package com.tx.core.method.request.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.method.request.InvokeRequest;

/**
 * 参数注入请求<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2018年5月25日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class MapInvokeRequest implements InvokeRequest {
    
    /** 参数注入请求 */
    private MultiValueMap<String, Object> multiValueMap;
    
    /** <默认构造函数> */
    @SuppressWarnings("rawtypes")
    public MapInvokeRequest(Map<String, Object> params) {
        super();
        this.multiValueMap = new LinkedMultiValueMap<>();
        if (!MapUtils.isEmpty(params)) {
            for (Entry<String, Object> entryTemp : params.entrySet()) {
                if (entryTemp.getValue() instanceof Collection) {
                    for (Object obj : (Collection) entryTemp.getValue()) {
                        this.multiValueMap.add(entryTemp.getKey(), obj);
                    }
                } else {
                    this.multiValueMap.add(entryTemp.getKey(),
                            entryTemp.getValue());
                }
            }
        }
    }
    
    /**
     * @param paramName
     * @return
     */
    @Override
    public Object getParameter(String paramName) {
        AssertUtils.notEmpty(paramName,"paramName is empty.");
        
        return this.multiValueMap.getFirst(paramName);
    }
    
    /**
     * @param paramName
     * @param type
     * @return
     */
    @SuppressWarnings("unchecked")
    @Override
    public <T> T getParameter(String paramName, Class<T> type) {
        AssertUtils.notEmpty(paramName,"paramName is empty.");
        AssertUtils.notNull(type,"type is null.");
        
        Object obj = this.multiValueMap.getFirst(paramName);
        if(obj == null){
            return null;
        }
        AssertUtils.isTrue(type.isInstance(obj), "obj type is :{} not :{}",new Object[]{obj.getClass(),type});
        
        return (T)obj;
    }
    
    /**
     * @param paramName
     * @return
     */
    @Override
    public Object[] getParameterValues(String paramName) {
        AssertUtils.notEmpty(paramName,"paramName is empty.");
        
        List<Object> resList = this.multiValueMap.get(paramName);
        if(CollectionUtils.isEmpty(resList)){
            return null;
        }
        
        Object[] resObjects = resList.toArray(new Object[resList.size()]);
        return resObjects;
    }
    
    /**
     * @param paramName
     * @param type
     * @return
     */
    @Override
    public <T> T[] getParameterValues(String paramName, Class<T> type) {
        AssertUtils.notEmpty(paramName,"paramName is empty.");
        AssertUtils.notNull(type,"type is null.");
        
        List<Object> resList = this.multiValueMap.get(paramName);
        if(CollectionUtils.isEmpty(resList)){
            return null;
        }
        
        @SuppressWarnings("unchecked")
        T[] resObjects = (T[])resList.toArray(new Object[resList.size()]);
        return resObjects;
    }
    
    /**
     * @return
     */
    @Override
    public Iterator<String> getParameterNames() {
        return this.multiValueMap.keySet().iterator();
    }
    
    /**
     * @return
     */
    @Override
    public Map<String, Object> getParameterMap() {
        Map<String, Object> resMap = new HashMap<>();
        for(Entry<String, List<Object>> entryTemp : this.multiValueMap.entrySet()){
            resMap.put(entryTemp.getKey(), entryTemp.getValue());
        }
        return resMap;
    }
    
//    /**
//     * @return
//     */
//    @Override
//    public MultiValueMap<String, Object> getParameterMultiValueMap() {
//        return this.multiValueMap;
//    }
}
