package com.tx.component.statistical.mybatismapping;

import com.tx.component.statistical.mapping.SqlMapperItem;
import com.tx.core.util.MessageUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.ibatis.session.Configuration;

import java.util.List;
import java.util.Map;

/**
 * <br/>
 *
 * @author XRX
 * @version [版本号, 2017/11/22]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class StatisticalMapperAssistantRepository {
    public final String countSuffix = "Count";
    public String statisticalSuffix = "Statistical";
    private static String COUNT_SCRIPT_FORMATTER = "<script> SELECT COUNT(1) AS CNT FROM ({}) TB </script>";
    private static String STATISTICAL_SCRIPT_FORMATTER = "<script> SELECT ${statisticalColumn} FROM  ( \n {} " +
            " <if test=\"@com.tx.core.util.OgnlUtils@isNotEmpty(limitStart)\"> \n" +
            " <![CDATA[ LIMIT #{limitStart} , #{limitSize} ]]> \n" +
            " </if> \n" +
            " ) TB </script>";


    private static final String SCRIPT_FORMATTER = "<script>{}</script>";

    private Configuration configuration;

    private Map<String, StatisticalMapperAssistant> assistantMap = new HashedMap();

    public StatisticalMapperAssistantRepository(Configuration configuration) {
        this.configuration = configuration;
    }

    public StatisticalMapperAssistant doBuilderAssistant(String namespace) {
        StatisticalMapperAssistant statisticalMapperAssistant = new StatisticalMapperAssistant(configuration, namespace);

        return statisticalMapperAssistant;
    }

    public void assistant(List<SqlMapperItem> srSqlItems) {
        for (SqlMapperItem temp : srSqlItems) {
            assistant(temp);
        }
    }

    public StatisticalMapperAssistant assistant(SqlMapperItem srSqlItem) {
        String namespace = srSqlItem.getNamespace();
        StatisticalMapperAssistant assistant = assistantMap.get(namespace);
        if (assistant == null) {
            assistant = doBuilderAssistant(namespace);
            assistantMap.put(namespace, assistant);
        }

        synchronized (assistantMap) {
            assistant = doBuilderAssistant(namespace);
            assistant.publishMapperStatement(srSqlItem.getSqlCommandType(),
                    srSqlItem.getId(),
                    srSqlItem.getSqlScript(),
                    srSqlItem.getParameterType(),
                    srSqlItem.getReturnType());

            if (srSqlItem.isNeedStatisticalScript()) {
                String countSql = MessageUtils.format(COUNT_SCRIPT_FORMATTER, srSqlItem.getSqlScript());
                assistant.publishMapperStatement(srSqlItem.getSqlCommandType(),
                        srSqlItem.getId() + "Count",
                        countSql,
                        srSqlItem.getParameterType(),
                        Integer.class);

                String statistical = MessageUtils.format(STATISTICAL_SCRIPT_FORMATTER, srSqlItem.getSqlScript());
                assistant.publishMapperStatement(srSqlItem.getSqlCommandType(),
                        srSqlItem.getId() + "Statistical",
                        statistical,
                        srSqlItem.getParameterType(),
                        srSqlItem.getReturnType());
            }

            assistantMap.put(namespace, assistant);
        }
        return assistant;
    }


    public Configuration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    public Map<String, StatisticalMapperAssistant> getAssistantMap() {
        return assistantMap;
    }

    public void setAssistantMap(Map<String, StatisticalMapperAssistant> assistantMap) {
        this.assistantMap = assistantMap;
    }
}
