/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2016年11月19日
 * <修改描述:>
 */
package com.tx.core.jdbc.sqlbuilder;

import java.io.IOException;

/**
 * 用于字符串构建的辅助类<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2016年11月19日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class SafeAppendable {
    
    /** 字符串构建器实现 */
    private final Appendable appender;
    
    /** 是否为空 */
    private boolean empty = true;
    
    /** <默认构造函数> */
    public SafeAppendable(Appendable a) {
        super();
        this.appender = a;
    }
    
    /** 追加字符 */
    public SafeAppendable append(CharSequence s) {
        try {
            if (empty && s.length() > 0) {
                empty = false;
            }
            appender.append(s);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return this;
    }
    
    /** 判断是否为空 */
    public boolean isEmpty() {
        return empty;
    }
}
