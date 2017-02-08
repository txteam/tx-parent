/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2017年1月15日
 * <修改描述:>
 */
package com.tx.component.file.driver;

import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tx.component.file.driver.impl.OSSFileDefinitionResourceDriver;
import com.tx.component.file.driver.impl.SystemFileDefinitionResourceDriver;
import com.tx.core.exceptions.SILException;
import com.tx.core.exceptions.util.AssertUtils;

/**
 * 资源驱动注册表<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2017年1月15日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class FileDefinitionResourceDriverRegistry {
    
    /** 驱动正则表达式 */
    private static Pattern driverPattern = Pattern.compile("(.+?)\\:\\{(.+?)\\}");
    
    /**
      * 解析驱动定义<br/>
      * <功能详细描述>
      * @param driver
      * @return [参数说明]
      * 
      * @return FileDefinitionResourceDriver [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public static FileDefinitionResourceDriver parseDriver(String driver) {
        AssertUtils.notEmpty(driver, "driver is empty.");
        
        Matcher matcher = driverPattern.matcher(driver);
        AssertUtils.isTrue(matcher.matches(), "driver is invalid.");
        String driverName = matcher.group(1).toUpperCase();
        String jsonString = "{" + matcher.group(2) + "}";
        JSONObject jsonObject = JSON.parseObject(jsonString);
        
        FileDefinitionResourceDriver resourceDriver = null;
        switch (driverName) {
            case "SYSTEMFILE":
                resourceDriver = new SystemFileDefinitionResourceDriver();
                break;
            case "OSSFILE":
                resourceDriver = new OSSFileDefinitionResourceDriver();
                break;
            default:
                break;
        }
        AssertUtils.notNull(resourceDriver,
                "resourceDriver is null.driverName:{}",
                driverName);
        
        //为驱动设置值
        Set<String> keySet = jsonObject.keySet();
        BeanWrapper bw = new BeanWrapperImpl(resourceDriver);
        for (String keyTemp : keySet) {
            if (!bw.isWritableProperty(keyTemp)) {
                continue;
            }
            bw.setPropertyValue(keyTemp, jsonObject.getString(keyTemp));
        }
        
        try {
            //驱动初始化
            resourceDriver.afterPropertiesSet();
        } catch (Exception e) {
            throw new SILException(e.getMessage(), e);
        }
        
        return resourceDriver;
    }
    
    //public static void main(String[] args) {
    //    String driver = "systemfile:{path:\"d:/develop/txworkspace/wtp/thumbnail\"}";
    //    
    //    Matcher matcher = driverPattern.matcher(driver);
    //    
    //    System.out.println(matcher.matches());
    //    AssertUtils.isTrue(matcher.matches(), "driver is invalid.");
    //    
    //    System.out.println(driver.substring(0, driver.indexOf(":")));
    //    System.out.println(driver.substring(driver.indexOf(":") + 1,
    //            driver.length()));
    //    
    //    String jsonString = "{" + matcher.group(2) + "}";
    //    System.out.println(matcher.group(1));
    //    System.out.println(jsonString);
    //    
    //    JSONObject jsonObject = JSON.parseObject(jsonString);
    //    System.out.println(jsonObject.get("path"));
    //    
    //    testSystemFileDriver(driver);//测试系统文件驱动
    //    
    //    driver = "ossfile:{endpoint:\"http://oss-cn-shenzhen.aliyuncs.com\",accessKeyId:\"LTAIqiYuWr9WAkWa\",secretAccessKey:\"e4tyMfOU9IGiiDwWlc6gidT8siQek1\",bucketName:\"clientinfo\"}";
    //    testOSSFileDriver(driver);
    //}
    ///** 
    // * 文件资源存储<br/>
    // * <功能详细描述>
    // * @param driver [参数说明]
    // * 
    // * @return void [返回类型说明]
    // * @exception throws [异常类型] [异常说明]
    // * @see [类、类#方法、类#成员]
    // */
    //private static void testOSSFileDriver(String driver) {
    //    FileDefinitionResourceDriver resourceDriver = parseDriver(driver);
    //    System.out.println(resourceDriver);
    //    FileDefinition fileDefinition = new FileDefinition();
    //    fileDefinition.setCreateDate(new Date());
    //    fileDefinition.setId(UUIDUtils.generateUUID());
    //    fileDefinition.setRelativePath("testfolder/testfile.xml");
    //fileDefinition.setFilename("testfile.xml");
    //FileResource fdResource = resourceDriver.getResource(fileDefinition);
    //
    //
    //String res = "";
    //try {
    //    res = IOUtils.toString(fdResource.getInputStream());
    //} catch (IOException e) {
    //        e.printStackTrace();
    //    }
    //    System.out.println(res);
    //}
    //
    ///** 
    // * 文件资源存储<br/>
    // * <功能详细描述>
    // * @param driver [参数说明]
    // * 
    // * @return void [返回类型说明]
    // * @exception throws [异常类型] [异常说明]
    // * @see [类、类#方法、类#成员]
    // */
    //private static void testSystemFileDriver(String driver) {
    //    FileDefinitionResourceDriver resourceDriver = parseDriver(driver);
    //    System.out.println(resourceDriver);
    //    FileDefinition fileDefinition = new FileDefinition();
    //    fileDefinition.setCreateDate(new Date());
    //    fileDefinition.setId(UUIDUtils.generateUUID());
    //    fileDefinition.setRelativePath("testfolder/testfile.xml");
    //fileDefinition.setFilename("testfile.xml");
    //    FileResource fdResource = resourceDriver.getResource(fileDefinition);
    //    
    //}
}
