/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2014年3月2日
 * <修改描述:>
 */
package com.tx.component.basicdata;

import com.tx.component.basicdata.generator.BasicDataCodeGenerator;
import com.tx.component.basicdata.model.TestBasicData;
import com.tx.component.basicdata.model.TestBasicData2;
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
public class BasicCodeGeneratorTest {
    
    public static void main1(String[] args) {
        Class<?> basicDataType = TestBasicData.class;
        String codeBaseFolder = "d:/basicdata";
        
        BasicDataCodeGenerator.generate(basicDataType,
                DataSourceTypeEnum.MYSQL,
                codeBaseFolder,
                new String[][] { new String[] { "name" },
                        new String[] { "name", "code" } },
                "valid",
                false);
        
        System.out.println("success");
    }
    
    public static void main(String[] args) {
        Class<?> basicDataType = TestBasicData2.class;
        String codeBaseFolder = "d:/basicdata";
        
        BasicDataCodeGenerator.generate(basicDataType,
                DataSourceTypeEnum.MYSQL,
                codeBaseFolder,
                new String[][] { new String[] { "name" } },
                "valid",
                true);
        
        System.out.println("success");
    }
    
}
