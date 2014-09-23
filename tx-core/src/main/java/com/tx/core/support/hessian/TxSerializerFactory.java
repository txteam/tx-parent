/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2014年6月4日
 * <修改描述:>
 */
package com.tx.core.support.hessian;

import java.util.Set;

import org.springframework.beans.factory.InitializingBean;

import javassist.Modifier;

import com.caucho.hessian.io.SerializerFactory;
import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.util.ClassScanUtils;
import com.tx.core.util.ObjectUtils;

/**
 * 继承hessian中的序列化工厂
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2014年6月4日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class TxSerializerFactory extends SerializerFactory implements InitializingBean{
    
    private String packages = "com.tx.core.support.hessian.impl";
    
    /** <默认构造函数> */
    public TxSerializerFactory() {
        super();
    }
    
    /** <默认构造函数> */
    public TxSerializerFactory(ClassLoader loader) {
        super(loader);
    }
    
    /**
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        registSerializerFactory();
    }

    @SuppressWarnings("rawtypes")
    private void registSerializerFactory() {
        AssertUtils.notEmpty(packages, "packages is empty.");
        String[] packages = this.packages.split(",");
        Set<Class<? extends TypeHandleSerializerFactory>> factoryClassSet = ClassScanUtils.scanByParentClass(TypeHandleSerializerFactory.class,
                packages);
        for (Class<? extends TypeHandleSerializerFactory> type : factoryClassSet) {
            if (Modifier.isInterface(type.getModifiers())
                    || Modifier.isAbstract(type.getModifiers())) {
                continue;
            }
            //没有无参构造函数的直接跳过
            try {
                if (type.getConstructor() == null) {
                    
                    continue;
                }
            } catch (Exception e) {
                continue;
            }
            TypeHandleSerializerFactory serializerFactory = ObjectUtils.newInstance(type);
            super.addFactory(serializerFactory);
        }
        
    }
    
    /**
     * @return 返回 packages
     */
    public String getPackages() {
        return packages;
    }
    
    /**
     * @param 对packages进行赋值
     */
    public void setPackages(String packages) {
        this.packages = packages;
    }
}
