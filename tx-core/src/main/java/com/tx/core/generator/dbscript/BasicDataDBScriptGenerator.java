/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-9-22
 * <修改描述:>
 */
package com.tx.core.generator.dbscript;

import com.tx.core.dbscript.model.DataSourceTypeEnum;
import com.tx.core.generator.JpaEntityFreeMarkerGenerator;

/**
 * 业务日志数据脚本生成辅助器<br/>
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2013-9-22]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class BasicDataDBScriptGenerator {
    
    private static Class<?> loadTemplateClass = BasicDataDBScriptGenerator.class;
    
    private static String dbScriptTemplateFilePath = "com/tx/component/basicdata/generator/defaultftl/dbscript.ftl";
    
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
    public static String generateDBScriptContent(Class<?> serviceLogType,
            DataSourceTypeEnum dataSourceType, String encode) {
        JpaEntityFreeMarkerGenerator factory = new JpaEntityFreeMarkerGenerator();
        factory.setLoadTemplateClass(loadTemplateClass);
        factory.setDbScriptTemplateFilePath(dbScriptTemplateFilePath);
        
        //生成后在自己指定的文件夹中去找即可
        String script = factory.generateScriptContent(serviceLogType,
                dataSourceType,
                encode);
        
        return script;
    }
    
    /**
     * @param 对loadTemplateClass进行赋值
     */
    public static void setLoadTemplateClass(Class<?> loadTemplateClass) {
        BasicDataDBScriptGenerator.loadTemplateClass = loadTemplateClass;
    }
    
    /**
     * @param 对dbScriptTemplateFilePath进行赋值
     */
    public static void setDbScriptTemplateFilePath(
            String dbScriptTemplateFilePath) {
        BasicDataDBScriptGenerator.dbScriptTemplateFilePath = dbScriptTemplateFilePath;
    }
}
