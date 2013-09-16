/*
 * 描          述:  <描述>
 * 修  改   人:  PengQingyang
 * 修改时间:  2012-12-16
 * <修改描述:>
 */
package com.tx.component.servicelog.logger;

import org.slf4j.helpers.MarkerIgnoringBase;


 /**
  * <功能简述>
  * <功能详细描述>
  * 
  * @author  PengQingyang
  * @version  [版本号, 2012-12-16]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public class ServiceLogger extends MarkerIgnoringBase {
    
    /** 注释内容 */
    private static final long serialVersionUID = -8532128615627064452L;

    /**
     * @param arg0
     * @param arg1
     * @param arg2
     */
    @Override
    public void debug(String arg0, Object arg1, Object arg2) {
        writeLog(arg0, arg1, arg2);
    }

    /**
     * @param arg0
     * @param arg1
     */
    @Override
    public void debug(String arg0, Object arg1) {
        writeLog(arg0, arg1);
    }

    /**
     * @param arg0
     * @param arg1
     */
    @Override
    public void debug(String arg0, Object[] arg1) {
        writeLog(arg0, arg1);
    }

    /**
     * @param arg0
     * @param arg1
     */
    @Override
    public void debug(String arg0, Throwable arg1) {
        writeLog(arg0, arg1);
    }

    /**
     * @param arg0
     */
    @Override
    public void debug(String arg0) {
        writeLog(arg0);
    }

    /**
     * @param arg0
     * @param arg1
     * @param arg2
     */
    @Override
    public void error(String arg0, Object arg1, Object arg2) {
        writeLog(arg0, arg1, arg2);
    }

    /**
     * @param arg0
     * @param arg1
     */
    @Override
    public void error(String arg0, Object arg1) {
        writeLog(arg0, arg1);
    }

    /**
     * @param arg0
     * @param arg1
     */
    @Override
    public void error(String arg0, Object[] arg1) {
        writeLog(arg0, arg1);
    }

    /**
     * @param arg0
     * @param arg1
     */
    @Override
    public void error(String arg0, Throwable arg1) {
        writeLog(arg0, arg1);
    }

    /**
     * @param arg0
     */
    @Override
    public void error(String arg0) {
        writeLog(arg0);
    }

    /**
     * @param arg0
     * @param arg1
     * @param arg2
     */
    @Override
    public void info(String arg0, Object arg1, Object arg2) {
        writeLog(arg0, arg1, arg2);
    }

    /**
     * @param arg0
     * @param arg1
     */
    @Override
    public void info(String arg0, Object arg1) {
        writeLog(arg0, arg1);
    }

    /**
     * @param arg0
     * @param arg1
     */
    @Override
    public void info(String arg0, Object[] arg1) {
        writeLog(arg0, arg1);
    }

    /**
     * @param arg0
     * @param arg1
     */
    @Override
    public void info(String arg0, Throwable arg1) {
        writeLog(arg0, arg1);
    }

    /**
     * @param arg0
     */
    @Override
    public void info(String arg0) {
        writeLog(arg0);
    }

    /**
     * @return
     */
    @Override
    public boolean isDebugEnabled() {
        return true;
    }

    /**
     * @return
     */
    @Override
    public boolean isErrorEnabled() {
        return true;
    }

    /**
     * @return
     */
    @Override
    public boolean isInfoEnabled() {
        return true;
    }

    /**
     * @return
     */
    @Override
    public boolean isTraceEnabled() {
        return false;
    }

    /**
     * @return
     */
    @Override
    public boolean isWarnEnabled() {
        return true;
    }

    /**
     * @param arg0
     * @param arg1
     * @param arg2
     */
    @Override
    public void trace(String arg0, Object arg1, Object arg2) {
        writeLog(arg0, arg1, arg2);
    }

    /**
     * @param arg0
     * @param arg1
     */
    @Override
    public void trace(String arg0, Object arg1) {
        writeLog(arg0, arg1);
    }

    /**
     * @param arg0
     * @param arg1
     */
    @Override
    public void trace(String arg0, Object[] arg1) {
        writeLog(arg0, arg1);
    }

    /**
     * @param arg0
     * @param arg1
     */
    @Override
    public void trace(String arg0, Throwable arg1) {
        writeLog(arg0, arg1);
    }

    /**
     * @param arg0
     */
    @Override
    public void trace(String arg0) {
        writeLog(arg0);
    }

    /**
     * @param arg0
     * @param arg1
     * @param arg2
     */
    @Override
    public void warn(String arg0, Object arg1, Object arg2) {
        writeLog(arg0, arg1, arg2);
    }

    /**
     * @param arg0
     * @param arg1
     */
    @Override
    public void warn(String arg0, Object arg1) {
        writeLog(arg0, arg1);
    }

    /**
     * @param arg0
     * @param arg1
     */
    @Override
    public void warn(String arg0, Object[] arg1) {
        writeLog(arg0, arg1);
    }

    /**
     * @param arg0
     * @param arg1
     */
    @Override
    public void warn(String arg0, Throwable arg1) {
        writeLog(arg0, arg1);
    }

    /**
     * @param arg0
     */
    @Override
    public void warn(String arg0) {
        writeLog(arg0);
    }

    /**
     * 记录日志
     * 
     * @author liujun
     * @param msg
     *            要写的信息
     * @param objs
     *            [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    private void writeLog(String messagePattern, Object... objs) {

    }
}
