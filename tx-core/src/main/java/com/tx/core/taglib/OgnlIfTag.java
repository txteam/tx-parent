/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-1-5
 * <修改描述:>
 */
package com.tx.core.taglib;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.jstl.core.ConditionalTagSupport;

import ognl.OgnlException;

import org.apache.taglibs.standard.tag.common.core.NullAttributeException;

import com.tx.core.taglib.model.TagOgnlExpressionCache;

/**
 * 继承于jstl ifTag
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2013-1-5]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class OgnlIfTag extends ConditionalTagSupport {
    
    /** 注释内容 */
    private static final long serialVersionUID = -6531758974089388301L;
    
    private String test; // the value of the 'test' attribute
    
    //*********************************************************************
    // Constructor and lifecycle management

    // initialize inherited and local state
    public OgnlIfTag() {
        super();
        init();
    }
    
    /**
     * @return
     * @throws JspTagException
     */
    @Override
    protected boolean condition() throws JspTagException {
        try {
            Object r = TagOgnlExpressionCache.getValue(test, pageContext);

            if (r == null)
                throw new NullAttributeException("if", "test");
            else
                return (((Boolean) r).booleanValue());
        } catch (JspException ex) {
            throw new JspTagException(ex.toString(), ex);
        } catch (OgnlException e) {
            throw new JspTagException(e.toString(), e);
        }
    }

    // Releases any resources we may have (or inherit)
    public void release() {
        super.release();
        init();
    }
    
    /**
      * 初始化方法
      * <功能详细描述> [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    private void init() {
        test = null;
    }
    
    // receives the tag's 'test' attribute
    public void setTest(String test) {
        this.test = test;
    }
    
}
