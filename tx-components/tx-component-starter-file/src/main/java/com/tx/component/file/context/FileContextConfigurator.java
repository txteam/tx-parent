/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2014年5月11日
 * <修改描述:>
 */
package com.tx.component.file.context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.tx.component.file.catalog.FileCatalogComposite;
import com.tx.component.file.service.FileDefinitionService;

/**
 * 文件容器配置器<br/>
 *
 * @author Administrator
 * @version [版本号, 2014年5月11日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class FileContextConfigurator
        implements InitializingBean, ApplicationContextAware, BeanNameAware {
    
    /** 日志记录器 */
    protected static Logger logger = LoggerFactory
            .getLogger(FileContextConfigurator.class);
    
    /** spring容器句柄 */
    protected static ApplicationContext applicationContext;
    
    /** beanName实例 */
    protected static String beanName;
    
    /** 配置容器所属模块 */
    protected String module;
    
    /** 文件定义业务层 */
    protected FileDefinitionService fileDefinitionService;
    
    /** 文件目录composite */
    protected FileCatalogComposite fileCatalogComposite;
    
    /**
     * @param name
     */
    @Override
    public void setBeanName(String name) {
        FileContextConfigurator.beanName = name;
    }
    
    /**
     * @param applicationContext
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        FileContextConfigurator.applicationContext = applicationContext;
    }
    
    /**
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        
        //进行容器构建
        doBuild();
        
        //初始化容器
        doInitContext();
    }
    
    /**
     * 基础数据容器构建
     * <功能详细描述>
     *
     * @return void [返回类型说明]
     * @throws Exception [参数说明]
     * @throws throws    [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    protected void doBuild() throws Exception {
    }
    
    /**
     * 初始化容器<br/>
     * <功能详细描述>
     *
     * @return void [返回类型说明]
     * @throws Exception [参数说明]
     * @throws throws    [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    protected void doInitContext() throws Exception {
    }
    
    /**
     * @param 对module进行赋值
     */
    public void setModule(String module) {
        this.module = module;
    }
    
    /**
     * @param 对fileDefinitionService进行赋值
     */
    public void setFileDefinitionService(
            FileDefinitionService fileDefinitionService) {
        this.fileDefinitionService = fileDefinitionService;
    }
    
}
