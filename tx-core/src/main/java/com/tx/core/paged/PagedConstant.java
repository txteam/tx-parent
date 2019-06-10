/*
 * 描          述:  <描述>
 * 修  改   人:  PengQingyang
 * 修改时间:  2012-11-5
 * <修改描述:>
 */
package com.tx.core.paged;


 /**
  * <分页常量>
  * <功能详细描述>
  * 
  * @author  PengQingyang
  * @version  [版本号, 2012-11-5]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public interface PagedConstant {
    
    /** 默认每页显示条数 */
    int DEFAULT_PAGE_SIZE = 20;
    
    /** 默认页索引 */
    int DEFAULT_PAGE_INDEX = 0;
    
    /** 默认每次查询页条数  */
    int DEFAULT_QUERY_PAGE_SIZE = 1;
    
    /** 默认页码 */
    int DEFAULT_PAGE_NUMBER = 1;

    /** 默认每页记录数 */
    //int DEFAULT_PAGE_SIZE = 20;

    /** 最大每页记录数 */
    int MAX_PAGE_SIZE = 1000;
    
    /** 默认是否忽略大小写 */
    boolean DEFAULT_IGNORE_CASE = true;
}
