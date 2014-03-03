/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2014年3月2日
 * <修改描述:>
 */
package com.tx.component.basicdata;

import com.tx.component.basicdata.generator.BasicDataCodeGenerator;
import com.tx.component.basicdata.model.TestBasicData;
import com.tx.core.dbscript.model.DataSourceTypeEnum;

/**
 * <功能简述>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2014年3月2日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class BasicCodeGenerator {
    
    public static void main(String[] args) {
        Class<?> basicDataType = TestBasicData.class;
        String codeBaseFolder = "d:/basicdata";
        
        BasicDataCodeGenerator.generate(basicDataType,
                DataSourceTypeEnum.MYSQL,
                codeBaseFolder,
                new String[] { "name", "code" },
                "valid");
        
        System.out.println("success");
    }
    
}
