/*
 * 描          述:  <描述>
 * 修  改   人:  PengQingyang
 * 修改时间:  2013-10-2
 * <修改描述:>
 */
package com.tx.component.basicdata.context;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.InitializingBean;

import com.tx.component.basicdata.annotation.BasicData;
import com.tx.component.basicdata.executor.BasicDataExecutor;
import com.tx.component.basicdata.executor.EnumBasicDataExecutor;
import com.tx.component.basicdata.executor.SqlSourceBasicDataExecutor;
import com.tx.core.exceptions.util.ExceptionWrapperUtils;
import com.tx.core.reflection.exception.ReflectionException;
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
public class BasicDataExecutorFactory extends BasicDataContextConfigurator
        implements InitializingBean {
    
    /** 基础数据执行器本次缓存 */
    private static Map<Class<?>, BasicDataExecutor<?>> basicDataExecutorMapping = new HashMap<Class<?>, BasicDataExecutor<?>>();
    
    private static BasicDataExecutorFactory basicDataExecutorFactory;
    
    /** <默认构造函数> */
    protected BasicDataExecutorFactory() {
        super();
    }
    
    /**
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        BasicDataExecutorFactory.basicDataExecutorFactory = this;
        
        loadExecutorOnStartup();
    }
    
    /** 根据类型获取执行器 */
    @SuppressWarnings("unchecked")
    public static <TYPE> BasicDataExecutor<TYPE> getExecutor(Class<TYPE> type) {
        synchronized (type) {
            if (basicDataExecutorMapping.containsKey(type)) {
                return (BasicDataExecutor<TYPE>) basicDataExecutorMapping.get(type);
            } else {
                BasicDataExecutor<TYPE> resExecutor = null;//buildBasicDataExecutor(type);
                basicDataExecutorMapping.put(type, resExecutor);
                return resExecutor;
            }
        }
    }
    
    /**
      * 构建基础数据执行器<br/>
      *<功能详细描述>
      * @param type
      * @return [参数说明]
      * 
      * @return BasicDataExecutor<TYPE> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    @SuppressWarnings({ "unchecked" })
    private static <TYPE> BasicDataExecutor<TYPE> buildBasicDataExecutor(
            Class<TYPE> type) {
        try {
            type.getConstructor();
        } catch (SecurityException e1) {
            throw ExceptionWrapperUtils.wrapperSILException(ReflectionException.class,
                    "基础数据类型无参构造器访问异常：{}",
                    e1,
                    new Object[] { e1 });
        } catch (NoSuchMethodException e1) {
            throw ExceptionWrapperUtils.wrapperSILException(ReflectionException.class,
                    "基础数据类型应当具备无参数构造器.异常信息：{}",
                    e1,
                    new Object[] { e1 });
        }
        
        //对应的executor也应该具备无参构造函数
        @SuppressWarnings("rawtypes")
        Class<? extends BasicDataExecutor> basicDataExecutorClassTemp = null;
        
        if (type.isAnnotationPresent(BasicData.class)) {
            BasicData basicDataAnno = type.getAnnotation(BasicData.class);
            basicDataExecutorClassTemp = basicDataAnno.executor();
        } else {
            if(type.isEnum()){
                basicDataExecutorClassTemp = EnumBasicDataExecutor.class;
            }else{
                basicDataExecutorClassTemp = SqlSourceBasicDataExecutor.class;
            }
        }
        
        try {
            @SuppressWarnings({ "rawtypes" })
            Constructor<? extends BasicDataExecutor> constructor = basicDataExecutorClassTemp.getConstructor(Class.class,
                    BasicDataContextConfigurator.class);
            @SuppressWarnings("rawtypes")
            BasicDataExecutor basicDataExecutor = constructor.newInstance(type,
                    (BasicDataContextConfigurator) basicDataExecutorFactory);
            
            return basicDataExecutor;
        } catch (SecurityException e) {
            throw ExceptionWrapperUtils.wrapperSILException(ReflectionException.class,
                    "获取基础数据执行器构造器异常:{}",
                    e,
                    new Object[] { e });
        } catch (NoSuchMethodException e) {
            throw ExceptionWrapperUtils.wrapperSILException(ReflectionException.class,
                    "获取基础数据执行器构造器异常",
                    e);
        } catch (IllegalArgumentException e) {
            throw ExceptionWrapperUtils.wrapperSILException(ReflectionException.class,
                    "实例化基础数据执行器异常：{}",
                    e,
                    new Object[] { e });
        } catch (InstantiationException e) {
            throw ExceptionWrapperUtils.wrapperSILException(ReflectionException.class,
                    "实例化基础数据执行器异常：{}",
                    e,
                    new Object[] { e });
        } catch (IllegalAccessException e) {
            throw ExceptionWrapperUtils.wrapperSILException(ReflectionException.class,
                    "实例化基础数据执行器异常：{}",
                    e,
                    new Object[] { e });
        } catch (InvocationTargetException e) {
            throw ExceptionWrapperUtils.wrapperSILException(ReflectionException.class,
                    "实例化基础数据执行器异常：{}",
                    e,
                    new Object[] { e });
        }
    }
    
    /**
     * 
     */
    @Override
    protected void loadExecutorOnStartup() {
        Set<Class<?>> basicDataClassSet = ClassScanUtils.scanByAnnotation(BasicData.class,
                StringUtils.splitByWholeSeparator(getBasePackages(), ","));
        
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
                BasicDataExecutor<?> basicDataExecutorTemp = buildBasicDataExecutor(basicDataClassTemp);
                
                basicDataExecutorMapping.put(basicDataClassTemp,
                        basicDataExecutorTemp);
            } catch (ReflectionException e) {
                if (isStopOnBuildBasicDataExecutorWhenStartup()) {
                    throw e;
                } else {
                    logger.warn("构造基础数据查询器异常:{}", e);
                    continue;
                }
            }
        }
    }
}
