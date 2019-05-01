///*
// * 描          述:  <描述>
// * 修  改   人:  Administrator
// * 修改时间:  2019年3月21日
// * <修改描述:>
// */
//package com.tx.core.redis.support;
//
//import java.net.URI;
//import java.net.URISyntaxException;
//import java.net.UnknownHostException;
//import java.util.ArrayList;
//import java.util.List;
//
//import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
//import org.springframework.boot.autoconfigure.data.redis.RedisProperties.Cluster;
//import org.springframework.boot.autoconfigure.data.redis.RedisProperties.Sentinel;
//import org.springframework.data.redis.connection.RedisClusterConfiguration;
//import org.springframework.data.redis.connection.RedisNode;
//import org.springframework.data.redis.connection.RedisSentinelConfiguration;
//import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
//import org.springframework.util.Assert;
//import org.springframework.util.StringUtils;
//
//import redis.clients.jedis.JedisPoolConfig;
//
///**
// * Redis支撑辅助类<br/>
// * <功能详细描述>
// * 
// * @author  Administrator
// * @version  [版本号, 2019年3月21日]
// * @see  [相关类/方法]
// * @since  [产品/模块版本]
// */
//public class RedisSupportHelper {
//    
//    /**
//     * 构建RedisConnectionFactory<br/>
//     * <功能详细描述>
//     * @param properties
//     * @param sentinelConfiguration
//     * @param clusterConfiguration
//     * @return
//     * @throws UnknownHostException [参数说明]
//     * 
//     * @return JedisConnectionFactory [返回类型说明]
//     * @exception throws [异常类型] [异常说明]
//     * @see [类、类#方法、类#成员]
//     */
//    public static JedisConnectionFactory buildRedisConnectionFactory(
//            RedisProperties properties) throws UnknownHostException {
//        JedisConnectionFactory factory = createJedisConnectionFactory(
//                properties, null, null);
//        applyProperties(factory, properties);
//        return factory;
//    }
//    
//    /**
//     * 构建RedisConnectionFactory<br/>
//     * <功能详细描述>
//     * @param properties
//     * @param sentinelConfiguration
//     * @param clusterConfiguration
//     * @return
//     * @throws UnknownHostException [参数说明]
//     * 
//     * @return JedisConnectionFactory [返回类型说明]
//     * @exception throws [异常类型] [异常说明]
//     * @see [类、类#方法、类#成员]
//     */
//    public static JedisConnectionFactory buildRedisConnectionFactory(
//            RedisProperties properties,
//            RedisSentinelConfiguration sentinelConfiguration)
//            throws UnknownHostException {
//        JedisConnectionFactory factory = createJedisConnectionFactory(
//                properties, sentinelConfiguration, null);
//        applyProperties(factory, properties);
//        return factory;
//    }
//    
//    /**
//     * 构建RedisConnectionFactory<br/>
//     * <功能详细描述>
//     * @param properties
//     * @param sentinelConfiguration
//     * @param clusterConfiguration
//     * @return
//     * @throws UnknownHostException [参数说明]
//     * 
//     * @return JedisConnectionFactory [返回类型说明]
//     * @exception throws [异常类型] [异常说明]
//     * @see [类、类#方法、类#成员]
//     */
//    public static JedisConnectionFactory buildRedisConnectionFactory(
//            RedisProperties properties,
//            RedisClusterConfiguration clusterConfiguration)
//            throws UnknownHostException {
//        JedisConnectionFactory factory = createJedisConnectionFactory(
//                properties, null, clusterConfiguration);
//        applyProperties(factory, properties);
//        return factory;
//    }
//    
//    /**
//     * 构建jedis连接池<br/>
//     * <功能详细描述>
//     * @param properties
//     * @return [参数说明]
//     * 
//     * @return JedisConnectionFactory [返回类型说明]
//     * @exception throws [异常类型] [异常说明]
//     * @see [类、类#方法、类#成员]
//     */
//    private static JedisConnectionFactory createJedisConnectionFactory(
//            RedisProperties properties,
//            RedisSentinelConfiguration sentinelConfiguration,
//            RedisClusterConfiguration clusterConfiguration) {
//        JedisPoolConfig poolConfig = (properties.getPool() != null
//                ? jedisPoolConfig(properties) : new JedisPoolConfig());
//        
//        RedisSentinelConfiguration sentinelConfig = getSentinelConfig(
//                properties, sentinelConfiguration);
//        if (sentinelConfig != null) {
//            return new JedisConnectionFactory(sentinelConfig, poolConfig);
//        }
//        
//        RedisClusterConfiguration clusterConfig = getClusterConfiguration(
//                properties, clusterConfiguration);
//        if (clusterConfig != null) {
//            return new JedisConnectionFactory(clusterConfig, poolConfig);
//        }
//        return new JedisConnectionFactory(poolConfig);
//    }
//    
//    /**
//     * 通过配置设置超时时间等<br/>
//     * <功能详细描述>
//     * @param factory
//     * @param properties
//     * @return [参数说明]
//     * 
//     * @return JedisConnectionFactory [返回类型说明]
//     * @exception throws [异常类型] [异常说明]
//     * @see [类、类#方法、类#成员]
//     */
//    private static JedisConnectionFactory applyProperties(
//            JedisConnectionFactory factory, RedisProperties properties) {
//        configureConnection(factory, properties);
//        if (properties.isSsl()) {
//            factory.setUseSsl(true);
//        }
//        factory.setDatabase(properties.getDatabase());
//        if (properties.getTimeout() > 0) {
//            factory.setTimeout(properties.getTimeout());
//        }
//        return factory;
//    }
//    
//    /**
//     * 通过配置设置链接<br/>
//     * <功能详细描述>
//     * @param factory
//     * @param properties [参数说明]
//     * 
//     * @return void [返回类型说明]
//     * @exception throws [异常类型] [异常说明]
//     * @see [类、类#方法、类#成员]
//     */
//    private static void configureConnection(JedisConnectionFactory factory,
//            RedisProperties properties) {
//        if (StringUtils.hasText(properties.getUrl())) {
//            configureConnectionFromUrl(factory, properties);
//        } else {
//            factory.setHostName(properties.getHost());
//            factory.setPort(properties.getPort());
//            if (properties.getPassword() != null) {
//                factory.setPassword(properties.getPassword());
//            }
//        }
//    }
//    
//    /**
//     * 通过Url配置配置链接<br/>
//     * <功能详细描述>
//     * @param factory
//     * @param properties [参数说明]
//     * 
//     * @return void [返回类型说明]
//     * @exception throws [异常类型] [异常说明]
//     * @see [类、类#方法、类#成员]
//     */
//    private static void configureConnectionFromUrl(
//            JedisConnectionFactory factory, RedisProperties properties) {
//        String url = properties.getUrl();
//        if (url.startsWith("rediss://")) {
//            factory.setUseSsl(true);
//        }
//        try {
//            URI uri = new URI(url);
//            factory.setHostName(uri.getHost());
//            factory.setPort(uri.getPort());
//            if (uri.getUserInfo() != null) {
//                String password = uri.getUserInfo();
//                int index = password.indexOf(":");
//                if (index >= 0) {
//                    password = password.substring(index + 1);
//                }
//                factory.setPassword(password);
//            }
//        } catch (URISyntaxException ex) {
//            throw new IllegalArgumentException(
//                    "Malformed 'spring.redis.url' " + url, ex);
//        }
//    }
//    
//    /**
//     * 获取Redis集群配置<br/>
//     * <功能详细描述>
//     * @param properties
//     * @param sentinelConfiguration
//     * @return [参数说明]
//     * 
//     * @return RedisSentinelConfiguration [返回类型说明]
//     * @exception throws [异常类型] [异常说明]
//     * @see [类、类#方法、类#成员]
//     */
//    private static final RedisSentinelConfiguration getSentinelConfig(
//            RedisProperties properties,
//            RedisSentinelConfiguration sentinelConfiguration) {
//        if (sentinelConfiguration != null) {
//            return sentinelConfiguration;
//        }
//        
//        Sentinel sentinelProperties = properties.getSentinel();
//        if (sentinelProperties != null) {
//            RedisSentinelConfiguration config = new RedisSentinelConfiguration();
//            config.master(sentinelProperties.getMaster());
//            config.setSentinels(createSentinels(sentinelProperties));
//            return config;
//        }
//        return null;
//    }
//    
//    /**
//     * 获取redisCluster配置<br/>
//     * <功能详细描述>
//     * @param properties
//     * @param clusterConfiguration
//     * @return [参数说明]
//     * 
//     * @return RedisClusterConfiguration [返回类型说明]
//     * @exception throws [异常类型] [异常说明]
//     * @see [类、类#方法、类#成员]
//     */
//    private static final RedisClusterConfiguration getClusterConfiguration(
//            RedisProperties properties,
//            RedisClusterConfiguration clusterConfiguration) {
//        if (clusterConfiguration != null) {
//            return clusterConfiguration;
//        }
//        if (properties.getCluster() == null) {
//            return null;
//        }
//        Cluster clusterProperties = properties.getCluster();
//        RedisClusterConfiguration config = new RedisClusterConfiguration(
//                clusterProperties.getNodes());
//        
//        if (clusterProperties.getMaxRedirects() != null) {
//            config.setMaxRedirects(clusterProperties.getMaxRedirects());
//        }
//        return config;
//    }
//    
//    /**
//     * 创建Redis的主从配置<br/>
//     * <功能详细描述>
//     * @param sentinel
//     * @return [参数说明]
//     * 
//     * @return List<RedisNode> [返回类型说明]
//     * @exception throws [异常类型] [异常说明]
//     * @see [类、类#方法、类#成员]
//     */
//    private static List<RedisNode> createSentinels(Sentinel sentinel) {
//        List<RedisNode> nodes = new ArrayList<RedisNode>();
//        for (String node : StringUtils
//                .commaDelimitedListToStringArray(sentinel.getNodes())) {
//            try {
//                String[] parts = StringUtils.split(node, ":");
//                Assert.state(parts.length == 2,
//                        "Must be defined as 'host:port'");
//                nodes.add(new RedisNode(parts[0], Integer.valueOf(parts[1])));
//            } catch (RuntimeException ex) {
//                throw new IllegalStateException(
//                        "Invalid redis sentinel " + "property '" + node + "'",
//                        ex);
//            }
//        }
//        return nodes;
//    }
//    
//    /**
//     * 获取Jedis连接池配置
//     * <功能详细描述>
//     * @param properties
//     * @return [参数说明]
//     * 
//     * @return JedisPoolConfig [返回类型说明]
//     * @exception throws [异常类型] [异常说明]
//     * @see [类、类#方法、类#成员]
//     */
//    private static JedisPoolConfig jedisPoolConfig(RedisProperties properties) {
//        JedisPoolConfig config = new JedisPoolConfig();
//        RedisProperties.Pool props = properties.getPool();
//        config.setMaxTotal(props.getMaxActive());
//        config.setMaxIdle(props.getMaxIdle());
//        config.setMinIdle(props.getMinIdle());
//        config.setMaxWaitMillis(props.getMaxWait());
//        return config;
//    }
//}
