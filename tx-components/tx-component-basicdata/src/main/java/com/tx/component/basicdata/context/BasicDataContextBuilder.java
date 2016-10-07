/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2016年10月4日
 * <修改描述:>
 */
package com.tx.component.basicdata.context;

import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.tx.component.basicdata.model.BasicData;
import com.tx.core.util.ClassScanUtils;

/**
 * 基础数据容器构建器<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2016年10月4日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class BasicDataContextBuilder extends BasicDataContextConfigurator {
    
    /** 类型2业务逻辑层的映射关联 */
    protected Map<Class<?>, BasicDataService<?>> type2serviceMap = new HashMap<>();
    
    /**
     * @throws Exception
     */
    @Override
    protected final void doBuild() throws Exception {
        //加载基础数据类<br/>
        
    }
    

}
