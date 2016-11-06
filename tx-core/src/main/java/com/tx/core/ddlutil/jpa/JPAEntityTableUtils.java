/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2016年11月6日
 * <修改描述:>
 */
package com.tx.core.ddlutil.jpa;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tx.core.ddlutil.model.JPAEntityColumnDef;
import com.tx.core.ddlutil.model.JPAEntityIndexDef;
import com.tx.core.ddlutil.model.JPAEntityTableDef;
import com.tx.core.ddlutil.model.TableDef;
import com.tx.core.exceptions.util.AssertUtils;

/**
 * JPA实体表工具类<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2016年11月6日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public abstract class JPAEntityTableUtils {
    
    private static final Map<Class<?>, JPAEntityTableDef> TYPE_2_TABLEDEF_MAP = new HashMap<Class<?>, JPAEntityTableDef>();
    
    /**
      * 解析类型为表定义详细实例<br/>
      *    ：实例中将含有对应的索引以及字段和索引<br/>
      * <功能详细描述>
      * @param type
      * @return [参数说明]
      * 
      * @return TableDef [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public static TableDef parseClassToTableDefDetail(Class<?> type) {
        AssertUtils.notNull(type, "type is null.");
        
        if (TYPE_2_TABLEDEF_MAP.containsKey(type)) {
            return TYPE_2_TABLEDEF_MAP.get(type);
        }
        JPAEntityTableDef tableDef = doParseClassToTableDefDetail(type);
        TYPE_2_TABLEDEF_MAP.put(type, tableDef);
        return tableDef;
    }
    
    /**
      * 解析类或接口生成对应的表定义详情(含字段以及索引)<br/>
      * <功能详细描述>
      * @param type
      * @return [参数说明]
      * 
      * @return JPAEntityTableDef [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    private static JPAEntityTableDef doParseClassToTableDefDetail(Class<?> type) {
        JPAEntityTableDef tableDef = doParseTableDef(type);
        
        List<JPAEntityColumnDef> columnDefs = doParseCoumnDefs(type);
        List<JPAEntityIndexDef> indexDefs = doParseCoumnIndexes(type);
        
        tableDef.setColumns(columnDefs);
        tableDef.setIndexes(indexDefs);
        return tableDef;
    }
    
    private static JPAEntityTableDef doParseTableDef(Class<?> type) {
        
        return null;
    }
    
    private static List<JPAEntityColumnDef> doParseCoumnDefs(Class<?> type) {
        
        return null;
    }
    
    private static List<JPAEntityIndexDef> doParseCoumnIndexes(Class<?> type) {
        
        return null;
    }
}
