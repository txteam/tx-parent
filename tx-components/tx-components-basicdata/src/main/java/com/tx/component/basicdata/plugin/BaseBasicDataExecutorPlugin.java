/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-10-10
 * <修改描述:>
 */
package com.tx.component.basicdata.plugin;

import org.springframework.core.Ordered;

import com.tx.component.basicdata.executor.BasicDataExecutor;
import com.tx.core.exceptions.logic.CloneException;
import com.tx.core.exceptions.util.ExceptionWrapperUtils;
import com.tx.core.reflection.JpaMetaClass;

/**
 * 基础数据执行器插件基类<br/>
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2013-10-10]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public abstract class BaseBasicDataExecutorPlugin implements
        BasicDataExecutorPlugin, Ordered, Cloneable {
    
    private BasicDataExecutor<?> basicDataExecutor;
    
    private Class<?> type;
    
    private JpaMetaClass<?> jpaMetaClass;
    
    /** <默认构造函数> */
    public BaseBasicDataExecutorPlugin() {
    }
    
    /**
     * @param basicDataExecutor
     * @return
     */
    @Override
    public BasicDataExecutorPlugin plugin(BasicDataExecutor<?> basicDataExecutor,Class<?> type) {
        try {
            BasicDataExecutorPlugin newPlugin = (BasicDataExecutorPlugin)this.clone();
            newPlugin.setBasicDataExecutor(basicDataExecutor);
            newPlugin.setType(type);
            return newPlugin;
        } catch (CloneNotSupportedException e) {
            throw ExceptionWrapperUtils.wrapperSILException(CloneException.class, "克隆基础数据执行器插件异常", e);
        }
    }

    /**
     * @return 返回 basicDataExecutor
     */
    protected BasicDataExecutor<?> getBasicDataExecutor() {
        return basicDataExecutor;
    }

    /**
     * @param 对basicDataExecutor进行赋值
     */
    public void setBasicDataExecutor(BasicDataExecutor<?> basicDataExecutor) {
        this.basicDataExecutor = basicDataExecutor;
    }

    /**
     * @return 返回 type
     */
    protected Class<?> getType() {
        return type;
    }

    /**
     * @param 对type进行赋值
     */
    public void setType(Class<?> type) {
        this.type = type;
        this.jpaMetaClass = JpaMetaClass.forClass(type);
    }

    /**
     * @return 返回 jpaMetaClass
     */
    protected JpaMetaClass<?> getJpaMetaClass() {
        return jpaMetaClass;
    }
}
