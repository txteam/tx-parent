/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2016年6月21日
 * <修改描述:>
 */
package com.tx.core.generator.table;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.dialect.Dialect;
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
import com.tx.core.generator.util.GeneratorUtils;
import com.tx.core.reflection.JpaColumnInfo;
import com.tx.core.reflection.JpaMetaClass;
import com.tx.core.util.FreeMarkerUtils;
import com.tx.core.util.JdbcUtils;


 /**
  * <功能简述>
  * <功能详细描述>
  * 
  * @author  Administrator
  * @version  [版本号, 2016年6月21日]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public class TableCodeGenerator {
    
    private Class<?> loadTemplateClass = TableCodeGenerator.class;
    
    private String sqlMapTemplateFilePath = "com/tx/core/generator/table/defaultftl/sqlMap.ftl";
    
    private String daoTemplateFilePath = "com/tx/core/generator/table/defaultftl/dao.ftl";
    
    private String daoImplTemplateFilePath = "com/tx/core/generator/table/defaultftl/daoImpl.ftl";
    
    private String serviceTemplateFilePath = "com/tx/core/generator/table/defaultftl/service.ftl";
    
    private String serviceTestTemplateFilePath = "com/tx/core/generator/table/defaultftl/serviceTest.ftl";
    
    private String dbScriptTemplateFilePath = "com/tx/core/generator/table/defaultftl/dbscript.ftl";
    
    public void generate(Class<?> type, String resultFolderPath,boolean cleanFolder) {
        if(cleanFolder){
            File folder = new File(resultFolderPath);
            try {
                FileUtils.cleanDirectory(folder);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        //生成sqlMap
        generateSimpleSqlMap(type, resultFolderPath);
        
        //生成Dao以及DaoImpl
        generateDao(type, resultFolderPath);
        
        //生成Service
        generateService(type, resultFolderPath);
    }
    
    public void generate(Class<?> type, String resultFolderPath) {
        File folder = new File(resultFolderPath);
        try {
            FileUtils.cleanDirectory(folder);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        //生成sqlMap
        generateSimpleSqlMap(type, resultFolderPath);
        
        //生成Dao以及DaoImpl
        generateDao(type, resultFolderPath);
        
        //生成Service
        generateService(type, resultFolderPath);
    }
    
    public void generateScript(Class<?> type, String resultFolderPath) {
        generateScript(type, resultFolderPath, "UTF-8");
    }
    
    /**
      * 生成脚本文件
      *<功能详细描述>
      * @param type
      * @param resultFolderPath
      * @param encode [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public void generateScript(Class<?> type, String resultFolderPath,
            String encode) {
        JpaMetaClass<?> jpaMetaClass = JpaMetaClass.forClass(type);
        
        //生成service单元测试类
        generateScript(DataSourceTypeEnum.ORACLE,
                jpaMetaClass,
                resultFolderPath,
                encode);
        generateScript(DataSourceTypeEnum.H2,
                jpaMetaClass,
                resultFolderPath,
                encode);
        generateScript(DataSourceTypeEnum.MYSQL,
                jpaMetaClass,
                resultFolderPath,
                encode);
    }
    
    /**
      * 生成脚本<br/>
      *<功能详细描述>
      * @param type
      * @param dataSourceType
      * @param encode
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public <TYPE> String generateScriptContent(Class<TYPE> type,
            DataSourceTypeEnum dataSourceType, String encode) {
        JpaMetaClass<TYPE> jpaMetaClass = JpaMetaClass.forClass(type);
        
        //生成service单元测试类
        String script = generateScriptContentByDataSourceType(dataSourceType,
                jpaMetaClass,
                encode);
        return script;
    }
    
    /**
      * 生成建表数据库脚本<br/>
      *<功能详细描述>
      * @param dataSourceType
      * @param jpaMetaClass
      * @param resultFolderPath
      * @param encode [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public void generateScript(DataSourceTypeEnum dataSourceType,
            JpaMetaClass<?> jpaMetaClass, String resultFolderPath, String encode) {
        //
        Dialect dialect = dataSourceType.getDialect();
        
        Map<String, Object> data = new HashMap<String, Object>();
        
        DBScriptMapper dbScriptMapper = new DBScriptMapper();
        dbScriptMapper.setTableName(jpaMetaClass.getTableName().toUpperCase());
        dbScriptMapper.setPkColumnName(jpaMetaClass.getGetter2columnInfoMapping()
                .get(jpaMetaClass.getPkGetterName())
                .getColumnName());
        for (Entry<String, JpaColumnInfo> entryTemp : jpaMetaClass.getGetter2columnInfoMapping()
                .entrySet()) {
            JpaColumnInfo columnInfo = entryTemp.getValue();
            dbScriptMapper.getColumnName2TypeNameMapping()
                    .put(columnInfo.getColumnName(),
                            dialect.getTypeName(JdbcUtils.getSqlTypeByJavaType(columnInfo.getRealGetterType()),
                                    columnInfo.getLength(),
                                    columnInfo.getPrecision(),
                                    columnInfo.getScale()));
        }
        data.put("dbScriptMapper", dbScriptMapper);
        
        String entityTypeName = jpaMetaClass.getEntityTypeName();
        String[] splitNames = entityTypeName.split("\\.");
        String moduleName = splitNames[splitNames.length - 3].toLowerCase();
        
        FreeMarkerUtils.fprint(loadTemplateClass,
                this.dbScriptTemplateFilePath,
                data,
                resultFolderPath + "/dbscript/" + dataSourceType.getName()
                        + "/01basisScript/" + moduleName + "/tables/"
                        + jpaMetaClass.getTableName().toLowerCase() + ".sql",
                encode);
    }
    
    private String generateScriptContentByDataSourceType(
            DataSourceTypeEnum dataSourceType, JpaMetaClass<?> jpaMetaClass,
            String encode) {
        //
        Dialect dialect = dataSourceType.getDialect();
        
        Map<String, Object> data = new HashMap<String, Object>();
        
        DBScriptMapper dbScriptMapper = new DBScriptMapper();
        dbScriptMapper.setTableName(jpaMetaClass.getTableName().toUpperCase());
        dbScriptMapper.setPkColumnName(jpaMetaClass.getGetter2columnInfoMapping()
                .get(jpaMetaClass.getPkGetterName())
                .getColumnName());
        for (Entry<String, JpaColumnInfo> entryTemp : jpaMetaClass.getGetter2columnInfoMapping()
                .entrySet()) {
            JpaColumnInfo columnInfo = entryTemp.getValue();
            dbScriptMapper.getColumnName2TypeNameMapping()
                    .put(columnInfo.getColumnName(),
                            dialect.getTypeName(JdbcUtils.getSqlTypeByJavaType(columnInfo.getRealGetterType()),
                                    columnInfo.getLength(),
                                    columnInfo.getPrecision(),
                                    columnInfo.getScale()));
        }
        data.put("dbScriptMapper", dbScriptMapper);
        
        String content = FreeMarkerUtils.generateContent(loadTemplateClass,
                this.dbScriptTemplateFilePath,
                data,
                encode);
        return content;
    }
    
    /**
      * 生成业务层代码
      * <功能详细描述>
      * @param jpaMetaClass
      * @param resultFolderPath [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public <TYPE> void generateService(Class<TYPE> type, String resultFolderPath) {
        JpaMetaClass<TYPE> jpaMetaClass = JpaMetaClass.forClass(type);
        ServiceGeneratorModel model = new ServiceGeneratorModel();
        
        String basePath = ClassUtils.convertClassNameToResourcePath(jpaMetaClass.getEntityTypeName())
                + "/../..";
        basePath = org.springframework.util.StringUtils.cleanPath(basePath);
        
        model.setBasePackage(ClassUtils.convertResourcePathToClassName(basePath));
        model.setEntitySimpleName(jpaMetaClass.getEntitySimpleName());
        model.setIdPropertyName(jpaMetaClass.getPkGetterName());
        model.setLowerCaseEntitySimpleName(org.apache.commons.lang.StringUtils.uncapitalize(jpaMetaClass.getEntitySimpleName()));
        model.setSqlMapColumnList(GeneratorUtils.generateSqlMapColumnList(jpaMetaClass));
        model.setUpCaseIdPropertyName(StringUtils.capitalize(jpaMetaClass.getPkGetterName()));
        
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("service", model);
        
        FreeMarkerUtils.fprint(loadTemplateClass,
                this.serviceTemplateFilePath,
                data,
                resultFolderPath + "/main/java/" + basePath + "/service/"
                        + jpaMetaClass.getEntitySimpleName() + "Service.java");
        
        FreeMarkerUtils.fprint(loadTemplateClass,
                this.serviceTestTemplateFilePath,
                data,
                resultFolderPath + "/test/java/" + basePath + "/"
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
    public <TYPE> void generateDao(Class<TYPE> type, String resultFolderPath) {
        JpaMetaClass<TYPE> jpaMetaClass = JpaMetaClass.forClass(type);
        DaoGeneratorModel model = new DaoGeneratorModel();
        
        String daoPath = ClassUtils.convertClassNameToResourcePath(jpaMetaClass.getEntityTypeName())
                + "/../../dao";
        daoPath = org.springframework.util.StringUtils.cleanPath(daoPath);
        
        model.setBasePackage(ClassUtils.convertResourcePathToClassName(daoPath));
        model.setEntityTypeName(jpaMetaClass.getEntityTypeName());
        model.setSimpleEntityTypeName(jpaMetaClass.getEntitySimpleName());
        model.setLowerCaseEntityTypeName(org.apache.commons.lang.StringUtils.uncapitalize(jpaMetaClass.getEntitySimpleName()));
        
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("dao", model);
        
        FreeMarkerUtils.fprint(loadTemplateClass,
                this.daoTemplateFilePath,
                data,
                resultFolderPath + "/main/java/" + daoPath + "/"
                        + jpaMetaClass.getEntitySimpleName() + "Dao.java");
        
        FreeMarkerUtils.fprint(loadTemplateClass,
                this.daoImplTemplateFilePath,
                data,
                resultFolderPath + "/main/java/" + daoPath + "/impl/"
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
    public <TYPE> void generateSimpleSqlMap(Class<TYPE> type,
            String resultFolderPath) {
        JpaMetaClass<TYPE> jpaMetaClass = JpaMetaClass.forClass(type);
        
        SqlMapMapper mapper = generateMapper(jpaMetaClass);
        InsertMapper insert = generateInsertMapper(jpaMetaClass);
        DeleteMapper delete = generateDeleteMapper(jpaMetaClass);
        SelectMapper select = generateSelectMapper(jpaMetaClass);
        UpdateMapper update = generateUpdateMapper(jpaMetaClass);
        
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
                this.sqlMapTemplateFilePath,
                data,
                resultFolderPath + "/main/java/" + sqlMapPath + "/"
                        + jpaMetaClass.getEntitySimpleName() + "SqlMap.xml");
        
    }
    
    /**
      * 自动生成更新语句
      * <功能详细描述>
      * @param jpaMetaClass
      * @return [参数说明]
      * 
      * @return UpdateMapper [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    private UpdateMapper generateUpdateMapper(JpaMetaClass<?> jpaMetaClass) {
        UpdateMapper updateMapper = new UpdateMapper();
        
        updateMapper.setId("update" + jpaMetaClass.getEntitySimpleName());
        
        Map<String, JpaColumnInfo> getter2columnInfoMapping = jpaMetaClass.getGetter2columnInfoMapping();
        String idPropertyName = jpaMetaClass.getPkGetterName();
        String idColumnName = getter2columnInfoMapping.get(idPropertyName)
                .getColumnName();
        
        updateMapper.setIdColumnName(idColumnName == null ? ""
                : idColumnName.toUpperCase());
        updateMapper.setIdPropertyName(idPropertyName);
        
        updateMapper.setSimpleTableName(jpaMetaClass.getSimpleTableName()
                .toUpperCase());
        updateMapper.setTableName(jpaMetaClass.getTableName());
        updateMapper.setSqlMapColumnList(GeneratorUtils.generateSqlMapColumnList(jpaMetaClass));
        
        return updateMapper;
    }
    
    /**
      * 生成查询映射
      * <功能详细描述>
      * @param jpaMetaClass
      * @return [参数说明]
      * 
      * @return SelectMapper [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public SelectMapper generateSelectMapper(JpaMetaClass<?> jpaMetaClass) {
        SelectMapper selectMapper = new SelectMapper();
        
        selectMapper.setFindId("find" + jpaMetaClass.getEntitySimpleName());
        selectMapper.setQueryId("query" + jpaMetaClass.getEntitySimpleName());
        
        Map<String, JpaColumnInfo> getter2columnInfoMapping = jpaMetaClass.getGetter2columnInfoMapping();
        String idPropertyName = jpaMetaClass.getPkGetterName();
        String idColumnName = getter2columnInfoMapping.get(idPropertyName)
                .getColumnName();
        
        selectMapper.setIdColumnName(idColumnName == null ? ""
                : idColumnName.toUpperCase());
        selectMapper.setIdPropertyName(idPropertyName);
        
        selectMapper.setParameterType(jpaMetaClass.getEntityTypeName());
        selectMapper.setResultMapId(org.apache.commons.lang.StringUtils.uncapitalize(jpaMetaClass.getEntitySimpleName())
                + "Map");
        
        selectMapper.setSimpleTableName(jpaMetaClass.getSimpleTableName()
                .toUpperCase());
        selectMapper.setTableName(jpaMetaClass.getTableName().toUpperCase());
        
        selectMapper.setSqlMapColumnList(GeneratorUtils.generateSqlMapColumnList(jpaMetaClass));
        
        return selectMapper;
    }
    
    /**
      * 生成删除映射
      * <功能详细描述>
      * @param jpaMetaClass
      * @return [参数说明]
      * 
      * @return DeleteMapper [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public DeleteMapper generateDeleteMapper(JpaMetaClass<?> jpaMetaClass) {
        DeleteMapper deleteMapper = new DeleteMapper();
        
        deleteMapper.setId("delete" + jpaMetaClass.getEntitySimpleName());
        deleteMapper.setParameterType(jpaMetaClass.getEntityTypeName());
        
        Map<String, JpaColumnInfo> getter2columnInfoMapping = jpaMetaClass.getGetter2columnInfoMapping();
        String idPropertyName = jpaMetaClass.getPkGetterName();
        String idColumnName = getter2columnInfoMapping.get(idPropertyName)
                .getColumnName();
        
        deleteMapper.setIdColumnName(idColumnName == null ? ""
                : idColumnName.toUpperCase());
        deleteMapper.setIdPropertyName(idPropertyName);
        deleteMapper.setSimpleTableName(jpaMetaClass.getSimpleTableName()
                .toUpperCase());
        deleteMapper.setTableName(jpaMetaClass.getTableName().toUpperCase());
        
        return deleteMapper;
    }
    
    /**
      * 生成增加映射
      *<功能详细描述>
      * @param jpaMetaClass
      * @return [参数说明]
      * 
      * @return InsertMapper [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    private InsertMapper generateInsertMapper(JpaMetaClass<?> jpaMetaClass) {
        InsertMapper insertMapper = new InsertMapper();
        
        insertMapper.setId("insert" + jpaMetaClass.getEntitySimpleName());
        insertMapper.setParameterType(jpaMetaClass.getEntityTypeName());
        //设置表名，并转换为大写
        insertMapper.setTableName(jpaMetaClass.getTableName().toUpperCase());
        
        //字段
        insertMapper.getSqlMapColumnList()
                .addAll(GeneratorUtils.generateSqlMapColumnList(jpaMetaClass));
        
        return insertMapper;
    }
    
    /**
      * 根据jpa对象解析结果生成映射mapper
      *<功能详细描述>
      * @param japMetaClass
      * @return [参数说明]
      * 
      * @return SqlMapMapper [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    private SqlMapMapper generateMapper(JpaMetaClass<?> jpaMetaClass) {
        SqlMapMapper mapper = new SqlMapMapper();
        mapper.setNamespace(org.apache.commons.lang.StringUtils.uncapitalize(jpaMetaClass.getEntitySimpleName()));
        return mapper;
    }
    
    /**
     * @param 对sqlMapTemplateFilePath进行赋值
     */
    public void setSqlMapTemplateFilePath(String sqlMapTemplateFilePath) {
        this.sqlMapTemplateFilePath = sqlMapTemplateFilePath;
    }
    
    /**
     * @param 对daoTemplateFilePath进行赋值
     */
    public void setDaoTemplateFilePath(String daoTemplateFilePath) {
        this.daoTemplateFilePath = daoTemplateFilePath;
    }
    
    /**
     * @param 对daoImplTemplateFilePath进行赋值
     */
    public void setDaoImplTemplateFilePath(String daoImplTemplateFilePath) {
        this.daoImplTemplateFilePath = daoImplTemplateFilePath;
    }
    
    /**
     * @param 对serviceTemplateFilePath进行赋值
     */
    public void setServiceTemplateFilePath(String serviceTemplateFilePath) {
        this.serviceTemplateFilePath = serviceTemplateFilePath;
    }
    
    /**
     * @param 对serviceTestTemplateFilePath进行赋值
     */
    public void setServiceTestTemplateFilePath(
            String serviceTestTemplateFilePath) {
        this.serviceTestTemplateFilePath = serviceTestTemplateFilePath;
    }
    
    /**
     * @param 对loadTemplateClass进行赋值
     */
    public void setLoadTemplateClass(Class<?> loadTemplateClass) {
        this.loadTemplateClass = loadTemplateClass;
    }
    
    /**
     * @param 对dbScriptTemplateFilePath进行赋值
     */
    public void setDbScriptTemplateFilePath(String dbScriptTemplateFilePath) {
        this.dbScriptTemplateFilePath = dbScriptTemplateFilePath;
    }   
}
