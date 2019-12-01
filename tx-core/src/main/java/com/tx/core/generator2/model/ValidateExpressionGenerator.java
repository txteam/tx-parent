/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2019年12月1日
 * <修改描述:>
 */
package com.tx.core.generator2.model;

import com.tx.core.generator2.util.GeneratorUtils.EntityProperty;

/**
 * 验证表达式生成器<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2019年12月1日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface ValidateExpressionGenerator {
    
    /**
     * 默认的验证表达式生成逻辑<br/>
     * <功能详细描述>
     * @param property [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    void generate(EntityProperty property, Class<?> entityType);
}
