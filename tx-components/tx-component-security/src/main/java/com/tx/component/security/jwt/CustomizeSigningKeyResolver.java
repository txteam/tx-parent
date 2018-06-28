/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2018年6月28日
 * <修改描述:>
 */
package com.tx.component.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.SigningKeyResolverAdapter;

/**
 * 自定义签名解析器<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2018年6月28日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class CustomizeSigningKeyResolver extends SigningKeyResolverAdapter {
    
    /**
     * @param header
     * @param claims
     * @return
     */
    @SuppressWarnings("rawtypes")
    @Override
    public byte[] resolveSigningKeyBytes(JwsHeader header, Claims claims) {
        header.getOrDefault(key, defaultValue);
        header.getOrDefault(key, defaultValue);
        return super.resolveSigningKeyBytes(header, claims);
    }
    
    /**
     * @param header
     * @param payload
     * @return
     */
    @SuppressWarnings("rawtypes")
    @Override
    public byte[] resolveSigningKeyBytes(JwsHeader header, String payload) {
        header.getOrDefault(key, defaultValue);
        header.getOrDefault(key, defaultValue);
        return super.resolveSigningKeyBytes(header, payload);
    }
    
}
