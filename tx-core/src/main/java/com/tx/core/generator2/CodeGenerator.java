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
import com.tx.core.generator2.model.ValidateExpressionGenerator;
import com.tx.core.generator2.model.ViewGeneratorModel;
import com.tx.core.generator2.model.ViewTypeEnum;
import com.tx.core.generator2.util.GeneratorUtils.EntityProperty;
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
    public static String dbScriptTemplateFilePath = "com/tx/core/generator2/defaultftl/dbscript.ftl";
    
    public static String sqlMapTemplateFilePath = "com/tx/core/generator2/defaultftl/sqlMap.ftl";
    
    public static String daoImplTemplateFilePath = "com/tx/core/generator2/defaultftl/daoImpl.ftl";
    
    public static String daoTemplateFilePath = "com/tx/core/generator2/defaultftl/dao.ftl";
    
    public static String serviceTemplateFilePath = "com/tx/core/generator2/defaultftl/service.ftl";
    
    public static String controllerTemplateFilePath = "com/tx/core/generator2/defaultftl/controller.ftl";
    
    public static String apicontrollerTemplateFilePath = "com/tx/core/generator2/defaultftl/apicontroller.ftl";
    
    public static String facadeTemplateFilePath = "com/tx/core/generator2/defaultftl/facade.ftl";
    
    public static String viewQueryListTemplateFilePath = "com/tx/core/generator2/defaultftl/html_easyui_queryList.ftl";
    
    public static String viewQueryTreeListTemplateFilePath = "com/tx/core/generator2/defaultftl/html_easyui_queryTreeList.ftl";
    
    public static String viewQueryPagedListTemplateFilePath = "com/tx/core/generator2/defaultftl/html_easyui_queryPagedList.ftl";
    
    public static String viewAddTemplateFilePath = "com/tx/core/generator2/defaultftl/html_easyui_add.ftl";
    
    public static String viewUpdateTemplateFilePath = "com/tx/core/generator2/defaultftl/html_easyui_update.ftl";
    
    /** easyui的验证表达式生成器 */
    private final static ValidateExpressionGenerator HTML_EASYUI_VALIDATE_EXPRESSION_GENERATOR = new ValidateExpressionGenerator() {
        @Override
        public void generate(EntityProperty property, Class<?> entityType) {
            String validateExpression = null;
            if (CharSequence.class
                    .isAssignableFrom(property.getPropertyType())) {
                if ("code".equals(property.getPropertyName())) {
                    //'编码:required;length[2~16];remote[' + @{/virtualCenter/validate} + ', name, id]'
                    validateExpression = "th:data-rule=\"'编码:required;length[1~"
                            + (int) (property.getLength() / 2)
                            + "];remote[' + @{/"
                            + StringUtils
                                    .uncapitalize(entityType.getSimpleName())
                            + "/validate} + ', code, id];'\"";
                } else if ("name".equals(property.getPropertyName())) {
                    //'名称:required;length[2~16];remote[' + @{/virtualCenter/validate} + ', name, id]'
                    validateExpression = "th:data-rule=\"'名称:required;length[1~"
                            + (int) (property.getLength() / 2)
                            + "];remote[' + @{/"
                            + StringUtils
                                    .uncapitalize(entityType.getSimpleName())
                            + "/validate} + ', name, id];'\"";
                } else if (!property.isNullable()) {
                    //名称:required;length[2~16];
                    validateExpression = "data-rule=\""
                            + property.getPropertyComment()
                            + ":required;length[1~"
                            + (int) (property.getLength() / 2) + "];\"";
                } else {
                    //名称:required;length[2~16];
                    validateExpression = "data-rule=\""
                            + property.getPropertyComment() + ":length[0~"
                            + (int) (property.getLength() / 2) + "];\"";
                }
            } else {
                if (!property.isNullable()) {
                    //名称:required;length[2~16];
                    validateExpression = "data-rule=\""
                            + property.getPropertyComment() + ":required;\"";
                }
            }
            property.setValidateExpression(validateExpression);
        }
    };
    
    public static ValidateExpressionGenerator validateExpressionGenerator = HTML_EASYUI_VALIDATE_EXPRESSION_GENERATOR;
    
    /**
     * 生成视图逻辑<br/>
     * <功能详细描述>
     * @param entityType [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static <T> void generateView(Class<?> entityType) {
        generateView(entityType, ViewTypeEnum.LIST);
    }
    
    /**
     * 生成视图逻辑<br/>
     * <功能详细描述>
     * @param entityType
     * @param viewType [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static <T> void generateView(Class<?> entityType,
            ViewTypeEnum viewType) {
        viewType = viewType == null ? ViewTypeEnum.LIST : viewType;
        ViewGeneratorModel viewModel = new ViewGeneratorModel(entityType,
                viewType);
        
        //设置验证器的生成器
        viewModel.setValidateExpressionGenerator(validateExpressionGenerator);
        Map<String, Object> data = new HashMap<String, Object>();
        String packageName = getPackageName(entityType);
        data.put("view", viewModel);
        data.put("packageName", packageName);
        
        String basePath = ClassUtils.convertClassNameToResourcePath(
                entityType.getName()) + "/../..";
        basePath = org.springframework.util.StringUtils.cleanPath(basePath);
        
        String addFilePath = BASE_CODE_FOLDER + "/src/main/resources/templates/"
                + packageName + "/add" + entityType.getSimpleName() + ".html";
        if (isOverwrite(addFilePath)) {
            logger.info("addView存放路径:{}", addFilePath);
            FreeMarkerUtils.fprint(LOAD_TEMPLATE_CLASS,
                    viewAddTemplateFilePath,
                    data,
                    addFilePath);
        }
        
        String updateFilePath = BASE_CODE_FOLDER
                + "/src/main/resources/templates/" + packageName + "/update"
                + entityType.getSimpleName() + ".html";
        if (isOverwrite(updateFilePath)) {
            logger.info("updateView存放路径:{}", updateFilePath);
            FreeMarkerUtils.fprint(LOAD_TEMPLATE_CLASS,
                    viewUpdateTemplateFilePath,
                    data,
                    updateFilePath);
        }
        
        switch (viewType) {
            case PAGEDLIST: {
                String queryPagedListFilePath = BASE_CODE_FOLDER
                        + "/src/main/resources/templates/" + packageName
                        + "/query" + entityType.getSimpleName()
                        + "PagedList.html";
                if (isOverwrite(queryPagedListFilePath)) {
                    logger.info("queryPagedListView存放路径:{}",
                            queryPagedListFilePath);
                    FreeMarkerUtils.fprint(LOAD_TEMPLATE_CLASS,
                            viewQueryPagedListTemplateFilePath,
                            data,
                            queryPagedListFilePath);
                }
            }
                
                break;
            case TREELIST: {
                String queryTreeListFilePath = BASE_CODE_FOLDER
                        + "/src/main/resources/templates/" + packageName
                        + "/query" + entityType.getSimpleName()
                        + "TreeList.html";
                if (isOverwrite(queryTreeListFilePath)) {
                    logger.info("queryTreeListView存放路径:{}",
                            queryTreeListFilePath);
                    FreeMarkerUtils.fprint(LOAD_TEMPLATE_CLASS,
                            viewQueryTreeListTemplateFilePath,
                            data,
                            queryTreeListFilePath);
                }
            }
                break;
            case LIST:
            default: {
                String queryListFilePath = BASE_CODE_FOLDER
                        + "/src/main/resources/templates/" + packageName
                        + "/query" + entityType.getSimpleName() + "List.html";
                if (isOverwrite(queryListFilePath)) {
                    logger.info("queryListView存放路径:{}", queryListFilePath);
                    FreeMarkerUtils.fprint(LOAD_TEMPLATE_CLASS,
                            viewQueryListTemplateFilePath,
                            data,
                            queryListFilePath);
                }
            }
                break;
        }
    }
    
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
        viewType = viewType == null ? ViewTypeEnum.LIST : viewType;
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
        if (isOverwrite(controllerFilePath)) {
            logger.info("controller存放路径:{}", controllerFilePath);
            FreeMarkerUtils.fprint(LOAD_TEMPLATE_CLASS,
                    controllerTemplateFilePath,
                    data,
                    controllerFilePath);
        }
        
        String apicontrollerFilePath = BASE_CODE_FOLDER + "/src/main/java/"
                + basePath + "/controller/" + entityType.getSimpleName()
                + "APIController.java";
        if (isOverwrite(apicontrollerFilePath)) {
            logger.info("apicontroller存放路径:{}", apicontrollerFilePath);
            FreeMarkerUtils.fprint(LOAD_TEMPLATE_CLASS,
                    apicontrollerTemplateFilePath,
                    data,
                    apicontrollerFilePath);
        }
        
        String facadeFilePath = BASE_CODE_FOLDER + "/src/main/java/" + basePath
                + "/facade/" + entityType.getSimpleName() + "Facade.java";
        if (isOverwrite(facadeFilePath)) {
            logger.info("facade存放路径:{}", facadeFilePath);
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
        
        if (isOverwrite(serviceFilePath)) {
            logger.info("service存放路径:{}", serviceFilePath);
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
        
        if (isOverwrite(daoFilePath)) {
            logger.info("dao存放路径:{}", daoFilePath);
            FreeMarkerUtils.fprint(LOAD_TEMPLATE_CLASS,
                    daoTemplateFilePath,
                    data,
                    daoFilePath);
        }
        
        if (isOverwrite(daoImplFilePath)) {
            logger.info("daoImpl存放路径:{}", daoImplFilePath);
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
        
        if (isOverwrite(sqlmapFilePath)) {
            logger.info("sqlmap存放路径:{}", sqlmapPath);
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
        
        if (isOverwrite(dbScriptPath)) {
            logger.info("mysql脚本存放路径:{}", dbScriptPath);
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
            return true;
        }
    }
    
    //    public static void main(String[] args) {
    //         (null,
    //                MessageUtils.format("文件:[{}]已存在,是否确认覆盖?", "...test..."),
    //                "提示",
    //                JOptionPane.YES_NO_OPTION);
    //    }
}
