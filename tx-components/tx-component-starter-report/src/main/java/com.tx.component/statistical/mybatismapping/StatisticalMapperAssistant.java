package com.tx.component.statistical.mybatismapping;

import com.tx.core.mybatis.builder.MapperBuilderAssistantExtention;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.session.Configuration;

import java.util.Map;

/**
 * <br/>
 *
 * @author XRX
 * @version [版本号, 2017/11/22]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class StatisticalMapperAssistant {
    /**
     * 配置器
     */
    protected final Configuration configuration;

    /**
     * namespace
     */
    protected final String namespace;

    protected MapperBuilderAssistantExtention mapperBuilderAssistantExtention;

    public StatisticalMapperAssistant(Configuration configuration, String namespace) {
        this.configuration = configuration;
        this.namespace = namespace;

        if (this.configuration != null) {
            if (this.mapperBuilderAssistantExtention == null) {
                this.mapperBuilderAssistantExtention = new MapperBuilderAssistantExtention(
                        this.configuration, this.namespace);
                this.mapperBuilderAssistantExtention.setCurrentNamespace(this.namespace);
            }
        }
    }

    public void publishMapperStatement(SqlCommandType sqlCommandType, String mappedStatementId, String sql, Class<?> parameterType, Class<?> resultParameterType) {
        if(!sql.startsWith("<script>")){
            sql ="<script>"+ sql +"</script>";
        }
        this.mapperBuilderAssistantExtention.saveMappedStatement(mappedStatementId,
                sqlCommandType,
                sql,
                parameterType,
                resultParameterType
        );
    }

    public void publishQueryMapperStatement(String mappedStatementId, String sql, Class<?> parameterType, Class<?> resultParameterType) {
        this.mapperBuilderAssistantExtention.saveMappedStatement(mappedStatementId,
                SqlCommandType.SELECT,
                sql,
                parameterType,
                resultParameterType
        );
    }

    public void publishQueryListMapperStatement(String mappedStatementId, String sql, Class<?> resultParameterType) {
        this.mapperBuilderAssistantExtention.saveMappedStatement(mappedStatementId,
                SqlCommandType.SELECT,
                sql,
                Map.class,
                resultParameterType
        );
    }

    public void publishCountMapperStatement(String mappedStatementId, String sql) {
        this.mapperBuilderAssistantExtention.saveMappedStatement(mappedStatementId,
                SqlCommandType.SELECT,
                sql,
                Map.class,
                Integer.class
        );
    }


    public Configuration getConfiguration() {
        return configuration;
    }

    public String getNamespace() {
        return namespace;
    }
}
