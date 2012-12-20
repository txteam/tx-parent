/*
 * 描          述:  <描述>
 * 修  改   人:  PengQingyang
 * 修改时间:  2012-12-13
 * <修改描述:>
 */
package com.tx.core.mybatis.generator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.apache.commons.httpclient.util.DateUtil;

import com.ctc.wstx.util.StringUtil;

/**
 * 基础model生成器
 * <功能详细描述>
 * 
 * @author  PengQingyang copy from liujun
 * @version  [版本号, 2012-12-13]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public abstract class BaseModelGenerator {
    @Resource(name = "datasource")
    private DataSource datasource;
    
    private Map<String, String> mappingType = null;//数据库类型和java类型的映射集合，key为数据类型，value为java数据类型
    
    private String packageName = null;//包名
    
    private String className = null;//类名
    
    private String tableName = null;//表名
    
    /**
     * 获取表的注释
     * @author liujun
     * @return 返回String
     * */
    public abstract String getTableOfComemnt() throws Exception;
    
    /**
     *  获取表字段、类型、注释、是否主键
     *  @author liujun
     * */
    public abstract List<GenerateCode> getTableOfColumn() throws Exception;
    
    /**
     * 返回tableName
     * @author liujun
     * */
    public String getTableName() {
        return tableName;
    }
    
    /**
     * 获取数据库连接(Connection)
     * @author liujun
     * */
    public Connection getConnection() {
        try {
            return datasource.getConnection();
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    /**
     * 获取数据库连接(DataSource)
     * @author liujun
     * @return DataSource
     * */
    public DataSource getDataSource() {
        return datasource;
    }
    
    /**
     * 关闭连接
     * */
    public void close(ResultSet rs, PreparedStatement psts, Connection con) {
        if (null != rs) {
            try {
                rs.close();
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        if (null != psts) {
            try {
                psts.close();
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        if (null != con) {
            try {
                con.close();
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * 设置类名
     * 
     * @author liujun
     * @param className类名，如果传入的值为空则抛出“类名不能为空”的异常
     * */
    public void setClassName(String className) {
        if (null == className || StringUtil.isAllWhitespace(className)) {
            throw new RuntimeException("类名不能为空");
        }
        else {
            this.className = className;
        }
    }
    
    /**
     * 设置表名
     * 
     * @author liujun
     * @param tableName 表名
     * */
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
    
    /**
     * 设置包名
     * @author liujun
     * @param pageName 包名,如果传入的包名为空,则取默认值 " com.boda.los"
     * */
    public void setPackageName(String pageName) {
        if (null == pageName || StringUtil.isAllWhitespace(pageName)) {
            this.packageName = " com.boda.los";
        }
        else {
            this.packageName = pageName;
        }
    }
    
    /**
     * 创建Java字段和数据库字段映射关系
     * @author liujun
     * 
     * @param maps 数据库类型和java类型的映射集合，key为数据类型，value为java数据类型
     * */
    public void setMappingChars(Map<String, String> maps) {
        if (null == maps || maps.isEmpty()) {
            mappingType = new HashMap<String, String>();
            
            mappingType.put("VARCHAR2", "String");
            mappingType.put("DATE", "java.util.Date");
            mappingType.put("NUMBER", "java.math.BigDecimal");
        }
        else {
            this.mappingType = maps;
        }
    }
    
    /**
     *  获得项目绝对路径
     * @return
     * @throws Exception
     */
    private String getProjectPath() {
        String path = BaseModelGenerator.class.getResource("").getPath();
        String filePath = (path.split("/target/classes/com/boda/components")[0]);
        filePath = filePath + File.separator + "src" + File.separator + "main"
                + File.separator + "java";
        return filePath;
    }
    
    /**
     * 写文件方法
     * @author liujun
     * */
    public void writeFile() throws Exception {
        initemptyProperty();
        emptyInspection();
        
        FileOutputStream fos = null;
        OutputStreamWriter osw = null;
        BufferedWriter bw = null;
        
        try {
            String filePath = formatFilePath();
            
            String comment = getTableOfComemnt();
            List<GenerateCode> lists = getTableOfColumn();
            
            String fileName = filePath + File.separator + className + ".java";
            
            File file = new File(fileName);
            if (file.isFile()) {
                throw new RuntimeException("文件已经存在");
            }
            
            fos = new FileOutputStream(file);
            osw = new OutputStreamWriter(fos, "UTF-8");
            bw = new BufferedWriter(osw);
            
            StringBuilder text = getFileContxt(comment, lists);
            
            bw.write(text.toString());
            
        }
        catch (Exception e) {
            throw e;
        }
        finally {
            if (null != bw) {
                bw.close();
            }
            
            if (null != osw) {
                osw.close();
            }
            
            if (null != fos) {
                fos.close();
            }
        }
    }
    
    /**
     * 关键信息非null检查，为null：抛出异常，不为null:继续执行
     * @author liujun
     * */
    public void emptyInspection() {
        emptyInspection(packageName, "包名不能为空");
        emptyInspection(className, "类名不能为空");
        emptyInspection(tableName, "表名不能为空");
        emptyInspection(mappingType, "Java字段和数据库字段映射集合不能为空");
    }
    
    /**
     * 判断对象是否为null,为null返回true,不为null返回false
     * @author liujun
     * @param obj 要判断的值
     * */
    public Boolean isEmpty(Object obj) {
        if (null == obj) {
            return true;
        }
        return false;
    }
    
    /**
     * 格式化方法名称,采用骆驼命名法
     * @author liujun
     * @param value 要格式化的方法名称
     * */
    public String formatMethodName(String value) {
        if (null != value && value.length() > 0) {
            if (value.length() > 1) {
                value = value.substring(0, 1).toUpperCase()
                        + value.substring(1);
            }
            else {
                value = value.substring(0, 1).toUpperCase();
            }
        }
        return value;
    }
    
    /**
     * 以chars将value格式化为数组，字段
     * */
    public String formatcharsName(String chars, String value) {
        if (value.indexOf(chars) > 0) {
            String[] temp = value.split(chars);
            value = "";
            for (String str : temp) {
                if (str.length() > 1) {
                    value = value + str.substring(0, 1).toUpperCase()
                            + str.substring(1).toLowerCase();
                }
                else if (str.length() == 1) {
                    value = value + str.toUpperCase();
                }
            }
        }
        
        if (value.length() > 1) {
            value = value.substring(0, 1).toLowerCase() + value.substring(1);
        }
        else {
            value = value.substring(0, 1).toLowerCase();
        }
        return value;
    }
    
    /**
     * 将包名替换为文件路径
     * @author liujun
     * */
    public String formatFilePath() {
        String classPath = getProjectPath();
        String[] temp = packageName.split("\\.");
        for (String str : temp) {
            str = str.trim();
            if (str.length() > 0) {
                classPath += File.separator + str;
            }
        }
        return classPath;
    }
    
    /**
     * 判断value值是否为null，为null 抛出errorMsg信息异常
     * @author liujun
     * */
    private void emptyInspection(Object value, String errorMsg) {
        if (isEmpty(value)) {
            throw new RuntimeException(errorMsg);
        }
    }
    
    /**
     * 将类和字段说明生成domain内容，并以字符串形式返回
     * @author liujun
     * @param comment类的说明
     * @param lists 字段及说明
     * @return 生成好的字符串
     * */
    private StringBuilder getFileContxt(String comment, List<GenerateCode> lists) {
        StringBuilder text = new StringBuilder();
        String time = DateUtil.formatDate(new Date(), "yyyy-MM-dd");
        
        text.append("package " + packageName + ";\r\n");
        text.append("\r\n");
        text.append("import org.apache.ibatis.type.Alias;\r\n");
        text.append("import javax.persistence.Id;\r\n");
        text.append("\r\n");
        text.append("/**\r\n");
        text.append("* <" + comment + ">\r\n");
        text.append("*\r\n");
        text.append("* @author \r\n");
        text.append("* @version  [版本号, " + time + "]\r\n");
        text.append("* @see  [相关类/方法]\r\n");
        text.append("* @since  [产品/模块版本]\r\n");
        text.append("*/\r\n");
        text.append("\r\n");
        text.append("@Alias(\"" + className + "\")\r\n");
        text.append("public class " + className + "{\r\n");
        
        for (GenerateCode code : lists) {
            String chars = formatcharsName("_", code.getCharacter());
            String charsComment = code.getComment();
            String type = code.getType();
            String charsType = mappingType.get(type);
            
            text.append(" \r\n");
            text.append(" \t /** " + charsComment + " */\r\n");
            text.append("\t private " + charsType + " " + chars + ";");
        }
        
        for (GenerateCode code : lists) {
            String chars = formatcharsName("_", code.getCharacter());
            String charsComment = code.getComment();
            String type = code.getType();
            String charsType = mappingType.get(type);
            String getMethodName = "get" + formatMethodName(chars);
            String setMethodName = "set" + formatMethodName(chars);
            
            text.append(" \r\n");
            text.append(" \t/** \r\n");
            text.append(" \t* @return " + chars + ":" + charsComment + "\r\n");
            text.append(" \t*/\r\n");
            
            if (code.getIsId()) {
                text.append("\t@Id");
                text.append("\r\n");
            }
            
            text.append(" \tpublic " + charsType + " " + getMethodName
                    + "(){\r\n");
            text.append("  \t\treturn " + chars + ";\r\n");
            text.append(" \t}\r\n");
            
            text.append(" \r\n");
            text.append(" \t/** \r\n");
            text.append(" \t*  @param " + chars + " " + charsComment + "\r\n");
            text.append(" \t*/\r\n");
            
            text.append(" \tpublic void " + setMethodName + "(" + charsType
                    + " " + chars + "){\r\n");
            text.append("  \t\tthis." + chars + " = " + chars + ";\r\n");
            text.append(" \t}\r\n");
        }
        text.append("}");
        return text;
    }
    
    /**
     * 查询packageName、className、mappingType是否为null，如果为null，调用默认方法初始化
     * @author liujun
     * */
    private void initemptyProperty() {
        if (isEmpty(packageName)) {
            setPackageName(null);
        }
        
        if (isEmpty(className)) {
            setClassName(null);
        }
        
        if (isEmpty(mappingType)) {
            setMappingChars(null);
        }
    }
    
    /**
     * 表字段描述实例
     * @author liujun
     * */
    public class GenerateCode {
        private String character;//字段
        
        private String type;//类型
        
        private String comment;//描述
        
        private Boolean isId;//是否是主键
        
        public String getCharacter() {
            return character;
        }
        
        public void setCharacter(String character) {
            this.character = character;
        }
        
        public String getType() {
            return type;
        }
        
        public void setType(String type) {
            this.type = type;
        }
        
        public String getComment() {
            if (null == comment) {
                return "";
            }
            return comment;
        }
        
        public void setComment(String comment) {
            this.comment = comment;
        }
        
        public Boolean getIsId() {
            if (null == isId) {
                isId = false;
            }
            return isId;
        }
        
        public void setIsId(Boolean isId) {
            this.isId = isId;
        }
        
        public GenerateCode() {
            
        }
        
        /**
         * 字段描述类
         * @author liujun
         * 
         * @param character 字段
         * @param type 类型
         * @param comment 字段的描述
         * */
        public GenerateCode(String character, String type, String comment) {
            this.character = character;
            this.type = type;
            this.comment = comment;
        }
        
    }
}
