/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2019年5月27日
 * <修改描述:>
 */
package com.tx.generator2test;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import com.tx.core.generator2.CodeGenerator;
import com.tx.generator2test.model.TestMode;
import com.tx.generator2test.model.TestMode2;
import com.tx.generator2test.model.TestMode3;
import com.tx.generator2test.model.TestMode4;

/**
 * 编码生成测试
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2019年5月27日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class CodeGeneratorTest {
    
    public static void main(String[] args) throws IOException {
        
        //CodeGenerator.clearGeneratorPath();
        clearGeneratorPath(CodeGenerator.BASE_CODE_FOLDER);
        
        Class<?> entityType = TestMode.class;
        
        CodeGenerator.generateDBScript(entityType);
        CodeGenerator.generateSqlMap(entityType);
        CodeGenerator.generateDao(entityType);
        CodeGenerator.generateService(entityType);
        CodeGenerator.generateController(entityType);
        
        entityType = TestMode2.class;
        
        CodeGenerator.generateDBScript(entityType);
        CodeGenerator.generateSqlMap(entityType);
        CodeGenerator.generateDao(entityType);
        CodeGenerator.generateDao(entityType);
        CodeGenerator.generateService(entityType);
        CodeGenerator.generateController(entityType);
        
        entityType = TestMode3.class;
        
        CodeGenerator.generateDBScript(entityType);
        CodeGenerator.generateSqlMap(entityType);
        CodeGenerator.generateDao(entityType);
        CodeGenerator.generateDao(entityType);
        CodeGenerator.generateService(entityType);
        CodeGenerator.generateController(entityType);
        
        entityType = TestMode4.class;
        
        CodeGenerator.generateDBScript(entityType);
        CodeGenerator.generateSqlMap(entityType);
        CodeGenerator.generateDao(entityType);
        CodeGenerator.generateDao(entityType);
        CodeGenerator.generateService(entityType);
        CodeGenerator.generateController(entityType);
    }
    
    /**
     * 清空已存在生成代码目录
     * <功能详细描述>
     * 
     * @return void [返回类型说明]
     * @throws IOException 
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static void clearGeneratorPath(String BASE_CODE_FOLDER)
            throws IOException {
        File dest = FileUtils.getFile(BASE_CODE_FOLDER);
        if (!dest.exists()) {
            dest.mkdirs();
        }
        FileUtils.cleanDirectory(dest);
    }
}
