/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2016年10月21日
 * <修改描述:>
 */
package com.tx.core.ddlutil.dialect;

import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import org.hibernate.dialect.MySQL5InnoDBDialect;

import com.tx.core.ddlutil.model.JdbcTypeEnum;
import com.tx.core.exceptions.util.AssertUtils;

/**
 * MysqlDDL方言<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2016年10月21日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class MysqlDDLDialect extends DDLDialect {
    
    public static final DDLDialect INSTANCE = new MysqlDDLDialect();
    
    /** <默认构造函数> */
    MysqlDDLDialect() {
        super();
        setDialect(new MySQL5InnoDBDialect());
    }
    
    /** class类型对jdbctype的映射 */
    private static final Map<Class<?>, JdbcTypeEnum> CLASSTYPE_2_JDBCTYPE_MAP = new HashMap<Class<?>, JdbcTypeEnum>();
    
    /** 字段名默认长度映射 */
    private static final Map<Class<?>, Integer> COLUMNTYPE_2_LENGTH_MAP = new HashMap<Class<?>, Integer>();
    
    /** 字段名默认长度映射 */
    private static final Map<Class<?>, Integer> COLUMNTYPE_2_SCALE_MAP = new HashMap<Class<?>, Integer>();
    
    /** 字段名默认长度映射 */
    private static final Map<Class<?>, Map<Pattern, Integer>> COLUMNNAME_2_LENGTH_MAP = new HashMap<Class<?>, Map<Pattern, Integer>>();
    
    /** 字段名默认长度映射 */
    private static final Map<Class<?>, Map<Pattern, Integer>> COLUMNNAME_2_SCALE_MAP = new HashMap<Class<?>, Map<Pattern, Integer>>();
    
    static {
        registeClass(String.class, JdbcTypeEnum.VARCHAR);
        registeClass2Length(String.class, 255);
        registeClass2Scale(String.class, 0);
        registeColumnName2Length(String.class,
                Pattern.compile("^.*id$", Pattern.CASE_INSENSITIVE),
                64);
        registeColumnName2Length(String.class,
                Pattern.compile("^.*number$", Pattern.CASE_INSENSITIVE),
                64);
        registeColumnName2Length(String.class,
                Pattern.compile("^.*code$", Pattern.CASE_INSENSITIVE),
                64);
        registeColumnName2Length(String.class,
                Pattern.compile("^.*name$", Pattern.CASE_INSENSITIVE),
                64);
        registeColumnName2Length(String.class,
                Pattern.compile("^.*remark$", Pattern.CASE_INSENSITIVE),
                512);
        registeColumnName2Length(String.class,
                Pattern.compile("^.*des$", Pattern.CASE_INSENSITIVE),
                512);
        registeColumnName2Length(String.class,
                Pattern.compile("^.*description$", Pattern.CASE_INSENSITIVE),
                512);
        registeColumnName2Length(String.class,
                Pattern.compile("^.*description$", Pattern.CASE_INSENSITIVE),
                512);
        registeColumnName2Length(String.class,
                Pattern.compile("^.*reason$", Pattern.CASE_INSENSITIVE),
                512);
        registeColumnName2Length(String.class,
                Pattern.compile("^.*category$", Pattern.CASE_INSENSITIVE),
                64);
        registeColumnName2Length(String.class,
                Pattern.compile("^.*type$", Pattern.CASE_INSENSITIVE),
                64);
        registeColumnName2Length(String.class,
                Pattern.compile("^.*way$", Pattern.CASE_INSENSITIVE),
                64);
        registeColumnName2Length(String.class,
                Pattern.compile("^.*status$", Pattern.CASE_INSENSITIVE),
                64);
        registeColumnName2Length(String.class,
                Pattern.compile("^.*version$", Pattern.CASE_INSENSITIVE),
                64);
        
        registeClass(Byte.class, JdbcTypeEnum.TINYINT);
        registeClass2Length(Byte.class, 4);
        registeClass2Scale(Byte.class, 0);
        
        registeClass(byte.class, JdbcTypeEnum.TINYINT);
        registeClass2Length(byte.class, 4);
        registeClass2Scale(byte.class, 0);
        
        registeClass(Short.class, JdbcTypeEnum.SMALLINT);
        registeClass2Length(Short.class, 6);
        registeClass2Scale(Short.class, 0);
        
        registeClass(short.class, JdbcTypeEnum.SMALLINT);
        registeClass2Length(short.class, 6);
        registeClass2Scale(short.class, 0);
        
        registeClass(Integer.class, JdbcTypeEnum.INTEGER);
        registeClass2Length(Integer.class, 11);
        registeClass2Scale(Integer.class, 0);
        registeColumnName2Length(Integer.class,
                Pattern.compile("^.*id$", Pattern.CASE_INSENSITIVE),
                11);
        registeColumnName2Length(Integer.class,
                Pattern.compile("^.*number$", Pattern.CASE_INSENSITIVE),
                11);
        registeColumnName2Length(Integer.class,
                Pattern.compile("^.*code$", Pattern.CASE_INSENSITIVE),
                11);
        
        registeClass(int.class, JdbcTypeEnum.INTEGER);
        registeClass2Length(int.class, 11);
        registeClass2Scale(int.class, 0);
        registeColumnName2Length(int.class,
                Pattern.compile("^.*id$", Pattern.CASE_INSENSITIVE),
                11);
        registeColumnName2Length(int.class,
                Pattern.compile("^.*number$", Pattern.CASE_INSENSITIVE),
                11);
        registeColumnName2Length(int.class,
                Pattern.compile("^.*code$", Pattern.CASE_INSENSITIVE),
                11);
        
        registeClass(Long.class, JdbcTypeEnum.BIGINT);
        registeClass2Length(Long.class, 20);
        registeClass2Scale(Long.class, 0);
        registeColumnName2Length(Long.class,
                Pattern.compile("^.*id$", Pattern.CASE_INSENSITIVE),
                20);
        registeColumnName2Length(Long.class,
                Pattern.compile("^.*number$", Pattern.CASE_INSENSITIVE),
                20);
        registeColumnName2Length(Long.class,
                Pattern.compile("^.*code$", Pattern.CASE_INSENSITIVE),
                20);
        
        registeClass(long.class, JdbcTypeEnum.BIGINT);
        registeClass2Length(long.class, 20);
        registeClass2Scale(long.class, 0);
        registeColumnName2Length(long.class,
                Pattern.compile("^.*id$", Pattern.CASE_INSENSITIVE),
                20);
        registeColumnName2Length(long.class,
                Pattern.compile("^.*number$", Pattern.CASE_INSENSITIVE),
                20);
        registeColumnName2Length(long.class,
                Pattern.compile("^.*code$", Pattern.CASE_INSENSITIVE),
                20);
        
        registeClass(Float.class, JdbcTypeEnum.NUMERIC);
        registeClass2Length(Float.class, 16);
        registeClass2Scale(Float.class, 8);
        
        registeClass(float.class, JdbcTypeEnum.NUMERIC);
        registeClass2Length(float.class, 16);
        registeClass2Scale(float.class, 8);
        
        registeClass(Double.class, JdbcTypeEnum.NUMERIC);
        registeClass2Length(Double.class, 32);
        registeClass2Scale(Double.class, 16);
        
        registeClass(double.class, JdbcTypeEnum.NUMERIC);
        registeClass2Length(double.class, 32);
        registeClass2Scale(double.class, 16);
        
        registeClass(BigDecimal.class, JdbcTypeEnum.NUMERIC);
        registeClass2Length(BigDecimal.class, 32);
        registeClass2Scale(BigDecimal.class, 16);
        registeColumnName2Scale(BigDecimal.class,
                Pattern.compile("^.*amount$", Pattern.CASE_INSENSITIVE),
                2);
        
        registeClass(Boolean.class, JdbcTypeEnum.BIT);
        registeClass2Length(Boolean.class, 1);
        registeClass2Scale(Boolean.class, 0);
        
        registeClass(boolean.class, JdbcTypeEnum.BIT);
        registeClass2Length(Boolean.class, 1);
        registeClass2Scale(Boolean.class, 0);
        
        registeClass(Character.class, JdbcTypeEnum.VARCHAR);
        registeClass2Length(Character.class, 4);
        registeClass2Scale(Character.class, 0);
        
        registeClass(char.class, JdbcTypeEnum.VARCHAR);
        registeClass2Length(char.class, 4);
        registeClass2Scale(char.class, 0);
        
        registeClass(Date.class, JdbcTypeEnum.DATETIME);
        registeClass2Length(Date.class, 0);
        registeClass2Scale(Date.class, 0);
        
        registeClass(java.sql.Date.class, JdbcTypeEnum.DATETIME);
        registeClass2Length(java.sql.Date.class, 0);
        registeClass2Scale(java.sql.Date.class, 0);
        
        registeClass(Timestamp.class, JdbcTypeEnum.DATETIME);
        registeClass2Length(Timestamp.class, 0);
        registeClass2Scale(Timestamp.class, 0);
        
        registeClass(Time.class, JdbcTypeEnum.DATETIME);
        registeClass2Length(Time.class, 0);
        registeClass2Scale(Time.class, 0);
        
        registeClass(Class.class, JdbcTypeEnum.VARCHAR);
        registeClass2Length(Class.class, 255);
        registeClass2Scale(Class.class, 0);
        
        registeClass(CharSequence.class, JdbcTypeEnum.VARCHAR);
        
        registeClass(Number.class, JdbcTypeEnum.NUMERIC);
    }
    
    /**
      * 注册类型对jdbcType的映射<br/>
      * <功能详细描述>
      * @param type
      * @param jdbcType [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    static void registeClass(Class<?> type, JdbcTypeEnum jdbcType) {
        CLASSTYPE_2_JDBCTYPE_MAP.put(type, jdbcType);
    }
    
    /**
     * 注册类型对jdbcType的映射<br/>
     * <功能详细描述>
     * @param type
     * @param jdbcType [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    static void registeClass2Length(Class<?> type, int length) {
        COLUMNTYPE_2_LENGTH_MAP.put(type, length);
    }
    
    /**
    * 注册类型对jdbcType的映射<br/>
    * <功能详细描述>
    * @param type
    * @param jdbcType [参数说明]
    * 
    * @return void [返回类型说明]
    * @exception throws [异常类型] [异常说明]
    * @see [类、类#方法、类#成员]
    */
    static void registeClass2Scale(Class<?> type, int scale) {
        COLUMNTYPE_2_SCALE_MAP.put(type, scale);
    }
    
    /**
     * 注册类型对jdbcType的映射<br/>
     * <功能详细描述>
     * @param type
     * @param jdbcType [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    static void registeColumnName2Length(Class<?> type, Pattern pattern,
            int length) {
        if (!COLUMNNAME_2_LENGTH_MAP.containsKey(type)) {
            COLUMNNAME_2_LENGTH_MAP.put(type, new HashMap<>());
        }
        COLUMNNAME_2_LENGTH_MAP.get(type).put(pattern, length);
    }
    
    /**
     * 注册类型对jdbcType的映射<br/>
     * <功能详细描述>
     * @param type
     * @param jdbcType [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    static void registeColumnName2Scale(Class<?> type, Pattern pattern,
            int scale) {
        if (!COLUMNNAME_2_SCALE_MAP.containsKey(type)) {
            COLUMNNAME_2_SCALE_MAP.put(type, new HashMap<>());
        }
        COLUMNNAME_2_SCALE_MAP.get(type).put(pattern, scale);
    }
    
    /**
     * @param type
     * @return
     */
    @Override
    public JdbcTypeEnum getJdbcType(Class<?> type) {
        AssertUtils.notNull(type, "type is null.");
        
        if (type.isEnum()) {
            return JdbcTypeEnum.VARCHAR;
        } else if (CLASSTYPE_2_JDBCTYPE_MAP.containsKey(type)) {
            return CLASSTYPE_2_JDBCTYPE_MAP.get(type);
        }
        
        for (Entry<Class<?>, JdbcTypeEnum> entryTemp : CLASSTYPE_2_JDBCTYPE_MAP
                .entrySet()) {
            if (entryTemp.getKey().isAssignableFrom(type)) {
                return entryTemp.getValue();
            }
        }
        
        return JdbcTypeEnum.VARCHAR;
    }
    
    /**
     * @param type
     * @return
     */
    @Override
    public int getDefaultLengthByType(Class<?> type) {
        AssertUtils.notNull(type, "type is null.");
        
        if (type.isEnum()) {
            return 64;
        } else if (COLUMNTYPE_2_LENGTH_MAP.containsKey(type)) {
            return COLUMNTYPE_2_LENGTH_MAP.get(type);
        }
        
        return -1;
    }
    
    /**
     * @param type
     * @return
     */
    @Override
    public int getDefaultScaleByType(Class<?> type) {
        AssertUtils.notNull(type, "type is null.");
        
        if (type.isEnum()) {
            return 0;
        } else if (COLUMNTYPE_2_SCALE_MAP.containsKey(type)) {
            return COLUMNTYPE_2_SCALE_MAP.get(type);
        }
        
        return -1;
    }
    
    /**
     * @param type
     * @param name
     * @return
     */
    @Override
    public int getDefaultLengthByName(Class<?> type, String name) {
        AssertUtils.notNull(type, "type is null.");
        AssertUtils.notEmpty(name, "name is null.");
        
        if (type.isEnum()) {
            return 64;
        } else if (!COLUMNNAME_2_LENGTH_MAP.containsKey(type)) {
            return -1;
        } else {
            for (Entry<Pattern, Integer> entry : COLUMNNAME_2_LENGTH_MAP
                    .get(type).entrySet()) {
                if (entry.getKey().matcher(name).matches()) {
                    return entry.getValue();
                }
            }
        }
        return -1;
    }
    
    /**
     * @param type
     * @param name
     * @return
     */
    @Override
    public int getDefaultScaleByName(Class<?> type, String name) {
        AssertUtils.notNull(type, "type is null.");
        AssertUtils.notEmpty(name, "name is null.");
        
        if (type.isEnum()) {
            return 0;
        } else if (!COLUMNNAME_2_SCALE_MAP.containsKey(type)) {
            return -1;
        } else {
            for (Entry<Pattern, Integer> entry : COLUMNNAME_2_SCALE_MAP
                    .get(type).entrySet()) {
                if (entry.getKey().matcher(name).matches()) {
                    return entry.getValue();
                }
            }
        }
        return -1;
    }
}
