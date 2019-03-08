//package com.tx.core.datasource.finder;
//
//import javax.sql.DataSource;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import com.tx.core.datasource.DataSourceFinder;
//
///**
// * 读取现成的数据源，并利用base
// * <从配置中获取数据源>
// * 
// * @author  PengQingyang
// * @version  [版本号, 2012-10-5]
// * @see  [相关类/方法]
// * @since  [产品/模块版本]
// */
//public class DelegatingDataSourceImplFinder implements DataSourceFinder {
//    
//    private static Logger logger = LoggerFactory.getLogger(DelegatingDataSourceImplFinder.class);
//    
//    private DataSource srcDatasource;
//    
//    /**
//     * <根据jndi名获取jndi数据源>
//     * @param jndiName
//     * @return
//     */
//    @Override
//    public DataSource getDataSource() {
//        
//        logger.info("Try to init DataSource by DelegatingDataSourceImplFinder");
//        
//        return srcDatasource;
//    }
//    
//    /**
//     * @return 返回 srcDatasource
//     */
//    public DataSource getSrcDatasource() {
//        return srcDatasource;
//    }
//    
//    /**
//     * @param 对srcDatasource进行赋值
//     */
//    public void setSrcDatasource(DataSource srcDatasource) {
//        this.srcDatasource = srcDatasource;
//    }
//}
