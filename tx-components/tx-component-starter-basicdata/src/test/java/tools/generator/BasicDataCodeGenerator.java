/*
DataDictTransferRecord * 描 述: <描述> 修 改 人: Administrator 修改时间: 2014年3月2日 <修改描述:>
 */
package tools.generator;

import java.io.IOException;

import com.tx.component.plugin.model.PluginInstance;
import com.tx.core.generator2.CodeGenerator;
import com.tx.core.generator2.model.ViewTypeEnum;

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
        boolean toProjectPath = true;//是否生成覆盖到项目代码中，如果设置为false则会写入D盘的目录中
        Class<?> entityType = PluginInstance.class;
        ViewTypeEnum viewType = ViewTypeEnum.LIST;
        boolean needConfirmOverwriteFile = true;//覆盖文件前是否需要提示
        
        //基础数据逻辑代码生成存放目录com.tx.component.basicdata.generator.
        if (toProjectPath) {
            String project_path = org.springframework.util.StringUtils
                    .cleanPath(entityType.getResource("/").getPath() + "../..");
            String codeBaseFolder = project_path;
            CodeGenerator.BASE_CODE_FOLDER = codeBaseFolder;
        }
        
        //基础数据生成逻辑代码对应的数据库类型(mysql与oracle)在sqlMap中组装like条件是不一致的
        CodeGenerator.NEED_CONFIRM_WHEN_EXSITS = needConfirmOverwriteFile;
        CodeGenerator.generateDBScript(entityType);
        CodeGenerator.generateSqlMap(entityType);
        CodeGenerator.generateDao(entityType);
        CodeGenerator.generateService(entityType);
        CodeGenerator.generateController(entityType, viewType);
        
        System.out.println("success");
    }
}
