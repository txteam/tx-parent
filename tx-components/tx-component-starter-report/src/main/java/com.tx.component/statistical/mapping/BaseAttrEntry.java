package com.tx.component.statistical.mapping;

import com.tx.component.statistical.utils.EnumUtil;
import com.tx.core.util.StringUtils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * <br/>
 *
 * @author XRX
 * @version [版本号, 2017/12/08]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class BaseAttrEntry {
    protected Map<String, BaseAttr> nodeAttrs;

    public Map<String, BaseAttr> getNodeAttrs() {
        return nodeAttrs;
    }

    public void setNodeAttrs(Map<String, BaseAttr> nodeAttrsMap) {
        if (nodeAttrsMap == null) {
            this.nodeAttrs = new HashMap<>();
            return;
        }
        //将key全部转化为小写
        Map<String, BaseAttr> tempNodeAttrsMap = new HashMap<>();
        for (String key : nodeAttrsMap.keySet()) {
            tempNodeAttrsMap.put(key.toLowerCase(), nodeAttrsMap.get(key));
        }
        this.nodeAttrs = tempNodeAttrsMap;

        try {
            Field[] fields = this.getClass().getDeclaredFields();
            for (Field field : fields) {
                String fieldName = field.getName().toLowerCase();
                if (fieldName.equalsIgnoreCase("nodeAttrs")) {
                    continue;
                }

                //为子类设置相应的属性
                if (nodeAttrs.containsKey(fieldName)
                        && nodeAttrs.get(fieldName) != null) {
                    String fieldValue = nodeAttrs.get(fieldName).getValue();
                    if (StringUtils.isEmpty(fieldValue)) {
                        continue;
                    }
                    field.setAccessible(true);
                    Class fieldType = field.getType();
                    //String 类型
                    if (fieldType.equals(String.class)) {
                        field.set(this, fieldValue);
                        removeAttr(fieldName);
                        //枚举类型
                    } else if (fieldType.isEnum()) {
                        Map<String, Enum<?>> enumMap = EnumUtil.parseEnum(field.getType());
                        for (String key : enumMap.keySet()) {
                            if (key.equalsIgnoreCase(fieldValue)) {
                                field.set(this, enumMap.get(key));
                                break;
                            }
                        }
                        removeAttr(fieldName);
                        //TODO 基本类型
                    } else if (fieldType.isPrimitive()) {

                        // field.setBoolean();
                    }
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private void removeAttr(String attr) {
        if (removeObjectFieldAttr()) {
            nodeAttrs.remove(attr.toLowerCase());
        }
    }

    /**
     * 是否需要从NodeAttr中移除原有对象的属性
     *
     * @return
     */
    protected boolean removeObjectFieldAttr() {
        return false;
    }
}
