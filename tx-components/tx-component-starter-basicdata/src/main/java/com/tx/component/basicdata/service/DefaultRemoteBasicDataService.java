///*
// * 描          述:  <描述>
// * 修  改   人:  Administrator
// * 修改时间:  2016年10月7日
// * <修改描述:>
// */
//package com.tx.component.basicdata.service;
//
//import java.util.List;
//import java.util.Map;
//
//import org.apache.commons.collections4.CollectionUtils;
//
//import com.tx.component.basicdata.client.BasicDataAPIClient;
//import com.tx.component.basicdata.context.AbstractBasicDataService;
//import com.tx.component.basicdata.model.BasicData;
//import com.tx.core.exceptions.util.AssertUtils;
//import com.tx.core.paged.model.PagedList;
//
///**
// * 默认的基础数据业务层实现<br/>
// * <功能详细描述>
// * 
// * @author  Administrator
// * @version  [版本号, 2016年10月7日]
// * @see  [相关类/方法]
// * @since  [产品/模块版本]
// */
//public class DefaultRemoteBasicDataService<T extends BasicData>
//        extends AbstractBasicDataService<T> {
//    
//    private Class<T> type;
//    
//    /** 基础数据远程调用客户端 */
//    private BasicDataAPIClient client;
//    
//    /** <默认构造函数> */
//    public DefaultRemoteBasicDataService() {
//        super();
//    }
//    
//    /**
//     * @return
//     */
//    @Override
//    public Class<T> getType() {
//        return this.type;
//    }
//    
//    /**
//     * @param data
//     */
//    @Override
//    public void insert(T object) {
//        AssertUtils.notNull(object, "object is null.");
//        
//        String type = type();
//        AssertUtils.notEmpty(type, "type is empty.");
//        
//        //构建字典对象
//        this.client.insert(type, object);
//    }
//    
//    /**
//     * @param id
//     * @return
//     */
//    @Override
//    public boolean deleteById(String id) {
//        AssertUtils.notEmpty(id, "id is null.");
//        
//        String type = type();
//        AssertUtils.notEmpty(type, "type is empty.");
//        
//        boolean flag = this.client.deleteById(type, id);
//        return flag;
//    }
//    
//    /**
//     * @param basicDataTypeCode
//     * @param code
//     * @return
//     */
//    @Override
//    public boolean deleteByCode(String code) {
//        AssertUtils.notEmpty(code, "code is null.");
//        
//        String type = type();
//        AssertUtils.notEmpty(type, "type is empty.");
//        
//        boolean flag = this.client.deleteByCode(type, code);
//        return flag;
//    }
//    
//    /**
//     * @param id
//     * @return
//     */
//    @Override
//    public T findById(String id) {
//        AssertUtils.notEmpty(id, "id is null.");
//        
//        String type = type();
//        AssertUtils.notEmpty(type, "type is empty.");
//        
//        T res = this.client.findById(type, id);
//        return res;
//    }
//    
//    /**
//     * @param code
//     * @return
//     */
//    @Override
//    public T findByCode(String code) {
//        AssertUtils.notEmpty(code, "code is null.");
//        
//        String type = type();
//        AssertUtils.notEmpty(type, "type is empty.");
//        
//        //查询基础数据详情实例
//        T res = this.client.findByCode(type, code);
//        return res;
//    }
//    
//    /**
//     * @param valid
//     * @param params
//     * @return
//     */
//    @Override
//    public List<T> queryList(Boolean valid, Map<String, Object> params) {
//        String type = type();
//        AssertUtils.notEmpty(type, "type is empty.");
//        
//        List<T> resList = this.client.queryList(type, valid, params);
//        return resList;
//    }
//    
//    /**
//     * @param valid
//     * @param params
//     * @param pageIndex
//     * @param pageSize
//     * @return
//     */
//    @Override
//    public PagedList<T> queryPagedList(Boolean valid,
//            Map<String, Object> params, int pageIndex, int pageSize) {
//        String type = type();
//        AssertUtils.notEmpty(type, "type is empty.");
//        
//        PagedList<T> resPageList = this.client.queryPagedList(type,
//                valid,
//                params,
//                pageIndex,
//                pageSize);
//        return resPageList;
//    }
//    
//    /**
//     * 判断数据是否存在<br/>
//     * @param key2valueMap
//     * @param excludeId
//     * @return
//     */
//    @Override
//    public boolean isExist(Map<String, String> key2valueMap, String excludeId) {
//        AssertUtils.notEmpty(key2valueMap, "key2valueMap is null.");
//        
//        String type = type();
//        AssertUtils.notEmpty(type, "type is empty.");
//        
//        boolean flag = this.client.isExist(type, key2valueMap, excludeId);
//        return flag;
//        
//    }
//    
//    /**
//     * @param data
//     * @return
//     */
//    @Override
//    public boolean updateById(T data) {
//        AssertUtils.notNull(data, "data is null.");
//        
//        String type = type();
//        AssertUtils.notEmpty(type, "type is empty.");
//        
//        boolean flag = this.client.updateById(type, data);
//        return flag;
//    }
//    
//    /**
//     * @param dataList
//     */
//    @Override
//    public void batchInsert(List<T> dataList) {
//        if (CollectionUtils.isEmpty(dataList)) {
//            return;
//        }
//        
//        String type = type();
//        AssertUtils.notEmpty(type, "type is empty.");
//        
//        this.client.batchInsert(type, dataList);
//    }
//    
//    /**
//     * @param dataList
//     */
//    @Override
//    public void batchUpdate(List<T> dataList) {
//        if (CollectionUtils.isEmpty(dataList)) {
//            return;
//        }
//        
//        String type = type();
//        AssertUtils.notEmpty(type, "type is empty.");
//        
//        this.client.batchUpdate(type, dataList);
//    }
//    
//    /**
//     * @param id
//     * @return
//     */
//    @Override
//    public boolean disableById(String id) {
//        AssertUtils.notEmpty(id, "id is empty.");
//        
//        String type = type();
//        AssertUtils.notEmpty(type, "type is empty.");
//        
//        boolean flag = this.client.disableById(type(), id);
//        return flag;
//    }
//    
//    /**
//     * @param id
//     * @return
//     */
//    @Override
//    public boolean enableById(String id) {
//        AssertUtils.notEmpty(id, "id is empty.");
//        
//        String type = type();
//        AssertUtils.notEmpty(type, "type is empty.");
//        
//        boolean flag = this.client.enableById(type(), id);
//        return flag;
//    }
//    
//    /**
//     * @param 对type进行赋值
//     */
//    public void setType(Class<T> type) {
//        this.type = type;
//    }
//    
//    /**
//     * @param 对client进行赋值
//     */
//    public void setClient(BasicDataAPIClient client) {
//        this.client = client;
//    }
//}
