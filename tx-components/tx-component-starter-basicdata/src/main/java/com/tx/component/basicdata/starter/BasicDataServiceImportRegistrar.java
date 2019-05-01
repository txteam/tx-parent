/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2019年4月30日
 * <修改描述:>
 */
package com.tx.component.basicdata.starter;

import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

import com.tx.component.basicdata.context.BasicDataService;
import com.tx.component.basicdata.model.BasicData;
import com.tx.component.basicdata.model.DataDict;
import com.tx.component.basicdata.service.DataDictService;

/**
 * 数据字典业务层<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2019年4月30日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Import(BasicDataPersisterConfiguration.class)
@Configuration
public class BasicDataServiceImportRegistrar
        implements ImportBeanDefinitionRegistrar, InitializingBean,
        ApplicationContextAware {
    
    /** spring容器 */
    private ApplicationContext applicationContext;
    
    /** 基础数据：数据字典业务层 */
    private DataDictService dataDictService;
    
    /**
     * @param applicationContext
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        this.applicationContext = applicationContext;
    }
    
    /**
     * @throws Exception
     */
    @Override
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void afterPropertiesSet() throws Exception {
        //查找spring容器中已经存在的业务层
        Map<String, BasicDataService> basicDataServiceMap = this.applicationContext
                .getBeansOfType(BasicDataService.class);
        
        for (Entry<String, BasicDataService> entry : basicDataServiceMap
                .entrySet()) {
            BasicDataService service = entry.getValue();
            String beanName = entry.getKey();
            if (service.type() == null
                    || DataDict.class.equals(service.type())) {
                continue;
            }
            String alias = generateServiceBeanName(service.getType());
            //注册单例Bean进入Spring容器
            //registerSingletonBean(beanName, service);
            if (!beanName.equals(alias)) {
                //registerAlise(beanName, alias);
            }
            //注册处理的业务类型
            //registeType2Service(service);
        }
    }
    
    /**
     * @param importingClassMetadata
     * @param registry
     */
    @Override
    public void registerBeanDefinitions(
            AnnotationMetadata importingClassMetadata,
            BeanDefinitionRegistry registry) {
        // TODO Auto-generated method stub
        
    }
    
    /**
     * 生成对应的业务层Bean名称<br/>
     * <功能详细描述>
     * @param type
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    private String generateServiceBeanName(Class<? extends BasicData> type) {
        String beanName = "basicdata."
                + StringUtils.uncapitalize(type.getSimpleName()) + "Service";
        return beanName;
    }
}
