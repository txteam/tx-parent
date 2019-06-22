/*
DataDictTransferRecord * 描 述: <描述> 修 改 人: Administrator 修改时间: 2014年3月2日 <修改描述:>
 */
package tools.generator;

import java.io.IOException;

import com.tx.component.auth.model.AuthRefItem;
import com.tx.component.role.model.RoleRefItem;
import com.tx.core.generator2.CodeGenerator;

/**
 * 基础数据生成类<br/>
 *
 * @author Administrator
 * @version [版本号, 2014年3月2日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class SecurityCodeGenerator {

    public static void main(String[] args) throws IOException {
        Class<?> entityType = RoleRefItem.class;

        //基础数据逻辑代码生成存放目录com.tx.component.basicdata.generator.
        String project_path = org.springframework.util.StringUtils
                .cleanPath(entityType.getResource("/").getPath() + "../..");
        String codeBaseFolder = project_path;
        CodeGenerator.BASE_CODE_FOLDER = codeBaseFolder;

        //基础数据生成逻辑代码对应的数据库类型(mysql与oracle)在sqlMap中组装like条件是不一致的
        //CodeGenerator.generateDBScript(entityType);
        CodeGenerator.generateSqlMap(entityType);
        CodeGenerator.generateDao(entityType);
        CodeGenerator.generateService(entityType);
        CodeGenerator.generateController(entityType);
        System.out.println("success");
    }
}
