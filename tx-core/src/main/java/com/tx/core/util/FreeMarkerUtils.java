/*
 * 描          述:  <描述>
 * 修  改   人:  PengQingyang
 * 修改时间:  2012-12-9
 * <修改描述:>
 */
package com.tx.core.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tx.core.exceptions.resource.ResourceLoadException;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * freeMarker工具类
 * <功能详细描述>
 * 
 * @author  PengQingyang
 * @version  [版本号, 2012-12-9]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class FreeMarkerUtils {
    
    /** 日志 */
    private static final Logger logger = LoggerFactory.getLogger(FreeMarkerUtils.class);
    
    /** 模版缓存 */
    private static Map<String, Template> templateCache = new HashMap<String, Template>();
    
    /**
      * 获取模板
      * <功能详细描述>
      * @param filePath 模版所在类路径
      * @return [参数说明]
      * 
      * @return Template [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public static Template getTemplateByTemplateClassPath(Class<?> loadClass,
            String filePath) {
        if (templateCache.containsKey(filePath)) {
            return templateCache.get(filePath);
        }
        
        try {
            //通过Freemaker的Configuration读取相应的ftl
            Configuration cfg = new Configuration();
            
            //设定去哪里读取相应的ftl模板文件
            cfg.setClassForTemplateLoading(loadClass, "/");
            
            //在模板文件目录中找到名称为name的文件
            Template temp = cfg.getTemplate(filePath, "UTF-8");
            
            templateCache.put(filePath, temp);
            return temp;
        } catch (IOException e) {
            logger.error(e.toString(), e);
            throw new ResourceLoadException("", e);
        }
    }
    
    /**
      * 根据数据以及文件生成模版
      * <功能详细描述>
      * @param name
      * @param root
      * @param outFile [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public static void fprint(Class<?> loadClass, String filePath,
            Map<String, Object> root, String outFilePath) {
        FileWriter out = null;
        try {
            //通过一个文件输出流，就可以写到相应的文件中
            File newFile = new File(outFilePath);
            if (!newFile.exists()) {
                FileUtils.forceMkdir(newFile.getParentFile());
                newFile.createNewFile();
            }
            out = new FileWriter(newFile);
            
            Template temp = getTemplateByTemplateClassPath(loadClass, filePath);
            temp.process(root, out);
        } catch (IOException e) {
            logger.error(e.toString(), e);
            throw new ResourceLoadException("", e);
        } catch (TemplateException e) {
            logger.error(e.toString(), e);
            throw new ResourceLoadException("", e);
        } finally {
            IOUtils.closeQuietly(out);
        }
    }
    
}
