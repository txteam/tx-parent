/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-1-21
 * <修改描述:>
 */
package com.tx.component.rule.drools;

import java.io.File;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.core.io.Resource;


 /**
  * drools规则引擎<br/>
  *     用以封闭drools底层实现的复杂性，转换成，规则引擎的封装形式<br/>
  * <功能详细描述>
  * 
  * @author  brady
  * @version  [版本号, 2013-1-21]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public class DroolsRulesEngine {
    
    /** 基础规则库，需要在初始化加载其他规则前，提前加载基础规则库 */
    private List<Resource> baseLibRuleLocations;
    
    /**
      * 启动时，启动drools引擎，并加载相关规则
      * <功能详细描述> [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public void initEngine(List<?> rules){
        
    }
    
    
    public void executeRules(WorkingEnvironmentCallback callback) {
//        WorkingMemory workingMemory = rules.newStatefulSession();
//        if (debug) {
//           workingMemory
//              .addEventListener(new DebugWorkingMemoryEventListener());
//        }
//        callback.initEnvironment(workingMemory);
//        workingMemory.fireAllRules();
     }
    
    public static void main(String[] args) throws Exception {
        Calendar c = Calendar.getInstance();
        c.setTime(DateUtils.parseDate("2013-08-27", new String[]{"yyyy-MM-dd"}));
        
        System.out.println(c.get(Calendar.DATE));
        
        String a = "d:/aa/bb";
        File file = new File(a);
        if(!file.isDirectory()){
            System.out.println("111");
            //file.mkdirs();
            FileUtils.forceMkdir(file);
        }
    }
    
}
