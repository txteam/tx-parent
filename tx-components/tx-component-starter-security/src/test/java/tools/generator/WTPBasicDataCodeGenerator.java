/*
DataDictTransferRecord * 描 述: <描述> 修 改 人: Administrator 修改时间: 2014年3月2日 <修改描述:>
 */
package tools.generator;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import com.tx.component.auth.model.AuthItem;
import com.tx.component.security.model.JwtSigningKey;
import com.tx.core.generator.basicedata.BasicDataCodeGenerator;
import com.tx.core.util.dialect.DataSourceTypeEnum;

/**
 * 基础数据生成类<br/>
 * 
 * @author Administrator
 * @version [版本号, 2014年3月2日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class WTPBasicDataCodeGenerator {
    
    public static void main(String[] args) throws IOException {
        //基础数据类examineratifyrulecfgitem
        Class<?> basicDataType = JwtSigningKey.class;
        //基础数据逻辑代码生成存放目录com.tx.component.basicdata.generator.
        String codeBaseFolder = "d:/generator/basicdata";
        FileUtils.forceMkdir(new File(codeBaseFolder));
        
        //基础数据生成逻辑代码对应的数据库类型(mysql与oracle)在sqlMap中组装like条件是不一致的
        DataSourceTypeEnum dataSourceType = DataSourceTypeEnum.MYSQL;
        //基础数据唯一键数组uniqueGetterNamesArray
        //String[][] uniqueGetterNamesArray = new String[][] {new String[]{"code"},new String[]{"vcid","name"}};
        String[][] uniqueGetterNamesArray = new String[][] {
                new String[] { "taskId"}};
        //是否有效的字段名
        String validFieldName = "";
        //是否需要生成分页查询列表
        boolean isPaged = false;
        BasicDataCodeGenerator.generate(basicDataType,
                dataSourceType,
                codeBaseFolder,
                uniqueGetterNamesArray,
                validFieldName,
                isPaged,
                true);
        
        System.out.println("success");
    }
}
