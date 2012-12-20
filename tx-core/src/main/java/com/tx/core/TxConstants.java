/**
 * 文 件 名:  TxConstants.java
 * 版    权:  TX Workgroup . Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  PengQingyang
 * 修改时间:  2012-5-5
 * <修改描述:>
 */
package com.tx.core;

/**
 * 常用变量
 * 
 * @author  PengQingyang
 * @version  [版本号, 2012-5-5]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface TxConstants {
    /** 默认的字符集 */
    String DEFAULT_CHARSET = "UTF-8";
    
    /** 新生成StringBuild,buffer,等字符相关对象时默认应该设置的初始化大小 */
    int INITIAL_STR_LENGTH = 128;
    
    /** 新生成map对象时，默认应该设置的初始化大小 */
    int INITIAL_MAP_SIZE = 32;
    
    /** 新生成容器list,set对象时，默认应该设置的初始化大小 */
    int INITIAL_CONLLECTION_SIZE = 32;
}
