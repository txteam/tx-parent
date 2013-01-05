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
import javax.servlet.jsp.tagext.Tag;

import ognl.OgnlException;

import org.apache.taglibs.standard.resources.Resources;
import org.apache.taglibs.standard.tag.common.core.ChooseTag;
import org.apache.taglibs.standard.tag.common.core.NullAttributeException;

import com.tx.core.taglib.model.TagOgnlExpressionCache;

/**
 * 定义类似于jstl标签的支持ognl表达式的标签
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2013-1-5]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class OgnlWhenTag extends ConditionalTagSupport {
    
    //*********************************************************************
    // Implementation of exclusive-conditional behavior
    /** 注释内容 */
    private static final long serialVersionUID = 2237737413303969243L;
    
    //*********************************************************************
    // Constructor and lifecycle management
    
    // initialize inherited and local state
    public OgnlWhenTag() {
        super();
        init();
    }
    
    /*
     * Includes its body if condition() evalutes to true AND its parent
     * ChooseTag wants it to do so.  The condition will not even be
     * evaluated if ChooseTag instructs us not to run.
     */
    public int doStartTag() throws JspException {
        
        Tag parent;
        
        // make sure we're contained properly
        if (!((parent = getParent()) instanceof ChooseTag))
            throw new JspTagException(
                    Resources.getMessage("WHEN_OUTSIDE_CHOOSE"));
        
        // make sure our parent wants us to continue
        if (!((ChooseTag) parent).gainPermission())
            return SKIP_BODY; // we've been reeled in
            
        // handle conditional behavior
        if (condition()) {
            ((ChooseTag) parent).subtagSucceeded();
            return EVAL_BODY_INCLUDE;
        } else
            return SKIP_BODY;
    }
    
    // Releases any resources we may have (or inherit)
    public void release() {
        super.release();
        init();
    }
    
    //*********************************************************************
    // Supplied conditional logic
    
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
    
    //*********************************************************************
    // Private state
    
    private String test; // the value of the 'test' attribute
    
    //*********************************************************************
    // Accessors
    
    // receives the tag's 'test' attribute
    public void setTest(String test) {
        this.test = test;
    }
    
    //*********************************************************************
    // Private utility methods
    
    // resets internal state
    private void init() {
        test = null;
    }
    
}
