package com.tx.core.mybatis.generator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component("testOracle")
public class OracleModelGenerator extends BaseModelGenerator {
    
    @Override
    public String getTableOfComemnt() throws Exception {
        String comement = "";
        Connection con = null;
        PreparedStatement psts = null;
        ResultSet rs = null;
        String sql = "select comments from user_tab_comments where table_Name=?";
        try {
            
            con = getConnection();
            
            psts = con.prepareStatement(sql);
            psts.setString(1, getTableName());
            
            rs = psts.executeQuery();
            
            if (rs.next()) {
                comement = rs.getString("comments");
            }
            
        }
        catch (Exception e) {
            System.out.println(sql);
            throw e;
        }
        finally {
            close(rs, psts, con);
        }
        return comement;
    }
    
    @Override
    public List<GenerateCode> getTableOfColumn() throws Exception {
        Connection con = null;
        PreparedStatement psts = null;
        ResultSet rs = null;
        List<GenerateCode> lists = new ArrayList<GenerateCode>();
        
        StringBuilder sql = new StringBuilder();
        sql.append("select distinct col.column_name,col.comments,m.CONSTRAINT_TYPE,tab.data_type");
        sql.append(" from user_col_comments col ");
        sql.append(" left join user_tab_columns tab on tab.column_name=col.column_name");
        sql.append(" left join USER_CONS_COLUMNS d on d.column_name=col.column_name ");
        sql.append(" left join USER_CONSTRAINTS m on M.CONSTRAINT_NAME=D.CONSTRAINT_NAME ");
        sql.append(" where col.table_Name=?");
        
        try {
            
            con = getConnection();
            
            psts = con.prepareStatement(sql.toString());
            psts.setString(1, getTableName());
            
            rs = psts.executeQuery();
            
            while (rs.next()) {
                GenerateCode code = new GenerateCode(
                        rs.getString("column_name"), rs.getString("data_type"),
                        rs.getString("comments"));
                
                String pk = rs.getString("CONSTRAINT_TYPE");
                if ("P".equals(pk)) {
                    code.setIsId(true);
                }
                
                lists.add(code);
            }
            
        }
        catch (Exception e) {
            System.out.println(sql);
            throw e;
        }
        finally {
            close(rs, psts, con);
        }
        return lists;
    }
}
