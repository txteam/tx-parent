/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2012-12-11
 * <修改描述:>
 */
package com.tx.component.auth.mybatis;

import com.tx.core.mybatis.generator.JpaEntityFreeMarkerGenerator;


 /**
  * <功能简述>
  * <功能详细描述>
  * 
  * @author  brady
  * @version  [版本号, 2012-12-11]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public class GenerateCodeByJpaModelTest {
    
    public static void main(String[] args) {
        
        JpaEntityFreeMarkerGenerator factory = new JpaEntityFreeMarkerGenerator();
        factory.setLoadTemplateClass(GenerateCodeByJpaModelTest.class);
        
        factory.setDaoImplTemplateFilePath("com/tx/component/auth/mybatis/daoImpl.ftl");
        factory.setDaoTemplateFilePath("com/tx/component/auth/mybatis/dao.ftl");
        factory.setServiceTemplateFilePath("com/tx/component/auth/mybatis/service.ftl");
        factory.setServiceTestTemplateFilePath("com/tx/component/auth/mybatis/serviceTest.ftl");
        factory.setSqlMapTemplateFilePath("com/tx/component/auth/mybatis/sqlMap.ftl");
        
        //生成后在自己指定的文件夹中去找即可
        factory.generate(null, "d:/mybatis");
        
        System.out.println("success");
    }
}
