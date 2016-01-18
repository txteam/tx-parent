/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2016年1月3日
 * <修改描述:>
 */
package com.tx.core.proxy;

import java.lang.reflect.Proxy;

import com.tx.core.util.AopTargetUtils;
import com.tx.core.util.ClassUtils;

public class TestCglib2Count {
    public static void main(String[] args) {  
        CountImpl countImpl = new CountImpl();  
        Count countProxy = (Count)((new CountCglibProxy2()).bind(countImpl));  
        countProxy.updateCount();  
        countProxy.queryCount();  
        
        System.out.println(AopTargetUtils.isProxy(countProxy));
        System.out.println(Proxy.isProxyClass(countProxy.getClass()));
        System.out.println(countProxy.getClass());
        for(Class t : countProxy.getClass().getInterfaces()){
            System.out.println(t);
        }
    }  
}
