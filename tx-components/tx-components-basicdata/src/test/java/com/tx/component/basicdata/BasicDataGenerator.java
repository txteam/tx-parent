/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-10-8
 * <修改描述:>
 */
package com.tx.component.basicdata;

import java.util.Map;

import org.apache.commons.collections.MapUtils;

import com.tx.component.basicdata.generator.BasicDataDBScriptGenerator;
import com.tx.component.basicdata.model.BaseTestBasicModel;
import com.tx.core.dbscript.model.DataSourceTypeEnum;
import com.tx.core.generator.GeneratorUtils;
import com.tx.core.jdbc.sqlsource.SqlSource;
import com.tx.core.jdbc.sqlsource.SqlSourceBuilder;
import com.tx.core.reflection.JpaMetaClass;

/**
 * <功能简述>
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2013-10-8]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class BasicDataGenerator {
    
    public static void main(String[] args) {
        Class<BaseTestBasicModel> basicDataType = BaseTestBasicModel.class;
        @SuppressWarnings("unused")
        String resultFolderPath = "d:/basicdata";
        
        @SuppressWarnings("unused")
        JpaMetaClass<BaseTestBasicModel> jpaMetaClass = JpaMetaClass.forClass(basicDataType);
        String dbScript = BasicDataDBScriptGenerator.generateDBScriptContent(basicDataType,
                DataSourceTypeEnum.MYSQL,
                "UTF-8");
        SqlSource<BaseTestBasicModel> sqlSource = (new SqlSourceBuilder()).build(basicDataType,
                DataSourceTypeEnum.MYSQL.getDialect());
        System.out.println(dbScript);
        
        Map<String, String> resMap = GeneratorUtils.generateQueryConditionMap(jpaMetaClass, sqlSource);
        MapUtils.verbosePrint(System.out, "1", resMap);
        //        System.out.println("toQueryAction: /servicelog/"
        //                + jpaMetaClass.getModulePackageSimpleName() + "/"
        //                + StringUtils.uncapitalize(jpaMetaClass.getEntitySimpleName())
        //                + "/toQuery" + jpaMetaClass.getEntitySimpleName()
        //                + "PagedList.action");
        //        System.out.println("queryAction: /servicelog/"
        //                + jpaMetaClass.getModulePackageSimpleName() + "/"
        //                + StringUtils.uncapitalize(jpaMetaClass.getEntitySimpleName())
        //                + "/query" + jpaMetaClass.getEntitySimpleName()
        //                + "PagedList.action");
    }
}
