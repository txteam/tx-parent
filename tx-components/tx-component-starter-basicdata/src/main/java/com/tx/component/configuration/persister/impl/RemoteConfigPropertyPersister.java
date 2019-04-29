///*
// * 描          述:  <描述>
// * 修  改   人:  Administrator
// * 修改时间:  2019年3月5日
// * <修改描述:>
// */
//package com.tx.component.configuration.persister.impl;
//
//import java.util.List;
//import java.util.Map;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.BeansException;
//import org.springframework.cloud.netflix.feign.FeignContext;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.ApplicationContextAware;
//import org.springframework.web.client.RestTemplate;
//
//import com.tx.component.configuration.model.ConfigProperty;
//import com.tx.component.configuration.persister.ConfigPropertyPersister;
//import com.tx.component.configuration.remote.ConfigurationRemote;
//
///**
// * 本地配置属性查询器<br/>
// * <功能详细描述>
// * 
// * @author  Administrator
// * @version  [版本号, 2019年3月5日]
// * @see  [相关类/方法]
// * @since  [产品/模块版本]
// */
//public class RemoteConfigPropertyPersister
//        implements ConfigPropertyPersister, ApplicationContextAware {
//    
//    //日志记录句柄
//    private Logger logger = LoggerFactory
//            .getLogger(RemoteConfigPropertyPersister.class);
//    
//    protected <T> T get(FeignContext context, Class<T> type) {
//        T instance = context.getInstance(this.name, type);
//        if (instance == null) {
//            throw new IllegalStateException("No bean found of type " + type + " for "
//                    + this.name);
//        }
//        return instance;
//    }
//    
//    /**
//     * @param module
//     * @return
//     */
//    @Override
//    public boolean supportsModule(String module) {
//        
//        // TODO Auto-generated method stub
//        return false;
//    }
//    
//    /**
//     * @param module
//     * @param code
//     * @return
//     */
//    @Override
//    public ConfigProperty findByCode(String module, String code) {
//        //restTemplate.getForObject(url, responseType)
//        // TODO Auto-generated method stub
//        return null;
//    }
//    
//    /**
//     * @param module
//     * @param params
//     * @return
//     */
//    @Override
//    public List<ConfigProperty> queryList(String module,
//            Map<String, Object> params) {
//        // TODO Auto-generated method stub
//        return null;
//    }
//    
//    /**
//     * @param module
//     * @param parentId
//     * @param params
//     * @return
//     */
//    @Override
//    public List<ConfigProperty> queryNestedListByParentId(String module,
//            String parentId, Map<String, Object> params) {
//        // TODO Auto-generated method stub
//        return null;
//    }
//    
//    /**
//     * @param module
//     * @param code
//     * @param value
//     * @return
//     */
//    @Override
//    public boolean update(String module, String code, String value) {
//        // TODO Auto-generated method stub
//        return false;
//    }
//    
//}
