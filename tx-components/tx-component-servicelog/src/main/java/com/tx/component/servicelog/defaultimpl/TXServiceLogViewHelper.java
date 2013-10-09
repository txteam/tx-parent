/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-10-8
 * <修改描述:>
 */
package com.tx.component.servicelog.defaultimpl;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

import org.springframework.util.ClassUtils;

import com.tx.core.dbscript.model.DataSourceTypeEnum;
import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.reflection.JpaMetaClass;
import com.tx.core.util.FreeMarkerUtils;

/**
 * 业务日志视图逻辑生成器
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2013-10-8]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class TXServiceLogViewHelper {
    
    private static Class<?> loadTemplateClass = TXServiceLogDBScriptHelper.class;
    
    private static String controllerTemplateFilePath = "com/tx/component/servicelog/defaultimpl/serviceLogController.ftl";
    
    /**
     * 生成对应日志对象的脚本
     *<功能详细描述>
     * @param serviceLogType
     * @param dataSourceType
     * @param encode
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    public static void generate(String resultFolderPath,
            Class<?> serviceLogType, DataSourceTypeEnum dataSourceType,
            String encode) {
        //校验业务日志类型:是否具有无参构造函数
        checkServiceLogType(serviceLogType);
        
        JpaMetaClass<?> jpaMetaClass = JpaMetaClass.forClass(serviceLogType);
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("jpaMetaClass", jpaMetaClass);
        
        String filePath = ClassUtils.convertClassNameToResourcePath(jpaMetaClass.getEntityTypeName())
                + "/../servicelog/controller";
        filePath = org.springframework.util.StringUtils.cleanPath(filePath);
        String outFilePath = resultFolderPath + "/main/java/" + filePath + "/"
                + jpaMetaClass.getEntitySimpleName() + ".java";
        
        FreeMarkerUtils.fprint(loadTemplateClass,
                controllerTemplateFilePath,
                params,
                outFilePath,
                "UTF-8");
    }
    
    /** 
     * 校验业务日志类定义<br/>
     *<功能详细描述>
     * @param serviceLogType [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    private static void checkServiceLogType(Class<?> serviceLogType) {
        Constructor<?> constructor = null;
        try {
            constructor = serviceLogType.getConstructor();
        } catch (SecurityException e) {
            AssertUtils.isTrue(false,
                    "业务日志类：{},必须具有一个无参构造函数",
                    new Object[] { serviceLogType });
        } catch (NoSuchMethodException e) {
            AssertUtils.isTrue(false,
                    "业务日志类：{},必须具有一个无参构造函数",
                    new Object[] { serviceLogType });
        }
        AssertUtils.notNull(constructor,
                "业务日志类：{},必须具有一个无参构造函数",
                new Object[] { serviceLogType });
    }
}
