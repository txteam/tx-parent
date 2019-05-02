/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2019年5月1日
 * <修改描述:>
 */
package com.tx.core.starter.mybatis;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.ibatis.io.VFS;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

/**
 * VFS实现<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2019年5月1日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class SpringBootVFS extends VFS{
    
    /** 资源加载器 */
    private final ResourcePatternResolver resourceResolver;
    
    /** 构造函数 */
    public SpringBootVFS() {
        this.resourceResolver = new PathMatchingResourcePatternResolver(
                getClass().getClassLoader());
    }
    
    /** 是否有效 */
    @Override
    public boolean isValid() {
        return true;
    }
    
    @Override
    protected List<String> list(URL url, String path) throws IOException {
        String urlString = url.toString();
        String baseUrlString = urlString.endsWith("/") ? urlString
                : urlString.concat("/");
        Resource[] resources = resourceResolver
                .getResources(baseUrlString + "**/*.class");
        return Stream.of(resources)
                .map(resource -> preserveSubpackageName(baseUrlString,
                        resource,
                        path))
                .collect(Collectors.toList());
    }
    
    private static String preserveSubpackageName(final String baseUrlString,
            final Resource resource, final String rootPath) {
        try {
            return rootPath + (rootPath.endsWith("/") ? "" : "/") + resource
                    .getURL().toString().substring(baseUrlString.length());
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
