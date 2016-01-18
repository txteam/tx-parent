/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2016年1月3日
 * <修改描述:>
 */
package com.tx.core.proxy;

import java.lang.reflect.Proxy;

import com.tx.core.util.AopTargetUtils;

public class TestCount {
    public static void main(String[] args) {  
        CountImpl countImpl = new CountImpl();  
        CountProxy countProxy = new CountProxy(countImpl);  
        countProxy.updateCount();  
        countProxy.queryCount();  
        
        System.out.println(AopTargetUtils.isProxy(countProxy));
        System.out.println(Proxy.isProxyClass(countProxy.getClass()));
        for(Class t : countProxy.getClass().getInterfaces()){
            System.out.println(t);
        }
    }  
}
