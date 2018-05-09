package com.tx.component.statistical.mapping;

import org.apache.ibatis.mapping.SqlCommandType;

/**
 * 统计报表--查询语句
 *
 * @author XRX
 * @version [版本号, 2017/11/22]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class SqlMapperItem extends BaseItem {
//    private String fullId;
    private String namespace;
    private String id;
    private String sqlScript;
    private Class<?> returnType;
    private Class<?> parameterType;
    private SqlCommandType sqlCommandType = SqlCommandType.SELECT;
    private String datasourceId;
    private boolean needStatisticalScript = true;

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSqlScript() {
        return sqlScript;
    }

    public void setSqlScript(String sqlScript) {
        this.sqlScript = sqlScript;
    }

    public Class<?> getReturnType() {
        return returnType;
    }

    public void setReturnType(Class<?> returnType) {
        this.returnType = returnType;
    }

    public SqlCommandType getSqlCommandType() {
        return sqlCommandType;
    }

    public void setSqlCommandType(SqlCommandType sqlCommandType) {
        this.sqlCommandType = sqlCommandType;
    }

    public Class<?> getParameterType() {
        return parameterType;
    }

    public void setParameterType(Class<?> parameterType) {
        this.parameterType = parameterType;
    }

    public String getDatasourceId() {
        return datasourceId;
    }

    public void setDatasourceId(String datasourceId) {
        this.datasourceId = datasourceId;
    }

    public String getFullId() {
        return namespace + "." + id;
    }


    public boolean isNeedStatisticalScript() {
        return needStatisticalScript;
    }

    public void setNeedStatisticalScript(boolean needStatisticalScript) {
        this.needStatisticalScript = needStatisticalScript;
    }
}
