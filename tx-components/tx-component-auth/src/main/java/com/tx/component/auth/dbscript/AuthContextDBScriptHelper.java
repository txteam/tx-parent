/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-10-18
 * <修改描述:>
 */
package com.tx.component.auth.dbscript;

import com.tx.core.dbscript.model.DataSourceTypeEnum;
import com.tx.core.exceptions.logic.UnsupportedDataSourceTypeException;

/**
 * 权限容器数据脚本帮助类<br/>
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2013-10-18]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class AuthContextDBScriptHelper {
    
    /**
      * 根据数据源类型获取数据脚本所在基础路径地址
      *<功能详细描述>
      * @param dataSourceType
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public static String getDBScriptBasePath(DataSourceTypeEnum dataSourceType) {
        switch (dataSourceType) {
            case H2:
                return "com/tx/component/auth/dbscript/h2/";
            case MYSQL:
                return "com/tx/component/auth/dbscript/mysql/";
            case ORACLE:
            case ORACLE10G:
            case ORACLE9I:
                return "com/tx/component/auth/dbscript/h2/";
            default:
                throw new UnsupportedDataSourceTypeException(
                        "dataSourceType:{}", new Object[] { dataSourceType });
        }
    }
}
