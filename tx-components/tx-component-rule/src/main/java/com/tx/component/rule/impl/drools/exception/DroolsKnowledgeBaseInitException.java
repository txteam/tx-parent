/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-3-31
 * <修改描述:>
 */
package com.tx.component.rule.impl.drools.exception;

import org.drools.builder.KnowledgeBuilderErrors;


 /**
  * 创建 drools KnowledgeBase 异常
  * <功能详细描述>
  * 
  * @author  brady
  * @version  [版本号, 2013-3-31]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public class DroolsKnowledgeBaseInitException extends Exception {

    /** 注释内容 */
    private static final long serialVersionUID = -1831736986193853473L;
    
    private KnowledgeBuilderErrors knowledgeBuilderErrors;

    /** <默认构造函数> */
    public DroolsKnowledgeBaseInitException(
            KnowledgeBuilderErrors knowledgeBuilderErrors) {
        super();
        this.knowledgeBuilderErrors = knowledgeBuilderErrors;
    }

    /**
     * @return 返回 knowledgeBuilderErrors
     */
    public KnowledgeBuilderErrors getKnowledgeBuilderErrors() {
        return knowledgeBuilderErrors;
    }

    /**
     * @param 对knowledgeBuilderErrors进行赋值
     */
    public void setKnowledgeBuilderErrors(
            KnowledgeBuilderErrors knowledgeBuilderErrors) {
        this.knowledgeBuilderErrors = knowledgeBuilderErrors;
    }
}
