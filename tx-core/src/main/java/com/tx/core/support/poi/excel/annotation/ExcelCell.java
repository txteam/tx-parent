/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2014年5月13日
 * <修改描述:>
 */
package com.tx.core.support.poi.excel.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.tx.core.support.poi.excel.CellReader;
import com.tx.core.support.poi.excel.CellWriter;

/**
 * excel单元格定义
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2014年5月13日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({METHOD, FIELD})
@Inherited
public @interface ExcelCell {
    
    /**
      * 字段索引值<br/> 
      * <功能详细描述>
      * @return [参数说明]
      * 
      * @return int [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public int index();
    
    /**
      * 单元格格式<br/>
      *     读取时：
      *         -1时为不指定格式进行读取,系统通过兼容逻辑进行处理
      *         如果指定了格式对应的格式不正确时抛出异常CellTypeUnmatchException
      *     写入时：
      *         -1时默认为text类型写入
      *         如果指定了格式，则采用指定格式进行写入
      *<功能详细描述>
      * @return [参数说明]
      * 
      * @return int [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public int cellType() default -1;
    
    /**
      * 在生成时单元格的宽度<br/>
      *     -1时为默认宽度
      *<功能详细描述>
      * @return [参数说明]
      * 
      * @return int [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public int width() default -1;
    
    /**
      * 单元格读取器<br/> 
      *<功能详细描述>
      * @return [参数说明]
      * 
      * @return Class<? extends CellReader> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    @SuppressWarnings("rawtypes")
    public Class<? extends CellReader> reader() default CellReader.class;
    
    /**
      * 单元格写入器
      *<功能详细描述>
      * @return [参数说明]
      * 
      * @return Class<? extends CellWriter> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    @SuppressWarnings("rawtypes")
    public Class<? extends CellWriter> writer() default CellWriter.class;
}
