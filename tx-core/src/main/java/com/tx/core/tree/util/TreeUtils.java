package com.tx.core.tree.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.tx.core.TxConstants;
import com.tx.core.tree.model.TreeAble;
import com.tx.core.tree.model.TreeAbleAdapter;
import com.tx.core.tree.model.TreeAbleProxy;

/**
 * <树转换工具类>
 * <功能详细描述>
 * 
 * @author  PengQingyang
 * @version  [版本号, 2012-10-5]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class TreeUtils {
    
//    /**
//      * 根据传入对象列表，以及适配器，生成代理树节点对象树
//      * <功能详细描述>
//      * @param objectList
//      * @param adapter
//      * @return [参数说明]
//      * 
//      * @return C [返回类型说明]
//      * @exception throws [异常类型] [异常说明]
//      * @see [类、类#方法、类#成员]
//     */
//    @SuppressWarnings("rawtypes")
//    public static <A, B extends Collection<A>, C extends Collection<TreeAbleProxy>> C changeToProxyTree(
//            B objectList, TreeAbleAdapter<B> adapter) {
//        if (objectList == null) {
//            return null;
//        }
//        C resCollection = generatorTreeAbleProxyCollection(objectList, adapter);
//        return changToTree(resCollection);
//    }
//    
//    /**
//     * 根据传入对象列表，以及适配器，生成代理树节点对象树<br/>
//     * 可限制树节点迭代层数
//     * <功能详细描述>
//     * @param objectList
//     * @param adapter
//     * @param maxLevelIndex
//     * @return [参数说明]
//     * 
//     * @return C [返回类型说明]
//     * @exception throws [异常类型] [异常说明]
//     * @see [类、类#方法、类#成员]
//    */
//    @SuppressWarnings("rawtypes")
//    public static <A, B extends Collection<A>, C extends Collection<TreeAbleProxy>> C changeToProxyTree(
//            B objectList, TreeAbleAdapter<B> adapter, int maxLevelIndex) {
//        if (objectList == null) {
//            return null;
//        }
//        C resCollection = generatorTreeAbleProxyCollection(objectList, adapter);
//        return changToTree(resCollection, maxLevelIndex);
//    }
//    
//    /**
//      * 根据对象集合生成树节点代理
//      * <功能详细描述>
//      * @param objectList
//      * @param adapter
//      * @return [参数说明]
//      * 
//      * @return C [返回类型说明]
//      * @exception throws [异常类型] [异常说明]
//      * @see [类、类#方法、类#成员]
//     */
//    @SuppressWarnings({ "rawtypes", "unchecked" })
//    private static <T, C extends Collection<?>, B extends Collection<T>> C generatorTreeAbleProxyCollection(
//            B objectList, TreeAbleAdapter<B> adapter) {
//        C resCollection = newTreeProxyCollectionInstance(objectList);
//        for (T objTemp : objectList) {
//            resCollection.add(new TreeAbleProxy(adapter, objTemp));
//        }
//        return resCollection;
//    }
//    
//    /**
//      * 生成与入参集合相同的树代理集合
//      * <功能详细描述>
//      * @param treeAbleCollection
//      * @return [参数说明]
//      * 
//      * @return C [返回类型说明]
//      * @exception throws [异常类型] [异常说明]
//      * @see [类、类#方法、类#成员]
//     */
//    @SuppressWarnings({ "rawtypes", "unchecked" })
//    private static <T, B extends Collection<T>,C extends Collection<Collection<TreeAbleProxy<C,T>>, T>> Collection<TreeAbleProxy<Collection<TreeAbleProxy<C,T>>, T>> newTreeProxyCollectionInstance(
//            B treeAbleCollection) {
//        if (treeAbleCollection instanceof List) {
//            return (C) new ArrayList<TreeAbleProxy>(
//                    TxConstants.INITIAL_CONLLECTION_SIZE);
//        }
//        else if (treeAbleCollection instanceof Set) {
//            return (C) new HashSet<TreeAbleProxy>(
//                    TxConstants.INITIAL_CONLLECTION_SIZE);
//        }
//        //when default
//        return (C) new ArrayList<TreeAbleProxy>(
//                TxConstants.INITIAL_CONLLECTION_SIZE);
//    }

    
    /**
     * 根据指定集合生成树<br/>
     * 首先找到不存在父节点的所有节点，将这些节点当作根节点<br/>
     * 父节点不存在于集合，以及父节点于自己的id相同的节点将被当作根节点集合的一员<br/>
     * 然后利用这些根节点迭代生成树
     * <功能详细描述>
     * @param treeAbleCollection
     * @return [参数说明]
     * 
     * @return C [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    @SuppressWarnings("rawtypes")
    public static <C extends Collection<T>, T extends TreeAble> C changToTree(
            C treeAbleCollection) {
        return changToTree(treeAbleCollection, -1);
    }
    
    /**
      * 根据指定集合生成树<br/>
      * 首先找到不存在父节点的所有节点，将这些节点当作根节点<br/>
      * 然后利用这些根节点迭代生成树<br/>
      * 允许通过参数限制生成树的层数
      * <功能详细描述>
      * @param treeAbleCollection
      * @param maxLevelIndex
      * @return [参数说明]
      * 
      * @return C [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    @SuppressWarnings("rawtypes")
    public static <C extends Collection<T>, T extends TreeAble> C changToTree(
            C treeAbleCollection, int maxLevelIndex) {
        //判断入参是否合法
        if (treeAbleCollection == null || treeAbleCollection.size() == 0) {
            //如果root或需要转换的集合其中有一个为空，直接返回root集合当作结果
            return treeAbleCollection;
        }
        
        //循环得到当前传入list上级节点有多少个
        //并进行非法节点过滤，如果某节点id  = parentId将会造成死循环 ,将这样的节点抛弃
        Map<String, C> parentIdIndexMap = new HashMap<String, C>();
        Set<String> treeAbleNodeIdSet = new HashSet<String>();
        for (T treeAbleTemp : treeAbleCollection) {
            String superId = treeAbleTemp.getParentId();
            String id = treeAbleTemp.getId();
            if (StringUtils.isEmpty(id) || id.equals(superId)) {
                //并进行非法节点过滤，如果某节点id  = parentId将会造成死循环
                //非法节点在该方法中将被当作根节点放置于树中
                //doNothing
                continue;
            }
            
            treeAbleNodeIdSet.add(treeAbleTemp.getId());
            //根据父节点形成父ID与集合的映射
            if (parentIdIndexMap.containsKey(superId)) {
                parentIdIndexMap.get(superId).add(treeAbleTemp);
            }
            else {
                C collectionTemp = newCollectionInstance(treeAbleCollection);
                collectionTemp.add(treeAbleTemp);
                parentIdIndexMap.put(superId, collectionTemp);
            }
        }
        
        //迭代生成根节点集合
        C parentNodeCollection = newCollectionInstance(treeAbleCollection);
        for (T treeAbleTemp : treeAbleCollection) {
            if (!treeAbleNodeIdSet.contains(treeAbleTemp.getParentId())) {
                parentNodeCollection.add(treeAbleTemp);
            }
        }
        
        return changToTree(treeAbleCollection,
                parentNodeCollection,
                maxLevelIndex);
    }
    
    /**
     * 根据指定根节点集合<br/>
     * 以及具有parentId的集合生成树（将子节点set入树中）
     * <功能详细描述>
     * @param treeAbleCollection
     * @param parentNodeCollection
     * @return [参数说明]
     * 
     * @return C [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    @SuppressWarnings("rawtypes")
    public static <C extends Collection<T>, T extends TreeAble> C changToTree(
            C treeAbleCollection, C parentNodeCollection) {
        return changToTree(treeAbleCollection, parentNodeCollection, -1);
    }
    
    /**
      * 根据指定根节点集合<br/>
      * 以及具有parentId的集合生成树（将子节点set入树中）
      * <功能详细描述>
      * @param treeAbleCollection
      * @param parentNodeCollection
      * @param maxLevelIndex  生成树的最大层数如果maxLevelIndex<=0则认为无需限制层数<br/>
      * maxLevelIndex = 1直接返回根节点
      * maxLevelIndex = 2认为有两层节点
      * @return [参数说明]
      * 
      * @return C [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    @SuppressWarnings("rawtypes")
    public static <C extends Collection<T>, T extends TreeAble> C changToTree(
            C treeAbleCollection, C parentNodeCollection, int maxLevelIndex) {
        //判断入参是否合法
        if (treeAbleCollection == null || parentNodeCollection == null
                || treeAbleCollection.size() == 0
                || parentNodeCollection.size() == 0) {
            //如果root或需要转换的集合其中有一个为空，直接返回root集合当作结果
            return parentNodeCollection;
        }
        
        //循环得到当前传入list上级节点有多少个
        //并进行非法节点过滤，如果某节点id  = parentId将会造成死循环 ,将这样的节点抛弃
        Map<String, C> parentIdIndexMap = new HashMap<String, C>();
        for (T treeAbleTemp : treeAbleCollection) {
            String superId = treeAbleTemp.getParentId();
            String id = treeAbleTemp.getId();
            if (StringUtils.isEmpty(id) || id.equals(superId)) {
                //并进行非法节点过滤，如果某节点id  = parentId将会造成死循环
                //doNothing
                continue;
            }
            
            //根据父节点形成父ID与集合的映射
            if (parentIdIndexMap.containsKey(superId)) {
                parentIdIndexMap.get(superId).add(treeAbleTemp);
            }
            else {
                C collectionTemp = newCollectionInstance(treeAbleCollection);
                collectionTemp.add(treeAbleTemp);
                parentIdIndexMap.put(superId, collectionTemp);
            }
        }
        
        return changeToTreeByParentIdIndexMap(parentNodeCollection,
                maxLevelIndex,
                parentIdIndexMap);
    }
    
    /** 
     *<功能简述>
     *<功能详细描述>
     * @param parentNodeCollection
     * @param maxLevelIndex
     * @param parentIdIndexMap
     * @return [参数说明]
     * 
     * @return C [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @SuppressWarnings("rawtypes")
    private static <C extends Collection<T>, T extends TreeAble> C changeToTreeByParentIdIndexMap(
            C parentNodeCollection, int maxLevelIndex,
            Map<String, C> parentIdIndexMap) {
        //如果合法的节点映射得到的集合为空
        if (parentIdIndexMap.size() == 0) {
            return parentNodeCollection;
        }
        
        /** 将子节点放入父节点 */
        /** 并迭代进行下一级的节点放入 */
        C allNextChildCollecion = setNextLevelTree(parentIdIndexMap,
                parentNodeCollection);
        
        /** 开始迭代 */
        int nowTreeIndex = 1;
        while (allNextChildCollecion != null
                && allNextChildCollecion.size() > 0) {
            if (maxLevelIndex > 0 && nowTreeIndex >= nowTreeIndex) {
                //如果指定了tree的最大迭代层次，则根据该值不再迭代获取当前代的下一代子节点
                break;
            }
            allNextChildCollecion = setNextLevelTree(parentIdIndexMap,
                    allNextChildCollecion);
        }
        
        return parentNodeCollection;
    }
    
    /**
      *<获取下级树节点总集合>
      *<功能详细描述>
      * @param parentIdIndexMap
      * @param parentNodeCollection
      * @return [参数说明]
      * 
      * @return C [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private static <T extends TreeAble, C extends Collection<T>> C setNextLevelTree(
            Map<String, C> parentIdIndexMap, C parentNodeCollection) {
        
        //将子节点放入父节点 
        //并迭代进行下一级的节点放入
        C allNextChildCollection = newCollectionInstance(parentNodeCollection);
        for (T parentTreeAbleNodeTemp : parentNodeCollection) {
            String parentTreeNodeId = parentTreeAbleNodeTemp.getId();
            if (parentIdIndexMap.containsKey(parentTreeNodeId)) {
                parentTreeAbleNodeTemp.setChilds(parentIdIndexMap.get(parentTreeNodeId));
                allNextChildCollection.addAll(parentIdIndexMap.get(parentTreeNodeId));
            }
        }
        return allNextChildCollection;
    }
    
    /**
      * 生成树节点集合实体
      * <功能详细描述>
      * @param treeAbleCollection
      * @return [参数说明]
      * 
      * @return Collection<T> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private static <T extends TreeAble, C extends Collection<T>> C newCollectionInstance(
            C treeAbleCollection) {
        if (treeAbleCollection instanceof List) {
            return (C) new ArrayList<T>(TxConstants.INITIAL_CONLLECTION_SIZE);
        }
        else if (treeAbleCollection instanceof Set) {
            return (C) new HashSet<T>(TxConstants.INITIAL_CONLLECTION_SIZE);
        }
        //when default
        return (C) new ArrayList<T>(TxConstants.INITIAL_CONLLECTION_SIZE);
    }
}
