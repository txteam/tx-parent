/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2014年2月24日
 * <修改描述:>
 */
package com.tx.core.util;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.dialect.Dialect;
import org.springframework.beans.factory.InitializingBean;

import com.tx.core.dbscript.model.DataSourceTypeEnum;
import com.tx.core.exceptions.argument.NullArgException;
import com.tx.core.exceptions.util.AssertUtils;

/**
 * 本地sql工具类 <功能详细描述>
 * 
 * @author Administrator
 * @version [版本号, 2014年2月24日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class LocalSqlUtils implements InitializingBean {
    
    public static LocalSqlUtils instance = null;
    
    private Dialect dialect;
    
    private DataSourceTypeEnum dataSourceType;
    
    /** <默认构造函数> */
    protected LocalSqlUtils() {
        super();
    }
    
    /**
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        if (this.dialect != null) {
            //doNothing
        } else if (this.dataSourceType != null) {
            this.dialect = this.dataSourceType.getDialect();
        } else {
            throw new NullArgException("dialect and dataSourceType all null");
        }
        LocalSqlUtils.instance = this;
    }
    
    /**
     * 获取本地sql处理工具实例类 <功能详细描述>
     * 
     * @return [参数说明]
     * 
     * @return LocalSqlUtils [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static LocalSqlUtils instance() {
        AssertUtils.notNull(instance,
                "instance is null.LocalSqlUtils must be init on springContext When start.");
        return instance;
    }
    
    /**
     * 构建like字符串 <功能详细描述>
     * 
     * @param parameter
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public String buildLikeArg(String parameter) {
        List<String> concatArgs = new ArrayList<String>();
        concatArgs.add("'%'");
        concatArgs.add(parameter);
        concatArgs.add("'%'");
        String valueStr = this.dialect.getFunctions()
                .get("concat")
                .render(null, concatArgs, null);
        return valueStr;
    }
    
    /**
     * 构建前置like字符串 <功能详细描述>
     * 
     * @param parameter
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public String buildBeforeLikeArg(String parameter) {
        List<String> concatArgs = new ArrayList<String>();
        concatArgs.add(parameter);
        concatArgs.add("'%'");
        String valueStr = this.dialect.getFunctions()
                .get("concat")
                .render(null, concatArgs, null);
        return valueStr;
    }
    
    /**
     * 构建后置like字符串 <功能详细描述>
     * 
     * @param parameter
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public String buildAfterLikeArg(String parameter) {
        List<String> concatArgs = new ArrayList<String>();
        concatArgs.add(parameter);
        concatArgs.add("'%'");
        String valueStr = this.dialect.getFunctions()
                .get("concat")
                .render(null, concatArgs, null);
        return valueStr;
    }
    
    /**
     * @param 对dialect进行赋值
     */
    public void setDialect(Dialect dialect) {
        this.dialect = dialect;
    }
    
    /**
     * @param 对dataSourceType进行赋值
     */
    public void setDataSourceType(DataSourceTypeEnum dataSourceType) {
        this.dataSourceType = dataSourceType;
        this.dialect = this.dataSourceType.getDialect();
    }
    
    public static void main(String[] args) {
        System.out.println("use: @com.tx.core.util.LocalSqlUtils@instance().buildLikeArg('#{xxx}')");
        
        LocalSqlUtils t = new LocalSqlUtils();
        t.setDataSourceType(DataSourceTypeEnum.MYSQL);
        
        System.out.println(t.buildLikeArg("#{test}"));
        
        LocalSqlUtils t2 = new LocalSqlUtils();
        t2.setDataSourceType(DataSourceTypeEnum.ORACLE);
        
        System.out.println(t2.buildLikeArg("#{test}"));
    }
}
