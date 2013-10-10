/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-10-8
 * <修改描述:>
 */
package com.tx.component.basicdata;

import com.tx.component.basicdata.generator.BasicDataDBScriptGenerator;
import com.tx.component.basicdata.testmodel.BaseTestBasicModel;
import com.tx.core.dbscript.model.DataSourceTypeEnum;
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
        Class<?> serviceLogType = BaseTestBasicModel.class;
        String resultFolderPath = "d:/servicelog";
        
        JpaMetaClass<?> jpaMetaClass = JpaMetaClass.forClass(serviceLogType);
        String dbScript = BasicDataDBScriptGenerator.generateDBScriptContent(serviceLogType,
                DataSourceTypeEnum.ORACLE,
                "UTF-8");
        
        System.out.println(dbScript);
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
