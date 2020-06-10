/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2020年1月7日
 * <修改描述:>
 */
package com.tx.component.servicelogger.support;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.OrderComparator;

import com.tx.core.exceptions.util.AssertUtils;

/**
 * 业务日志切面<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2020年1月7日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Aspect
public class ServiceLoggerAop implements InitializingBean {
    
    private List<LogArgumentHandler> handlers;
    
    /** <默认构造函数> */
    public ServiceLoggerAop(List<LogArgumentHandler> handlers) {
        super();
        this.handlers = handlers;
    }
    
    /**
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        if (this.handlers == null) {
            this.handlers = new ArrayList<LogArgumentHandler>();
        }
        Collections.sort(this.handlers, OrderComparator.INSTANCE);
    }
    
    /**
     * 业务日志子类插入逻辑执行前执行<br/>
     * <功能详细描述>
     * @param joinPoint [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Before("execution(public void com.tx..ServiceLogger.insert(*))")
    public void before4insert(JoinPoint joinPoint) {
        AssertUtils.notNull(joinPoint, "joinPoint is null.");
        Object arg = joinPoint.getArgs()[0];
        AssertUtils.notNull(arg, "insert arg is null.");
        BeanWrapper bw = PropertyAccessorFactory.forBeanPropertyAccess(arg);
        handle(bw);
    }
    
    /**
     * 业务日志子类批量插入逻辑执行前执行<br/>
     * <功能详细描述>
     * @param joinPoint [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Before("execution(public void com.tx..ServiceLogger.batchInsert(java.util.List))")
    public void before4batchInsert(JoinPoint joinPoint) {
        AssertUtils.notNull(joinPoint, "joinPoint is null.");
        Object arg = joinPoint.getArgs()[0];
        AssertUtils.notNull(arg, "insert arg is null.");
        
        for (Object objTemp : (List<?>) arg) {
            BeanWrapper bw = PropertyAccessorFactory
                    .forBeanPropertyAccess(objTemp);
            handle(bw);
        }
    }
    
    /**
     * 遍历对象属性注入值<br/>
     * <功能详细描述>
     * @param bw [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    private void handle(BeanWrapper bw) {
        for (LogArgumentHandler handler : this.handlers) {
            if (handler.support(bw)) {
                handler.handle(bw);
            }
        }
        //for (PropertyDescriptor pd : bw.getPropertyDescriptors()) {
        //    if (pd.getReadMethod() == null || pd.getWriteMethod() == null) {
        //        continue;
        //    }
        //}
    }
    
    //@Pointcut("execution(public void com.tx..ServiceLogger.insert(..))")
    //public void PointcutDeclaration() {
    //}
    //
    //@Before("PointcutDeclaration()")
    //public void BeforeMethod(JoinPoint jp) {
    //    String methodName = jp.getSignature().getName();
    //    Object[] args = jp.getArgs();
    //    System.out.println("BeforeMethod: The method=" + methodName
    //            + " parameter=[" + Arrays.asList(args) + "]");
    //}
    //
    ////后置通知,方法执行之后执行(不管是否发生异常)
    //@After("PointcutDeclaration()")
    //public void AfterMethod(JoinPoint jp) {
    //    String methodName = jp.getSignature().getName();
    //    Object[] args = jp.getArgs();
    //    System.out.println("AfterMethod: The method=" + methodName
    //            + " parameter=[" + Arrays.asList(args) + "]");
    //}
    //
    ////返回通知,方法正常执行完毕之后执行
    //@AfterReturning(value = "PointcutDeclaration()", returning = "result")
    //public void AfterReturningMethod(JoinPoint jp, Object result) {
    //    String methodName = jp.getSignature().getName();
    //    Object[] args = jp.getArgs();
    //    System.out.println("AfterReturningMethod: The method=" + methodName
    //            + ",parameter=[" + Arrays.asList(args) + "],result=" + result);
    //}
    //
    ////异常通知,在方法抛出异常之后执行
    //@AfterThrowing(value = "PointcutDeclaration()", throwing = "e")
    //public void AfterThrowingMethod(JoinPoint jp, Exception e) {
    //    String methodName = jp.getSignature().getName();
    //    Object[] args = jp.getArgs();
    //    System.out.println("AfterThrowingMethod: The method=" + methodName
    //            + ",parameter=[" + Arrays.asList(args) + "],exception="
    //            + e.getMessage());
    //}
    //
    //@Pointcut("target(com.tx.component.servicelogger.support.ServiceLogger)")
    //protected void test3() {
    //    System.out.println("test3 ...");
    //}
    //
    //@Before("test3()")
    //public void test2(JoinPoint joinPoint) {
    //    System.out.println("test2 ...");
    //}
    //
    //@Before("target(com.tx.component.servicelogger.support.ServiceLogger)")
    //public void test1(JoinPoint joinPoint) {
    //    System.out.println("test1 ...");
    //}
}
