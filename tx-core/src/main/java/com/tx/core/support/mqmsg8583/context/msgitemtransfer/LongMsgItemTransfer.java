///*
// * 描          述:  <描述>
// * 修  改   人:  Administrator
// * 修改时间:  2016年9月6日
// * <修改描述:>
// */
//package com.tx.core.support.mqmsg8583.context.msgitemtransfer;
//
//import org.apache.commons.lang3.StringUtils;
//import org.apache.commons.lang3.math.NumberUtils;
//
//import com.tx.core.exceptions.util.AssertUtils;
//import com.tx.core.support.mqmsg8583.context.AbstractMsgItemTransfer;
//
///**
// * Integer类型的
// * <功能详细描述>
// * 
// * @author  Administrator
// * @version  [版本号, 2016年9月6日]
// * @see  [相关类/方法]
// * @since  [产品/模块版本]
// */
//public class LongMsgItemTransfer extends AbstractMsgItemTransfer<Long> {
//    
//    /**
//     * @param parameter
//     * @param length
//     * @return
//     */
//    @Override
//    protected String doMarshal(Long parameter, int length) {
//        AssertUtils.isTrue(length > 0, "length should > 0.");
//        
//        String parameterSource = parameter == null ? "0" : parameter.toString();
//        String parameterTarget = StringUtils.leftPad(parameterSource,
//                length,
//                '0');
//        return parameterTarget;
//    }
//    
//    /**
//     * @param parameter
//     * @return
//     */
//    @Override
//    protected Long doUnmarshal(String parameter) {
//        long intValue = parameter == null ? 0 : NumberUtils.toLong(parameter);
//        return intValue;
//    }
//    
//}
