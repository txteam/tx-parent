/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2016年6月21日
 * <修改描述:>
 */
package com.tx.core.generator.tableview;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ClassUtils;

import com.tx.core.dbscript.model.DataSourceTypeEnum;
import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.generator.basicedata.BasicDataCodeGenerator;
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
 * <功能简述>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2016年6月21日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class TableViewCodeGenerator {
    /** 日志记录器 */
    private static Logger logger = LoggerFactory.getLogger(TableViewCodeGenerator.class);
    
    private static SqlSourceBuilder sqlSourceBuilder = new SqlSourceBuilder();
    
    private static Class<?> loadTemplateClass = BasicDataCodeGenerator.class;
    
    private static String sqlMapTemplateFilePath = "com/tx/core/generator/tableview/defaultftl/sqlMap.ftl";
    
    private static String daoTemplateFilePath = "com/tx/core/generator/tableview/defaultftl/dao.ftl";
    
    private static String daoImplTemplateFilePath = "com/tx/core/generator/tableview/defaultftl/daoImpl.ftl";
    
    private static String serviceTemplateFilePath = "com/tx/core/generator/tableview/defaultftl/service.ftl";
    
    /**
     * 生成基础数据代码<br/>
     * <功能详细描述>
     * @param basicDataType 数据库类型
     * @param dataSourceType
     * @param codeBaseFolder
     * @param uniqueGetterNames
     * @param validGetterName [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    public static <T> void generate(Class<T> basicDataType,
            DataSourceTypeEnum dataSourceType, String codeBaseFolder) {
        generate("UTF-8",
                "UTF-8",
                basicDataType,
                dataSourceType,
                codeBaseFolder,
                true);
    }
    
    /**
      * 辅助代码生成<br/>
      * <功能详细描述>
      * @param basicDataType
      * @param dataSourceType
      * @param codeBaseFolder
      * @param cleanFolder [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public static <T> void generate(Class<T> basicDataType,
            DataSourceTypeEnum dataSourceType, String codeBaseFolder,
            boolean cleanFolder) {
        generate("UTF-8",
                "UTF-8",
                basicDataType,
                dataSourceType,
                codeBaseFolder,
                cleanFolder);
    }
    
    /**
      * 生成基础数据代码<br/>
      * <功能详细描述>
      * @param basicDataType 数据库类型
      * @param dataSourceType
      * @param codeBaseFolder
      * @param uniqueGetterNames
      * @param validGetterName [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public static <T> void generate(String codeEncode, String dbEncode,
            Class<T> basicDataType, DataSourceTypeEnum dataSourceType,
            String codeBaseFolder) {
        generate(codeEncode,
                dbEncode,
                basicDataType,
                dataSourceType,
                codeBaseFolder,
                true);
    }
    
    /**
      * 生成基础数据代码<br/>
      * <功能详细描述>
      * @param basicDataType
      * @param codeEncode
      * @param dataSourceType
      * @param dbEncode [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public static <T> void generate(String codeEncode, String dbEncode,
            Class<T> basicDataType, DataSourceTypeEnum dataSourceType,
            String codeBaseFolder, boolean cleanFolder) {
        try {
            if (cleanFolder) {
                FileUtils.cleanDirectory(new File(codeBaseFolder));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        AssertUtils.notTrue(Modifier.isInterface(basicDataType.getClass()
                .getModifiers())
                || Modifier.isAbstract(basicDataType.getClass().getModifiers()),
                "指定的基础数据类型不能为接口或抽象类。class:{}",
                new Object[] { basicDataType.getClass() });
        logger.info("校验:指定类费接口，非抽象类：---------------------通过");
        JpaMetaClass<T> jpaMetaClass = JpaMetaClass.forClass(basicDataType);
        SqlSource<T> sqlSource = sqlSourceBuilder.build(basicDataType,
                dataSourceType.getDialect());
        //校验类型是否合法
        //校验对应的类不是接口或抽象类
        //校验对象是否存在可更新字段
        AssertUtils.notTrue(CollectionUtils.isEmpty(sqlSource.getUpdateAblePropertyNames()),
                "指定的基础数据类型不含有可更新的字段。class:{}",
                new Object[] { basicDataType.getClass() });
        logger.info("校验:指定类存在可更新字段：-----------------------通过");
        
        //校验对象是否存在主键设置
        AssertUtils.notEmpty(sqlSource.getPkName(),
                "指定的基础数据类型未指定主键字段。class:{}",
                new Object[] { basicDataType.getClass() });
        logger.info("校验:指定类主键设定存在：-------------------------通过");
        //TODO:校验对象属性是否合法不能有sql关键字
        Set<String> getterNames = jpaMetaClass.getGetterNames();
        AssertUtils.notTrue(getterNames.contains("order"),
                "指定对象:{}中不建议含有数据库关键字{}",
                new Object[] { basicDataType.getClass(), "order" });
        AssertUtils.notTrue(getterNames.contains("key"),
                "指定对象:{}中不建议含有数据库关键字{}",
                new Object[] { basicDataType.getClass(), "key" });
        logger.info("校验:指定类字段不含有数据库关键字：----------------通过");
        //TODO:校验设定的uniqueGetterName存在equals判断条件
        //TODO:校验设定的uniqueGetterName不是主键（允许是主键与其他字段一起的联合唯一索引）
        //生成sqlMap
        logger.info("开始生成SqlMap:");
        generateSqlMap(jpaMetaClass, sqlSource, dataSourceType, codeBaseFolder);
        //生成Dao、DaoImpl
        logger.info("开始生成Dao/DaoImpl:");
        generateDao(jpaMetaClass, sqlSource, dataSourceType, codeBaseFolder);
        //生成Service
        logger.info("开始生成Service");
        generateService(jpaMetaClass, sqlSource, dataSourceType, codeBaseFolder);
        
        String[] entityTypeNameArray = jpaMetaClass.getEntityTypeName()
                .split("\\.");
        String packageName = entityTypeNameArray[entityTypeNameArray.length - 3];
        logger.info("代码生成完毕，请按以下步骤进行操作：");
        logger.info("   \t 1、打开文件夹：{}", codeBaseFolder);
        logger.info("   \t 2、拷贝main文件夹，粘贴于src目录下。");
        logger.info("   \t 3、拷贝mysql、oracle文件夹，粘贴于dbscript目录下。");
        logger.info("   \t 4、打开生成的Service文件，处理其中存在//TODO:的逻辑");
        logger.info("   \t 5、打开生成的Controller文件，处理其中存在//TODO:的逻辑");
        logger.info("   \t 6、修改生成的数据库脚本，并运行。");
        logger.info("   \t 7、根据提示信息：添加对应菜单项。");
        logger.info("   \t 8、启动项目验证是否启动正确。");
        logger.info("   \t 9、点击对应菜单项目，进入查询页面。打开对应查询页面修改其中存在//TODO:的逻辑，并查看效果");
        logger.info("   \t 10、新增、修改、删除等操作。并调整新增、修改页面排版。");
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
        logger.info("service存放路径:{}", codeBaseFolder + "/main/java/" + basePath
                + "/service/" + jpaMetaClass.getEntitySimpleName()
                + "service.java");
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
                dataSourceType.getDialect());
        
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
        
        logger.info("dao存放路径:{}", codeBaseFolder + "/main/java/" + daoPath
                + "/" + jpaMetaClass.getEntitySimpleName() + "Dao.java");
        logger.info("daoImpl存放路径:{}", codeBaseFolder + "/main/java/" + daoPath
                + "/impl/" + jpaMetaClass.getEntitySimpleName()
                + "DaoImpl.java");
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
        SqlMapMapper mapper = new SqlMapMapper(jpaMetaClass);
        InsertMapper insert = new InsertMapper(jpaMetaClass);
        DeleteMapper delete = new DeleteMapper(jpaMetaClass);
        SelectMapper select = new SelectMapper(jpaMetaClass, sqlSource);
        UpdateMapper update = new UpdateMapper(jpaMetaClass);
        
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
}
