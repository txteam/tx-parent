/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2016年11月9日
 * <修改描述:>
 */
package com.tx.core.ddlutil.helper;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.tx.core.ddlutil.model.JdbcTypeEnum;
import com.tx.core.exceptions.util.AssertUtils;

/**
 * 字段类型注册机<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2016年11月9日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
class JPAEntityTypeRegistry {
    
    /** class类型对jdbctype的映射 */
    private static final Map<Class<?>, JdbcTypeEnum> CLASSTYPE_2_JDBCTYPE_MAP = new HashMap<Class<?>, JdbcTypeEnum>();
    
    static {
        registeClass(String.class, JdbcTypeEnum.VARCHAR);
        registeClass(Byte.class, JdbcTypeEnum.TINYINT);
        registeClass(byte.class, JdbcTypeEnum.TINYINT);
        registeClass(Short.class, JdbcTypeEnum.TINYINT);
        registeClass(short.class, JdbcTypeEnum.TINYINT);
        registeClass(Integer.class, JdbcTypeEnum.INTEGER);
        registeClass(int.class, JdbcTypeEnum.INTEGER);
        registeClass(Long.class, JdbcTypeEnum.BIGINT);
        registeClass(long.class, JdbcTypeEnum.BIGINT);
        registeClass(Float.class, JdbcTypeEnum.DECIMAL);
        registeClass(float.class, JdbcTypeEnum.DECIMAL);
        registeClass(Double.class, JdbcTypeEnum.DECIMAL);
        registeClass(double.class, JdbcTypeEnum.DECIMAL);
        registeClass(Boolean.class, JdbcTypeEnum.BIT);
        registeClass(boolean.class, JdbcTypeEnum.BIT);
        registeClass(Character.class, JdbcTypeEnum.VARCHAR);
        registeClass(char.class, JdbcTypeEnum.VARCHAR);
        
        registeClass(BigDecimal.class, JdbcTypeEnum.DECIMAL);
        registeClass(Date.class, JdbcTypeEnum.DATETIME);
        registeClass(CharSequence.class, JdbcTypeEnum.VARCHAR);
        registeClass(Number.class, JdbcTypeEnum.DECIMAL);
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
      * 获取类型对应的JdbcType<br/>
      * <功能详细描述>
      * @param type
      * @return [参数说明]
      * 
      * @return JdbcTypeEnum [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public static JdbcTypeEnum getJdbcType(Class<?> type) {
        AssertUtils.notNull(type,"type is null.");
        
        if (type.isEnum()) {
            return JdbcTypeEnum.VARCHAR;
        } else if (CLASSTYPE_2_JDBCTYPE_MAP.containsKey(type)) {
            return CLASSTYPE_2_JDBCTYPE_MAP.get(type);
        } 
        
        for(Entry<Class<?>, JdbcTypeEnum> entryTemp : CLASSTYPE_2_JDBCTYPE_MAP.entrySet()){
            if(entryTemp.getKey().isAssignableFrom(type)){
                return entryTemp.getValue();
            }
        }
        
        return JdbcTypeEnum.VARCHAR;
    }
}
