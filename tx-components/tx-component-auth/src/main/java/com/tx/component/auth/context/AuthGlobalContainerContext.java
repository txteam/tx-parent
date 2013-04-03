/*
 * 描          述:  <描述>
 * 修  改   人:  PengQingyang
 * 修改时间:  2013-4-4
 * <修改描述:>
 */
package com.tx.component.auth.context;

import java.util.Map;
import java.util.WeakHashMap;

import com.tx.component.auth.model.AuthItemRef;

/**
 * 权限全局容器（web容器）上下文
 *     用以保存存在的操作员登录后写入session的map的引用<br/>
 *     以便临时权限的授予<br/>
 *     以达到，一旦授权，马上生效的效果<br/>
 *     如果后续，还需要，分布集群话，这里逻辑就需要重构下<br/>
 *     
 * <功能详细描述>
 * 
 * @author  PengQingyang
 * @version  [版本号, 2013-4-4]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class AuthGlobalContainerContext {
    
    /**
     * 利用弱引用map行程每个操作人员的权限引用项
     * 如果该操作员已经登出
     * 对应map从session中回收后，在该map中会随着GC被回收掉
     */
    private Map<String, Map<String, AuthItemRef>> operatorSessionAuthMapMapping = new WeakHashMap<String, Map<String, AuthItemRef>>();
    
    /** <默认构造函数> */
    public AuthGlobalContainerContext() {
        
    }
    
    
    
}
