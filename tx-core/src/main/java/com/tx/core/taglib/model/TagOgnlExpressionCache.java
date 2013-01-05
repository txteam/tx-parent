/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-1-5
 * <修改描述:>
 */
package com.tx.core.taglib.model;

import java.io.StringReader;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import ognl.ExpressionSyntaxException;
import ognl.Node;
import ognl.Ognl;
import ognl.OgnlException;
import ognl.OgnlParser;
import ognl.ParseException;
import ognl.TokenMgrError;

/**
 * ognl表达式缓存类，页面taglib中存在的表达式应该是不会使该类超出缓存的
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2013-1-5]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class TagOgnlExpressionCache {
    
    /**
     * 定义线程安全的hashMap
     */
    private static final Map<String, Node> expressionCache = new ConcurrentHashMap<String, ognl.Node>();
    
    /**
      * 根据表达式以及root对象获取值
      * @param expression
      * @param root
      * @return
      * @throws OgnlException [参数说明]
      * 
      * @return Object [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public static Object getValue(String expression, Object root)
            throws OgnlException {
        return Ognl.getValue(parseExpression(expression), root);
    }
    
    /**
      * 解析表达式
      * @param expression
      * @return
      * @throws OgnlException [参数说明]
      * 
      * @return Object [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    private static Object parseExpression(String expression)
            throws OgnlException {
        try {
            Node node = expressionCache.get(expression);
            if (node == null) {
                node = new OgnlParser(new StringReader(expression)).topLevelExpression();
                expressionCache.put(expression, node);
            }
            return node;
        } catch (ParseException e) {
            throw new ExpressionSyntaxException(expression, e);
        } catch (TokenMgrError e) {
            throw new ExpressionSyntaxException(expression, e);
        }
    }
}
