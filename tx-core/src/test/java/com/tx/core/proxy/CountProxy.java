/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2016年1月3日
 * <修改描述:>
 */
package com.tx.core.proxy;

public class CountProxy implements Count{
    
    private CountImpl countImpl;  
    
    /** 
     * 覆盖默认构造器 
     *  
     * @param countImpl 
     */  
    public CountProxy(CountImpl countImpl) {  
        this.countImpl = countImpl;  
    }  
  
    @Override  
    public void queryCount() {  
        System.out.println("事务处理之前");  
        // 调用委托类的方法;  
        countImpl.queryCount();  
        System.out.println("事务处理之后");  
    }  
  
    @Override  
    public void updateCount() {  
        System.out.println("事务处理之前");  
        // 调用委托类的方法;  
        countImpl.updateCount();  
        System.out.println("事务处理之后");  
  
    }  
}
