/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2019年11月14日
 * <修改描述:>
 */
package test;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import com.tx.component.role.model.Role;
import com.tx.component.role.model.RoleItem;

/**
 * 表达式识别测试<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2019年11月14日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class SecurityAccessExpressionTest {
    
    public static void main(String[] args) {
        ExpressionParser parser = new SpelExpressionParser();
        StandardEvaluationContext ctx = new StandardEvaluationContext();
        
        Expression exp = parser.parseExpression("not true");
        //取出解析结果
        String result = exp.getValue().toString();
        //输出结果
        System.out.println(result);
    }
    
    public static void main1(String[] args)
            throws NoSuchMethodException, SecurityException {
        //创建SpEL表达式的解析器
        ExpressionParser parser = new SpelExpressionParser();
        StandardEvaluationContext ctx = new StandardEvaluationContext();
        //EvaluationContext ctx = new StandardEvaluationContext();
        
        
        //解析表达式'Hello '+' World!'
        Expression exp = parser.parseExpression("'Hello '+' World!'");
        //取出解析结果
        String result = exp.getValue().toString();
        //输出结果
        System.out.println(result);
        
        System.out.println(parser.parseExpression("'HelloWorld'").getValue());
        System.out.println(parser.parseExpression("0xffffff").getValue());
        System.out.println(parser.parseExpression("1.234345e+3").getValue());
        System.out.println(
                parser.parseExpression("new java.util.Date()").getValue());
        
        //User user=new User(9527,"周星驰");
        RoleItem role = new RoleItem();
        role.setId("9527");
        role.setName("周星驰");
        //解析表达式需要的上下文，解析时有一个默认的上下文
        
        //在上下文中设置变量，变量名为user，内容为user对象
        ctx.setVariable("role", role);
        //从用户对象中获得id并+1900，获得解析后的值在ctx上下文中
        String id = (String) parser.parseExpression("#role.getId()")
                .getValue(ctx);
        System.out.println(id);
        
        String[] students = new String[] { "tom", "jack", "rose", "mark",
                "lucy" };
        ctx.setVariable("students", students);
        String student = parser.parseExpression("#students[3]").getValue(ctx,
                String.class);
        System.out.println(student);
        
        List numbers = (List) parser.parseExpression("{1,2,3,4,5}").getValue();
        System.out.println(numbers.get(2) + "");
        List listOfLists = (List) parser
                .parseExpression("{{'a','b'},{'x','y'}}").getValue();
        System.out.println(((List) listOfLists.get(1)).get(1));
        
        RoleItem role1 = new RoleItem();
        role1.setId("9001");
        role1.setName("邹任飞");
        RoleItem role2 = new RoleItem();
        role2.setId("9002");
        role2.setName("练汶峰");
        List<Role> roles = new ArrayList<Role>();
        roles.add(role1);
        roles.add(role2);
        ctx.setVariable("roles", roles);
        String name = parser.parseExpression("#roles[1].name").getValue(ctx,
                String.class);
        System.out.println(name);
        
        String c = parser.parseExpression("'abcdef'.substring(2, 3)")
                .getValue(String.class);
        System.out.println(c);
        
        //true
        boolean trueValue1 = parser.parseExpression("2 == 2")
                .getValue(Boolean.class);
        //false
        boolean falseValue1 = parser.parseExpression("2 < -5.0")
                .getValue(Boolean.class);
        //true
        boolean trueValue2 = parser.parseExpression("'black' < 'block'")
                .getValue(Boolean.class);
        //false，字符xyz是否为int类型
        boolean falseValue2 = parser.parseExpression("'xyz' instanceof T(int)")
                .getValue(Boolean.class);
        //true，正则是否匹配
        boolean trueValue3 = parser
                .parseExpression("'5.00' matches '^-?\\d+(\\.\\d{2})?$'")
                .getValue(Boolean.class);
        //false
        boolean falseValue3 = parser
                .parseExpression("'5.0067' matches '^-?\\d+(\\.\\d{2})?$'")
                .getValue(Boolean.class);
        
        // -- AND 与运算 --
        //false 
        boolean falseValue4 = parser.parseExpression("true and false")
                .getValue(Boolean.class);
        //true，isMember方法用于测试是否为某个对象的成员
        //String expression = "isMember('Nikola Tesla') and isMember('Mihajlo Pupin')";
        //boolean trueValue4 = parser.parseExpression(expression)=.getValue(Boolean.class);
        //System.out.println(trueValue4);
        //
        //        // -- OR 或运算--
        //        //true
        //        boolean trueValue5 = parser.parseExpression("true or false")
        //                .getValue(Boolean.class);
        //        //true
        //        String expression1 = "isMember('Nikola Tesla') or isMember('Albert Einstein')";
        //        boolean trueValue6 = parser.parseExpression(expression)
        //                .getValue(Boolean.class);
        //        //false
        //        boolean falseValue5 = parser.parseExpression("!true")
        //                .getValue(Boolean.class);
        //        //false
        //        String expression2 = "isMember('Nikola Tesla') and !isMember('Mihajlo Pupin')";
        //        boolean falseValue6 = parser.parseExpression(expression)
        //                .getValue(Boolean.class);
        
        // Addition
        int two = parser.parseExpression("1 + 1").getValue(Integer.class); // 2
        String testString = parser.parseExpression("'test' + ' ' + 'string'")
                .getValue(String.class); // 'test string'
        // Subtraction
        int four = parser.parseExpression("1 - -3").getValue(Integer.class); // 4
        double d = parser.parseExpression("1000.00 - 1e4")
                .getValue(Double.class); // -9000
        // Multiplication
        int six = parser.parseExpression("-2 * -3").getValue(Integer.class); // 6
        double twentyFour = parser.parseExpression("2.0 * 3e0 * 4")
                .getValue(Double.class); // 24.0
        // Division
        int minusTwo = parser.parseExpression("6 / -3").getValue(Integer.class); // -2
        double one = parser.parseExpression("8.0 / 4e0 / 2")
                .getValue(Double.class); // 1.0
        // Modulus
        int three = parser.parseExpression("7 % 4").getValue(Integer.class); // 3
        one = parser.parseExpression("8 / 5 % 2").getValue(Integer.class); // 1
        // Operator precedence
        int minusTwentyOne = parser.parseExpression("1+2-3*8")
                .getValue(Integer.class); // -21
        
        ctx.registerFunction("reverse",
                StringUtils.class.getDeclaredMethod("reverse",
                        new Class[] { String.class }));
        String helloWorldReversed = parser.parseExpression("#reverse('hello')")
                .getValue(ctx, String.class);
        System.out.println(helloWorldReversed);
        
        //parser.parseExpression(expressionString, context)
        
        RoleItem roleTest = new RoleItem();
        roleTest.setId("1234567890");
        roleTest.setName("testRoleName");
        System.out.println(parser.parseExpression("name").getValue(ctx, roleTest));
        System.out.println(parser.parseExpression("getName()").getValue(ctx, roleTest));
    }
    
    
    //    /** 表达式解析器 */
    //    private static ExpressionParser parser = new SpelExpressionParser();
    //    
    //    /** 表达式运行环境 */
    //    private static StandardEvaluationContext ctx = new StandardEvaluationContext();
    //    
    //    static {
    //        try {
    //            ctx.registerFunction("hasAuth",
    //                    SecurityContext.class.getDeclaredMethod("hasAuth",
    //                            String.class));
    //            ctx.registerFunction("hasAnyAuth",
    //                    SecurityContext.class.getDeclaredMethod("hasAnyAuth",
    //                            String.class));
    //            
    //            ctx.registerFunction("hasRole",
    //                    SecurityContext.class.getDeclaredMethod("hasRole",
    //                            String.class));
    //            ctx.registerFunction("hasAnyRole",
    //                    SecurityContext.class.getDeclaredMethod("hasAnyRole",
    //                            String.class));
    //            
    //            ctx.registerFunction("hasAuthority",
    //                    SecurityContext.class.getDeclaredMethod("hasAuthority",
    //                            String.class));
    //            ctx.registerFunction("hasAnyAuthority",
    //                    SecurityContext.class.getDeclaredMethod("hasAnyAuthority",
    //                            String.class));
    //        } catch (NoSuchMethodException | SecurityException e) {
    //            throw new SILException("表达式解析注册异常.", e);
    //        }
    //    }
}
