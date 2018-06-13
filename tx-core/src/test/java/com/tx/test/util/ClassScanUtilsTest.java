/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2018年6月11日
 * <修改描述:>
 */
package com.tx.test.util;

import java.util.Set;

import com.tx.core.util.ClassScanUtils;
import com.tx.test.model.TestAbstractModel;

/**
 * <功能简述>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2018年6月11日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class ClassScanUtilsTest {
    
    public static void main(String[] args) {
        Set<Class<?>> types = ClassScanUtils.scanByAnnotation(
                com.tx.test.model.TestAnnotationOnModel.class, "com.tx");
        for(Class<?> typeTemp : types){
            System.out.println(typeTemp);
        }
        
        System.out.println("");
        types = ClassScanUtils.scanByAnnotation(
                com.tx.test.model.TestAnnotationOnInterface.class, "com.tx");
        for(Class<?> typeTemp : types){
            System.out.println(typeTemp);
        }
        
        System.out.println("");
        types = ClassScanUtils.scanByAnnotation(
                com.tx.test.model.TestAnnotationOnAbstract.class, "com.tx");
        for(Class<?> typeTemp : types){
            System.out.println(typeTemp);
        }
        
        System.out.println("");
        Set<Class<? extends TestAbstractModel>> types1 = ClassScanUtils.scanByParentClass(TestAbstractModel.class, "com.tx");
        for(Class<?> typeTemp : types1){
            System.out.println(typeTemp);
        }
    }
}
