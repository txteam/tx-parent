/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2018年6月11日
 * <修改描述:>
 */
package com.tx.test.model;

/**
 * 测试抽象类<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2018年6月11日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@TestAnnotationOnAbstract
public abstract class TestAbstractModel {
    
    private String id;
    
    private String code;
    
    private String name;
    
    /**
     * @return 返回 id
     */
    public String getId() {
        return id;
    }
    
    /**
     * @param 对id进行赋值
     */
    public void setId(String id) {
        this.id = id;
    }
    
    /**
     * @return 返回 code
     */
    public String getCode() {
        return code;
    }
    
    /**
     * @param 对code进行赋值
     */
    public void setCode(String code) {
        this.code = code;
    }
    
    /**
     * @return 返回 name
     */
    public String getName() {
        return name;
    }
    
    /**
     * @param 对name进行赋值
     */
    public void setName(String name) {
        this.name = name;
    }
    
    public abstract String getAbstractProperty();
    
}
