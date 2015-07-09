import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.type.TypeHandlerRegistry;

import com.tx.core.util.JdbcUtils;

/*
 * 描          述:  <描述>
 * 修  改   人:  rain
 * 修改时间:  2015年7月1日
 * <修改描述:>
 */

/**
 * <功能简述>
 * <功能详细描述>
 * 
 * @author  rain
 * @version  [版本号, 2015年7月1日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class Main {
    public static void main(String[] args) {
        System.out.println(Integer.class == int.class);
        System.out.println("Object : " + JdbcUtils.isSupportedSimpleType(Object.class));
        System.out.println("int : " + JdbcUtils.isSupportedSimpleType(int.class));
        System.out.println("Class : " + JdbcUtils.isSupportedSimpleType(Class.class));
        System.out.println("String : " + JdbcUtils.isSupportedSimpleType(String.class));
        System.out.println("boolean : " + JdbcUtils.isSupportedSimpleType(boolean.class));
        System.out.println("Map : " + JdbcUtils.isSupportedSimpleType(Map.class));
        System.out.println("ArrayList : " + JdbcUtils.isSupportedSimpleType(ArrayList.class));
        System.out.println("HashMap : " + JdbcUtils.isSupportedSimpleType(HashMap.class));
        System.out.println("Main : " + JdbcUtils.isSupportedSimpleType(Main.class));
        System.out.println("Date : " + JdbcUtils.isSupportedSimpleType(Date.class));
        System.out.println("java.util.Date : " + JdbcUtils.isSupportedSimpleType(java.util.Date.class));
        System.out.println("Timestamp : " + JdbcUtils.isSupportedSimpleType(Timestamp.class));
        System.out.println("Time : " + JdbcUtils.isSupportedSimpleType(Time.class));
        System.out.println("BigDecimal : " + JdbcUtils.isSupportedSimpleType(BigDecimal.class));
        
        TypeHandlerRegistry typeHandlerRegistry = new TypeHandlerRegistry();
        
    }
}
