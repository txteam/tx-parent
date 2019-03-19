/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2016年1月17日
 * <修改描述:>
 */
package com.tx.component.command;


/**
 * 交易常量类<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2016年1月17日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface CommandConstants {
    
    /** 交易关键字：金额冻结处理记录 */
    String TRADING_KEY_AMOUNT_FROZEN_PROCESS_RECORDS = "TRADING_KEY_AMOUNT_FROZEN_PROCESS_RECORDS";
    
    /** 交易关键字：金额流转记录 */
    String TRADING_KEY_AMOUNT_FLOW_RECORDS = "TRADING_KEY_AMOUNT_FLOW_RECORDS";
    
    /** 交易关键字：客户账户处理器 */
    String TRADING_KEY_CLIENTACCOUNTHANDLE = "TRADING_KEY_CLIENTACCOUNTHANDLE";
    
    /** 交易关键字：实收变更记录 */
    String TRADING_KEY_CLIENTACTUALRECEIVEDCHANGERECORDS = "TRADING_KEY_CLIENTACTUALRECEIVEDCHANGERECORDS";
    
    /** 交易关键字：应收变更记录 */
    String TRADING_KEY_CLIENTRECEIVEABLECHANGERECORDS = "TRADING_KEY_CLIENTRECEIVEABLECHANGERECORDS";
    
    /** 返回值关键字：支付订单集合 */
    String RESPONSE_KEY_PAYMENTORDERS = "RESPONSE_KEY_PAYMENTORDERS";
    
    /** 返回值关键字：支付订单集合 */
    String RESPONSE_KEY_PAYMENTORDER = "RESPONSE_KEY_PAYMENTORDER";
    
    /** 返回值关键字：支付订单集合 */
    String RESPONSE_KEY_CLA_TRADING_RECORD = "RESPONSE_KEY_CA_TRADING_RECORD";
}
