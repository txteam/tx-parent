/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2017年2月15日
 * <修改描述:>
 */
package com.tx.component.file.util;

import java.util.UUID;

import org.springframework.util.StringUtils;

import com.tx.core.exceptions.util.AssertUtils;

/**
 * 客户信息文件辅助类<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2017年2月15日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public abstract class FileContextUtils {
    
    /**
     * 生成UUID的文件名<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static String generateUUIDFilename() {
        String filename = UUID.randomUUID().toString();
        return filename;
    }
    
    /**
     * 生成UUID的文件名<br/>
     * <功能详细描述>
     * @param filenameExtension
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static String generateUUIDFilename(String filenameExtension) {
        String filename = generateUUIDFilename();
        //如果入参传入了后缀，则对后缀名进行核对
        if (!StringUtils.isEmpty(filenameExtension)) {
            //如果指定后缀名不为空，则进行文件名核对，如果不是这个后缀，则直接增加文件后缀
            //如果文件名不以指定后缀名
            filename = filename + "." + filenameExtension;
        }
        return filename;
    }
    
    /**
     * 处理文件保存路径,去除相对路径前面的"/",相对路径应以"."或"字符"作为起始字符<br/>
     * <功能详细描述>
     * @param relativePath
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static String handleRelativePath(String relativePath) {
        AssertUtils.notEmpty(relativePath, "relativePath is empty.");
        
        //整理存储的relativePath相对路径
        relativePath = handleRelativePath(relativePath, null);
        return relativePath;
    }
    
    /**
     * 处理文件保存路径,去除相对路径前面的"/",相对路径应以"."或"字符"作为起始字符<br/>
     * <功能详细描述>
     * @param relativePath
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static String handleRelativePath(String relativePath,
            String filenameExtension) {
        AssertUtils.notEmpty(relativePath, "relativePath is empty.");
        
        //整理存储的relativePath相对路径
        while (relativePath.startsWith("/")) {
            //去除相对路径前面的"/"
            relativePath = relativePath.substring(1, relativePath.length());
        }
        //如果入参传入了后缀，则对后缀名进行核对
        if (!StringUtils.isEmpty(filenameExtension)) {
            //如果指定后缀名不为空，则进行文件名核对，如果不是这个后缀，则直接增加文件后缀
            if (!org.apache.commons.lang3.StringUtils
                    .endsWithIgnoreCase(relativePath, filenameExtension)) {
                //如果文件名不以指定后缀名
                relativePath = relativePath + "." + filenameExtension;
            }
        }
        return relativePath;
    }
    
    /**
     * 处理相对路径<br/>
     * <功能详细描述>
     * @param relativeFolderPath
     * @param filename
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static String handleRelativeFolderPath(String relativeFolderPath,
            String filename) {
        String relativePath = handleRelativeFolderPath(relativeFolderPath,
                filename,
                null);
        return relativePath;
    }
    
    /**
     * 生成文件保存路径<br/>
     * <功能详细描述>
     * @param relativePath
     * @param filenameExtension
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static String handleRelativeFolderPath(String relativeFolderPath,
            String filename, String filenameExtension) {
        AssertUtils.notEmpty(relativeFolderPath,
                "relativeFolderPath is empty.");
        AssertUtils.notEmpty(filename, "filename is empty.");
        
        //调用cleanPath去除/../../的逻辑，并
        relativeFolderPath = StringUtils.cleanPath(relativeFolderPath);
        while (relativeFolderPath.startsWith("/")) {
            //去除相对路径前面的"/"
            relativeFolderPath = relativeFolderPath.substring(1);
        }
        while (filename.startsWith("/")) {
            //如果文件名以反斜杠开始，则去掉反斜杠，避免出现最后路径有双反斜杠的情况
            filename = filename.substring(1);
        }
        //去掉多余的反斜杠后再判断不能为空
        AssertUtils.notEmpty(relativeFolderPath,
                "relativeFolderPath is empty.");
        AssertUtils.notEmpty(filename, "filename is empty.");
        
        //文件目录添加最后的反斜杠（如果没有的话）
        if (!relativeFolderPath.endsWith("/")) {
            relativeFolderPath = relativeFolderPath + "/";
        }
        //如果入参传入了后缀，则对后缀名进行核对
        if (!StringUtils.isEmpty(filenameExtension)) {
            //如果指定后缀名不为空，则进行文件名核对，如果不是这个后缀，则直接增加文件后缀
            if (!org.apache.commons.lang3.StringUtils
                    .endsWithIgnoreCase(filename, filenameExtension)) {
                //如果文件名不以指定后缀名
                filename = filename + "." + filenameExtension;
            }
        }
        
        //整理存储的relativePath相对路径
        String relativePath = StringUtils.applyRelativePath(relativeFolderPath,
                filename);
        return relativePath;
    }
}
