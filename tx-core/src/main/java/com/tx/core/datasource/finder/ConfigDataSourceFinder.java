package com.tx.core.datasource.finder;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.lang.math.NumberUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import com.tx.core.datasource.DataSourceFinder;

/**
 * 从配置中获取数据源
 * <从配置中获取数据源>
 * 
 * @author  PengQingyang
 * @version  [版本号, 2012-10-5]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class ConfigDataSourceFinder implements DataSourceFinder {
    
    private static Logger logger = LoggerFactory.getLogger(ConfigDataSourceFinder.class);
    
    private final static String COMP_ENV = "java:comp/env/";
    
    private static boolean isReaded = false;
    
    private Map<String, DataSource> dataSourceMap = new HashMap<String, DataSource>();
    
    private String dbContextPath = "classpath:dbcontext/context.xml";
    
    private ResourceLoader defaultResourceLoader = new DefaultResourceLoader(
            ConfigDataSourceFinder.class.getClassLoader());
    
    /**
     * <根据jndi名获取jndi数据源>
     * @param jndiName
     * @return
     */
    @Override
    @SuppressWarnings("unchecked")
    public DataSource getDataSource(String jndiName) {
        
        logger.info("Try to init DataSource by classpath:/resources/context/dbContext.xml jndiName:"
                + jndiName);
        // 这里不做同步控制
        DataSource ds1 = (DataSource) this.dataSourceMap.get(jndiName);
        
        if (ds1 != null) {
            return ds1;
        }
        
        String jndiNameAlias = jndiName;
        if (jndiName.startsWith(COMP_ENV)) {
            jndiNameAlias = jndiName.substring(COMP_ENV.length());
        }
        
        ds1 = (DataSource) this.dataSourceMap.get(jndiNameAlias);
        if (ds1 != null) {
            logger.info("Init DataSource by configDataSource success.");
            return ds1;
        }
        
        if (isReaded && ds1 == null) {
            logger.info("Init DataSource by configDataSource fail. datasource not exist. ");
            return null;
        }
        
        Resource dbcontextResource = defaultResourceLoader.getResource(dbContextPath);
        
        if (!dbcontextResource.exists()) {
            logger.info("Init DataSource by configDataSource fail. dbcontext.xml not exists");
            return null;
        }
        
        isReaded = true;
        SAXReader reader = new SAXReader();
        InputStream io = null;
        
        List<Element> elList = new ArrayList<Element>();
        try {
            io = dbcontextResource.getInputStream();
            Document doc = reader.read(io);
            Element rootEl = doc.getRootElement();
            elList = (List<Element>)rootEl.elements("Resource");
            
            if (elList == null) {
                logger.info("Init DataSource by configDataSource. In dbContext Resource size is zero(empty).");
            }
            

        } catch (Exception e) {
            logger.info("Init DataSource by configDataSource. read dbconfig context exception: "
                    + e.toString());
            logger.debug("Init DataSource by configDataSource. read dbconfig context exception: "
                    + e.toString(),
                    e);
        } finally {
            if (io != null) {
                try {
                    io.close();
                } catch (IOException e) {
                }
            }
        }
        
        for (Element elTemp : elList) {
            try {
                String name = elTemp.attributeValue("name");
                @SuppressWarnings("unused")
                String auth = elTemp.attributeValue("auth");
                @SuppressWarnings("unused")
                String type = elTemp.attributeValue("type");
                
                String maxActive = elTemp.attributeValue("maxActive");
                String maxIdle = elTemp.attributeValue("maxIdle");
                String maxWait = elTemp.attributeValue("maxWait");
                
                String username = elTemp.attributeValue("username");
                String password = elTemp.attributeValue("password");
                
                String driverClassName = elTemp.attributeValue("driverClassName");
                String url = elTemp.attributeValue("url");
                
                BasicDataSource bds = null;
                
                bds = new BasicDataSource();
                //设置驱动程序
                bds.setDriverClassName(driverClassName);
                //设置连接用户名
                bds.setUsername(username);
                //设置连接密码
                bds.setPassword(password);
                //设置连接地址
                bds.setUrl(url);
                //设置初始化连接总数
                bds.setInitialSize(NumberUtils.toInt(maxIdle, 10));
                //设置同时应用的连接总数
                bds.setMaxActive(NumberUtils.toInt(maxActive, -1));
                //设置在缓冲池的最大连接数
                bds.setMaxIdle(NumberUtils.toInt(maxIdle, -1));
                //设置在缓冲池的最小连接数
                bds.setMinIdle(0);
                //设置最长的等待时间
                bds.setMaxWait(NumberUtils.toInt(maxWait, -1));
                
                this.dataSourceMap.put(name, bds);
            } catch (Exception e) {
                logger.info("Init DataSource by configDataSource. new BasicDataSource happend exception: "
                        + e.toString());
                logger.debug("Init DataSource by configDataSource. new BasicDataSource happend exception: "
                        + e.toString(),
                        e);
            }
        }
        
        ds1 = (DataSource) this.dataSourceMap.get(jndiNameAlias);
        
        if (ds1 != null) {
            logger.info("Init DataSource by configDataSource success.");
        } else {
            logger.info("Init DataSource by configDataSource fail. datasource not exist. ");
        }
        
        return ds1;
    }
}
