package com.tx.core.httpsocket.context.responseheader;

import org.apache.commons.lang3.StringUtils;

import com.tx.core.httpsocket.exception.HttpSocketException;

/** 返回的压缩类型 */
public enum ResponseContentEncoding {
	/** gzip */
	gzip, //
	/** 不压缩 */
	deflate, //
	;

	/** 返回压缩类型,如果找不到或者传入空值则抛错 */
	public static ResponseContentEncoding getResponseContentEncoding(String encoding) {
		if (StringUtils.isEmpty(encoding)) {
			throw new NullPointerException("不能传入空的encoding");
		}
		for (ResponseContentEncoding rce : values()) {
			if (rce.name().equals(encoding)) {
				return rce;
			}
		}
		throw new HttpSocketException("传入了未知的ContentEncoding:[" + encoding + "]");
	}
}
