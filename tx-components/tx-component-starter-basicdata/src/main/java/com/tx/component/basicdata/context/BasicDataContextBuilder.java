/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2016年10月4日
 * <修改描述:>
 */
package com.tx.component.basicdata.context;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.BeanNameAware;

import com.tx.component.basicdata.model.BasicData;
import com.tx.component.basicdata.model.TreeAbleBasicData;
import com.tx.component.basicdata.service.BasicDataService;
import com.tx.component.basicdata.service.TreeAbleBasicDataService;
import com.tx.core.exceptions.util.AssertUtils;

/**
 * 基础数据容器构建器<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2016年10月4日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public abstract class BasicDataContextBuilder extends BasicDataContextConfigurator
        implements BeanNameAware {
    
    /** beanName实例 */
    protected static String beanName;
    
    /** 类型2业务逻辑层的映射关联 */
    protected Map<Class<?>, BasicDataService<?>> type2serviceMap = new HashMap<>();
    
    /**
     * @param name
     */
    @Override
    public void setBeanName(String name) {
        BasicDataContextBuilder.beanName = name;
    }
    
    /**
     * @throws Exception
     */
    @Override
    protected void doBuild() throws Exception {
        //加载基础数据类<br/>
    }
    
    /**
     * 根据类型获取对应的基础数据业务层<br/>
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
        AssertUtils.isTrue(BasicData.class.isAssignableFrom(type),
                "type:{} is not assign from BasicData.",
                new Object[] { type });
        
        BasicDataService<BDTYPE> service = (BasicDataService<BDTYPE>) this.basicDataEntityRegistry
                .getService(type);
        return service;
    }
    
    /**
     * 根据类型获取对应的基础数据业务层<br/>
     * <功能详细描述>
     * @param type
     * @return [参数说明]
     * 
     * @return BasicDataService<BDTYPE> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @SuppressWarnings("unchecked")
    protected <BDTYPE extends TreeAbleBasicData<BDTYPE>> TreeAbleBasicDataService<BDTYPE> doGetTreeAbleBasicDataService(
            Class<BDTYPE> type) {
        AssertUtils.notNull(type, "type is null.");
        AssertUtils.isTrue(TreeAbleBasicData.class.isAssignableFrom(type),
                "type:{} is not assign from BasicData.",
                new Object[] { type });
        
        TreeAbleBasicDataService<BDTYPE> service = (TreeAbleBasicDataService<BDTYPE>) this.basicDataEntityRegistry
                .getService(type);
        return service;
    }
}
