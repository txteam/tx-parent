/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2014年3月1日
 * <修改描述:>
 */
package com.tx.core.generator.util;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.type.JdbcType;

import com.tx.core.TxConstants;
import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.generator.model.FieldView;
import com.tx.core.generator.model.SqlMapColumn;
import com.tx.core.jdbc.sqlsource.SqlSource;
import com.tx.core.reflection.JpaColumnInfo;
import com.tx.core.reflection.JpaMetaClass;
import com.tx.core.reflection.ReflectionUtils;

/**
 * <功能简述>
 * <功能详细描述>
 *
 * @author  Administrator
 * @version  [版本号, 2014年3月1日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class GeneratorUtils {

    /**
     * 生成页面显示行信息
     *<功能详细描述>
     * @param jpaMetaClass
     * @param sqlSource
     * @param uniqueGetterNamesArray
     * @return [参数说明]
     *
     * @return Map<String,FieldView> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static Map<String, FieldView> generateFieldViewMapping(
            JpaMetaClass<?> jpaMetaClass, SqlSource<?> sqlSource,
            String[][] uniqueGetterNamesArray) {
        Map<String, FieldView> fieldViewMap = new HashMap<String, FieldView>();
        String pkFieldName = jpaMetaClass.getPkGetterName();
        Set<String> updateAbleGetterSet = sqlSource.getUpdateAblePropertyNames();
        Map<String, String[]> uniqueGetterNameMap = new HashMap<String, String[]>();

        if (!ArrayUtils.isEmpty(uniqueGetterNamesArray)) {
            for (String[] temp : uniqueGetterNamesArray) {
                if (ArrayUtils.isEmpty(temp)) {
                    continue;
                }
                for (String uniqueGetterNameTemp : temp) {
                    if (!StringUtils.isEmpty(uniqueGetterNameTemp)) {
                        uniqueGetterNameMap.put(uniqueGetterNameTemp, temp);
                    }
                }
            }
        }
        String entitySimpleName = jpaMetaClass.getEntitySimpleName();
        String uncapitalizeEntitySimpleName = StringUtils.uncapitalize(entitySimpleName);

        for (Entry<String, JpaColumnInfo> entryTemp : jpaMetaClass.getGetter2columnInfoMapping()
                .entrySet()) {
            FieldView fieldView = new FieldView();
            JpaColumnInfo jpaColumnInfo = entryTemp.getValue();

            fieldView.setDate(Date.class.equals(jpaColumnInfo.getGetterType())
                    || Timestamp.class.equals(jpaColumnInfo.getGetterType())
                    || java.sql.Date.class.equals(jpaColumnInfo.getGetterType()));

            fieldView.setId(pkFieldName.equals(entryTemp.getKey()));
            fieldView.setFieldName(entryTemp.getKey());
            fieldView.setFieldComment(jpaColumnInfo.getColumnComment());
            if (!jpaColumnInfo.isSimpleType()) {
                fieldView.setSimpleType(false);
                fieldView.setForeignKeyFieldName(jpaColumnInfo.getForeignKeyGetterName());
            } else {
                fieldView.setSimpleType(true);
            }
            fieldView.setJavaType(jpaColumnInfo.getGetterType());
            fieldView.setRequired(false);
            fieldView.setUpdateAble(updateAbleGetterSet.contains(entryTemp.getKey()));
            if (uniqueGetterNameMap.containsKey(entryTemp.getKey())) {
                fieldView.setRequired(true);
                StringBuilder validateMethodNameSb = new StringBuilder(
                        TxConstants.INITIAL_STR_LENGTH);
                validateMethodNameSb.append("validate");
                int index = 0;
                String[] uniqueGetterNames = uniqueGetterNameMap.get(entryTemp.getKey());
                for (String temp : uniqueGetterNames) {
                    validateMethodNameSb.append(StringUtils.capitalize(temp));
                    if (++index < uniqueGetterNames.length) {
                        validateMethodNameSb.append("And");
                    }
                }
                validateMethodNameSb.append("IsExist.action, ");
                for (String temp : uniqueGetterNames) {
                    validateMethodNameSb.append(temp);
                    if (++index < uniqueGetterNames.length) {
                        validateMethodNameSb.append(", ");
                    }
                }
                validateMethodNameSb.append(", ").append(pkFieldName);
                fieldView.setValidateExpression("required;;remote[${contextPath}/"
                        + uncapitalizeEntitySimpleName
                        + "/"
                        + validateMethodNameSb.toString() + "]");
            }

            fieldViewMap.put(entryTemp.getKey(), fieldView);
        }

        return fieldViewMap;
    }

    /**
     * 生成查询条件映射<br/>
     *<功能详细描述>
     * @param jpaMetaClass
     * @param sqlSource
     * @return [参数说明]
     *
     * @return Map<String,String> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static Map<String, String> generateQueryConditionMap(
            JpaMetaClass<?> jpaMetaClass, SqlSource<?> sqlSource) {
        Map<String, String> resMap = new HashMap<String, String>();

        Map<String, String> queryConditionSqlMapping = sqlSource.getQueryConditionKey2SqlMapping();
        Map<String, JdbcType> queryConditionTypeMapping = sqlSource.getQueryConditionKey2JdbcTypeMapping();
        //Map<String, QueryConditionInfo> queryConditionInfoMapping = sqlSource.getQueryConditionKey2ConditionInfoMapping();

        for (Entry<String, String> entryTemp : queryConditionSqlMapping.entrySet()) {

            String queryConditionKey = entryTemp.getKey();
            //QueryConditionInfo queryConditionInfoTemp = queryConditionInfoMapping.get(queryConditionKey);;
            JdbcType jdbcType = queryConditionTypeMapping.get(queryConditionKey);
            AssertUtils.notNull(jdbcType, "jdbcType is null.");
            //            String replaceValue = "#{" + queryConditionKey + ",jdbcType="
            //                    + jdbcType.toString() + "}";
            String replaceValue = "#{" + queryConditionKey + "}";
            String queryCondition = StringUtils.replace(entryTemp.getValue(),
                    "?",
                    replaceValue);
            /*
            if(queryConditionInfoTemp != null){
                if(QueryConditionTypeEnum.UNEQUAL.equals(queryConditionInfoTemp.getQueryConditionType())
                        || QueryConditionTypeEnum.GREATER.equals(queryConditionInfoTemp.getQueryConditionType())
                        || QueryConditionTypeEnum.GREATER_OR_EQUAL.equals(queryConditionInfoTemp.getQueryConditionType())
                        || QueryConditionTypeEnum.LESS.equals(queryConditionInfoTemp.getQueryConditionType())
                        || QueryConditionTypeEnum.LESS_OR_EQUAL.equals(queryConditionInfoTemp.getQueryConditionType())){
                    queryCondition = "<![CDATA[ " + queryCondition + " ]]>";
                }
            }
            */
            resMap.put(queryConditionKey, queryCondition);
        }
        return resMap;
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
    public static <TYPE> List<SqlMapColumn> generateSqlMapColumnList(
            JpaMetaClass<TYPE> jpaMetaClass) {
        List<SqlMapColumn> columnList = generateSqlMapColumnList(jpaMetaClass,
                true);
        return columnList;
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
    public static <TYPE> List<SqlMapColumn> generateSqlMapColumnList(
            JpaMetaClass<TYPE> jpaMetaClass, boolean isColumnNameToUpCase) {
        List<SqlMapColumn> columnList = new ArrayList<SqlMapColumn>();
        //生成对应需要的列关系
        //Set<String> getterNameList = jpaMetaClass.getGetterNames();
        //Set<String> setterNameList = jpaMetaClass.getSetterNames();
        String idPropertyName = jpaMetaClass.getPkGetterName();

        for (Entry<String, JpaColumnInfo> entryTemp : jpaMetaClass.getGetter2columnInfoMapping()
                .entrySet()) {
            String getterName = entryTemp.getKey();
            JpaColumnInfo jpaColumnInfo = entryTemp.getValue();

            SqlMapColumn columnTemp = null;
            if (jpaColumnInfo.isSimpleType()) {
                columnTemp = new SqlMapColumn(true,
                        jpaColumnInfo.getGetterName(),
                        isColumnNameToUpCase ? jpaColumnInfo.getColumnName()
                                .toUpperCase() : jpaColumnInfo.getColumnName(),
                        jpaColumnInfo.getRealGetterType(), null);
            } else {
                columnTemp = new SqlMapColumn(false,
                        jpaColumnInfo.getGetterName(),
                        isColumnNameToUpCase ? jpaColumnInfo.getColumnName()
                                .toUpperCase() : jpaColumnInfo.getColumnName(),
                        jpaColumnInfo.getRealGetterType(),
                        jpaColumnInfo.getForeignKeyGetterName());
            }

            if (idPropertyName.equals(getterName)) {
                columnTemp.setId(true);
            }

            columnTemp.setGetterMethodSimpleName(ReflectionUtils.getGetMethodNameByGetterNameAndType(jpaColumnInfo.getGetterName(),
                    jpaColumnInfo.getGetterType()));

            columnList.add(columnTemp);
        }

        //Collections.sort(columnList, columnComparator);
        return columnList;
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

}
