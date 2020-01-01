///*
// * 描          述:  <描述>
// * 修  改   人:  Administrator
// * 修改时间:  2017年2月10日
// * <修改描述:>
// */
//package com.tx.core.remote;
//
//import com.tx.core.util.ObjectUtils;
//
//import java.io.Serializable;
//
///**
// * 参数验证
// * <功能详细描述>
// *
// * @author Administrator
// * @version [版本号, 2017年2月10日]
// * @see [相关类/方法]
// * @since [产品/模块版本]
// */
//public class RemoteValidate extends RemoteResult implements Serializable {
//    /**
//     * <默认构造函数>
//     */
//    public RemoteValidate() {
//        setResult(true);
//    }
//
//    public static RemoteValidate instance() {
//        return new RemoteValidate();
//    }
//
//    public RemoteValidate notEmpty(Object obj, String errorMessage) {
//        if (!this.isResult()) {
//            return this;
//        }
//        if (ObjectUtils.isEmpty(obj)) {
//            setResult(false);
//            setErrorCode(RemoteResultCode.VALIDATE_PARAM_EMPTY.getCode());
//            setErrorMessage(errorMessage);
//        }
//        return this;
//    }
//
//    public RemoteValidate isTrue(boolean isTrue, String errorMessage) {
//        if (!this.isResult()) {
//            return this;
//        }
//        if (!isTrue) {
//            setResult(false);
//            setErrorCode(RemoteResultCode.VALIDATE_PARAM_INVALID.getCode());
//            setErrorMessage(errorMessage);
//        }
//        return this;
//    }
//
//    public RemoteValidate isEmpty(Object obj, String errorMessage) {
//        if (!this.isResult()) {
//            return this;
//        }
//        if (!ObjectUtils.isEmpty(obj)) {
//            setResult(false);
//            setErrorCode(RemoteResultCode.VALIDATE_PARAM_EMPTY.getCode());
//            setErrorMessage(errorMessage);
//        }
//        return this;
//    }
//
//
//}
