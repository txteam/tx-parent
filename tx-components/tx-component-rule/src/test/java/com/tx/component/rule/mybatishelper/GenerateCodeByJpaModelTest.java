/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2012-12-11
 * <修改描述:>
 */
package com.tx.component.rule.mybatishelper;

import com.tx.component.rule.model.SimpleRulePropertyValue;
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
        
        factory.setDaoImplTemplateFilePath("com/tx/component/rule/mybatishelper/daoImpl.ftl");
        factory.setDaoTemplateFilePath("com/tx/component/rule/mybatishelper/dao.ftl");
        factory.setServiceTemplateFilePath("com/tx/component/rule/mybatishelper/service.ftl");
        factory.setServiceTestTemplateFilePath("com/tx/component/rule/mybatishelper/serviceTest.ftl");
        factory.setSqlMapTemplateFilePath("com/tx/component/rule/mybatishelper/sqlMap.ftl");
        
        //生成后在自己指定的文件夹中去找即可
        factory.generate(SimpleRulePropertyValue.class, "d:/mybatis");
        
        System.out.println("success");
    }
}
