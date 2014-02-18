package com.tx.core.support.jsoup;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Document.OutputSettings;
import org.jsoup.parser.Parser;
import org.jsoup.safety.Whitelist;

import com.tx.core.support.jsoup.exception.JsoupParseException;

/**
  * Jsoup的包装类<br/>
  * 增加自动判断文件编码类型功能
  * 
  * @author  wanxin
  * @version  [版本号, 2013-11-25]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
 */
public abstract class JsoupUtils {
    
    /**
      * 将htmlContext解析为jsoup的Document对象
      *<功能详细描述>
      * @param html
      * @param baseUri
      * @return [参数说明]
      * 
      * @return Document [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public static Document parse(String htmlContext, String baseUri) {
        try {
            return Jsoup.parse(htmlContext, baseUri);
        } catch (Exception e) {
            throw new JsoupParseException(
                    "parse htmlContext exception.htmlContext:{}",
                    new Object[] { htmlContext });
        }
    }
    
    public static Document parse(String html, String baseUri, Parser parser) {
        return Jsoup.parse(html, baseUri);
    }
    
    public static Document parse(String htmlContext) {
        try {
            return Jsoup.parse(htmlContext, "");
        } catch (Exception e) {
            throw new JsoupParseException(
                    "parse htmlContext exception.htmlContext:{}",
                    new Object[] { htmlContext });
        }
    }
    
    public static Connection connect(String url) {
        return Jsoup.connect(url);
    }
    
    public static Document parse(File in, String charsetName, String baseUri)
            throws IOException {
        return Jsoup.parse(in, charsetName, baseUri);
    }
    
//    /**
//      * 根据文件自动判断编码类型,如果不能判断出结果则抛出异常<br/>
//      * <功能详细描述>
//      * @param in
//      * @return
//      * @throws IOException [参数说明]
//      * 
//      * @return Document [返回类型说明]
//      * @exception throws [异常类型] [异常说明]
//      * @see [类、类#方法、类#成员]
//     */
//    public static Document parse(File in) throws IOException {
//        //调用cpdetector工具自动判断编码类型
//        CodepageDetectorProxy detector = CodepageDetectorProxy.getInstance();
//        detector.add(JChardetFacade.getInstance());
//        Charset charset = detector.detectCodepage(in.toURI().toURL());
//        
//        //判断时能正确判断出了编码类型,如果为成功,则抛出异常,提示用户调用方法手动指定编码类型
//        AssertUtils.notNull(charset,
//                "未能自动识别文件编码,请调用parse(File in, String charsetName)方法手动指定编码类型");
//        AssertUtils.notEmpty(charset.name(),
//                "未能自动识别文件编码,请调用parse(File in, String charsetName)方法手动指定编码类型");
//        return Jsoup.parse(in, charset.name());
//    }
    
    public static Document parse(File in, String charsetName)
            throws IOException {
        return Jsoup.parse(in, charsetName);
    }
    
    public static Document parse(InputStream in, String charsetName,
            String baseUri) throws IOException {
        return Jsoup.parse(in, charsetName, baseUri);
    }
    
    public static Document parse(InputStream in, String charsetName,
            String baseUri, Parser parser) throws IOException {
        return Jsoup.parse(in, charsetName, baseUri, parser);
    }
    
    public static Document parseBodyFragment(String bodyHtml, String baseUri) {
        return Jsoup.parseBodyFragment(bodyHtml, baseUri);
    }
    
    public static Document parseBodyFragment(String bodyHtml) {
        return Jsoup.parseBodyFragment(bodyHtml, "");
    }
    
    public static Document parse(URL url, int timeoutMillis) throws IOException {
        return Jsoup.parse(url, timeoutMillis);
    }
    
    public static String clean(String bodyHtml, String baseUri,
            Whitelist whitelist) {
        return Jsoup.clean(bodyHtml, baseUri, whitelist);
    }
    
    public static String clean(String bodyHtml, Whitelist whitelist) {
        return Jsoup.clean(bodyHtml, whitelist);
    }
    
    public static String clean(String bodyHtml, String baseUri,
            Whitelist whitelist, OutputSettings outputSettings) {
        return Jsoup.clean(bodyHtml, baseUri, whitelist, outputSettings);
    }
    
    public static boolean isValid(String bodyHtml, Whitelist whitelist) {
        return Jsoup.isValid(bodyHtml, whitelist);
    }
}
