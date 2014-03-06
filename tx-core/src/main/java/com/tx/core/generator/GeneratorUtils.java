/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2014年3月1日
 * <修改描述:>
 */
package com.tx.core.generator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.ibatis.type.JdbcType;
import org.h2.util.StringUtils;

import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.generator.model.SqlMapColumn;
import com.tx.core.jdbc.model.QueryConditionInfo;
import com.tx.core.jdbc.model.QueryConditionTypeEnum;
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
        Map<String, QueryConditionInfo> queryConditionInfoMapping = sqlSource.getQueryConditionKey2ConditionInfoMapping();
        
        for (Entry<String, String> entryTemp : queryConditionSqlMapping.entrySet()) {
            
            String queryConditionKey = entryTemp.getKey();
            QueryConditionInfo queryConditionInfoTemp = queryConditionInfoMapping.get(queryConditionKey);;
            JdbcType jdbcType = queryConditionTypeMapping.get(queryConditionKey);
            AssertUtils.notNull(jdbcType, "jdbcType is null.");
            String replaceValue = "#{" + queryConditionKey + ",jdbcType="
                    + jdbcType.toString() + "}";
            String queryCondition = StringUtils.replaceAll(entryTemp.getValue(),
                    "?",
                    replaceValue);
            if(queryConditionInfoTemp != null){
                if(QueryConditionTypeEnum.UNEQUAL.equals(queryConditionInfoTemp.getQueryConditionType())){
                    queryCondition = "<![CDATA[ " + queryCondition + " ]]>";
                }
            }
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
                        jpaColumnInfo.getColumnName(),
                        jpaColumnInfo.getRealGetterType(), null);
            } else {
                columnTemp = new SqlMapColumn(false,
                        jpaColumnInfo.getGetterName(),
                        jpaColumnInfo.getColumnName(),
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
        
        Collections.sort(columnList, columnComparator);
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
