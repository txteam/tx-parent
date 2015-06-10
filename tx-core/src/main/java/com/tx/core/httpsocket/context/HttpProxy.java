package com.tx.core.httpsocket.context;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Proxy.Type;

public class HttpProxy {
	/** 代理类型,如果为null则不设置代理 */
	private Proxy.Type proxyType = null;
	/** 是否启用代理 */
	private boolean isEnable;
	/** 代理主机 */
	private String proxyHostName;
	/** 代理端口 */
	private int proxyPort;

	/** 默认构造函数 */
	private HttpProxy() {
		super();
	}

	/**
	 * 根据传入的代理设置实例化一个HttpProxy
	 * 
	 * @param isEnable
	 *            是否启用
	 * @param proxyType
	 *            代理类型
	 * @param proxyHostName
	 *            代理主机
	 * @param proxyPort
	 *            代理端口
	 * @return
	 */
	public static HttpProxy newInstance(boolean isEnable, Type proxyType, String proxyHostName, int proxyPort) {
		HttpProxy httpProxy = new HttpProxy();
		httpProxy.setEnable(isEnable);
		httpProxy.setProxyType(proxyType);
		httpProxy.setProxyHostName(proxyHostName);
		httpProxy.setProxyPort(proxyPort);
		return httpProxy;
	}

	/**
	 * 根据传入的代理设置实例化一个HttpProxy
	 * 
	 * @param isEnable
	 *            是否启用
	 * @param proxyType
	 *            代理类型
	 * @param proxyHostName
	 *            代理主机
	 * @param proxyPort
	 *            代理端口
	 * @return
	 */
	public static HttpProxy newInstance(boolean isEnable, Proxy proxy) {
		HttpProxy httpProxy = new HttpProxy();
		httpProxy.setEnable(isEnable);
		httpProxy.setProxyType(proxy.type());
		InetSocketAddress address = (InetSocketAddress) proxy.address();
		httpProxy.setProxyHostName(address.getHostName());
		httpProxy.setProxyPort(address.getPort());
		return httpProxy;
	}

	public String getProxyName() {
		return this.proxyHostName + ":" + this.proxyPort;
	}

	public void setEnable(boolean isEnable) {
		this.isEnable = isEnable;
	}

	public boolean isProxyEnable() {
		return isEnable;
	}

	/**
	 * @return the proxyType
	 */
	public Proxy.Type getProxyType() {
		return proxyType;
	}

	/**
	 * @param proxyType
	 *            the proxyType to set
	 */
	public void setProxyType(Proxy.Type proxyType) {
		this.proxyType = proxyType;
	}

	/**
	 * @return the proxyHostName
	 */
	public String getProxyHostName() {
		return proxyHostName;
	}

	/**
	 * @param proxyHostName
	 *            the proxyHostName to set
	 */
	public void setProxyHostName(String proxyHostName) {
		this.proxyHostName = proxyHostName;
	}

	/**
	 * @return the proxyPort
	 */
	public int getProxyPort() {
		return proxyPort;
	}

	/**
	 * @param proxyPort
	 *            the proxyPort to set
	 */
	public void setProxyPort(int proxyPort) {
		this.proxyPort = proxyPort;
	}

	@Override
	public String toString() {
		return "[" + (proxyType == null ? "null" : proxyType.name()) + ":" + proxyHostName + ":" + proxyPort + "]";
	}
}
