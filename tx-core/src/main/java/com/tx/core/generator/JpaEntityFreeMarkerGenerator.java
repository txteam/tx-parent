/*
 * 描          述:  <描述>
 * 修  改   人:  PengQingyang
 * 修改时间:  2012-12-9
 * <修改描述:>
 */
package com.tx.core.generator;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.cxf.common.util.StringUtils;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.hibernate.dialect.Dialect;
import org.springframework.util.ClassUtils;

import com.tx.core.dbscript.model.DataSourceTypeEnum;
import com.tx.core.exceptions.argument.IllegalArgException;
import com.tx.core.generator.model.ColumnInfo;
import com.tx.core.generator.model.DBScriptMapper;
import com.tx.core.generator.model.DaoGeneratorModel;
import com.tx.core.generator.model.DeleteMapper;
import com.tx.core.generator.model.InsertMapper;
import com.tx.core.generator.model.SelectMapper;
import com.tx.core.generator.model.ServiceGeneratorModel;
import com.tx.core.generator.model.SqlMapColumn;
import com.tx.core.generator.model.SqlMapMapper;
import com.tx.core.generator.model.UpdateMapper;
import com.tx.core.reflection.JpaMetaClass;
import com.tx.core.util.FreeMarkerUtils;

/**
 * 根据JPA实体生成sqlMap
 * <功能详细描述>
 * 
 * @author  PengQingyang
 * @version  [版本号, 2012-12-9]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class JpaEntityFreeMarkerGenerator {
    
    /** 基本类型集合 */
    private static final Set<Class<?>> SIMPLE_TYPE = new HashSet<Class<?>>();
    
    private String sqlMapTemplateFilePath = "com/tx/core/generator/defaultftl/sqlMap.ftl";
    
    private String daoTemplateFilePath = "com/tx/core/generator/defaultftl/dao.ftl";
    
    private String daoImplTemplateFilePath = "com/tx/core/generator/defaultftl/daoImpl.ftl";
    
    private String serviceTemplateFilePath = "com/tx/core/generator/defaultftl/service.ftl";
    
    private String serviceTestTemplateFilePath = "com/tx/core/generator/defaultftl/serviceTest.ftl";
    
    private String dbScriptTemplateFilePath = "com/tx/core/generator/defaultftl/dbScript.ftl";
    
    private Class<?> loadTemplateClass = JpaEntityFreeMarkerGenerator.class;
    
    static {
        SIMPLE_TYPE.add(char[].class);
        SIMPLE_TYPE.add(byte[].class);
        
        SIMPLE_TYPE.add(char.class);
        SIMPLE_TYPE.add(byte.class);
        SIMPLE_TYPE.add(int.class);
        SIMPLE_TYPE.add(short.class);
        SIMPLE_TYPE.add(double.class);
        SIMPLE_TYPE.add(float.class);
        SIMPLE_TYPE.add(long.class);
        SIMPLE_TYPE.add(boolean.class);
        
        SIMPLE_TYPE.add(Character.class);
        SIMPLE_TYPE.add(Byte.class);
        SIMPLE_TYPE.add(Integer.class);
        SIMPLE_TYPE.add(Short.class);
        SIMPLE_TYPE.add(Double.class);
        SIMPLE_TYPE.add(Float.class);
        SIMPLE_TYPE.add(Long.class);
        SIMPLE_TYPE.add(Boolean.class);
        
        SIMPLE_TYPE.add(String.class);
        SIMPLE_TYPE.add(Date.class);
        SIMPLE_TYPE.add(java.sql.Date.class);
        SIMPLE_TYPE.add(Timestamp.class);
        //SIMPLE_TYPE.add(Number.class);
        SIMPLE_TYPE.add(BigInteger.class);
        SIMPLE_TYPE.add(BigDecimal.class);
    }
    
    /** 默认的字段比较器，用以排序 */
    private static final Comparator<SqlMapColumn> columnComparator = new Comparator<SqlMapColumn>() {
        /**
         * @param o1
         * @param o2
         * @return
         */
        @Override
        public int compare(SqlMapColumn o1, SqlMapColumn o2) {
            if (o1.isId()) {
                return -1;
            }
            if (o2.isId()) {
                return 1;
            }
            if (o1.getClass().getName().length()
                    - o2.getClass().getName().length() > 0) {
                return 1;
            } else if (o1.getClass().getName().length()
                    - o2.getClass().getName().length() < 0) {
                return -1;
            } else {
                return 0;
            }
        }
    };
    
    public void generate(Class<?> type, String resultFolderPath) {
        JpaMetaClass japMetaClass = JpaMetaClass.forClass(type);
        
        //生成sqlMap
        generateSimpleSqlMap(japMetaClass, resultFolderPath);
        
        //生成Dao以及DaoImpl
        generateDao(japMetaClass, resultFolderPath);
        
        //生成Service
        generateService(japMetaClass, resultFolderPath);
        
        //生成service单元测试类
    }
    
    /**
      * 字段类型映射器<br/>
      * <功能详细描述>
      * 
      * @author  brady
      * @version  [版本号, 2013-8-27]
      * @see  [相关类/方法]
      * @since  [产品/模块版本]
     */
    public static interface ColumnTypeMapper {
        
        /**
          * 生成数据字段类型
          *<功能详细描述>
          * @param dataSourceType
          * @param jdbcType
          * @param columnName
          * @return [参数说明]
          * 
          * @return String [返回类型说明]
          * @exception throws [异常类型] [异常说明]
          * @see [类、类#方法、类#成员]
         */
        public String generateColumnType(DataSourceTypeEnum dataSourceType,
                Class<?> jdbcType, String columnName);
    }
    
    public static class DefaultColumnTypeMapper implements ColumnTypeMapper {
        
        /**
         * @param jdbcType
         * @param columnName
         * @return
         */
        @Override
        public String generateColumnType(DataSourceTypeEnum dataSourceType,
                Class<?> jdbcType, String columnName) {
            // TODO Auto-generated method stub
            return null;
        }
        
    }
    
    public void generateScript(Class<?> type, String resultFolderPath) {
        generateScript(type, resultFolderPath, "UTF-8");
    }
    
    public void generateScript(Class<?> type, String resultFolderPath,
            String encode) {
        JpaMetaClass jpaMetaClass = JpaMetaClass.forClass(type);
        
        //生成service单元测试类
        generateScriptByDataSourceType(DataSourceTypeEnum.ORACLE,
                jpaMetaClass,
                resultFolderPath,
                encode);
        generateScriptByDataSourceType(DataSourceTypeEnum.H2,
                jpaMetaClass,
                resultFolderPath,
                encode);
        generateScriptByDataSourceType(DataSourceTypeEnum.MYSQL,
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
    public String generateScriptContent(Class<?> type,
            DataSourceTypeEnum dataSourceType, String encode) {
        JpaMetaClass jpaMetaClass = JpaMetaClass.forClass(type);
        
        //生成service单元测试类
        String script = generateScriptContentByDataSourceType(dataSourceType,
                jpaMetaClass,
                encode);
        return script;
    }
    
    private void generateScriptByDataSourceType(
            DataSourceTypeEnum dataSourceType, JpaMetaClass jpaMetaClass,
            String resultFolderPath, String encode) {
        //
        Dialect dialect = dataSourceType.getDialect();
        
        Map<String, Object> data = new HashMap<String, Object>();
        
        DBScriptMapper dbScriptMapper = new DBScriptMapper();
        dbScriptMapper.setTableName(jpaMetaClass.getTableName().toUpperCase());
        dbScriptMapper.setPkColumnName(jpaMetaClass.getColumnInfoMapping()
                .get(jpaMetaClass.getPkPropertyName())
                .getName()
                .toUpperCase());
        for (Entry<String, ColumnInfo> entryTemp : jpaMetaClass.getColumnInfoMapping()
                .entrySet()) {
            ColumnInfo columnInfo = entryTemp.getValue();
            dbScriptMapper.getColumnName2TypeNameMapping()
                    .put(columnInfo.getName(),
                            dialect.getTypeName(columnInfo.getJdbcType(),
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
                        + jpaMetaClass.getTableName().toUpperCase() + ".sql",
                encode);
    }
    
    private String generateScriptContentByDataSourceType(
            DataSourceTypeEnum dataSourceType, JpaMetaClass jpaMetaClass,
            String encode) {
        //
        Dialect dialect = dataSourceType.getDialect();
        
        Map<String, Object> data = new HashMap<String, Object>();
        
        DBScriptMapper dbScriptMapper = new DBScriptMapper();
        dbScriptMapper.setTableName(jpaMetaClass.getTableName().toUpperCase());
        dbScriptMapper.setPkColumnName(jpaMetaClass.getColumnInfoMapping()
                .get(jpaMetaClass.getPkPropertyName())
                .getName()
                .toUpperCase());
        for (Entry<String, ColumnInfo> entryTemp : jpaMetaClass.getColumnInfoMapping()
                .entrySet()) {
            ColumnInfo columnInfo = entryTemp.getValue();
            dbScriptMapper.getColumnName2TypeNameMapping()
                    .put(columnInfo.getName(),
                            dialect.getTypeName(columnInfo.getJdbcType(),
                                    columnInfo.getLength(),
                                    columnInfo.getPrecision(),
                                    columnInfo.getScale()));
        }
        data.put("dbScriptMapper", dbScriptMapper);
        
        //String entityTypeName = jpaMetaClass.getEntityTypeName();
        //String[] splitNames = entityTypeName.split("\\.");
        //String moduleName = splitNames[splitNames.length - 3].toLowerCase();
        
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
    private void generateService(JpaMetaClass jpaMetaClass,
            String resultFolderPath) {
        ServiceGeneratorModel model = new ServiceGeneratorModel();
        
        String basePath = ClassUtils.convertClassNameToResourcePath(jpaMetaClass.getEntityTypeName())
                + "/../..";
        basePath = org.springframework.util.StringUtils.cleanPath(basePath);
        
        model.setBasePackage(ClassUtils.convertResourcePathToClassName(basePath));
        model.setEntitySimpleName(jpaMetaClass.getEntitySimpleName());
        model.setIdPropertyName(jpaMetaClass.getPkPropertyName());
        model.setLowerCaseEntitySimpleName(jpaMetaClass.getLowerCaseFirstCharEntitySimpleName());
        model.setSqlMapColumnList(generateColumnList(jpaMetaClass));
        model.setUpCaseIdPropertyName(StringUtils.capitalize(jpaMetaClass.getPkPropertyName()));
        
        //        model.setBasePackage(ClassUtils.convertResourcePathToClassName(basePath));
        //        model.setEntityTypeName(jpaMetaClass.getEntityTypeName());
        //        model.setSimpleEntityTypeName(jpaMetaClass.getEntitySimpleName());
        //        model.setLowerCaseEntityTypeName(jpaMetaClass.getLowerCaseFirstCharEntitySimpleName());
        
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
    private void generateDao(JpaMetaClass jpaMetaClass, String resultFolderPath) {
        DaoGeneratorModel model = new DaoGeneratorModel();
        
        String daoPath = ClassUtils.convertClassNameToResourcePath(jpaMetaClass.getEntityTypeName())
                + "/../../dao";
        daoPath = org.springframework.util.StringUtils.cleanPath(daoPath);
        
        model.setBasePackage(ClassUtils.convertResourcePathToClassName(daoPath));
        model.setEntityTypeName(jpaMetaClass.getEntityTypeName());
        model.setSimpleEntityTypeName(jpaMetaClass.getEntitySimpleName());
        model.setLowerCaseEntityTypeName(jpaMetaClass.getLowerCaseFirstCharEntitySimpleName());
        
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
    private void generateSimpleSqlMap(JpaMetaClass jpaMetaClass,
            String resultFolderPath) {
        SqlMapMapper mapper = generateMapper(jpaMetaClass);
        InsertMapper insert = generateInsertMapper(jpaMetaClass);
        DeleteMapper delete = generateDeleteMapper(jpaMetaClass);
        SelectMapper select = generateSelectMapper(jpaMetaClass);
        UpdateMapper update = generateUpdateMapper(jpaMetaClass);
        
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("parseMessage", jpaMetaClass.getParseMessage().toString());
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
    public UpdateMapper generateUpdateMapper(JpaMetaClass jpaMetaClass) {
        UpdateMapper updateMapper = new UpdateMapper();
        
        updateMapper.setId("update" + jpaMetaClass.getEntitySimpleName());
        
        Map<String, String> columnNameMapping = jpaMetaClass.getColumnNameMapping();
        String idPropertyName = jpaMetaClass.getPkPropertyName();
        String idColumnName = columnNameMapping.get(idPropertyName);
        
        updateMapper.setIdColumnName(idColumnName == null ? ""
                : idColumnName.toUpperCase());
        updateMapper.setIdPropertyName(idPropertyName);
        
        updateMapper.setSimpleTableName(jpaMetaClass.getSimpleTableName()
                .toUpperCase());
        updateMapper.setTableName(jpaMetaClass.getTableName());
        updateMapper.setSqlMapColumnList(generateColumnList(jpaMetaClass));
        
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
    public SelectMapper generateSelectMapper(JpaMetaClass jpaMetaClass) {
        SelectMapper selectMapper = new SelectMapper();
        
        selectMapper.setFindId("find" + jpaMetaClass.getEntitySimpleName());
        selectMapper.setQueryId("query" + jpaMetaClass.getEntitySimpleName());
        
        Map<String, String> columnNameMapping = jpaMetaClass.getColumnNameMapping();
        String idPropertyName = jpaMetaClass.getPkPropertyName();
        String idColumnName = columnNameMapping.get(idPropertyName);
        
        selectMapper.setIdColumnName(idColumnName == null ? ""
                : idColumnName.toUpperCase());
        selectMapper.setIdPropertyName(idPropertyName);
        
        selectMapper.setParameterType(jpaMetaClass.getEntityTypeName());
        selectMapper.setResultMapId(jpaMetaClass.getLowerCaseFirstCharEntitySimpleName()
                + "Map");
        
        selectMapper.setSimpleTableName(jpaMetaClass.getSimpleTableName()
                .toUpperCase());
        selectMapper.setTableName(jpaMetaClass.getTableName().toUpperCase());
        
        selectMapper.setSqlMapColumnList(generateColumnList(jpaMetaClass));
        
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
    public DeleteMapper generateDeleteMapper(JpaMetaClass jpaMetaClass) {
        DeleteMapper deleteMapper = new DeleteMapper();
        
        deleteMapper.setId("delete" + jpaMetaClass.getEntitySimpleName());
        deleteMapper.setParameterType(jpaMetaClass.getEntityTypeName());
        
        Map<String, String> columnNameMapping = jpaMetaClass.getColumnNameMapping();
        String idPropertyName = jpaMetaClass.getPkPropertyName();
        String idColumnName = columnNameMapping.get(idPropertyName);
        
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
    public InsertMapper generateInsertMapper(JpaMetaClass jpaMetaClass) {
        InsertMapper insertMapper = new InsertMapper();
        
        insertMapper.setId("insert" + jpaMetaClass.getEntitySimpleName());
        insertMapper.setParameterType(jpaMetaClass.getEntityTypeName());
        //设置表名，并转换为大写
        insertMapper.setTableName(jpaMetaClass.getTableName().toUpperCase());
        
        //字段
        insertMapper.getSqlMapColumnList()
                .addAll(generateColumnList(jpaMetaClass));
        
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
    private SqlMapMapper generateMapper(JpaMetaClass japMetaClass) {
        SqlMapMapper mapper = new SqlMapMapper();
        mapper.setNamespace(japMetaClass.getLowerCaseFirstCharEntitySimpleName());
        return mapper;
    }
    
    /** 
      * 生成字段列表
      *<功能详细描述>
      * @param jpaMetaClass
      * @return [参数说明]
      * 
      * @return List<SqlMapColumn> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    private List<SqlMapColumn> generateColumnList(JpaMetaClass jpaMetaClass) {
        List<SqlMapColumn> columnList = new ArrayList<SqlMapColumn>();
        //生成对应需要的列关系
        List<String> getterNameList = jpaMetaClass.getGetterNames();
        
        Map<String, Method> methodMap = jpaMetaClass.getGetterMethodMapping();
        Map<String, Class<?>> typeMap = jpaMetaClass.getGetterTypeMapping();
        Map<String, Boolean> ignoreMap = jpaMetaClass.getIgnoreGetterMapping();
        Map<String, String> columnNameMapping = jpaMetaClass.getColumnNameMapping();
        String idPropertyName = jpaMetaClass.getPkPropertyName();
        
        for (String getterName : getterNameList) {
            if (StringUtils.isEmpty(getterName) || ignoreMap.get(getterName)) {
                continue;
            }
            Class<?> typeTemp = typeMap.get(getterName);
            SqlMapColumn columnTemp = null;
            if (SIMPLE_TYPE.contains(typeTemp)
                    || Enum.class.isAssignableFrom(typeTemp)) {
                columnTemp = new SqlMapColumn(true, getterName,
                        columnNameMapping.get(getterName).toUpperCase(),
                        typeTemp, null);
            } else {
                JpaMetaClass temp = JpaMetaClass.forClass(typeTemp);
                String tempIdPropertyName = temp.getPkPropertyName();
                Class<?> tempIdType = temp.getGetterTypeMapping()
                        .get(tempIdPropertyName);
                if (StringUtils.isEmpty(tempIdPropertyName)) {
                    //如果不为简单对象，关联对象中又不存在主键设置，这里将认为发生了异常，这样的情形不应该出现
                    throw new IllegalArgException(typeTemp.getName()
                            + " id property is empty.");
                }
                columnTemp = new SqlMapColumn(false, getterName,
                        columnNameMapping.get(getterName).toUpperCase(),
                        tempIdType, tempIdPropertyName);
            }
            if (idPropertyName.equals(getterName)) {
                columnTemp.setId(true);
            }
            columnTemp.setGetterMethod(methodMap.get(getterName));
            
            columnTemp.setGetterMethodSimpleName(org.springframework.util.StringUtils.unqualify(methodMap.get(getterName)
                    .getName()));
            
            columnList.add(columnTemp);
        }
        
        Collections.sort(columnList, columnComparator);
        return columnList;
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