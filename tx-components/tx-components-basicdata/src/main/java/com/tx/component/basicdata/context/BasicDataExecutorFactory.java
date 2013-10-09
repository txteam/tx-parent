/*
 * 描          述:  <描述>
 * 修  改   人:  PengQingyang
 * 修改时间:  2013-10-2
 * <修改描述:>
 */
package com.tx.component.basicdata.context;

import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.tx.component.basicdata.annotation.BasicData;
import com.tx.component.basicdata.executor.BasicDataExecutor;
import com.tx.core.util.ClassScanUtils;

/**
 * 基础数据执行器工厂类
 * <功能详细描述>
 * 
 * @author  PengQingyang
 * @version  [版本号, 2013-10-2]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class BasicDataExecutorFactory extends BasicDataContextConfigurator {
    
    private static Map<Class<?>, BasicDataExecutor<?>> basicDataExecutorMapping = new HashMap<Class<?>, BasicDataExecutor<?>>();
    
    public static <TYPE> BasicDataExecutor<TYPE> getExecutor(Class<TYPE> type) {
        return null;
    }
    
    /**
     * 
     */
    @Override
    protected void loadExecutorOnStartup() {
        Set<Class<?>> basicDataClassSet = ClassScanUtils.scanByAnnotation(BasicData.class,
                StringUtils.splitByWholeSeparator(getBasePackage(), ","));
        
        if (CollectionUtils.isEmpty(basicDataClassSet)) {
            return;
        }
        
        for (Class<?> basicDataClassTemp : basicDataClassSet) {
            //如果为接口，抽象类，或不含有BasicData注解则不进行解析
            if (basicDataClassTemp.isInterface()
                    || Modifier.isAbstract(basicDataClassTemp.getModifiers())
                    || !basicDataClassTemp.isAnnotationPresent(BasicData.class)) {
                continue;
            }
            
            //如果不含有无参构造函数也不进行解析
            try {
                basicDataClassTemp.getConstructor();
            } catch (SecurityException e) {
                continue;
            } catch (NoSuchMethodException e) {
                continue;
            }
            
            BasicData basicDataAnno = basicDataClassTemp.getAnnotation(BasicData.class);
            Class<? extends BasicDataExecutor> basicDataExecutorClassTemp = basicDataAnno.executor();
            
            try {
                basicDataExecutorClassTemp.getConstructor();
            } catch (SecurityException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
