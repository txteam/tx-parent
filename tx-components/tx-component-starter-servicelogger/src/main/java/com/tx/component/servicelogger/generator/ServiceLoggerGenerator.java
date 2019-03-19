/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-10-8
 * <修改描述:>
 */
package com.tx.component.servicelogger.generator;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.util.ClassUtils;

import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.reflection.JpaMetaClass;
import com.tx.core.util.FreeMarkerUtils;
import com.tx.core.util.dialect.DataSourceTypeEnum;

/**
 * 业务日志视图逻辑生成器
 * 
 * @author brady
 * @version [版本号, 2013-10-8]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class ServiceLoggerGenerator {
    
    private static Class<?> loadTemplateClass = ServiceLoggerGenerator.class;
    
    private static String controllerTemplateFilePath = "com/tx/component/servicelogger/generator/serviceLoggerController.ftl";
    
    private static String jspTemplateFilePath = "com/tx/component/servicelogger/generator/queryServiceLoggerPagedList.ftl";
    
    /**
     * 生成对应日志对象的脚本
     * 
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
        
        //JPAParseUtils.pa
        JpaMetaClass<?> jpaMetaClass = JpaMetaClass.forClass(serviceLogType);
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("jpaMetaClass", jpaMetaClass);
        
        Field[] fields = serviceLogType.getDeclaredFields();
        List<String> classTopGetters = new ArrayList<String>();
        if (fields != null) {
            Set<String> getterNames = jpaMetaClass.getGetterNames();
            for (Field fieldTemp : fields) {
                String fieldName = fieldTemp.getName();
                if (getterNames.contains(fieldName)) {
                    classTopGetters.add(fieldName);
                }
            }
        }
        params.put("classTopGetters", classTopGetters);
        
        //生成controller
        generateController(resultFolderPath, jpaMetaClass, params);
        //生成jsp
        generateQueryJSP(resultFolderPath, jpaMetaClass, params);
    }
    
    /**
     * 生成查询jsp页面
     * 
     * @param resultFolderPath
     * @param jpaMetaClass
     * @param params [参数说明]
     *            
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    private static void generateQueryJSP(String resultFolderPath,
            JpaMetaClass<?> jpaMetaClass, Map<String, Object> params) {
        String outFilePath = resultFolderPath + "/main/webapp/WEB-INF/view/"
                + jpaMetaClass.getModulePackageSimpleName() + "/query"
                + jpaMetaClass.getEntitySimpleName() + "PagedList.jsp";
        
        FreeMarkerUtils.fprint(loadTemplateClass,
                jspTemplateFilePath,
                params,
                outFilePath,
                "UTF-8");
    }
    
    /**
     * controller生成器
     * 
     * @param resultFolderPath
     * @param jpaMetaClass
     * @param params [参数说明]
     *            
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    private static void generateController(String resultFolderPath,
            JpaMetaClass<?> jpaMetaClass, Map<String, Object> params) {
        String filePath = ClassUtils.convertClassNameToResourcePath(
                jpaMetaClass.getEntityTypeName()) + "/../servicelog/controller";
        filePath = org.springframework.util.StringUtils.cleanPath(filePath);
        String outFilePath = resultFolderPath + "/main/java/" + filePath + "/"
                + jpaMetaClass.getEntitySimpleName() + "Controller.java";
        
        FreeMarkerUtils.fprint(loadTemplateClass,
                controllerTemplateFilePath,
                params,
                outFilePath,
                "UTF-8");
    }
    
    /**
     * 校验业务日志类定义<br/>
     * 
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
    
    public static void setLoadTemplateClass(Class<?> loadTemplateClass) {
        ServiceLoggerGenerator.loadTemplateClass = loadTemplateClass;
    }
    
    public static void setControllerTemplateFilePath(
            String controllerTemplateFilePath) {
        ServiceLoggerGenerator.controllerTemplateFilePath = controllerTemplateFilePath;
    }
    
    public static void setJspTemplateFilePath(String jspTemplateFilePath) {
        ServiceLoggerGenerator.jspTemplateFilePath = jspTemplateFilePath;
    }
}
