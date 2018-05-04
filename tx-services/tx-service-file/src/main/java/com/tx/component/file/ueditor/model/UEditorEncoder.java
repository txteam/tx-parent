package com.tx.component.file.ueditor.model;

/**
  * 编码类<br/>
  * 
  * <功能详细描述>
  * @author  PengQY
  * @version  [版本号, 2017年3月9日]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
 */
public class UEditorEncoder {
    
    /**
      * 转换为编码类<br/>
      * <功能详细描述>
      * @param input
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public static String toUnicode(String input) {
        StringBuilder builder = new StringBuilder();
        char[] chars = input.toCharArray();
        for (char ch : chars) {
            if (ch < 256) {
                builder.append(ch);
            } else {
                builder.append("\\u" + Integer.toHexString(ch & 0xffff));
            }
        }
        String unicode = builder.toString();
        return unicode;
    }
}