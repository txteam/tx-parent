///*
// * 描          述:  <描述>
// * 修  改   人:
// * 修改时间:
// * <修改描述:>
// */
//package com.tx.component.file.dao.impl;
//
//import java.util.List;
//import java.util.Map;
//
//import javax.annotation.Resource;
//
//import com.tx.component.file.dao.FileDefinitionDao;
//import com.tx.component.file.model.FileDefinition;
//import com.tx.core.mybatis.model.Order;
//import com.tx.core.mybatis.support.MyBatisDaoSupport;
//import com.tx.core.paged.model.PagedList;
//
///**
// * FileDefinition持久层
// * <功能详细描述>
// *
// * @author
// * @version  [版本号, 2012-12-11]
// * @see  [相关类/方法]
// * @since  [产品/模块版本]
// */
//public class FileDefinitionDaoImpl implements FileDefinitionDao {
//
//    @Resource(name = "fileContext.myBatisDaoSupport")
//    private MyBatisDaoSupport myBatisDaoSupport;
//
//    /**
//     * @param condition
//     */
//    @Override
//    public void batchInsert(List<FileDefinition> condition) {
//        this.myBatisDaoSupport.batchInsertUseUUID("fileDefinition.insertFileDefinition",
//                condition,
//                "id",
//                true);
//    }
//
//    /**
//     * @param condition
//     */
//    @Override
//    public void batchUpdate(List<Map<String, Object>> updateRowMapList) {
//        this.myBatisDaoSupport.batchUpdate("fileDefinition.updateFileDefinition",
//                updateRowMapList,
//                true);
//    }
//
//    /**
//     * @param condition
//     */
//    @Override
//    public void insertFileDefinition(FileDefinition condition) {
//        this.myBatisDaoSupport.insertUseUUID("fileDefinition.insertFileDefinition",
//                condition,
//                "id");
//    }
//
//    /**
//     * @param condition
//     */
//    @Override
//    public void insertFileDefinitionToHis(FileDefinition condition) {
//        this.myBatisDaoSupport.insertFileDefinition("fileDefinition.insertFileDefinitionToHis",
//                condition);
//    }
//
//    /**
//     * @param condition
//     * @return
//     */
//    @Override
//    public int deleteFileDefinition(FileDefinition condition) {
//        return this.myBatisDaoSupport.deleteFileDefinition("fileDefinition.deleteFileDefinition",
//                condition);
//    }
//
//    /**
//     * @param condition
//     * @return
//     */
//    @Override
//    public FileDefinition findFileDefinition(FileDefinition condition) {
//        return this.myBatisDaoSupport.<FileDefinition> findFileDefinition("fileDefinition.findFileDefinition",
//                condition);
//    }
//
//    /**
//     * @param params
//     * @return
//     */
//    @Override
//    public List<FileDefinition> queryFileDefinition(Map<String, Object> params) {
//        return this.myBatisDaoSupport.<FileDefinition> queryFileDefinition("fileDefinition.queryFileDefinition",
//                params);
//    }
//
//    /**
//     * @param params
//     * @param orderList
//     * @return
//     */
//    @Override
//    public List<FileDefinition> queryFileDefinition(Map<String, Object> params,
//            List<Order> orderList) {
//        return this.myBatisDaoSupport.<FileDefinition> queryFileDefinition("fileDefinition.queryFileDefinition",
//                params,
//                orderList);
//    }
//
//    /**
//     * @param params
//     * @return
//     */
//    @Override
//    public int count(Map<String, Object> params) {
//        return this.myBatisDaoSupport.<Integer> findFileDefinition("fileDefinition.queryFileDefinitionCount",
//                params);
//    }
//
//    /**
//     * @param params
//     * @param pageIndex
//     * @param pageSize
//     * @return
//     */
//    @Override
//    public PagedList<FileDefinition> queryPagedList(Map<String, Object> params,
//            int pageIndex, int pageSize) {
//        return this.myBatisDaoSupport.<FileDefinition> queryPagedList("fileDefinition.queryFileDefinition",
//                params,
//                pageIndex,
//                pageSize);
//    }
//
//    /**
//     * @param params
//     * @param pageIndex
//     * @param pageSize
//     * @param orderList
//     * @return
//     */
//    @Override
//    public PagedList<FileDefinition> queryPagedList(Map<String, Object> params,
//            int pageIndex, int pageSize, List<Order> orderList) {
//        return this.myBatisDaoSupport.<FileDefinition> queryPagedList("fileDefinition.queryFileDefinition",
//                params,
//                pageIndex,
//                pageSize,
//                orderList);
//    }
//
//    /**
//     * @param updateRowMap
//     * @return
//     */
//    @Override
//    public int updateFileDefinition(Map<String, Object> updateRowMap) {
//        return this.myBatisDaoSupport.updateFileDefinition("fileDefinition.updateFileDefinition",
//                updateRowMap);
//    }
//}
