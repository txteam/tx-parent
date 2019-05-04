/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2014年5月14日
 * <修改描述:>
 */
package com.tx.core.support.poi.excel.builder;

import java.util.Map;

import org.apache.commons.collections4.map.LRUMap;
import org.apache.commons.lang3.ArrayUtils;

import com.tx.core.TxConstants;
import com.tx.core.support.poi.excel.CellRowWriter;
import com.tx.core.util.ObjectUtils;

/**
 * excel行写入器构建器
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2014年5月14日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public abstract class CellRowWriterBuilder {
    
    /** cell的读取器 */
    @SuppressWarnings({ "unchecked" })
    private static Map<String, CellRowWriter<?>> cellRowWriterCache = (Map<String, CellRowWriter<?>>) new LRUMap(
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
            @SuppressWarnings("rawtypes") Class<? extends CellRowWriter> cellRowWriterType,
            Object... objs) {
        StringBuilder sb = new StringBuilder(TxConstants.INITIAL_STR_LENGTH);
        long paramLength = ArrayUtils.isEmpty(objs) ? 0 : objs.length;
        long paramHashCodes = 0;
        if (!ArrayUtils.isEmpty(objs)) {
            for (Object objTemp : objs) {
                paramHashCodes += objTemp.hashCode();
            }
        }
        sb.append(cellRowWriterType.getName())
                .append("(")
                .append("[")
                .append(paramHashCodes)
                .append("]length =")
                .append(paramLength)
                .append(")");
        return sb.toString();
    }
    
    /**
     * 构建cellRowReader
     *<功能详细描述>
     * @param readerType
     * @return [参数说明]
     * 
     * @return CellReader<?> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    @SuppressWarnings("rawtypes")
    public static CellRowWriter build(
            Class<? extends CellRowWriter> cellRowWriterType, Object[] objs) {
        String key = buildCacheKey(cellRowWriterType, objs);
        CellRowWriter<?> cellRowWriter = null;
        if (cellRowWriterCache.containsKey(key)) {
            cellRowWriter = cellRowWriterCache.get(key);
        } else {
            cellRowWriter = ObjectUtils.newInstance(cellRowWriterType, objs);
            cellRowWriterCache.put(key, cellRowWriter);
        }
        return cellRowWriter;
    }
    
}
