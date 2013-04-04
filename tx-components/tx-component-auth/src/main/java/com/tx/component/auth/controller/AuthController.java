/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-3-21
 * <修改描述:>
 */
package com.tx.component.auth.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tx.component.auth.context.AuthSessionContext;
import com.tx.component.auth.model.AuthItem;


 /**
  * 权限容器表现层逻辑
  * <功能详细描述>
  * 
  * @author  brady
  * @version  [版本号, 2013-3-21]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
@Controller("authController")
@RequestMapping("/auth")
public class AuthController {
    
    /**
      * 查询得到当前人员的权限项
      *<功能详细描述>
      * @return [参数说明]
      * 
      * @return List<AuthItem> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public List<AuthItem> queryAllAuthItemByCurrentOperator(){
        //获取当前人员所拥有的权限项引用
        //AuthContext.getContext().getAllAuthRefByOperatorId(operatorId);
        
        //获取当前系统所有的权限项
        //AuthSessionContext.getContext().getAuthRefMultiValueMapFromSession();
        
        //获取当前人员所拥有的权限项
        
        //生成权限项树
        return null;
    }
    
    public boolean configAuth(){
        
        return false;
    }
}
