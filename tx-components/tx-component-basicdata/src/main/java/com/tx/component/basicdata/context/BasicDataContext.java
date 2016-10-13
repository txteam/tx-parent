/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2016年10月4日
 * <修改描述:>
 */
package com.tx.component.basicdata.context;

import java.util.List;

import com.tx.component.basicdata.model.BasicData;
import com.tx.component.basicdata.model.TreeAbleBasicData;
import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.paged.model.PagedList;

/**
 * 基础数据容器<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2016年10月4日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class BasicDataContext extends BasicDataContextBuilder {
    
    protected static BasicDataContext context;
    
    /**
      * 获取基础数据容器实例<br/>
      * <功能详细描述>
      * @return [参数说明]
      * 
      * @return BasicDataContext [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public static BasicDataContext getContext() {
        if (BasicDataContext.context != null) {
            return BasicDataContext.context;
        }
        synchronized (BasicDataContext.class) {
            BasicDataContext.context = applicationContext.getBean(beanName,
                    BasicDataContext.class);
        }
        AssertUtils.notNull(BasicDataContext.context, "context is null.");
        return BasicDataContext.context;
    }
    
    /**
     * @throws Exception
     */
    @Override
    protected final void doInitContext() throws Exception {
        //限定容器有且只能被初始化一次
        AssertUtils.isNull(BasicDataContext.context, "context already inited.");
        
        BasicDataContext.context = this;
    }
    
    /**
      * 装载对象属性<br/>
      * <功能详细描述>
      * @param obj [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public void setup(Object object) {
        if (object == null) {
            return;
        }
        
        //装载基础数据
        BasicDataSetupHandler handler = new BasicDataSetupHandler(object,
                BasicDataContext.context);
        handler.setup();
    }
    
    public void setup(List<Object> objectList){
        return ;
    }
    
    public void setup(PagedList<Object> objectList){
        return ;
    }
    
    /**
      * 获取基础数据业务层<br/>
      * <功能详细描述>
      * @param type
      * @return [参数说明]
      * 
      * @return BasicDataService<BDTYPE> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public <BDTYPE extends BasicData> BasicDataService<BDTYPE> getBasicDataService(
            Class<BDTYPE> type) {
        BasicDataService<BDTYPE> service = doGetBasicDataService(type);
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
    public <BDTYPE extends TreeAbleBasicData<BDTYPE>> TreeAbleBasicDataService<BDTYPE> getTreeAbleBasicDataService(
            Class<BDTYPE> type) {
        TreeAbleBasicDataService<BDTYPE> service = doGetTreeAbleBasicDataService(type);
        return service;
    }
    
}
