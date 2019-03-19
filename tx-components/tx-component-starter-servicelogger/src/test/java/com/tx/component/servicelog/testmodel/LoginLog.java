///*
// * 描          述:  <描述>
// * 修  改   人:  brady
// * 修改时间:  2013-9-24
// * <修改描述:>
// */
//package com.tx.component.servicelog.testmodel;
//
//import javax.persistence.Table;
//
//import com.tx.component.servicelog.model.AbstractServiceLog;
//
///**
// * 人员登录日志<br/>
// * <功能详细描述>
// * 
// * @author  brady
// * @version  [版本号, 2013-9-24]
// * @see  [相关类/方法]
// * @since  [产品/模块版本]
// */
//@Table(name = "mainframe_login_log")
//public class LoginLog extends AbstractServiceLog {
//    
//    /** 登录类型 0:登入 1：登出 */
//    private String type = "0";
//    
//    /** <默认构造函数> */
//    public LoginLog() {
//    }
//    
//    /** <默认构造函数> */
//    public LoginLog(String type) {
//        super("0".equals(type) ? "登录" : "退出");
//    }
//    
//    /**
//     * @return 返回 type
//     */
//    public String getType() {
//        return type;
//    }
//    
//    /**
//     * @param 对type进行赋值
//     */
//    public void setType(String type) {
//        this.type = type;
//    }
//}
