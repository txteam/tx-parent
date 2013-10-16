/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-10-12
 * <修改描述:>
 */
package com.tx.component.template.context;

import java.lang.reflect.Proxy;

import org.springframework.beans.factory.InitializingBean;

import com.tx.component.template.dao.impl.TemplateDataDaoImpl;
import com.tx.component.template.service.TemplateDataService;
import com.tx.component.template.service.TemplateTableService;

/**
 * 模板引擎<br/>
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2013-10-12]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class TemplateEngine extends TempalteEngineConfigurator implements
        InitializingBean {
    
    private TemplateDataService templateDataService;
    
    /**
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        TemplateDataDaoImpl templateDataDaoImpl = new TemplateDataDaoImpl(
                this.getDataSource(), this.getSystemId(),
                this.getDataSourceType());
        
        this.templateDataService = new com.tx.component.template.service.impl.TemplateDataService(
                null, templateDataDaoImpl);
        
    }
    
    public TemplateTableService getTemplateTableService() {
        return null;
    }
    
    public TemplateDataService getTemplateDataService() {
        return null;
    }
}
