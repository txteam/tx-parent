/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2016年9月15日
 * <修改描述:>
 */
package com.tx.core.support.entrysupport.support;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

import org.apache.commons.collections.MapUtils;

import com.tx.core.support.entrysupport.model.AbstractEntryAble;
import com.tx.core.support.entrysupport.model.EntityEntry;

/**
 * 实体分项反射类描述<br/>
 *     暂不用去支持Column等注解<br/>
 *     entry中附加属性如果需要作为查询条件不建议使用该类进行应用<br/>
 *     一般适用于其他字段仅仅是附加属性并且为简单类型，复杂类型不应适用于该类型实现<br/>
 * 
 * @author  Administrator
 * @version  [版本号, 2016年9月15日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class TestMetaEntityEntry {
    
    public static class TestEntityEntry extends EntityEntry {
        /** 注释内容 */
        private static final long serialVersionUID = 2777848293811612650L;
        
        private String test1;
        
        private String test2;
        
        private Date testDate;
        
        private BigDecimal testBigDecimal;
        
        private boolean valid;
        
        public String getTest1() {
            return test1;
        }
        
        public void setTest1(String test1) {
            this.test1 = test1;
        }
        
        public String getTest2() {
            return test2;
        }
        
        public void setTest2(String test2) {
            this.test2 = test2;
        }
        
        public Date getTestDate() {
            return testDate;
        }
        
        public void setTestDate(Date testDate) {
            this.testDate = testDate;
        }
        
        public BigDecimal getTestBigDecimal() {
            return testBigDecimal;
        }
        
        public void setTestBigDecimal(BigDecimal testBigDecimal) {
            this.testBigDecimal = testBigDecimal;
        }
        
        public boolean isValid() {
            return valid;
        }
        
        public void setValid(boolean valid) {
            this.valid = valid;
        }
    }
    
    public static class TestEntryAbleFieldEntityOfEntityEntry extends
            AbstractEntryAble<EntityEntry> {
        
        /** 注释内容 */
        private static final long serialVersionUID = 8355518853017127324L;
        
        private String id;
        
        public String getId() {
            return id;
        }
        
        public void setId(String id) {
            this.id = id;
        }
        
    }
    
    public static class TestEntryAbleFieldEntityOfTestEntityEntry extends
            AbstractEntryAble<TestEntityEntry> {
        
        /** 注释内容 */
        private static final long serialVersionUID = 5429503808574541835L;
        
        private String id;
        
        public String getId() {
            return id;
        }
        
        public void setId(String id) {
            this.id = id;
        }
        
    }
    
    public static void main(String[] args) throws IllegalAccessException,
            InvocationTargetException {
        Class<?> type = TestEntityEntry.class;
        MetaEntityEntry mee = MetaEntityEntry.forClass(type, "t_test_entry");
        
        System.out.println(mee.getSqlOfInsert());
        System.out.println(mee.getSqlOfInsertToHis());
        
        System.out.println(mee.getSqlOfDeleteByEntityId());
        System.out.println(mee.getSqlOfDeleteById());
        System.out.println(mee.getSqlOfDeleteByEntryKey());
        
        System.out.println(mee.getSqlOfUpdateById());
        System.out.println(mee.getSqlOfUpdateByEntryKey());
        
        System.out.println(mee.getSqlOfFindById());
        System.out.println(mee.getSqlOfFindByEntryKey());
        
        System.out.println(mee.getSqlOfQueryListByEntityId());
        
        TestEntityEntry tt = new TestEntityEntry();
        tt.setEntityId("entityId");
        tt.setEntryValue("entryValue");
        tt.setTest1("test1Value");
        tt.setTest2("test2Value");
        tt.setTestDate(new Date());
        tt.setTestBigDecimal(new BigDecimal("123.34"));
        
        Map<String, Object> res = mee.transferBean2Map(tt);
        MapUtils.debugPrint(System.out, "out", res);
    }
}
