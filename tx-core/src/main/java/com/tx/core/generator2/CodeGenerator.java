/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2019年5月27日
 * <修改描述:>
 */
package com.tx.core.generator2;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ClassUtils;

import com.tx.core.generator2.model.ControllerGeneratorModel;
import com.tx.core.generator2.model.DBScriptGeneratorModel;
import com.tx.core.generator2.model.DaoGeneratorModel;
import com.tx.core.generator2.model.ServiceGeneratorModel;
import com.tx.core.generator2.model.SqlMapGeneratorModel;
import com.tx.core.generator2.model.ViewTypeEnum;
import com.tx.core.util.FreeMarkerUtils;
import com.tx.core.util.MessageUtils;
import com.tx.core.util.dialect.DataSourceTypeEnum;

/**
 * <功能简述>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2019年5月27日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class CodeGenerator {
    
    /** 日志记录器 */
    private static Logger logger = LoggerFactory.getLogger(CodeGenerator.class);
    
    /** 加载模板类 */
    private final static Class<?> LOAD_TEMPLATE_CLASS = CodeGenerator.class;
    
    /** 编码目录 */
    public static String BASE_CODE_FOLDER = "d:/generator/code";
    
    /** 生成代码的编码 */
    public static String DEFAULT_ENCODE = "UTF-8";
    
    /** 数据源类型 */
    public static DataSourceTypeEnum DATASOURCE_TYPE = DataSourceTypeEnum.MYSQL;
    
    /** 是否需要确认当文件已存在时 */
    public static boolean NEED_CONFIRM_WHEN_EXSITS = true;
    
    /** 脚本模板文件路径 */
    private static String dbScriptTemplateFilePath = "com/tx/core/generator2/defaultftl/dbscript.ftl";
    
    private static String sqlMapTemplateFilePath = "com/tx/core/generator2/defaultftl/sqlMap.ftl";
    
    private static String daoImplTemplateFilePath = "com/tx/core/generator2/defaultftl/daoImpl.ftl";
    
    private static String daoTemplateFilePath = "com/tx/core/generator2/defaultftl/dao.ftl";
    
    private static String serviceTemplateFilePath = "com/tx/core/generator2/defaultftl/service.ftl";
    
    private static String controllerTemplateFilePath = "com/tx/core/generator2/defaultftl/controller.ftl";
    
    private static String apicontrollerTemplateFilePath = "com/tx/core/generator2/defaultftl/apicontroller.ftl";
    
    private static String facadeTemplateFilePath = "com/tx/core/generator2/defaultftl/facade.ftl";
    
    /**
     * 生成控制层逻辑<br/>
     * <功能详细描述>
     * @param entityType [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static <T> void generateController(Class<?> entityType) {
        generateController(entityType, ViewTypeEnum.LIST);
    }
    
    /**
     * 生成控制层逻辑
     * <功能详细描述>
     * @param jpaMetaClass
     * @param resultFolderPath [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static <T> void generateController(Class<?> entityType,
            ViewTypeEnum viewType) {
        ControllerGeneratorModel controllerModel = new ControllerGeneratorModel(
                entityType, viewType);
        
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("controller", controllerModel);
        data.put("packageName", getPackageName(entityType));
        
        String basePath = ClassUtils.convertClassNameToResourcePath(
                entityType.getName()) + "/../..";
        basePath = org.springframework.util.StringUtils.cleanPath(basePath);
        
        String controllerFilePath = BASE_CODE_FOLDER + "/src/main/java/"
                + basePath + "/controller/" + entityType.getSimpleName()
                + "Controller.java";
        FreeMarkerUtils.fprint(LOAD_TEMPLATE_CLASS,
                controllerTemplateFilePath,
                data,
                controllerFilePath);
        logger.info("controller存放路径:{}", controllerFilePath);
        
        String apicontrollerFilePath = BASE_CODE_FOLDER + "/src/main/java/"
                + basePath + "/controller/" + entityType.getSimpleName()
                + "APIController.java";
        FreeMarkerUtils.fprint(LOAD_TEMPLATE_CLASS,
                apicontrollerTemplateFilePath,
                data,
                apicontrollerFilePath);
        logger.info("apicontroller存放路径:{}", apicontrollerFilePath);
        
        String facadeFilePath = BASE_CODE_FOLDER + "/src/main/java/" + basePath
                + "/facade/" + entityType.getSimpleName() + "Facade.java";
        logger.info("facade存放路径:{}", facadeFilePath);
        
        if (isOverwrite(facadeFilePath)) {
            FreeMarkerUtils.fprint(LOAD_TEMPLATE_CLASS,
                    facadeTemplateFilePath,
                    data,
                    facadeFilePath);
        }
    }
    
    /**
     * 生成业务层逻辑
     * <功能详细描述>
     * @param jpaMetaClass
     * @param resultFolderPath [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static <T> void generateService(Class<?> entityType) {
        ServiceGeneratorModel serviceModel = new ServiceGeneratorModel(
                entityType);
        
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("service", serviceModel);
        
        String servicePath = ClassUtils.convertClassNameToResourcePath(
                entityType.getName()) + "/../../service";
        servicePath = org.springframework.util.StringUtils
                .cleanPath(servicePath);
        String serviceFilePath = BASE_CODE_FOLDER + "/src/main/java/"
                + servicePath + "/" + entityType.getSimpleName()
                + "Service.java";
        
        logger.info("service存放路径:{}", serviceFilePath);
        if (isOverwrite(serviceFilePath)) {
            FreeMarkerUtils.fprint(LOAD_TEMPLATE_CLASS,
                    serviceTemplateFilePath,
                    data,
                    serviceFilePath);
        }
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
    public static <T> void generateDao(Class<?> entityType) {
        DaoGeneratorModel daoModel = new DaoGeneratorModel(entityType);
        
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("dao", daoModel);
        
        String daoPath = ClassUtils.convertClassNameToResourcePath(
                entityType.getName()) + "/../../dao";
        daoPath = org.springframework.util.StringUtils.cleanPath(daoPath);
        
        String daoFilePath = BASE_CODE_FOLDER + "/src/main/java/" + daoPath
                + "/" + entityType.getSimpleName() + "Dao.java";
        String daoImplFilePath = BASE_CODE_FOLDER + "/src/main/java/" + daoPath
                + "/impl/" + entityType.getSimpleName() + "DaoImpl.java";
        
        logger.info("dao存放路径:{}", daoFilePath);
        if (isOverwrite(daoFilePath)) {
            FreeMarkerUtils.fprint(LOAD_TEMPLATE_CLASS,
                    daoTemplateFilePath,
                    data,
                    daoFilePath);
        }
        
        logger.info("daoImpl存放路径:{}", daoImplFilePath);
        if (isOverwrite(daoImplFilePath)) {
            FreeMarkerUtils.fprint(LOAD_TEMPLATE_CLASS,
                    daoImplTemplateFilePath,
                    data,
                    daoImplFilePath);
        }
    }
    
    /**
     * 生成SqlMap
     * <功能详细描述>
     * @param jpaMetaClass
     * @param resultFolderPath [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static <T> void generateSqlMap(Class<?> entityType) {
        SqlMapGeneratorModel sqlmap = new SqlMapGeneratorModel(entityType);
        
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("sqlmap", sqlmap);
        
        String sqlmapPath = ClassUtils.convertClassNameToResourcePath(
                entityType.getName()) + "/../../dao/impl";
        sqlmapPath = org.springframework.util.StringUtils.cleanPath(sqlmapPath);
        
        String sqlmapFilePath = BASE_CODE_FOLDER + "/src/main/java/"
                + sqlmapPath + "/" + entityType.getSimpleName() + "SqlMap.xml";
        
        logger.info("sqlmap存放路径:{}", sqlmapPath);
        
        if (isOverwrite(sqlmapFilePath)) {
            FreeMarkerUtils.fprint(LOAD_TEMPLATE_CLASS,
                    sqlMapTemplateFilePath,
                    data,
                    sqlmapFilePath);
        }
    }
    
    /**
     * 生成脚本<br/>
     * <功能详细描述>
     * @param entityType [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static <T> void generateDBScript(Class<?> entityType) {
        Map<String, Object> data = new HashMap<String, Object>();
        
        //数据脚本映射
        DBScriptGeneratorModel dbScript = new DBScriptGeneratorModel(entityType,
                DATASOURCE_TYPE.getDialect4DDL());
        data.put("dbScript", dbScript);
        
        //代码生成路径生成
        String[] arrs = entityType.getName().split("\\.");
        String packageName = arrs[arrs.length - 3];
        String dbScriptPath = BASE_CODE_FOLDER + "/dbscript/"
                + DATASOURCE_TYPE.toString().toLowerCase() + "/01basisScript/"
                + packageName + "/tables/"
                + dbScript.getTableName().toLowerCase() + ".sql";
        
        logger.info("mysql脚本存放路径:{}", dbScriptPath);
        
        if (isOverwrite(dbScriptPath)) {
            FreeMarkerUtils.fprint(LOAD_TEMPLATE_CLASS,
                    dbScriptTemplateFilePath,
                    data,
                    dbScriptPath);
        }
        
    }
    
    /**
     * 获取包名<br/>
     * <功能详细描述>
     * @param entityType
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    private static String getPackageName(Class<?> entityType) {
        String servicePath = ClassUtils.convertClassNameToResourcePath(
                entityType.getName()) + "/../../";
        servicePath = org.springframework.util.StringUtils
                .cleanPath(servicePath);
        String[] servicePaths = StringUtils.split(servicePath, "/");
        String packageName = servicePaths[servicePaths.length - 1];
        return packageName;
    }
    
    /**
     * 确认文件是否存在<br/>
     * <功能详细描述>
     * @param filePath
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static boolean isOverwrite(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            return true;
        }
        if (NEED_CONFIRM_WHEN_EXSITS) {
            int flag = JOptionPane.showConfirmDialog(null,
                    MessageUtils.format("文件:[{}]已存在,是否确认覆盖?", filePath),
                    "提示",
                    JOptionPane.YES_NO_OPTION);
            return flag == 0;
        } else {
            return false;
        }
    }
    
    //    public static void main(String[] args) {
    //         (null,
    //                MessageUtils.format("文件:[{}]已存在,是否确认覆盖?", "...test..."),
    //                "提示",
    //                JOptionPane.YES_NO_OPTION);
    //    }
}
