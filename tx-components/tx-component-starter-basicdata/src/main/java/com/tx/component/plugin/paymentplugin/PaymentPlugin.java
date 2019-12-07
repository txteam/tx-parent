//package com.tx.component.plugin.paymentplugin;
//
//import java.math.BigDecimal;
//import java.math.RoundingMode;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
//import javax.inject.Inject;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.apache.commons.beanutils.ConvertUtils;
//import org.apache.commons.lang.ArrayUtils;
//import org.apache.commons.lang.StringUtils;
//import org.apache.commons.lang.builder.CompareToBuilder;
//import org.apache.commons.lang.builder.EqualsBuilder;
//import org.apache.commons.lang.builder.HashCodeBuilder;
//import org.springframework.stereotype.Component;
//import org.springframework.util.Assert;
//import org.springframework.web.servlet.ModelAndView;
//
//import com.tx.local.Setting;
//import com.tx.local.entity.PaymentTransaction;
//import com.tx.local.entity.PluginConfig;
//import com.tx.local.service.PluginConfigService;
//import com.tx.local.util.SystemUtils;
//
///**
// * Plugin - 支付
// * 
// * @author cqtianxin Team
// * @version 5.0
// */
//public abstract class PaymentPlugin implements Comparable<PaymentPlugin> {
//
//	/**
//	 * "支付方式名称"属性名称
//	 */
//	public static final String PAYMENT_NAME_ATTRIBUTE_NAME = "paymentName";
//
//	/**
//	 * "手续费类型"属性名称
//	 */
//	public static final String FEE_TYPE_ATTRIBUTE_NAME = "feeType";
//
//	/**
//	 * "手续费"属性名称
//	 */
//	public static final String FEE_ATTRIBUTE_NAME = "fee";
//
//	/**
//	 * "LOGO"属性名称
//	 */
//	public static final String LOGO_ATTRIBUTE_NAME = "logo";
//
//	/**
//	 * "描述"属性名称
//	 */
//	public static final String DESCRIPTION_ATTRIBUTE_NAME = "description";
//
//	/**
//	 * 默认超时时间
//	 */
//	public static final Integer DEFAULT_TIMEOUT = 24 * 60 * 60;
//
//	/**
//	 * 默认支付视图名称
//	 */
//	public static final String DEFAULT_PAY_VIEW_NAME = "/shop/payment/pay";
//
//	/**
//	 * 默认支付结果视图名称
//	 */
//	public static final String DEFAULT_PAY_RESULT_VIEW_NAME = "/shop/payment/pay_result";
//
//	/**
//	 * 手续费类型
//	 */
//	public enum FeeType {
//
//		/**
//		 * 按比例收费
//		 */
//		scale,
//
//		/**
//		 * 固定收费
//		 */
//		fixed
//	}
//
//	@Inject
//	private PluginConfigService pluginConfigService;
//
//	/**
//	 * 获取ID
//	 * 
//	 * @return ID
//	 */
//	public String getId() {
//		return getClass().getAnnotation(Component.class).value();
//	}
//
//	/**
//	 * 获取名称
//	 * 
//	 * @return 名称
//	 */
//	public abstract String getName();
//
//	/**
//	 * 获取版本
//	 * 
//	 * @return 版本
//	 */
//	public abstract String getVersion();
//
//	/**
//	 * 获取作者
//	 * 
//	 * @return 作者
//	 */
//	public abstract String getAuthor();
//
//	/**
//	 * 获取网址
//	 * 
//	 * @return 网址
//	 */
//	public abstract String getSiteUrl();
//
//	/**
//	 * 获取安装URL
//	 * 
//	 * @return 安装URL
//	 */
//	public abstract String getInstallUrl();
//
//	/**
//	 * 获取卸载URL
//	 * 
//	 * @return 卸载URL
//	 */
//	public abstract String getUninstallUrl();
//
//	/**
//	 * 获取设置URL
//	 * 
//	 * @return 设置URL
//	 */
//	public abstract String getSettingUrl();
//
//	/**
//	 * 获取是否已安装
//	 * 
//	 * @return 是否已安装
//	 */
//	public boolean getIsInstalled() {
//		return pluginConfigService.pluginIdExists(getId());
//	}
//
//	/**
//	 * 获取插件配置
//	 * 
//	 * @return 插件配置
//	 */
//	public PluginConfig getPluginConfig() {
//		return pluginConfigService.findByPluginId(getId());
//	}
//
//	/**
//	 * 获取是否已启用
//	 * 
//	 * @return 是否已启用
//	 */
//	public boolean getIsEnabled() {
//		PluginConfig pluginConfig = getPluginConfig();
//		return pluginConfig != null ? pluginConfig.getIsEnabled() : false;
//	}
//
//	/**
//	 * 获取属性值
//	 * 
//	 * @param name
//	 *            属性名称
//	 * @return 属性值
//	 */
//	public String getAttribute(String name) {
//		PluginConfig pluginConfig = getPluginConfig();
//		return pluginConfig != null ? pluginConfig.getAttribute(name) : null;
//	}
//
//	/**
//	 * 获取排序
//	 * 
//	 * @return 排序
//	 */
//	public Integer getOrder() {
//		PluginConfig pluginConfig = getPluginConfig();
//		return pluginConfig != null ? pluginConfig.getOrder() : null;
//	}
//
//	/**
//	 * 获取支付方式名称
//	 * 
//	 * @return 支付方式名称
//	 */
//	public String getPaymentName() {
//		PluginConfig pluginConfig = getPluginConfig();
//		return pluginConfig != null ? pluginConfig.getAttribute(PAYMENT_NAME_ATTRIBUTE_NAME) : null;
//	}
//
//	/**
//	 * 获取手续费类型
//	 * 
//	 * @return 手续费类型
//	 */
//	public PaymentPlugin.FeeType getFeeType() {
//		PluginConfig pluginConfig = getPluginConfig();
//		return pluginConfig != null ? PaymentPlugin.FeeType.valueOf(pluginConfig.getAttribute(FEE_TYPE_ATTRIBUTE_NAME)) : null;
//	}
//
//	/**
//	 * 获取手续费
//	 * 
//	 * @return 手续费
//	 */
//	public BigDecimal getFee() {
//		PluginConfig pluginConfig = getPluginConfig();
//		return pluginConfig != null ? new BigDecimal(pluginConfig.getAttribute(FEE_ATTRIBUTE_NAME)) : null;
//	}
//
//	/**
//	 * 获取LOGO
//	 * 
//	 * @return LOGO
//	 */
//	public String getLogo() {
//		PluginConfig pluginConfig = getPluginConfig();
//		return pluginConfig != null ? pluginConfig.getAttribute(LOGO_ATTRIBUTE_NAME) : null;
//	}
//
//	/**
//	 * 获取描述
//	 * 
//	 * @return 描述
//	 */
//	public String getDescription() {
//		PluginConfig pluginConfig = getPluginConfig();
//		return pluginConfig != null ? pluginConfig.getAttribute(DESCRIPTION_ATTRIBUTE_NAME) : null;
//	}
//
//	/**
//	 * 是否支持
//	 * 
//	 * @param request
//	 *            HttpServletRequest
//	 * @return 是否支持
//	 */
//	public boolean supports(HttpServletRequest request) {
//		return true;
//	}
//
//	/**
//	 * 支付前处理
//	 * 
//	 * @param paymentPlugin
//	 *            支付插件
//	 * @param paymentTransaction
//	 *            支付事务
//	 * @param paymentDescription
//	 *            支付描述
//	 * @param extra
//	 *            附加内容
//	 * @param request
//	 *            HttpServletRequest
//	 * @param response
//	 *            HttpServletResponse
//	 * @param modelAndView
//	 *            ModelAndView
//	 * @throws Exception
//	 */
//	public void prePayHandle(PaymentPlugin paymentPlugin, PaymentTransaction paymentTransaction, String paymentDescription, String extra, HttpServletRequest request, HttpServletResponse response, ModelAndView modelAndView) throws Exception {
//		modelAndView.setViewName("redirect:" + paymentPlugin.getPayUrl(paymentPlugin, paymentTransaction));
//	}
//
//	/**
//	 * 支付处理
//	 * 
//	 * @param paymentPlugin
//	 *            支付插件
//	 * @param paymentTransaction
//	 *            支付事务
//	 * @param paymentDescription
//	 *            支付描述
//	 * @param extra
//	 *            附加内容
//	 * @param request
//	 *            HttpServletRequest
//	 * @param response
//	 *            HttpServletResponse
//	 * @param modelAndView
//	 *            ModelAndView
//	 * @throws Exception
//	 */
//	public abstract void payHandle(PaymentPlugin paymentPlugin, PaymentTransaction paymentTransaction, String paymentDescription, String extra, HttpServletRequest request, HttpServletResponse response, ModelAndView modelAndView) throws Exception;
//
//	/**
//	 * 支付后处理
//	 * 
//	 * @param paymentPlugin
//	 *            支付插件
//	 * @param paymentTransaction
//	 *            支付事务
//	 * @param paymentDescription
//	 *            支付描述
//	 * @param extra
//	 *            附加内容
//	 * @param isPaySuccess
//	 *            是否支付成功
//	 * @param request
//	 *            HttpServletRequest
//	 * @param response
//	 *            HttpServletResponse
//	 * @param modelAndView
//	 *            ModelAndView
//	 * @throws Exception
//	 */
//	public void postPayHandle(PaymentPlugin paymentPlugin, PaymentTransaction paymentTransaction, String paymentDescription, String extra, boolean isPaySuccess, HttpServletRequest request, HttpServletResponse response, ModelAndView modelAndView) throws Exception {
//		modelAndView.addObject("paymentTransaction", paymentTransaction);
//		modelAndView.setViewName(DEFAULT_PAY_RESULT_VIEW_NAME);
//	}
//
//	/**
//	 * 判断是否支付成功
//	 * 
//	 * @param paymentPlugin
//	 *            支付插件
//	 * @param paymentTransaction
//	 *            支付事务
//	 * @param paymentDescription
//	 *            支付描述
//	 * @param extra
//	 *            附加内容
//	 * @param request
//	 *            HttpServletRequest
//	 * @param response
//	 *            HttpServletResponse
//	 * @return 是否支付成功
//	 * @throws Exception
//	 */
//	public abstract boolean isPaySuccess(PaymentPlugin paymentPlugin, PaymentTransaction paymentTransaction, String paymentDescription, String extra, HttpServletRequest request, HttpServletResponse response) throws Exception;
//
//	/**
//	 * 获取支付前处理URL
//	 * 
//	 * @param paymentPlugin
//	 *            支付插件
//	 * @param paymentTransaction
//	 *            支付事务
//	 * @return 支付前处理URL
//	 */
//	public String getPrePayUrl(PaymentPlugin paymentPlugin, PaymentTransaction paymentTransaction) {
//		return getPrePayUrl(paymentPlugin, paymentTransaction, null);
//	}
//
//	/**
//	 * 获取支付前处理URL
//	 * 
//	 * @param paymentPlugin
//	 *            支付插件
//	 * @param paymentTransaction
//	 *            支付事务
//	 * @param extra
//	 *            附加内容
//	 * @return 支付前处理URL
//	 */
//	public String getPrePayUrl(PaymentPlugin paymentPlugin, PaymentTransaction paymentTransaction, String extra) {
//		Assert.notNull(paymentPlugin);
//		Assert.hasText(paymentPlugin.getId());
//		Assert.notNull(paymentTransaction);
//		Assert.hasText(paymentTransaction.getSn());
//
//		Setting setting = SystemUtils.getSetting();
//		return setting.getSiteUrl() + "/payment/pre_pay_" + paymentTransaction.getSn() + (StringUtils.isNotEmpty(extra) ? "_" + extra : "");
//	}
//
//	/**
//	 * 获取支付处理URL
//	 * 
//	 * @param paymentPlugin
//	 *            支付插件
//	 * @param paymentTransaction
//	 *            支付事务
//	 * @return 支付处理URL
//	 */
//	public String getPayUrl(PaymentPlugin paymentPlugin, PaymentTransaction paymentTransaction) {
//		return getPayUrl(paymentPlugin, paymentTransaction, null);
//	}
//
//	/**
//	 * 获取支付处理URL
//	 * 
//	 * @param paymentPlugin
//	 *            支付插件
//	 * @param paymentTransaction
//	 *            支付事务
//	 * @param extra
//	 *            附加内容
//	 * @return 支付处理URL
//	 */
//	public String getPayUrl(PaymentPlugin paymentPlugin, PaymentTransaction paymentTransaction, String extra) {
//		Assert.notNull(paymentPlugin);
//		Assert.hasText(paymentPlugin.getId());
//		Assert.notNull(paymentTransaction);
//		Assert.hasText(paymentTransaction.getSn());
//
//		Setting setting = SystemUtils.getSetting();
//		return setting.getSiteUrl() + "/payment/pay_" + paymentTransaction.getSn() + (StringUtils.isNotEmpty(extra) ? "_" + extra : "");
//	}
//
//	/**
//	 * 获取支付后处理URL
//	 * 
//	 * @param paymentPlugin
//	 *            支付插件
//	 * @param paymentTransaction
//	 *            支付事务
//	 * @return 支付后处理URL
//	 */
//	public String getPostPayUrl(PaymentPlugin paymentPlugin, PaymentTransaction paymentTransaction) {
//		return getPostPayUrl(paymentPlugin, paymentTransaction, null);
//	}
//
//	/**
//	 * 获取支付后处理URL
//	 * 
//	 * @param paymentPlugin
//	 *            支付插件
//	 * @param paymentTransaction
//	 *            支付事务
//	 * @param extra
//	 *            附加内容
//	 * @return 支付后处理URL
//	 */
//	public String getPostPayUrl(PaymentPlugin paymentPlugin, PaymentTransaction paymentTransaction, String extra) {
//		Assert.notNull(paymentPlugin);
//		Assert.hasText(paymentPlugin.getId());
//		Assert.notNull(paymentTransaction);
//		Assert.hasText(paymentTransaction.getSn());
//
//		Setting setting = SystemUtils.getSetting();
//		return setting.getSiteUrl() + "/payment/post_pay_" + paymentTransaction.getSn() + (StringUtils.isNotEmpty(extra) ? "_" + extra : "");
//	}
//
//	/**
//	 * 获取超时时间
//	 * 
//	 * @return 超时时间
//	 */
//	public Integer getTimeout() {
//		return PaymentPlugin.DEFAULT_TIMEOUT;
//	}
//
//	/**
//	 * 计算支付手续费
//	 * 
//	 * @param amount
//	 *            金额
//	 * @return 支付手续费
//	 */
//	public BigDecimal calculateFee(BigDecimal amount) {
//		Assert.notNull(amount);
//		Assert.state(amount.compareTo(BigDecimal.ZERO) >= 0);
//
//		if (amount.compareTo(BigDecimal.ZERO) == 0) {
//			return BigDecimal.ZERO;
//		}
//
//		Setting setting = SystemUtils.getSetting();
//		if (PaymentPlugin.FeeType.scale.equals(getFeeType())) {
//			return setting.setScale(amount.multiply(getFee()));
//		} else {
//			return setting.setScale(getFee());
//		}
//	}
//
//	/**
//	 * 计算支付金额
//	 * 
//	 * @param amount
//	 *            金额
//	 * @return 支付金额
//	 */
//	public BigDecimal calculateAmount(BigDecimal amount) {
//		Assert.notNull(amount);
//		Assert.state(amount.compareTo(BigDecimal.ZERO) >= 0);
//
//		return amount.add(calculateFee(amount)).setScale(2, RoundingMode.UP);
//	}
//
//	/**
//	 * 连接Map键值对
//	 * 
//	 * @param map
//	 *            Map
//	 * @param prefix
//	 *            前缀
//	 * @param suffix
//	 *            后缀
//	 * @param separator
//	 *            连接符
//	 * @param ignoreEmptyValue
//	 *            忽略空值
//	 * @param ignoreKeys
//	 *            忽略Key
//	 * @return 字符串
//	 */
//	protected String joinKeyValue(Map<String, Object> map, String prefix, String suffix, String separator, boolean ignoreEmptyValue, String... ignoreKeys) {
//		List<String> list = new ArrayList<>();
//		if (map != null) {
//			for (Map.Entry<String, Object> entry : map.entrySet()) {
//				String key = entry.getKey();
//				String value = ConvertUtils.convert(entry.getValue());
//				if (StringUtils.isNotEmpty(key) && !ArrayUtils.contains(ignoreKeys, key) && (!ignoreEmptyValue || StringUtils.isNotEmpty(value))) {
//					list.add(key + "=" + (value != null ? value : ""));
//				}
//			}
//		}
//		return (prefix != null ? prefix : "") + StringUtils.join(list, separator) + (suffix != null ? suffix : "");
//	}
//
//	/**
//	 * 连接Map值
//	 * 
//	 * @param map
//	 *            Map
//	 * @param prefix
//	 *            前缀
//	 * @param suffix
//	 *            后缀
//	 * @param separator
//	 *            连接符
//	 * @param ignoreEmptyValue
//	 *            忽略空值
//	 * @param ignoreKeys
//	 *            忽略Key
//	 * @return 字符串
//	 */
//	protected String joinValue(Map<String, Object> map, String prefix, String suffix, String separator, boolean ignoreEmptyValue, String... ignoreKeys) {
//		List<String> list = new ArrayList<>();
//		if (map != null) {
//			for (Map.Entry<String, Object> entry : map.entrySet()) {
//				String key = entry.getKey();
//				String value = ConvertUtils.convert(entry.getValue());
//				if (StringUtils.isNotEmpty(key) && !ArrayUtils.contains(ignoreKeys, key) && (!ignoreEmptyValue || StringUtils.isNotEmpty(value))) {
//					list.add(value != null ? value : "");
//				}
//			}
//		}
//		return (prefix != null ? prefix : "") + StringUtils.join(list, separator) + (suffix != null ? suffix : "");
//	}
//
//	/**
//	 * 实现compareTo方法
//	 * 
//	 * @param paymentPlugin
//	 *            支付插件
//	 * @return 比较结果
//	 */
//	public int compareTo(PaymentPlugin paymentPlugin) {
//		if (paymentPlugin == null) {
//			return 1;
//		}
//		return new CompareToBuilder().append(getOrder(), paymentPlugin.getOrder()).append(getId(), paymentPlugin.getId()).toComparison();
//	}
//
//	/**
//	 * 重写equals方法
//	 * 
//	 * @param obj
//	 *            对象
//	 * @return 是否相等
//	 */
//	@Override
//	public boolean equals(Object obj) {
//		if (obj == null) {
//			return false;
//		}
//		if (getClass() != obj.getClass()) {
//			return false;
//		}
//		if (this == obj) {
//			return true;
//		}
//		PaymentPlugin other = (PaymentPlugin) obj;
//		return new EqualsBuilder().append(getId(), other.getId()).isEquals();
//	}
//
//	/**
//	 * 重写hashCode方法
//	 * 
//	 * @return HashCode
//	 */
//	@Override
//	public int hashCode() {
//		return new HashCodeBuilder(17, 37).append(getId()).toHashCode();
//	}
//
//}