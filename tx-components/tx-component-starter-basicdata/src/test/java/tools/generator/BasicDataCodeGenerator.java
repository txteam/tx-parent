/*
DataDictTransferRecord * 描 述: <描述> 修 改 人: Administrator 修改时间: 2014年3月2日 <修改描述:>
 */
package tools.generator;

import java.io.IOException;

import com.tx.component.basicdata.model.DataDict;
import com.tx.component.configuration.model.ConfigPropertyItem;
import com.tx.core.generator2.CodeGenerator;

/**
 * 基础数据生成类<br/>
 * 
 * @author Administrator
 * @version [版本号, 2014年3月2日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class BasicDataCodeGenerator {
    
    public static void main(String[] args) throws IOException {
        //Class<?> entityType = DataDict.class;
        Class<?> entityType = ConfigPropertyItem.class;
        
        CodeGenerator.generateDBScript(entityType);
        CodeGenerator.generateSqlMap(entityType);
        CodeGenerator.generateDao(entityType);
        CodeGenerator.generateService(entityType);
        CodeGenerator.generateController(entityType);
    }
}
