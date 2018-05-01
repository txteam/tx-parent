//package com.tx.test.ddlutil;
//import java.lang.reflect.Field;
//import java.math.BigDecimal;
//import java.sql.Time;
//import java.sql.Timestamp;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//
//import org.apache.commons.lang3.reflect.FieldUtils;
//import org.apache.ibatis.type.TypeHandlerRegistry;
//import org.slf4j.helpers.MessageFormatter;
//import org.springframework.util.LinkedMultiValueMap;
//import org.springframework.util.MultiValueMap;
//
//import com.tx.core.ddlutil.executor.TableDDLExecutor;
//import com.tx.core.ddlutil.model.DBColumnDef;
//import com.tx.core.ddlutil.model.DBIndexDef;
//import com.tx.core.exceptions.SILException;
//import com.tx.core.util.JdbcUtils;
//import com.tx.core.util.order.OrderedSupportComparator;
//import com.tx.test.ddlutil.model.DDLTestDemo;
//
///*
// * 描          述:  <描述>
// * 修  改   人:  rain
// * 修改时间:  2015年7月1日
// * <修改描述:>
// */
//
///**
// * <功能简述> <功能详细描述>
// * 
// * @author rain
// * @version [版本号, 2015年7月1日]
// * @see [相关类/方法]
// * @since [产品/模块版本]
// */
//public class Main {
//    
//    //    SELECT 
//    //    TCOL.COLUMN_NAME AS 'columnName', TCOL.COLUMN_TYPE AS 'columnType', 
//    //    TCOL.TABLE_NAME AS 'tableName', 
//    //    ( CASE WHEN TCOL.COLUMN_KEY = 'PRI' THEN 'Y' ELSE 'N' END ) AS 'primaryKey', 
//    //    ( CASE WHEN TCOL.IS_NULLABLE = 'NO' THEN 'Y' ELSE 'N' END ) AS 'required', 
//    //    ( CASE WHEN TCOL.DATA_TYPE = 'int' THEN 'integer' ELSE TCOL.DATA_TYPE END ) AS 'jdbcType', 
//    //    ( CASE WHEN TCOL.CHARACTER_MAXIMUM_LENGTH IS NULL THEN TCOL.NUMERIC_PRECISION ELSE TCOL.CHARACTER_MAXIMUM_LENGTH END ) AS size, 
//    //    TCOL.NUMERIC_SCALE AS 'scale', 
//    //    ( CASE 
//    //             WHEN TCOL.DATA_TYPE = 'bit' AND TCOL.COLUMN_DEFAULT = 'b''1''' THEN 1 
//    //             WHEN TCOL.DATA_TYPE = 'bit' AND TCOL.COLUMN_DEFAULT = 'b''0''' THEN 0 
//    //             WHEN TCOL.DATA_TYPE = 'datetime' AND TCOL.COLUMN_DEFAULT = 'CURRENT_TIMESTAMP' THEN 'now()' 
//    //             WHEN TCOL.DATA_TYPE = 'varchar' THEN CONCAT( '''', TCOL.COLUMN_DEFAULT, '''' ) 
//    //             ELSE TCOL.COLUMN_DEFAULT END) AS 'defaultValue' 
//    //    FROM INFORMATION_SCHEMA.`COLUMNS` TCOL 
//    //    WHERE TCOL.TABLE_NAME = 'test_ddl_test_demo' AND TCOL.TABLE_SCHEMA = 'test'
//    
//    //    select t.indexName,t.tableName,t.uniqueKey,GROUP_CONCAT(t.columnName) as columnNames from (
//    //            SELECT 
//    //            TIDX.INDEX_NAME AS 'indexName', 
//    //            TIDX.COLUMN_NAME AS 'columnName', 
//    //            TIDX.TABLE_NAME AS 'tableName', 
//    //            (CASE WHEN TIDX.NON_UNIQUE = 1 THEN 0 ELSE 1 END ) AS 'uniqueKey',
//    //            TIDX.SEQ_IN_INDEX as 'orderPriority' 
//    //            -- , 
//    //            -- (CASE WHEN TCONS.CONSTRAINT_TYPE = 'PRIMARY KEY' THEN 'Y' ELSE 'N' END ) AS 'primaryKey'
//    //            FROM information_schema.`STATISTICS` TIDX
//    //            LEFT JOIN information_schema.`TABLE_CONSTRAINTS` TCONS ON (TIDX.TABLE_SCHEMA = TCONS.CONSTRAINT_SCHEMA AND TIDX.TABLE_NAME = TCONS.TABLE_NAME AND TIDX.INDEX_NAME = TCONS.CONSTRAINT_NAME)
//    //            WHERE TIDX.TABLE_NAME = 'test_ddl_test_demo' AND TIDX.TABLE_SCHEMA = 'test'
//    //            and (TCONS.CONSTRAINT_TYPE is null or 'PRIMARY KEY' <> TCONS.CONSTRAINT_TYPE)
//    //            ORDER BY TIDX.INDEX_NAME,TIDX.SEQ_IN_INDEX
//    //        ) t group by indexName,tableName,uniqueKey
//    
//    //    SELECT 
//    //    TIDX.INDEX_NAME, 
//    //    TIDX.COLUMN_NAME,
//    //  TIDX.TABLE_NAME, 
//    //    TIDX.NON_UNIQUE,
//    //    TIDX.SEQ_IN_INDEX , 
//    //    TCONS.CONSTRAINT_TYPE
//    //    FROM information_schema.`STATISTICS` TIDX
//    //    LEFT JOIN information_schema.`TABLE_CONSTRAINTS` TCONS ON (TIDX.TABLE_SCHEMA = TCONS.CONSTRAINT_SCHEMA AND TIDX.TABLE_NAME = TCONS.TABLE_NAME AND TIDX.INDEX_NAME = TCONS.CONSTRAINT_NAME)
//    //    WHERE TIDX.TABLE_NAME = 'test_ddl_test_demo' AND TIDX.TABLE_SCHEMA = 'test'
//    //    ORDER BY TIDX.INDEX_NAME,TIDX.SEQ_IN_INDEX
//    
//
//    public static void printCreateTableJavaCode(TableDDLExecutor ddlExecutor,
//            String tableName) {
//        boolean flag = ddlExecutor.exists(tableName);
//        if (!flag) {
//            System.out.println("table not exist.");
//        }
//        
//        List<DBColumnDef> cols = ddlExecutor
//                .queryDBColumnsByTableName(tableName);
//        for (DBColumnDef col : cols) {
//            switch (col.getJdbcType()) {
//                case VARCHAR:
//                case CHAR:
//                case TEXT:
//                case LONGTEXT:
//                case LONGVARCHAR:
//                    if (col.isPrimaryKey()) {
//                        System.out.println(MessageFormatter
//                                .arrayFormat(
//                                        ".newColumnOfVarchar(true, \"{}\", {}, {}, {})",
//                                        new Object[] { col.getColumnName(),
//                                                col.getSize(), col.isRequired(),
//                                                col.getDefaultValue() })
//                                .getMessage());
//                    } else {
//                        System.out.println(MessageFormatter
//                                .arrayFormat(
//                                        ".newColumnOfVarchar(\"{}\", {}, {}, {})",
//                                        new Object[] { col.getColumnName(),
//                                                col.getSize(), col.isRequired(),
//                                                col.getDefaultValue() })
//                                .getMessage());
//                    }
//                    break;
//                case INT:
//                case TINYINT:
//                case SMALLINT:
//                case INTEGER:
//                case BIGINT:
//                    if (col.isPrimaryKey()) {
//                        System.out.println(MessageFormatter
//                                .arrayFormat(
//                                        ".newColumnOfInteger(true, \"{}\", {}, {}, {})",
//                                        new Object[] { col.getColumnName(),
//                                                col.getSize(), col.isRequired(),
//                                                col.getDefaultValue() })
//                                .getMessage());
//                    } else {
//                        System.out.println(MessageFormatter
//                                .arrayFormat(
//                                        ".newColumnOfInteger(\"{}\", {}, {}, {})",
//                                        new Object[] { col.getColumnName(),
//                                                col.getSize(), col.isRequired(),
//                                                col.getDefaultValue() })
//                                .getMessage());
//                    }
//                    break;
//                case FLOAT:
//                case DOUBLE:
//                case DECIMAL:
//                case NUMERIC:
//                    System.out.println(MessageFormatter.arrayFormat(
//                            ".newColumnOfBigDecimal(\"{}\", {}, {}, {}, {})",
//                            new Object[] { col.getColumnName(), col.getSize(),
//                                    col.getScale(), col.isRequired(),
//                                    col.getDefaultValue() })
//                            .getMessage());
//                    break;
//                case BIT:
//                    System.out.println(MessageFormatter
//                            .arrayFormat(".newColumnOfBoolean(\"{}\", {}, {})",
//                                    new Object[] { col.getColumnName(),
//                                            col.isRequired(),
//                                            col.getDefaultValue() == null ? null
//                                                    : "1".equals(
//                                                            col.getDefaultValue()) })
//                            .getMessage());
//                    break;
//                case DATE:
//                case DATETIME:
//                case TIMESTAMP:
//                case TIME:
//                    System.out.println(MessageFormatter
//                            .arrayFormat(".newColumnOfDate(\"{}\", {}, {})",
//                                    new Object[] { col.getColumnName(),
//                                            col.isRequired(),
//                                            "now()".equals(
//                                                    col.getDefaultValue()) })
//                            .getMessage());
//                    break;
//                default:
//                    throw new SILException("error type." + col.getJdbcType());
//            }
//        }
//        
//        System.out.println();
//        
//        List<DBIndexDef> indexes = ddlExecutor
//                .queryDBIndexesByTableName(tableName);
//        MultiValueMap<String, DBIndexDef> idxMap = new LinkedMultiValueMap<>();
//        for (DBIndexDef idx : indexes) {
//            if (idx.isPrimaryKey()) {
//                continue;
//            }
//            idxMap.add(idx.getIndexName().toUpperCase(), idx);
//        }
//        
//        for (List<DBIndexDef> idxList : idxMap.values()) {
//            String indexName = idxList.get(0).getIndexName();
//            boolean uniqueKey = idxList.get(0).isUniqueKey();
//            OrderedSupportComparator.sort(idxList);
//            StringBuffer sb = new StringBuffer();
//            for (DBIndexDef d : idxList) {
//                sb.append("\"").append(d.getColumnName()).append("\",");
//            }
//            sb.deleteCharAt(sb.length() - 1);
//            String columnNames = sb.toString();
//            
//            if (uniqueKey) {
//                System.out
//                        .println(
//                                MessageFormatter
//                                        .arrayFormat(
//                                                ".newIndex(true,\"{}\",{})",
//                                                new Object[] { indexName,
//                                                        columnNames })
//                                        .getMessage());
//            } else {
//                System.out.println(MessageFormatter.arrayFormat(
//                        ".newIndex(\"{}\",{})",
//                        new Object[] { indexName, tableName, columnNames })
//                        .getMessage());
//            }
//        }
//        
//    }
//    
//    
//    public static void main(String[] args) {
//        System.out.println("Object : " + JdbcUtils.isSupportedSimpleType(Object.class));
//        System.out.println("Class : " + JdbcUtils.isSupportedSimpleType(Class.class));
//        
//        System.out.println("short : " + JdbcUtils.isSupportedSimpleType(short.class));
//        System.out.println("int : " + JdbcUtils.isSupportedSimpleType(int.class));
//        System.out.println("long : " + JdbcUtils.isSupportedSimpleType(long.class));
//        System.out.println("float : " + JdbcUtils.isSupportedSimpleType(float.class));
//        System.out.println("double : " + JdbcUtils.isSupportedSimpleType(double.class));
//        System.out.println("boolean : " + JdbcUtils.isSupportedSimpleType(boolean.class));
//        System.out.println("char : " + JdbcUtils.isSupportedSimpleType(char.class));
//        
//        System.out.println("Short : " + JdbcUtils.isSupportedSimpleType(Short.class));
//        System.out.println("Integer : " + JdbcUtils.isSupportedSimpleType(Integer.class));
//        System.out.println("Long : " + JdbcUtils.isSupportedSimpleType(Long.class));
//        System.out.println("Float : " + JdbcUtils.isSupportedSimpleType(Float.class));
//        System.out.println("Double : " + JdbcUtils.isSupportedSimpleType(Double.class));
//        System.out.println("Boolean : " + JdbcUtils.isSupportedSimpleType(Boolean.class));
//        System.out.println("Character : " + JdbcUtils.isSupportedSimpleType(Character.class));
//        
//        System.out.println("java.sql.Date : " + JdbcUtils.isSupportedSimpleType(java.sql.Date.class));
//        System.out.println("java.util.Date : " + JdbcUtils.isSupportedSimpleType(java.util.Date.class));
//        System.out.println("Timestamp : " + JdbcUtils.isSupportedSimpleType(Timestamp.class));
//        System.out.println("Time : " + JdbcUtils.isSupportedSimpleType(Time.class));
//        
//        System.out.println("BigDecimal : " + JdbcUtils.isSupportedSimpleType(BigDecimal.class));
//        System.out.println("String : " + JdbcUtils.isSupportedSimpleType(String.class));
//        
//        System.out.println("Map : " + JdbcUtils.isSupportedSimpleType(Map.class));
//        System.out.println("Set : " + JdbcUtils.isSupportedSimpleType(Set.class));
//        System.out.println("List : " + JdbcUtils.isSupportedSimpleType(List.class));
//        System.out.println("ArrayList : " + JdbcUtils.isSupportedSimpleType(ArrayList.class));
//        System.out.println("HashMap : " + JdbcUtils.isSupportedSimpleType(HashMap.class));
//        System.out.println("HashSet : " + JdbcUtils.isSupportedSimpleType(HashSet.class));
//        
//        TypeHandlerRegistry typeHandlerRegistry = new TypeHandlerRegistry();
//        
//        Field field1 = FieldUtils.getDeclaredField(DDLTestDemo.class, "test_String", true);
//        System.out.println(field1);
//        Field field11 = FieldUtils.getField(DDLTestDemo.class, "test_String", true);
//        System.out.println(field11);
//        
//        Field field2 = FieldUtils.getDeclaredField(DDLTestDemo.class, "parentString", true);
//        System.out.println(field2);
//        Field field21 = FieldUtils.getField(DDLTestDemo.class, "parentString", true);
//        System.out.println(field21);
//    }
//}
