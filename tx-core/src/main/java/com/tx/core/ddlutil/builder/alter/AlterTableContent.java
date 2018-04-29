///*
// * 描          述:  <描述>
// * 修  改   人:  Administrator
// * 修改时间:  2018年4月28日
// * <修改描述:>
// */
//package com.tx.core.ddlutil.builder.alter;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Map.Entry;
//
//import org.apache.commons.collections.CollectionUtils;
//import org.springframework.util.LinkedMultiValueMap;
//import org.springframework.util.MultiValueMap;
//
//import com.tx.core.ddlutil.model.TableColumnDef;
//import com.tx.core.ddlutil.model.TableDef;
//import com.tx.core.ddlutil.model.TableIndexDef;
//import com.tx.core.exceptions.util.AssertUtils;
//import com.tx.core.util.order.OrderedSupportComparator;
//
///**
//  * <功能简述>
//  * <功能详细描述>
//  * 
//  * @author  Administrator
//  * @version  [版本号, 2018年4月28日]
//  * @see  [相关类/方法]
//  * @since  [产品/模块版本]
//  */
//public class AlterTableContent {
//    
//    /** 新表的表字段 */
//    private final List<? extends TableColumnDef> newColumns;
//    
//    /** 新表的表索引 */
//    private final List<? extends TableIndexDef> newIndexes;
//    
//    /** 对比表 */
//    private final TableDef sourceTable;
//    
//    /** 是否仅做增量升级 */
//    private final boolean isIncrementUpdate;
//    
//    /** 是否忽略索引变化 */
//    private final boolean isIgnoreIndexChange;
//    
//    private boolean isNeedModifyPrimaryKey = false;
//    
//    private String primaryKeyName = "PRIMARY";
//    
//    private String primaryKeyColumnNames;
//    
//    private final List<TableColumnDef> alterAddColumns = new ArrayList<>();
//    
//    private final List<TableColumnDef> alterModifyColumns = new ArrayList<>();
//    
//    private final List<TableColumnDef> alterDeleteColumns = new ArrayList<>();
//    
//    private final List<TableIndexDef> alterAddIndexes = new ArrayList<>();
//    
//    private final List<TableIndexDef> alterDeleteIndexes = new ArrayList<>();
//    
//    /** <默认构造函数> */
//    public AlterTableContent(List<? extends TableColumnDef> newColumns,
//            List<? extends TableIndexDef> newIndexes, TableDef sourceTable,
//            boolean isIncrementUpdate, boolean isIgnoreIndexChange) {
//        this.newColumns = newColumns;
//        this.newIndexes = newIndexes == null ? new ArrayList<TableIndexDef>()
//                : newIndexes;
//        this.sourceTable = sourceTable;
//        this.isIncrementUpdate = isIncrementUpdate;
//        this.isIgnoreIndexChange = isIgnoreIndexChange;
//        
//        //校验原表主键：如果存在，在索引以及字段中必须表现一致.
//        validatePrimaryKey(sourceTable.getColumns(), sourceTable.getIndexes());
//        
//        initAlterColumn();//初始化修改字段
//        initAlterPrimaryKey();//初始化修改主键
//        initAlterIndex();//初始化修改索引
//    }
//    
//    /**
//     * 初始化修改字段逻辑<br/>
//     * <功能详细描述> [参数说明]
//     * 
//     * @return void [返回类型说明]
//     * @exception throws [异常类型] [异常说明]
//     * @see [类、类#方法、类#成员]
//    */
//   private void initAlterColumn() {
//       Map<String, TableColumnDef> sourceMap = new HashMap<String, TableColumnDef>();
//       Map<String, TableColumnDef> newMap = new HashMap<String, TableColumnDef>();
//       for (TableColumnDef sourceCol : this.sourceTable.getColumns()) {
//           sourceMap.put(sourceCol.getColumnName().toUpperCase(),
//                   sourceCol);
//       }
//       
//       //遍历新字段
//       for (TableColumnDef newCol : this.newColumns) {
//           newMap.put(newCol.getColumnName().toUpperCase(), newCol);
//           if (!sourceMap
//                   .containsKey(newCol.getColumnName().toUpperCase())) {
//               //如果在原表中不存在对应的字段，则添加到需要新增的字段
//               this.alterAddColumns.add(newCol);
//           } else if (isNeedUpdateForColumn(newCol,
//                   sourceMap.get(newCol.getColumnName().toUpperCase()))) {
//               //根据字段的属性，判断是否需要编辑该字段
//               this.alterModifyColumns.add(newCol);
//           }
//       }
//       //遍历原字段
//       for (TableColumnDef sourceCol : this.sourceTable.getColumns()) {
//           if (!isIncrementUpdate) {
//               //非增量升级
//               if (!newMap.containsKey(
//                       sourceCol.getColumnName().toUpperCase())) {
//                   this.alterDeleteColumns.add(sourceCol);
//               }
//           } else {
//               if (!newMap.containsKey(
//                       sourceCol.getColumnName().toUpperCase())
//                       && sourceCol.isRequired()) {
//                   //如果是增量升级，需要把原来的必填设置修改为非必填
//                   sourceCol.setRequired(false);
//                   this.alterModifyColumns.add(sourceCol);
//               }
//           }
//       }
//   }
//   
//   /**
//     * 初始化主键修改<br/>
//     * <功能详细描述> [参数说明]
//     * 
//     * @return void [返回类型说明]
//     * @exception throws [异常类型] [异常说明]
//     * @see [类、类#方法、类#成员]
//    */
//   private void initAlterPrimaryKey() {
//       String newPrimaryKeyColumnNames = parsePrimaryKeyColumnNames(
//               this.newColumns, this.newIndexes);
//       String sourcePrimaryKeyColumnNames = parsePrimaryKeyColumnNames(
//               this.sourceTable.getColumns(),
//               this.sourceTable.getIndexes());
//       
//       if (newPrimaryKeyColumnNames
//               .equalsIgnoreCase(sourcePrimaryKeyColumnNames)) {
//           this.isNeedModifyPrimaryKey = false;
//       } else {
//           this.isNeedModifyPrimaryKey = true;
//       }
//       
//       for (TableIndexDef priIdx : this.sourceTable.getIndexes()) {
//           if (priIdx.isPrimaryKey()) {
//               this.primaryKeyName = priIdx.getIndexName();
//               break;
//           }
//       }
//       this.primaryKeyColumnNames = newPrimaryKeyColumnNames;
//   }
//   
//   /**
//     * 初始化修改索引逻辑
//     * <功能详细描述> [参数说明]
//     * 
//     * @return void [返回类型说明]
//     * @exception throws [异常类型] [异常说明]
//     * @see [类、类#方法、类#成员]
//    */
//   private void initAlterIndex() {
//       if (isIgnoreIndexChange) {
//           return;
//       }
//       
//       MultiValueMap<String, TableIndexDef> sourceMap = new LinkedMultiValueMap<String, TableIndexDef>();
//       for (TableIndexDef sourceIndex : this.sourceTable.getIndexes()) {
//           if (sourceIndex.isPrimaryKey()) {
//               continue;
//           }
//           sourceMap.add(sourceIndex.getIndexName().toUpperCase(),
//                   sourceIndex);
//       }
//       MultiValueMap<String, TableIndexDef> newMap = new LinkedMultiValueMap<String, TableIndexDef>();
//       for (TableIndexDef newIndex : this.newIndexes) {
//           if (newIndex.isPrimaryKey()) {
//               continue;
//           }
//           newMap.add(newIndex.getIndexName().toUpperCase(), newIndex);
//       }
//       
//       //遍历新字段
//       for (Entry<String, List<TableIndexDef>> entryTemp : newMap
//               .entrySet()) {
//           String indexName = entryTemp.getKey().toUpperCase();
//           if (!sourceMap.containsKey(indexName)) {
//               //如果在原表中不存在对应的字段，则添加到需要新增的字段
//               this.alterAddIndexes.addAll(entryTemp.getValue());
//           } else if (isNeedUpdateForIndex(entryTemp.getValue(),
//                   sourceMap.get(indexName))) {
//               //根据字段的属性，判断是否需要编辑该字段
//               this.alterDeleteIndexes.addAll(entryTemp.getValue());
//               this.alterAddIndexes.addAll(entryTemp.getValue());
//           }
//       }
//       //遍历原字段
//       for (Entry<String, List<TableIndexDef>> entryTemp : sourceMap
//               .entrySet()) {
//           String indexName = entryTemp.getKey().toUpperCase();
//           if (!isIncrementUpdate) {
//               //非增量升级
//               if (!newMap.containsKey(indexName)) {
//                   this.alterDeleteIndexes.addAll(entryTemp.getValue());
//               }
//           }
//       }
//   }
//   
//   /**
//    * 字段是否需要进行升级<br/>
//    * <功能详细描述>
//    * @param newCol
//    * @param sourceCol
//    * @return [参数说明]
//    * 
//    * @return boolean [返回类型说明]
//    * @exception throws [异常类型] [异常说明]
//    * @see [类、类#方法、类#成员]
//   */
//   private boolean isNeedUpdateForIndex(List<TableIndexDef> newIndexes,
//           List<TableIndexDef> sourceIndexes) {
//       AssertUtils.notEmpty(newIndexes, "newIndexes is empty.");
//       AssertUtils.notEmpty(sourceIndexes, "sourceIndexes is empty.");
//       
//       OrderedSupportComparator.sort(newIndexes);
//       OrderedSupportComparator.sort(sourceIndexes);
//       
//       StringBuilder sourceIndexColumns = new StringBuilder();
//       int index = 0;
//       for (TableIndexDef idx : sourceIndexes) {
//           sourceIndexColumns.append(idx.getColumnName());
//           if (++index < sourceIndexes.size()) {
//               sourceIndexColumns.append(",");
//           }
//       }
//       index = 0;
//       StringBuilder newIndexColumns = new StringBuilder();
//       for (TableIndexDef idx : newIndexes) {
//           newIndexColumns.append(idx.getColumnName());
//           if (++index < newIndexes.size()) {
//               newIndexColumns.append(",");
//           }
//       }
//       
//       boolean flag = isNeedUpdateForIndex(
//               newIndexColumns.toString().toUpperCase(),
//               sourceIndexColumns.toString().toUpperCase());
//       if (!flag) {
//           if (newIndexes.get(0).isUniqueKey() != sourceIndexes.get(0)
//                   .isUniqueKey()) {
//               return true;
//           }
//       }
//       return flag;
//   }
//   
//   /**
//     * 判断是否需要更新<br/>
//     * <功能详细描述>
//     * @return [参数说明]
//     * 
//     * @return boolean [返回类型说明]
//     * @exception throws [异常类型] [异常说明]
//     * @see [类、类#方法、类#成员]
//    */
//   public boolean isNeedAlter() {
//       if (CollectionUtils.isNotEmpty(this.alterAddColumns)) {
//           return true;
//       }
//       if (CollectionUtils.isNotEmpty(this.alterModifyColumns)) {
//           return true;
//       }
//       if (CollectionUtils.isNotEmpty(this.alterDeleteColumns)) {
//           return true;
//       }
//       if (CollectionUtils.isNotEmpty(this.alterAddIndexes)) {
//           return true;
//       }
//       if (CollectionUtils.isNotEmpty(this.alterDeleteIndexes)) {
//           return true;
//       }
//       if (isNeedModifyPrimaryKey) {
//           return true;
//       }
//       return false;
//   }
//   
//   /**
//    * @return 返回 alterAddColumns
//    */
//   public List<TableColumnDef> getAlterAddColumns() {
//       return alterAddColumns;
//   }
//   
//   /**
//    * @return 返回 alterModifyColumns
//    */
//   public List<TableColumnDef> getAlterModifyColumns() {
//       return alterModifyColumns;
//   }
//   
//   /**
//    * @return 返回 alterDeleteColumns
//    */
//   public List<TableColumnDef> getAlterDeleteColumns() {
//       return alterDeleteColumns;
//   }
//   
//   /**
//    * @return 返回 alterAddIndexes
//    */
//   public List<TableIndexDef> getAlterAddIndexes() {
//       return alterAddIndexes;
//   }
//   
//   /**
//    * @return 返回 alterDeleteIndexes
//    */
//   public List<TableIndexDef> getAlterDeleteIndexes() {
//       return alterDeleteIndexes;
//   }
//   
//   /**
//    * @return 返回 isNeedModifyPrimaryKey
//    */
//   public boolean isNeedModifyPrimaryKey() {
//       return isNeedModifyPrimaryKey;
//   }
//   
//   /**
//    * @return 返回 primaryKeyName
//    */
//   public String getPrimaryKeyName() {
//       return primaryKeyName;
//   }
//   
//   /**
//    * @return 返回 newPrimaryKeyColumnNames
//    */
//   public String getPrimaryKeyColumnNames() {
//       return primaryKeyColumnNames;
//   }
//}
