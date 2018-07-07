/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2018年6月28日
 * <修改描述:>
 */
package com.tx.component.security.jwthandler;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.SigningKeyResolverAdapter;
import io.jsonwebtoken.impl.TextCodec;

/**
 * 自定义签名解析器<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2018年6月28日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public abstract class AbstractSigningKeyResolver
        extends SigningKeyResolverAdapter {
    
    /**
     * @param header
     * @param claims
     * @return
     */
    @SuppressWarnings("rawtypes")
    @Override
    public byte[] resolveSigningKeyBytes(JwsHeader header, Claims claims) {
        String base64EncodedKeyBytes = doGetBase64EncodedKeyBytes(header);
        byte[] keyBytes = TextCodec.BASE64.decode(base64EncodedKeyBytes);
        return keyBytes;
    }
    
    /**
     * @param header
     * @param payload
     * @return
     */
    @SuppressWarnings("rawtypes")
    @Override
    public byte[] resolveSigningKeyBytes(JwsHeader header, String payload) {
        String base64EncodedKeyBytes = doGetBase64EncodedKeyBytes(header);
        byte[] keyBytes = TextCodec.BASE64.decode(base64EncodedKeyBytes);
        return keyBytes;
    }
    
    /**
     * 获取Base64EncodedKeyBytes<br/>
     * <功能详细描述>
     * @param header
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @SuppressWarnings("rawtypes")
    protected abstract String doGetBase64EncodedKeyBytes(JwsHeader header);
}
