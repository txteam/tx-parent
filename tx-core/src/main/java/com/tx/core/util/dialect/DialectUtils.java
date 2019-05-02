/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2014年3月22日
 * <修改描述:>
 */
package com.tx.core.util.dialect;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.dialect.Dialect;
import org.hibernate.dialect.H2Dialect;
import org.hibernate.dialect.MySQL5Dialect;
import org.hibernate.dialect.Oracle10gDialect;
import org.hibernate.dialect.Oracle9iDialect;
import org.hibernate.dialect.SQLServer2008Dialect;
import org.springframework.util.ClassUtils;

import com.tx.core.exceptions.argument.ArgIllegalException;
import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.util.MessageUtils;
import com.tx.core.util.ObjectUtils;

/**
 * 方言工具类<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2014年3月22日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public abstract class DialectUtils {
    
    /** 方言类实例映射 */
    private static final Map<String, Dialect> dialectMap = new HashMap<String, Dialect>();
    
    public static final SQLServer2008Dialect sqlServer2008Dialect = new SQLServer2008Dialect();
    
    public static final Oracle10gDialect oracle10gDialect = new Oracle10gDialect();
    
    public static final Oracle9iDialect oracle9iDialect = new Oracle9iDialect();
    
    public static final H2Dialect h2Dialect = new H2Dialect();
    
    public static final MySQL5Dialect mySQL5Dialect = new MySQL5Dialect();
    
    /**
     * 获取方言实例<br/>
     * <功能详细描述>
     * @param databasePlatform
     * @return [参数说明]
     * 
     * @return Dialect [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static Dialect getDialect(String databasePlatform) {
        AssertUtils.notEmpty(databasePlatform, "databasePlatform is empty.");
        if (dialectMap.containsKey(databasePlatform)) {
            return dialectMap.get(databasePlatform);
        }
        
        Class<?> dialectType;
        try {
            dialectType = ClassUtils.forName(databasePlatform,
                    DialectUtils.class.getClassLoader());
        } catch (ClassNotFoundException | LinkageError e) {
            throw new ArgIllegalException(MessageUtils.format(
                    "databasePlatform:{} is invalid.", databasePlatform));
        }
        AssertUtils.notNull(dialectType,
                "dialectType is null.databasePlatform:{}",
                databasePlatform);
        AssertUtils.isTrue(ClassUtils.isAssignable(Dialect.class, dialectType),
                "dialectType is not assign from Dialect.class");
        
        Dialect dialect = (Dialect) ObjectUtils.newInstance(dialectType);
        
        dialectMap.put(databasePlatform, dialect);
        return dialect;
    }
}
