/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2018年6月1日
 * <修改描述:>
 */
package com.tx.test.reflection;

import java.beans.PropertyDescriptor;

import org.springframework.beans.BeanUtils;
import org.springframework.core.convert.TypeDescriptor;

import com.tx.test.reflection.model.TestReflection;

/**
 * <功能简述>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2018年6月1日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class BeanUtilsTest {
    
    public static void main(String[] args) {
        for(PropertyDescriptor pd : BeanUtils.getPropertyDescriptors(TestReflection.class)){
            if(pd.getWriteMethod() != null && pd.getReadMethod() != null){
                System.out.println(pd.getName());
            }else{
                System.out.println(pd.getName() + ". wm is null " + (pd.getWriteMethod() == null)
                        + ". rm is null " + (pd.getReadMethod() == null));
            }
            
        }
    }
}
