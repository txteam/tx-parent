package com.tx.core.datasource.finder;

import java.util.Map;
import java.util.WeakHashMap;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tx.core.datasource.DataSourceFinder;
import com.tx.core.exceptions.util.AssertUtils;

/**
 * 从配置中获取数据源
 * <从配置中获取数据源>
 * 
 * @author  PengQingyang
 * @version  [版本号, 2012-10-5]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class SimpleDataSourceFinder implements DataSourceFinder {
    
    private static Logger logger = LoggerFactory.getLogger(SimpleDataSourceFinder.class);
    
    private static Map<String, DataSource> dataSourceMap = new WeakHashMap<String, DataSource>();
    
    private String maxActive = "";
    
    private String maxIdle = "";
    
    private String maxWait = "";
    
    private String type;
    
    private String username;
    
    private String password;
    
    private String driverClassName;
    
    private String url;
    
    /** <默认构造函数> */
    public SimpleDataSourceFinder() {
        super();
    }
    
    /** <默认构造函数> */
    public SimpleDataSourceFinder(String driverClassName, String url,
            String username, String password) {
        super();
        this.username = username;
        this.password = password;
        this.driverClassName = driverClassName;
        this.url = url;
    }
    
    /**
     * <根据jndi名获取jndi数据源>
     * @param resourceId
     * @return
     */
    @Override
    public DataSource getDataSource() {
        AssertUtils.notEmpty(this.url, "this.url is empty.");
        if (SimpleDataSourceFinder.dataSourceMap.containsKey(this.url)) {
            return SimpleDataSourceFinder.dataSourceMap.get(this.url);
        }
        
        logger.info("Try to init SimpleDataSource.");
        
        try {
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
            //设置最长的等待时间
            bds.setMaxWait(NumberUtils.toInt(maxWait, -1));
            //设置在缓冲池的最小连接数
            bds.setMinIdle(0);
            
            SimpleDataSourceFinder.dataSourceMap.put(this.url, bds);
        } catch (Exception e) {
            logger.info("Init DataSource by configDataSource. new BasicDataSource happend exception: "
                    + e.toString());
            logger.debug("Init DataSource by configDataSource. new BasicDataSource happend exception: "
                    + e.toString(),
                    e);
        }
        
        return SimpleDataSourceFinder.dataSourceMap.get(this.url);
    }
    
    /**
     * @return 返回 maxActive
     */
    public String getMaxActive() {
        return maxActive;
    }
    
    /**
     * @param 对maxActive进行赋值
     */
    public void setMaxActive(String maxActive) {
        this.maxActive = maxActive;
    }
    
    /**
     * @return 返回 maxIdle
     */
    public String getMaxIdle() {
        return maxIdle;
    }
    
    /**
     * @param 对maxIdle进行赋值
     */
    public void setMaxIdle(String maxIdle) {
        this.maxIdle = maxIdle;
    }
    
    /**
     * @return 返回 maxWait
     */
    public String getMaxWait() {
        return maxWait;
    }
    
    /**
     * @param 对maxWait进行赋值
     */
    public void setMaxWait(String maxWait) {
        this.maxWait = maxWait;
    }
    
    /**
     * @return 返回 type
     */
    public String getType() {
        return type;
    }
    
    /**
     * @param 对type进行赋值
     */
    public void setType(String type) {
        this.type = type;
    }
    
    /**
     * @return 返回 username
     */
    public String getUsername() {
        return username;
    }
    
    /**
     * @param 对username进行赋值
     */
    public void setUsername(String username) {
        this.username = username;
    }
    
    /**
     * @return 返回 password
     */
    public String getPassword() {
        return password;
    }
    
    /**
     * @param 对password进行赋值
     */
    public void setPassword(String password) {
        this.password = password;
    }
    
    /**
     * @return 返回 driverClassName
     */
    public String getDriverClassName() {
        return driverClassName;
    }
    
    /**
     * @param 对driverClassName进行赋值
     */
    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
    }
    
    /**
     * @return 返回 url
     */
    public String getUrl() {
        return url;
    }
    
    /**
     * @param 对url进行赋值
     */
    public void setUrl(String url) {
        this.url = url;
    }
}
