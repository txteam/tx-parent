import java.util.List;

import com.tx.core.util.IDCardUtils;

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
        List<String> ss = IDCardUtils.cal("5001071987", "17");
        for (String string : ss) {
            System.out.println(string);
        }
    }
}
