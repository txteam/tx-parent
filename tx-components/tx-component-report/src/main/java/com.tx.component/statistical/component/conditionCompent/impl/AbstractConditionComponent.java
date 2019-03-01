package com.tx.component.statistical.component.conditionCompent.impl;

import com.tx.component.statistical.component.conditionCompent.ConditionComponent;
import com.tx.component.statistical.mapping.ConditionItem;
import com.tx.component.statistical.mapping.BaseAttr;
import com.tx.component.statistical.service.StatisticalReportService;
import com.tx.component.statistical.type.ConditionTypeEnum;
import com.tx.component.statistical.utils.EnumUtil;
import com.tx.core.util.StringUtils;
import org.apache.commons.collections.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by SEELE on 2016/9/22.
 */
public abstract class AbstractConditionComponent implements ConditionComponent {

    private Map<ConditionTypeEnum, AbstractConditionComponent> conditionTypeEnumMap = new HashMap<>();

//    @Resource(name = "defaultStatisticalEngineService")
//    protected StatisticalDataEngineService statisticalDataEngineService;

    @Resource(name = "StatisticalReport.StatisticalReportService")
    protected StatisticalReportService statisticalReportService;

    /**
     * 设置默认的属性
     *
     * @param conditionItem
     */
    private void setDefaultProperties(ConditionItem conditionItem) {
        List<BaseAttr> baseAttrs = getDefaultAttrs();
        if (CollectionUtils.isEmpty(baseAttrs)) {
            return;
        }
        Map<String, BaseAttr> nodeAttrMap = conditionItem.getNodeAttrs();
        for (BaseAttr defaultBaseAttr : baseAttrs) {
            String key = defaultBaseAttr.getKey().toLowerCase();
            BaseAttr currentBaseAttr = nodeAttrMap.get(key);
            if (currentBaseAttr == null
                    || StringUtils.isEmpty(currentBaseAttr.getValue())) {
                nodeAttrMap.put(key, defaultBaseAttr);
            }
        }
        conditionItem.setNodeAttrs(nodeAttrMap);
    }

    /*    @Override
    public void addProperties(ConditionItem conditionItem) {
    
    }
    */
    protected List<BaseAttr> getDefaultAttrs() {
        return null;
    }

    @Override
    public String generatorHtml(ConditionItem conditionItem) {
        setDefaultProperties(conditionItem);
        //移除部分不需要展示到页面上的属性
        Map<String, BaseAttr> nodeAttrMap = conditionItem.getNodeAttrs();
        for (String key : needRemoveAttrs()) {
            nodeAttrMap.remove(key);
        }
        return doGeneratorHtml(conditionItem);
    }

    protected Set<String> needRemoveAttrs() {
        Set<String> needRemoveAttrs = new HashSet<>();
       /* needRemoveAttrs.add("id");
        needRemoveAttrs.add("name");
        needRemoveAttrs.add("type");
        needRemoveAttrs.add("datasource");
        needRemoveAttrs.add("database");
        needRemoveAttrs.add("enumClass");*/
        return needRemoveAttrs;
    }

    /**
     * 生成标签
     *
     * @param conditionItem
     * @return
     */
    protected String doGeneratorHtml(ConditionItem conditionItem) {
        Map<String, BaseAttr> nodeAttrMap = conditionItem.getNodeAttrs();
        StringBuffer tag = new StringBuffer();

        tag.append("<").append(tagName());
        for (BaseAttr baseAttr : nodeAttrMap.values()) {
            tag.append("  ");
            tag.append(baseAttr.getKey())
                    .append(" = ")
                    .append("\"")
                    .append(baseAttr.getValue())
                    .append("\" ");
        }
        tag.append(" id ")
                .append(" = ")
                .append("\"")
                .append(conditionItem.getId())
                .append("\" ");
        tag.append(" name ")
                .append(" = ")
                .append("\"")
                .append(conditionItem.getId())
                .append("\" ");

        String generatorTagChild = generatorInnerHTML(conditionItem);
        if (StringUtils.isEmpty(generatorTagChild)) {
            tag.append(" />");
        } else {
            tag.append(" >");
            tag.append(generatorTagChild);
            tag.append("</").append(tagName()).append(">");

        }
        return tag.toString();
    }

    /**
     * 标签的子内容，如select的option
     *
     * @param conditionItem
     * @return
     */
    protected String generatorInnerHTML(ConditionItem conditionItem) {
        return null;
    }

    /**
     * 加载数据
     *
     * @param conditionItem
     * @return
     */
    protected List<BaseAttr> buildAttrNodeFromData(ConditionItem conditionItem) {
        List<BaseAttr> dataBaseAttrs = new ArrayList<>();
        try {
            String labelName = conditionItem.getLabelName();

            if (StringUtils.isNotEmpty(conditionItem.getItems())) {
                String items = conditionItem.getItems();
                if (items.contains("{")) {
                    String[] itemArr = items.split("，|,");
                    for (String str : itemArr) {
                        str = str.replace("{", "").replace("}", "");
                        String[] subStrArr = str.split(":");
                        BaseAttr baseAttr = null;
                        if (subStrArr.length >= 2) {
                            baseAttr = new BaseAttr(subStrArr[0], subStrArr[1]);
                        } else if (subStrArr.length == 1) {
                            baseAttr = new BaseAttr(subStrArr[0], subStrArr[0]);
                        }
                        dataBaseAttrs.add(baseAttr);
                    }
                } else {
                    String[] itemArr = items.split(",");
                    for (String str : itemArr) {
                        BaseAttr baseAttr = new BaseAttr(str, str);
                        dataBaseAttrs.add(baseAttr);
                    }
                }

            }

            if (StringUtils.isNotEmpty(conditionItem.getEnumClass())) {
                Enum[] enums = EnumUtil.parseEnum2Arr(conditionItem.getEnumClass());
                for (Enum temp : enums) {
                    BaseAttr baseAttr = null;
                    if (StringUtils.isEmpty(labelName)) {
                        baseAttr = new BaseAttr(temp.name(), temp.name());
                    } else {
                        baseAttr = new BaseAttr(temp.name(), EnumUtil.getFieldValue(temp, labelName));
                    }
                    dataBaseAttrs.add(baseAttr);
                }

            } else if (StringUtils.isNotEmpty(conditionItem.getSqlMapperId())) {
                String labelValue = conditionItem.getLabelValue();
                labelValue = StringUtils.isEmpty(labelValue) ? labelName : labelValue;
                List<Map<String, Object>> list = statisticalReportService.queryList(conditionItem.getSqlMapperId(), null);
                for (Map<String, Object> temp : list) {
                    BaseAttr baseAttr = new BaseAttr((String) temp.get(labelValue), (String) temp.get(labelName));
                    dataBaseAttrs.add(baseAttr);
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return dataBaseAttrs;
    }


    @Override
    public boolean isSupport(String typeStr) {
        ConditionTypeEnum conditionTypeEnum = supportType();
        return conditionTypeEnum.name().equalsIgnoreCase(typeStr);
    }
}
