/**
 * 
 */
package com.tx.core.httpsocket.context;

import org.apache.commons.lang3.StringUtils;

import com.tx.core.httpsocket.exception.HttpSocketException;

/**
 * Http协议<br>
 * HTTP/1.0 or HTTP/1.1
 * 
 * @author Rain
 * 
 */
public enum HttpType {
	HTTP_1_0("HTTP/1.0"), //
	HTTP_1_1("HTTP/1.1"), //
	;

	private String httpType;

	/** 获得Http请求类型 */
	public String getHttpType() {
		return this.httpType;
	}

	HttpType(String httpType) {
		this.httpType = httpType;
	}

	@Override
	public String toString() {
		return getHttpType();
	}

	/** 返回http协议版本,如果找不到则抛错 */
	public static HttpType getHttpType(String http) {
		if (StringUtils.isEmpty(http)) {
			throw new NullPointerException("不能传入空的http协议");
		}
		for (HttpType httpType : values()) {
			if (httpType.getHttpType().equals(http)) {
				return httpType;
			}
		}
		throw new HttpSocketException("传入了识别不到的http协议:[" + http + "]");
	}
}
