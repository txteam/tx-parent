/*
 * 描          述:  <描述>
 * 修  改   人:  PengQingyang
 * 修改时间:  2012-12-9
 * <修改描述:>
 */
package com.tx.core.mybatis.generator.model;


 /**
  * selectkey实体，当指定主键生成策略为sequence时需要生成该类
  * <功能详细描述>
  * 
  * @author  PengQingyang
  * @version  [版本号, 2012-12-9]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public class SqlMapSelectKey {
    
    /** 对应属性 */
    private String keyProperty;
    
    /** 对应属性类型 */
    private String resultType;
    
    /** 对应sequece名 */
    private String sequence;

    /**
     * @return 返回 keyProperty
     */
    public String getKeyProperty() {
        return keyProperty;
    }

    /**
     * @param 对keyProperty进行赋值
     */
    public void setKeyProperty(String keyProperty) {
        this.keyProperty = keyProperty;
    }

    /**
     * @return 返回 resultType
     */
    public String getResultType() {
        return resultType;
    }

    /**
     * @param 对resultType进行赋值
     */
    public void setResultType(String resultType) {
        this.resultType = resultType;
    }

    /**
     * @return 返回 sequence
     */
    public String getSequence() {
        return sequence;
    }

    /**
     * @param 对sequence进行赋值
     */
    public void setSequence(String sequence) {
        this.sequence = sequence;
    }
}
