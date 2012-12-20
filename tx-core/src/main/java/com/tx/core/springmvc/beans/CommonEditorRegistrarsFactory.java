/*
 * 描          述:  <描述>
 * 修  改   人:  PengQingyang
 * 修改时间:  2012-12-16
 * <修改描述:>
 */
package com.tx.core.springmvc.beans;

import java.beans.PropertyEditor;
import java.util.HashMap;
import java.util.Map;


 /**
  * 常用的PropertyEditor
  * <功能详细描述>
  * 
  * @author  PengQingyang
  * @version  [版本号, 2012-12-16]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public class CommonEditorRegistrarsFactory extends
        PropertyEditorRegistrarsFactory {
    
    /**
     * @return
     */
    @Override
    public Map<Class<?>, PropertyEditor> getType2PropertyEditorMapping() {
        Map<Class<?>, PropertyEditor> resMap = new HashMap<Class<?>, PropertyEditor>();
        
        //添加常用的属性编辑器
        
        return resMap;
    }
    
}
