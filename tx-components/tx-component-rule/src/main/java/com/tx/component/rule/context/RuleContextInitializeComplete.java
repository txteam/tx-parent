/*
 * 描          述:  <描述>
 * 修  改   人:  PengQingyang
 * 修改时间:  2013-1-30
 * <修改描述:>
 */
package com.tx.component.rule.context;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;

/**
 * 规则加载器初始化完成事件<br/>
 * 
 * @author  PengQingyang
 * @version  [版本号, 2013-1-30]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class RuleContextInitializeComplete extends ApplicationEvent {
    
    /** 注释内容 */
    private static final long serialVersionUID = -974486400924338446L;
    
    /** <默认构造函数> */
    public RuleContextInitializeComplete(ApplicationContext source) {
        super(source);
    }
    
}
