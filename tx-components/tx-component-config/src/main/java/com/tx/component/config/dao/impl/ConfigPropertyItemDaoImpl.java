/*
 * 描          述:  <描述>
 * 修  改   人:  wanxin
 * 修改时间:  2013-8-7
 * <修改描述:>
 */
package com.tx.component.config.dao.impl;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.reflection.MetaObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.helpers.MessageFormatter;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.tx.component.config.context.ConfigContextConfigurator;
import com.tx.component.config.dao.ConfigPropertyItemDao;
import com.tx.component.config.exception.ConfigContextInitException;
import com.tx.component.config.model.ConfigPropertyItem;
import com.tx.core.mybatis.model.Order;

/**
 * 配置属性项持久层<br/>
 * <功能详细描述>
 * 
 * @author  wanxin
 * @version  [版本号, 2013-8-7]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class ConfigPropertyItemDaoImpl implements ConfigPropertyItemDao {
    
    private static Logger logger = LoggerFactory.getLogger(ConfigContextConfigurator.class);
    
    private static ResourceLoader resourceLoader = new DefaultResourceLoader();
    
    private static String resourcePath = "com/tx/component/config/dao/impl/configPropertyMapper.";
    
    /** 配置容器版本 */
    @SuppressWarnings("unused")
    /*
     * 配置容器版本的功能暂不需要待后续版本再进行添加
     */
    private static final String CONFIG_CONTEXT_VERSION = "V1.0";
    
    /** 配置容器属性存储表名前缀 */
    private static final String PROPERTIES_TABLE_NAME_PREFIX = "CFG_PROPERTIS";
    
    /** 配置容器 容器表 表名前缀 */
    private static final String CONTEXT_TABLE_NAME_PREFIX = "CFG_CONTEXT";
    
    /** 数据源 */
    private final DataSource dataSource;
    
    private final String dataSourceType;
    
    /** 表后缀 */
    private String tableSuffix;
    
    private final JdbcTemplate jdbcTemplate;
    
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    
    private String contextTableName;
    
    private String propertiesTableName;
    
    /** 查询名及查询语句对照 */
    private Properties sqlProp = new Properties();
    
    /** 配置总表插入sql */
    private static String insertSql;
    

    
    /**
     * <默认构造函数>
     */
    public ConfigPropertyItemDaoImpl(DataSource dataSource,
            String dataSourceType, String tableSuffix) {
        super();
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(this.dataSource);
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(this.dataSource);
        this.dataSourceType = dataSourceType;
        this.tableSuffix = tableSuffix;
        
        //根据数据库类型加载操作配置容器的数据库操作脚本文件
        logger.info("   根据数据库类型,加载数据库相关操作语句:");
        try {
            Resource tableResourceMapper = resourceLoader.getResource("classpath*:"
                    + resourcePath + this.dataSourceType + ".xml");
            if (!tableResourceMapper.exists()) {
                tableResourceMapper = resourceLoader.getResource("classpath:"
                        + resourcePath + this.dataSourceType + ".xml");
            }
            sqlProp.loadFromXML(tableResourceMapper.getInputStream());
        } catch (IOException e) {
            //TODO:异常处理
            e.printStackTrace();
        }
       
        
        //根据后最名构造对应的配置容器表名
        if (StringUtils.isEmpty(this.tableSuffix)) {
            this.contextTableName = CONTEXT_TABLE_NAME_PREFIX;
            this.propertiesTableName = PROPERTIES_TABLE_NAME_PREFIX;
        } else {
            this.contextTableName = CONTEXT_TABLE_NAME_PREFIX + "_"
                    + this.tableSuffix.trim().toUpperCase();
            this.propertiesTableName = PROPERTIES_TABLE_NAME_PREFIX + "_"
                    + this.tableSuffix.trim().toUpperCase();
        }
        logger.info("   加载配置容器持久表信息:属性存放表名:{},容器表名:{}", new Object[] {
                this.contextTableName, this.propertiesTableName });
        
        //构造属性插入语句
        ConfigPropertyItemDaoImpl.insertSql = MessageFormatter.arrayFormat(sqlProp.getProperty("insert"),
                new Object[] { this.propertiesTableName }).getMessage();
        
        if (configPropertyItemTableIsExist()) {
            //TODO : AND 版本号与当前表版本一致,暂不实现
            logger.info("  检测到容器属性表:已存在.");
            //TODO: 检查配置容器表版本是否正确
        } else {
            try {
                //不存在则,在此处创建表
                createConfigPropertyItemTable();
                logger.info("  检测到容器属性表:不存在.自动创建表成功");
            } catch (DataAccessException e) {
                logger.error("  检测到容器属性表:不存在.自动创建表失败", e);
                // TODO 如果表创建异常,则对已经存在的表进行drop回滚操作,暂不实现
                throw new ConfigContextInitException("检测到容器属性表:不存在.自动创建表失败", e);
            }
        }
    }
    
    /**
      * 配置属性项表是否存在<br/>
      *    如果容器表及配置属性表均存在,并且配置属性表版本号一致则返回true
      *    如果均不存在则返回false
      *    如果其中一张表存在,另一张不存在,或版本号不一致,则抛出配置容器初始化异常
     * @return
     */
    private boolean configPropertyItemTableIsExist() {
        boolean propertiesTableIsExist = false;
        boolean contextTableIsExist = false;
        try {
            this.jdbcTemplate.queryForInt("SELECT COUNT(1) FROM " + this.contextTableName);
            logger.info("  检测到容器表:{}已存在.", this.contextTableName);
            propertiesTableIsExist = true;
        } catch (DataAccessException e) {
            logger.warn("checkTableIsExist:{},exception:{}.", new Object[] {
                    this.contextTableName, e });
        }
        try {
            this.jdbcTemplate.queryForInt("SELECT COUNT(1) FROM " + this.propertiesTableName);
            contextTableIsExist = true;
            logger.info("  检测到容器属性表:{}已存在.", this.propertiesTableName);
        } catch (DataAccessException e) {
            logger.warn("checkTableIsExist:{},exception:{}.", new Object[] {
                    this.propertiesTableName, e });
        }
        
        //判断是否两张表都存在或都不存在,如果仅有其中一张表存在,则抛出异常<br/>
        if (propertiesTableIsExist && contextTableIsExist) {
            return true;
        } else if (!propertiesTableIsExist && !contextTableIsExist) {
            return false;
        }
        throw new ConfigContextInitException(
                "配置容器表异常:propertiesTable:'{}' {},contextTable:'{}' {}",
                new Object[] { this.propertiesTableName,
                        propertiesTableIsExist ? "已创建" : "未创建",
                        this.contextTableName,
                        contextTableIsExist ? "已创建" : "未创建" });
    }
    
    /**
     * 创建属性配置项表
     */
    private void createConfigPropertyItemTable() {
        //创建容器表
        String createContextTableSql = sqlProp.getProperty("createContextTable");
        createContextTableSql = MessageFormatter.arrayFormat(createContextTableSql,
                new Object[] { this.contextTableName })
                .getMessage();
        jdbcTemplate.execute(createContextTableSql);
        
        //创建容器属性表
        String createPropertieTable = sqlProp.getProperty("createPropertiesTable");
        createPropertieTable = MessageFormatter.arrayFormat(createPropertieTable,
                new Object[] { this.propertiesTableName })
                .getMessage();
        jdbcTemplate.execute(createPropertieTable);
    }
    
    
    
    /**
     * @param configPropertyItem
     */
//    public void insertConfigPropertyItem(ConfigPropertyItem configPropertyItem) {
//        //从propertiues中得到SQL语句
//        String insertSql = sqlProp.getProperty("insert");
//        
//        //为查询语句中添加表名
//        querySql = MessageFormatter.arrayFormat(querySql,
//                new Object[] { this.propertiesTableName }).getMessage();
//        
//        //用一个List来存放SQL语句中的绑定变量
//        List<Object> paramsList = new ArrayList<Object>();
//        //通过反射得到变量的属性名
//        List<Object> paramsNameList = this.getAllFieldName(configPropertyItem);
//        //通过反射得到变量的类型
//        List<Object> paramsTypeList = this.getAllFieldType(configPropertyItem);
//        //通过反射调用get方法得到变量的值
//        List<Object> paramsValueList = this.getAllFieldValue(configPropertyItem);
//
//        //把得到的值按顺序放入到绑定变量List中
//        paramsList.addAll(paramsNameList);
//        paramsList.addAll(paramsValueList);
//        
//        //执行插入
//        //this.jdbcTemplate.update(querySql, paramsList,);
//        this.jdbcTemplate.update(querySql, paramsList, paramsTypeList);
//    }
    
    
    
    
    /**
     * @param configPropertyItem
     */
    @Override
    public void insertConfigPropertyItem(ConfigPropertyItem configPropertyItem) {
        Map<String, Object> itemBeanMap = describe(configPropertyItem);
        this.namedParameterJdbcTemplate.update(insertSql, itemBeanMap);

    }
    
    /**
     * @param configPropertyItemList
     */
    @SuppressWarnings("unchecked")
    public void batchInsertConfigPropertyItem(
            List<ConfigPropertyItem> configPropertyItemList) {
        Map<String, Object>[] paramMapArr = new Map[configPropertyItemList.size()];
        int index = 0;
        for(ConfigPropertyItem configPropertyItemTemp : configPropertyItemList){
            paramMapArr[index] = describe(configPropertyItemTemp);
            index++;
        }
        this.namedParameterJdbcTemplate.batchUpdate(insertSql, paramMapArr);
    }
    
    /**
     * @param condition
     * @return
     */
    public int deleteConfigPropertyItem(ConfigPropertyItem condition) {
        //从propertiues中得到SQL语句
        String querySql = sqlProp.getProperty("delete");
        //为查询语句中添加表名
        querySql = MessageFormatter.arrayFormat(querySql,
                new Object[] { this.propertiesTableName }).getMessage();
        //为SQL语句添加刪除条件
        //querySql = this.addCondition2Sql(querySql, condition, "where");
        
        //返回删除的行数
        int affectRows = this.jdbcTemplate.update(querySql,
                configPropertyItemRowMapper);
        
        return affectRows;
    }
    
    /**
     * @param condition
     * @return
     */
    public ConfigPropertyItem findConfigPropertyItem(
            ConfigPropertyItem condition) {
        
        String id = condition.getId();
        if (StringUtils.isEmpty(id)) {
            throw new ConfigContextInitException("查询条件有误,其中不包含ID");
        }
        //从propertiues中得到查询语句
        String querySql = sqlProp.getProperty("find");
        //为查询语句中添加表名
        querySql = MessageFormatter.arrayFormat(querySql,
                new Object[] { this.propertiesTableName }).getMessage();
        
        //按ID查询
        List<ConfigPropertyItem> resList = this.jdbcTemplate.query(querySql,
                new Object[] { id },
                configPropertyItemRowMapper);
        
        //由于按ID查询应该只返回一个结果,所以需要对其作判断
        //如果返回多条就抛出异常
        if (resList.size() > 1) {
            throw new ConfigContextInitException("根据ID查询查询出了多条记录,查询条件有误");
        }
        if (resList.size() == 1) {
            return resList.get(0);
        }
        return null;
    }
    

    
    /**
     * @param params
     * @return
     */
    public List<ConfigPropertyItem> queryConfigPropertyItemList(
            Map<String, Object> params) {
        //从propertiues中得到查询语句
        String querySql = sqlProp.getProperty("query");
        //为SQL语句中添加表名
        querySql = MessageFormatter.arrayFormat(querySql,
                new Object[] { this.propertiesTableName }).getMessage();
        //为SQL语句添加查询条件
        querySql = this.addCondition2Sql(querySql, params, "where");
        
        List<ConfigPropertyItem> resList = this.jdbcTemplate.query(querySql,
                configPropertyItemRowMapper);
        
        return resList;
    }
    
    /**
     * 
      *为SQL语句添加条件限定部分
      *<功能详细描述>
      * @param originalSql
      * @param params
      * @param prefix
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    private String addCondition2Sql(String originalSql,
            Map<String, Object> params, String prefix) {
        //将查询语句封装为StringBuilder以便添加查询条件
        StringBuilder querySqlSB = new StringBuilder(originalSql);
        //判断条件集合是否为空
        if (params != null && CollectionUtils.isNotEmpty(params.keySet())) {
            //如果条件集合不为空,则开始为sql语句添加条件,先把前缀加上
            querySqlSB.append(" " + prefix);
            for (String key : params.keySet()) {
                querySqlSB.append(" ");
                querySqlSB.append(key);
                querySqlSB.append("=");
                querySqlSB.append("'");
                querySqlSB.append(params.get(key));
                querySqlSB.append("'");
                querySqlSB.append(" ");
                querySqlSB.append("and");
            }
        }
        String targetSql = querySqlSB.toString();
        if (targetSql.endsWith("and")) {
            targetSql = targetSql.substring(0, targetSql.lastIndexOf("and"));
        }
        return targetSql;
    }
    
    /**
     * @param params
     * @param orderList
     * @return
     */
    public List<ConfigPropertyItem> queryConfigPropertyItemList(
            Map<String, Object> params, List<Order> orderList) {
        // TODO Auto-generated method stub
        return null;
    }
    
    /**
     * @param updateRowMap
     * @return
     */
    public int updateConfigPropertyItem(Map<String, Object> updateRowMap) {
        //从propertiues中得到SQL语句
        String querySql = sqlProp.getProperty("update");
        //SQL语句中添加表名
        querySql = MessageFormatter.arrayFormat(querySql,
                new Object[] { this.propertiesTableName }).getMessage();
        
        //为SQL语句添加更新项信息
        querySql = this.addUpdateItem2Sql(querySql, updateRowMap);
        
        //返回删除的行数
        //        int affectRows = this.jdbcTemplate.update(querySql,
        //                new Object[] { configPropertyItem.getId() });
        int affectRows = 0;
        
        return affectRows;
    }
    
    /**
     * 
      *为SQL语句添加更新项信息
      *<功能详细描述>
      * @param querySql
      * @param updateRowMap
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    private String addUpdateItem2Sql(String querySql,
            Map<String, Object> updateRowMap) {
        //得到SQL语句的前半段,即where之前的部分
        StringBuilder preSql = new StringBuilder(querySql.substring(0,
                querySql.toLowerCase().indexOf("set")));
        StringBuilder sufSql = new StringBuilder(
                querySql.substring(querySql.toLowerCase().indexOf("where")));
        
        for (String key : updateRowMap.keySet()) {
            preSql.append(" ");
            preSql.append(key);
            preSql.append("=");
            preSql.append("'");
            preSql.append(updateRowMap.get(key));
            preSql.append("'");
            preSql.append(", ");
        }
        String preSqlStr = preSql.toString();
        //如果末尾包含一个逗号,则去掉
        
        if (preSqlStr.endsWith(",")) {
            preSqlStr = preSqlStr.substring(0, preSqlStr.lastIndexOf(","));
        }
        
        return preSql + sufSql.toString();
    }
    
    /**
      * 获取配置属性项实例映射到的map实例<BR/>
      * <功能详细描述>
      * @param configPropertyItem
      * @param itemBeanMap
      * @return [参数说明]
      * 
      * @return Map<String,?> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    private Map<String, Object> describe(ConfigPropertyItem configPropertyItem) {
        Map<String, Object> itemBeanMap = new HashMap<String, Object>();
        MetaObject metaObject = MetaObject.forObject(configPropertyItem);
        for(String getterTemp : metaObject.getGetterNames()){
            Object value = metaObject.getValue(getterTemp);
            //boolean类型变量特殊处理
            if(value != null && value instanceof Boolean){
                Boolean booleanValue = (Boolean)value;
                itemBeanMap.put(getterTemp,booleanValue ? "1" : "0");
            }
            itemBeanMap.put(getterTemp, value);
        }
        return itemBeanMap;
    }
    
    /** 
     * ConfigPropertyItem的RowMapper实现
     * 用于处理提取的数据转换为bean的过程
     */
    private static RowMapper<ConfigPropertyItem> configPropertyItemRowMapper = new RowMapper<ConfigPropertyItem>() {
        /**
         * @param rs
         * @param rowNum
         * @return
         * @throws SQLException
         */
        public ConfigPropertyItem mapRow(ResultSet rs, int rowNum)
                throws SQLException {
            ConfigPropertyItem item = new ConfigPropertyItem();
            item.setId(rs.getString("ID"));
            item.setName(rs.getString("NAME"));
            item.setKey(rs.getString("KEY"));
            item.setValue(rs.getString("VALUE"));
            item.setDescription(rs.getString("DESCRIPTION"));
            item.setConfigResourceId(rs.getString("CONFIGRESOURCEID"));
            item.setEditAble(rs.getBoolean("EDITABLE"));
            item.setParentKey(rs.getString("PARENTKEY"));
            item.setValid(rs.getBoolean("VALID"));
            item.setViewAble(rs.getBoolean("VIEWEXPRESSION"));
            item.setViewExpression(rs.getString("VIEWEXPRESSIO"));
            item.setValidateExpression(rs.getString("VALIDATEEXPRESSION"));
            item.setLastUpdateDate(rs.getDate("LASTUPDATEDATE"));
            item.setCreateDate(rs.getDate("CREATEDATE"));
            return item;
        }
    };
}
