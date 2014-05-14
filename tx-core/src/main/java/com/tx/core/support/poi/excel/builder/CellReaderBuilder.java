/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2014年5月13日
 * <修改描述:>
 */
package com.tx.core.support.poi.excel.builder;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.map.LRUMap;
import org.apache.commons.lang.ArrayUtils;

import com.tx.core.TxConstants;
import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.support.poi.excel.CellReader;
import com.tx.core.support.poi.excel.cellreader.CellReader4BigDecimal;
import com.tx.core.support.poi.excel.cellreader.CellReader4StringValue;
import com.tx.core.util.ObjectUtils;

/**
 * 单元格读取器工厂<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2014年5月13日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public abstract class CellReaderBuilder {
    
    private static Map<Class<?>, CellReader<?>> defaultReaderMapping = new HashMap<Class<?>, CellReader<?>>();
    
    static {
        defaultReaderMapping.put(String.class, CellReader4StringValue.INSTANCE);
        defaultReaderMapping.put(BigDecimal.class,
                CellReader4BigDecimal.INSTANCE);
    }
    
    /** cell的读取器 */
    @SuppressWarnings({ "unchecked" })
    private static Map<String, CellReader<?>> cellReaderCache = (Map<String, CellReader<?>>) new LRUMap(
            255);
    
    /**
      *  构建缓存key
      * <功能详细描述>
      * @param readerType
      * @param objs
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    private static String buildCacheKey(
            @SuppressWarnings("rawtypes") Class<? extends CellReader> readerType,
            Object... objs) {
        StringBuilder sb = new StringBuilder(TxConstants.INITIAL_STR_LENGTH);
        long paramLength = ArrayUtils.isEmpty(objs) ? 0 : objs.length;
        long paramHashCodes = 0;
        if (!ArrayUtils.isEmpty(objs)) {
            for (Object objTemp : objs) {
                paramHashCodes += objTemp.hashCode();
            }
        }
        sb.append(readerType.getName())
                .append("(")
                .append("[")
                .append(paramHashCodes)
                .append("]length =")
                .append(paramLength)
                .append(")");
        return sb.toString();
    }
    
    /**
      * 
      *<功能详细描述>
      * @param readerType
      * @return [参数说明]
      * 
      * @return CellReader<?> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public static CellReader<?> build(
            @SuppressWarnings("rawtypes") Class<? extends CellReader> readerType,
            Class<?> fieldType, Object... objs) {
        AssertUtils.notNull(readerType, "readerType is null.");
        AssertUtils.notNull(fieldType, "fieldType is null.");
        
        String key = buildCacheKey(readerType, objs);
        CellReader<?> reader = null;
        if (cellReaderCache.containsKey(key)) {
            reader = cellReaderCache.get(key);
        } else if (readerType.isInterface()) {
            AssertUtils.isTrue(defaultReaderMapping.containsKey(fieldType),
                    "readerType is null.and fieldType:{} unsupport default",
                    new Object[] { fieldType });
            
            reader = defaultReaderMapping.get(fieldType);
            cellReaderCache.put(key, reader);
        } else {
            reader = ObjectUtils.newInstance(readerType, objs);
            cellReaderCache.put(key, reader);
        }
        return reader;
    }
}
