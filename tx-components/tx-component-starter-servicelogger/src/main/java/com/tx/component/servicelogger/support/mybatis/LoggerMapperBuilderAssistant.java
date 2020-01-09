/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2016年11月17日
 * <修改描述:>
 */
package com.tx.component.servicelogger.support.mybatis;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.Configuration;
import org.springframework.core.annotation.AnnotationUtils;

import com.tx.component.servicelogger.annotation.ServiceLog;
import com.tx.core.mybatis.assistant.BaseDaoMapperBuilderAssistant;
import com.tx.core.util.JPAParseUtils;

/**
 * Mapper构建助手扩展类<br/>
 *    该逻辑的调用，在设计上应该避免在修改statement期间出现业务调用的情况，不然可能出现不可预知的错误<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2016年11月17日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class LoggerMapperBuilderAssistant
        extends BaseDaoMapperBuilderAssistant {
    
    /** 注解信息 */
    protected ServiceLog annotation;
    
    /** <默认构造函数> */
    public LoggerMapperBuilderAssistant(Configuration configuration,
            Class<?> beanType) {
        super(configuration, beanType);
        
        //解析表名
        this.annotation = AnnotationUtils.findAnnotation(beanType,
                ServiceLog.class);
        this.tableName = StringUtils.isBlank(this.annotation.tablename())
                ? JPAParseUtils.parseTableName(beanType)
                : this.annotation.tablename().toUpperCase();
    }
    
    /**
     * 获取主键属性名列表<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return List<String> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public String getPKProperyName() {
        String propertyName = this.getPkColumn().getPropertyName();
        return propertyName;
    }
    
    /**
     * @return
     */
    @Override
    protected String getDeleteSQL() {
        return null;
    }
    
    /**
     * @return
     */
    @Override
    protected String getUpdateSQL() {
        return null;
    }
}