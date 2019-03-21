/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2014年2月19日
 * <修改描述:>
 */
package com.tx.component.configuration.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tx.component.configuration.context.ConfigContext;
import com.tx.component.configuration.model.ConfigProperty;
import com.tx.core.tree.util.TreeUtils;

/**
 * 配置属性控制器 <br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2014年2月19日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Controller("configurationController")
@RequestMapping("/configuration")
public class ConfigurationController {
    
    @Resource(name = "configContext")
    private ConfigContext configContext;
    
    /**
     * 更新配置属性值<br/>
     *<功能详细描述>
     * @param key
     * @param value
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @ResponseBody
    @RequestMapping("update")
    public boolean update(String code, String value) {
        
        return true;
    }
    
    /**
      * 查询所有的配置属性组
      *<功能详细描述>
      * @return [参数说明]
      * 
      * @return List<ConfigPropertyGroup> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    @ResponseBody
    @RequestMapping("getAllConfigPropertyGroup")
    public List<ConfigPropertyGroup> getAllConfigPropertyGroup() {
        List<ConfigPropertyGroup> configPropertyGroupList = configContext
                .getAllConfigPropertyGroup();
        return configPropertyGroupList;
    }
    
    /**
      * 获取配置属性树节点<br/>
      *<功能详细描述>
      * @param configPropertyGroupName
      * @return [参数说明]
      * 
      * @return List<TreeNode> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    @ResponseBody
    @RequestMapping("getConfigPropertyTreeNodeList")
    public List<TreeNode> getConfigPropertyTreeNodeList(
            @RequestParam(value = "configPropertyGroupName", required = false) String configPropertyGroupName) {
        List<ConfigPropertyGroup> configPropertyGroupList = new ArrayList<ConfigPropertyGroup>(
                configContext.getAllConfigPropertyGroup());
        List<ConfigProperty> configPropertyList = new ArrayList<ConfigProperty>(
                configContext.getAllConfigPropertyMap().values());
        
        List<TreeNode> resList = new ArrayList<TreeNode>();
        resList.addAll(TreeNodeUtils.transformedList(configPropertyGroupList,
                configGroupAdapter));
        resList.addAll(TreeNodeUtils.transformedList(configPropertyList,
                configPropertyAdapter));
        
        List<TreeNode> resTreeList = TreeUtils.changeToTree(resList);
        if (StringUtils.isEmpty(configPropertyGroupName)) {
            return resTreeList;
        } else {
            resTreeList = new ArrayList<TreeNode>();
            for (TreeNode treeNodeTemp : resList) {
                if (configPropertyGroupName
                        .equals(treeNodeTemp.getParentId())) {
                    resTreeList.add(treeNodeTemp);
                }
            }
            return resTreeList;
        }
    }
    
}
