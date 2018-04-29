/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2016年11月9日
 * <修改描述:>
 */
package com.tx.core.ddlutil.helper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.dialect.Dialect;
import org.hibernate.dialect.MySQL5InnoDBDialect;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.tx.core.ddlutil.builder.DDLBuilder;
import com.tx.core.ddlutil.model.JdbcTypeEnum;
import com.tx.core.ddlutil.model.TableColumnDef;
import com.tx.core.ddlutil.model.TableDef;
import com.tx.core.ddlutil.model.TableIndexDef;
import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.util.order.OrderedSupportComparator;

/**
 * ddlutil模块中工具类<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2016年11月9日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public abstract class TableDefHelper {
    
    /** 主键名称 */
    public static final String PRIMARY_KEY_NAME = "PRIMARY";
    
    /**
     * 预处理：写入主键
     * <功能详细描述>
     * @param columns
     * @param indexes
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    public static void preProcess(DDLBuilder<?> createBuilder) {
        List<? extends TableColumnDef> columns = createBuilder.getColumns();
        List<? extends TableIndexDef> indexes = createBuilder.getIndexes();
        
        //预处理：写入主键
        preProcess(createBuilder, columns, indexes);
    }
    
    /** 
     * 预处理：写入主键
     * <功能详细描述>
     * @param createBuilder
     * @param columns
     * @param indexes [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    private static void preProcess(DDLBuilder<?> createBuilder,
            List<? extends TableColumnDef> columns,
            List<? extends TableIndexDef> indexes) {
        //根据索引获取主键名
        StringBuffer pkIndexColumnNames = new StringBuffer();
        List<TableIndexDef> pkIndexes = new ArrayList<>();
        for (TableIndexDef idxTemp : indexes) {
            if (idxTemp.isPrimaryKey()) {
                pkIndexes.add(idxTemp);
            }
        }
        OrderedSupportComparator.sort(pkIndexes);
        int index1 = 0;
        for (TableIndexDef idxTemp : pkIndexes) {
            pkIndexColumnNames.append(idxTemp.getColumnName());
            if ((++index1) < pkIndexes.size()) {
                pkIndexColumnNames.append(",");
            }
        }
        
        //根据索引获取主键名
        StringBuffer pkColumnNames = new StringBuffer();
        List<TableColumnDef> pkColumns = new ArrayList<>();
        for (TableColumnDef columnTemp : columns) {
            if (columnTemp.isPrimaryKey()) {
                pkColumns.add(columnTemp);
            }
        }
        OrderedSupportComparator.sort(pkColumns);
        int index2 = 0;
        for (TableColumnDef columnTemp : pkColumns) {
            pkColumnNames.append(columnTemp.getColumnName());
            if ((++index2) < pkColumns.size()) {
                pkColumnNames.append(",");
            }
        }
        if (CollectionUtils.isEmpty(pkColumns)
                && CollectionUtils.isEmpty(pkIndexes)) {
            //如果主键字段和索引主键为空，直接返回
            return;
        }
        if (!StringUtils.isBlank(pkIndexColumnNames.toString())) {
            AssertUtils.isTrue(pkIndexColumnNames.toString()
                    .toUpperCase()
                    .equals(pkColumnNames.toString().toUpperCase()),
                    "pkColumnNames should equal.byIndex:{} byColumn:{}",
                    new Object[] { pkIndexColumnNames.toString().toUpperCase(),
                            pkColumnNames.toString().toUpperCase() });
        } else {
            String[] columnNames = new String[pkColumns.size()];
            for (int i = 0; i < pkColumns.size(); i++) {
                columnNames[i] = pkColumns.get(i).getColumnName();
            }
            createBuilder.newIndex(true, true, PRIMARY_KEY_NAME, columnNames);
            pkIndexes.clear();
            for (TableIndexDef idxTemp : createBuilder.getIndexes()) {
                if (idxTemp.isPrimaryKey()) {
                    pkIndexes.add(idxTemp);
                }
            }
        }
        validatePrimaryKey(pkColumns, pkIndexes);
    }
    
    /**
      * 解析表主键字段名称<br/>
      * <功能详细描述>
      * @param columns
      * @param indexes
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public static String parsePrimaryKeyColumnNames(
            List<? extends TableColumnDef> columns,
            List<? extends TableIndexDef> indexes) {
        //根据索引获取主键名
        StringBuffer pkIndexColumnNames = new StringBuffer();
        List<TableIndexDef> pkIndexes = new ArrayList<>();
        for (TableIndexDef idxTemp : indexes) {
            if (idxTemp.isPrimaryKey()) {
                pkIndexes.add(idxTemp);
            }
        }
        OrderedSupportComparator.sort(pkIndexes);
        int index1 = 0;
        for (TableIndexDef idxTemp : pkIndexes) {
            pkIndexColumnNames.append(idxTemp.getColumnName());
            if ((++index1) < pkIndexes.size()) {
                pkIndexColumnNames.append(",");
            }
        }
        
        //根据索引获取主键名
        StringBuffer pkColumnNames = new StringBuffer();
        List<TableColumnDef> pkColumns = new ArrayList<>();
        for (TableColumnDef columnTemp : columns) {
            if (columnTemp.isPrimaryKey()) {
                pkColumns.add(columnTemp);
            }
        }
        OrderedSupportComparator.sort(pkColumns);
        int index2 = 0;
        for (TableColumnDef columnTemp : pkColumns) {
            pkColumnNames.append(columnTemp.getColumnName());
            if ((++index2) < pkColumns.size()) {
                pkColumnNames.append(",");
            }
        }
        if (!StringUtils.isBlank(pkIndexColumnNames.toString())) {
            AssertUtils.isTrue(pkIndexColumnNames.toString()
                    .toUpperCase()
                    .equals(pkColumnNames.toString().toUpperCase()),
                    "pkColumnNames should equal.byIndex:{} byColumn:{}",
                    new Object[] { pkIndexColumnNames.toString().toUpperCase(),
                            pkColumnNames.toString().toUpperCase() });
            
        }
        String columnNames = pkColumnNames.toString();
        return columnNames;
    }
    
    private static void validatePrimaryKey(
            List<? extends TableColumnDef> columns,
            List<? extends TableIndexDef> indexes) {
        //根据索引获取主键名
        StringBuffer pkIndexColumnNames = new StringBuffer();
        List<TableIndexDef> pkIndexes = new ArrayList<>();
        for (TableIndexDef idxTemp : indexes) {
            if (idxTemp.isPrimaryKey()) {
                pkIndexes.add(idxTemp);
            }
        }
        OrderedSupportComparator.sort(pkIndexes);
        int index1 = 0;
        for (TableIndexDef idxTemp : pkIndexes) {
            pkIndexColumnNames.append(idxTemp.getColumnName());
            if ((++index1) < pkIndexes.size()) {
                pkIndexColumnNames.append(",");
            }
        }
        
        //根据索引获取主键名
        StringBuffer pkColumnNames = new StringBuffer();
        List<TableColumnDef> pkColumns = new ArrayList<>();
        for (TableColumnDef columnTemp : columns) {
            if (columnTemp.isPrimaryKey()) {
                pkColumns.add(columnTemp);
            }
        }
        OrderedSupportComparator.sort(pkColumns);
        int index2 = 0;
        for (TableColumnDef columnTemp : pkColumns) {
            pkColumnNames.append(columnTemp.getColumnName());
            if ((++index2) < pkColumns.size()) {
                pkColumnNames.append(",");
            }
        }
        AssertUtils.isTrue(pkIndexColumnNames.toString()
                .toUpperCase()
                .equals(pkColumnNames.toString().toUpperCase()),
                "pkColumnNames should equals.byIndex:{} byColumn:{}",
                new Object[] { pkIndexColumnNames.toString().toUpperCase(),
                        pkColumnNames.toString().toUpperCase() });
    }
    
    /**
      * 根据JdbcType对比判断是否需要修改<br/>
      * <功能详细描述>
      * @param newJdbcType
      * @param sourceJdbcType
      * @return [参数说明]
      * 
      * @return boolean [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    @SuppressWarnings("incomplete-switch")
    private static boolean isNeedUpdateForJdbcType(JdbcTypeEnum newJdbcType,
            JdbcTypeEnum sourceJdbcType) {
        AssertUtils.notNull(newJdbcType, "newJdbcType is null.");
        AssertUtils.notNull(sourceJdbcType, "sourceJdbcType is null.");
        
        if (newJdbcType.equals(sourceJdbcType)) {
            return false;
        }
        
        switch (newJdbcType) {
            case TINYINT:
            case SMALLINT:
            case INT:
            case INTEGER:
            case BIGINT:
                switch (sourceJdbcType) {
                    case TINYINT:
                    case SMALLINT:
                    case INT:
                    case INTEGER:
                    case BIGINT:
                        return false;
                }
                break;
            case FLOAT:
            case DOUBLE:
            case REAL:
            case NUMERIC:
            case DECIMAL:
                switch (sourceJdbcType) {
                    case FLOAT:
                    case DOUBLE:
                    case REAL:
                    case NUMERIC:
                    case DECIMAL:
                        return false;
                }
                break;
            case CHAR:
            case VARCHAR:
            case NCHAR:
            case NVARCHAR:
            case TEXT:
            case LONGTEXT:
            case LONGVARCHAR:
            case TINYTEXT:
                switch (sourceJdbcType) {
                    case CHAR:
                    case VARCHAR:
                    case NCHAR:
                    case NVARCHAR:
                    case TEXT:
                    case LONGTEXT:
                    case LONGVARCHAR:
                    case TINYTEXT:
                        return false;
                }
                break;
            case DATE:
            case DATETIME:
            case TIMESTAMP:
            case TIME:
                switch (sourceJdbcType) {
                    case DATE:
                    case DATETIME:
                    case TIMESTAMP:
                    case TIME:
                        return false;
                }
                break;
            default:
                break;
        }
        
        return true;
    }
    
    /**
      * 字段是否需要进行升级<br/>
      * <功能详细描述>
      * @param newCol
      * @param sourceCol
      * @return [参数说明]
      * 
      * @return boolean [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    private static boolean isNeedUpdateForColumn(TableColumnDef newCol,
            TableColumnDef sourceCol) {
        AssertUtils.notNull(newCol, "newCol is null.");
        AssertUtils.notNull(sourceCol, "sourceCol is null.");
        AssertUtils.isTrue(newCol.getColumnName()
                .equalsIgnoreCase(sourceCol.getColumnName()),
                "newCol.columnName:{} should equalsIgnoreCase sourceCol.columnName:{}",
                new Object[] { newCol.getColumnName(),
                        sourceCol.getColumnName() });
        
        if (isNeedUpdateForJdbcType(newCol.getJdbcType(),
                sourceCol.getJdbcType())) {
            return true;
        }
        if (newCol.getSize() != sourceCol.getSize()) {
            return true;
        }
        if (newCol.getScale() != sourceCol.getScale()) {
            return true;
        }
        if (newCol.isRequired() != sourceCol.isRequired()) {
            return true;
        }
        if (!StringUtils.equals(newCol.getDefaultValue(),
                sourceCol.getDefaultValue())) {
            return true;
        }
        return false;
    }
    
    /**
     * 字段是否需要进行升级<br/>
     * <功能详细描述>
     * @param newCol
     * @param sourceCol
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    private static boolean isNeedUpdateForIndex(List<TableIndexDef> newIndexes,
            List<TableIndexDef> sourceIndexes) {
        AssertUtils.notEmpty(newIndexes, "newIndexes is empty.");
        AssertUtils.notEmpty(sourceIndexes, "sourceIndexes is empty.");
        
        OrderedSupportComparator.sort(newIndexes);
        OrderedSupportComparator.sort(sourceIndexes);
        
        StringBuilder sourceIndexColumns = new StringBuilder();
        int index = 0;
        for (TableIndexDef idx : sourceIndexes) {
            sourceIndexColumns.append(idx.getColumnName());
            if (++index < sourceIndexes.size()) {
                sourceIndexColumns.append(",");
            }
        }
        index = 0;
        StringBuilder newIndexColumns = new StringBuilder();
        for (TableIndexDef idx : newIndexes) {
            newIndexColumns.append(idx.getColumnName());
            if (++index < newIndexes.size()) {
                newIndexColumns.append(",");
            }
        }
        
        boolean flag = isNeedUpdateForIndex(newIndexColumns.toString()
                .toUpperCase(), sourceIndexColumns.toString().toUpperCase());
        if (!flag) {
            if (newIndexes.get(0).isUniqueKey() != sourceIndexes.get(0)
                    .isUniqueKey()) {
                return true;
            }
        }
        return flag;
    }
    
    /**
     * 字段是否需要进行升级<br/>
     * <功能详细描述>
     * @param newCol
     * @param sourceCol
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    private static boolean isNeedUpdateForIndex(String newIndexColumns,
            String sourceIndexColumns) {
        AssertUtils.notEmpty(newIndexColumns, "newIndexColumns is empty.");
        AssertUtils.notEmpty(sourceIndexColumns, "sourceIndexColumns is empty.");
        
        if (!newIndexColumns.equalsIgnoreCase(sourceIndexColumns)) {
            return true;
        }
        return false;
    }
    
    /**
     * 判断是否需要升级表<br/>
     * <功能详细描述>
     * @param newColumns
     * @param newIndexes
     * @param sourceTable
     * @param isIncrementUpdate
     * @param isIgnoreIndexChange
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    public static boolean isNeedAlter(
            List<? extends TableColumnDef> newColumns, TableDef sourceTable,
            boolean isIncrementUpdate) {
        AssertUtils.notEmpty(newColumns, "newColumns is empty.");
        AssertUtils.notNull(sourceTable, "sourceTable is null.");
        AssertUtils.notEmpty(sourceTable.getColumns(),
                "sourceTable.columns is null.");
        
        boolean isNeedUpdate = isNeedAlter(newColumns,
                null,
                sourceTable,
                isIncrementUpdate,
                true);
        return isNeedUpdate;
    }
    
    /**
     * 判断是否需要升级表<br/>
     * <功能详细描述>
     * @param newColumns
     * @param newIndexes
     * @param sourceTable
     * @param isIncrementUpdate
     * @param isIgnoreIndexChange
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    public static boolean isNeedAlter(
            List<? extends TableColumnDef> newColumns,
            List<? extends TableIndexDef> newIndexes, TableDef sourceTable,
            boolean isIncrementUpdate) {
        AssertUtils.notEmpty(newColumns, "newColumns is empty.");
        AssertUtils.notNull(sourceTable, "sourceTable is null.");
        AssertUtils.notEmpty(sourceTable.getColumns(),
                "sourceTable.columns is null.");
        
        boolean isNeedUpdate = isNeedAlter(newColumns,
                null,
                sourceTable,
                isIncrementUpdate,
                false);
        return isNeedUpdate;
    }
    
    /**
      * 判断是否需要升级表<br/>
      * <功能详细描述>
      * @param newColumns
      * @param newIndexes
      * @param sourceTable
      * @param isIncrementUpdate
      * @param isIgnoreIndexChange
      * @return [参数说明]
      * 
      * @return boolean [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public static boolean isNeedAlter(
            List<? extends TableColumnDef> newColumns,
            List<? extends TableIndexDef> newIndexes, TableDef sourceTable,
            boolean isIncrementUpdate, boolean isIgnoreIndexChange) {
        AssertUtils.notEmpty(newColumns, "newColumns is empty.");
        AssertUtils.notNull(sourceTable, "sourceTable is null.");
        AssertUtils.notEmpty(sourceTable.getColumns(),
                "sourceTable.columns is null.");
        
        AlterTableContent content = new AlterTableContent(newColumns,
                newIndexes, sourceTable, isIncrementUpdate, isIgnoreIndexChange);
        boolean isNeedUpdate = content.isNeedAlter();
        return isNeedUpdate;
    }
    
    /**
      * 构建修改表内容实例<br/>
      * <功能详细描述>
      * @param newColumns
      * @param newIndexes
      * @param sourceTable
      * @param isIncrementUpdate
      * @param isIgnoreIndexChange
      * @return [参数说明]
      * 
      * @return AlterTableContent [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public static AlterTableContent buildAlterTableContent(
            List<TableColumnDef> newColumns, List<TableIndexDef> newIndexes,
            TableDef sourceTable, boolean isIncrementUpdate,
            boolean isIgnoreIndexChange) {
        AlterTableContent content = new AlterTableContent(newColumns,
                newIndexes, sourceTable, isIncrementUpdate, isIgnoreIndexChange);
        return content;
    }
    
    /**
      * 表修改对象<br/>
      * <功能详细描述>
      * 
      * @author  Administrator
      * @version  [版本号, 2016年11月14日]
      * @see  [相关类/方法]
      * @since  [产品/模块版本]
     */
    public static class AlterTableContent {
        
        /** 新表的表字段 */
        private final List<? extends TableColumnDef> newColumns;
        
        /** 新表的表索引 */
        private final List<? extends TableIndexDef> newIndexes;
        
        /** 对比表 */
        private final TableDef sourceTable;
        
        /** 是否仅做增量升级 */
        private final boolean isIncrementUpdate;
        
        /** 是否忽略索引变化 */
        private final boolean isIgnoreIndexChange;
        
        private boolean isNeedModifyPrimaryKey = false;
        
        private String primaryKeyName = "PRIMARY";
        
        private String primaryKeyColumnNames;
        
        private final List<TableColumnDef> alterAddColumns = new ArrayList<>();
        
        private final List<TableColumnDef> alterModifyColumns = new ArrayList<>();
        
        private final List<TableColumnDef> alterDeleteColumns = new ArrayList<>();
        
        private final List<TableIndexDef> alterAddIndexes = new ArrayList<>();
        
        private final List<TableIndexDef> alterDeleteIndexes = new ArrayList<>();
        
        /** <默认构造函数> */
        public AlterTableContent(List<? extends TableColumnDef> newColumns,
                List<? extends TableIndexDef> newIndexes, TableDef sourceTable,
                boolean isIncrementUpdate, boolean isIgnoreIndexChange) {
            this.newColumns = newColumns;
            this.newIndexes = newIndexes == null ? new ArrayList<TableIndexDef>()
                    : newIndexes;
            this.sourceTable = sourceTable;
            this.isIncrementUpdate = isIncrementUpdate;
            this.isIgnoreIndexChange = isIgnoreIndexChange;
            
            //校验原表主键：如果存在，在索引以及字段中必须表现一致.
            validatePrimaryKey(sourceTable.getColumns(),
                    sourceTable.getIndexes());
            
            initAlterColumn();//初始化修改字段
            initAlterPrimaryKey();//初始化修改主键
            initAlterIndex();//初始化修改索引
        }
        
        /**
          * 初始化修改字段逻辑<br/>
          * <功能详细描述> [参数说明]
          * 
          * @return void [返回类型说明]
          * @exception throws [异常类型] [异常说明]
          * @see [类、类#方法、类#成员]
         */
        private void initAlterColumn() {
            Map<String, TableColumnDef> sourceMap = new HashMap<String, TableColumnDef>();
            Map<String, TableColumnDef> newMap = new HashMap<String, TableColumnDef>();
            for (TableColumnDef sourceCol : this.sourceTable.getColumns()) {
                sourceMap.put(sourceCol.getColumnName().toUpperCase(),
                        sourceCol);
            }
            
            //遍历新字段
            for (TableColumnDef newCol : this.newColumns) {
                newMap.put(newCol.getColumnName().toUpperCase(), newCol);
                if (!sourceMap.containsKey(newCol.getColumnName().toUpperCase())) {
                    //如果在原表中不存在对应的字段，则添加到需要新增的字段
                    this.alterAddColumns.add(newCol);
                } else if (isNeedUpdateForColumn(newCol,
                        sourceMap.get(newCol.getColumnName().toUpperCase()))) {
                    //根据字段的属性，判断是否需要编辑该字段
                    this.alterModifyColumns.add(newCol);
                }
            }
            //遍历原字段
            for (TableColumnDef sourceCol : this.sourceTable.getColumns()) {
                if (!isIncrementUpdate) {
                    //非增量升级
                    if (!newMap.containsKey(sourceCol.getColumnName()
                            .toUpperCase())) {
                        this.alterDeleteColumns.add(sourceCol);
                    }
                } else {
                    if (!newMap.containsKey(sourceCol.getColumnName()
                            .toUpperCase()) && sourceCol.isRequired()) {
                        //如果是增量升级，需要把原来的必填设置修改为非必填
                        sourceCol.setRequired(false);
                        this.alterModifyColumns.add(sourceCol);
                    }
                }
            }
        }
        
        /**
          * 初始化主键修改<br/>
          * <功能详细描述> [参数说明]
          * 
          * @return void [返回类型说明]
          * @exception throws [异常类型] [异常说明]
          * @see [类、类#方法、类#成员]
         */
        private void initAlterPrimaryKey() {
            String newPrimaryKeyColumnNames = parsePrimaryKeyColumnNames(this.newColumns,
                    this.newIndexes);
            String sourcePrimaryKeyColumnNames = parsePrimaryKeyColumnNames(this.sourceTable.getColumns(),
                    this.sourceTable.getIndexes());
            
            if (newPrimaryKeyColumnNames.equalsIgnoreCase(sourcePrimaryKeyColumnNames)) {
                this.isNeedModifyPrimaryKey = false;
            } else {
                this.isNeedModifyPrimaryKey = true;
            }
            
            for (TableIndexDef priIdx : this.sourceTable.getIndexes()) {
                if (priIdx.isPrimaryKey()) {
                    this.primaryKeyName = priIdx.getIndexName();
                    break;
                }
            }
            this.primaryKeyColumnNames = newPrimaryKeyColumnNames;
        }
        
        /**
          * 初始化修改索引逻辑
          * <功能详细描述> [参数说明]
          * 
          * @return void [返回类型说明]
          * @exception throws [异常类型] [异常说明]
          * @see [类、类#方法、类#成员]
         */
        private void initAlterIndex() {
            if (isIgnoreIndexChange) {
                return;
            }
            
            MultiValueMap<String, TableIndexDef> sourceMap = new LinkedMultiValueMap<String, TableIndexDef>();
            for (TableIndexDef sourceIndex : this.sourceTable.getIndexes()) {
                if (sourceIndex.isPrimaryKey()) {
                    continue;
                }
                sourceMap.add(sourceIndex.getIndexName().toUpperCase(),
                        sourceIndex);
            }
            MultiValueMap<String, TableIndexDef> newMap = new LinkedMultiValueMap<String, TableIndexDef>();
            for (TableIndexDef newIndex : this.newIndexes) {
                if (newIndex.isPrimaryKey()) {
                    continue;
                }
                newMap.add(newIndex.getIndexName().toUpperCase(), newIndex);
            }
            
            //遍历新字段
            for (Entry<String, List<TableIndexDef>> entryTemp : newMap.entrySet()) {
                String indexName = entryTemp.getKey().toUpperCase();
                if (!sourceMap.containsKey(indexName)) {
                    //如果在原表中不存在对应的字段，则添加到需要新增的字段
                    this.alterAddIndexes.addAll(entryTemp.getValue());
                } else if (isNeedUpdateForIndex(entryTemp.getValue(),
                        sourceMap.get(indexName))) {
                    //根据字段的属性，判断是否需要编辑该字段
                    this.alterDeleteIndexes.addAll(entryTemp.getValue());
                    this.alterAddIndexes.addAll(entryTemp.getValue());
                }
            }
            //遍历原字段
            for (Entry<String, List<TableIndexDef>> entryTemp : sourceMap.entrySet()) {
                String indexName = entryTemp.getKey().toUpperCase();
                if (!isIncrementUpdate) {
                    //非增量升级
                    if (!newMap.containsKey(indexName)) {
                        this.alterDeleteIndexes.addAll(entryTemp.getValue());
                    }
                }
            }
        }
        
        /**
          * 判断是否需要更新<br/>
          * <功能详细描述>
          * @return [参数说明]
          * 
          * @return boolean [返回类型说明]
          * @exception throws [异常类型] [异常说明]
          * @see [类、类#方法、类#成员]
         */
        public boolean isNeedAlter() {
            if (CollectionUtils.isNotEmpty(this.alterAddColumns)) {
                return true;
            }
            if (CollectionUtils.isNotEmpty(this.alterModifyColumns)) {
                return true;
            }
            if (CollectionUtils.isNotEmpty(this.alterDeleteColumns)) {
                return true;
            }
            if (CollectionUtils.isNotEmpty(this.alterAddIndexes)) {
                return true;
            }
            if (CollectionUtils.isNotEmpty(this.alterDeleteIndexes)) {
                return true;
            }
            if (isNeedModifyPrimaryKey) {
                return true;
            }
            return false;
        }
        
        /**
         * @return 返回 alterAddColumns
         */
        public List<TableColumnDef> getAlterAddColumns() {
            return alterAddColumns;
        }
        
        /**
         * @return 返回 alterModifyColumns
         */
        public List<TableColumnDef> getAlterModifyColumns() {
            return alterModifyColumns;
        }
        
        /**
         * @return 返回 alterDeleteColumns
         */
        public List<TableColumnDef> getAlterDeleteColumns() {
            return alterDeleteColumns;
        }
        
        /**
         * @return 返回 alterAddIndexes
         */
        public List<TableIndexDef> getAlterAddIndexes() {
            return alterAddIndexes;
        }
        
        /**
         * @return 返回 alterDeleteIndexes
         */
        public List<TableIndexDef> getAlterDeleteIndexes() {
            return alterDeleteIndexes;
        }
        
        /**
         * @return 返回 isNeedModifyPrimaryKey
         */
        public boolean isNeedModifyPrimaryKey() {
            return isNeedModifyPrimaryKey;
        }
        
        /**
         * @return 返回 primaryKeyName
         */
        public String getPrimaryKeyName() {
            return primaryKeyName;
        }
        
        /**
         * @return 返回 newPrimaryKeyColumnNames
         */
        public String getPrimaryKeyColumnNames() {
            return primaryKeyColumnNames;
        }
    }
    
    public static void main(String[] args) {
        Dialect dia = new MySQL5InnoDBDialect();
        System.out.println(dia.getTypeName(JdbcTypeEnum.NUMERIC.getSqlType(),
                10,
                10,
                2));
        //System.out.println(dia.getTypeName(JdbcTypeEnum.DECIMAL.getSqlType(), 10, 10, 2));
    }
}
