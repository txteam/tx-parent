/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2016年10月4日
 * <修改描述:>
 */
package com.tx.component.basicdata.context;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.tx.component.basicdata.model.BasicData;
import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.util.ClassScanUtils;

/**
 * 基础数据容器构建器<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2016年10月4日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class BasicDataContextBuilder extends BasicDataContextConfigurator {
    
    /** 类型2业务逻辑层的映射关联 */
    protected Map<Class<?>, BasicDataService<?>> type2serviceMap = new HashMap<>();
    
    /**
     * @throws Exception
     */
    @SuppressWarnings("rawtypes")
    @Override
    protected final void doBuild() throws Exception {
        //查找spring容器中已经存在的业务层
        Collection<BasicDataService> basicDataServices = this.applicationContext.getBeansOfType(BasicDataService.class)
                .values();
        for (BasicDataService serviceTemp : basicDataServices) {
            this.type2serviceMap.put(serviceTemp.basicDataType(), serviceTemp);
        }
        
        //加载基础数据类<br/>
        Set<Class<? extends BasicData>> bdClassSet = new HashSet<>();
        String[] packageArray = StringUtils.splitByWholeSeparator(this.packages,
                ",");
        for (String packageTemp : packageArray) {
            if (StringUtils.isEmpty(packageTemp)) {
                continue;
            }
            Set<Class<? extends BasicData>> bdClassSetTemp = ClassScanUtils.scanByParentClass(BasicData.class,
                    packageTemp);
            bdClassSet.addAll(bdClassSetTemp);
        }
        
        //加载类与业务层的映射关联
        for(Class<? extends BasicData> bdType : bdClassSet){
            if(this.type2serviceMap.containsKey(bdType)){
                //如果已经存在对应的业务逻辑层
                continue;
            }
        }
    }
    
    /**
      * 根据基础数据类型挪去对应的基础数据处理业务层逻辑<br/>
      * <功能详细描述>
      * @param type
      * @return [参数说明]
      * 
      * @return BasicDataService<BDTYPE> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    @SuppressWarnings("unchecked")
    protected <BDTYPE extends BasicData> BasicDataService<BDTYPE> doGetBasicDataService(
            Class<BDTYPE> type) {
        AssertUtils.notNull(type, "type is null.");
        AssertUtils.isTrue(type2serviceMap.containsKey(type),
                "type handler service is not exist.type:{}",
                new Object[] { type });
        
        BasicDataService<BDTYPE> service = (BasicDataService<BDTYPE>) type2serviceMap.get(type);
        return service;
    }
}
