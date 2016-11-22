/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2016年10月12日
 * <修改描述:>
 */
package com.tx.component.basicdata.context;

import java.beans.PropertyDescriptor;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;

import com.tx.component.basicdata.model.BasicData;
import com.tx.core.exceptions.util.AssertUtils;

/**
 * 基础数据加载器<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2016年10月12日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
class BasicDataSetupHandler {
    
    /** 基础数据对象 */
    private Object object;
    
    /** 基础数据容器 */
    private BasicDataContext context;
    
    /** <默认构造函数> */
    public BasicDataSetupHandler(Object object, BasicDataContext context) {
        super();
        this.object = object;
        this.context = context;
        AssertUtils.notNull(this.object, "object is null");
    }
    
    /** 装载基础数据 */
    public void setup() {
        BeanWrapper bw = PropertyAccessorFactory.forBeanPropertyAccess(this.object);
        for (PropertyDescriptor pd : bw.getPropertyDescriptors()) {
            String pdName = pd.getName();
            if (!bw.isReadableProperty(pdName)) {
                //如果不是可读属性则跳过该属性
                continue;
            }
            Object pdValue = bw.getPropertyValue(pdName);
            if (pdValue == null || !(pdValue instanceof BasicData)) {
                //如果为空，或不为基础数据的实现
                continue;
            }
            BasicData basicDataValue = (BasicData) pdValue;
            Class<? extends BasicData> basicDataType = basicDataValue.getClass();
            
            String id = basicDataValue.getId();
            String code = basicDataValue.getCode();
            String name = basicDataValue.getName();
            if (StringUtils.isEmpty(id) && StringUtils.isEmpty(code)) {
                //id 和 code 均为空，则跳过
                continue;
            }
            if (!StringUtils.isEmpty(id) && !StringUtils.isEmpty(code)
                    && !StringUtils.isEmpty(name)) {
                //如果id,code,name均不为空，则不进行加载
                continue;
            }
            BasicDataService<? extends BasicData> service = this.context.getBasicDataService(basicDataType);
            if (service == null) {
                //如果没有对应类型业务层逻辑<br/>
                continue;
            }
            
            //如果进入此处逻辑，则需要加载对应的基础数据
            BasicData bdFromDB = null;
            if (!StringUtils.isEmpty(id)) {
                bdFromDB = service.findById(id);
            } else {
                bdFromDB = service.findByCode(code);
            }
            if (bdFromDB == null) {
                //如果查询出的额数据为空，有可能已经被删除了，则跳过
                continue;
            }
            
            BasicDataSetupHandler setupHandler = new BasicDataSetupHandler(
                    bdFromDB, this.context);
            //写入
            if (bw.isWritableProperty(pdName)) {
                //如果有写入
                bw.setPropertyValue(pdName, bdFromDB);
            } else {
                BeanUtils.copyProperties(bdFromDB, pdValue);
            }
            setupHandler.setup();
        }
    }
}
