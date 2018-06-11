/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2018年6月11日
 * <修改描述:>
 */
package com.tx.test.model;

/**
 * <功能简述>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2018年6月11日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@TestAnnotationOnModel
public class TestModel2 extends TestAbstractModel implements TestInterface {
    
    private String testModel2Property;
    
    private String interfaceProperty;
    
    private String abstractProperty;
    
    /**
     * @return 返回 testModel2Property
     */
    public String getTestModel2Property() {
        return testModel2Property;
    }
    
    /**
     * @param 对testModel2Property进行赋值
     */
    public void setTestModel2Property(String testModel2Property) {
        this.testModel2Property = testModel2Property;
    }
    
    /**
     * @return
     */
    @Override
    public String getInterfaceProperty() {
        return this.interfaceProperty;
    }
    
    /**
     * @return
     */
    @Override
    public String getAbstractProperty() {
        return this.abstractProperty;
    }
    
    /**
     * @param 对interfaceProperty进行赋值
     */
    public void setInterfaceProperty(String interfaceProperty) {
        this.interfaceProperty = interfaceProperty;
    }
    
    /**
     * @param 对abstractProperty进行赋值
     */
    public void setAbstractProperty(String abstractProperty) {
        this.abstractProperty = abstractProperty;
    }
    
}
