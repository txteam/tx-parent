/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2016年10月22日
 * <修改描述:>
 */
package com.tx.core.ddlutil.model;

import java.sql.Types;
import java.util.Map;

import org.apache.commons.lang3.EnumUtils;

import com.tx.core.exceptions.util.AssertUtils;

/**
 * 数据库中JdbcTypeEnum<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2016年10月22日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public enum JdbcTypeEnum {
    
    SET(Types.ARRAY),
    
    ARRAY(Types.ARRAY),
    
    BOOLEAN(Types.BOOLEAN),
    
    BIT(Types.BIT),
    /**
     * TINYINT[(M)] [UNSIGNED] [ZEROFILL]  M默认为4
     * 很小的整数。带符号的范围是-128到127。无符号的范围是0到255
     */
    TINYINT(Types.TINYINT),
    /**
     * SMALLINT[(M)] [UNSIGNED] [ZEROFILL] M默认为6
     * 小的整数。带符号的范围是-32768到32767。无符号的范围是0到65535。
     */
    SMALLINT(Types.SMALLINT),
    /**
     * INT[(M)] [UNSIGNED] [ZEROFILL]   M默认为11
     * 普通大小的整数。带符号的范围是-2147483648到2147483647。无符号的范围是0到4294967295。
     */
    INT(Types.INTEGER),
    /**
     * INT[(M)] [UNSIGNED] [ZEROFILL]   M默认为11
     * 普通大小的整数。带符号的范围是-2147483648到2147483647。无符号的范围是0到4294967295。
     */
    INTEGER(Types.INTEGER),
    /**
     * BIGINT[(M)] [UNSIGNED] [ZEROFILL] M默认为20
     * 大整数。带符号的范围是-9223372036854775808到9223372036854775807。无符号的范围是0到18446744073709551615
     */
    BIGINT(Types.BIGINT),
    
    FLOAT(Types.FLOAT),
    
    REAL(Types.REAL),
    
    DOUBLE(Types.DOUBLE),
    
    NUMERIC(Types.NUMERIC),
    
    DECIMAL(Types.DECIMAL),
    
    CHAR(Types.CHAR),
    
    VARCHAR(Types.VARCHAR),
    
    LONGVARCHAR(Types.LONGVARCHAR),
    
    /**
     * TEXT 最大长度是 65535 (2^16 – 1) 个字符。
     */
    TEXT(Types.LONGVARCHAR),
    /**
     * 最大长度是 255 (2^8 – 1) 个字符。
     */
    TINYTEXT(Types.LONGVARCHAR),
    /**
     * MEDIUMTEXT 最大长度是 16777215 (2^24 – 1) 个字符。
     */
    MEDIUMTEXT(Types.LONGNVARCHAR),
    /**
     * LONGTEXT 最大长度是 4294967295 (2^32 – 1) 个字符。
     */
    LONGTEXT(Types.LONGVARCHAR),
    
    DATE(Types.DATE),
    
    TIME(Types.TIME),
    
    TIMESTAMP(Types.TIMESTAMP),
    
    DATETIME(Types.TIMESTAMP),
    
    BINARY(Types.BINARY),
    
    VARBINARY(Types.VARBINARY),
    
    LONGVARBINARY(Types.LONGVARBINARY),
    
    NULL(Types.NULL),
    
    OTHER(Types.OTHER),
    
    BLOB(Types.BLOB),
    
    LONGBLOB(Types.BLOB),
    
    CLOB(Types.CLOB),
    
    STRUCT(Types.STRUCT),
    
    NVARCHAR(Types.NVARCHAR), // JDK6
    
    NCHAR(Types.NCHAR), // JDK6
    
    NCLOB(Types.NCLOB), // JDK6
    
    ENUM(Types.VARCHAR),
    
    CURSOR(-10), // Oracle
    
    UNDEFINED(Integer.MIN_VALUE + 1000);
    
    public final int sqlType;
    
    /** <默认构造函数> */
    private JdbcTypeEnum(int sqlType) {
        this.sqlType = sqlType;
    }
    
    /**
     * @return 返回 sqlType
     */
    public int getSqlType() {
        return sqlType;
    }
    
    private static Map<String, JdbcTypeEnum> typekey2typeMap = EnumUtils.getEnumMap(JdbcTypeEnum.class);
    
    /**
      * 根据Type名获取对应的JdbcTypeEnum
      * <功能详细描述>
      * @param typekey
      * @return [参数说明]
      * 
      * @return JdbcTypeEnum [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public static JdbcTypeEnum forTypeKey(String typekey) {
        AssertUtils.notEmpty(typekey, "typekey is empty.");
        
        JdbcTypeEnum jdbcType = typekey2typeMap.get(typekey.toUpperCase());
        return jdbcType;
    }
}
