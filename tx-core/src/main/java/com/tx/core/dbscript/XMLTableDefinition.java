/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-12-18
 * <修改描述:>
 */
package com.tx.core.dbscript;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import com.thoughtworks.xstream.XStream;
import com.tx.core.TxConstants;
import com.tx.core.dbscript.config.DataSourceScriptConfig;
import com.tx.core.dbscript.config.TableDefinitionConfig;
import com.tx.core.dbscript.config.UpdateTableScriptConfig;
import com.tx.core.dbscript.model.DataSourceTypeEnum;
import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.exceptions.util.ExceptionWrapperUtils;
import com.tx.core.util.XstreamUtils;

/**
 * 基于脚本文件的表定义<br/>
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2013-12-18]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class XMLTableDefinition implements TableDefinition, InitializingBean {
    
    /** tableDefintionConfig解析器 */
    private static XStream TableDefinitionConfigXstream = XstreamUtils.getXstream(TableDefinitionConfig.class);
    
    /** 脚本所在路径 */
    private String location;
    
    /** 资源加载器 */
    private ResourceLoader resourceLoader = new DefaultResourceLoader();
    
    /** 表定义配置 */
    private TableDefinitionConfig tableDefinitionConfig;
    
    /** <默认构造函数> */
    public XMLTableDefinition() {
    }
    
    /** <默认构造函数> */
    public XMLTableDefinition(String location) {
        this.location = location;
        //解析
        this.afterPropertiesSet();
    }
    
    /**
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() {
        AssertUtils.notEmpty(this.location, "location is empty.");
        
        Resource scriptResourceTemp = resourceLoader.getResource(this.location);
        AssertUtils.isExist(scriptResourceTemp,
                "scriptResource:{} is not exist.",
                new Object[] { scriptResourceTemp });
        try {
            this.tableDefinitionConfig = (TableDefinitionConfig) TableDefinitionConfigXstream.fromXML(scriptResourceTemp.getFile());
        } catch (IOException e) {
            throw ExceptionWrapperUtils.wrapperIOException(e,
                    "parse resource:{} exception.{}",
                    new Object[] { scriptResourceTemp, e });
        }
    }
    
    /**
      * 转换表名<br/>
      *<功能详细描述>
      * @param tableName [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    protected String convertTableName(String tableName) {
        return tableName;
    }
    
    /**
     * @return
     */
    @Override
    public String tableName() {
        String tableName = this.tableDefinitionConfig.getTableName();
        AssertUtils.notEmpty(tableName, "tableName is empty.");
        
        tableName = convertTableName(tableName);
        return tableName;
    }
    
    /**
     * @return
     */
    @Override
    public String tableVersion() {
        String tableVersion = this.tableDefinitionConfig.getTableVersion();
        
        return tableVersion;
    }
    
    /**
      * 转换创建表脚本<br/>
      *<功能详细描述>
      * @param createTableScript
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    protected String convertCreateTableScript(String createTableScript) {
        return createTableScript;
    }
    
    /**
     * @param dataSourceType
     * @return
     */
    @Override
    public String createTableScript(DataSourceTypeEnum dataSourceType) {
        DataSourceScriptConfig dataSourceScriptConfig = null;
        for (DataSourceScriptConfig dataSourceScriptConfigTemp : this.tableDefinitionConfig.getDataSourceScriptConfigs()) {
            if (dataSourceType.equals(dataSourceScriptConfigTemp.getDataSourceType())) {
                dataSourceScriptConfig = dataSourceScriptConfigTemp;
            }
        }
        AssertUtils.notNull(dataSourceScriptConfig,
                "dataSourceType:{} dataSourceScriptConfig is not Exist.",
                new Object[] { dataSourceType });
        
        String createTableScript = dataSourceScriptConfig.getCreateTableScript();
        createTableScript = convertCreateTableScript(createTableScript);
        return createTableScript;
    }
    
    /**
     * 转换创建表脚本<br/>
     *<功能详细描述>
     * @param createTableScript
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    protected String convertUpdateTableScript(String updateTableScript) {
        return updateTableScript;
    }
    
    /**
     * @param currentVersion 可以为空
     * @param dataSourceTyp
     * @return
     */
    @Override
    public String updateTableScript(String sourceTableVersion,
            DataSourceTypeEnum dataSourceType) {
        DataSourceScriptConfig dataSourceScriptConfig = null;
        for (DataSourceScriptConfig dataSourceScriptConfigTemp : this.tableDefinitionConfig.getDataSourceScriptConfigs()) {
            if (dataSourceType.equals(dataSourceScriptConfigTemp.getDataSourceType())) {
                dataSourceScriptConfig = dataSourceScriptConfigTemp;
            }
        }
        AssertUtils.notNull(dataSourceScriptConfig,
                "dataSourceType:{} dataSourceScriptConfig is not Exist.",
                new Object[] { dataSourceType });
        
        String updateTableScript = getUpdateTableScriptByDataSourceScriptConfig(dataSourceScriptConfig,
                sourceTableVersion);
        updateTableScript = convertUpdateTableScript(updateTableScript);
        return updateTableScript;
    }
    
    /**
      * 获取更新表脚本<br/>
      *<功能详细描述>
      * @param dataSourceScriptConfig
      * @param sourceTableVersion
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    private String getUpdateTableScriptByDataSourceScriptConfig(
            DataSourceScriptConfig dataSourceScriptConfig,
            String sourceTableVersion) {
        if (StringUtils.isEmpty(sourceTableVersion)) {
            return dataSourceScriptConfig.getDefaultUpdateTableScript();
        }
        StringBuilder sb = new StringBuilder(TxConstants.INITIAL_STR_LENGTH);
        Map<String, UpdateTableScriptConfig> targetVersion2UpdateTableScriptConfigMap = new HashMap<String, UpdateTableScriptConfig>();
        if (!CollectionUtils.isEmpty(dataSourceScriptConfig.getUpdateTableScripts())) {
            for (UpdateTableScriptConfig temp : dataSourceScriptConfig.getUpdateTableScripts()) {
                targetVersion2UpdateTableScriptConfigMap.put(temp.getTargetVersion(),
                        temp);
            }
        }
        
        //如果目标版本不包含当前版本
        if (!targetVersion2UpdateTableScriptConfigMap.containsKey(tableVersion())) {
            return dataSourceScriptConfig.getDefaultUpdateTableScript();
        }
        
        //如果包含则进行递归查找
        LinkedList<UpdateTableScriptConfig> updateTableList = new LinkedList<UpdateTableScriptConfig>();
        UpdateTableScriptConfig updateTableScriptConfigTemp = targetVersion2UpdateTableScriptConfigMap.get(tableVersion());
        boolean flag = false;
        do {
            flag = false;
            String targetVersion = updateTableScriptConfigTemp.getTargetVersion();
            String sourceVersion = updateTableScriptConfigTemp.getSourceVersion();
            //targetVersion不能为空
            AssertUtils.notEmpty(targetVersion,
                    "tableName:{} updateTableScript.target:{} targetVersion is empty.",
                    new Object[] { tableName(), targetVersion });
            //sourceVersion不能为空
            AssertUtils.notEmpty(targetVersion,
                    "tableName:{} updateTableScript.target:{} sourceVersion is empty.",
                    new Object[] { tableName(), targetVersion });
            //sourceVersion与targetVersion不能相同
            AssertUtils.isTrue(!targetVersion.equals(sourceVersion),
                    "tableName:{} updateTableScript.target:{} targetVersion equals sourceVersion.",
                    new Object[] { tableName(), targetVersion });
            
            //将脚本进行添加
            updateTableList.add(0, updateTableScriptConfigTemp);
            //如果当前版本为sourceVersion则根据链表直接生成升级脚本<br/>
            if (sourceTableVersion.equals(sourceVersion)) {
                for (UpdateTableScriptConfig updateTempScriptTemp : updateTableList) {
                    sb.append(updateTempScriptTemp.getScript()).append("\n");
                    return sb.toString();
                }
            } else if (targetVersion2UpdateTableScriptConfigMap.containsKey(sourceVersion)) {
                updateTableScriptConfigTemp = targetVersion2UpdateTableScriptConfigMap.get(sourceVersion);
                flag = true;
            } else {
                flag = false;
            }
        } while (flag);
        
        return dataSourceScriptConfig.getDefaultUpdateTableScript();
    }
    
    /**
     * @return 返回 location
     */
    public String getLocation() {
        return location;
    }
    
    /**
     * @param 对location进行赋值
     */
    public void setLocation(String location) {
        this.location = location;
    }
    
    /**
     * @return 返回 resourceLoader
     */
    public ResourceLoader getResourceLoader() {
        return resourceLoader;
    }
    
    /**
     * @param 对resourceLoader进行赋值
     */
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }
    
    /**
     * @return 返回 tableDefinitionConfig
     */
    public TableDefinitionConfig getTableDefinitionConfig() {
        return tableDefinitionConfig;
    }
    
    /**
     * @param 对tableDefinitionConfig进行赋值
     */
    public void setTableDefinitionConfig(
            TableDefinitionConfig tableDefinitionConfig) {
        this.tableDefinitionConfig = tableDefinitionConfig;
    }
}
