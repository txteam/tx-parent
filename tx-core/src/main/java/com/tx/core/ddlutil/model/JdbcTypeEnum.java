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
import org.apache.ibatis.type.JdbcType;

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
    
    SET(Types.ARRAY, JdbcType.ARRAY),
    
    ARRAY(Types.ARRAY, JdbcType.ARRAY),
    
    BOOLEAN(Types.BOOLEAN, JdbcType.BIT),
    
    BIT(Types.BIT, JdbcType.BIT),
    /**
     * TINYINT[(M)] [UNSIGNED] [ZEROFILL]  M默认为4
     * 很小的整数。带符号的范围是-128到127。无符号的范围是0到255
     */
    TINYINT(Types.TINYINT, JdbcType.TINYINT),
    /**
     * SMALLINT[(M)] [UNSIGNED] [ZEROFILL] M默认为6
     * 小的整数。带符号的范围是-32768到32767。无符号的范围是0到65535。
     */
    SMALLINT(Types.SMALLINT, JdbcType.SMALLINT),
    /**
     * INT[(M)] [UNSIGNED] [ZEROFILL]   M默认为11
     * 普通大小的整数。带符号的范围是-2147483648到2147483647。无符号的范围是0到4294967295。
     */
    INT(Types.INTEGER, JdbcType.INTEGER),
    /**
     * INT[(M)] [UNSIGNED] [ZEROFILL]   M默认为11
     * 普通大小的整数。带符号的范围是-2147483648到2147483647。无符号的范围是0到4294967295。
     */
    INTEGER(Types.INTEGER, JdbcType.INTEGER),
    /**
     * BIGINT[(M)] [UNSIGNED] [ZEROFILL] M默认为20
     * 大整数。带符号的范围是-9223372036854775808到9223372036854775807。无符号的范围是0到18446744073709551615
     */
    BIGINT(Types.BIGINT, JdbcType.BIGINT),
    
    FLOAT(Types.FLOAT, JdbcType.FLOAT),
    
    REAL(Types.REAL, JdbcType.REAL),
    
    DOUBLE(Types.DOUBLE, JdbcType.DOUBLE),
    
    NUMERIC(Types.NUMERIC, JdbcType.NUMERIC),
    
    DECIMAL(Types.DECIMAL, JdbcType.DECIMAL),
    
    CHAR(Types.CHAR, JdbcType.CHAR),
    
    VARCHAR(Types.VARCHAR, JdbcType.VARCHAR),
    
    LONGVARCHAR(Types.LONGVARCHAR, JdbcType.LONGNVARCHAR),
    
    /**
     * TEXT 最大长度是 65535 (2^16 – 1) 个字符。
     */
    TEXT(Types.LONGVARCHAR, JdbcType.LONGVARCHAR),
    /**
     * 最大长度是 255 (2^8 – 1) 个字符。
     */
    TINYTEXT(Types.LONGVARCHAR, JdbcType.LONGVARCHAR),
    /**
     * MEDIUMTEXT 最大长度是 16777215 (2^24 – 1) 个字符。
     */
    MEDIUMTEXT(Types.LONGNVARCHAR, JdbcType.LONGVARCHAR),
    /**
     * LONGTEXT 最大长度是 4294967295 (2^32 – 1) 个字符。
     */
    LONGTEXT(Types.LONGVARCHAR, JdbcType.LONGVARCHAR),
    
    DATE(Types.DATE, JdbcType.DATE),
    
    TIME(Types.TIME, JdbcType.TIME),
    
    TIMESTAMP(Types.TIMESTAMP, JdbcType.TIMESTAMP),
    
    DATETIME(Types.TIMESTAMP, JdbcType.TIMESTAMP),
    
    BINARY(Types.BINARY, JdbcType.BINARY),
    
    VARBINARY(Types.VARBINARY, JdbcType.VARBINARY),
    
    LONGVARBINARY(Types.LONGVARBINARY, JdbcType.LONGVARBINARY),
    
    NULL(Types.NULL, JdbcType.NULL),
    
    OTHER(Types.OTHER, JdbcType.OTHER),
    
    BLOB(Types.BLOB, JdbcType.BLOB),
    
    LONGBLOB(Types.BLOB, JdbcType.BLOB),
    
    CLOB(Types.CLOB, JdbcType.CLOB),
    
    STRUCT(Types.STRUCT, JdbcType.STRUCT),
    
    NVARCHAR(Types.NVARCHAR, JdbcType.NVARCHAR), // JDK6
    
    NCHAR(Types.NCHAR, JdbcType.NCHAR), // JDK6
    
    NCLOB(Types.NCLOB, JdbcType.NCLOB), // JDK6
    
    ENUM(Types.VARCHAR, JdbcType.VARCHAR),
    
    CURSOR(-10, JdbcType.CURSOR), // Oracle
    
    UNDEFINED(Integer.MIN_VALUE + 1000, JdbcType.UNDEFINED);
    
    public final int sqlType;
    
    private final JdbcType mybatisJdbcType;
    
    /** <默认构造函数> */
    private JdbcTypeEnum(int sqlType, JdbcType mybatisJdbcType) {
        this.sqlType = sqlType;
        this.mybatisJdbcType = mybatisJdbcType;
    }
    
    /**
     * @return 返回 sqlType
     */
    public int getSqlType() {
        return sqlType;
    }
    
    /**
     * @return 返回 mybatisJdbcType
     */
    public JdbcType getMybatisJdbcType() {
        return mybatisJdbcType;
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
