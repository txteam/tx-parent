/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2014年2月18日
 * <修改描述:>
 */
package com.tx.component.basicdata.generator;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.hibernate.dialect.Dialect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ClassUtils;

import com.tx.core.dbscript.model.DataSourceTypeEnum;
import com.tx.core.generator.model.DBScriptMapper;
import com.tx.core.generator.model.DaoGeneratorModel;
import com.tx.core.generator.model.DeleteMapper;
import com.tx.core.generator.model.InsertMapper;
import com.tx.core.generator.model.SelectMapper;
import com.tx.core.generator.model.ServiceGeneratorModel;
import com.tx.core.generator.model.SqlMapMapper;
import com.tx.core.generator.model.UpdateMapper;
import com.tx.core.jdbc.sqlsource.SqlSource;
import com.tx.core.jdbc.sqlsource.SqlSourceBuilder;
import com.tx.core.reflection.JpaMetaClass;
import com.tx.core.util.FreeMarkerUtils;

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
    
    /** 日志记录器 */
    private static Logger logger = LoggerFactory.getLogger(BasicDataCodeGenerator.class);
    
    private static SqlSourceBuilder sqlSourceBuilder = new SqlSourceBuilder();
    
    private static Class<?> loadTemplateClass = BasicDataCodeGenerator.class;
    
    private static String dbScriptTemplateFilePath = "com/tx/component/basicdata/generator/defaultftl/basicdata_dbscript.ftl";
    
    private static String sqlMapTemplateFilePath = "com/tx/component/basicdata/generator/defaultftl/basicdata_sqlMap.ftl";
    
    private static String daoTemplateFilePath = "com/tx/component/basicdata/generator/defaultftl/basicdata_dao.ftl";
    
    private static String daoImplTemplateFilePath = "com/tx/component/basicdata/generator/defaultftl/basicdata_daoImpl.ftl";
    
    private static String serviceTemplateFilePath = "com/tx/component/basicdata/generator/defaultftl/basicdata_service.ftl";
    
    private static String serviceTestTemplateFilePath = "com/tx/component/basicdata/generator/defaultftl/basicdata_serviceTest.ftl";
    
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
            DataSourceTypeEnum dataSourceType, String codeBaseFolder,
            String[] uniqueGetterNames, String validGetterName) {
        generate(basicDataType,
                "UTF-8",
                dataSourceType,
                "GBK",
                codeBaseFolder,
                uniqueGetterNames,
                validGetterName);
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
            DataSourceTypeEnum dataSourceType, String dbEncode,
            String codeBaseFolder, String[] uniqueGetterNames,
            String validGetterName) {
        try {
            FileUtils.cleanDirectory(new File(codeBaseFolder));
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        JpaMetaClass<T> jpaMetaClass = JpaMetaClass.forClass(basicDataType);
        SqlSource<T> sqlSource = sqlSourceBuilder.build(basicDataType,
                dataSourceType.getDialect());
        
        //生成脚本
        logger.info("开始生成脚本:");
        String scriptContext = generateDBScriptContext(jpaMetaClass,
                sqlSource,
                dataSourceType,
                dbEncode);
        generateDBScript(jpaMetaClass, sqlSource, codeBaseFolder, dbEncode);
        logger.info("打印脚本:");
        System.out.println("/*------------------------------------------------------*/");
        System.out.println(scriptContext);
        System.out.println("/*------------------------------------------------------*/");
        //生成sqlMap
        logger.info("开始生成SqlMap:");
        generateSqlMap(jpaMetaClass, sqlSource, dataSourceType, codeBaseFolder);
        //生成Dao、DaoImpl
        logger.info("开始生成Dao/DaoImpl:");
        generateDao(jpaMetaClass, sqlSource, dataSourceType, codeBaseFolder);
        //生成Service
        logger.info("开始生成Service/ServiceTest:");
        generateService(jpaMetaClass, sqlSource, dataSourceType, codeBaseFolder);
        //生成Controller
        
        //生成queryPage,addPage,updatePage
    }
    
    /**
     * 生成持久层逻辑
     * <功能详细描述>
     * @param jpaMetaClass
     * @param resultFolderPath [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    public static <T> void generateService(JpaMetaClass<T> jpaMetaClass,
            SqlSource<T> sqlSource, DataSourceTypeEnum dataSourceType,
            String codeBaseFolder) {
        ServiceGeneratorModel serviceModel = new ServiceGeneratorModel(
                jpaMetaClass, sqlSource, dataSourceType.getDialect());
        
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("service", serviceModel);
        
        String basePath = ClassUtils.convertClassNameToResourcePath(jpaMetaClass.getEntityTypeName())
                + "/../..";
        basePath = org.springframework.util.StringUtils.cleanPath(basePath);
        FreeMarkerUtils.fprint(loadTemplateClass,
                serviceTemplateFilePath,
                data,
                codeBaseFolder + "/main/java/" + basePath + "/service/"
                        + jpaMetaClass.getEntitySimpleName() + "Service.java");
        FreeMarkerUtils.fprint(loadTemplateClass,
                serviceTestTemplateFilePath,
                data,
                codeBaseFolder + "/test/java/" + basePath + "/"
                        + jpaMetaClass.getEntitySimpleName()
                        + "ServiceTest.java");
    }
    
    /**
     * 生成持久层逻辑
     * <功能详细描述>
     * @param jpaMetaClass
     * @param resultFolderPath [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    public static <T> void generateDao(JpaMetaClass<T> jpaMetaClass,
            SqlSource<T> sqlSource, DataSourceTypeEnum dataSourceType,
            String codeBaseFolder) {
        DaoGeneratorModel daoModel = new DaoGeneratorModel(jpaMetaClass,
                sqlSource, dataSourceType.getDialect());
        
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("dao", daoModel);
        
        String daoPath = ClassUtils.convertClassNameToResourcePath(jpaMetaClass.getEntityTypeName())
                + "/../../dao";
        daoPath = org.springframework.util.StringUtils.cleanPath(daoPath);
        FreeMarkerUtils.fprint(loadTemplateClass,
                daoTemplateFilePath,
                data,
                codeBaseFolder + "/main/java/" + daoPath + "/"
                        + jpaMetaClass.getEntitySimpleName() + "Dao.java");
        FreeMarkerUtils.fprint(loadTemplateClass,
                daoImplTemplateFilePath,
                data,
                codeBaseFolder + "/main/java/" + daoPath + "/impl/"
                        + jpaMetaClass.getEntitySimpleName() + "DaoImpl.java");
    }
    
    /**
     * 生成sqlMap
     * <功能详细描述>
     * @param jpaMetaClass
     * @param resultFolderPath [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    public static <T> void generateSqlMap(JpaMetaClass<T> jpaMetaClass,
            SqlSource<T> sqlSource, DataSourceTypeEnum dataSourceType,
            String codeBaseFolder) {
        Dialect dialect = dataSourceType.getDialect();
        SqlMapMapper mapper = new SqlMapMapper(jpaMetaClass, sqlSource, dialect);
        InsertMapper insert = new InsertMapper(jpaMetaClass, sqlSource, dialect);
        DeleteMapper delete = new DeleteMapper(jpaMetaClass, sqlSource, dialect);
        SelectMapper select = new SelectMapper(jpaMetaClass, sqlSource, dialect);
        UpdateMapper update = new UpdateMapper(jpaMetaClass, sqlSource, dialect);
        
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("parseMessage", "");
        data.put("mapper", mapper);
        data.put("insert", insert);
        data.put("delete", delete);
        data.put("select", select);
        data.put("update", update);
        
        //org.springframework.util.StringUtils
        String sqlMapPath = ClassUtils.convertClassNameToResourcePath(jpaMetaClass.getEntityTypeName())
                + "/../../dao/impl";
        sqlMapPath = org.springframework.util.StringUtils.cleanPath(sqlMapPath);
        
        FreeMarkerUtils.fprint(loadTemplateClass,
                sqlMapTemplateFilePath,
                data,
                codeBaseFolder + "/main/java/" + sqlMapPath + "/"
                        + jpaMetaClass.getEntitySimpleName() + "SqlMap.xml");
        logger.info("sqlMap存放路径:{}", codeBaseFolder + "/main/java/"
                + sqlMapPath + "/" + jpaMetaClass.getEntitySimpleName()
                + "SqlMap.xml");
    }
    
    /**
      *<功能简述>
      *<功能详细描述>
      * @param dataSourceType
      * @param jpaMetaClass
      * @param encode
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    private static <T> void generateDBScript(JpaMetaClass<T> jpaMetaClass,
            SqlSource<T> sqlSource, String codeBaseFolder, String encode) {
        Map<String, Object> data = new HashMap<String, Object>();
        
        DBScriptMapper dbScriptMapper = new DBScriptMapper(jpaMetaClass,
                sqlSource, DataSourceTypeEnum.MySQL5InnoDBDialect.getDialect());
        data.put("dbScriptMapper", dbScriptMapper);
        
        String[] arrs = jpaMetaClass.getEntityTypeName().split("\\.");
        String packageName = arrs[arrs.length - 3];
        
        FreeMarkerUtils.fprint(loadTemplateClass,
                dbScriptTemplateFilePath,
                data,
                codeBaseFolder + "/mysql/01basisScript/" + packageName + "/"
                        + sqlSource.getTableName().toUpperCase() + ".sql");
        logger.info("mysql脚本存放路径:{}", codeBaseFolder + "/mysql/01basisScript/"
                + packageName + "/" + sqlSource.getTableName().toUpperCase()
                + ".sql");
        dbScriptMapper = new DBScriptMapper(jpaMetaClass, sqlSource,
                DataSourceTypeEnum.ORACLE10G.getDialect());
        data.put("dbScriptMapper", dbScriptMapper);
        FreeMarkerUtils.fprint(loadTemplateClass,
                dbScriptTemplateFilePath,
                data,
                codeBaseFolder + "/oracle/01basisScript/" + packageName + "/"
                        + sqlSource.getTableName().toUpperCase() + ".sql");
        logger.info("oracle脚本存放路径:{}", codeBaseFolder
                + "/oracle/01basisScript/" + packageName + "/"
                + sqlSource.getTableName().toUpperCase() + ".sql");
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
    private static <T> String generateDBScriptContext(
            JpaMetaClass<T> jpaMetaClass, SqlSource<T> sqlSource,
            DataSourceTypeEnum dataSourceType, String encode) {
        Dialect dialect = dataSourceType.getDialect();
        
        Map<String, Object> data = new HashMap<String, Object>();
        
        DBScriptMapper dbScriptMapper = new DBScriptMapper(jpaMetaClass,
                sqlSource, dialect);
        data.put("dbScriptMapper", dbScriptMapper);
        
        String content = FreeMarkerUtils.generateContent(loadTemplateClass,
                dbScriptTemplateFilePath,
                data,
                encode);
        return content;
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
