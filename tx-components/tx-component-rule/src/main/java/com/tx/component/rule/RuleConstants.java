/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-1-19
 * <修改描述:>
 */
package com.tx.component.rule;


 /**
  * 规则引擎常量
  * <功能详细描述>
  * 
  * @author  brady
  * @version  [版本号, 2013-1-19]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public class RuleConstants {
    
    /** 规则引擎支持的：规则类型 **/
    /** 规则集 */
    public static final String RULE_TYPE_COLLECTIONS = "collections";
    
    /** 规则类型：drools规则 */
    public static final String RULE_TYPE_DROOLS = "drools";
    
    /** 规则类型：接口实现 */
    public static final String RULE_TYPE_RULE_METHOD = "method";
    
    /** 规则类型：mvel表达式支持 */
    public static final String RULE_TYPE_SCRIPT_MVEL = "script_mvel";
    
    /** 规则类型：juel表达式支持 */
    public static final String RULE_TYPE_SCRIPT_JUEL = "script_juel";
    
    ///** 规则类型：mvel表达式支持 */
    //public static final String RULE_TYPE_SCRIPT_JAVASCRIPT = "script_javascript";
    
    ///** 规则类型：ognl表达式 */
    //public static final String RULE_TYPE_OGNL = "ognl";
    
    ///** 规则类型：groovy代码(暂不进行支持) */
    //public static final String RULE_TYPE_SCRIPT_GROOVE = "script_groove";
    
    ///** 规则类型：java代码(暂不进行支持) */
    //public static final String RULE_TYPE_SCRIPT_JAVA = "script_java";
    
}
