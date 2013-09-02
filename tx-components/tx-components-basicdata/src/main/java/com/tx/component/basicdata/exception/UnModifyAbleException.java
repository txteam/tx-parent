/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-9-2
 * <修改描述:>
 */
package com.tx.component.basicdata.exception;

import com.tx.core.exceptions.SILException;


 /**
  * 不可编辑异常<br/>
  *     当某基础数据不能被编辑时，如果对其编辑，则抛出该异常<br/>
  * <功能详细描述>
  * 
  * @author  brady
  * @version  [版本号, 2013-9-2]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public class UnModifyAbleException extends SILException{

    /** 注释内容 */
    private static final long serialVersionUID = -5261028137401362901L;
    /**
     * @return
     */
    @Override
    protected String doGetErrorCode() {
        return "UN_MODIFY_ABLE_ERROR";
    }

    /**
     * @return
     */
    @Override
    protected String doGetErrorMessage() {
        return "不可编辑异常";
    }

    private Class<?> type;

    /** <默认构造函数> */
    public UnModifyAbleException(Class<?> type) {
        super("BasicData type: {}.is unModifyAble.",new Object[]{type});
        this.type = type;
    }

    /**
     * @return 返回 type
     */
    public Class<?> getType() {
        return type;
    }

    /**
     * @param 对type进行赋值
     */
    public void setType(Class<?> type) {
        this.type = type;
    }
}
