/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2014年2月18日
 * <修改描述:>
 */
package com.tx.component.basicdata.generator;

import java.util.HashMap;
import java.util.Map;

import com.tx.core.dbscript.model.DataSourceTypeEnum;
import com.tx.core.generator.JpaEntityFreeMarkerGenerator;
import com.tx.core.jdbc.sqlsource.SqlSource;
import com.tx.core.jdbc.sqlsource.SqlSourceBuilder;

/**
 * 基础数据代码自动生成器<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2014年2月18日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class BasicDataCodeGenerator {
    
    private static SqlSourceBuilder sqlSourceBuilder = new SqlSourceBuilder();
    
    private static Class<?> loadTemplateClass = BasicDataCodeGenerator.class;
    
    private static String dbScriptTemplateFilePath = "com/tx/component/basicdata/generator/dbscript.ftl";
    
    /**
      * 生成基础数据代码<br/>
      *<功能详细描述>
      * @param basicDataType
      * @param dataSourceType [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public static void generate(Class<?> basicDataType,
            DataSourceTypeEnum dataSourceType) {
        generate(basicDataType, "UTF-8", dataSourceType, "GBK");
    }
    
    /**
      * 生成基础数据代码<br/>
      *<功能详细描述>
      * @param basicDataType
      * @param codeEncode
      * @param dataSourceType
      * @param dbEncode [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public static <T> void generate(Class<T> basicDataType, String codeEncode,
            DataSourceTypeEnum dataSourceType, String dbEncode) {
        SqlSource<T> sqlSource = sqlSourceBuilder.build(basicDataType,
                dataSourceType.getDialect());
        
        Map<String, Object> buildData = new HashMap<String, Object>();
        buildData.put("typeSimpleName", basicDataType.getSimpleName());
        buildData.put("typeName", basicDataType.getName());
        //StringUtils.uncapitalise(str)
        
        //生成脚本
        generateDBScriptContent(basicDataType, dataSourceType, dbEncode);
    }
    
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
    private static String generateDBScriptContent(Class<?> basicDataType,
            DataSourceTypeEnum dataSourceType, String encode) {
        JpaEntityFreeMarkerGenerator factory = new JpaEntityFreeMarkerGenerator();
        factory.setLoadTemplateClass(loadTemplateClass);
        factory.setDbScriptTemplateFilePath(dbScriptTemplateFilePath);
        
        //生成后在自己指定的文件夹中去找即可
        String script = factory.generateScriptContent(basicDataType,
                dataSourceType,
                encode);
        
        return script;
    }
    
    /**
     * @param 对loadTemplateClass进行赋值
     */
    public static void setLoadTemplateClass(Class<?> loadTemplateClass) {
        BasicDataCodeGenerator.loadTemplateClass = loadTemplateClass;
    }
    
    /**
     * @param 对dbScriptTemplateFilePath进行赋值
     */
    public static void setDbScriptTemplateFilePath(
            String dbScriptTemplateFilePath) {
        BasicDataCodeGenerator.dbScriptTemplateFilePath = dbScriptTemplateFilePath;
    }
}
