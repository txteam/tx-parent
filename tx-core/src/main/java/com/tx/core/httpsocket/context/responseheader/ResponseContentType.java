package com.tx.core.httpsocket.context.responseheader;

import org.apache.commons.lang3.StringUtils;

import com.tx.core.exceptions.remote.HttpSocketException;
import com.tx.core.exceptions.util.AssertUtils;

/**
 * 返回内容的编码<br>
 * 默认text/html;charset=utf-8
 * 
 * @author rain
 * 
 */
public class ResponseContentType {
	/** 返回内容的编码格式,默认ResponseContentTypeCode.text_html */
	private ResponseContentTypeCode responseContentTypeCode = ResponseContentTypeCode.text_html;
	/** 返回内容编码,默认null */
	private String encoding = null;

	private ResponseContentType() {
	}

	/**
	 * 创建一个默认内容编码为gb2312,没有其它信息的空白编码<br>
	 * text/html;charset=gb2312
	 * 
	 * @return
	 */
	public static ResponseContentType newResponseContentType() {
		return new ResponseContentType();
	}

	/**
	 * 创建一个内容编码
	 * 
	 * @param contentTypeCode
	 *            返回的内容类型,如果为空则抛错,例子:"text/html"
	 * @param encoding
	 *            返回内容的编码方式,可以为空
	 * @return
	 */
	public static ResponseContentType newResponseContentType(String contentTypeCode, String encoding) {
		ResponseContentType rct = new ResponseContentType();
		rct.responseContentTypeCode = ResponseContentTypeCode.getResponseContentTypeCode(contentTypeCode);
		rct.encoding = encoding;
		return rct;
	}

	/**
	 * 根据Response返回的Header信息来创建内容编码,创建失败抛错<br>
	 * 
	 * @param header
	 *            Content-Type字符串,例子:Content-Type: text/html;charset=gb2312
	 *            Content-Type字符串,例子:Content-Type: text/html
	 * @param encoding
	 *            编码<br>
	 *            如果为空且Content-Type中也没有指定编码则取默认编码:gb2312<br>
	 *            如果为空且Content-Type中有编码则取Content-Type中的编码<br>
	 *            如果不为空则强制指定传入的编码<br>
	 * 
	 * @return
	 */
	public static ResponseContentType newResponseContentTypeByHeader(String header, String encoding) {
		AssertUtils.notEmpty(header,"header is empty.");
		if (header.toUpperCase().startsWith("CONTENT-TYPE:")) {
			ResponseContentType rct = new ResponseContentType();
			String content = header.substring(13);
			String[] split = content.split(";");
			rct.responseContentTypeCode = ResponseContentTypeCode.getResponseContentTypeCode(split[0]);
			if (StringUtils.isNotEmpty(encoding)) {
				rct.encoding = encoding;
			} else {
				try {
					String[] split2 = split[1].split("=");
					if (!StringUtils.isEmpty(split2[0]) && "charset".equals(split2[0].trim().toLowerCase())) {
						rct.encoding = split2[1];
					}
				} catch (Exception e) {
				}
			}
			return rct;
		}
		throw new HttpSocketException("请传入拥有\"Content-Type\"前缀的正确参数:" + header);
	}

	/** 内容的编码格式 */
	public ResponseContentTypeCode getResponseContentTypeCode() {
		return responseContentTypeCode;
	}

	/** 获得返回内容的编码 */
	public String getEncoding() {
		return encoding;
	}

}
