/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2019年7月18日
 * <修改描述:>
 */
package com.tx.component.security.util;

import com.tx.component.security.context.SecurityAccessExpressionHolder;
import com.tx.component.security.context.SecurityResourceHolderStrategy;

/**
 * 安全容器工具类<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2019年7月18日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class SecurityContextUtils {
    
    /**
     * 
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return SecurityAccessExpressionHolder [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static SecurityAccessExpressionHolder getAccessExpressionHolder() {
        SecurityAccessExpressionHolder holder = SecurityResourceHolderStrategy
                .get(SecurityAccessExpressionHolder.class);
        return holder;
    }
    
}
