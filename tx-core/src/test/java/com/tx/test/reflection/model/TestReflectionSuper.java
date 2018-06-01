/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-9-27
 * <修改描述:>
 */
package com.tx.test.reflection.model;

import java.io.Serializable;

import javax.persistence.Id;


 /**
  * <功能简述>
  * <功能详细描述>
  * 
  * @author  brady
  * @version  [版本号, 2013-9-27]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public class TestReflectionSuper implements Serializable{
    
    /** 注释内容 */
    private static final long serialVersionUID = 5910110324123366035L;
    
    public static final String SUPER_STATIC_STRING = "test";

    @Id
    private String id;
    
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
    
    private boolean is;
    
    private boolean super_boolean;
    
    private boolean is_super_boolean;
    
    private Boolean super_Boolean;
    
    private Boolean isSuper_Boolean;
    
    private String abc_String;
    
    private int super_int;
    
    private Integer super_Integer;

    /**
     * @return 返回 is
     */
    public boolean isIs() {
        return is;
    }

    /**
     * @param 对is进行赋值
     */
    public void setIs(boolean is) {
        this.is = is;
    }

    /**
     * @return 返回 super_boolean
     */
    public boolean isSuper_boolean() {
        return super_boolean;
    }

    /**
     * @param 对super_boolean进行赋值
     */
    public void setSuper_boolean(boolean super_boolean) {
        this.super_boolean = super_boolean;
    }

    /**
     * @return 返回 is_super_boolean
     */
    public boolean isIs_super_boolean() {
        return is_super_boolean;
    }

    /**
     * @param 对is_super_boolean进行赋值
     */
    public void setIs_super_boolean(boolean is_super_boolean) {
        this.is_super_boolean = is_super_boolean;
    }

    /**
     * @return 返回 super_Boolean
     */
    public Boolean getSuper_Boolean() {
        return super_Boolean;
    }

    /**
     * @param 对super_Boolean进行赋值
     */
    public void setSuper_Boolean(Boolean super_Boolean) {
        this.super_Boolean = super_Boolean;
    }

    /**
     * @return 返回 isSuper_Boolean
     */
    public Boolean getIsSuper_Boolean() {
        return isSuper_Boolean;
    }

    /**
     * @param 对isSuper_Boolean进行赋值
     */
    public void setIsSuper_Boolean(Boolean isSuper_Boolean) {
        this.isSuper_Boolean = isSuper_Boolean;
    }

    /**
     * @return 返回 abc_String
     */
    public String getAbc_String() {
        return abc_String;
    }

    /**
     * @param 对abc_String进行赋值
     */
    public void setAbc_String(String abc_String) {
        this.abc_String = abc_String;
    }

    /**
     * @return 返回 super_int
     */
    public int getSuper_int() {
        return super_int;
    }

    /**
     * @param 对super_int进行赋值
     */
    public void setSuper_int(int super_int) {
        this.super_int = super_int;
    }

    /**
     * @return 返回 super_Integer
     */
    public Integer getSuper_Integer() {
        return super_Integer;
    }

    /**
     * @param 对super_Integer进行赋值
     */
    public void setSuper_Integer(Integer super_Integer) {
        this.super_Integer = super_Integer;
    }
}
