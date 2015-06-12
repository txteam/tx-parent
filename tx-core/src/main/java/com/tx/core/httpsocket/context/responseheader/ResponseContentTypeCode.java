/**
 * 
 */
package com.tx.core.httpsocket.context.responseheader;

import org.apache.commons.lang3.StringUtils;

import com.tx.core.httpsocket.exception.HttpSocketException;

/**
 * 返回内容的编码格式
 * 
 * @author Rain
 * 
 */
public enum ResponseContentTypeCode {
	application_octet_stream("application/octet-stream", ResponseDataType.二进制流, ""), // 二进制数据
	application_javascript("application/javascript", ResponseDataType.文本, "js"), // js
	application_x_javascript("application/x-javascript", ResponseDataType.文本, "js"), // js
	application_xhtml_xml("application/xhtml+xml", ResponseDataType.文本, "xhtml"), // xhtml
	application_json("application/json",ResponseDataType.文本, "js"),// json
	text_javascript("text/javascript", ResponseDataType.文本, "js"), // js
	text_css("text/css", ResponseDataType.文本, "css"), // css
	text_html("text/html", ResponseDataType.文本, "html"), // html
	text_htm("text/html", ResponseDataType.文本, "htm"), // htm
	text_plain("text/plain", ResponseDataType.文本, "txt"), // txt
	text_xml("text/xml", ResponseDataType.文本, "xml"), // xml
	image_gif("image/gif", ResponseDataType.图片, "gif"), // gif
	image_jpeg_jpg("image/jpeg", ResponseDataType.图片, "jpg"), // jpg
	image_jpeg_jpeg("image/jpeg", ResponseDataType.图片, "jpeg"), // jpeg
	image_bmp("image/bmp", ResponseDataType.图片, "bmp"), // bmp
	image_png("image/png", ResponseDataType.图片, "png"), // png
	audio_mpeg("audio/mpeg", ResponseDataType.音频, "mp3"), // mp3
	
	;

	/** response返回的ContentType字符串 */
	private String contentTypeCode;
	/** 实际返回的内容文件的分类 */
	private ResponseDataType responseDataType;
	/** 实际返回内容文件的后缀名 */
	private String fileSuffixName;

	/** 获得ContentType字符串 */
	public String getContentTypeCode() {
		return contentTypeCode;
	}

	/** 获得内容文件的分类 */
	public ResponseDataType getResponseDataType() {
		return responseDataType;
	}

	/** h获得内容文件的后缀名 */
	public String getFileSuffixName() {
		return fileSuffixName;
	}

	ResponseContentTypeCode(String contentTypeCode, ResponseDataType responseDataType, String fileSuffixName) {
		this.contentTypeCode = contentTypeCode;
		this.responseDataType = responseDataType;
		this.fileSuffixName = fileSuffixName;
	}

	/** 获得内容编码格式,如果不能识别或者传入的值为空,则抛错 */
	public static ResponseContentTypeCode getResponseContentTypeCode(String contentTypeCode) {
		if (StringUtils.isEmpty(contentTypeCode)) {
			throw new NullPointerException("不能传入空的ContentTypeCode");
		}
		for (ResponseContentTypeCode rctc : values()) {
			if (rctc.getContentTypeCode().equals(contentTypeCode.trim().toLowerCase())) {
				return rctc;
			}
		}
		throw new HttpSocketException("传入了未知的ContentTypeCode:[" + contentTypeCode + "]");
	}

	/** 获得内容编码格式,如果不能识别或者传入的值为空,则抛错 */
	public static ResponseContentTypeCode getResponseContentTypeCode(String contentTypeCode, String fileSuffixName) {
		if (StringUtils.isEmpty(contentTypeCode)) {
			throw new NullPointerException("不能传入空的ContentTypeCode");
		}
		if (StringUtils.isEmpty(fileSuffixName)) {
			throw new NullPointerException("不能传入空的返回文件后缀名");
		}
		for (ResponseContentTypeCode rctc : values()) {
			if (rctc.getContentTypeCode().equals(contentTypeCode.trim().toLowerCase()) && rctc.getFileSuffixName().equals(fileSuffixName.trim().toLowerCase())) {
				return rctc;
			}
		}
		throw new HttpSocketException("传入了未知的ContentTypeCode:[" + contentTypeCode + "],fileSuffixName:[" + fileSuffixName + "]");
	}
}
