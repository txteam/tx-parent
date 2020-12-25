/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2015年10月21日
 * <修改描述:>
 */
package com.tx.component.statistical.context;

import com.tx.component.statistical.mapping.ReportStatement;
import org.springframework.core.io.Resource;

/**
  * 统计源注册机<br/>
  * <功能详细描述>
  * 
  * @author  Administrator
  * @version  [版本号, 2015年10月21日]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public interface StatisticalResourceRegister {


    /**
     * 资源解析
     * @param resource
     * @return
     */
    ReportStatement parseResource(Resource resource);

    /**
     * 资源注册
     * @param resource
     */
    ReportStatement registerResource(Resource resource);

    /**
     * 注册资源
     * @param resources
     */
    void registerResource(Resource[] resources);

    /**
     * 重新加载报表
     * @param reportStatement
     */
    void reloadReport(ReportStatement reportStatement);

    /**
     * 获取所有的报表
     * @return
     */


    boolean supportRegister(Resource tempResource);
}
